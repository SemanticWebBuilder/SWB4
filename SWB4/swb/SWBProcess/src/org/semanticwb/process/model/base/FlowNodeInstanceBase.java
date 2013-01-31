package org.semanticwb.process.model.base;


public abstract class FlowNodeInstanceBase extends org.semanticwb.process.model.Instance implements org.semanticwb.process.model.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_flowNodeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#flowNodeType");
    public static final org.semanticwb.platform.SemanticProperty swp_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
    public static final org.semanticwb.platform.SemanticClass swp_ContainerInstanceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ContainerInstanceable");
    public static final org.semanticwb.platform.SemanticProperty swp_containerInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#containerInstance");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_sourceInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sourceInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTargetInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTargetInstanceInv");
    public static final org.semanticwb.platform.SemanticProperty swp_subject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#subject");
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticProperty swp_fromConnection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fromConnection");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of FlowNodeInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.FlowNodeInstance for all models
       * @return Iterator of org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(it, true);
        }

        public static org.semanticwb.process.model.FlowNodeInstance createFlowNodeInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.createFlowNodeInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.FlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return A org.semanticwb.process.model.FlowNodeInstance
       */
        public static org.semanticwb.process.model.FlowNodeInstance getFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNodeInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.FlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return A org.semanticwb.process.model.FlowNodeInstance
       */
        public static org.semanticwb.process.model.FlowNodeInstance createFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNodeInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.FlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       */
        public static void removeFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.FlowNodeInstance
       * @param id Identifier for org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
       */

        public static boolean hasFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlowNodeInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined FlowNodeType
       * @param value FlowNodeType of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByAssignedto(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByAssignedto(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined ContainerInstance
       * @param value ContainerInstance of the type org.semanticwb.process.model.ContainerInstanceable
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByContainerInstance(org.semanticwb.process.model.ContainerInstanceable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_containerInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined SourceInstance
       * @param value SourceInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceBySourceInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_sourceInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined TargetInstance
       * @param value TargetInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByTargetInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTargetInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined FromConnection
       * @param value FromConnection of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByFromConnection(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromConnection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.FlowNodeInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.FlowNodeInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FlowNodeInstanceBase.ClassMgr getFlowNodeInstanceClassMgr()
    {
        return new FlowNodeInstanceBase.ClassMgr();
    }

   /**
   * Constructs a FlowNodeInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FlowNodeInstance
   */
    public FlowNodeInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property FlowNodeType
   * @param value FlowNodeType to set
   */

    public void setFlowNodeType(org.semanticwb.process.model.FlowNode value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_flowNodeType, value.getSemanticObject());
        }else
        {
            removeFlowNodeType();
        }
    }
   /**
   * Remove the value for FlowNodeType property
   */

    public void removeFlowNodeType()
    {
        getSemanticObject().removeProperty(swp_flowNodeType);
    }

   /**
   * Gets the FlowNodeType
   * @return a org.semanticwb.process.model.FlowNode
   */
    public org.semanticwb.process.model.FlowNode getFlowNodeType()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_flowNodeType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Status property
* @return int with the Status
*/
    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swp_status);
    }

/**
* Sets the Status property
* @param value long with the Status
*/
    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swp_status, value);
    }
   /**
   * Sets the value for the property ContainerInstance
   * @param value ContainerInstance to set
   */

    public void setContainerInstance(org.semanticwb.process.model.ContainerInstanceable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_containerInstance, value.getSemanticObject());
        }else
        {
            removeContainerInstance();
        }
    }
   /**
   * Remove the value for ContainerInstance property
   */

    public void removeContainerInstance()
    {
        getSemanticObject().removeProperty(swp_containerInstance);
    }

   /**
   * Gets the ContainerInstance
   * @return a org.semanticwb.process.model.ContainerInstanceable
   */
    public org.semanticwb.process.model.ContainerInstanceable getContainerInstance()
    {
         org.semanticwb.process.model.ContainerInstanceable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_containerInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ContainerInstanceable)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property SourceInstance
   * @param value SourceInstance to set
   */

    public void setSourceInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_sourceInstance, value.getSemanticObject());
        }else
        {
            removeSourceInstance();
        }
    }
   /**
   * Remove the value for SourceInstance property
   */

    public void removeSourceInstance()
    {
        getSemanticObject().removeProperty(swp_sourceInstance);
    }

   /**
   * Gets the SourceInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getSourceInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_sourceInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listTargetInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasTargetInstanceInv));
    }

   /**
   * Gets true if has a TargetInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasTargetInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasTargetInstanceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the TargetInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getTargetInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTargetInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Subject property
* @return String with the Subject
*/
    public String getSubject()
    {
        return getSemanticObject().getProperty(swp_subject);
    }

/**
* Sets the Subject property
* @param value long with the Subject
*/
    public void setSubject(String value)
    {
        getSemanticObject().setProperty(swp_subject, value);
    }
   /**
   * Sets the value for the property FromConnection
   * @param value FromConnection to set
   */

    public void setFromConnection(org.semanticwb.process.model.ConnectionObject value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_fromConnection, value.getSemanticObject());
        }else
        {
            removeFromConnection();
        }
    }
   /**
   * Remove the value for FromConnection property
   */

    public void removeFromConnection()
    {
        getSemanticObject().removeProperty(swp_fromConnection);
    }

   /**
   * Gets the FromConnection
   * @return a org.semanticwb.process.model.ConnectionObject
   */
    public org.semanticwb.process.model.ConnectionObject getFromConnection()
    {
         org.semanticwb.process.model.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_fromConnection);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConnectionObject)obj.createGenericInstance();
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
