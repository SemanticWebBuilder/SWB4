package org.semanticwb.process.model.base;


public abstract class ErrorEndEventBase extends org.semanticwb.process.model.EndEvent implements org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Throwable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_ErrorEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ErrorEndEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ErrorEndEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent>(it, true);
       }

       public static org.semanticwb.process.model.ErrorEndEvent createErrorEndEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ErrorEndEvent.ClassMgr.createErrorEndEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ErrorEndEvent getErrorEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ErrorEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ErrorEndEvent createErrorEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ErrorEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeErrorEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasErrorEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getErrorEndEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEventByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEventByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEventByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEventByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEventByResult(org.semanticwb.process.model.EventDetail hasresult,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasResult, hasresult.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEventByResult(org.semanticwb.process.model.EventDetail hasresult)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorEndEvent> it=new org.semanticwb.model.GenericIterator(hasresult.getSemanticObject().getModel().listSubjects(swp_hasResult,hasresult.getSemanticObject()));
       return it;
   }
    }

    public ErrorEndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
