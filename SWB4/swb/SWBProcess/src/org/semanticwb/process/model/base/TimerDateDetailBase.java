package org.semanticwb.process.model.base;


public abstract class TimerDateDetailBase extends org.semanticwb.process.model.TimerDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TimeDateExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimeDateExpression");
       public static final org.semanticwb.platform.SemanticProperty swp_timeDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#timeDate");
       public static final org.semanticwb.platform.SemanticClass swp_TimerDateDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerDateDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerDateDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TimerDateDetail> listTimerDateDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDateDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TimerDateDetail> listTimerDateDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDateDetail>(it, true);
       }

       public static org.semanticwb.process.model.TimerDateDetail createTimerDateDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TimerDateDetail.ClassMgr.createTimerDateDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TimerDateDetail getTimerDateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerDateDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TimerDateDetail createTimerDateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimerDateDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTimerDateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTimerDateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTimerDateDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TimerDateDetail> listTimerDateDetailByTimeDate(org.semanticwb.process.model.TimeDateExpression timedate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDateDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_timeDate, timedate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerDateDetail> listTimerDateDetailByTimeDate(org.semanticwb.process.model.TimeDateExpression timedate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDateDetail> it=new org.semanticwb.model.GenericIterator(timedate.getSemanticObject().getModel().listSubjects(swp_timeDate,timedate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.TimerDateDetail> listTimerDateDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDateDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimerDateDetail> listTimerDateDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerDateDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TimerDateDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
