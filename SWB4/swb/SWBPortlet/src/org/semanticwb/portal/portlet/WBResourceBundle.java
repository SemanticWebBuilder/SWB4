/*
 * WBResourceBundle.java
 *
 * Created on 7 de septiembre de 2005, 03:40 PM
 */

package org.semanticwb.portal.portlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBResourceBundle extends ResourceBundle
{
    private HashMap data;    
    ResourceBundle bundle;
    
    public WBResourceBundle(ResourceBundle bundle, ResourceBundle defaults)
    {
        data = new HashMap();
        importData(defaults);
        importData(bundle);
    }
    
    public WBResourceBundle(String defaultTitle, String defaultShortTitle, String defaultKeyWords)
    {
        data = new HashMap();
        if(defaultTitle==null)defaultTitle="";
        if(defaultShortTitle==null)defaultShortTitle="";
        if(defaultKeyWords==null)defaultKeyWords="";
        data.put("javax.portlet.title", defaultTitle);
        data.put("javax.portlet.short-title", defaultShortTitle);
        data.put("javax.portlet.keywords", defaultKeyWords);
    }


    private void importData(ResourceBundle bundle) {
        if (bundle != null) {
            for (Enumeration enumerator= bundle.getKeys(); enumerator.hasMoreElements();) {
                String key   = (String)enumerator.nextElement();
                Object value = bundle.getObject(key);
                data.put(key, value);
            }
        }
    }
    
    /**
     * Gets an object for the given key from this resource bundle.
     * Returns null if this resource bundle does not contain an
     * object for the given key.
     *
     * @param key the key for the desired object
     * @exception NullPointerException if <code>key</code> is <code>null</code>
     * @return the object for the given key, or null
     */
    protected Object handleGetObject(String key) {
        //System.out.println(key+":"+data.get(key));
        return data.get(key);
    }

    

    /**
     * Returns an enumeration of the keys.
     *
     */    
    public Enumeration getKeys() {
        return new Vector(data.keySet()).elements();
    }    
    
}
