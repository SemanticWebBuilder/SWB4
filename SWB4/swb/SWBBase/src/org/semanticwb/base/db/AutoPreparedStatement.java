/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
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

// TODO: Auto-generated Javadoc
/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class AutoPreparedStatement implements java.sql.PreparedStatement {

    /** The st. */
    private PreparedStatement st;
    
    /** The closed. */
    private boolean closed = false;
    
    /** The query. */
    private String query = null;
    
    /** The args. */
    private String args = "";
    
    /** The debug. */
    private boolean debug = false;
    
    /** The aconn. */
    private AutoConnection aconn;
    
    /** The conn. */
    private Connection conn;
    
    /** The batchs. */
    private ArrayList<String> batchs;
    //private HashMap<Integer, Object> params;

    /**
     * Creates a new instance of PoolStatement.
     * 
     * @param aconn the aconn
     * @param query the query
     * @throws SQLException the sQL exception
     */
    public AutoPreparedStatement(AutoConnection aconn, String query) throws SQLException
    {
        batchs = new ArrayList();
        this.aconn = aconn;
        this.query = query;
        checkStatement();
    }

    /**
     * Check statement.
     * 
     * @throws SQLException the sQL exception
     */
    private void checkStatement() throws SQLException
    {
        if (!closed && aconn.getNativeConnection() != conn)
        {
            //MAPS74 if redundante
//            if (aconn.getNativeConnection() != conn)
//            {
                if (conn != null)
                {
                    System.out.println("Recreating PreparedStatement...");
                }
                conn = aconn.getNativeConnection();
                st = conn.prepareStatement(query);
                Iterator it = batchs.iterator();
                while (it.hasNext())
                {
                    st.addBatch((String) it.next());
                }
//            }
        }
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#addBatch(java.lang.String)
     */
    public void addBatch(String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("addBatch:" + str);
        }
        checkStatement();
        st.addBatch(str);
        batchs.add(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#cancel()
     */
    public void cancel() throws java.sql.SQLException
    {
        checkStatement();
        st.cancel();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearBatch()
     */
    public void clearBatch() throws java.sql.SQLException
    {
        checkStatement();
        st.clearBatch();
        batchs.clear();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearWarnings()
     */
    public void clearWarnings() throws java.sql.SQLException
    {
        checkStatement();
        st.clearWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#close()
     */
    public void close() throws java.sql.SQLException
    {
        closed = true;
        st.close();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String)
     */
    public boolean execute(String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("execute:" + str);
        }
        checkStatement();
        return st.execute(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeBatch()
     */
    public int[] executeBatch() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("*************************************");
            System.out.println("executeBatch():" + query);
            Iterator<String> it = batchs.iterator();
            while (it.hasNext())
            {
                String string = it.next();
                System.out.print(": " + string);
            }
        }
        checkStatement();
        return st.executeBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeQuery(java.lang.String)
     */
    public java.sql.ResultSet executeQuery(String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("executeQuery:" + str);
        }
        checkStatement();
        return st.executeQuery(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    public int executeUpdate(String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("executeUpdate:" + str);
        }
        checkStatement();
        return st.executeUpdate(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getConnection()
     */
    public java.sql.Connection getConnection() throws java.sql.SQLException
    {
        checkStatement();
        return conn;
        //return st.getConnection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchDirection()
     */
    public int getFetchDirection() throws java.sql.SQLException
    {
        checkStatement();
        return st.getFetchDirection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchSize()
     */
    public int getFetchSize() throws java.sql.SQLException
    {
        checkStatement();
        return st.getFetchSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxFieldSize()
     */
    public int getMaxFieldSize() throws java.sql.SQLException
    {
        checkStatement();
        return st.getMaxFieldSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxRows()
     */
    public int getMaxRows() throws java.sql.SQLException
    {
        checkStatement();
        return st.getMaxRows();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults()
     */
    public boolean getMoreResults() throws java.sql.SQLException
    {
        checkStatement();
        return st.getMoreResults();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getQueryTimeout()
     */
    public int getQueryTimeout() throws java.sql.SQLException
    {
        checkStatement();
        return st.getQueryTimeout();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSet()
     */
    public java.sql.ResultSet getResultSet() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("getResultSet");
        }
        checkStatement();
        return st.getResultSet();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetConcurrency()
     */
    public int getResultSetConcurrency() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSetConcurrency();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetType()
     */
    public int getResultSetType() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSetType();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getUpdateCount()
     */
    public int getUpdateCount() throws java.sql.SQLException
    {
        checkStatement();
        return st.getUpdateCount();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getWarnings()
     */
    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException
    {
        checkStatement();
        return st.getWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setCursorName(java.lang.String)
     */
    public void setCursorName(String str) throws java.sql.SQLException
    {
        checkStatement();
        st.setCursorName(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     */
    public void setEscapeProcessing(boolean param) throws java.sql.SQLException
    {
        checkStatement();
        st.setEscapeProcessing(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchDirection(int)
     */
    public void setFetchDirection(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setFetchDirection(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchSize(int)
     */
    public void setFetchSize(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setFetchSize(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxFieldSize(int)
     */
    public void setMaxFieldSize(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setMaxFieldSize(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxRows(int)
     */
    public void setMaxRows(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setMaxRows(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setQueryTimeout(int)
     */
    public void setQueryTimeout(int param) throws java.sql.SQLException
    {
        checkStatement();
        st.setQueryTimeout(param);
    }

    /**
     * Checks if is closed.
     * 
     * @return true, if is closed
     */
    public boolean isClosed()
    {
        return closed;
    }

//********************************** version 1.4
    /* (non-Javadoc)
 * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
 */
public boolean execute(String str, String[] str1) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("execute:" + str + " " + str1);
        }
        checkStatement();
        return st.execute(str, str1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    public boolean execute(String str, int[] values) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("execute:" + str + " " + values);
        }
        checkStatement();
        return st.execute(str, values);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    public boolean execute(String str, int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("execute:" + str + " " + param);
        }
        checkStatement();
        return st.execute(str, param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws java.sql.SQLException
    {
        checkStatement();
        return st.getResultSetHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int param) throws java.sql.SQLException
    {
        checkStatement();
        return st.getMoreResults(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    public int executeUpdate(String str, String[] str1) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("executeUpdate():" + str + " " + str1);
        }
        checkStatement();
        return st.executeUpdate(str, str1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    public int executeUpdate(String str, int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("executeUpdate():" + str + " " + param);
        }
        checkStatement();
        return st.executeUpdate(str, param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    public int executeUpdate(String str, int[] values) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("executeUpdate():" + str + " " + values);
        }
        checkStatement();
        return st.executeUpdate(str, values);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        checkStatement();
        return st.getGeneratedKeys();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException
    {
        if (debug)
        {
            System.out.println("executeQuery():" + query);
        }
        checkStatement();
        return st.executeQuery();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException
    {
        if (debug)
        {
            System.out.println("executeUpdate():" + query);
        }
        if (debug)
        {
            //time=System.currentTimeMillis();
            System.out.println("----------------------------------------------");
            System.out.println("--> execute():" + query);
            System.out.println("--> args:" + args);
            args = "";
            if (query.contains("Delete"))
            {
                new Exception().printStackTrace();
            }
        }
        checkStatement();
        return st.executeUpdate();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNull(int, int)
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + sqlType;
        }
        checkStatement();
        st.setNull(parameterIndex, sqlType);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBoolean(int, boolean)
     */
    public void setBoolean(int parameterIndex, boolean x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setBoolean(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setByte(int, byte)
     */
    public void setByte(int parameterIndex, byte x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setByte(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setShort(int, short)
     */
    public void setShort(int parameterIndex, short x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setShort(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setInt(int, int)
     */
    public void setInt(int parameterIndex, int x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setInt(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setLong(int, long)
     */
    public void setLong(int parameterIndex, long x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setLong(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setFloat(int, float)
     */
    public void setFloat(int parameterIndex, float x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setFloat(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setDouble(int, double)
     */
    public void setDouble(int parameterIndex, double x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setDouble(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBigDecimal(int, java.math.BigDecimal)
     */
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setBigDecimal(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(int parameterIndex, String x) throws SQLException
    {
//        if(x.equals("Uv::http://www.semanticwebbuilder.org/swb4/ontology#ResourceType:"))
//        {
//            new Exception().printStackTrace();
//            //Thread.dumpStack();
//        }
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setString(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBytes(int, byte[])
     */
    public void setBytes(int parameterIndex, byte[] x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setBytes(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
     */
    public void setDate(int parameterIndex, Date x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setDate(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
     */
    public void setTime(int parameterIndex, Time x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setTime(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException
    {
        if (debug)
        {
            args += " " + parameterIndex + " " + x;
        }
        checkStatement();
        st.setTimestamp(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, int)
     */
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException
    {
        checkStatement();
        st.setAsciiStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream, int)
     */
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException
    {
        checkStatement();
        st.setUnicodeStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, int)
     */
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException
    {
        checkStatement();
        st.setBinaryStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#clearParameters()
     */
    public void clearParameters() throws SQLException
    {
        checkStatement();
        st.clearParameters();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int, int)
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException
    {
        checkStatement();
        st.setObject(parameterIndex, x, targetSqlType, scale);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int)
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException
    {
        checkStatement();
        st.setObject(parameterIndex, x, targetSqlType);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(int parameterIndex, Object x) throws SQLException
    {
        checkStatement();
        st.setObject(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#execute()
     */
    public boolean execute() throws SQLException
    {
        checkStatement();
        boolean ret;
        long time = 0;
        if (debug)
        {
            time = System.currentTimeMillis();
            System.out.println("----------------------------------------------");
            System.out.println("--> execute():" + query);
            System.out.println("--> args:" + args);
            args = "";
            new Exception().printStackTrace();
        }

        try
        {
            ret = st.execute();
        } catch (SQLException se)
        {
            //System.out.println("SQLEx:"+se.getMessage());
            if (aconn.checkConnection())
            {
                System.out.println("P1");
                checkStatement();
                ret = st.execute();
            } else if (se.getMessage().indexOf("SocketException") > -1)
            {
                System.out.println("P2");
                aconn.changeConnection();
                checkStatement();
                ret = st.execute();
            } else
            {
                System.out.println("P3");
                throw se;
            }
        }

        if (debug)
        {
            System.out.println("------------------" + (System.currentTimeMillis() - time) + "-----------------------");
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#addBatch()
     */
    public void addBatch() throws SQLException
    {
        checkStatement();
        st.addBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, int)
     */
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException
    {
        checkStatement();
        st.setCharacterStream(parameterIndex, reader, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
     */
    public void setRef(int i, Ref x) throws SQLException
    {
        checkStatement();
        st.setRef(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
     */
    public void setBlob(int i, Blob x) throws SQLException
    {
        checkStatement();
        st.setBlob(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
     */
    public void setClob(int i, Clob x) throws SQLException
    {
        checkStatement();
        st.setClob(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setArray(int, java.sql.Array)
     */
    public void setArray(int i, Array x) throws SQLException
    {
        checkStatement();
        st.setArray(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#getMetaData()
     */
    public ResultSetMetaData getMetaData() throws SQLException
    {
        checkStatement();
        return st.getMetaData();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setDate(int, java.sql.Date, java.util.Calendar)
     */
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException
    {
        checkStatement();
        st.setDate(parameterIndex, x, cal);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTime(int, java.sql.Time, java.util.Calendar)
     */
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException
    {
        checkStatement();
        st.setTime(parameterIndex, x, cal);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp, java.util.Calendar)
     */
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException
    {
        checkStatement();
        st.setTimestamp(parameterIndex, x, cal);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
     */
    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException
    {
        checkStatement();
        st.setNull(paramIndex, sqlType, typeName);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
     */
    public void setURL(int parameterIndex, URL x) throws SQLException
    {
        checkStatement();
        st.setURL(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#getParameterMetaData()
     */
    public ParameterMetaData getParameterMetaData() throws SQLException
    {
        checkStatement();
        return st.getParameterMetaData();
    }
//********************************** version 1.6
/*
    public void setPoolable(boolean poolable) throws SQLException {
    checkStatement();
    st.setPoolable(poolable);
    }

    public boolean isPoolable() throws SQLException {
    checkStatement();
    return st.isPoolable();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
    checkStatement();
    return st.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
    checkStatement();
    return st.isWrapperFor(iface);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException
    {
    checkStatement();
    st.setAsciiStream(parameterIndex, x, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException
    {
    checkStatement();
    st.setAsciiStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException
    {
    checkStatement();
    st.setBinaryStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException
    {
    checkStatement();
    st.setBinaryStream(parameterIndex, x);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException
    {
    checkStatement();
    st.setBlob(parameterIndex, inputStream, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException
    {
    checkStatement();
    st.setBlob(parameterIndex, inputStream);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException
    {
    checkStatement();
    st.setCharacterStream(parameterIndex, reader, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException
    {
    checkStatement();
    st.setCharacterStream(parameterIndex, reader);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException
    {
    checkStatement();
    st.setClob(parameterIndex, reader, length);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException
    {
    checkStatement();
    st.setClob(parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException
    {
    checkStatement();
    st.setNCharacterStream(parameterIndex, value, length);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException
    {
    checkStatement();
    st.setNCharacterStream(parameterIndex, value);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException
    {
    checkStatement();
    st.setNClob(parameterIndex, value);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException
    {
    checkStatement();
    st.setNClob(parameterIndex, reader, length);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException
    {
    checkStatement();
    st.setNClob(parameterIndex, reader);
    }

    public void setNString(int parameterIndex, String value) throws SQLException
    {
    checkStatement();
    st.setNString(parameterIndex, value);
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException
    {
    checkStatement();
    st.setRowId(parameterIndex, x);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException
    {
    checkStatement();
    st.setSQLXML(parameterIndex, xmlObject);
    }




    //*/
}
