/**  
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
 **/
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBSoftkHashMap;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.security.limiter.FailedAttempt;


/**
 * The Internal Servlet Login.
 * 
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class Login implements InternalServlet
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Login.class);
    // private static String VALSESS = "swb4-auto";
    // private static String CALLBACK = "swb4-callback";
    /** The _realm. */
    private static String _realm = "Semantic Web Builder";


    /** The _name. */
    private String _name = "login";
    /** The handle error. */
    private boolean handleError = false;
    //Constantes para primer implementación

    private static SWBSoftkHashMap<String, FailedAttempt> blockedList = new SWBSoftkHashMap<String, FailedAttempt>();
    

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config)
    {
        log.event("Initializing InternalServlet Login...");
        //TODO: preparar los aspectos configurables de la autenticación
    }

    /**
     * Sets the handle error.
     * 
     * @param handleError the new handle error
     */
    public void setHandleError(boolean handleError)
    {
        this.handleError = handleError;
    }

    /**
     * Send login log.
     * 
     * @param request the request
     * @param usr the usr
     */
    public static void sendLoginLog(HttpServletRequest request, User usr)
    {
        //User session log
        {
            StringBuffer logbuf = new StringBuffer();
            logbuf.append("lgn|");
            logbuf.append(request.getRemoteAddr());
            logbuf.append("|");
            logbuf.append(SWBPortal.getMessageCenter().getAddress());
            logbuf.append("|");
            logbuf.append(usr.getSemanticObject().getModel().getName());
            logbuf.append("|");
            String lg = usr.getLogin();
            if (lg == null)
            {
                lg = "_";
            }
            logbuf.append(lg);
            logbuf.append("|");
            logbuf.append("" + request.getSession(true).hashCode());
            SWBPortal.getMessageCenter().sendMessage(logbuf.toString());
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException
    {
        if (null == dparams.getWebPage())
        {
            return;
        }
        UserRepository ur = dparams.getWebPage().getWebSite().getUserRepository();
        String authMethod = ur.getAuthMethod();
        String context = ur.getLoginContext();
        String CBHClassName = ur.getCallBackHandlerClassName();
        Subject subject = SWBPortal.getUserMgr().getSubject(request, dparams.getWebPage().getWebSiteId());
        String uri = request.getRequestURI();
        String path = SWBPlatform.getContextPath();
        User user = null;
        Iterator it = subject.getPrincipals().iterator();
        if (it.hasNext())
        {
            user = (User) it.next();
        }
        if (request.getParameter("wb_logout") != null)
        {
            LoginContext lc;
            try
            {
                lc = new LoginContext(context, subject);
                lc.logout();
                request.getSession(true).invalidate();
                String url = request.getParameter("wb_goto");
                if ((url == null))
                {
                    url = path + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
                }
                log.debug("LOGOUT (Path, uri, url): " + path + "   |   " + uri + "    |  " + url);
                sendRedirect(response, url);
                return;
            } catch (Exception elo)
            {
                log.error("LoggingOut " + subject, elo);
            }
        }

        if (uri != null)
        {
            path = uri.replaceFirst(_name, SWBPlatform.getEnv("swb/distributor"));
        }
        //
        if (null == request.getParameter("wb_username"))
        {
            log.debug("Request a new username...");
            doResponse(request, response, dparams, null, authMethod);
            return;
        } else
        {
            CallbackHandler callbackHandler = null;
            try
            {
                callbackHandler = getHandler(CBHClassName, request, response, authMethod, dparams.getWebPage().getWebSiteId());
            } catch (Exception ex)
            {
                log.error("Can't Instanciate a CallBackHandler for UserRepository " + ur.getId() + "\n" + CBHClassName, ex);
                response.sendError(500, "Authentication System failure!!!");
                return;
            }
            try
            {
                String matchKey = dparams.getWebPage().getWebSiteId()+"|"+request.getParameter("wb_username");
                doLogin(callbackHandler, context, subject, request, matchKey);

            } catch (LoginException ex)
            {
                markFailedAttepmt(request.getParameter("wb_username"));
                log.debug("Can't log User", ex);
                String alert = "User non existent";
                if (isblocked(request.getParameter("wb_username"))){
                    alert = "User has been temporarily blocked";
                }
                doResponse(request, response, dparams, alert, authMethod);
                return;
            }

        }


        //
        String url = request.getParameter("wb_goto");
        if (user.getUserRepository().isExternal())
        {
            if ((url == null || url.equals("/")) && user.getUserRepository().getBridge().doRedirect())
            {
                url = user.getUserRepository().getBridge().getRedirectURL();
            }
        }
        if ((url == null || url.equals("/")))
        {
            log.debug("PATHs: Path:" + path + " - " + dparams.getWebPage().getWebSiteId() + " - " + dparams.getWebPage().getId());
            url =
                    SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
        }

        sendRedirect(response, url);
    }

    /**
     * public void doProcessxx(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException
     * {
     * if (null == dparams.getWebPage())
     * {
     * return;
     * }
     * UserRepository ur = dparams.getWebPage().getWebSite().getUserRepository();
     * String authMethod = ur.getAuthMethod();
     * //String authMethod = ur.getProperty(UserRepository.SWBUR_AuthMethod);
     * String context = ur.getLoginContext();
     * //String context = ur.getProperty(UserRepository.SWBUR_LoginContext);
     * String CBHClassName = ur.getCallBackHandlerClassName();
     * //String CBHClassName = ur.getProperty(UserRepository.SWBUR_CallBackHandlerClassName);
     * Subject subject = SWBPortal.getUserMgr().getSubject(request);
     * HttpSession session = request.getSession(true);
     * String enAuto = (String) session.getAttribute(VALSESS);
     * String uri = request.getRequestURI();
     * String path = SWBPlatform.getContextPath();
     * User user = null;
     * Iterator it = subject.getPrincipals().iterator();
     * if (it.hasNext())
     * {
     * user = (User) it.next();
     * }
     * if (request.getParameter("wb_logout") != null)
     * {
     * LoginContext lc;
     * try
     * {
     * lc = new LoginContext(context, subject);
     * lc.logout();
     * request.getSession(true).invalidate();
     * String url = request.getParameter("wb_goto");
     * if ((url == null || url.equals("/")))
     * {
     * url = path + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
     * log.debug("LOGOUT3(Path, uri, url): " + path + "   |   " + uri + "    |  " + url);
     * sendRedirect(response, url);
     * return;
     * }
     * } catch (Exception elo)
     * {
     * log.error("LoggingOut " + subject, elo);
     * }
     * }
     *
     * if (uri != null)
     * {
     * path = uri.replaceFirst(_name, SWBPlatform.getEnv("swb/distributor"));
     * }
     * if (handleError || user == null || !user.isSigned())
     * {
     * CallbackHandler callbackHandler = (CallbackHandler) session.getAttribute(CALLBACK);
     * if (null == callbackHandler)
     * {
     * if (null != request.getParameter("wb_username"))
     * {
     * enAuto = "Working";
     * }
     * try
     * {
     * log.debug("New callbackHandler...");
     * Constructor[] constructor = Class.forName(CBHClassName).getConstructors();
     * int method = 0;
     * for (int i = 0; i < constructor.length; i++)
     * {
     * if (constructor[i].getParameterTypes().length == 4)
     * {
     * method = i;
     * }
     * }
     * callbackHandler = (CallbackHandler) constructor[method].newInstance(request, response, authMethod, dparams);
     * //callbackHandler = new SWB4CallbackHandlerLoginPasswordImp(request, response, authMethod, dparams); //TODO proveer otros métodos
     * session.setAttribute(CALLBACK, callbackHandler);
     * } catch (Exception ex)
     * {
     * log.error("Can't Instanciate a CallBackHandler for UserRepository " + ur.getId()+"\n"+CBHClassName, ex);
     * response.sendError(500, "Authentication System failure!!!");
     * return;
     * }
     * } else
     * {
     * if (null == request.getParameter("wb_username"))
     * {
     * log.debug("Request a new username...");
     * doResponse(request, response, dparams, null, authMethod);
     * session.setAttribute(VALSESS, "Working");
     * return;
     * }
     * ((SWB4CallbackHandler) callbackHandler).setRequest(request);
     * ((SWB4CallbackHandler) callbackHandler).setResponse(response);
     * }
     * if (null == enAuto)
     * {
     * log.debug("Starts new Authentication process...");
     * doResponse(request, response, dparams, null, authMethod);
     * session.setAttribute(VALSESS, "Working");
     * return;
     * }
     * LoginContext lc;
     * try
     * {
     * log.trace("1er callback "+callbackHandler);
     * request.getSession(true).invalidate();
     * subject = SWBPortal.getUserMgr().getSubject(request);
     * log.trace("es null??? "+((SWB4CallbackHandler) callbackHandler).getRequest()+" y es SWB4 " + (callbackHandler instanceof SWB4CallbackHandler));
     * if (callbackHandler instanceof SWB4CallbackHandler && null == ((SWB4CallbackHandler) callbackHandler).getRequest())
     * {
     * log.trace("intentando...");
     * ((SWB4CallbackHandler) callbackHandler).setRequest(request);
     * ((SWB4CallbackHandler) callbackHandler).setResponse(response);
     *
     * }
     * try
     * {
     * log.trace("testing for null: "+
     * ((SWB4CallbackHandler) callbackHandler).getRequest());
     * } catch (NullPointerException npe)
     * {
     * try
     * {
     * log.trace("re Build callbackHandler...");
     * Constructor[] constructor = Class.forName(CBHClassName).getConstructors();
     * int method = 0;
     * for (int i = 0; i < constructor.length; i++)
     * {
     * if (constructor[i].getParameterTypes().length == 4)
     * {
     * method = i;
     * }
     * }
     * callbackHandler = (CallbackHandler) constructor[method].newInstance(request, response, authMethod, dparams);
     * //callbackHandler = new SWB4CallbackHandlerLoginPasswordImp(request, response, authMethod, dparams); //TODO proveer otros métodos
     * session.setAttribute(CALLBACK, callbackHandler);
     * } catch (Exception ex)
     * {
     * log.error("Can't Instanciate a CallBackHandler for UserRepository " + ur.getId(), ex);
     * response.sendError(500, "Authentication System failure!!!");
     * return;
     * }
     * }
     * log.trace("2o callback "+callbackHandler);
     * lc = new LoginContext(context, subject, callbackHandler);
     * lc.login();
     * // Obtener el principal asociado (el principal se modifica en el login)
     * it = subject.getPrincipals().iterator();
     * if (it.hasNext())
     * {
     * user = (User) it.next();
     * log.trace("user checked?:"+user.hashCode()+":"+user.isSigned());
     * }
     * sendLoginLog(request, user);
     * // session.removeAttribute(VALSESS);
     * // session.removeAttribute(CALLBACK);
     * //   System.out.println(subject.toString());
     * //   System.out.println(lc.getSubject().toString());
     * //   System.out.println(((User)lc.getSubject().getPrincipals().iterator().next()).isSigned());
     * } catch (LoginException ex)
     * {
     * log.debug("Can't log User", ex);
     * doResponse(request, response, dparams, "User non existent", authMethod);
     * return;
     * }
     * }
     *
     * String url = request.getParameter("wb_goto");
     * if ((url == null || url.equals("/")))
     * {
     * log.debug("PATHs: Path:" + path + " - " + dparams.getWebPage().getWebSiteId() + " - " + dparams.getWebPage().getId());
     * url =
     * SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
     * }
     *
     * sendRedirect(response, url);
     * //response.getWriter().print("Hello Login, Authenticated User: "+subject.getPrincipals().iterator().next().getName());
     * }
     *
     * @param request the request
     * @param response the response
     * @param distributorParams the distributor params
     * @param alert the alert
     * @param authMethod the auth method
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void doResponse(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String alert, String authMethod) throws IOException
    {
        if ("BASIC".equals(authMethod))
        {
            String realm = (null != distributorParams.getWebPage().getDescription(distributorParams.getUser().getLanguage()) ? distributorParams.getWebPage().getDescription(distributorParams.getUser().getLanguage()) : _realm);
            basicChallenge(realm, response);//TODO Asignar nombre de Realm
        }

        if ("FORM".equals(authMethod))
        {
            formChallenge(request, response, distributorParams, alert);
        }

        if ("OTHER".equals(authMethod))
        {
            redirectAlternateLogin(request, response, distributorParams, alert);
        }

    }

    /**
     * Basic challenge.
     * 
     * @param realm the realm
     * @param response the response
     */
    private void basicChallenge(String realm, HttpServletResponse response)
    {
        StringBuffer header = new StringBuffer();
        header.append("Basic realm=\"");
        header.append(realm);
        header.append("\"");
        response.setHeader("WWW-Authenticate", header.toString());
        response.setHeader("Cache-Control", "no-cache=\"Authorization\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * Form challenge.
     * 
     * @param request the request
     * @param response the response
     * @param distributorParams the distributor params
     * @param alert the alert
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void formChallenge(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String alert) throws IOException
    {
        String ruta = "/config/";
        //TODO: Obtener objetivo del siguiente código
        if (handleError)
        {
            ruta += "403";
        } else
        {
            ruta += "login";
        }

        ruta += ".html";
        String login = null;
        try
        {


            String rutaSite = SWBPortal.getWorkPath() + ruta;

            try
            {
                rutaSite = "/models/" + distributorParams.getWebPage().getWebSite().getId() + ruta;
                login =
                        SWBPortal.readFileFromWorkPath(rutaSite);
                login =
                        SWBPortal.UTIL.parseHTML(login, SWBPortal.getWebWorkPath() + "/models/" + distributorParams.getWebPage().getWebSite().getId() + "/config/images/");
            } catch (Exception ignored)
            {
            }
            if (null == login || "".equals(login))
            {
                login = SWBPortal.readFileFromWorkPath(ruta);
                login =
                        SWBPortal.UTIL.parseHTML(login, SWBPortal.getWebWorkPath() + "/config/images/");
            }

            login = login.replaceFirst("<SWBVERSION>", SWBPlatform.getVersion());
        } catch (Exception e)
        {
            log.error("Error to load login page...", e);
        }

        if (handleError)
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

    /**
     * Send redirect.
     * 
     * @param response the response
     * @param url the url
     */
    public void sendRedirect(HttpServletResponse response, String url)
    {
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

    /**
     * Gets the handler.
     * 
     * @param CBHClassName the cBH class name
     * @param request the request
     * @param response the response
     * @param authType the auth type
     * @param website the website
     * @return the handler
     * @throws ClassNotFoundException the class not found exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static CallbackHandler getHandler(String CBHClassName, HttpServletRequest request, HttpServletResponse response, String authType, String website) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        Constructor[] constructor = Class.forName(CBHClassName).getConstructors();
        int method = 0;
        for (int i = 0; i < constructor.length; i++)
        {
            if (constructor[i].getParameterTypes().length == 4)
            {
                method = i;
            }
        }
        return (CallbackHandler) constructor[method].newInstance(request, response, authType, website);

    }

    /**
     * Redirect alternate login.
     * 
     * @param request the request
     * @param response the response
     * @param distributorParams the distributor params
     * @param alert the alert
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void redirectAlternateLogin(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, String alert) throws IOException
    {
        String altURL = distributorParams.getWebPage().getWebSite().getUserRepository().getAlternateLoginURL();
        if (null == altURL)
        {
            formChallenge(request, response, distributorParams, alert);
        } else
        {
            sendRedirect(response, altURL);
        }
    }

    public static void doLogin(CallbackHandler callbackHandler, String context, Subject subject, HttpServletRequest request, String matchKey) throws LoginException
    {
        if (isblocked(matchKey)){
            throw new LoginException("Login blocked for repeated attempts");
        }
        LoginContext lc;
        User user=null;
        log.trace("Sending calback:" + callbackHandler + " " + context);
        // request.getSession(true).invalidate();
        lc = new LoginContext(context, subject, callbackHandler);
        lc.login();

        Iterator it = subject.getPrincipals().iterator();
        if (it.hasNext())
        {
            user = (User) it.next();
            log.trace("user checked?:" + user.hashCode() + ":" + user.isSigned());
        }
        if(null==user.getLanguage()) user.setLanguage("es"); //forzar lenguage si no se dio de alta.
        cleanBlockedEntry(matchKey);
        sendLoginLog(request, user);
    }

    public static void markFailedAttepmt(String matchKey)
    {
        FailedAttempt failedAttempt = blockedList.get(matchKey);
        if (null==failedAttempt) {
            FailedAttempt fa = new FailedAttempt(matchKey);
            blockedList.put(matchKey, fa);
            failedAttempt = fa;
        }
        failedAttempt.failedAttempt();
    }

    public static boolean isblocked(String matchKey)
    {
        boolean ret=false;
        FailedAttempt current = blockedList.get(matchKey);
        if (null!=current){
            ret = current.isBlocked();
            if (current.isBlocked() && current.getTsBlockedTime()+(1000*60*5)<System.currentTimeMillis()){
                blockedList.remove(matchKey);
                ret=false;
            }
        }
        return ret;
    }

    private static void cleanBlockedEntry(String matchKey)
    {
        FailedAttempt current = blockedList.get(matchKey);
        if (null!=current){
            blockedList.remove(matchKey);
        }
    }
}
