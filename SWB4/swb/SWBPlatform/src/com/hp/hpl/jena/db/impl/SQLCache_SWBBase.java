package com.hp.hpl.jena.db.impl;

import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.RDFRDBException;
import java.sql.*;
import java.util.*;

import com.hp.hpl.jena.shared.JenaException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLCache_SWBBase extends SQLCache
{
    static protected Logger logger = LoggerFactory.getLogger( SQLCache_SWBBase.class );

    /**
     * Constructor. Creates a new cache sql statements for interfacing to
     * a specific database.
     * @param sqlFile the name of the file of sql statements to load, this is
     * loaded from the classpath.
     * @param defaultOps Properties table which provides the default
     * sql statements, any definitions of a given operation in the loaded file
     * will override the default.
     * @param connection the jdbc connection to the database itself
     * @param idType the sql string to use for id types (substitutes for $id in files)
     */
    public SQLCache_SWBBase(String sqlFile, Properties defaultOps, IDBConnection connection, String idType) throws IOException
    {
        super(sqlFile, defaultOps, connection, idType);
        m_preparedStatements=new ConcurrentHashMap();
        m_cachedStmtInUse=new ConcurrentHashMap();
    }

    /**
     * Flush the cache of all currently prepared statements.
     */
    @Override
    public void flushPreparedStatementCache() throws RDFRDBException {
        try {
            Iterator<List<PreparedStatement>> it = m_preparedStatements.values().iterator();
            while (it.hasNext()) {
                Iterator<PreparedStatement> psit = it.next().iterator();
                while (psit.hasNext()) {
                    psit.next().close();
                }
            }
        } catch (SQLException e) {
            throw new RDFRDBException("Problem flushing PS cache", e);
        } finally {
            m_preparedStatements = new ConcurrentHashMap();
	    m_cachedStmtInUse = new ConcurrentHashMap();
        }
    }

    /**
     * Return a prepared SQL statement corresponding to the named operation.
     * The statement should either be closed after use or returned to the
     * prepared statement pool using {@link #returnPreparedSQLStatement returnPreparedSQLStatement}
     *
     * <p>Only works for single statements, not compound statements.
     * @param con the jdbc connection to use for preparing statements
     * @param opname the name of the sql operation to locate
     * @return a prepared SQL statement appropriate for the JDBC connection
     * used when this SQLCache was constructed or null if there is no such
     * operation or no such connection
     *
     *
     */
    @Override
    public PreparedStatement getPreparedSQLStatement(String opname, String[] attr) throws SQLException
    {
        /* TODO extended calling format or statement format to support different
         * result sets and conconcurrency modes.
         */
        PreparedStatement ps = null;
        if (m_connection == null || opname == null) {
            return null;
        }
        int attrCnt = (attr == null) ? 0 : attr.length;
        String aop = opname;
        if (attrCnt > 0) {
            aop = concatOpName(aop, attr[0]);
        }
        if (attrCnt > 1) {
            aop = concatOpName(aop, attr[1]);
        }
        if (attrCnt > 2) {
            aop = concatOpName(aop, attr[2]);
        }
        if (attrCnt > 3) {
            throw new JenaException("Too many arguments");
        }

        List<PreparedStatement> psl = m_preparedStatements.get(aop);
        // OVERRIDE: added proper PreparedStatement removal.
        if(psl!=null)
        {
            synchronized(psl)
            {
                if (!psl.isEmpty()) {
                    ps = psl.remove(0);
                }
            }
            if(ps!=null)
            {
                try {
                    ps.clearParameters();
                } catch (SQLException e) {
                    ps.close();
                }
            }
        }
        if (ps == null) {
            String sql = getSQLStatement(opname, attr);
            if (sql == null) {
                throw new SQLException("No SQL defined for operation: " + opname);
            }
            if (psl == null && CACHE_PREPARED_STATEMENTS) {
                psl = new LinkedList<PreparedStatement>();
                m_preparedStatements.put(aop, psl);
            }
            ps = doPrepareSQLStatement(sql);
        }
        if (CACHE_PREPARED_STATEMENTS) {
            m_cachedStmtInUse.put(ps, psl);
        }
        return ps;
    }

    /**
     * Prepare a SQL statement for the given statement string.
     *
     * <p>Only works for single statements, not compound statements.
     * @param stmt the sql statement to prepare.
     * @return a prepared SQL statement appropriate for the JDBC connection
     * used when this SQLCache was constructed or null if there is no such
     * connection.
     */
    private PreparedStatement doPrepareSQLStatement(String sql) throws SQLException
    {
        if (m_connection == null) {
            return null;
        }
        return getConnection().prepareStatement(sql);
    }

    /**
     * Return a prepared SQL statement for the given statement string.
     * The statement should either be closed after use.
     *
     * <p>Only works for single statements, not compound statements.
     * @param stmt the sql statement to prepare.
     * @return a prepared SQL statement appropriate for the JDBC connection
     * used when this SQLCache was constructed or null if there is no such
     * connection.
     */
    @Override
    public PreparedStatement prepareSQLStatement(String sql) throws SQLException
    {
        if (m_connection == null) {
            return null;
        }
        return doPrepareSQLStatement(sql);
    }

    @Override
    public PreparedStatement getPreparedSQLStatement(String opname) throws SQLException
    {
        return getPreparedSQLStatement(opname, (String[]) null);
    }

    /**
     * Variant on {@link #getPreparedSQLStatement getPreparedSQLStatement} which
     * accesses the attribute variant correspond to the given attribute suffix.
     */
    @Override
    public PreparedStatement getPreparedSQLStatement(String opname, String attr) throws SQLException
    {
        String[] param = {attr};
        return getPreparedSQLStatement(opname, param);
    }

    /**
     * Variant on {@link #getPreparedSQLStatement getPreparedSQLStatement} which
     * access the attribute variant correspond to the given attribute suffix.
     */
    @Override
    public PreparedStatement getPreparedSQLStatement(String opname, String attrA, String attrB) throws SQLException
    {
        String[] param = {attrA, attrB};
        return getPreparedSQLStatement(opname, param);
    }

    /**
     * Return a prepared statement to the statement pool for reuse by
     * another caller. Any close problems logged rather than raising exception
     * so that iterator close() operations can be silent so that they can meet
     * the ClosableIterator signature.
     */
    @Override
    public void returnPreparedSQLStatement(PreparedStatement ps)
    {
        if (!CACHE_PREPARED_STATEMENTS) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.warn("Problem discarded prepared statement", e);
            }
            return;
        }
        List<PreparedStatement> psl = m_cachedStmtInUse.get(ps);
        if (psl != null) 
        {
            synchronized(psl)
            {
                if (psl.size() >= MAX_PS_CACHE) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        logger.warn("Problem discarded prepared statement", e);
                    }
                } else {
                    psl.add(ps);
                }
            }
            m_cachedStmtInUse.remove(ps);
        } else {
            throw new JenaException("Attempt to return unused prepared statement");
        }
    }
}
