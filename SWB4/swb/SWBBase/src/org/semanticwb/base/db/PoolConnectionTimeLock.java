

/*
 * PoolConnectionTimeLock.java
 *
 * Created on 27 de enero de 2004, 19:22
 */

package org.semanticwb.base.db;

import java.util.*;
import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Administra la duración de las conexiones con el fin de
 * identificar cuando una conexión excedio el tiempo limite
 * permitido (300sg) de duraci�n, al estar siendo utilizada
 * por un recurso.
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */
public class PoolConnectionTimeLock extends TimerTask
{
    private static Logger log=SWBUtils.getLogger(PoolConnectionTimeLock.class);        

    private Timer timer = null;
    //private Timestamp lastupdate;

    private HashMap pools = new HashMap();
    
    private long lastTime=System.currentTimeMillis();


    /** Creates a new instance of PoolConnectionTimeLock */
    public PoolConnectionTimeLock()
    {
        //this.lastupdate = new Timestamp(new java.util.Date().getTime());
    }

    /**
     * 
     * @param con
     */
    public synchronized void addConnection(PoolConnection con)
    {
        if (con != null)
        {
            try
            {
                long time=System.currentTimeMillis();
                while(time<=lastTime)time++;
                lastTime=time;
                con.setId(time);
                HashMap pool = (HashMap) pools.get(con.getPool().getName());
                if (pool == null)
                {
                    pool = new HashMap();
                    pools.put(con.getPool().getName(), pool);
                }
                pool.put(Long.valueOf(con.getId()), con.getDescription());
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    /**
     * 
     * @param con
     */
    public synchronized void removeConnection(PoolConnection con)
    {
        if (con != null)
        {
            try
            {
                HashMap pool = (HashMap) pools.get(con.getPool().getName());
                if (pool != null)
                {
                    pool.remove(Long.valueOf(con.getId()));
                    con.getPool().addHit(System.currentTimeMillis()-con.getIdleTime());
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    public void run()
    {
        //System.out.println("Checking Connections...");
        long actual = System.currentTimeMillis();
        Iterator it = pools.values().iterator();
        while (it.hasNext())
        {
            HashMap pool = new HashMap((HashMap) it.next());
            Iterator it2 = pool.entrySet().iterator();
            while (it2.hasNext())
            {
                Map.Entry entry = (Map.Entry) it2.next();
                Long time = (Long) entry.getKey();
                String des = (String)entry.getValue();
                
                if ((time.longValue() + 300000L) < actual)
                {
                    log.warn("Connection Time Lock, (" + ((actual - time.longValue()) / 1000) + "s)" + des);
                }
            }
        }
    }

    /**
     * 
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
     * 
     */
    public void init()
    {
        log.info("PoolConnectionTimeLock Started" + "...");
        timer = new Timer();
        timer.schedule(this, 30000, 30000);
    }

    /**
     * 
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
    public java.util.HashMap getPools()
    {
        synchronized (pools)
        {
            return new HashMap(pools);
        }
    }

}
