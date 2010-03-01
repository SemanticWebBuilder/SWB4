/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class UserRepositoryBase.
 */
public abstract class UserRepositoryBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_userRepLoginContext. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepLoginContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepLoginContext");
    
    /** The Constant swb_userRepAuthMethod. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAuthMethod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAuthMethod");
    
    /** The Constant swb_userRepSecurityQuestionList. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepSecurityQuestionList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepSecurityQuestionList");
    
    /** The Constant swb_userRepAlternateLoginURL. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAlternateLoginURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAlternateLoginURL");
    
    /** The Constant swb_userRepCallBackHandlerClassName. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepCallBackHandlerClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepCallBackHandlerClassName");
    
    /** The Constant swb_userRepExternalConfigFile. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepExternalConfigFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepExternalConfigFile");
    
    /** The Constant swb_UserGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swb_UserFavorite. */
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
    
    /** The Constant swb_Role. */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    
    /** The Constant swb_UserRepository. */
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List user repositories.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
        }

        /**
         * List user repositories.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
        }

        /**
         * Gets the user repository.
         * 
         * @param id the id
         * @return the user repository
         */
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

        /**
         * Creates the user repository.
         * 
         * @param id the id
         * @param namespace the namespace
         * @return the org.semanticwb.model. user repository
         */
        public static org.semanticwb.model.UserRepository createUserRepository(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.UserRepository)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the user repository.
         * 
         * @param id the id
         */
        public static void removeUserRepository(String id)
        {
            org.semanticwb.model.UserRepository obj=getUserRepository(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        /**
         * Checks for user repository.
         * 
         * @param id the id
         * @return true, if successful
         */
        public static boolean hasUserRepository(String id)
        {
            return (getUserRepository(id)!=null);
        }

        /**
         * List user repository by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List user repository by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List user repository by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List user repository by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List user repository by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List user repository by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new user repository base.
     * 
     * @param base the base
     */
    public UserRepositoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * Gets the login context.
     * 
     * @return the login context
     */
    public String getLoginContext()
    {
        return getSemanticObject().getProperty(swb_userRepLoginContext);
    }

    /**
     * Sets the login context.
     * 
     * @param value the new login context
     */
    public void setLoginContext(String value)
    {
        getSemanticObject().setProperty(swb_userRepLoginContext, value);
    }

    /**
     * Gets the auth method.
     * 
     * @return the auth method
     */
    public String getAuthMethod()
    {
        return getSemanticObject().getProperty(swb_userRepAuthMethod);
    }

    /**
     * Sets the auth method.
     * 
     * @param value the new auth method
     */
    public void setAuthMethod(String value)
    {
        getSemanticObject().setProperty(swb_userRepAuthMethod, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Gets the user rep security question list.
     * 
     * @return the user rep security question list
     */
    public String getUserRepSecurityQuestionList()
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList);
    }

    /**
     * Sets the user rep security question list.
     * 
     * @param value the new user rep security question list
     */
    public void setUserRepSecurityQuestionList(String value)
    {
        getSemanticObject().setProperty(swb_userRepSecurityQuestionList, value);
    }

    /**
     * Gets the user rep security question list.
     * 
     * @param lang the lang
     * @return the user rep security question list
     */
    public String getUserRepSecurityQuestionList(String lang)
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList, null, lang);
    }

    /**
     * Gets the display user rep security question list.
     * 
     * @param lang the lang
     * @return the display user rep security question list
     */
    public String getDisplayUserRepSecurityQuestionList(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_userRepSecurityQuestionList, lang);
    }

    /**
     * Sets the user rep security question list.
     * 
     * @param userRepSecurityQuestionList the user rep security question list
     * @param lang the lang
     */
    public void setUserRepSecurityQuestionList(String userRepSecurityQuestionList, String lang)
    {
        getSemanticObject().setProperty(swb_userRepSecurityQuestionList, userRepSecurityQuestionList, lang);
    }

    /**
     * Gets the alternate login url.
     * 
     * @return the alternate login url
     */
    public String getAlternateLoginURL()
    {
        return getSemanticObject().getProperty(swb_userRepAlternateLoginURL);
    }

    /**
     * Sets the alternate login url.
     * 
     * @param value the new alternate login url
     */
    public void setAlternateLoginURL(String value)
    {
        getSemanticObject().setProperty(swb_userRepAlternateLoginURL, value);
    }

    /**
     * Gets the call back handler class name.
     * 
     * @return the call back handler class name
     */
    public String getCallBackHandlerClassName()
    {
        return getSemanticObject().getProperty(swb_userRepCallBackHandlerClassName);
    }

    /**
     * Sets the call back handler class name.
     * 
     * @param value the new call back handler class name
     */
    public void setCallBackHandlerClassName(String value)
    {
        getSemanticObject().setProperty(swb_userRepCallBackHandlerClassName, value);
    }

    /**
     * Gets the user rep external config file.
     * 
     * @return the user rep external config file
     */
    public String getUserRepExternalConfigFile()
    {
        return getSemanticObject().getProperty(swb_userRepExternalConfigFile);
    }

    /**
     * Sets the user rep external config file.
     * 
     * @param value the new user rep external config file
     */
    public void setUserRepExternalConfigFile(String value)
    {
        getSemanticObject().setProperty(swb_userRepExternalConfigFile, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#isUndeleteable()
     */
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#setUndeleteable(boolean)
     */
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    /**
     * Gets the user group.
     * 
     * @param id the id
     * @return the user group
     */
    public org.semanticwb.model.UserGroup getUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.ClassMgr.getUserGroup(id, this);
    }

    /**
     * List user groups.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return org.semanticwb.model.UserGroup.ClassMgr.listUserGroups(this);
    }

    /**
     * Creates the user group.
     * 
     * @param id the id
     * @return the org.semanticwb.model. user group
     */
    public org.semanticwb.model.UserGroup createUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.ClassMgr.createUserGroup(id,this);
    }

    /**
     * Removes the user group.
     * 
     * @param id the id
     */
    public void removeUserGroup(String id)
    {
        org.semanticwb.model.UserGroup.ClassMgr.removeUserGroup(id, this);
    }
    
    /**
     * Checks for user group.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.ClassMgr.hasUserGroup(id, this);
    }

    /**
     * Gets the user.
     * 
     * @param id the id
     * @return the user
     */
    public org.semanticwb.model.User getUser(String id)
    {
        return org.semanticwb.model.User.ClassMgr.getUser(id, this);
    }

    /**
     * List users.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.User> listUsers()
    {
        return org.semanticwb.model.User.ClassMgr.listUsers(this);
    }

    /**
     * Creates the user.
     * 
     * @param id the id
     * @return the org.semanticwb.model. user
     */
    public org.semanticwb.model.User createUser(String id)
    {
        return org.semanticwb.model.User.ClassMgr.createUser(id,this);
    }

    /**
     * Creates the user.
     * 
     * @return the org.semanticwb.model. user
     */
    public org.semanticwb.model.User createUser()
    {
        long id=getSemanticObject().getModel().getCounter(swb_User);
        return org.semanticwb.model.User.ClassMgr.createUser(String.valueOf(id),this);
    } 

    /**
     * Removes the user.
     * 
     * @param id the id
     */
    public void removeUser(String id)
    {
        org.semanticwb.model.User.ClassMgr.removeUser(id, this);
    }
    
    /**
     * Checks for user.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasUser(String id)
    {
        return org.semanticwb.model.User.ClassMgr.hasUser(id, this);
    }

    /**
     * Gets the user favorite.
     * 
     * @param id the id
     * @return the user favorite
     */
    public org.semanticwb.model.UserFavorite getUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.getUserFavorite(id, this);
    }

    /**
     * List user favorites.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites()
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.listUserFavorites(this);
    }

    /**
     * Creates the user favorite.
     * 
     * @param id the id
     * @return the org.semanticwb.model. user favorite
     */
    public org.semanticwb.model.UserFavorite createUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.createUserFavorite(id,this);
    }

    /**
     * Creates the user favorite.
     * 
     * @return the org.semanticwb.model. user favorite
     */
    public org.semanticwb.model.UserFavorite createUserFavorite()
    {
        long id=getSemanticObject().getModel().getCounter(swb_UserFavorite);
        return org.semanticwb.model.UserFavorite.ClassMgr.createUserFavorite(String.valueOf(id),this);
    } 

    /**
     * Removes the user favorite.
     * 
     * @param id the id
     */
    public void removeUserFavorite(String id)
    {
        org.semanticwb.model.UserFavorite.ClassMgr.removeUserFavorite(id, this);
    }
    
    /**
     * Checks for user favorite.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.ClassMgr.hasUserFavorite(id, this);
    }

    /**
     * Gets the role.
     * 
     * @param id the id
     * @return the role
     */
    public org.semanticwb.model.Role getRole(String id)
    {
        return org.semanticwb.model.Role.ClassMgr.getRole(id, this);
    }

    /**
     * List roles.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Role> listRoles()
    {
        return org.semanticwb.model.Role.ClassMgr.listRoles(this);
    }

    /**
     * Creates the role.
     * 
     * @param id the id
     * @return the org.semanticwb.model. role
     */
    public org.semanticwb.model.Role createRole(String id)
    {
        return org.semanticwb.model.Role.ClassMgr.createRole(id,this);
    }

    /**
     * Removes the role.
     * 
     * @param id the id
     */
    public void removeRole(String id)
    {
        org.semanticwb.model.Role.ClassMgr.removeRole(id, this);
    }
    
    /**
     * Checks for role.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRole(String id)
    {
        return org.semanticwb.model.Role.ClassMgr.hasRole(id, this);
    }
}
