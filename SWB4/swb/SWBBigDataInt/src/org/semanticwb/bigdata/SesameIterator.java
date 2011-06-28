/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
