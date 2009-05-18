
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticIterator<T extends SemanticObject> implements Iterator
{
    private static Logger log=SWBUtils.getLogger(SemanticIterator.class);

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
                //System.out.println("res:"+((Statement)obj).getResource());
                //System.out.println("sub:"+((Statement)obj).getSubject());
                try
                {
                    if(invert)
                    {
                        return (T)SemanticObject.createSemanticObject(((Statement)obj).getSubject());
                    }
                    return (T)SemanticObject.createSemanticObject(((Statement)obj).getResource());
                }catch(SWBRuntimeException re)
                {
                    log.error(re);
                    if(re.getMessage().startsWith("Resource not Found"))
                    {
                        log.error("Removing bad link:"+((Statement)obj).getResource());
                        ((Statement)obj).remove();
                    }
                    return null;
                }catch(Exception ie)
                {
                    log.error(ie);
                    return null;
                }
                //{
                //    throw new AssertionError(ie.getMessage());
                //}
            }
            else if(obj instanceof Resource)
            {
                try
                {
                    return (T)SemanticObject.createSemanticObject((Resource)obj);
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
