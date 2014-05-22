package org.semanticwb.model.base;


public abstract class RepositoryFileBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.RuleRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Hitable,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Versionable,org.semanticwb.model.Expirable
{
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_repositoryFileDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#repositoryFileDirectory");
    public static final org.semanticwb.platform.SemanticClass swb_RepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RepositoryFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RepositoryFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of RepositoryFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.RepositoryFile for all models
       * @return Iterator of org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile>(it, true);
        }

        public static org.semanticwb.model.RepositoryFile createRepositoryFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.RepositoryFile.ClassMgr.createRepositoryFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.RepositoryFile
       * @param id Identifier for org.semanticwb.model.RepositoryFile
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return A org.semanticwb.model.RepositoryFile
       */
        public static org.semanticwb.model.RepositoryFile getRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.RepositoryFile
       * @param id Identifier for org.semanticwb.model.RepositoryFile
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return A org.semanticwb.model.RepositoryFile
       */
        public static org.semanticwb.model.RepositoryFile createRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.RepositoryFile
       * @param id Identifier for org.semanticwb.model.RepositoryFile
       * @param model Model of the org.semanticwb.model.RepositoryFile
       */
        public static void removeRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.RepositoryFile
       * @param id Identifier for org.semanticwb.model.RepositoryFile
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return true if the org.semanticwb.model.RepositoryFile exists, false otherwise
       */

        public static boolean hasRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRepositoryFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined ActualVersion
       * @param value ActualVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByActualVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_actualVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined ActualVersion
       * @param value ActualVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByActualVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_actualVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined RepositoryFileDirectory
       * @param value RepositoryFileDirectory of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByRepositoryFileDirectory(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_repositoryFileDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined RepositoryFileDirectory
       * @param value RepositoryFileDirectory of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByRepositoryFileDirectory(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_repositoryFileDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined LastVersion
       * @param value LastVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByLastVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_lastVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined LastVersion
       * @param value LastVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByLastVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_lastVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RepositoryFile with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.model.RepositoryFile> listRepositoryFileByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RepositoryFileBase.ClassMgr getRepositoryFileClassMgr()
    {
        return new RepositoryFileBase.ClassMgr();
    }

   /**
   * Constructs a RepositoryFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RepositoryFile
   */
    public RepositoryFileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NotInheritRoleRef property
* @return boolean with the NotInheritRoleRef
*/
    public boolean isNotInheritRoleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritRoleRef);
    }

/**
* Sets the NotInheritRoleRef property
* @param value long with the NotInheritRoleRef
*/
    public void setNotInheritRoleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritRoleRef, value);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   /**
   * Sets the value for the property ActualVersion
   * @param value ActualVersion to set
   */

    public void setActualVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_actualVersion, value.getSemanticObject());
        }else
        {
            removeActualVersion();
        }
    }
   /**
   * Remove the value for ActualVersion property
   */

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

   /**
   * Gets the ActualVersion
   * @return a org.semanticwb.model.VersionInfo
   */
    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the NotInheritUserGroupRef property
* @return boolean with the NotInheritUserGroupRef
*/
    public boolean isNotInheritUserGroupRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritUserGroupRef);
    }

/**
* Sets the NotInheritUserGroupRef property
* @param value long with the NotInheritUserGroupRef
*/
    public void setNotInheritUserGroupRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritUserGroupRef, value);
    }

/**
* Gets the Expiration property
* @return java.util.Date with the Expiration
*/
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
* Sets the Expiration property
* @param value long with the Expiration
*/
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

/**
* Gets the NotInheritRuleRef property
* @return boolean with the NotInheritRuleRef
*/
    public boolean isNotInheritRuleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritRuleRef);
    }

/**
* Sets the NotInheritRuleRef property
* @param value long with the NotInheritRuleRef
*/
    public void setNotInheritRuleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritRuleRef, value);
    }

/**
* Gets the Hits property
* @return long with the Hits
*/
    public long getHits()
    {
        //Override this method in RepositoryFile object
        return getSemanticObject().getLongProperty(swb_hits,false);
    }

