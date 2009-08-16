package org.semanticwb.portal.community;

import java.util.Iterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;


public class FriendshipProspect extends org.semanticwb.portal.community.base.FriendshipProspectBase 
{
    public FriendshipProspect(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static boolean removeFriendshipProspectByRequested(User requested, User requester, WebSite website) {
        Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequested(requested, website);
        while (itFriendshipProspect.hasNext()) {
            FriendshipProspect friendshipProspect = itFriendshipProspect.next();
            User userRequester = friendshipProspect.getFriendShipRequester();
            if (userRequester.getURI().equals(requester.getURI())) {
                friendshipProspect.remove();
                return true;
            }
        }
        return false;
    }


    public static boolean removeFriendshipProspectByRequester(User requester, User requested, WebSite website) {
        Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequester(requester, website);
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
}
