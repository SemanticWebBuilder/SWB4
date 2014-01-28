package org.semanticwb.process.model.base;


public abstract class EventBasedGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_EventBasedGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EventBasedGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EventBasedGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of EventBasedGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.EventBasedGateway for all models
       * @return Iterator of org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return A org.semanticwb.process.model.EventBasedGateway
       */
        public static org.semanticwb.process.model.EventBasedGateway getEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EventBasedGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return A org.semanticwb.process.model.EventBasedGateway
       */
        public static org.semanticwb.process.model.EventBasedGateway createEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EventBasedGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       */
        public static void removeEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return true if the org.semanticwb.process.model.EventBasedGateway exists, false otherwise
       */

        public static boolean hasEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEventBasedGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EventBasedGatewayBase.ClassMgr getEventBasedGatewayClassMgr()
    {
        return new EventBasedGatewayBase.ClassMgr();
    }

   /**
   * Constructs a EventBasedGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EventBasedGateway
   */
    public EventBasedGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
