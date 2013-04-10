package org.semanticwb.process.resources.reports.base;


public abstract class FilterReportConcretValueBase extends org.semanticwb.process.resources.reports.FilterReport implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass rep_FilterReportConcretValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReportConcretValue");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReportConcretValue");

    public static class ClassMgr
    {
       /**
       * Returns a list of FilterReportConcretValue for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.reports.FilterReportConcretValue for all models
       * @return Iterator of org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue>(it, true);
        }

        public static org.semanticwb.process.resources.reports.FilterReportConcretValue createFilterReportConcretValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.reports.FilterReportConcretValue.ClassMgr.createFilterReportConcretValue(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.reports.FilterReportConcretValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportConcretValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       * @return A org.semanticwb.process.reports.FilterReportConcretValue
       */
        public static org.semanticwb.process.resources.reports.FilterReportConcretValue getFilterReportConcretValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.FilterReportConcretValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.reports.FilterReportConcretValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportConcretValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       * @return A org.semanticwb.process.reports.FilterReportConcretValue
       */
        public static org.semanticwb.process.resources.reports.FilterReportConcretValue createFilterReportConcretValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.FilterReportConcretValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.reports.FilterReportConcretValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportConcretValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       */
        public static void removeFilterReportConcretValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.reports.FilterReportConcretValue
       * @param id Identifier for org.semanticwb.process.reports.FilterReportConcretValue
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       * @return true if the org.semanticwb.process.reports.FilterReportConcretValue exists, false otherwise
       */

        public static boolean hasFilterReportConcretValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFilterReportConcretValue(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportConcretValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValueByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportConcretValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValueByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportConcretValue with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValueByColumn(org.semanticwb.process.resources.reports.ColumnReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_column, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportConcretValue with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValueByColumn(org.semanticwb.process.resources.reports.ColumnReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_column,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportConcretValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReportConcretValue
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValueByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReportConcretValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReportConcretValue
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> listFilterReportConcretValueByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FilterReportConcretValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FilterReportConcretValueBase.ClassMgr getFilterReportConcretValueClassMgr()
    {
        return new FilterReportConcretValueBase.ClassMgr();
    }

   /**
   * Constructs a FilterReportConcretValueBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FilterReportConcretValue
   */
    public FilterReportConcretValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
