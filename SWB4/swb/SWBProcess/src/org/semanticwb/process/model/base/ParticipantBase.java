package org.semanticwb.process.model.base;


public abstract class ParticipantBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
       public static final org.semanticwb.platform.SemanticProperty swp_roleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#roleRef");
       public static final org.semanticwb.platform.SemanticProperty swp_participantType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#participantType");
       public static final org.semanticwb.platform.SemanticClass swp_Entity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Entity");
       public static final org.semanticwb.platform.SemanticProperty swp_entityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#entityRef");
       public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipants(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipants()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant>(it, true);
       }

       public static org.semanticwb.process.model.Participant createParticipant(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Participant.ClassMgr.createParticipant(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Participant getParticipant(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Participant)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Participant createParticipant(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Participant)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeParticipant(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasParticipant(String id, org.semanticwb.model.SWBModel model)
       {
           return (getParticipant(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByRoleRef(org.semanticwb.model.Role roleref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_roleRef, roleref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByRoleRef(org.semanticwb.model.Role roleref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(roleref.getSemanticObject().getModel().listSubjects(swp_roleRef,roleref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByEntityRef(org.semanticwb.process.model.Entity entityref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_entityRef, entityref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByEntityRef(org.semanticwb.process.model.Entity entityref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(entityref.getSemanticObject().getModel().listSubjects(swp_entityRef,entityref.getSemanticObject()));
       return it;
   }
    }

    public ParticipantBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setRoleRef(org.semanticwb.model.Role value)
    {
        getSemanticObject().setObjectProperty(swp_roleRef, value.getSemanticObject());
    }

    public void removeRoleRef()
    {
        getSemanticObject().removeProperty(swp_roleRef);
    }


    public org.semanticwb.model.Role getRoleRef()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_roleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public String getParticipantType()
    {
        return getSemanticObject().getProperty(swp_participantType);
    }

    public void setParticipantType(String value)
    {
        getSemanticObject().setProperty(swp_participantType, value);
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
