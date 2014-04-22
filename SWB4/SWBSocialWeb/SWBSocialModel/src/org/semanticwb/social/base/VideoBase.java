package org.semanticwb.social.base;


   /**
   * Clase que sera creada cuando un post sea de tipo video, como lo es para youtube y facebook (cuando se envia un video a facebook). 
   */
public abstract class VideoBase extends org.semanticwb.social.PostOut implements org.semanticwb.model.Referensable,org.semanticwb.social.PostTextable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable,org.semanticwb.social.PostDataable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.CalendarRefable,org.semanticwb.social.PostVideoable
{
   /**
   * Clase que sera creada cuando un post sea de tipo video, como lo es para youtube y facebook (cuando se envia un video a facebook).
   */
    public static final org.semanticwb.platform.SemanticClass social_Video=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Video");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Video");

    public static class ClassMgr
    {
       /**
       * Returns a list of Video for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Video>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Video for all models
       * @return Iterator of org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Video>(it, true);
        }

        public static org.semanticwb.social.Video createVideo(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Video.ClassMgr.createVideo(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Video
       * @param id Identifier for org.semanticwb.social.Video
       * @param model Model of the org.semanticwb.social.Video
       * @return A org.semanticwb.social.Video
       */
        public static org.semanticwb.social.Video getVideo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Video)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Video
       * @param id Identifier for org.semanticwb.social.Video
       * @param model Model of the org.semanticwb.social.Video
       * @return A org.semanticwb.social.Video
       */
        public static org.semanticwb.social.Video createVideo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Video)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Video
       * @param id Identifier for org.semanticwb.social.Video
       * @param model Model of the org.semanticwb.social.Video
       */
        public static void removeVideo(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Video
       * @param id Identifier for org.semanticwb.social.Video
       * @param model Model of the org.semanticwb.social.Video
       * @return true if the org.semanticwb.social.Video exists, false otherwise
       */

        public static boolean hasVideo(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVideo(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Podur_PostOutInv
       * @param value Podur_PostOutInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPodur_PostOutInv(org.semanticwb.social.PostOutDirectUserRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_PostOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Podur_PostOutInv
       * @param value Podur_PostOutInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPodur_PostOutInv(org.semanticwb.social.PostOutDirectUserRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_PostOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostOutLinksHitsInv
       * @param value PostOutLinksHitsInv of the type org.semanticwb.social.PostOutLinksHits
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostOutLinksHitsInv(org.semanticwb.social.PostOutLinksHits value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutLinksHitsInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostOutLinksHitsInv
       * @param value PostOutLinksHitsInv of the type org.semanticwb.social.PostOutLinksHits
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostOutLinksHitsInv(org.semanticwb.social.PostOutLinksHits value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutLinksHitsInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Msg_lang
       * @param value Msg_lang of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByMsg_lang(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_msg_lang, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Msg_lang
       * @param value Msg_lang of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByMsg_lang(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_msg_lang,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined FastCalendar
       * @param value FastCalendar of the type org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByFastCalendar(org.semanticwb.social.FastCalendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_fastCalendar, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined FastCalendar
       * @param value FastCalendar of the type org.semanticwb.social.FastCalendar
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByFastCalendar(org.semanticwb.social.FastCalendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_fastCalendar,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostInSource(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostInSource(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostOutNetInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostOutNetInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPflowInstance(org.semanticwb.social.SocialPFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPflowInstance(org.semanticwb.social.SocialPFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static VideoBase.ClassMgr getVideoClassMgr()
    {
        return new VideoBase.ClassMgr();
    }

   /**
   * Constructs a VideoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Video
   */
    public VideoBase(org.semanticwb.platform.SemanticObject base)
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

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
