/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.util.Iterator;
import org.semanticwb.social.*;

/**
 *
 * @author jorge.jimenez
 */
public class PostOutResClassifierThread extends java.lang.Thread {
    
    PostOut postOut;
    
    public PostOutResClassifierThread(PostOut postOut) throws java.net.SocketException {
        this.postOut=postOut;
    }
    
    @Override
    public void run()
    {
        if(postOut!=null && postOut.getSocialTopic()!=null && !postOut.getSocialTopic().isDeleted() && postOut.getSocialTopic().isActive())
        {
            Iterator<SocialNetwork> itSocialNets=postOut.listSocialNetworks();
            while(itSocialNets.hasNext())
            {
                SocialNetwork socialNet=itSocialNets.next();
                if(socialNet instanceof PostOutMonitorable)
                {
                    PostOutMonitorable postOutMonitorAble=(PostOutMonitorable) socialNet;
                    int newResponses=postOutMonitorAble.monitorPostOutResponses(postOut);
                    if(newResponses>0)  //Enviar email a los que esten en el o los grupos del tema al que pertenece el PostOut
                    {
                        
                    }
                }
            }
        }
    }
    
}
