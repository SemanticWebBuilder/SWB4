package org.semanticwb.process.model.base;


public abstract class StoreRepositoryFileBase extends org.semanticwb.process.model.ProcessService implements org.semanticwb.process.model.StoreRepositoryFileable
{
    public static final org.semanticwb.platform.SemanticProperty swp_repositoryFileVarName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#repositoryFileVarName");
    public static final org.semanticwb.platform.SemanticClass swp_StoreRepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of StoreRepositoryFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.StoreRepositoryFile for all models
       * @return Iterator of org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile>(it, true);
        }

        public static org.semanticwb.process.model.StoreRepositoryFile createStoreRepositoryFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.StoreRepositoryFile.ClassMgr.createStoreRepositoryFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return A org.semanticwb.process.model.StoreRepositoryFile
       */
        public static org.semanticwb.process.model.StoreRepositoryFile getStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StoreRepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return A org.semanticwb.process.model.StoreRepositoryFile
       */
        public static org.semanticwb.process.model.StoreRepositoryFile createStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StoreRepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       */
        public static void removeStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return true if the org.semanticwb.process.model.StoreRepositoryFile exists, false otherwise
       */

        public static boolean hasStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStoreRepositoryFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined ProcessDirectory
       * @param value ProcessDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined ProcessDirectory
       * @param value ProcessDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a StoreRepositoryFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StoreRepositoryFile
   */
    public StoreRepositoryFileBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Varname property
* @return String with the Varname
*/
    public String getVarname()
    {
        return getSemanticObject().getProperty(swp_repositoryFileVarName);
    }

/**
* Sets the Varname property
* @param value long with the Varname
*/
    public void setVarname(String value)
    {
        getSemanticObject().setProperty(swp_repositoryFileVarName, value);
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
