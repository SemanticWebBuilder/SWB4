/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.*;

/**
 *
 * @author Jei
 */
public class SemanticClassIterator<T extends SemanticClass> implements Iterator
{
    private Iterator<SemanticClass> m_it;
    private boolean create=false;
    private SemanticClass tmp=null;
    private boolean next=false;
    
    
    public SemanticClassIterator(Iterator it)
    {
        this(it,false);
    }   
    
    public SemanticClassIterator(Iterator it, boolean create)
    {
        this.m_it=it;
        this.create=create;
    }

    public boolean hasNext() 
    {
        next=true;
        boolean ret=m_it.hasNext();
        if(ret)
        {
            tmp=_next();    
            if(tmp==null)ret=hasNext();
        }
        return ret;
    }
    
    private SemanticClass _next()
    {
        Object obj=m_it.next();
        SemanticClass cls=null;
        if(obj instanceof Statement)
        {
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(((Statement)obj).getResource().getURI());
        }else
        {
            OntClass ocls=(OntClass)obj;
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(ocls.getURI());
            if(create && cls==null)
            {
                cls=new SemanticClass(ocls);
            }
        }            
        return cls;
    }

    public T next()
    {
        if(!next)
        {
            hasNext();
        }
        next=false;
        return (T)tmp;
    }
    
    public void remove()
    {
        m_it.remove();
    }

}
