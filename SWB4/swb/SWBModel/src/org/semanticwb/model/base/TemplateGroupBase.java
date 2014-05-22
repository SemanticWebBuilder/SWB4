package org.semanticwb.model.base;


   /**
   * Objeto que define un Grupo de plantillas 
   */
public abstract class TemplateGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable
{
   /**
   * Las Plantillas son documentos HTML que sirven de base a SemanticWebBuilder para poder mostrar el "look & feel" del sitio, así como la distribución de todos los elementos en la pagina.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_hasGroupedTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasGroupedTemplate");
   /**
   * Objeto que define un Grupo de plantillas
   */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of TemplateGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.TemplateGroup for all models
       * @return Iterator of org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup>(it, true);
        }

        public static org.semanticwb.model.TemplateGroup createTemplateGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.TemplateGroup
       * @param id Identifier for org.semanticwb.model.TemplateGroup
       * @param model Model of the org.semanticwb.model.TemplateGroup
       * @return A org.semanticwb.model.TemplateGroup
       */
        public static org.semanticwb.model.TemplateGroup getTemplateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.TemplateGroup
       * @param id Identifier for org.semanticwb.model.TemplateGroup
       * @param model Model of the org.semanticwb.model.TemplateGroup
       * @return A org.semanticwb.model.TemplateGroup
       */
        public static org.semanticwb.model.TemplateGroup createTemplateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.TemplateGroup
       * @param id Identifier for org.semanticwb.model.TemplateGroup
       * @param model Model of the org.semanticwb.model.TemplateGroup
       */
        public static void removeTemplateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.TemplateGroup
       * @param id Identifier for org.semanticwb.model.TemplateGroup
       * @param model Model of the org.semanticwb.model.TemplateGroup
       * @return true if the org.semanticwb.model.TemplateGroup exists, false otherwise
       */

        public static boolean hasTemplateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.TemplateGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.TemplateGroup
       * @return Iterator with all the org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.TemplateGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.TemplateGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.TemplateGroup
       * @return Iterator with all the org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.TemplateGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.TemplateGroup with a determined Template
       * @param value Template of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.model.TemplateGroup
       * @return Iterator with all the org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroupByTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasGroupedTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.TemplateGroup with a determined Template
       * @param value Template of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.model.TemplateGroup
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroupByTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasGroupedTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TemplateGroupBase.ClassMgr getTemplateGroupClassMgr()
    {
        return new TemplateGroupBase.ClassMgr();
    }

   /**
   * Constructs a TemplateGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TemplateGroup
   */
    public TemplateGroupBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.model.Template
   * @return A GenericIterator with all the org.semanticwb.model.Template
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(getSemanticObject().listObjectProperties(swb_hasGroupedTemplate));
    }

   /**
   * Gets true if has a Template
   * @param value org.semanticwb.model.Template to verify
   * @return true if the org.semanticwb.model.Template exists, false otherwise
   */
    public boolean hasTemplate(org.semanticwb.model.Template value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasGroupedTemplate,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Template
   * @return a org.semanticwb.model.Template
   */
    public org.semanticwb.model.Template getTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasGroupedTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
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
