/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.social.PostMonitor;
import org.semanticwb.social.SocialFlow.SocialPFlowMgr;
import org.semanticwb.social.listener.ListenerMgr;
import org.semanticwb.social.listener.MonitorMgr;

/**
 *
 * @author jorge.jimenez
 */
public class SocialLoader implements SWBAppObject {

    private static Logger log = SWBUtils.getLogger(SocialLoader.class);
    
    /**
     * Is the manager for the publication flows in this portal.
     * <p>Es el administrador de los flujos de publicaci&oacute;n en este portal.</p>
     */
    private static SocialPFlowMgr pflowMgr;
    static private PostMonitor postMonitor=null;
   
    
    
     /*
     * Metodo constructor que levanta listener de cada uno de los streams de cada sitio de tipo SWBSocial
     */
    public SocialLoader() {
        try {
            System.out.println("Entra a Cargar:SocialLoader");
            pflowMgr = new SocialPFlowMgr();    //Sin threads
            pflowMgr.init(); //Sin threads
            new SWBSocialCalendarMgr(); //Se dispara un solo thread c/minuto + los threads que se disparen en c/red social en ese minuto (si aplicara)
            new ListenerMgr();  //Trae un Thread + los threads que se disparen en c/red social en ese minuto (si aplicara)
            new MonitorMgr();   //Trae un Thread + los threads que se disparen en c/red social en ese minuto (si aplicara)
            
            //new StreamCleanerMsgbyNumber(); //Eliminador automatico por número de postIns en streams. Se levanta un thread cada minuto.
            
            //new StreamCleanerMsgbyDays(); //Eliminador automatico por número de días de postIns en streams. Se levanta un thread cada 24 hrs.
            
            new SWBSocialUtil().createInstance(); //Sin threads
            
             //Charges the unique PostMonitor instance
            if(PostMonitor.ClassMgr.listPostMonitors(SWBContext.getAdminWebSite()).hasNext())
            {
                postMonitor=PostMonitor.ClassMgr.listPostMonitors(SWBContext.getAdminWebSite()).next();
            }
            
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    /**
     * Gets a reference to the publication flow manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de flujos de publicaci&oacute;n
     * de esta instancia de SWB</p>
     * @return a PFlowManager object for this instance of SWB.
     */
    public static SocialPFlowMgr getPFlowManager()
    {
        return pflowMgr;
    }
    
    public static PostMonitor getPostMonitor()
    {
        return postMonitor;
    }

    @Override
    public void init() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
