/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
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
        Object obj=it.next();
        if(obj instanceof Resource)
        {
            return new Topic((Resource)obj);
        }else if(obj instanceof Statement)
        {
            return new Topic(((Statement)obj).getResource());
        }
        throw new AssertionError("No type found...");
    }
    
    public Topic nextTopic() 
    {
        return (Topic)next();
    }

    public void remove()
    {
        it.remove();
    }

}
