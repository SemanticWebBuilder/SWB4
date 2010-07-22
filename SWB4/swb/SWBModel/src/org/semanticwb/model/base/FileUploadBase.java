package org.semanticwb.model.base;


public abstract class FileUploadBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileMaxSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileMaxSize");
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileFilter");
    public static final org.semanticwb.platform.SemanticClass swbxf_FileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }

        public static org.semanticwb.model.FileUpload getFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.FileUpload createFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFileUpload(id, model)!=null);
        }
    }

    public FileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public long getFileMaxSize()
    {
        return getSemanticObject().getLongProperty(swbxf_fileMaxSize);
    }

    public void setFileMaxSize(long value)
    {
        getSemanticObject().setLongProperty(swbxf_fileMaxSize, value);
    }

    public String getFileFilter()
    {
        return getSemanticObject().getProperty(swbxf_fileFilter);
    }

    public void setFileFilter(String value)
    {
        getSemanticObject().setProperty(swbxf_fileFilter, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
