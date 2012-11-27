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
package org.semanticwb.opensocial.model;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import org.jdom.JDOMException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.resources.RequestException;
import org.semanticwb.opensocial.resources.SocialContainer;
import org.semanticwb.opensocial.resources.SocialUser;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

public class Gadget extends org.semanticwb.opensocial.model.base.GadgetBase
{

    private static final Logger log = SWBUtils.getLogger(Gadget.class);
    private Document doc;
    private Document original;
    private String title;
    private final HashSet<String> categories = new HashSet<String>();
    private final Map<String, FeatureDetail> featureDetails = new HashMap<String, FeatureDetail>();
    private URL thumbnail;
    private final Map<String, String> getDefaultUserPref = new HashMap<String, String>();
    private URL screenshot;
    private String directoryTitle;
    private String author;
    private String authorEmail;
    private int width;
    private int height;
    private String description;
    private final HashSet<String> userPrefsNames = new HashSet<String>();
    private final HashSet<View> views = new HashSet<View>();
    private URL titleUrl;
    private boolean scrolling;

    public Gadget(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        if (this.getUrl() != null)
        {
            reload();
        }
    }

    public Document getDocument(String languaje, String country)
    {
        Document _doc = null;
        if (original != null)
        {
            Map<String, String> messages = this.getMessagesFromGadget(languaje, country);
            if (messages.isEmpty())
            {
                _doc = original;
            }
            else
            {
                Charset charset = Charset.defaultCharset();
                if (original != null)
                {
                    String xml = SWBUtils.XML.domToXml(original, charset.name(), false);
                    for (String key : messages.keySet())
                    {
                        String value = messages.get(key);
                        xml = xml.replace(key, value);
                    }
                    try
                    {
                        _doc = SWBUtils.XML.xmlToDom(xml);
                    }
                    catch(RuntimeException e)
                    {

                        log.debug("gadget "+this.getUrl(),e);
                        log.debug("xml");
                        log.debug(xml);

                    }
                }
            }
        }
        if (_doc == null)
        {
            _doc = doc;
        }
        return _doc;
    }

    @Override
    public void setUrl(String value)
    {
        super.setUrl(value);
        reload();
    }

    public static String[] getAllCategories(WebSite site)
    {
        HashSet<String> getCategories = new HashSet<String>();
        Iterator<Gadget> gadgets = Gadget.ClassMgr.listGadgets(site);
        while (gadgets.hasNext())
        {
            Gadget g = gadgets.next();
            for (String cat : g.getCategories())
            {
                if (cat != null && !cat.equals(""))
                {
                    getCategories.add(cat);
                }
            }
        }
        return getCategories.toArray(new String[getCategories.size()]);
    }

    public static Gadget[] getGadgetsByCategory(String category, WebSite site)
    {
        HashSet<Gadget> getCategories = new HashSet<Gadget>();
        Iterator<Gadget> gadgets = Gadget.ClassMgr.listGadgets(site);
        while (gadgets.hasNext())
        {
            Gadget g = gadgets.next();
            for (String cat : g.getCategories())
            {
                if (cat != null && !cat.equals("") && cat.equals(category))
                {
                    getCategories.add(g);
                }
            }
        }
        return getCategories.toArray(new Gadget[getCategories.size()]);
    }

    public FeatureDetail[] getFeatureDetails()
    {
        return featureDetails.values().toArray(new FeatureDetail[featureDetails.values().size()]);
    }

    private Map<String, String> getMesssages(Document docMessages)
    {
        return getMesssages(docMessages, true);
    }

