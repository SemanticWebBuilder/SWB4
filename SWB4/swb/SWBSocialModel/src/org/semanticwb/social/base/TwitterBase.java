package org.semanticwb.social.base;


public abstract class TwitterBase extends org.semanticwb.social.SocialNetWork implements org.semanticwb.social.Photoable,org.semanticwb.social.Messageable
{
    public static final org.semanticwb.platform.SemanticClass social_Twitter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Twitter");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Twitter");

    public static class ClassMgr
    {
       /**
       * Returns a list of Twitter for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitters(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Twitter for all models
       * @return Iterator of org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitters()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       * @return A org.semanticwb.social.Twitter
       */
        public static org.semanticwb.social.Twitter getTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Twitter)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       * @return A org.semanticwb.social.Twitter
       */
        public static org.semanticwb.social.Twitter createTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Twitter)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       */
        public static void removeTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Twitter
       * @param id Identifier for org.semanticwb.social.Twitter
       * @param model Model of the org.semanticwb.social.Twitter
       * @return true if the org.semanticwb.social.Twitter exists, false otherwise
       */

        public static boolean hasTwitter(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTwitter(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Twitter
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPost(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Twitter with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Twitter
       */

        public static java.util.Iterator<org.semanticwb.social.Twitter> listTwitterByPost(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Twitter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a TwitterBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Twitter
   */
    public TwitterBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
