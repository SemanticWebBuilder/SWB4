package org.semanticwb.process.model.base;


public abstract class ParticipantEntityBase extends org.semanticwb.process.model.Participant implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Entity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Entity");
       public static final org.semanticwb.platform.SemanticProperty swp_entityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#entityRef");
       public static final org.semanticwb.platform.SemanticClass swp_ParticipantEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantEntity");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantEntity");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ParticipantEntity> listParticipantEntities(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantEntity>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ParticipantEntity> listParticipantEntities()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantEntity>(it, true);
       }

       public static org.semanticwb.process.model.ParticipantEntity createParticipantEntity(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ParticipantEntity.ClassMgr.createParticipantEntity(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ParticipantEntity getParticipantEntity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ParticipantEntity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ParticipantEntity createParticipantEntity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ParticipantEntity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeParticipantEntity(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasParticipantEntity(String id, org.semanticwb.model.SWBModel model)
       {
           return (getParticipantEntity(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ParticipantEntity> listParticipantEntityByEntityRef(org.semanticwb.process.model.Entity entityref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantEntity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_entityRef, entityref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ParticipantEntity> listParticipantEntityByEntityRef(org.semanticwb.process.model.Entity entityref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantEntity> it=new org.semanticwb.model.GenericIterator(entityref.getSemanticObject().getModel().listSubjects(swp_entityRef,entityref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ParticipantEntity> listParticipantEntityByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantEntity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ParticipantEntity> listParticipantEntityByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantEntity> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ParticipantEntityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setEntityRef(org.semanticwb.process.model.Entity value)
    {
        getSemanticObject().setObjectProperty(swp_entityRef, value.getSemanticObject());
    }

    public void removeEntityRef()
    {
        getSemanticObject().removeProperty(swp_entityRef);
    }


    public org.semanticwb.process.model.Entity getEntityRef()
    {
         org.semanticwb.process.model.Entity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_entityRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Entity)obj.createGenericInstance();
         }
         return ret;
    }
}
