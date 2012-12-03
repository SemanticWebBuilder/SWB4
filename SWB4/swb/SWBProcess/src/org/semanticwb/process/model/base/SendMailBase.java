package org.semanticwb.process.model.base;


public abstract class SendMailBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticProperty swp_sendMailTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sendMailTo");
    public static final org.semanticwb.platform.SemanticProperty swp_sendMailContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sendMailContent");
    public static final org.semanticwb.platform.SemanticProperty swp_sendMailFrom=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sendMailFrom");
    public static final org.semanticwb.platform.SemanticProperty swp_sendMailSubject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sendMailSubject");
    public static final org.semanticwb.platform.SemanticClass swp_SendMail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SendMail");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SendMail");

    public static class ClassMgr
    {
       /**
       * Returns a list of SendMail for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SendMail
       */

        public static java.util.Iterator<org.semanticwb.process.model.SendMail> listSendMails(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SendMail>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SendMail for all models
       * @return Iterator of org.semanticwb.process.model.SendMail
       */

        public static java.util.Iterator<org.semanticwb.process.model.SendMail> listSendMails()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SendMail>(it, true);
        }

        public static org.semanticwb.process.model.SendMail createSendMail(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SendMail.ClassMgr.createSendMail(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SendMail
       * @param id Identifier for org.semanticwb.process.model.SendMail
       * @param model Model of the org.semanticwb.process.model.SendMail
       * @return A org.semanticwb.process.model.SendMail
       */
        public static org.semanticwb.process.model.SendMail getSendMail(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SendMail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SendMail
       * @param id Identifier for org.semanticwb.process.model.SendMail
       * @param model Model of the org.semanticwb.process.model.SendMail
       * @return A org.semanticwb.process.model.SendMail
       */
        public static org.semanticwb.process.model.SendMail createSendMail(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SendMail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SendMail
       * @param id Identifier for org.semanticwb.process.model.SendMail
       * @param model Model of the org.semanticwb.process.model.SendMail
       */
        public static void removeSendMail(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SendMail
       * @param id Identifier for org.semanticwb.process.model.SendMail
       * @param model Model of the org.semanticwb.process.model.SendMail
       * @return true if the org.semanticwb.process.model.SendMail exists, false otherwise
       */

        public static boolean hasSendMail(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSendMail(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.SendMail with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.SendMail
       * @return Iterator with all the org.semanticwb.process.model.SendMail
       */

        public static java.util.Iterator<org.semanticwb.process.model.SendMail> listSendMailByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SendMail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SendMail with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.SendMail
       */

        public static java.util.Iterator<org.semanticwb.process.model.SendMail> listSendMailByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SendMail> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SendMailBase.ClassMgr getSendMailClassMgr()
    {
        return new SendMailBase.ClassMgr();
    }

   /**
   * Constructs a SendMailBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SendMail
   */
    public SendMailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the To property
* @return String with the To
*/
    public String getTo()
    {
        return getSemanticObject().getProperty(swp_sendMailTo);
    }

/**
* Sets the To property
* @param value long with the To
*/
    public void setTo(String value)
    {
        getSemanticObject().setProperty(swp_sendMailTo, value);
    }

/**
* Gets the Content property
* @return String with the Content
*/
    public String getContent()
    {
        return getSemanticObject().getProperty(swp_sendMailContent);
    }

/**
* Sets the Content property
* @param value long with the Content
*/
    public void setContent(String value)
    {
        getSemanticObject().setProperty(swp_sendMailContent, value);
    }

/**
* Gets the From property
* @return String with the From
*/
    public String getFrom()
    {
        return getSemanticObject().getProperty(swp_sendMailFrom);
    }

/**
* Sets the From property
* @param value long with the From
*/
    public void setFrom(String value)
    {
        getSemanticObject().setProperty(swp_sendMailFrom, value);
    }

/**
* Gets the Subject property
* @return String with the Subject
*/
    public String getSubject()
    {
        return getSemanticObject().getProperty(swp_sendMailSubject);
    }

/**
* Sets the Subject property
* @param value long with the Subject
*/
    public void setSubject(String value)
    {
        getSemanticObject().setProperty(swp_sendMailSubject, value);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
