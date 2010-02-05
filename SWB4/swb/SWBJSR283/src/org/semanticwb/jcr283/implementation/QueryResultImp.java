/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Set;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
/**
 *
 * @author victor.lorenzana
 */
public class QueryResultImp implements QueryResult{

    
    private final Set<NodeImp> nodes;
    public QueryResultImp(Set<NodeImp> nodes)
    {        
        this.nodes=nodes;
    }
    public String[] getColumnNames() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowIterator getRows() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        HashSet<NodeImp> getNodes=new HashSet<NodeImp>();
        getNodes.addAll(nodes);
        return new NodeIteratorImp(nodes);
    }

    public String[] getSelectorNames() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
