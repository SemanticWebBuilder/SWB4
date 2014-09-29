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

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.LicenseType;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.SocialSite;
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
            int initTime=180*MILISEG_IN_SEGUNDO;
            Timer timer = new Timer();
            log.event("Initializing StreamCleanerMsgbyNumber, starts in:"+initTime+"ms, periodicity:"+periodTime+"ms");
            timer.schedule(new StreamCleanerMsgbyNumber.CheckStreamsMsgbyNumber(), initTime, periodTime);
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
            Iterator<SocialSite> itSocialSites=SocialSite.ClassMgr.listSocialSites();
            while(itSocialSites.hasNext())
            {
                SocialSite socialSite=itSocialSites.next();
                if(socialSite.isValid())
                {
                    long streamMaxMessages=0;
                    LicenseType licenseType=socialSite.getLicenseType();
                    if(licenseType!=null) streamMaxMessages=Long.parseLong(SWBSocialUtil.getLicenseTypeProp(licenseType.getId().toLowerCase()+".messagenum", "0"));
                    Iterator<Stream> itStreams = socialSite.listStreams();
                    while (itStreams.hasNext()) {
                        Stream stream = itStreams.next();
                        if (stream.isValid()) 
                        {

                            //System.out.println("Entra a StreamCleanerMsgbyNumber...EJECUTAR/STREAM:"+stream);
                            long postInNumAccepted=stream.getStream_maxMsg();
                            if(postInNumAccepted<1000 || postInNumAccepted>streamMaxMessages) postInNumAccepted=streamMaxMessages;
                            //1000 es el menor número aceptado en un stream, 100,000 es el mayor número aceptado
                            //El número de mensajes en el stream no debe ser mayor que el que acepta el stream (entre 1000 y 100,000)
                            if(postInNumAccepted<Integer.parseInt(getAllPostInStream(stream)))    
                            {
                                long totalExist=Integer.parseInt(getAllPostInStream(stream));
                                long toErase=totalExist-postInNumAccepted;
                                //System.out.println("Entra a StreamCleanerMsgbyNumber...EJECUTAR/STREAM-1:"+stream+",toErase:"+toErase);
                                String query=getAllPostIn2Remove(stream, toErase);
                                //System.out.println("query pa eliminarJJ:"+query);
                                Iterator<PostIn> itPostIns2Remove=SWBSocial.executeQueryArray(query, socialSite).iterator();
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
        query = SWBSocial.executeQuery(query, wsite);
        return query;
    }
    
     private String getAllPostIn2Remove(Stream stream, long limit) {
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
