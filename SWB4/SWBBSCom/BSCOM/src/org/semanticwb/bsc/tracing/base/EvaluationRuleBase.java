package org.semanticwb.bsc.tracing.base;


public abstract class EvaluationRuleBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help
{
    public static final org.semanticwb.platform.SemanticClass bsc_Series=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Series");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasEvaluationRuleInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasEvaluationRuleInv");
    public static final org.semanticwb.platform.SemanticProperty bsc_operationId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#operationId");
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
    public static final org.semanticwb.platform.SemanticProperty bsc_appraisal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#appraisal");
    public static final org.semanticwb.platform.SemanticProperty bsc_anotherSeries=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#anotherSeries");
    public static final org.semanticwb.platform.SemanticProperty bsc_factor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#factor");
    public static final org.semanticwb.platform.SemanticClass bsc_EvaluationRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#EvaluationRule");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#EvaluationRule");

    public static class ClassMgr
    {
       /**
       * Returns a list of EvaluationRule for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRules(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.EvaluationRule for all models
       * @return Iterator of org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRules()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule>(it, true);
        }

        public static org.semanticwb.bsc.tracing.EvaluationRule createEvaluationRule(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.EvaluationRule.ClassMgr.createEvaluationRule(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.EvaluationRule
       * @param id Identifier for org.semanticwb.bsc.tracing.EvaluationRule
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return A org.semanticwb.bsc.tracing.EvaluationRule
       */
        public static org.semanticwb.bsc.tracing.EvaluationRule getEvaluationRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.EvaluationRule)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.EvaluationRule
       * @param id Identifier for org.semanticwb.bsc.tracing.EvaluationRule
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return A org.semanticwb.bsc.tracing.EvaluationRule
       */
        public static org.semanticwb.bsc.tracing.EvaluationRule createEvaluationRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.EvaluationRule)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.EvaluationRule
       * @param id Identifier for org.semanticwb.bsc.tracing.EvaluationRule
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       */
        public static void removeEvaluationRule(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.EvaluationRule
       * @param id Identifier for org.semanticwb.bsc.tracing.EvaluationRule
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return true if the org.semanticwb.bsc.tracing.EvaluationRule exists, false otherwise
       */

        public static boolean hasEvaluationRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEvaluationRule(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined Series
       * @param value Series of the type org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleBySeries(org.semanticwb.bsc.tracing.Series value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasEvaluationRuleInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined Series
       * @param value Series of the type org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleBySeries(org.semanticwb.bsc.tracing.Series value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasEvaluationRuleInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined Appraisal
       * @param value Appraisal of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByAppraisal(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_appraisal, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined Appraisal
       * @param value Appraisal of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByAppraisal(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_appraisal,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined AnotherSeries
       * @param value AnotherSeries of the type org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.tracing.EvaluationRule
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByAnotherSeries(org.semanticwb.bsc.tracing.Series value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_anotherSeries, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.EvaluationRule with a determined AnotherSeries
       * @param value AnotherSeries of the type org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.tracing.EvaluationRule
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.EvaluationRule> listEvaluationRuleByAnotherSeries(org.semanticwb.bsc.tracing.Series value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.EvaluationRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_anotherSeries,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EvaluationRuleBase.ClassMgr getEvaluationRuleClassMgr()
    {
        return new EvaluationRuleBase.ClassMgr();
    }

   /**
   * Constructs a EvaluationRuleBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EvaluationRule
   */
    public EvaluationRuleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Series
   * @param value Series to set
   */

    public void setSeries(org.semanticwb.bsc.tracing.Series value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_hasEvaluationRuleInv, value.getSemanticObject());
        }else
        {
            removeSeries();
        }
    }
   /**
   * Remove the value for Series property
   */

    public void removeSeries()
    {
        getSemanticObject().removeProperty(bsc_hasEvaluationRuleInv);
    }

   /**
   * Gets the Series
   * @return a org.semanticwb.bsc.tracing.Series
   */
    public org.semanticwb.bsc.tracing.Series getSeries()
    {
         org.semanticwb.bsc.tracing.Series ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasEvaluationRuleInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Series)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the OperationId property
* @return String with the OperationId
*/
    public String getOperationId()
    {
        return getSemanticObject().getProperty(bsc_operationId);
    }

/**
* Sets the OperationId property
* @param value long with the OperationId
*/
    public void setOperationId(String value)
    {
        getSemanticObject().setProperty(bsc_operationId, value);
    }
   /**
   * Sets the value for the property Appraisal
   * @param value Appraisal to set
   */

    public void setAppraisal(org.semanticwb.bsc.accessory.State value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_appraisal, value.getSemanticObject());
        }else
        {
            removeAppraisal();
        }
    }
   /**
   * Remove the value for Appraisal property
   */

    public void removeAppraisal()
    {
        getSemanticObject().removeProperty(bsc_appraisal);
    }

   /**
   * Gets the Appraisal
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getAppraisal()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_appraisal);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property AnotherSeries
   * @param value AnotherSeries to set
   */

    public void setAnotherSeries(org.semanticwb.bsc.tracing.Series value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_anotherSeries, value.getSemanticObject());
        }else
        {
            removeAnotherSeries();
        }
    }
   /**
   * Remove the value for AnotherSeries property
   */

    public void removeAnotherSeries()
    {
        getSemanticObject().removeProperty(bsc_anotherSeries);
    }

   /**
   * Gets the AnotherSeries
   * @return a org.semanticwb.bsc.tracing.Series
   */
    public org.semanticwb.bsc.tracing.Series getAnotherSeries()
    {
         org.semanticwb.bsc.tracing.Series ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_anotherSeries);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Series)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Factor property
* @return String with the Factor
*/
    public String getFactor()
    {
        return getSemanticObject().getProperty(bsc_factor);
    }

/**
* Sets the Factor property
* @param value long with the Factor
*/
    public void setFactor(String value)
    {
        getSemanticObject().setProperty(bsc_factor, value);
    }
}
