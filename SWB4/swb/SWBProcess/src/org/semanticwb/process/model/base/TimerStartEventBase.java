package org.semanticwb.process.model.base;


public abstract class TimerStartEventBase extends org.semanticwb.process.model.StartEvent implements org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Catchable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TimerStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerStartEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerStartEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent>(it, true);
       }

       public static org.semanticwb.process.model.TimerStartEvent createTimerStartEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TimerStartEvent.ClassMgr.createTimerStartEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TimerStartEvent getTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerStartEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TimerStartEvent createTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerStartEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTimerStartEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByTrigger(org.semanticwb.process.model.EventDetail hastrigger,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasTrigger, hastrigger.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByTrigger(org.semanticwb.process.model.EventDetail hastrigger)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(hastrigger.getSemanticObject().getModel().listSubjects(swp_hasTrigger,hastrigger.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TimerStartEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail> listTriggers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail>(getSemanticObject().listObjectProperties(swp_hasTrigger));
    }

    public boolean hasTrigger(org.semanticwb.process.model.EventDetail eventdetail)
    {
        if(eventdetail==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasTrigger,eventdetail.getSemanticObject());
    }

    public void addTrigger(org.semanticwb.process.model.EventDetail value)
    {
        getSemanticObject().addObjectProperty(swp_hasTrigger, value.getSemanticObject());
    }

    public void removeAllTrigger()
    {
        getSemanticObject().removeProperty(swp_hasTrigger);
    }

    public void removeTrigger(org.semanticwb.process.model.EventDetail eventdetail)
    {
        getSemanticObject().removeObjectProperty(swp_hasTrigger,eventdetail.getSemanticObject());
    }


    public org.semanticwb.process.model.EventDetail getTrigger()
    {
         org.semanticwb.process.model.EventDetail ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTrigger);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EventDetail)obj.createGenericInstance();
         }
         return ret;
    }
}
