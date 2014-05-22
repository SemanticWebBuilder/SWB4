package org.semanticwb.model.base;


   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc. 
   */
public abstract class UserGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable
{
   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_hasUsrGrpChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUsrGrpChild");
    public static final org.semanticwb.platform.SemanticProperty swb_usrgrpParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrgrpParent");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado uno grupo de usuarios
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupable");
    public static final org.semanticwb.platform.SemanticProperty swb_hasGroupedUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasGroupedUser");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UserGroup for all models
       * @return Iterator of org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.UserGroup
       * @param id Identifier for org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return A org.semanticwb.model.UserGroup
       */
        public static org.semanticwb.model.UserGroup getUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UserGroup
       * @param id Identifier for org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return A org.semanticwb.model.UserGroup
       */
        public static org.semanticwb.model.UserGroup createUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UserGroup
       * @param id Identifier for org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroup
       */
        public static void removeUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UserGroup
       * @param id Identifier for org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return true if the org.semanticwb.model.UserGroup exists, false otherwise
       */

        public static boolean hasUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined Child
       * @param value Child of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByChild(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUsrGrpChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined Child
       * @param value Child of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByChild(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUsrGrpChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined Parent
       * @param value Parent of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByParent(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_usrgrpParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined Parent
       * @param value Parent of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByParent(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_usrgrpParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined User
       * @param value User of the type org.semanticwb.model.UserGroupable
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByUser(org.semanticwb.model.UserGroupable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasGroupedUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined User
       * @param value User of the type org.semanticwb.model.UserGroupable
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByUser(org.semanticwb.model.UserGroupable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasGroupedUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.UserGroup
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserGroupBase.ClassMgr getUserGroupClassMgr()
    {
        return new UserGroupBase.ClassMgr();
    }

   /**
   * Constructs a UserGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserGroup
   */
    public UserGroupBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.model.UserGroup
   * @return A GenericIterator with all the org.semanticwb.model.UserGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUsrGrpChild));
    }

   /**
   * Gets true if has a Child
   * @param value org.semanticwb.model.UserGroup to verify
   * @return true if the org.semanticwb.model.UserGroup exists, false otherwise
   */
    public boolean hasChild(org.semanticwb.model.UserGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUsrGrpChild,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Child
   * @return a org.semanticwb.model.UserGroup
   */
    public org.semanticwb.model.UserGroup getChild()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUsrGrpChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Parent
   * @param value Parent to set
   */

    public void setParent(org.semanticwb.model.UserGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_usrgrpParent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }
   /**
   * Remove the value for Parent property
   */

    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_usrgrpParent);
    }

   /**
   * Gets the Parent
   * @return a org.semanticwb.model.UserGroup
   */
    public org.semanticwb.model.UserGroup getParent()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_usrgrpParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.model.UserGroupable
   * @return A GenericIterator with all the org.semanticwb.model.UserGroupable
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupable> listUsers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupable>(getSemanticObject().listObjectProperties(swb_hasGroupedUser));
    }

   /**
   * Gets true if has a User
   * @param value org.semanticwb.model.UserGroupable to verify
   * @return true if the org.semanticwb.model.UserGroupable exists, false otherwise
   */
    public boolean hasUser(org.semanticwb.model.UserGroupable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasGroupedUser,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.UserGroupable
   */
    public org.semanticwb.model.UserGroupable getUser()
    {
         org.semanticwb.model.UserGroupable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasGroupedUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupable)obj.createGenericInstance();
         }
         return ret;
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
   * Gets the UserRepository
   * @return a instance of org.semanticwb.model.UserRepository
   */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
