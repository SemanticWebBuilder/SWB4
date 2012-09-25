package org.semanticwb.social.base;


   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social. 
   */
public abstract class SocialNetworkUserBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Cantidad de seguidores que tiene el usuario que es almacenado en c/instancia de esta clase (en su propiedad id)
   */
    public static final org.semanticwb.platform.SemanticProperty social_followers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#followers");
   /**
   * Número de usuarios a los que yo sigo (así se maneja en twitter), en facebook aqui pondría a mis amigos y la de followers (que son los que me siguen) según yo no la utilizaría (en facebook)
   */
    public static final org.semanticwb.platform.SemanticProperty social_friends=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#friends");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Red social a la cual pertenece este usuario (usuario de red social)
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_SocialNetwork");
   /**
   * Id del usuario en una determinada red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_id=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_id");
   /**
   * Nombre del usuario en la Red Social
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_name");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Propiedad inversa que regresa los PostIn pertenecientes a un usuario de una red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInInv");
   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetworkUser for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUsers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetworkUser for all models
       * @return Iterator of org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUsers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(it, true);
        }

        public static org.semanticwb.social.SocialNetworkUser createSocialNetworkUser(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialNetworkUser.ClassMgr.createSocialNetworkUser(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return A org.semanticwb.social.SocialNetworkUser
       */
        public static org.semanticwb.social.SocialNetworkUser getSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetworkUser)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return A org.semanticwb.social.SocialNetworkUser
       */
        public static org.semanticwb.social.SocialNetworkUser createSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetworkUser)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       */
        public static void removeSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return true if the org.semanticwb.social.SocialNetworkUser exists, false otherwise
       */

        public static boolean hasSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetworkUser(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Snu_SocialNetwork
       * @param value Snu_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserBySnu_SocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_snu_SocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Snu_SocialNetwork
       * @param value Snu_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserBySnu_SocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_snu_SocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined PostInInv
       * @param value PostInInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByPostInInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined PostInInv
       * @param value PostInInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByPostInInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialNetworkUserBase.ClassMgr getSocialNetworkUserClassMgr()
    {
        return new SocialNetworkUserBase.ClassMgr();
    }

   /**
   * Constructs a SocialNetworkUserBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetworkUser
   */
    public SocialNetworkUserBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Followers property
* @return int with the Followers
*/
    public int getFollowers()
    {
        return getSemanticObject().getIntProperty(social_followers);
    }

/**
* Sets the Followers property
* @param value long with the Followers
*/
    public void setFollowers(int value)
    {
        getSemanticObject().setIntProperty(social_followers, value);
    }

/**
* Gets the Friends property
* @return int with the Friends
*/
    public int getFriends()
    {
        return getSemanticObject().getIntProperty(social_friends);
    }

/**
* Sets the Friends property
* @param value long with the Friends
*/
    public void setFriends(int value)
    {
        getSemanticObject().setIntProperty(social_friends, value);
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
   * Sets the value for the property Snu_SocialNetwork
   * @param value Snu_SocialNetwork to set
   */

    public void setSnu_SocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_snu_SocialNetwork, value.getSemanticObject());
        }else
        {
            removeSnu_SocialNetwork();
        }
    }
   /**
   * Remove the value for Snu_SocialNetwork property
   */

    public void removeSnu_SocialNetwork()
    {
        getSemanticObject().removeProperty(social_snu_SocialNetwork);
    }

   /**
   * Gets the Snu_SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSnu_SocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_snu_SocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Snu_id property
* @return String with the Snu_id
*/
    public String getSnu_id()
    {
        return getSemanticObject().getProperty(social_snu_id);
    }

/**
* Sets the Snu_id property
* @param value long with the Snu_id
*/
    public void setSnu_id(String value)
    {
        getSemanticObject().setProperty(social_snu_id, value);
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
* Gets the Snu_name property
* @return String with the Snu_name
*/
    public String getSnu_name()
    {
        return getSemanticObject().getProperty(social_snu_name);
    }

/**
* Sets the Snu_name property
* @param value long with the Snu_name
*/
    public void setSnu_name(String value)
    {
        getSemanticObject().setProperty(social_snu_name, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInInv));
    }

   /**
   * Gets true if has a PostInInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
}
