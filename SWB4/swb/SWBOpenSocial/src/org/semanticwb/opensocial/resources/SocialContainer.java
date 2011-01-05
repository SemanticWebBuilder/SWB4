/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.RequestDispatcher;
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
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.opensocial.model.UserPref;
import org.semanticwb.portal.api.GenericResource;
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
public class SocialContainer extends GenericResource
{

    private static final String gadgetschema = SocialContainer.loadSchema("gadget.xml");
    private static final Logger log = SWBUtils.getLogger(SocialContainer.class);
    public static final String Mode_METADATA = "METADATA";
    public static final String Mode_IFRAME = "IFRAME";
    public static final String Mode_JAVASCRIPT = "JAVASCRIPT";
    public static final String Mode_PROXY = "PROXY";
    public static final String Mode_MAKE_REQUEST = "MAKEREQUEST";
    public static final String Mode_CONFIGGADGET = "CONFIGGADGET";
    public static final String Mode_LISTGADGETS = "LISTGADGETS";
    public static final String Mode_RPC = "RPC";

    static
    {
        WebSite site = WebSite.ClassMgr.getWebSite("reg_digital_demo");
        Iterator<Gadget> gadgets = Gadget.ClassMgr.listGadgets();
        while (gadgets.hasNext())
        {
            gadgets.next().remove();
        }
        Gadget g = Gadget.ClassMgr.createGadget(site);
        g.setUrl("http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml");

        g = Gadget.ClassMgr.createGadget(site);
        //g.setUrl("http://localhost:8080/swb/samplecontainer/examples/horoscope.xml");
        g.setUrl("http://www.google.com/ig/modules/horoscope/horoscope.xml");

        g = Gadget.ClassMgr.createGadget(site);
        g.setUrl("http://www.google.com/ig/modules/test_setprefs_multiple_ifpc.xml");

    }

    private static Map<String, String> getMesssages(Document docMessages)
    {
        Map<String, String> getMesssages = new HashMap<String, String>();
        NodeList messages = docMessages.getElementsByTagName("msg");
        for (int i = 0; i < messages.getLength(); i++)
        {
            if (messages.item(i) instanceof Element)
            {
                Element msg = (Element) messages.item(i);
                String name = msg.getAttribute("name");
                String value = msg.getTextContent();
                if (name != null && !name.equals("") && value != null && !value.equals(""))
                {
                    getMesssages.put("__MSG_" + name + "__", value);
                }
            }
        }
        return getMesssages;
    }

    public static Map<String, String> getMessagesFromGadget(Gadget g, String language, String country)
    {
        Map<String, String> getMessagesFromGadget = new HashMap<String, String>();
        if (language != null && language.equals("ALL"))
        {
            language = null;
        }
        if (country != null && country.equals("ALL"))
        {
            country = null;
        }
        String lang_toseach = "";
        if (language != null)
        {
            lang_toseach = language;
        }
        if (country != null)
        {
            lang_toseach += lang_toseach + "-" + country;
        }
        Document document = g.getDocument();
        NodeList locales = document.getElementsByTagName("Locale");
        for (int i = 0; i < locales.getLength(); i++)
        {
            if (locales.item(i) instanceof Element)
            {
                Element locale = (Element) locales.item(i);
                String lang_test = locale.getAttribute("lang");
                if (lang_test == null)
                {
                    lang_test = "";
                }
                if (lang_test.equals(lang_toseach))
                {
                    String messages = locale.getAttribute("messages");
                    if (messages != null && !messages.equals(""))
                    {
                        try
                        {
                            URI uriGadget = new URI(g.getUrl());
                            URI uri = new URI(messages);
                            if (!uri.isAbsolute())
                            {
                                uri = uriGadget.resolve(uri);
                            }
                            Document docMessages = SocialContainer.getXML(uri.toURL());
                            return getMesssages(docMessages);
                        }
                        catch (URISyntaxException use)
                        {
                            log.debug(use);
                        }
                        catch (MalformedURLException use)
                        {
                            log.debug(use);
                        }
                        catch (IOException use)
                        {
                            log.debug(use);
                        }
                        catch (JDOMException use)
                        {
                            log.debug(use);
                        }
                    }
                }

            }
        }
        return getMessagesFromGadget;
    }

