package org.semanticwb.model.base;


public abstract class UserRepositoryBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb_userRepSecurityQuestionList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepSecurityQuestionList");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAlternateLoginURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAlternateLoginURL");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepCallBackHandlerClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepCallBackHandlerClassName");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepLoginContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepLoginContext");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAuthMethod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAuthMethod");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepExternalConfigFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepExternalConfigFile");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
        }

        public static org.semanticwb.model.UserRepository getUserRepository(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.UserRepository ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.UserRepository)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.model.UserRepository createUserRepository(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.UserRepository)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }

        public static void removeUserRepository(String id)
        {
            org.semanticwb.model.UserRepository obj=getUserRepository(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasUserRepository(String id)
        {
            return (getUserRepository(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public UserRepositoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getUserRepSecurityQuestionList()
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList);
    }

    public void setUserRepSecurityQuestionList(String value)
    {
        getSemanticObject().setProperty(swb_userRepSecurityQuestionList, value);
    }

    public String getUserRepSecurityQuestionList(String lang)
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList, null, lang);
    }

    public String getDisplayUserRepSecurityQuestionList(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_userRepSecurityQuestionList, lang);
    }

    public void setUserRepSecurityQuestionList(String userRepSecurityQuestionList, String lang)
    {
        getSemanticObject().setProperty(swb_userRepSecurityQuestionList, userRepSecurityQuestionList, lang);
    }

    public String getAlternateLoginURL()
    {
        return getSemanticObject().getProperty(swb_userRepAlternateLoginURL);
    }

    public void setAlternateLoginURL(String value)
    {
        getSemanticObject().setProperty(swb_userRepAlternateLoginURL, value);
    }

    public String getCallBackHandlerClassName()
    {
        return getSemanticObject().getProperty(swb_userRepCallBackHandlerClassName);
    }

    public void setCallBackHandlerClassName(String value)
    {
        getSemanticObject().setProperty(swb_userRepCallBackHandlerClassName, value);
    }

    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
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
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getLoginContext()
    {
        return getSemanticObject().getProperty(swb_userRepLoginContext);
    }

    public void setLoginContext(String value)
    {
        getSemanticObject().setProperty(swb_userRepLoginContext, value);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
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

    public String getAuthMethod()
    {
        return getSemanticObject().getProperty(swb_userRepAuthMethod);
    }

    public void setAuthMethod(String value)
    {
        getSemanticObject().setProperty(swb_userRepAuthMethod, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> listOntologies()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(getSemanticObject().listObjectProperties(swb_hasOntology));
    }

    public boolean hasOntology(org.semanticwb.model.Ontology value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasOntology,value.getSemanticObject());
        }
        return ret;
    }

    public void addOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().addObjectProperty(swb_hasOntology, value.getSemanticObject());
    }

    public void removeAllOntology()
    {
        getSemanticObject().removeProperty(swb_hasOntology);
    }

    public void removeOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().removeObjectProperty(swb_hasOntology,value.getSemanticObject());
    }

    public org.semanticwb.model.Ontology getOntology()
    {
         org.semanticwb.model.Ontology ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasOntology);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Ontology)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
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
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getUserRepExternalConfigFile()
    {
        return getSemanticObject().getProperty(swb_userRepExternalConfigFile);
    }

    public void setUserRepExternalConfigFile(String value)
    {
        getSemanticObject().setProperty(swb_userRepExternalConfigFile, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
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

    public org.semanticwb.model.UserGroup getUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.ClassMgr.getUserGroup(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return org.semanticwb.model.UserGroup.ClassMgr.listUserGroups(this);
    }

    public org.semanticwb.model.UserGroup createUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.ClassMgr.createUserGroup(id,this);
    }

    public void removeUserGroup(String id)
    {
        org.semanticwb.model.UserGroup.ClassMgr.removeUserGroup(id, this);
    }
    public boolean hasUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.ClassMgr.hasUserGroup(id, this);
    }

    public org.semanticwb.model.User getUser(String id)
    {
        return org.semanticwb.model.User.ClassMgr.getUser(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.User> listUsers()
    {
        return org.semanticwb.model.User.ClassMgr.listUsers(this);
    }

    public org.semanticwb.model.User createUser(String id)
    {
        return org.semanticwb.model.User.ClassMgr.createUser(id,this);
    }

    public org.semanticwb.model.User createUser()
    {
        long id=getSemanticObject().getModel().getCounter(swb_User);
        return org.semanticwb.model.User.ClassMgr.createUser(String.valueOf(id),this);
    } 

    public void removeUser(String id)
    {
        org.semanticwb.model.User.ClassMgr.removeUser(id, this);
    }
    public boolean hasUser(String id)
    {
        return org.semanticwb.model.User.ClassMgr.hasUser(id, this);
    }

    public org.semanticwb.model.UserFavorite getUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.getUserFavorite(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites()
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.listUserFavorites(this);
    }

    public org.semanticwb.model.UserFavorite createUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.createUserFavorite(id,this);
    }

    public org.semanticwb.model.UserFavorite createUserFavorite()
    {
        long id=getSemanticObject().getModel().getCounter(swb_UserFavorite);
        return org.semanticwb.model.UserFavorite.ClassMgr.createUserFavorite(String.valueOf(id),this);
    } 

    public void removeUserFavorite(String id)
    {
        org.semanticwb.model.UserFavorite.ClassMgr.removeUserFavorite(id, this);
    }
    public boolean hasUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.hasUserFavorite(id, this);
    }

    public org.semanticwb.model.Role getRole(String id)
    {
        return org.semanticwb.model.Role.ClassMgr.getRole(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Role> listRoles()
    {
        return org.semanticwb.model.Role.ClassMgr.listRoles(this);
    }

    public org.semanticwb.model.Role createRole(String id)
    {
        return org.semanticwb.model.Role.ClassMgr.createRole(id,this);
    }

    public void removeRole(String id)
    {
        org.semanticwb.model.Role.ClassMgr.removeRole(id, this);
    }
    public boolean hasRole(String id)
    {
        return org.semanticwb.model.Role.ClassMgr.hasRole(id, this);
    }
}
