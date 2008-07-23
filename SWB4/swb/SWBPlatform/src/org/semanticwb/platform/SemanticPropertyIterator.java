/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Property;
import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class SemanticPropertyIterator implements Iterator
{
    Iterator it;
    
    public SemanticPropertyIterator(Iterator it)
    {
        this.it=it;
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        return new SemanticProperty((Property)it.next());
    }
    
    public SemanticProperty nextSemanticProperty() 
    {
        return new SemanticProperty((Property)it.next());
    }

    public void remove()
    {
        it.remove();
    }

}
