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
