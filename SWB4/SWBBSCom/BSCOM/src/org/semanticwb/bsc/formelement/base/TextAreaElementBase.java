package org.semanticwb.bsc.formelement.base;


   /**
   * Utilizado en la generación de vistas resumen y detalle 
   */
public abstract class TextAreaElementBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_editable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#editable");
   /**
   * Utilizado en la generación de vistas resumen y detalle
   */
    public static final org.semanticwb.platform.SemanticClass bsc_TextAreaElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#TextAreaElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#TextAreaElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of TextAreaElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.TextAreaElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.TextAreaElement> listTextAreaElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.TextAreaElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.TextAreaElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.TextAreaElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.TextAreaElement> listTextAreaElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.TextAreaElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.TextAreaElement
       * @param id Identifier for org.semanticwb.bsc.formelement.TextAreaElement
       * @param model Model of the org.semanticwb.bsc.formelement.TextAreaElement
       * @return A org.semanticwb.bsc.formelement.TextAreaElement
       */
        public static org.semanticwb.bsc.formelement.TextAreaElement getTextAreaElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.TextAreaElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.TextAreaElement
       * @param id Identifier for org.semanticwb.bsc.formelement.TextAreaElement
       * @param model Model of the org.semanticwb.bsc.formelement.TextAreaElement
       * @return A org.semanticwb.bsc.formelement.TextAreaElement
       */
        public static org.semanticwb.bsc.formelement.TextAreaElement createTextAreaElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.TextAreaElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.TextAreaElement
       * @param id Identifier for org.semanticwb.bsc.formelement.TextAreaElement
       * @param model Model of the org.semanticwb.bsc.formelement.TextAreaElement
       */
        public static void removeTextAreaElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.TextAreaElement
       * @param id Identifier for org.semanticwb.bsc.formelement.TextAreaElement
       * @param model Model of the org.semanticwb.bsc.formelement.TextAreaElement
       * @return true if the org.semanticwb.bsc.formelement.TextAreaElement exists, false otherwise
       */

        public static boolean hasTextAreaElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextAreaElement(id, model)!=null);
        }
    }

    public static TextAreaElementBase.ClassMgr getTextAreaElementClassMgr()
    {
        return new TextAreaElementBase.ClassMgr();
    }

   /**
   * Constructs a TextAreaElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TextAreaElement
   */
    public TextAreaElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Editable property
* @return boolean with the Editable
*/
    public boolean isEditable()
    {
        return getSemanticObject().getBooleanProperty(bsc_editable);
    }

/**
* Sets the Editable property
* @param value long with the Editable
*/
    public void setEditable(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_editable, value);
    }
}
