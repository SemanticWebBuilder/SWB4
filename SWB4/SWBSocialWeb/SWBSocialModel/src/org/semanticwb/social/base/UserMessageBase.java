package org.semanticwb.social.base;


public abstract class UserMessageBase extends org.semanticwb.social.Colaboration implements org.semanticwb.model.Traceable
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Usuario autor del mensaje
   */
    public static final org.semanticwb.platform.SemanticProperty social_fromUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#fromUser");
   /**
   * Para que usuarios esta dirigido el mensaje
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasUsers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasUsers");
   /**
   * Mensaje a dejar a uno o varios usuarios
   */
    public static final org.semanticwb.platform.SemanticProperty social_usrMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#usrMsg");
    public static final org.semanticwb.platform.SemanticProperty social_userPriority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userPriority");
    public static final org.semanticwb.platform.SemanticClass social_UserMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#UserMessage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#UserMessage");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserMessage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.UserMessage for all models
       * @return Iterator of org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage>(it, true);
        }

        public static org.semanticwb.social.UserMessage createUserMessage(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.UserMessage.ClassMgr.createUserMessage(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.UserMessage
       * @param id Identifier for org.semanticwb.social.UserMessage
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return A org.semanticwb.social.UserMessage
       */
        public static org.semanticwb.social.UserMessage getUserMessage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.UserMessage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.UserMessage
       * @param id Identifier for org.semanticwb.social.UserMessage
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return A org.semanticwb.social.UserMessage
       */
        public static org.semanticwb.social.UserMessage createUserMessage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.UserMessage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.UserMessage
       * @param id Identifier for org.semanticwb.social.UserMessage
       * @param model Model of the org.semanticwb.social.UserMessage
       */
        public static void removeUserMessage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.UserMessage
       * @param id Identifier for org.semanticwb.social.UserMessage
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return true if the org.semanticwb.social.UserMessage exists, false otherwise
       */

        public static boolean hasUserMessage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserMessage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined FromUser
       * @param value FromUser of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByFromUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_fromUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined FromUser
       * @param value FromUser of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByFromUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_fromUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined Users
       * @param value Users of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByUsers(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasUsers, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined Users
       * @param value Users of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByUsers(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasUsers,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.UserMessage
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.UserMessage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.UserMessage
       */

        public static java.util.Iterator<org.semanticwb.social.UserMessage> listUserMessageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.UserMessage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserMessageBase.ClassMgr getUserMessageClassMgr()
    {
        return new UserMessageBase.ClassMgr();
    }

   /**
   * Constructs a UserMessageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserMessage
   */
    public UserMessageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property FromUser
   * @param value FromUser to set
   */

    public void setFromUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_fromUser, value.getSemanticObject());
        }else
        {
            removeFromUser();
        }
    }
   /**
   * Remove the value for FromUser property
   */

    public void removeFromUser()
    {
        getSemanticObject().removeProperty(social_fromUser);
    }

   /**
   * Gets the FromUser
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getFromUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_fromUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.model.User
   * @return A GenericIterator with all the org.semanticwb.model.User
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listUserses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(social_hasUsers));
    }

   /**
   * Gets true if has a Users
   * @param value org.semanticwb.model.User to verify
   * @return true if the org.semanticwb.model.User exists, false otherwise
   */
    public boolean hasUsers(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasUsers,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Users
   * @param value org.semanticwb.model.User to add
   */

    public void addUsers(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(social_hasUsers, value.getSemanticObject());
    }
   /**
   * Removes all the Users
   */

    public void removeAllUsers()
    {
        getSemanticObject().removeProperty(social_hasUsers);
    }
   /**
   * Removes a Users
   * @param value org.semanticwb.model.User to remove
   */

    public void removeUsers(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(social_hasUsers,value.getSemanticObject());
    }

   /**
   * Gets the Users
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUsers()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasUsers);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the UsrMsg property
* @return String with the UsrMsg
*/
    public String getUsrMsg()
    {
        return getSemanticObject().getProperty(social_usrMsg);
    }

/**
* Sets the UsrMsg property
* @param value long with the UsrMsg
*/
    public void setUsrMsg(String value)
    {
        getSemanticObject().setProperty(social_usrMsg, value);
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
* Gets the UserPriority property
* @return boolean with the UserPriority
*/
    public boolean isUserPriority()
    {
        return getSemanticObject().getBooleanProperty(social_userPriority);
    }

/**
* Sets the UserPriority property
* @param value long with the UserPriority
*/
    public void setUserPriority(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_userPriority, value);
    }
}
