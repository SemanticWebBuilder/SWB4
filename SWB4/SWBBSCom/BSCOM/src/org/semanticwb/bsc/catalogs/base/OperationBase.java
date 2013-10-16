package org.semanticwb.bsc.catalogs.base;


public abstract class OperationBase extends org.semanticwb.bsc.catalogs.Catalog implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_script=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#script");
    public static final org.semanticwb.platform.SemanticClass bsc_Operation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Operation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Operation");

    public static class ClassMgr
    {
       /**
       * Returns a list of Operation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Operation> listOperations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Operation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Operation for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Operation> listOperations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Operation>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Operation createOperation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Operation.ClassMgr.createOperation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Operation
       * @param id Identifier for org.semanticwb.bsc.catalogs.Operation
       * @param model Model of the org.semanticwb.bsc.catalogs.Operation
       * @return A org.semanticwb.bsc.catalogs.Operation
       */
        public static org.semanticwb.bsc.catalogs.Operation getOperation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Operation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Operation
       * @param id Identifier for org.semanticwb.bsc.catalogs.Operation
       * @param model Model of the org.semanticwb.bsc.catalogs.Operation
       * @return A org.semanticwb.bsc.catalogs.Operation
       */
        public static org.semanticwb.bsc.catalogs.Operation createOperation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Operation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Operation
       * @param id Identifier for org.semanticwb.bsc.catalogs.Operation
       * @param model Model of the org.semanticwb.bsc.catalogs.Operation
       */
        public static void removeOperation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Operation
       * @param id Identifier for org.semanticwb.bsc.catalogs.Operation
       * @param model Model of the org.semanticwb.bsc.catalogs.Operation
       * @return true if the org.semanticwb.bsc.catalogs.Operation exists, false otherwise
       */

        public static boolean hasOperation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOperation(id, model)!=null);
        }
    }

    public static OperationBase.ClassMgr getOperationClassMgr()
    {
        return new OperationBase.ClassMgr();
    }

   /**
   * Constructs a OperationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Operation
   */
    public OperationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Script property
* @return String with the Script
*/
    public String getScript()
    {
        return getSemanticObject().getProperty(bsc_script);
    }

/**
* Sets the Script property
* @param value long with the Script
*/
    public void setScript(String value)
    {
        getSemanticObject().setProperty(bsc_script, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
