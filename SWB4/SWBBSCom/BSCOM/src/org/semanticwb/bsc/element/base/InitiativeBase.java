package org.semanticwb.bsc.element.base;


public abstract class InitiativeBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.FilterableClass,org.semanticwb.bsc.Committable,org.semanticwb.bsc.Schedule,org.semanticwb.bsc.Updateable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Status,org.semanticwb.bsc.Help,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Sortable,org.semanticwb.model.Activeable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Traceable,org.semanticwb.model.RuleRefable,org.semanticwb.model.FilterableNode
{
    public static final org.semanticwb.platform.SemanticProperty bsc_totalInvestment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#totalInvestment");
    public static final org.semanticwb.platform.SemanticProperty bsc_businessCase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#businessCase");
    public static final org.semanticwb.platform.SemanticProperty bsc_risponsableInitiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#risponsableInitiative");
    public static final org.semanticwb.platform.SemanticClass bsc_Deliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDeliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDeliverable");
    public static final org.semanticwb.platform.SemanticProperty bsc_area=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#area");
    public static final org.semanticwb.platform.SemanticProperty bsc_estimatedStart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#estimatedStart");
    public static final org.semanticwb.platform.SemanticProperty bsc_estimatedEnd=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#estimatedEnd");
    public static final org.semanticwb.platform.SemanticProperty bsc_investmentAmount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#investmentAmount");
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");

