package org.semanticwb.process.model.base;


public abstract class CallScriptTaskBase extends org.semanticwb.process.model.CallTask implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.model.Sortable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_CallScriptTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallScriptTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallScriptTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of CallScriptTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CallScriptTask for all models
       * @return Iterator of org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask>(it, true);
        }

        public static org.semanticwb.process.model.CallScriptTask createCallScriptTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CallScriptTask.ClassMgr.createCallScriptTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CallScriptTask
       * @param id Identifier for org.semanticwb.process.model.CallScriptTask
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return A org.semanticwb.process.model.CallScriptTask
       */
        public static org.semanticwb.process.model.CallScriptTask getCallScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallScriptTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CallScriptTask
       * @param id Identifier for org.semanticwb.process.model.CallScriptTask
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return A org.semanticwb.process.model.CallScriptTask
       */
        public static org.semanticwb.process.model.CallScriptTask createCallScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallScriptTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CallScriptTask
       * @param id Identifier for org.semanticwb.process.model.CallScriptTask
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       */
        public static void removeCallScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CallScriptTask
       * @param id Identifier for org.semanticwb.process.model.CallScriptTask
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return true if the org.semanticwb.process.model.CallScriptTask exists, false otherwise
       */

        public static boolean hasCallScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallScriptTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.CallScriptTask
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallScriptTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.CallScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallScriptTask> listCallScriptTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CallScriptTaskBase.ClassMgr getCallScriptTaskClassMgr()
    {
        return new CallScriptTaskBase.ClassMgr();
    }

   /**
   * Constructs a CallScriptTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CallScriptTask
   */
    public CallScriptTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
