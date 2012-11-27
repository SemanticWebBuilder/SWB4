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
package org.semanticwb.opensocial.resources;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.View;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author victor.lorenzana
 */
public class IFrame
{

    private static final Logger log = SWBUtils.getLogger(IFrame.class);

    public static String parseHTML(String datos, URI gadget, URI proxy)
    {
        StringBuilder ret = new StringBuilder();
        StringBuilder cssString = new StringBuilder();
        HtmlTag tag = new HtmlTag();
        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
        boolean css = false;
        try
        {
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                {
                    if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->"))
                    {
                        continue;
                    }
                    tok.parseTag(tok.getStringValue(), tag);

                    if (!tag.isEndTag() && (tag.getTagString().equalsIgnoreCase("script") || tag.getTagString().equalsIgnoreCase("img")))
                    {
                        ret.append("<");
                        ret.append(tag.getTagString());
                        ret.append(" ");
                        for (int iparam = 0; iparam < tag.getParamCount(); iparam++)
                        {
                            String paramName = tag.getParamName(iparam);
                            String value = tag.getParamValue(iparam);
                            if ("src".equals(paramName) && value != null)
                            {
                                if (value.toLowerCase().startsWith("http") || value.toLowerCase().startsWith("/") || value.toLowerCase().startsWith("../") || value.toLowerCase().startsWith("./"))
                                {
                                    try
                                    {
                                        URI uriSRC = new URI(value);
                                        if (!uriSRC.isAbsolute())
                                        {
                                            uriSRC = gadget.resolve(uriSRC);
                                        }
                                        String url = uriSRC.toString();
                                        int pos = url.indexOf("?"); // elimina parametros por seguridad
                                        if (pos != -1)
                                        {
                                            url = url.substring(0, pos);
                                        }
                                        value = proxy + "?url=" + URLEncoder.encode(url);
                                    }
                                    catch (URISyntaxException use)
                                    {
                                        log.debug(use);
                                    }
                                }
                                else
                                {
                                    ret.append(paramName);
                                    ret.append("=\"");
                                    ret.append(value);
                                    ret.append("\" ");
                                }

                            }
                            ret.append(paramName);
                            ret.append("=\"");
                            ret.append(value);
                            ret.append("\" ");
                        }
                        ret.append(">");

                    }
                    else if (tag.getTagString().toLowerCase().equals("style"))
                    {
                        if (tag.isEndTag())
                        {

                            ret.append(parseCSS(cssString.toString(), gadget, proxy));
                            //ret.append(cssString.toString());
                            cssString = new StringBuilder();
                            css = false;
                            ret.append(tok.getRawString());
                        }
                        else
                        {
                            css = true;
                            ret.append(tok.getRawString());
                        }
                    }
                    else
                    {
                        ret.append(tok.getRawString());

                    }

                }
                else
                {
                    if ((ttype == HtmlStreamTokenizer.TT_TEXT || ttype == HtmlStreamTokenizer.TT_COMMENT) && css)
                    {
                        cssString.append(tok.getRawString());
                        cssString.append("\r\n");

                    }
                    else
                    {
                        ret.append(tok.getRawString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return ret.toString();
        //return datos;
    }

    public static String parseCSS(String cssbody, URI gadget, URI proxy)
    {
        StringBuilder sb = new StringBuilder();
        int pos = cssbody.indexOf("url(");
        while (pos != -1)
        {
            sb.append(cssbody.substring(0, pos + 4));
            cssbody = cssbody.substring(pos + 4);
            pos = cssbody.indexOf(")");
            if (pos != -1)
            {
                String value = cssbody.substring(0, pos);
                if (value.startsWith("'"))
                {
                    value = value.substring(1);
                }
                if (value.endsWith("'"))
                {
                    value = value.substring(0, value.length() - 1);
                }
                if (value != null && value.startsWith("http"))
                {
                    try
                    {
                        URI uriValue = new URI(value);
                        if (!uriValue.isAbsolute())
                        {
                            uriValue = gadget.resolve(uriValue);
                        }
                        String url = uriValue.toString();
                        int pos2 = url.indexOf("?"); // elimina parametros por seguridad
                        if (pos2 != -1)
                        {
                            url = url.substring(0, pos2);
                        }
                        value = proxy.toString() + "?url=" + URLEncoder.encode(url);
                    }
                    catch (URISyntaxException e)
                    {
                        log.debug(e);
                    }
                }
                sb.append("'");
                sb.append(value);
                sb.append("'");
                cssbody = cssbody.substring(pos);
            }
            pos = cssbody.indexOf("url(");
        }
        return sb.toString();
    }

    private String getHTMLFromView(String view, Gadget gadget, SocialUser socialuser, String lang, String country, String moduleid)
    {
        String html = null;
        for (View oview : gadget.getViews())
        {
            if (view.equals(oview.getName()))
            {
                if (oview.getContent() != null)
                {
                    Map<String, String> variables = socialuser.getVariablesubstituion(gadget, lang, country, moduleid);
                    html = oview.getContent();
                    for (String key : variables.keySet())
                    {
                        String value = variables.get(key);
                        html = html.replace(key, value);
                    }
                }
                else if (oview.getUrlcontent() != null)
                {
                    Map<String, String> variables = socialuser.getVariablesubstituion(gadget, lang, country, moduleid, false);
                    String _url = oview.getUrlcontent().toString() + "?";
                    for (String key : variables.keySet())
                    {
                        String value = variables.get(key);
                        _url += "&" + URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
                    }
                    //_url+="&parent=&libs=ebbdPN3h-1s/lib/libcore.js";
                    html = "<iframe style=\"width:100%;height:100%\" frameborder=\"0\" src=\"" + _url + "\"></iframe>";
                }
            }
        }
        return html;
    }

    private String prepareScript(String script)
    {
        StringBuilder sb = new StringBuilder();
        int pos = script.indexOf(">");
        if (pos != -1)
        {
            String bodyScript = script.substring(pos + 1);
            sb.append(script.substring(0, pos + 1));
            pos = bodyScript.toLowerCase().indexOf("</script>");
            if (pos != -1)
            {
                bodyScript = bodyScript.substring(0, pos);
                pos = bodyScript.indexOf("<!--");
                if (pos == -1 && !"".equals(bodyScript.trim()))
                {
                    sb.append("\r\n<!-- \r\n ");
                    sb.append(bodyScript);
                    sb.append("\r\n -->\r\n");
                    sb.append("</script>");
                }
                else
                {
                    return script;
                }
            }
            else
            {
                return script;
            }
        }
        return sb.toString();
    }

    private String prepare(String body)
    {
        StringBuilder sb = new StringBuilder();
        int pos = body.toLowerCase().indexOf("<script");
        while (pos != -1)
        {
            String script = body.substring(pos);
            sb.append(body.substring(0, pos));
            body = body.substring(pos);
            pos = body.toLowerCase().indexOf("</script>");
            if (pos != -1)
            {
                script = script.substring(0, pos + 9);
                body = body.substring(pos + 9);
                script = prepareScript(script);
                sb.append(script);
            }
            pos = body.toLowerCase().indexOf("<script>");
        }
        sb.append(body);
        return sb.toString();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String port = "";
        if (request.getServerPort() != 80)
        {
            port = ":" + request.getServerPort();
        }

        HashMap<String, String> userprefs = new HashMap<String, String>();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements())
        {
            String name = names.nextElement().toString();
            if (name.startsWith("up_"))
            {
                String value = request.getParameter(name);
                name = name.substring(3);
                userprefs.put(name, value);

            }
        }
        boolean changeUserPrefs = false;


        changeUserPrefs = true;
        /*String referer = request.getHeader("referer");
        if (referer != null)
        {
            try
            {
                URL uri_referer = new URL(referer);
                URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port + paramRequest.getWebPage().getUrl());
                String host1 = uri_referer.getHost();
                if ("localhost".equals(host1))
                {
                    host1 = "127.0.0.1";
                }
                String host2 = urilocal.getHost();
                if ("localhost".equals(host2))
                {
                    host2 = "127.0.0.1";
                }
                if (host1.equals(host2) && uri_referer.getPort() == urilocal.getPort() && uri_referer.getProtocol().equals(urilocal.getProtocol()))
                {
                    changeUserPrefs = true;
                }
            }
            catch (MalformedURLException e)
            {
                log.debug(e);
            }
        }*/

        WebSite site = paramRequest.getWebPage().getWebSite();
        User user = paramRequest.getUser();

        log.debug("Iframe request.getQueryString(): " + request.getQueryString());
        String url = request.getParameter("url");
        String country = request.getParameter("country");
        String lang = request.getParameter("lang");
        String moduleid = request.getParameter("moduleid");
        String sview = request.getParameter("view");
        if (sview == null)
        {
            sview = "default";
        }
        String body = "";
        if (moduleid == null)
        {
            moduleid = "0";
        }


        try
        {
            Gadget gadget = SocialContainer.getGadget(url, paramRequest.getWebPage().getWebSite());
            if (gadget != null)
            {
                SocialUser socialuser = SocialContainer.getSocialUser(user, request.getSession(), site);
                if (changeUserPrefs)
                {
                    for (String key : userprefs.keySet())
                    {
                        String value = userprefs.get(key);
                        socialuser.saveUserPref(gadget, moduleid, key, value);
                    }
                }


                body = getHTMLFromView(sview, gadget, socialuser, lang, country, moduleid);
                if (body == null)
                {
                    sview = "default";
                    body = getHTMLFromView(sview, gadget, socialuser, lang, country, moduleid);
                }

                if (body == null)
                {
                    body = "";
                }




                SWBResourceURL proxy = paramRequest.getRenderUrl();
                proxy.setCallMethod(SWBResourceURL.Call_DIRECT);
                proxy.setMode(SocialContainer.Mode_PROXY);


                body = prepare(body);
                body = parseHTML(body, new URI(gadget.getUrl()), new URI(proxy.toString()));




                JSONObject j_default_values = new JSONObject();
                Map<String, String> default_values = gadget.getDefaultUserPref(lang, country, false);
                for (String key : default_values.keySet())
                {
                    j_default_values.put(key, default_values.get(key));
                }

                JSONObject msg = new JSONObject();
                Map<String, String> messages = gadget.getMessagesFromGadget(lang, country, false);
                for (String key : messages.keySet())
                {
                    msg.put(key, messages.get(key));
                }

                String path = "/work/" + paramRequest.getResourceBase().getWorkPath() + "/frame.jsp";
                RequestDispatcher dis = request.getRequestDispatcher(path);

                for (String feature : gadget.getAllFeatures())
                {
                    if ("views".equals(feature))
                    {
                        JSONObject appParams = new JSONObject();
                        String _appParams = request.getParameter("appParams");
                        if (_appParams != null)
                        {
                            _appParams = URLDecoder.decode(_appParams);
                            StringTokenizer st = new StringTokenizer(_appParams, "&");
                            while (st.hasMoreTokens())
                            {
                                String token = st.nextToken();
                                StringTokenizer st2 = new StringTokenizer(token, "=");
                                if (st2.countTokens() == 2)
                                {
                                    String key = st2.nextToken();
                                    String value = st2.nextToken();
                                    appParams.accumulate(key, value);
                                }
                            }
                        }
                        request.setAttribute("appParams", appParams.toString());
                    }
                }

                try
                {
                    request.setAttribute("moduleid", moduleid);
                    request.setAttribute("paramRequest", paramRequest);
                    request.setAttribute("msg", msg.toString());
                    request.setAttribute("default_values", j_default_values.toString());
                    request.setAttribute("html", body);
                    request.setAttribute("view", sview);
                    dis.include(request, response);
                }
                catch (Exception e)
                {
                    log.error(e);
                }

                /*String HtmlResponse = iframeContent.replace("<%=msg%>", msg.toString());
                HtmlResponse = HtmlResponse.replace("<%=context%>", SWBPortal.getContextPath());
                HtmlResponse = HtmlResponse.replace("<%=default_values%>", j_default_values.toString());
                HtmlResponse = HtmlResponse.replace("<%=js%>", javascript.toString());
                HtmlResponse = HtmlResponse.replace("<%=rpc%>", rpc.toString());
                HtmlResponse = HtmlResponse.replace("<%=proxy%>", proxy.toString());
                HtmlResponse = HtmlResponse.replace("<%=makerequest%>", makerequest.toString());
                HtmlResponse = HtmlResponse.replace("<%=html%>", body);
                PrintWriter out = response.getWriter();
                out.write(HtmlResponse);
                out.close();*/
            }

        }
        catch (Exception e)
        {
            log.debug(e);
            response.setStatus(500, e.getMessage());
        }
    }
}
