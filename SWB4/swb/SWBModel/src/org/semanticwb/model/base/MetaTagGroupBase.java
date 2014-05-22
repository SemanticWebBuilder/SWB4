package org.semanticwb.model.base;


public abstract class MetaTagGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode
{
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagDef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagDef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasMetaTagDefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasMetaTagDefInv");
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of MetaTagGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.MetaTagGroup for all models
       * @return Iterator of org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup>(it, true);
        }

        public static org.semanticwb.model.MetaTagGroup createMetaTagGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.MetaTagGroup.ClassMgr.createMetaTagGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.MetaTagGroup
       * @param id Identifier for org.semanticwb.model.MetaTagGroup
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       * @return A org.semanticwb.model.MetaTagGroup
       */
        public static org.semanticwb.model.MetaTagGroup getMetaTagGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MetaTagGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.MetaTagGroup
       * @param id Identifier for org.semanticwb.model.MetaTagGroup
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       * @return A org.semanticwb.model.MetaTagGroup
       */
        public static org.semanticwb.model.MetaTagGroup createMetaTagGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MetaTagGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.MetaTagGroup
       * @param id Identifier for org.semanticwb.model.MetaTagGroup
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       */
        public static void removeMetaTagGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.MetaTagGroup
       * @param id Identifier for org.semanticwb.model.MetaTagGroup
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       * @return true if the org.semanticwb.model.MetaTagGroup exists, false otherwise
       */

        public static boolean hasMetaTagGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMetaTagGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.MetaTagGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       * @return Iterator with all the org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       * @return Iterator with all the org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagGroup with a determined MetaTagDefInv
       * @param value MetaTagDefInv of the type org.semanticwb.model.MetaTagDef
       * @param model Model of the org.semanticwb.model.MetaTagGroup
       * @return Iterator with all the org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroupByMetaTagDefInv(org.semanticwb.model.MetaTagDef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagDefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagGroup with a determined MetaTagDefInv
       * @param value MetaTagDefInv of the type org.semanticwb.model.MetaTagDef
       * @return Iterator with all the org.semanticwb.model.MetaTagGroup
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagGroup> listMetaTagGroupByMetaTagDefInv(org.semanticwb.model.MetaTagDef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagDefInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MetaTagGroupBase.ClassMgr getMetaTagGroupClassMgr()
    {
        return new MetaTagGroupBase.ClassMgr();
    }

   /**
   * Constructs a MetaTagGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MetaTagGroup
   */
    public MetaTagGroupBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.model.MetaTagDef
   * @return A GenericIterator with all the org.semanticwb.model.MetaTagDef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef> listMetaTagDefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagDef>(getSemanticObject().listObjectProperties(swb_hasMetaTagDefInv));
    }

   /**
   * Gets true if has a MetaTagDefInv
   * @param value org.semanticwb.model.MetaTagDef to verify
   * @return true if the org.semanticwb.model.MetaTagDef exists, false otherwise
   */
    public boolean hasMetaTagDefInv(org.semanticwb.model.MetaTagDef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasMetaTagDefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the MetaTagDefInv
   * @return a org.semanticwb.model.MetaTagDef
   */
    public org.semanticwb.model.MetaTagDef getMetaTagDefInv()
    {
         org.semanticwb.model.MetaTagDef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasMetaTagDefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.MetaTagDef)obj.createGenericInstance();
         }
         return ret;
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
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
