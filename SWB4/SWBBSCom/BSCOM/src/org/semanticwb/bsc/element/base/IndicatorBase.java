package org.semanticwb.bsc.element.base;


public abstract class IndicatorBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.bsc.Updateable,org.semanticwb.bsc.Recognizable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Clase que permite definir los atributos de las evidencias de un Indicador
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Evidence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Evidence");
   /**
   * Periste las evidencias asociadas a un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasEvidence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasEvidence");
   /**
   * Persiste la unidad de medida de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_unitMesure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#unitMesure");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Persiste información de un facilitador de campeón en una indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_championFacilitator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#championFacilitator");
   /**
   * Persiste la frecuencia de medida de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_measurementFrequency=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#measurementFrequency");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
    public static final org.semanticwb.platform.SemanticProperty bsc_objectiveInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#objectiveInv");
   /**
   * Persiste información de una nota a la fórmula de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_notesFormula=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#notesFormula");
   /**
   * Persiste una fuente de información de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_informationSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#informationSource");
   /**
   * Persiste información de las metas de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_goals=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#goals");
   /**
   * Persiste información de una formula para el cálculo de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_formula=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#formula");
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");

    public static class ClassMgr
    {
       /**
       * Returns a list of Indicator for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Indicator for all models
       * @return Iterator of org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(it, true);
        }

        public static org.semanticwb.bsc.element.Indicator createIndicator(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Indicator.ClassMgr.createIndicator(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return A org.semanticwb.bsc.element.Indicator
       */
        public static org.semanticwb.bsc.element.Indicator getIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Indicator)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return A org.semanticwb.bsc.element.Indicator
       */
        public static org.semanticwb.bsc.element.Indicator createIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Indicator)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       */
        public static void removeIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return true if the org.semanticwb.bsc.element.Indicator exists, false otherwise
       */

        public static boolean hasIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (getIndicator(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Evidence
       * @param value Evidence of the type org.semanticwb.bsc.tracing.Evidence
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByEvidence(org.semanticwb.bsc.tracing.Evidence value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasEvidence, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Evidence
       * @param value Evidence of the type org.semanticwb.bsc.tracing.Evidence
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByEvidence(org.semanticwb.bsc.tracing.Evidence value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasEvidence,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ChampionFacilitator
       * @param value ChampionFacilitator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChampionFacilitator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_championFacilitator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ChampionFacilitator
       * @param value ChampionFacilitator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChampionFacilitator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_championFacilitator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_objectiveInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_objectiveInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static IndicatorBase.ClassMgr getIndicatorClassMgr()
    {
        return new IndicatorBase.ClassMgr();
    }

   /**
   * Constructs a IndicatorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Indicator
   */
    public IndicatorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.tracing.Evidence
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.Evidence
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> listEvidences()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence>(getSemanticObject().listObjectProperties(bsc_hasEvidence));
    }

   /**
   * Gets true if has a Evidence
   * @param value org.semanticwb.bsc.tracing.Evidence to verify
   * @return true if the org.semanticwb.bsc.tracing.Evidence exists, false otherwise
   */
    public boolean hasEvidence(org.semanticwb.bsc.tracing.Evidence value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasEvidence,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Evidence
   * @param value org.semanticwb.bsc.tracing.Evidence to add
   */

    public void addEvidence(org.semanticwb.bsc.tracing.Evidence value)
    {
        getSemanticObject().addObjectProperty(bsc_hasEvidence, value.getSemanticObject());
    }
   /**
   * Removes all the Evidence
   */

    public void removeAllEvidence()
    {
        getSemanticObject().removeProperty(bsc_hasEvidence);
    }
   /**
   * Removes a Evidence
   * @param value org.semanticwb.bsc.tracing.Evidence to remove
   */

    public void removeEvidence(org.semanticwb.bsc.tracing.Evidence value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasEvidence,value.getSemanticObject());
    }

   /**
   * Gets the Evidence
   * @return a org.semanticwb.bsc.tracing.Evidence
   */
    public org.semanticwb.bsc.tracing.Evidence getEvidence()
    {
         org.semanticwb.bsc.tracing.Evidence ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasEvidence);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Evidence)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PercentageProgress property
* @return float with the PercentageProgress
*/
    public float getPercentageProgress()
    {
        return getSemanticObject().getFloatProperty(bsc_percentageProgress);
    }

/**
* Sets the PercentageProgress property
* @param value long with the PercentageProgress
*/
    public void setPercentageProgress(float value)
    {
        getSemanticObject().setFloatProperty(bsc_percentageProgress, value);
    }

/**
* Gets the UnitMesure property
* @return String with the UnitMesure
*/
    public String getUnitMesure()
    {
        return getSemanticObject().getProperty(bsc_unitMesure);
    }

/**
* Sets the UnitMesure property
* @param value long with the UnitMesure
*/
    public void setUnitMesure(String value)
    {
        getSemanticObject().setProperty(bsc_unitMesure, value);
    }
   /**
   * Sets the value for the property ChampionFacilitator
   * @param value ChampionFacilitator to set
   */

    public void setChampionFacilitator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_championFacilitator, value.getSemanticObject());
        }else
        {
            removeChampionFacilitator();
        }
    }
   /**
   * Remove the value for ChampionFacilitator property
   */

    public void removeChampionFacilitator()
    {
        getSemanticObject().removeProperty(bsc_championFacilitator);
    }

   /**
   * Gets the ChampionFacilitator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getChampionFacilitator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_championFacilitator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Indicator object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Indicator object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }

/**
* Gets the MeasurementFrequency property
* @return String with the MeasurementFrequency
*/
    public String getMeasurementFrequency()
    {
        return getSemanticObject().getProperty(bsc_measurementFrequency);
    }

