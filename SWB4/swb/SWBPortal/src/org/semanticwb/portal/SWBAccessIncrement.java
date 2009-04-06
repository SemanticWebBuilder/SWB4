/*
 * WBTopicAccessIncrement.java
 *
 * Created on 4 de octubre de 2002, 16:18
 */

package org.semanticwb.portal;

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;

/** objeto: incrementa los accesos de un topico y sus recursos asociados.
 * @author Javier Solis Gonzalez
 */
public class SWBAccessIncrement implements SWBAppObject
{
    public static Logger log = SWBUtils.getLogger(SWBAccessIncrement.class);

    static private SWBAccessIncrement instance = null;       // The single instance

    /** Creates a new instance of WBTopicAccessIncrement */
    public SWBAccessIncrement()
    {
        log.event("Initializing WBTopicAccessIncrement");
    }

    /** Get Instance.
     * @return  */
    static public SWBAccessIncrement getInstance()
    {
        if (instance == null)
        {
            instance = new SWBAccessIncrement();
            instance.init();
        }
        return instance;
    }

    public void destroy()
    {
        instance=null;        
        log.info("SWBTopicAccessIncrement End...");
    }

    public void init()
    {
    }

    public void refresh()
    {
    }
    //TODO:
/*
    /**
     * @param str  * /
    public synchronized void log(String str)
    {
        updateRes(str);
    }

    /**
     * @param str  * /
    public synchronized void logHit(String str)
    {
        updateResHit(str);
    }

    /**
     * @param str  * /

    public void updateRes(String str)
    {
        try
        {
            StringTokenizer st = new StringTokenizer(str, "|");
            if (st.hasMoreTokens())
            {
                String date = st.nextToken();
                String ipuser = st.nextToken();
                String ipserver = st.nextToken();
                String sess = st.nextToken();
                String map = st.nextToken();
                String topic = st.nextToken();
                String rep = st.nextToken();
                String user = st.nextToken();
                String usertype = st.nextToken();
                String device = st.nextToken();
                String lang = st.nextToken();
                String stime = st.nextToken();

                try
                {
                    RecTopic rec = DBTopicMap.getInstance().getTopic(map, topic);
                    if (rec.incViews() && !WBUtils.getInstance().isClient()) rec.updateViews();
                } catch (Exception e)
                {
                    log.error("Error to increment views of topic:"+topic,e);
                }

                while (st.hasMoreTokens())
                {
                    String resid = st.nextToken();
                    try
                    {
                        long res = Long.parseLong(resid);
                        RecResource recr=null;
                        if(resid.length()>1 && resid.charAt(0)=='0')
                        {
                            recr = DBResource.getInstance().getResource(TopicMgr.TM_GLOBAL,res);
                        }else
                        {
                            recr = DBResource.getInstance().getResource(map,res);
                        }
                        if (recr!=null && recr.incViews() && !WBUtils.getInstance().isClient()) recr.updateViews();
                    } catch (Exception e)
                    {
                        //AFUtils.log(e,"Error al incrementar acceso del recurso:"+resid,true);
                    }
                }
            }
        } catch (Exception e)
        {
            log.error("WBTopicAccessIncrement Error:" + str, e);
        }
    }
*
    / **
     * @param str  * /
    public void updateResHit(String str)
    {
        try
        {
            StringTokenizer st = new StringTokenizer(str, "|");
            if (st.hasMoreTokens())
            {
                String date = st.nextToken();
                String ipuser = st.nextToken();
                String ipserver = st.nextToken();
                String sess = st.nextToken();
                String map = st.nextToken();
                String topic = st.nextToken();
                String rep = st.nextToken();
                String user = st.nextToken();
                String usertype = st.nextToken();
                String device = st.nextToken();
                String lang = st.nextToken();

                //RecTopic rec=DBTopicMap.getInstance().getTopic(map,topic);
                //if(rec.incViews()&&!AFUtils.getInstance().isClient())rec.updateViews();

                while (st.hasMoreTokens())
                {
                    String resid = st.nextToken();
                    try
                    {
                        long res = Long.parseLong(resid);
                        RecResource recr=null;
                        if(resid.length()>1 && resid.charAt(0)=='0')
                        {
                            recr = DBResource.getInstance().getResource(TopicMgr.TM_GLOBAL,res);
                        }else
                        {
                            recr = DBResource.getInstance().getResource(map,res);
                        }                        
                        if (recr!=null && recr.incHits() && !WBUtils.getInstance().isClient()) recr.updateViews();
                    } catch (Exception e)
                    {
                        //AFUtils.log(e,"Error al incrementar hits del recurso:"+resid,true);
                    }
                }
            }
        } catch (Exception e)
        {
            log.error("WBTopicAccessIncrement Error:" + str, e);
        }
    }

*/
}
