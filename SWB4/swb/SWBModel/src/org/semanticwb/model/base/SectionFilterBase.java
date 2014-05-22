package org.semanticwb.model.base;


   /**
   * Superclase de filtrado por secciones para recursos y usuarios 
   */
public abstract class SectionFilterBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.XMLable,org.semanticwb.model.Traceable
{
   /**
   * Superclase de filtrado por secciones para recursos y usuarios
   */
    public static final org.semanticwb.platform.SemanticClass swb_SectionFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SectionFilter");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SectionFilter");

    public static class ClassMgr
    {
       /**
       * Returns a list of SectionFilter for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SectionFilter
       */

        public static java.util.Iterator<org.semanticwb.model.SectionFilter> listSectionFilters(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SectionFilter>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SectionFilter for all models
       * @return Iterator of org.semanticwb.model.SectionFilter
       */

        public static java.util.Iterator<org.semanticwb.model.SectionFilter> listSectionFilters()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SectionFilter>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SectionFilter
       * @param id Identifier for org.semanticwb.model.SectionFilter
       * @param model Model of the org.semanticwb.model.SectionFilter
       * @return A org.semanticwb.model.SectionFilter
       */
        public static org.semanticwb.model.SectionFilter getSectionFilter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SectionFilter)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SectionFilter
       * @param id Identifier for org.semanticwb.model.SectionFilter
       * @param model Model of the org.semanticwb.model.SectionFilter
       * @return A org.semanticwb.model.SectionFilter
       */
        public static org.semanticwb.model.SectionFilter createSectionFilter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SectionFilter)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SectionFilter
       * @param id Identifier for org.semanticwb.model.SectionFilter
       * @param model Model of the org.semanticwb.model.SectionFilter
       */
        public static void removeSectionFilter(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SectionFilter
       * @param id Identifier for org.semanticwb.model.SectionFilter
       * @param model Model of the org.semanticwb.model.SectionFilter
       * @return true if the org.semanticwb.model.SectionFilter exists, false otherwise
       */

        public static boolean hasSectionFilter(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSectionFilter(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.SectionFilter with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.SectionFilter
       * @return Iterator with all the org.semanticwb.model.SectionFilter
       */

        public static java.util.Iterator<org.semanticwb.model.SectionFilter> listSectionFilterByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SectionFilter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.SectionFilter with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.SectionFilter
       */

        public static java.util.Iterator<org.semanticwb.model.SectionFilter> listSectionFilterByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SectionFilter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.SectionFilter with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.SectionFilter
       * @return Iterator with all the org.semanticwb.model.SectionFilter
       */

        public static java.util.Iterator<org.semanticwb.model.SectionFilter> listSectionFilterByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SectionFilter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.SectionFilter with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.SectionFilter
       */

        public static java.util.Iterator<org.semanticwb.model.SectionFilter> listSectionFilterByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SectionFilter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SectionFilterBase.ClassMgr getSectionFilterClassMgr()
    {
        return new SectionFilterBase.ClassMgr();
    }

   /**
   * Constructs a SectionFilterBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SectionFilter
   */
    public SectionFilterBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Xml property
* @return String with the Xml
*/
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

/**
* Sets the Xml property
* @param value long with the Xml
*/
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }
}
