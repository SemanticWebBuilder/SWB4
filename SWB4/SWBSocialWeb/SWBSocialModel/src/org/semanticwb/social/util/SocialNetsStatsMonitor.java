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

import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.SocialStatsMonitorable;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 */
public class SocialNetsStatsMonitor {

    private static Logger log = SWBUtils.getLogger(SocialNetsStatsMonitor.class);
    static final int MILISEG_IN_SEGUNDO = 1000;

    public SocialNetsStatsMonitor() {
        try {
            //Inicio del timer
            Calendar hour2Start = Calendar.getInstance();
            hour2Start.set(hour2Start.get(Calendar.YEAR),
                    hour2Start.get(Calendar.MONTH),
                    hour2Start.get(Calendar.DATE), 23, 50, 00); //Hora a las 11:50 pm

            Calendar timeNow = Calendar.getInstance(); //Hora actual

            //Milisegundos para empezar por primera vez el timer
            long time2Start = hour2Start.getTimeInMillis() - timeNow.getTimeInMillis();

            int oneDay = 60 * MILISEG_IN_SEGUNDO * 60 * 24;

            //System.out.println("StreamCleanerMsgbyDays/time2Start Para empezar por primera vez:"+time2Start);

            Timer timer = new Timer();

            timer.schedule(new monitorSocialNetStats(), 0, (60 * 1000)*10); //Cada 10 minuto

            //Que empiece hoy a las 11:59 pm y vuelve a iterar un dia despues y así se siga
            log.event("Initializing SocialNetsStatsMonitor, starts in:" + time2Start + "ms, periodicity:" + oneDay + ",ms");
            //timer.schedule(new SocialNetsStatsMonitor.monitorSocialNetStats(), time2Start, oneDay);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un
     * stream.
     */
    private class monitorSocialNetStats extends TimerTask {

        public monitorSocialNetStats() {
        }

        /*
         * METOO RUN
         * Metodo que revisa todos los streams activos en todas las marcas a
         */
        public void run() {
            //System.out.println("Ejecuta SocialNetsStatsMonitor/run-1");
            Iterator<SocialSite> itSocialSites=SocialSite.ClassMgr.listSocialSites();
            while(itSocialSites.hasNext())
            {
                SocialSite socialSite=itSocialSites.next();
                if(socialSite.isActive() && !socialSite.isDeleted())
                {
                    Iterator<SocialNetwork> itSocialNets=socialSite.listSocialNetworks();
                    while(itSocialNets.hasNext())
                    {
                        SocialNetwork socialNet=itSocialNets.next();
                        if(socialNet instanceof SocialStatsMonitorable && socialNet.isActive() && !socialNet.isDeleted())
                        {
                            //System.out.println("Ejecuta SocialNetsStatsMonitor/run-2");
                            SocialStatsMonitorable socialStatMonitorable=(SocialStatsMonitorable)socialNet;
                            socialStatMonitorable.getSocialNetStats(socialNet);
                        }
                    }
                }
            }

        }
    }    
}
