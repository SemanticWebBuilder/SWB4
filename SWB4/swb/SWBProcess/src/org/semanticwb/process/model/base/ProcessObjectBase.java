package org.semanticwb.process.model.base;


public abstract class ProcessObjectBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessObject for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObject> listProcessObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessObject for all models
       * @return Iterator of org.semanticwb.process.model.ProcessObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObject> listProcessObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObject>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessObject
       * @param id Identifier for org.semanticwb.process.model.ProcessObject
       * @param model Model of the org.semanticwb.process.model.ProcessObject
       * @return A org.semanticwb.process.model.ProcessObject
       */
        public static org.semanticwb.process.model.ProcessObject getProcessObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessObject
       * @param id Identifier for org.semanticwb.process.model.ProcessObject
       * @param model Model of the org.semanticwb.process.model.ProcessObject
       * @return A org.semanticwb.process.model.ProcessObject
       */
        public static org.semanticwb.process.model.ProcessObject createProcessObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessObject
       * @param id Identifier for org.semanticwb.process.model.ProcessObject
       * @param model Model of the org.semanticwb.process.model.ProcessObject
       */
        public static void removeProcessObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessObject
       * @param id Identifier for org.semanticwb.process.model.ProcessObject
       * @param model Model of the org.semanticwb.process.model.ProcessObject
       * @return true if the org.semanticwb.process.model.ProcessObject exists, false otherwise
       */

        public static boolean hasProcessObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessObject(id, model)!=null);
        }
    }

   /**
   * Constructs a ProcessObjectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessObject
   */
    public ProcessObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
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
