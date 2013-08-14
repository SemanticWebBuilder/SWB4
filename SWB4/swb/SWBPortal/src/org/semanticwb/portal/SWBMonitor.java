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
package org.semanticwb.portal;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.db.DBConnectionPool;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBMonitor.
 */
public class SWBMonitor implements Serializable
{
    
    /** The log. */
    static Logger log=SWBUtils.getLogger(SWBMonitor.class);    
    
    /** The timer. */
    private transient Timer timer;
    
    /** The vec. */
    private transient Vector vec=new Vector();
    
    /** The max. */
    private transient int max=500;
    
    /** The delays. */
    private transient int delays = 5;
    
    /** The t. */
    private transient TimerTask t=null;
    
    /** The instance hits. */
    private transient long instanceHits=0;
    
    /** The instance hit time. */
    private transient long instanceHitTime=1000;
    
    /**
     * Instantiates a new sWB monitor.
     */
    public SWBMonitor()
    {
    }

    /**
     * Inits the.
     */
    public void init()
    {
        vec=new Vector(max);
        t=new TimerTask(){
            public void run()
            {
                try {
                    _run();
                } catch (java.lang.NullPointerException npe){
                //Ignore, Posible NPE at Exit - MAPS74
                }
            }
        };
        timer = new Timer("SWBMonitor("+max+","+delays+"s)", true);
        timer.schedule(t, delays*1000, delays*1000);

        log.event("Initializing SWBMonitor("+max+","+delays+"s)...");
    }        
    
    /**
     * _run.
     */
    public void _run()
    {
        long maxm=Runtime.getRuntime().maxMemory();
        long totalm=Runtime.getRuntime().totalMemory();
        long freem=Runtime.getRuntime().freeMemory();
        int maxUsers=SWBPortal.getUserMgr().getNumberOfSessionObjects();
        long hits=instanceHits;     //WBAccessLog.getInstance().getInstanceHits();
        long hitsTime=instanceHitTime;
        long resourceHits=0;//ResourceMgr.getInstance().getResourceCacheMgr().getResourceHits();
        long cacheResHits=0;//ResourceMgr.getInstance().getResourceCacheMgr().getCacheHits();
        long cacheResLoadHits=0;//ResourceMgr.getInstance().getResourceCacheMgr().getCacheLoadHits();      
        
        log.trace("SWBMonitor.run():"+hits);
        
        HashMap pools=new HashMap();
        Enumeration en=SWBUtils.DB.getPools();
        while(en.hasMoreElements())
        {
            DBConnectionPool pool=(DBConnectionPool)en.nextElement();
            if(pool!=null)
            {
                String name=pool.getName();
                int tot=SWBUtils.DB.getConnections(name);
                int free=SWBUtils.DB.getFreeConnections(name);
                PoolRecord pr=new PoolRecord(pool.getHits(),pool.getHitsTime(),tot,free,pool.getMaxConn());
                pools.put(name,pr);
            }
        }
        
        MonitorRecord mr=new MonitorRecord(maxm, totalm, freem, maxUsers, hits, hitsTime, resourceHits, cacheResHits, cacheResLoadHits, pools);
        if(vec.size()==max)
        {
            vec.remove(0);
        }
        vec.add(mr);
    }   
    
    /**
     * Gets the monitor records.
     * 
     * @return the monitor records
     */
    public Vector getMonitorRecords()
    {
        return vec;
    }
    
