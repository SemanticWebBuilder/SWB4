/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 *
 * @author victor.lorenzana
 */
public class GenericIterator<T extends Topic> implements Iterator
{
    private Class clazz;
    private Iterator iterator;
    private Constructor constructor;
            
    public GenericIterator(Class clazz,Iterator iterator)
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
