package org.semanticwb.social;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;


public class SocialNetwork extends org.semanticwb.social.base.SocialNetworkBase 
{
    public SocialNetwork(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void addSentPost(PostOut post, String socialPostId, SocialNetwork socialNetwork)
    {
        SWBModel swbModel=SWBContext.getSWBModel(post.getSemanticObject().getModel().getName());
        
         // Se crea un onjeto de persistencia, el cual contiene el post, la red social y el identificador creado
        // en dicha red social para dicho post
        SocialPost newSocialPost=SocialPost.ClassMgr.createSocialPost(socialPostId, swbModel);
        newSocialPost.setSocialPost(post);
        newSocialPost.setSocialNetwork(socialNetwork);

        //Se crea un objeto de persistencia siempre y cuando no este creado para un ciero año y mes, de lo
        //contrario, se reutiliza uno para ese año y mes.
        //Una vez esto, se agrega el post al objeto creado o reutilizado.
       Date date=post.getCreated();
       PostContainer postContainer=PostContainer.getPostContainerByDate(date, swbModel);
       postContainer.addPost(post);
       postContainer.setPc_SocialNetworkInv(socialNetwork);
       //System.out.println("Datos completamente guardados..");

       //Código siguiente es solo para verificar de guardado, quitar despues...
       Iterator <SocialPost> itSocialPost=SocialPost.ClassMgr.listSocialPosts();
       while(itSocialPost.hasNext())
       {
           SocialPost socialPost=itSocialPost.next();
           System.out.println("socialPost en BD:"+socialPost.getId()+",SocialNetWork:"+socialPost.getSocialNetwork()+", Post:"+socialPost.getSocialPost());
       }


        Iterator <PostContainer> itPostContainers=PostContainer.ClassMgr.listPostContainers();
        while(itPostContainers.hasNext())
        {
            PostContainer postCont=itPostContainers.next();
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

    public void addReceivedPost(PostIn post, String socialPostId, SocialNetwork socialNetwork)
    {
        SWBModel swbModel=SWBContext.getSWBModel(post.getSemanticObject().getModel().getName());

         // Se crea un onjeto de persistencia, el cual contiene el post, la red social y el identificador creado
        // en dicha red social para dicho post
        SocialPost newSocialPost=SocialPost.ClassMgr.createSocialPost(socialPostId, swbModel);
        newSocialPost.setSocialPost(post);
        newSocialPost.setSocialNetwork(socialNetwork);

        //Se crea un objeto de persistencia siempre y cuando no este creado para un ciero año y mes, de lo
        //contrario, se reutiliza uno para ese año y mes.
        //Una vez esto, se agrega el post al objeto creado o reutilizado.
       Date date=post.getCreated();
       PostListenerContainer plcContainer=PostListenerContainer.getPostListenerContainerByDate(date, swbModel);
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

    }


    @Override
    public void listen(Stream stream) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void authenticate()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
