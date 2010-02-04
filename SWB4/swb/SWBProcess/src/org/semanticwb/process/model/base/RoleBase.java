package org.semanticwb.process.model.base;


public abstract class RoleBase extends org.semanticwb.process.model.SupportingElement 
{
       public static final org.semanticwb.platform.SemanticClass swp_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Role");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Role");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Role> listRoles(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Role>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Role> listRoles()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Role>(it, true);
       }

       public static org.semanticwb.process.model.Role createRole(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Role.ClassMgr.createRole(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Role getRole(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Role)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Role createRole(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Role)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeRole(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasRole(String id, org.semanticwb.model.SWBModel model)
       {
           return (getRole(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Role> listRoleByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Role> listRoleByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Role> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public RoleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

}
