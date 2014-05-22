package org.semanticwb.process.model.base;


public abstract class FlowNodeBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasFlowNodeInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowNodeInstanceInv");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");

    public static class ClassMgr
    {
       /**
       * Returns a list of FlowNode for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.FlowNode for all models
       * @return Iterator of org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.FlowNode
       * @param id Identifier for org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return A org.semanticwb.process.model.FlowNode
       */
        public static org.semanticwb.process.model.FlowNode getFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.FlowNode
       * @param id Identifier for org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return A org.semanticwb.process.model.FlowNode
       */
        public static org.semanticwb.process.model.FlowNode createFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.FlowNode
       * @param id Identifier for org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.FlowNode
       */
        public static void removeFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.FlowNode
       * @param id Identifier for org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return true if the org.semanticwb.process.model.FlowNode exists, false otherwise
       */

        public static boolean hasFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlowNode(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNode with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNode
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FlowNodeBase.ClassMgr getFlowNodeClassMgr()
    {
        return new FlowNodeBase.ClassMgr();
    }

   /**
   * Constructs a FlowNodeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FlowNode
   */
    public FlowNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasFlowNodeInstanceInv));
    }

   /**
   * Gets true if has a FlowObjectInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowNodeInstanceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the FlowObjectInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getFlowObjectInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowNodeInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
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
