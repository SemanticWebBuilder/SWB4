package org.semanticwb.domotic.model.base;


public abstract class TimerActionBase extends org.semanticwb.domotic.model.DomAction 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_TimerAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#TimerAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#TimerAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of TimerAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.TimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TimerAction> listTimerActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TimerAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.TimerAction for all models
       * @return Iterator of org.semanticwb.domotic.model.TimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TimerAction> listTimerActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TimerAction>(it, true);
        }

        public static org.semanticwb.domotic.model.TimerAction createTimerAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.TimerAction.ClassMgr.createTimerAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.TimerAction
       * @param id Identifier for org.semanticwb.domotic.model.TimerAction
       * @param model Model of the org.semanticwb.domotic.model.TimerAction
       * @return A org.semanticwb.domotic.model.TimerAction
       */
        public static org.semanticwb.domotic.model.TimerAction getTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.TimerAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.TimerAction
       * @param id Identifier for org.semanticwb.domotic.model.TimerAction
       * @param model Model of the org.semanticwb.domotic.model.TimerAction
       * @return A org.semanticwb.domotic.model.TimerAction
       */
        public static org.semanticwb.domotic.model.TimerAction createTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.TimerAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.TimerAction
       * @param id Identifier for org.semanticwb.domotic.model.TimerAction
       * @param model Model of the org.semanticwb.domotic.model.TimerAction
       */
        public static void removeTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.TimerAction
       * @param id Identifier for org.semanticwb.domotic.model.TimerAction
       * @param model Model of the org.semanticwb.domotic.model.TimerAction
       * @return true if the org.semanticwb.domotic.model.TimerAction exists, false otherwise
       */

        public static boolean hasTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimerAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.TimerAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.TimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.TimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TimerAction> listTimerActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.TimerAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.TimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TimerAction> listTimerActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.TimerAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.TimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.TimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TimerAction> listTimerActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.TimerAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.TimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TimerAction> listTimerActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TimerActionBase.ClassMgr getTimerActionClassMgr()
    {
        return new TimerActionBase.ClassMgr();
    }

   /**
   * Constructs a TimerActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TimerAction
   */
    public TimerActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
