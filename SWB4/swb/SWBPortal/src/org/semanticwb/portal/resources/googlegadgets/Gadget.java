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
package org.semanticwb.portal.resources.googlegadgets;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class Gadget.
 * 
 * @author victor.lorenzana
 */
public class Gadget
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Gadget.class);
    /** The Constant DEFAULT_VALUE. */
    private static final String DEFAULT_VALUE = "default_value";
    /** The Constant MESSAGES_ATTRIBUTE. */
    private static final String MESSAGES_ATTRIBUTE = "messages";
    /** The Constant MSG_LOCALE. */
    private static final String MSG_LOCALE = "__MSG_locale__";
    /** The Constant PATH_FIRST_LOCALE. */
    private static final String PATH_FIRST_LOCALE = "/Module/ModulePrefs/Locale[1]";
    /** The Constant PATH_USER_PREFERENCES. */
    private static final String PATH_USER_PREFERENCES = "/Module/UserPref[@name=\'";
    /** The Constant TITLE_ATTRIBUTE. */
    private static final String TITLE_ATTRIBUTE = "title";
    /** The builder. */
    private static SAXBuilder builder = new SAXBuilder();
    /** The documents. */
    private static Map<URL, Document> documents = new HashMap<URL, Document>();
    /** The U p_ prefix. */
    private static String UP_PREFIX = "__UP_";
    /** The U p_ sufix. */
    private static String UP_SUFIX = "__";
    /** The MS g_ prefix. */
    private static String MSG_PREFIX = "__MSG_";
    /** The MS g_ sufix. */
    private static String MSG_SUFIX = "__";
    /** The url. */
    private URL url;
    /** The module. */
    private Document module;
    /** The module prefs. */
    private Element modulePrefs;
    private Map<Locale, String> locales = new HashMap<Locale, String>();

    /**
     * Instantiates a new gadget.
     * 
     * @param url the url
     */
    public Gadget(URL url)
    {
        this.url = url;
        module = getDocument(url);

        if (module == null)
        {
            throw new IllegalArgumentException("The gadget xml was not found");
        }
        else
        {
            try
            {
                modulePrefs = (Element) XPath.newInstance("/Module/ModulePrefs").selectSingleNode(module);


                /*List<Element> nodes = XPath.newInstance("/Module/ModulePrefs/Locale").selectNodes(module);
                for (Element locale : nodes)
                {
                String language = locale.getAttributeValue("lang");
                String country = locale.getAttributeValue("country");
                String message = locale.getAttributeValue("messages");
                Locale localeToAdd = null;
                if (language != null)
                {
                if (country == null)
                {
                localeToAdd = new Locale(language);
                }
                else
                {
                localeToAdd = new Locale(language, country);
                }
                locales.put(localeToAdd, message);
                }
                }*/

                if (modulePrefs == null)
                {
                    throw new IllegalArgumentException("The tag ModulePrefs was not found");
                }
            }
            catch (JDOMException e)
            {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * Gets the values.
     * 
     * @param name the name
     * @param locale the locale
     * @return the values
     */
    public Map<String, String> getValues(String name, Locale locale)
    {
        Map<String, String> getValues = new HashMap<String, String>();
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']/EnumValue");
            List listEnumValues = xpath.selectNodes(module);
            for (int j = 0; j < listEnumValues.size(); j++)
            {
                Element eEnumValue = (Element) listEnumValues.get(j);
                String value = eEnumValue.getAttributeValue("value");
                String displayValue = eEnumValue.getAttributeValue("display_value");
                displayValue = getTextFromLanguage(displayValue, locale, null);
                getValues.put(value, displayValue);
            }
        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return getValues;
    }

    /**
     * Gets the default value.
     * 
     * @param name the name
     * @param locale the locale
     * @return the default value
     */
    public String getDefaultValue(String name, Locale locale)
    {
        String getDefaultValue = "";
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = (Element) xpath.selectSingleNode(module);
            if (eUserPref.getAttributeValue(DEFAULT_VALUE) != null)
            {
                String defaultValue = eUserPref.getAttributeValue(DEFAULT_VALUE);
                defaultValue = getTextFromLanguage(defaultValue, locale, null);
                getDefaultValue = defaultValue;
            }
        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return getDefaultValue;
    }

    /**
     * Gets the height.
     * 
     * @return the height
     */
    public String getHeight()
    {
        String getHeight = "320";
        if (this.modulePrefs.getAttributeValue("height") != null)
        {
            getHeight = this.modulePrefs.getAttributeValue("height");
        }

        return getHeight;
    }

    /**
     * Gets the width.
     * 
     * @return the width
     */
    public String getWidth()
    {
        String getWidth = "320";
        if (this.modulePrefs.getAttributeValue("width") != null)
        {
            getWidth = this.modulePrefs.getAttributeValue("width");
        }

        return getWidth;
    }

    /**
     * Checks if is enum.
     * 
     * @param name the name
     * @return true, if is enum
     */
    public boolean isEnum(String name)
    {
        boolean isEnum = false;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = (Element) xpath.selectSingleNode(module);
            if (eUserPref.getAttributeValue("datatype") != null && eUserPref.getAttributeValue("datatype").equals("enum"))
            {
                isEnum = true;
            }
        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return isEnum;
    }

    /**
     * Checks if is required.
     * 
     * @param name the name
     * @return true, if is required
     */
    public boolean isRequired(String name)
    {
        boolean isRequired = false;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = (Element) xpath.selectSingleNode(module);
            if (eUserPref.getAttributeValue("required") != null && eUserPref.getAttributeValue("required").equals("true"))
            {
                isRequired = true;
            }
        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return isRequired;
    }

    /**
     * Gets the display name.
     * 
     * @param name the name
     * @param locale the locale
     * @return the display name
     */
    public String getDisplayName(String name, Locale locale)
    {
        String getDisplayName = null;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = (Element) xpath.selectSingleNode(module);
            getDisplayName = eUserPref.getAttributeValue("display_name");
            if (getDisplayName == null)
            {
                getDisplayName = eUserPref.getAttributeValue("name");
            }
            getDisplayName = getTextFromLanguage(getDisplayName, locale, null);
        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return getDisplayName;
    }

    /**
     * Gets the data type.
     * 
     * @param name the name
     * @return the data type
     */
    public String getDataType(String name)
    {
        String getDataType = null;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = (Element) xpath.selectSingleNode(module);
            if (eUserPref != null)
            {
                getDataType = eUserPref.getAttributeValue("datatype");
            }

        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return getDataType;
    }

    /**
     * Gets the parameter names.
     * 
     * @return the parameter names
     */
    public Set<String> getParameterNames()
    {
        HashSet<String> getParameterNames = new HashSet<String>();
        try
        {
            XPath xpath = XPath.newInstance("/Module/UserPref");
            List elements = xpath.selectNodes(module);
            for (int i = 0; i < elements.size(); i++)
            {
                Element userPref = (Element) elements.get(i);
                String name = userPref.getAttributeValue("name");
                getParameterNames.add(name);
            }
        }
        catch (JDOMException jde)
        {
            log.error(jde);
            //AFUtils.log(jde);
        }
        return getParameterNames;
    }

    /**
     * Gets the uRL.
     * 
     * @return the uRL
     */
    public URL getURL()
    {
        return url;
    }

    /**
     * Gets the parameters.
     * 
     * @param resource the resource
     * @param localeUser the locale user
     * @return the parameters
     */
    public String getParameters(Resource resource, Locale localeUser)
    {
        StringBuilder buffer = new StringBuilder();
        Iterator<String> names = resource.getAttributeNames();
        while (names.hasNext())
        {
            String name = names.next();
            String value = resource.getAttribute(name);
            if (value != null && !name.equals("url"))
            {
                buffer.append("&").append(name).append("=").append(value);
            }
        }
        if (buffer.length() == 0)
        {
            setDefaultParameters(resource, localeUser);
            buffer.append(this.getParameters(resource, localeUser));
        }
        return buffer.toString();
    }

    public Map<String, String> getParametersMap(Resource resource, Locale localeUser)
    {
        Map<String, String> parameters = new HashMap<String, String>();
        Iterator<String> names = resource.getAttributeNames();
        while (names.hasNext())
        {
            String name = names.next();
            String value = resource.getAttribute(name);
            if (value != null && !name.equals("url"))
            {
                parameters.put(name, value);
            }
        }

        return parameters;
    }

    /**
     * Sets the default parameters.
     * 
     * @param resource the resource
     * @param locale the locale
     */
    private void setDefaultParameters(Resource resource, Locale locale)
    {
        try
        {
            try
            {
                resource.setAttribute(TITLE_ATTRIBUTE, this.getTitle(locale));
            }
            catch (Exception e)
            {
                resource.setAttribute(TITLE_ATTRIBUTE, "");
            }
            resource.setAttribute("w", this.getWidth());
            resource.setAttribute("h", this.getHeight());
            resource.setAttribute("lang", locale.getLanguage());
            resource.setAttribute("country", "ALL");
            Set<String> names = this.getParameterNames();
            for (String name : names)
            {
                name = "up_" + name;
                String value = "";
                if (this.getDataType(name) != null)
                {
                    String defaultValue = getDefaultValue(name, locale);
                    value = defaultValue;
                }
                resource.setAttribute(name, value);
            }
            resource.updateAttributesToDB();
        }
        catch (Exception e)
        {
            log.error(e);
            //AFUtils.log(e);
        }

    }

    private Document getAllLocale()
    {
        Document getLocale = null;

        try
        {
            XPath xpath = XPath.newInstance("/Module/ModulePrefs/Locale[@lang='all']");
            Element eLanguage = (Element) xpath.selectSingleNode(module);
            if (eLanguage != null)
            {
                if (eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE) != null)
                {
                    URI base = url.toURI();
                    URI relative = new URI(eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE));
                    URI urlDocumentLanguage = base.resolve(relative);
                    Document docLanguage = getDocument(urlDocumentLanguage.toURL());
                    if (docLanguage != null)
                    {
                        getLocale = docLanguage;
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
            //AFUtils.log(e);
        }
        if (getLocale == null)
        {
            getLocale = getDefaultLanguage();
        }
        return getLocale;

    }

    /**
     * Gets the locale.
     * 
     * @param locale the locale
     * @return the locale
     */
    public Document getLocale(Locale locale)
    {
        Document getLocale = null;
        String language = locale.getLanguage();
        try
        {
            XPath xpath = XPath.newInstance("/Module/ModulePrefs/Locale[@lang='" + language + "']");
            Element eLanguage = (Element) xpath.selectSingleNode(module);
            if (eLanguage != null)
            {
                if (eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE) != null)
                {
                    URI base = url.toURI();
                    URI relative = new URI(eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE));
                    URI urlDocumentLanguage = base.resolve(relative);
                    Document docLanguage = getDocument(urlDocumentLanguage.toURL());
                    if (docLanguage != null)
                    {
                        getLocale = docLanguage;
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
            //AFUtils.log(e);
        }
        if (getLocale == null)
        {
            getLocale = getDefaultLanguage();
        }
        return getLocale;

    }

    /**
     * Gets the default language.
     * 
     * @return the default language
     */
    private Document getDefaultLanguage()
    {
        Document getDefaultLanguage = null;
        try
        {
            XPath xpath = XPath.newInstance(PATH_FIRST_LOCALE);
            Element eLanguage = (Element) xpath.selectSingleNode(module);
            if (eLanguage != null && eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE) != null)
            {
                URI base = url.toURI();
                URI relative = new URI(eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE));
                URI url_Language = base.resolve(relative);
                Document docLanguage = getDocument(url_Language.toURL());
                getDefaultLanguage = docLanguage;
            }

        }
        catch (Exception e)
        {
            log.error(e);
            //AFUtils.log(e);
        }
        return getDefaultLanguage;
    }

    /**
     * Gets the name.
     * 
     * @param value the value
     * @return the name
     */
    private String getName(String value)
    {
        int pos = value.indexOf(UP_PREFIX);
        if (pos != -1)
        {
            value = value.substring(pos + UP_PREFIX.length());
        }
        pos = value.indexOf(UP_SUFIX);
        if (pos != -1)
        {
            value = value.substring(0, pos);
        }
        return value;
    }

    /**
     * Gets the value from user pref.
     * 
     * @param bundleName the bundle name
     * @param locale the locale
     * @return the value from user pref
     */
    private String getDefaultValueFromUserPref(String bundleName, Locale locale)
    {
        String nameUserpref = getName(bundleName);
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + nameUserpref + "']");
            Element eUserPref = (Element) xpath.selectSingleNode(module);
            if (eUserPref != null && eUserPref.getAttributeValue(DEFAULT_VALUE) != null)
            {
                String defaultValue = eUserPref.getAttributeValue(DEFAULT_VALUE);
                //defaultValue = getTextFromLanguage(defaultValue, locale);
                bundleName = bundleName.replaceAll(UP_PREFIX + nameUserpref + UP_SUFIX, defaultValue);
            }
        }
        catch (JDOMException jdome)
        {
            log.error(jdome);
            //AFUtils.log(jdome);
        }
        return bundleName;
    }

    /**
     * Gets the document.
     * 
     * @param url the url
     * @return the document
     */
    public static Document getDocument(URL url)
    {
        Document getDocument = null;
        try
        {
            getDocument = documents.get(url);
            if (getDocument == null)
            {
                getDocument = builder.build(url);
                documents.put(url, getDocument);
            }
        }
        catch (JDOMException jdome)
        {
            //AFUtils.log(jdome);
            log.error(jdome);
        }
        catch (IOException ioe)
        {
            log.error(ioe);
            //AFUtils.log(ioe);
        }
        return getDocument;
    }

    private String getMessageTag(String data)
    {
        int pos = data.indexOf(MSG_PREFIX);
        if (pos != -1)
        {
            int pos2 = data.indexOf(MSG_SUFIX, pos + MSG_PREFIX.length());
            if (pos2 != -1)
            {
                return data.substring(pos + MSG_PREFIX.length(), pos2);
            }
        }
        return null;
    }

    private String getParameterTag(String data)
    {
        int pos = data.indexOf(UP_PREFIX);
        if (pos != -1)
        {
            int pos2 = data.indexOf(UP_SUFIX, pos + UP_PREFIX.length());
            if (pos2 != -1)
            {
                return data.substring(pos + UP_PREFIX.length(), pos2);
            }
        }
        return null;
    }

    /**
     * Gets the text from language.
     * 
     * @param bundleName the bundle name
     * @param locale the locale
     * @return the text from language
     */
    public String getTextFromLanguage(String bundleName, Locale locale, Map<String, String> parameters)
    {
        String getTextFromLanguage = bundleName;
        if (bundleName != null)
        {
            if (bundleName.indexOf(MSG_LOCALE) != -1)
            {
                String userlang = locale.getLanguage();
                String lang = userlang;
                String country = "ALL";
                int pos = userlang.indexOf("-");
                if (pos != -1)
                {
                    lang = userlang.substring(0, pos);
                    country = userlang.substring(pos + 1);
                }
                bundleName = bundleName.replaceAll(MSG_LOCALE, lang + "_" + country);
                getTextFromLanguage = bundleName;
            }
            int pos = bundleName.indexOf(UP_PREFIX);
            while (pos != -1)
            {
                if (parameters == null || parameters.isEmpty())
                {
                    bundleName = getDefaultValueFromUserPref(bundleName, locale);
                }
                else
                {
                    String tag = getParameterTag(bundleName);
                    String value = parameters.get(tag);
                    if (value == null)
                    {
                        value = "";
                    }
                    bundleName = bundleName.replace(UP_PREFIX + tag + UP_SUFIX, value);
                }
                pos = bundleName.indexOf(UP_PREFIX);
            }
            try
            {
                while (bundleName.indexOf(MSG_PREFIX) != -1)
                {
                    String tag = getMessageTag(bundleName);
                    Document language = getLocale(locale);
                    if (language != null)
                    {
                        XPath xpath = XPath.newInstance("/messagebundle/msg[@name='" + tag + "']");
                        Element eMsg = (Element) xpath.selectSingleNode(language);
                        if (eMsg == null) // No existe
                        {
                            language = getAllLocale();
                            if (language != null)
                            {
                                eMsg = (Element) xpath.selectSingleNode(language);
                            }
                        }

                        if (eMsg != null && eMsg.getValue() != null && !eMsg.getValue().equals(""))
                        {
                            String value = eMsg.getValue();
                            bundleName = bundleName.replace(MSG_PREFIX + tag + MSG_SUFIX, value);
                        }
                        else
                        {
                            bundleName = bundleName.replace(MSG_PREFIX + tag + MSG_SUFIX, "");
                        }

                    }
                    else
                    {
                        bundleName = bundleName.replace(MSG_PREFIX + tag + MSG_SUFIX, "");
                    }
                }
                getTextFromLanguage = bundleName;
            }
            catch (Exception e)
            {
                log.error(e);
                //AFUtils.log(e);
            }
        }
        return getTextFromLanguage;

    }

    public String getHomeContent()
    {
        return getContentMode("home");
    }
    public String getCanvasContent()
    {
        return getContentMode("canvas");
    }

    public String getDefaultContent()
    {
        return getContentMode("default");
    }

    public String getPreviewtContent()
    {
        return getContentMode("preview");
    }

    public String getContentMode(String mode)
    {
        String content = "";
        try
        {
            XPath xpath = XPath.newInstance("/Module/Content");
            List<Element> nodes = xpath.selectNodes(module);
            for (Element element : nodes)
            {
                String view = element.getAttributeValue("view");
                if (view != null && view.indexOf(mode) != -1)
                {
                    content = element.getText();
                }
            }

        }
        catch (Exception e)
        {
            log.error(e);
        }
        return content;
    }

    public boolean isURLContent()
    {
        try
        {
            XPath xpath = XPath.newInstance("/Module/Content");
            List<Element> elements = xpath.selectNodes(module);
            for (Element element : elements)
            {
                String type = element.getAttributeValue("type");

                if ("url".equalsIgnoreCase(type))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);

            return false;
        }
        return false;
    }

    public boolean isHTMLContent()
    {
        try
        {
            XPath xpath = XPath.newInstance("/Module/Content");
            List<Element> elements = xpath.selectNodes(module);
            for (Element element : elements)
            {
                String type = element.getAttributeValue("type");

                if ("html".equalsIgnoreCase(type))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);

            return false;
        }
        return false;
    }

    public String getFirstHTMLContent()
    {        
        try
        {
            XPath xpath = XPath.newInstance("/Module/Content[0]");
            Element element = (Element)xpath.selectSingleNode(module);
            return element.getText();
        }
        catch(Exception e)
        {
            log.error(e);

        }
        return "";
    }
    public Set<String> getModes()
    {
        Set<String> modes = new HashSet<String>();
        try
        {
            XPath xpath = XPath.newInstance("/Module/Content");
            List<Element> elements = xpath.selectNodes(module);
            for (Element element : elements)
            {
                String view = element.getAttributeValue("view");
                if (view != null)
                {
                    StringTokenizer st = new StringTokenizer(view, ",");
                    while (st.hasMoreTokens())
                    {
                        String mode = st.nextToken();
                        modes.add(mode);
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return modes;
    }

    public int getHTMLCountContents()
    {
        
        try
        {
            XPath xpath = XPath.newInstance("/Module/Content[@type='html']");
            List<Element> elements = xpath.selectNodes(module);
            return elements.size();
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return 0;
    }

    public String getContent(String mode, Map<String, String> parameters)
    {
        String content="";
        try
        {
            if (isURLContent())
            {
                XPath xpath = XPath.newInstance("/Module/Content[@type='url']");
                Element econtent = (Element) xpath.selectSingleNode(module);
                String href = econtent.getAttributeValue("href");
                StringBuilder qstring = new StringBuilder();
                if (!parameters.isEmpty())
                {
                    qstring.append("?");
                }
                for (String key : parameters.keySet())
                {
                    if (key.startsWith("up_"))
                    {
                        String value = parameters.get(key);
                        if (key.startsWith("up_"))
                        {
                            key = key.substring("up_".length());
                        }
                        qstring.append(key);
                        qstring.append("=");
                        qstring.append(value);
                        qstring.append("&");
                    }
                }
                content = "<iframe src=\"" + href + qstring + "\">";
            }
            else if(isHTMLContent())
            {
                if(getHTMLCountContents()==1)
                {
                    content=getFirstHTMLContent();
                }
                else if(getHTMLCountContents()>1)
                {                    
                    if(mode==null) // no hay modo
                    {
                        content=getDefaultContent();
                        if("".equals(content))
                        {
                            content=getCanvasContent();
                        }
                    }
                    else
                    {
                        content=getContentMode(mode);
                    }
                }
                
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return content;
    }

    public String getContent(String mode, Locale locale, Resource resource)
    {
        Map<String, String> parameters = getParametersMap(resource, locale);
        String content = getContent(mode, parameters);
        content = parseContent(content, locale, null);
        return content;
    }

    public String parseContent(String content, Locale locale, Map<String, String> parameters)
    {
        int pos = content.indexOf(MSG_PREFIX);
        while (pos != -1)
        {
            int pos2 = content.indexOf(MSG_SUFIX, pos + MSG_PREFIX.length());
            if (pos2 != -1)
            {
                String tag = content.substring(pos, pos2 + MSG_SUFIX.length());
                String newText = getTextFromLanguage(tag, locale, parameters);
                content = content.replace(tag, newText);
            }
            pos = content.indexOf(MSG_PREFIX);
        }
        return content;
    }

    /**
     * Gets the description.
     * 
     * @param locale the locale
     * @return the description
     * @throws Exception the exception
     */
    public String getDescription(Locale locale) throws Exception
    {
        String getDescription = "Sin Descripción";
        if (this.modulePrefs.getAttributeValue("description") != null)
        {
            getDescription = this.modulePrefs.getAttributeValue("description");
            getDescription = getTextFromLanguage(getDescription, locale, null);
        }
        return getDescription;
    }

    /**
     * Gets the src image.
     * 
     * @param locale the locale
     * @return the src image
     * @throws Exception the exception
     */
    public String getSrcImage(Locale locale) throws Exception
    {
        String getSrcImage = null;
        if (modulePrefs.getAttributeValue("thumbnail") != null)
        {
            String src = modulePrefs.getAttributeValue("thumbnail");
            src = getTextFromLanguage(src, locale, null);
            if (!src.startsWith("http://") && src.startsWith("www"))
            {
                src = "http://" + src;
            }
            try
            {
                URI urlimage = new URI(src);
                getSrcImage = url.toURI().resolve(urlimage).toString();
            }
            catch(Exception e)
            {
                log.debug(e);
                return src;
            }
        }
        else
        {
            if (modulePrefs.getAttributeValue("screenshot") != null)
            {
                String src = modulePrefs.getAttributeValue("screenshot");
                src = getTextFromLanguage(src, locale, null);
                if (!src.startsWith("http://") && src.startsWith("www"))
                {
                    src = "http://" + src;
                }
                URI urlimage = new URI(src);
                getSrcImage = url.toURI().resolve(urlimage).toString();
            }
        }

        return getSrcImage;
    }

    /**
     * Gets the directory title.
     * 
     * @param locale the locale
     * @return the directory title
     * @throws Exception the exception
     */
    public String getDirectoryTitle(Locale locale) throws Exception
    {
        String getDirectoryTitle = null;
        if (modulePrefs.getAttributeValue("directory_title") != null)
        {
            getDirectoryTitle = getTextFromLanguage(modulePrefs.getAttributeValue("directory_title"), locale, null);
        }
        if (getDirectoryTitle == null)
        {
            getDirectoryTitle = getTitle(locale);
        }
        return getDirectoryTitle;

    }

    /**
     * Gets the module.
     * 
     * @return the module
     */
    public Document getModule()
    {
        return module;
    }

    /**
     * Gets the languages.
     * 
     * @return the languages
     */
    public Set<String> getLanguages()
    {
        HashSet<String> getLanguages = new HashSet<String>();
        try
        {
            XPath xpath = XPath.newInstance("/Module/ModulePrefs/Locale");
            List locales = xpath.selectNodes(module);
            for (int i = 0; i < locales.size(); i++)
            {
                Element eLocale = (Element) locales.get(i);
                String lang = eLocale.getAttributeValue("lang");
                if (lang != null && !lang.equals(""))
                {
                    getLanguages.add(lang);
                }
            }
        }
        catch (Exception ex)
        {
            log.error(ex);
            //AFUtils.log(ex);
        }
        return getLanguages;
    }

    /**
     * Gets the author_ location.
     * 
     * @return the author_ location
     */
    public String getAuthor_Location()
    {
        String getAuthor_Location = "";
        if (modulePrefs.getAttributeValue("author_location") != null)
        {
            getAuthor_Location = modulePrefs.getAttributeValue("author_location");
        }
        return getAuthor_Location;
    }

    /**
     * Gets the title.
     * 
     * @param locale the locale
     * @return the title
     * @throws Exception the exception
     */
    public String getTitle(Locale locale) throws Exception
    {
        String getTitle = "Sin título";

        if (modulePrefs.getAttributeValue(TITLE_ATTRIBUTE) != null)
        {
            getTitle = modulePrefs.getAttributeValue(TITLE_ATTRIBUTE);
            getTitle = getTextFromLanguage(getTitle, locale, null);
        }
        return getTitle;
    }

    /**
     * Gets the author.
     * 
     * @return the author
     */
    public String getAuthor()
    {
        String getAuthor = "Sin autor";
        if (modulePrefs.getAttributeValue("author") != null)
        {
            getAuthor = modulePrefs.getAttributeValue("author");
        }

        return getAuthor;
    }
}
