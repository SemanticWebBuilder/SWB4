package org.semanticwb.domotic.model.base;


public abstract class OffEventBase extends org.semanticwb.domotic.model.DomEvent 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_OffEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OffEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OffEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of OffEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OffEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OffEvent> listOffEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OffEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OffEvent for all models
       * @return Iterator of org.semanticwb.domotic.model.OffEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OffEvent> listOffEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OffEvent>(it, true);
        }

        public static org.semanticwb.domotic.model.OffEvent createOffEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OffEvent.ClassMgr.createOffEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OffEvent
       * @param id Identifier for org.semanticwb.domotic.model.OffEvent
       * @param model Model of the org.semanticwb.domotic.model.OffEvent
       * @return A org.semanticwb.domotic.model.OffEvent
       */
        public static org.semanticwb.domotic.model.OffEvent getOffEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OffEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OffEvent
       * @param id Identifier for org.semanticwb.domotic.model.OffEvent
       * @param model Model of the org.semanticwb.domotic.model.OffEvent
       * @return A org.semanticwb.domotic.model.OffEvent
       */
        public static org.semanticwb.domotic.model.OffEvent createOffEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OffEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OffEvent
       * @param id Identifier for org.semanticwb.domotic.model.OffEvent
       * @param model Model of the org.semanticwb.domotic.model.OffEvent
       */
        public static void removeOffEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OffEvent
       * @param id Identifier for org.semanticwb.domotic.model.OffEvent
       * @param model Model of the org.semanticwb.domotic.model.OffEvent
       * @return true if the org.semanticwb.domotic.model.OffEvent exists, false otherwise
       */

        public static boolean hasOffEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOffEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OffEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OffEvent
       * @return Iterator with all the org.semanticwb.domotic.model.OffEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OffEvent> listOffEventByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OffEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OffEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OffEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OffEvent> listOffEventByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OffEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OffEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OffEvent
       * @return Iterator with all the org.semanticwb.domotic.model.OffEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OffEvent> listOffEventByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OffEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OffEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OffEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OffEvent> listOffEventByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OffEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OffEventBase.ClassMgr getOffEventClassMgr()
    {
        return new OffEventBase.ClassMgr();
    }

   /**
   * Constructs a OffEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OffEvent
   */
    public OffEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
