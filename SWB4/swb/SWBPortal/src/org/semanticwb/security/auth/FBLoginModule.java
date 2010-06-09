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
import org.semanticwb.model.SWBSessionUser;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;

// TODO: Auto-generated Javadoc
/**
 * The Class FBLoginModule.
 * 
 * @author serch
 */
public class FBLoginModule implements LoginModule
{

    /** The log. */
    static Logger log = SWBUtils.getLogger(TripleStoreLoginModule.class);
    
    /** The subject. */
    protected Subject subject;
    
    /** The callback handler. */
    protected CallbackHandler callbackHandler;
    
    /** The shared state. */
    protected Map sharedState;
    
    /** The options. */
    protected Map options;
    
    /** The loginflag. */
    protected boolean loginflag = false;
    
    /** The principal. */
    protected User principal = null;
    
    /** The credential. */
    protected Object credential = null;
    
    /** The website. */
    protected String website = null;
    
    /** The fb session. */
    protected String fbSession = null;
    
    /** The fb secret. */
    protected String fbSecret = null;
    
    /** The fb key. */
    protected String fbKey = null;
    
    /** The fb sig. */
    protected String fbSig = null;
    
    /** The fbflag. */
    protected boolean fbflag = false;

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject, javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options)
    {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        log.debug("Initialized...");
    }

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#login()
     */
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

                String alg = principal.getPassword().substring(1,principal.getPassword().indexOf("}"));
                if (!principal.getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential), alg)))
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

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#commit()
     */
    public boolean commit() throws LoginException
    {
        boolean flag = false;
        if (!loginflag)
        {
            return false;
        }
        Iterator it = subject.getPrincipals().iterator();
        User tmp = null;
        while(it.hasNext())
        {
            tmp=(User)it.next();
            if(principal.getUserRepository().equals(tmp.getUserRepository()))
            {
                break;
            }
        }
        if (null != tmp)
        {
            ((SWBSessionUser)tmp).updateUser(principal);
            //tmp.getSemanticObject().setRDFResource(principal.getSemanticObject().getRDFResource());
            flag = true;
            //TODO: Pendiente
            //subject.getPrincipals().clear();
            //principal.setDefaultData(tmp);
            //subject.getPrincipals().add(principal);
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

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#abort()
     */
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

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#logout()
     */
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
