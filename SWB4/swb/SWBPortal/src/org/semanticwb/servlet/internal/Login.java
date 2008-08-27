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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.security.auth.SWB4CallbackHandler;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class Login implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Login.class);
    static String authMethod = "FORM"; //"BASIC" "FORM"
    
    //Constantes para primer implementación
    
    public void init(ServletContext config) 
    {
        log.event("Login initialized...");
    }    

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException
    {
        HttpSession session = request.getSession(true);
        Subject subject  = (Subject) session.getAttribute("swb4-subject");
        CallbackHandler callbackHandler = (CallbackHandler) session.getAttribute("swb4-callback");
        if (null == callbackHandler){
            log.debug("New callbackHandler...");
            callbackHandler = new SWB4CallbackHandler(request,response,authMethod); //TODO proveer otros métodos
            session.setAttribute("swb4-callback", callbackHandler);
        } else {
            ((SWB4CallbackHandler)callbackHandler).setRequest(request);
            ((SWB4CallbackHandler)callbackHandler).setResponse(response);
        }
        if (null == subject){
            log.debug("New Subject...");
            if ("BASIC".equals(authMethod))
                basicChallenge("Serch Web Builder 4.0", response);
            if ("FORM".equals(authMethod))
                formChallenge(request, response, "ErrMEssage", "Alert");
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
            if ("BASIC".equals(authMethod))
                basicChallenge("Serch Web Builder 4.0", response);
            if ("FORM".equals(authMethod))
                formChallenge(request, response, "ErrMEssage", "Alert");
            subject = new Subject();
            return;
        }
       
    
        response.getWriter().print("Hello Login, Authenticated User: "+subject.getPrincipals().iterator().next().getName());
        //SWBContext.getSemanticMgr().getAdminModel().createResource(SWBContext.getSemanticMgr().getOntology().getResource("http://www.semanticwebbuilder.org/swb4/ontology#WebPage"));
    }        
    
    private void basicChallenge(String realm, HttpServletResponse response){
        StringBuffer header= new StringBuffer();
		header.append("Basic realm=\"");
		header.append(realm);
		header.append("\"");
		response.setHeader("WWW-Authenticate",header.toString());
		response.setHeader("Cache-Control", "no-cache=\"Authorization\"");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    
    private void formChallenge(HttpServletRequest request, HttpServletResponse response, String errorMessage, String alert) throws IOException{
         String ruta = "/config/";
      /*  if (request.getParameter("err") != null) {
            if (user.isRegistered()) {
                ruta += request.getParameter("err");
            } else {
                ruta += "login";
            }
        } else {
        */    ruta += "login";
       // }
       
        
        
        ruta += ".html";
        String login = null;
        try {
           // String rutaSite = "/sites/" + topic.getMap().getId() + ruta;
            //try {

              //  try { 
              //      login = SWBUtils.IO.getFileFromPath(rutaSite + "." + "es" /*user.getLanguage()*/);
                    //login = WBUtils.getInstance().getFileFromWorkPath2(rutaSite + "." + user.getLanguage());
              //  } catch (Exception ignore) {
                    //There is no file for Language, going after regular one
              //  }
              //  if (null == login) login = SWBUtils.IO.getFileFromPath(rutaSite);
                //TODO
                //login = WBUtils.getInstance().parseHTML(login, WBUtils.getInstance().getWebWorkPath() + "/sites/" + topic.getMap() + "/config/images/");
                //login = login.replaceFirst("<WBVERSION>", WBLoader.getInstance().getCoreVersion());
              //  login = login.replaceFirst("<ERRMESSAGE>", errorMessage);
            //} catch (Exception e) {
            ruta = SWBUtils.getApplicationPath()+"work"+ruta; 
            System.out.println(ruta);
                login = SWBUtils.IO.getFileFromPath(ruta);
              //  login = WBUtils.getInstance().parseHTML(login, WBUtils.getInstance().getWebWorkPath() + "/config/images/");
            //} 
                
        } catch (Exception e) {
            log.error("Error to load login page...", e);
        }

        if (request.getParameter("err") != null) response.setStatus(403);
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");

        java.io.PrintWriter out = response.getWriter();

        out.print(login);
        out.print(alert);
        out.flush();
        out.close();
    }
}
