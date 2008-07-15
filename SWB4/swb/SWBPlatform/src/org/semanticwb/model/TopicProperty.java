/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Property;

/**
 *
 * @author Jei
 */
public class TopicProperty 
{
    Property m_prop;
    
    public TopicProperty(Property prop)
    {
        this.m_prop=prop;
    }
    
    public String getName()
    {
        return m_prop.getLocalName();
    }
    
    public String getURI()
    {
        return m_prop.getURI();
    }
    
    public Property getRDFProperty()
    {
        return m_prop;
    }
}
