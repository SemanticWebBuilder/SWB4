/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 */
public class StreamThreadRemoverbyNumber extends java.lang.Thread {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(StreamThreadRemoverbyNumber.class);
    
    SocialSite socialSite=null;
    Stream stream=null;
    Boolean usingLicenseMgr=false;
    long streamMaxMessages=0;
    
    public StreamThreadRemoverbyNumber(SocialSite socialSite, Stream stream, boolean usingLicenseMgr, long streamMaxMessages) throws java.net.SocketException {
        this.socialSite=socialSite;
        this.stream=stream;
        this.usingLicenseMgr=usingLicenseMgr;
        this.streamMaxMessages=streamMaxMessages;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        try
        {
          if(socialSite!=null && stream!=null){
            long postInNumAccepted=stream.getStream_maxMsg();
            if(usingLicenseMgr && (postInNumAccepted<1000 || postInNumAccepted>streamMaxMessages)) postInNumAccepted=streamMaxMessages;
            else if(!usingLicenseMgr && (postInNumAccepted<1000 || postInNumAccepted>100000)) postInNumAccepted=100000;
            //1000 es el menor número aceptado en un stream, 100,000 es el mayor número aceptado
            //El número de mensajes en el stream no debe ser mayor que el que acepta el stream (entre 1000 y 100,000)
            int postInStream=Integer.parseInt(getAllPostInStream(stream));
            if(postInNumAccepted<postInStream)    
            {
                long toErase=postInStream-postInNumAccepted;
                //System.out.println("Entra a StreamCleanerMsgbyNumber...EJECUTAR/STREAM-1:"+stream+",toErase:"+toErase);
                String query=getAllPostIn2Remove(stream, toErase);
                Iterator<PostIn> itPostIns2Remove=SWBSocial.executeQueryArray(query, socialSite).iterator();
                while(itPostIns2Remove.hasNext())
                {
                    PostIn postIn=itPostIns2Remove.next();
                    //System.out.println("Va a eliminar PostIn:"+postIn);
                    postIn.remove();
                }
            }
          }
        } catch (Exception e) {
            log.error(e);
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