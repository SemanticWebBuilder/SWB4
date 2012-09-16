package org.semanticwb.model.base;


   /**
   * Elemento que muestra un componente grafico para seleccionar una fecha 
   */
public abstract class DateElementBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swb_dateConstraints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dateConstraints");
    public static final org.semanticwb.platform.SemanticProperty swb_dateOnChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dateOnChange");
    public static final org.semanticwb.platform.SemanticProperty swb_dateId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dateId");
   /**
   * Elemento que muestra un componente grafico para seleccionar una fecha
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_DateElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DateElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DateElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of DateElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.DateElement
       */

        public static java.util.Iterator<org.semanticwb.model.DateElement> listDateElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DateElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.DateElement for all models
       * @return Iterator of org.semanticwb.model.DateElement
       */

        public static java.util.Iterator<org.semanticwb.model.DateElement> listDateElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DateElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.DateElement
       * @param id Identifier for org.semanticwb.model.DateElement
       * @param model Model of the org.semanticwb.model.DateElement
       * @return A org.semanticwb.model.DateElement
       */
        public static org.semanticwb.model.DateElement getDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DateElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.DateElement
       * @param id Identifier for org.semanticwb.model.DateElement
       * @param model Model of the org.semanticwb.model.DateElement
       * @return A org.semanticwb.model.DateElement
       */
        public static org.semanticwb.model.DateElement createDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DateElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.DateElement
       * @param id Identifier for org.semanticwb.model.DateElement
       * @param model Model of the org.semanticwb.model.DateElement
       */
        public static void removeDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.DateElement
       * @param id Identifier for org.semanticwb.model.DateElement
       * @param model Model of the org.semanticwb.model.DateElement
       * @return true if the org.semanticwb.model.DateElement exists, false otherwise
       */

        public static boolean hasDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDateElement(id, model)!=null);
        }
    }

    public static DateElementBase.ClassMgr getDateElementClassMgr()
    {
        return new DateElementBase.ClassMgr();
    }

   /**
   * Constructs a DateElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DateElement
   */
    public DateElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Constraints property
* @return String with the Constraints
*/
    public String getConstraints()
    {
        return getSemanticObject().getProperty(swb_dateConstraints);
    }

/**
* Sets the Constraints property
* @param value long with the Constraints
*/
    public void setConstraints(String value)
    {
        getSemanticObject().setProperty(swb_dateConstraints, value);
    }

/**
* Gets the DateOnChange property
* @return String with the DateOnChange
*/
    public String getDateOnChange()
    {
        return getSemanticObject().getProperty(swb_dateOnChange);
    }

/**
* Sets the DateOnChange property
* @param value long with the DateOnChange
*/
    public void setDateOnChange(String value)
    {
        getSemanticObject().setProperty(swb_dateOnChange, value);
    }

/**
* Gets the DateId property
* @return String with the DateId
*/
    public String getDateId()
    {
        return getSemanticObject().getProperty(swb_dateId);
    }

/**
* Sets the DateId property
* @param value long with the DateId
*/
    public void setDateId(String value)
    {
        getSemanticObject().setProperty(swb_dateId, value);
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
