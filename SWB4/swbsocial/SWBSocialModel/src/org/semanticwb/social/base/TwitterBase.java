package org.semanticwb.social.base;


   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social Twitter. 
   */
public abstract class TwitterBase extends org.semanticwb.social.SocialNetwork implements org.semanticwb.social.KeepAliveListenerable,org.semanticwb.social.Oauthable,org.semanticwb.social.Messageable,org.semanticwb.social.Secreteable,org.semanticwb.model.Descriptiveable,org.semanticwb.social.Listenerable,org.semanticwb.social.Photoable,org.semanticwb.model.Traceable,org.semanticwb.social.SocialNetPostable
{
   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social Twitter.
   */
    public static final org.semanticwb.platform.SemanticClass social_Twitter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Twitter");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Twitter");

    public static class ClassMgr
    {
       /**
       * Returns a list of Twitter for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitters(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Twitter for all models
       * @return Iterator of org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitters()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       * @return A org.semanticwb.social.Twitter
       */
        public static org.semanticwb.social.Twitter getTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Twitter)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       * @return A org.semanticwb.social.Twitter
       */
        public static org.semanticwb.social.Twitter createTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Twitter)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       */
        public static void removeTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       * @return true if the org.semanticwb.social.Twitter exists, false otherwise
       */

        public static boolean hasTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTwitter(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPostListenerContainer(org.semanticwb.social.PostListenerContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined SocialNetworkUsersInv
       * @param value SocialNetworkUsersInv of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterBySocialNetworkUsersInv(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkUsersInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined SocialNetworkUsersInv
       * @param value SocialNetworkUsersInv of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterBySocialNetworkUsersInv(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkUsersInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPostContainer(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPostContainer(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a TwitterBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Twitter
   */
    public TwitterBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the TokenExpirationDate property
* @return java.util.Date with the TokenExpirationDate
*/
    public java.util.Date getTokenExpirationDate()
    {
        return getSemanticObject().getDateProperty(social_tokenExpirationDate);
    }

/**
* Sets the TokenExpirationDate property
* @param value long with the TokenExpirationDate
*/
    public void setTokenExpirationDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(social_tokenExpirationDate, value);
    }

/**
* Gets the AppKey property
* @return String with the AppKey
*/
    public String getAppKey()
    {
        return getSemanticObject().getProperty(social_appKey);
    }

/**
* Sets the AppKey property
* @param value long with the AppKey
*/
    public void setAppKey(String value)
    {
        getSemanticObject().setProperty(social_appKey, value);
    }

/**
* Gets the AccessToken property
* @return String with the AccessToken
*/
    public String getAccessToken()
    {
        return getSemanticObject().getProperty(social_accessToken);
    }

/**
* Sets the AccessToken property
* @param value long with the AccessToken
*/
    public void setAccessToken(String value)
    {
        getSemanticObject().setProperty(social_accessToken, value);
    }

/**
* Gets the IsKeepingConnection property
* @return boolean with the IsKeepingConnection
*/
    public boolean isIsKeepingConnection()
    {
        return getSemanticObject().getBooleanProperty(social_isKeepingConnection);
    }

/**
* Sets the IsKeepingConnection property
* @param value long with the IsKeepingConnection
*/
    public void setIsKeepingConnection(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_isKeepingConnection, value);
    }

/**
* Gets the AccessTokenSecret property
* @return String with the AccessTokenSecret
*/
    public String getAccessTokenSecret()
    {
        return getSemanticObject().getProperty(social_accessTokenSecret);
    }

/**
* Sets the AccessTokenSecret property
* @param value long with the AccessTokenSecret
*/
    public void setAccessTokenSecret(String value)
    {
        getSemanticObject().setProperty(social_accessTokenSecret, value);
    }

/**
* Gets the SecretKey property
* @return String with the SecretKey
*/
    public String getSecretKey()
    {
        return getSemanticObject().getProperty(social_secretKey);
    }

/**
* Sets the SecretKey property
* @param value long with the SecretKey
*/
    public void setSecretKey(String value)
    {
        getSemanticObject().setProperty(social_secretKey, value);
    }
}
