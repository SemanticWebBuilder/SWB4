/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.HashMap;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;

/**
 *
 * @author Jei
 */
public class SemanticPropertyCache 
{
    private SemanticProperty m_prop;
    private HashMap map=new HashMap();
    
    public SemanticPropertyCache(SemanticProperty prop)
    {
        this.m_prop=prop;
    }
    
    public boolean hasChacheProperty(String name)
    {
        return map.containsKey(name);
    }
    
    public void addStringProperty(String name, String value)
    {
        map.put(name, value);
    }
    
    public String getStringProperty(String name)
    {
        return (String)map.get(name);
    }
    
    public void addBooleanProperty(String name, boolean value)
    {
        map.put(name, Boolean.valueOf(value));
    }
    
    public boolean getBooleanProperty(String name)
    {
        return (Boolean)map.get(name);
    }    

}
