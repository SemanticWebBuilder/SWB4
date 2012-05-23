/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.net.SocketException;
import java.util.LinkedList;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SendPostThread;

/**
 *
 * @author jorge.jimenez
 */
public class ClassifierThread  extends java.lang.Thread {

    /** The log. */
    private static Logger log=SWBUtils.getLogger(SendPostThread.class);

    /** The emails. */
    LinkedList<Post> postableList = null;


    /**
     * Creates a new instance of WBMessageServer.
     *
     * @throws SocketException the socket exception
     */
    public ClassifierThread() throws java.net.SocketException
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
                while (!postableList.isEmpty())
                {
                    Object obj=postableList.removeLast();
                    try
                    {
                         if(obj instanceof Post)
                         {
                           String words2classify=null;
                           Post postableObj=(Post) obj;
                           if(postableObj instanceof MessageIn)
                            {
                                 MessageIn messageIn=(MessageIn) postableObj;
                                 words2classify=messageIn.getMsg_Text();

                            }else if(postableObj instanceof PhotoIn)
                            {
                                PhotoIn photoIn=(PhotoIn) postableObj;
                                words2classify=photoIn.getTitle() + photoIn.getDescription();
                            }else if(postableObj instanceof VideoIn)
                            {
                                VideoIn videoIn=(VideoIn) postableObj;
                                words2classify=videoIn.getTitle() + videoIn.getDescription();
                            }
                            new SentimentalDataClassifier(postableObj, words2classify);
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
    public void addPostAble(Post postableObj)
    {
        postableList.addFirst(postableObj);
    }

}
