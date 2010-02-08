/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class RowIteratorImp implements RowIterator {

    private final Iterator<RowImp> it;
    private final long size;
    private long position = 0;    
    public RowIteratorImp(Collection<RowImp> rows)
    {
        ArrayList<RowImp> orows = new ArrayList<RowImp>();
        orows.addAll(rows);
        it=orows.iterator();
        size=orows.size();
    }
    public Row nextRow()
    {
        RowImp node=it.next();
        if(node!=null)
            position++;
        return node;
    }

    public void skip(long skipNum)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getSize()
    {
        return size;
    }

    public long getPosition()
    {
        return position;
    }

    public boolean hasNext()
    {
        return it.hasNext();
    }

    public Object next()
    {
        return this.nextRow();
    }

    public void remove()
    {
        it.remove();
    }

}
