package org.semanticwb.bsc.tracing.base;


public abstract class MeasureBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Roleable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#value");
    public static final org.semanticwb.platform.SemanticClass bsc_Series=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Series");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasMeasureInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasMeasureInv");
   /**
   * Clase que define el valor de un estado en un periodo. Ejemplo: Para el periodo "Enero 2013" - Estado "En Riesgo"
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");
    public static final org.semanticwb.platform.SemanticProperty bsc_evaluation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#evaluation");
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");

    public static class ClassMgr
    {
       /**
       * Returns a list of Measure for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasures(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Measure for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasures()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Measure createMeasure(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Measure.ClassMgr.createMeasure(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Measure
       * @param id Identifier for org.semanticwb.bsc.tracing.Measure
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return A org.semanticwb.bsc.tracing.Measure
       */
        public static org.semanticwb.bsc.tracing.Measure getMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Measure)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Measure
       * @param id Identifier for org.semanticwb.bsc.tracing.Measure
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return A org.semanticwb.bsc.tracing.Measure
       */
        public static org.semanticwb.bsc.tracing.Measure createMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Measure)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Measure
       * @param id Identifier for org.semanticwb.bsc.tracing.Measure
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       */
        public static void removeMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Measure
       * @param id Identifier for org.semanticwb.bsc.tracing.Measure
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return true if the org.semanticwb.bsc.tracing.Measure exists, false otherwise
       */

        public static boolean hasMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeasure(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Series
       * @param value Series of the type org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureBySeries(org.semanticwb.bsc.tracing.Series value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMeasureInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Series
       * @param value Series of the type org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureBySeries(org.semanticwb.bsc.tracing.Series value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMeasureInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Evaluation
       * @param value Evaluation of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByEvaluation(org.semanticwb.bsc.tracing.PeriodStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_evaluation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Evaluation
       * @param value Evaluation of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByEvaluation(org.semanticwb.bsc.tracing.PeriodStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_evaluation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Measure
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Measure with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Measure> listMeasureByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MeasureBase.ClassMgr getMeasureClassMgr()
    {
        return new MeasureBase.ClassMgr();
    }

   /**
   * Constructs a MeasureBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Measure
   */
    public MeasureBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property Series
   * @param value Series to set
   */

    public void setSeries(org.semanticwb.bsc.tracing.Series value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_hasMeasureInv, value.getSemanticObject());
        }else
        {
            removeSeries();
        }
    }
   /**
   * Remove the value for Series property
   */

    public void removeSeries()
    {
        getSemanticObject().removeProperty(bsc_hasMeasureInv);
    }

   /**
   * Gets the Series
   * @return a org.semanticwb.bsc.tracing.Series
   */
    public org.semanticwb.bsc.tracing.Series getSeries()
    {
         org.semanticwb.bsc.tracing.Series ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasMeasureInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Series)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Evaluation
   * @param value Evaluation to set
   */

    public void setEvaluation(org.semanticwb.bsc.tracing.PeriodStatus value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_evaluation, value.getSemanticObject());
        }else
        {
            removeEvaluation();
        }
    }
   /**
   * Remove the value for Evaluation property
   */

    public void removeEvaluation()
    {
        getSemanticObject().removeProperty(bsc_evaluation);
    }

   /**
   * Gets the Evaluation
   * @return a org.semanticwb.bsc.tracing.PeriodStatus
   */
    public org.semanticwb.bsc.tracing.PeriodStatus getEvaluation()
    {
         org.semanticwb.bsc.tracing.PeriodStatus ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_evaluation);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.PeriodStatus)obj.createGenericInstance();
         }
         return ret;
    }
}
