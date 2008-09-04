
/*
 * WBResourceTraceMgr.java
 *
 * Created on 27 de enero de 2004, 19:22
 */

package org.semanticwb.portal.resources;

import java.util.*;
import java.io.*;

import java.sql.Timestamp;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/** Objeto: Manejador de objetos WBResourceTrace disponibles en memoria.
 *
 * Object: Manager of WBResourceTrace objects availables in memory.
 *
 * @author  Administrador
 */
public class SWBResourceTraceMgr extends TimerTask
{
    private static Logger log = SWBUtils.getLogger(SWBResourceTraceMgr.class);

    private Timer timer = null;
    private Timestamp lastupdate;

    private Hashtable types = new Hashtable();
    private Hashtable typesMeter = new Hashtable();
    private boolean resourceTrace=false;


    /** Creates a new instance of WBResourceTraceMgr */
    public SWBResourceTraceMgr()
    {
        this.lastupdate = new Timestamp(new java.util.Date().getTime());
        if(SWBPlatform.getEnv("swb/resourceTrace","false").equalsIgnoreCase("true"))
        {
            resourceTrace=true;
            log.error("Initializing SWBResourceTraceMgr...");
        }
    }

    public void addResource(SWBResourceTrace res)
    {
        if (res != null)
        {
            Hashtable type = (Hashtable) types.get(res.getType());
            if (type == null)
            {
                type = new Hashtable();
                types.put(res.getType(), type);
            }
            type.put(new Long(res.getId()), res);
        }
    }

    public void removeResource(SWBResourceTrace res)
    {
        if (res != null)
        {
            Hashtable type = (Hashtable) types.get(res.getType());
            if (type != null)
            {
                type.remove(new Long(res.getId()));
            }
            addResourceTime(res);
        }
    }
    
    public void addResourceTime(SWBResourceTrace res)
    {
        if (res != null)
        {
            long fin=System.currentTimeMillis();
            SWBResourceTraceMeter type = (SWBResourceTraceMeter) getTypesMeter().get(res.getType());
            if (type == null)
            {
                type= new SWBResourceTraceMeter();
                type.setId(res.getWBResource().getResourceBase().getPortletType().getId());
                type.setTypeMap(res.getWBResource().getResourceBase().getPortletType().getWebSite().getId());
                type.setName(res.getWBResource().getResourceBase().getPortletType().getTitle());
                getTypesMeter().put(res.getType(), type);
            }
            type.addTime(fin-res.getTime());
        }
    }
    
    public void renderTraced(SWBResource res, HttpServletRequest request, HttpServletResponse response, SWBParamRequest resReq ) throws SWBException, java.io.IOException
    {
        SWBResourceTrace trace = null;
        if(resourceTrace)
        {
            trace=new SWBResourceTrace(res, request, resReq);
            addResource(trace);
        }
        try
        {
            res=SWBPortal.getResourceMgr().getResourceCached(res, request, resReq);
            if(resReq.getCallMethod()==resReq.Call_DIRECT)
            {
                res.render(request, response, resReq);
            }else
            {
                SWBResourceWindowRender resw=new SWBResourceWindowRender(res);
                resw.render(request, response, resReq);
            }
        } catch (SWBException e)
        {
            throw e;
        } catch (IOException e)
        {
            throw e;
        } finally
        {
            if(trace!=null)
            {
                removeResource(trace);
                log.debug("<!-- res:"+res.getResourceBase().getPortletType().getTitle()+":"+res.getResourceBase().getTitle()+":"+res.getResourceBase().getId()+":"+res.getResourceBase().getWebSite().getId()+" "+(System.currentTimeMillis()-trace.getTime())+"ms -->");            }
        }
    }

    public void run()
    {
        //System.out.println("Checking Connections...");
        long actual = System.currentTimeMillis();
        Enumeration en = types.elements();
        while (en.hasMoreElements())
        {
            Hashtable type = (Hashtable)en.nextElement();
            Enumeration en2 = type.elements();
            while (en2.hasMoreElements())
            {
                SWBResourceTrace t = (SWBResourceTrace) en2.nextElement();
                long time=t.getTime();
                if ((time + 300000L) < actual)
                {
                    log.warn("Connection Time Lock, (" + ((actual - time) / 1000) + "s)" + t);
                }
            }
        }
    }

    public void destroy()
    {
        log.event("WBResourceTraceMgr Finished...");
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }

    }

    public void init()
    {
        log.event("Initializing WBResourceTraceMgr...");
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 30000, 30000);
    }

    public void stop()
    {
        log.event("WBResourceTraceMgr Stoped...");
        if (timer != null)
        {
            timer.cancel();
            this.cancel();
            timer = null;
        }
    }
    
    public void clearResourceTrace()
    {
        types = new Hashtable();
        //nresTraced=0;
    }

    /** Getter for property pools.
     * @return Value of property pools.
     *
     */
    public java.util.Hashtable getTypes()
    {
        return types;
    }

    /**
     * Getter for property resourceTrace.
     * @return Value of property resourceTrace.
     */
    public boolean isResourceTrace()
    {
        return resourceTrace;
    }
    
    /**
     * Setter for property resourceTrace.
     * @param resourceTrace New value of property resourceTrace.
     */
    public void setResourceTrace(boolean resourceTrace)
    {
        this.resourceTrace = resourceTrace;
    }
    
    /**
     * Getter for property nresTraced.
     * @return Value of property nresTraced.
     */
    public int getResourceTracedSize()
    {
        int ret=0;
        Enumeration en = types.elements();
        while (en.hasMoreElements())
        {
            Hashtable type = (Hashtable)en.nextElement();
            ret+=type.size();
        }
        return ret;
    }

    public Hashtable getTypesMeter()
    {
        return typesMeter;
    }
    
    public Set getSortTypesMeter()
    {
        TreeSet set=new TreeSet(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                long x=0;
                long y=0;
                x=((SWBResourceTraceMeter)o1).getAvgTime();
                y=((SWBResourceTraceMeter)o2).getAvgTime();
                if (x < y)
                    return 1;
                else
                    return -1;        
            }            
        });
        set.addAll(getTypesMeter().values());
        return set;
    }
        
}
