package org.semanticwb.bsc.formelement.base;


   /**
   * Form Element que presentará la vista para administrar archivos adjuntos (Creación, Edición y Eliminación) 
   */
public abstract class AttachmentElementBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Form Element que presentará la vista para administrar archivos adjuntos (Creación, Edición y Eliminación)
   */
    public static final org.semanticwb.platform.SemanticClass bsc_AttachmentElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#AttachmentElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#AttachmentElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of AttachmentElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.AttachmentElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.AttachmentElement> listAttachmentElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.AttachmentElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.AttachmentElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.AttachmentElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.AttachmentElement> listAttachmentElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.AttachmentElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.AttachmentElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AttachmentElement
       * @param model Model of the org.semanticwb.bsc.formelement.AttachmentElement
       * @return A org.semanticwb.bsc.formelement.AttachmentElement
       */
        public static org.semanticwb.bsc.formelement.AttachmentElement getAttachmentElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.AttachmentElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.AttachmentElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AttachmentElement
       * @param model Model of the org.semanticwb.bsc.formelement.AttachmentElement
       * @return A org.semanticwb.bsc.formelement.AttachmentElement
       */
        public static org.semanticwb.bsc.formelement.AttachmentElement createAttachmentElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.AttachmentElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.AttachmentElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AttachmentElement
       * @param model Model of the org.semanticwb.bsc.formelement.AttachmentElement
       */
        public static void removeAttachmentElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.AttachmentElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AttachmentElement
       * @param model Model of the org.semanticwb.bsc.formelement.AttachmentElement
       * @return true if the org.semanticwb.bsc.formelement.AttachmentElement exists, false otherwise
       */

        public static boolean hasAttachmentElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAttachmentElement(id, model)!=null);
        }
    }

    public static AttachmentElementBase.ClassMgr getAttachmentElementClassMgr()
    {
        return new AttachmentElementBase.ClassMgr();
    }

   /**
   * Constructs a AttachmentElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AttachmentElement
   */
    public AttachmentElementBase(org.semanticwb.platform.SemanticObject base)
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
}
