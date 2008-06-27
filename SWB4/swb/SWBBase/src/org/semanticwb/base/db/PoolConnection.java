
/*
 * PoolConnection.java
 *
 * Created on 22 de octubre de 2001, 16:46
 */

package org.semanticwb.base.db;

import java.sql.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Una conexi�n (sesi�n) con una base de datos. Sentencias SQL son ejecutadas y los resultados se
 * obtienen dentro del contexto de la conexi�n.
 *
 * Un objeto PoolConnection con una base de datos puede proporcionar la informaci�n que describe sus
 * tablas, soporta la gram�tica SQL, los procedimientos almacenados, las capacidades de esta conexi�n,
 * etc. Esta informaci�n se obtiene con el m�todo getMetaData().}
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 *
 * @see java.sql.Connection
 */
public class PoolConnection implements java.sql.Connection
{
    private static Logger log=SWBUtils.getLogger(PoolConnection.class);    
    
    private java.sql.Connection con = null;
    private Vector vec = new Vector();
    private DBConnectionPool pool;
    private boolean isclosed = false;
    private String description = "";
    private long id = 0;
    private long idle_time=0;
    private boolean destroy=false;

    /**
     * 
     * @param con
     * @param pool
     */
    public PoolConnection(Connection con, DBConnectionPool pool)
    {
        this.con = con;
        this.pool = pool;
        pool.checkedOut++;
        log.trace("PoolConnection("+getId()+","+pool.getName()+"):"+pool.checkedOut);
        init();
    }
    
    /**
     * 
     */
    public void init()
    {
        idle_time=System.currentTimeMillis();
        isclosed = false;
        description = "";
        id = 0;
    }
    

    /** Getter for property id.
     * @return Value of property id.
     *
     */
    public long getId()
    {
        return id;
    }

    /** Setter for property id.
     * @param id New value of property id.
     *
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public java.sql.Connection getNativeConnection()
    {
        return con;
    }

    /** Getter for property pool.
     * @return Value of property pool.
     *
     */
    public DBConnectionPool getPool()
    {
        return pool;
    }

