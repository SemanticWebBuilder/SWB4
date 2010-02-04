package org.semanticwb.process.model.base;


public abstract class CompensateDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
       public static final org.semanticwb.platform.SemanticProperty swp_activityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#activityRef");
       public static final org.semanticwb.platform.SemanticClass swp_CompensateDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensateDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensateDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.CompensateDetail> listCompensateDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensateDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.CompensateDetail> listCompensateDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensateDetail>(it, true);
       }

       public static org.semanticwb.process.model.CompensateDetail createCompensateDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.CompensateDetail.ClassMgr.createCompensateDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.CompensateDetail getCompensateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.CompensateDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.CompensateDetail createCompensateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.CompensateDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeCompensateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasCompensateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getCompensateDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.CompensateDetail> listCompensateDetailByActivityRef(org.semanticwb.process.model.Activity activityref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensateDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_activityRef, activityref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.CompensateDetail> listCompensateDetailByActivityRef(org.semanticwb.process.model.Activity activityref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensateDetail> it=new org.semanticwb.model.GenericIterator(activityref.getSemanticObject().getModel().listSubjects(swp_activityRef,activityref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.CompensateDetail> listCompensateDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensateDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.CompensateDetail> listCompensateDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensateDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public CompensateDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setActivityRef(org.semanticwb.process.model.Activity value)
    {
        getSemanticObject().setObjectProperty(swp_activityRef, value.getSemanticObject());
    }

    public void removeActivityRef()
    {
        getSemanticObject().removeProperty(swp_activityRef);
    }


    public org.semanticwb.process.model.Activity getActivityRef()
    {
         org.semanticwb.process.model.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_activityRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Activity)obj.createGenericInstance();
         }
         return ret;
    }
}
