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

import java.util.*;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Administra la duración de las conexiones con el fin de
 * identificar cuando una conexión excedio el tiempo limite
 * permitido (300sg) de duraci�n, al estar siendo utilizada
 * por un recurso.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */
public class PoolConnectionTimeLock extends TimerTask {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(PoolConnectionTimeLock.class);
    
    /** The timer. */
    private Timer timer = null;
    //private Timestamp lastupdate;
    /** The pools. */
    private ConcurrentHashMap pools = new ConcurrentHashMap();
    
    /** The last time. */
    private volatile long lastTime = System.nanoTime();

    /**
     * Creates a new instance of PoolConnectionTimeLock.
     */
    public PoolConnectionTimeLock()
    {
        //this.lastupdate = new Timestamp(new java.util.Date().getTime());
    }

    /**
     * Adds the connection.
     * 
     * @param con the con
     */
    public void addConnection(PoolConnection con)
    {
        if (con != null)
        {
            try
            {
                long time = System.nanoTime();
                while (time <= lastTime)
                {
                    time++;
                }
                lastTime = time;
                con.setId(time);
                ConcurrentHashMap pool = (ConcurrentHashMap) pools.get(con.getPool().getName());
                if (pool == null)
                {
                    pool = new ConcurrentHashMap();
                    pools.put(con.getPool().getName(), pool);
                }
                //con.printTrackTrace(System.out);
                pool.put(Long.valueOf(con.getId()), con.getDescription());
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    /**
     * Removes the connection.
     * 
     * @param con the con
     */
    public void removeConnection(PoolConnection con)
    {
        if (con != null)
        {
            try
            {
                ConcurrentHashMap pool = (ConcurrentHashMap) pools.get(con.getPool().getName());
                if (pool != null)
                {
                    pool.remove(Long.valueOf(con.getId()));
                    con.getPool().addHit(System.currentTimeMillis() - con.getIdleTime());
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     */
    public void run()
    {
        //System.out.println("Checking Connections...");
        long actual = System.currentTimeMillis();
        Iterator it = pools.values().iterator();
        while (it.hasNext())
        {
            ConcurrentHashMap pool = new ConcurrentHashMap((ConcurrentHashMap) it.next());
            Iterator it2 = pool.entrySet().iterator();
            while (it2.hasNext())
            {
                Map.Entry entry = (Map.Entry) it2.next();
                Long time = (Long) entry.getKey();
                String des = (String) entry.getValue();

                if ((time.longValue() + 300000L) < actual)
                {
                    log.warn("Connection Time Lock, (" + ((actual - time.longValue()) / 1000) + "s)" + des);
                }
            }
        }
    }

    /**
     * Destroy.
     */
    public void destroy()
    {
        log.info("PoolConnectionTimeLock Finished" + "...");
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }

    }

    /**
     * Inits the.
     */
    public void init()
    {
        log.info("PoolConnectionTimeLock Started" + "...");
        timer = new Timer();
        timer.schedule(this, 30000, 30000);
    }

    /**
     * Stop.
     */
    public void stop()
    {
        log.info("PoolConnectionTimeLock Stoped" + "...");
        if (timer != null)
        {
            timer.cancel();
            this.cancel();
            timer = null;
        }
    }

    /** Getter for property pools.
     * @return Value of property pools.
     *
     */
    public HashMap getPools()
    {
        return new HashMap(pools);
    }
}
