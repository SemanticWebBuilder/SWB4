package org.semanticwb.process.model.base;


public abstract class NormalIntermediateEventBase extends org.semanticwb.process.model.IntermediateEvent implements org.semanticwb.process.model.Assignable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_NormalIntermediateEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalIntermediateEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalIntermediateEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.NormalIntermediateEvent> listNormalIntermediateEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalIntermediateEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.NormalIntermediateEvent> listNormalIntermediateEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalIntermediateEvent>(it, true);
       }

       public static org.semanticwb.process.model.NormalIntermediateEvent createNormalIntermediateEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.NormalIntermediateEvent.ClassMgr.createNormalIntermediateEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.NormalIntermediateEvent getNormalIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.NormalIntermediateEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.NormalIntermediateEvent createNormalIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.NormalIntermediateEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeNormalIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasNormalIntermediateEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getNormalIntermediateEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.NormalIntermediateEvent> listNormalIntermediateEventByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalIntermediateEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalIntermediateEvent> listNormalIntermediateEventByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalIntermediateEvent> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalIntermediateEvent> listNormalIntermediateEventByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalIntermediateEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalIntermediateEvent> listNormalIntermediateEventByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalIntermediateEvent> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public NormalIntermediateEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
