package org.semanticwb.process.model.base;


public abstract class CalledFlowNodeInstanceBase extends org.semanticwb.process.model.FlowNodeInstance implements org.semanticwb.process.model.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_CalledFlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CalledFlowNodeInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CalledFlowNodeInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of CalledFlowNodeInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CalledFlowNodeInstance for all models
       * @return Iterator of org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance>(it, true);
        }

        public static org.semanticwb.process.model.CalledFlowNodeInstance createCalledFlowNodeInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CalledFlowNodeInstance.ClassMgr.createCalledFlowNodeInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CalledFlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.CalledFlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return A org.semanticwb.process.model.CalledFlowNodeInstance
       */
        public static org.semanticwb.process.model.CalledFlowNodeInstance getCalledFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CalledFlowNodeInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CalledFlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.CalledFlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return A org.semanticwb.process.model.CalledFlowNodeInstance
       */
        public static org.semanticwb.process.model.CalledFlowNodeInstance createCalledFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CalledFlowNodeInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CalledFlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.CalledFlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       */
        public static void removeCalledFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CalledFlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.CalledFlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return true if the org.semanticwb.process.model.CalledFlowNodeInstance exists, false otherwise
       */

        public static boolean hasCalledFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCalledFlowNodeInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByAssignedto(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByAssignedto(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.CalledFlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CalledFlowNodeInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.CalledFlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.CalledFlowNodeInstance> listCalledFlowNodeInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledFlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CalledFlowNodeInstanceBase.ClassMgr getCalledFlowNodeInstanceClassMgr()
    {
        return new CalledFlowNodeInstanceBase.ClassMgr();
    }

   /**
   * Constructs a CalledFlowNodeInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CalledFlowNodeInstance
   */
    public CalledFlowNodeInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
