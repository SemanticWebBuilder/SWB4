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
package org.semanticwb.portal.access;

import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;

// TODO: Auto-generated Javadoc
/** objeto: incrementa los accesos de un topico y sus recursos asociados.
 * @author Javier Solis Gonzalez
 */
public class SWBAccessIncrement implements SWBAppObject
{
    
    /** The log. */
    public static Logger log = SWBUtils.getLogger(SWBAccessIncrement.class);

    /**
     * Creates a new instance of WBTopicAccessIncrement.
     */
    public SWBAccessIncrement()
    {
        log.event("Initializing SWBAccessIncrement...");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#destroy()
     */
    /**
     * Destroy.
     */
    public void destroy()
    {
        log.info("SWBAccessIncrement End...");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#init()
     */
    /**
     * Inits the.
     */
    public void init()
    {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#refresh()
     */
    /**
     * Refresh.
     */
    public void refresh()
    {
    }


    /**
     * Log.
     * 
     * @param str the str
     */
    public synchronized void log(String str)
    {
        updateRes(str);
    }

    /**
     * Log hit.
     * 
     * @param str the str
     */
    public synchronized void logHit(String str)
    {
        updateResHit(str);
    }

    /**
     * Update res.
     * 
     * @param str the str
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
                    synchronized(rec)
                    {
                        if(rec.incViews() && !SWBPortal.isClient()) rec.updateViews();
                    }
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
                        if (recr!=null)
                        {
                            synchronized(recr)
                            {
                                if (recr.incViews() && !SWBPortal.isClient())recr.updateViews();
                            }
                        }
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
     * Update res hit.
     * 
     * @param str the str
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
                        if (recr!=null)
                        {
                            synchronized(recr)
                            {
                                if (recr.incHits() && !SWBPortal.isClient()) recr.updateViews();
                            }
                        }
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
