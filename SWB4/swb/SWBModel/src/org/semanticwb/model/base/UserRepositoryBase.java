package org.semanticwb.model.base;


   /**
   * Objeto que define un Repositorio de Usuarios 
   */
public abstract class UserRepositoryBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableClass,org.semanticwb.model.OntologyDepable
{
    public static final org.semanticwb.platform.SemanticProperty swb_userRepCallBackHandlerClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepCallBackHandlerClassName");
   /**
   * Lista de preguntas de seguridad. Ejemplo: (1|Color favorito:2|Marca de auto:3|licencia de conducir)
   */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepSecurityQuestionList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepSecurityQuestionList");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepRememberUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepRememberUser");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepLoginContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepLoginContext");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepExternalConfigFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepExternalConfigFile");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAuthMethod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAuthMethod");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAlternateLoginURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAlternateLoginURL");
   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Objeto que representa una relacion de favoritos entre un usuario y algun elemento del arbol de navegacion dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
   /**
   * Objeto que define un Role dentro de un repositorio de usuarios aplicable a un Usuario para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
   /**
   * Objeto que define un Repositorio de Usuarios
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserRepository for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UserRepository for all models
       * @return Iterator of org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.UserRepository
       * @param id Identifier for org.semanticwb.model.UserRepository
       * @return A org.semanticwb.model.UserRepository
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
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.model.UserRepository)
                    {
                        ret=(org.semanticwb.model.UserRepository)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.model.UserRepository
       * @param id Identifier for org.semanticwb.model.UserRepository
       * @return A org.semanticwb.model.UserRepository
       */
        public static org.semanticwb.model.UserRepository createUserRepository(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.UserRepository)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UserRepository
       * @param id Identifier for org.semanticwb.model.UserRepository
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
       * Returns true if exists a org.semanticwb.model.UserRepository
       * @param id Identifier for org.semanticwb.model.UserRepository
       * @return true if the org.semanticwb.model.UserRepository exists, false otherwise
       */

        public static boolean hasUserRepository(String id)
        {
            return (getUserRepository(id)!=null);
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserRepository with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.model.UserRepository
       */

        public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositoryByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserRepositoryBase.ClassMgr getUserRepositoryClassMgr()
    {
        return new UserRepositoryBase.ClassMgr();
    }

   /**
   * Constructs a UserRepositoryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserRepository
   */
    public UserRepositoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

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
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
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

/**
* Gets the CallBackHandlerClassName property
* @return String with the CallBackHandlerClassName
*/
    public String getCallBackHandlerClassName()
    {
        return getSemanticObject().getProperty(swb_userRepCallBackHandlerClassName);
    }

/**
* Sets the CallBackHandlerClassName property
* @param value long with the CallBackHandlerClassName
*/
    public void setCallBackHandlerClassName(String value)
    {
        getSemanticObject().setProperty(swb_userRepCallBackHandlerClassName, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
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

/**
* Gets the UserRepSecurityQuestionList property
* @return String with the UserRepSecurityQuestionList
*/
    public String getUserRepSecurityQuestionList()
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList);
    }

/**
* Sets the UserRepSecurityQuestionList property
* @param value long with the UserRepSecurityQuestionList
*/
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

/**
* Gets the UserRepRememberUser property
* @return boolean with the UserRepRememberUser
*/
    public boolean isUserRepRememberUser()
    {
        return getSemanticObject().getBooleanProperty(swb_userRepRememberUser);
    }

/**
* Sets the UserRepRememberUser property
* @param value long with the UserRepRememberUser
*/
    public void setUserRepRememberUser(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_userRepRememberUser, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

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
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
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
   /**
   * Gets all the org.semanticwb.model.Ontology
   * @return A GenericIterator with all the org.semanticwb.model.Ontology
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> listOntologies()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(getSemanticObject().listObjectProperties(swb_hasOntology));
    }

   /**
   * Gets true if has a Ontology
   * @param value org.semanticwb.model.Ontology to verify
   * @return true if the org.semanticwb.model.Ontology exists, false otherwise
   */
    public boolean hasOntology(org.semanticwb.model.Ontology value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasOntology,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Ontology
   * @param value org.semanticwb.model.Ontology to add
   */

    public void addOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().addObjectProperty(swb_hasOntology, value.getSemanticObject());
    }
   /**
   * Removes all the Ontology
   */

    public void removeAllOntology()
    {
        getSemanticObject().removeProperty(swb_hasOntology);
    }
   /**
   * Removes a Ontology
   * @param value org.semanticwb.model.Ontology to remove
   */

    public void removeOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().removeObjectProperty(swb_hasOntology,value.getSemanticObject());
    }

   /**
   * Gets the Ontology
   * @return a org.semanticwb.model.Ontology
   */
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

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
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

/**
* Gets the LoginContext property
* @return String with the LoginContext
*/
    public String getLoginContext()
    {
        return getSemanticObject().getProperty(swb_userRepLoginContext);
    }

/**
* Sets the LoginContext property
* @param value long with the LoginContext
*/
    public void setLoginContext(String value)
    {
        getSemanticObject().setProperty(swb_userRepLoginContext, value);
    }

/**
* Gets the Undeleteable property
* @return boolean with the Undeleteable
*/
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

/**
* Sets the Undeleteable property
* @param value long with the Undeleteable
*/
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

/**
* Gets the UserRepExternalConfigFile property
* @return String with the UserRepExternalConfigFile
*/
    public String getUserRepExternalConfigFile()
    {
        return getSemanticObject().getProperty(swb_userRepExternalConfigFile);
    }

/**
* Sets the UserRepExternalConfigFile property
* @param value long with the UserRepExternalConfigFile
*/
    public void setUserRepExternalConfigFile(String value)
    {
        getSemanticObject().setProperty(swb_userRepExternalConfigFile, value);
    }

/**
* Gets the AuthMethod property
* @return String with the AuthMethod
*/
    public String getAuthMethod()
    {
        return getSemanticObject().getProperty(swb_userRepAuthMethod);
    }

/**
* Sets the AuthMethod property
* @param value long with the AuthMethod
*/
    public void setAuthMethod(String value)
    {
        getSemanticObject().setProperty(swb_userRepAuthMethod, value);
    }

/**
* Gets the AlternateLoginURL property
* @return String with the AlternateLoginURL
*/
    public String getAlternateLoginURL()
    {
        return getSemanticObject().getProperty(swb_userRepAlternateLoginURL);
    }

/**
* Sets the AlternateLoginURL property
* @param value long with the AlternateLoginURL
*/
    public void setAlternateLoginURL(String value)
    {
        getSemanticObject().setProperty(swb_userRepAlternateLoginURL, value);
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
