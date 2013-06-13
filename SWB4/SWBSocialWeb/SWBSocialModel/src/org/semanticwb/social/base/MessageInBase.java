package org.semanticwb.social.base;


   /**
   * Clase en donde se almacenan todos los post de tipo Mensaje y que entran por el Listener 
   */
public abstract class MessageInBase extends org.semanticwb.social.PostIn implements org.semanticwb.model.Traceable,org.semanticwb.social.Descriptable,org.semanticwb.social.PostDataable,org.semanticwb.model.Tagable,org.semanticwb.social.PostTextable
{
   /**
   * Clase en donde se almacenan todos los post de tipo Mensaje y que entran por el Listener
   */
    public static final org.semanticwb.platform.SemanticClass social_MessageIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#MessageIn");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#MessageIn");

    public static class ClassMgr
    {
       /**
       * Returns a list of MessageIn for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageIns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.MessageIn for all models
       * @return Iterator of org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageIns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn>(it, true);
        }

        public static org.semanticwb.social.MessageIn createMessageIn(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.MessageIn.ClassMgr.createMessageIn(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.MessageIn
       * @param id Identifier for org.semanticwb.social.MessageIn
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return A org.semanticwb.social.MessageIn
       */
        public static org.semanticwb.social.MessageIn getMessageIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.MessageIn)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.MessageIn
       * @param id Identifier for org.semanticwb.social.MessageIn
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return A org.semanticwb.social.MessageIn
       */
        public static org.semanticwb.social.MessageIn createMessageIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.MessageIn)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.MessageIn
       * @param id Identifier for org.semanticwb.social.MessageIn
       * @param model Model of the org.semanticwb.social.MessageIn
       */
        public static void removeMessageIn(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.MessageIn
       * @param id Identifier for org.semanticwb.social.MessageIn
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return true if the org.semanticwb.social.MessageIn exists, false otherwise
       */

        public static boolean hasMessageIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageIn(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByPostInStream(org.semanticwb.social.Stream value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInStream, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInByPostInStream(org.semanticwb.social.Stream value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInStream,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.MessageIn
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MessageIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.MessageIn
       */

        public static java.util.Iterator<org.semanticwb.social.MessageIn> listMessageInBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MessageIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MessageInBase.ClassMgr getMessageInClassMgr()
    {
        return new MessageInBase.ClassMgr();
    }

   /**
   * Constructs a MessageInBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MessageIn
   */
    public MessageInBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
