/*
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
 */
package org.semanticwb.base.db;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import org.semanticwb.Logger;
import org.semanticwb.base.util.SWBProperties;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Esta clase proporciona acceso a uno o más connection pools
 * definidos en el archivo de propiedades db.properties. 
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */
public class DBConnectionManager {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(DBConnectionManager.class);
    
    /** The drivers. */
    private Vector drivers = new Vector();
    
    /** The pools. */
    private Hashtable pools = new Hashtable();
    
    /** The is jndi. */
    private boolean isJNDI;
    
    /** The JNDI patern. */
    private String JNDIPatern;
    
    /** The init ctx. */
    private Context initCtx;
    
    /** The time lock. */
    private PoolConnectionTimeLock timeLock = new PoolConnectionTimeLock();

    /**
     * Instantiates a new dB connection manager.
     */
    public DBConnectionManager()
    {
        log.event("Initializing DBConnectionManager...");
        init();
    }

    /**
     * Gets the num connections.
     * 
     * @return the num connections
     * @return
     */
    public int getNumConnections()
    {
        int cl = 0;
        Enumeration allPools = pools.elements();
        if (!isJNDI)
        {
            while (allPools.hasMoreElements())
            {
                DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
                cl += pool.freeConnections.size();
            }
        }
        return cl;
    }

