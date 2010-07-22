package org.semanticwb.model.base;


public abstract class RoleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#role");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(it, true);
        }

        public static org.semanticwb.model.RoleRef createRoleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id), model);
        }

        public static org.semanticwb.model.RoleRef getRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.RoleRef createRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRoleRef(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_role, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_role,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public RoleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_role, value.getSemanticObject());
        }else
        {
            removeRole();
        }
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_role);
    }

    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_role);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
