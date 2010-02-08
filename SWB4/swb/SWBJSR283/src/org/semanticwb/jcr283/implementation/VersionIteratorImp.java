/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class VersionIteratorImp implements VersionIterator
{

    private final Iterator<VersionImp> it;
    private final long size;
    private long position = 0;

    public VersionIteratorImp(Collection<VersionImp> versions)
    {
        ArrayList<VersionImp> values = new ArrayList<VersionImp>();
        values.addAll(versions);
        it = values.iterator();
        size = values.size();
    }

    public Version nextVersion()
    {
        VersionImp node = it.next();
        if (node != null)
        {
            position++;
        }
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
        return this.nextVersion();
    }

    public void remove()
    {
        it.remove();
    }
}
