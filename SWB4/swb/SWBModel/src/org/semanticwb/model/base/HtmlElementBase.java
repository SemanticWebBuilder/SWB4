package org.semanticwb.model.base;


   /**
   * Elemento que muestra un componente grafico para editar HTML 
   */
public abstract class HtmlElementBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Elemento que muestra un componente grafico para editar HTML
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_HtmlElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HtmlElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HtmlElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of HtmlElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.HtmlElement
       */

        public static java.util.Iterator<org.semanticwb.model.HtmlElement> listHtmlElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HtmlElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.HtmlElement for all models
       * @return Iterator of org.semanticwb.model.HtmlElement
       */

        public static java.util.Iterator<org.semanticwb.model.HtmlElement> listHtmlElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HtmlElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.HtmlElement
       * @param id Identifier for org.semanticwb.model.HtmlElement
       * @param model Model of the org.semanticwb.model.HtmlElement
       * @return A org.semanticwb.model.HtmlElement
       */
        public static org.semanticwb.model.HtmlElement getHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HtmlElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.HtmlElement
       * @param id Identifier for org.semanticwb.model.HtmlElement
       * @param model Model of the org.semanticwb.model.HtmlElement
       * @return A org.semanticwb.model.HtmlElement
       */
        public static org.semanticwb.model.HtmlElement createHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HtmlElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.HtmlElement
       * @param id Identifier for org.semanticwb.model.HtmlElement
       * @param model Model of the org.semanticwb.model.HtmlElement
       */
        public static void removeHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.HtmlElement
       * @param id Identifier for org.semanticwb.model.HtmlElement
       * @param model Model of the org.semanticwb.model.HtmlElement
       * @return true if the org.semanticwb.model.HtmlElement exists, false otherwise
       */

        public static boolean hasHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHtmlElement(id, model)!=null);
        }
    }

   /**
   * Constructs a HtmlElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the HtmlElement
   */
    public HtmlElementBase(org.semanticwb.platform.SemanticObject base)
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
