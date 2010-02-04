package org.semanticwb.process.model.base;


public abstract class TimerDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TimeDateExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimeDateExpression");
       public static final org.semanticwb.platform.SemanticProperty swp_timeCycle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#timeCycle");
       public static final org.semanticwb.platform.SemanticProperty swp_timeDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#timeDate");
       public static final org.semanticwb.platform.SemanticClass swp_TimerDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail>(it, true);
       }

       public static org.semanticwb.process.model.TimerDetail createTimerDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TimerDetail.ClassMgr.createTimerDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TimerDetail getTimerDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TimerDetail createTimerDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTimerDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTimerDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTimerDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetailByTimeCycle(org.semanticwb.process.model.TimeDateExpression timecycle,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_timeCycle, timecycle.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetailByTimeCycle(org.semanticwb.process.model.TimeDateExpression timecycle)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail> it=new org.semanticwb.model.GenericIterator(timecycle.getSemanticObject().getModel().listSubjects(swp_timeCycle,timecycle.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetailByTimeDate(org.semanticwb.process.model.TimeDateExpression timedate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_timeDate, timedate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetailByTimeDate(org.semanticwb.process.model.TimeDateExpression timedate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail> it=new org.semanticwb.model.GenericIterator(timedate.getSemanticObject().getModel().listSubjects(swp_timeDate,timedate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerDetail> listTimerDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TimerDetailBase(org.semanticwb.platform.SemanticObject base)
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

    public void setTimeDate(org.semanticwb.process.model.TimeDateExpression value)
    {
        getSemanticObject().setObjectProperty(swp_timeDate, value.getSemanticObject());
    }

    public void removeTimeDate()
    {
        getSemanticObject().removeProperty(swp_timeDate);
    }


    public org.semanticwb.process.model.TimeDateExpression getTimeDate()
    {
         org.semanticwb.process.model.TimeDateExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_timeDate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.TimeDateExpression)obj.createGenericInstance();
         }
         return ret;
    }
}
