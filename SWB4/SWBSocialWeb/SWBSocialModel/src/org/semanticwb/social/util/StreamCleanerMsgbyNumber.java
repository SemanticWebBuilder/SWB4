/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 */
public class StreamCleanerMsgbyNumber {

    private static Logger log = SWBUtils.getLogger(SWBSocialCalendarMgr.class);
    static final int MILISEG_IN_SEGUNDO = 1000;

    public StreamCleanerMsgbyNumber() {
        try {
            //System.out.println("Entra a StreamCleanerMsgbyNumber...");
            int periodTime = 60 * MILISEG_IN_SEGUNDO; //Un minuto
            Timer timer = new Timer();
            timer.schedule(new StreamCleanerMsgbyNumber.CheckStreamsMsgbyNumber(), 0, periodTime);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un
     * stream.
     */
    private class CheckStreamsMsgbyNumber extends TimerTask {

        public CheckStreamsMsgbyNumber() {
        }

        /*
         * METOO RUN
         * Metodo que revisa todos los streams activos en todas las marcas a
         */
        public void run() {
            System.out.println("Entra a StreamCleanerMsgbyNumber...EJECUTAR....");
            Iterator<Stream> itStreams = Stream.ClassMgr.listStreams();
            while (itStreams.hasNext()) {
                Stream stream = itStreams.next();
                if (stream.getSocialSite().isValid() && stream.isValid()) {
                    System.out.println("Entra a StreamCleanerMsgbyNumber...EJECUTAR/STREAM:"+stream);
                    int postInNumAccepted=stream.getStream_maxMsg();
                    //1000 es el menor número aceptado en un stream, 99999 es el mayor número aceptado
                    //El número de mensajes en el stream no debe ser mayor que el que acepta el stream (entre 1000 y 99999)
                    if(postInNumAccepted>=1000 && postInNumAccepted<Integer.parseInt(getAllPostInStream(stream)))    
                    {
                        int totalExist=Integer.parseInt(getAllPostInStream(stream));
                        int toErase=totalExist-postInNumAccepted;
                        //System.out.println("Entra a StreamCleanerMsgbyNumber...EJECUTAR/STREAM-1:"+stream+",toErase:"+toErase);
                        String query=getAllPostIn2Remove(stream, toErase);
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

    private String getAllPostInStream(Stream stream) {
        String query ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>"
                + "\n";
        query += "select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
        query +="where {\n"
                + "  ?postUri social:postInStream <" + stream.getURI() + ">. \n"
                + "  }\n";
        WebSite wsite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        //System.out.println("query:"+query+",wsite:"+wsite);
        query = SWBSocial.executeQuery(query, wsite);
        return query;
    }
    
     private String getAllPostIn2Remove(Stream stream, int limit) {
        String query ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>"
                + "\n";
        query += "select * \n";    //Para Gena
        query +="where {\n"
                + "  ?postUri social:postInStream <" + stream.getURI() + ">. \n"
                + "  ?postUri social:pi_created ?postInCreated. \n"
                + "}\n"
                + "ORDER BY asc(?postInCreated) \n"
                + "OFFSET 0"
                + "LIMIT " + limit;
        return query;
    }
    
    
    
}
