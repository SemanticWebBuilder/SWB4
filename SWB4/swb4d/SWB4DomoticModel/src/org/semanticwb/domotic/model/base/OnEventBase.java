package org.semanticwb.domotic.model.base;


public abstract class OnEventBase extends org.semanticwb.domotic.model.DomEvent 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_OnEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnEvent> listOnEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnEvent for all models
       * @return Iterator of org.semanticwb.domotic.model.OnEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnEvent> listOnEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnEvent>(it, true);
        }

        public static org.semanticwb.domotic.model.OnEvent createOnEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnEvent.ClassMgr.createOnEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnEvent
       * @param model Model of the org.semanticwb.domotic.model.OnEvent
       * @return A org.semanticwb.domotic.model.OnEvent
       */
        public static org.semanticwb.domotic.model.OnEvent getOnEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnEvent
       * @param model Model of the org.semanticwb.domotic.model.OnEvent
       * @return A org.semanticwb.domotic.model.OnEvent
       */
        public static org.semanticwb.domotic.model.OnEvent createOnEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnEvent
       * @param model Model of the org.semanticwb.domotic.model.OnEvent
       */
        public static void removeOnEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnEvent
       * @param model Model of the org.semanticwb.domotic.model.OnEvent
       * @return true if the org.semanticwb.domotic.model.OnEvent exists, false otherwise
       */

        public static boolean hasOnEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnEvent
       * @return Iterator with all the org.semanticwb.domotic.model.OnEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnEvent> listOnEventByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnEvent> listOnEventByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnEvent
       * @return Iterator with all the org.semanticwb.domotic.model.OnEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnEvent> listOnEventByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnEvent> listOnEventByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnEventBase.ClassMgr getOnEventClassMgr()
    {
        return new OnEventBase.ClassMgr();
    }

   /**
   * Constructs a OnEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnEvent
   */
    public OnEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
