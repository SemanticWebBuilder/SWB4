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
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.security.Principal;
import java.util.Locale;
import java.util.regex.Matcher;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBSoftkHashMap;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBSessionObject;
import org.semanticwb.security.limiter.FailedAttempt;


// TODO: Auto-generated Javadoc
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

    /** The secure. */
    boolean secure = false;

    String paramList = "";

    /** The adm map. */
    String admMap=null;

    boolean admin = true;

    //Constantes para primer implementación

    /** The blocked list. */
    private static SWBSoftkHashMap<String, FailedAttempt> blockedList = new SWBSoftkHashMap<String, FailedAttempt>();

    //private static java.security.KeyPair rsakey = SWBUtils.CryptoWrapper.getRSAKey();

    private static String codigoRSA1 = "\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/jsbn.js\"></script>\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/prng4.js\"></script>\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/rng.js\"></script>\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/rsa.js\"></script>\n"
            + "<script> \n"
            + "var rsa = new RSAKey();\n";
    private static String codigoRSA2 = "function encrypt(){\n"
            + "var res = rsa.encrypt(document.login.wb_password.value);\n"
            + "if (res){ document.login.wb_password.value=res; return true;}\n"
            + "else {return false;}\n}\n"
            + "document.login.onsubmit=encrypt;\n"
            + "</script>";
    

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config)
    {
        log.event("Initializing InternalServlet Login...");
        secure = SWBPlatform.getEnv("swb/secureAdmin", "false").equalsIgnoreCase("true");
        paramList = SWBPlatform.getEnv("swb/ExternalParamList","");
        admin = SWBPlatform.getEnv("swb/administration", "true").equalsIgnoreCase("true");
        admMap=SWBContext.WEBSITE_ADMIN;
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

    public static void sendMailLog(HttpServletRequest request, User usr)
    {
        StringBuffer logbuf =  new StringBuffer();
        if ("es".equals(usr.getLanguage())){
            logbuf.append(usr.getFullName());
            logbuf.append("\nLe informamos que se ha firmado el ");
            logbuf.append(new java.util.Date());
            logbuf.append(" desde la IP:");
            logbuf.append(request.getRemoteAddr());
            logbuf.append(" al sitio:");
            logbuf.append(request.getServerName());
        }else{
            logbuf.append(usr.getFullName());
            logbuf.append("\nWe inform you that you have logged in the ");
            logbuf.append(new java.util.Date());
            logbuf.append(" from IP:");
            logbuf.append(request.getRemoteAddr());
            logbuf.append(" to site:");
            logbuf.append(request.getServerName());
        }
        try {
            SWBUtils.EMAIL.sendBGEmail(usr.getEmail(), request.getServerName(), logbuf.toString());
        } catch (java.net.SocketException sex) {
            log.error(sex);
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
        if (dparams.getWebPage() == null || (!admin && dparams.getWebPage().getWebSite().getId().equals(admMap)))
            {
                response.sendError(404, "La pagina " + request.getRequestURI() + " no existe... ");
                log.debug("Distributor: SendError 404");
                return ;
            }
        if (secure  && admMap.equalsIgnoreCase(dparams.getWebPage().getWebSiteId()) && !request.isSecure()){
            sendRedirect(response, "https://"+request.getServerName()+request.getRequestURI());
        }
        UserRepository ur = dparams.getWebPage().getWebSite().getUserRepository();
        String authMethod = ur.getAuthMethod();
        String context = ur.getLoginContext();
        String CBHClassName = ur.getCallBackHandlerClassName();
        Subject subject = SWBPortal.getUserMgr().getSubject(request, dparams.getWebPage().getWebSiteId());
        String uri = request.getRequestURI();
        String path = SWBPlatform.getContextPath();
        boolean inLoginFase=false;
        User user = null; //System.out.println("Externo: "+ur.getId()+":"+ur.isExternal());
        if (ur.isExternal()) {
            String[]list =paramList.split(",");
            for (String param:list){ //System.out.println("paramlist:"+param);
                if (request.getParameter(param)!=null) inLoginFase=true;
            }
        }
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
                if (null!=user) user.checkCredential("{123}456".toCharArray()); //Invalidate user even in cluster
                lc = new LoginContext(context, subject);
                lc.logout();
                request.getSession(false).invalidate();
                String url = request.getParameter("wb_goto");
                if ((url == null))
                {
                   // url = path + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
                     url = dparams.getWebPage().getUrl(dparams.getUser().getLanguage());
                }
                log.debug("LOGOUT (Path, uri, url): " + path + "   |   " + uri + "    |  " + url);
                sendRedirect(response, url);
                return;
            } catch (Exception elo)
            {
                log.error("LoggingOut " + subject, elo);
            }
        }
        if (null != request.getParameter("user")){
            User pcUser = null;
            try {
                String ids = new String(SWBUtils.CryptoWrapper.PBEAES128Decipher(SWBPlatform.getVersion(),
                    SWBUtils.TEXT.decodeBase64(request.getParameter("user")).getBytes()));
                UserRepository pur = SWBContext.getUserRepository(ids.substring(0,ids.indexOf("|")));
                pcUser=pur.getUserByLogin(ids.substring(ids.indexOf("|")+1));
                String alg = pcUser.getPassword().substring(1,pcUser.getPassword().indexOf("}"));
                if (pcUser.getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(request.getParameter("wb_old_password"), alg)))
                {
                    if (request.getParameter("wb_new_password").equals(request.getParameter("wb_new_password2")))
                    {
                        pcUser.setPassword(request.getParameter("wb_new_password"));
                        pcUser.setRequestChangePassword(false);

                        if(null==pcUser.getLanguage()) pcUser.setLanguage("es"); //forzar lenguage si no se dio de alta.

                        subject.getPrincipals().clear();
                        subject.getPrincipals().add(pcUser);
                        pcUser.setLastLogin(new java.util.Date());
                        pcUser.checkCredential(request.getParameter("wb_new_password").toCharArray());
                        uri = uri.replaceFirst("/login/", "/swb/");
                        sendRedirect(response, uri);
                        return;
                    } else {
                        formChangePwd(request, response, dparams, pcUser, SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", "errdifconf", new Locale(dparams.getUser().getLanguage())));
                        return;
                    }
                }else {
                        formChangePwd(request, response, dparams, pcUser, SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", "errnomatch", new Locale(dparams.getUser().getLanguage())));
                        return;
                    }
            } catch (NullPointerException npe)
            {
                doResponse(request, response, dparams, null, authMethod);
                return;
            }catch (Exception sec){
                formChangePwd(request, response, dparams, pcUser, "Error: "+sec.getMessage());
                return;
            }
        }
        if (uri != null)
        {
            path = uri.replaceFirst(_name, SWBPlatform.getEnv("swb/distributor"));
        }
        if (null != request.getParameter("wb_username") ) inLoginFase=true;
        if (!inLoginFase)
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
                response.sendError(500, "Authentication System failure!!!" +ex.getMessage());
                return;
            }
            try
            {
                String matchKey = dparams.getWebPage().getWebSiteId()+"|"+request.getParameter("wb_username");
                doLogin(callbackHandler, context, subject, request, matchKey, dparams.getWebPage().getWebSite().getUserRepository().getId());
               //MAPS740508 - 18-09-2013 - Checar si recordar con cookie
                if ("true".equals(request.getParameter("wb_rememberuser")))
                {
                    setRememberCookie((User)subject.getPrincipals().iterator().next(), 
                            response, dparams.getWebPage().getWebSite().getUserRepository().getId(),
                            dparams.getWebPage().getWebSite().getUserRepository());
                }
                
            } catch (Exception ex)
            {
                try {
                    User tmpuser = dparams.getWebPage().getWebSite().getUserRepository().getUserByLogin(request.getParameter("wb_username"));

                    if (null!=tmpuser && (tmpuser.isRequestChangePassword() || 
                            SWBPlatform.getSecValues().isForceChage() || SWBPlatform.getSecValues().getExpires()>0))
                    {
                        String alg = tmpuser.getPassword().substring(1,tmpuser.getPassword().indexOf("}"));
                        String tmpPass = request.getParameter("wb_password");
                        if (SWBPlatform.getSecValues().isEncrypt()) tmpPass = SWBUtils.CryptoWrapper.decryptPassword(tmpPass,
                                SWBPortal.getUserMgr().getSessionKey(request));
                        if (tmpuser.getPassword().equals(
                                SWBUtils.CryptoWrapper.comparablePassword(
                                tmpPass, alg)) && tmpuser.isRequestChangePassword())
                        {
                            formChangePwd(request, response, dparams, tmpuser, SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", "errmustupd", new Locale(dparams.getUser().getLanguage())));
                            return;
                        }
                    }
                } catch (Exception ne)
                {
                    log.debug("Problema al intentar cambiar password!", ne); //continuar como nuevo logueo
                }
                markFailedAttepmt(request.getParameter("wb_username"));
                log.debug("Can't log User", ex); 
                String alert = SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", "alert", new Locale(dparams.getUser().getLanguage()));         
                    //    "User non existent";
                if (isblocked(request.getParameter("wb_username"))){
                    alert = SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", "blocked", new Locale(dparams.getUser().getLanguage()));
                   // "User has been temporarily blocked";
                }
                doResponse(request, response, dparams, alert, authMethod);
                return;
            }

        }


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
            url =   /*SWBPlatform.getContextPath() + */ dparams.getWebPage().getUrl(dparams.getUser().getLanguage());
                    //SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
        } 
        if (null!=request.getQueryString() && request.getQueryString().indexOf("wb_logout")==-1) url = url+"?"+request.getQueryString();
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
            if (SWBPlatform.getSecValues().isEncrypt()){
                java.security.KeyPair key = SWBPortal.getUserMgr().getSessionKey(request);
                String codigoRSA = codigoRSA1 + "rsa.setPublic(\""+ ((java.security.interfaces.RSAPublicKey)key.getPublic()).getModulus().toString(16)+"\", \""
            +((java.security.interfaces.RSAPublicKey)key.getPublic()).getPublicExponent().toString(16)+"\");\n"
                        +codigoRSA2;
                login = login.replaceFirst("<SWBSECURITY>", codigoRSA);
            } else {
                login = login.replaceFirst("<SWBSECURITY>", "");
            }
            if (distributorParams.getWebPage().getWebSite().getUserRepository().isUserRepRememberUser())
            {
                String rememberField = handleError?
                    "<br>"+SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", 
                        "remember", new Locale(distributorParams.getUser().getLanguage()))
                        +": <input type=\"checkbox\" name=\"wb_rememberuser\" value=\"true\" />":
                    "<tr>\n<th>\n<h2>"+SWBUtils.TEXT.getLocaleString("org.semanticwb.servlet.internal.Login", 
                        "remember", new Locale(distributorParams.getUser().getLanguage()))
                        +":</h2></th><td><input type=\"checkbox\" name=\"wb_rememberuser\""
                        + " value=\"true\" />\n</td>\n</tr>\n";
                login = login.replaceFirst("<SWBREMEMBER>", rememberField);
            } else 
            {
                login = login.replaceFirst("<SWBREMEMBER>", "");
            }
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
            altURL = SWBUtils.TEXT.replaceAll(altURL, "<website>", distributorParams.getWebPage().getWebSiteId());
            altURL = SWBUtils.TEXT.replaceAll(altURL, "<webpage>", distributorParams.getWebPage().getId());
            sendRedirect(response, altURL);
        }
    }

    /**
     * Do login.
     * 
     * @param callbackHandler the callback handler
     * @param context the context
     * @param subject the subject
     * @param request the request
     * @param matchKey the match key
     * @throws LoginException the login exception
     */
    public static void doLogin(CallbackHandler callbackHandler, String context, Subject subject, HttpServletRequest request, String matchKey, String usserrep) throws LoginException
    {
        log.trace("doLogin of:"+request.getParameter("wb_username")+"|"+request.getParameter("wb_password"));
        if (isblocked(matchKey)){
            throw new LoginException("Login blocked for repeated attempts");
        }
        if (SWBPlatform.getSecValues().isMultiple()){
            String login = request.getParameter("wb_username");
             Iterator<SWBSessionObject> llist =SWBPortal.getUserMgr().listSessionObjects();
             while(llist.hasNext()){
                 SWBSessionObject so = llist.next();
                 Iterator<Principal> lpri = so.getSubjectByUserRep(usserrep).getPrincipals().iterator();
                 if (lpri.hasNext() && login.equalsIgnoreCase(((User)lpri.next()).getLogin()))
                     throw new LoginException("User already logged in");
             }
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
            log.trace("l user checked?:" + user.hashCode() + ":" + user.isSigned());
        }
        if(null==user.getLanguage()) user.setLanguage("es"); //forzar lenguage si no se dio de alta.
        cleanBlockedEntry(matchKey);
        if (SWBPlatform.getSecValues().isSendMail())
        {
            sendMailLog(request, user);
        }
        sendLoginLog(request, user);
    }

    /**
     * Mark failed attepmt.
     * 
     * @param matchKey the match key
     */
    public static void markFailedAttepmt(String matchKey)
    {
        FailedAttempt failedAttempt = blockedList.get(matchKey);
        if (null==failedAttempt) {
            FailedAttempt fa = new FailedAttempt(matchKey);
            blockedList.put(matchKey, fa);
            failedAttempt = fa;
        }
        failedAttempt.failedAttempt(); //System.out.println("******************************:"+failedAttempt.getLogin()+":"+failedAttempt.getCont()+":"+failedAttempt.isBlocked()+":"+failedAttempt.getTsBlockedTime());
    }

    /**
     * Checks if is blocked.
     * 
     * @param matchKey the match key
     * @return true, if is blocked
     */
    public static boolean isblocked(String matchKey)
    {
        boolean ret=false;
        FailedAttempt current = blockedList.get(matchKey);
        if (null!=current){
            ret = current.isBlocked();
            if (current.isBlocked() && (current.getTsBlockedTime()+(1000*60*5))<System.currentTimeMillis()){
                blockedList.remove(matchKey);
                ret=false;
            }
        }
        return ret;
    }

    /**
     * Clean blocked entry.
     * 
     * @param matchKey the match key
     */
    private static void cleanBlockedEntry(String matchKey)
    {
        FailedAttempt current = blockedList.get(matchKey);
        if (null!=current){
            blockedList.remove(matchKey);
        }
    }

    private void formChangePwd(HttpServletRequest request, HttpServletResponse response, DistributorParams distributorParams, User user, String Message) throws IOException
    {
        String ruta = "/config/password.html";
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
            login = login.replaceFirst("<ussid>", Matcher.quoteReplacement("<input type=\"hidden\" name=\"user\" value=\""+
                    SWBUtils.TEXT.encodeBase64(new String(SWBUtils.CryptoWrapper.PBEAES128Cipher(SWBPlatform.getVersion(),
                    (""+user.getUserRepository().getId()+"|"+user.getLogin()).getBytes())))+"\">"));
            login = login.replaceFirst("<val01>", (SWBPlatform.getSecValues().isDifferFromLogin())?
                    Matcher.quoteReplacement("if (form.wb_new_password.value == '"+user.getLogin()+
                    "') { ret=false; alert('Error: password must be diferent from login.');}"):"");
            login = login.replaceFirst("<val02>", (SWBPlatform.getSecValues().getMinlength()>0)?
                    Matcher.quoteReplacement("if (form.wb_new_password.value.length < "+SWBPlatform.getSecValues().getMinlength()+
                    ") { ret=false; alert('Error: password must have at least "+SWBPlatform.getSecValues().getMinlength()+" characters.');}"):"");
            login = login.replaceFirst("<val03>", (SWBPlatform.getSecValues().getComplexity()==1)?
                    Matcher.quoteReplacement("if (!form.wb_new_password.value.match(/^.*(?=.*[a-zA-Z])(?=.*[0-9])().*$/) ) { ret=false; alert('Error: password must have leters and numbers.');}"):"");
            login = login.replaceFirst("<val04>", (SWBPlatform.getSecValues().getComplexity()==2)?
                    Matcher.quoteReplacement("if (!form.wb_new_password.value.match(/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W])().*$/) ) { ret=false; alert('Error: password must have leters, numbres and special symbols.');}"):"");

        } catch (Exception e)
        {
            log.error("Error to load password page...", e);
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
        if (null != Message)
        {
            out.print("<script>alert(\"" + Message + "\");</script>");
        }

        out.flush();
        out.close();
    }

    public void setRememberCookie(final User user, final HttpServletResponse response, final String id, final UserRepository urep) {
        try {
            if (urep.isUserRepRememberUser() && user.isSigned()) {
                String uid = user.getShortURI();
                SemanticProperty sp = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getSemanticProperty(SWBPlatform.getSemanticMgr().SWBAdminURI + "/PrivateKey");
                //System.out.println("sp:"+sp);
                String pass = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getModelObject().getProperty(sp);
                //System.out.println("pass:"+pass);
                if (null!=pass){
                    byte[] buid = SWBUtils.CryptoWrapper.PBEAES128Cipher(pass, uid.getBytes());
                    Cookie cookie = new Cookie("swb."+id, SWBUtils.TEXT.encodeBase64(new String(buid)));
                    cookie.setPath(SWBPortal.getContextPath()+"/");
                    cookie.setMaxAge(60 * 60 * 24 * 365);
                    String name = "Set-Cookie";
                    cookie.setVersion(1);
                    response.setHeader(name, getCookieData(cookie));
                    
                    
                }
            }
        } catch (GeneralSecurityException ex) {
            log.error("Can't create remembering cookie", ex);
        }
    }
    
    private String getCookieData(Cookie cookie){
        StringBuffer buf = new StringBuffer();
        buf.append(cookie.getName());
        buf.append("=");
        buf.append ('"');
        buf.append (cookie.getValue());
        buf.append ('"');
        buf.append ("; Version=1");
        buf.append ("; Max-Age=");
        buf.append (cookie.getMaxAge());
        buf.append ("; Path=");
        //buf.append ('"');
        buf.append (cookie.getPath());
        //buf.append ('"');
        buf.append ("; HttpOnly");
        return buf.toString();
    }

}
