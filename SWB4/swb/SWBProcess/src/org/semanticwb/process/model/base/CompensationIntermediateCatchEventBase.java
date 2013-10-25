package org.semanticwb.process.model.base;


public abstract class CompensationIntermediateCatchEventBase extends org.semanticwb.process.model.IntermediateCatchEvent implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.model.ActionCodeable
{
    public static final org.semanticwb.platform.SemanticClass swp_CompensationIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationIntermediateCatchEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationIntermediateCatchEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of CompensationIntermediateCatchEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CompensationIntermediateCatchEvent for all models
       * @return Iterator of org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.CompensationIntermediateCatchEvent createCompensationIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.createCompensationIntermediateCatchEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return A org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.CompensationIntermediateCatchEvent getCompensationIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationIntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return A org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.CompensationIntermediateCatchEvent createCompensationIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationIntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */
        public static void removeCompensationIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return true if the org.semanticwb.process.model.CompensationIntermediateCatchEvent exists, false otherwise
       */

        public static boolean hasCompensationIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompensationIntermediateCatchEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CompensationIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CompensationIntermediateCatchEventBase.ClassMgr getCompensationIntermediateCatchEventClassMgr()
    {
        return new CompensationIntermediateCatchEventBase.ClassMgr();
    }

   /**
   * Constructs a CompensationIntermediateCatchEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CompensationIntermediateCatchEvent
   */
    public CompensationIntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
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
