package org.semanticwb.process.model.base;


public abstract class ProcessBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Referensable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Activeable,org.semanticwb.model.Resourceable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Expirable,org.semanticwb.model.UserGroupRefable,org.semanticwb.process.model.Containerable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.OwnerPropertyable,org.semanticwb.process.model.Callable,org.semanticwb.model.CalendarRefable,org.semanticwb.process.model.BPMNSerializable,org.semanticwb.model.Filterable,org.semanticwb.model.RoleRefable
{
   /**
   * Objeto que define un Role dentro de un repositorio de usuarios aplicable a un Usuario para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swp_administrationRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#administrationRole");
    public static final org.semanticwb.platform.SemanticClass swp_WrapperProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WrapperProcessWebPage");
    public static final org.semanticwb.platform.SemanticProperty swp_processWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processWebPage");
   /**
   * Grupo de Procesos
   */
    public static final org.semanticwb.platform.SemanticClass swp_ProcessGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessGroup");
    public static final org.semanticwb.platform.SemanticProperty swp_processGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processGroup");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessInstanceInv");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swp_parentWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentWebPage");
    public static final org.semanticwb.platform.SemanticClass swp_NotificationTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#NotificationTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_delayNotificationTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#delayNotificationTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_notificationRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#notificationRole");
    public static final org.semanticwb.platform.SemanticProperty swp_assigmentNotificationTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#assigmentNotificationTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_filterByOwnerUserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#filterByOwnerUserGroup");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");

    public static class ClassMgr
    {
       /**
       * Returns a list of Process for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Process for all models
       * @return Iterator of org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       * @return A org.semanticwb.process.model.Process
       */
        public static org.semanticwb.process.model.Process getProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       * @return A org.semanticwb.process.model.Process
       */
        public static org.semanticwb.process.model.Process createProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       */
        public static void removeProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       * @return true if the org.semanticwb.process.model.Process exists, false otherwise
       */

        public static boolean hasProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined AdministrationRole
       * @param value AdministrationRole of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByAdministrationRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_administrationRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined AdministrationRole
       * @param value AdministrationRole of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByAdministrationRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_administrationRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessWebPage
       * @param value ProcessWebPage of the type org.semanticwb.process.model.WrapperProcessWebPage
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessWebPage(org.semanticwb.process.model.WrapperProcessWebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessWebPage
       * @param value ProcessWebPage of the type org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessWebPage(org.semanticwb.process.model.WrapperProcessWebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessGroup
       * @param value ProcessGroup of the type org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessGroup(org.semanticwb.process.model.ProcessGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessGroup
       * @param value ProcessGroup of the type org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessGroup(org.semanticwb.process.model.ProcessGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessInstance
       * @param value ProcessInstance of the type org.semanticwb.process.model.ProcessInstance
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessInstance(org.semanticwb.process.model.ProcessInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessInstance
       * @param value ProcessInstance of the type org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessInstance(org.semanticwb.process.model.ProcessInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ParentWebPage
       * @param value ParentWebPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByParentWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentWebPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ParentWebPage
       * @param value ParentWebPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByParentWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentWebPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined DelayNotificationTemplate
       * @param value DelayNotificationTemplate of the type org.semanticwb.process.model.NotificationTemplate
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDelayNotificationTemplate(org.semanticwb.process.model.NotificationTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_delayNotificationTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined DelayNotificationTemplate
       * @param value DelayNotificationTemplate of the type org.semanticwb.process.model.NotificationTemplate
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDelayNotificationTemplate(org.semanticwb.process.model.NotificationTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_delayNotificationTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined NotificationRole
       * @param value NotificationRole of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByNotificationRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_notificationRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined NotificationRole
       * @param value NotificationRole of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByNotificationRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_notificationRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined AssigmentNotificationTemplate
       * @param value AssigmentNotificationTemplate of the type org.semanticwb.process.model.NotificationTemplate
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByAssigmentNotificationTemplate(org.semanticwb.process.model.NotificationTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assigmentNotificationTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined AssigmentNotificationTemplate
       * @param value AssigmentNotificationTemplate of the type org.semanticwb.process.model.NotificationTemplate
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByAssigmentNotificationTemplate(org.semanticwb.process.model.NotificationTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assigmentNotificationTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined OwnerProperty
       * @param value OwnerProperty of the type org.semanticwb.process.model.OwnerProperty
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByOwnerProperty(org.semanticwb.process.model.OwnerProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOwnerProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined OwnerProperty
       * @param value OwnerProperty of the type org.semanticwb.process.model.OwnerProperty
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByOwnerProperty(org.semanticwb.process.model.OwnerProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOwnerProperty,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessBase.ClassMgr getProcessClassMgr()
    {
        return new ProcessBase.ClassMgr();
    }

   /**
   * Constructs a ProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Process
   */
    public ProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NotInheritRoleRef property
* @return boolean with the NotInheritRoleRef
*/
    public boolean isNotInheritRoleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritRoleRef);
    }

/**
* Sets the NotInheritRoleRef property
* @param value long with the NotInheritRoleRef
*/
    public void setNotInheritRoleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritRoleRef, value);
    }
   /**
   * Sets the value for the property AdministrationRole
   * @param value AdministrationRole to set
   */

    public void setAdministrationRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_administrationRole, value.getSemanticObject());
        }else
        {
            removeAdministrationRole();
        }
    }
   /**
   * Remove the value for AdministrationRole property
   */

    public void removeAdministrationRole()
    {
        getSemanticObject().removeProperty(swp_administrationRole);
    }

   /**
   * Gets the AdministrationRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getAdministrationRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_administrationRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessWebPage
   * @param value ProcessWebPage to set
   */

    public void setProcessWebPage(org.semanticwb.process.model.WrapperProcessWebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processWebPage, value.getSemanticObject());
        }else
        {
            removeProcessWebPage();
        }
    }
   /**
   * Remove the value for ProcessWebPage property
   */

    public void removeProcessWebPage()
    {
        getSemanticObject().removeProperty(swp_processWebPage);
    }

   /**
   * Gets the ProcessWebPage
   * @return a org.semanticwb.process.model.WrapperProcessWebPage
   */
    public org.semanticwb.process.model.WrapperProcessWebPage getProcessWebPage()
    {
         org.semanticwb.process.model.WrapperProcessWebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.WrapperProcessWebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessGroup
   * @param value ProcessGroup to set
   */

    public void setProcessGroup(org.semanticwb.process.model.ProcessGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processGroup, value.getSemanticObject());
        }else
        {
            removeProcessGroup();
        }
    }
   /**
   * Remove the value for ProcessGroup property
   */

    public void removeProcessGroup()
    {
        getSemanticObject().removeProperty(swp_processGroup);
    }

   /**
   * Gets the ProcessGroup
   * @return a org.semanticwb.process.model.ProcessGroup
   */
    public org.semanticwb.process.model.ProcessGroup getProcessGroup()
    {
         org.semanticwb.process.model.ProcessGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessGroup)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(getSemanticObject().listObjectProperties(swp_hasProcessInstanceInv));
    }

   /**
   * Gets true if has a ProcessInstance
   * @param value org.semanticwb.process.model.ProcessInstance to verify
   * @return true if the org.semanticwb.process.model.ProcessInstance exists, false otherwise
   */
    public boolean hasProcessInstance(org.semanticwb.process.model.ProcessInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessInstanceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ProcessInstance
   * @return a org.semanticwb.process.model.ProcessInstance
   */
    public org.semanticwb.process.model.ProcessInstance getProcessInstance()
    {
         org.semanticwb.process.model.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ParentWebPage
   * @param value ParentWebPage to set
   */

    public void setParentWebPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_parentWebPage, value.getSemanticObject());
        }else
        {
            removeParentWebPage();
        }
    }
   /**
   * Remove the value for ParentWebPage property
   */

    public void removeParentWebPage()
    {
        getSemanticObject().removeProperty(swp_parentWebPage);
    }

   /**
   * Gets the ParentWebPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getParentWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parentWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DelayNotificationTemplate
   * @param value DelayNotificationTemplate to set
   */

    public void setDelayNotificationTemplate(org.semanticwb.process.model.NotificationTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_delayNotificationTemplate, value.getSemanticObject());
        }else
        {
            removeDelayNotificationTemplate();
        }
    }
   /**
   * Remove the value for DelayNotificationTemplate property
   */

    public void removeDelayNotificationTemplate()
    {
        getSemanticObject().removeProperty(swp_delayNotificationTemplate);
    }

   /**
   * Gets the DelayNotificationTemplate
   * @return a org.semanticwb.process.model.NotificationTemplate
   */
    public org.semanticwb.process.model.NotificationTemplate getDelayNotificationTemplate()
    {
         org.semanticwb.process.model.NotificationTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_delayNotificationTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.NotificationTemplate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property NotificationRole
   * @param value NotificationRole to set
   */

    public void setNotificationRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_notificationRole, value.getSemanticObject());
        }else
        {
            removeNotificationRole();
        }
    }
   /**
   * Remove the value for NotificationRole property
   */

    public void removeNotificationRole()
    {
        getSemanticObject().removeProperty(swp_notificationRole);
    }

   /**
   * Gets the NotificationRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getNotificationRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_notificationRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property AssigmentNotificationTemplate
   * @param value AssigmentNotificationTemplate to set
   */

    public void setAssigmentNotificationTemplate(org.semanticwb.process.model.NotificationTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_assigmentNotificationTemplate, value.getSemanticObject());
        }else
        {
            removeAssigmentNotificationTemplate();
        }
    }
   /**
   * Remove the value for AssigmentNotificationTemplate property
   */

    public void removeAssigmentNotificationTemplate()
    {
        getSemanticObject().removeProperty(swp_assigmentNotificationTemplate);
    }

   /**
   * Gets the AssigmentNotificationTemplate
   * @return a org.semanticwb.process.model.NotificationTemplate
   */
    public org.semanticwb.process.model.NotificationTemplate getAssigmentNotificationTemplate()
    {
         org.semanticwb.process.model.NotificationTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_assigmentNotificationTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.NotificationTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NotInheritUserGroupRef property
* @return boolean with the NotInheritUserGroupRef
*/
    public boolean isNotInheritUserGroupRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritUserGroupRef);
    }

