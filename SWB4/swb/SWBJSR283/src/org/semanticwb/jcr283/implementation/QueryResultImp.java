/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
/**
 *
 * @author victor.lorenzana
 */
public class QueryResultImp implements QueryResult{

    
    private final Set<NodeImp> nodes;
    private final String[] columnNames;
    private final ArrayList<RowImp> rows;
    public QueryResultImp(Set<NodeImp> nodes,String[] ColumnNames,ArrayList<RowImp> rows)
    {        
        this.nodes=nodes;
        this.columnNames=ColumnNames;
        this.rows=rows;
    }    
    public String[] getColumnNames() throws RepositoryException
    {
        return columnNames;
    }

    public RowIterator getRows() throws RepositoryException
    {
        return new RowIteratorImp(rows);
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