/**
* Sets the Hits property
* @param value long with the Hits
*/
    public void setHits(long value)
    {
        //Override this method in RepositoryFile object
        getSemanticObject().setLongProperty(swb_hits, value,false);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Gets all the org.semanticwb.model.RuleRef
   * @return A GenericIterator with all the org.semanticwb.model.RuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

   /**
   * Gets true if has a RuleRef
   * @param value org.semanticwb.model.RuleRef to verify
   * @return true if the org.semanticwb.model.RuleRef exists, false otherwise
   */
    public boolean hasRuleRef(org.semanticwb.model.RuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the RuleRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.RuleRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }
   /**
   * Adds a RuleRef
   * @param value org.semanticwb.model.RuleRef to add
   */

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }
   /**
   * Removes all the RuleRef
   */

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }
   /**
   * Removes a RuleRef
   * @param value org.semanticwb.model.RuleRef to remove
   */

    public void removeRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,value.getSemanticObject());
    }

   /**
   * Gets the RuleRef
   * @return a org.semanticwb.model.RuleRef
   */
    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the MaxHits property
* @return long with the MaxHits
*/
    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(swb_maxHits);
    }

/**
* Sets the MaxHits property
* @param value long with the MaxHits
*/
    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(swb_maxHits, value);
    }
   /**
   * Sets the value for the property RepositoryFileDirectory
   * @param value RepositoryFileDirectory to set
   */

    public void setRepositoryFileDirectory(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_repositoryFileDirectory, value.getSemanticObject());
        }else
        {
            removeRepositoryFileDirectory();
        }
    }
   /**
   * Remove the value for RepositoryFileDirectory property
   */

    public void removeRepositoryFileDirectory()
    {
        getSemanticObject().removeProperty(swb_repositoryFileDirectory);
    }

   /**
   * Gets the RepositoryFileDirectory
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getRepositoryFileDirectory()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_repositoryFileDirectory);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AndEvalUserGroupRef property
* @return boolean with the AndEvalUserGroupRef
*/
    public boolean isAndEvalUserGroupRef()
    {
        return getSemanticObject().getBooleanProperty(swb_andEvalUserGroupRef);
    }

/**
* Sets the AndEvalUserGroupRef property
* @param value long with the AndEvalUserGroupRef
*/
    public void setAndEvalUserGroupRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_andEvalUserGroupRef, value);
    }
   /**
   * Gets all the org.semanticwb.model.UserGroupRef
   * @return A GenericIterator with all the org.semanticwb.model.UserGroupRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

   /**
   * Gets true if has a UserGroupRef
   * @param value org.semanticwb.model.UserGroupRef to verify
   * @return true if the org.semanticwb.model.UserGroupRef exists, false otherwise
   */
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the UserGroupRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.UserGroupRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }
   /**
   * Adds a UserGroupRef
   * @param value org.semanticwb.model.UserGroupRef to add
   */

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }
   /**
   * Removes all the UserGroupRef
   */

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }
   /**
   * Removes a UserGroupRef
   * @param value org.semanticwb.model.UserGroupRef to remove
   */

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
    }

   /**
   * Gets the UserGroupRef
   * @return a org.semanticwb.model.UserGroupRef
   */
    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property LastVersion
   * @param value LastVersion to set
   */

    public void setLastVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_lastVersion, value.getSemanticObject());
        }else
        {
            removeLastVersion();
        }
    }
   /**
   * Remove the value for LastVersion property
   */

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

   /**
   * Gets the LastVersion
   * @return a org.semanticwb.model.VersionInfo
   */
    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AndEvalRoleRef property
* @return boolean with the AndEvalRoleRef
*/
    public boolean isAndEvalRoleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_andEvalRoleRef);
    }

/**
* Sets the AndEvalRoleRef property
* @param value long with the AndEvalRoleRef
*/
    public void setAndEvalRoleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_andEvalRoleRef, value);
    }
   /**
   * Gets all the org.semanticwb.model.RoleRef
   * @return A GenericIterator with all the org.semanticwb.model.RoleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

   /**
   * Gets true if has a RoleRef
   * @param value org.semanticwb.model.RoleRef to verify
   * @return true if the org.semanticwb.model.RoleRef exists, false otherwise
   */
    public boolean hasRoleRef(org.semanticwb.model.RoleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the RoleRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.RoleRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }
   /**
   * Adds a RoleRef
   * @param value org.semanticwb.model.RoleRef to add
   */

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }
   /**
   * Removes all the RoleRef
   */

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }
   /**
   * Removes a RoleRef
   * @param value org.semanticwb.model.RoleRef to remove
   */

    public void removeRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,value.getSemanticObject());
    }

   /**
   * Gets the RoleRef
   * @return a org.semanticwb.model.RoleRef
   */
    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AndEvalRuleRef property
* @return boolean with the AndEvalRuleRef
*/
    public boolean isAndEvalRuleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_andEvalRuleRef);
    }

/**
* Sets the AndEvalRuleRef property
* @param value long with the AndEvalRuleRef
*/
    public void setAndEvalRuleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_andEvalRuleRef, value);
    }
}
