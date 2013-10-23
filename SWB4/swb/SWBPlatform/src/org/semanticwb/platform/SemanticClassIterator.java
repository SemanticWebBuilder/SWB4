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

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ClosableIterator;
import java.util.Iterator;
import org.semanticwb.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticClassIterator.
 * 
 * @param <T> the generic type
 * @author Jei
 */
public class SemanticClassIterator<T extends SemanticClass> implements Iterator
{
    
    /** The m_it. */
    private Iterator<SemanticClass> m_it;
    
    /** The create. */
    private boolean create=false;
    
    /** The tmp. */
    private SemanticClass tmp=null;
    
    /** The next. */
    private boolean next=false;
    
    /** The retnext. */
    private boolean retnext=false;
    
    private boolean newInstances=false;
    
    
    /**
     * Instantiates a new semantic class iterator.
     * 
     * @param it the it
     */
    public SemanticClassIterator(Iterator it)
    {
        this.m_it=it;
    }
    
    /**
     * Instantiates a new semantic class iterator.
     * 
     * @param it the it
     */
    public SemanticClassIterator(Iterator it, boolean newInstances)
    {
        this.m_it=it;
        this.newInstances=newInstances;
    }    

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() 
    {
        if(!next)
        {
            boolean ret=m_it.hasNext();
            if(ret)
            {
                tmp=_next();
                if(tmp==null)ret=hasNext();
            }
            next=true;
            retnext=ret;
        }

        if(!retnext && m_it instanceof ClosableIterator)
        {
            ((ClosableIterator)m_it).close();
        }

        return retnext;
    }
    
    /**
     * _next.
     * 
     * @return the semantic class
     */
    private SemanticClass _next()
    {
        Object obj=m_it.next();
        SemanticClass cls=null;
        if(obj instanceof Statement)
        {
            if(newInstances)
            {
                OntClass ocls=null;
                Model m=((Statement)obj).getModel();
                OntModel om=null;
                if(m instanceof OntModel)
                {
                    om=(OntModel)m;
                }
                if(om!=null)
                {
                    ocls=om.createClass(((Statement)obj).getResource().getURI());
                }
                else
                {
                    ocls=SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().createClass(((Statement)obj).getResource().getURI());
                }
                if(ocls!=null)cls=new SemanticClass(ocls);
            }else
            {
                cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(((Statement)obj).getResource().getURI());
            }
        }else
        {
            OntClass ocls=(OntClass)obj;
            
            if(newInstances)
            {
                cls=new SemanticClass(ocls);
            }else
            {
                //System.out.println(ocls+" "+ocls.getURI()+ocls.getLocalName());
                cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(ocls.getURI());
            }
        }            
        return cls;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    public T next()
    {
        if(!next)
        {
            hasNext();
        }
        next=false;
        return (T)tmp;
    }
    
    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove()
    {
        m_it.remove();
    }

}
