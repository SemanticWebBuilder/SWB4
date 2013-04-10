package org.semanticwb.process.resources.reports.base;


public abstract class FilterReportGroupValueBase extends org.semanticwb.process.resources.reports.FilterReport implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty rep_initialDates=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#initialDates");
    public static final org.semanticwb.platform.SemanticProperty rep_initialValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#initialValue");
    public static final org.semanticwb.platform.SemanticProperty rep_finalValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#finalValue");
    public static final org.semanticwb.platform.SemanticProperty rep_finalDates=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#finalDates");
    public static final org.semanticwb.platform.SemanticClass rep_FilterReportGroupValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReportGroupValue");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReportGroupValue");

    public static class ClassMgr
    {
       /**
       * Returns a list of FilterReportGroupValue for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.reports.FilterReportGroupValue for all models
       * @return Iterator of org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue>(it, true);
        }

        public static org.semanticwb.process.resources.reports.FilterReportGroupValue createFilterReportGroupValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.reports.FilterReportGroupValue.ClassMgr.createFilterReportGroupValue(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.reports.FilterReportGroupValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportGroupValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       * @return A org.semanticwb.process.reports.FilterReportGroupValue
       */
        public static org.semanticwb.process.resources.reports.FilterReportGroupValue getFilterReportGroupValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.FilterReportGroupValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.reports.FilterReportGroupValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportGroupValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       * @return A org.semanticwb.process.reports.FilterReportGroupValue
       */
        public static org.semanticwb.process.resources.reports.FilterReportGroupValue createFilterReportGroupValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.FilterReportGroupValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.reports.FilterReportGroupValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportGroupValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       */
        public static void removeFilterReportGroupValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.reports.FilterReportGroupValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportGroupValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       * @return true if the org.semanticwb.process.reports.FilterReportGroupValue exists, false otherwise
       */

        public static boolean hasFilterReportGroupValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFilterReportGroupValue(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportGroupValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValueByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportGroupValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValueByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportGroupValue with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValueByColumn(org.semanticwb.process.resources.reports.ColumnReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_column, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportGroupValue with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValueByColumn(org.semanticwb.process.resources.reports.ColumnReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_column,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportGroupValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReportGroupValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValueByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportGroupValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportGroupValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> listFilterReportGroupValueByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportGroupValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FilterReportGroupValueBase.ClassMgr getFilterReportGroupValueClassMgr()
    {
        return new FilterReportGroupValueBase.ClassMgr();
    }

   /**
   * Constructs a FilterReportGroupValueBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FilterReportGroupValue
   */
    public FilterReportGroupValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the InitialDates property
* @return java.util.Date with the InitialDates
*/
    public java.util.Date getInitialDates()
    {
        return getSemanticObject().getDateProperty(rep_initialDates);
    }

/**
* Sets the InitialDates property
* @param value long with the InitialDates
*/
    public void setInitialDates(java.util.Date value)
    {
        getSemanticObject().setDateProperty(rep_initialDates, value);
    }

/**
* Gets the InitialValue property
* @return float with the InitialValue
*/
    public float getInitialValue()
    {
        return getSemanticObject().getFloatProperty(rep_initialValue);
    }

/**
* Sets the InitialValue property
* @param value long with the InitialValue
*/
    public void setInitialValue(float value)
    {
        getSemanticObject().setFloatProperty(rep_initialValue, value);
    }

/**
* Gets the FinalValue property
* @return float with the FinalValue
*/
    public float getFinalValue()
    {
        return getSemanticObject().getFloatProperty(rep_finalValue);
    }

/**
* Sets the FinalValue property
* @param value long with the FinalValue
*/
    public void setFinalValue(float value)
    {
        getSemanticObject().setFloatProperty(rep_finalValue, value);
    }

/**
* Gets the FinalDates property
* @return java.util.Date with the FinalDates
*/
    public java.util.Date getFinalDates()
    {
        return getSemanticObject().getDateProperty(rep_finalDates);
    }

/**
* Sets the FinalDates property
* @param value long with the FinalDates
*/
    public void setFinalDates(java.util.Date value)
    {
        getSemanticObject().setDateProperty(rep_finalDates, value);
    }
}
