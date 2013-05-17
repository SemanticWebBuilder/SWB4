package org.semanticwb.bsc.tracing.base;


public abstract class SeriesBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
    public static final org.semanticwb.platform.SemanticProperty bsc_measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#measure");
    public static final org.semanticwb.platform.SemanticProperty bsc_formatPattern=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#formatPattern");
    public static final org.semanticwb.platform.SemanticProperty bsc_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#value");
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
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByMeasure(org.semanticwb.bsc.element.Measure value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_measure, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.element.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByMeasure(org.semanticwb.bsc.element.Measure value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_measure,value.getSemanticObject(),sclass));
            return it;
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
   * Sets the value for the property Measure
   * @param value Measure to set
   */

    public void setMeasure(org.semanticwb.bsc.element.Measure value)
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
   * @return a org.semanticwb.bsc.element.Measure
   */
    public org.semanticwb.bsc.element.Measure getMeasure()
    {
         org.semanticwb.bsc.element.Measure ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_measure);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Measure)obj.createGenericInstance();
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
}
