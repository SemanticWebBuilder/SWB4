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
import org.jdom.Element;
import org.jdom.Namespace;
import org.semanticwb.SWBException;
import org.semanticwb.model.SWBContext;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.Workspace;

/**
 *
 * @author victor.lorenzana
 */
public class QueryResultImp implements QueryResult
{

    private final List nodes;
    private final String workspaceName;
    private final SessionImp session;
    private final String jcr_uri;
    private final Namespace ns;
    QueryResultImp(SessionImp session, List nodes, String workspaceName)
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
        jcr_uri = getUri(Workspace.vocabulary.listUris().get("jcr"));        
        ns = Namespace.getNamespace("jcr", jcr_uri);
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
        for ( int i = 0; i < nodes.size(); i++ )
        {
            Element eNode = ( Element ) nodes.get(i);
            String path = eNode.getAttributeValue("path");                        
            for(SimpleNode node : session.getSimpleNodeByPath(path,session.getSimpleRootNode(),true))
            {
                simpleNodes.add(node);            
            }
        }
        return new NodeIteratorImp(session, simpleNodes);
    }
}
