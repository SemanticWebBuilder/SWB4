/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

/**
 *
 * @author victor.lorenzana
 */
public final class JQOMQuery extends QueryImp {

    public JQOMQuery(SessionImp session, String statement)
    {
        super(session, statement, Query.JCR_JQOM);
    }
    public QueryResult execute() throws InvalidQueryException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
