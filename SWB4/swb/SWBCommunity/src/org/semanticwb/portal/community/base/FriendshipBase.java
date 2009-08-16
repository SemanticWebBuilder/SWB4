package org.semanticwb.portal.community.base;


public class FriendshipBase extends org.semanticwb.portal.community.UserRelationship 
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasFriend=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasFriend");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Friendship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Friendship");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Friendship");

    public FriendshipBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendships(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendships()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship>(it, true);
    }

    public static org.semanticwb.portal.community.Friendship createFriendship(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Friendship.createFriendship(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Friendship getFriendship(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Friendship)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Friendship createFriendship(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Friendship)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFriendship(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFriendship(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFriendship(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listFriends()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(swbcomm_hasFriend));
    }

    public boolean hasFriend(org.semanticwb.model.User user)
    {
        if(user==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasFriend,user.getSemanticObject());
    }

    public void addFriend(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasFriend, value.getSemanticObject());
    }

    public void removeAllFriend()
    {
        getSemanticObject().removeProperty(swbcomm_hasFriend);
    }

    public void removeFriend(org.semanticwb.model.User user)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasFriend,user.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendshipByFriend(org.semanticwb.model.User hasfriend,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasFriend, hasfriend.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendshipByFriend(org.semanticwb.model.User hasfriend)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship> it=new org.semanticwb.model.GenericIterator(hasfriend.getSemanticObject().getModel().listSubjects(swbcomm_hasFriend,hasfriend.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getFriend()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasFriend);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
