package org.semanticwb.domotic.model.base;


public abstract class ChangeEventBase extends org.semanticwb.domotic.model.DomEvent 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeEvent> listChangeEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeEvent for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeEvent> listChangeEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeEvent>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeEvent createChangeEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeEvent.ClassMgr.createChangeEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeEvent
       * @param id Identifier for org.semanticwb.domotic.model.ChangeEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeEvent
       * @return A org.semanticwb.domotic.model.ChangeEvent
       */
        public static org.semanticwb.domotic.model.ChangeEvent getChangeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeEvent
       * @param id Identifier for org.semanticwb.domotic.model.ChangeEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeEvent
       * @return A org.semanticwb.domotic.model.ChangeEvent
       */
        public static org.semanticwb.domotic.model.ChangeEvent createChangeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeEvent
       * @param id Identifier for org.semanticwb.domotic.model.ChangeEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeEvent
       */
        public static void removeChangeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeEvent
       * @param id Identifier for org.semanticwb.domotic.model.ChangeEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeEvent
       * @return true if the org.semanticwb.domotic.model.ChangeEvent exists, false otherwise
       */

        public static boolean hasChangeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeEvent> listChangeEventByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeEvent> listChangeEventByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.ChangeEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeEvent> listChangeEventByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeEvent> listChangeEventByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeEventBase.ClassMgr getChangeEventClassMgr()
    {
        return new ChangeEventBase.ClassMgr();
    }

   /**
   * Constructs a ChangeEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeEvent
   */
    public ChangeEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
