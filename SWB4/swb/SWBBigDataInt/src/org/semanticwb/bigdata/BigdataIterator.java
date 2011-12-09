/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bigdata;

import com.bigdata.rdf.store.BigdataStatementIterator;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openrdf.model.Statement;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class BigdataIterator implements ExtendedIterator<Triple>
{
    private static Logger log=SWBUtils.getLogger(BigdataIterator.class);

    private BigdataStatementIterator sres;

    private Triple actual=null;

    private BigdataGraph graph=null;

    public BigdataIterator(BigdataGraph graph, BigdataStatementIterator stit)
    {
        this.graph=graph;
        this.sres = stit;
    }

    public Triple removeNext()
    {
        Triple tp=next();
        remove();
        return tp;
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
            log.error(ex);
        }
    }

    public boolean hasNext()
    {
        return sres.hasNext();
    }

    public Triple next()
    {
        Statement st = sres.next();
        actual = new Triple(SesameUtil.value2Node(st.getSubject()), SesameUtil.value2Node(st.getPredicate()), SesameUtil.value2Node(st.getObject()));
        return actual;
    }

    public void remove()
    {
        graph.performDelete(actual);
        //sres.remove();
    }
}
