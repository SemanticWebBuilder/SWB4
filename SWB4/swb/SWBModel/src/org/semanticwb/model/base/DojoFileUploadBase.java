package org.semanticwb.model.base;


public abstract class DojoFileUploadBase extends org.semanticwb.model.FileUpload 
{
    public static final org.semanticwb.platform.SemanticClass swb_DojoFileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#DojoFileUpload");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#DojoFileUpload");

    public static class ClassMgr
    {
       /**
       * Returns a list of DojoFileUpload for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.DojoFileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.DojoFileUpload> listDojoFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DojoFileUpload>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.DojoFileUpload for all models
       * @return Iterator of org.semanticwb.model.DojoFileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.DojoFileUpload> listDojoFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DojoFileUpload>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.DojoFileUpload
       * @param id Identifier for org.semanticwb.model.DojoFileUpload
       * @param model Model of the org.semanticwb.model.DojoFileUpload
       * @return A org.semanticwb.model.DojoFileUpload
       */
        public static org.semanticwb.model.DojoFileUpload getDojoFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DojoFileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.DojoFileUpload
       * @param id Identifier for org.semanticwb.model.DojoFileUpload
       * @param model Model of the org.semanticwb.model.DojoFileUpload
       * @return A org.semanticwb.model.DojoFileUpload
       */
        public static org.semanticwb.model.DojoFileUpload createDojoFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DojoFileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.DojoFileUpload
       * @param id Identifier for org.semanticwb.model.DojoFileUpload
       * @param model Model of the org.semanticwb.model.DojoFileUpload
       */
        public static void removeDojoFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.DojoFileUpload
       * @param id Identifier for org.semanticwb.model.DojoFileUpload
       * @param model Model of the org.semanticwb.model.DojoFileUpload
       * @return true if the org.semanticwb.model.DojoFileUpload exists, false otherwise
       */

        public static boolean hasDojoFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDojoFileUpload(id, model)!=null);
        }
    }

   /**
   * Constructs a DojoFileUploadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DojoFileUpload
   */
    public DojoFileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
