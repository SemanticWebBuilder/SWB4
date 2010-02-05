package org.semanticwb.process.model.base;


public abstract class TerminateEndEventBase extends org.semanticwb.process.model.EndEvent implements org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Throwable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TerminateEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TerminateEndEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TerminateEndEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent>(it, true);
       }

       public static org.semanticwb.process.model.TerminateEndEvent createTerminateEndEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TerminateEndEvent.ClassMgr.createTerminateEndEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TerminateEndEvent getTerminateEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TerminateEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TerminateEndEvent createTerminateEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TerminateEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTerminateEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTerminateEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTerminateEndEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEventByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEventByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEventByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEventByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEventByResult(org.semanticwb.process.model.EventDetail hasresult,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasResult, hasresult.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TerminateEndEvent> listTerminateEndEventByResult(org.semanticwb.process.model.EventDetail hasresult)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateEndEvent> it=new org.semanticwb.model.GenericIterator(hasresult.getSemanticObject().getModel().listSubjects(swp_hasResult,hasresult.getSemanticObject()));
       return it;
   }
    }

    public TerminateEndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
