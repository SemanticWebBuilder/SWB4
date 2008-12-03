/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;


import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;

/**
 *
 * @author victor.lorenzana
 */
public final class QueryManagerImp implements QueryManager
{
    static Logger log=SWBUtils.getLogger(QueryManagerImp.class);
    private final String workspaceName;
    private final SessionImp session;
    QueryManagerImp(SessionImp session,String workspaceName)
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
        this.session=session;
    }
    public Query createQuery(String statement, String language) throws InvalidQueryException, RepositoryException
    {
        if(!(language.equals(Query.XPATH) || language.endsWith(QueryImp.SPARQL)))
        {
            throw new InvalidQueryException("This implementation only accepts XPATH language");
        }
        return new QueryImp(session, workspaceName, statement, language);
    }

    public Query getQuery(Node node) throws InvalidQueryException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getSupportedQueryLanguages() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
