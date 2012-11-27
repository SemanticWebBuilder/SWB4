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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author victor.lorenzana
 */
public class MakeRequest
{

    private static final Logger log = SWBUtils.getLogger(MakeRequest.class);
    private static final String rsschema = SocialContainer.loadSchema("rss.xml");
    private static final String atomchema = SocialContainer.loadSchema("atom.txt");

    private static boolean isValidRss(Document doc)
    {
        return true;
        /*SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        StringReader reader = new StringReader(rsschema);
        try
        {
            Source schemaFile = new StreamSource(reader);
            try
            {
                Schema schema = factory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                DOMSource source = new DOMSource(doc);
                validator.validate(source);
                return true;
            }
            catch (IOException ioe)
            {
                log.debug(ioe);
                return false;
            }
            catch (SAXException saxe)
            {
                log.debug(saxe);
                return false;
            }
        }
        catch (Exception e)
        {
            log.debug(e);
            return false;
        }*/
    }

    private static boolean isValidAtom(Document doc)
    {
        
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI);
        StringReader reader = new StringReader(atomchema);
        try
        {
            Source schemaFile = new StreamSource(reader);
            try
            {
                Schema schema = factory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                DOMSource source = new DOMSource(doc);
                validator.validate(source);
                return true;
            }
            catch (IOException ioe)
            {
                log.debug(ioe);
                return false;
            }
            catch (SAXException saxe)
            {
                log.debug(saxe);
                return false;
            }
        }
        catch (Exception e)
        {
            log.debug(e);
            return false;
        }
    }

    private void sendResponse(String objresponse, HttpServletResponse response) throws IOException
    {
        Charset utf8 = Charset.forName("utf-8");
        response.setContentType("application/json;charset=" + utf8.name());
        OutputStream out = response.getOutputStream();
        out.write("throw 1; < don't be evil' >".getBytes());
        out.write(objresponse.getBytes(utf8));
        out.close();
    }

    private void getJSON(URL url, String headers, HttpServletResponse response) throws JSONException, IOException
    {

        Charset charset = Charset.forName("utf-8");
        int code = 500;
        String xml = "";
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200)
            {
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        charset = Charset.forName(scharset);
                    }
                }
                StringBuilder sb = new StringBuilder();
                InputStream in = con.getInputStream();
                InputStreamReader reader = new InputStreamReader(in, charset);
                char[] buffer = new char[1024 * 8];
                int read = reader.read(buffer);
                while (read != -1)
                {
                    sb.append(new String(buffer, 0, read));
                    read = reader.read(buffer);
                }
                try
                {
                    JSONObject obj = new JSONObject(sb.toString());
                    xml = obj.toString();
                }
                catch (JSONException e)
                {
                    code = 500;
                }
            }
        }
        catch (IOException ioe)
        {
            code = 500;
        }
        JSONObject responseJSONObject = new JSONObject();
        response.setContentType("application/json");
        JSONObject body = new JSONObject();
        try
        {
            body.put("body", xml);
            responseJSONObject.put(url.toString(), body);
            responseJSONObject.put("rc", code);
            sendResponse(responseJSONObject.toString(4), response);
        }
        catch (JSONException e)
        {
            log.debug(e);
            throw e;
        }
        catch (IOException e)
        {
            log.debug(e);
            throw e;
        }



    }

    private void getText(URL url, String headers, HttpServletResponse response) throws JSONException, IOException
    {

        Charset charset = Charset.forName("utf-8");
        int code = 500;
        String xml = "";
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200)
            {
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        charset = Charset.forName(scharset);
                    }
                }
                StringBuilder sb = new StringBuilder();
                InputStream in = con.getInputStream();
                InputStreamReader reader = new InputStreamReader(in, charset);
                char[] buffer = new char[1024 * 8];
                int read = reader.read(buffer);
                while (read != -1)
                {
                    sb.append(new String(buffer, 0, read));
                    read = reader.read(buffer);
                }
                xml = sb.toString();

            }
        }
        catch (IOException ioe)
        {
            code = 500;
        }
        JSONObject responseJSONObject = new JSONObject();
        response.setContentType("application/json");
        JSONObject body = new JSONObject();
        try
        {
            body.put("body", xml);
            responseJSONObject.put(url.toString(), body);
            responseJSONObject.put("rc", code);
            sendResponse(responseJSONObject.toString(4), response);
        }
        catch (JSONException e)
        {
            log.debug(e);
            throw e;
        }
        catch (IOException e)
        {
            log.debug(e);
            throw e;
        }
    }

    private static void addElement(Element e, JSONObject response) throws JSONException
    {
        String name = e.getTagName();
        NodeList childs = e.getChildNodes();
        boolean hasChilds = false;
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element)
            {
                hasChilds = true;
            }
        }
        if (hasChilds)
        {
            
            childs = e.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element child = (Element) childs.item(i);
                    JSONObject jchild = new JSONObject();
                    addElement(child, jchild);
                    response.accumulate(name, jchild);
                }
            }
        }
        else
        {
            String content = e.getTextContent();
            if (content != null)
            {
                response.accumulate(name, content);
            }
        }
    }

    private static JSONObject transformFeedToJSon(Document doc, String name) throws JSONException
    {
        JSONObject response = new JSONObject();
        NodeList channels = doc.getElementsByTagName(name);
        for (int i = 0; i < channels.getLength(); i++)
        {
            if (channels.item(i) instanceof Element)
            {
                Element channel = (Element) channels.item(i);
                addElement(channel, response);
            }
        }

        return response;
    }

    private static JSONObject getFeed(URL url, String headers) throws IOException, JDOMException, JSONException
    {

        int code = 500;
        String xml = "";
        try
        {
            Charset charset = Charset.forName("utf-8");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200)
            {
                String contentType = con.getContentType();
                String rootname = null;
                if (contentType!=null && (contentType.startsWith("application/rss+xml")))
                {
                    rootname = "channel";
                }
                else if(contentType != null && contentType.startsWith("application/atom+xml"))
                {
                    rootname = "feed";
                }
                else if(contentType != null && contentType.equals("application/xml"))
                {
                    rootname = "check";
                }
                if (rootname != null)
                {
                    if (contentType != null)
                    {
                        int pos = contentType.indexOf("charset=");
                        if (pos != -1)
                        {
                            String scharset = contentType.substring(pos + 8).trim();
                            charset = Charset.forName(scharset);
                        }
                    }
                    InputStream in = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in, charset);
                    DOMOutputter out = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document doc = out.output(builder.build(reader));
                    if ("check".equals(rootname))
                    {
                        if(doc.getElementsByTagName("feed").getLength()>0)
                        {
                            rootname="feed";
                        }
                        else if(doc.getElementsByTagName("channel").getLength() > 0)
                        {
                            rootname="channel";
                        }
                    }
                    if ("channel".equals(rootname))
                    {
                        if (isValidRss(doc))
                        {
                            xml = transformFeedToJSon(doc, rootname).toString();
                            code = 200;
                        }
                        else
                        {
                            code = 500;
                        }
                    }
                    else if("feed".equals(rootname))
                    {
                        if (isValidAtom(doc))
                        {
                            xml = transformFeedToJSon(doc, rootname).toString();
                            code = 200;
                        }
                        else
                        {
                            code = 500;
                        }
                    }

                }
                else
                {
                    code = 500;
                }

            }
            else
            {
                code = con.getResponseCode();
            }



            JSONObject responseJSONObject = new JSONObject();
            JSONObject body = new JSONObject();
            body.put("body", xml);
            responseJSONObject.put(url.toString(), body);
            responseJSONObject.put("rc", code);
            return responseJSONObject;
        }
        catch (IOException e)
        {
            log.debug(e);
            throw e;
        }
        catch (JSONException e)
        {
            log.debug(e);
            throw e;
        }
    }

    private void getFeed(URL url, String headers, HttpServletResponse response) throws IOException, JDOMException, JSONException
    {
        JSONObject responseJSONObject = getFeed(url, headers);
        response.setContentType("application/json");
        sendResponse(responseJSONObject.toString(4), response);

    }
    

    public static void main(String[] args)
    {
        try
        {
            JSONObject resp = MakeRequest.getFeed(new URL("http://www.tea-tron.com/rubenramos/blog/feed/"),"");
            System.out.println("resp");
            System.out.println(resp.toString(6));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getDocument(URL url, String headers, HttpServletResponse response) throws IOException, JDOMException, JSONException
    {
        try
        {
            int code = 500;
            String xml = "";
            try
            {
                Document doc = SocialContainer.getXML(url);
                Charset charset = Charset.defaultCharset();
                xml = SWBUtils.XML.domToXml(doc, charset.name(), true);
                code = 200;
            }
            catch (RequestException e)
            {
                code = e.getCode();
            }

            JSONObject responseJSONObject = new JSONObject();
            response.setContentType("application/json");
            JSONObject body = new JSONObject();
            body.put("body", xml);
            responseJSONObject.put(url.toString(), body);
            responseJSONObject.put("rc", code);
            sendResponse(responseJSONObject.toString(4), response);
        }
        catch (IOException e)
        {
            log.debug(e);
            throw e;
        }
        /*catch (JDOMException e)
        {
        log.debug(e);
        throw e;
        }*/
        catch (JSONException e)
        {
            log.debug(e);
            throw e;
        }

    }

    private void sendResponse(Document objresponse, HttpServletResponse response) throws IOException
    {
        Charset defaultCharset = Charset.defaultCharset();
        response.setContentType("text/xml;charset=" + defaultCharset.name());
        OutputStream out = response.getOutputStream();
        String xml = SWBUtils.XML.domToXml(objresponse, defaultCharset.name(), false);
        out.write(xml.getBytes());
        out.close();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        log.debug("MakeRequest request.getQueryString(): " + request.getQueryString());
        log.debug("MakeRequest request.getContentType(): " + request.getContentType());
        String refresh = request.getParameter("refresh");
        log.debug("refresh: " + refresh);
        String url = request.getParameter("url");
        log.debug("url: " + url);
        String httpMethod = request.getParameter("httpMethod");
        log.debug("httpMethod: " + httpMethod);
        String headers = request.getParameter("headers");
        log.debug("headers: " + headers);
        String postData = request.getParameter("postData");
        log.debug("postData: " + postData);
        String authz = request.getParameter("authz");
        log.debug("authz: " + authz);
        String contentType = request.getParameter("contentType");
        log.debug("contentType: " + contentType);
        String numEntries = request.getParameter("numEntries");
        log.debug("numEntries: " + numEntries);
        String getSummaries = request.getParameter("getSummaries");
        log.debug("getSummaries: " + getSummaries);
        String signOwner = request.getParameter("signOwner");
        log.debug("signOwner: " + signOwner);
        String container = request.getParameter("container");
        log.debug("container: " + container);
        String bypassSpecCache = request.getParameter("bypassSpecCache");
        log.debug("bypassSpecCache: " + bypassSpecCache);
        String getFullHeaders = request.getParameter("getFullHeaders");
        log.debug("getFullHeaders: " + getFullHeaders);
        String gadget = request.getParameter("gadget");
        log.debug(gadget);
        Gadget g = SocialContainer.getGadget(gadget, site);


        /* para cumplir con el siguiente código
         * switch (params.CONTENT_TYPE) {
        case "JSON":
        case "FEED":
        resp.data = gadgets.json.parse(resp.text);
        if (!resp.data) {
        resp.errors.push("500 Failed to parse JSON");
        resp.rc = 500;
        resp.data = null;
        }
        break;
        case "DOM":
        var dom;
        if (typeof ActiveXObject != 'undefined') {
        dom = new ActiveXObject("Microsoft.XMLDOM");
        dom.async = false;
        dom.validateOnParse = false;
        dom.resolveExternals = false;
        if (!dom.loadXML(resp.text)) {
        resp.errors.push("500 Failed to parse XML");
        resp.rc = 500;
        } else {
        resp.data = dom;
        }
        } else {
        var parser = new DOMParser();
        dom = parser.parseFromString(resp.text, "text/xml");
        if ("parsererror" === dom.documentElement.nodeName) {
        resp.errors.push("500 Failed to parse XML");
        resp.rc = 500;
        } else {
        resp.data = dom;
        }
        }
        break;
        default:
        resp.data = resp.text;
        break;
        }
         */
        if (g != null)
        {
            if (httpMethod.trim().equalsIgnoreCase("GET"))
            {
                if ("DOM".equals(contentType.trim()))
                {
                    try
                    {
                        getDocument(new URL(url), headers, response);

                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                        return;
                    }
                }
                else if ("FEED".equals(contentType.trim()))
                {
                    try
                    {
                        getFeed(new URL(url), headers, response);
                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                        return;
                    }
                }
                else if ("JSON".equals(contentType.trim()))
                {
                    try
                    {
                        getJSON(new URL(url), headers, response);
                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                        return;
                    }
                }
                else
                {
                    try
                    {
                        getText(new URL(url), headers, response);
                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                        return;
                    }
                }

            }
            else
            {
                response.sendError(404);
            }
        }
        response.sendError(404);
    }
}
