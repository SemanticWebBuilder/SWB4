package org.semanticwb.bsc.tracing.base;


public abstract class SeriesBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.bsc.Committable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#value");
    public static final org.semanticwb.platform.SemanticProperty bsc_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#index");
    public static final org.semanticwb.platform.SemanticClass bsc_Format=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Format");
    public static final org.semanticwb.platform.SemanticProperty bsc_format=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#format");
   /**
   * Persiste los atributos de un indicador
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
    public static final org.semanticwb.platform.SemanticProperty bsc_indicatorInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#indicatorInv");
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");
    public static final org.semanticwb.platform.SemanticProperty bsc_measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#measure");
    public static final org.semanticwb.platform.SemanticClass bsc_Series=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Series");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Series");

    public static class ClassMgr
    {
       /**
       * Returns a list of Series for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSerieses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Series for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSerieses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Series createSeries(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Series.ClassMgr.createSeries(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Series
       * @param id Identifier for org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return A org.semanticwb.bsc.tracing.Series
       */
        public static org.semanticwb.bsc.tracing.Series getSeries(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Series)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Series
       * @param id Identifier for org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return A org.semanticwb.bsc.tracing.Series
       */
        public static org.semanticwb.bsc.tracing.Series createSeries(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Series)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Series
       * @param id Identifier for org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       */
        public static void removeSeries(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Series
       * @param id Identifier for org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return true if the org.semanticwb.bsc.tracing.Series exists, false otherwise
       */

        public static boolean hasSeries(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSeries(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Format
       * @param value Format of the type org.semanticwb.bsc.catalogs.Format
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByFormat(org.semanticwb.bsc.catalogs.Format value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_format, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Format
       * @param value Format of the type org.semanticwb.bsc.catalogs.Format
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByFormat(org.semanticwb.bsc.catalogs.Format value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_format,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Indicator
       * @param value Indicator of the type org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByIndicator(org.semanticwb.bsc.element.Indicator value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_indicatorInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Indicator
       * @param value Indicator of the type org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByIndicator(org.semanticwb.bsc.element.Indicator value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_indicatorInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.element.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByMeasure(org.semanticwb.bsc.element.PeriodStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_measure, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.element.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByMeasure(org.semanticwb.bsc.element.PeriodStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_measure,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SeriesBase.ClassMgr getSeriesClassMgr()
    {
        return new SeriesBase.ClassMgr();
    }

   /**
   * Constructs a SeriesBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Series
   */
    public SeriesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Value property
* @return float with the Value
*/
    public float getValue()
    {
        return getSemanticObject().getFloatProperty(bsc_value);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(float value)
    {
        getSemanticObject().setFloatProperty(bsc_value, value);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(bsc_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(bsc_index, value);
    }
   /**
   * Sets the value for the property Format
   * @param value Format to set
   */

    public void setFormat(org.semanticwb.bsc.catalogs.Format value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_format, value.getSemanticObject());
        }else
        {
            removeFormat();
        }
    }
   /**
   * Remove the value for Format property
   */

    public void removeFormat()
    {
        getSemanticObject().removeProperty(bsc_format);
    }

   /**
   * Gets the Format
   * @return a org.semanticwb.bsc.catalogs.Format
   */
    public org.semanticwb.bsc.catalogs.Format getFormat()
    {
         org.semanticwb.bsc.catalogs.Format ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_format);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.catalogs.Format)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Indicator
   * @param value Indicator to set
   */

    public void setIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_indicatorInv, value.getSemanticObject());
        }else
        {
            removeIndicator();
        }
    }
   /**
   * Remove the value for Indicator property
   */

    public void removeIndicator()
    {
        getSemanticObject().removeProperty(bsc_indicatorInv);
    }

   /**
   * Gets the Indicator
   * @return a org.semanticwb.bsc.element.Indicator
   */
    public org.semanticwb.bsc.element.Indicator getIndicator()
    {
         org.semanticwb.bsc.element.Indicator ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_indicatorInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Indicator)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Measure
   * @param value Measure to set
   */

    public void setMeasure(org.semanticwb.bsc.element.PeriodStatus value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_measure, value.getSemanticObject());
        }else
        {
            removeMeasure();
        }
    }
   /**
   * Remove the value for Measure property
   */

    public void removeMeasure()
    {
        getSemanticObject().removeProperty(bsc_measure);
    }

   /**
   * Gets the Measure
   * @return a org.semanticwb.bsc.element.PeriodStatus
   */
    public org.semanticwb.bsc.element.PeriodStatus getMeasure()
    {
         org.semanticwb.bsc.element.PeriodStatus ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_measure);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.PeriodStatus)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Commited property
* @return boolean with the Commited
*/
    public boolean isCommited()
    {
        return getSemanticObject().getBooleanProperty(bsc_commited);
    }

/**
* Sets the Commited property
* @param value long with the Commited
*/
    public void setCommited(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_commited, value);
    }
}
