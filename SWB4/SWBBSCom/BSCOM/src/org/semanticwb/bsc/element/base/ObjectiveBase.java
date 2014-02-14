package org.semanticwb.bsc.element.base;


public abstract class ObjectiveBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.bsc.Sortable,org.semanticwb.bsc.Preference,org.semanticwb.model.Referensable,org.semanticwb.bsc.PeriodStatusAssignable,org.semanticwb.model.Traceable,org.semanticwb.bsc.FixedMeasurable,org.semanticwb.bsc.Help,org.semanticwb.bsc.Causal,org.semanticwb.model.UserGroupable,org.semanticwb.bsc.Serializable,org.semanticwb.bsc.Status,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Updateable,org.semanticwb.bsc.Seasonable,org.semanticwb.model.FilterableNode,org.semanticwb.model.RuleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Roleable,org.semanticwb.bsc.Recognizable,org.semanticwb.model.Filterable
{
   /**
   * Persiste los atributos de un indicador
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasIndicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasIndicator");
   /**
   * Los temas estratégicos agrupan objetivos con fines en común. A su vez, los temas están agrupados dentro de las perspectivas.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");
    public static final org.semanticwb.platform.SemanticProperty bsc_themeInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#themeInv");
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasInitiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasInitiative");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
   /**
   * Especifica el objetivo al cual está alineado el objetivo en cuestión, o sea el objetivo padre
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_parentObjective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#parentObjective");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Un usuario que se asigna como dueño del objetivo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_sponsor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#sponsor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");

    public static class ClassMgr
    {
       /**
       * Returns a list of Objective for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Objective for all models
       * @return Iterator of org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(it, true);
        }

        public static org.semanticwb.bsc.element.Objective createObjective(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Objective.ClassMgr.createObjective(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return A org.semanticwb.bsc.element.Objective
       */
        public static org.semanticwb.bsc.element.Objective getObjective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Objective)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return A org.semanticwb.bsc.element.Objective
       */
        public static org.semanticwb.bsc.element.Objective createObjective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Objective)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       */
        public static void removeObjective(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return true if the org.semanticwb.bsc.element.Objective exists, false otherwise
       */

        public static boolean hasObjective(String id, org.semanticwb.model.SWBModel model)
        {
            return (getObjective(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByPeriod(org.semanticwb.bsc.accessory.Period value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByPeriod(org.semanticwb.bsc.accessory.Period value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Indicator
       * @param value Indicator of the type org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByIndicator(org.semanticwb.bsc.element.Indicator value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasIndicator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Indicator
       * @param value Indicator of the type org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByIndicator(org.semanticwb.bsc.element.Indicator value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasIndicator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByTheme(org.semanticwb.bsc.element.Theme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_themeInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByTheme(org.semanticwb.bsc.element.Theme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_themeInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Initiative
       * @param value Initiative of the type org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByInitiative(org.semanticwb.bsc.element.Initiative value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasInitiative, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Initiative
       * @param value Initiative of the type org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByInitiative(org.semanticwb.bsc.element.Initiative value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasInitiative,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined CausalTheme
       * @param value CausalTheme of the type org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCausalTheme(org.semanticwb.bsc.element.Theme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalTheme, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined CausalTheme
       * @param value CausalTheme of the type org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCausalTheme(org.semanticwb.bsc.element.Theme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalTheme,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined State
       * @param value State of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByState(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasState, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined State
       * @param value State of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByState(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasState,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined PeriodStatus
       * @param value PeriodStatus of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriodStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined PeriodStatus
       * @param value PeriodStatus of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriodStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined CausalObjective
       * @param value CausalObjective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCausalObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalObjective, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined CausalObjective
       * @param value CausalObjective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCausalObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalObjective,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ParentObjective
       * @param value ParentObjective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByParentObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_parentObjective, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ParentObjective
       * @param value ParentObjective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByParentObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_parentObjective,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Sponsor
       * @param value Sponsor of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveBySponsor(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_sponsor, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Sponsor
       * @param value Sponsor of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveBySponsor(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_sponsor,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Periodicity
       * @param value Periodicity of the type org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByPeriodicity(org.semanticwb.bsc.tracing.MeasurementFrequency value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_periodicity, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Periodicity
       * @param value Periodicity of the type org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByPeriodicity(org.semanticwb.bsc.tracing.MeasurementFrequency value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_periodicity,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ObjectiveBase.ClassMgr getObjectiveClassMgr()
    {
        return new ObjectiveBase.ClassMgr();
    }

   /**
   * Constructs a ObjectiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Objective
   */
    public ObjectiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.Period
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> listPeriods()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period>(getSemanticObject().listObjectProperties(bsc_hasPeriod));
    }

   /**
   * Gets true if has a Period
   * @param value org.semanticwb.bsc.accessory.Period to verify
   * @return true if the org.semanticwb.bsc.accessory.Period exists, false otherwise
   */
    public boolean hasPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasPeriod,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Period
   * @param value org.semanticwb.bsc.accessory.Period to add
   */

    public void addPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        getSemanticObject().addObjectProperty(bsc_hasPeriod, value.getSemanticObject());
    }
   /**
   * Removes all the Period
   */

    public void removeAllPeriod()
    {
        getSemanticObject().removeProperty(bsc_hasPeriod);
    }
   /**
   * Removes a Period
   * @param value org.semanticwb.bsc.accessory.Period to remove
   */

    public void removePeriod(org.semanticwb.bsc.accessory.Period value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasPeriod,value.getSemanticObject());
    }

   /**
   * Gets the Period
   * @return a org.semanticwb.bsc.accessory.Period
   */
    public org.semanticwb.bsc.accessory.Period getPeriod()
    {
         org.semanticwb.bsc.accessory.Period ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasPeriod);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.Period)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Indicator
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Indicator
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> listIndicators()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(getSemanticObject().listObjectProperties(bsc_hasIndicator));
    }

   /**
   * Gets true if has a Indicator
   * @param value org.semanticwb.bsc.element.Indicator to verify
   * @return true if the org.semanticwb.bsc.element.Indicator exists, false otherwise
   */
    public boolean hasIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasIndicator,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Indicator
   * @param value org.semanticwb.bsc.element.Indicator to add
   */

    public void addIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        getSemanticObject().addObjectProperty(bsc_hasIndicator, value.getSemanticObject());
    }
   /**
   * Removes all the Indicator
   */

    public void removeAllIndicator()
    {
        getSemanticObject().removeProperty(bsc_hasIndicator);
    }
   /**
   * Removes a Indicator
   * @param value org.semanticwb.bsc.element.Indicator to remove
   */

    public void removeIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasIndicator,value.getSemanticObject());
    }

   /**
   * Gets the Indicator
   * @return a org.semanticwb.bsc.element.Indicator
   */
    public org.semanticwb.bsc.element.Indicator getIndicator()
    {
         org.semanticwb.bsc.element.Indicator ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasIndicator);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Indicator)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Theme
   * @param value Theme to set
   */

    public void setTheme(org.semanticwb.bsc.element.Theme value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_themeInv, value.getSemanticObject());
        }else
        {
            removeTheme();
        }
    }
   /**
   * Remove the value for Theme property
   */

    public void removeTheme()
    {
        getSemanticObject().removeProperty(bsc_themeInv);
    }

   /**
   * Gets the Theme
   * @return a org.semanticwb.bsc.element.Theme
   */
    public org.semanticwb.bsc.element.Theme getTheme()
    {
         org.semanticwb.bsc.element.Theme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_themeInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Theme)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Initiative
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Initiative
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> listInitiatives()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative>(getSemanticObject().listObjectProperties(bsc_hasInitiative));
    }

   /**
   * Gets true if has a Initiative
   * @param value org.semanticwb.bsc.element.Initiative to verify
   * @return true if the org.semanticwb.bsc.element.Initiative exists, false otherwise
   */
    public boolean hasInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasInitiative,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Initiative
   * @param value org.semanticwb.bsc.element.Initiative to add
   */

    public void addInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        getSemanticObject().addObjectProperty(bsc_hasInitiative, value.getSemanticObject());
    }
   /**
   * Removes all the Initiative
   */

    public void removeAllInitiative()
    {
        getSemanticObject().removeProperty(bsc_hasInitiative);
    }
   /**
   * Removes a Initiative
   * @param value org.semanticwb.bsc.element.Initiative to remove
   */

    public void removeInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasInitiative,value.getSemanticObject());
    }

   /**
   * Gets the Initiative
   * @return a org.semanticwb.bsc.element.Initiative
   */
    public org.semanticwb.bsc.element.Initiative getInitiative()
    {
         org.semanticwb.bsc.element.Initiative ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasInitiative);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Initiative)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Theme
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Theme
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> listCausalThemes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme>(getSemanticObject().listObjectProperties(bsc_hasCausalTheme));
    }

   /**
   * Gets true if has a CausalTheme
   * @param value org.semanticwb.bsc.element.Theme to verify
   * @return true if the org.semanticwb.bsc.element.Theme exists, false otherwise
   */
    public boolean hasCausalTheme(org.semanticwb.bsc.element.Theme value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasCausalTheme,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CausalTheme
   * @param value org.semanticwb.bsc.element.Theme to add
   */

    public void addCausalTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().addObjectProperty(bsc_hasCausalTheme, value.getSemanticObject());
    }
   /**
   * Removes all the CausalTheme
   */

    public void removeAllCausalTheme()
    {
        getSemanticObject().removeProperty(bsc_hasCausalTheme);
    }
   /**
   * Removes a CausalTheme
   * @param value org.semanticwb.bsc.element.Theme to remove
   */

    public void removeCausalTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasCausalTheme,value.getSemanticObject());
    }

   /**
   * Gets the CausalTheme
   * @return a org.semanticwb.bsc.element.Theme
   */
    public org.semanticwb.bsc.element.Theme getCausalTheme()
    {
         org.semanticwb.bsc.element.Theme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasCausalTheme);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Theme)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Priority property
