
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 *
 * @author victor.lorenzana
 */
public class DataTypeIterator<T> implements Iterator
{
    private Class cls;
    private Iterator iterator;
    private boolean invert=false;
            
    public DataTypeIterator(Class cls,Iterator iterator)
    {
        this.cls=cls;
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
                if(cls==String.class)
                {
                    return (T)((Statement)obj).getString();
                }else if(cls==Integer.class)
                {
                    return (T)Integer.valueOf(((Statement)obj).getInt());
                }else if(cls==Boolean.class)
                {
                    return (T)Boolean.valueOf(((Statement)obj).getBoolean());
                }
            }
            catch(Exception ie)
            {
                throw new AssertionError(ie.getMessage());        
            }
        }
        throw new AssertionError();
    }
}
