/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.platform.SemanticObject;
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
                        strbd.append("Le informamos que el mensaje enviado con las siguientes caracteristicas: \n");
                        strbd.append("Mensaje:"+postOut.getMsg_Text());
                        strbd.append("Tema:"+postOut.getSocialTopic().getTitle());
                        strbd.append("F. creación:"+postOut.getPout_created());
                        strbd.append("Cuenta de red social:"+socialNet.getTitle());
                        strbd.append("Tiene el siguiente número de mensajes nuevos:");
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
                                    strbd.append("Referencia:"+postOutNet.getPo_socialNetMsgID());
                                    strbd.append("Mensajes Nuevos:"+increaseResponses);
                                }
                            }
                        }
                        //Enviar un email por cuenta de red social
                        System.out.println("Se envía email de monitor de respuestas(PostOutResClassifierThread) con la sig info:"+strbd.toString());    
                        //Termina envío de email.
                    }
                }
            }
        }
    }
    
}
