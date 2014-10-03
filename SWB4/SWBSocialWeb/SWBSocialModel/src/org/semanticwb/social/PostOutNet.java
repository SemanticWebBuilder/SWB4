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
                //System.out.println("Entra a Observer de PostOutNet-0");
                if(action!=null)
                {
                    //System.out.println("Entra a Observer de PostOutNet");
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
                                //System.out.println("Elimina PostOutNet de PostMonitor unico:"+postMonitor);
                                postMonitor.removePostOutNet(postOutNet);
                            }
                        }
                    }
                    
                }
            }
        });
     }
    
    
    
}
