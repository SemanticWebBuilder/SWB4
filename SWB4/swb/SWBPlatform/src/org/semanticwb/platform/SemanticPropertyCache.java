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
package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.HashMap;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticPropertyCache.
 * 
 * @author Jei
 */
public class SemanticPropertyCache 
{
    
    /** The m_prop. */
    private SemanticProperty m_prop;
    
    /** The map. */
    private HashMap map=new HashMap();
    
    /**
     * Instantiates a new semantic property cache.
     * 
     * @param prop the prop
     */
    public SemanticPropertyCache(SemanticProperty prop)
    {
        this.m_prop=prop;
    }
    
    /**
     * Checks for chache property.
     * 
     * @param name the name
     * @return true, if successful
     */
    public boolean hasChacheProperty(String name)
    {
        return map.containsKey(name);
    }
    
    /**
     * Adds the string property.
     * 
     * @param name the name
     * @param value the value
     */
    public void addStringProperty(String name, String value)
    {
        map.put(name, value);
    }
    
    /**
     * Gets the string property.
     * 
     * @param name the name
     * @return the string property
     */
    public String getStringProperty(String name)
    {
        return (String)map.get(name);
    }
    
    /**
     * Adds the boolean property.
     * 
     * @param name the name
     * @param value the value
     */
    public void addBooleanProperty(String name, boolean value)
    {
        map.put(name, Boolean.valueOf(value));
    }
    
    /**
     * Gets the boolean property.
     * 
     * @param name the name
     * @return the boolean property
     */
    public boolean getBooleanProperty(String name)
    {
        return (Boolean)map.get(name);
    }    

}
