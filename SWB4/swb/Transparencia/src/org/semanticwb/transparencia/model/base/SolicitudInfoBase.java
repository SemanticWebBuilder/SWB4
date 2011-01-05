package org.semanticwb.transparencia.model.base;


public abstract class SolicitudInfoBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass trans_SolicitudInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#SolicitudInfo");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#SolicitudInfo");

    public static class ClassMgr
    {
       /**
       * Returns a list of SolicitudInfo for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.SolicitudInfo
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.SolicitudInfo> listSolicitudInfos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.SolicitudInfo>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.SolicitudInfo for all models
       * @return Iterator of org.semanticwb.transparencia.model.SolicitudInfo
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.SolicitudInfo> listSolicitudInfos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.SolicitudInfo>(it, true);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.SolicitudInfo
       * @param id Identifier for org.semanticwb.transparencia.model.SolicitudInfo
       * @param model Model of the org.semanticwb.transparencia.model.SolicitudInfo
       * @return A org.semanticwb.transparencia.model.SolicitudInfo
       */
        public static org.semanticwb.transparencia.model.SolicitudInfo getSolicitudInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.SolicitudInfo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.SolicitudInfo
       * @param id Identifier for org.semanticwb.transparencia.model.SolicitudInfo
       * @param model Model of the org.semanticwb.transparencia.model.SolicitudInfo
       * @return A org.semanticwb.transparencia.model.SolicitudInfo
       */
        public static org.semanticwb.transparencia.model.SolicitudInfo createSolicitudInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.SolicitudInfo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.SolicitudInfo
       * @param id Identifier for org.semanticwb.transparencia.model.SolicitudInfo
       * @param model Model of the org.semanticwb.transparencia.model.SolicitudInfo
       */
        public static void removeSolicitudInfo(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.SolicitudInfo
       * @param id Identifier for org.semanticwb.transparencia.model.SolicitudInfo
       * @param model Model of the org.semanticwb.transparencia.model.SolicitudInfo
       * @return true if the org.semanticwb.transparencia.model.SolicitudInfo exists, false otherwise
       */

        public static boolean hasSolicitudInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSolicitudInfo(id, model)!=null);
        }
    }

   /**
   * Constructs a SolicitudInfoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SolicitudInfo
   */
    public SolicitudInfoBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
