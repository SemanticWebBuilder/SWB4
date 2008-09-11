/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Vector;
//import com.infotec.wb.core.WBLoader;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericXformsResource;

/** Objeto que maneja el lenguaje en la api de administración de recursos para la utilización adecuada de xml's
 * <p>
 * Object that handles the language in the resources administration api for correct use of xml's
 * @author Infotec
 * @version 1.1
 */

public class XmlBundle 
{
    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
    
    private static Hashtable bundles = new Hashtable();
    
    public XmlBundle(String className, String baseName) 
    {
        init(className, baseName);
    }

    public void init(String className, String baseName)
    {
        ArrayList languages=SWBPortal.getAppLanguages();
        if(!languages.isEmpty())
        {
            for(int i=0; i < languages.size() ;i++)
            {
                findBundle(className, baseName, new Locale((String)languages.get(i)));
            }
        }
        findBundle(className, baseName, new Locale("", ""));
    }
    
    public static String getBundle(String baseName, Locale locale)
    {
        String ret=getBundleImpl(baseName, locale);
        return (null!=ret ? ret : getDefaultBundle(baseName));
    }    

    public static String getDefaultBundle(String baseName)
    {
        String ret=getBundleImpl(baseName, new Locale("", ""));
        if(ret==null)  {
            log.error("Can't find none xmlbundle for base name " + baseName);
        }
        return ret;
    }
    
    public static String getBundleImpl(String baseName, Locale locale)
    {
        final Vector names = calculateBundleNames(baseName, locale);
        if (names.size()>0) 
        {
            for (int i = 0; i < names.size(); i++) 
            {
                String bundleName = (String)names.elementAt(i);
                if(bundles.containsKey(bundleName)) 
                {
                    PropertyXmlBundle lookup=(PropertyXmlBundle)bundles.get(bundleName);
                    if(lookup!=null)  return lookup.getXml();
                }
            }
        }
        return null;
    }    
    
    public static void findBundle(String className, String baseName)
    {
        findBundleImpl(baseName, Locale.getDefault(), getLoader(className));
    }

    public static void findBundle(String className, String baseName, Locale locale)
    {
        findBundleImpl(baseName, locale, getLoader(className));
    }

    public static void findBundle(String baseName, Locale locale, ClassLoader loader)
    {
        if (loader == null) throw new NullPointerException();
        findBundleImpl(baseName, locale, loader);
    }

    private static void findBundleImpl(String baseName, Locale locale, ClassLoader loader)
    {
        try 
        {
            final Vector names = calculateBundleNames(baseName, locale);
            if (names.size()>0) 
            {
                for (int i = 0; i < names.size(); i++) 
                {
                    String bundleName = (String)names.elementAt(i);
                    PropertyXmlBundle lookup = loadBundle(loader, bundleName, baseName);
                    if (lookup != null) {
                        bundles.put(bundleName, lookup);
                    }
                }
            }
        } 
        catch (Exception e) { log.error("Error in XmlBundle while getting bundle", e); } 
    }

    private static Vector calculateBundleNames(String baseName, Locale locale) 
    {
        final Vector result = new Vector();
        final String language = locale.getLanguage();
        final int languageLength = language.length();
        final String country = locale.getCountry();
        final int countryLength = country.length();
        final String variant = locale.getVariant();
        final int variantLength = variant.length();

        if (languageLength + countryLength + variantLength == 0) 
        {
            result.addElement(baseName);
            return result;
        }
        final StringBuffer temp = new StringBuffer(baseName);
        temp.append('_');
        temp.append(language);
        if (languageLength > 0) 
        {
            result.addElement(temp.toString());
        }
        if (countryLength + variantLength == 0) 
        {
            return result;
        }
        temp.append('_');
        temp.append(country);
        if (countryLength > 0) 
        {
            result.addElement(temp.toString());
        }
        if (variantLength == 0) 
        {
            return result;
        }
        temp.append('_');
        temp.append(variant);
        result.addElement(temp.toString());
        return result;
    }

    private static PropertyXmlBundle loadBundle(final ClassLoader loader, String bundleName, String baseName)
    {
        final String resName = bundleName.replace('.', '/') + ".xml";
        InputStream stream = (InputStream)java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction() 
            {
                public Object run() 
                {
                    if (loader != null) {
                        return loader.getResourceAsStream(resName);
                    }
                    else {
                        return ClassLoader.getSystemResourceAsStream(resName);
                    }
                }
            }
        );
        
        if (stream != null) 
        {
            
            stream = new BufferedInputStream(stream);
            try 
            {
                String xml=SWBUtils.IO.readInputStream(stream);
                //if(WBAdmResourceUtils.getInstance().xmlVerifierDefault(xml)) 
                //{
                    PropertyXmlBundle bundle=new PropertyXmlBundle(xml);
                    bundle.setLocale(baseName, bundleName);
                    return bundle;
                //}
                //else
//                {
//                    AFUtils.log("XML is not valid in resource: "+bundleName,true);
//                    return null;
//                }
            } 
            catch (Exception e) {}
            finally 
            {
                try { stream.close(); } 
                catch (Exception e) {}
            }
        }
        return null;
    }

    private static ClassLoader getLoader(String className) 
    {
        //ClassLoader cl = WBLoader.getInstance().getClassLoader();
        //if (cl == null)  cl = ClassLoader.getSystemClassLoader();
        ClassLoader cl = SWBPortal.getResourceMgr().getResourceLoader(className);
        if (cl == null)  {
            cl = SWBPortal.class.getClassLoader();
        }
        return cl;
  
    }
}


class PropertyXmlBundle
{
    private Locale locale = null;
    private String xml;
    
    PropertyXmlBundle(String xml)
    {
        this.xml=xml;
    }

    String getXml()
    {
        return this.xml;
    }
    
    Locale getLocale() {
        return this.locale;
    }
    
    void setLocale(String baseName, String bundleName) 
    {
        if (baseName.length() == bundleName.length()) 
        {
            this.locale = new Locale("", "");
        }
        else if (baseName.length() < bundleName.length()) 
        {
            int pos = baseName.length();
            String temp = bundleName.substring(pos + 1);
            pos = temp.indexOf('_');
            if (pos == -1) 
            {
                this.locale = new Locale(temp, "", "");
                return;
            }
            String language = temp.substring(0, pos);
            temp = temp.substring(pos + 1);
            pos = temp.indexOf('_');
            if (pos == -1) 
            {
                this.locale = new Locale(language, temp, "");
                return;
            }
            String country = temp.substring(0, pos);
            temp = temp.substring(pos + 1);
            this.locale = new Locale(language, country, temp);
        } 
        else throw new IllegalArgumentException();
    }
}