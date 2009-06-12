package org.semanticwb.resources.filerepository.base;


public class RepositoryNodeBase extends org.semanticwb.repository.Folder implements org.semanticwb.repository.Referenceable,org.semanticwb.model.Descriptiveable,org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_hasUserGroupId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#hasUserGroupId");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_hasUserId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#hasUserId");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_hasRoleId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#hasRoleId");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swbfilerep_RepositoryNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#RepositoryNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#RepositoryNode");

    public RepositoryNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.resources.filerepository.RepositoryNode> listRepositoryNodes(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.filerepository.RepositoryNode>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.resources.filerepository.RepositoryNode> listRepositoryNodes()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.filerepository.RepositoryNode>(it, true);
    }

    public static org.semanticwb.resources.filerepository.RepositoryNode getRepositoryNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resources.filerepository.RepositoryNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.resources.filerepository.RepositoryNode createRepositoryNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resources.filerepository.RepositoryNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeRepositoryNode(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasRepositoryNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (getRepositoryNode(id, model)!=null);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
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

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
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

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
