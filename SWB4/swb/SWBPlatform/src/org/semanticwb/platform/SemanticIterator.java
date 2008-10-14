
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticIterator<T extends SemanticObject> implements Iterator
{
    private SemanticClass scls;
    private Class cls;
    private Iterator iterator;
    private Constructor constructor;
    private boolean invert=false;
            
    public SemanticIterator(SemanticClass cls,Iterator iterator)
    {
        this(cls,iterator,false);
    }

    public SemanticIterator(SemanticClass cls,Iterator iterator, boolean invert)
    {
        this.scls=cls;
        this.iterator=iterator;
        this.invert=invert;
    }
    
    public SemanticIterator(Class cls,Iterator iterator)
    {
        this.cls=cls;
        this.iterator=iterator;
        try
        {
            constructor=cls.getDeclaredConstructor(Resource.class);
        }
        catch(NoSuchMethodException nsme)
        {
            new IllegalArgumentException(nsme);
        }
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
        if(scls!=null)
        {
            if(obj instanceof Statement)
            {
                try
                {
                    if(invert)
                    {
                        return (T)scls.newInstance(((Statement)obj).getSubject());
                    }
                    return (T)scls.newInstance(((Statement)obj).getResource());
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
                    return (T)scls.newInstance((Resource)obj);
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
        }else
        {
            if(obj instanceof Statement)
            {
                try
                {
                    if(invert)
                    {
                        return (T)constructor.newInstance(((Statement)obj).getSubject());
                    }
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
}