    /** Getter for property description.
     * @return Value of property description.
     *
     */
    public java.lang.String getDescription()
    {
        return description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     *
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    /**
     * 
     * @return
     */
    public boolean closeStatements()
    {
        boolean noerrors = true;
        while (!vec.isEmpty())
        {
            PoolStatement st = (PoolStatement) vec.elementAt(0);
            if (st != null)
            {
                try
                {
                    if (!st.isClosed())
                    {
                        ResultSet rs = st.getResultSet();
                        if (rs != null)
                            rs.close();
                        st.close();
                        log.warn("Statement was not closed..., " + description);
                        noerrors = false;
                    }
                } catch (SQLException noe){/*Es correcto el error, ya que el susuario cerro la conexion*/}
            }
            vec.removeElementAt(0);
        }
        return noerrors;
    }

    /**
     * 
     * @throws java.sql.SQLException
     */
    public void _close() throws SQLException
    {
        destroyConnection();
    }

    /**
     * Cierra la conexi�n con la base de datos en vez de esperar. Una conexi�n puede ser cerrada
     * autom�ticamente cuando es garbage collected. Tambi�n ciertos errores fatales puden cerrar la
     * conexi�n.
     *
     * @exception java.sql.SQLException
     *              Si un error de acceso a kla base de datos ocurre.
     */
    public void close() throws SQLException
    {
        if(isclosed==false)
        {
            if(destroy)
            {
                try
                {
                    destroyConnection();
                    return;
                }catch(Exception e){log.error("Connection "+description+", close.setAutocomit(false):",e);}
            }
            isclosed=true;
            pool.getConnectionManager().getTimeLock().removeConnection(this);
            idle_time=System.currentTimeMillis();
            try
            {
                closeStatements();
            }catch(Exception e){log.error("Connection "+description+", closeStatement:",e);}
            try
            {
                pool.freeConnection(this);
            }catch(Exception e){log.error("Connection "+description+", freeConnection:",e);}
            log.trace("close:("+getId()+","+pool.getName()+"):"+pool.checkedOut);
        }
    }

    /**
     * Configura el modo auto-commit de la conexi�n en el estado determinado. Si una conexi�n est� en
     * auto-commit, entonces cada sentencia SQL ser� procesada y el commit se ejecutar� por cada una
     * como una transacci�n individual. De lo contrario, sus sentencias SQL se agrupan en una transacci�n
     * que finalizar� por una llamada al m�todo <code>commit</code> o al m�todo <code>rollback</code>.
     * Por default un nuevo objeto PoolConnection est� en modo auto-commit.
     *
     * @param param 
     * @exception java.sql.SQLException
     *              Si un error de acceso a kla base de datos ocurre.
     * @see       getAutoCommit()
     */
    public void setAutoCommit(boolean param) throws SQLException
    {
        destroy=true;
        con.setAutoCommit(param);
    }

    public SQLWarning getWarnings() throws SQLException
    {
        return con.getWarnings();
    }

    public String getCatalog() throws SQLException
    {
        return con.getCatalog();
    }

    public void setTypeMap(java.util.Map map) throws SQLException
    {
        destroy=true;
        con.setTypeMap(map);
    }

    public java.util.Map getTypeMap() throws SQLException
    {
        return con.getTypeMap();
    }

    public int getTransactionIsolation() throws SQLException
    {
        destroy=true;
        return con.getTransactionIsolation();
    }

    public boolean isReadOnly() throws SQLException
    {
        return con.isReadOnly();
    }

    public DatabaseMetaData getMetaData() throws SQLException
    {
        return con.getMetaData();
    }

    public void clearWarnings() throws SQLException
    {
        con.clearWarnings();
    }

    public String nativeSQL(String str) throws SQLException
    {
        return con.nativeSQL(str);
    }

    public PreparedStatement prepareStatement(String str, int param, int param2) throws SQLException
    {
        return con.prepareStatement(str, param, param2);
    }

    public void setTransactionIsolation(int param) throws SQLException
    {
        con.setTransactionIsolation(param);
    }

    public void setReadOnly(boolean param) throws SQLException
    {
        destroy=true;
        con.setReadOnly(param);
    }

    public void setCatalog(String str) throws SQLException
    {
        destroy=true;
        con.setCatalog(str);
    }

    public boolean isClosed() throws SQLException
    {
        return con.isClosed();
    }

    public Statement createStatement() throws SQLException
    {
        Statement st = new PoolStatement(con.createStatement());
        vec.addElement(st);
        //System.out.println("New Statement was Created...");
        return st;
    }

    public Statement createStatement(int param, int param1) throws SQLException
    {
        Statement st = new PoolStatement(con.createStatement(param, param1));
        vec.addElement(st);
        return st;
    }

    public PreparedStatement prepareStatement(String str) throws SQLException
    {
        return con.prepareStatement(str);
    }

    public boolean getAutoCommit() throws SQLException
    {
        return con.getAutoCommit();
    }

    public CallableStatement prepareCall(String str) throws SQLException
    {
        return con.prepareCall(str);
    }

    public void commit() throws SQLException
    {
        destroy=true;
        con.commit();
    }

    public CallableStatement prepareCall(String str, int param, int param2) throws SQLException
    {
        return con.prepareCall(str, param, param2);
    }

    public void rollback() throws SQLException
    {
        destroy=true;
        con.rollback();
    }
    
    /**
     * 
     */
    protected void destroyConnection()
    {
        if (isclosed == false)
        {
            isclosed=true;
            pool.checkedOut--;
            pool.getConnectionManager().getTimeLock().removeConnection(this);
            try
            {
                con.close();
            }catch(Exception e)
            {
                log.error("Connection "+description+" finalize:"+e);
            }
            log.trace("destroyConnection:("+getId()+","+pool.getName()+"):"+pool.checkedOut);
        }
    }    

    protected void finalize() throws Throwable
    {
        // We are no longer referenced by anyone (including the
        // connection pool). Time to close down.
        if(isclosed==false)
        {
            destroyConnection();
            log.warn("finalize()..., connection was not closed, "+description);
        }
    }
//************************************ jdk 1.4 *****************************************************************
    public java.sql.Savepoint setSavepoint() throws java.sql.SQLException
    {
        destroy=true;
        return con.setSavepoint();
        //return null;
    }

    public void setHoldability(int param) throws java.sql.SQLException
    {
        destroy=true;
        con.setHoldability(param);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int param) throws java.sql.SQLException
    {
        return con.prepareStatement(str, param);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int param, int param2, int param3) throws java.sql.SQLException
    {
        return con.prepareStatement(str, param, param2, param3);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int[] values) throws java.sql.SQLException
    {
        return con.prepareStatement(str, values);
    }

    public int getHoldability() throws java.sql.SQLException
    {
        return con.getHoldability();
    }

    public java.sql.Savepoint setSavepoint(java.lang.String str) throws java.sql.SQLException
    {
        destroy=true;
        return con.setSavepoint(str);
    }

    public java.sql.Statement createStatement(int param, int param1, int param2) throws java.sql.SQLException
    {
        return con.createStatement(param, param1, param2);
    }

    public java.sql.CallableStatement prepareCall(java.lang.String str, int param, int param2, int param3) throws java.sql.SQLException
    {
        return con.prepareCall(str, param, param2, param3);
    }

    public void releaseSavepoint(java.sql.Savepoint savepoint) throws java.sql.SQLException
    {
        con.releaseSavepoint(savepoint);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, java.lang.String[] str1) throws java.sql.SQLException
    {
        return con.prepareStatement(str, str1);
    }

    public void rollback(java.sql.Savepoint savepoint) throws java.sql.SQLException
    {
        destroy=true;
        con.rollback(savepoint);
    }
    
    /**
     * Getter for property idle_time.
     * @return Value of property idle_time.
     */
    public long getIdleTime()
    {
        return idle_time;
    }
    
//********************************* JAVA 1.5    

    public Clob createClob() throws SQLException {
        return con.createClob();
    }

    public Blob createBlob() throws SQLException {
        return con.createBlob();
    }

    public NClob createNClob() throws SQLException {
        return con.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return con.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        return con.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        con.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        con.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        return con.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        return con.getClientInfo();
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return con.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return con.createStruct(typeName, attributes);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return con.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return con.isWrapperFor(iface);
    }
    
   
//*/
}
