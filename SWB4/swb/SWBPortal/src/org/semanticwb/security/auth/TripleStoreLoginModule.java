/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.security.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
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
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class TripleStoreLoginModule implements LoginModule {

    static Logger log = SWBUtils.getLogger(TripleStoreLoginModule.class);
    protected Subject subject;
    protected CallbackHandler callbackHandler;
    protected Map sharedState;
    protected Map options;
    protected boolean loginflag = false;
    protected User principal = null;
    protected Object credential = null;
    protected String website = null;

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        log.debug("Initialized...");
    }

    public boolean login() throws LoginException {
        if (callbackHandler == null && !(callbackHandler instanceof SWB4CallbackHandler)) {
            throw new LoginException("No callbackHandler or not adecuate callbackHandler supplied");
        }

        String login;
        Callback[] callbacks = new Callback[3];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", false);
        callbacks[2] = new TextInputCallback("Site");
        try {

            callbackHandler.handle(callbacks);
            login = ((NameCallback) callbacks[0]).getName();
            credential = ((PasswordCallback) callbacks[1]).getPassword();
            ((PasswordCallback) callbacks[1]).clearPassword();
            website = ((TextInputCallback)callbacks[2]).getText();
        } catch (IOException ex) {
            log.error("IO Error Login a user", ex);
            throw new LoginException("IO Error: " + ex.getMessage());
        } catch (UnsupportedCallbackException ex) {
            log.error("UnsupportedCallbackException Error Login a user", ex);
            throw new LoginException("UnsupportedCallbackException Error: " + ex.getMessage());
        }
        WebSite ws = SWBContext.getWebSite(website);
        UserRepository ur = ws.getUserRepository();
        principal = ur.getUserByLogin(login); 
        //TODO Checar lo del repositorio de usuarios
        if (null==principal) throw new LoginException("User inexistent");
        
        System.out.println(principal.getClass().getName());
        if (!principal.isActive()) throw new LoginException("User innactive");
        if (null==principal.getUsrPassword()) {
            if (null!=credential) throw new LoginException("Password Mistmatch");
        } else {
            try {
                if (!principal.getUsrPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential)))) {
                    throw new LoginException("Password Mistmatch");
                }
            } catch (NoSuchAlgorithmException ex) {
                    log.error("User: Can't set a crypted Password", ex);
                    throw new LoginException("Digest Failed");
            }
        }
        loginflag = true;
        principal.setUsrLastLogin(new Date());
        return loginflag;
    }

    public boolean commit() throws LoginException {
        if (!loginflag) {
            return false;
        }
        Iterator it = subject.getPrincipals().iterator();
        User tmp = null;
        if (it.hasNext()) tmp = (User)it.next();
        if (null!=tmp) {
            tmp.getSemanticObject().setRDFResource(principal.getSemanticObject().getRDFResource());
        } else {
            subject.getPrincipals().add(principal);
        }
        subject.getPrivateCredentials().add(credential);
        try
        {
            principal.checkCredential(credential);
        } catch (NoSuchAlgorithmException ex)
        {
            log.error("Can't set Signed status", ex);
        }
        return loginflag;
    }

    public boolean abort() throws LoginException {
        if (subject != null) {
            subject.getPrincipals().clear();
            subject.getPrivateCredentials().clear();
            subject.getPublicCredentials().clear();
        }
        return true;
    }

    public boolean logout() throws LoginException {
        if (subject != null) {
            subject.getPrincipals().clear();
            subject.getPrivateCredentials().clear();
            subject.getPublicCredentials().clear();
        }
        return true;
    }
}
