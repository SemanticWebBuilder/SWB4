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
    Iterator<SemanticClass> m_it;
    
    public SemanticClassIterator(Iterator it)
    {
        this(it,false);
    }   
    
    public SemanticClassIterator(Iterator it, boolean create)
    {
        ArrayList<SemanticClass> list=new ArrayList();
        while(it.hasNext())
        {
            Object obj=it.next();
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
            //if(cls!=null && cls.getName()!=null)
            if(cls!=null)
            {
                list.add(cls);
            }
        }
        m_it=list.iterator();
    }

    public boolean hasNext() 
    {
        return m_it.hasNext();
    }

    public T next()
    {
        return (T)m_it.next();
    }
    
    public void remove()
    {
        m_it.remove();
    }

}
