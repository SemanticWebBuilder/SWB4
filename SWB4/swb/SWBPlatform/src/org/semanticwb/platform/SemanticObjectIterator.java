/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class SemanticObjectIterator implements Iterator
{
    Iterator it;
    
    public SemanticObjectIterator(Iterator it)
    {
        this.it=it;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
    }

    public boolean hasNext() 
    {
        return it.hasNext();
    }

    public Object next()
    {
        Object obj=it.next();
        if(obj instanceof Resource)
        {
            return new SemanticObject((Resource)obj);
        }else if(obj instanceof Statement)
        {
            return new SemanticObject(((Statement)obj).getResource());
        }
        throw new AssertionError("No type found...");
    }
    
    public SemanticObject nextSemanticObject() 
    {
        return (SemanticObject)next();
    }

    public void remove()
    {
        it.remove();
    }

}
