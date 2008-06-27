/*
 * DBConnectionPool.java
 *
 * Created on 14 de mayo de 2002, 13:12
 */

package org.semanticwb.base.db;

import java.sql.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Esta clase interna representa un connection pool. Crea nuevas conexiones con base en la demanda,
 * hasta un numero máximo si esta especificado. Tambien se cerciora de que una conexión todavia
 * esté abierta antes de que se regrese a un cliente.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class DBConnectionPool
{
    private static Logger log=SWBUtils.getLogger(DBConnectionPool.class);    
    
    /**
     * 
     */
    protected int checkedOut;
    /**
     * 
     */
    protected Vector freeConnections = new Vector();
    private int maxConn;
    private String name;
    private String password;
    private String URL;
    private String user;
    private long idle_time=0;
    private DBConnectionManager manager;
    
    private long hits=0;
    private long hitsTime=0;

    /**
     * Crea un nuevo objeto connection pool.
     *
     * @param manager 
     * @param name      El nombre del pool
     * @param URL       El URL JDBC de la base de datos.
     * @param user      Un usuario de la base de datos o nulo.
     * @param password  El password del usuario de la base de datos o nulo.
     * @param maxConn   El número máximo de conexiones o 0 para definir que no tenga límite.
     * @param idle_time 
     */
    public DBConnectionPool(DBConnectionManager manager, String name, String URL, String user, String password,
                            int maxConn, long idle_time)
    {
        this.manager=manager;
        this.name = name;
        setURL(URL);
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        this.idle_time=idle_time*1000;
    }

    /**
     * Libera una conexión del pool. Notifica a otros Threads que pudieran estar esperando una
     * conexión.
     *
     * @param con La conexión a liberar.
     */
    public void freeConnection(Connection con)
    {
        // Put the connection at the end of the Vector
        try
        {
            if(!con.isClosed())
            {
                freeConnections.addElement(con);
            }
        }catch(Exception e){log.error("Error freeConnection()",e);}
    }
    
    /**
     * Obtiene una conexión del pool. Si ninguna conexión esté disponible, una nueva conexión es
     * creada al menos que el número máximo de conexiones haya sido alcanzado. Si una conexión
     * libre ha sido cerrada por la base de datos, se elimina del pool y este método es llamado
     * otra vez recursivamente.
     * @return 
     */
    public Connection getConnection()
    {
        PoolConnection con = null;
        synchronized(freeConnections)
        {
            if (freeConnections.size() > 0)
            {
                // Escoje la primera conexi�n en el vector o utiliza round-robin.
                con = (PoolConnection) freeConnections.remove(0);
            }
        }
        if(con!=null)
        {
            try
            {
                con.init();
                if (con.isClosed())
                {
                    log.warn("Removed bad connection (isClosed)from " + name + ", " + con.getDescription());
                    con.destroyConnection();
                    con=null;
                    return getConnection();
                }else if(idle_time>0 && (System.currentTimeMillis()-con.getIdleTime())>idle_time)
                {
                    log.warn("Removed bad connection (idle_time) from " + name + ", " + con.getDescription());
                    con.destroyConnection();
                    con=null;
                    return getConnection();
                }
            } catch (SQLException e)
            {
                log.error("Removed bad connection from " + name + ", " + con.getDescription(),e);
                con.destroyConnection();
                con=null;
                return getConnection();
            }
        } else if (maxConn == 0 || checkedOut < maxConn)
        {
            con = (PoolConnection) newConnection();
        }
        
        if(con!=null)
        {
            manager.getTimeLock().addConnection(con);
        }
        //System.out.println("getConnection():"+con);
        return con;
    }

    /**
     * Obtiene una conexi�n del pool. Si ninguna conexi�n est� disponible, una nueva conexi�n es
     * creada al menos que el n�mero m�ximo de conexiones haya sido alcanzado. Si una conexi�n
     * libre ha sido cerrada por la base de datos, se elimina del pool y este m�tdo es llamado
     * otra vez recursivamente.
     * <P>
     * Si ninguna conexi�n est� disponible y el n�mero m�ximo ha sido alcanzado, este m�todo espera
     * por una conexi�n liberada el tiempo especificado.
     *
     * @param timeout El valor del timeout en milisegundos.
     * @return 
     */
    public Connection getConnection(long timeout)
    {
        long startTime = System.currentTimeMillis();
        Connection con;
        while ((con = getConnection()) == null)
        {
            if ((System.currentTimeMillis() - startTime) >= timeout)
            {
                // Timeout ha transcurrido.
                return null;
            }
        }
        //System.out.println("getConnection(time):"+con);
        return con;
    }

    /**
     * Cierra todas las conexiones disponibles.
     */
    public void release()
    {
        PoolConnection con=null;
        synchronized(freeConnections)
        {
            while(freeConnections.size() > 0)
            {
                con = (PoolConnection) freeConnections.remove(0);
                try
                {
                    con.init();
                    con.destroyConnection();
                    log.debug("Closed connection for pool " + name + ", " + con.getDescription());
                } catch (Exception e)
                {
                    log.error("Can't close connection for pool " + name + ", " + con.getDescription(),e);
                }
                con=null;
            }
        }        
        //System.out.println("release()");
    }


    /**
     * Crea una nueva conexi�n usando un identificador de usuario y passsword si son especificados.
     * @return 
     */
    public Connection newNoPoolConnection()
    {
        Connection con = null;
        try
        {
            if (user == null)
            {
                con = DriverManager.getConnection(URL);
            } else
            {
                con = DriverManager.getConnection(URL, user, password);
            }
            log.debug("Created a new connection in pool " + name);
        } catch (SQLException e)
        {
            log.error("Can't create a new connection for " + URL,e);
            return null;
        }
        //System.out.println(new java.util.Date()+ ": newConnection");
        return con;
    }

    /**
     * Crea una nueva conexión usando un identificador de usuario y passsword si son especificados.
     */
    private Connection newConnection()
    {
        Connection con = null;
        try
        {
            if (user == null)
            {
                con = DriverManager.getConnection(URL);
            } else
            {
                con = DriverManager.getConnection(URL, user, password);
            }
            log.debug("Created a new connection in pool " + name);
        } catch (SQLException e)
        {
            log.error("Can't create a new connection for " + URL,e);
            return null;
        }
        if(con!=null)
        {
            return new PoolConnection(con, this);
        }else return null;
    }


    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public java.lang.String getName()
    {
        return name;
    }

    /** Getter for property maxConn.
     * @return Value of property maxConn.
     *
     */
    public int getMaxConn()
    {
        return maxConn;
    }

    /** Setter for property maxConn.
     * @param maxConn New value of property maxConn.
     *
     */
    public void setMaxConn(int maxConn)
    {
        this.maxConn = maxConn;
    }

    /** Getter for property URL.
     * @return Value of property URL.
     *
     */
    public java.lang.String getURL()
    {
        return URL;
    }

    /** Setter for property URL.
     * @param URL New value of property URL.
     *
     */
    public void setURL(java.lang.String URL)
    {
        this.URL = SWBUtils.TEXT.replaceAll(URL,"{apppath}",SWBUtils.getApplicationPath());
        //System.out.println("URL:"+this.URL);
    }

    /** Getter for property user.
     * @return Value of property user.
     *
     */
    public java.lang.String getUser()
    {
        return user;
    }

    /** Setter for property user.
     * @param user New value of property user.
     *
     */
    public void setUser(java.lang.String user)
    {
        this.user = user;
    }

    /** 
     * @return Regresa las total de solicitudes de conexiones
     */
    public long getHits()
    {
        return hits;
    }
    
    /** 
     * @return Regresa las total de solicitudes de conexiones
     */
    public long getHitsTime()
    {
        return hitsTime/100;
    }    
    
    /**
     * 
     * @param time
     */
    public void addHit(long time)
    {
        time=time*100;
        hits++;
        hitsTime=(hitsTime*9+time)/10;
    }
    
    /**
     * 
     * @return
     */
    public DBConnectionManager getConnectionManager()
    {
        return manager;
    }

}