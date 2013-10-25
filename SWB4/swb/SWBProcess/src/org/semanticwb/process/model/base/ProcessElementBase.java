package org.semanticwb.process.model.base;


public abstract class ProcessElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Stores a textual description of a process element
   */
    public static final org.semanticwb.platform.SemanticClass swp_Documentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Documentation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDocumentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasDocumentation");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessElement for all models
       * @return Iterator of org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessElement
       * @param id Identifier for org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       * @return A org.semanticwb.process.model.ProcessElement
       */
        public static org.semanticwb.process.model.ProcessElement getProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessElement
       * @param id Identifier for org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       * @return A org.semanticwb.process.model.ProcessElement
       */
        public static org.semanticwb.process.model.ProcessElement createProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessElement
       * @param id Identifier for org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       */
        public static void removeProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessElement
       * @param id Identifier for org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       * @return true if the org.semanticwb.process.model.ProcessElement exists, false otherwise
       */

        public static boolean hasProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessElement(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessElement with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElementByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessElement with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElementByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessElement> listProcessElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessElementBase.ClassMgr getProcessElementClassMgr()
    {
        return new ProcessElementBase.ClassMgr();
    }

   /**
   * Constructs a ProcessElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessElement
   */
    public ProcessElementBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.process.model.Documentation
   * @return A GenericIterator with all the org.semanticwb.process.model.Documentation
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation> listDocumentations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation>(getSemanticObject().listObjectProperties(swp_hasDocumentation));
    }

   /**
   * Gets true if has a Documentation
   * @param value org.semanticwb.process.model.Documentation to verify
   * @return true if the org.semanticwb.process.model.Documentation exists, false otherwise
   */
    public boolean hasDocumentation(org.semanticwb.process.model.Documentation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDocumentation,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Documentation
   * @param value org.semanticwb.process.model.Documentation to add
   */

    public void addDocumentation(org.semanticwb.process.model.Documentation value)
    {
        getSemanticObject().addObjectProperty(swp_hasDocumentation, value.getSemanticObject());
    }
   /**
   * Removes all the Documentation
   */

    public void removeAllDocumentation()
    {
        getSemanticObject().removeProperty(swp_hasDocumentation);
    }
   /**
   * Removes a Documentation
   * @param value org.semanticwb.process.model.Documentation to remove
   */

    public void removeDocumentation(org.semanticwb.process.model.Documentation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDocumentation,value.getSemanticObject());
    }

   /**
   * Gets the Documentation
   * @return a org.semanticwb.process.model.Documentation
   */
    public org.semanticwb.process.model.Documentation getDocumentation()
    {
         org.semanticwb.process.model.Documentation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDocumentation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Documentation)obj.createGenericInstance();
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
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
