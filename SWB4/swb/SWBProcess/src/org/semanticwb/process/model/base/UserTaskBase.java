package org.semanticwb.process.model.base;


public abstract class UserTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable,org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.model.Resourceable,org.semanticwb.model.Sortable,org.semanticwb.model.RoleRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.process.model.Callable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Referensable,org.semanticwb.process.model.ActivityConfable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.CalendarRefable
{
    public static final org.semanticwb.platform.SemanticProperty swp_notificationTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#notificationTime");
    public static final org.semanticwb.platform.SemanticClass swp_WrapperTaskWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WrapperTaskWebPage");
    public static final org.semanticwb.platform.SemanticProperty swp_taskWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#taskWebPage");
    public static final org.semanticwb.platform.SemanticClass swp_TaskAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskAction");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTaskAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTaskAction");
    public static final org.semanticwb.platform.SemanticProperty swp_linkNextUserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#linkNextUserTask");
    public static final org.semanticwb.platform.SemanticClass swp_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.UserTask for all models
       * @return Iterator of org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask>(it, true);
        }

        public static org.semanticwb.process.model.UserTask createUserTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.UserTask.ClassMgr.createUserTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.UserTask
       * @param id Identifier for org.semanticwb.process.model.UserTask
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return A org.semanticwb.process.model.UserTask
       */
        public static org.semanticwb.process.model.UserTask getUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.UserTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.UserTask
       * @param id Identifier for org.semanticwb.process.model.UserTask
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return A org.semanticwb.process.model.UserTask
       */
        public static org.semanticwb.process.model.UserTask createUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.UserTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.UserTask
       * @param id Identifier for org.semanticwb.process.model.UserTask
       * @param model Model of the org.semanticwb.process.model.UserTask
       */
        public static void removeUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.UserTask
       * @param id Identifier for org.semanticwb.process.model.UserTask
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return true if the org.semanticwb.process.model.UserTask exists, false otherwise
       */

        public static boolean hasUserTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined TaskWebPage
       * @param value TaskWebPage of the type org.semanticwb.process.model.WrapperTaskWebPage
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByTaskWebPage(org.semanticwb.process.model.WrapperTaskWebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_taskWebPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined TaskWebPage
       * @param value TaskWebPage of the type org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByTaskWebPage(org.semanticwb.process.model.WrapperTaskWebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_taskWebPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined TaskAction
       * @param value TaskAction of the type org.semanticwb.process.model.TaskAction
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByTaskAction(org.semanticwb.process.model.TaskAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTaskAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined TaskAction
       * @param value TaskAction of the type org.semanticwb.process.model.TaskAction
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByTaskAction(org.semanticwb.process.model.TaskAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTaskAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.UserTask with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.UserTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserTaskBase.ClassMgr getUserTaskClassMgr()
    {
        return new UserTaskBase.ClassMgr();
    }

   /**
   * Constructs a UserTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserTask
   */
    public UserTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NotificationTime property
* @return int with the NotificationTime
*/
    public int getNotificationTime()
    {
        return getSemanticObject().getIntProperty(swp_notificationTime);
    }

/**
* Sets the NotificationTime property
* @param value long with the NotificationTime
*/
    public void setNotificationTime(int value)
    {
        getSemanticObject().setIntProperty(swp_notificationTime, value);
    }
   /**
   * Sets the value for the property TaskWebPage
   * @param value TaskWebPage to set
   */

    public void setTaskWebPage(org.semanticwb.process.model.WrapperTaskWebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_taskWebPage, value.getSemanticObject());
        }else
        {
            removeTaskWebPage();
        }
    }
   /**
   * Remove the value for TaskWebPage property
   */

    public void removeTaskWebPage()
    {
        getSemanticObject().removeProperty(swp_taskWebPage);
    }

   /**
   * Gets the TaskWebPage
   * @return a org.semanticwb.process.model.WrapperTaskWebPage
   */
    public org.semanticwb.process.model.WrapperTaskWebPage getTaskWebPage()
    {
         org.semanticwb.process.model.WrapperTaskWebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_taskWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.WrapperTaskWebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.TaskAction
   * @return A GenericIterator with all the org.semanticwb.process.model.TaskAction
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TaskAction> listTaskActions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TaskAction>(getSemanticObject().listObjectProperties(swp_hasTaskAction));
    }

   /**
   * Gets true if has a TaskAction
   * @param value org.semanticwb.process.model.TaskAction to verify
   * @return true if the org.semanticwb.process.model.TaskAction exists, false otherwise
   */
    public boolean hasTaskAction(org.semanticwb.process.model.TaskAction value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasTaskAction,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a TaskAction
   * @param value org.semanticwb.process.model.TaskAction to add
   */

    public void addTaskAction(org.semanticwb.process.model.TaskAction value)
    {
        getSemanticObject().addObjectProperty(swp_hasTaskAction, value.getSemanticObject());
    }
   /**
   * Removes all the TaskAction
   */

    public void removeAllTaskAction()
    {
        getSemanticObject().removeProperty(swp_hasTaskAction);
    }
   /**
   * Removes a TaskAction
   * @param value org.semanticwb.process.model.TaskAction to remove
   */

    public void removeTaskAction(org.semanticwb.process.model.TaskAction value)
    {
        getSemanticObject().removeObjectProperty(swp_hasTaskAction,value.getSemanticObject());
    }

   /**
   * Gets the TaskAction
   * @return a org.semanticwb.process.model.TaskAction
   */
    public org.semanticwb.process.model.TaskAction getTaskAction()
    {
         org.semanticwb.process.model.TaskAction ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTaskAction);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.TaskAction)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.TemplateRef
   * @return A GenericIterator with all the org.semanticwb.model.TemplateRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listObjectProperties(swb_hasTemplateRef));
    }

   /**
   * Gets true if has a TemplateRef
   * @param value org.semanticwb.model.TemplateRef to verify
   * @return true if the org.semanticwb.model.TemplateRef exists, false otherwise
   */
    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasTemplateRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the TemplateRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.TemplateRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listInheritProperties(swb_hasTemplateRef));
    }
   /**
   * Adds a TemplateRef
   * @param value org.semanticwb.model.TemplateRef to add
   */

    public void addTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasTemplateRef, value.getSemanticObject());
    }
   /**
   * Removes all the TemplateRef
   */

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(swb_hasTemplateRef);
    }
   /**
   * Removes a TemplateRef
   * @param value org.semanticwb.model.TemplateRef to remove
   */

    public void removeTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasTemplateRef,value.getSemanticObject());
    }

   /**
   * Gets the TemplateRef
   * @return a org.semanticwb.model.TemplateRef
   */
    public org.semanticwb.model.TemplateRef getTemplateRef()
    {
         org.semanticwb.model.TemplateRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplateRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateRef)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Deleted property
