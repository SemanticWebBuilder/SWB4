package org.semanticwb.process.model.base;


public abstract class BusinessRuleTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.model.Referensable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Callable,org.semanticwb.process.model.ActivityConfable,org.semanticwb.model.RoleRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_BusinessRuleTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#BusinessRuleTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#BusinessRuleTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of BusinessRuleTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.BusinessRuleTask for all models
       * @return Iterator of org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask>(it, true);
        }

        public static org.semanticwb.process.model.BusinessRuleTask createBusinessRuleTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BusinessRuleTask.ClassMgr.createBusinessRuleTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.BusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.BusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return A org.semanticwb.process.model.BusinessRuleTask
       */
        public static org.semanticwb.process.model.BusinessRuleTask getBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BusinessRuleTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.BusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.BusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return A org.semanticwb.process.model.BusinessRuleTask
       */
        public static org.semanticwb.process.model.BusinessRuleTask createBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BusinessRuleTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.BusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.BusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       */
        public static void removeBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.BusinessRuleTask
       * @param id Identifier for org.semanticwb.process.model.BusinessRuleTask
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return true if the org.semanticwb.process.model.BusinessRuleTask exists, false otherwise
       */

        public static boolean hasBusinessRuleTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBusinessRuleTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.BusinessRuleTask
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.BusinessRuleTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.BusinessRuleTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessRuleTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static BusinessRuleTaskBase.ClassMgr getBusinessRuleTaskClassMgr()
    {
        return new BusinessRuleTaskBase.ClassMgr();
    }

   /**
   * Constructs a BusinessRuleTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BusinessRuleTask
   */
    public BusinessRuleTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Callable property
* @return boolean with the Callable
*/
    public boolean isCallable()
    {
        return getSemanticObject().getBooleanProperty(swp_callable);
    }

/**
* Sets the Callable property
* @param value long with the Callable
*/
    public void setCallable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_callable, value);
    }
}
