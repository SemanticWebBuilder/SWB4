package org.semanticwb.process.reports.base;


public abstract class FilterReportRangeValueBase extends org.semanticwb.process.reports.FilterReport implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass rep_FilterReportRangeValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReportRangeValue");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReportRangeValue");

    public static class ClassMgr
    {
       /**
       * Returns a list of FilterReportRangeValue for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.reports.FilterReportRangeValue for all models
       * @return Iterator of org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue>(it, true);
        }

        public static org.semanticwb.process.reports.FilterReportRangeValue createFilterReportRangeValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.reports.FilterReportRangeValue.ClassMgr.createFilterReportRangeValue(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.reports.FilterReportRangeValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportRangeValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       * @return A org.semanticwb.process.reports.FilterReportRangeValue
       */
        public static org.semanticwb.process.reports.FilterReportRangeValue getFilterReportRangeValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.reports.FilterReportRangeValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.reports.FilterReportRangeValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportRangeValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       * @return A org.semanticwb.process.reports.FilterReportRangeValue
       */
        public static org.semanticwb.process.reports.FilterReportRangeValue createFilterReportRangeValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.reports.FilterReportRangeValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.reports.FilterReportRangeValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportRangeValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       */
        public static void removeFilterReportRangeValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.reports.FilterReportRangeValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportRangeValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       * @return true if the org.semanticwb.process.reports.FilterReportRangeValue exists, false otherwise
       */

        public static boolean hasFilterReportRangeValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFilterReportRangeValue(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportRangeValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValueByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportRangeValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValueByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportRangeValue with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValueByColumn(org.semanticwb.process.reports.ColumnReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_column, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportRangeValue with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValueByColumn(org.semanticwb.process.reports.ColumnReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_column,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportRangeValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReportRangeValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValueByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportRangeValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportRangeValue
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReportRangeValue> listFilterReportRangeValueByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReportRangeValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FilterReportRangeValueBase.ClassMgr getFilterReportRangeValueClassMgr()
    {
        return new FilterReportRangeValueBase.ClassMgr();
    }

   /**
   * Constructs a FilterReportRangeValueBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FilterReportRangeValue
   */
    public FilterReportRangeValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
