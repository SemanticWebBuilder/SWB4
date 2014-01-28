package org.semanticwb.process.model.base;


public abstract class CalledSubProcessInstanceBase extends org.semanticwb.process.model.SubProcessInstance implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessTraceable,org.semanticwb.process.model.ContainerInstanceable
{
    public static final org.semanticwb.platform.SemanticClass swp_CalledSubProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CalledSubProcessInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CalledSubProcessInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of CalledSubProcessInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CalledSubProcessInstance for all models
       * @return Iterator of org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance>(it, true);
        }

        public static org.semanticwb.process.model.CalledSubProcessInstance createCalledSubProcessInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CalledSubProcessInstance.ClassMgr.createCalledSubProcessInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CalledSubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.CalledSubProcessInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return A org.semanticwb.process.model.CalledSubProcessInstance
       */
        public static org.semanticwb.process.model.CalledSubProcessInstance getCalledSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CalledSubProcessInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CalledSubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.CalledSubProcessInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return A org.semanticwb.process.model.CalledSubProcessInstance
       */
        public static org.semanticwb.process.model.CalledSubProcessInstance createCalledSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CalledSubProcessInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CalledSubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.CalledSubProcessInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       */
        public static void removeCalledSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CalledSubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.CalledSubProcessInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return true if the org.semanticwb.process.model.CalledSubProcessInstance exists, false otherwise
       */

        public static boolean hasCalledSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCalledSubProcessInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByAssignedto(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByAssignedto(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstancesInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstancesInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.CalledSubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledSubProcessInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.CalledSubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessInstance> listCalledSubProcessInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CalledSubProcessInstanceBase.ClassMgr getCalledSubProcessInstanceClassMgr()
    {
        return new CalledSubProcessInstanceBase.ClassMgr();
    }

   /**
   * Constructs a CalledSubProcessInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CalledSubProcessInstance
   */
    public CalledSubProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
