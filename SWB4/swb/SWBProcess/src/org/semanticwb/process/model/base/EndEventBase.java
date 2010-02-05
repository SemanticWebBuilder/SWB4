package org.semanticwb.process.model.base;


public abstract class EndEventBase extends org.semanticwb.process.model.Event implements org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Throwable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_EndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EndEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EndEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent>(it, true);
       }

       public static org.semanticwb.process.model.EndEvent createEndEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.EndEvent.ClassMgr.createEndEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.EndEvent getEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.EndEvent createEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasEndEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getEndEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEventByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEventByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEventByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEventByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEventByResult(org.semanticwb.process.model.EventDetail hasresult,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasResult, hasresult.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEventByResult(org.semanticwb.process.model.EventDetail hasresult)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEvent> it=new org.semanticwb.model.GenericIterator(hasresult.getSemanticObject().getModel().listSubjects(swp_hasResult,hasresult.getSemanticObject()));
       return it;
   }
    }

    public EndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail> listResults()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail>(getSemanticObject().listObjectProperties(swp_hasResult));
    }

    public boolean hasResult(org.semanticwb.process.model.EventDetail eventdetail)
    {
        if(eventdetail==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasResult,eventdetail.getSemanticObject());
    }

    public void addResult(org.semanticwb.process.model.EventDetail value)
    {
        getSemanticObject().addObjectProperty(swp_hasResult, value.getSemanticObject());
    }

    public void removeAllResult()
    {
        getSemanticObject().removeProperty(swp_hasResult);
    }

    public void removeResult(org.semanticwb.process.model.EventDetail eventdetail)
    {
        getSemanticObject().removeObjectProperty(swp_hasResult,eventdetail.getSemanticObject());
    }


    public org.semanticwb.process.model.EventDetail getResult()
    {
         org.semanticwb.process.model.EventDetail ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasResult);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EventDetail)obj.createGenericInstance();
         }
         return ret;
    }
}
