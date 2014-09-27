package org.semanticwb.social.base;


   /**
   * Clase que hereda de swb:WebSite. Es un tipo de website Social. De esta manera se puede contar con todos los elementos en el arbol de navegación en la administración, y otros elementos utiles para Social Site (Marca). 
   */
public abstract class SocialSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Countryable,org.semanticwb.model.Activeable,org.semanticwb.model.Localeable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable
{
   /**
   * Número de Días antes de que se refresque información del usuario en las redes sociales
   */
    public static final org.semanticwb.platform.SemanticProperty social_numDaysToRefreshUserData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#numDaysToRefreshUserData");
   /**
   * Tipos de Licencia para SWB Socisal
   */
    public static final org.semanticwb.platform.SemanticClass social_LicenseType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#LicenseType");
   /**
   * LicenseType.
   */
    public static final org.semanticwb.platform.SemanticProperty social_licenseType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#licenseType");
   /**
   * Enviar al clasificador un determinado número de mensajes o todos los que puedan llegar por la red social de un solo golpe. Si se registra un número en este campo apareceran los mensajes mas rapidamente clasificados en la pestaña "Mensajes de entrada" de un Stream y de los socialTopic, sin embargo, esto puede hacer que se generen mayor cantidad de threads en el aplicativo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_blockofMsgToClassify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#blockofMsgToClassify");
   /**
   * Instituciones que contraten Marcas en SWB Social4Cloud
   */
    public static final org.semanticwb.platform.SemanticClass social_Institution=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Institution");
   /**
   * Institución a la cual pertenece la marca
   */
    public static final org.semanticwb.platform.SemanticProperty social_institution=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#institution");
   /**
   * Contador que indica cuantas ligas se han codificado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_counter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#counter");
   /**
   * Acción específica mediante la cual se envía un correo electrónico
   */
    public static final org.semanticwb.platform.SemanticClass social_SendEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SendEmail");
   /**
   * Catalogo de temas de un modelo (Marca)
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialTopic");
   /**
   * Clase que contendra los streams que configurados para cada usuario
   */
    public static final org.semanticwb.platform.SemanticClass social_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");
   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlow");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowInstance");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Acción específica mediante la cual se envía un mensaje por defecto a una o varias redes sociales seleccionadas
   */
    public static final org.semanticwb.platform.SemanticClass social_SendPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SendPost");
   /**
   * Referencia a un objeto de tipo SocialPFlow
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowRef");
   /**
   * Acción específica mediante la cual se marca un mensaje como prioritario. Esto en la propiedad "IsPrioritary" de un mensaje (Post).
   */
    public static final org.semanticwb.platform.SemanticClass social_MarkMsgAsPrioritary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#MarkMsgAsPrioritary");
   /**
   * Manejo de Rss en una marca
   */
    public static final org.semanticwb.platform.SemanticClass social_Rss=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Rss");
   /**
   * Clase principal para manejo de reglas en swbSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRule");
   /**
   * Clase que hereda de swb:WebSite. Es un tipo de website Social. De esta manera se puede contar con todos los elementos en el arbol de navegación en la administración, y otros elementos utiles para Social Site (Marca).
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialSite");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialSite");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialSite for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialSite for all models
       * @return Iterator of org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       * @return A org.semanticwb.social.SocialSite
       */
        public static org.semanticwb.social.SocialSite getSocialSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.social.SocialSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.social.SocialSite)
                    {
                        ret=(org.semanticwb.social.SocialSite)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       * @return A org.semanticwb.social.SocialSite
       */
        public static org.semanticwb.social.SocialSite createSocialSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.social.SocialSite)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       */
        public static void removeSocialSite(String id)
        {
            org.semanticwb.social.SocialSite obj=getSocialSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       * @return true if the org.semanticwb.social.SocialSite exists, false otherwise
       */

        public static boolean hasSocialSite(String id)
        {
            return (getSocialSite(id)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined LicenseType
       * @param value LicenseType of the type org.semanticwb.social.LicenseType
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByLicenseType(org.semanticwb.social.LicenseType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_licenseType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined LicenseType
       * @param value LicenseType of the type org.semanticwb.social.LicenseType
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByLicenseType(org.semanticwb.social.LicenseType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_licenseType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Institution
       * @param value Institution of the type org.semanticwb.social.Institution
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByInstitution(org.semanticwb.social.Institution value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_institution, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Institution
       * @param value Institution of the type org.semanticwb.social.Institution
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByInstitution(org.semanticwb.social.Institution value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_institution,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialSiteBase.ClassMgr getSocialSiteClassMgr()
    {
        return new SocialSiteBase.ClassMgr();
    }

   /**
   * Constructs a SocialSiteBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialSite
   */
    public SocialSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NumDaysToRefreshUserData property
* @return int with the NumDaysToRefreshUserData
*/
    public int getNumDaysToRefreshUserData()
    {
        return getSemanticObject().getIntProperty(social_numDaysToRefreshUserData);
    }

/**
* Sets the NumDaysToRefreshUserData property
* @param value long with the NumDaysToRefreshUserData
*/
    public void setNumDaysToRefreshUserData(int value)
    {
        getSemanticObject().setIntProperty(social_numDaysToRefreshUserData, value);
    }
   /**
   * Sets the value for the property LicenseType
   * @param value LicenseType to set
   */

    public void setLicenseType(org.semanticwb.social.LicenseType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_licenseType, value.getSemanticObject());
        }else
        {
            removeLicenseType();
        }
    }
   /**
   * Remove the value for LicenseType property
   */

    public void removeLicenseType()
    {
        getSemanticObject().removeProperty(social_licenseType);
    }

   /**
   * Gets the LicenseType
   * @return a org.semanticwb.social.LicenseType
   */
    public org.semanticwb.social.LicenseType getLicenseType()
    {
         org.semanticwb.social.LicenseType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_licenseType);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.LicenseType)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the BlockofMsgToClassify property
* @return int with the BlockofMsgToClassify
*/
    public int getBlockofMsgToClassify()
    {
        return getSemanticObject().getIntProperty(social_blockofMsgToClassify);
    }

/**
* Sets the BlockofMsgToClassify property
* @param value long with the BlockofMsgToClassify
*/
    public void setBlockofMsgToClassify(int value)
    {
        getSemanticObject().setIntProperty(social_blockofMsgToClassify, value);
    }
   /**
   * Sets the value for the property Institution
   * @param value Institution to set
   */

    public void setInstitution(org.semanticwb.social.Institution value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_institution, value.getSemanticObject());
        }else
        {
            removeInstitution();
        }
    }
   /**
   * Remove the value for Institution property
   */

    public void removeInstitution()
    {
        getSemanticObject().removeProperty(social_institution);
    }

   /**
   * Gets the Institution
   * @return a org.semanticwb.social.Institution
   */
    public org.semanticwb.social.Institution getInstitution()
    {
         org.semanticwb.social.Institution ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_institution);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Institution)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Counter property
* @return int with the Counter
*/
    public int getCounter()
    {
        return getSemanticObject().getIntProperty(social_counter);
    }

/**
* Sets the Counter property
* @param value long with the Counter
*/
    public void setCounter(int value)
    {
        getSemanticObject().setIntProperty(social_counter, value);
    }

    public org.semanticwb.social.SendEmail getSendEmail(String id)
    {
        return org.semanticwb.social.SendEmail.ClassMgr.getSendEmail(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SendEmail> listSendEmails()
    {
        return org.semanticwb.social.SendEmail.ClassMgr.listSendEmails(this);
    }

    public org.semanticwb.social.SendEmail createSendEmail(String id)
    {
        return org.semanticwb.social.SendEmail.ClassMgr.createSendEmail(id,this);
    }

    public org.semanticwb.social.SendEmail createSendEmail()
    {
        long id=getSemanticObject().getModel().getCounter(social_SendEmail);
        return org.semanticwb.social.SendEmail.ClassMgr.createSendEmail(String.valueOf(id),this);
    } 

    public void removeSendEmail(String id)
    {
        org.semanticwb.social.SendEmail.ClassMgr.removeSendEmail(id, this);
    }
    public boolean hasSendEmail(String id)
    {
        return org.semanticwb.social.SendEmail.ClassMgr.hasSendEmail(id, this);
    }

    public org.semanticwb.social.SocialTopic getSocialTopic(String id)
    {
        return org.semanticwb.social.SocialTopic.ClassMgr.getSocialTopic(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopics()
    {
        return org.semanticwb.social.SocialTopic.ClassMgr.listSocialTopics(this);
    }

    public org.semanticwb.social.SocialTopic createSocialTopic(String id)
    {
        return org.semanticwb.social.SocialTopic.ClassMgr.createSocialTopic(id,this);
    }

    public org.semanticwb.social.SocialTopic createSocialTopic()
    {
        long id=getSemanticObject().getModel().getCounter(social_SocialTopic);
        return org.semanticwb.social.SocialTopic.ClassMgr.createSocialTopic(String.valueOf(id),this);
    } 

    public void removeSocialTopic(String id)
    {
        org.semanticwb.social.SocialTopic.ClassMgr.removeSocialTopic(id, this);
    }
    public boolean hasSocialTopic(String id)
    {
        return org.semanticwb.social.SocialTopic.ClassMgr.hasSocialTopic(id, this);
    }

    public org.semanticwb.social.Stream getStream(String id)
    {
        return org.semanticwb.social.Stream.ClassMgr.getStream(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.Stream> listStreams()
    {
        return org.semanticwb.social.Stream.ClassMgr.listStreams(this);
    }

    public org.semanticwb.social.Stream createStream(String id)
    {
        return org.semanticwb.social.Stream.ClassMgr.createStream(id,this);
    }

    public org.semanticwb.social.Stream createStream()
    {
        long id=getSemanticObject().getModel().getCounter(social_Stream);
        return org.semanticwb.social.Stream.ClassMgr.createStream(String.valueOf(id),this);
    } 

    public void removeStream(String id)
    {
        org.semanticwb.social.Stream.ClassMgr.removeStream(id, this);
    }
    public boolean hasStream(String id)
    {
        return org.semanticwb.social.Stream.ClassMgr.hasStream(id, this);
    }

    public org.semanticwb.social.SocialPFlow getSocialPFlow(String id)
    {
        return org.semanticwb.social.SocialPFlow.ClassMgr.getSocialPFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlows()
    {
        return org.semanticwb.social.SocialPFlow.ClassMgr.listSocialPFlows(this);
    }

    public org.semanticwb.social.SocialPFlow createSocialPFlow(String id)
    {
        return org.semanticwb.social.SocialPFlow.ClassMgr.createSocialPFlow(id,this);
    }

    public org.semanticwb.social.SocialPFlow createSocialPFlow()
    {
        long id=getSemanticObject().getModel().getCounter(social_SocialPFlow);
        return org.semanticwb.social.SocialPFlow.ClassMgr.createSocialPFlow(String.valueOf(id),this);
    } 

    public void removeSocialPFlow(String id)
    {
        org.semanticwb.social.SocialPFlow.ClassMgr.removeSocialPFlow(id, this);
    }
    public boolean hasSocialPFlow(String id)
    {
        return org.semanticwb.social.SocialPFlow.ClassMgr.hasSocialPFlow(id, this);
    }

    public org.semanticwb.social.SocialPFlowInstance getSocialPFlowInstance(String id)
    {
        return org.semanticwb.social.SocialPFlowInstance.ClassMgr.getSocialPFlowInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SocialPFlowInstance> listSocialPFlowInstances()
    {
        return org.semanticwb.social.SocialPFlowInstance.ClassMgr.listSocialPFlowInstances(this);
    }

    public org.semanticwb.social.SocialPFlowInstance createSocialPFlowInstance(String id)
    {
        return org.semanticwb.social.SocialPFlowInstance.ClassMgr.createSocialPFlowInstance(id,this);
    }

    public org.semanticwb.social.SocialPFlowInstance createSocialPFlowInstance()
    {
        long id=getSemanticObject().getModel().getCounter(social_SocialPFlowInstance);
        return org.semanticwb.social.SocialPFlowInstance.ClassMgr.createSocialPFlowInstance(String.valueOf(id),this);
    } 

    public void removeSocialPFlowInstance(String id)
    {
        org.semanticwb.social.SocialPFlowInstance.ClassMgr.removeSocialPFlowInstance(id, this);
    }
    public boolean hasSocialPFlowInstance(String id)
    {
        return org.semanticwb.social.SocialPFlowInstance.ClassMgr.hasSocialPFlowInstance(id, this);
    }

    public org.semanticwb.social.SocialNetwork getSocialNetwork(String id)
    {
        return org.semanticwb.social.SocialNetwork.ClassMgr.getSocialNetwork(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
    {
        return org.semanticwb.social.SocialNetwork.ClassMgr.listSocialNetworks(this);
    }

    public org.semanticwb.social.SocialNetwork createSocialNetwork(String id)
    {
        return org.semanticwb.social.SocialNetwork.ClassMgr.createSocialNetwork(id,this);
    }

    public org.semanticwb.social.SocialNetwork createSocialNetwork()
    {
        long id=getSemanticObject().getModel().getCounter(social_SocialNetwork);
        return org.semanticwb.social.SocialNetwork.ClassMgr.createSocialNetwork(String.valueOf(id),this);
    } 

    public void removeSocialNetwork(String id)
    {
        org.semanticwb.social.SocialNetwork.ClassMgr.removeSocialNetwork(id, this);
    }
    public boolean hasSocialNetwork(String id)
    {
        return org.semanticwb.social.SocialNetwork.ClassMgr.hasSocialNetwork(id, this);
    }

    public org.semanticwb.social.SendPost getSendPost(String id)
    {
        return org.semanticwb.social.SendPost.ClassMgr.getSendPost(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SendPost> listSendPosts()
    {
        return org.semanticwb.social.SendPost.ClassMgr.listSendPosts(this);
    }

    public org.semanticwb.social.SendPost createSendPost(String id)
    {
        return org.semanticwb.social.SendPost.ClassMgr.createSendPost(id,this);
    }

    public org.semanticwb.social.SendPost createSendPost()
    {
        long id=getSemanticObject().getModel().getCounter(social_SendPost);
        return org.semanticwb.social.SendPost.ClassMgr.createSendPost(String.valueOf(id),this);
    } 

    public void removeSendPost(String id)
    {
        org.semanticwb.social.SendPost.ClassMgr.removeSendPost(id, this);
    }
    public boolean hasSendPost(String id)
    {
        return org.semanticwb.social.SendPost.ClassMgr.hasSendPost(id, this);
    }

    public org.semanticwb.social.SocialPFlowRef getSocialPFlowRef(String id)
    {
        return org.semanticwb.social.SocialPFlowRef.ClassMgr.getSocialPFlowRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SocialPFlowRef> listSocialPFlowRefs()
    {
        return org.semanticwb.social.SocialPFlowRef.ClassMgr.listSocialPFlowRefs(this);
    }

    public org.semanticwb.social.SocialPFlowRef createSocialPFlowRef(String id)
    {
        return org.semanticwb.social.SocialPFlowRef.ClassMgr.createSocialPFlowRef(id,this);
    }

    public org.semanticwb.social.SocialPFlowRef createSocialPFlowRef()
    {
        long id=getSemanticObject().getModel().getCounter(social_SocialPFlowRef);
        return org.semanticwb.social.SocialPFlowRef.ClassMgr.createSocialPFlowRef(String.valueOf(id),this);
    } 

    public void removeSocialPFlowRef(String id)
    {
        org.semanticwb.social.SocialPFlowRef.ClassMgr.removeSocialPFlowRef(id, this);
    }
    public boolean hasSocialPFlowRef(String id)
    {
        return org.semanticwb.social.SocialPFlowRef.ClassMgr.hasSocialPFlowRef(id, this);
    }

    public org.semanticwb.social.MarkMsgAsPrioritary getMarkMsgAsPrioritary(String id)
    {
        return org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.getMarkMsgAsPrioritary(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaries()
    {
        return org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.listMarkMsgAsPrioritaries(this);
    }

    public org.semanticwb.social.MarkMsgAsPrioritary createMarkMsgAsPrioritary(String id)
    {
        return org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.createMarkMsgAsPrioritary(id,this);
    }

    public org.semanticwb.social.MarkMsgAsPrioritary createMarkMsgAsPrioritary()
    {
        long id=getSemanticObject().getModel().getCounter(social_MarkMsgAsPrioritary);
        return org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.createMarkMsgAsPrioritary(String.valueOf(id),this);
    } 

    public void removeMarkMsgAsPrioritary(String id)
    {
        org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.removeMarkMsgAsPrioritary(id, this);
    }
    public boolean hasMarkMsgAsPrioritary(String id)
    {
        return org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.hasMarkMsgAsPrioritary(id, this);
    }

    public org.semanticwb.social.Rss getRss(String id)
    {
        return org.semanticwb.social.Rss.ClassMgr.getRss(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.Rss> listRsses()
    {
        return org.semanticwb.social.Rss.ClassMgr.listRsses(this);
    }

    public org.semanticwb.social.Rss createRss(String id)
    {
        return org.semanticwb.social.Rss.ClassMgr.createRss(id,this);
    }

    public org.semanticwb.social.Rss createRss()
    {
        long id=getSemanticObject().getModel().getCounter(social_Rss);
        return org.semanticwb.social.Rss.ClassMgr.createRss(String.valueOf(id),this);
    } 

    public void removeRss(String id)
    {
        org.semanticwb.social.Rss.ClassMgr.removeRss(id, this);
    }
    public boolean hasRss(String id)
    {
        return org.semanticwb.social.Rss.ClassMgr.hasRss(id, this);
    }

    public org.semanticwb.social.SocialRule getSocialRule(String id)
    {
        return org.semanticwb.social.SocialRule.ClassMgr.getSocialRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRules()
    {
        return org.semanticwb.social.SocialRule.ClassMgr.listSocialRules(this);
    }

    public org.semanticwb.social.SocialRule createSocialRule(String id)
    {
        return org.semanticwb.social.SocialRule.ClassMgr.createSocialRule(id,this);
    }

    public org.semanticwb.social.SocialRule createSocialRule()
    {
        long id=getSemanticObject().getModel().getCounter(social_SocialRule);
        return org.semanticwb.social.SocialRule.ClassMgr.createSocialRule(String.valueOf(id),this);
    } 

    public void removeSocialRule(String id)
    {
        org.semanticwb.social.SocialRule.ClassMgr.removeSocialRule(id, this);
    }
    public boolean hasSocialRule(String id)
    {
        return org.semanticwb.social.SocialRule.ClassMgr.hasSocialRule(id, this);
    }
}
