/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.security.AccessController;
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
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.security.auth.SWB4CallbackHandler;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class Login implements InternalServlet {

    private static Logger log = SWBUtils.getLogger(Login.class);
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
        UserRepository ur = dparams.getWebPage().getWebSite().getUserRepository();
        String authMethod = ur.getProperty(UserRepository.SWBUR_AuthMethod);
        String context = ur.getProperty(UserRepository.SWBUR_LoginContext);
        String CBHClassName = ur.getProperty(UserRepository.SWBUR_CallBackHandlerClassName);
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
                lc = new LoginContext(context, subject);
                lc.logout();
                request.getSession(true).invalidate();
                String url = request.getParameter("_wb_goto");
                if ((url == null || url.equals("/")))
                {
                    url = path +"/"+SWBPlatform.getEnv("swb/distributor")+ "/" +dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
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
            try
            {
                log.debug("New callbackHandler...");
                Constructor[] constructor =  Class.forName(CBHClassName).getConstructors();
                int method = 0;
                for (int i=0; i<constructor.length; i++)
                {
                    if (constructor[i].getParameterTypes().length==4) method = i;
                }
                callbackHandler = (CallbackHandler) constructor[method].newInstance(request, response, authMethod, dparams);
                //callbackHandler = new SWB4CallbackHandlerLoginPasswordImp(request, response, authMethod, dparams); //TODO proveer otros métodos
                session.setAttribute(CALLBACK, callbackHandler);
            } catch (Exception ex)
            {
                log.error("Can't Instanciate a CallBackHandler for UserRepository "+ur.getId(), ex);
                response.sendError(500, "Authentication System failure!!!");
                return;
            }
        } else
        {
            if (null == request.getParameter("_wb_username")){
            log.debug("Request a new username...");
            doResponse(request, response, dparams, null, authMethod);
            session.setAttribute(VALSESS, "Working");
            return;
            }
            ((SWB4CallbackHandler) callbackHandler).setRequest(request);
            ((SWB4CallbackHandler) callbackHandler).setResponse(response);
        }
        if (null == enAuto)
        {
            log.debug("Starts new Authentication process...");
            doResponse(request, response, dparams, null, authMethod);
            session.setAttribute(VALSESS, "Working");
            return;
        }
        LoginContext lc;
        try
        {
            request.getSession(true).invalidate();
            subject = SWBPortal.getUserMgr().getSubject(request);
            lc = new LoginContext(context, subject, callbackHandler);
            lc.login();
        // session.removeAttribute(VALSESS);
        // session.removeAttribute(CALLBACK);
         //   System.out.println(subject.toString());
         //   System.out.println(lc.getSubject().toString());
         //   System.out.println(((User)lc.getSubject().getPrincipals().iterator().next()).isSigned());
        } catch (LoginException ex)
        {
            log.error("Can't log User", ex);
            doResponse(request, response, dparams, "User non existent", authMethod);
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

    private void doResponse(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String alert, String authMethod) throws IOException {
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
                rutaSite = "/" + distributorParams.getWebPage().getWebSite().getId() + ruta;
                login = SWBPlatform.readFileFromWorkPath(rutaSite);
                login = SWBPortal.parseHTML(login, SWBPlatform.getWebWorkPath() + "/" + distributorParams.getWebPage().getWebSite().getId() + "/config/images/");
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