    private Map<String, String> getMesssages(Document docMessages, boolean formated)
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
                    if (formated)
                    {
                        name = "__MSG_" + name + "__";
                    }
                    getMesssages.put(name, value);

                }
            }
        }
        return getMesssages;
    }

    public Map<String, String> getDefaultUserPref(String language, String country, boolean formated)
    {
        return getDefaultUserPref;
    }

    public Map<String, String> getMessagesFromGadget(String language, String country)
    {
        return getMessagesFromGadget(language, country, true);
    }

    public boolean existLocale(String lang_toseach, boolean exact)
    {
        NodeList locales = this.original.getElementsByTagName("Locale");
        for (int i = 0; i < locales.getLength(); i++)
        {
            if (locales.item(i) instanceof Element)
            {
                Element locale = (Element) locales.item(i);
                String lang_test = getKey(locale, "lang");
                if (lang_test == null)
                {
                    lang_test = "";
                }
                String country=getKey(locale, "country");
                if(country!=null)
                {
                    lang_test="_"+country;
                }
                if (exact)
                {
                    if (lang_test.equals(lang_toseach))
                    {
                        return true;
                    }
                }
                else
                {
                    if (lang_test.startsWith(lang_toseach))
                    {
                        return true;
                    }
                }


            }
        }
        return false;
    }

    public Map<String, String> getMessagesFromGadget(String language, String country, boolean formated)
    {

        Map<String, String> getMessagesFromGadget = new HashMap<String, String>();
        if (this.original != null)
        {

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
                lang_toseach += lang_toseach + "_" + country;
            }
            boolean exact = true;

            boolean existLocale = existLocale(lang_toseach, exact);

            if (!existLocale)
            {
                exact = false;
                if (!"".equals(lang_toseach) && lang_toseach.indexOf("-") == -1)
                {
                    lang_toseach = lang_toseach + "_";
                }
                existLocale = existLocale(lang_toseach, exact);
                if (!existLocale)
                {
                    exact = true;
                    lang_toseach = "";
                }
            }
            NodeList locales = this.original.getElementsByTagName("Locale");
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
                    String _country=getKey(locale, "country");
                    if(_country!=null)
                    {
                        lang_test="_"+_country;
                    }
                    if (exact)
                    {
                        if (lang_test.equals(lang_toseach))
                        {
                            String messages = locale.getAttribute("messages");
                            if (messages != null && !messages.equals(""))
                            {
                                try
                                {
                                    URI uriGadget = new URI(this.getUrl());
                                    URI uri = new URI(messages);
                                    if (!uri.isAbsolute())
                                    {
                                        uri = uriGadget.resolve(uri);
                                    }
                                    Document docMessages = SocialContainer.getXML(uri.toURL());
                                    return getMesssages(docMessages, formated);
                                }
                                catch (RequestException e)
                                {
                                    log.debug(e);
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
                    else
                    {
                        if (lang_test.startsWith(lang_toseach))
                        {
                            String messages = locale.getAttribute("messages");
                            if (messages != null && !messages.equals(""))
                            {
                                try
                                {
                                    URI uriGadget = new URI(this.getUrl());
                                    URI uri = new URI(messages);
                                    if (!uri.isAbsolute())
                                    {
                                        uri = uriGadget.resolve(uri);
                                    }
                                    Document docMessages = SocialContainer.getXML(uri.toURL());
                                    return getMesssages(docMessages, formated);
                                }
                                catch (RequestException e)
                                {
                                    log.debug(e);
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
            }
        }
        return getMessagesFromGadget;
    }

    private String getKey(Element module, String key)
    {
        String value = module.getAttribute(key) == null || "".equals(module.getAttribute(key).trim()) ? null : module.getAttribute(key);//(module.getAttribute(key) != null || "".equals(module.getAttribute(key))) ? module.getAttribute(key) : null;
        return value;
    }

    public final void reload()
    {
        doc = null;
        try
        {
            original = SocialContainer.getXML(new URL(this.getUrl()));
            doc = original;
        }
        catch (IOException e)
        {
            log.debug(e);
        }
        catch (JDOMException e)
        {
            log.debug(e);
        }
        catch (RequestException e)
        {
            log.debug(e);
        }
        Map<String, String> variables = this.getMessagesFromGadget("ALL", "ALL");
        if (!variables.isEmpty() && doc != null)
        {
            try
            {
                Charset charset = Charset.defaultCharset();
                String xml = SWBUtils.XML.domToXml(SocialContainer.getXML(new URL(this.getUrl())), charset.name(), false);

                for (String key : variables.keySet())
                {
                    String value = variables.get(key);
                    xml = xml.replace(key, value);
                }
                doc = SWBUtils.XML.xmlToDom(xml);
            }
            catch (RequestException e)
            {
                log.debug(e);
            }
            catch (IOException e)
            {
                log.debug(e);
            }
            catch (JDOMException e)
            {
                log.debug(e);
            }
        }
        categories.clear();
        getDefaultUserPref.clear();
        views.clear();
        userPrefsNames.clear();
        getDefaultUserPref.clear();
        featureDetails.clear();
        directoryTitle = null;
        titleUrl = null;
        authorEmail = null;
        author = null;
        title = null;
        width = 0;
        height = 0;
        thumbnail = null;
        screenshot = null;
        description = null;
        scrolling = false;
        if (doc != null && doc.getElementsByTagName("ModulePrefs").getLength() > 0)
        {
            Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
            directoryTitle = getKey(module, "directory_title");
            String _titleUrl = getKey(module, "title_url");
            authorEmail = getKey(module, "author_email");
            author = getKey(module, "author");
            title = getKey(module, "title");
            String _thumbnail = getKey(module, "thumbnail");
            String _screenshot = getKey(module, "screenshot");
            String _width = getKey(module, "width");
            String _height = getKey(module, "height");
            description = getKey(module, "description");
            String _scrolling = getKey(module, "scrolling");
            String _category = getKey(module, "category");
            NodeList contents = original.getElementsByTagName("Content");
            for (int i = 0; i < contents.getLength(); i++)
            {
                if (contents.item(i) instanceof Element)
                {
                    Element content = (Element) contents.item(i);
                    String _view = getKey(content, "view");
                    if (_view != null)
                    {
                        StringTokenizer st = new StringTokenizer(_view, ",");
                        while (st.hasMoreTokens())
                        {
                            String view = st.nextToken().trim();
                            if (!"".equals(view))
                            {
                                views.add(createView(content, view));
                            }
                        }
                    }
                    else
                    {
                        views.add(createView(content, "default"));
                    }
                }
            }




            if (_titleUrl != null && !_titleUrl.trim().equals(""))
            {
                try
                {
                    URI url_title_url = new URI(_titleUrl);
                    URI gadget = new URI(this.getUrl());
                    if (!url_title_url.isAbsolute())
                    {
                        url_title_url = gadget.resolve(url_title_url);
                    }
                    titleUrl = url_title_url.toURL();
                }
                catch (URISyntaxException use)
                {
                    log.debug(use);
                }
                catch (MalformedURLException use)
                {
                    log.debug(use);
                }
            }

            if (_screenshot != null && !_screenshot.trim().equals(""))
            {
                try
                {
                    URI urlscreenshot = new URI(_screenshot);
                    URI gadget = new URI(this.getUrl());
                    if (!urlscreenshot.isAbsolute())
                    {
                        urlscreenshot = gadget.resolve(urlscreenshot);
                    }
                    screenshot = urlscreenshot.toURL();
                }
                catch (URISyntaxException use)
                {
                    log.debug(use);
                }
                catch (MalformedURLException use)
                {
                    log.debug(use);
                }
            }


            if (_thumbnail != null && !_thumbnail.trim().equals(""))
            {
                try
                {
                    URI urlthumbnail = new URI(_thumbnail);
                    URI gadget = new URI(this.getUrl());
                    if (!urlthumbnail.isAbsolute())
                    {
                        urlthumbnail = gadget.resolve(urlthumbnail);
                    }
                    thumbnail = urlthumbnail.toURL();
                }
                catch (URISyntaxException use)
                {
                    log.debug(use);
                }
                catch (MalformedURLException use)
                {
                    log.debug(use);
                }
            }

            if (_width != null)
            {
                try
                {
                    width = Integer.parseInt(_width);
                }
                catch (NumberFormatException nfe)
                {
                    log.debug(nfe);
                }
            }


            if (_height != null)
            {
                try
                {
                    height = Integer.parseInt(_height);
                }
                catch (NumberFormatException nfe)
                {
                    log.debug(nfe);
                }
            }


            if (_category != null)
            {
                StringTokenizer st = new StringTokenizer(_category, ",");
                while (st.hasMoreTokens())
                {
                    String category = st.nextToken().trim();
                    if (!"".equals(category))
                    {
                        categories.add(category);
                    }
                }
            }


            if ("true".equals(_scrolling))
            {
                scrolling = true;
            }
            else
            {
                scrolling = false;
            }
            NodeList userprefs = doc.getElementsByTagName("UserPref");
            for (int i = 0; i < userprefs.getLength(); i++)
            {
                Element userpref = (Element) userprefs.item(i);
                String name = userpref.getAttribute("name");
                String default_value = userpref.getAttribute("default_value");
                if(default_value==null)
                {
                    default_value="";
                }
                String type = userpref.getAttribute("type");
                if (type == null || "".equals(type))
                {
                    type = "string";
                }
                if (name != null && !"".equals(name))
                {
                    userPrefsNames.add(name);
                    if (("hidden".equalsIgnoreCase(type) || "list".equalsIgnoreCase(type) || "string".equalsIgnoreCase(type)) && (default_value == null || "".equals(default_value)))
                    {
                        default_value = "";
                    }
                    else if ("bool".equalsIgnoreCase(type) && (default_value == null || "".equals(default_value)))
                    {
                        default_value = "false";
                    }
                    else if ("number".equalsIgnoreCase(type) && (default_value == null || "".equals(default_value)))
                    {
                        default_value = "0";
                    }
                    else if ("enum".equalsIgnoreCase(type) && (default_value == null || "".equals(default_value)))
                    {
                        NodeList enumValues = userpref.getElementsByTagName("EnumValue");
                        if (enumValues.getLength() > 0)
                        {
                            Element enumValue = (Element) enumValues.item(0);
                            default_value = enumValue.getAttribute("value");
                        }
                        else
                        {
                            default_value = "";
                        }
                    }
                    getDefaultUserPref.put(name, default_value);
                }
            }



            NodeList childs = module.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element echild = (Element) childs.item(i);
                    if ("Require".equals(echild.getTagName()) || "Optional".equals(echild.getTagName()))
                    {
                        Element require = echild;
                        if (require.getAttribute("feature") != null && !"".equals(require.getAttribute("feature")))
                        {
                            String feature = getKey(require, "feature");
                            if (feature != null)
                            {
                                StringTokenizer st = new StringTokenizer(feature, ",");
                                while (st.hasMoreTokens())
                                {
                                    String featureName = st.nextToken().trim();
                                    FeatureDetail detail = new FeatureDetail();
                                    detail.name = featureName;
                                    detail.required = "Optional".equals(echild.getTagName()) ? false : true;
                                    NodeList params = require.getChildNodes();
                                    ArrayList<Parameter> parameters = new ArrayList<Parameter>();
                                    for (int j = 0; j < params.getLength(); j++)
                                    {
                                        if (params.item(j) instanceof Element && ((Element) params.item(j)).getTagName().equals("Param"))
                                        {
                                            Element param = (Element) params.item(j);
                                            String name = param.getAttribute("name");
                                            if (name != null)
                                            {
                                                Parameter p = new Parameter();
                                                p.name = name;
                                                parameters.add(p);
                                            }
                                        }
                                    }
                                    detail.parameters = parameters.toArray(new Parameter[parameters.size()]);
                                    featureDetails.put(featureName, detail);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Document getDocument()
    {
        if (doc == null)
        {
            try
            {
                doc = SocialContainer.getXML(new URL(this.getUrl()));
                Map<String, String> variables = this.getMessagesFromGadget("ALL", "ALL");
                if (!variables.isEmpty() && doc != null)
                {
                    Charset charset = Charset.defaultCharset();
                    String xml = SWBUtils.XML.domToXml(SocialContainer.getXML(new URL(this.getUrl())), charset.name(), false);
                    for (String key : variables.keySet())
                    {
                        String value = variables.get(key);
                        xml = xml.replace(key, value);
                    }
                    doc = SWBUtils.XML.xmlToDom(xml);
                }
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        return doc;
    }

    public URL getThumbnail()
    {
        return thumbnail;
    }

    public static boolean existImage(URL url)
    {
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() == 200)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        return false;
    }

    public URL getScreenshot()
    {
        return screenshot;
    }

    public String getDirectoryTitle()
    {
        return directoryTitle;
    }

    public String getDirectoryTitle(SocialUser socialuser)
    {
        return getDirectoryTitle(socialuser, null);
    }

    public String getDirectoryTitle(SocialUser socialuser, String language, String country, String moduleid)
    {
        Document _doc = getDocument(language, country);
        String _directory_title = null;
        if (_doc != null)
        {
            if (_doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) _doc.getElementsByTagName("ModulePrefs").item(0);
                _directory_title = getKey(module, "directory_title");
            }
            if (_directory_title != null && moduleid != null && socialuser!=null)
            {                
                Map<String, String> variables = socialuser.getVariablesubstituion(this, language, country, moduleid);
                if (!variables.isEmpty())
                {
                    for (String key : variables.keySet())
                    {
                        _directory_title = _directory_title.replace(key, variables.get(key));
                    }
                }

            }
        }
        if (_directory_title == null)
        {
            _directory_title = getTitle();
        }

        return _directory_title;
    }

    public String getDirectoryTitle(SocialUser socialuser, String moduleid)
    {
        return getDirectoryTitle(socialuser, socialuser.getLanguage(), socialuser.getCountry(), moduleid);
    }

    public String getTitle(SocialUser socialuser)
    {
        return getTitle(socialuser, null);
    }

    public String getTitle(SocialUser socialuser,String language, String country, String moduleid)
    {
        Document _doc = getDocument(language, country);
        String _title = null;
        if (_doc != null)
        {
            if (_doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) _doc.getElementsByTagName("ModulePrefs").item(0);
                _title = getKey(module, "title");

            }
            if (_title != null && moduleid != null && socialuser != null)
            {                
                Map<String, String> variables = socialuser.getVariablesubstituion(this, language, country, moduleid);
                if (!variables.isEmpty())
                {
                    for (String key : variables.keySet())
                    {
                        _title = _title.replace(key, variables.get(key));
                    }
                }

            }
        }
        if (_title == null)
        {
            _title = getTitle();
        }

        return _title;
    }

    public String getTitle(String language, String country, String moduleid)
    {
        return this.getTitle(null, language, country, moduleid);
    }

    public String getTitle(SocialUser socialuser, String moduleid)
    {
        return this.getTitle(socialuser,socialuser.getLanguage(), socialuser.getCountry(), moduleid);
    }

    public String getDescription(SocialUser socialuser)
    {
        return getDescription(socialuser, null);
    }

    public String getDescription(String language, String country, String moduleid)
    {
        return this.getDescription(null, language, country, moduleid);
    }

    public String getDescription(SocialUser socialuser, String language, String country, String moduleid)
    {
        Document _doc = getDocument(language, country);
        String _description = null;
        if (_doc != null)
        {
            if (_doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) _doc.getElementsByTagName("ModulePrefs").item(0);
                _description = getKey(module, "description");
            }
            if (_description != null && moduleid != null && socialuser != null)
            {
                WebSite site=socialuser.getWebSite();
                Map<String, String> variables = socialuser.getVariablesubstituion(this, language, country, moduleid);
                if (!variables.isEmpty())
                {
                    for (String key : variables.keySet())
                    {
                        _description = _description.replace(key, variables.get(key));
                    }
                }

            }
        }
        if (_description == null)
        {
            _description = getDescription();
        }

        return _description;
    }

    public String getDescription(SocialUser socialuser, String moduleid)
    {
        return getDescription(socialuser, socialuser.getLanguage(), socialuser.getCountry(), moduleid);
    }

    public String getGadgetTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public String getAuthorEmail()
    {
        return authorEmail;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getGadgetDescription()
    {
        return description;
    }

    public String[] getUserPrefsNames()
    {
        return userPrefsNames.toArray(new String[userPrefsNames.size()]);
    }

    public String[] getAllFeatures()
    {
        return featureDetails.keySet().toArray(new String[featureDetails.keySet().size()]);
    }

    public String[] getRequiredFeatures()
    {
        HashSet<String> getRequiredFeatures = new HashSet<String>();
        for (String key : featureDetails.keySet())
        {
            FeatureDetail detail = featureDetails.get(key);
            if (detail.required)
            {
                getRequiredFeatures.add(key);
            }
        }
        return getRequiredFeatures.toArray(new String[getRequiredFeatures.size()]);
    }

    public String[] getOptionalFeatures()
    {
        HashSet<String> getOptionalFeatures = new HashSet<String>();
        for (String key : featureDetails.keySet())
        {
            FeatureDetail detail = featureDetails.get(key);
            if (!detail.required)
            {
                getOptionalFeatures.add(key);
            }
        }
        return getOptionalFeatures.toArray(new String[getOptionalFeatures.size()]);
    }

    public String[] getCategories()
    {
        return categories.toArray(new String[categories.size()]);
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

    private View createView(Element content, String viewName)
    {

        View view = new View(viewName);
        String type = getKey(content, "type");
        view.setType(type);
        int preferredHeight = 0;
        if (getKey(content, "preferred_height") != null)
        {
            try
            {
                preferredHeight = Integer.parseInt(content.getAttribute("preferred_height"));
            }
            catch (NumberFormatException bfe)
            {
                log.debug(bfe);
            }
        }
        view.setPreferredHeight(preferredHeight);


        int preferredWidth = 0;
        if (getKey(content, "preferred_width") != null)
        {
            try
            {
                preferredWidth = Integer.parseInt(content.getAttribute("preferred_width"));
            }
            catch (NumberFormatException bfe)
            {
                log.debug(bfe);
            }
        }
        view.setPreferredWidth(preferredWidth);



        boolean _scrolling = true;
        if (getKey(content, "scrolling") != null)
        {
            try
            {
                _scrolling = Boolean.parseBoolean(content.getAttribute("scrolling"));
            }
            catch (NumberFormatException bfe)
            {
                log.debug(bfe);
            }
        }
        view.setScrolling(_scrolling);

        String href = getKey(content, "href");
        if ("url".equalsIgnoreCase(type))
        {
            if (href != null)
            {
                try
                {
                    URI urihref = new URI(href);
                    URI urigadget = new URI(this.getUrl());
                    if (!urihref.isAbsolute())
                    {
                        urigadget.resolve(urihref);
                    }
                    view.setUrlContent(urihref.toURL());
                }
                catch (Exception e)
                {
                    log.debug(e);
                }
            }
        }
        else
        {
            if (href != null)
            {
                try
                {
                    URI urihref = new URI(href);
                    URI urigadget = new URI(this.getUrl());
                    if (!urihref.isAbsolute())
                    {
                        urihref = urigadget.resolve(urihref);
                    }
                    String html = getHTML(urihref.toURL());
                    view.setContent(html);
                }
                catch (Exception e)
                {
                    log.debug(e);
                }
            }
            else
            {
                StringBuilder html = new StringBuilder();
                NodeList childs = content.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    if (childs.item(j) instanceof CDATASection)
                    {
                        CDATASection section = (CDATASection) childs.item(j);
                        if (section.getNodeValue() != null)
                        {
                            html.append(section.getNodeValue());
                        }
                    }
                }
                view.setContent(html.toString());
            }
        }

        return view;
    }

    public View[] getViews()
    {
        return views.toArray(new View[views.size()]);
    }

    public URL getTitleUrl()
    {
        return titleUrl;
    }

    public boolean getScrolling()
    {
        return scrolling;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Gadget other = (Gadget) obj;
        if ((this.getUrl() == null) ? (other.getUrl() != null) : !this.getUrl().equals(other.getUrl()))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 71 * hash + (this.getUrl() != null ? this.getUrl().hashCode() : 0);
        return hash;
    }
}
