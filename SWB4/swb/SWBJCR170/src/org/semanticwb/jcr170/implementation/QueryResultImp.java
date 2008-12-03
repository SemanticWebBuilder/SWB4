/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;

/**
 *
 * @author victor.lorenzana
 */
public class QueryResultImp implements QueryResult
{
    static Logger log=SWBUtils.getLogger(QueryResultImp.class);
    private final List<String> nodes;
    private final String workspaceName;
    private final SessionImp session;    
    QueryResultImp(SessionImp session, List<String> nodes, String workspaceName)
    {
        if ( session == null || workspaceName == null )
        {
            throw new IllegalArgumentException("the session or workspaceName was null");
        }
        if ( SWBContext.getWorkspace(workspaceName) == null )
        {
            throw new IllegalArgumentException("The workspace " + workspaceName + " was not found");
        }
        this.nodes = nodes;
        this.workspaceName = workspaceName;
        this.session = session;        
        
    }
    public String[] getColumnNames() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowIterator getRows() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static String getUri(String uri)
    {
        int pos = uri.indexOf("#");
        if ( pos != -1 )
        {
            uri = uri.substring(0, pos);
        }
        return uri;
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        ArrayList<SimpleNode> simpleNodes = new ArrayList<SimpleNode>();        
        for ( String path : nodes )
        { 
            for(SimpleNode node : session.getSimpleNodeByPath(path,session.getSimpleRootNode(),true))
            {
                simpleNodes.add(node);            
            }
        }
        return new NodeIteratorImp(session, simpleNodes);
    }
}
