/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
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

    private static final String frame = SocialContainer.loadFrame("frame.html");
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
                            if ("src".equals(paramName) && value!=null && value.toLowerCase().startsWith("http"))
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
                if(value.startsWith("'"))
                {
                    value=value.substring(1);
                }
                if(value.endsWith("'"))
                {
                    value=value.substring(0,value.length()-1);
                }
                if(value!=null && value.startsWith("http"))
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
                        value = proxy.toString()+"?url="+URLEncoder.encode(url);
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

    private String getHTMLFromView(String view, Gadget gadget, Map<String, String> variables)
    {
        String html = null;
        for (View oview : gadget.getViews())
        {
            if (view.equals(oview.getName()))
            {
                if (oview.getContent() != null)
                {
                    html = oview.getContent();
                    for (String key : variables.keySet())
                    {
                        String value = variables.get(key);
                        html = html.replace(key, value);
                    }
                }
                else if (oview.getUrlcontent() != null)
                {
                    String _url = oview.getUrlcontent().toString() + "?";
                    for (String key : variables.keySet())
                    {
                        String value = variables.get(key);
                        _url += "&" + URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
                    }
                    html = "<iframe frameborder=\"0\" src=\"" + _url + "\"></iframe>";
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

        HashMap<String,String> userprefs=new HashMap<String, String>();
        Enumeration names=request.getParameterNames();
        while(names.hasMoreElements())
        {
            String name=names.nextElement().toString();
            if(name.startsWith("up_"))
            {
                String value=request.getParameter(name);
                name=name.substring(3);                
                userprefs.put(name, value);
            }            
        }
        boolean changeUserPrefs=false;
        Enumeration headernames=request.getHeaderNames();
        while(headernames.hasMoreElements())
        {
            String name=headernames.nextElement().toString();

            if(name.equalsIgnoreCase("referer"))
            {
                String referer=request.getHeader(name);
                try
                {
                    URL uri_referer=new URL(referer);
                    URL urilocal=new URL(request.getScheme() + "://" + request.getServerName() + port +paramRequest.getWebPage().getUrl());                    

                    if(uri_referer.getHost().equals(urilocal.getHost()) && uri_referer.getPort()==urilocal.getPort() && uri_referer.getProtocol().equals(urilocal.getProtocol()))
                    {
                        changeUserPrefs=true;
                    }
                }
                catch(MalformedURLException e)
                {
                    log.debug(e);
                }
            }
        }

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
                SocialUser socialuser = SocialContainer.getSocialUser(user, request.getSession(),site);
                if(changeUserPrefs)
                {
                    for(String key : userprefs.keySet())
                    {
                        String value=userprefs.get(key);
                        socialuser.saveUserPref(gadget, moduleid, key, value);
                    }
                }

                Map<String, String> variables = socialuser.getVariablesubstituion(gadget, lang, country, moduleid);
                log.debug("variables: "+variables);

                body = getHTMLFromView(sview, gadget, variables);
                if (body == null)
                {
                    sview = "default";
                    body = getHTMLFromView(sview, gadget, variables);
                }

                if (body == null)
                {
                    body = "";
                }
                SWBResourceURL makerequest = paramRequest.getRenderUrl();
                makerequest.setCallMethod(SWBResourceURL.Call_DIRECT);
                makerequest.setMode(SocialContainer.Mode_MAKE_REQUEST);

                SWBResourceURL rpc = paramRequest.getRenderUrl();
                rpc.setCallMethod(SWBResourceURL.Call_DIRECT);
                rpc.setMode(SocialContainer.Mode_RPC);




                SWBResourceURL proxy = paramRequest.getRenderUrl();
                proxy.setCallMethod(SWBResourceURL.Call_DIRECT);
                proxy.setMode(SocialContainer.Mode_PROXY);



                body = prepare(body);




                body = parseHTML(body, new URI(gadget.getUrl()), new URI(proxy.toString()));


                SWBResourceURL javascript = paramRequest.getRenderUrl();
                javascript.setMode(SocialContainer.Mode_JAVASCRIPT);
                javascript.setCallMethod(SWBResourceURL.Call_DIRECT);
                javascript.setParameter("script", "core_rpc.js");

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


                String fileName = SWBUtils.getApplicationPath() + "swbadmin/jsp/opensocial/frame.html";
                File file = new File(fileName);

                FileInputStream in = new FileInputStream(file);
                StringBuilder sb = new StringBuilder();
                byte[] buffer = new byte[1028];
                int read = in.read(buffer);
                while (read != -1)
                {
                    String data = new String(buffer, 0, read);
                    read = in.read(buffer);
                    sb.append(data);
                }

                String _frame = sb.toString();
                String HtmlResponse = _frame.replace("<%=msg%>", msg.toString());
                HtmlResponse = HtmlResponse.replace("<%=context%>", SWBPortal.getContextPath());
                HtmlResponse = HtmlResponse.replace("<%=default_values%>", j_default_values.toString());
                HtmlResponse = HtmlResponse.replace("<%=js%>", javascript.toString());
                HtmlResponse = HtmlResponse.replace("<%=rpc%>", rpc.toString());
                HtmlResponse = HtmlResponse.replace("<%=proxy%>", proxy.toString());
                HtmlResponse = HtmlResponse.replace("<%=makerequest%>", makerequest.toString());
                HtmlResponse = HtmlResponse.replace("<%=html%>", body);                                
                PrintWriter out = response.getWriter();
                out.write(HtmlResponse);
                out.close();
            }
        }
        catch (Exception e)
        {
            log.debug(e);            
            response.setStatus(500, e.getMessage());
        }
    }
}