    public static Map<String, String> getVariablesubstituion(User user, Gadget gadget, String language, String country, String moduleID)
    {

        Map<String, String> getVariablesubstituion = new HashMap<String, String>();
        getVariablesubstituion.putAll(getMessagesFromGadget(gadget, language, country));

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
                    getVariablesubstituion.put("__UP_" + pref.getKey() + "__", pref.getValue());
                }
            }
        }
        return getVariablesubstituion;
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Iterator<Gadget> gadgets = Gadget.ClassMgr.listGadgets();
        while (gadgets.hasNext())
        {
            Gadget g = gadgets.next();
            g.getDocument();
        }
        if (paramRequest.getMode().equals(Mode_METADATA))
        {
            doMetadata(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_IFRAME))
        {
            doIframe(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_JAVASCRIPT))
        {
            doJavaScript(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_PROXY))
        {
            doProxy(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_CONFIGGADGET))
        {
            docConfigGadgetForUser(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_LISTGADGETS))
        {
            doList(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_MAKE_REQUEST))
        {
            doMakeRequest(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals(Mode_RPC))
        {
            doRPC(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    public static String loadScript(String name)
    {
        StringBuilder sb = new StringBuilder();
        InputStream in = JavaScript.class.getResourceAsStream("/org/semanticwb/opensocial/javascript/" + name);
        byte[] buffer = new byte[2048];
        try
        {
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return sb.toString();
    }

    public static String loadFrame(String name)
    {
        StringBuilder sb = new StringBuilder();
        InputStream in = JavaScript.class.getResourceAsStream("/org/semanticwb/opensocial/frame/" + name);
        byte[] buffer = new byte[2048];
        try
        {
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return sb.toString();
    }

    private boolean isValidGadgetDocument(Gadget gadget)
    {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        StringReader reader = new StringReader(gadgetschema);
        try
        {
            Document doc = getXML(new URL(gadget.getUrl()));
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

    public static String loadSchema(String name)
    {
        StringBuilder sb = new StringBuilder();
        InputStream in = JavaScript.class.getResourceAsStream("/org/semanticwb/opensocial/util/" + name);
        byte[] buffer = new byte[2048];
        try
        {
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return sb.toString();
    }

    public void doRPC(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        RPC rpc = new RPC();
        rpc.doProcess(request, response, paramRequest);
    }

    public void doMakeRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        MakeRequest makeRequest = new MakeRequest();
        makeRequest.doProcess(request, response, paramRequest);
    }

    public void doProxy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Proxy proxy = new Proxy();
        proxy.doProcess(request, response, paramRequest);
    }

    public void doJavaScript(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        JavaScript javascript = new JavaScript();
        javascript.doProcess(request, response, paramRequest);
    }

    public void doMetadata(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Metadata metadata = new Metadata();
        metadata.doProcess(request, response, paramRequest);
    }

    public void doIframe(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        IFrame iframe = new IFrame();
        iframe.doProcess(request, response, paramRequest);
    }

    public static Document getXML(URL url) throws IOException, JDOMException
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream in = con.getInputStream();
        DOMOutputter out = new DOMOutputter();
        SAXBuilder builder = new SAXBuilder();
        return out.output(builder.build(in));
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/opensocial/samplecontainer.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        //doList(request, response, paramRequest);
    }

    public static boolean isValidGadGet(URL url)
    {
        return true;
    }

    public static Gadget getGadget(String url, WebSite site)
    {
        Gadget gadget = null;
        if (url != null && site != null)
        {
            Iterator<Gadget> gadgets = Gadget.ClassMgr.listGadgets(site);
            while (gadgets.hasNext())
            {
                Gadget temp = gadgets.next();
                if (temp.getUrl().equals(url))
                {
                    gadget = temp;
                    break;
                }
            }
        }
        return gadget;
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
    }

    public void doList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/opensocial/list.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    public void docConfigGadgetForUser(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        String url = request.getParameter("url");
        if (url != null)
        {
            Gadget g = getGadget(url, site);
            if (g != null)
            {
                String path = "/swbadmin/jsp/opensocial/config.jsp";
                RequestDispatcher dis = request.getRequestDispatcher(path);
                try
                {
                    request.setAttribute("paramRequest", paramRequest);
                    request.setAttribute("gadget", g);
                    dis.include(request, response);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        }
    }
}
