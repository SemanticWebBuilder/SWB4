package org.semanticwb.process.model.base;


public abstract class AdhocTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_AdhocTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AdhocTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AdhocTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of AdhocTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.AdhocTask for all models
       * @return Iterator of org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask>(it, true);
        }

        public static org.semanticwb.process.model.AdhocTask createAdhocTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.AdhocTask.ClassMgr.createAdhocTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.AdhocTask
       * @param id Identifier for org.semanticwb.process.model.AdhocTask
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return A org.semanticwb.process.model.AdhocTask
       */
        public static org.semanticwb.process.model.AdhocTask getAdhocTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AdhocTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.AdhocTask
       * @param id Identifier for org.semanticwb.process.model.AdhocTask
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return A org.semanticwb.process.model.AdhocTask
       */
        public static org.semanticwb.process.model.AdhocTask createAdhocTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AdhocTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.AdhocTask
       * @param id Identifier for org.semanticwb.process.model.AdhocTask
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       */
        public static void removeAdhocTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.AdhocTask
       * @param id Identifier for org.semanticwb.process.model.AdhocTask
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return true if the org.semanticwb.process.model.AdhocTask exists, false otherwise
       */

        public static boolean hasAdhocTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAdhocTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.AdhocTask
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AdhocTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.AdhocTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdhocTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a AdhocTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AdhocTask
   */
    public AdhocTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
