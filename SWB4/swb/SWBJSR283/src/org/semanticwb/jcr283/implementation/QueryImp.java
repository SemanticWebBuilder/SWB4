/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;

/**
 *
 * @author victor.lorenzana
 */
public abstract  class QueryImp implements Query
{   
    protected final String statement;
    protected final String language;
    protected final SessionImp session;

    public QueryImp(SessionImp session, String statement, String language)
    {
        this.session = session;
        this.statement = statement;
        this.language = language;        
    }

    public abstract QueryResult execute() throws InvalidQueryException, RepositoryException;
    

    public void setLimit(long limit)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOffset(long offset)
    {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public Node storeAsNode(String absPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void bindValue(String varName, Value value) throws IllegalArgumentException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getBindVariableNames() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