* @return String with the Priority
*/
    public String getPriority()
    {
        return getSemanticObject().getProperty(bsc_priority);
    }

/**
* Sets the Priority property
* @param value long with the Priority
*/
    public void setPriority(String value)
    {
        getSemanticObject().setProperty(bsc_priority, value);
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
   * Gets all the org.semanticwb.bsc.tracing.PeriodStatus
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.PeriodStatus
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatuses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus>(getSemanticObject().listObjectProperties(bsc_hasPeriodStatus));
    }

   /**
   * Gets true if has a PeriodStatus
   * @param value org.semanticwb.bsc.tracing.PeriodStatus to verify
   * @return true if the org.semanticwb.bsc.tracing.PeriodStatus exists, false otherwise
   */
    public boolean hasPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasPeriodStatus,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PeriodStatus
   * @param value org.semanticwb.bsc.tracing.PeriodStatus to add
   */

    public void addPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value)
    {
        getSemanticObject().addObjectProperty(bsc_hasPeriodStatus, value.getSemanticObject());
    }
   /**
   * Removes all the PeriodStatus
   */

    public void removeAllPeriodStatus()
    {
        getSemanticObject().removeProperty(bsc_hasPeriodStatus);
    }
   /**
   * Removes a PeriodStatus
   * @param value org.semanticwb.bsc.tracing.PeriodStatus to remove
   */

    public void removePeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasPeriodStatus,value.getSemanticObject());
    }

   /**
   * Gets the PeriodStatus
   * @return a org.semanticwb.bsc.tracing.PeriodStatus
   */
    public org.semanticwb.bsc.tracing.PeriodStatus getPeriodStatus()
    {
         org.semanticwb.bsc.tracing.PeriodStatus ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasPeriodStatus);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.PeriodStatus)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Objective object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Objective object
        getSemanticObject().setProperty(bsc_prefix, value,false);
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
   * Gets all the org.semanticwb.bsc.element.Objective
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Objective
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> listCausalObjectives()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(getSemanticObject().listObjectProperties(bsc_hasCausalObjective));
    }

   /**
   * Gets true if has a CausalObjective
   * @param value org.semanticwb.bsc.element.Objective to verify
   * @return true if the org.semanticwb.bsc.element.Objective exists, false otherwise
   */
    public boolean hasCausalObjective(org.semanticwb.bsc.element.Objective value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasCausalObjective,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CausalObjective
   * @param value org.semanticwb.bsc.element.Objective to add
   */

    public void addCausalObjective(org.semanticwb.bsc.element.Objective value)
    {
        getSemanticObject().addObjectProperty(bsc_hasCausalObjective, value.getSemanticObject());
    }
   /**
   * Removes all the CausalObjective
   */

    public void removeAllCausalObjective()
    {
        getSemanticObject().removeProperty(bsc_hasCausalObjective);
    }
   /**
   * Removes a CausalObjective
   * @param value org.semanticwb.bsc.element.Objective to remove
   */

    public void removeCausalObjective(org.semanticwb.bsc.element.Objective value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasCausalObjective,value.getSemanticObject());
    }

   /**
   * Gets the CausalObjective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getCausalObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasCausalObjective);
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
   * Sets the value for the property ParentObjective
   * @param value ParentObjective to set
   */

    public void setParentObjective(org.semanticwb.bsc.element.Objective value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_parentObjective, value.getSemanticObject());
        }else
        {
            removeParentObjective();
        }
    }
   /**
   * Remove the value for ParentObjective property
   */

    public void removeParentObjective()
    {
        getSemanticObject().removeProperty(bsc_parentObjective);
    }

   /**
   * Gets the ParentObjective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getParentObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_parentObjective);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Objective)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Sponsor
   * @param value Sponsor to set
   */

    public void setSponsor(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_sponsor, value.getSemanticObject());
        }else
        {
            removeSponsor();
        }
    }
   /**
   * Remove the value for Sponsor property
   */

    public void removeSponsor()
    {
        getSemanticObject().removeProperty(bsc_sponsor);
    }

   /**
   * Gets the Sponsor
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getSponsor()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_sponsor);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Periodicity
   * @param value Periodicity to set
   */

    public void setPeriodicity(org.semanticwb.bsc.tracing.MeasurementFrequency value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_periodicity, value.getSemanticObject());
        }else
        {
            removePeriodicity();
        }
    }
   /**
   * Remove the value for Periodicity property
   */

    public void removePeriodicity()
    {
        getSemanticObject().removeProperty(bsc_periodicity);
    }

   /**
   * Gets the Periodicity
   * @return a org.semanticwb.bsc.tracing.MeasurementFrequency
   */
    public org.semanticwb.bsc.tracing.MeasurementFrequency getPeriodicity()
    {
         org.semanticwb.bsc.tracing.MeasurementFrequency ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_periodicity);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.MeasurementFrequency)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Serial property
* @return int with the Serial
*/
    public int getSerial()
    {
        return getSemanticObject().getIntProperty(bsc_serial);
    }

/**
* Sets the Serial property
* @param value long with the Serial
*/
    public void setSerial(int value)
    {
        getSemanticObject().setIntProperty(bsc_serial, value);
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
