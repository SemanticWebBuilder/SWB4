package org.semanticwb.process.model.base;


public abstract class ErrorDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_errorCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#errorCode");
       public static final org.semanticwb.platform.SemanticClass swp_ErrorDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ErrorDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ErrorDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ErrorDetail> listErrorDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ErrorDetail> listErrorDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorDetail>(it, true);
       }

       public static org.semanticwb.process.model.ErrorDetail createErrorDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ErrorDetail.ClassMgr.createErrorDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ErrorDetail getErrorDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ErrorDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ErrorDetail createErrorDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ErrorDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeErrorDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasErrorDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getErrorDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ErrorDetail> listErrorDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ErrorDetail> listErrorDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ErrorDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getErrorCode()
    {
        return getSemanticObject().getProperty(swp_errorCode);
    }

    public void setErrorCode(String value)
    {
        getSemanticObject().setProperty(swp_errorCode, value);
    }
}
