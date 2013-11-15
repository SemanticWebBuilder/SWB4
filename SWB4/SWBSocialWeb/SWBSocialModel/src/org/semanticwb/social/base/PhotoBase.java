package org.semanticwb.social.base;


   /**
   * Clase que sera creada cuando un post sea de tipo Foto (una imagen), como lo es flicker y facebook (cuando se envia una foto a facebook). 
   */
public abstract class PhotoBase extends org.semanticwb.social.PostOut implements org.semanticwb.model.Tagable,org.semanticwb.social.PostDataable,org.semanticwb.social.PostTextable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.social.PostImageable,org.semanticwb.model.CalendarRefable
{
   /**
   * Clase que sera creada cuando un post sea de tipo Foto (una imagen), como lo es flicker y facebook (cuando se envia una foto a facebook).
   */
    public static final org.semanticwb.platform.SemanticClass social_Photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Photo");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Photo");

    public static class ClassMgr
    {
       /**
       * Returns a list of Photo for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Photo for all models
       * @return Iterator of org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo>(it, true);
        }

        public static org.semanticwb.social.Photo createPhoto(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Photo.ClassMgr.createPhoto(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       * @return A org.semanticwb.social.Photo
       */
        public static org.semanticwb.social.Photo getPhoto(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Photo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       * @return A org.semanticwb.social.Photo
       */
        public static org.semanticwb.social.Photo createPhoto(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Photo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       */
        public static void removePhoto(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       * @return true if the org.semanticwb.social.Photo exists, false otherwise
       */

        public static boolean hasPhoto(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhoto(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostInSource(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostInSource(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostOutNetInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostOutNetInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPflowInstance(org.semanticwb.social.SocialPFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPflowInstance(org.semanticwb.social.SocialPFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined FastCalendar
       * @param value FastCalendar of the type org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByFastCalendar(org.semanticwb.social.FastCalendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_fastCalendar, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined FastCalendar
       * @param value FastCalendar of the type org.semanticwb.social.FastCalendar
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByFastCalendar(org.semanticwb.social.FastCalendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_fastCalendar,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PhotoBase.ClassMgr getPhotoClassMgr()
    {
        return new PhotoBase.ClassMgr();
    }

   /**
   * Constructs a PhotoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Photo
   */
    public PhotoBase(org.semanticwb.platform.SemanticObject base)
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