    /**
     * Gets the connections.
     * 
     * @param name the name
     * @return the connections
     * @return
     */
    public int getConnections(String name)
    {
        int cl = 0;
        if (!isJNDI)
        {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null)
            {
                cl = pool.checkedOut;
            }
        }
        return cl;
    }

    /**
     * Gets the free connections.
     * 
     * @param name the name
     * @return the free connections
     * @return
     */
    public int getFreeConnections(String name)
    {
        int cl = 0;
        if (!isJNDI)
        {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null)
            {
                cl = pool.freeConnections.size();
            }
        }
        return cl;
    }

    /**
     * Cierra una conexi�n del pool especificado.
     * 
     * @param name the name
     * @param con the con
     */
    public void freeConnection(String name, Connection con)
    {
        if (!isJNDI)
        {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null)
            {
                pool.freeConnection(con);
            }
        } else
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            } catch (SQLException ex)
            {
                log.error("Error to create JNDI Pool Connection...", ex);
            }
        }
    }

    /**
     * Regresa una conexión que no pertenece al pool.
     * 
     * @param name the name
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getNoPoolConnection(String name)
    {
        Connection ret = null;
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null)
        {
            ret = pool.newNoPoolConnection();
        }
        return ret;
    }

    /**
     * Regresa una conexión que no se registra en el pool, pero que se auto restablece si se piede la conexion.
     * 
     * @param name the name
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getAutoConnection(String name)
    {
        Connection ret = null;
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null)
        {
            ret = pool.newAutoConnection();
        }
        return ret;
    }

    /**
     * Regresa una conexi�n abierta. Si ninguna otra conexi�n est� disponible y el n�mero m�ximo
     * de conexiones no se ha alcanzado, una nueva conexi�n es creada.
     * 
     * @param name the name
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getConnection(String name)
    {
        return getConnection(name, null);
    }

    /**
     * Regresa una conexi�n abierta. Si ninguna otra conexi�n est� disponible y el n�mero m�ximo
     * de conexiones no se ha alcanzado, una nueva conexi�n es creada.
     * 
     * @param name the name
     * @param description the description
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getConnection(String name, String description)
    {
        Connection ret = null;
        if (!isJNDI)
        {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null)
            {
                PoolConnection con = (PoolConnection) pool.getConnection();
//                if (con != null)
//                {
//                    if (description == null)
//                    {                        
//                        description = "NoDesc";                        
//                        try
//                        {
//                            StringBuffer buf=new StringBuffer();
//                            buf.append("Trace detail:\n");
//                            //System.out.print(" getConnection 1");
//                            StackTraceElement eles[]=Thread.currentThread().getStackTrace();
//                            //System.out.print(" 2");
//                            for(int i=0;i<eles.length;i++)
//                            {
//                                buf.append(eles[i].toString());
//                                buf.append("\n");
//                            }
//                            description=buf.toString();
//                        }catch(Throwable noe){}
//                        //System.out.println(" 3");
//                    }
//                    con.setDescription(description);
//                }
                //System.out.println("getConnection("+con.getId()+","+name+","+description+")");
                ret = con;
            }
        } else
        {
            DataSource ds = (DataSource) pools.get(name);
            if (ds == null)
            {
                try
                {
                    ds = (DataSource) initCtx.lookup(JNDIPatern + name);
                    pools.put(name, ds);
                    //ds.setLogWriter(log);
                    log.info("Initialized JNDI Connection Pool " + name);
                } catch (Exception ex)
                {
                    log.error("Error to get DataSource of Context...", ex);
                }
            }
            try
            {
                ret = ds.getConnection();
            } catch (SQLException ex)
            {
                log.error("Error to get JNDI Pool Connection...", ex);
            }
        }
        //System.out.println("getConnections:"+ret);
        return ret;
    }

    /**
     * Regresa una conexi�n abierta. Si ninguna otra conexi�n est� disponible y el n�mero m�ximo
     * de conexiones no se ha alcanzado, una nueva conexi�n es creada. Si el n�mero m�ximo ha sido
     * alcanzado espera hasta que una conexi�n este disponible o el tiempo especificado haya
     * transcurrido.
     * 
     * @param name the name
     * @param time the time
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getConnection(String name, long time)
    {
        Connection ret = null;
        if (!isJNDI)
        {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null)
            {
                ret = pool.getConnection(time);
            }
        } else
        {
            DataSource ds = (DataSource) pools.get(name);
            if (ds == null)
            {
                try
                {
                    ds = (DataSource) initCtx.lookup(JNDIPatern + name);
                    pools.put(name, ds);
                    //TODO:
                    //ds.setLogWriter(log);
                    log.info("Initialized JNDI Pool [" + name + "]");
                } catch (Exception ex)
                {
                    log.error("Error to get DataSource of Context...", ex);
                }
            }
            try
            {
                ds.setLoginTimeout((int) (time / 1000));
                ret = ds.getConnection();
            } catch (SQLException ex)
            {
                log.error("Error to get JNDI Pool Connection...", ex);
            }
        }
        return ret;
    }

    /**
     * Cierra todas las conexiones abiertas.
     */
    public void closeAllConnection()
    {
        Enumeration allPools = pools.elements();
        if (!isJNDI)
        {
            while (allPools.hasMoreElements())
            {
                DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
                pool.release();
            }
        }
    }

    /**
     * Crea instancias del DBConnectionPool bas�ndose en el archivo de propiedades.
     * Un DBConnectionPool puede ser definido con las siguientes propiedades:
     * <PRE>
     * &lt;poolname&gt;.url         El URL JDBC de la base de datos.
     * &lt;poolname&gt;.user        Un usuario de la base de datos (opcional)
     * &lt;poolname&gt;.password    El password del usuario de la base de datos. (Si el usuario se especifica)
     * &lt;poolname&gt;.maxconn     El n�mero m�ximo de conexiones (opcional)
     * </PRE>
     * 
     * @param props the props
     */
    private void createPools(Properties props)
    {
        Enumeration propNames = props.propertyNames();
        while (propNames.hasMoreElements())
        {
            String name = (String) propNames.nextElement();
            if (name.endsWith(".url"))
            {
                String poolName = name.substring(0, name.lastIndexOf("."));
                String url = props.getProperty(poolName + ".url");
                if (url == null)
                {
                    log.error("No URL specified for " + poolName);
                    continue;
                }
                String user = props.getProperty(poolName + ".user");
                String password = props.getProperty(poolName + ".password");
                String maxconn = props.getProperty(poolName + ".maxconn", "0");
                String sidle_time = props.getProperty(poolName + ".idle_time", "0");
                if (user != null)
                {
                    user = user.trim();
                }
                if (password != null)
                {
                    password = password.trim();
                }
                int max;
                try
                {
                    max = Integer.parseInt(maxconn.trim());
                } catch (NumberFormatException e)
                {
                    log.warn("Invalid maxconn value " + maxconn + " for " + poolName);
                    max = 0;
                }
                long idle_time = 0;
                try
                {
                    idle_time = Long.parseLong(sidle_time.trim());
                } catch (NumberFormatException e)
                {
                    log.warn("Invalid idle_time value " + sidle_time + " for " + poolName);
                    idle_time = 0;
                }
                DBConnectionPool pool =
                        new DBConnectionPool(this, poolName, url, user, password, max, idle_time);
                pools.put(poolName, pool);
                log.info("Initialized Connection Pool [" + poolName + "]");
            }
        }
    }

    /**
     * Carga las propiedades e inicializa la instancia con sus valores.
     */
    private void init()
    {
        InputStream is = getClass().getResourceAsStream("/db.properties");
        Properties dbProps = new SWBProperties();
        try
        {
            if (is != null)
            {
                dbProps.load(is);
            } else
            {
                throw new FileNotFoundException();
            }
            is.close();
        } catch (Exception e)
        {
            log.error("Can't read the properties file. Make sure db.properties is in the CLASSPATH", e);
            return;
        }

        String jndi = dbProps.getProperty("jndi_pool", "false");
        if (jndi.equals("true"))
        {
            log.info("JDNI Pool found...");
            isJNDI = true;
            JNDIPatern = dbProps.getProperty("jndi_patern", "java:comp/env/jdbc/");
            try
            {
                initCtx = new InitialContext();
            } catch (javax.naming.NamingException ex)
            {
                log.error("Error to initialize JNDI Context", ex);
            }
        }
        if (!isJNDI)
        {
            loadDrivers(dbProps);
            createPools(dbProps);
        }
    }

    /**
     * Carga y registra todos los drivers JDBC. Esto lo realiza el DBConnectionManager, en comparaci�n
     * con el DBConnectionPool, puesto que muchos pools pueden compartir el mismo driver.
     * 
     * @param props the props
     */
    private void loadDrivers(Properties props)
    {
        String driverClasses = props.getProperty("drivers");
        StringTokenizer st = new StringTokenizer(driverClasses);
        while (st.hasMoreElements())
        {
            String driverClassName = st.nextToken().trim();
            try
            {
                Driver driver = (Driver) Class.forName(driverClassName).newInstance();
                DriverManager.registerDriver(driver);
                drivers.addElement(driver);
                log.info("Registered JDBC driver " + driverClassName);
            } catch (Exception e)
            {
                log.error("Can't register JDBC driver: " + driverClassName + ", Exception: " + e);
            }
        }
    }

    /** Getter for property timeLock.
     * @return Value of property timeLock.
     *
     */
    public PoolConnectionTimeLock getTimeLock()
    {
        return timeLock;
    }

    /** Getter for property pools.
     * @return Value of property pools.
     *
     */
    public java.util.Hashtable getPools()
    {
        java.util.Hashtable map = new java.util.Hashtable();
        Enumeration en = pools.keys();
        while (en.hasMoreElements())
        {
            String key = (String) en.nextElement();
            Object obj = pools.get(key);
            if (obj instanceof DBConnectionPool)
            {
                map.put(key, obj);
            }
        }
        return map;
    }

    /**
     * Gets the hits.
     * 
     * @return Regresa las total de solicitudes de conexiones
     */
    public long getHits()
    {
        long hits = 0;
        Enumeration en = pools.elements();
        while (en.hasMoreElements())
        {
            Object obj = en.nextElement();
            if (obj instanceof DBConnectionPool)
            {
                hits += ((DBConnectionPool) obj).getHits();
            }
        }
        return hits;
    }
}
