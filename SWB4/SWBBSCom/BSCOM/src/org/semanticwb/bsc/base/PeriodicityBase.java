package org.semanticwb.bsc.base;


public abstract class PeriodicityBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_fin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#fin");
    public static final org.semanticwb.platform.SemanticProperty bsc_inicio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#inicio");
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
       * @return Iterator of org.semanticwb.bsc.Periodicity
       */

        public static java.util.Iterator<org.semanticwb.bsc.Periodicity> listPeriodicities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Periodicity>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.Periodicity for all models
       * @return Iterator of org.semanticwb.bsc.Periodicity
       */

        public static java.util.Iterator<org.semanticwb.bsc.Periodicity> listPeriodicities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Periodicity>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.Periodicity
       * @param id Identifier for org.semanticwb.bsc.Periodicity
       * @param model Model of the org.semanticwb.bsc.Periodicity
       * @return A org.semanticwb.bsc.Periodicity
       */
        public static org.semanticwb.bsc.Periodicity getPeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.Periodicity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.Periodicity
       * @param id Identifier for org.semanticwb.bsc.Periodicity
       * @param model Model of the org.semanticwb.bsc.Periodicity
       * @return A org.semanticwb.bsc.Periodicity
       */
        public static org.semanticwb.bsc.Periodicity createPeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.Periodicity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.Periodicity
       * @param id Identifier for org.semanticwb.bsc.Periodicity
       * @param model Model of the org.semanticwb.bsc.Periodicity
       */
        public static void removePeriodicity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.Periodicity
       * @param id Identifier for org.semanticwb.bsc.Periodicity
       * @param model Model of the org.semanticwb.bsc.Periodicity
       * @return true if the org.semanticwb.bsc.Periodicity exists, false otherwise
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
* Gets the Fin property
* @return String with the Fin
*/
    public String getFin()
    {
        return getSemanticObject().getProperty(bsc_fin);
    }

/**
* Sets the Fin property
* @param value long with the Fin
*/
    public void setFin(String value)
    {
        getSemanticObject().setProperty(bsc_fin, value);
    }

/**
* Gets the Inicio property
* @return String with the Inicio
*/
    public String getInicio()
    {
        return getSemanticObject().getProperty(bsc_inicio);
    }

/**
* Sets the Inicio property
* @param value long with the Inicio
*/
    public void setInicio(String value)
    {
        getSemanticObject().setProperty(bsc_inicio, value);
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
