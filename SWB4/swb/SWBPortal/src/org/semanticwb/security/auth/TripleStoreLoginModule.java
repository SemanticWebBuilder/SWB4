/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.security.auth;

import java.io.IOException;
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
 * The Class TripleStoreLoginModule.
 * 
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class TripleStoreLoginModule implements LoginModule
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
        Callback[] callbacks = new Callback[3];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", false);
        callbacks[2] = new TextInputCallback("Site");
        try
        {
            log.trace("callback:"+callbackHandler);
            callbackHandler.handle(callbacks);
            login = ((NameCallback) callbacks[0]).getName();
            credential = ((PasswordCallback) callbacks[1]).getPassword();
            ((PasswordCallback) callbacks[1]).clearPassword();
            website = ((TextInputCallback) callbacks[2]).getText();
        } catch (IOException ex)
        {
            log.error("IO Error Login a user", ex);
            throw new LoginException("IO Error: " + ex.getMessage());
        } catch (UnsupportedCallbackException ex)
        {
            log.error("UnsupportedCallbackException Error Login a user", ex);
            throw new LoginException("UnsupportedCallbackException Error: " + ex.getMessage());
        }
        log.trace("WebSite id: "+website);
        WebSite ws = SWBContext.getWebSite(website);
        UserRepository ur = ws.getUserRepository();
        principal = ur.getUserByLogin(login);
        log.debug("principal:"+principal);
        //TODO Checar lo del repositorio de usuarios
        if (null == principal)
        {
            throw new LoginException("User inexistent");
        }

        log.debug("isvalid:"+principal.isValid());
        //System.out.println(principal.getClass().getName());
        if (!principal.isValid())
        {
            throw new LoginException("User invalid");
        }
        if (null == principal.getPassword())
        {
            if (null != credential)
            {
                throw new LoginException("Password Mistmatch");
            }
        } else
        {
            try
            {    //TODO quitar siguiente trace
                log.trace("passwords (u/c/t): \n" + principal.getPassword() +
                        "\n" + SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential)) +
                        "\n" + (new String((char[]) credential))+
                        "\n"+ principal.getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential))));
                String alg = principal.getPassword().substring(1,principal.getPassword().indexOf("}")); 
                if (!principal.getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential), alg)))
                {
                    throw new LoginException("Password Mistmatch:");
                }

            } catch (Exception ex)
                    //NoSuchAlgorithmException & UnsupportedEncodingException,
                    //Wrapped up, it doesn't matter which one, we just can't do anything else
            {
                log.error("User: Can't compare Passwords:"+login, ex);
                throw new LoginException("Digest Failed: "+ex.getMessage());
            }
        }
        loginflag = true;
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
            if (flag) tmp.checkCredential(credential);
        } catch (java.security.NoSuchAlgorithmException ex)
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
