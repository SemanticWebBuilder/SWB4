package org.semanticwb.social.base;


   /**
   * Clase en donde se almacenan todos los post de tipo Video y que entran por el Listener 
   */
public abstract class VideoInBase extends org.semanticwb.social.PostIn implements org.semanticwb.model.Descriptiveable,org.semanticwb.social.PostTextable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable,org.semanticwb.social.PostDataable,org.semanticwb.social.PostVideoable
{
   /**
   * Clase en donde se almacenan todos los post de tipo Video y que entran por el Listener
   */
    public static final org.semanticwb.platform.SemanticClass social_VideoIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#VideoIn");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#VideoIn");

    public static class ClassMgr
    {
       /**
       * Returns a list of VideoIn for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoIns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.VideoIn for all models
       * @return Iterator of org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoIns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn>(it, true);
        }

        public static org.semanticwb.social.VideoIn createVideoIn(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.VideoIn.ClassMgr.createVideoIn(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.VideoIn
       * @param id Identifier for org.semanticwb.social.VideoIn
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return A org.semanticwb.social.VideoIn
       */
        public static org.semanticwb.social.VideoIn getVideoIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.VideoIn)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.VideoIn
       * @param id Identifier for org.semanticwb.social.VideoIn
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return A org.semanticwb.social.VideoIn
       */
        public static org.semanticwb.social.VideoIn createVideoIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.VideoIn)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.VideoIn
       * @param id Identifier for org.semanticwb.social.VideoIn
       * @param model Model of the org.semanticwb.social.VideoIn
       */
        public static void removeVideoIn(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.VideoIn
       * @param id Identifier for org.semanticwb.social.VideoIn
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return true if the org.semanticwb.social.VideoIn exists, false otherwise
       */

        public static boolean hasVideoIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVideoIn(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostOutResponseInv
       * @param value PostOutResponseInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostOutResponseInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspostOutResponseInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostOutResponseInv
       * @param value PostOutResponseInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostOutResponseInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspostOutResponseInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostInStream(org.semanticwb.social.Stream value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInStream, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInByPostInStream(org.semanticwb.social.Stream value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInStream,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.VideoIn
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.VideoIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.VideoIn
       */

        public static java.util.Iterator<org.semanticwb.social.VideoIn> listVideoInBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.VideoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static VideoInBase.ClassMgr getVideoInClassMgr()
    {
        return new VideoInBase.ClassMgr();
    }

   /**
   * Constructs a VideoInBase with a SemanticObject
   * @param base The SemanticObject with the properties for the VideoIn
   */
    public VideoInBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Video property
* @return String with the Video
*/
    public String getVideo()
    {
        return getSemanticObject().getProperty(social_video);
    }

/**
* Sets the Video property
* @param value long with the Video
*/
    public void setVideo(String value)
    {
        getSemanticObject().setProperty(social_video, value);
    }

/**
* Gets the Category property
* @return String with the Category
*/
    public String getCategory()
    {
        return getSemanticObject().getProperty(social_category);
    }

/**
* Sets the Category property
* @param value long with the Category
*/
    public void setCategory(String value)
    {
        getSemanticObject().setProperty(social_category, value);
    }
}
