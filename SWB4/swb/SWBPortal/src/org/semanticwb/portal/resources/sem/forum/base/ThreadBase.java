package org.semanticwb.portal.resources.sem.forum.base;


public abstract class ThreadBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Tagable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Searchable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Referensable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Countryable,org.semanticwb.model.Viewable,org.semanticwb.model.Indexable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Resourceable,org.semanticwb.model.Expirable,org.semanticwb.model.Hiddenable
{
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thForum");
    public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasUserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasUserFavThread");
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
    public static final org.semanticwb.platform.SemanticProperty frm_hasThAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThAttachments");
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thReplyCount");
    public static final org.semanticwb.platform.SemanticProperty frm_thViewCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thViewCount");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasPost");
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostDate");
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thBody");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostMember");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");

    public static class ClassMgr
    {
       /**
       * Returns a list of Thread for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.forum.Thread for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.forum.Thread createThread(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Thread.ClassMgr.createThread(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.forum.Thread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return A org.semanticwb.portal.resources.sem.forum.Thread
       */
        public static org.semanticwb.portal.resources.sem.forum.Thread getThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.forum.Thread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return A org.semanticwb.portal.resources.sem.forum.Thread
       */
        public static org.semanticwb.portal.resources.sem.forum.Thread createThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.forum.Thread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       */
        public static void removeThread(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.forum.Thread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return true if the org.semanticwb.portal.resources.sem.forum.Thread exists, false otherwise
       */

        public static boolean hasThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (getThread(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Forum
       * @param value Forum of the type org.semanticwb.portal.resources.sem.forum.SWBForum
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByForum(org.semanticwb.portal.resources.sem.forum.SWBForum value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_thForum, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Forum
       * @param value Forum of the type org.semanticwb.portal.resources.sem.forum.SWBForum
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByForum(org.semanticwb.portal.resources.sem.forum.SWBForum value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_thForum,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined UserFavThread
       * @param value UserFavThread of the type org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasUserFavThread, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined UserFavThread
       * @param value UserFavThread of the type org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_hasUserFavThread,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Attachment
       * @param value Attachment of the type org.semanticwb.portal.resources.sem.forum.Attachment
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAttachment(org.semanticwb.portal.resources.sem.forum.Attachment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasThAttachments, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Attachment
       * @param value Attachment of the type org.semanticwb.portal.resources.sem.forum.Attachment
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAttachment(org.semanticwb.portal.resources.sem.forum.Attachment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_hasThAttachments,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Post
       * @param value Post of the type org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPost(org.semanticwb.portal.resources.sem.forum.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Post
       * @param value Post of the type org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPost(org.semanticwb.portal.resources.sem.forum.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined LastPostMember
       * @param value LastPostMember of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLastPostMember(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_thLastPostMember, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined LastPostMember
       * @param value LastPostMember of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLastPostMember(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_thLastPostMember,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Thread with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Thread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ThreadBase.ClassMgr getThreadClassMgr()
    {
        return new ThreadBase.ClassMgr();
    }

   /**
   * Constructs a ThreadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Thread
   */
    public ThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Forum
   * @param value Forum to set
   */

    public void setForum(org.semanticwb.portal.resources.sem.forum.SWBForum value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_thForum, value.getSemanticObject());
        }else
        {
            removeForum();
        }
    }
   /**
   * Remove the value for Forum property
   */

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thForum);
    }

   /**
   * Gets the Forum
   * @return a org.semanticwb.portal.resources.sem.forum.SWBForum
   */
    public org.semanticwb.portal.resources.sem.forum.SWBForum getForum()
    {
         org.semanticwb.portal.resources.sem.forum.SWBForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thForum);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.SWBForum)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.forum.UserFavThread
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(getSemanticObject().listObjectProperties(frm_hasUserFavThread));
    }

   /**
   * Gets true if has a UserFavThread
   * @param value org.semanticwb.portal.resources.sem.forum.UserFavThread to verify
   * @return true if the org.semanticwb.portal.resources.sem.forum.UserFavThread exists, false otherwise
   */
    public boolean hasUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasUserFavThread,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the UserFavThread
   * @return a org.semanticwb.portal.resources.sem.forum.UserFavThread
   */
    public org.semanticwb.portal.resources.sem.forum.UserFavThread getUserFavThread()
    {
         org.semanticwb.portal.resources.sem.forum.UserFavThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasUserFavThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.UserFavThread)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.forum.Attachment
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(getSemanticObject().listObjectProperties(frm_hasThAttachments));
    }

   /**
   * Gets true if has a Attachment
   * @param value org.semanticwb.portal.resources.sem.forum.Attachment to verify
   * @return true if the org.semanticwb.portal.resources.sem.forum.Attachment exists, false otherwise
   */
    public boolean hasAttachment(org.semanticwb.portal.resources.sem.forum.Attachment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasThAttachments,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Attachment
   * @return a org.semanticwb.portal.resources.sem.forum.Attachment
   */
    public org.semanticwb.portal.resources.sem.forum.Attachment getAttachment()
    {
         org.semanticwb.portal.resources.sem.forum.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasThAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Attachment)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ReplyCount property
* @return int with the ReplyCount
*/
    public int getReplyCount()
    {
        return getSemanticObject().getIntProperty(frm_thReplyCount);
    }

/**
* Sets the ReplyCount property
* @param value long with the ReplyCount
*/
    public void setReplyCount(int value)
    {
        getSemanticObject().setIntProperty(frm_thReplyCount, value);
    }

/**
* Gets the ViewCount property
* @return int with the ViewCount
*/
    public int getViewCount()
    {
        return getSemanticObject().getIntProperty(frm_thViewCount);
    }

/**
* Sets the ViewCount property
* @param value long with the ViewCount
*/
    public void setViewCount(int value)
    {
        getSemanticObject().setIntProperty(frm_thViewCount, value);
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.forum.Post
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.forum.Post
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(getSemanticObject().listObjectProperties(frm_hasPost));
    }

   /**
   * Gets true if has a Post
   * @param value org.semanticwb.portal.resources.sem.forum.Post to verify
   * @return true if the org.semanticwb.portal.resources.sem.forum.Post exists, false otherwise
   */
    public boolean hasPost(org.semanticwb.portal.resources.sem.forum.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasPost,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Post
   * @return a org.semanticwb.portal.resources.sem.forum.Post
   */
    public org.semanticwb.portal.resources.sem.forum.Post getPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the LastPostDate property
* @return java.util.Date with the LastPostDate
*/
    public java.util.Date getLastPostDate()
    {
        return getSemanticObject().getDateProperty(frm_thLastPostDate);
    }

/**
* Sets the LastPostDate property
* @param value long with the LastPostDate
*/
    public void setLastPostDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(frm_thLastPostDate, value);
    }

/**
* Gets the Body property
* @return String with the Body
*/
    public String getBody()
    {
        return getSemanticObject().getProperty(frm_thBody);
    }

/**
* Sets the Body property
* @param value long with the Body
*/
    public void setBody(String value)
    {
        getSemanticObject().setProperty(frm_thBody, value);
    }
   /**
   * Sets the value for the property LastPostMember
   * @param value LastPostMember to set
   */

    public void setLastPostMember(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_thLastPostMember, value.getSemanticObject());
        }else
        {
            removeLastPostMember();
        }
    }
   /**
   * Remove the value for LastPostMember property
   */

    public void removeLastPostMember()
    {
        getSemanticObject().removeProperty(frm_thLastPostMember);
    }

   /**
   * Gets the LastPostMember
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getLastPostMember()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thLastPostMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
