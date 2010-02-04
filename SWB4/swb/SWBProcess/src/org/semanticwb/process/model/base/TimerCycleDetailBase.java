package org.semanticwb.process.model.base;


public abstract class TimerCycleDetailBase extends org.semanticwb.process.model.TimerDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TimeDateExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimeDateExpression");
       public static final org.semanticwb.platform.SemanticProperty swp_timeCycle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#timeCycle");
       public static final org.semanticwb.platform.SemanticClass swp_TimerCycleDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerCycleDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerCycleDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TimerCycleDetail> listTimerCycleDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerCycleDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TimerCycleDetail> listTimerCycleDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerCycleDetail>(it, true);
       }

       public static org.semanticwb.process.model.TimerCycleDetail createTimerCycleDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TimerCycleDetail.ClassMgr.createTimerCycleDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TimerCycleDetail getTimerCycleDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerCycleDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TimerCycleDetail createTimerCycleDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerCycleDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTimerCycleDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTimerCycleDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTimerCycleDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TimerCycleDetail> listTimerCycleDetailByTimeCycle(org.semanticwb.process.model.TimeDateExpression timecycle,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerCycleDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_timeCycle, timecycle.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerCycleDetail> listTimerCycleDetailByTimeCycle(org.semanticwb.process.model.TimeDateExpression timecycle)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerCycleDetail> it=new org.semanticwb.model.GenericIterator(timecycle.getSemanticObject().getModel().listSubjects(swp_timeCycle,timecycle.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TimerCycleDetail> listTimerCycleDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerCycleDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerCycleDetail> listTimerCycleDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerCycleDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TimerCycleDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setTimeCycle(org.semanticwb.process.model.TimeDateExpression value)
    {
        getSemanticObject().setObjectProperty(swp_timeCycle, value.getSemanticObject());
    }

    public void removeTimeCycle()
    {
        getSemanticObject().removeProperty(swp_timeCycle);
    }


    public org.semanticwb.process.model.TimeDateExpression getTimeCycle()
    {
         org.semanticwb.process.model.TimeDateExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_timeCycle);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.TimeDateExpression)obj.createGenericInstance();
         }
         return ret;
    }
}
