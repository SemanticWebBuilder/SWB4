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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class SocialNetSemanticObserver implements SemanticObserver {
    
    private static Logger log = SWBUtils.getLogger(SocialNetSemanticObserver.class);

    @Override
    public void notify(SemanticObject obj, Object prop, String lang, String action) {
        if (action != null) {
            SocialNetwork socialNetwork = (SocialNetwork) obj.createGenericInstance();
            WebSite wsite = WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
            
            //System.out.println("*********************SocialNetSemanticObserver/Observer/action:" + action + ", socialNetwork:" + socialNetwork);

            //TODO:Probar este código.
            if (action.equals("REMOVE") && prop==null) //Si la acción es eliminar el SocialNetwork
            {
                Iterator<PostOut> itPostOuts = PostOut.ClassMgr.listPostOutBySocialNetwork(socialNetwork, wsite);   //Lista todos los PostOut de la red social a eliminar
                while (itPostOuts.hasNext()) {
                    PostOut postOut = itPostOuts.next();
                    boolean isAlone = true;
                    Iterator<SocialNetwork> itPostOutSocialNets = postOut.listSocialNetworks();
                    while (itPostOutSocialNets.hasNext()) {
                        SocialNetwork socialNet = itPostOutSocialNets.next();
                        if (!socialNet.getURI().equals(socialNetwork.getURI())) {
                            //System.out.println("NO SON IGUALES LAS SOCIALNETS DEL POSTOUT:" + socialNet.getURI() + ",---:" + socialNetwork.getURI());
                            isAlone = false;
                            break;
                        }
                    }
                    //La red social que estoy intentando borrar es la única en este PostOut, por lo tanto, si puedo borrar el PostOut (si no quedaría como basura)
                    //System.out.println("isAlone:" + isAlone);
                    if (isAlone) {
                        //System.out.println("postOut BORRADO:" + postOut);
                        postOut.remove();
                    }
                }
                //Elimino todas las estadisticas de la tabla relacional socialnets_stats, que tengan que ver con la cuenta de red social que se esta eliminando
                try{
                    SWBSocialUtil.LOG.removeSocialNetStats((SocialSite)wsite, socialNetwork);
                }catch(Exception e){
                    log.error(e);
                }
            }
        }
    }
}
