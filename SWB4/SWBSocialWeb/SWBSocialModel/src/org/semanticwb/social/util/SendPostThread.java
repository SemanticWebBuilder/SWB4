/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

import java.net.SocketException;
import java.util.Calendar;
import java.util.LinkedList;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.Message;
import org.semanticwb.social.Messageable;
import org.semanticwb.social.Photo;
import org.semanticwb.social.Photoable;
import org.semanticwb.social.PostOut;
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

        //while (true)
        {
            /*
            try
            {
                this.currentThread().sleep(20000);
            }catch(Exception e){log.error(e);}
            * */
            try
            {
                while (!postableList.isEmpty())
                {
                    Object obj=postableList.removeLast();
                    System.out.println("SendPostThread/obj:"+obj);
                    try
                    {
                     if(obj instanceof PostableObj)
                     {
                       PostableObj postableObj=(PostableObj) obj;
                       if(postableObj.getPost()!=null && postableObj.getPostable()!=null)
                       {
                         PostOut postOut=(PostOut)postableObj.getPost();   
                         SocialNetPostable postable=postableObj.getPostable();
                        
                         if(postOut instanceof Message && postable instanceof Messageable)
                          {
                              Messageable messageable=(Messageable) postable;
                              messageable.postMsg((Message)postableObj.getPost());
                              System.out.println("Se publica-4");
                              //postOut.setPublished(true);
                          }else if(postOut instanceof Photo && postable instanceof Photoable)
                          {
                              Photoable photoable=(Photoable) postable;
                              photoable.postPhoto((Photo)postableObj.getPost());
                              //postOut.setPublished(true);
                          }else if(postOut instanceof Video && postable instanceof Videoable)
                          {
                              Videoable videoable=(Videoable) postable;
                              videoable.postVideo((Video)postableObj.getPost());
                              //postOut.setPublished(true);
                          }
                         //Guardamos la fecha de publicación del PostOut. Esto para fines de buscar en el monitoreo de PostOuts (PostOutResClassifierThread)
                         //solo los postOut que tengan menos de 30 días de haber sido publicados y con ello cerrar el monitoreo de dicho PostOut(isClosedforResponses)
                         //Los PostOut que tengan asignado un calendario Avanzado, se les estara actualizando esta fecha, eso esta bien, ya que nos interesaría seguir
                         //monitoreando esas instancias de PostOutNets que se vayan generando de dicho calendario avanzado asignado al PostOut.
                         postOut.setPo_publishDate(Calendar.getInstance().getTime());
                         postOut.setIsClosedforResponses(false);  //Se crea el triple, esto para revisarlos postOut en la clase MonitorPostOutResponsesMgr
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
