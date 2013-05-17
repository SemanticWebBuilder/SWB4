package org.semanticwb.bsc.formelement.base;


   /**
   * Período válidos son los que no tienen intervalos de tiempo traslapados 
   */
public abstract class PeriodicityBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_dateConstraints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#dateConstraints");
    public static final org.semanticwb.platform.SemanticProperty bsc_dateOnChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#dateOnChange");
    public static final org.semanticwb.platform.SemanticProperty bsc_dateId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#dateId");
   /**
   * Período válidos son los que no tienen intervalos de tiempo traslapados
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Periodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Periodicity");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Periodicity");

    public static class ClassMgr
    {
       /**
       * Returns a list of Periodicity for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.Periodicity
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.Periodicity> listPeriodicities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.Periodicity>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.Periodicity for all models
       * @return Iterator of org.semanticwb.bsc.formelement.Periodicity
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.Periodicity> listPeriodicities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.Periodicity>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.Periodicity
       * @param id Identifier for org.semanticwb.bsc.formelement.Periodicity
       * @param model Model of the org.semanticwb.bsc.formelement.Periodicity
       * @return A org.semanticwb.bsc.formelement.Periodicity
       */
        public static org.semanticwb.bsc.formelement.Periodicity getPeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.Periodicity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.Periodicity
       * @param id Identifier for org.semanticwb.bsc.formelement.Periodicity
       * @param model Model of the org.semanticwb.bsc.formelement.Periodicity
       * @return A org.semanticwb.bsc.formelement.Periodicity
       */
        public static org.semanticwb.bsc.formelement.Periodicity createPeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.Periodicity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.Periodicity
       * @param id Identifier for org.semanticwb.bsc.formelement.Periodicity
       * @param model Model of the org.semanticwb.bsc.formelement.Periodicity
       */
        public static void removePeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.Periodicity
       * @param id Identifier for org.semanticwb.bsc.formelement.Periodicity
       * @param model Model of the org.semanticwb.bsc.formelement.Periodicity
       * @return true if the org.semanticwb.bsc.formelement.Periodicity exists, false otherwise
       */

        public static boolean hasPeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPeriodicity(id, model)!=null);
        }
    }

    public static PeriodicityBase.ClassMgr getPeriodicityClassMgr()
    {
        return new PeriodicityBase.ClassMgr();
    }

   /**
   * Constructs a PeriodicityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Periodicity
   */
    public PeriodicityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Constraints property
* @return String with the Constraints
*/
    public String getConstraints()
    {
        return getSemanticObject().getProperty(bsc_dateConstraints);
    }

/**
* Sets the Constraints property
* @param value long with the Constraints
*/
    public void setConstraints(String value)
    {
        getSemanticObject().setProperty(bsc_dateConstraints, value);
    }

/**
* Gets the DateOnChange property
* @return String with the DateOnChange
*/
    public String getDateOnChange()
    {
        return getSemanticObject().getProperty(bsc_dateOnChange);
    }

/**
* Sets the DateOnChange property
* @param value long with the DateOnChange
*/
    public void setDateOnChange(String value)
    {
        getSemanticObject().setProperty(bsc_dateOnChange, value);
    }

/**
* Gets the DateId property
* @return String with the DateId
*/
    public String getDateId()
    {
        return getSemanticObject().getProperty(bsc_dateId);
    }

/**
* Sets the DateId property
* @param value long with the DateId
*/
    public void setDateId(String value)
    {
        getSemanticObject().setProperty(bsc_dateId, value);
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
