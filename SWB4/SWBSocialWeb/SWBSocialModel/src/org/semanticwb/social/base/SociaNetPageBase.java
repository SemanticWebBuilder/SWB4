package org.semanticwb.social.base;


   /**
   * Clase de la cual desciende todo el manejo de FanPages en las redes sociales. 
   */
public abstract class SociaNetPageBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Viewable,org.semanticwb.model.Filterable,org.semanticwb.model.Localeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Referensable,org.semanticwb.model.Expirable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableNode,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Tagable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Activeable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Resourceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Rankable,org.semanticwb.model.Countryable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Searchable,org.semanticwb.model.TemplateRefable
{
   /**
   * Clase de la cual desciende todo el manejo de FanPages en las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SociaNetPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SociaNetPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SociaNetPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of SociaNetPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SociaNetPage for all models
       * @return Iterator of org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage>(it, true);
        }

        public static org.semanticwb.social.SociaNetPage createSociaNetPage(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SociaNetPage.ClassMgr.createSociaNetPage(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SociaNetPage
       * @param id Identifier for org.semanticwb.social.SociaNetPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return A org.semanticwb.social.SociaNetPage
       */
        public static org.semanticwb.social.SociaNetPage getSociaNetPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SociaNetPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SociaNetPage
       * @param id Identifier for org.semanticwb.social.SociaNetPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return A org.semanticwb.social.SociaNetPage
       */
        public static org.semanticwb.social.SociaNetPage createSociaNetPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SociaNetPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SociaNetPage
       * @param id Identifier for org.semanticwb.social.SociaNetPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       */
        public static void removeSociaNetPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SociaNetPage
       * @param id Identifier for org.semanticwb.social.SociaNetPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return true if the org.semanticwb.social.SociaNetPage exists, false otherwise
       */

        public static boolean hasSociaNetPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSociaNetPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.social.SociaNetPage
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SociaNetPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.social.SociaNetPage
       */

        public static java.util.Iterator<org.semanticwb.social.SociaNetPage> listSociaNetPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SociaNetPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SociaNetPageBase.ClassMgr getSociaNetPageClassMgr()
    {
        return new SociaNetPageBase.ClassMgr();
    }

   /**
   * Constructs a SociaNetPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SociaNetPage
   */
    public SociaNetPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
