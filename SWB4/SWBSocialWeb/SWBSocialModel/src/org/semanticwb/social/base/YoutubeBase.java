package org.semanticwb.social.base;


   /**
   * Clase que almacenara las diferentes cuentas de una organización para la red social YouTube. 
   */
public abstract class YoutubeBase extends org.semanticwb.social.SocialNetwork implements org.semanticwb.social.Listenerable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.social.Videoable,org.semanticwb.social.Secreteable,org.semanticwb.social.Authenticable,org.semanticwb.model.Traceable,org.semanticwb.social.SocialNetPostable
{
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
       * @param value SocialPostInv of the type org.semanticwb.social.SocialPost
       * @param model Model of the org.semanticwb.social.Youtube
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialPostInv(org.semanticwb.social.SocialPost value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Youtube with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.SocialPost
       * @return Iterator with all the org.semanticwb.social.Youtube
       */

        public static java.util.Iterator<org.semanticwb.social.Youtube> listYoutubeBySocialPostInv(org.semanticwb.social.SocialPost value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Youtube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv,value.getSemanticObject(),sclass));
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
* Gets the Password property
* @return String with the Password
*/
    public String getPassword()
    {
        return getSemanticObject().getProperty(social_password);
    }

/**
* Sets the Password property
* @param value long with the Password
*/
    public void setPassword(String value)
    {
        getSemanticObject().setProperty(social_password, value);
    }

/**
* Gets the Login property
* @return String with the Login
*/
    public String getLogin()
    {
        return getSemanticObject().getProperty(social_login);
    }

/**
* Sets the Login property
* @param value long with the Login
*/
    public void setLogin(String value)
    {
        getSemanticObject().setProperty(social_login, value);
    }
}
