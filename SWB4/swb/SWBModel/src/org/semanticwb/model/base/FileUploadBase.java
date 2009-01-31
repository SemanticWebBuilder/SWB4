package org.semanticwb.model.base;


public class FileUploadBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_filePath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#filePath");
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileFilter");
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileContentType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileContentType");
    public static final org.semanticwb.platform.SemanticClass swbxf_FileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");

    public FileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.FileUpload getFileUpload(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(org.semanticwb.model.FileUpload.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(org.semanticwb.model.FileUpload.class, it, true);
    }

    public static org.semanticwb.model.FileUpload createFileUpload(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFileUpload(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFileUpload(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFileUpload(id, model)!=null);
    }

    public String getFilePath()
    {
        return getSemanticObject().getProperty(swbxf_filePath);
    }

    public void setFilePath(String filePath)
    {
        getSemanticObject().setProperty(swbxf_filePath, filePath);
    }

    public String getFileFilter()
    {
        return getSemanticObject().getProperty(swbxf_fileFilter);
    }

    public void setFileFilter(String fileFilter)
    {
        getSemanticObject().setProperty(swbxf_fileFilter, fileFilter);
    }

    public String getFileContentType()
    {
        return getSemanticObject().getProperty(swbxf_fileContentType);
    }

    public void setFileContentType(String fileContentType)
    {
        getSemanticObject().setProperty(swbxf_fileContentType, fileContentType);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
