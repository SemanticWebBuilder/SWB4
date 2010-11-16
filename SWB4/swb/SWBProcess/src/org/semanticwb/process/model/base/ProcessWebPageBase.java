package org.semanticwb.process.model.base;


public abstract class ProcessWebPageBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Trashable,org.semanticwb.model.Resourceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Localeable,org.semanticwb.model.Tagable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Expirable,org.semanticwb.model.Indexable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Searchable,org.semanticwb.model.PFlowRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#process");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessWebPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessWebPage for all models
       * @return Iterator of org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.ProcessWebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return A org.semanticwb.process.model.ProcessWebPage
       */
        public static org.semanticwb.process.model.ProcessWebPage getProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessWebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.ProcessWebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return A org.semanticwb.process.model.ProcessWebPage
       */
        public static org.semanticwb.process.model.ProcessWebPage createProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessWebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.ProcessWebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       */
        public static void removeProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.ProcessWebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return true if the org.semanticwb.process.model.ProcessWebPage exists, false otherwise
       */

        public static boolean hasProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessWebPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_process, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_process,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.process.model.ProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ProcessWebPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessWebPage
   */
    public ProcessWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Process
   * @param value Process to set
   */

    public void setProcess(org.semanticwb.process.model.Process value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_process, value.getSemanticObject());
        }else
        {
            removeProcess();
        }
    }
   /**
   * Remove the value for Process property
   */

    public void removeProcess()
    {
        getSemanticObject().removeProperty(swp_process);
    }

   /**
   * Gets the Process
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_process);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
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
