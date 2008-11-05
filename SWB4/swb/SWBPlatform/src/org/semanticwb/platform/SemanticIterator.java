
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticIterator<T extends SemanticObject> implements Iterator
{
    private Iterator iterator;
    private boolean invert=false;
            
    public SemanticIterator(Iterator iterator)
    {
        this(iterator,false);
    }

    public SemanticIterator(Iterator iterator, boolean invert)
    {
        this.iterator=iterator;
        this.invert=invert;
    }
    
    public void remove()
    {
        iterator.remove();
    }
    
    public boolean hasNext() 
    {
        return iterator.hasNext();
    }
    
    public T next() 
    {        
        Object obj=iterator.next();
        {
            if(obj instanceof Statement)
            {
                try
                {
                    if(invert)
                    {
                        return (T)new SemanticObject(((Statement)obj).getSubject());
                    }
                    return (T)new SemanticObject(((Statement)obj).getResource());
                }
                catch(Exception ie)
                {
                    throw new AssertionError(ie.getMessage());        
                }
            }
            else if(obj instanceof Resource)
            {
                try
                {
                    return (T)new SemanticObject((Resource)obj);
                }
                catch(Exception ie)
                {
                    throw new AssertionError(ie.getMessage());        
                }
            }
            else
            {
                throw new AssertionError("No type found...");      
            }
        }
    }
}
