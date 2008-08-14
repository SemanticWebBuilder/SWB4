/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;
import org.semanticwb.*;

/**
 *
 * @author Jei
 */
public class SemanticClassIterator implements Iterator
{
    Iterator it;
    
    public SemanticClassIterator(Iterator it)
    {
        this.it=it;
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        Object obj=it.next();
        if(obj instanceof Statement)
        {
            return SWBInstance.getSemanticMgr().getVocabulary().getSemanticClass(((Statement)obj).getResource().getURI());
        }
        return new SemanticClass((OntClass)obj);
    }
    
    public SemanticClass nextSemanticClass() 
    {
        return new SemanticClass((OntClass)it.next());
    }

    public void remove()
    {
        it.remove();
    }

}
