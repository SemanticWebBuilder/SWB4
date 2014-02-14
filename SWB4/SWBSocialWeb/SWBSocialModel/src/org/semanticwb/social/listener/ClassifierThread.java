/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SendPostThread;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase cuya funcionalidad es la de realizar ciertas tareas de clasificación por cada mensaje que llega por medio del listener.
 * Estas tareas se realizan en un thread, esta clase puede enviar hacia otras clases a realizar otras tareas mas específicas
 */
public class ClassifierThread extends java.lang.Thread {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SendPostThread.class);
    /** The emails. */
    //PostIn post = null;
    ArrayList <ExternalPost> aListExternalPost=null;
    Stream stream=null;
    SocialNetwork socialNetwork=null;
    boolean classifyGeoLocation=false;

    /**
     * Creates a new instance of WBMessageServer.
     *
     * @throws SocketException the socket exception
     */
    /*
    public ClassifierThread(PostIn post) throws java.net.SocketException {
        this.post = post;
    }**/
    
    public ClassifierThread(ArrayList <ExternalPost> aListExternalPost, Stream stream, SocialNetwork socialNetwork, boolean classifyGeoLocation) throws java.net.SocketException {
        this.aListExternalPost = aListExternalPost;
        this.stream = stream;
        this.socialNetwork = socialNetwork;
        this.classifyGeoLocation=classifyGeoLocation;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        /*
        boolean checkKlout=false; 
        try{
           WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName()); 
           checkKlout=Boolean.parseBoolean(SWBSocialUtil.Util.getModelPropertyValue(wsite, "checkKlout"));
        }catch(Exception ignored)
        {
           checkKlout=false;
        }*/
        try
        {
          if(stream.isValid())
          {
            Iterator<ExternalPost> itExternalThreads=aListExternalPost.iterator();  
            while(itExternalThreads.hasNext())
            {
                ExternalPost externalPost=itExternalThreads.next();
                if(socialNetwork!=null && stream!=null && externalPost.getCreatorId()!=null && externalPost.getPostId()!=null && externalPost.getSocialNetwork()!=null)
                {
                    new SentimentalDataClassifier(externalPost, stream, socialNetwork, classifyGeoLocation);
                }else{
                    System.out.println("NO MANCHESSSS/socialNetwork:"+socialNetwork+",NET2:"+externalPost.getSocialNetwork());
                }
            }
          }
           /*
            String words2classify = null;
            if (post instanceof MessageIn) {
                MessageIn messageIn = (MessageIn) post;
                words2classify = messageIn.getMsg_Text();
                if(words2classify!=null && words2classify.trim().length()>0)
                {
                    new SentimentalDataClassifier(messageIn, words2classify);
                }
            } else if (post instanceof PhotoIn) {
                PhotoIn photoIn = (PhotoIn) post;
                words2classify = photoIn.getTitle() + photoIn.getDescription();
                if(words2classify!=null && words2classify.trim().length()>0)
                {
                    new SentimentalDataClassifier(photoIn, words2classify);
                }
            } else if (post instanceof VideoIn) {
                VideoIn videoIn = (VideoIn) post;
                words2classify = videoIn.getTitle() + videoIn.getDescription();
                if(words2classify!=null && words2classify.trim().length()>0)
                {
                    new SentimentalDataClassifier(videoIn, words2classify);
                }
            }
            * */
        } catch (Exception e) {
            log.error(e);
        }
    }
}
