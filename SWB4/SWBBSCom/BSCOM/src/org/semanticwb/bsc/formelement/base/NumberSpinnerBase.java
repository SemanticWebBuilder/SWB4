package org.semanticwb.bsc.formelement.base;


   /**
   * Form Element que presentará la vista para un sppiner, el cual permitirá ajustar un valor dentro de un cuadro de texto. 
   */
public abstract class NumberSpinnerBase extends org.semanticwb.model.Text 
{
   /**
   * Define el valor máximo permitido.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_valueMax=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#valueMax");
   /**
   * Define el valor mínimo permitido
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_valueMin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#valueMin");
   /**
   * Define el incremento permitido.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_increase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#increase");
   /**
   * Form Element que presentará la vista para un sppiner, el cual permitirá ajustar un valor dentro de un cuadro de texto.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_NumberSpinner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#NumberSpinner");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#NumberSpinner");

    public static class ClassMgr
    {
       /**
       * Returns a list of NumberSpinner for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.NumberSpinner
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.NumberSpinner> listNumberSpinners(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.NumberSpinner>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.NumberSpinner for all models
       * @return Iterator of org.semanticwb.bsc.formelement.NumberSpinner
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.NumberSpinner> listNumberSpinners()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.NumberSpinner>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.NumberSpinner
       * @param id Identifier for org.semanticwb.bsc.formelement.NumberSpinner
       * @param model Model of the org.semanticwb.bsc.formelement.NumberSpinner
       * @return A org.semanticwb.bsc.formelement.NumberSpinner
       */
        public static org.semanticwb.bsc.formelement.NumberSpinner getNumberSpinner(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.NumberSpinner)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.NumberSpinner
       * @param id Identifier for org.semanticwb.bsc.formelement.NumberSpinner
       * @param model Model of the org.semanticwb.bsc.formelement.NumberSpinner
       * @return A org.semanticwb.bsc.formelement.NumberSpinner
       */
        public static org.semanticwb.bsc.formelement.NumberSpinner createNumberSpinner(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.NumberSpinner)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.NumberSpinner
       * @param id Identifier for org.semanticwb.bsc.formelement.NumberSpinner
       * @param model Model of the org.semanticwb.bsc.formelement.NumberSpinner
       */
        public static void removeNumberSpinner(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.NumberSpinner
       * @param id Identifier for org.semanticwb.bsc.formelement.NumberSpinner
       * @param model Model of the org.semanticwb.bsc.formelement.NumberSpinner
       * @return true if the org.semanticwb.bsc.formelement.NumberSpinner exists, false otherwise
       */

        public static boolean hasNumberSpinner(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNumberSpinner(id, model)!=null);
        }
    }

    public static NumberSpinnerBase.ClassMgr getNumberSpinnerClassMgr()
    {
        return new NumberSpinnerBase.ClassMgr();
    }

   /**
   * Constructs a NumberSpinnerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the NumberSpinner
   */
    public NumberSpinnerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ValueMax property
* @return int with the ValueMax
*/
    public int getValueMax()
    {
        return getSemanticObject().getIntProperty(bsc_valueMax);
    }

/**
* Sets the ValueMax property
* @param value long with the ValueMax
*/
    public void setValueMax(int value)
    {
        getSemanticObject().setIntProperty(bsc_valueMax, value);
    }

/**
* Gets the ValueMin property
* @return int with the ValueMin
*/
    public int getValueMin()
    {
        return getSemanticObject().getIntProperty(bsc_valueMin);
    }

/**
* Sets the ValueMin property
* @param value long with the ValueMin
*/
    public void setValueMin(int value)
    {
        getSemanticObject().setIntProperty(bsc_valueMin, value);
    }

/**
* Gets the Increase property
* @return int with the Increase
*/
    public int getIncrease()
    {
        return getSemanticObject().getIntProperty(bsc_increase);
    }

/**
* Sets the Increase property
* @param value long with the Increase
*/
    public void setIncrease(int value)
    {
        getSemanticObject().setIntProperty(bsc_increase, value);
    }
}
