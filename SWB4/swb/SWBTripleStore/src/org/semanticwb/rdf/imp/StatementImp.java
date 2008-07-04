/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf.imp;

import org.semanticwb.rdf.Property;
import org.semanticwb.rdf.RDFNode;
import org.semanticwb.rdf.Resource;
import org.semanticwb.rdf.Statement;

/**
 *
 * @author Jei
 */
public class StatementImp implements Statement
{
    private com.hp.hpl.jena.rdf.model.Statement interObject;

    public StatementImp(com.hp.hpl.jena.rdf.model.Statement interObject)
    {
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.Statement getInternalObject() 
    {
        return interObject;
    }

    public Resource getSubject() {
        return new ResourceImp(interObject.getSubject());
    }

    public Property getPredicate() {
        return new PropertyImp(interObject.getPredicate());
    }

    public RDFNode getObject() {
        return new RDFNodeImp(interObject.getObject());
    }
    
    @Override
    public String toString()
    {
        return interObject.toString();
    }

}
