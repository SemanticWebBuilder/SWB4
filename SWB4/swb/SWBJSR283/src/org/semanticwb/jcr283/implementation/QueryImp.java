/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
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
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author victor.lorenzana
 */
public class QueryImp implements Query
{

    public static final String SPARQL = "SPARQL";
    private static final String NL = "\r\n";
    private StringBuilder prefixStatement = new StringBuilder("");
    private final String statement;
    private final String language;
    private final SessionImp session;

    public QueryImp(SessionImp session, String statement, String language) throws RepositoryException
    {
        this.session = session;
        this.statement = statement;
        this.language = language;
        for (String prefix : session.getNamespacePrefixes())
        {
            String uri = session.getNamespaceURI(prefix);
            if (!uri.endsWith("#"))
            {
                uri += "#";
            }
            prefixStatement.append("PREFIX " + prefix + ": <" + uri + ">" + NL);
        }
    }

    public QueryResult execute() throws InvalidQueryException, RepositoryException
    {
        HashSet<SemanticObject> nodes = new HashSet<SemanticObject>();
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