    /**
     * Gets the average monitor records.
     * 
     * @param ratio the ratio
     * @return the average monitor records
     */
    public Vector getAverageMonitorRecords(int ratio)
    {
        Vector ret=new Vector();
        Vector data=getMonitorRecords();
        int x=0;
        int y=0;
        //int z=((data.size()-1)/max)+1;
        int z=ratio;

        //long max=0;
        long total=0;
        long free=0;
        long time=0;
        long hits=0;
        long hitsTime=0;
        int maxUsers=0;
        long resourceHits=0;
        long cacheResHits=0;
        long cacheResLoadHits=0;     
        HashMap pools=new HashMap();

        Enumeration en=data.elements();
        while(en.hasMoreElements())
        {
            y++;
            SWBMonitor.MonitorRecord mr=(SWBMonitor.MonitorRecord)en.nextElement();
            long val=mr.getResourceHits();

            total+=mr.getTotalMemory();
            free+=mr.getFreeMemory();
            hits+=mr.getHits();
            hitsTime+=mr.getHitsTime();
            maxUsers+=mr.getMaxUsers();
            resourceHits+=mr.getResourceHits();
            cacheResHits+=mr.getCacheResHits();
            cacheResLoadHits+=mr.getCacheResLoadHits(); 
            
            Iterator it=mr.getPools().keySet().iterator();
            while(it.hasNext())
            {
                String key=(String)it.next();
                PoolRecord pr=(PoolRecord)mr.getPools().get(key);
                PoolRecord npr=(PoolRecord)pools.get(key);
                
                if(npr==null)
                {
                    npr=new PoolRecord(0,0,0,0,0);
                    pools.put(key,npr);
                }
                npr.setHits(npr.getHits()+pr.getHits());
                npr.setHitsTime(npr.getHitsTime()+pr.getHitsTime());
                npr.setFreeConnections(npr.getFreeConnections()+pr.getFreeConnections());
                npr.setMaxConnections(npr.getMaxConnections()+pr.getMaxConnections());
                npr.setTotalConnections(npr.getTotalConnections()+pr.getTotalConnections());
            }            
        
            if((y%z)==0)
            {
                Iterator it2=pools.values().iterator();
                while(it2.hasNext())
                {
                    PoolRecord npr=(PoolRecord)it2.next();
                    npr.setHits(npr.getHits()/z);
                    npr.setHitsTime(npr.getHitsTime()/z);
                    npr.setFreeConnections(npr.getFreeConnections()/z);
                    npr.setMaxConnections(npr.getMaxConnections()/z);
                    npr.setTotalConnections(npr.getTotalConnections()/z);
                }
                
                MonitorRecord aux=new MonitorRecord(mr.getMaxMemory(), total/z, free/z, maxUsers/z, hits/z, hitsTime/z, resourceHits/z, cacheResHits/z, cacheResLoadHits/z, pools);
                aux.setTime(mr.getTime());
                ret.add(aux);
                
                total=0;
                free=0;
                time=0;
                hits=0;
                hitsTime=0;
                maxUsers=0;
                resourceHits=0;
                cacheResHits=0;
                cacheResLoadHits=0;      
                pools=new HashMap();
            }
        }        
        return ret;
    }
    
    /**
     * Destroy.
     */
    public void destroy()
    {
        timer.cancel();
        log.event("WBMonitor Stoped");
    }    
    
    /**
     * Getter for property max.
     * @return Value of property max.
     */
    public int getMaxRecords()
    {
        return max;
    }
    
    /**
     * Setter for property max.
     * @param max New value of property max.
     */
    public void setMaxRecords(int max)
    {
        this.max = max;
    }
    
    /**
     * Getter for property delayms.
     * @return Value of property delayms.
     */
    public int getDelay()
    {
        return delays;
    }
    
    /**
     * Setter for property delayms.
     * @param delayms New value of property delayms.
     */
    public void setDelay(int delayms)
    {
        this.delays = delayms;
    }
    
    /**
     * Addinstance hit.
     * 
     * @param time the time
     */
    public void addinstanceHit(long time)
    {
        time=time/1000;
        instanceHits++;
        instanceHitTime=(instanceHitTime*49+time)/50;
    }
    
    /**
     * The Class PoolRecord.
     */
    public class PoolRecord implements Serializable
    {
        
        /** The hits. */
        private long hits=0;
        
        /** The hits time. */
        private long hitsTime=0;
        
