package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class PostIn extends org.semanticwb.social.base.PostInBase 
{
    public PostIn(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
     /*
     * Busqueda de aseguradora por nombre de clase de implementación de webservice de cotización
     */
    public static PostIn getPostInbySocialMsgId(SWBModel model, String socialMsgId)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(PostIn.social_socialNetMsgId, socialMsgId);
        if(it.hasNext())
        {
            SemanticObject obj=it.next();
            return (PostIn)obj.createGenericInstance();
        }
        return null;
    }
    
    
    static {
        //Observador del objeto PostIn, este observador en realidad no se utiliza, ya que cada objeto
        //MessageIn, PhotoIn y VideoIn tienen su propio observador, de hecho probé y si hace algo con ellos
        //aunque desciendan de PostIn, no se ejecuta el código de este (PostIn), solo se ejecuta cuando se interactua
        //con ese objeto (PostIn) de manera directa.
        PostIn.social_PostIn.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null)
                {
                    PostIn postIn = (PostIn) obj.createGenericInstance();
                    
                    //Cada que un PostIn se elimine, se ejecutara este código, revisa si el usuario(SocialNetworkUser) asociado al PostIn,
                    //tiene mas PostIn asociados, de no ser así, elimina dicho usuario (SocialNetworkUser).
                    
                    System.out.println("*********************postIn/Observer/action:"+action+", postIn:"+postIn);
                    
                    //TODO:Probar este código.
                    if(action.equals("REMOVE")) //Si la acción es eliminar el SocialTopic
                    {
                        SocialNetworkUser socialNetworkUser=postIn.getPostInSocialNetworkUser();
                        if(socialNetworkUser!=null)
                        {
                            int i=0;
                            Iterator itPostInUserNumber=PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser);
                            while(itPostInUserNumber.hasNext())
                            {
                                i++;
                                System.out.println("IJ1:"+i);
                                if(i>1) 
                                {
                                    System.out.println("IJ2:"+i);
                                    break;
                                }
                                System.out.println("IJ3:"+i);
                                itPostInUserNumber.next();
                            }
                           
                            System.out.println("I:"+i);
                            
                            if(i<=1)
                            {
                                System.out.println("MessageIn Observador-J3:"+socialNetworkUser+", FUE ELIMINADO...");
                                socialNetworkUser.remove();
                                System.out.println("MessageIn Observador-J3.1:"+socialNetworkUser+", FUE ELIMINADO...");
                            }else{
                                System.out.println("Por PostIn, no encontro ni maiz k");
                            }
                    }
                    
                }
            }
            }
        });
    }
    
    
}
