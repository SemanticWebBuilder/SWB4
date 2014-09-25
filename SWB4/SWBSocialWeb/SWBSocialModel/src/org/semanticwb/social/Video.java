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


public class Video extends org.semanticwb.social.base.VideoBase 
{
    public Video(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    //CUANDO SE ELIMINA UN POSTOUT, NO SE REQUIERE BORRAR SUS POSTOUTNETS DESDE OBSERVADOR, YA QUE ESO SE HACE DESDE LA ONTOLOGÍA (REMOVEDEPENDENCY)
    /*
    static {
        //Observador del objeto Video (PostOut)
        Video.social_Video.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                System.out.println("Entra a Observer de VideoPOSTOUT-0");
                if(action!=null)
                {
                    System.out.println("Entra a Observer de VideoPOSTOUT");
                    //Cada que un PostOut se elimine, se ejecutara este código, revisa si el PostOut tiene PostOutNets asociados
                    //de ser así elimina cada uno de ellos.
                    if(action.equals("REMOVE")) //Si la acción es eliminar el PostOut
                    {
                        System.out.println("Entra a Observer de VideoPOSTOUT-REMOVE");
                        PostOut postOut = (PostOut) obj.createGenericInstance();
                        Iterator <PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut);
                        while(itPostOutNets.hasNext())
                        {
                            PostOutNet postOutNet=itPostOutNets.next();
                            System.out.println("Entra a eliminar PostOutNet:"+postOutNet.getSocialPost());
                            postOutNet.remove();
                            System.out.println("Entra a eliminar PostOutNet-BORRADO...:");
                        }
                    }
                    
                }
            }
        });
     }
    */
}