/**
* Sets the NotInheritUserGroupRef property
* @param value long with the NotInheritUserGroupRef
*/
    public void setNotInheritUserGroupRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritUserGroupRef, value);
    }

/**
* Gets the Expiration property
* @return java.util.Date with the Expiration
*/
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
* Sets the Expiration property
* @param value long with the Expiration
*/
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
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
* Gets the NotInheritRuleRef property
* @return boolean with the NotInheritRuleRef
*/
    public boolean isNotInheritRuleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritRuleRef);
    }

/**
* Sets the NotInheritRuleRef property
* @param value long with the NotInheritRuleRef
*/
    public void setNotInheritRuleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritRuleRef, value);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   /**
   * Gets all the org.semanticwb.model.RuleRef
   * @return A GenericIterator with all the org.semanticwb.model.RuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

   /**
   * Gets true if has a RuleRef
   * @param value org.semanticwb.model.RuleRef to verify
   * @return true if the org.semanticwb.model.RuleRef exists, false otherwise
   */
    public boolean hasRuleRef(org.semanticwb.model.RuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the RuleRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.RuleRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }
   /**
   * Adds a RuleRef
   * @param value org.semanticwb.model.RuleRef to add
   */

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }
   /**
   * Removes all the RuleRef
   */

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }
   /**
   * Removes a RuleRef
   * @param value org.semanticwb.model.RuleRef to remove
   */

    public void removeRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,value.getSemanticObject());
    }

   /**
   * Gets the RuleRef
   * @return a org.semanticwb.model.RuleRef
   */
    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
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
* Gets the FilterByOwnerUserGroup property
* @return boolean with the FilterByOwnerUserGroup
*/
    public boolean isFilterByOwnerUserGroup()
    {
        return getSemanticObject().getBooleanProperty(swp_filterByOwnerUserGroup);
    }

