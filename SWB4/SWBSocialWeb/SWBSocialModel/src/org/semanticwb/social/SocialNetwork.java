package org.semanticwb.social;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


public class SocialNetwork extends org.semanticwb.social.base.SocialNetworkBase 
{
    public SocialNetwork(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    //ESTE SIGUIENTE CÓDIGO NO FUNCIONA AQUÍ, VER CON JEI DONDE LO PODRÍA PONER QUE NO SEA LA CLASE TWITTER, NI PORSUPUESTO 
    //LA CLASE TWITTER BASE YA QUE DE AHI SE BORRARÍA CON CUALQUIER CAMBIO A LA ONTOLOGÍA.
    //ESTO ES UN CÓDIGO QUE NO QUE NO DEBE ESTAR EN LA CLASE TWITTER, PORQUE ES PROPIO DEL FUNCIONAMIENTO DE LA HERRAMIENTA, NO TANTO
    //DE LA IMPLEMENTACIÓN DE ESA RED SOCIAL.
    /*
    static {
        Twitter.social_Twitter.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null)
                {
                    SocialNetwork socialNetwork = (SocialNetwork) obj.createGenericInstance();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                   
                    System.out.println("*********************Twitter/Observer/action:"+action+", Twitter-Aberr:"+socialNetwork);
                    
                    //TODO:Probar este código.
                    if(action.equals("REMOVE")) //Si la acción es eliminar el SocialTopic
                    {
                            Iterator<PostOut> itPostOuts=PostOut.ClassMgr.listPostOutBySocialNetwork(socialNetwork, wsite);
                            while(itPostOuts.hasNext())
                            {
                                PostOut postOut=itPostOuts.next();
                                boolean isAlone=true;
                                Iterator<SocialNetwork> itPostOutSocialNets=postOut.listSocialNetworks();
                                while(itPostOutSocialNets.hasNext())
                                {
                                    SocialNetwork socialNet=itPostOutSocialNets.next();
                                    if(!socialNet.getURI().equals(socialNetwork.getURI())) {
                                        System.out.println("NO SON IGUALES LAS SOCIALNETS DEL POSTOUT:"+socialNet.getURI()+",---:"+socialNetwork.getURI());
                                        isAlone=false;
                                        break;
                                    }
                                }
                                //La red social que estoy intentando borrar es la única en este PostOut, por lo tanto, si puedo borrar el PostOut (si no quedaría como basura)
                                System.out.println("isAlone:"+isAlone);
                                if(isAlone) 
                                {
                                    System.out.println("postOut BORRADO:"+postOut);
                                    postOut.remove();
                                }
                            }
                    }
                }
                }
            });
    }
    * */
    //TERMINA
    
    
    /*
     * Metodo que funciona para realizar ciertas acciones despues de enviado un post a una red social
     *
     */
    /*
    public void addSentPost(PostOut post, String socialPostId, SocialNetwork socialNetwork)
    {
        SWBModel swbModel=SWBContext.getSWBModel(post.getSemanticObject().getModel().getName());
        
         // Se crea un objeto de persistencia, el cual contiene el post, la red social y el identificador creado
        // en dicha red social para dicho post, esto es necesario ya que cuando se envía un post a varias redes sociales
        // c/una de ellas genera su propio id del mensaje que le llega (por parte de SWBSocial) y este identificador nosotros
        //lo tenemos que guardar de este lado para darle trazabilidad, por ejemplo: si alguien contesta nuestro mensaje, etc.
        SocialPost newSocialPost=SocialPost.ClassMgr.createSocialPost(socialPostId, swbModel);
        newSocialPost.setSocialPost(post);
        newSocialPost.setSocialNetwork(socialNetwork);

       //Lo siguiente que se realiza, es para poder buscar rapidamente los post enviados
       //Se crea un objeto de persistencia siempre y cuando no este creado para un cierto año y mes, de lo
       //contrario, se reutiliza uno para ese año y mes.
       //Una vez esto, se agrega el post al objeto creado o reutilizado.
       Date date=post.getCreated();
       PostOutContainer postContainer=PostOutContainer.getPostContainerByDate(date, swbModel);
       postContainer.addPost(post);
       postContainer.setPc_SocialNetworkInv(socialNetwork);
       //System.out.println("Datos completamente guardados..");

       //................................CÓDIGOS DE PRUEBAS......
       //Código siguiente es solo para verificar de guardado, quitar despues...
       Iterator <SocialPost> itSocialPost=SocialPost.ClassMgr.listSocialPosts();
       while(itSocialPost.hasNext())
       {
           SocialPost socialPost=itSocialPost.next();
           System.out.println("socialPost en BD:"+socialPost.getId()+",SocialNetWork:"+socialPost.getSocialNetwork()+", Post:"+socialPost.getSocialPost());
       }


        Iterator <PostOutContainer> itPostContainers=PostOutContainer.ClassMgr.listPostOutContainers();
        while(itPostContainers.hasNext())
        {
            PostOutContainer postCont=itPostContainers.next();
            System.out.println("postCont:"+postCont);
            System.out.println("postCont Year:"+postCont.getYear());
            System.out.println("postCont Month:"+postCont.getMonth());
            Iterator <Post> itPost=postCont.listPosts();
            while(itPost.hasNext())
            {
                Post postTmp=itPost.next();
                System.out.println("post en PostContainer:"+postTmp.getId());

            }
        }
    }
    * */
    
    /**
     * Metodo que agrega un postIn a un objeto (instancia) de la clase PostListenerContainer, el cual sirve para buscar por mes y año, todos los Post de entrada 
     * que han llegado por el listener
     * @param post
     * @param socialPostId
     * @param socialNetwork 
     */
    /*
    public void addReceivedPost(PostIn post, String socialPostId, SocialNetwork socialNetwork)
    {
        SWBModel swbModel=SWBContext.getSWBModel(post.getSemanticObject().getModel().getName());

        //Se crea un objeto de persistencia siempre y cuando no este creado para un ciero año y mes, de lo
        //contrario, se reutiliza uno para ese año y mes.
        //Una vez esto, se agrega el post al objeto creado o reutilizado.
       Date date=post.getPi_created();
       PostInContainer plcContainer=PostInContainer.getPostListenerContainerByDate(date, swbModel);
       plcContainer.addPlc_Post(post);
       plcContainer.setPlc_SocialNetworkInv(socialNetwork);
       //System.out.println("Datos completamente guardados..");

       //Código siguiente es solo para verificar de guardado, quitar despues...
       /*
       Iterator <SocialPost> itSocialPost=SocialPost.ClassMgr.listSocialPosts();
       while(itSocialPost.hasNext())
       {
           SocialPost socialPost=itSocialPost.next();
           System.out.println("socialPost en BD:"+socialPost.getId()+",SocialNetWork:"+socialPost.getSocialNetwork()+", Post:"+socialPost.getSocialPost());
       }


        Iterator <PostListenerContainer> itPlcContainers=PostListenerContainer.ClassMgr.listPostListenerContainers(swbModel);
        while(itPlcContainers.hasNext())
        {
            PostListenerContainer plcCont=itPlcContainers.next();
            System.out.println("postCont:"+plcCont);
            System.out.println("postCont Year:"+plcCont.getPlc_year());
            System.out.println("postCont Month:"+plcCont.getPlc_month());
            Iterator <Post> itPost=plcCont.listPlc_Posts();
            while(itPost.hasNext())
            {
                Post postTmp=itPost.next();
                System.out.println("post en PlcContainer:"+postTmp.getId());

            }
        }
        * */

    //}


    /*
     * Metodo que las clases que extiendan de esta deberan implementar para
     * escuchar una determinada red social por petición(Request)
     */
    @Override
    public void listen(Stream stream) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void stopListen(Stream stream) {
        //throw new UnsupportedOperationException("Not supported yet."
    }

    

    /*
     * Metodo que las clases que extiendan de esta deberan implementar para
     * autenticar una cuenta de red social
     */
    public void authenticate()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("SocialNetwork.authenticate(request,response,paramRequest)");
    }

    @Override
    public JSONObject getUserInfobyId(String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removePostOutfromSocialNet(PostOut postOut, SocialNetwork socialNet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



   
}
