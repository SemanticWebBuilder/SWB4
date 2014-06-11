package org.semanticwb.bsc.catalogs.base;


public abstract class PriorityBase extends org.semanticwb.bsc.catalogs.Catalog implements org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_weighing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#weighing");
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
       /**
       * Gets all org.semanticwb.bsc.catalogs.Priority with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.catalogs.Priority
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Priority> listPriorityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Priority> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Priority with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Priority> listPriorityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Priority> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Priority with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.catalogs.Priority
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Priority> listPriorityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Priority> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Priority with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Priority> listPriorityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Priority> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
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

/**
* Gets the Weighing property
* @return float with the Weighing
*/
    public float getWeighing()
    {
        return getSemanticObject().getFloatProperty(bsc_weighing);
    }

/**
* Sets the Weighing property
* @param value long with the Weighing
*/
    public void setWeighing(float value)
    {
        getSemanticObject().setFloatProperty(bsc_weighing, value);
    }
}
