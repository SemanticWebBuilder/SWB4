/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.base.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis
 */
public class AutoConnection implements Connection
{
    private static Logger log=SWBUtils.getLogger(AutoConnection.class);

    private java.sql.Connection con = null;
    private DBConnectionPool pool;
    private String description = "";
    private long id = 0;
    private boolean isclosed=false;

    /**
     *
     * @param con
     * @param pool
     */
    public AutoConnection(Connection con, DBConnectionPool pool)
    {
        this.con = con;
        this.pool = pool;
        log.trace("AutoConnection("+getId()+","+pool.getName()+"):"+pool.checkedOut);
    }

    public boolean checkConnection()
    {
        boolean ret=false;
        if(!isclosed)
        {
            if(con!=null)
            {
                try
                {
                    boolean closed=con.isClosed();
                    if(closed)
                    {
                        changeConnection();
                        ret=true;
                    }
                }catch(SQLException e)
                {
                    log.error("SQLException:"+e.getMessage());
                    changeConnection();
                    ret=true;
                }
            }else
            {
                changeConnection();
            }
        }
        return ret;
    }

    public void changeConnection()
    {
        log.error("Error checking connection, Auto Reconnect...");
        con=pool.newNoPoolConnection();
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
     * Cierra la conexi�n con la base de datos en vez de esperar. Una conexi�n puede ser cerrada
     * autom�ticamente cuando es garbage collected. Tambi�n ciertos errores fatales puden cerrar la
     * conexi�n.
     *
     * @exception java.sql.SQLException
     *              Si un error de acceso a kla base de datos ocurre.
     */
    public void close() throws SQLException
    {
        isclosed=true;
        con.close();
        log.trace("close:("+getId()+","+pool.getName()+"):"+pool.checkedOut);
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
        checkConnection();
        con.setAutoCommit(param);
    }

    public SQLWarning getWarnings() throws SQLException
    {
        checkConnection();
        return con.getWarnings();
    }

    public String getCatalog() throws SQLException
    {
        checkConnection();
        return con.getCatalog();
    }

    public void setTypeMap(java.util.Map map) throws SQLException
    {
        checkConnection();
        con.setTypeMap(map);
    }

    public java.util.Map getTypeMap() throws SQLException
    {
        checkConnection();
        return con.getTypeMap();
    }

    public int getTransactionIsolation() throws SQLException
    {
        checkConnection();
        return con.getTransactionIsolation();
    }

    public boolean isReadOnly() throws SQLException
    {
        checkConnection();
        return con.isReadOnly();
    }

    public DatabaseMetaData getMetaData() throws SQLException
    {
        checkConnection();
        return con.getMetaData();
    }

    public void clearWarnings() throws SQLException
    {
        checkConnection();
        con.clearWarnings();
    }

    public String nativeSQL(String str) throws SQLException
    {
        checkConnection();
        return con.nativeSQL(str);
    }

    public PreparedStatement prepareStatement(String str, int param, int param2) throws SQLException
    {
        checkConnection();
        return con.prepareStatement(str, param, param2);
    }

    public void setTransactionIsolation(int param) throws SQLException
    {
        checkConnection();
        con.setTransactionIsolation(param);
    }

    public void setReadOnly(boolean param) throws SQLException
    {
        checkConnection();
        con.setReadOnly(param);
    }

    public void setCatalog(String str) throws SQLException
    {
        checkConnection();
        con.setCatalog(str);
    }

    public boolean isClosed() throws SQLException
    {
        checkConnection();
        return con.isClosed();
    }

    public Statement createStatement() throws SQLException
    {
        checkConnection();
        Statement st = new AutoStatement(this);
        return st;
    }

    public Statement createStatement(int param, int param1) throws SQLException
    {
        checkConnection();
        Statement st = new AutoStatement(this,param, param1);
        return st;
    }

    public PreparedStatement prepareStatement(String str) throws SQLException
    {
        checkConnection();
        PreparedStatement st = new AutoPreparedStatement(this,str);
        return st;
    }

    public boolean getAutoCommit() throws SQLException
    {
        checkConnection();
        return con.getAutoCommit();
    }

    public CallableStatement prepareCall(String str) throws SQLException
    {
        checkConnection();
        return con.prepareCall(str);
    }

    public void commit() throws SQLException
    {
        checkConnection();
        con.commit();
    }

    public CallableStatement prepareCall(String str, int param, int param2) throws SQLException
    {
        checkConnection();
        return con.prepareCall(str, param, param2);
    }

    public void rollback() throws SQLException
    {
        checkConnection();
        con.rollback();
    }

    @Override
    protected void finalize() throws Throwable
    {
        log.warn("finalize()..., connection was not closed, "+description);
    }

//************************************ jdk 1.4 *****************************************************************
    public java.sql.Savepoint setSavepoint() throws java.sql.SQLException
    {
        checkConnection();
        return con.setSavepoint();
    }

    public void setHoldability(int param) throws java.sql.SQLException
    {
        checkConnection();
        con.setHoldability(param);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int param) throws java.sql.SQLException
    {
        checkConnection();
        return con.prepareStatement(str, param);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int param, int param2, int param3) throws java.sql.SQLException
    {
        checkConnection();
        return con.prepareStatement(str, param, param2, param3);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int[] values) throws java.sql.SQLException
    {
        checkConnection();
        return con.prepareStatement(str, values);
    }

    public int getHoldability() throws java.sql.SQLException
    {
        checkConnection();
        return con.getHoldability();
    }

    public java.sql.Savepoint setSavepoint(java.lang.String str) throws java.sql.SQLException
    {
        checkConnection();
        return con.setSavepoint(str);
    }

    public java.sql.Statement createStatement(int param, int param1, int param2) throws java.sql.SQLException
    {
        checkConnection();
        return con.createStatement(param, param1, param2);
    }

    public java.sql.CallableStatement prepareCall(java.lang.String str, int param, int param2, int param3) throws java.sql.SQLException
    {
        checkConnection();
        return con.prepareCall(str, param, param2, param3);
    }

    public void releaseSavepoint(java.sql.Savepoint savepoint) throws java.sql.SQLException
    {
        checkConnection();
        con.releaseSavepoint(savepoint);
    }

    public java.sql.PreparedStatement prepareStatement(java.lang.String str, java.lang.String[] str1) throws java.sql.SQLException
    {
        checkConnection();
        return con.prepareStatement(str, str1);
    }

    public void rollback(java.sql.Savepoint savepoint) throws java.sql.SQLException
    {
        checkConnection();
        con.rollback(savepoint);
    }

//********************************* JAVA 1.6
/*
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
*/

//*/
}
