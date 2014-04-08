package org.semanticwb.social.base;


   /**
   * Clase manejadora de una cuenta de Facebook para controlar páginas sociales. 
   */
public abstract class FacebookFanPageBase extends org.semanticwb.social.FanPages implements org.semanticwb.model.MetaTagable,org.semanticwb.model.Filterable,org.semanticwb.model.Tagable,org.semanticwb.model.Countryable,org.semanticwb.model.Trashable,org.semanticwb.model.Expirable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Localeable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableClass,org.semanticwb.model.RuleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Rankable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Viewable,org.semanticwb.model.Searchable,org.semanticwb.model.Referensable,org.semanticwb.model.Activeable
{
   /**
   * Facebook PageID to manage
   */
    public static final org.semanticwb.platform.SemanticProperty social_page_id=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#page_id");
   /**
   * Facebook Access token, it is returned by facebook and its used to mannage the Fan Page, meaning to link a Tab to it.
   */
    public static final org.semanticwb.platform.SemanticProperty social_pageAccessToken=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pageAccessToken");
   /**
   * Clase manejadora de una cuenta de Facebook para controlar páginas sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_FacebookFanPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacebookFanPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacebookFanPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of FacebookFanPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.FacebookFanPage for all models
       * @return Iterator of org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage>(it, true);
        }

        public static org.semanticwb.social.FacebookFanPage createFacebookFanPage(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.FacebookFanPage.ClassMgr.createFacebookFanPage(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.FacebookFanPage
       * @param id Identifier for org.semanticwb.social.FacebookFanPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return A org.semanticwb.social.FacebookFanPage
       */
        public static org.semanticwb.social.FacebookFanPage getFacebookFanPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FacebookFanPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.FacebookFanPage
       * @param id Identifier for org.semanticwb.social.FacebookFanPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return A org.semanticwb.social.FacebookFanPage
       */
        public static org.semanticwb.social.FacebookFanPage createFacebookFanPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FacebookFanPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.FacebookFanPage
       * @param id Identifier for org.semanticwb.social.FacebookFanPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       */
        public static void removeFacebookFanPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.FacebookFanPage
       * @param id Identifier for org.semanticwb.social.FacebookFanPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return true if the org.semanticwb.social.FacebookFanPage exists, false otherwise
       */

        public static boolean hasFacebookFanPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFacebookFanPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Sn_socialNet
       * @param value Sn_socialNet of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageBySn_socialNet(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_sn_socialNet, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Sn_socialNet
       * @param value Sn_socialNet of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageBySn_socialNet(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_sn_socialNet,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.social.FacebookFanPage
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacebookFanPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.social.FacebookFanPage
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookFanPage> listFacebookFanPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookFanPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FacebookFanPageBase.ClassMgr getFacebookFanPageClassMgr()
    {
        return new FacebookFanPageBase.ClassMgr();
    }

   /**
   * Constructs a FacebookFanPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FacebookFanPage
   */
    public FacebookFanPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Page_id property
* @return String with the Page_id
*/
    public String getPage_id()
    {
        return getSemanticObject().getProperty(social_page_id);
    }

/**
* Sets the Page_id property
* @param value long with the Page_id
*/
    public void setPage_id(String value)
    {
        getSemanticObject().setProperty(social_page_id, value);
    }

/**
* Gets the PageAccessToken property
* @return String with the PageAccessToken
*/
    public String getPageAccessToken()
    {
        return getSemanticObject().getProperty(social_pageAccessToken);
    }

/**
* Sets the PageAccessToken property
* @param value long with the PageAccessToken
*/
    public void setPageAccessToken(String value)
    {
        getSemanticObject().setProperty(social_pageAccessToken, value);
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
