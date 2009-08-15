package org.semanticwb.portal.community.base;


public class FriendshipProspectBase extends org.semanticwb.portal.community.UserRelationship 
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_friendShipRequester=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#friendShipRequester");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_friendShipRequested=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#friendShipRequested");
    public static final org.semanticwb.platform.SemanticClass swbcomm_FriendshipProspect=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#FriendshipProspect");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#FriendshipProspect");

    public FriendshipProspectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspects(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspects()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect>(it, true);
    }

    public static org.semanticwb.portal.community.FriendshipProspect getFriendshipProspect(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.FriendshipProspect)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.FriendshipProspect createFriendshipProspect(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.FriendshipProspect)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFriendshipProspect(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFriendshipProspect(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFriendshipProspect(id, model)!=null);
    }

    public void setFriendShipRequester(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swbcomm_friendShipRequester, value.getSemanticObject());
    }

    public void removeFriendShipRequester()
    {
        getSemanticObject().removeProperty(swbcomm_friendShipRequester);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspectByFriendShipRequester(org.semanticwb.model.User friendshiprequester,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_friendShipRequester, friendshiprequester.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspectByFriendShipRequester(org.semanticwb.model.User friendshiprequester)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect> it=new org.semanticwb.model.GenericIterator(friendshiprequester.getSemanticObject().getModel().listSubjects(swbcomm_friendShipRequester,friendshiprequester.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getFriendShipRequester()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_friendShipRequester);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public void setFriendShipRequested(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swbcomm_friendShipRequested, value.getSemanticObject());
    }

    public void removeFriendShipRequested()
    {
        getSemanticObject().removeProperty(swbcomm_friendShipRequested);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspectByFriendShipRequested(org.semanticwb.model.User friendshiprequested,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_friendShipRequested, friendshiprequested.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspectByFriendShipRequested(org.semanticwb.model.User friendshiprequested)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect> it=new org.semanticwb.model.GenericIterator(friendshiprequested.getSemanticObject().getModel().listSubjects(swbcomm_friendShipRequested,friendshiprequested.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getFriendShipRequested()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_friendShipRequested);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
