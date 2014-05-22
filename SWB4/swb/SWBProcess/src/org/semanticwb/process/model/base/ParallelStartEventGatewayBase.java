package org.semanticwb.process.model.base;


public abstract class ParallelStartEventGatewayBase extends org.semanticwb.process.model.EventBasedGateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_ParallelStartEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelStartEventGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelStartEventGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of ParallelStartEventGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ParallelStartEventGateway for all models
       * @return Iterator of org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway>(it, true);
        }

        public static org.semanticwb.process.model.ParallelStartEventGateway createParallelStartEventGateway(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.createParallelStartEventGateway(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ParallelStartEventGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEventGateway
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return A org.semanticwb.process.model.ParallelStartEventGateway
       */
        public static org.semanticwb.process.model.ParallelStartEventGateway getParallelStartEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParallelStartEventGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ParallelStartEventGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEventGateway
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return A org.semanticwb.process.model.ParallelStartEventGateway
       */
        public static org.semanticwb.process.model.ParallelStartEventGateway createParallelStartEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParallelStartEventGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ParallelStartEventGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEventGateway
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       */
        public static void removeParallelStartEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ParallelStartEventGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEventGateway
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return true if the org.semanticwb.process.model.ParallelStartEventGateway exists, false otherwise
       */

        public static boolean hasParallelStartEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParallelStartEventGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ParallelStartEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEventGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ParallelStartEventGatewayBase.ClassMgr getParallelStartEventGatewayClassMgr()
    {
        return new ParallelStartEventGatewayBase.ClassMgr();
    }

   /**
   * Constructs a ParallelStartEventGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ParallelStartEventGateway
   */
    public ParallelStartEventGatewayBase(org.semanticwb.platform.SemanticObject base)
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
