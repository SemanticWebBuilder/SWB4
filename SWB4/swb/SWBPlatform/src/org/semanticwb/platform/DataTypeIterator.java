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
import java.lang.reflect.Constructor;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class DataTypeIterator.
 * 
 * @param <T> the generic type
 * @author victor.lorenzana
 */
public class DataTypeIterator<T> implements Iterator
{
    
    /** The cls. */
    private Class cls;
    
    /** The iterator. */
    private Iterator iterator;
    
    /** The invert. */
    private boolean invert=false;
            
    /**
     * Instantiates a new data type iterator.
     * 
     * @param cls the cls
     * @param iterator the iterator
     */
    public DataTypeIterator(Class cls,Iterator iterator)
    {
        this.cls=cls;
        this.iterator=iterator;
    }    
    
    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove()
    {
        iterator.remove();
    }
    
    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() 
    {
        boolean ret=iterator.hasNext();
        if(!ret && iterator instanceof ClosableIterator)
        {
            ((ClosableIterator)iterator).close();
        }
        return ret;
    }
    
    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    public T next() 
    {        
        Object obj=iterator.next();
        if(obj instanceof Statement)
        {
            try
            {
                if(cls==String.class)
                {
                    return (T)((Statement)obj).getString();
                }else if(cls==Integer.class)
                {
                    return (T)Integer.valueOf(((Statement)obj).getInt());
                }else if(cls==Boolean.class)
                {
                    return (T)Boolean.valueOf(((Statement)obj).getBoolean());
                }
            }
            catch(Exception ie)
            {
                throw new AssertionError(ie.getMessage());        
            }
        }
        throw new AssertionError();
    }
}
