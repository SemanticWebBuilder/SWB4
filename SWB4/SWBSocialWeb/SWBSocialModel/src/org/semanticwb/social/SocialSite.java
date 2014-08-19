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
