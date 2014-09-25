/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
import org.semanticwb.social.listener.MonitorPostOutResponsesMgr;

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
            pflowMgr = new SocialPFlowMgr();    //Sin threads
            pflowMgr.init(); //Sin threads
            new SWBSocialCalendarMgr(); //Se dispara un solo thread c/minuto + los threads que se disparen en c/red social en ese minuto (si aplicara)
            new ListenerMgr();  //Trae un Thread + los threads que se disparen en c/red social en ese minuto (si aplicara)
            new MonitorMgr();   //Trae un Thread + los threads que se disparen en c/red social en ese minuto (si aplicara)
            
            new StreamCleanerMsgbyNumber(); //Eliminador automatico por número de postIns en streams. Se levanta un thread cada minuto.
            
            new StreamCleanerMsgbyDays(); //Eliminador automatico por número de días de postIns en streams. Se levanta un thread cada 24 hrs.
            
            new SocialNetsStatsMonitor(); //SocialNetworks monitor, monitorizes some socialnetworks stats 
            
            new SWBSocialUtil().createInstance(); //Sin threads
            
            new MonitorPostOutResponsesMgr();
            
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
