package org.semanticwb.process.model.base;


public abstract class CompensationDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
       public static final org.semanticwb.platform.SemanticProperty swp_activityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#activityRef");
       public static final org.semanticwb.platform.SemanticClass swp_CompensationDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensationDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensationDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.CompensationDetail> listCompensationDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.CompensationDetail> listCompensationDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationDetail>(it, true);
       }

       public static org.semanticwb.process.model.CompensationDetail createCompensationDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.CompensationDetail.ClassMgr.createCompensationDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.CompensationDetail getCompensationDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.CompensationDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.CompensationDetail createCompensationDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.CompensationDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeCompensationDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasCompensationDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getCompensationDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.CompensationDetail> listCompensationDetailByActivityRef(org.semanticwb.process.model.Activity activityref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_activityRef, activityref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.CompensationDetail> listCompensationDetailByActivityRef(org.semanticwb.process.model.Activity activityref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationDetail> it=new org.semanticwb.model.GenericIterator(activityref.getSemanticObject().getModel().listSubjects(swp_activityRef,activityref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.CompensationDetail> listCompensationDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.CompensationDetail> listCompensationDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public CompensationDetailBase(org.semanticwb.platform.SemanticObject base)
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
