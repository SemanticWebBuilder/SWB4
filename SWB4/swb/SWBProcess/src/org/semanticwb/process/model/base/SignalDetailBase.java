package org.semanticwb.process.model.base;


public abstract class SignalDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Signal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Signal");
       public static final org.semanticwb.platform.SemanticProperty swp_signalRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#signalRef");
       public static final org.semanticwb.platform.SemanticClass swp_SignalDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SignalDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SignalDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.SignalDetail> listSignalDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.SignalDetail> listSignalDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalDetail>(it, true);
       }

       public static org.semanticwb.process.model.SignalDetail createSignalDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.SignalDetail.ClassMgr.createSignalDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.SignalDetail getSignalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SignalDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.SignalDetail createSignalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SignalDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSignalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSignalDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSignalDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.SignalDetail> listSignalDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SignalDetail> listSignalDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SignalDetail> listSignalDetailBySignalRef(org.semanticwb.process.model.Signal signalref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_signalRef, signalref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SignalDetail> listSignalDetailBySignalRef(org.semanticwb.process.model.Signal signalref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalDetail> it=new org.semanticwb.model.GenericIterator(signalref.getSemanticObject().getModel().listSubjects(swp_signalRef,signalref.getSemanticObject()));
       return it;
   }
    }

    public SignalDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSignalRef(org.semanticwb.process.model.Signal value)
    {
        getSemanticObject().setObjectProperty(swp_signalRef, value.getSemanticObject());
    }

    public void removeSignalRef()
    {
        getSemanticObject().removeProperty(swp_signalRef);
    }


    public org.semanticwb.process.model.Signal getSignalRef()
    {
         org.semanticwb.process.model.Signal ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_signalRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Signal)obj.createGenericInstance();
         }
         return ret;
    }
}
