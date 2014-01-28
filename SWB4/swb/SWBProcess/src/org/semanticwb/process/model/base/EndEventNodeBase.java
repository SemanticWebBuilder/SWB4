package org.semanticwb.process.model.base;


   /**
   * Nodo raiz de Eventos Finales 
   */
public abstract class EndEventNodeBase extends org.semanticwb.process.model.ThrowEvent implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
   /**
   * Nodo raiz de Eventos Finales
   */
    public static final org.semanticwb.platform.SemanticClass swp_EndEventNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EndEventNode");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EndEventNode");

    public static class ClassMgr
    {
       /**
       * Returns a list of EndEventNode for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.EndEventNode for all models
       * @return Iterator of org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.EndEventNode
       * @param id Identifier for org.semanticwb.process.model.EndEventNode
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return A org.semanticwb.process.model.EndEventNode
       */
        public static org.semanticwb.process.model.EndEventNode getEndEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EndEventNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.EndEventNode
       * @param id Identifier for org.semanticwb.process.model.EndEventNode
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return A org.semanticwb.process.model.EndEventNode
       */
        public static org.semanticwb.process.model.EndEventNode createEndEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EndEventNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.EndEventNode
       * @param id Identifier for org.semanticwb.process.model.EndEventNode
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       */
        public static void removeEndEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.EndEventNode
       * @param id Identifier for org.semanticwb.process.model.EndEventNode
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return true if the org.semanticwb.process.model.EndEventNode exists, false otherwise
       */

        public static boolean hasEndEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEndEventNode(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.EndEventNode
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EndEventNode with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.EndEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.EndEventNode> listEndEventNodeByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EndEventNodeBase.ClassMgr getEndEventNodeClassMgr()
    {
        return new EndEventNodeBase.ClassMgr();
    }

   /**
   * Constructs a EndEventNodeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EndEventNode
   */
    public EndEventNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
