package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto Post (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese post para c/red social. 
   */
public class PostOutNet extends org.semanticwb.social.base.PostOutNetBase 
{
    public PostOutNet(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    //Si se elimina un PostOutNet, debo buscarlo en la clase PostMonitor y si esta ahi, debo eliminarlo, de lo contrario si existe en PostMonitor
    //y no se elimina de ahi, marcaría un NullPointer Exception el MonitorMgr y dejaría de guncionar el monitoreo
    static {
        //Observador del objeto PostOutNet 
        PostOutNet.social_PostOutNet.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                System.out.println("Entra a Observer de PostOutNet-0");
                if(action!=null)
                {
                    System.out.println("Entra a Observer de PostOutNet");
                    //Cada que un PostOut se elimine, se ejecutara este código, revisa si el PostOut tiene PostOutNets asociados
                    //de ser así elimina cada uno de ellos.
                    if(action.equals("REMOVE") && prop==null) //Si la acción es eliminar el PostOut
                    {
                        PostOutNet postOutNet = (PostOutNet) obj.createGenericInstance();
                        if(PostMonitor.ClassMgr.listPostMonitors(SWBContext.getAdminWebSite()).hasNext())
                        {
                            PostMonitor postMonitor=PostMonitor.ClassMgr.listPostMonitors(SWBContext.getAdminWebSite()).next();
                            if(postMonitor.hasPostOutNet(postOutNet))
                            {
                                System.out.println("Elimina PostOutNet de PostMonitor unico:"+postMonitor);
                                postMonitor.removePostOutNet(postOutNet);
                            }
                        }
                    }
                    
                }
            }
        });
     }
    
    
    
}
