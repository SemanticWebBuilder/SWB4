/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf.imp;

import java.util.NoSuchElementException;
import org.semanticwb.rdf.*;

/**
 *
 * @author Jei
 */
public class StmtIteratorImp implements StmtIterator
{
    private com.hp.hpl.jena.rdf.model.StmtIterator interObject;

    public StmtIteratorImp(com.hp.hpl.jena.rdf.model.StmtIterator interObject)
    {
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.StmtIterator getInternalObject() 
    {
        return interObject;
    }      

    public boolean hasNext() {
        return interObject.hasNext();
    }

    public Statement nextStatement() throws NoSuchElementException {
        return new StatementImp(interObject.nextStatement());
    }

}
