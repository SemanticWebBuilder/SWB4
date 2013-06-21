package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


   /**
   * Catalogo de temas de un modelo (Marca) 
   */
public class SocialTopic extends org.semanticwb.social.base.SocialTopicBase 
{
    public SocialTopic(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    static {
        //Observador del objeto SocialTopic
        SocialTopic.social_SocialTopic.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(SocialTopic.social_SocialTopic))
                {
                    SocialTopic socialTopic = (SocialTopic) obj.createGenericInstance();
                    
                    //Cada que un SocialTopic se elimine(de la papelera), se ejecutara este código (borrado de PostOut asociados)
                    
                    System.out.println("SocialTopic/Observer/action:"+action+", socialTopic:"+socialTopic);
                    
                    if(action.equals("REMOVE")) //Si la acción es eliminar el SocialTopic
                    {
                        //TODO:Ver si este proceso de eliminación lo envío a un thread por separado, ya que pudieran
                        //ser muchos postOuts y estos pueden tener cosas en filesystem y pudiera tardar un poco,
                        //aunque no creo que tanto, talvez no sea necesario y lo dejo como esta.
                       Iterator<Post> itPost=socialTopic.listPosts();
                       while(itPost.hasNext())
                       {
                           Post post=itPost.next();
                           //No quiero borrar los PostIn, solo los PostOut. Los PostIn quedarían sin tema asignado, pero no importa
                           //se podrían seguir visualizando desde el stream que entraron, ahi se podrían reasignar a otro SocialTema si así se deseara
                           //Los PostOut en cambio, no se podrían visualizar si se elimina el SocialTopic,ademas, no sería necesario,  así que mejor los eliminanos aquí.
                           if(post instanceof PostOut)  
                           {
                               post.remove();
                               //System.out.println("Se elimina postOut en Observador-2:"+post.getMsg_Text());
                           }
                       }
                    }
                    
                }
            }
        });
        
        
    }
    
    
}
