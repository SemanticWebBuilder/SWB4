/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.*;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class PostOutResClassifierThread extends java.lang.Thread {
    
    private static Logger log = SWBUtils.getLogger(PostOutResClassifierThread.class);
    PostOut postOut;
    
    public PostOutResClassifierThread(PostOut postOut) throws java.net.SocketException {
        this.postOut=postOut;
    }
    
    @Override
    public void run()
    {
        if(postOut!=null && postOut.getSocialTopic()!=null && !postOut.getSocialTopic().isDeleted() && postOut.getSocialTopic().isActive())
        {
            StringBuilder strbd=new StringBuilder();
            Iterator<SocialNetwork> itSocialNets=postOut.listSocialNetworks();
            while(itSocialNets.hasNext())
            {
                SocialNetwork socialNet=itSocialNets.next();
                if(socialNet instanceof PostOutMonitorable)
                {
                    PostOutMonitorable postOutMonitorAble=(PostOutMonitorable) socialNet;
                    HashMap<String, Integer> hMapnewResponses=postOutMonitorAble.monitorPostOutResponses(postOut, socialNet);  //Me regresa los PostOutNet del PostOut en la socialNetwork enviada
                    if(!hMapnewResponses.isEmpty())  //Enviar email a los que esten en el o los grupos del tema al que pertenece el PostOut
                    {
                        strbd.append("Le informamos que el mensaje enviado con las siguientes caracteristicas: <br><br><br>");
                        strbd.append("<b>Mensaje:</b>"+postOut.getMsg_Text() +"<br>");
                        strbd.append("<b>Tema:</b>"+postOut.getSocialTopic().getDisplayTitle("es") +"<br>");
                        strbd.append("<b>F. publicación:</b>"+postOut.getPo_publishDate() + "<br>");
                        strbd.append("<b>Cuenta de red social:</b>"+socialNet.getTitle() + "<br>");
                        strbd.append("<b>Tiene mensajes nuevos:</b>" + "<br>");
                        Iterator<String> itPostOutNets=hMapnewResponses.keySet().iterator();
                        while(itPostOutNets.hasNext())
                        {
                            String spostOutNet=itPostOutNets.next();
                            int increaseResponses=0;
                            try{
                                increaseResponses=(hMapnewResponses.get(spostOutNet)).intValue();
                            }catch(Exception e){increaseResponses=0;}
                            if(spostOutNet!=null && increaseResponses>0)
                            {
                                SemanticObject semObj=SemanticObject.createSemanticObject(spostOutNet);
                                if(semObj.createGenericInstance() instanceof PostOutNet)
                                {
                                    PostOutNet postOutNet=(PostOutNet)semObj.createGenericInstance();
                                    strbd.append("<b>Referencia:</b>"+postOutNet.getPo_socialNetMsgID() + "<br>");
                                    strbd.append("<b>Mensajes Nuevos:</b>"+increaseResponses + "<br>");
                                }
                            }
                        }
                        //Enviar un email por cuenta de red social
                        try{
                            System.out.println("Se envía email de monitor de respuestas(PostOutResClassifierThread) con la sig info:\n"+SWBUtils.TEXT.encode(strbd.toString(), "iso8859-1"));    
                            //sendGenricEmail2UsersInSocialTopic
                            SWBSocialUtil.Classifier.sendGenricEmail2UsersInSocialTopic(postOut.getSocialTopic(), "Nuevas respuestas en mensajes enviados", strbd.toString());
                        }catch(Exception e){log.error(e);
                        }
                        //Termina envío de email.
                    }
                }
            }
        }
    }
    
}
