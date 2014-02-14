package org.semanticwb.bsc.catalogs.base;


public abstract class PriorityBase extends org.semanticwb.bsc.catalogs.Catalog implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Priority");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Priority");

    public static class ClassMgr
    {
       /**
       * Returns a list of Priority for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Priority> listPriorities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Priority>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Priority for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Priority> listPriorities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Priority>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Priority createPriority(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Priority.ClassMgr.createPriority(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Priority
       * @param id Identifier for org.semanticwb.bsc.catalogs.Priority
       * @param model Model of the org.semanticwb.bsc.catalogs.Priority
       * @return A org.semanticwb.bsc.catalogs.Priority
       */
        public static org.semanticwb.bsc.catalogs.Priority getPriority(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Priority)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Priority
       * @param id Identifier for org.semanticwb.bsc.catalogs.Priority
       * @param model Model of the org.semanticwb.bsc.catalogs.Priority
       * @return A org.semanticwb.bsc.catalogs.Priority
       */
        public static org.semanticwb.bsc.catalogs.Priority createPriority(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Priority)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Priority
       * @param id Identifier for org.semanticwb.bsc.catalogs.Priority
       * @param model Model of the org.semanticwb.bsc.catalogs.Priority
       */
        public static void removePriority(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Priority
       * @param id Identifier for org.semanticwb.bsc.catalogs.Priority
       * @param model Model of the org.semanticwb.bsc.catalogs.Priority
       * @return true if the org.semanticwb.bsc.catalogs.Priority exists, false otherwise
       */

        public static boolean hasPriority(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPriority(id, model)!=null);
        }
    }

    public static PriorityBase.ClassMgr getPriorityClassMgr()
    {
        return new PriorityBase.ClassMgr();
    }

   /**
   * Constructs a PriorityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Priority
   */
    public PriorityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
