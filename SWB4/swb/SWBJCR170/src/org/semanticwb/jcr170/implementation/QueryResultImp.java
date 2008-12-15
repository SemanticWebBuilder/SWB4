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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class QueryResultImp implements QueryResult
{
    static Logger log=SWBUtils.getLogger(QueryResultImp.class);
    private final List<SemanticObject> nodes;
    private final String workspaceName;
    private final SessionImp session;
    QueryResultImp(SessionImp session, SemanticObject[] nodes, String workspaceName)
    {
        if ( session == null || workspaceName == null )
        {
            throw new IllegalArgumentException("the session or workspaceName was null");
        }
        if ( SWBContext.getWorkspace(workspaceName) == null )
        {
            throw new IllegalArgumentException("The workspace " + workspaceName + " was not found");
        }
        this.workspaceName = workspaceName;
        this.session = session;
        this.nodes=new ArrayList<SemanticObject>();
        for(SemanticObject id : nodes)
        {
            this.nodes.add(id);
        }
    }
    QueryResultImp(SessionImp session, List<SemanticObject> nodes, String workspaceName)
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
        for ( SemanticObject object : nodes )
        { 
            BaseNode node=new BaseNode(object);
            if(session.existSimpleNodeByID(node.getId()))
            {
                simpleNodes.add(session.getSimpleNodeByID(node.getId()));
            }
            else
            {
                SimpleNode simpleNode=new SimpleNode(node, session);
                session.addSimpleNode(simpleNode);
                simpleNodes.add(simpleNode);
            }
        }
        return new NodeIteratorImp(session, simpleNodes);
    }
}
