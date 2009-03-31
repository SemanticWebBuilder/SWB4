package org.semanticwb.portal.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;

/**
 *
 * @author victor.lorenzana
 */
public class Gadget
{
    public static Logger log = SWBUtils.getLogger(Gadget.class);

    private static final String DEFAULT_VALUE = "default_value";
    private static final String MESSAGES_ATTRIBUTE = "messages";
    private static final String MSG_LOCALE = "__MSG_locale__";
    private static final String PATH_FIRST_LOCALE = "/Module/ModulePrefs/Locale[1]";
    private static final String PATH_USER_PREFERENCES = "/Module/UserPref[@name=\'";
    private static final String TITLE_ATTRIBUTE = "title";
    private static SAXBuilder builder = new SAXBuilder();
    private static Hashtable<URL, Document> documents = new Hashtable<URL, Document>();
    private static String UP_PREFIX = "__UP_";
    private static String UP_SUFIX = "__";
    private static String MSG_PREFIX = "__MSG_";
    private static String MSG_SUFIX = "__";
    private URL url;
    private Document module;
    private Element modulePrefs;

    public Gadget(URL url)
    {
        this.url = url;
        module = getDocument(url);
        if ( module == null )
        {
            throw new IllegalArgumentException("The gadget xml was not found");
        }
        else
        {
            try
            {
                modulePrefs = ( Element ) XPath.newInstance("/Module/ModulePrefs").selectSingleNode(module);
                if ( modulePrefs == null )
                {
                    throw new IllegalArgumentException("The tag ModulePrefs was not found");
                }
            }
            catch ( JDOMException e )
            {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public Hashtable<String, String> getValues(String name, Locale locale)
    {
        Hashtable<String, String> getValues = new Hashtable<String, String>();
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']/EnumValue");
            List listEnumValues = xpath.selectNodes(module);
            for ( int j = 0; j < listEnumValues.size(); j++ )
            {
                Element eEnumValue = ( Element ) listEnumValues.get(j);
                String value = eEnumValue.getAttributeValue("value");
                String displayValue = eEnumValue.getAttributeValue("display_value");
                displayValue = getTextFromLanguage(displayValue, locale);
                getValues.put(value, displayValue);
            }
        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return getValues;
    }

    public String getDefaultValue(String name, Locale locale)
    {
        String getDefaultValue = "";
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = ( Element ) xpath.selectSingleNode(module);
            if ( eUserPref.getAttributeValue(DEFAULT_VALUE) != null )
            {
                String defaultValue = eUserPref.getAttributeValue(DEFAULT_VALUE);
                defaultValue = getTextFromLanguage(defaultValue, locale);
                getDefaultValue = defaultValue;
            }
        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return getDefaultValue;
    }

    public String getHeight()
    {
        String getHeight = "320";
        if ( this.modulePrefs.getAttributeValue("height") != null )
        {
            getHeight = this.modulePrefs.getAttributeValue("height");
        }

        return getHeight;
    }

    public String getWidth()
    {
        String getWidth = "320";
        if ( this.modulePrefs.getAttributeValue("width") != null )
        {
            getWidth = this.modulePrefs.getAttributeValue("width");
        }

        return getWidth;
    }

    public boolean isEnum(String name)
    {
        boolean isEnum = false;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = ( Element ) xpath.selectSingleNode(module);
            if ( eUserPref.getAttributeValue("datatype") != null && eUserPref.getAttributeValue("datatype").equals("enum") )
            {
                isEnum = true;
            }
        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return isEnum;
    }

    public boolean isRequired(String name)
    {
        boolean isRequired = false;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = ( Element ) xpath.selectSingleNode(module);
            if ( eUserPref.getAttributeValue("required") != null && eUserPref.getAttributeValue("required").equals("true") )
            {
                isRequired = true;
            }
        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return isRequired;
    }

    public String getDisplayName(String name, Locale locale)
    {
        String getDisplayName = null;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = ( Element ) xpath.selectSingleNode(module);
            getDisplayName = eUserPref.getAttributeValue("display_name");
            if ( getDisplayName == null )
            {
                getDisplayName = eUserPref.getAttributeValue("name");
            }
            getDisplayName = getTextFromLanguage(getDisplayName, locale);
        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return getDisplayName;
    }

    public String getDataType(String name)
    {
        String getDataType = null;
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + name + "']");
            Element eUserPref = ( Element ) xpath.selectSingleNode(module);
            if ( eUserPref != null )
            {
                getDataType = eUserPref.getAttributeValue("datatype");
            }

        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return getDataType;
    }

    public Set<String> getParameterNames()
    {
        HashSet<String> getParameterNames = new HashSet<String>();
        try
        {
            XPath xpath = XPath.newInstance("/Module/UserPref");
            List elements = xpath.selectNodes(module);
            for ( int i = 0; i < elements.size(); i++ )
            {
                Element userPref = ( Element ) elements.get(i);
                String name = userPref.getAttributeValue("name");
                getParameterNames.add(name);
            }
        }
        catch ( JDOMException jde )
        {
            log.error(jde);
        }
        return getParameterNames;
    }

    public URL getURL()
    {
        return url;
    }

    public String getParameters(Resource resource, Locale localeUser)
    {
        StringBuffer buffer = new StringBuffer();
        Iterator names = resource.getAttributeNames();
        while (names.hasNext())
        {
            String name = ( String ) names.next();
            String value = resource.getAttribute(name);
            if ( value != null && !name.equals("url") )
            {
                buffer.append("&" + name + "=" + value);
            }
        }
        if ( buffer.length() == 0 )
        {
            setDefaultParameters(resource, localeUser);
            buffer.append(this.getParameters(resource, localeUser));
        }
        return buffer.toString();
    }

    private void setDefaultParameters(Resource resource, Locale locale)
    {
        try
        {
            try
            {
                resource.setAttribute(TITLE_ATTRIBUTE, this.getTitle(locale));
            }
            catch ( Exception e )
            {
                resource.setAttribute(TITLE_ATTRIBUTE, "");
            }
            resource.setAttribute("w", this.getWidth());
            resource.setAttribute("h", this.getHeight());
            resource.setAttribute("lang", locale.getLanguage());
            resource.setAttribute("country", "ALL");
            Set<String> names = this.getParameterNames();
            for ( String name : names )
            {
                name = "up_" + name;
                String value = "";
                if ( this.getDataType(name) != null )
                {
                    String defaultValue = getDefaultValue(name, locale);
                    value = defaultValue;
                }
                resource.setAttribute(name, value);
            }
            //resource.updateAttributesToDB();
        }
        catch ( Exception e )
        {
            log.error(e);
        }

    }

    private Document getLocale(Locale locale)
    {
        Document getLocale = null;
        String language = locale.getLanguage();
        try
        {
            XPath xpath = XPath.newInstance("/Module/ModulePrefs/Locale[@lang='" + language + "']");
            Element eLanguage = ( Element ) xpath.selectSingleNode(module);
            if ( eLanguage != null )
            {
                if ( eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE) != null )
                {
                    URI base = url.toURI();
                    URI relative = new URI(eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE));
                    URI urlDocumentLanguage = base.resolve(relative);
                    Document docLanguage = getDocument(urlDocumentLanguage.toURL());
                    if ( docLanguage != null )
                    {
                        getLocale = docLanguage;
                    }
                }
            }
        }
        catch ( Exception e )
        {
            log.error(e);
        }
        if ( getLocale == null )
        {
            getLocale = getDefaultLanguage();
        }
        return getLocale;

    }

