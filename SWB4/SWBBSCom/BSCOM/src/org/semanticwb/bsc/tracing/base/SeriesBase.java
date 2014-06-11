package org.semanticwb.bsc.tracing.base;


public abstract class SeriesBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.bsc.Committable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.Roleable,org.semanticwb.bsc.Measurable,org.semanticwb.bsc.ReadOnly,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Sortable,org.semanticwb.bsc.Help,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_SM=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SM");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasSeriesInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasSeriesInv");
   /**
   * Un formato define un patrón para formatear mediciones. Tal como dólares, pesos, porcentaje, etc.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Format=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Format");
    public static final org.semanticwb.platform.SemanticProperty bsc_format=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#format");
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasMeasure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasMeasure");
    public static final org.semanticwb.platform.SemanticClass bsc_EvaluationRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#EvaluationRule");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasEvaluationRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasEvaluationRule");
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
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Sm
       * @param value Sm of the type org.semanticwb.bsc.SM
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesBySm(org.semanticwb.bsc.SM value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasSeriesInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Sm
       * @param value Sm of the type org.semanticwb.bsc.SM
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesBySm(org.semanticwb.bsc.SM value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasSeriesInv,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.tracing.Measure
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByMeasure(org.semanticwb.bsc.tracing.Measure value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMeasure, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByMeasure(org.semanticwb.bsc.tracing.Measure value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMeasure,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
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
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined EvaluationRule
       * @param value EvaluationRule of the type org.semanticwb.bsc.tracing.EvaluationRule
       * @param model Model of the org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByEvaluationRule(org.semanticwb.bsc.tracing.EvaluationRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasEvaluationRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Series with a determined EvaluationRule
       * @param value EvaluationRule of the type org.semanticwb.bsc.tracing.EvaluationRule
       * @return Iterator with all the org.semanticwb.bsc.tracing.Series
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Series> listSeriesByEvaluationRule(org.semanticwb.bsc.tracing.EvaluationRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasEvaluationRule,value.getSemanticObject(),sclass));
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
   * Sets the value for the property Sm
   * @param value Sm to set
   */

    public void setSm(org.semanticwb.bsc.SM value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_hasSeriesInv, value.getSemanticObject());
        }else
        {
            removeSm();
        }
    }
   /**
   * Remove the value for Sm property
   */

    public void removeSm()
    {
        getSemanticObject().removeProperty(bsc_hasSeriesInv);
    }

   /**
   * Gets the Sm
   * @return a org.semanticwb.bsc.SM
   */
    public org.semanticwb.bsc.SM getSm()
    {
         org.semanticwb.bsc.SM ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasSeriesInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.SM)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.bsc.tracing.Measure
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.Measure
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> listMeasures()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure>(getSemanticObject().listObjectProperties(bsc_hasMeasure));
    }

   /**
   * Gets true if has a Measure
   * @param value org.semanticwb.bsc.tracing.Measure to verify
   * @return true if the org.semanticwb.bsc.tracing.Measure exists, false otherwise
   */
    public boolean hasMeasure(org.semanticwb.bsc.tracing.Measure value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasMeasure,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Measure
   * @param value org.semanticwb.bsc.tracing.Measure to add
   */

    public void addMeasure(org.semanticwb.bsc.tracing.Measure value)
    {
        getSemanticObject().addObjectProperty(bsc_hasMeasure, value.getSemanticObject());
    }
   /**
   * Removes all the Measure
   */

    public void removeAllMeasure()
    {
        getSemanticObject().removeProperty(bsc_hasMeasure);
    }
   /**
   * Removes a Measure
   * @param value org.semanticwb.bsc.tracing.Measure to remove
   */

    public void removeMeasure(org.semanticwb.bsc.tracing.Measure value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasMeasure,value.getSemanticObject());
    }

   /**
   * Gets the Measure
   * @return a org.semanticwb.bsc.tracing.Measure
   */
    public org.semanticwb.bsc.tracing.Measure getMeasure()
    {
         org.semanticwb.bsc.tracing.Measure ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasMeasure);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Measure)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.tracing.EvaluationRule
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.EvaluationRule
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRules()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule>(getSemanticObject().listObjectProperties(bsc_hasEvaluationRule));
    }

   /**
   * Gets true if has a EvaluationRule
   * @param value org.semanticwb.bsc.tracing.EvaluationRule to verify
   * @return true if the org.semanticwb.bsc.tracing.EvaluationRule exists, false otherwise
   */
    public boolean hasEvaluationRule(org.semanticwb.bsc.tracing.EvaluationRule value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasEvaluationRule,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a EvaluationRule
   * @param value org.semanticwb.bsc.tracing.EvaluationRule to add
   */

    public void addEvaluationRule(org.semanticwb.bsc.tracing.EvaluationRule value)
    {
        getSemanticObject().addObjectProperty(bsc_hasEvaluationRule, value.getSemanticObject());
    }
   /**
   * Removes all the EvaluationRule
   */

    public void removeAllEvaluationRule()
    {
        getSemanticObject().removeProperty(bsc_hasEvaluationRule);
    }
   /**
   * Removes a EvaluationRule
   * @param value org.semanticwb.bsc.tracing.EvaluationRule to remove
   */

    public void removeEvaluationRule(org.semanticwb.bsc.tracing.EvaluationRule value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasEvaluationRule,value.getSemanticObject());
    }

   /**
   * Gets the EvaluationRule
   * @return a org.semanticwb.bsc.tracing.EvaluationRule
   */
    public org.semanticwb.bsc.tracing.EvaluationRule getEvaluationRule()
    {
         org.semanticwb.bsc.tracing.EvaluationRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasEvaluationRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.EvaluationRule)obj.createGenericInstance();
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

/**
* Gets the ReadOnly property
* @return boolean with the ReadOnly
*/
    public boolean isReadOnly()
    {
        return getSemanticObject().getBooleanProperty(bsc_readOnly);
    }

/**
* Sets the ReadOnly property
* @param value long with the ReadOnly
*/
    public void setReadOnly(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_readOnly, value);
    }
}
