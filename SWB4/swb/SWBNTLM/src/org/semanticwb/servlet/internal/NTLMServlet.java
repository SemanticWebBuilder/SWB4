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
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBSoftkHashMap;
import org.semanticwb.model.UserRepository;
import waffle.servlet.spi.NegotiateSecurityFilterProvider;
import waffle.util.AuthorizationHeader;
import waffle.util.Base64;
import waffle.util.NtlmServletRequest;
import waffle.windows.auth.IWindowsIdentity;
import waffle.windows.auth.IWindowsSecurityContext;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

/**
 *
 * @author serch
 */
public class NTLMServlet implements InternalServlet
{

    private static Logger log = SWBUtils.getLogger(NTLMServlet.class);
    public final static WindowsAuthProviderImpl iwp = new WindowsAuthProviderImpl();
    public final static NegotiateSecurityFilterProvider nsp = new NegotiateSecurityFilterProvider(iwp);
    private static String securityPackage = "Negotiate";
    public final static SWBSoftkHashMap<String, IWindowsSecurityContext> secContx =
            new SWBSoftkHashMap<String, IWindowsSecurityContext>(10);
    private String _name = "NTLMLogin";

    public void init(ServletContext config) throws ServletException
    {
        //System.out.println("NTLMServlet: init");
        nsp.getProtocols().add("Basic realm=\"SemanticWebBuilder\"");
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        //System.out.println("NTLMServlet: doProcess");
        AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);
        UserRepository ur = dparams.getWebPage().getWebSite().getUserRepository();
        String authMethod = ur.getAuthMethod();
        String context = ur.getLoginContext();
        String CBHClassName = ur.getCallBackHandlerClassName();
        Subject subject = SWBPortal.getUserMgr().getSubject(request, dparams.getWebPage().getWebSiteId());
        String path = SWBPlatform.getContextPath();
        String uri = request.getRequestURI();
        String url = request.getParameter("wb_goto");
        if (uri != null)
        {
            path = uri.replaceFirst(_name, SWBPlatform.getEnv("swb/distributor"));
        }
        if ((url == null || url.equals("/")))
        {
            log.debug("PATHs: Path:" + path + " - " + dparams.getWebPage().getWebSiteId() + " - " + dparams.getWebPage().getId());
            url =
                    SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor") + "/" + dparams.getWebPage().getWebSiteId() + "/" + dparams.getWebPage().getId() + "/_lang/" + dparams.getUser().getLanguage();
        }
        if (null != request.getQueryString())
        {
            url = url + "?" + request.getQueryString();
        }
        //System.out.println("authorizationHeader:"+authorizationHeader.getHeader());
        if (!authorizationHeader.isNull())
        {   //System.out.println("Not Null authorizationHeader");
            boolean ntlmPost = authorizationHeader.isNtlmType1PostAuthorizationHeader();
            String connectionId = NtlmServletRequest.getConnectionId(request);
            //System.out.println("ntlmPost:"+ntlmPost+" - "+connectionId);

            if (ntlmPost)
            {
                iwp.resetSecurityToken(connectionId);
            }

            byte[] tokenBuffer = authorizationHeader.getTokenBytes();
            //System.out.println("tokenbuffer:"+new String(tokenBuffer));
                IWindowsSecurityContext securityContext = null;
                try {
                    securityContext=iwp.acceptSecurityToken(connectionId, tokenBuffer, securityPackage);
            } catch (Exception e){
                //e.printStackTrace(System.out);
                iwp.resetSecurityToken(connectionId);
                  //  System.out.println("redireccionando a "+url);
                    //response.setHeader("Connection", "close");
                    sendUnauthorized(response, true);
                    //System.out.println("Redireccionado...");
                return;
            }
                //System.out.println("sc: "+securityContext);
                byte[] continueTokenBytes = securityContext.getToken();
                
                if (continueTokenBytes != null)
                {//System.out.println("CTB: "+new String(continueTokenBytes));
                    //System.out.println("Adding Header:");
                    String continueToken = new String(Base64.encode(continueTokenBytes));
                    response.addHeader("WWW-Authenticate", securityPackage + " " + continueToken);
                }

                if ((securityContext.getContinue() || ntlmPost))
                {
                    //System.out.println("Adding KeepAlive:");
                    response.setHeader("Connection", "keep-alive");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.flushBuffer();
                    return;
                } else
                {
                    //System.out.println("Processing");
                    secContx.put(connectionId, securityContext);
                    //System.out.println("login:" + securityContext.getIdentity().getFqn());
                    CallbackHandler callbackHandler = null;
                    try
                    {
                        callbackHandler = Login.getHandler(CBHClassName, request, response, authMethod, dparams.getWebPage().getWebSiteId());
                    } catch (Exception ex)
                    {
                        log.error("Can't Instanciate a CallBackHandler for UserRepository " + ur.getId() + "\n" + CBHClassName, ex);
                        response.sendError(500, "Authentication System failure!!!");
                        return;
                    }
                    try
                    {
                        String matchKey = dparams.getWebPage().getWebSiteId() + "|" + request.getParameter("wb_username");
                        Login.doLogin(callbackHandler, context, subject, request, matchKey, dparams.getWebPage().getWebSite().getUserRepository().getId());

                        sendRedirect(response, url);
                    } catch (Exception ex)
                    {
                    }

                }

                final IWindowsIdentity identity = securityContext.getIdentity();
                securityContext.dispose();
                return;
            
        }
        sendUnauthorized(response, false);

    }

    private void sendUnauthorized(HttpServletResponse response, boolean close)
    {
        try
        {
            nsp.sendUnauthorized(response);
            if (close)
            {
                response.setHeader("Connection", "close");
            } else
            {
                response.setHeader("Connection", "keep-alive");
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.flushBuffer();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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
}
