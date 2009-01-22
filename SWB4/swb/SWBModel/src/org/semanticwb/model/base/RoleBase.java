package org.semanticwb.model.base;


public class RoleBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Unmodifiable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRefInv");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRoleChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleChild");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticClass swb_Permission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Permission");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPermission");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticProperty swb_readOnly=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#readOnly");
    public static final org.semanticwb.platform.SemanticProperty swb_roleParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#roleParent");


    public static org.semanticwb.model.Role createRole(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Role)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swb_Role), swb_Role);
    }

    public RoleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, getSemanticObject().listObjectProperties(swb_hasRoleRefInv));
    }

    public boolean hasRoleRefInv(org.semanticwb.model.RoleRef roleref)
    {
        if(roleref==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasRoleRefInv,roleref.getSemanticObject());
    }

    public org.semanticwb.model.RoleRef getRoleRefInv()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, getSemanticObject().listObjectProperties(swb_hasRoleChild));
    }

    public boolean hasChild(org.semanticwb.model.Role role)
    {
        if(role==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasRoleChild,role.getSemanticObject());
    }

    public org.semanticwb.model.Role getChild()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Permission> listPermissions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Permission>(org.semanticwb.model.Permission.class, getSemanticObject().listObjectProperties(swb_hasPermission));
    }

    public boolean hasPermission(org.semanticwb.model.Permission permission)
    {
        if(permission==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasPermission,permission.getSemanticObject());
    }

    public void addPermission(org.semanticwb.model.Permission permission)
    {
        getSemanticObject().addObjectProperty(swb_hasPermission, permission.getSemanticObject());
    }

    public void removeAllPermission()
    {
        getSemanticObject().removeProperty(swb_hasPermission);
    }

    public void removePermission(org.semanticwb.model.Permission permission)
    {
        getSemanticObject().removeObjectProperty(swb_hasPermission,permission.getSemanticObject());
    }

    public org.semanticwb.model.Permission getPermission()
    {
         org.semanticwb.model.Permission ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPermission);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Permission)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
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

    public boolean isReadOnly()
    {
        return getSemanticObject().getBooleanProperty(swb_readOnly);
    }

    public void setReadOnly(boolean readOnly)
    {
        getSemanticObject().setBooleanProperty(swb_readOnly, readOnly);
    }

    public void setParent(org.semanticwb.model.Role role)
    {
        getSemanticObject().setObjectProperty(swb_roleParent, role.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_roleParent);
    }

    public org.semanticwb.model.Role getParent()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_roleParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return new org.semanticwb.model.UserRepository(getSemanticObject().getModel().getModelObject());
    }
}
