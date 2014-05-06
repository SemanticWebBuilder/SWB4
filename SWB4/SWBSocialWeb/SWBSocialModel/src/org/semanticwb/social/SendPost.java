package org.semanticwb.social;

import java.io.File;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.util.SWBSocialUtil;


   /**
   * Acción específica mediante la cual se envía un mensaje por defecto a una o varias redes sociales seleccionadas 
   */
public class SendPost extends org.semanticwb.social.base.SendPostBase 
{
    private static Logger log = SWBUtils.getLogger(SendPost.class);
    
    public SendPost(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /*
     * Acción que envía mensajes sin pasar por ningún flujo
     * se definió que solo llegaran a esta función aquellos postIn
     * que hayan sido clasificados por tema.
     */
    public static void sendPost(Action action, PostIn postIn)
    {
        if(!(action instanceof SendPost) || postIn.getSocialTopic()==null) return;
        try
        {
            WebSite wsite=WebSite.ClassMgr.getWebSite(action.getSemanticObject().getModel().getName());
            SendPost sendPost=(SendPost)action;
            //Se revisa si almenos una red social de las asociadas a la la acción es valida (No borrada y Activa)
            boolean atLeastOneIsValid=false;
            Iterator<SocialNetwork> itSendPostNets=sendPost.listSocialNetworkses();
            while(itSendPostNets.hasNext())
            {
                SocialNetwork socialNet=itSendPostNets.next();
                if(!socialNet.isDeleted() && socialNet.isActive())
                {
                    atLeastOneIsValid=true;
                    break;
                }
            }
            //Termina revisión de si al menos una red social de las asociadas a la la acción es valida (No borrada y Activa)
            if(atLeastOneIsValid && wsite!=null && (sendPost.getMsg_Text()!=null || sendPost.listPhotos().hasNext() || sendPost.getVideo()!=null))
            {
                
                if(sendPost.getVideo()!=null)   //Envía Video
                {
                    try
                    {
                        Video video=Video.ClassMgr.createVideo(wsite);
                    
                        String videoSource = SWBPortal.getWorkPath() + sendPost.getWorkPath() + "/" + sendPost.getVideo();
                        String videoTarget = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + sendPost.getVideo();
                        
                        SWBUtils.IO.copy(videoSource,videoTarget, false, null, null);
                        
                        video.setVideo(sendPost.getVideo());
                        if(sendPost.getMsg_Text()!=null)
                        {
                            video.setMsg_Text(sendPost.getMsg_Text());
                        }
                        
                        //Se agrega postIn fuente del nuevo postOut
                        video.setPostInSource(postIn);
                        
                        //Agrego datos del postIn al postOut, para su correcta creación y clasificación
                        video.setSocialTopic(postIn.getSocialTopic());
                        video.addSocialNetwork(postIn.getPostInSocialNetwork());
                        Iterator<SocialNetwork> itSendPostActionSocialNets=sendPost.listSocialNetworkses();
                        while(itSendPostActionSocialNets.hasNext())
                        {
                            SocialNetwork socialNet=itSendPostActionSocialNets.next();
                            if(!socialNet.isDeleted() && socialNet.isActive())
                            {
                                //Si es diferente de la del postIn, ya que esa ya se había agregado
                                if(!socialNet.getURI().equals(postIn.getPostInSocialNetwork().getURI()))
                                {
                                    video.addSocialNetwork(socialNet);
                                }
                            }
                        }
                        
                        //Envío post de respuesta, que no pasa por flujo
                        SWBSocialUtil.PostOutUtil.publishPost(video);
                    }catch(Exception e)
                    {
                        log.error(e);
                    }
                }else if(sendPost.listPhotos().hasNext()) //Envía Photo
                {
                    try
                    {
                        Photo photo=Photo.ClassMgr.createPhoto(wsite);
                        
                        Iterator itPhotos=sendPost.listPhotos();
                        
                        while(itPhotos.hasNext())
                        {
                            String sphoto=(String)itPhotos.next(); 
                            
                            String photoSource = SWBPortal.getWorkPath() + sendPost.getWorkPath() + "/" + sphoto;
                            //System.out.println("photoSource-George:"+photoSource);
                            String photoTarget = SWBPortal.getWorkPath() + photo.getWorkPath();
                            System.out.println("photoTarget-1:"+photoTarget);
                            File targetFile=new File(photoTarget);
                            if(!targetFile.exists())
                            {
                                targetFile.mkdirs();
                            }
                            photoTarget+="/" + sphoto;
                            System.out.println("photoTarget-2:"+photoTarget);
                            System.out.println("Va a copiar...");
                            SWBUtils.IO.copy(photoSource,photoTarget, false, null, null);
                            System.out.println("Copio....");


                            photo.addPhoto(sphoto);
                       
                        }
                        
                        if(sendPost.getMsg_Text()!=null)
                        {
                            photo.setMsg_Text(sendPost.getMsg_Text());
                        }
                        
                        //Se agrega postIn fuente del nuevo postOut
                        photo.setPostInSource(postIn);
                        
                        //Agrego datos del postIn al postOut, para su correcta creación y clasificación
                        photo.setSocialTopic(postIn.getSocialTopic());
                        photo.addSocialNetwork(postIn.getPostInSocialNetwork());
                        Iterator<SocialNetwork> itSendPostActionSocialNets=sendPost.listSocialNetworkses();
                        while(itSendPostActionSocialNets.hasNext())
                        {
                            SocialNetwork socialNet=itSendPostActionSocialNets.next();
                            if(!socialNet.isDeleted() && socialNet.isActive())
                            {
                                //Si es diferente de la del postIn, ya que esa ya se había agregado
                                if(!socialNet.getURI().equals(postIn.getPostInSocialNetwork().getURI()))
                                {
                                    photo.addSocialNetwork(socialNet);
                                    System.out.println("Copio....:2:"+socialNet);
                                }
                            }
                        }
                        
                        //System.out.println("Va a envíar...");
                        //Envío post de respuesta, que no pasa por flujo
                        SWBSocialUtil.PostOutUtil.publishPost(photo);
                        //System.out.println("Va a envíar...-1");
                    }catch(Exception e)
                    {
                        log.error(e);
                    }
                }else if(sendPost.getMsg_Text()!=null) //Envía Mensaje
                {
                    try
                    {
                        Message message=Message.ClassMgr.createMessage(wsite);
                        message.setMsg_Text(sendPost.getMsg_Text());
                        
                        
                        //Agrego datos del postIn al postOut, para su correcta creación y clasificación
                        message.setSocialTopic(postIn.getSocialTopic());
                        message.addSocialNetwork(postIn.getPostInSocialNetwork());
                        Iterator<SocialNetwork> itSendPostActionSocialNets=sendPost.listSocialNetworkses();
                        while(itSendPostActionSocialNets.hasNext())
                        {
                            SocialNetwork socialNet=itSendPostActionSocialNets.next();
                            if(!socialNet.isDeleted() && socialNet.isActive())
                            {
                                //Si es diferente de la del postIn, ya que esa ya se había agregado
                                if(!socialNet.getURI().equals(postIn.getPostInSocialNetwork().getURI()))
                                {
                                    message.addSocialNetwork(socialNet);
                                }
                            }
                        }
                        
                        //Se agrega postIn fuente del nuevo postOut
                        System.out.println("postIn NetMsgID:"+postIn.getSocialNetMsgId());
                        message.setPostInSource(postIn);
                        
                        
                        //Envío post de respuesta, que no pasa por flujo
                        SWBSocialUtil.PostOutUtil.publishPost(message);
                    }catch(Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
}
