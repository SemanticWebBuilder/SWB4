/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf.imp;

import org.semanticwb.rdf.*;

/**
 *
 * @author Jei
 */
public class ResourceImp extends RDFNodeImp implements Resource
{
    private com.hp.hpl.jena.rdf.model.Resource interObject;

    public ResourceImp(com.hp.hpl.jena.rdf.model.Resource interObject)
    {
        super(interObject);
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.Resource getInternalObject() 
    {
        return interObject;
    }     
    
    public Resource addProperty(Property prop, String value)
    {
        interObject.addProperty(((org.semanticwb.rdf.imp.PropertyImp)prop).getInternalObject(), value);
        return this;
    }
    
    public Resource addProperty(Property prop, String value, String lang)
    {
        interObject.addProperty(((org.semanticwb.rdf.imp.PropertyImp)prop).getInternalObject(), value, lang);
        return this;
    }
    
    public Resource addProperty(Property prop, RDFNode node)
    {
        interObject.addProperty(((org.semanticwb.rdf.imp.PropertyImp)prop).getInternalObject(), ((org.semanticwb.rdf.imp.RDFNodeImp)node).getInternalObject());
        return this;
    }

    public boolean isAnon() {
        return interObject.isAnon();
    }

    public boolean isLiteral() {
        return interObject.isLiteral();
    }

    public boolean isURIResource() {
        return interObject.isURIResource();
    }

    public boolean isResource() {
        return interObject.isResource();
    }
    
    @Override
    public String toString()
    {
        return interObject.toString();
    }
    
}
