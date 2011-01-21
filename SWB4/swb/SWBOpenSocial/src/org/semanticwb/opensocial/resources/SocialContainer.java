/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
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

    private static final String SOCIAL_USER_ATTRIBUTE = "socialuser";
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
    private static final Set<String> supportedFeatures = new HashSet<String>();

    static
    {
        supportedFeatures.add("settile");
        supportedFeatures.add("flash");
        supportedFeatures.add("rpc");
        supportedFeatures.add("setprefs");
        supportedFeatures.add("dynamic-height");
        //String[] urls={"http://www.delsearegional.us/academic/classes/highschool/science/physics/age/age.xml","http://midots.com/gadgets/xmldocs/midotsImgViewBeautifulPhotosOfIslands_11.xml","http://hosting.gmodules.com/ig/gadgets/file/112581010116074801021/spider.xml","http://www.donalobrien.net/apps/google/currency.xml","http://opensocial-resources.googlecode.com/svn/tests/trunk/suites/0.8/compliance/reference.xml","http://localhost:8080/swb/samplecontainer/examples/horoscope.xml","http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml","http://www.google.com/ig/modules/horoscope/horoscope.xml","http://www.google.com/ig/modules/test_setprefs_multiple_ifpc.xml"};
        String[] urls =
        {
        };
        WebSite site = WebSite.ClassMgr.getWebSite("reg_digital_demo");
        for (String url : urls)
        {
            boolean exists = false;
            Iterator<Gadget> gadgets = Gadget.ClassMgr.listGadgets();
            while (gadgets.hasNext())
            {
                Gadget gadget = gadgets.next();
                if (gadget.getUrl().equals(url))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                Gadget g = Gadget.ClassMgr.createGadget(site);
                g.setUrl(url);
            }
        }

    }

    public static boolean supportAllFeatures(Gadget gadget)
    {
        for (String feature : gadget.getAllFeatures())
        {
            if (!supportedFeatures.contains(feature))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean supportRequiredFeatures(Gadget gadget)
    {
        for (String feature : gadget.getRequiredFeatures())
        {
            if (!supportedFeatures.contains(feature))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean supportOptionalFeatures(Gadget gadget)
    {
        for (String feature : gadget.getOptionalFeatures())
        {
            if (!supportedFeatures.contains(feature))
            {
                return false;
            }
        }
        return true;
    }

    public static SocialUser getSocialUser(User user, HttpSession session)
    {
        SocialUser socialUser = (SocialUser) session.getAttribute(SOCIAL_USER_ATTRIBUTE);
        if (socialUser == null)
        {
            socialUser = new SocialUser(user);
            session.setAttribute(SOCIAL_USER_ATTRIBUTE, socialUser);
        }
        String user1 = user == null ? null : user.getId();
        String user2 = socialUser.getUserId();
        if (!(user1 == null && user2 == null))
        {
            if ((user1 == null && user2 != null) || (user1 != null && user2 == null))
            {
                socialUser = new SocialUser(user);
                session.setAttribute(SOCIAL_USER_ATTRIBUTE, socialUser);
            }
            else
            {
                if (!user1.equals(user2))
                {
                    socialUser = new SocialUser(user);
                    session.setAttribute(SOCIAL_USER_ATTRIBUTE, socialUser);
                }
            }
        }
        socialUser.refresh(user);
        return socialUser;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String url = request.getParameter("__url__");
        WebSite site = response.getWebPage().getWebSite();
        User user = response.getUser();




        if (url != null)
        {
            Gadget gadget = getGadget(url, site);




            if (gadget != null)
            {
                SocialUser socialUser = getSocialUser(user, request.getSession());
                Document doc = gadget.getDocument();
                NodeList userPrefs = doc.getElementsByTagName("UserPref");
                String moduleid = null;





                if (user != null && user.getId() != null)
                {
                    PersonalizedGadged pgadget = PersonalizedGadged.ClassMgr.createPersonalizedGadged(site);
                    pgadget.setGadget(gadget);
                    pgadget.setUser(user);
                    moduleid = pgadget.getId();




                }
                else
                {
                    moduleid = String.valueOf(socialUser.getNumberOfGadgets() + 1);




                    if (userPrefs.getLength() == 0)
                    {
                        socialUser.saveUserPref(gadget, moduleid, null, null, site);




                    }
                }

                for (int i = 0; i
                        < userPrefs.getLength(); i++)
                {
                    if (userPrefs.item(i) instanceof Element)
                    {
                        Element userPref = (Element) userPrefs.item(i);
                        String key = userPref.getAttribute("name");
                        String value = request.getParameter(key);
                        String type = "String";




                        if (userPref.getAttribute("type") != null && !userPref.getAttribute("type").equals(""))
                        {
                            type = userPref.getAttribute("type");




                        }
                        if ("list".equals(type))
                        {
                            StringTokenizer st = new StringTokenizer(value, "\r\n");




                            while (st.hasMoreTokens())
                            {
                                String temp = st.nextToken();
                                value += temp + "|";




                            }
                            if (value.endsWith("|"))
                            {
                                value = value.substring(0, value.length() - 2);




                            }
                        }
                        socialUser.saveUserPref(gadget, moduleid, key, value, site);




                    }
                }
            }
        }
        response.setMode(SocialContainer.Mode_LISTGADGETS);




    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException,
            IOException
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

    public static Document getXML(URL url) throws IOException, JDOMException, RequestException
    {
        Charset charset = Charset.forName("utf-8");
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
            InputStream in = con.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, charset);
            DOMOutputter out = new DOMOutputter();
            SAXBuilder builder = new SAXBuilder();
            return out.output(builder.build(reader));
        }
        else
        {
            throw new RequestException("", con.getResponseCode());
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        User user = paramRequest.getUser();
        SocialUser socialuser = new SocialUser(user);
        for (UserPrefs pref : socialuser.getUserPrefs(site))
        {
            if (pref.getGadget() != null)
            {
                Gadget g = pref.getGadget();
                socialuser.checkOsapiFeature(g, site, true);
            }
        }
        String path = "/swbadmin/jsp/opensocial/container.jsp";
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
