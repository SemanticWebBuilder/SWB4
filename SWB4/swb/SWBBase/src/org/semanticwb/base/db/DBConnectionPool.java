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
 * DBConnectionPool.java
 *
 * Created on 14 de mayo de 2002, 13:12
 */
package org.semanticwb.base.db;

import java.sql.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Esta clase interna representa un connection pool. Crea nuevas conexiones con base en la demanda,
 * hasta un numero máximo si esta especificado. Tambien se cerciora de que una conexión todavia
 * esté abierta antes de que se regrese a un cliente.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx) 
 */
public class DBConnectionPool {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(DBConnectionPool.class);
    
    /** The checked out. */
    protected int checkedOut;
    
    /** The free connections. */
    protected Vector freeConnections = new Vector();
    
    /** The max conn. */
    private int maxConn;
    
    /** The name. */
    private String name;
    
    /** The password. */
    private String password;
    
    /** The URL. */
    private String URL;
    
    /** The user. */
    private String user;
    
    /** The idle_time. */
    private long idle_time = 0;
    
    /** The manager. */
    private DBConnectionManager manager;
    
    /** The hits. */
    private long hits = 0;
    
    /** The hits time. */
    private long hitsTime = 0;

    /**
     * Crea un nuevo objeto connection pool.
     * 
     * @param manager the manager
     * @param name      El nombre del pool
     * @param URL       El URL JDBC de la base de datos.
     * @param user      Un usuario de la base de datos o nulo.
     * @param password  El password del usuario de la base de datos o nulo.
     * @param maxConn   El número máximo de conexiones o 0 para definir que no tenga límite.
     * @param idle_time the idle_time
     */
    public DBConnectionPool(DBConnectionManager manager, String name, String URL, String user, String password,
            int maxConn, long idle_time)
    {
        this.manager = manager;
        this.name = name;
        setURL(URL);
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        this.idle_time = idle_time * 1000;
    }

    /**
     * Libera una conexión del pool. Notifica a otros Threads que pudieran estar esperando una
     * conexión.
     *
     * @param con La conexión a liberar.
     */
    public void freeConnection(Connection con)
    {
        boolean add = true;
        // Put the connection at the end of the Vector
        try
        {
            if (idle_time > 0 && (System.currentTimeMillis() - ((PoolConnection) con).getIdleTime()) > idle_time)
            {
                ((PoolConnection) con).destroyConnection();
                add = false;
            }
            if (((PoolConnection) con).getNativeConnection().isClosed())
            {
                ((PoolConnection) con).destroyConnection();
                add = false;
            }
            if (add)
            {
                synchronized (freeConnections)
                {
                    freeConnections.addElement(con);
                }
            }
        } catch (Exception e)
        {
            log.warn("To return connection to pool", e);
        }
    }

    /**
     * Obtiene una conexión del pool. Si ninguna conexión esté disponible, una nueva conexión es
     * creada al menos que el número máximo de conexiones haya sido alcanzado. Si una conexión
     * libre ha sido cerrada por la base de datos, se elimina del pool y este método es llamado
     * otra vez recursivamente.
     * 
     * @return the connection
     * @return
     */
    public Connection getConnection()
    {
        PoolConnection con = null;
        synchronized (freeConnections)
        {
            if (freeConnections.size() > 0)
            {
                // Escoje la primera conexi�n en el vector o utiliza round-robin.
                con = (PoolConnection) freeConnections.remove(0);
            }
        }
        if (con != null)
        {
            try
            {
                if (idle_time > 0 && (System.currentTimeMillis() - con.getIdleTime()) > idle_time)
                {
                    log.warn("Removed bad connection " + con.getId() + " (idle_time) from " + name + ", " + con.getDescription());
                    //con.printTrackTrace(System.out);
                    con.destroyConnection();
                    con = null;
                    return getConnection();
                }
                if (con.getNativeConnection().isClosed())
                {
                    log.warn("Removed bad connection " + con.getId() + " (isClosed) from " + name + ", " + con.getDescription());
                    //con.printTrackTrace(System.out);
                    con.destroyConnection();
                    con = null;
                    return getConnection();
                }
                /*
                try
                {
                con.getMetaData();
                //                        Statement st=con.createStatement();
                //                        ResultSet rs=st.executeQuery("desc swb_admlog");
                //                        rs.close();
                //                        st.close();
                }catch(Exception e)
                {
                log.warn("Removed bad connection "+con.getId()+" (desc) from " + name + ", " + con.getDescription());
                //con.printTrackTrace(System.out);
                con.destroyConnection();
                con=null;
                return getConnection();
                }
                 */
            } catch (SQLException e)
            {
                log.error("Removed bad connection " + con.getId() + " from " + name + ", " + con.getDescription(), e);
                //con.printTrackTrace(System.out);
                con.destroyConnection();
                con = null;
                return getConnection();
            }
            con.init();
        } else if (maxConn == 0 || checkedOut < maxConn)
        {
            con = (PoolConnection) newConnection();
        }

        if (con != null)
        {
            manager.getTimeLock().addConnection(con);
        }
        log.trace("getConnection():" + con.getId() + " " + con.getPool().getName() + " " + freeConnections.size());
        return con;
    }

