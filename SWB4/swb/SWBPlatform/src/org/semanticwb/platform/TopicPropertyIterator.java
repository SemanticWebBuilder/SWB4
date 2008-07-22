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
public class TopicPropertyIterator implements Iterator
{
    Iterator it;
    
    public TopicPropertyIterator(Iterator it)
    {
        this.it=it;
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        return new TopicProperty((Property)it.next());
    }
    
    public TopicProperty nextTopicProperty() 
    {
        return new TopicProperty((Property)it.next());
    }

    public void remove()
    {
        it.remove();
    }

}
