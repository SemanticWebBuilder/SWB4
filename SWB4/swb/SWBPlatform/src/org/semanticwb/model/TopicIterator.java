/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Resource;
import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class TopicIterator implements Iterator
{
    Iterator it;
    
    public TopicIterator(Iterator it)
    {
        this.it=it;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        return new Topic((Resource)it.next());
    }
    
    public Topic nextTopic() 
    {
        return new Topic((Resource)it.next());
    }

    public void remove()
    {
        it.remove();
    }

}
