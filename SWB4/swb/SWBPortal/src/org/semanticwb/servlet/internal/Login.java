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
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.security.auth.SWB4CallbackHandler;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class Login implements InternalServlet
{
    private static Logger log=SWBUtils.getLogger(Login.class);
    //TODO llevar a configuración
    private static String authMethod = "FORM"; //"BASIC" "FORM"
    private static String VALSESS = "swb4-auto";
    private static String CALLBACK = "swb4-callback";
    private static String realm = "Serch Web Builder 4.0";
    
    //Constantes para primer implementación
    
    public void init(ServletContext config) 
    {
        log.event("Initializing InternalServlet Login...");
    }    

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException
    {
        
        Subject subject  = SWBPortal.getUserMgr().getSubject(request);
        HttpSession session = request.getSession(true);
        String enAuto = (String) session.getAttribute(VALSESS);

        CallbackHandler callbackHandler = (CallbackHandler) session.getAttribute(CALLBACK);
        if (null == callbackHandler){
            log.debug("New callbackHandler...");
            callbackHandler = new SWB4CallbackHandler(request,response,authMethod, dparams); //TODO proveer otros métodos
            session.setAttribute(CALLBACK, callbackHandler);
        } else {
            ((SWB4CallbackHandler)callbackHandler).setRequest(request);
            ((SWB4CallbackHandler)callbackHandler).setResponse(response);
        }
        if (null == enAuto){
            log.debug("Starts new Authentication process...");
            doResponse(request, response, dparams, "ErrMessage", "");
            session.setAttribute(VALSESS, "Working");
            return;
        }
        LoginContext lc;
        try {
            lc = new LoginContext("swb4TripleStoreModule", subject, callbackHandler); //TODO: Generar el contexto
            lc.login();
            session.removeAttribute(VALSESS);
            session.removeAttribute(CALLBACK);
        } catch (LoginException ex) {
            log.error("Can't log User", ex);
            doResponse(request, response, dparams, "ErrMessage", "Alert");
            return;
        }
       
    
        response.getWriter().print("Hello Login, Authenticated User: "+subject.getPrincipals().iterator().next().getName());
    }        
    
    private void doResponse(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String errorMessage, String alert) throws IOException{
            if ("BASIC".equals(authMethod))
                basicChallenge(realm, response);//TODO Asignar nombre de Realm
            if ("FORM".equals(authMethod))
                formChallenge(request, response, distributorParams, errorMessage, alert);

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
    
    private void formChallenge(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String errorMessage, String alert) throws IOException{
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
            
            
            String rutaSite = "/sites/" + distributorParams.getWebPage().getWebSite().getId() +"/"+ ruta;
            try {
                login = SWBPlatform.readFileFromWorkPath(rutaSite);
            } catch (IOException iOException) {
            } catch (SWBException sWBException) {
            }
            if (null==login || "".equals(login)){
                login = SWBPlatform.readFileFromWorkPath(ruta);
            }
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
                login = login.replaceFirst("<WBVERSION>", SWBPlatform.getVersion());
                login = login.replaceFirst("<ERRMESSAGE>", errorMessage);
            //} catch (Exception e) {
            //ruta = SWBUtils.getApplicationPath()+"work"+ruta; 
            //System.out.println(ruta);
              //  login = SWBUtils.IO.getFileFromPath(ruta);
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
