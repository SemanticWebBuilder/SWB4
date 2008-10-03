/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.security.auth.SWB4CallbackHandler;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class Login implements InternalServlet {

    private static Logger log = SWBUtils.getLogger(Login.class);
    //TODO llevar a configuración
    private static String authMethod = "FORM"; //"BASIC" "FORM"
    private static String VALSESS = "swb4-auto";
    private static String CALLBACK = "swb4-callback";
    private static String _realm = "Semantic Web Builder";
    private String _name = "login";
    //Constantes para primer implementación
    public void init(ServletContext config) {
        log.event("Initializing InternalServlet Login...");
    //TODO: preparar los aspectos configurables de la autenticación
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException {
        if (null == dparams.getWebPage())
        {
            return;
        }
        Subject subject = SWBPortal.getUserMgr().getSubject(request);
        HttpSession session = request.getSession(true);
        String enAuto = (String) session.getAttribute(VALSESS);
        String uri = request.getRequestURI();
        String path = SWBPlatform.getContextPath();
        if (request.getParameter("_wb_logout") != null)
        {
            LoginContext lc;
            try
            {
                lc = new LoginContext("swb4TripleStoreModule", subject);
                lc.logout();
                request.getSession(true).invalidate();
                String url = request.getParameter("_wb_goto");
                if ((url == null || url.equals("/")))
                {
                    url = path + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
                    log.debug("LOGOUT3(Path, uri, url): " + path + "   |   " + uri + "    |  " + url);
                    sendRedirect(response, url);
                    return;
                }
            } catch (Exception elo)
            {
                log.error("LoggingOut " + subject, elo);
            }
        }

        if (uri != null)
        {
            path = uri.replaceFirst(_name, SWBPlatform.getEnv("swb/distributor"));
        }
        CallbackHandler callbackHandler = (CallbackHandler) session.getAttribute(CALLBACK);
        if (null == callbackHandler)
        {
            log.debug("New callbackHandler...");
            callbackHandler = new SWB4CallbackHandler(request, response, authMethod, dparams); //TODO proveer otros métodos
            session.setAttribute(CALLBACK, callbackHandler);
        } else
        {
            ((SWB4CallbackHandler) callbackHandler).setRequest(request);
            ((SWB4CallbackHandler) callbackHandler).setResponse(response);
        }
        if (null == enAuto)
        {
            log.debug("Starts new Authentication process...");
            doResponse(request, response, dparams, null);
            session.setAttribute(VALSESS, "Working");
            return;
        }
        LoginContext lc;
        try
        {
            request.getSession(true).invalidate();
            subject = SWBPortal.getUserMgr().getSubject(request);
            lc = new LoginContext("swb4TripleStoreModule", subject, callbackHandler); //TODO: Generar el contexto

            lc.login();
        // session.removeAttribute(VALSESS);
        // session.removeAttribute(CALLBACK);
        } catch (LoginException ex)
        {
            log.error("Can't log User", ex);
            doResponse(request, response, dparams, "User non existent");
            return;
        }

        String url = request.getParameter("_wb_goto");
        if ((url == null || url.equals("/")))
        {
            log.debug("PATHs: Path:" + path + " - " + dparams.getWebPage().getWebSiteId() + " - " + dparams.getWebPage().getId());
            url = SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
        }
        sendRedirect(response, url);
    //response.getWriter().print("Hello Login, Authenticated User: "+subject.getPrincipals().iterator().next().getName());
    }

    private void doResponse(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String alert) throws IOException {
        if ("BASIC".equals(authMethod))
        {
            String realm = (null != distributorParams.getWebPage().getDescription(distributorParams.getUser().getLanguage()) ? distributorParams.getWebPage().getDescription(distributorParams.getUser().getLanguage()) : _realm);
            basicChallenge(realm, response);//TODO Asignar nombre de Realm
        }
        if ("FORM".equals(authMethod))
        {
            formChallenge(request, response, distributorParams, alert);
        }
    }

    private void basicChallenge(String realm, HttpServletResponse response) {
        StringBuffer header = new StringBuffer();
        header.append("Basic realm=\"");
        header.append(realm);
        header.append("\"");
        response.setHeader("WWW-Authenticate", header.toString());
        response.setHeader("Cache-Control", "no-cache=\"Authorization\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void formChallenge(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String alert) throws IOException {
        String ruta = "/config/";
        //TODO: Obtener objetivo del siguiente código
        /*if (request.getParameter("err") != null) {
        if (user.isRegistered()) {
        ruta += request.getParameter("err");
        } else {
        ruta += "login";
        }
        } else {
        */ ruta += "login";
        //}



        ruta += ".html";
        String login = null;
        try
        {


            String rutaSite = SWBPlatform.getWorkPath() + ruta;

            try
            {
                rutaSite = "/sites/" + distributorParams.getWebPage().getWebSite().getId() + "/" + ruta;
                login = SWBPlatform.readFileFromWorkPath(rutaSite);
                login = SWBPortal.parseHTML(login, SWBPlatform.getWebWorkPath() + "/sites/" + distributorParams.getWebPage().getId() + "/config/images/");
            } catch (Exception ignored)
            {
            }
            if (null == login || "".equals(login))
            {
                login = SWBPlatform.readFileFromWorkPath(ruta);
                login = SWBPortal.parseHTML(login, SWBPlatform.getWebWorkPath() + "/config/images/");
            }
            login = login.replaceFirst("<WBVERSION>", SWBPlatform.getVersion());
        } catch (Exception e)
        {
            log.error("Error to load login page...", e);
        }

        if (request.getParameter("err") != null)
        {
            response.setStatus(403);
        }
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");

        java.io.PrintWriter out = response.getWriter();

        out.print(login);
        if (null != alert)
        {
            out.print("<script>alert('" + alert + "');</script>");
        }
        out.flush();
        out.close();
    }

    public void sendRedirect(HttpServletResponse response, String url) {
        try
        {
            response.setContentType("Text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><meta http-equiv=\"Refresh\" CONTENT=\"0; URL=" + url + "\" /><script>window.location='" + url + "';</script></head></html>");
            out.flush();
        //out.close();
        } catch (IOException e)
        {
            log.error("Redirecting user", e);
        }
    }
}
