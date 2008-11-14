
/*
 * PoolStatement.java
 *
 * Created on 20 de febrero de 2003, 12:43
 */

package org.semanticwb.base.db;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class PoolPreparedStatement implements java.sql.PreparedStatement
{
    PreparedStatement st;
    boolean closed = false;
    String query=null;
    String args="";
    boolean debug=false;

    /** Creates a new instance of PoolStatement
     * @param st 
     */
    public PoolPreparedStatement(PreparedStatement st)
    {
        this.st = st;
    }

    /** Creates a new instance of PoolStatement
     * @param st
     */
    public PoolPreparedStatement(PreparedStatement st, String query)
    {
        this.st = st;
        this.query=query;
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
        if(debug)System.out.println("execute:"+str);
        return st.execute(str);
    }

    public int[] executeBatch() throws java.sql.SQLException
    {
        if(debug)System.out.println("executeBatch():");
        return st.executeBatch();
    }

    public java.sql.ResultSet executeQuery(String str) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeQuery:"+str);
        return st.executeQuery(str);
    }

    public int executeUpdate(String str) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeUpdate:"+str);
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
        if(debug)System.out.println("getResultSet");
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
        if(debug)System.out.println("execute:"+str+" "+str1);
        return st.execute(str, str1);
    }

    public boolean execute(String str, int[] values) throws java.sql.SQLException
    {
        if(debug)System.out.println("execute:"+str+" "+values);
        return st.execute(str, values);
    }

    public boolean execute(String str, int param) throws java.sql.SQLException
    {
        if(debug)System.out.println("execute:"+str+" "+param);
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
        if(debug)System.out.println("executeUpdate():"+str+" "+str1);
        return st.executeUpdate(str, str1);
    }

    public int executeUpdate(String str, int param) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeUpdate():"+str+" "+param);
        return st.executeUpdate(str, param);
    }

    public int executeUpdate(String str, int[] values) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeUpdate():"+str+" "+values);
        return st.executeUpdate(str, values);
    }

    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        return st.getGeneratedKeys();
    }

    public ResultSet executeQuery() throws SQLException
    {
        if(debug)System.out.println("executeQuery():"+query);
        return st.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        if(debug)System.out.println("executeUpdate():"+query);
        return st.executeUpdate();
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+sqlType;
        st.setNull(parameterIndex, sqlType);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setBoolean(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setByte(parameterIndex, x);
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setShort(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setInt(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setLong(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setFloat(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setDouble(parameterIndex, x);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setBigDecimal(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setString(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setBytes(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setDate(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setTime(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        st.setTimestamp(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        st.setAsciiStream(parameterIndex, x, length);
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        st.setUnicodeStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        st.setBinaryStream(parameterIndex, x, length);
    }

    public void clearParameters() throws SQLException {
        st.clearParameters();
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        st.setObject(parameterIndex, x, targetSqlType, scale);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        st.setObject(parameterIndex, x, targetSqlType);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        st.setObject(parameterIndex, x);
    }

    public boolean execute() throws SQLException {
        boolean ret;
        long time=0;
        if(debug)
        {
            time=System.currentTimeMillis();
            System.out.println("----------------------------------------------");
            System.out.println("--> execute():"+query);
            System.out.println("--> args:"+args);
            args="";
        }
        ret=st.execute();
        if(debug)System.out.println("------------------"+(System.currentTimeMillis() - time)+"-----------------------");
        return ret;
    }

    public void addBatch() throws SQLException {
        st.addBatch();
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        st.setCharacterStream(parameterIndex, reader, length);
    }

    public void setRef(int i, Ref x) throws SQLException {
        st.setRef(i, x);
    }

    public void setBlob(int i, Blob x) throws SQLException {
        st.setBlob(i, x);
    }

    public void setClob(int i, Clob x) throws SQLException {
        st.setClob(i, x);
    }

    public void setArray(int i, Array x) throws SQLException {
        st.setArray(i, x);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return st.getMetaData();
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        st.setDate(parameterIndex, x, cal);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        st.setTime(parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        st.setNull(paramIndex, sqlType, typeName);
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        st.setURL(parameterIndex, x);
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return st.getParameterMetaData();
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
