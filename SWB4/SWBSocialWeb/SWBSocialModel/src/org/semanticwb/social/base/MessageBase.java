package org.semanticwb.social.base;


   /**
   * Clase que sera creada cuando un post sea de tipo mensaje 
   */
public abstract class MessageBase extends org.semanticwb.social.PostOut implements org.semanticwb.model.Referensable,org.semanticwb.social.PostDataable,org.semanticwb.social.PostTextable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Tagable,org.semanticwb.social.FileAble
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
       * Gets all org.semanticwb.social.Message with a determined Podur_PostOutInv
       * @param value Podur_PostOutInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPodur_PostOutInv(org.semanticwb.social.PostOutDirectUserRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_PostOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined Podur_PostOutInv
       * @param value Podur_PostOutInv of the type org.semanticwb.social.PostOutDirectUserRelation
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPodur_PostOutInv(org.semanticwb.social.PostOutDirectUserRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspodur_PostOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostInSource(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostInSource(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.social.Message with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostOutNetInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPostOutNetInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv,value.getSemanticObject(),sclass));
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
       /**
       * Gets all org.semanticwb.social.Message with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPflowInstance(org.semanticwb.social.SocialPFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByPflowInstance(org.semanticwb.social.SocialPFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined FastCalendar
       * @param value FastCalendar of the type org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.Message
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByFastCalendar(org.semanticwb.social.FastCalendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_fastCalendar, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Message with a determined FastCalendar
       * @param value FastCalendar of the type org.semanticwb.social.FastCalendar
       * @return Iterator with all the org.semanticwb.social.Message
       */

        public static java.util.Iterator<org.semanticwb.social.Message> listMessageByFastCalendar(org.semanticwb.social.FastCalendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Message> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_fastCalendar,value.getSemanticObject(),sclass));
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

    public java.util.Iterator<String> listFiles()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(social_hasFile);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addFile(String value)
    {
        getSemanticObject().addLiteralProperty(social_hasFile, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllFile()
    {
        getSemanticObject().removeProperty(social_hasFile);
    }

    public void removeFile(String value)
    {
        getSemanticObject().removeLiteralProperty(social_hasFile,new org.semanticwb.platform.SemanticLiteral(value));
    }
}
