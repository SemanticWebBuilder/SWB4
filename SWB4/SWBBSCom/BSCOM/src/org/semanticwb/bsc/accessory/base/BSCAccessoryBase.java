package org.semanticwb.bsc.accessory.base;


public abstract class BSCAccessoryBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass bsc_BSCAccessory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCAccessory");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCAccessory");

    public static class ClassMgr
    {
       /**
       * Returns a list of BSCAccessory for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.BSCAccessory
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.BSCAccessory> listBSCAccessories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.BSCAccessory>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.BSCAccessory for all models
       * @return Iterator of org.semanticwb.bsc.accessory.BSCAccessory
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.BSCAccessory> listBSCAccessories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.BSCAccessory>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.BSCAccessory
       * @param id Identifier for org.semanticwb.bsc.accessory.BSCAccessory
       * @param model Model of the org.semanticwb.bsc.accessory.BSCAccessory
       * @return A org.semanticwb.bsc.accessory.BSCAccessory
       */
        public static org.semanticwb.bsc.accessory.BSCAccessory getBSCAccessory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.BSCAccessory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.BSCAccessory
       * @param id Identifier for org.semanticwb.bsc.accessory.BSCAccessory
       * @param model Model of the org.semanticwb.bsc.accessory.BSCAccessory
       * @return A org.semanticwb.bsc.accessory.BSCAccessory
       */
        public static org.semanticwb.bsc.accessory.BSCAccessory createBSCAccessory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.BSCAccessory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.BSCAccessory
       * @param id Identifier for org.semanticwb.bsc.accessory.BSCAccessory
       * @param model Model of the org.semanticwb.bsc.accessory.BSCAccessory
       */
        public static void removeBSCAccessory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.BSCAccessory
       * @param id Identifier for org.semanticwb.bsc.accessory.BSCAccessory
       * @param model Model of the org.semanticwb.bsc.accessory.BSCAccessory
       * @return true if the org.semanticwb.bsc.accessory.BSCAccessory exists, false otherwise
       */

        public static boolean hasBSCAccessory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBSCAccessory(id, model)!=null);
        }
    }

    public static BSCAccessoryBase.ClassMgr getBSCAccessoryClassMgr()
    {
        return new BSCAccessoryBase.ClassMgr();
    }

   /**
   * Constructs a BSCAccessoryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BSCAccessory
   */
    public BSCAccessoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
