package org.semanticwb.model.base;


public abstract class FlashFileUploadBase extends org.semanticwb.model.FileUpload 
{
    public static final org.semanticwb.platform.SemanticClass swb_FlashFileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashFileUpload");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashFileUpload");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.FlashFileUpload> listFlashFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashFileUpload>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.FlashFileUpload> listFlashFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashFileUpload>(it, true);
        }

        public static org.semanticwb.model.FlashFileUpload getFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashFileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.FlashFileUpload createFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashFileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlashFileUpload(id, model)!=null);
        }
    }

    public FlashFileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
