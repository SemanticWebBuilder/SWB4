package org.semanticwb.process.model.base;


public abstract class ProcessServiceBase extends org.semanticwb.model.SWBClass 
{
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
    }

   /**
   * Constructs a ProcessServiceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessService
   */
    public ProcessServiceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
