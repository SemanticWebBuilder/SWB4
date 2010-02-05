package org.semanticwb.process.model.base;


public abstract class IntermediateEventBase extends org.semanticwb.process.model.Event implements org.semanticwb.process.model.Assignable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_IntermediateEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IntermediateEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IntermediateEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.IntermediateEvent> listIntermediateEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.IntermediateEvent> listIntermediateEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateEvent>(it, true);
       }

       public static org.semanticwb.process.model.IntermediateEvent createIntermediateEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.IntermediateEvent.ClassMgr.createIntermediateEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.IntermediateEvent getIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.IntermediateEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.IntermediateEvent createIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.IntermediateEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getIntermediateEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.IntermediateEvent> listIntermediateEventByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.IntermediateEvent> listIntermediateEventByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateEvent> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.IntermediateEvent> listIntermediateEventByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.IntermediateEvent> listIntermediateEventByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateEvent> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public IntermediateEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
