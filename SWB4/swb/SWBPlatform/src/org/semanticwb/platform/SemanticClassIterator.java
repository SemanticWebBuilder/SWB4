/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class SemanticClassIterator implements Iterator
{
    Iterator it;
    
    public SemanticClassIterator(Iterator it)
    {
        this.it=it;
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        return new SemanticClass((OntClass)it.next());
    }
    
    public SemanticClass nextSemanticClass() 
    {
        return new SemanticClass((OntClass)it.next());
    }

    public void remove()
    {
        it.remove();
    }

}
