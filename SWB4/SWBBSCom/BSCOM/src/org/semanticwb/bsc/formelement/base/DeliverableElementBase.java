package org.semanticwb.bsc.formelement.base;


   /**
   * Form Element que presentará la vista para administrar entregables (Liga para Edición y Eliminación) 
   */
public abstract class DeliverableElementBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Form Element que presentará la vista para administrar entregables (Liga para Edición y Eliminación)
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DeliverableElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DeliverableElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DeliverableElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of DeliverableElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.DeliverableElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DeliverableElement> listDeliverableElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DeliverableElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.DeliverableElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.DeliverableElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DeliverableElement> listDeliverableElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DeliverableElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.DeliverableElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DeliverableElement
       * @param model Model of the org.semanticwb.bsc.formelement.DeliverableElement
       * @return A org.semanticwb.bsc.formelement.DeliverableElement
       */
        public static org.semanticwb.bsc.formelement.DeliverableElement getDeliverableElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DeliverableElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.DeliverableElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DeliverableElement
       * @param model Model of the org.semanticwb.bsc.formelement.DeliverableElement
       * @return A org.semanticwb.bsc.formelement.DeliverableElement
       */
        public static org.semanticwb.bsc.formelement.DeliverableElement createDeliverableElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DeliverableElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.DeliverableElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DeliverableElement
       * @param model Model of the org.semanticwb.bsc.formelement.DeliverableElement
       */
        public static void removeDeliverableElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.DeliverableElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DeliverableElement
       * @param model Model of the org.semanticwb.bsc.formelement.DeliverableElement
       * @return true if the org.semanticwb.bsc.formelement.DeliverableElement exists, false otherwise
       */

        public static boolean hasDeliverableElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDeliverableElement(id, model)!=null);
        }
    }

    public static DeliverableElementBase.ClassMgr getDeliverableElementClassMgr()
    {
        return new DeliverableElementBase.ClassMgr();
    }

   /**
   * Constructs a DeliverableElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DeliverableElement
   */
    public DeliverableElementBase(org.semanticwb.platform.SemanticObject base)
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
