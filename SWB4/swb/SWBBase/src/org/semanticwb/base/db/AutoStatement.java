
/*
 * PoolStatement.java
 *
 * Created on 20 de febrero de 2003, 12:43
 */

package org.semanticwb.base.db;

import java.net.SocketException;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class AutoStatement implements java.sql.Statement
{
    private static Logger log=SWBUtils.getLogger(AutoStatement.class);

    Statement st;
    AutoConnection aconn;
    Connection conn;
    boolean closed = false;

    Integer param;
    Integer param1;


    /** Creates a new instance of PoolStatement
     * @param st 
     */
    public AutoStatement(AutoConnection aconn) throws SQLException
    {
        this.aconn=aconn;
        checkStatement();
    }

    public AutoStatement(AutoConnection aconn, int param, int param1) throws SQLException
    {
        this.aconn=aconn;
        this.param=param;
        this.param1=param1;
        checkStatement();
    }

    private void checkStatement() throws SQLException
    {
        if(!closed)
        {
            if(aconn.getNativeConnection()!=conn)
            {
                if(conn!=null)System.out.println("Recreating Statement...");
                conn=aconn.getNativeConnection();
                if(param!=null && param1!=null)
                {
                    st=conn.createStatement(param,param1);
                }else
                {
                    st=conn.createStatement();
                }
            }
        }
    }

    public void addBatch(String str) throws java.sql.SQLException
    {
        checkStatement();
        st.addBatch(str);
    }

    public void cancel() throws java.sql.SQLException
    {
        checkStatement();
        st.cancel();
    }

    public void clearBatch() throws java.sql.SQLException
    {
        checkStatement();
        st.clearBatch();
    }

    public void clearWarnings() throws java.sql.SQLException
    {
        checkStatement();
        st.clearWarnings();
    }

    public void close() throws java.sql.SQLException
    {
        closed = true;
        st.close();
    }

    public boolean execute(String str) throws java.sql.SQLException
    {
        checkStatement();
        return st.execute(str);
    }

    public int[] executeBatch() throws java.sql.SQLException
    {
        checkStatement();
        return st.executeBatch();
    }

    public java.sql.ResultSet executeQuery(String str) throws java.sql.SQLException
    {
        checkStatement();
        try
        {
            return st.executeQuery(str);
        }catch(SQLException se)
        {
            if(aconn.checkConnection())
            {
                checkStatement();
                return st.executeQuery(str);
            }else
            {
                throw se;
            }
        }
    }

    public int executeUpdate(String str) throws java.sql.SQLException
    {
        checkStatement();
        return st.executeUpdate(str);
    }

    public java.sql.Connection getConnection() throws java.sql.SQLException
    {
        checkStatement();
        return st.getConnection();
    }

    public int getFetchDirection() throws java.sql.SQLException
    {
        checkStatement();
        return st.getFetchDirection();
    }

    public int getFetchSize() throws java.sql.SQLException
    {
        checkStatement();
        return st.getFetchSize();
    }

    public int getMaxFieldSize() throws java.sql.SQLException
    {
        checkStatement();
        return st.getMaxFieldSize();
    }

    public int getMaxRows() throws java.sql.SQLException
    {
        checkStatement();
        return st.getMaxRows();
    }

    public boolean getMoreResults() throws java.sql.SQLException
    {
        checkStatement();
        return st.getMoreResults();
    }

    public int getQueryTimeout() throws java.sql.SQLException
    {
        checkStatement();
        return st.getQueryTimeout();
    }

    public java.sql.ResultSet getResultSet() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSet();
    }

    public int getResultSetConcurrency() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSetConcurrency();
    }

    public int getResultSetType() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSetType();
    }

    public int getUpdateCount() throws java.sql.SQLException
    {
        checkStatement();
        return st.getUpdateCount();
    }

    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException
    {
        checkStatement();
        return st.getWarnings();
    }

    public void setCursorName(String str) throws java.sql.SQLException
    {
        checkStatement();
        st.setCursorName(str);
    }

    public void setEscapeProcessing(boolean param) throws java.sql.SQLException
    {
        checkStatement();
        st.setEscapeProcessing(param);
    }

    public void setFetchDirection(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setFetchDirection(param);
    }

    public void setFetchSize(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setFetchSize(param);
    }

    public void setMaxFieldSize(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setMaxFieldSize(param);
    }

    public void setMaxRows(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setMaxRows(param);
    }

    public void setQueryTimeout(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setQueryTimeout(param);
    }

    public boolean isClosed()
    {
        return closed;
    }

//********************************** version 1.4
    public boolean execute(String str, String[] str1) throws java.sql.SQLException
    {
        checkStatement();
        return st.execute(str, str1);
    }

    public boolean execute(String str, int[] values) throws java.sql.SQLException
    {
        checkStatement();
        return st.execute(str, values);
    }

    public boolean execute(String str, int param) throws java.sql.SQLException
    {
        checkStatement();
        return st.execute(str, param);
    }

    public int getResultSetHoldability() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSetHoldability();
    }

    public boolean getMoreResults(int param) throws java.sql.SQLException
    {
        checkStatement();
        return st.getMoreResults(param);
    }

    public int executeUpdate(String str, String[] str1) throws java.sql.SQLException
    {
        checkStatement();
        return st.executeUpdate(str, str1);
    }

    public int executeUpdate(String str, int param) throws java.sql.SQLException
    {
        checkStatement();
        return st.executeUpdate(str, param);
    }

    public int executeUpdate(String str, int[] values) throws java.sql.SQLException
    {
        checkStatement();
        return st.executeUpdate(str, values);
    }

    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        checkStatement();
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
