package org.semanticwb.model.base;


public abstract class FileUploadBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Valor en kbs
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileMaxSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileMaxSize");
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileFilter");
    public static final org.semanticwb.platform.SemanticClass swbxf_FileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");

    public static class ClassMgr
    {
       /**
       * Returns a list of FileUpload for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.FileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FileUpload for all models
       * @return Iterator of org.semanticwb.model.FileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       * @return A org.semanticwb.model.FileUpload
       */
        public static org.semanticwb.model.FileUpload getFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       * @return A org.semanticwb.model.FileUpload
       */
        public static org.semanticwb.model.FileUpload createFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       */
        public static void removeFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       * @return true if the org.semanticwb.model.FileUpload exists, false otherwise
       */

        public static boolean hasFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFileUpload(id, model)!=null);
        }
    }

   /**
   * Constructs a FileUploadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FileUpload
   */
    public FileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the FileMaxSize property
* @return long with the FileMaxSize
*/
    public long getFileMaxSize()
    {
        return getSemanticObject().getLongProperty(swbxf_fileMaxSize);
    }

/**
* Sets the FileMaxSize property
* @param value long with the FileMaxSize
*/
    public void setFileMaxSize(long value)
    {
        getSemanticObject().setLongProperty(swbxf_fileMaxSize, value);
    }

/**
* Gets the FileFilter property
* @return String with the FileFilter
*/
    public String getFileFilter()
    {
        return getSemanticObject().getProperty(swbxf_fileFilter);
    }

/**
* Sets the FileFilter property
* @param value long with the FileFilter
*/
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
