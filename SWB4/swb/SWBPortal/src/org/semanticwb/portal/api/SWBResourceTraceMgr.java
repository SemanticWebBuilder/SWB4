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
package org.semanticwb.portal.api;

import java.util.*;
import java.io.*;

import java.sql.Timestamp;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/** Objeto: Manejador de objetos WBResourceTrace disponibles en memoria.
 *
 * Object: Manager of WBResourceTrace objects availables in memory.
 *
 * @author  Administrador
 */
public class SWBResourceTraceMgr extends TimerTask
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBResourceTraceMgr.class);

    /** The timer. */
    private Timer timer = null;
    
    /** The lastupdate. */
    private Timestamp lastupdate;

    /** The types. */
    private Hashtable types = new Hashtable();
    
    /** The types meter. */
    private Hashtable typesMeter = new Hashtable();
    
    /** The resource trace. */
    private boolean resourceTrace=false;


    /**
     * Creates a new instance of WBResourceTraceMgr.
     */
    public SWBResourceTraceMgr()
    {
        this.lastupdate = new Timestamp(new java.util.Date().getTime());
        if(SWBPlatform.getEnv("swb/resourceTrace","false").equalsIgnoreCase("true"))
        {
            resourceTrace=true;
            log.event("Initializing SWBResourceTraceMgr...");
        }
    }

    /**
     * Adds the resource.
     * 
     * @param res the res
     */
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

    /**
     * Removes the resource.
     * 
     * @param res the res
     */
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
    
    /**
     * Adds the resource time.
     * 
     * @param res the res
     */
    public void addResourceTime(SWBResourceTrace res)
    {
        if (res != null)
        {
            long fin=System.currentTimeMillis();
            SWBResourceTraceMeter type = (SWBResourceTraceMeter) getTypesMeter().get(res.getType());
            if (type == null)
            {
                type= new SWBResourceTraceMeter();
                type.setId(res.getWBResource().getResourceBase().getResourceType().getId());
                type.setTypeMap(res.getWBResource().getResourceBase().getResourceType().getWebSite().getId());
                type.setName(res.getWBResource().getResourceBase().getResourceType().getTitle());
                getTypesMeter().put(res.getType(), type);
            }
            long time=fin-res.getTime();
            type.addTime(time);
            log.trace(res.getType()+" "+time);
            //System.out.println(res.getType()+" "+time);
        }
    }
    
    /**
     * Render traced.
     * 
     * @param res the res
     * @param request the request
     * @param response the response
     * @param resReq the res req
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void renderTraced(SWBResource res, HttpServletRequest request, HttpServletResponse response, SWBParamRequest resReq ) throws SWBResourceException, java.io.IOException
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
        } catch (SWBResourceException e)
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
                log.debug("<!-- res:"+res.getResourceBase().getResourceType().getTitle()+":"+res.getResourceBase().getTitle()+":"+res.getResourceBase().getId()+":"+res.getResourceBase().getWebSite().getId()+" "+(System.currentTimeMillis()-trace.getTime())+"ms -->");            }
        }
    }

    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     */
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

    /**
     * Destroy.
     */
    public void destroy()
    {
        log.event("WBResourceTraceMgr Finished...");
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
        log.event("Initializing WBResourceTraceMgr...");
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 30000, 30000);
    }

    /**
     * Stop.
     */
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
    
    /**
     * Clear resource trace.
     */
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

    /**
     * Gets the types meter.
     * 
     * @return the types meter
     */
    public Hashtable getTypesMeter()
    {
        return typesMeter;
    }
    
    /**
     * Gets the sort types meter.
     * 
     * @return the sort types meter
     */
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
