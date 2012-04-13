package org.semanticwb.social.base;


public abstract class YouTubeBase extends org.semanticwb.social.SocialNetWork implements org.semanticwb.model.Descriptiveable,org.semanticwb.social.Postable,org.semanticwb.model.Traceable,org.semanticwb.social.Videoable
{
    public static final org.semanticwb.platform.SemanticClass social_YouTube=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#YouTube");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#YouTube");

    public static class ClassMgr
    {
       /**
       * Returns a list of YouTube for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.YouTube for all models
       * @return Iterator of org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.YouTube
       * @param id Identifier for org.semanticwb.social.YouTube
       * @param model Model of the org.semanticwb.social.YouTube
       * @return A org.semanticwb.social.YouTube
       */
        public static org.semanticwb.social.YouTube getYouTube(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YouTube)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.YouTube
       * @param id Identifier for org.semanticwb.social.YouTube
       * @param model Model of the org.semanticwb.social.YouTube
       * @return A org.semanticwb.social.YouTube
       */
        public static org.semanticwb.social.YouTube createYouTube(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YouTube)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.YouTube
       * @param id Identifier for org.semanticwb.social.YouTube
       * @param model Model of the org.semanticwb.social.YouTube
       */
        public static void removeYouTube(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.YouTube
       * @param id Identifier for org.semanticwb.social.YouTube
       * @param model Model of the org.semanticwb.social.YouTube
       * @return true if the org.semanticwb.social.YouTube exists, false otherwise
       */

        public static boolean hasYouTube(String id, org.semanticwb.model.SWBModel model)
        {
            return (getYouTube(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.YouTube with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.YouTube
       * @return Iterator with all the org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTube with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTube with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.YouTube
       * @return Iterator with all the org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTube with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTube with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.YouTube
       * @return Iterator with all the org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubeByPostContainer(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTube with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.YouTube
       */

        public static java.util.Iterator<org.semanticwb.social.YouTube> listYouTubeByPostContainer(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTube> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a YouTubeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the YouTube
   */
    public YouTubeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
