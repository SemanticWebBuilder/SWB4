package org.semanticwb.domotic.model.base;


public abstract class ChangeStatActionBase extends org.semanticwb.domotic.model.DomAction 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeStatAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeStatAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeStatAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeStatAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeStatAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeStatAction> listChangeStatActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeStatAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeStatAction for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeStatAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeStatAction> listChangeStatActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeStatAction>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeStatAction createChangeStatAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeStatAction.ClassMgr.createChangeStatAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeStatAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeStatAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeStatAction
       * @return A org.semanticwb.domotic.model.ChangeStatAction
       */
        public static org.semanticwb.domotic.model.ChangeStatAction getChangeStatAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeStatAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeStatAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeStatAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeStatAction
       * @return A org.semanticwb.domotic.model.ChangeStatAction
       */
        public static org.semanticwb.domotic.model.ChangeStatAction createChangeStatAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeStatAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeStatAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeStatAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeStatAction
       */
        public static void removeChangeStatAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeStatAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeStatAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeStatAction
       * @return true if the org.semanticwb.domotic.model.ChangeStatAction exists, false otherwise
       */

        public static boolean hasChangeStatAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeStatAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeStatAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.ChangeStatAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeStatAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeStatAction> listChangeStatActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeStatAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeStatAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeStatAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeStatAction> listChangeStatActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeStatAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeStatAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeStatAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeStatAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeStatAction> listChangeStatActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeStatAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeStatAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeStatAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeStatAction> listChangeStatActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeStatAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeStatActionBase.ClassMgr getChangeStatActionClassMgr()
    {
        return new ChangeStatActionBase.ClassMgr();
    }

   /**
   * Constructs a ChangeStatActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeStatAction
   */
    public ChangeStatActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
