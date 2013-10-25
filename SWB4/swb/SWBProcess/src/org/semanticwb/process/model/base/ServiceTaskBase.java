package org.semanticwb.process.model.base;


public abstract class ServiceTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.process.model.ActivityConfable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.RoleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.process.model.ResourceAssignmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessService");
    public static final org.semanticwb.platform.SemanticProperty swp_processService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processService");
    public static final org.semanticwb.platform.SemanticClass swp_ServiceTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ServiceTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ServiceTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of ServiceTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ServiceTask for all models
       * @return Iterator of org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask>(it, true);
        }

        public static org.semanticwb.process.model.ServiceTask createServiceTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ServiceTask.ClassMgr.createServiceTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ServiceTask
       * @param id Identifier for org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return A org.semanticwb.process.model.ServiceTask
       */
        public static org.semanticwb.process.model.ServiceTask getServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ServiceTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ServiceTask
       * @param id Identifier for org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return A org.semanticwb.process.model.ServiceTask
       */
        public static org.semanticwb.process.model.ServiceTask createServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ServiceTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ServiceTask
       * @param id Identifier for org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       */
        public static void removeServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ServiceTask
       * @param id Identifier for org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return true if the org.semanticwb.process.model.ServiceTask exists, false otherwise
       */

        public static boolean hasServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getServiceTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined ProcessService
       * @param value ProcessService of the type org.semanticwb.process.model.ProcessService
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByProcessService(org.semanticwb.process.model.ProcessService value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processService, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined ProcessService
       * @param value ProcessService of the type org.semanticwb.process.model.ProcessService
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByProcessService(org.semanticwb.process.model.ProcessService value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processService,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ServiceTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.ServiceTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ServiceTaskBase.ClassMgr getServiceTaskClassMgr()
    {
        return new ServiceTaskBase.ClassMgr();
    }

   /**
   * Constructs a ServiceTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ServiceTask
   */
    public ServiceTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ProcessService
   * @param value ProcessService to set
   */

    public void setProcessService(org.semanticwb.process.model.ProcessService value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processService, value.getSemanticObject());
        }else
        {
            removeProcessService();
        }
    }
   /**
   * Remove the value for ProcessService property
   */

    public void removeProcessService()
    {
        getSemanticObject().removeProperty(swp_processService);
    }

   /**
   * Gets the ProcessService
   * @return a org.semanticwb.process.model.ProcessService
   */
    public org.semanticwb.process.model.ProcessService getProcessService()
    {
         org.semanticwb.process.model.ProcessService ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processService);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessService)obj.createGenericInstance();
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
