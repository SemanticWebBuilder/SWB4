

package org.semanticwb.platform;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.semanticwb.Logger;
import org.semanticwb.SWBContext;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.db.DBConnectionPool;


public class SWBMonitor implements SWBContextObject
{
    static Logger log=SWBUtils.getLogger(SWBMonitor.class);    
    
    private Timer timer;
    private Vector vec=new Vector();
    private int max=500;
    private int delays = 5;
    private TimerTask t=null;
    
    private long instanceHits=0;
    private long instanceHitTime=15;
    
    SWBContext context;
    
    public SWBMonitor()
    {
    }

    public void init(SWBContext context)
    {
        this.context=context;
        vec=new Vector(max);
        t=new TimerTask(){
            public void run()
            {
                _run();
            }
        };
        timer = new Timer();
        timer.schedule(t, delays*1000, delays*1000);

        log.event("SWBMonitor Started ("+max+","+delays+"s)");
    }        
    
    public void _run()
    {
        long maxm=Runtime.getRuntime().maxMemory();
        long totalm=Runtime.getRuntime().totalMemory();
        long freem=Runtime.getRuntime().freeMemory();
        int maxUsers=0;//WBUserMgr.getInstance().getUsers().size();
        long hits=instanceHits;     //WBAccessLog.getInstance().getInstanceHits();
        long hitsTime=instanceHitTime/100;
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
    
    public Vector getMonitorRecords()
    {
        return vec;
    }
    
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
    
    public void addinstanceHit(long time)
    {
        time=time*100;
        instanceHits++;
        instanceHitTime=(instanceHitTime*49+time)/50;
    }
    
    public class PoolRecord
    {
        private long hits=0;
        private long hitsTime=0;
        private int total=0;
        private int free=0;
        private int max=0;
        
        public PoolRecord(long hits, long hitsTime, int total, int free, int max)
        {
            this.hits=hits;
            this.hitsTime=hitsTime;
            this.total=total;
            this.free=free;
            this.max=max;
        }

        public long getHits()
        {
            return hits;
        }

        public void setHits(long hits)
        {
            this.hits = hits;
        }

        public int getTotalConnections()
        {
            return total;
        }

        public void setTotalConnections(int total)
        {
            this.total = total;
        }

        public int getFreeConnections()
        {
            return free;
        }
        
        public int getUsedConnections()
        {
            return total-free;
        }        

        public void setFreeConnections(int free)
        {
            this.free = free;
        }

        public int getMaxConnections()
        {
            return max;
        }

        public void setMaxConnections(int max)
        {
            this.max = max;
        }

        public long getHitsTime()
        {
            return hitsTime;
        }

        public void setHitsTime(long hitsTime)
        {
            this.hitsTime = hitsTime;
        }
    }
    

    public class MonitorRecord
    {
        private long max=0;
        private long total=0;
        private long free=0;
        private long time=0;
        
        private long hits=0;
        private long hitsTime=0;
        
        private int maxUsers=0;
        
        private long resourceHits=0;
        private long cacheResHits=0;
        private long cacheResLoadHits=0;        
        
        private HashMap pools;

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

        public long getUsedMemory()
        {
            return total-free;
        }
        
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

        public long getHitsTime()
        {
            return hitsTime;
        }

        public void setHitsTime(long hitsTime)
        {
            this.hitsTime = hitsTime;
        }

        public HashMap getPools()
        {
            return pools;
        }

        public void setPools(HashMap pools)
        {
            this.pools = pools;
        }
        
    }
}
