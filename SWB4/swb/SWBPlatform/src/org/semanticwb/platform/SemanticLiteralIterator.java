
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticLiteralIterator<T extends SemanticLiteral> implements Iterator
{
    private Iterator iterator;
            
    public SemanticLiteralIterator(Iterator iterator)
    {
        this.iterator=iterator;
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
        if(obj instanceof Statement)
        {
            try
            {
                return (T)new SemanticLiteral(((Statement)obj).getLiteral());
            }
            catch(Exception ie)
            {
                throw new AssertionError(ie.getMessage());        
            }
        }
        throw new AssertionError();
    }
}
