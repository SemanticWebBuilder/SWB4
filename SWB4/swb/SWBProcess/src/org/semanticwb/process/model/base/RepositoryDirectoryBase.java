package org.semanticwb.process.model.base;


public abstract class RepositoryDirectoryBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.RoleRefable,org.semanticwb.model.Viewable,org.semanticwb.model.Countryable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Indexable,org.semanticwb.process.model.OwnerPropertyable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Searchable,org.semanticwb.model.Resourceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.Expirable,org.semanticwb.model.Localeable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Rankable,org.semanticwb.model.Trashable,org.semanticwb.model.Tagable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Hiddenable,org.semanticwb.model.PFlowRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRepositoryElementInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRepositoryElementInv");
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryDirectory");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryDirectory");

    public static class ClassMgr
    {
       /**
       * Returns a list of RepositoryDirectory for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.RepositoryDirectory for all models
       * @return Iterator of org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.RepositoryDirectory
       * @param id Identifier for org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return A org.semanticwb.process.model.RepositoryDirectory
       */
        public static org.semanticwb.process.model.RepositoryDirectory getRepositoryDirectory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RepositoryDirectory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.RepositoryDirectory
       * @param id Identifier for org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return A org.semanticwb.process.model.RepositoryDirectory
       */
        public static org.semanticwb.process.model.RepositoryDirectory createRepositoryDirectory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RepositoryDirectory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.RepositoryDirectory
       * @param id Identifier for org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       */
        public static void removeRepositoryDirectory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.RepositoryDirectory
       * @param id Identifier for org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return true if the org.semanticwb.process.model.RepositoryDirectory exists, false otherwise
       */

        public static boolean hasRepositoryDirectory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRepositoryDirectory(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined RepositoryElement
       * @param value RepositoryElement of the type org.semanticwb.process.model.RepositoryElement
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByRepositoryElement(org.semanticwb.process.model.RepositoryElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRepositoryElementInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined RepositoryElement
       * @param value RepositoryElement of the type org.semanticwb.process.model.RepositoryElement
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByRepositoryElement(org.semanticwb.process.model.RepositoryElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRepositoryElementInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined OwnerProperty
       * @param value OwnerProperty of the type org.semanticwb.process.model.OwnerProperty
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByOwnerProperty(org.semanticwb.process.model.OwnerProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOwnerProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined OwnerProperty
       * @param value OwnerProperty of the type org.semanticwb.process.model.OwnerProperty
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByOwnerProperty(org.semanticwb.process.model.OwnerProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOwnerProperty,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryDirectory with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryDirectory
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryDirectory> listRepositoryDirectoryByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryDirectory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RepositoryDirectoryBase.ClassMgr getRepositoryDirectoryClassMgr()
    {
        return new RepositoryDirectoryBase.ClassMgr();
    }

   /**
   * Constructs a RepositoryDirectoryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RepositoryDirectory
   */
    public RepositoryDirectoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.RepositoryElement
   * @return A GenericIterator with all the org.semanticwb.process.model.RepositoryElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryElement> listRepositoryElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryElement>(getSemanticObject().listObjectProperties(swp_hasRepositoryElementInv));
    }

   /**
   * Gets true if has a RepositoryElement
   * @param value org.semanticwb.process.model.RepositoryElement to verify
   * @return true if the org.semanticwb.process.model.RepositoryElement exists, false otherwise
   */
    public boolean hasRepositoryElement(org.semanticwb.process.model.RepositoryElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRepositoryElementInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the RepositoryElement
   * @return a org.semanticwb.process.model.RepositoryElement
   */
    public org.semanticwb.process.model.RepositoryElement getRepositoryElement()
    {
         org.semanticwb.process.model.RepositoryElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRepositoryElementInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.RepositoryElement)obj.createGenericInstance();
         }
         return ret;
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
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
