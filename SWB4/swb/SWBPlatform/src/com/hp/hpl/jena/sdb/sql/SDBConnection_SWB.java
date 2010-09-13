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
 * (c) Copyright 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sdb.sql;


import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.shared.Command;

import com.hp.hpl.jena.sdb.core.Generator;
import com.hp.hpl.jena.sdb.core.Gensym;
import com.hp.hpl.jena.sdb.core.SDBConstants;
import com.hp.hpl.jena.sdb.graph.TransactionHandlerSDB;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/*
 * An SDBConnection is the abstraction of the link between client
 * application and the database.
 *  There can be many Store's per connection.
 */  

/**
 * The Class SDBConnection_SWB.
 */
public class SDBConnection_SWB extends SDBConnection
{
    
    /** The log. */
    static private Logger log = SWBUtils.getLogger(SDBConnection_SWB.class) ;
    
    /** The gen. */
    static private Generator gen = Gensym.create("connection-") ;

    /** The sql connection. */
    private Connection sqlConnection = null ;
    
    /** The transaction handler. */
    TransactionHandler transactionHandler = null ;
    
    /** The label. */
    String label = gen.next() ;
    
    /** The jdbc url. */
    String jdbcURL = "unset" ;
    
    // Defaults 
    /** The log sql exceptions. */
    public static boolean logSQLExceptions = true ;
    
    /** The log sql statements. */
    public static boolean logSQLStatements = false ;
    
    /** The log sql queries. */
    public static boolean logSQLQueries    = false ;
    
    /** The this log sql exceptions. */
    private boolean thisLogSQLExceptions = logSQLExceptions ;
    
    /** The this log sql statements. */
    private boolean thisLogSQLStatements = logSQLStatements ;
    
    /** The this log sql queries. */
    private boolean thisLogSQLQueries    = logSQLQueries ;

    /**
     * Instantiates a new sDB connection_ swb.
     */
    public SDBConnection_SWB()
    {
        this(SWBUtils.DB.getDefaultConnection());
        //this((Connection)null);
    }

    /**
     * Instantiates a new sDB connection_ swb.
     * 
     * @param poolname the poolname
     */
    public SDBConnection_SWB(String poolname)
    {
        this(SWBUtils.DB.getConnection(poolname));
        //this((Connection)null);
    }

    /**
     * Instantiates a new sDB connection_ swb.
     * 
     * @param con the con
     */
    public SDBConnection_SWB(Connection con)
    {
        super(con);
        sqlConnection=con;
    }

    /*
    public SDBConnection_SWB(DataSource ds) throws SQLException
    {
        this(ds.getConnection()) ;
    }
    
    public SDBConnection_SWB(String url, String user, String password)
    { 
        this(SDBConnectionFactory.createSqlConnection(url, user, password)) ;
        setLabel(url) ;
        setJdbcURL(url) ;
    }
    
    public SDBConnection_SWB(Connection jdbcConnection)
    { 
        this(jdbcConnection, null) ;
    }
    
    public SDBConnection_SWB(Connection jdbcConnection, String url)
    {
        super(jdbcConnection, url);
        sqlConnection = jdbcConnection ;
        transactionHandler = new TransactionHandlerSDB(this) ;
        if ( url != null ) setJdbcURL(url) ;
    }
     */

