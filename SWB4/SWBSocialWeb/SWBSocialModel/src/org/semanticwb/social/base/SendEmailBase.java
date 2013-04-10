package org.semanticwb.social.base;


   /**
   * Acción específica mediante la cual se envía un correo electrónico 
   */
public abstract class SendEmailBase extends org.semanticwb.social.ActionMsg implements org.semanticwb.social.Emailable
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
}
