package org.semanticwb.process.model.base;


public abstract class ParticipantBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
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
    }

    public ParticipantBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
