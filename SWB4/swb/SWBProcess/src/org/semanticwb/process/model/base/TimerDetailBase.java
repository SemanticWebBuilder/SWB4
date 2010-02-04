package org.semanticwb.process.model.base;


public abstract class TimerDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
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
}
