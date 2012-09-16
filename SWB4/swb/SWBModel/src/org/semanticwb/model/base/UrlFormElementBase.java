package org.semanticwb.model.base;


public abstract class UrlFormElementBase extends org.semanticwb.model.Text 
{
    public static final org.semanticwb.platform.SemanticClass swb_UrlFormElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UrlFormElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UrlFormElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of UrlFormElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.UrlFormElement
       */

        public static java.util.Iterator<org.semanticwb.model.UrlFormElement> listUrlFormElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UrlFormElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UrlFormElement for all models
       * @return Iterator of org.semanticwb.model.UrlFormElement
       */

        public static java.util.Iterator<org.semanticwb.model.UrlFormElement> listUrlFormElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UrlFormElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.UrlFormElement
       * @param id Identifier for org.semanticwb.model.UrlFormElement
       * @param model Model of the org.semanticwb.model.UrlFormElement
       * @return A org.semanticwb.model.UrlFormElement
       */
        public static org.semanticwb.model.UrlFormElement getUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UrlFormElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UrlFormElement
       * @param id Identifier for org.semanticwb.model.UrlFormElement
       * @param model Model of the org.semanticwb.model.UrlFormElement
       * @return A org.semanticwb.model.UrlFormElement
       */
        public static org.semanticwb.model.UrlFormElement createUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UrlFormElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UrlFormElement
       * @param id Identifier for org.semanticwb.model.UrlFormElement
       * @param model Model of the org.semanticwb.model.UrlFormElement
       */
        public static void removeUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UrlFormElement
       * @param id Identifier for org.semanticwb.model.UrlFormElement
       * @param model Model of the org.semanticwb.model.UrlFormElement
       * @return true if the org.semanticwb.model.UrlFormElement exists, false otherwise
       */

        public static boolean hasUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUrlFormElement(id, model)!=null);
        }
    }

    public static UrlFormElementBase.ClassMgr getUrlFormElementClassMgr()
    {
        return new UrlFormElementBase.ClassMgr();
    }

   /**
   * Constructs a UrlFormElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UrlFormElement
   */
    public UrlFormElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
