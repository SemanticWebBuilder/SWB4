package org.semanticwb.portal.resources.sem.forum.base;


public abstract class UserFavThreadBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_ftUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#ftUser");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticProperty frm_ftThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#ftThread");
    public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserFavThread for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.forum.UserFavThread for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.forum.UserFavThread createUserFavThread(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.UserFavThread.ClassMgr.createUserFavThread(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return A org.semanticwb.portal.resources.sem.forum.UserFavThread
       */
        public static org.semanticwb.portal.resources.sem.forum.UserFavThread getUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.UserFavThread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return A org.semanticwb.portal.resources.sem.forum.UserFavThread
       */
        public static org.semanticwb.portal.resources.sem.forum.UserFavThread createUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.UserFavThread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */
        public static void removeUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return true if the org.semanticwb.portal.resources.sem.forum.UserFavThread exists, false otherwise
       */

        public static boolean hasUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserFavThread(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_ftUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_ftUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined Thread
       * @param value Thread of the type org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.UserFavThread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByThread(org.semanticwb.portal.resources.sem.forum.Thread value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_ftThread, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.UserFavThread with a determined Thread
       * @param value Thread of the type org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.UserFavThread
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByThread(org.semanticwb.portal.resources.sem.forum.Thread value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_ftThread,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserFavThreadBase.ClassMgr getUserFavThreadClassMgr()
    {
        return new UserFavThreadBase.ClassMgr();
    }

   /**
   * Constructs a UserFavThreadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserFavThread
   */
    public UserFavThreadBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property User
   * @param value User to set
   */

    public void setUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_ftUser, value.getSemanticObject());
        }else
        {
            removeUser();
        }
    }
   /**
   * Remove the value for User property
   */

    public void removeUser()
    {
        getSemanticObject().removeProperty(frm_ftUser);
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_ftUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
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
   * Sets the value for the property Thread
   * @param value Thread to set
   */

    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_ftThread, value.getSemanticObject());
        }else
        {
            removeThread();
        }
    }
   /**
   * Remove the value for Thread property
   */

    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_ftThread);
    }

   /**
   * Gets the Thread
   * @return a org.semanticwb.portal.resources.sem.forum.Thread
   */
    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_ftThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }
}
