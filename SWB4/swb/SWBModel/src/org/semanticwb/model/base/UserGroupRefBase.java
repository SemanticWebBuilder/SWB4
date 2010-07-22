package org.semanticwb.model.base;


public abstract class UserGroupRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_userGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userGroup");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(it, true);
        }

        public static org.semanticwb.model.UserGroupRef createUserGroupRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.UserGroupRef.ClassMgr.createUserGroupRef(String.valueOf(id), model);
        }

        public static org.semanticwb.model.UserGroupRef getUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroupRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.UserGroupRef createUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroupRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserGroupRef(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userGroup, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userGroup,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public UserGroupRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setUserGroup(org.semanticwb.model.UserGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_userGroup, value.getSemanticObject());
        }else
        {
            removeUserGroup();
        }
    }

    public void removeUserGroup()
    {
        getSemanticObject().removeProperty(swb_userGroup);
    }

    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }
}
