package org.semanticwb.process.model.base;


public abstract class WrapperTaskWebPageBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.Tagable,org.semanticwb.model.Resourceable,org.semanticwb.model.Indexable,org.semanticwb.model.Rankable,org.semanticwb.model.RoleRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Referensable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Trashable,org.semanticwb.model.Localeable,org.semanticwb.model.Countryable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Hiddenable,org.semanticwb.model.FilterableNode,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Searchable,org.semanticwb.model.RuleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Expirable
{
    public static final org.semanticwb.platform.SemanticClass swp_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
    public static final org.semanticwb.platform.SemanticProperty swp_userTaskInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#userTaskInv");
    public static final org.semanticwb.platform.SemanticClass swp_WrapperTaskWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WrapperTaskWebPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WrapperTaskWebPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of WrapperTaskWebPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.WrapperTaskWebPage for all models
       * @return Iterator of org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.WrapperTaskWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperTaskWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return A org.semanticwb.process.model.WrapperTaskWebPage
       */
        public static org.semanticwb.process.model.WrapperTaskWebPage getWrapperTaskWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WrapperTaskWebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.WrapperTaskWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperTaskWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return A org.semanticwb.process.model.WrapperTaskWebPage
       */
        public static org.semanticwb.process.model.WrapperTaskWebPage createWrapperTaskWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WrapperTaskWebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.WrapperTaskWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperTaskWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       */
        public static void removeWrapperTaskWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.WrapperTaskWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperTaskWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return true if the org.semanticwb.process.model.WrapperTaskWebPage exists, false otherwise
       */

        public static boolean hasWrapperTaskWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWrapperTaskWebPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined UserTask
       * @param value UserTask of the type org.semanticwb.process.model.UserTask
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByUserTask(org.semanticwb.process.model.UserTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_userTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined UserTask
       * @param value UserTask of the type org.semanticwb.process.model.UserTask
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByUserTask(org.semanticwb.process.model.UserTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_userTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.WrapperTaskWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperTaskWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperTaskWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperTaskWebPage> listWrapperTaskWebPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperTaskWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static WrapperTaskWebPageBase.ClassMgr getWrapperTaskWebPageClassMgr()
    {
        return new WrapperTaskWebPageBase.ClassMgr();
    }

   /**
   * Constructs a WrapperTaskWebPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WrapperTaskWebPage
   */
    public WrapperTaskWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property UserTask
   * @param value UserTask to set
   */

    public void setUserTask(org.semanticwb.process.model.UserTask value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_userTaskInv, value.getSemanticObject());
        }else
        {
            removeUserTask();
        }
    }
   /**
   * Remove the value for UserTask property
   */

    public void removeUserTask()
    {
        getSemanticObject().removeProperty(swp_userTaskInv);
    }

   /**
   * Gets the UserTask
   * @return a org.semanticwb.process.model.UserTask
   */
    public org.semanticwb.process.model.UserTask getUserTask()
    {
         org.semanticwb.process.model.UserTask ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_userTaskInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.UserTask)obj.createGenericInstance();
         }
         return ret;
    }
}