    /**
     * None.
     * 
     * @return the sDB connection
     */
    public static SDBConnection none()
    {
        return new SDBConnection(JDBC.jdbcNone, null, null) ;
    }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#hasSQLConnection()
     */
    /**
     * Checks for sql connection.
     * 
     * @return true, if successful
     */
    @Override
    public boolean hasSQLConnection() 
    {
        //return sqlConnection != null ;
        return true;
    }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#getTransactionHandler()
     */
    /**
     * Gets the transaction handler.
     * 
     * @return the transaction handler
     */
    @Override
    public TransactionHandler getTransactionHandler() { return transactionHandler ; } 
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#execQuery(java.lang.String)
     */
    /**
     * Exec query.
     * 
     * @param sqlString the sql string
     * @return the result set jdbc
     * @throws SQLException the sQL exception
     */
    @Override
    public ResultSetJDBC execQuery(String sqlString) throws SQLException
    { return execQuery(sqlString, SDBConstants.jdbcFetchSizeOff) ; }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#execQuery(java.lang.String, int)
     */
    /**
     * Exec query.
     * 
     * @param sqlString the sql string
     * @param fetchSize the fetch size
     * @return the result set jdbc
     * @throws SQLException the sQL exception
     */
    @Override
    public ResultSetJDBC execQuery(String sqlString, int fetchSize) throws SQLException
    {
        if ( loggingSQLStatements() || loggingSQLQueries() )
            writeLog("execQuery", sqlString) ;
        
        Connection conn = getPoolConnection() ;

        try {
            //Statement s = conn.createStatement() ; // Managed by ResultSetJDBC
            
            // These are needed for MySQL when trying for row-by-row fetching
            // and they are gemnerally true so set them always.
            Statement s = conn.createStatement(TYPE_FORWARD_ONLY, CONCUR_READ_ONLY) ; // Managed by ResultSetJDBC
            
            // MySQL : Integer.MIN_VALUE
            if ( fetchSize != SDBConstants.jdbcFetchSizeOff )
            {
                /* MySQL: streaming if:
                 * stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                            java.sql.ResultSet.CONCUR_READ_ONLY);
                   stmt.setFetchSize(Integer.MIN_VALUE);
                 */
                s.setFetchSize(fetchSize) ;
            }
            return new ResultSetJDBC(s, s.executeQuery(sqlString)) ;
        } catch (SQLException ex)
        {
            exception("execQuery", ex, sqlString) ;
            throw ex ;
        }
        catch (RuntimeException ex)
        {
            throw ex ;
        }finally
        {
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#executeInTransaction(com.hp.hpl.jena.shared.Command)
     */
    /**
     * Execute in transaction.
     * 
     * @param c the c
     * @return the object
     */
    @Override
    public Object executeInTransaction(Command c) { return getTransactionHandler().executeInTransaction(c) ; }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#executeSQL(com.hp.hpl.jena.sdb.sql.SQLCommand)
     */
    /**
     * Execute sql.
     * 
     * @param c the c
     * @return the object
     */
    @Override
    public Object executeSQL(final SQLCommand c)
    {
        Connection conn=getPoolConnection();
        try {
            return c.execute(conn) ;
        } catch (SQLException ex)
        { 
            exception("SQL", ex) ;
            throw new SDBExceptionSQL(ex) ;
        }finally
        {
            if(conn!=null)
            {
                try
                {
                    conn.close();
                    conn=null;
                }catch(SQLException e){log.error(e);}
            }
        }

    }

    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#execUpdate(java.lang.String)
     */
    /**
     * Exec update.
     * 
     * @param sqlString the sql string
     * @return the int
     * @throws SQLException the sQL exception
     */
    @Override
    public int execUpdate(String sqlString) throws SQLException
    {
        if ( loggingSQLStatements() )
            writeLog("execUpdate", sqlString) ;
        
        Connection conn = getPoolConnection() ;
        try {
            Statement s = conn.createStatement() ;
            int rc = s.executeUpdate(sqlString) ;
            s.close() ;
            return rc ;
        } catch (SQLException ex)
        {
            exception("execUpdate", ex, sqlString) ;
            throw ex ;
        }finally
        {
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }
    }

    /**
     * Execute a statement, return the result set if there was one, else null.
     * 
     * @param sqlString the sql string
     * @return the result set jdbc
     * @throws SQLException the sQL exception
     */
    @Override
    public ResultSetJDBC exec(String sqlString) throws SQLException
    {
        if ( loggingSQLStatements() )
            writeLog("exec", sqlString) ;
        
        Connection conn = getPoolConnection() ;
        
        try {
            Statement s = conn.createStatement() ;
            boolean r = s.execute(sqlString) ;
            if ( r )
                return new ResultSetJDBC(s, s.getResultSet()) ; 
            s.close() ;
            return null ;
        }
//        catch (SQLSyntaxErrorException ex)  // Java 6
//        {
//            exception("exec", ex, sqlString) ;
//            throw ex ;
//        }
        catch (SQLException ex)
        {
            exception("exec", ex, sqlString) ;
            throw ex ;
        }finally
        {
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }
    }

    /**
     * Execute a statement, return the result set if there was one, else null.
     * 
     * @param sqlString the sql string
     * @return the result set jdbc
     */
    @Override
    public ResultSetJDBC execSilent(String sqlString)
    {
        if ( loggingSQLStatements() )
            writeLog("execSilent", sqlString) ;
        
        Connection conn = getPoolConnection() ;
        
        try {
            Statement s = conn.createStatement() ;
            boolean r = s.execute(sqlString) ;
            if ( r )
                return new ResultSetJDBC(s, s.getResultSet()) ; 
            s.close() ;
            return null ;
        } catch (SQLException ex)
        {
            return null ;
        }finally
        {
            if(conn!=null)
            {
                try
                {
                    conn.close();
                    conn=null;
                }catch(SQLException e){log.error(e);}
            }
        }
    }
    
    /**
     * Prepare a statement *.
     * 
     * @param sqlString the sql string
     * @return the prepared statement
     * @throws SQLException the sQL exception
     */
    @Override
    public PreparedStatement prepareStatement(String sqlString) throws SQLException {
    	if ( loggingSQLStatements() )
    		writeLog("prepareStatement", sqlString) ;
    	
    	Connection conn = getPoolConnection();
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlString);
    		return ps;
    	} catch (SQLException ex) {
    		exception("prepareStatement", ex, sqlString) ;
    		throw ex;
    	}finally
        {
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }
    }
    
