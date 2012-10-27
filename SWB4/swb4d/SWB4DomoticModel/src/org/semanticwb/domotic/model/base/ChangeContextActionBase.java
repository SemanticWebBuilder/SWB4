package org.semanticwb.domotic.model.base;


public abstract class ChangeContextActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeContextAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeContextAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeContextAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeContextAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeContextAction for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeContextAction createChangeContextAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.createChangeContextAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return A org.semanticwb.domotic.model.ChangeContextAction
       */
        public static org.semanticwb.domotic.model.ChangeContextAction getChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeContextAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return A org.semanticwb.domotic.model.ChangeContextAction
       */
        public static org.semanticwb.domotic.model.ChangeContextAction createChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeContextAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       */
        public static void removeChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return true if the org.semanticwb.domotic.model.ChangeContextAction exists, false otherwise
       */

        public static boolean hasChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeContextAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeContextActionBase.ClassMgr getChangeContextActionClassMgr()
    {
        return new ChangeContextActionBase.ClassMgr();
    }

   /**
   * Constructs a ChangeContextActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeContextAction
   */
    public ChangeContextActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
