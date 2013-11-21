/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.net.SocketException;
import java.util.Date;
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
import org.semanticwb.social.FastCalendar;

/**
 * Clase controlada de los calendarios sociales en SWBSocial, esta clase ejecuta un proceso cada minuto el cual va a revisar todos
 * los calendarios que esten creados en todas las marcas en SWBSocial y de ellos obtendar sus referencias (PostOut asociados), se 
 * va ha ejecutar el metodo "SWBSocialUtil.PostOutUtil.publishPost(postOut)", solamente una vez durante el periodo de tiempo que el
 * calendario este vigente (isOnSchedule), una vez que se haya ejecuta la primera vez durante su vigencia, se agregara el PostOut a 
 * la lista de PostOut publicados por el calendario en cuestion (addPostOut_published(postOut)), para que mientras dura la vigencia
 * (por ejemplo de las 10:00 am a las 15:00 pm o Todo el Lunes, ya no se vuelva a ejecutar, una vez que su vigencia termine, se 
 * elimina el postOut del calendario en cuestion (sCalendar.removePostOut_published(postOut)) para que sea ejecutado nuevamente la
 * siguiente vez que entre en vigencia. Y así sucesivamente...
 * @author jorge.jimenez
 */

public class SWBSocialCalendarMgr {

    private static Logger log = SWBUtils.getLogger(SWBSocialCalendarMgr.class);
    static final int MILISEG_IN_SEGUNDO = 1000;

    public SWBSocialCalendarMgr() {
        try {
            System.out.println("Entra a SWBSocialCalendarMgr...");
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
            System.out.println("Revisar todos los calendarios de todas las marcas");
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
                                                    if(postOut.getFastCalendar()==null)
                                                    {
                                                        SWBSocialUtil.PostOutUtil.publishPost(postOut);
                                                        sCalendar.addPostOut_published(postOut);
                                                    }
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
            
            //FastCalendars
            Iterator<FastCalendar> itFastCalendars=FastCalendar.ClassMgr.listFastCalendars();
            while(itFastCalendars.hasNext())
            {
                FastCalendar fastCalendar=itFastCalendars.next();
                PostOut postOut=fastCalendar.getPostOutFastCalendarInv();
                if(postOut==null) fastCalendar.remove();
                Date inidate = fastCalendar.getFc_date();
                Date today=new Date();
                if(eval(fastCalendar))
                {
                    System.out.println("postout:"+postOut+", esta publicado?"+postOut.isPublished()+", necesita Autorizacion?:"+ pfmgr.needAnAuthorization(postOut));
                    boolean needAuthorization = pfmgr.needAnAuthorization(postOut);
                    
                    //if(!postOut.isPublished() && !pfmgr.needAnAuthorization(postOut))   //Si no ha sido publicado y no necesita autorizacón en flujos
                    if(!postOut.isPublished() && ((!pfmgr.needAnAuthorization(postOut)) || (needAuthorization && postOut.getPflowInstance() != null && postOut.getPflowInstance().getStatus() == 3)))
                    {
                        System.out.println("VA A PUBLICAR POR FASTCALENDAR, HEEEE!!");
                        try
                        {
                            SWBSocialUtil.PostOutUtil.publishPost(postOut);
                            postOut.removeFastCalendar();
                            fastCalendar.remove();
                        }catch(Exception e)
                        {
                            postOut.removeFastCalendar();
                            fastCalendar.remove();
                            log.error(e);
                        }
                    }else{  //Si ya fué evaluado correctamente (llego la fecha y hora para publicarlo), pero no cumple con algún flujo, se elimina el FastCalendar, cuando termine en el flujo se podra publicar el PostOut normalmente.
                        System.out.println("ELIMINO EL FASTCALENDAR...-1");
                        postOut.removeFastCalendar();
                        fastCalendar.remove();
                    }
                }else if(inidate.compareTo(today)<0)    //La fecha ya es pasada, eliminar el FastCalendar.
                {
                     //System.out.println("Se elimina FastCalendar de PostOut:"+postOut+", porque ya paso la fecha..que mancheados...:"+postOut);
                     if(postOut!=null)
                     {
                        postOut.removeFastCalendar();
                        fastCalendar.remove();
                        System.out.println("ELIMINO EL FASTCALENDAR...-2");
                     }
                }        
            }
            //Termina FastCalendars 
        }
    }
    
    
    /**
     * Eval.
     * 
     * @param today the today
     * @param interval the interval
     * @return true, if successful
     * @throws Exception the exception
     */
    private static boolean eval(FastCalendar fastCalendar){
        Date inidate = fastCalendar.getFc_date();
        Date today=new Date();
        System.out.println("inidate:"+inidate+",today:"+today);
        System.out.println("inidate Year:"+inidate.getYear()+",inidate Month:"+inidate.getMonth()+",inidate Day:"+inidate.getDay()+",inidate Min:"+inidate.getMinutes());
        System.out.println("today Year:"+today.getYear()+",today Month:"+today.getMonth()+",today Day:"+today.getDay()+",today Min:"+today.getMinutes());
        if(inidate.getYear()==today.getYear() && inidate.getMonth()==today.getMonth() && inidate.getDay()==today.getDay() && inidate.getHours()==today.getHours() && inidate.getMinutes()<=today.getMinutes())
        {
            System.out.println("Si son Iguales...");
            return true;
        }
        return false;
    }
    
    
}
