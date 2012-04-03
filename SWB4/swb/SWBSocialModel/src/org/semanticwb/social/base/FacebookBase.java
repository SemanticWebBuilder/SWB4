package org.semanticwb.social.base;


public abstract class FacebookBase extends org.semanticwb.social.SocialNetWork implements org.semanticwb.social.Photoable,org.semanticwb.social.Messageable
{
    public static final org.semanticwb.platform.SemanticClass social_Facebook=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Facebook");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Facebook");

    public static class ClassMgr
    {
       /**
       * Returns a list of Facebook for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebooks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Facebook for all models
       * @return Iterator of org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebooks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       * @return A org.semanticwb.social.Facebook
       */
        public static org.semanticwb.social.Facebook getFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Facebook)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       * @return A org.semanticwb.social.Facebook
       */
        public static org.semanticwb.social.Facebook createFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Facebook)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       */
        public static void removeFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Facebook
       * @param id Identifier for org.semanticwb.social.Facebook
       * @param model Model of the org.semanticwb.social.Facebook
       * @return true if the org.semanticwb.social.Facebook exists, false otherwise
       */

        public static boolean hasFacebook(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFacebook(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPost(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPost(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a FacebookBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Facebook
   */
    public FacebookBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
