package org.semanticwb.process.model.base;


public abstract class CompensationTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_CompensationTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of CompensationTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CompensationTask for all models
       * @return Iterator of org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask>(it, true);
        }

        public static org.semanticwb.process.model.CompensationTask createCompensationTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CompensationTask.ClassMgr.createCompensationTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CompensationTask
       * @param id Identifier for org.semanticwb.process.model.CompensationTask
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return A org.semanticwb.process.model.CompensationTask
       */
        public static org.semanticwb.process.model.CompensationTask getCompensationTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CompensationTask
       * @param id Identifier for org.semanticwb.process.model.CompensationTask
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return A org.semanticwb.process.model.CompensationTask
       */
        public static org.semanticwb.process.model.CompensationTask createCompensationTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CompensationTask
       * @param id Identifier for org.semanticwb.process.model.CompensationTask
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       */
        public static void removeCompensationTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CompensationTask
       * @param id Identifier for org.semanticwb.process.model.CompensationTask
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return true if the org.semanticwb.process.model.CompensationTask exists, false otherwise
       */

        public static boolean hasCompensationTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompensationTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationTask
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a CompensationTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CompensationTask
   */
    public CompensationTaskBase(org.semanticwb.platform.SemanticObject base)
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
