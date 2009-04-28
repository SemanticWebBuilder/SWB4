
/*
 * WBIndexMgr.java
 *
 * Created on 11 de mayo de 2006, 06:20 PM
 */

package org.semanticwb.portal.indexer;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBIndexMgr implements SWBAppObject
{
    private static Logger log=SWBUtils.getLogger(SWBIndexMgr.class);

    private HashMap indexers=new HashMap();
    private Properties prop;   
    private String defaultIndexer="swb";
    
    private Timer timer = null;
    private Timestamp lastupdate;

    
    /** Creates a new instance of WBIndexMgr */
    public SWBIndexMgr()
    {
        this.lastupdate = new Timestamp(new java.util.Date().getTime());
        if(!SWBPlatform.isClient())
        {
            log.event("Initializing SWBIndexMgr");
        }else
        {
            log.info("WBIndexMgr can not be Initialized (isClient)...");
        }
        init();
    }
    
    public void destroy()
    {
        timer.cancel();
        log.event("Webbuilder SWBIndexMgr Stoped");
    }
    
    public void init()
    {
        if(SWBPlatform.isClient())return;
        
        prop=SWBUtils.TEXT.getPropertyFile("/indexer.properties");
        Enumeration propNames = prop.propertyNames();
        while (propNames.hasMoreElements())
        {
            String name = (String) propNames.nextElement();
            //System.out.println("name:"+name);
            if (name.endsWith(".class"))
            {
                String pname = name.substring(0, name.lastIndexOf("."));
                String clsname = prop.getProperty(pname + ".class");
                log.info("Initializing Indexer: "+pname);
                
                try
                {
                    Class cls = Class.forName(clsname);
                    SWBIndexer ind = (SWBIndexer)cls.newInstance();
                    if(ind!=null)
                    {
                        indexers.put(pname,ind);
                        ind.init(pname, prop);
                    }
                }catch(Exception e)
                {
                    log.error(e);
                }
                
            }
        }
       
        defaultIndexer=prop.getProperty("defaultIndexer","swb");
        
        int delays=Integer.parseInt(prop.getProperty("delay","30"));
        TimerTask t=new TimerTask()
        {
            public void run()
            {
                _run();
            }
        };
        timer = new Timer();
        timer.schedule(t, delays*1000, delays*1000);   
     
    }
    
    /** Get Instance. */

    public void _run()
    {
        //if (indexer == null || indexer.indexer == null || !indexer.indexer.isAlive())
        {
            //System.out.println("IndexMgr->Indexando cambios... "+lastupdate);
            try
            {
                //SELECT log_modelid, log_objuri, log_date FROM swb_admlog where log_date>"2009-04-25 22:59:10" group by log_modelid, log_objuri, log_date order by log_date
                //TODO:
                /*
                Iterator iter = DBDbSync.getInstance().getChanges(lastupdate);
                while (iter.hasNext())
                {
                    RecDbSync rec = (RecDbSync) iter.next();
                    String table = rec.getDbTable();
                    String action = rec.getAction();
                    long id = rec.getIdint();
                    String ids = rec.getIdstr();
                    Timestamp date = rec.getDate();

                    //System.out.println("WBIndexMgr-->rec:"+table+" "+action+" "+id+" "+ids);
                    if (date.after(lastupdate)) lastupdate = date;
                    try
                    {
                        if (table.equals("wboccurrence"))
                        {
                            if (!action.equals("load"))
                            {
                                StringTokenizer st = new StringTokenizer(ids, " ");
                                if (st.hasMoreTokens())
                                {
                                    String occid = st.nextToken();
                                    String tmid = st.nextToken();
                                    SWBIndexer indexer=getTopicMapIndexer(tmid);
                                    if(indexer!=null)
                                    {
                                        String tpid = st.nextToken();

                                        WebSite tm = SWBContext.getWebSite(tmid);
                                        if (tm != null)
                                        {
                                            WebPage tp = tm.getWebPage(tpid);
                                            if (tp != null)
                                            {
                                                Occurrence occ = tp.getOccurrence(occid);
                                                if (occ != null)
                                                {
                                                    WebPage ttype = occ.getInstanceOf().getTopicRef();
                                                    if (ttype != null && ttype.getId().equals("REC_WBContent"))
                                                    {
                                                        long rid=Long.parseLong(occ.getResourceData());
                                                        //System.out.println("remove:"+rid);
                                                        indexer.removeContent(rid, tp.getMap().getId());
                                                        if(occ.isActive())
                                                        {
                                                            //System.out.println("index:"+rid);
                                                            indexer.indexContent(rid, tp);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (table.equals("wbresource"))
                        {
                            if (!action.equals("load"))
                            {
                                SWBIndexer indexer=getTopicMapIndexer(ids);
                                if(indexer!=null)
                                {
                                    Iterator it=indexer.search4Id(ids+" "+id).iterator();
                                    if(it.hasNext())
                                    {
                                        SWBIndexObj obj=(SWBIndexObj)it.next();
                                        indexer.removeContent(id, ids);
                                        WebSite map=SWBContext.getWebSite(obj.getTopicMapID());
                                        if(map!=null)
                                        {
                                            WebPage tpi=map.getWebPage(obj.getTopicID());
                                            if(tpi!=null)
                                            {
                                                indexer.indexContent(id, tpi);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (table.equals("wbtopic"))
                        {
                            if (!action.equals("load"))
                            {
                                StringTokenizer st = new StringTokenizer(ids, " ");
                                if (st.hasMoreTokens())
                                {
                                    String tpid = st.nextToken();
                                    String tmid = st.nextToken();
                                    SWBIndexer indexer=getTopicMapIndexer(tmid);
                                    if(indexer!=null)
                                    {
                                        if (action.equals("remove"))
                                        {
                                            indexer.removeTopic(tmid,tpid,true);
                                        } else
                                        {
                                            indexer.removeTopic(tmid,tpid,true);
                                            WebSite map=SWBContext.getWebSite(tmid);
                                            if(map!=null)
                                            {
                                                WebPage tpi=map.getWebPage(tpid);
                                                if(tpi!=null)
                                                {
                                                    indexer.indexWebPage(tpi,true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e)
                    {
                        log.error(e);
                    }
                }
                */
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }    
    
    
    public void refresh()
    {
    }
    
    /**
     * Getter for property indexers.
     * @return Value of property indexers.
     */
    public java.util.HashMap getIndexers()
    {
        return indexers;
    }
    
    public SWBIndexer getIndexer(String name)
    {
        return (SWBIndexer)indexers.get(name);
    }    
    
    public SWBIndexer getTopicMapIndexer(String tmid)
    {
        WebSite tm=SWBContext.getWebSite(tmid);
        if(tm==null)return null;
        //TODO:Agregar indexador al modelo
        //return (SWBIndexer)indexers.get(tm.getIndexer());
        return (SWBIndexer)indexers.get("swb");
    }        
    
}
