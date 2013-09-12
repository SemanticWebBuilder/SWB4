package org.semanticwb.process.model.base;


public abstract class NotificationTemplateBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swp_notificationTemplateSubject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#notificationTemplateSubject");
    public static final org.semanticwb.platform.SemanticProperty swp_notificationTemplateBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#notificationTemplateBody");
    public static final org.semanticwb.platform.SemanticClass swp_NotificationTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#NotificationTemplate");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#NotificationTemplate");

    public static class ClassMgr
    {
       /**
       * Returns a list of NotificationTemplate for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.NotificationTemplate for all models
       * @return Iterator of org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate>(it, true);
        }

        public static org.semanticwb.process.model.NotificationTemplate createNotificationTemplate(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.NotificationTemplate.ClassMgr.createNotificationTemplate(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.NotificationTemplate
       * @param id Identifier for org.semanticwb.process.model.NotificationTemplate
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       * @return A org.semanticwb.process.model.NotificationTemplate
       */
        public static org.semanticwb.process.model.NotificationTemplate getNotificationTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.NotificationTemplate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.NotificationTemplate
       * @param id Identifier for org.semanticwb.process.model.NotificationTemplate
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       * @return A org.semanticwb.process.model.NotificationTemplate
       */
        public static org.semanticwb.process.model.NotificationTemplate createNotificationTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.NotificationTemplate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.NotificationTemplate
       * @param id Identifier for org.semanticwb.process.model.NotificationTemplate
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       */
        public static void removeNotificationTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.NotificationTemplate
       * @param id Identifier for org.semanticwb.process.model.NotificationTemplate
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       * @return true if the org.semanticwb.process.model.NotificationTemplate exists, false otherwise
       */

        public static boolean hasNotificationTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNotificationTemplate(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.NotificationTemplate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       * @return Iterator with all the org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplateByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.NotificationTemplate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplateByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.NotificationTemplate with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       * @return Iterator with all the org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplateByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.NotificationTemplate with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplateByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.NotificationTemplate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.NotificationTemplate
       * @return Iterator with all the org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplateByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.NotificationTemplate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.NotificationTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.NotificationTemplate> listNotificationTemplateByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NotificationTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static NotificationTemplateBase.ClassMgr getNotificationTemplateClassMgr()
    {
        return new NotificationTemplateBase.ClassMgr();
    }

   /**
   * Constructs a NotificationTemplateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the NotificationTemplate
   */
    public NotificationTemplateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Subject property
* @return String with the Subject
*/
    public String getSubject()
    {
        return getSemanticObject().getProperty(swp_notificationTemplateSubject);
    }

/**
* Sets the Subject property
* @param value long with the Subject
*/
    public void setSubject(String value)
    {
        getSemanticObject().setProperty(swp_notificationTemplateSubject, value);
    }

/**
* Gets the Body property
* @return String with the Body
*/
    public String getBody()
    {
        return getSemanticObject().getProperty(swp_notificationTemplateBody);
    }

/**
* Sets the Body property
* @param value long with the Body
*/
    public void setBody(String value)
    {
        getSemanticObject().setProperty(swp_notificationTemplateBody, value);
    }
}
