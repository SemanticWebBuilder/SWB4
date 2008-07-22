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
public class TopicClassIterator implements Iterator
{
    Iterator it;
    
    public TopicClassIterator(Iterator it)
    {
        this.it=it;
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        return new TopicClass((OntClass)it.next());
    }
    
    public TopicClass nextTopicClass() 
    {
        return new TopicClass((OntClass)it.next());
    }

    public void remove()
    {
        it.remove();
    }

}
