package org.semanticwb.domotic.model.base;


public abstract class StartTimerActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_StartTimerAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#StartTimerAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#StartTimerAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of StartTimerAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.StartTimerAction for all models
       * @return Iterator of org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction>(it, true);
        }

        public static org.semanticwb.domotic.model.StartTimerAction createStartTimerAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.createStartTimerAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return A org.semanticwb.domotic.model.StartTimerAction
       */
        public static org.semanticwb.domotic.model.StartTimerAction getStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.StartTimerAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return A org.semanticwb.domotic.model.StartTimerAction
       */
        public static org.semanticwb.domotic.model.StartTimerAction createStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.StartTimerAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       */
        public static void removeStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return true if the org.semanticwb.domotic.model.StartTimerAction exists, false otherwise
       */

        public static boolean hasStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStartTimerAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StartTimerActionBase.ClassMgr getStartTimerActionClassMgr()
    {
        return new StartTimerActionBase.ClassMgr();
    }

   /**
   * Constructs a StartTimerActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StartTimerAction
   */
    public StartTimerActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
