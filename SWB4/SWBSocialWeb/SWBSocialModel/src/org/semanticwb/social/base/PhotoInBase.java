package org.semanticwb.social.base;


   /**
   * Clase en donde se almacenan todos los post de tipo Photo y que entran por el Listener 
   */
public abstract class PhotoInBase extends org.semanticwb.social.PostIn implements org.semanticwb.social.PostImageable,org.semanticwb.model.Traceable,org.semanticwb.social.PostTextable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Tagable,org.semanticwb.social.PostDataable
{
   /**
   * Clase en donde se almacenan todos los post de tipo Photo y que entran por el Listener
   */
    public static final org.semanticwb.platform.SemanticClass social_PhotoIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PhotoIn");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PhotoIn");

    public static class ClassMgr
    {
       /**
       * Returns a list of PhotoIn for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoIns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PhotoIn for all models
       * @return Iterator of org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoIns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn>(it, true);
        }

        public static org.semanticwb.social.PhotoIn createPhotoIn(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PhotoIn.ClassMgr.createPhotoIn(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PhotoIn
       * @param id Identifier for org.semanticwb.social.PhotoIn
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return A org.semanticwb.social.PhotoIn
       */
        public static org.semanticwb.social.PhotoIn getPhotoIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PhotoIn)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PhotoIn
       * @param id Identifier for org.semanticwb.social.PhotoIn
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return A org.semanticwb.social.PhotoIn
       */
        public static org.semanticwb.social.PhotoIn createPhotoIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PhotoIn)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PhotoIn
       * @param id Identifier for org.semanticwb.social.PhotoIn
       * @param model Model of the org.semanticwb.social.PhotoIn
       */
        public static void removePhotoIn(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PhotoIn
       * @param id Identifier for org.semanticwb.social.PhotoIn
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return true if the org.semanticwb.social.PhotoIn exists, false otherwise
       */

        public static boolean hasPhotoIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhotoIn(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostOutResponseInv
       * @param value PostOutResponseInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostOutResponseInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspostOutResponseInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostOutResponseInv
       * @param value PostOutResponseInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostOutResponseInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspostOutResponseInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostInStream(org.semanticwb.social.Stream value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInStream, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInByPostInStream(org.semanticwb.social.Stream value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInStream,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.PhotoIn
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PhotoIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.PhotoIn
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoIn> listPhotoInBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PhotoInBase.ClassMgr getPhotoInClassMgr()
    {
        return new PhotoInBase.ClassMgr();
    }

   /**
   * Constructs a PhotoInBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PhotoIn
   */
    public PhotoInBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
