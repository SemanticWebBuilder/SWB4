package org.semanticwb.process.model.base;


public abstract class CancelDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_CancelDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CancelDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CancelDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.CancelDetail> listCancelDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CancelDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.CancelDetail> listCancelDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CancelDetail>(it, true);
       }

       public static org.semanticwb.process.model.CancelDetail getCancelDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.CancelDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.CancelDetail createCancelDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.CancelDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeCancelDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasCancelDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getCancelDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.CancelDetail> listCancelDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CancelDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.CancelDetail> listCancelDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CancelDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public CancelDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
