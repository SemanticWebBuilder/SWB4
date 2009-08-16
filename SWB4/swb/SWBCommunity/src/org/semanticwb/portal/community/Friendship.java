package org.semanticwb.portal.community;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;


public class Friendship extends org.semanticwb.portal.community.base.FriendshipBase 
{
    public Friendship(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static boolean areFriends(User user1, User user2, SWBModel model) {
        Iterator<Friendship> itMyFriends = Friendship.listFriendshipByFriend(user1, model);
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

    public static boolean removeFriendRelationShip(User user1, User user2, SWBModel model) {
        Iterator<Friendship> itMyFriends = Friendship.listFriendshipByFriend(user1, model);
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
