package org.semanticwb.process.model.base;


public abstract class CallBusinessRuleTaskBase extends org.semanticwb.process.model.CallTask implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.model.Sortable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_CallBusinessRuleTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallBusinessRuleTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallBusinessRuleTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of CallBusinessRuleTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CallBusinessRuleTask for all models
       * @return Iterator of org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask>(it, true);
        }

        public static org.semanticwb.process.model.CallBusinessRuleTask createCallBusinessRuleTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CallBusinessRuleTask.ClassMgr.createCallBusinessRuleTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CallBusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.CallBusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return A org.semanticwb.process.model.CallBusinessRuleTask
       */
        public static org.semanticwb.process.model.CallBusinessRuleTask getCallBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallBusinessRuleTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CallBusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.CallBusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return A org.semanticwb.process.model.CallBusinessRuleTask
       */
        public static org.semanticwb.process.model.CallBusinessRuleTask createCallBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallBusinessRuleTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CallBusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.CallBusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       */
        public static void removeCallBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CallBusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.CallBusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return true if the org.semanticwb.process.model.CallBusinessRuleTask exists, false otherwise
       */

        public static boolean hasCallBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallBusinessRuleTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.CallBusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallBusinessRuleTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.CallBusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallBusinessRuleTask> listCallBusinessRuleTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallBusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CallBusinessRuleTaskBase.ClassMgr getCallBusinessRuleTaskClassMgr()
    {
        return new CallBusinessRuleTaskBase.ClassMgr();
    }

   /**
   * Constructs a CallBusinessRuleTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CallBusinessRuleTask
   */
    public CallBusinessRuleTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
