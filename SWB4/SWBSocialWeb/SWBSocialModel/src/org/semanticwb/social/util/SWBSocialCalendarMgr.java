/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.net.SocketException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.GenericObject;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialCalendar;
import org.semanticwb.social.SocialFlow.SocialPFlowMgr;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialCalendarMgr {

    private static Logger log = SWBUtils.getLogger(SWBSocialCalendarMgr.class);
    static final int MILISEG_IN_SEGUNDO = 1000;

    public SWBSocialCalendarMgr() {
        try {
            //System.out.println("Entra a SWBSocialCalendarMgr...");
            int periodTime = 60 * MILISEG_IN_SEGUNDO; //Un minuto
            Timer timer = new Timer();
            timer.schedule(new SWBSocialCalendarMgr.CallCalendarTask(), 0, periodTime);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un
     * stream.
     */
    private static class CallCalendarTask extends TimerTask {

        public CallCalendarTask() {
        }

        /*
         * METOO RUN
         * **TODO**:Tengo que ver si despues a la clase SocialCalendar le asocio los PostOut a los que aplique (hasPostOut),
         * de esta manera, ya no tendría que barrerme todos los CalendarRef del calendario y de estos sacar los related object
         * y comparar que si este de tipo PostOut, entonces ya veo si lo publico o no, esto lo puedo hacer directo si tuviera 
         * esa propiedad hasPostOut en la clase SocialCalendar, que para eso la hice, ahorita realmente no la estoy utilizando, ya
         * que como lo hago en este momento, pudiera utilizar Calendar directamente.
         */
        public void run() {
            //System.out.println("Revisar todos los calendarios de todas las marcas");
            SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
            Iterator<SocialCalendar> itSCalendars = SocialCalendar.ClassMgr.listSocialCalendars();
            while (itSCalendars.hasNext()) {
                SocialCalendar sCalendar = itSCalendars.next();
                //System.out.println("sCalendar Todos:" + sCalendar);
                if (sCalendar.isActive()) {
                    //CalendarRefs
                    Iterator<CalendarRef> itCalRefs = sCalendar.listCalendarRefInvs();
                    while (itCalRefs.hasNext()) {
                        CalendarRef calRef = itCalRefs.next();
                        if(calRef.isActive())
                        {
                            //System.out.println("calRef-Activo:" + calRef);
                            Iterator<GenericObject> itGenObjs = calRef.listRelatedObjects();
                            while (itGenObjs.hasNext()) {
                                GenericObject genObj = itGenObjs.next();
                                //System.out.println("genObjRelated:" + genObj);
                                if (genObj instanceof PostOut) {
                                    PostOut postOut = (PostOut) genObj;
                                    //System.out.println("El postOut es:"+postOut+",esta calendario en Schedule:"+sCalendar.isOnSchedule()+",isPublishByC:"+sCalendar.hasPostOut_published(postOut));
                                    if (sCalendar.isOnSchedule()) {
                                        if(!sCalendar.hasPostOut_published(postOut))
                                        {
                                            //System.out.println("El postOut no esta publicado por Calendario");
                                            if (postOut.isPublished() || (!postOut.isPublished() && !pfmgr.needAnAuthorization(postOut))) {
                                                try {
                                                    //System.out.println("El postOut puede y sera publicado por Calendario");
                                                    SWBSocialUtil.PostOutUtil.publishPost(postOut);
                                                    sCalendar.addPostOut_published(postOut);
                                                    //System.out.println("El postOut ha sido publicado por Calendario");
                                                } catch (SocketException se) {
                                                    //System.out.println("El postOut ha SUFRIDO ERROR AL PUBLICARSE POR CALENDARIO...");
                                                    sCalendar.removePostOut_published(postOut);
                                                    log.error(se);
                                                }
                                            }
                                        }
                                    } else {
                                        //System.out.println("El postOut sALIO DE CALENDARIO Y SE COLOCA SU PROPIEDAD EN FALSE...");
                                        sCalendar.removePostOut_published(postOut);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
