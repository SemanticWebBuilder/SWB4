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
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;

/**
 * Object that manages relationship between users (creates, removes, etc relationships)
 * @author jorge.jimenez
 * @version 1.0
 */
public class Friendship extends org.semanticwb.portal.community.base.FriendshipBase 
{
    public Friendship(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static boolean areFriends(User user1, User user2, SWBModel model) {
        Iterator<Friendship> itMyFriends = Friendship.ClassMgr.listFriendshipByFriend(user1, model);
        while (itMyFriends.hasNext()) {
            Friendship friendShip = itMyFriends.next();
            Iterator<User> itfriendUser = friendShip.listFriends();
            while (itfriendUser.hasNext()) {
                User friendUser = itfriendUser.next();
                if (friendUser.getURI().equals(user2.getURI())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int myFriends(User user1, SWBModel model) {
        int cont=0;
        Iterator<Friendship> itMyFriends = Friendship.ClassMgr.listFriendshipByFriend(user1, model);
        while (itMyFriends.hasNext()) {
            itMyFriends.next();
            cont++;
        }
        return cont;
    }

    public static boolean removeFriendRelationShip(User user1, User user2, SWBModel model) {
        Iterator<Friendship> itMyFriends = Friendship.ClassMgr.listFriendshipByFriend(user1, model);
        while (itMyFriends.hasNext()) {
            Friendship friendShip = itMyFriends.next();
            Iterator<User> itfriendUser = friendShip.listFriends();
            while (itfriendUser.hasNext()) {
                User friendUser = itfriendUser.next();
                if (friendUser.getURI().equals(user2.getURI())) {
                    friendShip.remove();
                    return true;
                }
            }
        }
        return false;
    }
}
