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
public final class QueryManagerImp implements QueryManager
{
    private final QueryObjectModelFactoryImp factory;
    private static final String[] supportedQueryLanguages;
    static
    {
        supportedQueryLanguages=new String[1];
        supportedQueryLanguages[0]=SPARQLQuery.SPARQL;
    }
    private final SessionImp session;

    public QueryManagerImp(SessionImp session)
    {
        this.session = session;
        factory=new QueryObjectModelFactoryImp(session);
    }

    public Query createQuery(String statement, String language) throws InvalidQueryException, RepositoryException
    {
        if (language.equalsIgnoreCase(SPARQLQuery.SPARQL))
        {
            return new SPARQLQuery(session, statement);
        }
        if (language.equalsIgnoreCase(Query.JCR_JQOM))
        {
            return new JQOMQuery(session, statement);
        }
        throw new RepositoryException("The language "+language+" is not supported");
        
    }

    public QueryObjectModelFactory getQOMFactory()
    {
        return factory;
    }

    public Query getQuery(Node node) throws InvalidQueryException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getSupportedQueryLanguages() throws RepositoryException
    {
        return supportedQueryLanguages;
    }
}
