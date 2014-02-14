/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 */
public class StreamCleanerMsgbyDays {
    
    private static Logger log = SWBUtils.getLogger(SWBSocialCalendarMgr.class);
    static final int MILISEG_IN_SEGUNDO = 1000;

    public StreamCleanerMsgbyDays() {
        try {
            //Inicio del timer
            Calendar hour2Start = Calendar.getInstance();
            hour2Start.set(hour2Start.get(Calendar.YEAR),
                    hour2Start.get(Calendar.MONTH),
                    hour2Start.get(Calendar.DATE), 23, 59, 00); //Hora a las 11:59 pm

            Calendar timeNow = Calendar.getInstance(); //Hora actual

            //Milisegundos para empezar por primera vez el timer
            long time2Start = hour2Start.getTimeInMillis() - timeNow.getTimeInMillis();

            int oneDay = 60 * MILISEG_IN_SEGUNDO * 60 * 24;
            
            //System.out.println("StreamCleanerMsgbyDays/time2Start Para empezar por primera vez:"+time2Start);

            Timer timer = new Timer();
            
            //timer.schedule(new CheckStreamsMsgbyDays(), 0, 60 * 1000); //Cada minuto
            
            //Que empiece hoy a las 11:59 pm y vuelve a iterar un dia despues y así se siga
            timer.schedule(new CheckStreamsMsgbyDays(), time2Start, oneDay);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un
     * stream.
     */
    private class CheckStreamsMsgbyDays extends TimerTask {

        public CheckStreamsMsgbyDays() {
        }

        /*
         * METOO RUN
         * Metodo que revisa todos los streams activos en todas las marcas a
         */
        public void run() {
            System.out.println("Entra a CheckStreamsMsgbyDays...EJECUTAR....");
            Iterator<Stream> itStreams = Stream.ClassMgr.listStreams();
            while (itStreams.hasNext()) {
                Stream stream = itStreams.next();
                if (stream.getSocialSite().isValid() && stream.isValid()) {
                    //System.out.println("Entra a CheckStreamsMsgbyDays...EJECUTAR/STREAM:"+stream+",MaxDays:"+stream.getStream_maxDays());
                    int postInDays2Stay=stream.getStream_maxDays();
                    if(postInDays2Stay>0)
                    {
                        Date d = Calendar.getInstance().getTime();//intialize your date to any date 
                        Date dateBefore = new Date(d.getTime() - postInDays2Stay * 24 * 3600 * 1000 ); //Subtract n days  
                       
                        String date2Compare=dateBefore.getYear()+1900+"-"+(dateBefore.getMonth()+01)+"-"+dateBefore.getDate()+"T00:00:00Z";
                        
                        //System.out.println("date2Compare:"+date2Compare);
                        
                        String query=getAllPostIn2Remove(stream, date2Compare);
                        //System.out.println("query pa eliminarJJ:"+query);
                        Iterator<PostIn> itPostIns2Remove=SWBSocial.executeQueryArray(query, stream.getSocialSite()).iterator();
                        while(itPostIns2Remove.hasNext())
                        {
                            PostIn postIn=itPostIns2Remove.next();
                            //System.out.println("Va a eliminar PostIn:"+postIn);
                            postIn.remove();
                        }
                    }
                }
            }

        }
    }
/*
    private String getAllPostInStream(Stream stream, Date dateBefore) {
        String query ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "\n";
        query += "select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
        query +="where {\n"
                + "  ?postUri social:postInStream <" + stream.getURI() + ">. \n"
                + "  }\n";
        WebSite wsite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        query = SWBSocial.executeQuery(query, wsite);
        return query;
    }
    * */
    
     private String getAllPostIn2Remove(Stream stream, String date) {
        String query ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "\n";
        query += "select * \n";    //Para Gena
        query +="where {\n"
                + "  ?postUri social:postInStream <" + stream.getURI() + ">. \n"
                + "  ?postUri social:pi_created ?postInCreated. \n"
                + "  FILTER(xsd:dateTime(?postInCreated) < xsd:dateTime(\""+date+"\")) \n" 
                + "}\n";                
               
        return query;
    }
    
}
