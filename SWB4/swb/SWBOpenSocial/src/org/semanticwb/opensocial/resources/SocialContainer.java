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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
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
    public static final String Mode_SERVICECONTAINER = "servicecontainer";
    public static final String Mode_RPC = "RPC";
    private static final Set<String> supportedFeatures = new HashSet<String>();
    private static final String _package = "/org/semanticwb/opensocial/web/";
    private static final String[] files =
    {
        "config.jsp",
        "container.jsp",
        "frame.jsp",
        "gadgets.css",
        "list.jsp",
        "rpc_relay.html",
        "sinfoto.png",
        "estilo.css"
    };
        static final String[] urls =
        {
            "http://hosting.gmodules.com/ig/gadgets/file/111782983456439885554/Google-Gadget-RSS.xml", "http://www.tc.df.gov.br/MpjTcdf/AlcCalc.xml",
            "http://www.italiagadget.net/news/newsussci.xml","http://www.delsearegional.us/academic/classes/highschool/science/physics/age/age.xml","http://midots.com/gadgets/xmldocs/midotsImgViewBeautifulPhotosOfIslands_11.xml","http://hosting.gmodules.com/ig/gadgets/file/112581010116074801021/spider.xml","http://www.donalobrien.net/apps/google/currency.xml","http://opensocial-resources.googlecode.com/svn/tests/trunk/suites/0.8/compliance/reference.xml","http://localhost:8080/swb/samplecontainer/examples/horoscope.xml","http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml","http://www.google.com/ig/modules/horoscope/horoscope.xml","http://www.google.com/ig/modules/test_setprefs_multiple_ifpc.xml"
        };//,http://www.delsearegional.us/academic/classes/highschool/science/physics/age/age.xml","http://midots.com/gadgets/xmldocs/midotsImgViewBeautifulPhotosOfIslands_11.xml","http://hosting.gmodules.com/ig/gadgets/file/112581010116074801021/spider.xml","http://www.donalobrien.net/apps/google/currency.xml","http://opensocial-resources.googlecode.com/svn/tests/trunk/suites/0.8/compliance/reference.xml","http://localhost:8080/swb/samplecontainer/examples/horoscope.xml","http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml","http://www.google.com/ig/modules/horoscope/horoscope.xml","http://www.google.com/ig/modules/test_setprefs_multiple_ifpc.xml"};
    static
    {
        supportedFeatures.add("views");
        supportedFeatures.add("settile");
        supportedFeatures.add("flash");
        supportedFeatures.add("rpc");
        supportedFeatures.add("setprefs");
        supportedFeatures.add("tabs");
        supportedFeatures.add("dynamic-height");

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

    public static SocialUser getSocialUser(User user, HttpSession session, WebSite site)
    {
        String siteid=site.getId();
        SocialUser socialUser = (SocialUser) session.getAttribute(SOCIAL_USER_ATTRIBUTE+siteid);
        if (socialUser == null)
        {
            log.debug("Creando nuevo usuario para session: " + session.getId());
            socialUser = new SocialUser(user, site);
            session.setAttribute(SOCIAL_USER_ATTRIBUTE+siteid, socialUser);
        }
        String user1 = user == null ? null : user.getId();
        String user2 = socialUser.getUserId();
        if (!(user1 == null && user2 == null))
        {
            if ((user1 == null && user2 != null) || (user1 != null && user2 == null))
            {
                log.debug("cambio de usuario old: " + user2 + " new: " + user1);
                socialUser = new SocialUser(user, site);
                session.setAttribute(SOCIAL_USER_ATTRIBUTE+siteid, socialUser);
            }
            else
            {
                if (!user1.equals(user2))
                {
                    log.debug("cambio de usuario old: " + user2 + " new: " + user1);
                    socialUser = new SocialUser(user, site);
                    session.setAttribute(SOCIAL_USER_ATTRIBUTE+siteid, socialUser);
                }
            }
        }
        socialUser.refresh(user, site);
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
                SocialUser socialUser = getSocialUser(user, request.getSession(), site);
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
                        socialUser.saveUserPref(gadget, moduleid, null, null);
                    }
                }

                for (int i = 0; i< userPrefs.getLength(); i++)
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
                        socialUser.saveUserPref(gadget, moduleid, key, value);
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

        try
        {
            WebSite site = paramRequest.getWebPage().getWebSite();
            for (String url : urls)
            {
                boolean exists = false;
                Iterator<Gadget> itgadgets = Gadget.ClassMgr.listGadgets(site);
                while (itgadgets.hasNext())
                {
                    Gadget gadget = itgadgets.next();
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
        catch (Exception e)
        {
            log.debug(e);
        }
        String pathdir = SWBPortal.getWorkPath() + this.getResourceBase().getWorkPath();
        File dir = new File(pathdir);
        for (String file : files)
        {
            checkfiles(file, dir);
        }
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
        else if (paramRequest.getMode().equals(Mode_SERVICECONTAINER))
        {
            doServiceContainer(request, response, paramRequest);
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

    /*public static String loadScript(String name)
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
    }*/
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

    public void doServiceContainer(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String port = "";
        if (request.getServerPort() != 80)
        {
            port = ":" + request.getServerPort();
        }
        boolean doService = false;
        String contentType = request.getContentType();
        if ("application/javascript".equals(contentType))
        {
            log.debug("referer:  " + request.getHeader("referer"));
            String referer = request.getHeader("referer");

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
                        doService = true;
                    }
                }
                catch (MalformedURLException e)
                {
                    log.debug(e);
                }
            }
            WebSite site = paramRequest.getWebPage().getWebSite();
            SocialUser socialUser = SocialContainer.getSocialUser(paramRequest.getUser(), request.getSession(), site);
            InputStream in = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1028];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
            in.close();

            try
            {
                if (doService)
                {
                    JSONObject json = new JSONObject(sb.toString());
                    String service = json.getString("service");
                    if ("remove".equals(service))
                    {
                        String moduleid = json.getString("moduleid");
                        String url = json.getString("url");
                        if (url != null && !"".equals(url))
                        {
                            Gadget gadget = SocialContainer.getGadget(url, site);
                            if (gadget != null && moduleid != null && !"".equals(moduleid))
                            {
                                socialUser.removeGadget(gadget, moduleid);
                            }
                        }
                    }
                }
            }
            catch (JSONException jsone)
            {
                response.sendError(500);
                log.debug(jsone);
            }
        }
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
            throw new RequestException("Error getting XML from "+url.toString()+" ResponseCode: "+con.getResponseCode(), con.getResponseCode());
        }
    }

    private void checkfiles(String file, File dir)
    {
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        File filecheck = new File(dir.getAbsolutePath() + "/" + file);
        if (!filecheck.exists())
        {
            try
            {
                log.debug("Writing file " + filecheck.getAbsolutePath());
                FileOutputStream out = new FileOutputStream(filecheck);
                InputStream in = SocialContainer.class.getResourceAsStream(_package + file);
                byte[] buffer = new byte[2048];
                int read = in.read(buffer);
                while (read != -1)
                {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
                log.debug("file " + filecheck.getAbsolutePath() + " was created");
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        User user = paramRequest.getUser();
        SocialUser socialuser = new SocialUser(user, site);
        for (UserPrefs pref : socialuser.getUserPrefs())
        {
            if (pref.getGadget() != null)
            {
                Gadget g = pref.getGadget();
                socialuser.checkOsapiFeature(g, true);
            }
        }
        String path = "/work/" + paramRequest.getResourceBase().getWorkPath() + "/container.jsp";
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
        String path = "/work/" + paramRequest.getResourceBase().getWorkPath() + "/list.jsp";
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
                String path = "/work/" + paramRequest.getResourceBase().getWorkPath() + "/config.jsp";
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