/**
* Sets the MeasurementFrequency property
* @param value long with the MeasurementFrequency
*/
    public void setMeasurementFrequency(String value)
    {
        getSemanticObject().setProperty(bsc_measurementFrequency, value);
    }

/**
* Gets the Recommendations property
* @return String with the Recommendations
*/
    public String getRecommendations()
    {
        return getSemanticObject().getProperty(bsc_recommendations);
    }

/**
* Sets the Recommendations property
* @param value long with the Recommendations
*/
    public void setRecommendations(String value)
    {
        getSemanticObject().setProperty(bsc_recommendations, value);
    }
   /**
   * Sets the value for the property Objective
   * @param value Objective to set
   */

    public void setObjective(org.semanticwb.bsc.element.Objective value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_objectiveInv, value.getSemanticObject());
        }else
        {
            removeObjective();
        }
    }
   /**
   * Remove the value for Objective property
   */

    public void removeObjective()
    {
        getSemanticObject().removeProperty(bsc_objectiveInv);
    }

   /**
   * Gets the Objective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_objectiveInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Objective)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Analysis property
* @return String with the Analysis
*/
    public String getAnalysis()
    {
        return getSemanticObject().getProperty(bsc_analysis);
    }

/**
* Sets the Analysis property
* @param value long with the Analysis
*/
    public void setAnalysis(String value)
    {
        getSemanticObject().setProperty(bsc_analysis, value);
    }

/**
* Gets the NotesFormula property
* @return String with the NotesFormula
*/
    public String getNotesFormula()
    {
        return getSemanticObject().getProperty(bsc_notesFormula);
    }

/**
* Sets the NotesFormula property
* @param value long with the NotesFormula
*/
    public void setNotesFormula(String value)
    {
        getSemanticObject().setProperty(bsc_notesFormula, value);
    }

/**
* Gets the InformationSource property
* @return String with the InformationSource
*/
    public String getInformationSource()
    {
        return getSemanticObject().getProperty(bsc_informationSource);
    }

/**
* Sets the InformationSource property
* @param value long with the InformationSource
*/
    public void setInformationSource(String value)
    {
        getSemanticObject().setProperty(bsc_informationSource, value);
    }

/**
* Gets the Goals property
* @return String with the Goals
*/
    public String getGoals()
    {
        return getSemanticObject().getProperty(bsc_goals);
    }

/**
* Sets the Goals property
* @param value long with the Goals
*/
    public void setGoals(String value)
    {
        getSemanticObject().setProperty(bsc_goals, value);
    }

/**
* Gets the Formula property
* @return String with the Formula
*/
    public String getFormula()
    {
        return getSemanticObject().getProperty(bsc_formula);
    }

/**
* Sets the Formula property
* @param value long with the Formula
*/
    public void setFormula(String value)
    {
        getSemanticObject().setProperty(bsc_formula, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
