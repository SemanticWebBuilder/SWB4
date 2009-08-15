package org.semanticwb.portal.community.base;


public class UserRelationshipBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_UserRelationship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#UserRelationship");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#UserRelationship");

    public UserRelationshipBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.UserRelationship> listUserRelationships(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.UserRelationship>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.UserRelationship> listUserRelationships()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.UserRelationship>(it, true);
    }

    public static org.semanticwb.portal.community.UserRelationship getUserRelationship(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.UserRelationship)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.UserRelationship createUserRelationship(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.UserRelationship)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeUserRelationship(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasUserRelationship(String id, org.semanticwb.model.SWBModel model)
    {
        return (getUserRelationship(id, model)!=null);
    }
}
