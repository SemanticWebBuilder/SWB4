package org.semanticwb.process.model.base;


public abstract class ItemAwareMappingElementBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareMappingElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareMappingElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareMappingElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of ItemAwareMappingElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ItemAwareMappingElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMappingElement> listItemAwareMappingElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMappingElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ItemAwareMappingElement for all models
       * @return Iterator of org.semanticwb.process.model.ItemAwareMappingElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMappingElement> listItemAwareMappingElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMappingElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ItemAwareMappingElement
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMappingElement
       * @param model Model of the org.semanticwb.process.model.ItemAwareMappingElement
       * @return A org.semanticwb.process.model.ItemAwareMappingElement
       */
        public static org.semanticwb.process.model.ItemAwareMappingElement getItemAwareMappingElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareMappingElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ItemAwareMappingElement
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMappingElement
       * @param model Model of the org.semanticwb.process.model.ItemAwareMappingElement
       * @return A org.semanticwb.process.model.ItemAwareMappingElement
       */
        public static org.semanticwb.process.model.ItemAwareMappingElement createItemAwareMappingElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareMappingElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ItemAwareMappingElement
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMappingElement
       * @param model Model of the org.semanticwb.process.model.ItemAwareMappingElement
       */
        public static void removeItemAwareMappingElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ItemAwareMappingElement
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMappingElement
       * @param model Model of the org.semanticwb.process.model.ItemAwareMappingElement
       * @return true if the org.semanticwb.process.model.ItemAwareMappingElement exists, false otherwise
       */

        public static boolean hasItemAwareMappingElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getItemAwareMappingElement(id, model)!=null);
        }
    }

    public static ItemAwareMappingElementBase.ClassMgr getItemAwareMappingElementClassMgr()
    {
        return new ItemAwareMappingElementBase.ClassMgr();
    }

   /**
   * Constructs a ItemAwareMappingElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ItemAwareMappingElement
   */
    public ItemAwareMappingElementBase(org.semanticwb.platform.SemanticObject base)
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
