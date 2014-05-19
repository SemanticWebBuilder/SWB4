package org.semanticwb.social.base;


   /**
   * Acción específica mediante la cual se envía un correo electrónico 
   */
public abstract class SendEmailBase extends org.semanticwb.social.ActionMsg implements org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.social.Emailable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.FilterableClass
{
   /**
   * Acción específica mediante la cual se envía un correo electrónico
   */
    public static final org.semanticwb.platform.SemanticClass social_SendEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SendEmail");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SendEmail");

    public static class ClassMgr
    {
       /**
       * Returns a list of SendEmail for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmails(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SendEmail for all models
       * @return Iterator of org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmails()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail>(it, true);
        }

        public static org.semanticwb.social.SendEmail createSendEmail(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SendEmail.ClassMgr.createSendEmail(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SendEmail
       * @param id Identifier for org.semanticwb.social.SendEmail
       * @param model Model of the org.semanticwb.social.SendEmail
       * @return A org.semanticwb.social.SendEmail
       */
        public static org.semanticwb.social.SendEmail getSendEmail(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SendEmail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SendEmail
       * @param id Identifier for org.semanticwb.social.SendEmail
       * @param model Model of the org.semanticwb.social.SendEmail
       * @return A org.semanticwb.social.SendEmail
       */
        public static org.semanticwb.social.SendEmail createSendEmail(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SendEmail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SendEmail
       * @param id Identifier for org.semanticwb.social.SendEmail
       * @param model Model of the org.semanticwb.social.SendEmail
       */
        public static void removeSendEmail(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SendEmail
       * @param id Identifier for org.semanticwb.social.SendEmail
       * @param model Model of the org.semanticwb.social.SendEmail
       * @return true if the org.semanticwb.social.SendEmail exists, false otherwise
       */

        public static boolean hasSendEmail(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSendEmail(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SendEmail with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SendEmail
       * @return Iterator with all the org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmailByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendEmail with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmailByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendEmail with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SendEmail
       * @return Iterator with all the org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmailByActionRuleInv(org.semanticwb.social.SocialRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendEmail with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmailByActionRuleInv(org.semanticwb.social.SocialRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendEmail with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SendEmail
       * @return Iterator with all the org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmailByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendEmail with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SendEmail
       */

        public static java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmailByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendEmail> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SendEmailBase.ClassMgr getSendEmailClassMgr()
    {
        return new SendEmailBase.ClassMgr();
    }

   /**
   * Constructs a SendEmailBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SendEmail
   */
    public SendEmailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Email property
* @return String with the Email
*/
    public String getEmail()
    {
        return getSemanticObject().getProperty(social_email);
    }

/**
* Sets the Email property
* @param value long with the Email
*/
    public void setEmail(String value)
    {
        getSemanticObject().setProperty(social_email, value);
    }

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
