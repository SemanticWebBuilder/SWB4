package org.semanticwb.process.model.base;


public abstract class WrapperProcessWebPageBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.MetaTagable,org.semanticwb.model.Localeable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Referensable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Tagable,org.semanticwb.model.Countryable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableNode,org.semanticwb.model.RuleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Resourceable,org.semanticwb.model.Searchable,org.semanticwb.model.Hiddenable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Viewable,org.semanticwb.model.Filterable,org.semanticwb.model.RoleRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_processInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processInv");
    public static final org.semanticwb.platform.SemanticClass swp_WrapperProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WrapperProcessWebPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WrapperProcessWebPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of WrapperProcessWebPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.WrapperProcessWebPage for all models
       * @return Iterator of org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.WrapperProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperProcessWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return A org.semanticwb.process.model.WrapperProcessWebPage
       */
        public static org.semanticwb.process.model.WrapperProcessWebPage getWrapperProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WrapperProcessWebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.WrapperProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperProcessWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return A org.semanticwb.process.model.WrapperProcessWebPage
       */
        public static org.semanticwb.process.model.WrapperProcessWebPage createWrapperProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WrapperProcessWebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.WrapperProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperProcessWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       */
        public static void removeWrapperProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.WrapperProcessWebPage
       * @param id Identifier for org.semanticwb.process.model.WrapperProcessWebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return true if the org.semanticwb.process.model.WrapperProcessWebPage exists, false otherwise
       */

        public static boolean hasWrapperProcessWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWrapperProcessWebPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.WrapperProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WrapperProcessWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.WrapperProcessWebPage
       */

        public static java.util.Iterator<org.semanticwb.process.model.WrapperProcessWebPage> listWrapperProcessWebPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WrapperProcessWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static WrapperProcessWebPageBase.ClassMgr getWrapperProcessWebPageClassMgr()
    {
        return new WrapperProcessWebPageBase.ClassMgr();
    }

   /**
   * Constructs a WrapperProcessWebPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WrapperProcessWebPage
   */
    public WrapperProcessWebPageBase(org.semanticwb.platform.SemanticObject base)
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
            getSemanticObject().setObjectProperty(swp_processInv, value.getSemanticObject());
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
        getSemanticObject().removeProperty(swp_processInv);
    }

   /**
   * Gets the Process
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }
}
