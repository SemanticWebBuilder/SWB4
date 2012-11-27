/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.community;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;


public class FriendshipProspect extends org.semanticwb.portal.community.base.FriendshipProspectBase 
{
    private static Logger log = SWBUtils.getLogger(FriendshipProspect.class);

    public FriendshipProspect(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static boolean createFriendshipProspect(User resquester, User requested, SWBModel model) {
        try{
            FriendshipProspect newFriendShip=FriendshipProspect.ClassMgr.createFriendshipProspect(model);
            newFriendShip.setFriendShipRequester(resquester);
            newFriendShip.setFriendShipRequested(requested);
            return true;
        }catch(Exception e){
            log.error(e);
            return false;
        }
    }

    public static boolean removeFriendshipProspectByRequested(User requested, User requester, SWBModel model) {
        Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequested(requested, model);
        while (itFriendshipProspect.hasNext()) {
            FriendshipProspect friendshipProspect = itFriendshipProspect.next();
            User userRequester = friendshipProspect.getFriendShipRequester();
            if (requester!=null && requester.getURI()!=null && userRequester!=null && userRequester.getURI()!=null && userRequester.getURI().equals(requester.getURI())) {
                friendshipProspect.remove();
                return true;
            }
        }
        return false;
    }


    public static boolean removeFriendshipProspectByRequester(User requester, User requested,  SWBModel model) {
        Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequester(requester, model);
        while (itFriendshipProspect.hasNext()) {
            FriendshipProspect friendshipProspect = itFriendshipProspect.next();
            User userRequested = friendshipProspect.getFriendShipRequested();
            if (userRequested.getURI().equals(requested.getURI())) {
                friendshipProspect.remove();
                return true;
            }
        }
        return false;
    }

    public static boolean findFriendProspectedByRequester(User requester, User requested, SWBModel model) {
        Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequester(requester, model);
        while (itFriendshipProspect.hasNext()) {
            FriendshipProspect friendshipProspect = itFriendshipProspect.next();
            User userRequested = friendshipProspect.getFriendShipRequested();
            if (userRequested.getURI().equals(requested.getURI())) {
                return true;
            }
        }
        return false;
    }

}
