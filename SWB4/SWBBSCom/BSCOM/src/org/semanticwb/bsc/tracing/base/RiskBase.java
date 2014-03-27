package org.semanticwb.bsc.tracing.base;


   /**
   * Define un riesgo que puede presentarse mediante un elemento del BSC: Objetivo, Entregable, Iniciativa o Indicador. Un riesgo tambien puede presentarse independientemente. 
   */
public abstract class RiskBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.bsc.MitigationActionAssignable,org.semanticwb.model.Traceable,org.semanticwb.bsc.MitigationInitiativeAssignable,org.semanticwb.bsc.DepartmentOrganizable,org.semanticwb.bsc.Recognizable,org.semanticwb.model.Roleable,org.semanticwb.bsc.Help,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupable
{
   /**
   * Representa un archivo físico utilizado a manera de evidencia sobre la realización de alguna actividad.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Deliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");
    public static final org.semanticwb.platform.SemanticProperty bsc_deliverableInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#deliverableInv");
   /**
   * Permite seleccionar una alineación. Los posibles valores son Estrategia, Objetivo y Meta
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_selectingAlignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#selectingAlignment");
   /**
   * Define mediante una descripción la selección de alineación
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_selectingAlignmentDescr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#selectingAlignmentDescr");
   /**
   * La escala de valor deberá relacionar con los factores de riesgos señalados. Los riesgos deben evaluarse en una escala de valor del 1 al 10
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_finAssessmentLikelihood=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#finAssessmentLikelihood");
   /**
   * Utilizado para relacionar el año de creación del riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_yearRisk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#yearRisk");
   /**
   * Define la clasificación de un Riesgo.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_classificationRisk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#classificationRisk");
    public static final org.semanticwb.platform.SemanticClass bsc_BSCElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");
    public static final org.semanticwb.platform.SemanticProperty bsc_elementInstanceRelated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#elementInstanceRelated");
   /**
   * Define los posibles efectos del Riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_possibleEffectsRisk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#possibleEffectsRisk");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Define el responsable de un riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_riskResponsible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#riskResponsible");
   /**
   * Son las opciones para administrar cada Riesgo identificado, basadas en su valoración respecto a controles que permiten tomar decisiones y determinar las acciones de control
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_stratManageRisk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#stratManageRisk");
   /**
   * Define si un riesgo es controlado suficientemente dependiendo si existen controles para cada factor y si cada uno de los Controles son Suficientes
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_riskControlledSufficiently=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#riskControlledSufficiently");
   /**
   * Define el nivel de desición del Riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_riskLeveldecision=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#riskLeveldecision");
   /**
   * En caso de que ninguna clasificación del Riesgo corresponda al Riesgo definido, este campo permite especifica otra clasficación mediante su descripción
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_classifRiskSpecifyOther=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#classifRiskSpecifyOther");
   /**
   * Se evalúa en función de la magnitud de los efectos identificados y registrados en el apartado de, en caso de materializarse el Riesgo (10 al de mayor y 1 al de menor magnitud).
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_finAssessmentImpactLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#finAssessmentImpactLevel");
   /**
   * La escala de valor deberá relacionar con los factores de riesgos señalados. Los riesgos deben evaluarse en una escala de valor del 1 al 10
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_iniAssessmentLikelihood=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#iniAssessmentLikelihood");
   /**
   * Elemento BSC(Entregable, Objetivo, Iniciativa o Indicador) relacionado a un riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_elementRelated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#elementRelated");
   /**
   * Se evalúa en función de la magnitud de los efectos identificados y registrados en el apartado de, en caso de materializarse el Riesgo (10 al de mayor y 1 al de menor magnitud).
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_iniAssessmentImpactLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#iniAssessmentImpactLevel");
   /**
   * Clase que define un factor de riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Factor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Factor");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasFactor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasFactor");
   /**
   * Define un riesgo que puede presentarse mediante un elemento del BSC: Objetivo, Entregable, Iniciativa o Indicador. Un riesgo tambien puede presentarse independientemente.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Risk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");

    public static class ClassMgr
    {
       /**
       * Returns a list of Risk for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRisks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Risk for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRisks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Risk createRisk(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Risk.ClassMgr.createRisk(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return A org.semanticwb.bsc.tracing.Risk
       */
        public static org.semanticwb.bsc.tracing.Risk getRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Risk)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return A org.semanticwb.bsc.tracing.Risk
       */
        public static org.semanticwb.bsc.tracing.Risk createRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Risk)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       */
        public static void removeRisk(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return true if the org.semanticwb.bsc.tracing.Risk exists, false otherwise
       */

        public static boolean hasRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRisk(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined DeliverableInv
       * @param value DeliverableInv of the type org.semanticwb.bsc.element.Deliverable
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByDeliverableInv(org.semanticwb.bsc.element.Deliverable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_deliverableInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined DeliverableInv
       * @param value DeliverableInv of the type org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByDeliverableInv(org.semanticwb.bsc.element.Deliverable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_deliverableInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined InitiativeRisk
       * @param value InitiativeRisk of the type org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByInitiativeRisk(org.semanticwb.bsc.element.Initiative value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasInitiativeRisk, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined InitiativeRisk
       * @param value InitiativeRisk of the type org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByInitiativeRisk(org.semanticwb.bsc.element.Initiative value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasInitiativeRisk,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined ElementInstanceRelated
       * @param value ElementInstanceRelated of the type org.semanticwb.bsc.element.BSCElement
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByElementInstanceRelated(org.semanticwb.bsc.element.BSCElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_elementInstanceRelated, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined ElementInstanceRelated
       * @param value ElementInstanceRelated of the type org.semanticwb.bsc.element.BSCElement
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByElementInstanceRelated(org.semanticwb.bsc.element.BSCElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_elementInstanceRelated,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined RiskResponsible
       * @param value RiskResponsible of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByRiskResponsible(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_riskResponsible, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined RiskResponsible
       * @param value RiskResponsible of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByRiskResponsible(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_riskResponsible,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined MitigationAction
       * @param value MitigationAction of the type org.semanticwb.bsc.tracing.MitigationAction
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMitigationAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined MitigationAction
       * @param value MitigationAction of the type org.semanticwb.bsc.tracing.MitigationAction
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMitigationAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Factor
       * @param value Factor of the type org.semanticwb.bsc.tracing.Factor
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByFactor(org.semanticwb.bsc.tracing.Factor value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasFactor, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Factor
       * @param value Factor of the type org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByFactor(org.semanticwb.bsc.tracing.Factor value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasFactor,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RiskBase.ClassMgr getRiskClassMgr()
    {
        return new RiskBase.ClassMgr();
    }

   /**
   * Constructs a RiskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Risk
   */
    public RiskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property DeliverableInv
   * @param value DeliverableInv to set
   */

    public void setDeliverableInv(org.semanticwb.bsc.element.Deliverable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_deliverableInv, value.getSemanticObject());
        }else
        {
            removeDeliverableInv();
        }
    }
   /**
   * Remove the value for DeliverableInv property
   */

    public void removeDeliverableInv()
    {
        getSemanticObject().removeProperty(bsc_deliverableInv);
    }

   /**
   * Gets the DeliverableInv
   * @return a org.semanticwb.bsc.element.Deliverable
   */
    public org.semanticwb.bsc.element.Deliverable getDeliverableInv()
    {
         org.semanticwb.bsc.element.Deliverable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_deliverableInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Deliverable)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the SelectingAlignment property
* @return String with the SelectingAlignment
*/
    public String getSelectingAlignment()
    {
        return getSemanticObject().getProperty(bsc_selectingAlignment);
    }

/**
* Sets the SelectingAlignment property
* @param value long with the SelectingAlignment
*/
    public void setSelectingAlignment(String value)
    {
        getSemanticObject().setProperty(bsc_selectingAlignment, value);
    }

/**
* Gets the SelectingAlignmentDescr property
* @return String with the SelectingAlignmentDescr
*/
    public String getSelectingAlignmentDescr()
    {
        return getSemanticObject().getProperty(bsc_selectingAlignmentDescr);
    }

/**
* Sets the SelectingAlignmentDescr property
* @param value long with the SelectingAlignmentDescr
*/
    public void setSelectingAlignmentDescr(String value)
    {
        getSemanticObject().setProperty(bsc_selectingAlignmentDescr, value);
    }

/**
* Gets the FinAssessmentLikelihood property
* @return int with the FinAssessmentLikelihood
*/
    public int getFinAssessmentLikelihood()
    {
        return getSemanticObject().getIntProperty(bsc_finAssessmentLikelihood);
    }

/**
* Sets the FinAssessmentLikelihood property
* @param value long with the FinAssessmentLikelihood
*/
    public void setFinAssessmentLikelihood(int value)
    {
        getSemanticObject().setIntProperty(bsc_finAssessmentLikelihood, value);
    }

/**
* Gets the YearRisk property
* @return String with the YearRisk
*/
    public String getYearRisk()
    {
        return getSemanticObject().getProperty(bsc_yearRisk);
    }

/**
* Sets the YearRisk property
* @param value long with the YearRisk
*/
    public void setYearRisk(String value)
    {
        getSemanticObject().setProperty(bsc_yearRisk, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Initiative
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Initiative
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> listInitiativeRisks()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative>(getSemanticObject().listObjectProperties(bsc_hasInitiativeRisk));
    }

   /**
   * Gets true if has a InitiativeRisk
   * @param value org.semanticwb.bsc.element.Initiative to verify
   * @return true if the org.semanticwb.bsc.element.Initiative exists, false otherwise
   */
    public boolean hasInitiativeRisk(org.semanticwb.bsc.element.Initiative value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasInitiativeRisk,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a InitiativeRisk
   * @param value org.semanticwb.bsc.element.Initiative to add
   */

    public void addInitiativeRisk(org.semanticwb.bsc.element.Initiative value)
    {
        getSemanticObject().addObjectProperty(bsc_hasInitiativeRisk, value.getSemanticObject());
    }
   /**
   * Removes all the InitiativeRisk
   */

    public void removeAllInitiativeRisk()
    {
        getSemanticObject().removeProperty(bsc_hasInitiativeRisk);
    }
   /**
   * Removes a InitiativeRisk
   * @param value org.semanticwb.bsc.element.Initiative to remove
   */

    public void removeInitiativeRisk(org.semanticwb.bsc.element.Initiative value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasInitiativeRisk,value.getSemanticObject());
    }

   /**
   * Gets the InitiativeRisk
   * @return a org.semanticwb.bsc.element.Initiative
   */
    public org.semanticwb.bsc.element.Initiative getInitiativeRisk()
    {
         org.semanticwb.bsc.element.Initiative ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasInitiativeRisk);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Initiative)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Risk object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Risk object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }

/**
* Gets the ClassificationRisk property
* @return String with the ClassificationRisk
*/
    public String getClassificationRisk()
    {
        return getSemanticObject().getProperty(bsc_classificationRisk);
    }

/**
* Sets the ClassificationRisk property
* @param value long with the ClassificationRisk
*/
    public void setClassificationRisk(String value)
    {
        getSemanticObject().setProperty(bsc_classificationRisk, value);
    }
   /**
   * Sets the value for the property ElementInstanceRelated
   * @param value ElementInstanceRelated to set
   */

    public void setElementInstanceRelated(org.semanticwb.bsc.element.BSCElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_elementInstanceRelated, value.getSemanticObject());
        }else
        {
            removeElementInstanceRelated();
        }
    }
   /**
   * Remove the value for ElementInstanceRelated property
   */

    public void removeElementInstanceRelated()
    {
        getSemanticObject().removeProperty(bsc_elementInstanceRelated);
    }

   /**
   * Gets the ElementInstanceRelated
   * @return a org.semanticwb.bsc.element.BSCElement
   */
    public org.semanticwb.bsc.element.BSCElement getElementInstanceRelated()
    {
         org.semanticwb.bsc.element.BSCElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_elementInstanceRelated);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.BSCElement)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PossibleEffectsRisk property
* @return String with the PossibleEffectsRisk
*/
    public String getPossibleEffectsRisk()
    {
        return getSemanticObject().getProperty(bsc_possibleEffectsRisk);
    }

/**
* Sets the PossibleEffectsRisk property
* @param value long with the PossibleEffectsRisk
*/
    public void setPossibleEffectsRisk(String value)
    {
        getSemanticObject().setProperty(bsc_possibleEffectsRisk, value);
    }
   /**
   * Sets the value for the property RiskResponsible
   * @param value RiskResponsible to set
   */

    public void setRiskResponsible(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_riskResponsible, value.getSemanticObject());
        }else
        {
            removeRiskResponsible();
        }
    }
   /**
   * Remove the value for RiskResponsible property
   */

    public void removeRiskResponsible()
    {
        getSemanticObject().removeProperty(bsc_riskResponsible);
    }

   /**
   * Gets the RiskResponsible
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getRiskResponsible()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_riskResponsible);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the StratManageRisk property
* @return String with the StratManageRisk
*/
    public String getStratManageRisk()
    {
        return getSemanticObject().getProperty(bsc_stratManageRisk);
    }

/**
* Sets the StratManageRisk property
* @param value long with the StratManageRisk
*/
    public void setStratManageRisk(String value)
    {
        getSemanticObject().setProperty(bsc_stratManageRisk, value);
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
* Gets the RiskControlledSufficiently property
* @return String with the RiskControlledSufficiently
*/
    public String getRiskControlledSufficiently()
    {
        return getSemanticObject().getProperty(bsc_riskControlledSufficiently);
    }

/**
* Sets the RiskControlledSufficiently property
* @param value long with the RiskControlledSufficiently
*/
    public void setRiskControlledSufficiently(String value)
    {
        getSemanticObject().setProperty(bsc_riskControlledSufficiently, value);
    }

/**
* Gets the RiskLeveldecision property
* @return String with the RiskLeveldecision
*/
    public String getRiskLeveldecision()
    {
        return getSemanticObject().getProperty(bsc_riskLeveldecision);
    }

/**
* Sets the RiskLeveldecision property
* @param value long with the RiskLeveldecision
*/
    public void setRiskLeveldecision(String value)
    {
        getSemanticObject().setProperty(bsc_riskLeveldecision, value);
    }

/**
* Gets the ClassifRiskSpecifyOther property
* @return String with the ClassifRiskSpecifyOther
*/
    public String getClassifRiskSpecifyOther()
    {
        return getSemanticObject().getProperty(bsc_classifRiskSpecifyOther);
    }

/**
* Sets the ClassifRiskSpecifyOther property
* @param value long with the ClassifRiskSpecifyOther
*/
    public void setClassifRiskSpecifyOther(String value)
    {
        getSemanticObject().setProperty(bsc_classifRiskSpecifyOther, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.tracing.MitigationAction
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.MitigationAction
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction>(getSemanticObject().listObjectProperties(bsc_hasMitigationAction));
    }

   /**
   * Gets true if has a MitigationAction
   * @param value org.semanticwb.bsc.tracing.MitigationAction to verify
   * @return true if the org.semanticwb.bsc.tracing.MitigationAction exists, false otherwise
   */
    public boolean hasMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasMitigationAction,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a MitigationAction
   * @param value org.semanticwb.bsc.tracing.MitigationAction to add
   */

    public void addMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value)
    {
        getSemanticObject().addObjectProperty(bsc_hasMitigationAction, value.getSemanticObject());
    }
   /**
   * Removes all the MitigationAction
   */

    public void removeAllMitigationAction()
    {
        getSemanticObject().removeProperty(bsc_hasMitigationAction);
    }
   /**
   * Removes a MitigationAction
   * @param value org.semanticwb.bsc.tracing.MitigationAction to remove
   */

    public void removeMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasMitigationAction,value.getSemanticObject());
    }

   /**
   * Gets the MitigationAction
   * @return a org.semanticwb.bsc.tracing.MitigationAction
   */
    public org.semanticwb.bsc.tracing.MitigationAction getMitigationAction()
    {
         org.semanticwb.bsc.tracing.MitigationAction ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasMitigationAction);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.MitigationAction)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the FinAssessmentImpactLevel property
* @return int with the FinAssessmentImpactLevel
*/
    public int getFinAssessmentImpactLevel()
    {
        return getSemanticObject().getIntProperty(bsc_finAssessmentImpactLevel);
    }

/**
* Sets the FinAssessmentImpactLevel property
* @param value long with the FinAssessmentImpactLevel
*/
    public void setFinAssessmentImpactLevel(int value)
    {
        getSemanticObject().setIntProperty(bsc_finAssessmentImpactLevel, value);
    }

/**
* Gets the IniAssessmentLikelihood property
* @return int with the IniAssessmentLikelihood
*/
    public int getIniAssessmentLikelihood()
    {
        return getSemanticObject().getIntProperty(bsc_iniAssessmentLikelihood);
    }

/**
* Sets the IniAssessmentLikelihood property
* @param value long with the IniAssessmentLikelihood
*/
    public void setIniAssessmentLikelihood(int value)
    {
        getSemanticObject().setIntProperty(bsc_iniAssessmentLikelihood, value);
    }

/**
* Gets the ElementRelated property
* @return String with the ElementRelated
*/
    public String getElementRelated()
    {
        return getSemanticObject().getProperty(bsc_elementRelated);
    }

/**
* Sets the ElementRelated property
* @param value long with the ElementRelated
*/
    public void setElementRelated(String value)
    {
        getSemanticObject().setProperty(bsc_elementRelated, value);
    }

/**
* Gets the IniAssessmentImpactLevel property
* @return int with the IniAssessmentImpactLevel
*/
    public int getIniAssessmentImpactLevel()
    {
        return getSemanticObject().getIntProperty(bsc_iniAssessmentImpactLevel);
    }

/**
* Sets the IniAssessmentImpactLevel property
* @param value long with the IniAssessmentImpactLevel
*/
    public void setIniAssessmentImpactLevel(int value)
    {
        getSemanticObject().setIntProperty(bsc_iniAssessmentImpactLevel, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.tracing.Factor
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.Factor
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> listFactors()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor>(getSemanticObject().listObjectProperties(bsc_hasFactor));
    }

   /**
   * Gets true if has a Factor
   * @param value org.semanticwb.bsc.tracing.Factor to verify
   * @return true if the org.semanticwb.bsc.tracing.Factor exists, false otherwise
   */
    public boolean hasFactor(org.semanticwb.bsc.tracing.Factor value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasFactor,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Factor
   * @param value org.semanticwb.bsc.tracing.Factor to add
   */

    public void addFactor(org.semanticwb.bsc.tracing.Factor value)
    {
        getSemanticObject().addObjectProperty(bsc_hasFactor, value.getSemanticObject());
    }
   /**
   * Removes all the Factor
   */

    public void removeAllFactor()
    {
        getSemanticObject().removeProperty(bsc_hasFactor);
    }
   /**
   * Removes a Factor
   * @param value org.semanticwb.bsc.tracing.Factor to remove
   */

    public void removeFactor(org.semanticwb.bsc.tracing.Factor value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasFactor,value.getSemanticObject());
    }

   /**
   * Gets the Factor
   * @return a org.semanticwb.bsc.tracing.Factor
   */
    public org.semanticwb.bsc.tracing.Factor getFactor()
    {
         org.semanticwb.bsc.tracing.Factor ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasFactor);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Factor)obj.createGenericInstance();
         }
         return ret;
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
