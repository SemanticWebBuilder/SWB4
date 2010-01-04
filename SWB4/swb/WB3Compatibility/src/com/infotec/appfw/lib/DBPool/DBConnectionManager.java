/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * DBConnectionManager.java
 *
 * Created on 22 de octubre de 2001, 16:46
 */

package com.infotec.appfw.lib.DBPool;

import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Esta clase es un Singleton que proporciona acceso a uno o m�s connection pools
 * definidos en un archivo de propiedades. Un cliente obtiene una instancia a trav�s
 * del m�todo est�tico getInstance() y puede entonces crear o cerrar conexiones de
 * un pool. Cuando se termina la sesi�n del cliente �ste debe llamar al m�tdodo
 * release() para cerrar todas las conexiones abiertas.
 */
public class DBConnectionManager
{
    private static Logger log=SWBUtils.getLogger(DBConnectionManager.class);

    static private DBConnectionManager instance;       // La instancia

    /**
     * Regresa una instancia, la cual es creada la primera vez que el m�todo es llamado.
     *
     * @return  DBConnectionManager Una instancia.
     */
    static synchronized public DBConnectionManager getInstance()
    {
        if (instance == null)
        {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    /**
     * Un constructor privado puesto que es un Singleton.
     */
    private DBConnectionManager()
    {
    }

    public int getClients()
    {
        return 0;
    }

    public int getNumConnections()
    {
        return 0;
    }

    public int getConnections(String name)
    {
        return SWBUtils.DB.getConnections(name);
    }

    public int getFreeConnections(String name)
    {
        return SWBUtils.DB.getFreeConnections(name);
    }


    /**
     * Cierra una conexi�n del pool especificado.
     *
     * @param   name    El nombre del pool definido en el archivo de propiedades.
     * @param   con     La conexi�n.
     */
    public void freeConnection(String name, Connection con)
    {
    }

    /**
     * Regresa una conexi�n que no pertenece al pool.
     *
     * @param   name        El nombre del pool definido en el archivo de propiedades.
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getNoPoolConnection(String name)
    {
        return SWBUtils.DB.getNoPoolConnection(name);
    }


    /**
     * Regresa una conexi�n abierta. Si ninguna otra conexi�n est� disponible y el n�mero m�ximo
     * de conexiones no se ha alcanzado, una nueva conexi�n es creada.
     *
     * @param   name        El nombre del pool definido en el archivo de propiedades.
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getConnection(String name)
    {
        return SWBUtils.DB.getConnection(name);
    }

    /**
     * Regresa una conexi�n abierta. Si ninguna otra conexi�n est� disponible y el n�mero m�ximo
     * de conexiones no se ha alcanzado, una nueva conexi�n es creada.
     *
     * @param   name        El nombre del pool definido en el archivo de propiedades.
     * @param   description Descripcion del para debugeo.
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getConnection(String name, String description)
    {
        return SWBUtils.DB.getConnection(name, description);
    }

    /**
     * Regresa una conexi�n abierta. Si ninguna otra conexi�n est� disponible y el n�mero m�ximo
     * de conexiones no se ha alcanzado, una nueva conexi�n es creada. Si el n�mero m�ximo ha sido
     * alcanzado espera hasta que una conexi�n este disponible o el tiempo especificado haya
     * transcurrido.
     *
     * @param   name        El nombre del pool definido en el archivo de propiedades.
     * @param   time        El n�mero de milisegundos para espera de la conexi�n.
     * @return  Connection  La conexi�n o nulo.
     */
    public Connection getConnection(String name, long time)
    {
        return SWBUtils.DB.getConnection(name);
    }

    /**
     * Cierra todas las conexiones abiertas.
     */
    public void closeAllConnection()
    {
    }

    /**
     * Cierra todas las conexiones abiertas y libera todos los drivers JDBC.
     */
    public synchronized void release()
    {
    }

    /**
     * Escribe un mensaje en el archivo de log.
     */
    void log(String msg)
    {
        log.debug(msg);
    }

    /**
     * Escribe el mensaje de la Excepci�n en el archivo de log.
     */
    void log(Throwable e, String msg)
    {
        log.error(msg,e);
    }


//    /** Getter for property pools.
//     * @return Value of property pools.
//     *
//     */
//    public java.util.Hashtable getPools()
//    {
//        java.util.Hashtable map=new java.util.Hashtable();
//        Enumeration en=pools.keys();
//        while(en.hasMoreElements())
//        {
//            String key=(String)en.nextElement();
//            Object obj=pools.get(key);
//            if(obj instanceof DBConnectionPool)
//            {
//                map.put(key, obj);
//            }
//        }
//        return map;
//    }
    
    /** 
     * @return Regresa las total de solicitudes de conexiones
     */
    public long getHits()
    {
        return 0;
    }

}