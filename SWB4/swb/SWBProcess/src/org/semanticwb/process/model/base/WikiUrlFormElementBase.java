package org.semanticwb.process.model.base;


public abstract class WikiUrlFormElementBase extends org.semanticwb.model.UrlFormElement 
{
    public static final org.semanticwb.platform.SemanticClass swp_WikiUrlFormElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WikiUrlFormElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WikiUrlFormElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of WikiUrlFormElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.WikiUrlFormElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.WikiUrlFormElement> listWikiUrlFormElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WikiUrlFormElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.WikiUrlFormElement for all models
       * @return Iterator of org.semanticwb.process.model.WikiUrlFormElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.WikiUrlFormElement> listWikiUrlFormElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WikiUrlFormElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.WikiUrlFormElement
       * @param id Identifier for org.semanticwb.process.model.WikiUrlFormElement
       * @param model Model of the org.semanticwb.process.model.WikiUrlFormElement
       * @return A org.semanticwb.process.model.WikiUrlFormElement
       */
        public static org.semanticwb.process.model.WikiUrlFormElement getWikiUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WikiUrlFormElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.WikiUrlFormElement
       * @param id Identifier for org.semanticwb.process.model.WikiUrlFormElement
       * @param model Model of the org.semanticwb.process.model.WikiUrlFormElement
       * @return A org.semanticwb.process.model.WikiUrlFormElement
       */
        public static org.semanticwb.process.model.WikiUrlFormElement createWikiUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WikiUrlFormElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.WikiUrlFormElement
       * @param id Identifier for org.semanticwb.process.model.WikiUrlFormElement
       * @param model Model of the org.semanticwb.process.model.WikiUrlFormElement
       */
        public static void removeWikiUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.WikiUrlFormElement
       * @param id Identifier for org.semanticwb.process.model.WikiUrlFormElement
       * @param model Model of the org.semanticwb.process.model.WikiUrlFormElement
       * @return true if the org.semanticwb.process.model.WikiUrlFormElement exists, false otherwise
       */

        public static boolean hasWikiUrlFormElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWikiUrlFormElement(id, model)!=null);
        }
    }

    public static WikiUrlFormElementBase.ClassMgr getWikiUrlFormElementClassMgr()
    {
        return new WikiUrlFormElementBase.ClassMgr();
    }

   /**
   * Constructs a WikiUrlFormElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WikiUrlFormElement
   */
    public WikiUrlFormElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
