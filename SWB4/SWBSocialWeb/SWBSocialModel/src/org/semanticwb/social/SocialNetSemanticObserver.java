/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/**
 *
 * @author jorge.jimenez
 */
public class SocialNetSemanticObserver implements SemanticObserver {

    @Override
    public void notify(SemanticObject obj, Object prop, String lang, String action) {
        if (action != null) {
            SocialNetwork socialNetwork = (SocialNetwork) obj.createGenericInstance();
            WebSite wsite = WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());

            System.out.println("*********************Twitter/Observer/action:" + action + ", Twitter-Aberr:" + socialNetwork);

            //TODO:Probar este código.
            if (action.equals("REMOVE")) //Si la acción es eliminar el SocialTopic
            {
                Iterator<PostOut> itPostOuts = PostOut.ClassMgr.listPostOutBySocialNetwork(socialNetwork, wsite);
                while (itPostOuts.hasNext()) {
                    PostOut postOut = itPostOuts.next();
                    boolean isAlone = true;
                    Iterator<SocialNetwork> itPostOutSocialNets = postOut.listSocialNetworks();
                    while (itPostOutSocialNets.hasNext()) {
                        SocialNetwork socialNet = itPostOutSocialNets.next();
                        if (!socialNet.getURI().equals(socialNetwork.getURI())) {
                            System.out.println("NO SON IGUALES LAS SOCIALNETS DEL POSTOUT:" + socialNet.getURI() + ",---:" + socialNetwork.getURI());
                            isAlone = false;
                            break;
                        }
                    }
                    //La red social que estoy intentando borrar es la única en este PostOut, por lo tanto, si puedo borrar el PostOut (si no quedaría como basura)
                    System.out.println("isAlone:" + isAlone);
                    if (isAlone) {
                        System.out.println("postOut BORRADO:" + postOut);
                        postOut.remove();
                    }
                }
            }
        }
    }
}
