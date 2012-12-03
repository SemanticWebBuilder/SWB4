package org.semanticwb.process.model.base;


public abstract class ProcessServiceBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swp_ServiceTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ServiceTask");
    public static final org.semanticwb.platform.SemanticProperty swp_serviceTaskInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#serviceTaskInv");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessService");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessService");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessService for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessService
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessService> listProcessServices(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessService>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessService for all models
       * @return Iterator of org.semanticwb.process.model.ProcessService
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessService> listProcessServices()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessService>(it, true);
        }

        public static org.semanticwb.process.model.ProcessService createProcessService(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessService.ClassMgr.createProcessService(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessService
       * @param id Identifier for org.semanticwb.process.model.ProcessService
       * @param model Model of the org.semanticwb.process.model.ProcessService
       * @return A org.semanticwb.process.model.ProcessService
       */
        public static org.semanticwb.process.model.ProcessService getProcessService(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessService)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessService
       * @param id Identifier for org.semanticwb.process.model.ProcessService
       * @param model Model of the org.semanticwb.process.model.ProcessService
       * @return A org.semanticwb.process.model.ProcessService
       */
        public static org.semanticwb.process.model.ProcessService createProcessService(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessService)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessService
       * @param id Identifier for org.semanticwb.process.model.ProcessService
       * @param model Model of the org.semanticwb.process.model.ProcessService
       */
        public static void removeProcessService(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessService
       * @param id Identifier for org.semanticwb.process.model.ProcessService
       * @param model Model of the org.semanticwb.process.model.ProcessService
       * @return true if the org.semanticwb.process.model.ProcessService exists, false otherwise
       */

        public static boolean hasProcessService(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessService(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessService with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.ProcessService
       * @return Iterator with all the org.semanticwb.process.model.ProcessService
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessService> listProcessServiceByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessService> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessService with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.ProcessService
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessService> listProcessServiceByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessService> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessServiceBase.ClassMgr getProcessServiceClassMgr()
    {
        return new ProcessServiceBase.ClassMgr();
    }

   /**
   * Constructs a ProcessServiceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessService
   */
    public ProcessServiceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ServiceTask
   * @param value ServiceTask to set
   */

    public void setServiceTask(org.semanticwb.process.model.ServiceTask value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_serviceTaskInv, value.getSemanticObject());
        }else
        {
            removeServiceTask();
        }
    }
   /**
   * Remove the value for ServiceTask property
   */

    public void removeServiceTask()
    {
        getSemanticObject().removeProperty(swp_serviceTaskInv);
    }

   /**
   * Gets the ServiceTask
   * @return a org.semanticwb.process.model.ServiceTask
   */
    public org.semanticwb.process.model.ServiceTask getServiceTask()
    {
         org.semanticwb.process.model.ServiceTask ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_serviceTaskInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ServiceTask)obj.createGenericInstance();
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
