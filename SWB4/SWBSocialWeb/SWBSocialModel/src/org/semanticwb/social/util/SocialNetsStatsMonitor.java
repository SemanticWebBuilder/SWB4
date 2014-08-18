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

            timer.schedule(new monitorSocialNetStats(), 0, (60 * 1000)*10); //Cada 10 minutos

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
                            SocialStatsMonitorable socialStatMonitorable=(SocialStatsMonitorable)socialNet;
                            socialStatMonitorable.getSocialNetStats(socialNet);
                        }
                    }
                }
            }

        }
    }    
}
