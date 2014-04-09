package org.semanticwb.social.base;


   /**
   * Clase que controla un tab de page en Facebook 
   */
public abstract class FacePageTabBase extends org.semanticwb.social.PageTab implements org.semanticwb.model.Searchable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable,org.semanticwb.model.Rankable,org.semanticwb.model.Localeable,org.semanticwb.model.Viewable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Indexable,org.semanticwb.model.RoleRefable,org.semanticwb.model.FilterableNode,org.semanticwb.model.TemplateRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Activeable,org.semanticwb.model.Expirable,org.semanticwb.model.Resourceable,org.semanticwb.model.Tagable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Countryable,org.semanticwb.model.Hiddenable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Filterable
{
   /**
   * Facebook Aplication ID
   */
    public static final org.semanticwb.platform.SemanticProperty social_face_appid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#face_appid");
   /**
   * Antiguo id de aplicación, este se buscaría para eliminarlo de la página de facebook.
   */
    public static final org.semanticwb.platform.SemanticProperty social_face_old_appid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#face_old_appid");
   /**
   * Clase que controla un tab de page en Facebook
   */
    public static final org.semanticwb.platform.SemanticClass social_FacePageTab=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacePageTab");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacePageTab");

    public static class ClassMgr
    {
       /**
       * Returns a list of FacePageTab for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.FacePageTab for all models
       * @return Iterator of org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab>(it, true);
        }

        public static org.semanticwb.social.FacePageTab createFacePageTab(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.FacePageTab.ClassMgr.createFacePageTab(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.FacePageTab
       * @param id Identifier for org.semanticwb.social.FacePageTab
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return A org.semanticwb.social.FacePageTab
       */
        public static org.semanticwb.social.FacePageTab getFacePageTab(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FacePageTab)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.FacePageTab
       * @param id Identifier for org.semanticwb.social.FacePageTab
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return A org.semanticwb.social.FacePageTab
       */
        public static org.semanticwb.social.FacePageTab createFacePageTab(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FacePageTab)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.FacePageTab
       * @param id Identifier for org.semanticwb.social.FacePageTab
       * @param model Model of the org.semanticwb.social.FacePageTab
       */
        public static void removeFacePageTab(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.FacePageTab
       * @param id Identifier for org.semanticwb.social.FacePageTab
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return true if the org.semanticwb.social.FacePageTab exists, false otherwise
       */

        public static boolean hasFacePageTab(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFacePageTab(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.social.FacePageTab
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FacePageTab with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.social.FacePageTab
       */

        public static java.util.Iterator<org.semanticwb.social.FacePageTab> listFacePageTabByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FacePageTab> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FacePageTabBase.ClassMgr getFacePageTabClassMgr()
    {
        return new FacePageTabBase.ClassMgr();
    }

   /**
   * Constructs a FacePageTabBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FacePageTab
   */
    public FacePageTabBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Face_appid property
* @return String with the Face_appid
*/
    public String getFace_appid()
    {
        return getSemanticObject().getProperty(social_face_appid);
    }

/**
* Sets the Face_appid property
* @param value long with the Face_appid
*/
    public void setFace_appid(String value)
    {
        getSemanticObject().setProperty(social_face_appid, value);
    }

/**
* Gets the Face_old_appid property
* @return String with the Face_old_appid
*/
    public String getFace_old_appid()
    {
        return getSemanticObject().getProperty(social_face_old_appid);
    }

/**
* Sets the Face_old_appid property
* @param value long with the Face_old_appid
*/
    public void setFace_old_appid(String value)
    {
        getSemanticObject().setProperty(social_face_old_appid, value);
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
