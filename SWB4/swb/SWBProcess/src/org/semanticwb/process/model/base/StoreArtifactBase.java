package org.semanticwb.process.model.base;


public abstract class StoreArtifactBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryDirectory");
    public static final org.semanticwb.platform.SemanticProperty swp_processDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processDirectory");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessFileTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_processFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processFileTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_processFileName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processFileName");
    public static final org.semanticwb.platform.SemanticProperty swp_repositoryFileId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#repositoryFileId");
    public static final org.semanticwb.platform.SemanticClass swp_StoreArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreArtifact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreArtifact");

    public static class ClassMgr
    {
       /**
       * Returns a list of StoreArtifact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.StoreArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreArtifact> listStoreArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreArtifact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.StoreArtifact for all models
       * @return Iterator of org.semanticwb.process.model.StoreArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreArtifact> listStoreArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreArtifact>(it, true);
        }

        public static org.semanticwb.process.model.StoreArtifact createStoreArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.StoreArtifact.ClassMgr.createStoreArtifact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.StoreArtifact
       * @param id Identifier for org.semanticwb.process.model.StoreArtifact
       * @param model Model of the org.semanticwb.process.model.StoreArtifact
       * @return A org.semanticwb.process.model.StoreArtifact
       */
        public static org.semanticwb.process.model.StoreArtifact getStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StoreArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.StoreArtifact
       * @param id Identifier for org.semanticwb.process.model.StoreArtifact
       * @param model Model of the org.semanticwb.process.model.StoreArtifact
       * @return A org.semanticwb.process.model.StoreArtifact
       */
        public static org.semanticwb.process.model.StoreArtifact createStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StoreArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.StoreArtifact
       * @param id Identifier for org.semanticwb.process.model.StoreArtifact
       * @param model Model of the org.semanticwb.process.model.StoreArtifact
       */
        public static void removeStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.StoreArtifact
       * @param id Identifier for org.semanticwb.process.model.StoreArtifact
       * @param model Model of the org.semanticwb.process.model.StoreArtifact
       * @return true if the org.semanticwb.process.model.StoreArtifact exists, false otherwise
       */

        public static boolean hasStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStoreArtifact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.StoreArtifact with a determined ProcessDirectory
       * @param value ProcessDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.StoreArtifact
       * @return Iterator with all the org.semanticwb.process.model.StoreArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreArtifact> listStoreArtifactByProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreArtifact with a determined ProcessDirectory
       * @param value ProcessDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.StoreArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreArtifact> listStoreArtifactByProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreArtifact with a determined ProcessFileTemplate
       * @param value ProcessFileTemplate of the type org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.StoreArtifact
       * @return Iterator with all the org.semanticwb.process.model.StoreArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreArtifact> listStoreArtifactByProcessFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processFileTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreArtifact with a determined ProcessFileTemplate
       * @param value ProcessFileTemplate of the type org.semanticwb.process.model.ProcessFileTemplate
       * @return Iterator with all the org.semanticwb.process.model.StoreArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreArtifact> listStoreArtifactByProcessFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processFileTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a StoreArtifactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StoreArtifact
   */
    public StoreArtifactBase(org.semanticwb.platform.SemanticObject base)
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
