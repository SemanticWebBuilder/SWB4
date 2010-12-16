/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.css.parser.Attribute;
import org.semanticwb.css.parser.CSSParser;
import org.semanticwb.css.parser.Selector;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.opensocial.model.UserPref;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class IFrame
{

    private static final String frame = SocialContainer.loadFrame("demoframe.html");
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
                    if (tag.getTagString().toLowerCase().equals("style"))
                    {
                        if (tag.isEndTag())
                        {
                            ret.append(parseCSS(cssString.toString(), gadget, proxy));
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
    }

    public static String parseCSS(String cssbody, URI gadget, URI proxy)
    {
        StringBuilder sb = new StringBuilder("\r\n");
        try
        {
            CSSParser p = new CSSParser(cssbody);
            for (Selector selector : p.getSelectors())
            {
                sb.append(selector.getName());
                sb.append("{");
                for (Attribute att : selector.getAttributes())
                {

                    sb.append("\r\n");
                    sb.append(att.getName());
                    sb.append(":");
                    if (att.getName().equals("background-image") || att.getName().equals("background") || att.getName().equals("list-style"))
                    {
                        for (String value : att.getValues())
                        {
                            if (value.startsWith("url("))
                            {
                                value = value.substring(4);
                                int pos = value.indexOf(")");
                                if (pos != -1)
                                {
                                    value = value.substring(0, pos).trim();
                                    if (value.startsWith("\"") && value.endsWith("\""))
                                    {
                                        value = value.substring(1, value.length() - 1);
                                    }
                                    if (value.startsWith("'") && value.endsWith("'"))
                                    {
                                        value = value.substring(1, value.length() - 1);
                                    }
                                    URI uriValue = new URI(value);
                                    if (!uriValue.isAbsolute())
                                    {
                                        uriValue = gadget.resolve(uriValue);
                                    }
                                    sb.append("'");
                                    sb.append(proxy.toString());
                                    sb.append("?url=");
                                    sb.append(URLEncoder.encode(uriValue.toString()));
                                    sb.append("'");

                                }
                                else
                                {
                                    sb.append(value);
                                }
                            }
                            else
                            {
                                sb.append(value);
                                sb.append(",");
                            }
                        }
                        sb.append(";");
                    }
                    else
                    {
                        for (String value : att.getValues())
                        {
                            sb.append(value);
                        }
                        if (sb.lastIndexOf(",") != -1)
                        {
                            sb.deleteCharAt(sb.length() - 2);
                        }
                        sb.append(";");
                    }
                }
                sb.append("\r\n}\r\n\r\n");
            }
        }
        catch (Throwable e)
        {
            log.error(e);
        }

        return sb.toString();
    }

    private Map<String, String> getVariablesubstituion(User user, Gadget gadget, String language, String country, String moduleID)
    {
        Map<String, String> getVariablesubstituion = new HashMap<String, String>();
        getVariablesubstituion.put("__MODULE_ID__", moduleID);
        getVariablesubstituion.put("__MSG_LANG__", language);
        getVariablesubstituion.put("__MSG_COUNTRY__", country);
        Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(user);
        while (preferences.hasNext())
        {
            PersonalizedGadged personalizedGadged = preferences.next();
            if (personalizedGadged.getGadget().getURI().equals(gadget.getURI()))
            {
                GenericIterator<UserPref> list = personalizedGadged.listUserPrefses();
                while (list.hasNext())
                {
                    UserPref pref = list.next();
                    getVariablesubstituion.put("__UP_" + pref.getName() + "__", pref.getValue());
                }
            }
        }
        return getVariablesubstituion;
    }

    private String getHTML(URL url)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Charset charset = Charset.defaultCharset();
            String contentType = con.getHeaderField("Content-Type");
            if (contentType != null && contentType.toLowerCase().indexOf("html") != -1)
            {
                int pos = contentType.indexOf("charset");
                if (pos != -1)
                {
                    String scharset = contentType.substring(pos + 1);
                    pos = scharset.indexOf("=");
                    if (pos != -1)
                    {
                        scharset = scharset.substring(pos + 1);
                        try
                        {
                            charset = Charset.forName(scharset);
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                    }
                }
            }
            InputStream in = con.getInputStream();
            byte[] buffer = new byte[1028];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read, charset));
                read = in.read(buffer);
            }
            in.close();
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        return sb.toString();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("IFrame url: " + request.getParameter("url"));
        String url = request.getParameter("url");
        String country = request.getParameter("country");
        String lang = request.getParameter("lang");
        String moduleid = request.getParameter("moduleid");
        String html = "";

        try
        {
            Gadget gadget = SocialContainer.getGadget(url, paramRequest.getWebPage().getWebSite());

            if (gadget != null)
            {
                Map<String, String> variables = getVariablesubstituion(paramRequest.getUser(), gadget, lang, country, moduleid);
                NodeList contents = gadget.getDocument().getElementsByTagName("Content");
                for (int i = 0; i < contents.getLength(); i++)
                {
                    Node node = contents.item(i);
                    if (node instanceof Element)
                    {
                        Element content = (Element) node;
                        if ("html".equals(content.getAttribute("type")))
                        {
                            NodeList childs = content.getChildNodes();
                            for (int j = 0; j < childs.getLength(); j++)
                            {
                                if (childs.item(j) instanceof CDATASection)
                                {
                                    CDATASection section = (CDATASection) childs.item(j);
                                    html = section.getNodeValue();
                                    for (String key : variables.keySet())
                                    {
                                        String value = variables.get(key);
                                        html = html.replace(key, value);
                                    }
                                }
                            }
                        }
                        if ("URL".equals(content.getAttribute("type")))
                        {
                            String href = content.getAttribute("href");
                            URI urihref = new URI(href);
                            URI urigadget = new URI(gadget.getUrl());
                            if (!urihref.isAbsolute())
                            {
                                urigadget.resolve(urihref);
                            }
                            // sends the html result from the href
                            String _url = urihref.toString() + "?";
                            for (String key : variables.keySet())
                            {
                                String value = variables.get(key);
                                _url+="&"+URLEncoder.encode(key)+"="+URLEncoder.encode(value);
                            }
                            html = getHTML(new URL(_url));
                        }
                    }
                }
                /*RequestDispatcher dis = request.getRequestDispatcher(path);
                try
                {
                SWBResourceURL javascript=paramRequest.getRenderUrl();
                javascript.setMode(SocialContainer.Mode_JAVASCRIPT);
                javascript.setCallMethod(SWBResourceURL.Call_DIRECT);
                javascript.setParameter("container", "default");
                javascript.setParameter("nocache", "1");
                javascript.setParameter("debug", "0");
                javascript.setParameter("c", "0");
                //request.setAttribute("jspath", "http://localhost:8080/swb/gadgets/js/core_rpc.js?container=default&amp;nocache=1&amp;debug=0&amp;c=0");
                request.setAttribute("jspath", javascript.toString());//"http://localhost:8080/swb/gadgets/js/core_rpc.js?container=default&amp;nocache=1&amp;debug=0&amp;c=0");
                request.setAttribute("html", html);
                dis.include(request, response);
                }
                catch (Exception e)
                {
                e.printStackTrace();
                }*/

                SWBResourceURL makerequest = paramRequest.getRenderUrl();
                makerequest.setCallMethod(SWBResourceURL.Call_DIRECT);
                makerequest.setMode(SocialContainer.Mode_MAKE_REQUEST);

                SWBResourceURL rpc = paramRequest.getRenderUrl();
                rpc.setCallMethod(SWBResourceURL.Call_DIRECT);
                rpc.setMode(SocialContainer.Mode_RPC);




                SWBResourceURL proxy = paramRequest.getRenderUrl();
                proxy.setCallMethod(SWBResourceURL.Call_DIRECT);
                proxy.setMode(SocialContainer.Mode_PROXY);



                html = parseHTML(html, new URI(gadget.getUrl()), new URI(proxy.toString()));
                SWBResourceURL javascript = paramRequest.getRenderUrl();
                javascript.setMode(SocialContainer.Mode_JAVASCRIPT);
                javascript.setCallMethod(SWBResourceURL.Call_DIRECT);
                javascript.setParameter("script", "core_rpc.js");
                String HtmlResponse = frame.replace("<%=js%>", javascript.toString());
                HtmlResponse = HtmlResponse.replace("<%=rpc%>", rpc.toString());
                HtmlResponse = HtmlResponse.replace("<%=proxy%>", proxy.toString());
                HtmlResponse = HtmlResponse.replace("<%=makerequest%>", makerequest.toString());
                HtmlResponse = HtmlResponse.replace("<%=html%>", html);
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
