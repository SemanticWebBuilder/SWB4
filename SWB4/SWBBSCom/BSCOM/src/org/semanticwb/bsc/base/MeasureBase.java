package org.semanticwb.bsc.base;


public abstract class MeasureBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#value");
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
       * @return Iterator of org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasures(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.Measure for all models
       * @return Iterator of org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasures()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure>(it, true);
        }

        public static org.semanticwb.bsc.Measure createMeasure(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.Measure.ClassMgr.createMeasure(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.Measure
       * @param id Identifier for org.semanticwb.bsc.Measure
       * @param model Model of the org.semanticwb.bsc.Measure
       * @return A org.semanticwb.bsc.Measure
       */
        public static org.semanticwb.bsc.Measure getMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.Measure)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.Measure
       * @param id Identifier for org.semanticwb.bsc.Measure
       * @param model Model of the org.semanticwb.bsc.Measure
       * @return A org.semanticwb.bsc.Measure
       */
        public static org.semanticwb.bsc.Measure createMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.Measure)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.Measure
       * @param id Identifier for org.semanticwb.bsc.Measure
       * @param model Model of the org.semanticwb.bsc.Measure
       */
        public static void removeMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.Measure
       * @param id Identifier for org.semanticwb.bsc.Measure
       * @param model Model of the org.semanticwb.bsc.Measure
       * @return true if the org.semanticwb.bsc.Measure exists, false otherwise
       */

        public static boolean hasMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeasure(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.Measure with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.Measure
       * @return Iterator with all the org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasureByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Measure with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasureByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Measure with a determined Evaluation
       * @param value Evaluation of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.Measure
       * @return Iterator with all the org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasureByEvaluation(org.semanticwb.bsc.tracing.PeriodStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_evaluation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Measure with a determined Evaluation
       * @param value Evaluation of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasureByEvaluation(org.semanticwb.bsc.tracing.PeriodStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_evaluation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Measure with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.Measure
       * @return Iterator with all the org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasureByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Measure with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.Measure> listMeasureByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
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
