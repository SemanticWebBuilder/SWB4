package org.semanticwb.process.model.base;


public abstract class SignalEndEventBase extends org.semanticwb.process.model.EndEventNode implements org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.ActionCodeable
{
    public static final org.semanticwb.platform.SemanticClass swp_SignalEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SignalEndEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SignalEndEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of SignalEndEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SignalEndEvent for all models
       * @return Iterator of org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent>(it, true);
        }

        public static org.semanticwb.process.model.SignalEndEvent createSignalEndEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SignalEndEvent.ClassMgr.createSignalEndEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SignalEndEvent
       * @param id Identifier for org.semanticwb.process.model.SignalEndEvent
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return A org.semanticwb.process.model.SignalEndEvent
       */
        public static org.semanticwb.process.model.SignalEndEvent getSignalEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SignalEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SignalEndEvent
       * @param id Identifier for org.semanticwb.process.model.SignalEndEvent
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return A org.semanticwb.process.model.SignalEndEvent
       */
        public static org.semanticwb.process.model.SignalEndEvent createSignalEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SignalEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SignalEndEvent
       * @param id Identifier for org.semanticwb.process.model.SignalEndEvent
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       */
        public static void removeSignalEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SignalEndEvent
       * @param id Identifier for org.semanticwb.process.model.SignalEndEvent
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return true if the org.semanticwb.process.model.SignalEndEvent exists, false otherwise
       */

        public static boolean hasSignalEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSignalEndEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.SignalEndEvent
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SignalEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.SignalEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SignalEndEventBase.ClassMgr getSignalEndEventClassMgr()
    {
        return new SignalEndEventBase.ClassMgr();
    }

   /**
   * Constructs a SignalEndEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SignalEndEvent
   */
    public SignalEndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ActionCode property
* @return String with the ActionCode
*/
    public String getActionCode()
    {
        return getSemanticObject().getProperty(swp_actionCode);
    }

/**
* Sets the ActionCode property
* @param value long with the ActionCode
*/
    public void setActionCode(String value)
    {
        getSemanticObject().setProperty(swp_actionCode, value);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
