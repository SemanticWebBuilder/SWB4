package org.semanticwb.domotic.model.base;


public abstract class SendSMSActionBase extends org.semanticwb.domotic.model.DomAction 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_SendSMSAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendSMSAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendSMSAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of SendSMSAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.SendSMSAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendSMSAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.SendSMSAction for all models
       * @return Iterator of org.semanticwb.domotic.model.SendSMSAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendSMSAction>(it, true);
        }
       /**
       * Gets a org.semanticwb.domotic.model.SendSMSAction
       * @param id Identifier for org.semanticwb.domotic.model.SendSMSAction
       * @param model Model of the org.semanticwb.domotic.model.SendSMSAction
       * @return A org.semanticwb.domotic.model.SendSMSAction
       */
        public static org.semanticwb.domotic.model.SendSMSAction getSendSMSAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.SendSMSAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.SendSMSAction
       * @param id Identifier for org.semanticwb.domotic.model.SendSMSAction
       * @param model Model of the org.semanticwb.domotic.model.SendSMSAction
       * @return A org.semanticwb.domotic.model.SendSMSAction
       */
        public static org.semanticwb.domotic.model.SendSMSAction createSendSMSAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.SendSMSAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.SendSMSAction
       * @param id Identifier for org.semanticwb.domotic.model.SendSMSAction
       * @param model Model of the org.semanticwb.domotic.model.SendSMSAction
       */
        public static void removeSendSMSAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.SendSMSAction
       * @param id Identifier for org.semanticwb.domotic.model.SendSMSAction
       * @param model Model of the org.semanticwb.domotic.model.SendSMSAction
       * @return true if the org.semanticwb.domotic.model.SendSMSAction exists, false otherwise
       */

        public static boolean hasSendSMSAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSendSMSAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendSMSAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.SendSMSAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendSMSAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendSMSAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendSMSAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.SendSMSAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendSMSAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendSMSAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.SendSMSAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendSMSAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendSMSAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendSMSAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.SendSMSAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendSMSAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SendSMSActionBase.ClassMgr getSendSMSActionClassMgr()
    {
        return new SendSMSActionBase.ClassMgr();
    }

   /**
   * Constructs a SendSMSActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SendSMSAction
   */
    public SendSMSActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
