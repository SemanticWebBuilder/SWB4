package org.semanticwb.model.base;


public abstract class MetaTagValueBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Countryable,org.semanticwb.model.Localeable
{
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagDef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagDef");
    public static final org.semanticwb.platform.SemanticProperty swb_metadataDef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#metadataDef");
    public static final org.semanticwb.platform.SemanticProperty swb_metaValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#metaValue");
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagValue");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagValue");

    public static class ClassMgr
    {
       /**
       * Returns a list of MetaTagValue for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.MetaTagValue for all models
       * @return Iterator of org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue>(it, true);
        }

        public static org.semanticwb.model.MetaTagValue createMetaTagValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.MetaTagValue.ClassMgr.createMetaTagValue(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.MetaTagValue
       * @param id Identifier for org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.MetaTagValue
       * @return A org.semanticwb.model.MetaTagValue
       */
        public static org.semanticwb.model.MetaTagValue getMetaTagValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MetaTagValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.MetaTagValue
       * @param id Identifier for org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.MetaTagValue
       * @return A org.semanticwb.model.MetaTagValue
       */
        public static org.semanticwb.model.MetaTagValue createMetaTagValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MetaTagValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.MetaTagValue
       * @param id Identifier for org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.MetaTagValue
       */
        public static void removeMetaTagValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.MetaTagValue
       * @param id Identifier for org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.MetaTagValue
       * @return true if the org.semanticwb.model.MetaTagValue exists, false otherwise
       */

        public static boolean hasMetaTagValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMetaTagValue(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.MetaTagValue with a determined MetadataDef
       * @param value MetadataDef of the type org.semanticwb.model.MetaTagDef
       * @param model Model of the org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValueByMetadataDef(org.semanticwb.model.MetaTagDef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_metadataDef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagValue with a determined MetadataDef
       * @param value MetadataDef of the type org.semanticwb.model.MetaTagDef
       * @return Iterator with all the org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValueByMetadataDef(org.semanticwb.model.MetaTagDef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_metadataDef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagValue with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValueByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagValue with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValueByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagValue with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValueByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MetaTagValue with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.MetaTagValue
       */

        public static java.util.Iterator<org.semanticwb.model.MetaTagValue> listMetaTagValueByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MetaTagValueBase.ClassMgr getMetaTagValueClassMgr()
    {
        return new MetaTagValueBase.ClassMgr();
    }

   /**
   * Constructs a MetaTagValueBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MetaTagValue
   */
    public MetaTagValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property MetadataDef
   * @param value MetadataDef to set
   */

    public void setMetadataDef(org.semanticwb.model.MetaTagDef value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_metadataDef, value.getSemanticObject());
        }else
        {
            removeMetadataDef();
        }
    }
   /**
   * Remove the value for MetadataDef property
   */

    public void removeMetadataDef()
    {
        getSemanticObject().removeProperty(swb_metadataDef);
    }

   /**
   * Gets the MetadataDef
   * @return a org.semanticwb.model.MetaTagDef
   */
    public org.semanticwb.model.MetaTagDef getMetadataDef()
    {
         org.semanticwb.model.MetaTagDef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_metadataDef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.MetaTagDef)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Value property
* @return String with the Value
*/
    public String getValue()
    {
        return getSemanticObject().getProperty(swb_metaValue);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(String value)
    {
        getSemanticObject().setProperty(swb_metaValue, value);
    }
   /**
   * Sets the value for the property Country
   * @param value Country to set
   */

    public void setCountry(org.semanticwb.model.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_country, value.getSemanticObject());
        }else
        {
            removeCountry();
        }
    }
   /**
   * Remove the value for Country property
   */

    public void removeCountry()
    {
        getSemanticObject().removeProperty(swb_country);
    }

   /**
   * Gets the Country
   * @return a org.semanticwb.model.Country
   */
    public org.semanticwb.model.Country getCountry()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_country);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Language
   * @param value Language to set
   */

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }
   /**
   * Remove the value for Language property
   */

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

   /**
   * Gets the Language
   * @return a org.semanticwb.model.Language
   */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }
}
