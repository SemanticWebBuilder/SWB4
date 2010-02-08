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
import javax.jcr.query.QueryResult;
import javax.jcr.query.qom.Column;
import javax.jcr.query.qom.Constraint;
import javax.jcr.query.qom.Ordering;
import javax.jcr.query.qom.QueryObjectModel;
import javax.jcr.query.qom.Source;
import javax.jcr.version.VersionException;

/**
 *
 * @author victor.lorenzana
 */
public final class QueryObjectModelImp implements QueryObjectModel {

    private final SessionImp session;
    public QueryObjectModelImp(SessionImp session)
    {
        this.session=session;
    }
    public Source getSource()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Constraint getConstraint()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Ordering[] getOrderings()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Column[] getColumns()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public QueryResult execute() throws InvalidQueryException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLanguage()
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
