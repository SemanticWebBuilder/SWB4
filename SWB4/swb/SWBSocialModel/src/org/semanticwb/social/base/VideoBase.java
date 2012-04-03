package org.semanticwb.social.base;


public abstract class VideoBase extends org.semanticwb.social.Post implements org.semanticwb.model.Expirable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass social_Video=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Video");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Video");

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
       * Gets all org.semanticwb.social.Video with a determined PostInv
       * @param value PostInv of the type org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.Video
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostInv(org.semanticwb.social.SocialNetWork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Video with a determined PostInv
       * @param value PostInv of the type org.semanticwb.social.SocialNetWork
       * @return Iterator with all the org.semanticwb.social.Video
       */

        public static java.util.Iterator<org.semanticwb.social.Video> listVideoByPostInv(org.semanticwb.social.SocialNetWork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Video> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a VideoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Video
   */
    public VideoBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