    /**
     * Obtiene una conexión del pool. Si ninguna conexión está disponible, una nueva conexión es
     * creada al menos que el número máximo de conexiones haya sido alcanzado. Si una conexión
     * libre ha sido cerrada por la base de datos, se elimina del pool y este métdo es llamado
     * otra vez recursivamente.
     * <P>
     * Si ninguna conexión está disponible y el número máximo ha sido alcanzado, este método espera
     * por una conexión liberada el tiempo especificado.
     * 
     * @param timeout El valor del timeout en milisegundos.
     * @return the connection
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
        PoolConnection con = null;
        synchronized (freeConnections)
        {
            while (freeConnections.size() > 0)
            {
                con = (PoolConnection) freeConnections.remove(0);
                try
                {
                    con.destroyConnection();
                    //con.init();
                    log.debug("Closed connection for pool " + name + ", " + con.getDescription());
                } catch (Exception e)
                {
                    log.error("Can't close connection for pool " + name + ", " + con.getDescription(), e);
                }
                con = null;
            }
        }
        //System.out.println("release()");
    }

    /**
     * Crea una nueva conexión usando un identificador de usuario y passsword si son especificados.
     * 
     * @return the connection
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
            log.error("Can't create a new connection for " + URL, e);
            con = null;
        }
        //System.out.println(new java.util.Date()+ ": newConnection");
        return con;
    }

    /**
     * Crea una nueva conexión que se auto conecta si se piede la conexion.
     * 
     * @return the connection
     * @return
     */
    public Connection newAutoConnection()
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
            log.error("Can't create a new connection for " + URL, e);
            return null;
        }
        if (con != null)
        {
            return new AutoConnection(con, this);
        } else
        {
            return null;
        }
    }

    /**
     * Crea una nueva conexión usando un identificador de usuario y passsword si son especificados.
     * 
     * @return the connection
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
            log.error("Can't create a new connection for " + URL, e);
            return null;
        }
        if (con != null)
        {
            return new PoolConnection(con, this);
        } else
        {
            return null;
        }
    }

    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public java.lang.String getName()
    {
        return name;
    }

    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public java.lang.String getPassword()
    {
        return password;
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
        this.URL = SWBUtils.TEXT.replaceAll(URL, "{apppath}", SWBUtils.getApplicationPath());
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
     * Gets the hits.
     * 
     * @return Regresa las total de solicitudes de conexiones
     */
    public long getHits()
    {
        return hits;
    }

    /**
     * Gets the hits time.
     * 
     * @return Regresa las total de solicitudes de conexiones
     */
    public long getHitsTime()
    {
        return hitsTime / 100;
    }

    /**
     * Adds the hit.
     * 
     * @param time the time
     */
    public void addHit(long time)
    {
        time = time * 100;
        hits++;
        hitsTime = (hitsTime * 9 + time) / 10;
    }

    /**
     * Gets the connection manager.
     * 
     * @return the connection manager
     * @return
     */
    public DBConnectionManager getConnectionManager()
    {
        return manager;
    }

    public long getIdleTime()
    {
       return idle_time;
    }

}
