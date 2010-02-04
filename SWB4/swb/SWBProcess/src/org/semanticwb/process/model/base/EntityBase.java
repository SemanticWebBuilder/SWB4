package org.semanticwb.process.model.base;


public abstract class EntityBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Entity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Entity");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Entity");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Entity> listEntities(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Entity>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Entity> listEntities()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Entity>(it, true);
       }

       public static org.semanticwb.process.model.Entity createEntity(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Entity.ClassMgr.createEntity(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Entity getEntity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Entity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Entity createEntity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Entity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeEntity(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasEntity(String id, org.semanticwb.model.SWBModel model)
       {
           return (getEntity(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Entity> listEntityByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Entity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Entity> listEntityByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Entity> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public EntityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
