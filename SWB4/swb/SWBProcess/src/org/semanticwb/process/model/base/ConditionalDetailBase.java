package org.semanticwb.process.model.base;


public abstract class ConditionalDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Condition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Condition");
       public static final org.semanticwb.platform.SemanticProperty swp_conditionRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#conditionRef");
       public static final org.semanticwb.platform.SemanticClass swp_ConditionalDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionalDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionalDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ConditionalDetail> listConditionalDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ConditionalDetail> listConditionalDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalDetail>(it, true);
       }

       public static org.semanticwb.process.model.ConditionalDetail createConditionalDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ConditionalDetail.ClassMgr.createConditionalDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ConditionalDetail getConditionalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ConditionalDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ConditionalDetail createConditionalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ConditionalDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeConditionalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasConditionalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getConditionalDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ConditionalDetail> listConditionalDetailByConditionRef(org.semanticwb.process.model.Condition conditionref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_conditionRef, conditionref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConditionalDetail> listConditionalDetailByConditionRef(org.semanticwb.process.model.Condition conditionref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalDetail> it=new org.semanticwb.model.GenericIterator(conditionref.getSemanticObject().getModel().listSubjects(swp_conditionRef,conditionref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ConditionalDetail> listConditionalDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConditionalDetail> listConditionalDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ConditionalDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setConditionRef(org.semanticwb.process.model.Condition value)
    {
        getSemanticObject().setObjectProperty(swp_conditionRef, value.getSemanticObject());
    }

    public void removeConditionRef()
    {
        getSemanticObject().removeProperty(swp_conditionRef);
    }


    public org.semanticwb.process.model.Condition getConditionRef()
    {
         org.semanticwb.process.model.Condition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_conditionRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Condition)obj.createGenericInstance();
         }
         return ret;
    }
}