    /**
     * Close a prepared statement *.
     * 
     * @param ps the ps
     * @throws SQLException the sQL exception
     */
    @Override
    public void closePreparedStatement(PreparedStatement ps) throws SQLException {
        if ( loggingSQLStatements() )
            writeLog("closePrepareStatement", ps.toString()) ;
        if ( ps == null )
            return ;
        try {
            ps.close() ;
        } catch (SQLException ex) {
            exception("closePrepareStatement", ex, ps.toString()) ;
            throw ex;
        }
    }
    
    /**
     * Get the names of the application tables.
     * 
     * @return the table names
     */
    @Override
    public List<String> getTableNames()
    {
        Connection conn=getPoolConnection();
        List list=TableUtils.getTableNames(conn);
        if(conn!=null)
        {
            try
            {
                conn.close();
            }catch(SQLException e){log.error(e);}
            conn=null;
        }
        return list;
    }
    
    
//    public ResultSet metaData(String sqlString) throws SQLException
//    {
//        try {
//            Connection conn = getSqlConnection() ;
//            DatabaseMetaData dbmd = conn.getMetaData() ;
//            ResultSet rsMD = dbmd.getTables(null, null, null, null) ;
//            return rsMD ;
//        } catch (SQLException e)
//        {
//            exception("metaData", e) ;
//            throw e ;
//        }
//    }
    
    /* (non-Javadoc)
 * @see com.hp.hpl.jena.sdb.sql.SDBConnection#getSqlConnection()
 */
/**
 * Gets the sql connection.
 * 
 * @return the sql connection
 */
@Override
    public Connection getSqlConnection()
    {
        // Potential pool point.
        return sqlConnection ;
    }

