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
package org.semanticwb.portal.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import javax.portlet.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The <CODE>PortletPreferences</CODE> interface allows the portlet to store
 * configuration data. It is not the
 * purpose of this interface to replace general purpose databases.
 * <p>
 * There are two different types of preferences:
 * <ul>
 * <li>modifiable preferences - these preferences can be changed by the
 *     portlet in any standard portlet mode (<code>EDIT, HELP, VIEW</code>).
 *     Per default every preference is modifiable.
 * <li>read-only preferences - these preferences cannot be changed by the
 *     portlet in any standard portlet mode, but may be changed by administrative modes.
 *     Preferences are read-only, if the are defined in the
 *     deployment descriptor with <code>read-only</code> set to <code>true</code>,
 *     or if the portlet container restricts write access.
 * </ul>
 * <p>
 * Changes are persisted when the <code>store</code> method is called. The <code>store</code> method
 * can only be invoked within the scope of a <code>processAction</code> call.
 * Changes that are not persisted are discarded when the
 * <code>processAction</code> or <code>render</code> method ends.
 */
public class PortletPreferencesImp implements PortletPreferences
{
    Hashtable map=new Hashtable();
    ArrayList readOnly=new ArrayList();
    
    int requestType;
    SWBParameters paramsRequest;
    PortletPreferences defaultPreferences=null;
    WBPortletDefinition portletDefinition;
    
    public PortletPreferencesImp()
    {
        
    }
    
    public PortletPreferencesImp(WBPortletDefinition portletDefinition, SWBParameters paramsRequest, int requestType)
    {
        this.portletDefinition=portletDefinition;
        this.paramsRequest=paramsRequest;
        this.defaultPreferences=portletDefinition.getDefaultPreferences();
        this.requestType=requestType;
        load();
    }
    
    private void load()
    {
        try
        {
            String data=paramsRequest.getResourceBase().getData(paramsRequest.getUser());
            if(data!=null)
            {
                Document doc=SWBUtils.XML.xmlToDom(data);
                Element root=(Element)doc.getFirstChild();
                Element portletPreferences=getTag(root, "portlet-preferences");
                if(portletPreferences!=null)
                {
                    NodeList nl2=portletPreferences.getElementsByTagName("preference");
                    for(int i=0;i<nl2.getLength();i++)
                    {
                        Element ini=(Element)nl2.item(i);
                        try
                        {
                            String pname=getTagValue(ini, "name");
                            String[] pvalues=getTagValues(ini, "value");
                            if(pname!=null && pvalues!=null)
                            {
                                setValues(pname, pvalues);
                            }
                            String readOnly=getTagValue(ini, "read-only");
                            if(readOnly!=null && readOnly.equalsIgnoreCase("true"))
                            {
                                setReadOnly(pname);
                            }
                        }catch(Exception e)
                        {WBFactoryMgr.getPortletContainer().log(e);}
                    }
                }
            }
        }catch(Exception e)
        {WBFactoryMgr.getPortletContainer().log(e);}
    }
    
