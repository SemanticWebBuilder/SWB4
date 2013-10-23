package org.semanticwb.model.base;


   /**
   * Objeto utilizado para identificar una version de algun componente 
   */
public abstract class VersionInfoBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_versionLockedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionLockedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_versionValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionValue");
    public static final org.semanticwb.platform.SemanticProperty swb_versionComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionComment");
    public static final org.semanticwb.platform.SemanticProperty swb_versionAuthorized=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionAuthorized");
    public static final org.semanticwb.platform.SemanticProperty swb_versionFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionFile");
    public static final org.semanticwb.platform.SemanticProperty swb_versionNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionNumber");
   /**
   * Objeto utilizado para identificar una version de algun componente
   */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_nextVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#nextVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_previousVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#previousVersion");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");

    public static class ClassMgr
    {
       /**
       * Returns a list of VersionInfo for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.VersionInfo for all models
       * @return Iterator of org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo>(it, true);
        }

        public static org.semanticwb.model.VersionInfo createVersionInfo(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.VersionInfo
       * @param id Identifier for org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return A org.semanticwb.model.VersionInfo
       */
        public static org.semanticwb.model.VersionInfo getVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.VersionInfo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.VersionInfo
       * @param id Identifier for org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return A org.semanticwb.model.VersionInfo
       */
        public static org.semanticwb.model.VersionInfo createVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.VersionInfo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.VersionInfo
       * @param id Identifier for org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.VersionInfo
       */
        public static void removeVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.VersionInfo
       * @param id Identifier for org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return true if the org.semanticwb.model.VersionInfo exists, false otherwise
       */

        public static boolean hasVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVersionInfo(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined VersionLockedBy
       * @param value VersionLockedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByVersionLockedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_versionLockedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined VersionLockedBy
       * @param value VersionLockedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByVersionLockedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_versionLockedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined NextVersion
       * @param value NextVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByNextVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_nextVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined NextVersion
       * @param value NextVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByNextVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_nextVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined PreviousVersion
       * @param value PreviousVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByPreviousVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_previousVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.VersionInfo with a determined PreviousVersion
       * @param value PreviousVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.model.VersionInfo
       */

        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByPreviousVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_previousVersion,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static VersionInfoBase.ClassMgr getVersionInfoClassMgr()
    {
        return new VersionInfoBase.ClassMgr();
    }

   /**
   * Constructs a VersionInfoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the VersionInfo
   */
    public VersionInfoBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
   * Sets the value for the property VersionLockedBy
   * @param value VersionLockedBy to set
   */

    public void setVersionLockedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_versionLockedBy, value.getSemanticObject());
        }else
        {
            removeVersionLockedBy();
        }
    }
   /**
   * Remove the value for VersionLockedBy property
   */

    public void removeVersionLockedBy()
    {
        getSemanticObject().removeProperty(swb_versionLockedBy);
    }

   /**
   * Gets the VersionLockedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getVersionLockedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_versionLockedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the VersionValue property
* @return String with the VersionValue
*/
    public String getVersionValue()
    {
        return getSemanticObject().getProperty(swb_versionValue);
    }

/**
* Sets the VersionValue property
* @param value long with the VersionValue
*/
    public void setVersionValue(String value)
    {
        getSemanticObject().setProperty(swb_versionValue, value);
    }

/**
* Gets the VersionComment property
* @return String with the VersionComment
*/
    public String getVersionComment()
    {
        return getSemanticObject().getProperty(swb_versionComment);
    }

/**
* Sets the VersionComment property
* @param value long with the VersionComment
*/
    public void setVersionComment(String value)
    {
        getSemanticObject().setProperty(swb_versionComment, value);
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
* Gets the VersionAuthorized property
* @return boolean with the VersionAuthorized
*/
    public boolean isVersionAuthorized()
    {
        return getSemanticObject().getBooleanProperty(swb_versionAuthorized);
    }

/**
* Sets the VersionAuthorized property
* @param value long with the VersionAuthorized
*/
    public void setVersionAuthorized(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_versionAuthorized, value);
    }

/**
* Gets the VersionFile property
* @return String with the VersionFile
*/
    public String getVersionFile()
    {
        return getSemanticObject().getProperty(swb_versionFile);
    }

/**
* Sets the VersionFile property
* @param value long with the VersionFile
*/
    public void setVersionFile(String value)
    {
        getSemanticObject().setProperty(swb_versionFile, value);
    }

/**
* Gets the VersionNumber property
* @return int with the VersionNumber
*/
    public int getVersionNumber()
    {
        return getSemanticObject().getIntProperty(swb_versionNumber);
    }

/**
* Sets the VersionNumber property
* @param value long with the VersionNumber
*/
    public void setVersionNumber(int value)
    {
        getSemanticObject().setIntProperty(swb_versionNumber, value);
    }
   /**
   * Sets the value for the property NextVersion
   * @param value NextVersion to set
   */

    public void setNextVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_nextVersion, value.getSemanticObject());
        }else
        {
            removeNextVersion();
        }
    }
   /**
   * Remove the value for NextVersion property
   */

    public void removeNextVersion()
    {
        getSemanticObject().removeProperty(swb_nextVersion);
    }

   /**
   * Gets the NextVersion
   * @return a org.semanticwb.model.VersionInfo
   */
    public org.semanticwb.model.VersionInfo getNextVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_nextVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property PreviousVersion
   * @param value PreviousVersion to set
   */

    public void setPreviousVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_previousVersion, value.getSemanticObject());
        }else
        {
            removePreviousVersion();
        }
    }
   /**
   * Remove the value for PreviousVersion property
   */

    public void removePreviousVersion()
    {
        getSemanticObject().removeProperty(swb_previousVersion);
    }

   /**
   * Gets the PreviousVersion
   * @return a org.semanticwb.model.VersionInfo
   */
    public org.semanticwb.model.VersionInfo getPreviousVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_previousVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
