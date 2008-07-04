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
public class LiteralImp implements Literal
{
    private com.hp.hpl.jena.rdf.model.Literal interObject;

    public LiteralImp(com.hp.hpl.jena.rdf.model.Literal interObject)
    {
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.Literal getInternalObject() 
    {
        return interObject;
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
