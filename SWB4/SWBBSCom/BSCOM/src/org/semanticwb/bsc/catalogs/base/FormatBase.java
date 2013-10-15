package org.semanticwb.bsc.catalogs.base;


public abstract class FormatBase extends org.semanticwb.bsc.catalogs.Catalog implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Countryable,org.semanticwb.model.Localeable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_formatPattern=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#formatPattern");
    public static final org.semanticwb.platform.SemanticClass bsc_Format=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Format");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Format");

    public static class ClassMgr
    {
       /**
       * Returns a list of Format for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Format
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Format> listFormats(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Format>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Format for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Format
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Format> listFormats()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Format>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Format createFormat(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Format.ClassMgr.createFormat(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Format
       * @param id Identifier for org.semanticwb.bsc.catalogs.Format
       * @param model Model of the org.semanticwb.bsc.catalogs.Format
       * @return A org.semanticwb.bsc.catalogs.Format
       */
        public static org.semanticwb.bsc.catalogs.Format getFormat(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Format)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Format
       * @param id Identifier for org.semanticwb.bsc.catalogs.Format
       * @param model Model of the org.semanticwb.bsc.catalogs.Format
       * @return A org.semanticwb.bsc.catalogs.Format
       */
        public static org.semanticwb.bsc.catalogs.Format createFormat(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Format)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Format
       * @param id Identifier for org.semanticwb.bsc.catalogs.Format
       * @param model Model of the org.semanticwb.bsc.catalogs.Format
       */
        public static void removeFormat(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Format
       * @param id Identifier for org.semanticwb.bsc.catalogs.Format
       * @param model Model of the org.semanticwb.bsc.catalogs.Format
       * @return true if the org.semanticwb.bsc.catalogs.Format exists, false otherwise
       */

        public static boolean hasFormat(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormat(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Format with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.bsc.catalogs.Format
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Format
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Format> listFormatByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Format with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Format
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Format> listFormatByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Format with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.bsc.catalogs.Format
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Format
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Format> listFormatByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Format with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Format
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Format> listFormatByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FormatBase.ClassMgr getFormatClassMgr()
    {
        return new FormatBase.ClassMgr();
    }

   /**
   * Constructs a FormatBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Format
   */
    public FormatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

/**
* Gets the FormatPattern property
* @return String with the FormatPattern
*/
    public String getFormatPattern()
    {
        return getSemanticObject().getProperty(bsc_formatPattern);
    }

/**
* Sets the FormatPattern property
* @param value long with the FormatPattern
*/
    public void setFormatPattern(String value)
    {
        getSemanticObject().setProperty(bsc_formatPattern, value);
    }
}
