package org.semanticwb.resources.filerepository.base;


public class RepositoryFileBase extends org.semanticwb.repository.File implements org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_hasUserGroupId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#hasUserGroupId");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#deleted");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_hasUserId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#hasUserId");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_hasRoleId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#hasRoleId");
    public static final org.semanticwb.platform.SemanticClass swbfilerep_RepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#RepositoryFile");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#RepositoryFile");

    public RepositoryFileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.resources.filerepository.RepositoryFile> listRepositoryFiles(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.filerepository.RepositoryFile>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.resources.filerepository.RepositoryFile> listRepositoryFiles()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.filerepository.RepositoryFile>(it, true);
    }

    public static org.semanticwb.resources.filerepository.RepositoryFile getRepositoryFile(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resources.filerepository.RepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.resources.filerepository.RepositoryFile createRepositoryFile(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resources.filerepository.RepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeRepositoryFile(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasRepositoryFile(String id, org.semanticwb.model.SWBModel model)
    {
        return (getRepositoryFile(id, model)!=null);
    }

    public java.util.Iterator<String> listUserGroupIds()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swbfilerep_hasUserGroupId);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addUserGroupId(String usergroupid)
    {
        getSemanticObject().setProperty(swbfilerep_hasUserGroupId, usergroupid);
    }

    public void removeAllUserGroupId()
    {
        getSemanticObject().removeProperty(swbfilerep_hasUserGroupId);
    }

    public void removeUserGroupId(String usergroupid)
    {
        getSemanticObject().removeProperty(swbfilerep_hasUserGroupId,usergroupid);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swbfilerep_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(swbfilerep_deleted, deleted);
    }

    public java.util.Iterator<String> listUserIds()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swbfilerep_hasUserId);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addUserId(String userid)
    {
        getSemanticObject().setProperty(swbfilerep_hasUserId, userid);
    }

    public void removeAllUserId()
    {
        getSemanticObject().removeProperty(swbfilerep_hasUserId);
    }

    public void removeUserId(String userid)
    {
        getSemanticObject().removeProperty(swbfilerep_hasUserId,userid);
    }

    public java.util.Iterator<String> listRoleIds()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swbfilerep_hasRoleId);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addRoleId(String roleid)
    {
        getSemanticObject().setProperty(swbfilerep_hasRoleId, roleid);
    }

    public void removeAllRoleId()
    {
        getSemanticObject().removeProperty(swbfilerep_hasRoleId);
    }

    public void removeRoleId(String roleid)
    {
        getSemanticObject().removeProperty(swbfilerep_hasRoleId,roleid);
    }
}
