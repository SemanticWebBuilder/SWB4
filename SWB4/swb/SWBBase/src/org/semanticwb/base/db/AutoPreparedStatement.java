
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class AutoPreparedStatement implements java.sql.PreparedStatement
{
    private PreparedStatement st;
    private boolean closed = false;
    private String query=null;
    private String args="";
    private boolean debug=false;

    private AutoConnection aconn;
    private Connection conn;

    private ArrayList<String> batchs;
    private HashMap<Integer,Object> params;

    /** Creates a new instance of PoolStatement
     * @param st
     */
    public AutoPreparedStatement(AutoConnection aconn, String query) throws SQLException
    {
        batchs=new ArrayList();
        this.aconn=aconn;
        this.query=query;
        checkStatement();
    }

    private void checkStatement() throws SQLException
    {
        if(!closed)
        {
            if(aconn.getNativeConnection()!=conn)
            {
                if(conn!=null)System.out.println("Recreating PreparedStatement...");
                conn=aconn.getNativeConnection();
                st=conn.prepareStatement(query);
                Iterator it=batchs.iterator(); 
                while(it.hasNext())
                {
                    st.addBatch((String)it.next());
                }
            }
        }
    }


    public void addBatch(String str) throws java.sql.SQLException
    {
        checkStatement();
        st.addBatch(str);
        batchs.add(str);
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
        batchs.clear();
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
        if(debug)System.out.println("execute:"+str);
        checkStatement();
        return st.execute(str);
    }

    public int[] executeBatch() throws java.sql.SQLException
    {
        if(debug)System.out.println("executeBatch():");
        checkStatement();
        return st.executeBatch();
    }

    public java.sql.ResultSet executeQuery(String str) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeQuery:"+str);
        checkStatement();
        return st.executeQuery(str);
    }

    public int executeUpdate(String str) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeUpdate:"+str);
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
        if(debug)System.out.println("getResultSet");
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
        if(debug)System.out.println("execute:"+str+" "+str1);
        checkStatement();
        return st.execute(str, str1);
    }

    public boolean execute(String str, int[] values) throws java.sql.SQLException
    {
        if(debug)System.out.println("execute:"+str+" "+values);
        checkStatement();
        return st.execute(str, values);
    }

    public boolean execute(String str, int param) throws java.sql.SQLException
    {
        if(debug)System.out.println("execute:"+str+" "+param);
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
        if(debug)System.out.println("executeUpdate():"+str+" "+str1);
        checkStatement();
        return st.executeUpdate(str, str1);
    }

    public int executeUpdate(String str, int param) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeUpdate():"+str+" "+param);
        checkStatement();
        return st.executeUpdate(str, param);
    }

    public int executeUpdate(String str, int[] values) throws java.sql.SQLException
    {
        if(debug)System.out.println("executeUpdate():"+str+" "+values);
        checkStatement();
        return st.executeUpdate(str, values);
    }

    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        checkStatement();
        return st.getGeneratedKeys();
    }

    public ResultSet executeQuery() throws SQLException
    {
        if(debug)System.out.println("executeQuery():"+query);
        checkStatement();
        return st.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        if(debug)System.out.println("executeUpdate():"+query);
        checkStatement();
        return st.executeUpdate();
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+sqlType;
        checkStatement();
        st.setNull(parameterIndex, sqlType);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setBoolean(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setByte(parameterIndex, x);
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setShort(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setInt(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setLong(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setFloat(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setDouble(parameterIndex, x);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setBigDecimal(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setString(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setBytes(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setDate(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setTime(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        if(debug)args+=" "+parameterIndex+" "+x;
        checkStatement();
        st.setTimestamp(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        checkStatement();
        st.setAsciiStream(parameterIndex, x, length);
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        checkStatement();
        st.setUnicodeStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        checkStatement();
        st.setBinaryStream(parameterIndex, x, length);
    }

    public void clearParameters() throws SQLException {
        checkStatement();
        st.clearParameters();
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        checkStatement();
        st.setObject(parameterIndex, x, targetSqlType, scale);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        checkStatement();
        st.setObject(parameterIndex, x, targetSqlType);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        checkStatement();
        st.setObject(parameterIndex, x);
    }

    public boolean execute() throws SQLException {
        checkStatement();
        boolean ret;
        long time=0;
        if(debug)
        {
            time=System.currentTimeMillis();
            System.out.println("----------------------------------------------");
            System.out.println("--> execute():"+query);
            System.out.println("--> args:"+args);
            args="";
            //new Exception().printStackTrace();
        }

        try
        {
            ret=st.execute();
        }catch(SQLException se)
        {
            //System.out.println("SQLEx:"+se.getMessage());
            if(aconn.checkConnection())
            {
                System.out.println("P1");
                checkStatement();
                ret=st.execute();
            }else if(se.getMessage().indexOf("SocketException")>-1)
            {
                System.out.println("P2");
                aconn.changeConnection();
                checkStatement();
                ret=st.execute();
            }else
            {
                System.out.println("P3");
                throw se;
            }
        }

        if(debug)System.out.println("------------------"+(System.currentTimeMillis() - time)+"-----------------------");
        return ret;
    }

    public void addBatch() throws SQLException {
        checkStatement();
        st.addBatch();
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        checkStatement();
        st.setCharacterStream(parameterIndex, reader, length);
    }

    public void setRef(int i, Ref x) throws SQLException {
        checkStatement();
        st.setRef(i, x);
    }

    public void setBlob(int i, Blob x) throws SQLException {
        checkStatement();
        st.setBlob(i, x);
    }

    public void setClob(int i, Clob x) throws SQLException {
        checkStatement();
        st.setClob(i, x);
    }

    public void setArray(int i, Array x) throws SQLException {
        checkStatement();
        st.setArray(i, x);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        checkStatement();
        return st.getMetaData();
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        checkStatement();
        st.setDate(parameterIndex, x, cal);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        checkStatement();
        st.setTime(parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        checkStatement();
        st.setTimestamp(parameterIndex, x, cal);
    }

    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        checkStatement();
        st.setNull(paramIndex, sqlType, typeName);
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        checkStatement();
        st.setURL(parameterIndex, x);
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        checkStatement();
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
