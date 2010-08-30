package org.semanticwb.model.base;


   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal. 
   */
public abstract class WebPageBase extends org.semanticwb.model.Topic implements org.semanticwb.model.Searchable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Viewable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Resourceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Localeable,org.semanticwb.model.Tagable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Indexable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Rankable,org.semanticwb.model.FilterableNode,org.semanticwb.model.RuleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Expirable,org.semanticwb.model.TemplateRefable
{
    public static final org.semanticwb.platform.SemanticProperty swb_webPageSortName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageSortName");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageURLType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURLType");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualParent");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageTarget");
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualChild");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageIconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageIconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageChild");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageParent");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageDiskUsage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageDiskUsage");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURL");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of WebPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.WebPage for all models
       * @return Iterator of org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.WebPage
       * @param id Identifier for org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return A org.semanticwb.model.WebPage
       */
        public static org.semanticwb.model.WebPage getWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.WebPage
       * @param id Identifier for org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return A org.semanticwb.model.WebPage
       */
        public static org.semanticwb.model.WebPage createWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.WebPage
       * @param id Identifier for org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       */
        public static void removeWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.WebPage
       * @param id Identifier for org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return true if the org.semanticwb.model.WebPage exists, false otherwise
       */

        public static boolean hasWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWebPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.WebPage
       */

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a WebPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WebPage
   */
    public WebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
* Gets the Reviews property
* @return long with the Reviews
*/
    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

/**
* Sets the Reviews property
* @param value long with the Reviews
*/
    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(swb_reviews, value);
    }

/**
* Gets the Rank property
* @return double with the Rank
*/
    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

/**
* Sets the Rank property
* @param value long with the Rank
*/
    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(swb_rank, value);
    }

/**
* Gets the SortName property
* @return String with the SortName
*/
    public String getSortName()
    {
        return getSemanticObject().getProperty(swb_webPageSortName);
    }

/**
* Sets the SortName property
* @param value long with the SortName
*/
    public void setSortName(String value)
    {
        getSemanticObject().setProperty(swb_webPageSortName, value);
    }

/**
* Gets the WebPageURLType property
* @return int with the WebPageURLType
*/
    public int getWebPageURLType()
    {
        return getSemanticObject().getIntProperty(swb_webPageURLType);
    }

/**
* Sets the WebPageURLType property
* @param value long with the WebPageURLType
*/
    public void setWebPageURLType(int value)
    {
        getSemanticObject().setIntProperty(swb_webPageURLType, value);
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
   * Gets all the org.semanticwb.model.WebPage
   * @return A GenericIterator with all the org.semanticwb.model.WebPage
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageVirtualParent));
    }

   /**
   * Gets true if has a VirtualParent
   * @param value org.semanticwb.model.WebPage to verify
   * @return true if the org.semanticwb.model.WebPage exists, false otherwise
   */
    public boolean hasVirtualParent(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageVirtualParent,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a VirtualParent
   * @param value org.semanticwb.model.WebPage to add
   */

    public void addVirtualParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(swb_hasWebPageVirtualParent, value.getSemanticObject());
    }
   /**
   * Removes all the VirtualParent
   */

    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(swb_hasWebPageVirtualParent);
    }
   /**
   * Removes a VirtualParent
   * @param value org.semanticwb.model.WebPage to remove
   */

    public void removeVirtualParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().removeObjectProperty(swb_hasWebPageVirtualParent,value.getSemanticObject());
    }

   /**
   * Gets the VirtualParent
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getVirtualParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
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
* Gets the Target property
* @return String with the Target
*/
    public String getTarget()
    {
        return getSemanticObject().getProperty(swb_webPageTarget);
    }

