package org.semanticwb.social.base;


public abstract class FanPagesBase extends org.semanticwb.social.SociaNetPage implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Searchable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Rankable,org.semanticwb.model.Viewable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Countryable,org.semanticwb.model.Indexable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Expirable,org.semanticwb.model.Resourceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Tagable,org.semanticwb.model.Localeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.Hiddenable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Activeable
{
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Cuenta de SocialNetwork a la cual esta haciendose referencia para su manejo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_sn_socialNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#sn_socialNet");
    public static final org.semanticwb.platform.SemanticClass social_FanPages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FanPages");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FanPages");

    public static class ClassMgr
    {
       /**
       * Returns a list of FanPages for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPageses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.FanPages for all models
       * @return Iterator of org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPageses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.FanPages
       * @param id Identifier for org.semanticwb.social.FanPages
       * @param model Model of the org.semanticwb.social.FanPages
       * @return A org.semanticwb.social.FanPages
       */
        public static org.semanticwb.social.FanPages getFanPages(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FanPages)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.FanPages
       * @param id Identifier for org.semanticwb.social.FanPages
       * @param model Model of the org.semanticwb.social.FanPages
       * @return A org.semanticwb.social.FanPages
       */
        public static org.semanticwb.social.FanPages createFanPages(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FanPages)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.FanPages
       * @param id Identifier for org.semanticwb.social.FanPages
       * @param model Model of the org.semanticwb.social.FanPages
       */
        public static void removeFanPages(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.FanPages
       * @param id Identifier for org.semanticwb.social.FanPages
       * @param model Model of the org.semanticwb.social.FanPages
       * @return true if the org.semanticwb.social.FanPages exists, false otherwise
       */

        public static boolean hasFanPages(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFanPages(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Sn_socialNet
       * @param value Sn_socialNet of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesBySn_socialNet(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_sn_socialNet, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Sn_socialNet
       * @param value Sn_socialNet of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesBySn_socialNet(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_sn_socialNet,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.social.FanPages
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FanPages with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.social.FanPages
       */

        public static java.util.Iterator<org.semanticwb.social.FanPages> listFanPagesByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FanPages> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FanPagesBase.ClassMgr getFanPagesClassMgr()
    {
        return new FanPagesBase.ClassMgr();
    }

   /**
   * Constructs a FanPagesBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FanPages
   */
    public FanPagesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Sn_socialNet
   * @param value Sn_socialNet to set
   */

    public void setSn_socialNet(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_sn_socialNet, value.getSemanticObject());
        }else
        {
            removeSn_socialNet();
        }
    }
   /**
   * Remove the value for Sn_socialNet property
   */

    public void removeSn_socialNet()
    {
        getSemanticObject().removeProperty(social_sn_socialNet);
    }

   /**
   * Gets the Sn_socialNet
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSn_socialNet()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_sn_socialNet);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
}