/**
* Sets the FilterByOwnerUserGroup property
* @param value long with the FilterByOwnerUserGroup
*/
    public void setFilterByOwnerUserGroup(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_filterByOwnerUserGroup, value);
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
   * Gets all the org.semanticwb.process.model.OwnerProperty
   * @return A GenericIterator with all the org.semanticwb.process.model.OwnerProperty
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OwnerProperty> listOwnerProperties()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OwnerProperty>(getSemanticObject().listObjectProperties(swp_hasOwnerProperty));
    }

   /**
   * Gets true if has a OwnerProperty
   * @param value org.semanticwb.process.model.OwnerProperty to verify
   * @return true if the org.semanticwb.process.model.OwnerProperty exists, false otherwise
   */
    public boolean hasOwnerProperty(org.semanticwb.process.model.OwnerProperty value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOwnerProperty,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a OwnerProperty
   * @param value org.semanticwb.process.model.OwnerProperty to add
   */

    public void addOwnerProperty(org.semanticwb.process.model.OwnerProperty value)
    {
        getSemanticObject().addObjectProperty(swp_hasOwnerProperty, value.getSemanticObject());
    }
   /**
   * Removes all the OwnerProperty
   */

    public void removeAllOwnerProperty()
    {
        getSemanticObject().removeProperty(swp_hasOwnerProperty);
    }
   /**
   * Removes a OwnerProperty
   * @param value org.semanticwb.process.model.OwnerProperty to remove
   */

    public void removeOwnerProperty(org.semanticwb.process.model.OwnerProperty value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOwnerProperty,value.getSemanticObject());
    }

   /**
   * Gets the OwnerProperty
   * @return a org.semanticwb.process.model.OwnerProperty
   */
    public org.semanticwb.process.model.OwnerProperty getOwnerProperty()
    {
         org.semanticwb.process.model.OwnerProperty ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOwnerProperty);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OwnerProperty)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.GraphicalElement
   * @return A GenericIterator with all the org.semanticwb.process.model.GraphicalElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listContaineds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasContainedInv));
    }

   /**
   * Gets true if has a Contained
   * @param value org.semanticwb.process.model.GraphicalElement to verify
   * @return true if the org.semanticwb.process.model.GraphicalElement exists, false otherwise
   */
    public boolean hasContained(org.semanticwb.process.model.GraphicalElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasContainedInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Contained
   * @return a org.semanticwb.process.model.GraphicalElement
   */
    public org.semanticwb.process.model.GraphicalElement getContained()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasContainedInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AndEvalUserGroupRef property
* @return boolean with the AndEvalUserGroupRef
*/
    public boolean isAndEvalUserGroupRef()
    {
        return getSemanticObject().getBooleanProperty(swb_andEvalUserGroupRef);
    }

/**
* Sets the AndEvalUserGroupRef property
* @param value long with the AndEvalUserGroupRef
*/
    public void setAndEvalUserGroupRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_andEvalUserGroupRef, value);
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
   * Gets all the org.semanticwb.model.UserGroupRef
   * @return A GenericIterator with all the org.semanticwb.model.UserGroupRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

   /**
   * Gets true if has a UserGroupRef
   * @param value org.semanticwb.model.UserGroupRef to verify
   * @return true if the org.semanticwb.model.UserGroupRef exists, false otherwise
   */
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the UserGroupRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.UserGroupRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }
   /**
   * Adds a UserGroupRef
   * @param value org.semanticwb.model.UserGroupRef to add
   */

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }
   /**
   * Removes all the UserGroupRef
   */

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }
   /**
   * Removes a UserGroupRef
   * @param value org.semanticwb.model.UserGroupRef to remove
   */

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
    }

   /**
   * Gets the UserGroupRef
   * @return a org.semanticwb.model.UserGroupRef
   */
    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Format property
