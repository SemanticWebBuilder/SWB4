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

import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class AutoStatement implements java.sql.Statement {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(AutoStatement.class);
    
    /** The debug. */
    private boolean debug = false;
    
    /** The st. */
    Statement st;
    
    /** The aconn. */
    AutoConnection aconn;
    
    /** The conn. */
    Connection conn;
    
    /** The closed. */
    boolean closed = false;
    
    /** The param. */
    Integer param;
    
    /** The param1. */
    Integer param1;

    /**
     * Creates a new instance of PoolStatement.
     * 
     * @param aconn the aconn
     * @throws SQLException the sQL exception
     */
    public AutoStatement(AutoConnection aconn) throws SQLException
    {
        this.aconn = aconn;
        checkStatement();
    }

    /**
     * Instantiates a new auto statement.
     * 
     * @param aconn the aconn
     * @param param the param
     * @param param1 the param1
     * @throws SQLException the sQL exception
     */
    public AutoStatement(AutoConnection aconn, int param, int param1) throws SQLException
    {
        this.aconn = aconn;
        this.param = param;
        this.param1 = param1;
        checkStatement();
    }

    /**
     * Check statement.
     * 
     * @throws SQLException the sQL exception
     */
    private void checkStatement() throws SQLException
    {
        if (debug)
        {
            System.out.println("s.checkStatement");
        }
        if (!closed && aconn.getNativeConnection() != conn)
        {
            //MAPS74 if redundante
//            if (aconn.getNativeConnection() != conn)
//            {
                if (conn != null)
                {
                    System.out.println("Recreating Statement...");
                }
                conn = aconn.getNativeConnection();
                if (param != null && param1 != null)
                {
                    st = conn.createStatement(param, param1);
                } else
                {
                    st = conn.createStatement();
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
            System.out.println("s.addBatch");
        }
        checkStatement();
        st.addBatch(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#cancel()
     */
    public void cancel() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.cancel");
        }
        checkStatement();
        st.cancel();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearBatch()
     */
    public void clearBatch() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.clearBatch");
        }
        checkStatement();
        st.clearBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearWarnings()
     */
    public void clearWarnings() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.clearWarnings");
        }
        checkStatement();
        st.clearWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#close()
     */
    public void close() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.close");
        }
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
            System.out.println("s.execute");
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
            System.out.println("s.executeBatch():");
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
            //System.out.println("s.executeQuery:" + str);
            System.out.println("s.executeQuery");
        }
        checkStatement();
        try
        {
            return st.executeQuery(str);
        } catch (SQLException se)
        {
            System.out.println("Error conexion dañada..., "+se);
            if(se.getMessage().contains("java.net.SocketException"))
            {
                aconn.changeConnection();
                checkStatement();
                return st.executeQuery(str);
            }else if (aconn.checkConnection())
            {
                checkStatement();
                return st.executeQuery(str);
            } else
            {
                throw se;
            }
        }
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    public int executeUpdate(String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.executeUpdate:" + str);
        }
        checkStatement();
        return st.executeUpdate(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getConnection()
     */
    public java.sql.Connection getConnection() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getConnection");
        }
        checkStatement();
        return st.getConnection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchDirection()
     */
    public int getFetchDirection() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getFetchDirection");
        }
        checkStatement();
        return st.getFetchDirection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchSize()
     */
    public int getFetchSize() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getFetchSize");
        }
        checkStatement();
        return st.getFetchSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxFieldSize()
     */
    public int getMaxFieldSize() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getMaxFieldSize");
        }
        checkStatement();
        return st.getMaxFieldSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxRows()
     */
    public int getMaxRows() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getMaxRows");
        }
        checkStatement();
        return st.getMaxRows();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults()
     */
    public boolean getMoreResults() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getMoreResults");
        }
        checkStatement();
        return st.getMoreResults();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getQueryTimeout()
     */
    public int getQueryTimeout() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getQueryTimeout");
        }
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
            System.out.println("s.getResultSet");
        }
        checkStatement();
        return st.getResultSet();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetConcurrency()
     */
    public int getResultSetConcurrency() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getResultSetConcurrency");
        }
        checkStatement();
        return st.getResultSetConcurrency();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetType()
     */
    public int getResultSetType() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getResultSetType");
        }
        checkStatement();
        return st.getResultSetType();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getUpdateCount()
     */
    public int getUpdateCount() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getUpdateCount");
        }
        checkStatement();
        return st.getUpdateCount();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getWarnings()
     */
    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getWarnings");
        }
        checkStatement();
        return st.getWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setCursorName(java.lang.String)
     */
    public void setCursorName(String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setCursorName");
        }
        checkStatement();
        st.setCursorName(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     */
    public void setEscapeProcessing(boolean param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setEscapeProcessing");
        }
        checkStatement();
        st.setEscapeProcessing(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchDirection(int)
     */
    public void setFetchDirection(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setFetchDirection");
        }
        checkStatement();
        st.setFetchDirection(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchSize(int)
     */
    public void setFetchSize(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setFetchSize");
        }
        checkStatement();
        st.setFetchSize(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxFieldSize(int)
     */
    public void setMaxFieldSize(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setMaxFieldSize");
        }
        checkStatement();
        st.setMaxFieldSize(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxRows(int)
     */
    public void setMaxRows(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setMaxRows");
        }
        checkStatement();
        st.setMaxRows(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setQueryTimeout(int)
     */
    public void setQueryTimeout(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.setQueryTimeout");
        }
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
            System.out.println("s.execute");
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
            System.out.println("s.execute");
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
            System.out.println("s.execute");
        }
        checkStatement();
        return st.execute(str, param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getResultSetHoldability");
        }
        checkStatement();
        return st.getResultSetHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getMoreResults");
        }
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
            System.out.println("s.executeUpdate");
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
            System.out.println("s.executeUpdate");
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
            System.out.println("s.executeUpdate");
        }
        checkStatement();
        return st.executeUpdate(str, values);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("s.getGeneratedKeys");
        }
        checkStatement();
        return st.getGeneratedKeys();
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

    //*/
}
