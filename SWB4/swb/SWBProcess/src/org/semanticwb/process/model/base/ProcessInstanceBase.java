package org.semanticwb.process.model.base;


public abstract class ProcessInstanceBase extends org.semanticwb.process.model.Instance implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessTraceable,org.semanticwb.process.model.ContainerInstanceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_processStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processStatus");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_processType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processType");
    public static final org.semanticwb.platform.SemanticProperty swp_processPriority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processPriority");
   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty swp_processOwnerUserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processOwnerUserGroup");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessInstance for all models
       * @return Iterator of org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(it, true);
        }

        public static org.semanticwb.process.model.ProcessInstance createProcessInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessInstance.ClassMgr.createProcessInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessInstance
       * @param id Identifier for org.semanticwb.process.model.ProcessInstance
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return A org.semanticwb.process.model.ProcessInstance
       */
        public static org.semanticwb.process.model.ProcessInstance getProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessInstance
       * @param id Identifier for org.semanticwb.process.model.ProcessInstance
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return A org.semanticwb.process.model.ProcessInstance
       */
        public static org.semanticwb.process.model.ProcessInstance createProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessInstance
       * @param id Identifier for org.semanticwb.process.model.ProcessInstance
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       */
        public static void removeProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessInstance
       * @param id Identifier for org.semanticwb.process.model.ProcessInstance
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return true if the org.semanticwb.process.model.ProcessInstance exists, false otherwise
       */

        public static boolean hasProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByAssignedto(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByAssignedto(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined ProcessType
       * @param value ProcessType of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByProcessType(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined ProcessType
       * @param value ProcessType of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByProcessType(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstancesInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstancesInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined OwnerUserGroup
       * @param value OwnerUserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByOwnerUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processOwnerUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessInstance with a determined OwnerUserGroup
       * @param value OwnerUserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstanceByOwnerUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processOwnerUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessInstanceBase.ClassMgr getProcessInstanceClassMgr()
    {
        return new ProcessInstanceBase.ClassMgr();
    }

   /**
   * Constructs a ProcessInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessInstance
   */
    public ProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Status property
* @return int with the Status
*/
    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swp_processStatus);
    }

/**
* Sets the Status property
* @param value long with the Status
*/
    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swp_processStatus, value);
    }
   /**
   * Sets the value for the property ProcessType
   * @param value ProcessType to set
   */

    public void setProcessType(org.semanticwb.process.model.Process value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processType, value.getSemanticObject());
        }else
        {
            removeProcessType();
        }
    }
   /**
   * Remove the value for ProcessType property
   */

    public void removeProcessType()
    {
        getSemanticObject().removeProperty(swp_processType);
    }

   /**
   * Gets the ProcessType
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcessType()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasFlowNodeInstancesInv));
    }

   /**
   * Gets true if has a FlowNodeInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowNodeInstancesInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the FlowNodeInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getFlowNodeInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowNodeInstancesInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Priority property
* @return int with the Priority
*/
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swp_processPriority);
    }

/**
* Sets the Priority property
* @param value long with the Priority
*/
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swp_processPriority, value);
    }
   /**
   * Sets the value for the property OwnerUserGroup
   * @param value OwnerUserGroup to set
   */

    public void setOwnerUserGroup(org.semanticwb.model.UserGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processOwnerUserGroup, value.getSemanticObject());
        }else
        {
            removeOwnerUserGroup();
        }
    }
   /**
   * Remove the value for OwnerUserGroup property
   */

    public void removeOwnerUserGroup()
    {
        getSemanticObject().removeProperty(swp_processOwnerUserGroup);
    }

   /**
   * Gets the OwnerUserGroup
   * @return a org.semanticwb.model.UserGroup
   */
    public org.semanticwb.model.UserGroup getOwnerUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processOwnerUserGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
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