    /**
     * Gets the pool connection.
     * 
     * @return the pool connection
     */
    public Connection getPoolConnection()
    {
        return SWBUtils.DB.getDefaultConnection();
    }


    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#close()
     */
    /**
     * Close.
     */
    @Override
    public void close()
    {
        Connection connection = getSqlConnection() ;
        try {
            if ( connection != null && ! connection.isClosed() )
                connection.close() ;
        } catch (SQLException ex){
            log.warn("Problems closing SQL connection", ex) ;
        }
    }

    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#toString()
     */
    /**
     * To string.
     * 
     * @return the string
     */
    @Override
    public String toString() { return getLabel() ; }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#loggingSQLExceptions()
     */
    /**
     * Logging sql exceptions.
     * 
     * @return true, if successful
     */
    @Override
    public boolean loggingSQLExceptions() { return thisLogSQLExceptions ;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#setLogSQLExceptions(boolean)
     */
    /**
     * Sets the log sql exceptions.
     * 
     * @param thisLogSQLExceptions the new log sql exceptions
     */
    @Override
    public void setLogSQLExceptions(boolean thisLogSQLExceptions)
    {
        this.thisLogSQLExceptions = thisLogSQLExceptions ;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#loggingSQLQueries()
     */
    /**
     * Logging sql queries.
     * 
     * @return true, if successful
     */
    @Override
    public boolean loggingSQLQueries() { return thisLogSQLQueries ; }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#setLogSQLQueries(boolean)
     */
    /**
     * Sets the log sql queries.
     * 
     * @param thisLogSQLQueries the new log sql queries
     */
    @Override
    public void setLogSQLQueries(boolean thisLogSQLQueries)
    {
        this.thisLogSQLQueries = thisLogSQLQueries ;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#loggingSQLStatements()
     */
    /**
     * Logging sql statements.
     * 
     * @return true, if successful
     */
    @Override
    public boolean loggingSQLStatements() { return thisLogSQLStatements ; }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#setLogSQLStatements(boolean)
     */
    /**
     * Sets the log sql statements.
     * 
     * @param thisLogSQLStatements the new log sql statements
     */
    @Override
    public void setLogSQLStatements(boolean thisLogSQLStatements)
    {
        this.thisLogSQLStatements = thisLogSQLStatements ;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#getLabel()
     */
    /**
     * Gets the label.
     * 
     * @return the label
     */
    @Override
    public String getLabel()
    {
        return label ;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#setLabel(java.lang.String)
     */
    /**
     * Sets the label.
     * 
     * @param label the new label
     */
    @Override
    public void setLabel(String label)
    {
        this.label = label ;
    }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#getJdbcURL()
     */
    /**
     * Gets the jdbc url.
     * 
     * @return the jdbc url
     */
    @Override
    public String getJdbcURL()
    {
        return jdbcURL ;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.sdb.sql.SDBConnection#setJdbcURL(java.lang.String)
     */
    /**
     * Sets the jdbc url.
     * 
     * @param jdbcURL the new jdbc url
     */
    @Override
    public void setJdbcURL(String jdbcURL)
    {
        this.jdbcURL = jdbcURL ;
    }

    /**
     * Exception.
     * 
     * @param who the who
     * @param ex the ex
     * @param sqlString the sql string
     */
    private void exception(String who, SQLException ex, String sqlString)
    {
        if ( this.loggingSQLExceptions() )
            log.warn(who+": SQLException\n"+ex.getMessage()+"\n"+sqlString+"\n") ;
    }

    /**
     * Exception.
     * 
     * @param who the who
     * @param ex the ex
     */
    private void exception(String who, SQLException ex)
    {
        if ( this.loggingSQLExceptions() )
            log.warn(who+": SQLException\n"+ex.getMessage()) ;
    }
    
    /**
     * Write log.
     * 
     * @param who the who
     * @param sqlString the sql string
     */
    private void writeLog(String who, String sqlString)
    {
            log.info(who+"\n\n"+sqlString+"\n") ;
    }
}

