package org.semanticwb.process.model.base;


public abstract class InclusiveGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InclusiveGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InclusiveGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of InclusiveGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.InclusiveGateway for all models
       * @return Iterator of org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway>(it, true);
        }

        public static org.semanticwb.process.model.InclusiveGateway createInclusiveGateway(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.InclusiveGateway.ClassMgr.createInclusiveGateway(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.InclusiveGateway
       * @param id Identifier for org.semanticwb.process.model.InclusiveGateway
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return A org.semanticwb.process.model.InclusiveGateway
       */
        public static org.semanticwb.process.model.InclusiveGateway getInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InclusiveGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.InclusiveGateway
       * @param id Identifier for org.semanticwb.process.model.InclusiveGateway
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return A org.semanticwb.process.model.InclusiveGateway
       */
        public static org.semanticwb.process.model.InclusiveGateway createInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InclusiveGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.InclusiveGateway
       * @param id Identifier for org.semanticwb.process.model.InclusiveGateway
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       */
        public static void removeInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.InclusiveGateway
       * @param id Identifier for org.semanticwb.process.model.InclusiveGateway
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return true if the org.semanticwb.process.model.InclusiveGateway exists, false otherwise
       */

        public static boolean hasInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInclusiveGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.InclusiveGateway
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InclusiveGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.InclusiveGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static InclusiveGatewayBase.ClassMgr getInclusiveGatewayClassMgr()
    {
        return new InclusiveGatewayBase.ClassMgr();
    }

   /**
   * Constructs a InclusiveGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the InclusiveGateway
   */
    public InclusiveGatewayBase(org.semanticwb.platform.SemanticObject base)
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
