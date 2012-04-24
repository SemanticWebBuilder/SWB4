package org.semanticwb.social.base;


public abstract class FacebookBase extends org.semanticwb.social.SocialNetwork implements org.semanticwb.social.Videoable,org.semanticwb.social.Postable,org.semanticwb.model.Descriptiveable,org.semanticwb.social.Photoable,org.semanticwb.social.Secreteable,org.semanticwb.model.Traceable,org.semanticwb.social.Messageable
{
    public static final org.semanticwb.platform.SemanticProperty social_facebookUserId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#facebookUserId");
    public static final org.semanticwb.platform.SemanticClass social_Facebook=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Facebook");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Facebook");

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
       * Gets all org.semanticwb.social.Facebook with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostContainer(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByPostContainer(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Facebook
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Facebook with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Facebook
       */

        public static java.util.Iterator<org.semanticwb.social.Facebook> listFacebookByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Facebook> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
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

/**
* Gets the AppKey property
* @return String with the AppKey
*/
    public String getAppKey()
    {
        return getSemanticObject().getProperty(social_appKey);
    }

/**
* Sets the AppKey property
* @param value long with the AppKey
*/
    public void setAppKey(String value)
    {
        getSemanticObject().setProperty(social_appKey, value);
    }

/**
* Gets the FacebookUserId property
* @return String with the FacebookUserId
*/
    public String getFacebookUserId()
    {
        return getSemanticObject().getProperty(social_facebookUserId);
    }

/**
* Sets the FacebookUserId property
* @param value long with the FacebookUserId
*/
    public void setFacebookUserId(String value)
    {
        getSemanticObject().setProperty(social_facebookUserId, value);
    }

/**
* Gets the SecretKey property
* @return String with the SecretKey
*/
    public String getSecretKey()
    {
        return getSemanticObject().getProperty(social_secretKey);
    }

/**
* Sets the SecretKey property
* @param value long with the SecretKey
*/
    public void setSecretKey(String value)
    {
        getSemanticObject().setProperty(social_secretKey, value);
    }
}
