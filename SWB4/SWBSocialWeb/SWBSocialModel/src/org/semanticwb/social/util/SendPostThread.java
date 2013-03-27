/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

import java.net.SocketException;
import java.util.LinkedList;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.Message;
import org.semanticwb.social.Messageable;
import org.semanticwb.social.Photo;
import org.semanticwb.social.Photoable;
import org.semanticwb.social.SocialNetPostable;
import org.semanticwb.social.Video;
import org.semanticwb.social.Videoable;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase que se encarga de enviar un mensaje a una determinada red social, esto mediante un thread.
 */
public class SendPostThread extends java.lang.Thread {

    /** The log. */
    private static Logger log=SWBUtils.getLogger(SendPostThread.class);

    /** The emails. */
    LinkedList<PostableObj> postableList = null;


    /**
     * Creates a new instance of WBMessageServer.
     *
     * @throws SocketException the socket exception
     */
    public SendPostThread() throws java.net.SocketException
    {
        postableList = new LinkedList();
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {

        while (true)
        {
            try
            {
                this.currentThread().sleep(20000);
            }catch(Exception e){log.error(e);}
            try
            {
                while (!postableList.isEmpty())
                {
                    Object obj=postableList.removeLast();
                    try
                    {
                     if(obj instanceof PostableObj)
                     {
                       PostableObj postableObj=(PostableObj) obj;
                       SocialNetPostable postable=postableObj.getPostable();
                       String action=postableObj.getAction();
                       if(action!=null)
                       {
                           if(action.equals("msg") && postable instanceof Messageable)
                            {
                                Messageable messageable=(Messageable) postable;
                                messageable.postMsg((Message)postableObj.getPost(), postableObj.getRequest(), postableObj.getResponse());
                            }else if(action.equals("photo") && postable instanceof Photoable)
                            {
                                Photoable photoable=(Photoable) postable;
                                photoable.postPhoto((Photo)postableObj.getPost(), postableObj.getRequest(), postableObj.getResponse());
                            }else if(action.equals("video") && postable instanceof Videoable)
                            {
                                System.out.println("ENTRA A SENTVIDEO...");
                                Videoable videoable=(Videoable) postable;
                                videoable.postVideo((Video)postableObj.getPost(), postableObj.getRequest(), postableObj.getResponse());
                            }
                       }
                     }
                    } catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    /**
     * Adds the e mail.
     *
     * @param email the email
     */
    public void addPostAble(PostableObj postableObj)
    {
        postableList.addFirst(postableObj);
    }

}
