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

// TODO: Auto-generated Javadoc
/**
 * Objeto que sobrescribe la clase Statement para poder tener control la misma desde el Pool de conexiones.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class PoolStatement implements java.sql.Statement
{
    
    /** The st. */
    Statement st;
    
    /** The closed. */
    boolean closed = false;

    /** The con. */
    Connection con;

    /**
     * Creates a new instance of PoolStatement.
     * 
     * @param st the st
     * @param con the con
     */
    public PoolStatement(Statement st, Connection con)
    {
        this.st = st;
        this.con=con;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#addBatch(java.lang.String)
     */
    public void addBatch(String str) throws java.sql.SQLException
    {
        st.addBatch(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#cancel()
     */
    public void cancel() throws java.sql.SQLException
    {
        st.cancel();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearBatch()
     */
    public void clearBatch() throws java.sql.SQLException
    {
        st.clearBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearWarnings()
     */
    public void clearWarnings() throws java.sql.SQLException
    {
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
        return st.execute(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeBatch()
     */
    public int[] executeBatch() throws java.sql.SQLException
    {
        return st.executeBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeQuery(java.lang.String)
     */
    public java.sql.ResultSet executeQuery(String str) throws java.sql.SQLException
    {
        return st.executeQuery(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    public int executeUpdate(String str) throws java.sql.SQLException
    {
        return st.executeUpdate(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getConnection()
     */
    public java.sql.Connection getConnection() throws java.sql.SQLException
    {
        return con;
        //return st.getConnection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchDirection()
     */
    public int getFetchDirection() throws java.sql.SQLException
    {
        return st.getFetchDirection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchSize()
     */
    public int getFetchSize() throws java.sql.SQLException
    {
        return st.getFetchSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxFieldSize()
     */
    public int getMaxFieldSize() throws java.sql.SQLException
    {
        return st.getMaxFieldSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxRows()
     */
    public int getMaxRows() throws java.sql.SQLException
    {
        return st.getMaxRows();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults()
     */
    public boolean getMoreResults() throws java.sql.SQLException
    {
        return st.getMoreResults();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getQueryTimeout()
     */
    public int getQueryTimeout() throws java.sql.SQLException
    {
        return st.getQueryTimeout();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSet()
     */
    public java.sql.ResultSet getResultSet() throws java.sql.SQLException
    {
        return st.getResultSet();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetConcurrency()
     */
    public int getResultSetConcurrency() throws java.sql.SQLException
    {
        return st.getResultSetConcurrency();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetType()
     */
    public int getResultSetType() throws java.sql.SQLException
    {
        return st.getResultSetType();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getUpdateCount()
     */
    public int getUpdateCount() throws java.sql.SQLException
    {
        return st.getUpdateCount();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getWarnings()
     */
    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException
    {
        return st.getWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setCursorName(java.lang.String)
     */
    public void setCursorName(String str) throws java.sql.SQLException
    {
        st.setCursorName(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     */
    public void setEscapeProcessing(boolean param) throws java.sql.SQLException
    {
        st.setEscapeProcessing(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchDirection(int)
     */
    public void setFetchDirection(int param) throws java.sql.SQLException
    {
        st.setFetchDirection(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchSize(int)
     */
    public void setFetchSize(int param) throws java.sql.SQLException
    {
        st.setFetchSize(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxFieldSize(int)
     */
    public void setMaxFieldSize(int param) throws java.sql.SQLException
    {
        st.setMaxFieldSize(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxRows(int)
     */
    public void setMaxRows(int param) throws java.sql.SQLException
    {
        st.setMaxRows(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setQueryTimeout(int)
     */
    public void setQueryTimeout(int param) throws java.sql.SQLException
    {
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
        return st.execute(str, str1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    public boolean execute(String str, int[] values) throws java.sql.SQLException
    {
        return st.execute(str, values);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    public boolean execute(String str, int param) throws java.sql.SQLException
    {
        return st.execute(str, param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws java.sql.SQLException
    {
        return st.getResultSetHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int param) throws java.sql.SQLException
    {
        return st.getMoreResults(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    public int executeUpdate(String str, String[] str1) throws java.sql.SQLException
    {
        return st.executeUpdate(str, str1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    public int executeUpdate(String str, int param) throws java.sql.SQLException
    {
        return st.executeUpdate(str, param);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    public int executeUpdate(String str, int[] values) throws java.sql.SQLException
    {
        return st.executeUpdate(str, values);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public java.sql.ResultSet getGeneratedKeys() throws java.sql.SQLException
    {
        return st.getGeneratedKeys();
    }

//********************************** version 1.6
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

    /* MAPS74 - JDK 7 SE Wrapper */
    @Override
    public void closeOnCompletion() throws SQLException
    {
        st.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException
    {
        return isCloseOnCompletion();
    }

}
