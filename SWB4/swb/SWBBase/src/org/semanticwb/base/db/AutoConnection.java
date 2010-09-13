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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.base.db;

import java.sql.*;
import java.util.Properties;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoConnection.
 * 
 * @author javier.solis
 */
public class AutoConnection implements Connection
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(AutoConnection.class);

    /** The con. */
    private java.sql.Connection con = null;
    
    /** The pool. */
    private DBConnectionPool pool;
    
    /** The description. */
    private String description = "";
    
    /** The id. */
    private long id = 0;
    
    /** The isclosed. */
    private boolean isclosed=false;

    /** The debug. */
    private boolean debug=false;

    /**
     * Instantiates a new auto connection.
     * 
     * @param con the con
     * @param pool the pool
     */
    public AutoConnection(Connection con, DBConnectionPool pool)
    {
        this.con = con;
        this.pool = pool;
        log.trace("AutoConnection("+getId()+","+pool.getName()+"):"+pool.checkedOut);
    }

    /**
     * Check connection.
     * 
     * @return true, if successful
     */
    public boolean checkConnection()
    {
        if (debug)
        {
            System.out.println("checkConnection");
        }
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

    /**
     * Change connection.
     */
    public void changeConnection()
    {
        if (debug)
        {
            System.out.println("changeConnection");
        }
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
     * Gets the native connection.
     * 
     * @return the native connection
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
     * @throws SQLException the sQL exception
     * @exception java.sql.SQLException
     * Si un error de acceso a kla base de datos ocurre.
     */
    public void close() throws SQLException
    {
        if (debug)
        {
            System.out.println("close");
        }
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
     * @param param the new auto commit
     * @throws SQLException the sQL exception
     * @exception java.sql.SQLException
     * Si un error de acceso a kla base de datos ocurre.
     * @see       getAutoCommit()
     */
    public void setAutoCommit(boolean param) throws SQLException
    {
        if (debug)
        {
            System.out.println("setAutoCommit");
        }
        checkConnection();
        con.setAutoCommit(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getWarnings()
     */
    public SQLWarning getWarnings() throws SQLException
    {
        if (debug)
        {
            System.out.println("getWarnings");
        }
        checkConnection();
        return con.getWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getCatalog()
     */
    public String getCatalog() throws SQLException
    {
        if (debug)
        {
            System.out.println("getCatalog");
        }
        checkConnection();
        return con.getCatalog();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setTypeMap(java.util.Map)
     */
    public void setTypeMap(java.util.Map map) throws SQLException
    {
        if (debug)
        {
            System.out.println("setTypeMap");
        }
        checkConnection();
        con.setTypeMap(map);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getTypeMap()
     */
    public java.util.Map getTypeMap() throws SQLException
    {
        if (debug)
        {
            System.out.println("getTypeMap");
        }
        checkConnection();
        return con.getTypeMap();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getTransactionIsolation()
     */
    public int getTransactionIsolation() throws SQLException
    {
        if (debug)
        {
            System.out.println("getTransactionIsolation");
        }
        checkConnection();
        return con.getTransactionIsolation();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#isReadOnly()
     */
    public boolean isReadOnly() throws SQLException
    {
        if (debug)
        {
            System.out.println("isReadOnly");
        }
        checkConnection();
        return con.isReadOnly();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getMetaData()
     */
    public DatabaseMetaData getMetaData() throws SQLException
    {
        if (debug)
        {
            System.out.println("getMetaData");
        }
        checkConnection();
        return con.getMetaData();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#clearWarnings()
     */
    public void clearWarnings() throws SQLException
    {
        if (debug)
        {
            System.out.println("clearWarnings");
        }
        checkConnection();
        con.clearWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#nativeSQL(java.lang.String)
     */
    public String nativeSQL(String str) throws SQLException
    {
        if (debug)
        {
            System.out.println("nativeSQL");
        }
        checkConnection();
        return con.nativeSQL(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
     */
    public PreparedStatement prepareStatement(String str, int param, int param2) throws SQLException
    {
        if (debug)
        {
            System.out.println("prepareStatement");
        }
        checkConnection();
        return con.prepareStatement(str, param, param2);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setTransactionIsolation(int)
     */
    public void setTransactionIsolation(int param) throws SQLException
    {
        if (debug)
        {
            System.out.println("setTransactionIsolation");
        }
        checkConnection();
        con.setTransactionIsolation(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setReadOnly(boolean)
     */
    public void setReadOnly(boolean param) throws SQLException
    {
        if (debug)
        {
            System.out.println("setReadOnly");
        }
        checkConnection();
        con.setReadOnly(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setCatalog(java.lang.String)
     */
    public void setCatalog(String str) throws SQLException
    {
        if (debug)
        {
            System.out.println("setCatalog");
        }
        checkConnection();
        con.setCatalog(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#isClosed()
     */
    public boolean isClosed() throws SQLException
    {
        if (debug)
        {
            System.out.println("isClosed");
        }
        checkConnection();
        return con.isClosed();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement()
     */
    public Statement createStatement() throws SQLException
    {
        if (debug)
        {
            System.out.println("createStatement");
        }
        checkConnection();
        Statement st = new AutoStatement(this);
        return st;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement(int, int)
     */
    public Statement createStatement(int param, int param1) throws SQLException
    {
        if (debug)
        {
            System.out.println("createStatement");
        }
        checkConnection();
        Statement st = new AutoStatement(this,param, param1);
        return st;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String)
     */
    public PreparedStatement prepareStatement(String str) throws SQLException
    {
        if (debug)
        {
            System.out.println("prepareStatement");
        }
        checkConnection();
        PreparedStatement st = new AutoPreparedStatement(this,str);
        return st;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getAutoCommit()
     */
    public boolean getAutoCommit() throws SQLException
    {
        if (debug)
        {
            System.out.println("getAutoCommit");
        }
        checkConnection();
        return con.getAutoCommit();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String)
     */
    public CallableStatement prepareCall(String str) throws SQLException
    {
        if (debug)
        {
            System.out.println("prepareCall");
        }
        checkConnection();
        return con.prepareCall(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#commit()
     */
    public void commit() throws SQLException
    {
        if (debug)
        {
            System.out.println("commit");
        }
        checkConnection();
        con.commit();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
     */
    public CallableStatement prepareCall(String str, int param, int param2) throws SQLException
    {
        if (debug)
        {
            System.out.println("prepareCall");
        }
        checkConnection();
        return con.prepareCall(str, param, param2);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#rollback()
     */
    public void rollback() throws SQLException
    {
        if (debug)
        {
            System.out.println("rollback");
        }
        checkConnection();
        con.rollback();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable
    {
        if (debug)
        {
            System.out.println("finalize");
        }
        log.warn("finalize()..., connection was not closed, "+description);
    }

//************************************ jdk 1.4 *****************************************************************
    /* (non-Javadoc)
 * @see java.sql.Connection#setSavepoint()
 */
    public java.sql.Savepoint setSavepoint() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("setSavepoint");
        }
        checkConnection();
        return con.setSavepoint();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setHoldability(int)
     */
    public void setHoldability(int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("setHoldability");
        }
        checkConnection();
        con.setHoldability(param);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int)
     */
    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int param) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("prepareStatement");
        }
        checkConnection();
        return con.prepareStatement(str, param);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
     */
    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int param, int param2, int param3) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("prepareStatement");
        }
        checkConnection();
        return con.prepareStatement(str, param, param2, param3);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
     */
    public java.sql.PreparedStatement prepareStatement(java.lang.String str, int[] values) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("prepareStatement");
        }
        checkConnection();
        return con.prepareStatement(str, values);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getHoldability()
     */
    public int getHoldability() throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("getHoldability");
        }
        checkConnection();
        return con.getHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setSavepoint(java.lang.String)
     */
    public java.sql.Savepoint setSavepoint(java.lang.String str) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("setSavepoint");
        }
        checkConnection();
        return con.setSavepoint(str);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement(int, int, int)
     */
    public java.sql.Statement createStatement(int param, int param1, int param2) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("createStatement");
        }
        checkConnection();
        return con.createStatement(param, param1, param2);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
     */
    public java.sql.CallableStatement prepareCall(java.lang.String str, int param, int param2, int param3) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("prepareCall");
        }
        checkConnection();
        return con.prepareCall(str, param, param2, param3);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
     */
    public void releaseSavepoint(java.sql.Savepoint savepoint) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("releaseSavepoint");
        }
        checkConnection();
        con.releaseSavepoint(savepoint);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
     */
    public java.sql.PreparedStatement prepareStatement(java.lang.String str, java.lang.String[] str1) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("prepareStatement");
        }
        checkConnection();
        return con.prepareStatement(str, str1);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#rollback(java.sql.Savepoint)
     */
    public void rollback(java.sql.Savepoint savepoint) throws java.sql.SQLException
    {
        if (debug)
        {
            System.out.println("rollback");
        }
        checkConnection();
        con.rollback(savepoint);
    }

//********************************* JAVA 1.6
/*
    public Clob createClob() throws SQLException {
        checkConnection();
        return con.createClob();
    }

    public Blob createBlob() throws SQLException {
        checkConnection();
        return con.createBlob();
    }

    public NClob createNClob() throws SQLException {
        checkConnection();
        return con.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        checkConnection();
        return con.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        checkConnection();
        return con.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        checkConnection();
        con.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        checkConnection();
        con.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        checkConnection();
        return con.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        checkConnection();
        return con.getClientInfo();
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        checkConnection();
        return con.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        checkConnection();
        return con.createStruct(typeName, attributes);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        checkConnection();
        return con.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        checkConnection();
        return con.isWrapperFor(iface);
    }


//*/
}
