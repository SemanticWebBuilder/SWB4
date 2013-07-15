package org.semanticwb.bsc.element.base;


   /**
   * Define el orden en que debe aparecer una propiedad en la vista resumen correspondiente. 
   */
public abstract class PropertyListItemBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass rdf_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property");
   /**
   * Representa una propiedad que forma parte de una vista resumen.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_elementProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#elementProperty");
   /**
   * Indica el orden en que aparece cada propiedad seleccionada en la vista.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_propertyOrder=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#propertyOrder");
   /**
   * Define el orden en que debe aparecer una propiedad en la vista resumen correspondiente.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PropertyListItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PropertyListItem");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PropertyListItem");

    public static class ClassMgr
    {
       /**
       * Returns a list of PropertyListItem for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.PropertyListItem
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.PropertyListItem> listPropertyListItems(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.PropertyListItem>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.PropertyListItem for all models
       * @return Iterator of org.semanticwb.bsc.element.PropertyListItem
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.PropertyListItem> listPropertyListItems()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.PropertyListItem>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.element.PropertyListItem
       * @param id Identifier for org.semanticwb.bsc.element.PropertyListItem
       * @param model Model of the org.semanticwb.bsc.element.PropertyListItem
       * @return A org.semanticwb.bsc.element.PropertyListItem
       */
        public static org.semanticwb.bsc.element.PropertyListItem getPropertyListItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.PropertyListItem)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.PropertyListItem
       * @param id Identifier for org.semanticwb.bsc.element.PropertyListItem
       * @param model Model of the org.semanticwb.bsc.element.PropertyListItem
       * @return A org.semanticwb.bsc.element.PropertyListItem
       */
        public static org.semanticwb.bsc.element.PropertyListItem createPropertyListItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.PropertyListItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.PropertyListItem
       * @param id Identifier for org.semanticwb.bsc.element.PropertyListItem
       * @param model Model of the org.semanticwb.bsc.element.PropertyListItem
       */
        public static void removePropertyListItem(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.PropertyListItem
       * @param id Identifier for org.semanticwb.bsc.element.PropertyListItem
       * @param model Model of the org.semanticwb.bsc.element.PropertyListItem
       * @return true if the org.semanticwb.bsc.element.PropertyListItem exists, false otherwise
       */

        public static boolean hasPropertyListItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPropertyListItem(id, model)!=null);
        }
    }

    public static PropertyListItemBase.ClassMgr getPropertyListItemClassMgr()
    {
        return new PropertyListItemBase.ClassMgr();
    }

   /**
   * Constructs a PropertyListItemBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PropertyListItem
   */
    public PropertyListItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setElementProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(bsc_elementProperty, value);
    }

    public void removeElementProperty()
    {
        getSemanticObject().removeProperty(bsc_elementProperty);
    }

/**
* Gets the ElementProperty property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getElementProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(bsc_elementProperty);
         return ret;
    }

/**
* Gets the PropertyOrder property
* @return int with the PropertyOrder
*/
    public int getPropertyOrder()
    {
        return getSemanticObject().getIntProperty(bsc_propertyOrder);
    }

/**
* Sets the PropertyOrder property
* @param value long with the PropertyOrder
*/
    public void setPropertyOrder(int value)
    {
        getSemanticObject().setIntProperty(bsc_propertyOrder, value);
    }
}
