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

/*
 * An SDBConnection is the abstraction of the link between client
 * application and the database.
 *  There can be many Store's per connection.
 */  

public class SDBConnection_SWB extends SDBConnection
{
    static private Logger log = SWBUtils.getLogger(SDBConnection_SWB.class) ;
    static private Generator gen = Gensym.create("connection-") ;

    private Connection sqlConnection = null ;
    TransactionHandler transactionHandler = null ;
    String label = gen.next() ;
    String jdbcURL = "unset" ;
    
    // Defaults 
    public static boolean logSQLExceptions = true ;
    public static boolean logSQLStatements = false ;
    public static boolean logSQLQueries    = false ;
    
    private boolean thisLogSQLExceptions = logSQLExceptions ;
    private boolean thisLogSQLStatements = logSQLStatements ;
    private boolean thisLogSQLQueries    = logSQLQueries ;

    public SDBConnection_SWB()
    {
        this(SWBUtils.DB.getDefaultConnection());
        //this((Connection)null);
    }

    public SDBConnection_SWB(String poolname)
    {
        this(SWBUtils.DB.getConnection(poolname));
        //this((Connection)null);
    }

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

    public static SDBConnection none()
    {
        return new SDBConnection(JDBC.jdbcNone, null, null) ;
    }
    
    @Override
    public boolean hasSQLConnection() 
    {
        //return sqlConnection != null ;
        return true;
    }
    
    @Override
    public TransactionHandler getTransactionHandler() { return transactionHandler ; } 
    
    @Override
    public ResultSetJDBC execQuery(String sqlString) throws SQLException
    { return execQuery(sqlString, SDBConstants.jdbcFetchSizeOff) ; }
    
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

    @Override
    public Object executeInTransaction(Command c) { return getTransactionHandler().executeInTransaction(c) ; }
    
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

    /** Execute a statement, return the result set if there was one, else null */
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

    /** Execute a statement, return the result set if there was one, else null.  */
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
    
    /** Prepare a statement **/
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
    
    /** Close a prepared statement **/
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
    
    /** Get the names of the application tables */
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
    
    @Override
    public Connection getSqlConnection()
    {
        // Potential pool point.
        return sqlConnection ;
    }

    public Connection getPoolConnection()
    {
        return SWBUtils.DB.getDefaultConnection();
    }


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

    
    @Override
    public String toString() { return getLabel() ; }

    @Override
    public boolean loggingSQLExceptions() { return thisLogSQLExceptions ;
    }

    @Override
    public void setLogSQLExceptions(boolean thisLogSQLExceptions)
    {
        this.thisLogSQLExceptions = thisLogSQLExceptions ;
    }

    @Override
    public boolean loggingSQLQueries() { return thisLogSQLQueries ; }

    @Override
    public void setLogSQLQueries(boolean thisLogSQLQueries)
    {
        this.thisLogSQLQueries = thisLogSQLQueries ;
    }

    @Override
    public boolean loggingSQLStatements() { return thisLogSQLStatements ; }
    
    @Override
    public void setLogSQLStatements(boolean thisLogSQLStatements)
    {
        this.thisLogSQLStatements = thisLogSQLStatements ;
    }

    @Override
    public String getLabel()
    {
        return label ;
    }

    @Override
    public void setLabel(String label)
    {
        this.label = label ;
    }
    
    @Override
    public String getJdbcURL()
    {
        return jdbcURL ;
    }

    @Override
    public void setJdbcURL(String jdbcURL)
    {
        this.jdbcURL = jdbcURL ;
    }

    private void exception(String who, SQLException ex, String sqlString)
    {
        if ( this.loggingSQLExceptions() )
            log.warn(who+": SQLException\n"+ex.getMessage()+"\n"+sqlString+"\n") ;
    }

    private void exception(String who, SQLException ex)
    {
        if ( this.loggingSQLExceptions() )
            log.warn(who+": SQLException\n"+ex.getMessage()) ;
    }
    
    private void writeLog(String who, String sqlString)
    {
            log.info(who+"\n\n"+sqlString+"\n") ;
    }
}