    /**
     * Returns true, if the value of this key cannot be modified by the user.
     * <p>
     * Modifiable preferences can be changed by the
     * portlet in any standard portlet mode (<code>EDIT, HELP, VIEW</code>).
     * Per default every preference is modifiable.
     * <p>
     * Read-only preferences cannot be changed by the
     * portlet in any standard portlet mode, but inside of custom modes
     * it may be allowed changing them.
     * Preferences are read-only, if they are defined in the
     * deployment descriptor with <code>read-only</code> set to <code>true</code>,
     * or if the portlet container restricts write access.
     *
     * @return  false, if the value of this key can be changed, or
     *          if the key is not known
     *
     * @exception java.lang.IllegalArgumentException
     *         if <code>key</code> is <code>null</code>.
     */
    public boolean isReadOnly(String key)
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.isReadOnly(null)");
        }
        if(defaultPreferences!=null && defaultPreferences.isReadOnly(key))return true;
        return readOnly.contains(key);
    }
    
    public void setReadOnly(String key)
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.setReadOnly(null)");
        }
        readOnly.add(key);
    }
    
    
    
    
    /**
     * Returns the first String value associated with the specified key of this preference.
     * If there is one or more preference values associated with the given key
     * it returns the first associated value.
     * If there are no preference values associated with the given key, or the
     * backing preference database is unavailable, it returns the given
     * default value.
     *
     * @param key key for which the associated value is to be returned
     * @param def the value to be returned in the event that there is no
     *            value available associated with this <code>key</code>.
     *
     * @return the value associated with <code>key</code>, or <code>def</code>
     *         if no value is associated with <code>key</code>, or the backing
     *         store is inaccessible.
     *
     * @exception java.lang.IllegalArgumentException
     *         if <code>key</code> is <code>null</code>. (A
     *         <code>null</code> value for <code>def</code> <i>is</i> permitted.)
     *
     * @see #getValues(String, String[])
     */
    public String getValue(String key, String def)
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.getValue(null)");
        }
        String val=getFistString((String [])map.get(key));
        
        if(val==null)
        {
            if(defaultPreferences!=null)
            {
                val=defaultPreferences.getValue(key, def);
            }else
            {
                val=def;
            }
        }
        return val;
    }
    
    private String getFistString(String vals[])
    {
        String val=null;
        if(vals!=null)
        {
            if(vals.length>0)
            {
                val=vals[0];
            }
        }
        return val;
    }
    
    
    /**
     * Returns the String array value associated with the specified key in this preference.
     *
     * <p>Returns the specified default if there is no value
     * associated with the key, or if the backing store is inaccessible.
     *
     * <p>If the implementation supports <i>stored defaults</i> and such a
     * default exists and is accessible, it is used in favor of the
     * specified default.
     *
     *
     * @param key key for which associated value is to be returned.
     * @param def the value to be returned in the event that this
     *        preference node has no value associated with <code>key</code>
     *        or the associated value cannot be interpreted as a String array,
     *        or the backing store is inaccessible.
     *
     * @return the String array value associated with
     *         <code>key</code>, or <code>def</code> if the
     *         associated value does not exist.
     *
     * @exception java.lang.IllegalArgumentException if <code>key</code> is <code>null</code>.  (A
     *         <code>null</code> value for <code>def</code> <i>is</i> permitted.)
     *
     * @see #getValue(String,String)
     */
    
    public String[] getValues(String key, String[] def)
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.getValues(null)");
        }
        String val[]=(String[])map.get(key);
        if(val==null)
        {
            if(defaultPreferences!=null)
            {
                val=defaultPreferences.getValues(key, def);
            }else
            {
                val=def;
            }
        }
        return val;
    }
    
    
    
    /**
     * Associates the specified String value with the specified key in this
     * preference.
     * <p>
     * The key cannot be <code>null</code>, but <code>null</code> values
     * for the value parameter are allowed.
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     *
     * @exception  ReadOnlyException
     *                 if this preference cannot be modified for this request
     * @exception java.lang.IllegalArgumentException if key is <code>null</code>,
     *                 or <code>key.length()</code>
     *                 or <code>value.length</code> are to long. The maximum length
     *                 for key and value are implementation specific.
     *
     * @see #setValues(String, String[])
     */
    public void setValue(String key, String value)  throws ReadOnlyException
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.setValue(null)");
        }
        if(isReadOnly(key))
        {
            throw new ReadOnlyException("PortletPreferences.setValue("+key+") is readOnly");
        }
        map.put(key, new String[]{value});
    }
    
    
    
    
    /**
     * Associates the specified String array value with the specified key in this
     * preference.
     * <p>
     * The key cannot be <code>null</code>, but <code>null</code> values
     * in the values parameter are allowed.
     *
     * @param key key with which the  value is to be associated
     * @param values values to be associated with key
     *
     * @exception  java.lang.IllegalArgumentException if key is <code>null</code>, or
     *                 <code>key.length()</code>
     *                 is to long or <code>value.size</code> is to large.  The maximum
     *                 length for key and maximum size for value are implementation specific.
     * @exception  ReadOnlyException
     *                 if this preference cannot be modified for this request
     *
     * @see #setValue(String,String)
     */
    
    public void setValues(String key, String[] values) throws ReadOnlyException
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.setValues(null)");
        }
        if(isReadOnly(key))
        {
            throw new ReadOnlyException("PortletPreferences.setValues("+key+") is readOnly");
        }
        map.put(key, values);
    }
    
    
    /**
     * Returns all of the keys that have an associated value,
     * or an empty <code>Enumeration</code> if no keys are
     * available.
     *
     * @return an Enumeration of the keys that have an associated value,
     *         or an empty <code>Enumeration</code> if no keys are
     *         available.
     */
    public java.util.Enumeration getNames()
    {
        Vector vec=new Vector(map.keySet());
        if(defaultPreferences!=null)
        {
            Enumeration en=defaultPreferences.getNames();
            while(en.hasMoreElements())
            {
                String key=(String)en.nextElement();
                if(!vec.contains(key))vec.add(key);
            }
        }
        return vec.elements();
    }
    
    /**
     * Returns a <code>Map</code> of the preferences.
     * <p>
     * The values in the returned <code>Map</code> are from type
     * String array (<code>String[]</code>).
     * <p>
     * If no preferences exist this method returns an empty <code>Map</code>.
     *
     * @return     an immutable <code>Map</code> containing preference names as
     *             keys and preference values as map values, or an empty <code>Map</code>
     *             if no preference exist. The keys in the preference
     *             map are of type String. The values in the preference map are of type
     *             String array (<code>String[]</code>).
     */
    
    public java.util.Map getMap()
    {
        HashMap aux=null;
        if(defaultPreferences!=null)
        {
            aux=new HashMap(defaultPreferences.getMap());
            aux.putAll(map);
        }else
        {
            aux=new HashMap(map);
        }
        return aux;
    }
    
    
    /**
     * Resets or removes the value associated with the specified key.
     * <p>
     * If this implementation supports stored defaults, and there is such
     * a default for the specified preference, the given key will be
     * reset to the stored default.
     * <p>
     * If there is no default available the key will be removed.
     *
     * @param  key to reset
     *
     * @exception  java.lang.IllegalArgumentException if key is <code>null</code>.
     * @exception  ReadOnlyException
     *                 if this preference cannot be modified for this request
     */
    
    public void reset(String key) throws ReadOnlyException
    {
        if(key==null)
        {
            throw new IllegalArgumentException("PortletPreferences.reset(null)");
        }
        if(!isReadOnly(key))
        {
            map.remove(key);
        }else
        {
            throw new ReadOnlyException("PortletPreferences.reset("+key+") is readOnly...");
        }
        
    }
    
    
    /**
     * Commits all changes made to the preferences via the
     * <code>set</code> methods in the persistent store.
     * <P>
     * If this call returns succesfull, all changes are made
     * persistent. If this call fails, no changes are made
     * in the persistent store. This call is an atomic operation
     * regardless of how many preference attributes have been modified.
     * <P>
     * All changes made to preferences not followed by a call
     * to the <code>store</code> method are discarded when the
     * portlet finishes the <code>processAction</code> method.
     * <P>
     * If a validator is defined for this preferences in the
     * deployment descriptor, this validator is called before
     * the actual store is performed to check wether the given
     * preferences are vaild. If this check fails a
     * <code>ValidatorException</code> is thrown.
     *
     * @exception  java.io.IOException
     *                 if changes cannot be written into
     *                 the backend store
     * @exception  ValidatorException
     *                 if the validation performed by the
     *                 associated validator fails
     * @exception  java.lang.IllegalStateException
     *                 if this method is called inside a render call
     *
     * @see  PreferencesValidator
     */
    
    public void store() throws java.io.IOException, ValidatorException
    {
        //System.out.println("store()");
        //new Exception().printStackTrace();
        if(requestType==0)
        {
            throw new IllegalArgumentException("PortletPreferences.reset(null)");
        }
        //System.out.println("store().valudator");
        // validate preferences
        PreferencesValidator validator = portletDefinition.getPreferencesValidator();
        if (validator != null)
        {
            validator.validate(this);
        }
        
        try
        {
            //System.out.println("store().store");
            Document doc=SWBUtils.XML.getNewDocument();
            //System.out.println("store().document:"+doc);
            Element portlet=doc.createElement("portlet");
            doc.appendChild(portlet);
            Element portlet_preferences=createElement(portlet,"portlet-preferences");
            Enumeration en=map.keys();
            while(en.hasMoreElements())
            {
                String key=(String)en.nextElement();
                Element preference=createElement(portlet_preferences,"preference");
                addAttributes(preference,"name",key);
                String vals[]=(String [])map.get(key);
                for(int x=0;x<vals.length;x++)
                {
                    addAttributes(preference,"value",vals[x]);
                }
            }
            //System.out.println("store:"+AFUtils.getInstance().DomtoXml(doc));
            paramsRequest.getResourceBase().setData(paramsRequest.getUser(),SWBUtils.XML.domToXml(doc));
            
        }catch(Exception e)
        {
            WBFactoryMgr.getPortletContainer().log(e);
            throw new IOException(e.getMessage());
        }
    }
    
    private Element createElement(Element root, String name)
    {
        Element ret=root.getOwnerDocument().createElement(name);
        root.appendChild(ret);
        return ret;
    }
    
    private Element addAttributes(Element root, String name, String value)
    {
        Element ret=root.getOwnerDocument().createElement(name);
        root.appendChild(ret);
        Node val=root.getOwnerDocument().createTextNode(value);
        ret.appendChild(val);
        return ret;
    }
    
    private String getTagValue(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        if(nl.getLength()>0)
        {
            Node txt=nl.item(0).getFirstChild();
            if(txt!=null)
            {
                return txt.getNodeValue();
            }else
            {
                return "";
            }
        }
        else return null;
    }
    
    private String[] getTagValues(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        String list[]=new String[nl.getLength()];
        if(nl.getLength()>0)
        {
            for(int x=0;x<nl.getLength();x++)
            {
                Node txt=nl.item(x).getFirstChild();
                if(txt!=null)
                {
                    list[x]=txt.getNodeValue();
                }else
                {
                    list[x]="";
                }
            }
        }
        return list;
    }
    
    private Element getTag(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        if(nl.getLength()>0)
        {
            return (Element)nl.item(0);
        }
        else return null;
    }
    
}
