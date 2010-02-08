/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class VersionIteratorImp implements VersionIterator {

    private final Iterator<VersionImp> it;
    private final long size;
    private long position = 0;
    private final ArrayList<VersionImp> nodes = new ArrayList<VersionImp>();
    public VersionIteratorImp(Set<VersionImp> nodes)
    {
        for(VersionImp node : nodes)
        {
            this.nodes.add(node);
        }
        it=this.nodes.iterator();
        size=this.nodes.size();
    }
    public Version nextVersion()
    {
        VersionImp node=it.next();
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
        return this.nextVersion();
    }

    public void remove()
    {
        it.remove();
    }

}
