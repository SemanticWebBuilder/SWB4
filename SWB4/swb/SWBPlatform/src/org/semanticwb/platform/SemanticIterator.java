
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticIterator<T extends SemanticObject>
{
    private Class clazz;
    private Iterator iterator;
    private Constructor constructor;
            
    public SemanticIterator(Class clazz,Iterator iterator)
    {
        this.clazz=clazz;
        this.iterator=iterator;
        try
        {
            constructor=clazz.getDeclaredConstructor(Resource.class);
        }
        catch(NoSuchMethodException nsme)
        {
            new IllegalArgumentException(nsme);
        }

    }
    public void remove()
    {
    
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
                return (T)constructor.newInstance(((Statement)obj).getResource());
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
                return (T)constructor.newInstance((Resource)obj);
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
