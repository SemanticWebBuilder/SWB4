package org.semanticwb.social.base;


   /**
   * Cuenta de Instagram 
   */
public abstract class InstagramBase extends org.semanticwb.social.SocialNetwork implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.social.SocialNetPostable,org.semanticwb.social.Relationable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Traceable,org.semanticwb.social.Listenerable,org.semanticwb.social.Secreteable,org.semanticwb.model.Filterable,org.semanticwb.social.Oauthable,org.semanticwb.social.PostOutMonitorable,org.semanticwb.social.Photoable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable
{
   /**
   * Instagram User Id
   */
    public static final org.semanticwb.platform.SemanticProperty social_InstagramUserId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#InstagramUserId");
   /**
   * Cuenta de Instagram
   */
    public static final org.semanticwb.platform.SemanticClass social_Instagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Instagram");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Instagram");

    public static class ClassMgr
    {
       /**
       * Returns a list of Instagram for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagrams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Instagram for all models
       * @return Iterator of org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagrams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Instagram
       * @param id Identifier for org.semanticwb.social.Instagram
       * @param model Model of the org.semanticwb.social.Instagram
       * @return A org.semanticwb.social.Instagram
       */
        public static org.semanticwb.social.Instagram getInstagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Instagram)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Instagram
       * @param id Identifier for org.semanticwb.social.Instagram
       * @param model Model of the org.semanticwb.social.Instagram
       * @return A org.semanticwb.social.Instagram
       */
        public static org.semanticwb.social.Instagram createInstagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Instagram)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Instagram
       * @param id Identifier for org.semanticwb.social.Instagram
       * @param model Model of the org.semanticwb.social.Instagram
       */
        public static void removeInstagram(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Instagram
       * @param id Identifier for org.semanticwb.social.Instagram
       * @param model Model of the org.semanticwb.social.Instagram
       * @return true if the org.semanticwb.social.Instagram exists, false otherwise
       */

        public static boolean hasInstagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInstagram(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostListenerContainer(org.semanticwb.social.PostInContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostListenerContainer(org.semanticwb.social.PostInContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostContainer(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostContainer(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramBySocialPostInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramBySocialPostInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined Podur_SocialNetworkInv
       * @param value Podur_SocialNetworkInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPodur_SocialNetworkInv(org.semanticwb.social.PostOutDirectUserRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_SocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined Podur_SocialNetworkInv
       * @param value Podur_SocialNetworkInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPodur_SocialNetworkInv(org.semanticwb.social.PostOutDirectUserRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_SocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostOutLinksInv
       * @param value PostOutLinksInv of the type org.semanticwb.social.PostOutLinksHits
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostOutLinksInv(org.semanticwb.social.PostOutLinksHits value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutLinksInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined PostOutLinksInv
       * @param value PostOutLinksInv of the type org.semanticwb.social.PostOutLinksHits
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPostOutLinksInv(org.semanticwb.social.PostOutLinksHits value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutLinksInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined Popr_socialNetworkInv
       * @param value Popr_socialNetworkInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.Instagram
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPopr_socialNetworkInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_socialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Instagram with a determined Popr_socialNetworkInv
       * @param value Popr_socialNetworkInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.Instagram
       */

        public static java.util.Iterator<org.semanticwb.social.Instagram> listInstagramByPopr_socialNetworkInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Instagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_socialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static InstagramBase.ClassMgr getInstagramClassMgr()
    {
        return new InstagramBase.ClassMgr();
    }

   /**
   * Constructs a InstagramBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Instagram
   */
    public InstagramBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the InstagramUserId property
* @return String with the InstagramUserId
*/
    public String getInstagramUserId()
    {
        return getSemanticObject().getProperty(social_InstagramUserId);
    }

/**
* Sets the InstagramUserId property
* @param value long with the InstagramUserId
*/
    public void setInstagramUserId(String value)
    {
        getSemanticObject().setProperty(social_InstagramUserId, value);
    }
}
