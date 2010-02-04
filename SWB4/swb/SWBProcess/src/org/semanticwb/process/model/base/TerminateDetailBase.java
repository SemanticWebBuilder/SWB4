package org.semanticwb.process.model.base;


public abstract class TerminateDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TerminateDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TerminateDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TerminateDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TerminateDetail> listTerminateDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TerminateDetail> listTerminateDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateDetail>(it, true);
       }

       public static org.semanticwb.process.model.TerminateDetail createTerminateDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TerminateDetail.ClassMgr.createTerminateDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TerminateDetail getTerminateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TerminateDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TerminateDetail createTerminateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TerminateDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTerminateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTerminateDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTerminateDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TerminateDetail> listTerminateDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TerminateDetail> listTerminateDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminateDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TerminateDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
