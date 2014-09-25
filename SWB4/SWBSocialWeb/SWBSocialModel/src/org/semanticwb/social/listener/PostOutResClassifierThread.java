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
        if(postOut!=null && postOut.getSocialTopic()!=null && !postOut.getSocialTopic().isDeleted() && postOut.getSocialTopic().isActive() && postOut.getSocialTopic().getSt_numDays2ClosePostOuts()>0 && postOut.getSocialTopic().getSt_numDays2ClosePostOuts()<100)
        {
            long numTotNewResponses=0;
            StringBuilder strbd=new StringBuilder();
            Iterator<SocialNetwork> itSocialNets=postOut.listSocialNetworks();
            while(itSocialNets.hasNext())
            {
                SocialNetwork socialNet=itSocialNets.next();
                if(socialNet instanceof PostOutMonitorable)
                {
                    PostOutMonitorable postOutMonitorAble=(PostOutMonitorable) socialNet;
                    HashMap<String, Long> hMapnewResponses=postOutMonitorAble.monitorPostOutResponses(postOut);  //Me regresa los PostOutNet del PostOut en la socialNetwork enviada
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
                            long increaseResponses=0;
                            try{
                                increaseResponses=hMapnewResponses.get(spostOutNet).longValue();
                            }catch(Exception e){increaseResponses=0;}
                            if(spostOutNet!=null && increaseResponses>0)
                            {
                                SemanticObject semObj=SemanticObject.createSemanticObject(spostOutNet);
                                if(semObj.createGenericInstance() instanceof PostOutNet)
                                {
                                    PostOutNet postOutNet=(PostOutNet)semObj.createGenericInstance();
                                    strbd.append("<b>Referencia:</b>"+postOutNet.getPo_socialNetMsgID() + "<br>");
                                    strbd.append("<b>Mensajes Nuevos:</b>"+increaseResponses + "<br>");
                                    numTotNewResponses+=increaseResponses;
                                }
                            }
                        }
                        //Enviar un email por cuenta de red social
                        if(postOut.getSocialTopic().isSentEmailInComments())
                        {
                            try{
                                System.out.println("Se envía email de monitor de respuestas(PostOutResClassifierThread) con la sig info:\n"+SWBUtils.TEXT.encode(strbd.toString(), "iso8859-1"));    
                                //sendGenricEmail2UsersInSocialTopic
                                SWBSocialUtil.Classifier.sendGenricEmail2UsersInSocialTopic(postOut.getSocialTopic(), "Nuevas respuestas en mensajes enviados", strbd.toString());
                            }catch(Exception e){log.error(e);}
                        }
                        //Termina envío de email.
                    }
                }
            }
            postOut.setNumTotNewResponses(postOut.getNumTotNewResponses()+numTotNewResponses);
        }
    }
    
}