    private Document getDefaultLanguage()
    {
        Document getDefaultLanguage = null;
        try
        {
            XPath xpath = XPath.newInstance(PATH_FIRST_LOCALE);
            Element eLanguage = ( Element ) xpath.selectSingleNode(module);
            if ( eLanguage != null && eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE) != null )
            {
                URI base = url.toURI();
                URI relative = new URI(eLanguage.getAttributeValue(MESSAGES_ATTRIBUTE));
                URI url_Language = base.resolve(relative);
                Document docLanguage = getDocument(url_Language.toURL());
                getDefaultLanguage = docLanguage;
            }

        }
        catch ( Exception e )
        {
            log.error(e);
        }
        return getDefaultLanguage;
    }

    private String getName(String value)
    {
        int pos = value.indexOf(UP_PREFIX);
        if ( pos != -1 )
        {
            value = value.substring(pos + UP_PREFIX.length());
        }
        pos = value.indexOf(UP_SUFIX);
        if ( pos != -1 )
        {
            value = value.substring(0, pos);
        }
        return value;
    }

    private String getValueFromUserPref(String bundleName, Locale locale)
    {
        String nameUserpref = getName(bundleName);
        try
        {
            XPath xpath = XPath.newInstance(PATH_USER_PREFERENCES + nameUserpref + "']");
            Element eUserPref = ( Element ) xpath.selectSingleNode(module);
            if ( eUserPref != null && eUserPref.getAttributeValue(DEFAULT_VALUE) != null )
            {
                String defaultValue = eUserPref.getAttributeValue(DEFAULT_VALUE);
                defaultValue = getTextFromLanguage(defaultValue, locale);
                bundleName = bundleName.replaceAll(UP_PREFIX + nameUserpref + UP_SUFIX, defaultValue);
            }
        }
        catch ( JDOMException jdome )
        {
            log.error(jdome);
        }
        return bundleName;
    }

    public static Document getDocument(URL url)
    {
        Document getDocument = null;
        try
        {
            getDocument = documents.get(url);
            if ( getDocument == null )
            {
                getDocument = builder.build(url);
                documents.put(url, getDocument);
            }
        }
        catch ( JDOMException jdome )
        {
        //AFUtils.log(jdome);
        }
        catch ( IOException ioe )
        {
            log.error(ioe);
        }
        return getDocument;
    }

    private String getTextFromLanguage(String bundleName, Locale locale)
    {
        String getTextFromLanguage = bundleName;
        if ( bundleName != null )
        {
            if ( bundleName.indexOf(MSG_LOCALE) != -1 )
            {
                String userlang = locale.getLanguage();
                String lang = userlang;
                String country = "ALL";
                int pos = userlang.indexOf("-");
                if ( pos != -1 )
                {
                    lang = userlang.substring(0, pos);
                    country = userlang.substring(pos + 1);
                }
                bundleName = bundleName.replaceAll(MSG_LOCALE, lang + "_" + country);
                getTextFromLanguage = bundleName;
            }
            int pos = bundleName.indexOf(UP_SUFIX);
            if ( pos != -1 )
            {
                bundleName = getValueFromUserPref(bundleName, locale);
            }
            try
            {
                if ( bundleName.startsWith(MSG_PREFIX) )
                {
                    XMLOutputter xMLOutputter = new XMLOutputter();
                    Document language = getLocale(locale);
                    if ( language != null )
                    {
                        //xMLOutputter.output(language, System.out);
                        String msgName = bundleName;
                        if ( msgName.startsWith(MSG_PREFIX) )
                        {
                            msgName = msgName.substring(MSG_PREFIX.length());
                        }
                        if ( msgName.endsWith(MSG_SUFIX) )
                        {
                            msgName = msgName.substring(0, msgName.length() - MSG_SUFIX.length());
                        }
                        XPath xpath = XPath.newInstance("/messagebundle/msg[@name='" + msgName + "']");
                        Element eMsg = ( Element ) xpath.selectSingleNode(language);
                        if ( eMsg != null && eMsg.getValue() != null && !eMsg.getValue().equals("") )
                        {
                            getTextFromLanguage = eMsg.getValue();
                        }
                    }
                }
            }
            catch ( Exception e )
            {
                log.error(e);
            }
        }
        return getTextFromLanguage;

    }

    public String getDescription(Locale locale) throws Exception
    {
        String getDescription = "Sin Descripci�n";
        if ( this.modulePrefs.getAttributeValue("description") != null )
        {
            getDescription = this.modulePrefs.getAttributeValue("description");
            getDescription = getTextFromLanguage(getDescription, locale);
        }
        return getDescription;
    }

    public String getSrcImage(Locale locale) throws Exception
    {
        String getSrcImage = null;
        if ( modulePrefs.getAttributeValue("thumbnail") != null )
        {
            String src = modulePrefs.getAttributeValue("thumbnail");
            src = getTextFromLanguage(src, locale);
            if ( !src.startsWith("http://") && src.startsWith("www") )
            {
                src = "http://" + src;
            }
            URI urlimage = new URI(src);
            getSrcImage = url.toURI().resolve(urlimage).toString();
        }
        else
        {
            if ( modulePrefs.getAttributeValue("screenshot") != null )
            {
                String src = modulePrefs.getAttributeValue("screenshot");
                src = getTextFromLanguage(src, locale);
                if ( !src.startsWith("http://") && src.startsWith("www") )
                {
                    src = "http://" + src;
                }
                URI urlimage = new URI(src);
                getSrcImage = url.toURI().resolve(urlimage).toString();
            }
        }

        return getSrcImage;
    }

    public String getDirectoryTitle(Locale locale) throws Exception
    {
        String getDirectoryTitle = null;
        if ( modulePrefs.getAttributeValue("directory_title") != null )
        {
            getDirectoryTitle = getTextFromLanguage(modulePrefs.getAttributeValue("directory_title"), locale);
        }
        if ( getDirectoryTitle == null )
        {
            getDirectoryTitle = getTitle(locale);
        }
        return getDirectoryTitle;

    }

    public Document getModule()
    {
        return module;
    }

    public Set<String> getLanguages()
    {
        HashSet<String> getLanguages = new HashSet<String>();
        try
        {
            XPath xpath = XPath.newInstance("/Module/ModulePrefs/Locale");
            List locales = xpath.selectNodes(module);
            for ( int i = 0; i < locales.size(); i++ )
            {
                Element eLocale = ( Element ) locales.get(i);
                String lang = eLocale.getAttributeValue("lang");
                if ( lang != null && !lang.equals("") )
                {
                    getLanguages.add(lang);
                }
            }
        }
        catch ( Exception ex )
        {
            log.error(ex);
        }
        return getLanguages;
    }

    public String getAuthor_Location()
    {
        String getAuthor_Location = "";
        if ( modulePrefs.getAttributeValue("author_location") != null )
        {
            getAuthor_Location = modulePrefs.getAttributeValue("author_location");
        }
        return getAuthor_Location;
    }

    public String getTitle(Locale locale) throws Exception
    {
        String getTitle = "Sin t�tulo";

        if ( modulePrefs.getAttributeValue(TITLE_ATTRIBUTE) != null )
        {
            getTitle = modulePrefs.getAttributeValue(TITLE_ATTRIBUTE);
            getTitle = getTextFromLanguage(getTitle, locale);
        }
        return getTitle;
    }

    public String getAuthor()
    {
        String getAuthor = "Sin autor";
        if ( modulePrefs.getAttributeValue("author") != null )
        {
            getAuthor = modulePrefs.getAttributeValue("author");
        }

        return getAuthor;
    }
}