    public static class ClassMgr
    {
       /**
       * Returns a list of Initiative for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiatives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Initiative for all models
       * @return Iterator of org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiatives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative>(it, true);
        }

        public static org.semanticwb.bsc.element.Initiative createInitiative(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Initiative.ClassMgr.createInitiative(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Initiative
       * @param id Identifier for org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return A org.semanticwb.bsc.element.Initiative
       */
        public static org.semanticwb.bsc.element.Initiative getInitiative(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Initiative)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Initiative
       * @param id Identifier for org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return A org.semanticwb.bsc.element.Initiative
       */
        public static org.semanticwb.bsc.element.Initiative createInitiative(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Initiative)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Initiative
       * @param id Identifier for org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       */
        public static void removeInitiative(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Initiative
       * @param id Identifier for org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return true if the org.semanticwb.bsc.element.Initiative exists, false otherwise
       */

        public static boolean hasInitiative(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInitiative(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined State
       * @param value State of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByState(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasState, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined State
       * @param value State of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByState(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasState,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Deliverable
       * @param value Deliverable of the type org.semanticwb.bsc.element.Deliverable
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByDeliverable(org.semanticwb.bsc.element.Deliverable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDeliverable, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Deliverable
       * @param value Deliverable of the type org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByDeliverable(org.semanticwb.bsc.element.Deliverable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDeliverable,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static InitiativeBase.ClassMgr getInitiativeClassMgr()
    {
        return new InitiativeBase.ClassMgr();
    }

   /**
   * Constructs a InitiativeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Initiative
   */
    public InitiativeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the TotalInvestment property
* @return String with the TotalInvestment
*/
    public String getTotalInvestment()
    {
        return getSemanticObject().getProperty(bsc_totalInvestment);
    }

/**
* Sets the TotalInvestment property
* @param value long with the TotalInvestment
*/
    public void setTotalInvestment(String value)
    {
        getSemanticObject().setProperty(bsc_totalInvestment, value);
    }

/**
* Gets the BusinessCase property
* @return String with the BusinessCase
*/
    public String getBusinessCase()
    {
        return getSemanticObject().getProperty(bsc_businessCase);
    }

/**
* Sets the BusinessCase property
* @param value long with the BusinessCase
*/
    public void setBusinessCase(String value)
    {
        getSemanticObject().setProperty(bsc_businessCase, value);
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
* Gets the PlannedEnd property
* @return java.util.Date with the PlannedEnd
*/
    public java.util.Date getPlannedEnd()
    {
        return getSemanticObject().getDateProperty(bsc_plannedEnd);
    }

/**
* Sets the PlannedEnd property
* @param value long with the PlannedEnd
*/
    public void setPlannedEnd(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_plannedEnd, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.State
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.State
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> listStates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State>(getSemanticObject().listObjectProperties(bsc_hasState));
    }

   /**
   * Gets true if has a State
   * @param value org.semanticwb.bsc.accessory.State to verify
   * @return true if the org.semanticwb.bsc.accessory.State exists, false otherwise
   */
    public boolean hasState(org.semanticwb.bsc.accessory.State value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasState,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a State
   * @param value org.semanticwb.bsc.accessory.State to add
   */

    public void addState(org.semanticwb.bsc.accessory.State value)
    {
        getSemanticObject().addObjectProperty(bsc_hasState, value.getSemanticObject());
    }
   /**
   * Removes all the State
   */

    public void removeAllState()
    {
        getSemanticObject().removeProperty(bsc_hasState);
    }
   /**
   * Removes a State
   * @param value org.semanticwb.bsc.accessory.State to remove
   */

    public void removeState(org.semanticwb.bsc.accessory.State value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasState,value.getSemanticObject());
    }

   /**
   * Gets the State
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getState()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasState);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(bsc_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(bsc_index, value);
    }

/**
* Gets the RisponsableInitiative property
* @return String with the RisponsableInitiative
*/
    public String getRisponsableInitiative()
    {
        return getSemanticObject().getProperty(bsc_risponsableInitiative);
    }

/**
* Sets the RisponsableInitiative property
* @param value long with the RisponsableInitiative
*/
    public void setRisponsableInitiative(String value)
    {
        getSemanticObject().setProperty(bsc_risponsableInitiative, value);
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
   * Gets all the org.semanticwb.bsc.element.Deliverable
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Deliverable
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> listDeliverables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable>(getSemanticObject().listObjectProperties(bsc_hasDeliverable));
    }

   /**
   * Gets true if has a Deliverable
   * @param value org.semanticwb.bsc.element.Deliverable to verify
   * @return true if the org.semanticwb.bsc.element.Deliverable exists, false otherwise
   */
    public boolean hasDeliverable(org.semanticwb.bsc.element.Deliverable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasDeliverable,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Deliverable
   * @param value org.semanticwb.bsc.element.Deliverable to add
   */

    public void addDeliverable(org.semanticwb.bsc.element.Deliverable value)
    {
        getSemanticObject().addObjectProperty(bsc_hasDeliverable, value.getSemanticObject());
    }
   /**
   * Removes all the Deliverable
   */

    public void removeAllDeliverable()
    {
        getSemanticObject().removeProperty(bsc_hasDeliverable);
    }
   /**
   * Removes a Deliverable
   * @param value org.semanticwb.bsc.element.Deliverable to remove
   */

    public void removeDeliverable(org.semanticwb.bsc.element.Deliverable value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasDeliverable,value.getSemanticObject());
    }

   /**
   * Gets the Deliverable
   * @return a org.semanticwb.bsc.element.Deliverable
   */
    public org.semanticwb.bsc.element.Deliverable getDeliverable()
    {
         org.semanticwb.bsc.element.Deliverable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasDeliverable);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Deliverable)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PlannedStart property
* @return java.util.Date with the PlannedStart
*/
    public java.util.Date getPlannedStart()
    {
        return getSemanticObject().getDateProperty(bsc_plannedStart);
    }

/**
* Sets the PlannedStart property
* @param value long with the PlannedStart
*/
    public void setPlannedStart(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_plannedStart, value);
    }

/**
* Gets the ActualEnd property
* @return java.util.Date with the ActualEnd
*/
    public java.util.Date getActualEnd()
    {
        return getSemanticObject().getDateProperty(bsc_actualEnd);
    }

/**
* Sets the ActualEnd property
* @param value long with the ActualEnd
*/
    public void setActualEnd(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_actualEnd, value);
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
* Gets the Area property
* @return String with the Area
*/
    public String getArea()
    {
        return getSemanticObject().getProperty(bsc_area);
    }

/**
* Sets the Area property
* @param value long with the Area
*/
    public void setArea(String value)
    {
        getSemanticObject().setProperty(bsc_area, value);
    }

/**
* Gets the Commited property
* @return boolean with the Commited
*/
    public boolean isCommited()
    {
        return getSemanticObject().getBooleanProperty(bsc_commited);
    }

/**
* Sets the Commited property
* @param value long with the Commited
*/
    public void setCommited(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_commited, value);
    }

/**
* Gets the ActualStart property
* @return java.util.Date with the ActualStart
*/
    public java.util.Date getActualStart()
    {
        return getSemanticObject().getDateProperty(bsc_actualStart);
    }

/**
* Sets the ActualStart property
* @param value long with the ActualStart
*/
    public void setActualStart(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_actualStart, value);
    }

/**
* Gets the EstimatedStart property
* @return java.util.Date with the EstimatedStart
*/
    public java.util.Date getEstimatedStart()
    {
        return getSemanticObject().getDateProperty(bsc_estimatedStart);
    }

/**
* Sets the EstimatedStart property
* @param value long with the EstimatedStart
*/
    public void setEstimatedStart(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_estimatedStart, value);
    }

/**
* Gets the EstimatedEnd property
* @return java.util.Date with the EstimatedEnd
*/
    public java.util.Date getEstimatedEnd()
    {
        return getSemanticObject().getDateProperty(bsc_estimatedEnd);
    }

/**
* Sets the EstimatedEnd property
* @param value long with the EstimatedEnd
*/
    public void setEstimatedEnd(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_estimatedEnd, value);
    }

/**
* Gets the InvestmentAmount property
* @return String with the InvestmentAmount
*/
    public String getInvestmentAmount()
    {
        return getSemanticObject().getProperty(bsc_investmentAmount);
    }

/**
* Sets the InvestmentAmount property
* @param value long with the InvestmentAmount
*/
    public void setInvestmentAmount(String value)
    {
        getSemanticObject().setProperty(bsc_investmentAmount, value);
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
