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
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 */
public class ListenerClassifierThread extends java.lang.Thread {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(ListenerClassifierThread.class);
    /** The emails. */
    SocialNetwork socialNetwork=null;
    Stream stream=null;
    
    /**
     * Creates a new instance of WBMessageServer.
     *
     * @throws SocketException the socket exception
     */
    /*
    public ClassifierThread(PostIn post) throws java.net.SocketException {
        this.post = post;
    }**/
    
    public ListenerClassifierThread(SocialNetwork socialNetwork, Stream stream) throws java.net.SocketException {
        this.socialNetwork=socialNetwork;     
        this.stream=stream;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        try
        {
          if(socialNetwork!=null && stream!=null){
              socialNetwork.listen(stream);
          }
        } catch (Exception e) {
            log.error(e);
        }
    }
}