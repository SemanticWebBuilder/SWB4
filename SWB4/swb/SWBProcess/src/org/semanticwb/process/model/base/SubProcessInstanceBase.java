package org.semanticwb.process.model.base;


public abstract class SubProcessInstanceBase extends org.semanticwb.process.model.FlowNodeInstance implements org.semanticwb.process.model.ContainerInstanceable,org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessTraceable
{
    public static final org.semanticwb.platform.SemanticClass swp_SubProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcessInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcessInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of SubProcessInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SubProcessInstance for all models
       * @return Iterator of org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance>(it, true);
        }

        public static org.semanticwb.process.model.SubProcessInstance createSubProcessInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SubProcessInstance.ClassMgr.createSubProcessInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.SubProcessInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return A org.semanticwb.process.model.SubProcessInstance
       */
        public static org.semanticwb.process.model.SubProcessInstance getSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubProcessInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.SubProcessInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return A org.semanticwb.process.model.SubProcessInstance
       */
        public static org.semanticwb.process.model.SubProcessInstance createSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubProcessInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.SubProcessInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       */
        public static void removeSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SubProcessInstance
       * @param id Identifier for org.semanticwb.process.model.SubProcessInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return true if the org.semanticwb.process.model.SubProcessInstance exists, false otherwise
       */

        public static boolean hasSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSubProcessInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByAssignedto(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByAssignedto(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstancesInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstancesInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.SubProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcessInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.SubProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SubProcessInstanceBase.ClassMgr getSubProcessInstanceClassMgr()
    {
        return new SubProcessInstanceBase.ClassMgr();
    }

   /**
   * Constructs a SubProcessInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SubProcessInstance
   */
    public SubProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasFlowNodeInstancesInv));
    }

   /**
   * Gets true if has a FlowNodeInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowNodeInstancesInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the FlowNodeInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getFlowNodeInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowNodeInstancesInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
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
