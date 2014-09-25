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
                    
                    System.out.println("SocialTopic/Observer/action:"+action+", socialTopic:"+socialTopic+",prop:"+prop);
                    
                    if(action.equals("REMOVE") && prop==null) //Si la acción es eliminar el SocialTopic
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
