
/*
 * PoolStatement.java
 *
 * Created on 20 de febrero de 2003, 12:43
 */

package org.semanticwb.base.db;

import java.sql.*;

/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class PoolStatement implements java.sql.Statement
{
    Statement st;
    boolean closed = false;

    /** Creates a new instance of PoolStatement
     * @param st 
     */
    public PoolStatement(Statement st)
    {
        this.st = st;
    }

    public void addBatch(String str) throws java.sql.SQLException
    {
        st.addBatch(str);
    }

    public void cancel() throws java.sql.SQLException
    {
        st.cancel();
    }

    public void clearBatch() throws java.sql.SQLException
    {
        st.clearBatch();
    }

    public void clearWarnings() throws java.sql.SQLException
    {
        st.clearWarnings();
    }

    public void close() throws java.sql.SQLException
    {
        closed = true;
        st.close();
    }

    public boolean execute(String str) throws java.sql.SQLException
    {
        return st.execute(str);
    }

    public int[] executeBatch() throws java.sql.SQLException
    {
        return st.executeBatch();
    }

    public java.sql.ResultSet executeQuery(String str) throws java.sql.SQLException
    {
        return st.executeQuery(str);
    }

    public int executeUpdate(String str) throws java.sql.SQLException
    {
        return st.executeUpdate(str);
    }

    public java.sql.Connection getConnection() throws java.sql.SQLException
    {
        return st.getConnection();
    }

    public int getFetchDirection() throws java.sql.SQLException
    {
        return st.getFetchDirection();
    }

    public int getFetchSize() throws java.sql.SQLException
    {
        return st.getFetchSize();
    }

    public int getMaxFieldSize() throws java.sql.SQLException
    {
        return st.getMaxFieldSize();
    }

    public int getMaxRows() throws java.sql.SQLException
    {
        return st.getMaxRows();
    }

    public boolean getMoreResults() throws java.sql.SQLException
    {
        return st.getMoreResults();
    }

    public int getQueryTimeout() throws java.sql.SQLException
    {
        return st.getQueryTimeout();
    }

    public java.sql.ResultSet getResultSet() throws java.sql.SQLException
    {
        return st.getResultSet();
    }

    public int getResultSetConcurrency() throws java.sql.SQLException
    {
        return st.getResultSetConcurrency();
    }

    public int getResultSetType() throws java.sql.SQLException
    {
        return st.getResultSetType();
    }

    public int getUpdateCount() throws java.sql.SQLException
    {
        return st.getUpdateCount();
    }

    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException
    {
        return st.getWarnings();
    }

    public void setCursorName(String str) throws java.sql.SQLException
    {
        st.setCursorName(str);
    }

    public void setEscapeProcessing(boolean param) throws java.sql.SQLException
    {
        st.setEscapeProcessing(param);
    }

    public void setFetchDirection(int param) throws java.sql.SQLException
    {
        st.setFetchDirection(param);
    }

    public void setFetchSize(int param) throws java.sql.SQLException
    {
        st.setFetchSize(param);
    }

    public void setMaxFieldSize(int param) throws java.sql.SQLException
    {
        st.setMaxFieldSize(param);
    }

    public void setMaxRows(int param) throws java.sql.SQLException
    {
        st.setMaxRows(param);
    }

    public void setQueryTimeout(int param) throws java.sql.SQLException
    {
        st.setQueryTimeout(param);
    }

    public boolean isClosed()
    {
        return closed;
    }

//********************************** version 1.4
    public boolean execute(String str, String[] str1) throws java.sql.SQLException
    {
        return st.execute(str, str1);
    }

    public boolean execute(String str, int[] values) throws java.sql.SQLException
    {
        return st.execute(str, values);
    }

    public boolean execute(String str, int param) throws java.sql.SQLException
    {
        return st.execute(str, param);
    }

    public int getResultSetHoldability() throws java.sql.SQLException
    {
        return st.getResultSetHoldability();
    }

    public boolean getMoreResults(int param) throws java.sql.SQLException
    {
        return st.getMoreResults(param);
    }

    public int executeUpdate(String str, String[] str1) throws java.sql.SQLException
    {
        return st.executeUpdate(str, str1);
    }

    public int executeUpdate(String str, int param) throws java.sql.SQLException
    {
        return st.executeUpdate(str, param);
    }

    public int executeUpdate(String str, int[] values) throws java.sql.SQLException
    {
        return st.executeUpdate(str, values);
    }

    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        return st.getGeneratedKeys();
    }

//********************************** version 1.5    
/*
    public void setPoolable(boolean poolable) throws SQLException {
        st.setPoolable(poolable);
    }

    public boolean isPoolable() throws SQLException {
        return st.isPoolable();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return st.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return st.isWrapperFor(iface);
    }
*/
//*/
}
