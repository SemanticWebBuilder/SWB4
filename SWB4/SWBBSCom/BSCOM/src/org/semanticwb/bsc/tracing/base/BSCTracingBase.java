package org.semanticwb.bsc.tracing.base;


public abstract class BSCTracingBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableNode,org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Roleable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help
{
    public static final org.semanticwb.platform.SemanticClass bsc_BSCTracing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCTracing");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCTracing");

    public static class ClassMgr
    {
       /**
       * Returns a list of BSCTracing for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.BSCTracing for all models
       * @return Iterator of org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing>(it, true);
        }

        public static org.semanticwb.bsc.tracing.BSCTracing createBSCTracing(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.BSCTracing.ClassMgr.createBSCTracing(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.BSCTracing
       * @param id Identifier for org.semanticwb.bsc.tracing.BSCTracing
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return A org.semanticwb.bsc.tracing.BSCTracing
       */
        public static org.semanticwb.bsc.tracing.BSCTracing getBSCTracing(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.BSCTracing)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.BSCTracing
       * @param id Identifier for org.semanticwb.bsc.tracing.BSCTracing
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return A org.semanticwb.bsc.tracing.BSCTracing
       */
        public static org.semanticwb.bsc.tracing.BSCTracing createBSCTracing(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.BSCTracing)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.BSCTracing
       * @param id Identifier for org.semanticwb.bsc.tracing.BSCTracing
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       */
        public static void removeBSCTracing(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.BSCTracing
       * @param id Identifier for org.semanticwb.bsc.tracing.BSCTracing
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return true if the org.semanticwb.bsc.tracing.BSCTracing exists, false otherwise
       */

        public static boolean hasBSCTracing(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBSCTracing(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.BSCTracing
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.BSCTracing with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.BSCTracing
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.BSCTracing> listBSCTracingByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.BSCTracing> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static BSCTracingBase.ClassMgr getBSCTracingClassMgr()
    {
        return new BSCTracingBase.ClassMgr();
    }

   /**
   * Constructs a BSCTracingBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BSCTracing
   */
    public BSCTracingBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Help property
* @return String with the Help
*/
    public String getHelp()
    {
        return getSemanticObject().getProperty(bsc_help);
    }

/**
* Sets the Help property
* @param value long with the Help
*/
    public void setHelp(String value)
    {
        getSemanticObject().setProperty(bsc_help, value);
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
   * Gets all the org.semanticwb.model.UserGroup
   * @return A GenericIterator with all the org.semanticwb.model.UserGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUserGroup));
    }

   /**
   * Gets true if has a UserGroup
   * @param value org.semanticwb.model.UserGroup to verify
   * @return true if the org.semanticwb.model.UserGroup exists, false otherwise
   */
    public boolean hasUserGroup(org.semanticwb.model.UserGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroup,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a UserGroup
   * @param value org.semanticwb.model.UserGroup to add
   */

    public void addUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroup, value.getSemanticObject());
    }
   /**
   * Removes all the UserGroup
   */

    public void removeAllUserGroup()
    {
        getSemanticObject().removeProperty(swb_hasUserGroup);
    }
   /**
   * Removes a UserGroup
   * @param value org.semanticwb.model.UserGroup to remove
   */

    public void removeUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroup,value.getSemanticObject());
    }

   /**
   * Gets the UserGroup
   * @return a org.semanticwb.model.UserGroup
   */
    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
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
   * Gets all the org.semanticwb.model.Role
   * @return A GenericIterator with all the org.semanticwb.model.Role
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(swb_hasRole));
    }

   /**
   * Gets true if has a Role
   * @param value org.semanticwb.model.Role to verify
   * @return true if the org.semanticwb.model.Role exists, false otherwise
   */
    public boolean hasRole(org.semanticwb.model.Role value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRole,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Role
   * @param value org.semanticwb.model.Role to add
   */

    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, value.getSemanticObject());
    }
   /**
   * Removes all the Role
   */

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }
   /**
   * Removes a Role
   * @param value org.semanticwb.model.Role to remove
   */

    public void removeRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,value.getSemanticObject());
    }

   /**
   * Gets the Role
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
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
}