/**
* Sets the Target property
* @param value long with the Target
*/
    public void setTarget(String value)
    {
        getSemanticObject().setProperty(swb_webPageTarget, value);
    }
   /**
   * Gets all the org.semanticwb.model.WebPage
   * @return A GenericIterator with all the org.semanticwb.model.WebPage
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageVirtualChild));
    }

   /**
   * Gets true if has a WebPageVirtualChild
   * @param value org.semanticwb.model.WebPage to verify
   * @return true if the org.semanticwb.model.WebPage exists, false otherwise
   */
    public boolean hasWebPageVirtualChild(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageVirtualChild,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the WebPageVirtualChild
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getWebPageVirtualChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the IconClass property
* @return String with the IconClass
*/
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_webPageIconClass);
    }

/**
* Sets the IconClass property
* @param value long with the IconClass
*/
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_webPageIconClass, value);
    }
   /**
   * Sets the value for the property Language
   * @param value Language to set
   */

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }
   /**
   * Remove the value for Language property
   */

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

   /**
   * Gets the Language
   * @return a org.semanticwb.model.Language
   */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.model.PFlowRef
   * @return A GenericIterator with all the org.semanticwb.model.PFlowRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listObjectProperties(swb_hasPFlowRef));
    }

   /**
   * Gets true if has a PFlowRef
   * @param value org.semanticwb.model.PFlowRef to verify
   * @return true if the org.semanticwb.model.PFlowRef exists, false otherwise
   */
    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPFlowRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the PFlowRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.PFlowRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listInheritProperties(swb_hasPFlowRef));
    }
   /**
   * Adds a PFlowRef
   * @param value org.semanticwb.model.PFlowRef to add
   */

    public void addPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasPFlowRef, value.getSemanticObject());
    }
   /**
   * Removes all the PFlowRef
   */

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(swb_hasPFlowRef);
    }
   /**
   * Removes a PFlowRef
   * @param value org.semanticwb.model.PFlowRef to remove
   */

    public void removePFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasPFlowRef,value.getSemanticObject());
    }

   /**
   * Gets the PFlowRef
   * @return a org.semanticwb.model.PFlowRef
   */
    public org.semanticwb.model.PFlowRef getPFlowRef()
    {
         org.semanticwb.model.PFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.WebPage
   * @return A GenericIterator with all the org.semanticwb.model.WebPage
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageChild));
    }

   /**
   * Gets true if has a Child
   * @param value org.semanticwb.model.WebPage to verify
   * @return true if the org.semanticwb.model.WebPage exists, false otherwise
   */
    public boolean hasChild(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageChild,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Child
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
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
   * Sets the value for the property Parent
   * @param value Parent to set
   */

    public void setParent(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_webPageParent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }
   /**
   * Remove the value for Parent property
   */

    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_webPageParent);
    }

   /**
   * Gets the Parent
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_webPageParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
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
* Gets the MaxViews property
* @return long with the MaxViews
*/
    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

/**
* Sets the MaxViews property
* @param value long with the MaxViews
*/
    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
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
* Gets the Hidden property
* @return boolean with the Hidden
*/
    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swb_hidden);
    }

/**
* Sets the Hidden property
* @param value long with the Hidden
*/
    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_hidden, value);
    }

/**
* Gets the Indexable property
* @return boolean with the Indexable
*/
    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

/**
* Sets the Indexable property
* @param value long with the Indexable
*/
    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }

/**
* Gets the DiskUsage property
* @return long with the DiskUsage
*/
    public long getDiskUsage()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(swb_webPageDiskUsage,false);
    }

/**
* Sets the DiskUsage property
* @param value long with the DiskUsage
*/
    public void setDiskUsage(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(swb_webPageDiskUsage, value,false);
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
* Gets the Views property
* @return long with the Views
*/
    public long getViews()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

/**
* Sets the Views property
* @param value long with the Views
*/
    public void setViews(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(swb_views, value,false);
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
* Gets the WebPageURL property
* @return String with the WebPageURL
*/
    public String getWebPageURL()
    {
        return getSemanticObject().getProperty(swb_webPageURL);
    }

/**
* Sets the WebPageURL property
* @param value long with the WebPageURL
*/
    public void setWebPageURL(String value)
    {
        getSemanticObject().setProperty(swb_webPageURL, value);
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
