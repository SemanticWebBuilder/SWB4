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