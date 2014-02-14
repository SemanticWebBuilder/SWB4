package org.semanticwb.bsc.base;


   /**
   * Modelo que define un Scorecard de la metodologia BalancedScorecard de Norton y Kaplan 
   */
public abstract class BSCBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Localeable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.bsc.Help,org.semanticwb.model.FilterableNode,org.semanticwb.model.Indexable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Activeable,org.semanticwb.model.Countryable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass
{
    public static final org.semanticwb.platform.SemanticProperty bsc_values=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#values");
   /**
   * Modelo que define un Scorecard de la metodologia BalancedScorecard de Norton y Kaplan
   */
    public static final org.semanticwb.platform.SemanticClass bsc_BSC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSC");
    public static final org.semanticwb.platform.SemanticProperty bsc_parent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#parent");
    public static final org.semanticwb.platform.SemanticProperty bsc_logo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#logo");
    public static final org.semanticwb.platform.SemanticProperty bsc_vision=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#vision");
    public static final org.semanticwb.platform.SemanticProperty bsc_mission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#mission");
   /**
   * Define una Collección de objetos de una clase especificada con la propiedad "collectionClass"
   */
    public static final org.semanticwb.platform.SemanticClass swb_Collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");
   /**
   * Referencia a un objeto de tipo Rule
   */
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    public static final org.semanticwb.platform.SemanticClass bsc_Operation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Operation");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
   /**
   * Objeto por medio del cual se define un tipo de componente o recurso
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
   /**
   * Período de medición.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");
   /**
   * Clase que define el valor de un estado en un periodo. Ejemplo: Para el periodo "Enero 2013" - Estado "En Riesgo"
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");
    public static final org.semanticwb.platform.SemanticClass bsc_Perspective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");
   /**
   * Objeto que define una agripacion de componentes o recursos de un mismo tipo
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
   /**
   * Referencia a un objeto de tipo Role
   */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
   /**
   * Catalogo de paises
   */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
   /**
   * Clase que define un factor de riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Factor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Factor");
   /**
   * Persiste los atributos de un indicador
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
   /**
   * Gestiona la información de un control en un Riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Control=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Control");
   /**
   * Las frecuencias de medición, definen bloques de períodos para determinar cuándo se requiere la captura de información. Frecuencia de medición.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MeasurementFrequency=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MeasurementFrequency");
   /**
   * Un recurso es un componente en una Página Web con el cual el usuario tiene interacción
   */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
   /**
   * Define un riesgo que puede presentarse mediante un elemento del BSC: Objetivo, Entregable, Iniciativa o Indicador. Un riesgo tambien puede presentarse independientemente.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Risk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");
   /**
   * Persiste la información de una Sesión. Existen  dos tipos de sesiones: RAE y NOA
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Meeting=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Meeting");
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
   /**
   * Define las características de un Acuerdo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Agreement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Agreement");
   /**
   * Objeto que define una regla de negocio, utilizando los atributos del usuario para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
   /**
   * Referencia a un objeto de tipo Resource
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSC");

    public static class ClassMgr
    {
       /**
       * Returns a list of BSC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.BSC for all models
       * @return Iterator of org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       * @return A org.semanticwb.bsc.BSC
       */
        public static org.semanticwb.bsc.BSC getBSC(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.bsc.BSC ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.bsc.BSC)
                    {
                        ret=(org.semanticwb.bsc.BSC)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       * @return A org.semanticwb.bsc.BSC
       */
        public static org.semanticwb.bsc.BSC createBSC(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.bsc.BSC)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       */
        public static void removeBSC(String id)
        {
            org.semanticwb.bsc.BSC obj=getBSC(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       * @return true if the org.semanticwb.bsc.BSC exists, false otherwise
       */

        public static boolean hasBSC(String id)
        {
            return (getBSC(id)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Parent
       * @param value Parent of the type org.semanticwb.bsc.BSC
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParent(org.semanticwb.bsc.BSC value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Parent
       * @param value Parent of the type org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParent(org.semanticwb.bsc.BSC value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static BSCBase.ClassMgr getBSCClassMgr()
    {
        return new BSCBase.ClassMgr();
    }

   /**
   * Constructs a BSCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BSC
   */
    public BSCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Help property
* @return String with the Help
*/
    public String getHelp()
    {
        return getSemanticObject().getProperty(bsc_help);
    }

/**
* Sets the Help property
* @param value long with the Help
*/
    public void setHelp(String value)
    {
        getSemanticObject().setProperty(bsc_help, value);
    }

/**
* Gets the Values property
* @return String with the Values
*/
    public String getValues()
    {
        return getSemanticObject().getProperty(bsc_values);
    }

/**
* Sets the Values property
* @param value long with the Values
*/
    public void setValues(String value)
    {
        getSemanticObject().setProperty(bsc_values, value);
    }

    public String getValues(String lang)
    {
        return getSemanticObject().getProperty(bsc_values, null, lang);
    }

    public String getDisplayValues(String lang)
    {
        return getSemanticObject().getLocaleProperty(bsc_values, lang);
    }

    public void setValues(String values, String lang)
    {
        getSemanticObject().setProperty(bsc_values, values, lang);
    }
   /**
   * Sets the value for the property Parent
   * @param value Parent to set
   */

    public void setParent(org.semanticwb.bsc.BSC value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_parent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }
   /**
   * Remove the value for Parent property
   */

    public void removeParent()
    {
        getSemanticObject().removeProperty(bsc_parent);
    }

   /**
   * Gets the Parent
   * @return a org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getParent()
    {
         org.semanticwb.bsc.BSC ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_parent);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.BSC)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Logo property
* @return String with the Logo
*/
    public String getLogo()
    {
        return getSemanticObject().getProperty(bsc_logo);
    }

/**
* Sets the Logo property
* @param value long with the Logo
*/
    public void setLogo(String value)
    {
        getSemanticObject().setProperty(bsc_logo, value);
    }

/**
* Gets the Vision property
* @return String with the Vision
*/
    public String getVision()
    {
        return getSemanticObject().getProperty(bsc_vision);
    }

/**
* Sets the Vision property
* @param value long with the Vision
*/
    public void setVision(String value)
    {
        getSemanticObject().setProperty(bsc_vision, value);
    }

    public String getVision(String lang)
    {
        return getSemanticObject().getProperty(bsc_vision, null, lang);
    }

    public String getDisplayVision(String lang)
    {
        return getSemanticObject().getLocaleProperty(bsc_vision, lang);
    }

    public void setVision(String vision, String lang)
    {
        getSemanticObject().setProperty(bsc_vision, vision, lang);
    }

/**
* Gets the Mission property
* @return String with the Mission
*/
    public String getMission()
    {
        return getSemanticObject().getProperty(bsc_mission);
    }

/**
* Sets the Mission property
* @param value long with the Mission
*/
    public void setMission(String value)
    {
        getSemanticObject().setProperty(bsc_mission, value);
    }

    public String getMission(String lang)
    {
        return getSemanticObject().getProperty(bsc_mission, null, lang);
    }

    public String getDisplayMission(String lang)
    {
        return getSemanticObject().getLocaleProperty(bsc_mission, lang);
    }

    public void setMission(String mission, String lang)
    {
        getSemanticObject().setProperty(bsc_mission, mission, lang);
    }

    public org.semanticwb.model.Collection getCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.getCollection(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Collection> listCollections()
    {
        return org.semanticwb.model.Collection.ClassMgr.listCollections(this);
    }

    public org.semanticwb.model.Collection createCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.createCollection(id,this);
    }

    public org.semanticwb.model.Collection createCollection()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Collection);
        return org.semanticwb.model.Collection.ClassMgr.createCollection(String.valueOf(id),this);
    } 

    public void removeCollection(String id)
    {
        org.semanticwb.model.Collection.ClassMgr.removeCollection(id, this);
    }
    public boolean hasCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.hasCollection(id, this);
    }

    public org.semanticwb.model.RuleRef getRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.getRuleRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return org.semanticwb.model.RuleRef.ClassMgr.listRuleRefs(this);
    }

    public org.semanticwb.model.RuleRef createRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(id,this);
    }

    public org.semanticwb.model.RuleRef createRuleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RuleRef);
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(String.valueOf(id),this);
    } 

