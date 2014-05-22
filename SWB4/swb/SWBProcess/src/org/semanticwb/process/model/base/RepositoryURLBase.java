package org.semanticwb.process.model.base;


public abstract class RepositoryURLBase extends org.semanticwb.process.model.RepositoryElement implements org.semanticwb.model.Referensable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Searchable,org.semanticwb.model.Expirable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Hitable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Versionable
{
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryURL");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryURL");

    public static class ClassMgr
    {
       /**
       * Returns a list of RepositoryURL for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.RepositoryURL for all models
       * @return Iterator of org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL>(it, true);
        }

        public static org.semanticwb.process.model.RepositoryURL createRepositoryURL(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.RepositoryURL.ClassMgr.createRepositoryURL(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.RepositoryURL
       * @param id Identifier for org.semanticwb.process.model.RepositoryURL
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return A org.semanticwb.process.model.RepositoryURL
       */
        public static org.semanticwb.process.model.RepositoryURL getRepositoryURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RepositoryURL)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.RepositoryURL
       * @param id Identifier for org.semanticwb.process.model.RepositoryURL
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return A org.semanticwb.process.model.RepositoryURL
       */
        public static org.semanticwb.process.model.RepositoryURL createRepositoryURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RepositoryURL)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.RepositoryURL
       * @param id Identifier for org.semanticwb.process.model.RepositoryURL
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       */
        public static void removeRepositoryURL(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.RepositoryURL
       * @param id Identifier for org.semanticwb.process.model.RepositoryURL
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return true if the org.semanticwb.process.model.RepositoryURL exists, false otherwise
       */

        public static boolean hasRepositoryURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRepositoryURL(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined RepositoryDirectory
       * @param value RepositoryDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByRepositoryDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_repositoryDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined RepositoryDirectory
       * @param value RepositoryDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByRepositoryDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_repositoryDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined ActualVersion
       * @param value ActualVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByActualVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_actualVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined ActualVersion
       * @param value ActualVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByActualVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_actualVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined Status
       * @param value Status of the type org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByStatus(org.semanticwb.process.model.ItemAwareStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_reStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined Status
       * @param value Status of the type org.semanticwb.process.model.ItemAwareStatus
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByStatus(org.semanticwb.process.model.ItemAwareStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_reStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined OwnerUserGroup
       * @param value OwnerUserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByOwnerUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fileOwnerUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined OwnerUserGroup
       * @param value OwnerUserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByOwnerUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fileOwnerUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined LastVersion
       * @param value LastVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByLastVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_lastVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined LastVersion
       * @param value LastVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByLastVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_lastVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.RepositoryURL
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryURL with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryURL
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryURL> listRepositoryURLByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RepositoryURLBase.ClassMgr getRepositoryURLClassMgr()
    {
        return new RepositoryURLBase.ClassMgr();
    }

   /**
   * Constructs a RepositoryURLBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RepositoryURL
   */
    public RepositoryURLBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
