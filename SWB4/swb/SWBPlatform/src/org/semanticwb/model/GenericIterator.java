
package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author victor.lorenzana
 */
public class GenericIterator<T extends GenericObject> implements Iterator
{
    private SemanticClass scls;
    private Class cls;
    private Iterator iterator;
    private Constructor constructor;
    private boolean invert=false;
            
    public GenericIterator(SemanticClass cls,Iterator iterator)
    {
        this(cls,iterator,false);
    }
    
    public GenericIterator(SemanticClass cls,Iterator iterator, boolean invert)
    {
        this.scls=cls;
        this.iterator=iterator;
        this.invert=invert;
//        try
//        {
//            constructor=clazz.getDeclaredConstructor(Resource.class);
//        }
//        catch(NoSuchMethodException nsme)
//        {
//            new IllegalArgumentException(nsme);
//        }
    }    
    
    public GenericIterator(Class cls,Iterator iterator)
    {
        this(cls,iterator,false);
    }    
    
    public GenericIterator(Class cls,Iterator iterator, boolean invert)
    {
        this.cls=cls;
        this.iterator=iterator;
        this.invert=invert;
        try
        {
            constructor=cls.getDeclaredConstructor(SemanticObject.class);
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
                        return (T)scls.newGenericInstance(((Statement)obj).getSubject());
                    }
                    return (T)scls.newGenericInstance(((Statement)obj).getResource());
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
                    return (T)scls.newGenericInstance((Resource)obj);
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
                        return (T)constructor.newInstance(new SemanticObject(((Statement)obj).getSubject()));
                    }
                    return (T)constructor.newInstance(new SemanticObject(((Statement)obj).getResource()));
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
                    return (T)constructor.newInstance(new SemanticObject((Resource)obj));
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
