/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
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
    private final ArrayList<RowImp> rows = new ArrayList<RowImp>();
    public RowIteratorImp(ArrayList<RowImp> nodes)
    {
        for(RowImp node : nodes)
        {
            this.rows.add(node);
        }
        it=this.rows.iterator();
        size=this.rows.size();
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
