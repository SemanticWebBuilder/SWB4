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
