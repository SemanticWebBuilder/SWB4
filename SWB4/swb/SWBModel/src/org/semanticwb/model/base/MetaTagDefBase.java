package org.semanticwb.model.base;


public abstract class MetaTagDefBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_metaInherit=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#metaInherit");
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagValue");
    public static final org.semanticwb.platform.SemanticProperty swb_hasMetaTagsValueInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasMetaTagsValueInv");
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_metaTagGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#metaTagGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_metaMaxSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#metaMaxSize");
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagDef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagDef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagDef");

    public static class ClassMgr
    {
       /**
       * Returns a list of MetaTagDef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.MetaTagDef for all models
       * @return Iterator of org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.MetaTagDef
       * @param id Identifier for org.semanticwb.model.MetaTagDef
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return A org.semanticwb.model.MetaTagDef
       */
        public static org.semanticwb.model.MetaTagDef getMetaTagDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MetaTagDef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.MetaTagDef
       * @param id Identifier for org.semanticwb.model.MetaTagDef
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return A org.semanticwb.model.MetaTagDef
       */
        public static org.semanticwb.model.MetaTagDef createMetaTagDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MetaTagDef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.MetaTagDef
       * @param id Identifier for org.semanticwb.model.MetaTagDef
       * @param model Model of the org.semanticwb.model.MetaTagDef
       */
        public static void removeMetaTagDef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.MetaTagDef
       * @param id Identifier for org.semanticwb.model.MetaTagDef
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return true if the org.semanticwb.model.MetaTagDef exists, false otherwise
       */

        public static boolean hasMetaTagDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMetaTagDef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined MetaTagValue
       * @param value MetaTagValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByMetaTagValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValueInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined MetaTagValue
       * @param value MetaTagValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByMetaTagValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValueInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined MetaTagDef
       * @param value MetaTagDef of the type org.semanticwb.model.MetaTagGroup
       * @param model Model of the org.semanticwb.model.MetaTagDef
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByMetaTagDef(org.semanticwb.model.MetaTagGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_metaTagGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagDef with a determined MetaTagDef
       * @param value MetaTagDef of the type org.semanticwb.model.MetaTagGroup
       * @return Iterator with all the org.semanticwb.model.MetaTagDef
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagDef> listMetaTagDefByMetaTagDef(org.semanticwb.model.MetaTagGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_metaTagGroup,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MetaTagDefBase.ClassMgr getMetaTagDefClassMgr()
    {
        return new MetaTagDefBase.ClassMgr();
    }

   /**
   * Constructs a MetaTagDefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MetaTagDef
   */
    public MetaTagDefBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Inherit property
* @return boolean with the Inherit
*/
    public boolean isInherit()
    {
        return getSemanticObject().getBooleanProperty(swb_metaInherit);
    }

/**
* Sets the Inherit property
* @param value long with the Inherit
*/
    public void setInherit(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_metaInherit, value);
    }
   /**
   * Gets all the org.semanticwb.model.MetaTagValue
   * @return A GenericIterator with all the org.semanticwb.model.MetaTagValue
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> listMetaTagValues()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue>(getSemanticObject().listObjectProperties(swb_hasMetaTagsValueInv));
    }

   /**
   * Gets true if has a MetaTagValue
   * @param value org.semanticwb.model.MetaTagValue to verify
   * @return true if the org.semanticwb.model.MetaTagValue exists, false otherwise
   */
    public boolean hasMetaTagValue(org.semanticwb.model.MetaTagValue value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasMetaTagsValueInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the MetaTagValue
   * @return a org.semanticwb.model.MetaTagValue
   */
    public org.semanticwb.model.MetaTagValue getMetaTagValue()
    {
         org.semanticwb.model.MetaTagValue ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasMetaTagsValueInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.MetaTagValue)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.model.MetaTagGroup
   * @return A GenericIterator with all the org.semanticwb.model.MetaTagGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> listMetaTagDefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup>(getSemanticObject().listObjectProperties(swb_metaTagGroup));
    }

   /**
   * Gets true if has a MetaTagDef
   * @param value org.semanticwb.model.MetaTagGroup to verify
   * @return true if the org.semanticwb.model.MetaTagGroup exists, false otherwise
   */
    public boolean hasMetaTagDef(org.semanticwb.model.MetaTagGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_metaTagGroup,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a MetaTagDef
   * @param value org.semanticwb.model.MetaTagGroup to add
   */

    public void addMetaTagDef(org.semanticwb.model.MetaTagGroup value)
    {
        getSemanticObject().addObjectProperty(swb_metaTagGroup, value.getSemanticObject());
    }
   /**
   * Removes all the MetaTagDef
   */

    public void removeAllMetaTagDef()
    {
        getSemanticObject().removeProperty(swb_metaTagGroup);
    }
   /**
   * Removes a MetaTagDef
   * @param value org.semanticwb.model.MetaTagGroup to remove
   */

    public void removeMetaTagDef(org.semanticwb.model.MetaTagGroup value)
    {
        getSemanticObject().removeObjectProperty(swb_metaTagGroup,value.getSemanticObject());
    }

   /**
   * Gets the MetaTagDef
   * @return a org.semanticwb.model.MetaTagGroup
   */
    public org.semanticwb.model.MetaTagGroup getMetaTagDef()
    {
         org.semanticwb.model.MetaTagGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_metaTagGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.MetaTagGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the MaxSize property
* @return int with the MaxSize
*/
    public int getMaxSize()
    {
        return getSemanticObject().getIntProperty(swb_metaMaxSize);
    }

/**
* Sets the MaxSize property
* @param value long with the MaxSize
*/
    public void setMaxSize(int value)
    {
        getSemanticObject().setIntProperty(swb_metaMaxSize, value);
    }
}
