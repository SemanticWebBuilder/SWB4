package org.semanticwb.social.base;


   /**
   * Clase que sera creada cuando un post sea de tipo mensaje 
   */
public abstract class MessageBase extends org.semanticwb.social.PostOut implements org.semanticwb.model.Traceable,org.semanticwb.social.PostDataable,org.semanticwb.model.Tagable,org.semanticwb.social.PostTextable,org.semanticwb.model.Descriptiveable
{
   /**
   * Clase que sera creada cuando un post sea de tipo mensaje
   */
    public static final org.semanticwb.platform.SemanticClass social_Message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Message");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Message");

    public static class ClassMgr
    {
       /**
       * Returns a list of Message for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Message>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Message for all models
       * @return Iterator of org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Message>(it, true);
        }

        public static org.semanticwb.social.Message createMessage(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Message.ClassMgr.createMessage(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Message
       * @param id Identifier for org.semanticwb.social.Message
       * @param model Model of the org.semanticwb.social.Message
       * @return A org.semanticwb.social.Message
       */
        public static org.semanticwb.social.Message getMessage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Message)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Message
       * @param id Identifier for org.semanticwb.social.Message
       * @param model Model of the org.semanticwb.social.Message
       * @return A org.semanticwb.social.Message
       */
        public static org.semanticwb.social.Message createMessage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Message)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Message
       * @param id Identifier for org.semanticwb.social.Message
       * @param model Model of the org.semanticwb.social.Message
       */
        public static void removeMessage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Message
       * @param id Identifier for org.semanticwb.social.Message
       * @param model Model of the org.semanticwb.social.Message
       * @return true if the org.semanticwb.social.Message exists, false otherwise
       */

        public static boolean hasMessage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostContainer_PostInv(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostContainer_PostInv(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MessageBase.ClassMgr getMessageClassMgr()
    {
        return new MessageBase.ClassMgr();
    }

   /**
   * Constructs a MessageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Message
   */
    public MessageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Msg_Text property
* @return String with the Msg_Text
*/
    public String getMsg_Text()
    {
        return getSemanticObject().getProperty(social_msg_Text);
    }

/**
* Sets the Msg_Text property
* @param value long with the Msg_Text
*/
    public void setMsg_Text(String value)
    {
        getSemanticObject().setProperty(social_msg_Text, value);
    }
}
