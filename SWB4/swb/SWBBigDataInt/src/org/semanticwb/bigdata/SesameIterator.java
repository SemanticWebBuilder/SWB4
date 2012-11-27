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
package org.semanticwb.bigdata;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import info.aduna.iteration.CloseableIteration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class SesameIterator implements ExtendedIterator<Triple>
{
    private static Logger log=SWBUtils.getLogger(SesameIterator.class);

    private CloseableIteration<Statement,SailException> sres;

    public SesameIterator(CloseableIteration<Statement,SailException> stit)
    {
        sres = stit;
    }

    public Triple removeNext()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <X extends Triple> ExtendedIterator<Triple> andThen(Iterator<X> other)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ExtendedIterator<Triple> filterKeep(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ExtendedIterator<Triple> filterDrop(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <U> ExtendedIterator<U> mapWith(Map1<Triple, U> map1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Triple> toList()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<Triple> toSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close()
    {
        try
        {
            sres.close();
        } catch (Exception ex)
        {
            throw new RuntimeException("Error closing iterator",ex);
        }
    }

    public boolean hasNext()
    {
        try
        {
            return sres.hasNext();
        } catch (SailException ex)
        {
            throw new RuntimeException("Error getting next statement",ex);
        }
    }

    public Triple next()
    {
        try
        {
            Statement st = sres.next();
            return new Triple(SesameUtil.value2Node(st.getSubject()), SesameUtil.value2Node(st.getPredicate()), SesameUtil.value2Node(st.getObject()));
        } catch (SailException ex)
        {
            throw new RuntimeException("Error getting next statement",ex);
        }
    }

    public void remove()
    {
        try
        {
            sres.remove();
        } catch (SailException ex)
        {
            throw new RuntimeException("Error removing statement",ex);
        }
    }
}