    public void removeRuleRef(String id)
    {
        org.semanticwb.model.RuleRef.ClassMgr.removeRuleRef(id, this);
    }
    public boolean hasRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.hasRuleRef(id, this);
    }

    public org.semanticwb.bsc.catalogs.Operation getOperation(String id)
    {
        return org.semanticwb.bsc.catalogs.Operation.ClassMgr.getOperation(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.catalogs.Operation> listOperations()
    {
        return org.semanticwb.bsc.catalogs.Operation.ClassMgr.listOperations(this);
    }

    public org.semanticwb.bsc.catalogs.Operation createOperation(String id)
    {
        return org.semanticwb.bsc.catalogs.Operation.ClassMgr.createOperation(id,this);
    }

    public org.semanticwb.bsc.catalogs.Operation createOperation()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Operation);
        return org.semanticwb.bsc.catalogs.Operation.ClassMgr.createOperation(String.valueOf(id),this);
    } 

    public void removeOperation(String id)
    {
        org.semanticwb.bsc.catalogs.Operation.ClassMgr.removeOperation(id, this);
    }
    public boolean hasOperation(String id)
    {
        return org.semanticwb.bsc.catalogs.Operation.ClassMgr.hasOperation(id, this);
    }

    public org.semanticwb.bsc.element.Objective getObjective(String id)
    {
        return org.semanticwb.bsc.element.Objective.ClassMgr.getObjective(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectives()
    {
        return org.semanticwb.bsc.element.Objective.ClassMgr.listObjectives(this);
    }

    public org.semanticwb.bsc.element.Objective createObjective(String id)
    {
        return org.semanticwb.bsc.element.Objective.ClassMgr.createObjective(id,this);
    }

    public org.semanticwb.bsc.element.Objective createObjective()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Objective);
        return org.semanticwb.bsc.element.Objective.ClassMgr.createObjective(String.valueOf(id),this);
    } 

    public void removeObjective(String id)
    {
        org.semanticwb.bsc.element.Objective.ClassMgr.removeObjective(id, this);
    }
    public boolean hasObjective(String id)
    {
        return org.semanticwb.bsc.element.Objective.ClassMgr.hasObjective(id, this);
    }

    public org.semanticwb.model.ResourceType getResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.getResourceType(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
    {
        return org.semanticwb.model.ResourceType.ClassMgr.listResourceTypes(this);
    }

    public org.semanticwb.model.ResourceType createResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.createResourceType(id,this);
    }

    public void removeResourceType(String id)
    {
        org.semanticwb.model.ResourceType.ClassMgr.removeResourceType(id, this);
    }
    public boolean hasResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.hasResourceType(id, this);
    }

    public org.semanticwb.bsc.accessory.Period getPeriod(String id)
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.getPeriod(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriods()
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.listPeriods(this);
    }

    public org.semanticwb.bsc.accessory.Period createPeriod(String id)
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.createPeriod(id,this);
    }

    public org.semanticwb.bsc.accessory.Period createPeriod()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Period);
        return org.semanticwb.bsc.accessory.Period.ClassMgr.createPeriod(String.valueOf(id),this);
    } 

    public void removePeriod(String id)
    {
        org.semanticwb.bsc.accessory.Period.ClassMgr.removePeriod(id, this);
    }
    public boolean hasPeriod(String id)
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.hasPeriod(id, this);
    }

    public org.semanticwb.bsc.tracing.PeriodStatus getPeriodStatus(String id)
    {
        return org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.getPeriodStatus(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatuses()
    {
        return org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.listPeriodStatuses(this);
    }

    public org.semanticwb.bsc.tracing.PeriodStatus createPeriodStatus(String id)
    {
        return org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.createPeriodStatus(id,this);
    }

    public org.semanticwb.bsc.tracing.PeriodStatus createPeriodStatus()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_PeriodStatus);
        return org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.createPeriodStatus(String.valueOf(id),this);
    } 

    public void removePeriodStatus(String id)
    {
        org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.removePeriodStatus(id, this);
    }
    public boolean hasPeriodStatus(String id)
    {
        return org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.hasPeriodStatus(id, this);
    }

    public org.semanticwb.bsc.element.Perspective getPerspective(String id)
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.getPerspective(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectives()
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.listPerspectives(this);
    }

    public org.semanticwb.bsc.element.Perspective createPerspective(String id)
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.createPerspective(id,this);
    }

    public org.semanticwb.bsc.element.Perspective createPerspective()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Perspective);
        return org.semanticwb.bsc.element.Perspective.ClassMgr.createPerspective(String.valueOf(id),this);
    } 

    public void removePerspective(String id)
    {
        org.semanticwb.bsc.element.Perspective.ClassMgr.removePerspective(id, this);
    }
    public boolean hasPerspective(String id)
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.hasPerspective(id, this);
    }

    public org.semanticwb.model.ResourceSubType getResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.getResourceSubType(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceSubType> listResourceSubTypes()
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.listResourceSubTypes(this);
    }

    public org.semanticwb.model.ResourceSubType createResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.createResourceSubType(id,this);
    }

    public void removeResourceSubType(String id)
    {
        org.semanticwb.model.ResourceSubType.ClassMgr.removeResourceSubType(id, this);
    }
    public boolean hasResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.hasResourceSubType(id, this);
    }

    public org.semanticwb.model.RoleRef getRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.getRoleRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return org.semanticwb.model.RoleRef.ClassMgr.listRoleRefs(this);
    }

    public org.semanticwb.model.RoleRef createRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(id,this);
    }

    public org.semanticwb.model.RoleRef createRoleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RoleRef);
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id),this);
    } 

    public void removeRoleRef(String id)
    {
        org.semanticwb.model.RoleRef.ClassMgr.removeRoleRef(id, this);
    }
    public boolean hasRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.hasRoleRef(id, this);
    }

    public org.semanticwb.model.Country getCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.getCountry(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Country> listCountries()
    {
        return org.semanticwb.model.Country.ClassMgr.listCountries(this);
    }

    public org.semanticwb.model.Country createCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.createCountry(id,this);
    }

    public void removeCountry(String id)
    {
        org.semanticwb.model.Country.ClassMgr.removeCountry(id, this);
    }
    public boolean hasCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.hasCountry(id, this);
    }

    public org.semanticwb.bsc.tracing.Factor getFactor(String id)
    {
        return org.semanticwb.bsc.tracing.Factor.ClassMgr.getFactor(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactors()
    {
        return org.semanticwb.bsc.tracing.Factor.ClassMgr.listFactors(this);
    }

    public org.semanticwb.bsc.tracing.Factor createFactor(String id)
    {
        return org.semanticwb.bsc.tracing.Factor.ClassMgr.createFactor(id,this);
    }

    public org.semanticwb.bsc.tracing.Factor createFactor()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Factor);
        return org.semanticwb.bsc.tracing.Factor.ClassMgr.createFactor(String.valueOf(id),this);
    } 

    public void removeFactor(String id)
    {
        org.semanticwb.bsc.tracing.Factor.ClassMgr.removeFactor(id, this);
    }
    public boolean hasFactor(String id)
    {
        return org.semanticwb.bsc.tracing.Factor.ClassMgr.hasFactor(id, this);
    }

    public org.semanticwb.bsc.element.Indicator getIndicator(String id)
    {
        return org.semanticwb.bsc.element.Indicator.ClassMgr.getIndicator(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators()
    {
        return org.semanticwb.bsc.element.Indicator.ClassMgr.listIndicators(this);
    }

    public org.semanticwb.bsc.element.Indicator createIndicator(String id)
    {
        return org.semanticwb.bsc.element.Indicator.ClassMgr.createIndicator(id,this);
    }

    public org.semanticwb.bsc.element.Indicator createIndicator()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Indicator);
        return org.semanticwb.bsc.element.Indicator.ClassMgr.createIndicator(String.valueOf(id),this);
    } 

    public void removeIndicator(String id)
    {
        org.semanticwb.bsc.element.Indicator.ClassMgr.removeIndicator(id, this);
    }
    public boolean hasIndicator(String id)
    {
        return org.semanticwb.bsc.element.Indicator.ClassMgr.hasIndicator(id, this);
    }

    public org.semanticwb.bsc.tracing.Control getControl(String id)
    {
        return org.semanticwb.bsc.tracing.Control.ClassMgr.getControl(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControls()
    {
        return org.semanticwb.bsc.tracing.Control.ClassMgr.listControls(this);
    }

    public org.semanticwb.bsc.tracing.Control createControl(String id)
    {
        return org.semanticwb.bsc.tracing.Control.ClassMgr.createControl(id,this);
    }

    public org.semanticwb.bsc.tracing.Control createControl()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Control);
        return org.semanticwb.bsc.tracing.Control.ClassMgr.createControl(String.valueOf(id),this);
    } 

    public void removeControl(String id)
    {
        org.semanticwb.bsc.tracing.Control.ClassMgr.removeControl(id, this);
    }
    public boolean hasControl(String id)
    {
        return org.semanticwb.bsc.tracing.Control.ClassMgr.hasControl(id, this);
    }

    public org.semanticwb.bsc.tracing.MeasurementFrequency getMeasurementFrequency(String id)
    {
        return org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.getMeasurementFrequency(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencies()
    {
        return org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.listMeasurementFrequencies(this);
    }

    public org.semanticwb.bsc.tracing.MeasurementFrequency createMeasurementFrequency(String id)
    {
        return org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.createMeasurementFrequency(id,this);
    }

    public org.semanticwb.bsc.tracing.MeasurementFrequency createMeasurementFrequency()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_MeasurementFrequency);
        return org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.createMeasurementFrequency(String.valueOf(id),this);
    } 

    public void removeMeasurementFrequency(String id)
    {
        org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.removeMeasurementFrequency(id, this);
    }
    public boolean hasMeasurementFrequency(String id)
    {
        return org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.hasMeasurementFrequency(id, this);
    }

    public org.semanticwb.model.Resource getResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.getResource(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Resource> listResources()
    {
        return org.semanticwb.model.Resource.ClassMgr.listResources(this);
    }

    public org.semanticwb.model.Resource createResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.createResource(id,this);
    }

    public org.semanticwb.model.Resource createResource()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Resource);
        return org.semanticwb.model.Resource.ClassMgr.createResource(String.valueOf(id),this);
    } 

    public void removeResource(String id)
    {
        org.semanticwb.model.Resource.ClassMgr.removeResource(id, this);
    }
    public boolean hasResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.hasResource(id, this);
    }

    public org.semanticwb.bsc.tracing.Risk getRisk(String id)
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.getRisk(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRisks()
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.listRisks(this);
    }

    public org.semanticwb.bsc.tracing.Risk createRisk(String id)
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.createRisk(id,this);
    }

    public org.semanticwb.bsc.tracing.Risk createRisk()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Risk);
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.createRisk(String.valueOf(id),this);
    } 

    public void removeRisk(String id)
    {
        org.semanticwb.bsc.tracing.Risk.ClassMgr.removeRisk(id, this);
    }
    public boolean hasRisk(String id)
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.hasRisk(id, this);
    }

    public org.semanticwb.bsc.tracing.Meeting getMeeting(String id)
    {
        return org.semanticwb.bsc.tracing.Meeting.ClassMgr.getMeeting(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetings()
    {
        return org.semanticwb.bsc.tracing.Meeting.ClassMgr.listMeetings(this);
    }

    public org.semanticwb.bsc.tracing.Meeting createMeeting(String id)
    {
        return org.semanticwb.bsc.tracing.Meeting.ClassMgr.createMeeting(id,this);
    }

    public org.semanticwb.bsc.tracing.Meeting createMeeting()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Meeting);
        return org.semanticwb.bsc.tracing.Meeting.ClassMgr.createMeeting(String.valueOf(id),this);
    } 

    public void removeMeeting(String id)
    {
        org.semanticwb.bsc.tracing.Meeting.ClassMgr.removeMeeting(id, this);
    }
    public boolean hasMeeting(String id)
    {
        return org.semanticwb.bsc.tracing.Meeting.ClassMgr.hasMeeting(id, this);
    }

    public org.semanticwb.bsc.element.Initiative getInitiative(String id)
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.getInitiative(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiatives()
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.listInitiatives(this);
    }

    public org.semanticwb.bsc.element.Initiative createInitiative(String id)
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.createInitiative(id,this);
    }

    public org.semanticwb.bsc.element.Initiative createInitiative()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Initiative);
        return org.semanticwb.bsc.element.Initiative.ClassMgr.createInitiative(String.valueOf(id),this);
    } 

    public void removeInitiative(String id)
    {
        org.semanticwb.bsc.element.Initiative.ClassMgr.removeInitiative(id, this);
    }
    public boolean hasInitiative(String id)
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.hasInitiative(id, this);
    }

    public org.semanticwb.bsc.tracing.Agreement getAgreement(String id)
    {
        return org.semanticwb.bsc.tracing.Agreement.ClassMgr.getAgreement(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Agreement> listAgreements()
    {
        return org.semanticwb.bsc.tracing.Agreement.ClassMgr.listAgreements(this);
    }

    public org.semanticwb.bsc.tracing.Agreement createAgreement(String id)
    {
        return org.semanticwb.bsc.tracing.Agreement.ClassMgr.createAgreement(id,this);
    }

    public org.semanticwb.bsc.tracing.Agreement createAgreement()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Agreement);
        return org.semanticwb.bsc.tracing.Agreement.ClassMgr.createAgreement(String.valueOf(id),this);
    } 

    public void removeAgreement(String id)
    {
        org.semanticwb.bsc.tracing.Agreement.ClassMgr.removeAgreement(id, this);
    }
    public boolean hasAgreement(String id)
    {
        return org.semanticwb.bsc.tracing.Agreement.ClassMgr.hasAgreement(id, this);
    }

    public org.semanticwb.model.Rule getRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.getRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Rule> listRules()
    {
        return org.semanticwb.model.Rule.ClassMgr.listRules(this);
    }

    public org.semanticwb.model.Rule createRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.createRule(id,this);
    }

    public org.semanticwb.model.Rule createRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Rule);
        return org.semanticwb.model.Rule.ClassMgr.createRule(String.valueOf(id),this);
    } 

    public void removeRule(String id)
    {
        org.semanticwb.model.Rule.ClassMgr.removeRule(id, this);
    }
    public boolean hasRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.hasRule(id, this);
    }

    public org.semanticwb.model.ResourceRef getResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.getResourceRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.listResourceRefs(this);
    }

    public org.semanticwb.model.ResourceRef createResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(id,this);
    }

    public org.semanticwb.model.ResourceRef createResourceRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceRef);
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id),this);
    } 

    public void removeResourceRef(String id)
    {
        org.semanticwb.model.ResourceRef.ClassMgr.removeResourceRef(id, this);
    }
    public boolean hasResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.hasResourceRef(id, this);
    }
}
