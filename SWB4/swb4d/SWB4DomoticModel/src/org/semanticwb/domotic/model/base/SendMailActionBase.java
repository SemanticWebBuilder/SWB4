package org.semanticwb.domotic.model.base;


public abstract class SendMailActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_SendMailAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendMailAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendMailAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of SendMailAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.SendMailAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendMailAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.SendMailAction for all models
       * @return Iterator of org.semanticwb.domotic.model.SendMailAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendMailAction>(it, true);
        }

        public static org.semanticwb.domotic.model.SendMailAction createSendMailAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.SendMailAction.ClassMgr.createSendMailAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.SendMailAction
       * @param id Identifier for org.semanticwb.domotic.model.SendMailAction
       * @param model Model of the org.semanticwb.domotic.model.SendMailAction
       * @return A org.semanticwb.domotic.model.SendMailAction
       */
        public static org.semanticwb.domotic.model.SendMailAction getSendMailAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.SendMailAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.SendMailAction
       * @param id Identifier for org.semanticwb.domotic.model.SendMailAction
       * @param model Model of the org.semanticwb.domotic.model.SendMailAction
       * @return A org.semanticwb.domotic.model.SendMailAction
       */
        public static org.semanticwb.domotic.model.SendMailAction createSendMailAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.SendMailAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.SendMailAction
       * @param id Identifier for org.semanticwb.domotic.model.SendMailAction
       * @param model Model of the org.semanticwb.domotic.model.SendMailAction
       */
        public static void removeSendMailAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.SendMailAction
       * @param id Identifier for org.semanticwb.domotic.model.SendMailAction
       * @param model Model of the org.semanticwb.domotic.model.SendMailAction
       * @return true if the org.semanticwb.domotic.model.SendMailAction exists, false otherwise
       */

        public static boolean hasSendMailAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSendMailAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendMailAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.SendMailAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendMailAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendMailAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendMailAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.SendMailAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendMailAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendMailAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.SendMailAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendMailAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendMailAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendMailAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.SendMailAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendMailAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SendMailActionBase.ClassMgr getSendMailActionClassMgr()
    {
        return new SendMailActionBase.ClassMgr();
    }

   /**
   * Constructs a SendMailActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SendMailAction
   */
    public SendMailActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
