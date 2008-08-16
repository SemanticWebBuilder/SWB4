/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.security.auth.SWB4CallbackHandler;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class Login implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Login.class);
    
    //Constantes para primer implementación
    
    public void init(ServletContext config) 
    {
        log.event("Login initialized...");
    }    

    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession(true);
        Subject subject  = (Subject) session.getAttribute("swb4-subject");
        CallbackHandler callbackHandler = (CallbackHandler) session.getAttribute("swb4-callback");
        if (null == callbackHandler){
            log.debug("New callbackHandler...");
            callbackHandler = new SWB4CallbackHandler(request,response,"BASIC"); //TODO proveer otros métodos
            session.setAttribute("swb4-callback", callbackHandler);
        } else {
            ((SWB4CallbackHandler)callbackHandler).setRequest(request);
            ((SWB4CallbackHandler)callbackHandler).setResponse(response);
        }
        if (null == subject){
            log.debug("New Subject...");
            SWB4CallbackHandler.basicChallenge("Serch Web Builder 4.0", response);
            subject = new Subject();
            session.setAttribute("swb4-subject", subject);
            return;
        }
        LoginContext lc;
        try {
            lc = new LoginContext("swb4DBModule", subject, callbackHandler);
            lc.login();
        } catch (LoginException ex) {
            log.error("Can't log User", ex);
            SWB4CallbackHandler.basicChallenge("Serch Web Builder 4.0", response);
            return;
        }
       
    
        response.getWriter().print("Hello Login, Authenticated User: "+subject.getPrincipals().iterator().next().getName());
        //SWBContext.getSemanticMgr().getAdminModel().createResource(SWBContext.getSemanticMgr().getOntology().getResource("http://www.semanticwebbuilder.org/swb4/ontology#WebPage"));
    }        
}
