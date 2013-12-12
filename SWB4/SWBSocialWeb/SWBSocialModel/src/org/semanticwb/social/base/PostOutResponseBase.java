package org.semanticwb.social.base;


public abstract class PostOutResponseBase extends org.semanticwb.social.Post implements org.semanticwb.model.Tagable,org.semanticwb.social.PostImageable,org.semanticwb.social.PostVideoable,org.semanticwb.social.PostDataable,org.semanticwb.social.PostTextable
{
    public static final org.semanticwb.platform.SemanticClass social_PostOutResponse=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutResponse");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutResponse");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutResponse for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutResponse
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutResponse> listPostOutResponses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutResponse>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutResponse for all models
       * @return Iterator of org.semanticwb.social.PostOutResponse
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutResponse> listPostOutResponses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutResponse>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostOutResponse
       * @param id Identifier for org.semanticwb.social.PostOutResponse
       * @param model Model of the org.semanticwb.social.PostOutResponse
       * @return A org.semanticwb.social.PostOutResponse
       */
        public static org.semanticwb.social.PostOutResponse getPostOutResponse(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutResponse)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutResponse
       * @param id Identifier for org.semanticwb.social.PostOutResponse
       * @param model Model of the org.semanticwb.social.PostOutResponse
       * @return A org.semanticwb.social.PostOutResponse
       */
        public static org.semanticwb.social.PostOutResponse createPostOutResponse(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutResponse)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutResponse
       * @param id Identifier for org.semanticwb.social.PostOutResponse
       * @param model Model of the org.semanticwb.social.PostOutResponse
       */
        public static void removePostOutResponse(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutResponse
       * @param id Identifier for org.semanticwb.social.PostOutResponse
       * @param model Model of the org.semanticwb.social.PostOutResponse
       * @return true if the org.semanticwb.social.PostOutResponse exists, false otherwise
       */

        public static boolean hasPostOutResponse(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutResponse(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutResponse with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.PostOutResponse
       * @return Iterator with all the org.semanticwb.social.PostOutResponse
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutResponse> listPostOutResponseByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutResponse> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutResponse with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.PostOutResponse
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutResponse> listPostOutResponseByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutResponse> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutResponse with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.PostOutResponse
       * @return Iterator with all the org.semanticwb.social.PostOutResponse
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutResponse> listPostOutResponseBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutResponse> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutResponse with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.PostOutResponse
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutResponse> listPostOutResponseBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutResponse> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutResponseBase.ClassMgr getPostOutResponseClassMgr()
    {
        return new PostOutResponseBase.ClassMgr();
    }

   /**
   * Constructs a PostOutResponseBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutResponse
   */
    public PostOutResponseBase(org.semanticwb.platform.SemanticObject base)
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

    public java.util.Iterator<String> listPhotos()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(social_hasPhoto);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addPhoto(String value)
    {
        getSemanticObject().addLiteralProperty(social_hasPhoto, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllPhoto()
    {
        getSemanticObject().removeProperty(social_hasPhoto);
    }

    public void removePhoto(String value)
    {
        getSemanticObject().removeLiteralProperty(social_hasPhoto,new org.semanticwb.platform.SemanticLiteral(value));
    }
}
