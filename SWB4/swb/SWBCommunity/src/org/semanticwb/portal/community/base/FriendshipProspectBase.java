package org.semanticwb.portal.community.base;


public class FriendshipProspectBase extends org.semanticwb.portal.community.UserRelationship 
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasFriendProspect=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasFriendProspect");
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listFriendProspects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(swbcomm_hasFriendProspect));
    }

    public boolean hasFriendProspect(org.semanticwb.model.User user)
    {
        if(user==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasFriendProspect,user.getSemanticObject());
    }

    public void addFriendProspect(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasFriendProspect, value.getSemanticObject());
    }

    public void removeAllFriendProspect()
    {
        getSemanticObject().removeProperty(swbcomm_hasFriendProspect);
    }

    public void removeFriendProspect(org.semanticwb.model.User user)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasFriendProspect,user.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspectByHasFriendProspect(org.semanticwb.model.User hasfriendprospect,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasFriendProspect, hasfriendprospect.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.FriendshipProspect> listFriendshipProspectByHasFriendProspect(org.semanticwb.model.User hasfriendprospect)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.FriendshipProspect> it=new org.semanticwb.model.GenericIterator(hasfriendprospect.getSemanticObject().getModel().listSubjects(swbcomm_hasFriendProspect,hasfriendprospect.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getFriendProspect()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasFriendProspect);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
