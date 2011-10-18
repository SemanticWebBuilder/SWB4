package org.semanticwb.process.schema.base;


public abstract class FileBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swp_fileValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fileValue");
    public static final org.semanticwb.platform.SemanticClass swps_File=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#File");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#File");

    public static class ClassMgr
    {
       /**
       * Returns a list of File for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.File
       */

        public static java.util.Iterator<org.semanticwb.process.schema.File> listFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.File>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.File for all models
       * @return Iterator of org.semanticwb.process.schema.File
       */

        public static java.util.Iterator<org.semanticwb.process.schema.File> listFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.File>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.File
       * @param id Identifier for org.semanticwb.process.schema.File
       * @param model Model of the org.semanticwb.process.schema.File
       * @return A org.semanticwb.process.schema.File
       */
        public static org.semanticwb.process.schema.File getFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.File)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.File
       * @param id Identifier for org.semanticwb.process.schema.File
       * @param model Model of the org.semanticwb.process.schema.File
       * @return A org.semanticwb.process.schema.File
       */
        public static org.semanticwb.process.schema.File createFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.File)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.File
       * @param id Identifier for org.semanticwb.process.schema.File
       * @param model Model of the org.semanticwb.process.schema.File
       */
        public static void removeFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.File
       * @param id Identifier for org.semanticwb.process.schema.File
       * @param model Model of the org.semanticwb.process.schema.File
       * @return true if the org.semanticwb.process.schema.File exists, false otherwise
       */

        public static boolean hasFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFile(id, model)!=null);
        }
    }

   /**
   * Constructs a FileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the File
   */
    public FileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the FileValue property
* @return String with the FileValue
*/
    public String getFileValue()
    {
        return getSemanticObject().getProperty(swp_fileValue);
    }

/**
* Sets the FileValue property
* @param value long with the FileValue
*/
    public void setFileValue(String value)
    {
        getSemanticObject().setProperty(swp_fileValue, value);
    }
}
