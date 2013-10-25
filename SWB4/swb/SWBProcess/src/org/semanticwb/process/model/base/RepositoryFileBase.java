package org.semanticwb.process.model.base;


public abstract class RepositoryFileBase extends org.semanticwb.process.model.RepositoryElement implements org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.Expirable,org.semanticwb.model.RoleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Searchable,org.semanticwb.model.Versionable,org.semanticwb.model.Hitable
{
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of RepositoryFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.RepositoryFile for all models
       * @return Iterator of org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile>(it, true);
        }

        public static org.semanticwb.process.model.RepositoryFile createRepositoryFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.RepositoryFile.ClassMgr.createRepositoryFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.RepositoryFile
       * @param id Identifier for org.semanticwb.process.model.RepositoryFile
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return A org.semanticwb.process.model.RepositoryFile
       */
        public static org.semanticwb.process.model.RepositoryFile getRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.RepositoryFile
       * @param id Identifier for org.semanticwb.process.model.RepositoryFile
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return A org.semanticwb.process.model.RepositoryFile
       */
        public static org.semanticwb.process.model.RepositoryFile createRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.RepositoryFile
       * @param id Identifier for org.semanticwb.process.model.RepositoryFile
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       */
        public static void removeRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.RepositoryFile
       * @param id Identifier for org.semanticwb.process.model.RepositoryFile
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return true if the org.semanticwb.process.model.RepositoryFile exists, false otherwise
       */

        public static boolean hasRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRepositoryFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined RepositoryDirectory
       * @param value RepositoryDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByRepositoryDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_repositoryDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined RepositoryDirectory
       * @param value RepositoryDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByRepositoryDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_repositoryDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined ActualVersion
       * @param value ActualVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByActualVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_actualVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined ActualVersion
       * @param value ActualVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByActualVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_actualVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined Status
       * @param value Status of the type org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByStatus(org.semanticwb.process.model.ItemAwareStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_reStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined Status
       * @param value Status of the type org.semanticwb.process.model.ItemAwareStatus
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByStatus(org.semanticwb.process.model.ItemAwareStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_reStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined OwnerUserGroup
       * @param value OwnerUserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByOwnerUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fileOwnerUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined OwnerUserGroup
       * @param value OwnerUserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByOwnerUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fileOwnerUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined LastVersion
       * @param value LastVersion of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByLastVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_lastVersion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined LastVersion
       * @param value LastVersion of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByLastVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_lastVersion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RepositoryFile with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.RepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFileByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RepositoryFileBase.ClassMgr getRepositoryFileClassMgr()
    {
        return new RepositoryFileBase.ClassMgr();
    }

   /**
   * Constructs a RepositoryFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RepositoryFile
   */
    public RepositoryFileBase(org.semanticwb.platform.SemanticObject base)
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
