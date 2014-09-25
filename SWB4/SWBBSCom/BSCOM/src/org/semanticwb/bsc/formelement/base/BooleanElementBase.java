package org.semanticwb.bsc.formelement.base;


   /**
   * Elemento para valores booleanos configurable como checkbox para edición y las palabras Si/No para impresión 
   */
public abstract class BooleanElementBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_booleanDisplayTrueTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#booleanDisplayTrueTitle");
   /**
   * Tipo de despliegue del elemento ("checkbox","radio","select")
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_booleanDisplayType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#booleanDisplayType");
    public static final org.semanticwb.platform.SemanticProperty bsc_booleanDisplayFalseTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#booleanDisplayFalseTitle");
   /**
   * Elemento para valores booleanos configurable como checkbox para edición y las palabras Si/No para impresión
   */
    public static final org.semanticwb.platform.SemanticClass bsc_BooleanElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BooleanElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BooleanElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of BooleanElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.BooleanElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.BooleanElement> listBooleanElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.BooleanElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.BooleanElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.BooleanElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.BooleanElement> listBooleanElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.BooleanElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.BooleanElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BooleanElement
       * @param model Model of the org.semanticwb.bsc.formelement.BooleanElement
       * @return A org.semanticwb.bsc.formelement.BooleanElement
       */
        public static org.semanticwb.bsc.formelement.BooleanElement getBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.BooleanElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.BooleanElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BooleanElement
       * @param model Model of the org.semanticwb.bsc.formelement.BooleanElement
       * @return A org.semanticwb.bsc.formelement.BooleanElement
       */
        public static org.semanticwb.bsc.formelement.BooleanElement createBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.BooleanElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.BooleanElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BooleanElement
       * @param model Model of the org.semanticwb.bsc.formelement.BooleanElement
       */
        public static void removeBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.BooleanElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BooleanElement
       * @param model Model of the org.semanticwb.bsc.formelement.BooleanElement
       * @return true if the org.semanticwb.bsc.formelement.BooleanElement exists, false otherwise
       */

        public static boolean hasBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBooleanElement(id, model)!=null);
        }
    }

    public static BooleanElementBase.ClassMgr getBooleanElementClassMgr()
    {
        return new BooleanElementBase.ClassMgr();
    }

   /**
   * Constructs a BooleanElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BooleanElement
   */
    public BooleanElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the TrueTitle property
* @return String with the TrueTitle
*/
    public String getTrueTitle()
    {
        return getSemanticObject().getProperty(bsc_booleanDisplayTrueTitle);
    }

/**
* Sets the TrueTitle property
* @param value long with the TrueTitle
*/
    public void setTrueTitle(String value)
    {
        getSemanticObject().setProperty(bsc_booleanDisplayTrueTitle, value);
    }

    public String getTrueTitle(String lang)
    {
        return getSemanticObject().getProperty(bsc_booleanDisplayTrueTitle, null, lang);
    }

    public String getDisplayTrueTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(bsc_booleanDisplayTrueTitle, lang);
    }

    public void setTrueTitle(String booleanDisplayTrueTitle, String lang)
    {
        getSemanticObject().setProperty(bsc_booleanDisplayTrueTitle, booleanDisplayTrueTitle, lang);
    }

/**
* Gets the DisplayType property
* @return String with the DisplayType
*/
    public String getDisplayType()
    {
        return getSemanticObject().getProperty(bsc_booleanDisplayType);
    }

/**
* Sets the DisplayType property
* @param value long with the DisplayType
*/
    public void setDisplayType(String value)
    {
        getSemanticObject().setProperty(bsc_booleanDisplayType, value);
    }

    public String getDisplayType(String lang)
    {
        return getSemanticObject().getProperty(bsc_booleanDisplayType, null, lang);
    }

    public String getDisplayDisplayType(String lang)
    {
        return getSemanticObject().getLocaleProperty(bsc_booleanDisplayType, lang);
    }

    public void setDisplayType(String booleanDisplayType, String lang)
    {
        getSemanticObject().setProperty(bsc_booleanDisplayType, booleanDisplayType, lang);
    }

/**
* Gets the FalseTitle property
* @return String with the FalseTitle
*/
    public String getFalseTitle()
    {
        return getSemanticObject().getProperty(bsc_booleanDisplayFalseTitle);
    }

/**
* Sets the FalseTitle property
* @param value long with the FalseTitle
*/
    public void setFalseTitle(String value)
    {
        getSemanticObject().setProperty(bsc_booleanDisplayFalseTitle, value);
    }

    public String getFalseTitle(String lang)
    {
        return getSemanticObject().getProperty(bsc_booleanDisplayFalseTitle, null, lang);
    }

    public String getDisplayFalseTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(bsc_booleanDisplayFalseTitle, lang);
    }

    public void setFalseTitle(String booleanDisplayFalseTitle, String lang)
    {
        getSemanticObject().setProperty(bsc_booleanDisplayFalseTitle, booleanDisplayFalseTitle, lang);
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
