/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.*;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class MonitorPostOutResponsesMgr {
    
    private static Logger log = SWBUtils.getLogger(MonitorPostOutResponsesMgr.class);
    static final int MILISEG_IN_SEGUNDO=1000;
    
    
    
    /*
     * Metodo constructor que levanta listener de cada uno de los streams de cada sitio de tipo SWBSocial
     */
    public MonitorPostOutResponsesMgr() {
        try {
            System.out.println("Entra a MonitorPostOutResponsesMgr..-1");
            int periodTime = (60*MILISEG_IN_SEGUNDO)*1; //10 minutos
            Timer timer = new Timer();
            timer.schedule(new MonitorPostOutResponsesMgr.MonitorTask(), 0,periodTime);
            System.out.println("Entra a MonitorPostOutResponsesMgr..-2");
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    
     /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un stream.
     */ 
    private static class MonitorTask extends TimerTask
    {
        public MonitorTask()
        {
        }

        public void run() {
             System.out.println("Entra a MonitorPostOutResponsesMgr/Run-1");
             Iterator<WebSite> itWebSites = SWBContext.listWebSites(false);
             while (itWebSites.hasNext()) {
                WebSite wsite = itWebSites.next();
                if (wsite.isActive() && !wsite.isDeleted() && wsite instanceof SocialSite) 
                {
                    Iterator<SemanticObject> itPostOut2Monitor=wsite.getSemanticModel().listSubjects(PostOut.social_isClosedforResponses, false);
                    while(itPostOut2Monitor.hasNext())
                    {
                        SemanticObject semObj=itPostOut2Monitor.next();
                        if(semObj.createGenericInstance() instanceof PostOut)
                        {
                            PostOut postOut=(PostOut)semObj.createGenericInstance();
                            if(postOut.getSocialTopic()!=null && !postOut.getSocialTopic().isDeleted() && postOut.getSocialTopic().isActive() && postOut.getSocialTopic().getSt_numDays2ClosePostOuts()>0 && postOut.getSocialTopic().getSt_numDays2ClosePostOuts()<100)
                            {    
                                int days=SWBSocialUtil.Util.Datediff(postOut.getPo_publishDate(), Calendar.getInstance().getTime());
                                //Solo si son menos de 30 días (configurar despues) se monitorearan respuestas del PostOut, si ya pasaron se cierra 
                                //para no volver a ser monitoreado (postOut.setIsClosedforResponses(true))
                                System.out.println("Entra a MonitorPostOutResponsesMgr/Run-4:"+days);
                                if(days<=postOut.getSocialTopic().getSt_numDays2ClosePostOuts())
                                {
                                    try{
                                        new PostOutResClassifierThread(postOut).start();
                                    }catch(Exception e)
                                    {
                                        log.error(e);
                                    }
                                }else { 
                                    System.out.println("Entra a MonitorPostOutResponsesMgr/Run-5/Elimina PostOut del monitoreo por el tiempo:"+postOut.getURI());
                                    //Si ya pasaron mas de 30 días..., se cierra el PostOut, para que a la proxima no se tome en cuenta, 
                                    //no nos interesan los comentarios mayores a 30 días, ver si se hace como parametro configurable.
                                    postOut.setIsClosedforResponses(true);
                                }
                            }
                        }
                    }
                }
             }
             
        }
     }
    
    
    
}