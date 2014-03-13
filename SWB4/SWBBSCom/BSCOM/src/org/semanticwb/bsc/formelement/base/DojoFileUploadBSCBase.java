package org.semanticwb.bsc.formelement.base;


public abstract class DojoFileUploadBSCBase extends org.semanticwb.model.FileUpload 
{
   /**
   * Propiedad que define si se muestra o no la opción para eliminar el archivo almacenado actualmente en la propiedad. La vista que es afectada es la Edición.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_showRemove=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#showRemove");
    public static final org.semanticwb.platform.SemanticClass bsc_DojoFileUploadBSC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DojoFileUploadBSC");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DojoFileUploadBSC");

    public static class ClassMgr
    {
       /**
       * Returns a list of DojoFileUploadBSC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.DojoFileUploadBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DojoFileUploadBSC> listDojoFileUploadBSCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DojoFileUploadBSC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.DojoFileUploadBSC for all models
       * @return Iterator of org.semanticwb.bsc.formelement.DojoFileUploadBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DojoFileUploadBSC> listDojoFileUploadBSCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DojoFileUploadBSC>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param id Identifier for org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param model Model of the org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @return A org.semanticwb.bsc.formelement.DojoFileUploadBSC
       */
        public static org.semanticwb.bsc.formelement.DojoFileUploadBSC getDojoFileUploadBSC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DojoFileUploadBSC)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param id Identifier for org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param model Model of the org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @return A org.semanticwb.bsc.formelement.DojoFileUploadBSC
       */
        public static org.semanticwb.bsc.formelement.DojoFileUploadBSC createDojoFileUploadBSC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DojoFileUploadBSC)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param id Identifier for org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param model Model of the org.semanticwb.bsc.formelement.DojoFileUploadBSC
       */
        public static void removeDojoFileUploadBSC(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param id Identifier for org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @param model Model of the org.semanticwb.bsc.formelement.DojoFileUploadBSC
       * @return true if the org.semanticwb.bsc.formelement.DojoFileUploadBSC exists, false otherwise
       */

        public static boolean hasDojoFileUploadBSC(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDojoFileUploadBSC(id, model)!=null);
        }
    }

    public static DojoFileUploadBSCBase.ClassMgr getDojoFileUploadBSCClassMgr()
    {
        return new DojoFileUploadBSCBase.ClassMgr();
    }

   /**
   * Constructs a DojoFileUploadBSCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DojoFileUploadBSC
   */
    public DojoFileUploadBSCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ShowRemove property
* @return boolean with the ShowRemove
*/
    public boolean isShowRemove()
    {
        return getSemanticObject().getBooleanProperty(bsc_showRemove);
    }

/**
* Sets the ShowRemove property
* @param value long with the ShowRemove
*/
    public void setShowRemove(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_showRemove, value);
    }
}
