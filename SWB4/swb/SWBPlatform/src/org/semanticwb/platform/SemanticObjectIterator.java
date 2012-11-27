/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ClosableIterator;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticObjectIterator.
 * 
 * @author Jei
 */
public class SemanticObjectIterator implements Iterator
{
    
    /** The it. */
    Iterator it;
    
    /**
     * Instantiates a new semantic object iterator.
     * 
     * @param it the it
     */
    public SemanticObjectIterator(Iterator it)
    {
        this.it=it;
    }

    /**
     * The main method.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() 
    {
        boolean ret=it.hasNext();
        if(!ret && it instanceof ClosableIterator)
        {
            ((ClosableIterator)it).close();
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    public Object next()
    {
        Object obj=it.next();
        if(obj instanceof Resource)
        {
            return SemanticObject.createSemanticObject((Resource)obj);
        }else if(obj instanceof Statement)
        {
            return SemanticObject.createSemanticObject(((Statement)obj).getResource());
        }
        throw new AssertionError("No type found...:"+obj.getClass());
    }
    
    /**
     * Next semantic object.
     * 
     * @return the semantic object
     */
    public SemanticObject nextSemanticObject() 
    {
        return (SemanticObject)next();
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove()
    {
        it.remove();
    }

}
