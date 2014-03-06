package org.semanticwb.social.base;


   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social YouTube. 
   */
public abstract class YoutubeBase extends org.semanticwb.social.SocialNetwork implements org.semanticwb.social.Videoable,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableNode,org.semanticwb.model.FilterableClass,org.semanticwb.social.Messageable,org.semanticwb.model.Descriptiveable,org.semanticwb.social.SocialNetPostable,org.semanticwb.social.SocialMonitorable,org.semanticwb.model.Activeable,org.semanticwb.social.DeveloperKeyable,org.semanticwb.social.Relationable,org.semanticwb.social.PostOutMonitorable,org.semanticwb.social.Kloutable,org.semanticwb.social.Secreteable,org.semanticwb.social.Oauthable,org.semanticwb.social.Listenerable
{
   /**
   * Categorias a llenar en una colección.
   */
    public static final org.semanticwb.platform.SemanticClass social_YouTubeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YouTubeCategory");
   /**
   * Categorias asociadas sobre las cuales se realizaría listener en una instancia de Youtube
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasYoutubeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasYoutubeCategory");
   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social YouTube.
   */
    public static final org.semanticwb.platform.SemanticClass social_Youtube=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Youtube");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Youtube");

    public static class ClassMgr
    {
       /**
       * Returns a list of Youtube for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Youtube for all models
       * @return Iterator of org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube>(it, true);
        }

        public static org.semanticwb.social.Youtube createYoutube(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Youtube.ClassMgr.createYoutube(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Youtube
       * @param id Identifier for org.semanticwb.social.Youtube
       * @param model Model of the org.semanticwb.social.Youtube
       * @return A org.semanticwb.social.Youtube
       */
        public static org.semanticwb.social.Youtube getYoutube(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Youtube)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Youtube
       * @param id Identifier for org.semanticwb.social.Youtube
       * @param model Model of the org.semanticwb.social.Youtube
       * @return A org.semanticwb.social.Youtube
       */
        public static org.semanticwb.social.Youtube createYoutube(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Youtube)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Youtube
       * @param id Identifier for org.semanticwb.social.Youtube
       * @param model Model of the org.semanticwb.social.Youtube
       */
        public static void removeYoutube(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Youtube
       * @param id Identifier for org.semanticwb.social.Youtube
       * @param model Model of the org.semanticwb.social.Youtube
       * @return true if the org.semanticwb.social.Youtube exists, false otherwise
       */

        public static boolean hasYoutube(String id, org.semanticwb.model.SWBModel model)
        {
            return (getYoutube(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPostListenerContainer(org.semanticwb.social.PostInContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPostListenerContainer(org.semanticwb.social.PostInContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPostContainer(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPostContainer(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialPostInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialPostInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined Podur_SocialNetworkInv
       * @param value Podur_SocialNetworkInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPodur_SocialNetworkInv(org.semanticwb.social.PostOutDirectUserRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_SocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined Podur_SocialNetworkInv
       * @param value Podur_SocialNetworkInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPodur_SocialNetworkInv(org.semanticwb.social.PostOutDirectUserRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_SocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined YoutubeCategory
       * @param value YoutubeCategory of the type org.semanticwb.social.YouTubeCategory
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByYoutubeCategory(org.semanticwb.social.YouTubeCategory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasYoutubeCategory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined YoutubeCategory
       * @param value YoutubeCategory of the type org.semanticwb.social.YouTubeCategory
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByYoutubeCategory(org.semanticwb.social.YouTubeCategory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasYoutubeCategory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined Popr_socialNetworkInv
       * @param value Popr_socialNetworkInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPopr_socialNetworkInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_socialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined Popr_socialNetworkInv
       * @param value Popr_socialNetworkInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeByPopr_socialNetworkInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_socialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static YoutubeBase.ClassMgr getYoutubeClassMgr()
    {
        return new YoutubeBase.ClassMgr();
    }

   /**
   * Constructs a YoutubeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Youtube
   */
    public YoutubeBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the RefreshToken property
* @return String with the RefreshToken
*/
    public String getRefreshToken()
    {
        return getSemanticObject().getProperty(social_refreshToken);
    }

/**
* Sets the RefreshToken property
* @param value long with the RefreshToken
*/
    public void setRefreshToken(String value)
    {
        getSemanticObject().setProperty(social_refreshToken, value);
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
* Gets the DeveloperKey property
* @return String with the DeveloperKey
*/
    public String getDeveloperKey()
    {
        return getSemanticObject().getProperty(social_developerKey);
    }

/**
* Sets the DeveloperKey property
* @param value long with the DeveloperKey
*/
    public void setDeveloperKey(String value)
    {
        getSemanticObject().setProperty(social_developerKey, value);
    }
   /**
   * Gets all the org.semanticwb.social.YouTubeCategory
   * @return A GenericIterator with all the org.semanticwb.social.YouTubeCategory
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeCategory> listYoutubeCategories()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeCategory>(getSemanticObject().listObjectProperties(social_hasYoutubeCategory));
    }

   /**
   * Gets true if has a YoutubeCategory
   * @param value org.semanticwb.social.YouTubeCategory to verify
   * @return true if the org.semanticwb.social.YouTubeCategory exists, false otherwise
   */
    public boolean hasYoutubeCategory(org.semanticwb.social.YouTubeCategory value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasYoutubeCategory,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a YoutubeCategory
   * @param value org.semanticwb.social.YouTubeCategory to add
   */

    public void addYoutubeCategory(org.semanticwb.social.YouTubeCategory value)
    {
        getSemanticObject().addObjectProperty(social_hasYoutubeCategory, value.getSemanticObject());
    }
   /**
   * Removes all the YoutubeCategory
   */

    public void removeAllYoutubeCategory()
    {
        getSemanticObject().removeProperty(social_hasYoutubeCategory);
    }
   /**
   * Removes a YoutubeCategory
   * @param value org.semanticwb.social.YouTubeCategory to remove
   */

    public void removeYoutubeCategory(org.semanticwb.social.YouTubeCategory value)
    {
        getSemanticObject().removeObjectProperty(social_hasYoutubeCategory,value.getSemanticObject());
    }

   /**
   * Gets the YoutubeCategory
   * @return a org.semanticwb.social.YouTubeCategory
   */
    public org.semanticwb.social.YouTubeCategory getYoutubeCategory()
    {
         org.semanticwb.social.YouTubeCategory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasYoutubeCategory);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.YouTubeCategory)obj.createGenericInstance();
         }
         return ret;
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
