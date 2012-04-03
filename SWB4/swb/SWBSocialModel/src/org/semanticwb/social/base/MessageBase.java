package org.semanticwb.social.base;


public abstract class MessageBase extends org.semanticwb.social.Post implements org.semanticwb.model.Expirable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass social_Message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Message");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Message");

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
       * Gets all org.semanticwb.social.Message with a determined PostInv
       * @param value PostInv of the type org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostInv(org.semanticwb.social.SocialNetWork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostInv
       * @param value PostInv of the type org.semanticwb.social.SocialNetWork
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostInv(org.semanticwb.social.SocialNetWork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a MessageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Message
   */
    public MessageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