        /** The total. */
        private int total=0;
        
        /** The free. */
        private int free=0;
        
        /** The max. */
        private int max=0;
        
        /**
         * Instantiates a new pool record.
         * 
         * @param hits the hits
         * @param hitsTime the hits time
         * @param total the total
         * @param free the free
         * @param max the max
         */
        public PoolRecord(long hits, long hitsTime, int total, int free, int max)
        {
            this.hits=hits;
            this.hitsTime=hitsTime;
            this.total=total;
            this.free=free;
            this.max=max;
        }

        /**
         * Gets the hits.
         * 
         * @return the hits
         */
        public long getHits()
        {
            return hits;
        }

        /**
         * Sets the hits.
         * 
         * @param hits the new hits
         */
        public void setHits(long hits)
        {
            this.hits = hits;
        }

        /**
         * Gets the total connections.
         * 
         * @return the total connections
         */
        public int getTotalConnections()
        {
            return total;
        }

        /**
         * Sets the total connections.
         * 
         * @param total the new total connections
         */
        public void setTotalConnections(int total)
        {
            this.total = total;
        }

        /**
         * Gets the free connections.
         * 
         * @return the free connections
         */
        public int getFreeConnections()
        {
            return free;
        }
        
        /**
         * Gets the used connections.
         * 
         * @return the used connections
         */
        public int getUsedConnections()
        {
            return total-free;
        }        

        /**
         * Sets the free connections.
         * 
         * @param free the new free connections
         */
        public void setFreeConnections(int free)
        {
            this.free = free;
        }

        /**
         * Gets the max connections.
         * 
         * @return the max connections
         */
        public int getMaxConnections()
        {
            return max;
        }

        /**
         * Sets the max connections.
         * 
         * @param max the new max connections
         */
        public void setMaxConnections(int max)
        {
            this.max = max;
        }

        /**
         * Gets the hits time.
         * 
         * @return the hits time
         */
        public long getHitsTime()
        {
            return hitsTime;
        }

        /**
         * Sets the hits time.
         * 
         * @param hitsTime the new hits time
         */
        public void setHitsTime(long hitsTime)
        {
            this.hitsTime = hitsTime;
        }
    }
    

    /**
     * The Class MonitorRecord.
     */
    public class MonitorRecord implements Serializable
    {
        
        /** The max. */
        private long max=0;
        
        /** The total. */
        private long total=0;
        
        /** The free. */
        private long free=0;
        
        /** The time. */
        private long time=0;
        
        /** The hits. */
        private long hits=0;
        
        /** The hits time. */
        private long hitsTime=0;
        
        /** The max users. */
        private int maxUsers=0;
        
        /** The resource hits. */
        private long resourceHits=0;
        
        /** The cache res hits. */
        private long cacheResHits=0;
        
        /** The cache res load hits. */
        private long cacheResLoadHits=0;        
        
        /** The pools. */
        private HashMap pools;

        /**
         * Instantiates a new monitor record.
         * 
         * @param max the max
         * @param total the total
         * @param free the free
         * @param maxUsers the max users
         * @param hits the hits
         * @param hitsTime the hits time
         * @param resourceHits the resource hits
         * @param cacheResHits the cache res hits
         * @param cacheResLoadHits the cache res load hits
         * @param pools the pools
         */
        public MonitorRecord(long max, long total, long free, int maxUsers, long hits, long hitsTime
        , long resourceHits, long cacheResHits, long cacheResLoadHits, HashMap pools)
        {
            this.max=max;
            this.total=total;
            this.free=free;
            this.time=System.currentTimeMillis();
            this.maxUsers=maxUsers;
            this.hits=hits;
            this.setHitsTime(hitsTime);
            this.resourceHits=resourceHits;
            this.cacheResHits=cacheResHits;
            this.cacheResLoadHits=cacheResLoadHits;
            this.setPools(pools);
        }