* @return String with the Format
*/
    public String getFormat()
    {
        return getSemanticObject().getProperty(swp_serializationFormat);
    }

/**
* Sets the Format property
* @param value long with the Format
*/
    public void setFormat(String value)
    {
        getSemanticObject().setProperty(swp_serializationFormat, value);
    }
   /**
   * Gets all the org.semanticwb.model.RoleRef
   * @return A GenericIterator with all the org.semanticwb.model.RoleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

   /**
   * Gets true if has a RoleRef
   * @param value org.semanticwb.model.RoleRef to verify
   * @return true if the org.semanticwb.model.RoleRef exists, false otherwise
   */
    public boolean hasRoleRef(org.semanticwb.model.RoleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the RoleRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.RoleRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }
   /**
   * Adds a RoleRef
   * @param value org.semanticwb.model.RoleRef to add
   */

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }
   /**
   * Removes all the RoleRef
   */

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }
   /**
   * Removes a RoleRef
   * @param value org.semanticwb.model.RoleRef to remove
   */

    public void removeRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,value.getSemanticObject());
    }

   /**
   * Gets the RoleRef
   * @return a org.semanticwb.model.RoleRef
   */
    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
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
* Gets the AndEvalRoleRef property
* @return boolean with the AndEvalRoleRef
*/
    public boolean isAndEvalRoleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_andEvalRoleRef);
    }

/**
* Sets the AndEvalRoleRef property
* @param value long with the AndEvalRoleRef
*/
    public void setAndEvalRoleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_andEvalRoleRef, value);
    }

/**
* Gets the Data property
* @return String with the Data
*/
    public String getData()
    {
        return getSemanticObject().getProperty(swp_serializationData);
    }

/**
* Sets the Data property
* @param value long with the Data
*/
    public void setData(String value)
    {
        getSemanticObject().setProperty(swp_serializationData, value);
    }

/**
* Gets the AndEvalRuleRef property
* @return boolean with the AndEvalRuleRef
*/
    public boolean isAndEvalRuleRef()
    {
        return getSemanticObject().getBooleanProperty(swb_andEvalRuleRef);
    }

/**
* Sets the AndEvalRuleRef property
* @param value long with the AndEvalRuleRef
*/
    public void setAndEvalRuleRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_andEvalRuleRef, value);
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
