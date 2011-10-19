package org.semanticwb.process.model.base;


public abstract class TransformRepositoryFileBase extends org.semanticwb.process.model.ProcessService implements org.semanticwb.process.model.StoreRepositoryFileable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessFileTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_processFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processFileTemplate");
    public static final org.semanticwb.platform.SemanticClass swp_TransformRepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransformRepositoryFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransformRepositoryFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of TransformRepositoryFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.TransformRepositoryFile for all models
       * @return Iterator of org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile>(it, true);
        }

        public static org.semanticwb.process.model.TransformRepositoryFile createTransformRepositoryFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TransformRepositoryFile.ClassMgr.createTransformRepositoryFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return A org.semanticwb.process.model.TransformRepositoryFile
       */
        public static org.semanticwb.process.model.TransformRepositoryFile getTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TransformRepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return A org.semanticwb.process.model.TransformRepositoryFile
       */
        public static org.semanticwb.process.model.TransformRepositoryFile createTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TransformRepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       */
        public static void removeTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return true if the org.semanticwb.process.model.TransformRepositoryFile exists, false otherwise
       */

        public static boolean hasTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTransformRepositoryFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ProcessDirectory
       * @param value ProcessDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ProcessDirectory
       * @param value ProcessDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ProcessFileTemplate
       * @param value ProcessFileTemplate of the type org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByProcessFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processFileTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ProcessFileTemplate
       * @param value ProcessFileTemplate of the type org.semanticwb.process.model.ProcessFileTemplate
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByProcessFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processFileTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a TransformRepositoryFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TransformRepositoryFile
   */
    public TransformRepositoryFileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ProcessDirectory
   * @param value ProcessDirectory to set
   */

    public void setProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processDirectory, value.getSemanticObject());
        }else
        {
            removeProcessDirectory();
        }
    }
   /**
   * Remove the value for ProcessDirectory property
   */

    public void removeProcessDirectory()
    {
        getSemanticObject().removeProperty(swp_processDirectory);
    }

   /**
   * Gets the ProcessDirectory
   * @return a org.semanticwb.process.model.RepositoryDirectory
   */
    public org.semanticwb.process.model.RepositoryDirectory getProcessDirectory()
    {
         org.semanticwb.process.model.RepositoryDirectory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processDirectory);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.RepositoryDirectory)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessFileTemplate
   * @param value ProcessFileTemplate to set
   */

    public void setProcessFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processFileTemplate, value.getSemanticObject());
        }else
        {
            removeProcessFileTemplate();
        }
    }
   /**
   * Remove the value for ProcessFileTemplate property
   */

    public void removeProcessFileTemplate()
    {
        getSemanticObject().removeProperty(swp_processFileTemplate);
    }

   /**
   * Gets the ProcessFileTemplate
   * @return a org.semanticwb.process.model.ProcessFileTemplate
   */
    public org.semanticwb.process.model.ProcessFileTemplate getProcessFileTemplate()
    {
         org.semanticwb.process.model.ProcessFileTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processFileTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessFileTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ProcessFileName property
* @return String with the ProcessFileName
*/
    public String getProcessFileName()
    {
        return getSemanticObject().getProperty(swp_processFileName);
    }

/**
* Sets the ProcessFileName property
* @param value long with the ProcessFileName
*/
    public void setProcessFileName(String value)
    {
        getSemanticObject().setProperty(swp_processFileName, value);
    }

/**
* Gets the RepositoryFileId property
* @return String with the RepositoryFileId
*/
    public String getRepositoryFileId()
    {
        return getSemanticObject().getProperty(swp_repositoryFileId);
    }

/**
* Sets the RepositoryFileId property
* @param value long with the RepositoryFileId
*/
    public void setRepositoryFileId(String value)
    {
        getSemanticObject().setProperty(swp_repositoryFileId, value);
    }
}
