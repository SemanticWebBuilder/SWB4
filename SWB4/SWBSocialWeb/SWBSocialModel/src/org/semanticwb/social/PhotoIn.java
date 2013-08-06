package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


   /**
   * Clase en donde se almacenan todos los post de tipo Photo y que entran por el Listener 
   */
public class PhotoIn extends org.semanticwb.social.base.PhotoInBase 
{
    public PhotoIn(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    static {
        //Observador del objeto PostIn
        PhotoIn.social_PhotoIn.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(PhotoIn.social_PhotoIn))
                {
                    //Cada que un PostIn se elimine, se ejecutara este código, revisa si el usuario(SocialNetworkUser) asociado al PostIn,
                    //tiene mas PostIn asociados, de no ser así, elimina dicho usuario (SocialNetworkUser).
                    if(action.equals("REMOVE")) //Si la acción es eliminar el PhotoIn
                    {
                        PostIn postIn = (PostIn) obj.createGenericInstance();
                        System.out.println("PhotoIn/Observer/action:"+action+", postIn:"+postIn);
                        System.out.println("PhotoIn Observador-1");
                        SocialNetworkUser socialNetworkUser=postIn.getPostInSocialNetworkUser();
                        if(socialNetworkUser!=null)
                        {
                            System.out.println("PhotoIn Observador-2:"+socialNetworkUser+", trae:"+PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser).hasNext());
                            
                            //Si dicho usuario no tiene mas PostIns, entonces que lo elimine de la clase SocialNetworkUser
                            //De lo contrarío se quedaría ahi como basura, pudiendo crecer demaciado la info en esa Clase.
                            
                            //****Si el usuario no tiene mas de un mensaje, que elimine a su SocialNewWorkUser asociado, porque un mensaje
                            //Porque javier mansa llamar este código antes de que se elimine el mensaje por el cual llegó aqui, de lo 
                            //contrario vería si no tiene ningún mensaje (0 mensajes) ejecutaría este código, pero como no es así, por 
                            //eso lo hago cuando solo tenga un mensaje, ya que aún cuenta por el que llega a este código.
                            
                            int i=0;
                            Iterator itPostInUserNumber=PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser);
                            while(itPostInUserNumber.hasNext())
                            {
                                i++;
                                if(i>1) break;
                                itPostInUserNumber.next();
                            }
                           
                            if(i<=1)    
                            {
                                socialNetworkUser.remove();
                                System.out.println("Elimino socialNetUser en PhotoIn:"+socialNetworkUser);
                            }
                        }
                        if(postIn.getPostInStream()!=null)
                        {
                            //Si el postIn que llega es el ultimo mensaje en su Stream, que elimine el registro(instancia) de ese Stream en la
                            //clase SocialNetStreamSearch
                            Stream stream=postIn.getPostInStream();
                            int i=0;
                            Iterator<PostIn> itPostInStreamNumber=stream.listPostInStreamInvs();
                            while(itPostInStreamNumber.hasNext())
                            {
                                i++;
                                if(i>1) break;
                                itPostInStreamNumber.next();
                            }

                            if(i<=1)    
                            {
                                Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
                                while(itSocialNetStreamSearch.hasNext())
                                {
                                    SocialNetStreamSearch socialNetStreamSearch=itSocialNetStreamSearch.next();
                                    System.out.println("Remueve en Stream:"+socialNetStreamSearch);
                                    socialNetStreamSearch.remove();
                                }
                            }
                        }
                    }
                }
            }
        });
     }
}
