/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.security.auth;

import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.IFacebookRestClient;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;

/**
 *
 * @author serch
 */
public class FBLoginModule implements LoginModule
{

    static Logger log = SWBUtils.getLogger(TripleStoreLoginModule.class);
    protected Subject subject;
    protected CallbackHandler callbackHandler;
    protected Map sharedState;
    protected Map options;
    protected boolean loginflag = false;
    protected User principal = null;
    protected Object credential = null;
    protected String website = null;
    protected String fbSession = null;
    protected String fbSecret = null;
    protected String fbKey = null;
    protected String fbSig = null;
    protected boolean fbflag = false;

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options)
    {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        log.debug("Initialized...");
    }

    public boolean login() throws LoginException
    {
        if (callbackHandler == null)
        {
            throw new LoginException("No callbackHandler or not adecuate callbackHandler supplied");
        }

        String login;
        Callback[] callbacks = new Callback[7];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", false);
        callbacks[2] = new TextInputCallback("Site");
        callbacks[3] = new TextInputCallback("Session");
        callbacks[4] = new TextInputCallback("Secret");
        callbacks[5] = new TextInputCallback("key");
        callbacks[6] = new TextInputCallback("sig");
        try
        {
            log.trace("callback:" + callbackHandler);
            callbackHandler.handle(callbacks);
            login = ((NameCallback) callbacks[0]).getName();
            credential = ((PasswordCallback) callbacks[1]).getPassword();
            ((PasswordCallback) callbacks[1]).clearPassword();
            website = ((TextInputCallback) callbacks[2]).getText();
            fbSession = ((TextInputCallback) callbacks[3]).getText();
            fbSecret = ((TextInputCallback) callbacks[4]).getText();
            fbKey = ((TextInputCallback) callbacks[5]).getText();
            fbSig = ((TextInputCallback) callbacks[6]).getText();
        } catch (IOException ex)
        {
            log.error("IO Error Login a user", ex);
            throw new LoginException("IO Error: " + ex.getMessage());
        } catch (UnsupportedCallbackException ex)
        {
            log.error("UnsupportedCallbackException Error Login a user", ex);
            throw new LoginException("UnsupportedCallbackException Error: " + ex.getMessage());
        }
        log.trace("WebSite id: " + website);
        WebSite ws = SWBContext.getWebSite(website);
        UserRepository ur = ws.getUserRepository();
        principal = ur.getUserByLogin(login);
        //TODO Checar lo del repositorio de usuarios
        if (null == principal)
        {
            principal = ur.getUserByExternalID(fbSession);
            if (null == principal)
            {
                throw new LoginException("User inexistent");
            } else
            {
                fbflag = true;
            }
        }

        //System.out.println(principal.getClass().getName());
        if (!principal.isValid())
        {
            throw new LoginException("User invalid");
        }
        if (null == principal.getPassword())
        {
            throw new LoginException("Password Mistmatch");
        }
        try
        {    //TODO quitar siguiente trace
//            log.trace("passwords (u/c/t): \n" + principal.getPassword() +
//                    "\n" + SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential)) +
//                    "\n" + (new String((char[]) credential)) +
//                    "\n" + principal.getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential))));
            if (fbflag)
            {
                SWB4FacebookBridge bridge = (SWB4FacebookBridge) principal.getUserRepository().getBridge();
                IFacebookRestClient<Object> userClient = new FacebookJsonRestClient(bridge.getAppKey(), bridge.getAppSecret(), fbKey);
                FacebookWebappHelper<Object> facebook = new FacebookWebappHelper(null, null, bridge.getAppKey(), bridge.getAppSecret(), userClient);
                credential = facebook;
                if (!facebook.get_loggedin_user().equals(Long.valueOf(fbSession)))
                {
                    throw new LoginException("fb Auth failed:");
                }
            } else
            {


                if (!principal.getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential))))
                {
                    throw new LoginException("Password Mistmatch:");
                }
            }
        } catch (Exception ex)
        //NoSuchAlgorithmException & UnsupportedEncodingException,
        //Wrapped up, it doesn't matter which one, we just can't do anything else
        {
            log.error("User: Can't compare Passwords", ex);
            throw new LoginException("Digest Failed");
        }
//        }
        loginflag = true;
        principal.setLastLogin(new Date());
        return loginflag;
    }

    public boolean commit() throws LoginException
    {
        boolean flag = false;
        if (!loginflag)
        {
            return false;
        }
        Iterator it = subject.getPrincipals().iterator();
        User tmp = null;
        if (it.hasNext())
        {
            tmp = (User) it.next();
        }
        if (null != tmp)
        {
            tmp.getSemanticObject().setRDFResource(principal.getSemanticObject().getRDFResource());
            flag = true;
        } else
        {
            subject.getPrincipals().add(principal);
        }
        subject.getPrivateCredentials().add(credential);
        try
        {
            principal.checkCredential(credential);
            if (flag)
            {
                tmp.checkCredential(credential);
            }
        } catch (Exception ex)
        //NoSuchAlgorithmException & UnsupportedEncodingException,
        //Wrapped up, it doesn't matter which one, we just can't do anything else
        {
            log.error("Can't set Signed status", ex);
        }
        return loginflag;
    }

    public boolean abort() throws LoginException
    {
        if (subject != null)
        {
            subject.getPrincipals().clear();
            subject.getPrivateCredentials().clear();
            subject.getPublicCredentials().clear();
        }
        return true;
    }

    public boolean logout() throws LoginException
    {
        if (subject != null)
        {
            subject.getPrincipals().clear();
            subject.getPrivateCredentials().clear();
            subject.getPublicCredentials().clear();
        }
        return true;
    }
}
