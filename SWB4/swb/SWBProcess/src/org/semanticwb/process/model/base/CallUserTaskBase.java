package org.semanticwb.process.model.base;


public abstract class CallUserTaskBase extends org.semanticwb.process.model.CallTask implements org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.RoleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.process.model.ResourceAssignmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_CallUserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallUserTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallUserTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of CallUserTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CallUserTask for all models
       * @return Iterator of org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask>(it, true);
        }

        public static org.semanticwb.process.model.CallUserTask createCallUserTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CallUserTask.ClassMgr.createCallUserTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CallUserTask
       * @param id Identifier for org.semanticwb.process.model.CallUserTask
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return A org.semanticwb.process.model.CallUserTask
       */
        public static org.semanticwb.process.model.CallUserTask getCallUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallUserTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CallUserTask
       * @param id Identifier for org.semanticwb.process.model.CallUserTask
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return A org.semanticwb.process.model.CallUserTask
       */
        public static org.semanticwb.process.model.CallUserTask createCallUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallUserTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CallUserTask
       * @param id Identifier for org.semanticwb.process.model.CallUserTask
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       */
        public static void removeCallUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CallUserTask
       * @param id Identifier for org.semanticwb.process.model.CallUserTask
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return true if the org.semanticwb.process.model.CallUserTask exists, false otherwise
       */

        public static boolean hasCallUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallUserTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.CallUserTask
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallUserTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.CallUserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallUserTask> listCallUserTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallUserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CallUserTaskBase.ClassMgr getCallUserTaskClassMgr()
    {
        return new CallUserTaskBase.ClassMgr();
    }

   /**
   * Constructs a CallUserTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CallUserTask
   */
    public CallUserTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
