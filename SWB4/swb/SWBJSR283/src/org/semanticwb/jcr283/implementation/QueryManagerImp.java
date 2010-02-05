/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.qom.QueryObjectModelFactory;

/**
 *
 * @author victor.lorenzana
 */
public class QueryManagerImp implements QueryManager
{

    private final SessionImp session;

    public QueryManagerImp(SessionImp session)
    {
        this.session = session;
    }

    public Query createQuery(String statement, String language) throws InvalidQueryException, RepositoryException
    {
        if (!language.equalsIgnoreCase(QueryImp.SPARQL))
        {
            throw new InvalidQueryException("This implementation only accepts XPATH language");
        }
        return new QueryImp(session, statement, language);
    }

    public QueryObjectModelFactory getQOMFactory()
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
