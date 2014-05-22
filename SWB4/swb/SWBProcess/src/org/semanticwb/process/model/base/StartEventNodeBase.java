package org.semanticwb.process.model.base;


   /**
   * Nodo raiz de Eventos Iniciales 
   */
public abstract class StartEventNodeBase extends org.semanticwb.process.model.CatchEvent implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_ie_next=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ie_next");
   /**
   * Nodo raiz de Eventos Iniciales
   */
    public static final org.semanticwb.platform.SemanticClass swp_StartEventNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StartEventNode");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StartEventNode");

    public static class ClassMgr
    {
       /**
       * Returns a list of StartEventNode for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.StartEventNode for all models
       * @return Iterator of org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.StartEventNode
       * @param id Identifier for org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return A org.semanticwb.process.model.StartEventNode
       */
        public static org.semanticwb.process.model.StartEventNode getStartEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StartEventNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.StartEventNode
       * @param id Identifier for org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return A org.semanticwb.process.model.StartEventNode
       */
        public static org.semanticwb.process.model.StartEventNode createStartEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StartEventNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.StartEventNode
       * @param id Identifier for org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       */
        public static void removeStartEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.StartEventNode
       * @param id Identifier for org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return true if the org.semanticwb.process.model.StartEventNode exists, false otherwise
       */

        public static boolean hasStartEventNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStartEventNode(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByNext(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByNext(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StartEventNode with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.StartEventNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.StartEventNode> listStartEventNodeByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StartEventNodeBase.ClassMgr getStartEventNodeClassMgr()
    {
        return new StartEventNodeBase.ClassMgr();
    }

   /**
   * Constructs a StartEventNodeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StartEventNode
   */
    public StartEventNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Next
   * @param value Next to set
   */

    public void setNext(org.semanticwb.process.model.FlowNode value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_ie_next, value.getSemanticObject());
        }else
        {
            removeNext();
        }
    }
   /**
   * Remove the value for Next property
   */

    public void removeNext()
    {
        getSemanticObject().removeProperty(swp_ie_next);
    }

   /**
   * Gets the Next
   * @return a org.semanticwb.process.model.FlowNode
   */
    public org.semanticwb.process.model.FlowNode getNext()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_ie_next);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
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
