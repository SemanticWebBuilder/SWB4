/*
 * WBTopicAccessIncrement.java
 *
 * Created on 4 de octubre de 2002, 16:18
 */

package org.semanticwb.portal.access;

import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;

/** objeto: incrementa los accesos de un topico y sus recursos asociados.
 * @author Javier Solis Gonzalez
 */
public class SWBAccessIncrement implements SWBAppObject
{
    public static Logger log = SWBUtils.getLogger(SWBAccessIncrement.class);

    /** Creates a new instance of WBTopicAccessIncrement */
    public SWBAccessIncrement()
    {
        log.event("Initializing SWBAccessIncrement...");
    }

    public void destroy()
    {
        log.info("SWBAccessIncrement End...");
    }

    public void init()
    {
    }

    public void refresh()
    {
    }


    /**
     * @param str
     */
    public synchronized void log(String str)
    {
        updateRes(str);
    }

    /**
     * @param str
     */
    public synchronized void logHit(String str)
    {
        updateResHit(str);
    }

    /**
     * @param str
     */

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
                    WebPage rec=SWBContext.getWebSite(map).getWebPage(topic);
                    if(rec.incViews() && !SWBPlatform.isClient()) rec.updateViews();
                } catch (Exception e)
                {
                    log.error("Error to increment views of WebPage:"+topic,e);
                }

                while (st.hasMoreTokens())
                {
                    String resid = st.nextToken();
                    try
                    {
                        Resource recr=null;
                        if(resid.length()>1 && resid.charAt(0)=='0')
                        {
                            recr = SWBContext.getGlobalWebSite().getResource(resid.substring(1));
                        }else
                        {
                            recr = SWBContext.getWebSite(map).getResource(resid);
                        }
                        if (recr!=null && recr.incViews() && !SWBPlatform.isClient())recr.updateViews();
                    } catch (Exception e)
                    {
                        log.error("Error to increment access of Resource:"+resid,e);
                    }
                }
            }
        } catch (Exception e)
        {
            log.error("SWBAccessIncrement Error:" + str, e);
        }
    }

    /**
     * @param str
     */
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
                        Resource recr=null;
                        if(resid.length()>1 && resid.charAt(0)=='0')
                        {
                            recr = SWBContext.getGlobalWebSite().getResource(resid.substring(0));
                        }else
                        {
                            recr = SWBContext.getWebSite(map).getResource(resid);
                        }                        
                        if (recr!=null && recr.incHits() && !SWBPlatform.isClient()) recr.updateViews();
                    } catch (Exception e)
                    {
                        log.error("Error to increment Resource Hits:"+resid,e);
                    }
                }
            }
        } catch (Exception e)
        {
            log.error("SWBAccessIncrement Error:" + str, e);
        }
    }
}
