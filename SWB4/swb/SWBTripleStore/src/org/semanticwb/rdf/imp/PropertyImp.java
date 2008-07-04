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
public class PropertyImp implements Property
{
    private com.hp.hpl.jena.rdf.model.Property interObject;

    public PropertyImp(com.hp.hpl.jena.rdf.model.Property interObject)
    {
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.Property getInternalObject() 
    {
        return interObject;
    }    
    
    @Override
    public String toString()
    {
        return interObject.toString();
    }
    
}
