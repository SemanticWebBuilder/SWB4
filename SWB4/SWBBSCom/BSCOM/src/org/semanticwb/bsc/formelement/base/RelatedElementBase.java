package org.semanticwb.bsc.formelement.base;


   /**
   * Muestra el título del objeto al que se relaciona el objeto actual, si ese objeto es uno de tipo BSCElement el título mostrará un vínculo a la página detalle correspondiente 
   */
public abstract class RelatedElementBase extends org.semanticwb.model.Text 
{
   /**
   * Muestra el título del objeto al que se relaciona el objeto actual, si ese objeto es uno de tipo BSCElement el título mostrará un vínculo a la página detalle correspondiente
   */
    public static final org.semanticwb.platform.SemanticClass bsc_RelatedElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#RelatedElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#RelatedElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of RelatedElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.RelatedElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.RelatedElement> listRelatedElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.RelatedElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.RelatedElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.RelatedElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.RelatedElement> listRelatedElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.RelatedElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.RelatedElement
       * @param id Identifier for org.semanticwb.bsc.formelement.RelatedElement
       * @param model Model of the org.semanticwb.bsc.formelement.RelatedElement
       * @return A org.semanticwb.bsc.formelement.RelatedElement
       */
        public static org.semanticwb.bsc.formelement.RelatedElement getRelatedElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.RelatedElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.RelatedElement
       * @param id Identifier for org.semanticwb.bsc.formelement.RelatedElement
       * @param model Model of the org.semanticwb.bsc.formelement.RelatedElement
       * @return A org.semanticwb.bsc.formelement.RelatedElement
       */
        public static org.semanticwb.bsc.formelement.RelatedElement createRelatedElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.RelatedElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.RelatedElement
       * @param id Identifier for org.semanticwb.bsc.formelement.RelatedElement
       * @param model Model of the org.semanticwb.bsc.formelement.RelatedElement
       */
        public static void removeRelatedElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.RelatedElement
       * @param id Identifier for org.semanticwb.bsc.formelement.RelatedElement
       * @param model Model of the org.semanticwb.bsc.formelement.RelatedElement
       * @return true if the org.semanticwb.bsc.formelement.RelatedElement exists, false otherwise
       */

        public static boolean hasRelatedElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRelatedElement(id, model)!=null);
        }
    }

    public static RelatedElementBase.ClassMgr getRelatedElementClassMgr()
    {
        return new RelatedElementBase.ClassMgr();
    }

   /**
   * Constructs a RelatedElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RelatedElement
   */
    public RelatedElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