* @return boolean with the Deleted
*/
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
* Sets the Deleted property
* @param value long with the Deleted
*/
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
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
   /**
   * Gets all the org.semanticwb.model.Resource
   * @return A GenericIterator with all the org.semanticwb.model.Resource
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasResource));
    }

   /**
   * Gets true if has a Resource
   * @param value org.semanticwb.model.Resource to verify
   * @return true if the org.semanticwb.model.Resource exists, false otherwise
   */
    public boolean hasResource(org.semanticwb.model.Resource value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResource,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Resource
   * @param value org.semanticwb.model.Resource to add
   */

    public void addResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().addObjectProperty(swb_hasResource, value.getSemanticObject());
    }
   /**
   * Removes all the Resource
   */

    public void removeAllResource()
    {
        getSemanticObject().removeProperty(swb_hasResource);
    }
   /**
   * Removes a Resource
   * @param value org.semanticwb.model.Resource to remove
   */

    public void removeResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().removeObjectProperty(swb_hasResource,value.getSemanticObject());
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NotInheritTemplateRef property
* @return boolean with the NotInheritTemplateRef
*/
    public boolean isNotInheritTemplateRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritTemplateRef);
    }

/**
* Sets the NotInheritTemplateRef property
* @param value long with the NotInheritTemplateRef
*/
    public void setNotInheritTemplateRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritTemplateRef, value);
    }

/**
* Gets the Undeleteable property
* @return boolean with the Undeleteable
*/
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

/**
* Sets the Undeleteable property
* @param value long with the Undeleteable
*/
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

/**
* Gets the LinkNextUserTask property
* @return boolean with the LinkNextUserTask
*/
    public boolean isLinkNextUserTask()
    {
        return getSemanticObject().getBooleanProperty(swp_linkNextUserTask);
    }

/**
* Sets the LinkNextUserTask property
* @param value long with the LinkNextUserTask
*/
    public void setLinkNextUserTask(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_linkNextUserTask, value);
    }

/**
* Gets the NotInheritCalendarRef property
* @return boolean with the NotInheritCalendarRef
*/
    public boolean isNotInheritCalendarRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritCalendarRef);
    }

/**
* Sets the NotInheritCalendarRef property
* @param value long with the NotInheritCalendarRef
*/
    public void setNotInheritCalendarRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritCalendarRef, value);
    }
   /**
   * Gets all the org.semanticwb.model.CalendarRef
   * @return A GenericIterator with all the org.semanticwb.model.CalendarRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

   /**
   * Gets true if has a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to verify
   * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
   */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to add
   */

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }
   /**
   * Removes all the CalendarRef
   */

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }
   /**
   * Removes a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to remove
   */

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

   /**
   * Gets the CalendarRef
   * @return a org.semanticwb.model.CalendarRef
   */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
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
