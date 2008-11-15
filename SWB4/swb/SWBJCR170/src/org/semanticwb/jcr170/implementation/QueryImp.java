/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.List;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.semanticwb.model.SWBContext;
import org.semanticwb.repository.Workspace;

/**
 *
 * @author victor.lorenzana
 */
public class QueryImp implements Query
{
    private final String workspaceName;
    private final String statement;
    private final String language;
    private final SessionImp session;
    private final XPath xpath;
    QueryImp(SessionImp session,String workspaceName,String statement,String language)
    {
        if(session==null || workspaceName==null)
        {
            throw new IllegalArgumentException("the session or workspaceName was null");
        }
        if(SWBContext.getWorkspace(workspaceName)==null)
        {
            throw new IllegalArgumentException("The workspace "+  workspaceName +" was not found");
        }
        this.workspaceName=workspaceName;
        this.statement=statement;
        this.language=language;
        this.session=session;
        try
        {
            xpath=XPath.newInstance(statement);
            for(String prefix : Workspace.vocabulary.listUris().keySet())
            {
                String namespace=Workspace.vocabulary.listUris().get(prefix);
                if(namespace.endsWith("#"))
                {
                    namespace=namespace.substring(0,namespace.length()-1);
                }
                Namespace ns=Namespace.getNamespace(prefix, namespace);
                xpath.addNamespace(ns);
            }
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
    public QueryResult execute() throws RepositoryException
    {
        Workspace workspace=SWBContext.getWorkspace(this.workspaceName);
        try
        {   
            List nodes=xpath.selectNodes(workspace.getDocumentInternalView());
            return new QueryResultImp(session,nodes,workspaceName);
        }
        catch(JDOMException jde)
        {
            throw new RepositoryException(jde);
        }
        catch(Exception jde)
        {
            throw new RepositoryException(jde);
        }    
        catch(Throwable jde)
        {
            throw new RepositoryException(jde);
        }
    }

    public String getStatement()
    {
        return statement;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getStoredQueryPath() throws ItemNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node storeAsNode(String arg0) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
