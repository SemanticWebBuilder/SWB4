
package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author victor.lorenzana
 */
public class GenericIterator<T extends GenericObject> implements Iterator
{
    private Iterator iterator;
    private boolean invert=false;
            
    public GenericIterator(Iterator iterator)
    {
        this(iterator,false);
    }
    
    public GenericIterator(Iterator iterator, boolean invert)
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
        if(obj instanceof Statement)
        {
            Resource res=null;
            if(invert)
            {
                res=((Statement)obj).getSubject();
            }else
            {
                res=((Statement)obj).getResource();
            }
            return (T)SemanticObject.createSemanticObject(res).createGenericInstance();
        }
        else if(obj instanceof Resource)
        {
            Resource res=(Resource)obj;
            return (T)SemanticObject.createSemanticObject(res).createGenericInstance();
        }
        else if(obj instanceof SemanticObject)
        {
            SemanticObject sobj=(SemanticObject)obj;
            return (T)sobj.createGenericInstance();
        }
        else
        {
            throw new AssertionError("No type found...,"+obj);
        }
    }
}
