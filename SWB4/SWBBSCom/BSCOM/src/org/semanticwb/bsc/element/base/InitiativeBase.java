package org.semanticwb.bsc.element.base;


   /**
   * Define las características de una Iniciativa 
   */
public abstract class InitiativeBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.bsc.Seasonable,org.semanticwb.model.Roleable,org.semanticwb.bsc.DepartmentOrganizable,org.semanticwb.model.Referensable,org.semanticwb.model.FilterableClass,org.semanticwb.bsc.PeriodStatusAssignable,org.semanticwb.bsc.StatusManuallyAssignable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help,org.semanticwb.bsc.Attachmentable,org.semanticwb.model.Traceable,org.semanticwb.bsc.Schedule,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Status,org.semanticwb.bsc.Updateable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_totalInvestment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#totalInvestment");
    public static final org.semanticwb.platform.SemanticProperty bsc_businessCase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#businessCase");
    public static final org.semanticwb.platform.SemanticProperty bsc_percentageProgress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#percentageProgress");
   /**
   * Define que puede tener iniciativas asignadas
   */
    public static final org.semanticwb.platform.SemanticClass bsc_InitiativeAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#InitiativeAssignable");
    public static final org.semanticwb.platform.SemanticProperty bsc_initiativeAssignableInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#initiativeAssignableInv");
   /**
   * Representa un archivo físico utilizado a manera de evidencia sobre la realización de alguna actividad.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Deliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");
   /**
   * Lista de los entregables asociados a la iniciativa
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDeliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDeliverable");
    public static final org.semanticwb.platform.SemanticProperty bsc_estimatedStart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#estimatedStart");
    public static final org.semanticwb.platform.SemanticProperty bsc_estimatedEnd=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#estimatedEnd");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty bsc_initiativeFacilitator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#initiativeFacilitator");
    public static final org.semanticwb.platform.SemanticProperty bsc_investmentAmount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#investmentAmount");
   /**
   * Define las características de una Iniciativa
   */
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
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByPeriod(org.semanticwb.bsc.accessory.Period value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByPeriod(org.semanticwb.bsc.accessory.Period value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod,value.getSemanticObject(),sclass));
            return it;
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
       * Gets all org.semanticwb.bsc.element.Initiative with a determined InitiativeAssignable
       * @param value InitiativeAssignable of the type org.semanticwb.bsc.InitiativeAssignable
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByInitiativeAssignable(org.semanticwb.bsc.InitiativeAssignable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_initiativeAssignableInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined InitiativeAssignable
       * @param value InitiativeAssignable of the type org.semanticwb.bsc.InitiativeAssignable
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByInitiativeAssignable(org.semanticwb.bsc.InitiativeAssignable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_initiativeAssignableInv,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.bsc.element.Initiative with a determined PeriodStatus
       * @param value PeriodStatus of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriodStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined PeriodStatus
       * @param value PeriodStatus of the type org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriodStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Attachments
       * @param value Attachments of the type org.semanticwb.bsc.catalogs.Attachment
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByAttachments(org.semanticwb.bsc.catalogs.Attachment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasAttachments, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Attachments
       * @param value Attachments of the type org.semanticwb.bsc.catalogs.Attachment
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByAttachments(org.semanticwb.bsc.catalogs.Attachment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasAttachments,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
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
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined StatusAssigned
       * @param value StatusAssigned of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByStatusAssigned(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_statusAssigned, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined StatusAssigned
       * @param value StatusAssigned of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByStatusAssigned(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_statusAssigned,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined InitiativeFacilitator
       * @param value InitiativeFacilitator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByInitiativeFacilitator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_initiativeFacilitator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Initiative with a determined InitiativeFacilitator
       * @param value InitiativeFacilitator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Initiative
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiativeByInitiativeFacilitator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_initiativeFacilitator,value.getSemanticObject(),sclass));
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
* @return boolean with the BusinessCase
*/
    public boolean isBusinessCase()
    {
        return getSemanticObject().getBooleanProperty(bsc_businessCase);
    }

/**
* Sets the BusinessCase property
* @param value long with the BusinessCase
*/
    public void setBusinessCase(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_businessCase, value);
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
   * Sets the value for the property InitiativeAssignable
   * @param value InitiativeAssignable to set
   */

    public void setInitiativeAssignable(org.semanticwb.bsc.InitiativeAssignable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_initiativeAssignableInv, value.getSemanticObject());
        }else
        {
            removeInitiativeAssignable();
        }
    }
   /**
   * Remove the value for InitiativeAssignable property
   */

    public void removeInitiativeAssignable()
    {
        getSemanticObject().removeProperty(bsc_initiativeAssignableInv);
    }

   /**
   * Gets the InitiativeAssignable
   * @return a org.semanticwb.bsc.InitiativeAssignable
   */
    public org.semanticwb.bsc.InitiativeAssignable getInitiativeAssignable()
    {
         org.semanticwb.bsc.InitiativeAssignable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_initiativeAssignableInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.InitiativeAssignable)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.bsc.catalogs.Attachment
   * @return A GenericIterator with all the org.semanticwb.bsc.catalogs.Attachment
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment>(getSemanticObject().listObjectProperties(bsc_hasAttachments));
    }

   /**
   * Gets true if has a Attachments
   * @param value org.semanticwb.bsc.catalogs.Attachment to verify
   * @return true if the org.semanticwb.bsc.catalogs.Attachment exists, false otherwise
   */
    public boolean hasAttachments(org.semanticwb.bsc.catalogs.Attachment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasAttachments,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Attachments
   * @param value org.semanticwb.bsc.catalogs.Attachment to add
   */

    public void addAttachments(org.semanticwb.bsc.catalogs.Attachment value)
    {
        getSemanticObject().addObjectProperty(bsc_hasAttachments, value.getSemanticObject());
    }
   /**
   * Removes all the Attachments
   */

    public void removeAllAttachments()
    {
        getSemanticObject().removeProperty(bsc_hasAttachments);
    }
   /**
   * Removes a Attachments
   * @param value org.semanticwb.bsc.catalogs.Attachment to remove
   */

    public void removeAttachments(org.semanticwb.bsc.catalogs.Attachment value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasAttachments,value.getSemanticObject());
    }

   /**
   * Gets the Attachments
   * @return a org.semanticwb.bsc.catalogs.Attachment
   */
    public org.semanticwb.bsc.catalogs.Attachment getAttachments()
    {
         org.semanticwb.bsc.catalogs.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.catalogs.Attachment)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property StatusAssigned
   * @param value StatusAssigned to set
   */

    public void setStatusAssigned(org.semanticwb.bsc.accessory.State value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_statusAssigned, value.getSemanticObject());
        }else
        {
            removeStatusAssigned();
        }
    }
   /**
   * Remove the value for StatusAssigned property
   */

    public void removeStatusAssigned()
    {
        getSemanticObject().removeProperty(bsc_statusAssigned);
    }

   /**
   * Gets the StatusAssigned
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getStatusAssigned()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_statusAssigned);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property InitiativeFacilitator
   * @param value InitiativeFacilitator to set
   */

    public void setInitiativeFacilitator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_initiativeFacilitator, value.getSemanticObject());
        }else
        {
            removeInitiativeFacilitator();
        }
    }
   /**
   * Remove the value for InitiativeFacilitator property
   */

    public void removeInitiativeFacilitator()
    {
        getSemanticObject().removeProperty(bsc_initiativeFacilitator);
    }

   /**
   * Gets the InitiativeFacilitator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getInitiativeFacilitator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_initiativeFacilitator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
