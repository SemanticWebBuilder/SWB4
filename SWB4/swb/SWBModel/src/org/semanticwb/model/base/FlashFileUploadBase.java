package org.semanticwb.model.base;


   /**
   * Elemento que muestra un componente grafico (en flash) para subir archivos 
   */
public abstract class FlashFileUploadBase extends org.semanticwb.model.FileUpload 
{
   /**
   * Elemento que muestra un componente grafico (en flash) para subir archivos
   */
    public static final org.semanticwb.platform.SemanticClass swb_FlashFileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashFileUpload");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashFileUpload");

    public static class ClassMgr
    {
       /**
       * Returns a list of FlashFileUpload for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.FlashFileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.FlashFileUpload> listFlashFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashFileUpload>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FlashFileUpload for all models
       * @return Iterator of org.semanticwb.model.FlashFileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.FlashFileUpload> listFlashFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashFileUpload>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.FlashFileUpload
       * @param id Identifier for org.semanticwb.model.FlashFileUpload
       * @param model Model of the org.semanticwb.model.FlashFileUpload
       * @return A org.semanticwb.model.FlashFileUpload
       */
        public static org.semanticwb.model.FlashFileUpload getFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashFileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FlashFileUpload
       * @param id Identifier for org.semanticwb.model.FlashFileUpload
       * @param model Model of the org.semanticwb.model.FlashFileUpload
       * @return A org.semanticwb.model.FlashFileUpload
       */
        public static org.semanticwb.model.FlashFileUpload createFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashFileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FlashFileUpload
       * @param id Identifier for org.semanticwb.model.FlashFileUpload
       * @param model Model of the org.semanticwb.model.FlashFileUpload
       */
        public static void removeFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FlashFileUpload
       * @param id Identifier for org.semanticwb.model.FlashFileUpload
       * @param model Model of the org.semanticwb.model.FlashFileUpload
       * @return true if the org.semanticwb.model.FlashFileUpload exists, false otherwise
       */

        public static boolean hasFlashFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlashFileUpload(id, model)!=null);
        }
    }

   /**
   * Constructs a FlashFileUploadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FlashFileUpload
   */
    public FlashFileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
