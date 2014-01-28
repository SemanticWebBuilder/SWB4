package org.semanticwb.process.model.base;


public abstract class ReceiveTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.process.model.ActivityConfable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_ReceiveTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ReceiveTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ReceiveTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of ReceiveTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ReceiveTask for all models
       * @return Iterator of org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask>(it, true);
        }

        public static org.semanticwb.process.model.ReceiveTask createReceiveTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ReceiveTask.ClassMgr.createReceiveTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ReceiveTask
       * @param id Identifier for org.semanticwb.process.model.ReceiveTask
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return A org.semanticwb.process.model.ReceiveTask
       */
        public static org.semanticwb.process.model.ReceiveTask getReceiveTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ReceiveTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ReceiveTask
       * @param id Identifier for org.semanticwb.process.model.ReceiveTask
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return A org.semanticwb.process.model.ReceiveTask
       */
        public static org.semanticwb.process.model.ReceiveTask createReceiveTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ReceiveTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ReceiveTask
       * @param id Identifier for org.semanticwb.process.model.ReceiveTask
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       */
        public static void removeReceiveTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ReceiveTask
       * @param id Identifier for org.semanticwb.process.model.ReceiveTask
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return true if the org.semanticwb.process.model.ReceiveTask exists, false otherwise
       */

        public static boolean hasReceiveTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getReceiveTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.ReceiveTask
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ReceiveTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.ReceiveTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReceiveTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ReceiveTaskBase.ClassMgr getReceiveTaskClassMgr()
    {
        return new ReceiveTaskBase.ClassMgr();
    }

   /**
   * Constructs a ReceiveTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ReceiveTask
   */
    public ReceiveTaskBase(org.semanticwb.platform.SemanticObject base)
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
