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
        //Observador del objeto PostIn
        PostIn.social_PostIn.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(PostIn.social_PostIn))
                {
                    PostIn postIn = (PostIn) obj.createGenericInstance();
                    
                    //Cada que un PostIn se elimine, se ejecutara este código, revisa si el usuario(SocialNetworkUser) asociado al PostIn,
                    //tiene mas PostIn asociados, de no ser así, elimina dicho usuario (SocialNetworkUser).
                    
                    System.out.println("postIn/Observer/action:"+action+", postIn:"+postIn);
                    
                    //TODO:Probar este código.
                    if(action.equals("REMOVE")) //Si la acción es eliminar el SocialTopic
                    {
                        SocialNetworkUser socialNetworkUser=postIn.getPostInSocialNetworkUser();
                        if(socialNetworkUser!=null)
                        {
                            //Si dicho usuario no tiene mas PostIns, entonces que lo elimine de la clase SocialNetworkUser
                            //De lo contrarío se quedaría ahi como basura, pudiendo crecer demaciado la info en esa Clase.
                            if(!PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser).hasNext())
                            {
                                socialNetworkUser.remove();
                            }
                        }
                    }
                    
                }
            }
        });
        
        
    }
}
