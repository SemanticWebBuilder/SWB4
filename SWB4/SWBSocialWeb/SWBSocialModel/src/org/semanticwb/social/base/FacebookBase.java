package org.semanticwb.social.base;


   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social Facebook. 
   */
public abstract class FacebookBase extends org.semanticwb.social.SocialNetwork implements org.semanticwb.social.PostOutMonitorable,org.semanticwb.model.Filterable,org.semanticwb.social.Oauthable,org.semanticwb.model.Traceable,org.semanticwb.social.Messageable,org.semanticwb.social.Videoable,org.semanticwb.model.Descriptiveable,org.semanticwb.social.SocialNetPostable,org.semanticwb.social.Secreteable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableNode,org.semanticwb.social.Photoable,org.semanticwb.social.Listenerable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableClass,org.semanticwb.social.Relationable
{
   /**
   * Eliminar despues esta propiedad, ver si Jose puede eliminar la propiedad login que se encuentra en la clase padre(SocialNetwork).
   */
    public static final org.semanticwb.platform.SemanticProperty social_facebookUserId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#facebookUserId");
   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social Facebook.
   */
    public static final org.semanticwb.platform.SemanticClass social_Facebook=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Facebook");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Facebook");

    public static class ClassMgr
    {
       /**
       * Returns a list of Facebook for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebooks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Facebook for all models
       * @return Iterator of org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebooks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook>(it, true);
        }

        public static org.semanticwb.social.Facebook createFacebook(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Facebook.ClassMgr.createFacebook(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       * @return A org.semanticwb.social.Facebook
       */
        public static org.semanticwb.social.Facebook getFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Facebook)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       * @return A org.semanticwb.social.Facebook
       */
        public static org.semanticwb.social.Facebook createFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Facebook)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       */
        public static void removeFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       * @return true if the org.semanticwb.social.Facebook exists, false otherwise
       */

        public static boolean hasFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFacebook(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostListenerContainer(org.semanticwb.social.PostInContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostListenerContainer(org.semanticwb.social.PostInContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostContainer(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostContainer(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookBySocialPostInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookBySocialPostInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Popr_socialNetworkInv
       * @param value Popr_socialNetworkInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPopr_socialNetworkInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_socialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Popr_socialNetworkInv
       * @param value Popr_socialNetworkInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPopr_socialNetworkInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_socialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FacebookBase.ClassMgr getFacebookClassMgr()
    {
        return new FacebookBase.ClassMgr();
    }

   /**
   * Constructs a FacebookBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Facebook
   */
    public FacebookBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the FacebookUserId property
* @return String with the FacebookUserId
*/
    public String getFacebookUserId()
    {
        return getSemanticObject().getProperty(social_facebookUserId);
    }

/**
* Sets the FacebookUserId property
* @param value long with the FacebookUserId
*/
    public void setFacebookUserId(String value)
    {
        getSemanticObject().setProperty(social_facebookUserId, value);
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
}
