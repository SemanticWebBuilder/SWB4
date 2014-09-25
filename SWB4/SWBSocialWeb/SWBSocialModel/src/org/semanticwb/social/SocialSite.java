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

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.social.util.SWBSocialUtil;


   /**
   * Clase que hereda de swb:WebSite. Es un tipo de website Social. De esta manera se puede contar con todos los elementos en el arbol de navegación en la administración, y otros elementos utiles para Social Site. 
   */
public class SocialSite extends org.semanticwb.social.base.SocialSiteBase 
{
    private static Logger log = SWBUtils.getLogger(SocialSite.class);
    
    public SocialSite(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    static {
       //Observador de la clase "Stream", cada que haya un cambio en ella se ejecuta el siguiente código
        SocialSite.sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(SocialSite.social_SocialSite))
                {
                    SocialSite socialSite = (SocialSite) obj.createGenericInstance();
                    if(action.equals("REMOVE") && prop==null)   //Quiere decir que se esta eliminando al SocialSite
                    {
                        //Elimino todas las estadisticas de la tabla relacional socialnets_stats, que tengan que ver con la marca que se esta eliminando 
                        try{
                            SWBSocialUtil.LOG.removeSocialNetStats(socialSite);
                        }catch(Exception e){
                            log.error(e);
                        }
                    }
                }
            }
        });
     }
}