        /**
         * Getter for property max.
         * @return Value of property max.
         */
        public long getMaxMemory()
        {
            return max;
        }

        /**
         * Setter for property max.
         * @param max New value of property max.
         */
        public void setMaxMemory(long max)
        {
            this.max = max;
        }

        /**
         * Getter for property total.
         * @return Value of property total.
         */
        public long getTotalMemory()
        {
            return total;
        }

        /**
         * Setter for property total.
         * @param total New value of property total.
         */
        public void setTotalMemory(long total)
        {
            this.total = total;
        }

        /**
         * Getter for property free.
         * @return Value of property free.
         */
        public long getFreeMemory()
        {
            return free;
        }

        /**
         * Setter for property free.
         * @param free New value of property free.
         */
        public void setFreeMemory(long free)
        {
            this.free = free;
        }

        /**
         * Gets the used memory.
         * 
         * @return the used memory
         */
        public long getUsedMemory()
        {
            return total-free;
        }
        
        /**
         * Sets the time.
         * 
         * @param time the new time
         */
        public void setTime(long time)
        {
            this.time=time;
        }
        
        /**
         * Getter for property time.
         * @return Value of property time.
         */
        public long getTime()
        {
            return time;
        }          

        /**
         * Getter for property time.
         * @return Value of property time.
         */
        public Date getDate()
        {
            return new Date(time);
        }   
        
        /**
         * Getter for property maxUsers.
         * @return Value of property maxUsers.
         */
        public int getMaxUsers()
        {
            return maxUsers;
        }
        
        /**
         * Setter for property maxUsers.
         * @param maxUsers New value of property maxUsers.
         */
        public void setMaxUsers(int maxUsers)
        {
            this.maxUsers = maxUsers;
        }
        
        /**
         * Getter for property hits.
         * @return Value of property hits.
         */
        public long getHits()
        {
            return hits;
        }
        
        /**
         * Setter for property hits.
         * @param hits New value of property hits.
         */
        public void setHits(long hits)
        {
            this.hits = hits;
        }
        
        /**
         * Getter for property resourceHits.
         * @return Value of property resourceHits.
         */
        public long getResourceHits()
        {
            return resourceHits;
        }
        
        /**
         * Setter for property resourceHits.
         * @param resourceHits New value of property resourceHits.
         */
        public void setResourceHits(long resourceHits)
        {
            this.resourceHits = resourceHits;
        }
        
        /**
         * Getter for property cacheResHits.
         * @return Value of property cacheResHits.
         */
        public long getCacheResHits()
        {
            return cacheResHits;
        }
        
        /**
         * Setter for property cacheResHits.
         * @param cacheResHits New value of property cacheResHits.
         */
        public void setCacheResHits(long cacheResHits)
        {
            this.cacheResHits = cacheResHits;
        }
        
        /**
         * Getter for property cacheResLoadHits.
         * @return Value of property cacheResLoadHits.
         */
        public long getCacheResLoadHits()
        {
            return cacheResLoadHits;
        }
        
        /**
         * Setter for property cacheResLoadHits.
         * @param cacheResLoadHits New value of property cacheResLoadHits.
         */
        public void setCacheResLoadHits(long cacheResLoadHits)
        {
            this.cacheResLoadHits = cacheResLoadHits;
        }

        /**
         * Gets the hits time.
         * 
         * @return the hits time
         */
        public long getHitsTime()
        {
            return hitsTime;
        }

        /**
         * Sets the hits time.
         * 
         * @param hitsTime the new hits time
         */
        public void setHitsTime(long hitsTime)
        {
            this.hitsTime = hitsTime;
        }

        /**
         * Gets the pools.
         * 
         * @return the pools
         */
        public HashMap getPools()
        {
            return pools;
        }

        /**
         * Sets the pools.
         * 
         * @param pools the new pools
         */
        public void setPools(HashMap pools)
        {
            this.pools = pools;
        }
        
    }
}
