/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class SemanticVocabulary 
{
    //RDF
    public static final String RDF_URI="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDF_TYPE=RDF_URI+"type";
    
    public static final String RDFS_URI="http://www.w3.org/2000/01/rdf-schema#";
    public static final String RDFS_DOMAIN=RDFS_URI+"domain";
    public static final String RDFS_RANGE=RDFS_URI+"range";
    public static final String RDFS_SUBPROPERTYOF=RDFS_URI+"subPropertyOf";
    
    public static final String XMLS_URI="http://www.w3.org/2001/XMLSchema#";
    public static final String XMLS_DATETIME=XMLS_URI+"dateTime";
    public static final String XMLS_BOOLEAN=XMLS_URI+"boolean";
    public static final String XMLS_STRING=XMLS_URI+"string";
    public static final String XMLS_INT=XMLS_URI+"int";
    public static final String XMLS_FLOAT=XMLS_URI+"float";
    public static final String XMLS_DOUBLE=XMLS_URI+"double";
    public static final String XMLS_LONG=XMLS_URI+"long";
    public static final String XMLS_SHORT=XMLS_URI+"short";
    public static final String XMLS_BYTE=XMLS_URI+"byte";
    
            
    public static final String OWL_URI="http://www.w3.org/2002/07/owl#";
    public static final String OWL_DATATYPEPROPERTY=OWL_URI+"DatatypeProperty";
    public static final String OWL_OBJECTPROPERTY=OWL_URI+"ObjectProperty";
        
    //General
    public static final String URI="http://www.semanticwebbuilder.org/swb4/ontology#";

    public SemanticClass SWBClass;
    public SemanticClass SWBInterface;
    public static final String SWB_CLASS_SWBModel=URI+"SWBModel";
    public static final String SWB_PROP_VALUE=URI+"value";

    
    public HashMap<String, SemanticClass> classes;
    public HashMap<String, SemanticProperty> properties;
    
    
    public SemanticVocabulary()
    {
        classes=new HashMap();
        properties=new HashMap();
    }
    
    public void init()
    {
        SWBClass=getSemanticClass(URI+"SWBClass");
        SWBInterface=getSemanticClass(URI+"SWBInterface");
    }
    
    void addSemanticClass(SemanticClass tpc)
    {
        classes.put(tpc.getURI(), tpc);
    }
    
    public Iterator<SemanticClass> listSemanticClasses()
    {
        return classes.values().iterator();
    }
    
    public SemanticClass getSemanticClass(String uri)
    {
        return classes.get(uri);
    }
    
    void addSemanticProperty(SemanticProperty tpp)
    {
        if(!properties.containsKey(tpp.getURI()))
        {
            properties.put(tpp.getURI(), tpp);
        }
    }
    
    public Iterator<SemanticProperty> listSemanticProperties()
    {
        return properties.values().iterator();
    }
    
    public SemanticProperty getSemanticProperty(String uri)
    {
        return properties.get(uri);
    }    
    
    
}
