/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
    * Objeto que define un Sitio Web.
    */
public abstract class WebSiteBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable
{
   
   /** Superclase de todos los tipos de Modelos de SemanticWebBuilder. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    
    /** The Constant swb_hasSubModel. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasSubModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasSubModel");
   
   /** Objeto que define un Repositorio de Usuarios. */
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    
    /** The Constant swb_userRepository. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    
    /** The Constant swb_homePage. */
    public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
   /**
   * Las Plantillas son documentos HTML que sirven de base a SemanticWebBuilder para poder mostrar el "look & feel" del sitio, así como la distribución de todos los elementos en la pagina.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    
    /** The Constant swb_defaultTemplate. */
    public static final org.semanticwb.platform.SemanticProperty swb_defaultTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#defaultTemplate");
   
   /** Objeto que define una agripacion de componentes o recursos de un mismo tipo. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
   
   /** Define una asociacion entre dos miembros en la especificacion de TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
   
   /** Referencia a un objeto de tipo Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
   
   /** Referencia a un objeto de tipo Rule. */
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
   /**
   * Un Lenguaje en SemanticWebBuilder es la definición de un Idioma para despliegue de las páginas y recursos. Al definir un lenguaje nuevo es posible definir el título y la descripción de páginas y recursos en él.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
   /**
   * Un servidor DNS permite conectarse con la máquina sin necesidad de conocer su dirección IP. En SemanticWebBuilder el DNS local es el nombre asociado al sitio. Al ser invocado el DNS presentará una sección específica a manera de página de inicio.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
   
   /** Miembro participante dentro de una asociacion en la especificacion de TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
   /**
   * Objeto que define una regla de negocio, utilizando los atributos del usuario para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
   
   /** Define una campaña de recursos o componentes de SemanticWebBuilder (No usada de momento). */
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
   
   /** Objeto utilizado para identificar una version de algun componente. */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
   /**
   * Objeto de calendarización que permite configurar una página o un recurso para desplegarse en cierta fecha, entre un rango de fechas o incluso en periodos de tiempo definidos.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
   /**
   * Un dispositivo es un elemento que tiene la capacidad de leer una Página Web, por ejemplo: un PDA, una PC o un celular. En SemanticWebBuilder se pueden encontrar algunos dispositivos ya definidos.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
   /**
   * Un Filtro permite configurar un recurso para que se despliegue sólo en ciertas páginas dentro de un Sitio Web, es decir, restringe el acceso a ciertas funcionalidades a nivel de navegación.
   */
    public static final org.semanticwb.platform.SemanticClass swb_AdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminFilter");
   /**
   * Define un Filtros de IPs, para denegar acceso, solo acceso o no contar accesos de las IPs definidas en el filtro.
   */
    public static final org.semanticwb.platform.SemanticClass swb_IPFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#IPFilter");
   
   /** Define una Collección de objetos de una clase especificada con la propiedad "collectionClass". */
    public static final org.semanticwb.platform.SemanticClass swb_Collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");
   
   /** Referencia a un objeto de tipo PFlow. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
   
   /** Catalogo de paises. */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
   
   /** Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
   
   /** Objeto por el cual se define un filtro para que un recurso o componente se pueda presentar o no dentro de la estructura del sitio. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
   
   /** Referencia a un objeto de tipo Template. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
   
   /** Referencia a un objeto de tipo Calendar. */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
   
   /** Un recurso es un componente en una Página Web con el cual el usuario tiene interacción. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
   
   /** Objeto por medio del cual se define un tipo de componente o recurso. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
   
   /** Objeto que define un Grupo de plantillas. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
   
   /** Referencia a un objeto de tipo Role. */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
   
   /** Objeto que define un Sitio Web. */
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of WebSite for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.WebSite
        */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.WebSite for all models
       * @return Iterator of org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.WebSite
       * @param id Identifier for org.semanticwb.model.WebSite
       * @return A org.semanticwb.model.WebSite
       */
        public static org.semanticwb.model.WebSite getWebSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.WebSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.WebSite)obj.createGenericInstance();
                }
            }
            return ret;
        }
       
       /**
        * Create a org.semanticwb.model.WebSite
        * 
        * @param id Identifier for org.semanticwb.model.WebSite
        * @param namespace the namespace
        * @return A org.semanticwb.model.WebSite
        */
        public static org.semanticwb.model.WebSite createWebSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.WebSite)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.WebSite
       * @param id Identifier for org.semanticwb.model.WebSite
       */
        public static void removeWebSite(String id)
        {
            org.semanticwb.model.WebSite obj=getWebSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.model.WebSite
       * @param id Identifier for org.semanticwb.model.WebSite
       * @return true if the org.semanticwb.model.WebSite exists, false otherwise
       */

        public static boolean hasWebSite(String id)
        {
            return (getWebSite(id)!=null);
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.WebSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.model.WebSite
       */

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a WebSiteBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the WebSite
    */
    public WebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the Deleted property.
 * 
 * @return boolean with the Deleted
 */
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
 * Sets the Deleted property.
 * 
 * @param value long with the Deleted
 */
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

/**
 * Gets the Active property.
 * 
 * @return boolean with the Active
 */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
 * Sets the Active property.
 * 
 * @param value long with the Active
 */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   
   /**
    * Sets the value for the property Language.
    * 
    * @param value Language to set
    */

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }
   
   /**
    * Remove the value for Language property.
    */

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

   /**
    * Gets the Language.
    * 
    * @return a org.semanticwb.model.Language
    */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.SWBModel
   * @return A GenericIterator with all the org.semanticwb.model.SWBModel
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> listSubModels()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(getSemanticObject().listObjectProperties(swb_hasSubModel));
    }

   /**
    * Gets true if has a SubModel.
    * 
    * @param value org.semanticwb.model.SWBModel to verify
    * @return true if the org.semanticwb.model.SWBModel exists, false otherwise
    */
    public boolean hasSubModel(org.semanticwb.model.SWBModel value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasSubModel,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a SubModel.
    * 
    * @param value org.semanticwb.model.SWBModel to add
    */

    public void addSubModel(org.semanticwb.model.SWBModel value)
    {
        getSemanticObject().addObjectProperty(swb_hasSubModel, value.getSemanticObject());
    }
   
   /**
    * Removes all the SubModel.
    */

    public void removeAllSubModel()
    {
        getSemanticObject().removeProperty(swb_hasSubModel);
    }
   
   /**
    * Removes a SubModel.
    * 
    * @param value org.semanticwb.model.SWBModel to remove
    */

    public void removeSubModel(org.semanticwb.model.SWBModel value)
    {
        getSemanticObject().removeObjectProperty(swb_hasSubModel,value.getSemanticObject());
    }

   /**
    * Gets the SubModel.
    * 
    * @return a org.semanticwb.model.SWBModel
    */
    public org.semanticwb.model.SWBModel getSubModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasSubModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Undeleteable property.
 * 
 * @return boolean with the Undeleteable
 */
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

/**
 * Sets the Undeleteable property.
 * 
 * @param value long with the Undeleteable
 */
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

/**
 * Gets the Created property.
 * 
 * @return java.util.Date with the Created
 */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
 * Sets the Created property.
 * 
 * @param value long with the Created
 */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   
   /**
    * Sets the value for the property ModifiedBy.
    * 
    * @param value ModifiedBy to set
    */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   
   /**
    * Remove the value for ModifiedBy property.
    */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
    * Gets the ModifiedBy.
    * 
    * @return a org.semanticwb.model.User
    */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Title property.
 * 
 * @return String with the Title
 */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
 * Sets the Title property.
 * 
 * @param value long with the Title
 */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
 * Gets the Updated property.
 * 
 * @return java.util.Date with the Updated
 */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
 * Sets the Updated property.
 * 
 * @param value long with the Updated
 */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
 * Gets the Indexable property.
 * 
 * @return boolean with the Indexable
 */
    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

/**
 * Sets the Indexable property.
 * 
 * @param value long with the Indexable
 */
    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }
   
   /**
    * Sets the value for the property UserRepository.
    * 
    * @param value UserRepository to set
    */

    public void setUserRepository(org.semanticwb.model.UserRepository value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_userRepository, value.getSemanticObject());
        }else
        {
            removeUserRepository();
        }
    }
   
   /**
    * Remove the value for UserRepository property.
    */

    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(swb_userRepository);
    }

   /**
    * Gets the UserRepository.
    * 
    * @return a org.semanticwb.model.UserRepository
    */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
         org.semanticwb.model.UserRepository ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userRepository);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserRepository)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.Ontology
   * @return A GenericIterator with all the org.semanticwb.model.Ontology
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> listOntologies()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(getSemanticObject().listObjectProperties(swb_hasOntology));
    }

   /**
    * Gets true if has a Ontology.
    * 
    * @param value org.semanticwb.model.Ontology to verify
    * @return true if the org.semanticwb.model.Ontology exists, false otherwise
    */
    public boolean hasOntology(org.semanticwb.model.Ontology value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasOntology,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a Ontology.
    * 
    * @param value org.semanticwb.model.Ontology to add
    */

    public void addOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().addObjectProperty(swb_hasOntology, value.getSemanticObject());
    }
   
   /**
    * Removes all the Ontology.
    */

    public void removeAllOntology()
    {
        getSemanticObject().removeProperty(swb_hasOntology);
    }
   
   /**
    * Removes a Ontology.
    * 
    * @param value org.semanticwb.model.Ontology to remove
    */

    public void removeOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().removeObjectProperty(swb_hasOntology,value.getSemanticObject());
    }

   /**
    * Gets the Ontology.
    * 
    * @return a org.semanticwb.model.Ontology
    */
    public org.semanticwb.model.Ontology getOntology()
    {
         org.semanticwb.model.Ontology ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasOntology);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Ontology)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property Creator.
    * 
    * @param value Creator to set
    */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   
   /**
    * Remove the value for Creator property.
    */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
    * Gets the Creator.
    * 
    * @return a org.semanticwb.model.User
    */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property HomePage.
    * 
    * @param value HomePage to set
    */

    public void setHomePage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_homePage, value.getSemanticObject());
        }else
        {
            removeHomePage();
        }
    }
   
   /**
    * Remove the value for HomePage property.
    */

    public void removeHomePage()
    {
        getSemanticObject().removeProperty(swb_homePage);
    }

   /**
    * Gets the HomePage.
    * 
    * @return a org.semanticwb.model.WebPage
    */
    public org.semanticwb.model.WebPage getHomePage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_homePage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property DefaultTemplate.
    * 
    * @param value DefaultTemplate to set
    */

    public void setDefaultTemplate(org.semanticwb.model.Template value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_defaultTemplate, value.getSemanticObject());
        }else
        {
            removeDefaultTemplate();
        }
    }
   
   /**
    * Remove the value for DefaultTemplate property.
    */

    public void removeDefaultTemplate()
    {
        getSemanticObject().removeProperty(swb_defaultTemplate);
    }

   /**
    * Gets the DefaultTemplate.
    * 
    * @return a org.semanticwb.model.Template
    */
    public org.semanticwb.model.Template getDefaultTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_defaultTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Description property.
 * 
 * @return String with the Description
 */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
 * Sets the Description property.
 * 
 * @param value long with the Description
 */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /**
     * Gets the resource sub type.
     * 
     * @param id the id
     * @return the resource sub type
     */
    public org.semanticwb.model.ResourceSubType getResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.getResourceSubType(id, this);
    }

    /**
     * List resource sub types.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceSubType> listResourceSubTypes()
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.listResourceSubTypes(this);
    }

    /**
     * Creates the resource sub type.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource sub type
     */
    public org.semanticwb.model.ResourceSubType createResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.createResourceSubType(id,this);
    }

    /**
     * Removes the resource sub type.
     * 
     * @param id the id
     */
    public void removeResourceSubType(String id)
    {
        org.semanticwb.model.ResourceSubType.ClassMgr.removeResourceSubType(id, this);
    }
    
    /**
     * Checks for resource sub type.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.hasResourceSubType(id, this);
    }

    /**
     * Gets the association.
     * 
     * @param id the id
     * @return the association
     */
    public org.semanticwb.model.Association getAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.getAssociation(id, this);
    }

    /**
     * List associations.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Association> listAssociations()
    {
        return org.semanticwb.model.Association.ClassMgr.listAssociations(this);
    }

    /**
     * Creates the association.
     * 
     * @param id the id
     * @return the org.semanticwb.model. association
     */
    public org.semanticwb.model.Association createAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.createAssociation(id,this);
    }

    /**
     * Creates the association.
     * 
     * @return the org.semanticwb.model. association
     */
    public org.semanticwb.model.Association createAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Association);
        return org.semanticwb.model.Association.ClassMgr.createAssociation(String.valueOf(id),this);
    } 

    /**
     * Removes the association.
     * 
     * @param id the id
     */
    public void removeAssociation(String id)
    {
        org.semanticwb.model.Association.ClassMgr.removeAssociation(id, this);
    }
    
    /**
     * Checks for association.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.hasAssociation(id, this);
    }

    /**
     * Gets the resource ref.
     * 
     * @param id the id
     * @return the resource ref
     */
    public org.semanticwb.model.ResourceRef getResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.getResourceRef(id, this);
    }

    /**
     * List resource refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.listResourceRefs(this);
    }

    /**
     * Creates the resource ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource ref
     */
    public org.semanticwb.model.ResourceRef createResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(id,this);
    }

    /**
     * Creates the resource ref.
     * 
     * @return the org.semanticwb.model. resource ref
     */
    public org.semanticwb.model.ResourceRef createResourceRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceRef);
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id),this);
    } 

    /**
     * Removes the resource ref.
     * 
     * @param id the id
     */
    public void removeResourceRef(String id)
    {
        org.semanticwb.model.ResourceRef.ClassMgr.removeResourceRef(id, this);
    }
    
    /**
     * Checks for resource ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.hasResourceRef(id, this);
    }

    /**
     * Gets the rule ref.
     * 
     * @param id the id
     * @return the rule ref
     */
    public org.semanticwb.model.RuleRef getRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.getRuleRef(id, this);
    }

    /**
     * List rule refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return org.semanticwb.model.RuleRef.ClassMgr.listRuleRefs(this);
    }

    /**
     * Creates the rule ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. rule ref
     */
    public org.semanticwb.model.RuleRef createRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(id,this);
    }

    /**
     * Creates the rule ref.
     * 
     * @return the org.semanticwb.model. rule ref
     */
    public org.semanticwb.model.RuleRef createRuleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RuleRef);
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(String.valueOf(id),this);
    } 

    /**
     * Removes the rule ref.
     * 
     * @param id the id
     */
    public void removeRuleRef(String id)
    {
        org.semanticwb.model.RuleRef.ClassMgr.removeRuleRef(id, this);
    }
    
    /**
     * Checks for rule ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.hasRuleRef(id, this);
    }

    /**
     * Gets the language.
     * 
     * @param id the id
     * @return the language
     */
    public org.semanticwb.model.Language getLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.getLanguage(id, this);
    }

    /**
     * List languages.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Language> listLanguages()
    {
        return org.semanticwb.model.Language.ClassMgr.listLanguages(this);
    }

    /**
     * Creates the language.
     * 
     * @param id the id
     * @return the org.semanticwb.model. language
     */
    public org.semanticwb.model.Language createLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.createLanguage(id,this);
    }

    /**
     * Removes the language.
     * 
     * @param id the id
     */
    public void removeLanguage(String id)
    {
        org.semanticwb.model.Language.ClassMgr.removeLanguage(id, this);
    }
    
    /**
     * Checks for language.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.hasLanguage(id, this);
    }

    /**
     * Gets the dns.
     * 
     * @param id the id
     * @return the dns
     */
    public org.semanticwb.model.Dns getDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.getDns(id, this);
    }

    /**
     * List dnses.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Dns> listDnses()
    {
        return org.semanticwb.model.Dns.ClassMgr.listDnses(this);
    }

    /**
     * Creates the dns.
     * 
     * @param id the id
     * @return the org.semanticwb.model. dns
     */
    public org.semanticwb.model.Dns createDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.createDns(id,this);
    }

    /**
     * Creates the dns.
     * 
     * @return the org.semanticwb.model. dns
     */
    public org.semanticwb.model.Dns createDns()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Dns);
        return org.semanticwb.model.Dns.ClassMgr.createDns(String.valueOf(id),this);
    } 

    /**
     * Removes the dns.
     * 
     * @param id the id
     */
    public void removeDns(String id)
    {
        org.semanticwb.model.Dns.ClassMgr.removeDns(id, this);
    }
    
    /**
     * Checks for dns.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.hasDns(id, this);
    }

    /**
     * Gets the ass member.
     * 
     * @param id the id
     * @return the ass member
     */
    public org.semanticwb.model.AssMember getAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.getAssMember(id, this);
    }

    /**
     * List ass members.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers()
    {
        return org.semanticwb.model.AssMember.ClassMgr.listAssMembers(this);
    }

    /**
     * Creates the ass member.
     * 
     * @param id the id
     * @return the org.semanticwb.model. ass member
     */
    public org.semanticwb.model.AssMember createAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.createAssMember(id,this);
    }

    /**
     * Creates the ass member.
     * 
     * @return the org.semanticwb.model. ass member
     */
    public org.semanticwb.model.AssMember createAssMember()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AssMember);
        return org.semanticwb.model.AssMember.ClassMgr.createAssMember(String.valueOf(id),this);
    } 

    /**
     * Removes the ass member.
     * 
     * @param id the id
     */
    public void removeAssMember(String id)
    {
        org.semanticwb.model.AssMember.ClassMgr.removeAssMember(id, this);
    }
    
    /**
     * Checks for ass member.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.hasAssMember(id, this);
    }

    /**
     * Gets the rule.
     * 
     * @param id the id
     * @return the rule
     */
    public org.semanticwb.model.Rule getRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.getRule(id, this);
    }

    /**
     * List rules.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Rule> listRules()
    {
        return org.semanticwb.model.Rule.ClassMgr.listRules(this);
    }

    /**
     * Creates the rule.
     * 
     * @param id the id
     * @return the org.semanticwb.model. rule
     */
    public org.semanticwb.model.Rule createRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.createRule(id,this);
    }

    /**
     * Creates the rule.
     * 
     * @return the org.semanticwb.model. rule
     */
    public org.semanticwb.model.Rule createRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Rule);
        return org.semanticwb.model.Rule.ClassMgr.createRule(String.valueOf(id),this);
    } 

    /**
     * Removes the rule.
     * 
     * @param id the id
     */
    public void removeRule(String id)
    {
        org.semanticwb.model.Rule.ClassMgr.removeRule(id, this);
    }
    
    /**
     * Checks for rule.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.hasRule(id, this);
    }

    /**
     * Gets the camp.
     * 
     * @param id the id
     * @return the camp
     */
    public org.semanticwb.model.Camp getCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.getCamp(id, this);
    }

    /**
     * List camps.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Camp> listCamps()
    {
        return org.semanticwb.model.Camp.ClassMgr.listCamps(this);
    }

    /**
     * Creates the camp.
     * 
     * @param id the id
     * @return the org.semanticwb.model. camp
     */
    public org.semanticwb.model.Camp createCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.createCamp(id,this);
    }

    /**
     * Creates the camp.
     * 
     * @return the org.semanticwb.model. camp
     */
    public org.semanticwb.model.Camp createCamp()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Camp);
        return org.semanticwb.model.Camp.ClassMgr.createCamp(String.valueOf(id),this);
    } 

    /**
     * Removes the camp.
     * 
     * @param id the id
     */
    public void removeCamp(String id)
    {
        org.semanticwb.model.Camp.ClassMgr.removeCamp(id, this);
    }
    
    /**
     * Checks for camp.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.hasCamp(id, this);
    }

    /**
     * Gets the version info.
     * 
     * @param id the id
     * @return the version info
     */
    public org.semanticwb.model.VersionInfo getVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.getVersionInfo(id, this);
    }

    /**
     * List version infos.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.listVersionInfos(this);
    }

    /**
     * Creates the version info.
     * 
     * @param id the id
     * @return the org.semanticwb.model. version info
     */
    public org.semanticwb.model.VersionInfo createVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(id,this);
    }

    /**
     * Creates the version info.
     * 
     * @return the org.semanticwb.model. version info
     */
    public org.semanticwb.model.VersionInfo createVersionInfo()
    {
        long id=getSemanticObject().getModel().getCounter(swb_VersionInfo);
        return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(String.valueOf(id),this);
    } 

    /**
     * Removes the version info.
     * 
     * @param id the id
     */
    public void removeVersionInfo(String id)
    {
        org.semanticwb.model.VersionInfo.ClassMgr.removeVersionInfo(id, this);
    }
    
    /**
     * Checks for version info.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.hasVersionInfo(id, this);
    }

    /**
     * Gets the web page.
     * 
     * @param id the id
     * @return the web page
     */
    public org.semanticwb.model.WebPage getWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.getWebPage(id, this);
    }

    /**
     * List web pages.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return org.semanticwb.model.WebPage.ClassMgr.listWebPages(this);
    }

    /**
     * Creates the web page.
     * 
     * @param id the id
     * @return the org.semanticwb.model. web page
     */
    public org.semanticwb.model.WebPage createWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.createWebPage(id,this);
    }

    /**
     * Removes the web page.
     * 
     * @param id the id
     */
    public void removeWebPage(String id)
    {
        org.semanticwb.model.WebPage.ClassMgr.removeWebPage(id, this);
    }
    
    /**
     * Checks for web page.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.hasWebPage(id, this);
    }

    /**
     * Gets the calendar.
     * 
     * @param id the id
     * @return the calendar
     */
    public org.semanticwb.model.Calendar getCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.getCalendar(id, this);
    }

    /**
     * List calendars.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return org.semanticwb.model.Calendar.ClassMgr.listCalendars(this);
    }

    /**
     * Creates the calendar.
     * 
     * @param id the id
     * @return the org.semanticwb.model. calendar
     */
    public org.semanticwb.model.Calendar createCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.createCalendar(id,this);
    }

    /**
     * Creates the calendar.
     * 
     * @return the org.semanticwb.model. calendar
     */
    public org.semanticwb.model.Calendar createCalendar()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Calendar);
        return org.semanticwb.model.Calendar.ClassMgr.createCalendar(String.valueOf(id),this);
    } 

    /**
     * Removes the calendar.
     * 
     * @param id the id
     */
    public void removeCalendar(String id)
    {
        org.semanticwb.model.Calendar.ClassMgr.removeCalendar(id, this);
    }
    
    /**
     * Checks for calendar.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.hasCalendar(id, this);
    }

    /**
     * Gets the device.
     * 
     * @param id the id
     * @return the device
     */
    public org.semanticwb.model.Device getDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.getDevice(id, this);
    }

    /**
     * List devices.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Device> listDevices()
    {
        return org.semanticwb.model.Device.ClassMgr.listDevices(this);
    }

    /**
     * Creates the device.
     * 
     * @param id the id
     * @return the org.semanticwb.model. device
     */
    public org.semanticwb.model.Device createDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.createDevice(id,this);
    }

    /**
     * Removes the device.
     * 
     * @param id the id
     */
    public void removeDevice(String id)
    {
        org.semanticwb.model.Device.ClassMgr.removeDevice(id, this);
    }
    
    /**
     * Checks for device.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.hasDevice(id, this);
    }

    /**
     * Gets the admin filter.
     * 
     * @param id the id
     * @return the admin filter
     */
    public org.semanticwb.model.AdminFilter getAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.getAdminFilter(id, this);
    }

    /**
     * List admin filters.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.listAdminFilters(this);
    }

    /**
     * Creates the admin filter.
     * 
     * @param id the id
     * @return the org.semanticwb.model. admin filter
     */
    public org.semanticwb.model.AdminFilter createAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.createAdminFilter(id,this);
    }

    /**
     * Creates the admin filter.
     * 
     * @return the org.semanticwb.model. admin filter
     */
    public org.semanticwb.model.AdminFilter createAdminFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AdminFilter);
        return org.semanticwb.model.AdminFilter.ClassMgr.createAdminFilter(String.valueOf(id),this);
    } 

    /**
     * Removes the admin filter.
     * 
     * @param id the id
     */
    public void removeAdminFilter(String id)
    {
        org.semanticwb.model.AdminFilter.ClassMgr.removeAdminFilter(id, this);
    }
    
    /**
     * Checks for admin filter.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.hasAdminFilter(id, this);
    }

    /**
     * Gets the iP filter.
     * 
     * @param id the id
     * @return the iP filter
     */
    public org.semanticwb.model.IPFilter getIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.getIPFilter(id, this);
    }

    /**
     * List ip filters.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        return org.semanticwb.model.IPFilter.ClassMgr.listIPFilters(this);
    }

    /**
     * Creates the ip filter.
     * 
     * @param id the id
     * @return the org.semanticwb.model. ip filter
     */
    public org.semanticwb.model.IPFilter createIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.createIPFilter(id,this);
    }

    /**
     * Creates the ip filter.
     * 
     * @return the org.semanticwb.model. ip filter
     */
    public org.semanticwb.model.IPFilter createIPFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_IPFilter);
        return org.semanticwb.model.IPFilter.ClassMgr.createIPFilter(String.valueOf(id),this);
    } 

    /**
     * Removes the ip filter.
     * 
     * @param id the id
     */
    public void removeIPFilter(String id)
    {
        org.semanticwb.model.IPFilter.ClassMgr.removeIPFilter(id, this);
    }
    
    /**
     * Checks for ip filter.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.hasIPFilter(id, this);
    }

    /**
     * Gets the collection.
     * 
     * @param id the id
     * @return the collection
     */
    public org.semanticwb.model.Collection getCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.getCollection(id, this);
    }

    /**
     * List collections.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Collection> listCollections()
    {
        return org.semanticwb.model.Collection.ClassMgr.listCollections(this);
    }

    /**
     * Creates the collection.
     * 
     * @param id the id
     * @return the org.semanticwb.model. collection
     */
    public org.semanticwb.model.Collection createCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.createCollection(id,this);
    }

    /**
     * Creates the collection.
     * 
     * @return the org.semanticwb.model. collection
     */
    public org.semanticwb.model.Collection createCollection()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Collection);
        return org.semanticwb.model.Collection.ClassMgr.createCollection(String.valueOf(id),this);
    } 

    /**
     * Removes the collection.
     * 
     * @param id the id
     */
    public void removeCollection(String id)
    {
        org.semanticwb.model.Collection.ClassMgr.removeCollection(id, this);
    }
    
    /**
     * Checks for collection.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.hasCollection(id, this);
    }

    /**
     * Gets the p flow ref.
     * 
     * @param id the id
     * @return the p flow ref
     */
    public org.semanticwb.model.PFlowRef getPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.getPFlowRef(id, this);
    }

    /**
     * List p flow refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.listPFlowRefs(this);
    }

    /**
     * Creates the p flow ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. p flow ref
     */
    public org.semanticwb.model.PFlowRef createPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(id,this);
    }

    /**
     * Creates the p flow ref.
     * 
     * @return the org.semanticwb.model. p flow ref
     */
    public org.semanticwb.model.PFlowRef createPFlowRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowRef);
        return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(String.valueOf(id),this);
    } 

    /**
     * Removes the p flow ref.
     * 
     * @param id the id
     */
    public void removePFlowRef(String id)
    {
        org.semanticwb.model.PFlowRef.ClassMgr.removePFlowRef(id, this);
    }
    
    /**
     * Checks for p flow ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.hasPFlowRef(id, this);
    }

    /**
     * Gets the template.
     * 
     * @param id the id
     * @return the template
     */
    public org.semanticwb.model.Template getTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.getTemplate(id, this);
    }

    /**
     * List templates.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Template> listTemplates()
    {
        return org.semanticwb.model.Template.ClassMgr.listTemplates(this);
    }

    /**
     * Creates the template.
     * 
     * @param id the id
     * @return the org.semanticwb.model. template
     */
    public org.semanticwb.model.Template createTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.createTemplate(id,this);
    }

    /**
     * Creates the template.
     * 
     * @return the org.semanticwb.model. template
     */
    public org.semanticwb.model.Template createTemplate()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Template);
        return org.semanticwb.model.Template.ClassMgr.createTemplate(String.valueOf(id),this);
    } 

    /**
     * Removes the template.
     * 
     * @param id the id
     */
    public void removeTemplate(String id)
    {
        org.semanticwb.model.Template.ClassMgr.removeTemplate(id, this);
    }
    
    /**
     * Checks for template.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.hasTemplate(id, this);
    }

    /**
     * Gets the country.
     * 
     * @param id the id
     * @return the country
     */
    public org.semanticwb.model.Country getCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.getCountry(id, this);
    }

    /**
     * List countries.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Country> listCountries()
    {
        return org.semanticwb.model.Country.ClassMgr.listCountries(this);
    }

    /**
     * Creates the country.
     * 
     * @param id the id
     * @return the org.semanticwb.model. country
     */
    public org.semanticwb.model.Country createCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.createCountry(id,this);
    }

    /**
     * Removes the country.
     * 
     * @param id the id
     */
    public void removeCountry(String id)
    {
        org.semanticwb.model.Country.ClassMgr.removeCountry(id, this);
    }
    
    /**
     * Checks for country.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.hasCountry(id, this);
    }

    /**
     * Gets the p flow.
     * 
     * @param id the id
     * @return the p flow
     */
    public org.semanticwb.model.PFlow getPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.getPFlow(id, this);
    }

    /**
     * List p flows.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        return org.semanticwb.model.PFlow.ClassMgr.listPFlows(this);
    }

    /**
     * Creates the p flow.
     * 
     * @param id the id
     * @return the org.semanticwb.model. p flow
     */
    public org.semanticwb.model.PFlow createPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.createPFlow(id,this);
    }

    /**
     * Creates the p flow.
     * 
     * @return the org.semanticwb.model. p flow
     */
    public org.semanticwb.model.PFlow createPFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlow);
        return org.semanticwb.model.PFlow.ClassMgr.createPFlow(String.valueOf(id),this);
    } 

    /**
     * Removes the p flow.
     * 
     * @param id the id
     */
    public void removePFlow(String id)
    {
        org.semanticwb.model.PFlow.ClassMgr.removePFlow(id, this);
    }
    
    /**
     * Checks for p flow.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.hasPFlow(id, this);
    }

    /**
     * Gets the resource filter.
     * 
     * @param id the id
     * @return the resource filter
     */
    public org.semanticwb.model.ResourceFilter getResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.getResourceFilter(id, this);
    }

    /**
     * List resource filters.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceFilter> listResourceFilters()
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.listResourceFilters(this);
    }

    /**
     * Creates the resource filter.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource filter
     */
    public org.semanticwb.model.ResourceFilter createResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.createResourceFilter(id,this);
    }

    /**
     * Creates the resource filter.
     * 
     * @return the org.semanticwb.model. resource filter
     */
    public org.semanticwb.model.ResourceFilter createResourceFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceFilter);
        return org.semanticwb.model.ResourceFilter.ClassMgr.createResourceFilter(String.valueOf(id),this);
    } 

    /**
     * Removes the resource filter.
     * 
     * @param id the id
     */
    public void removeResourceFilter(String id)
    {
        org.semanticwb.model.ResourceFilter.ClassMgr.removeResourceFilter(id, this);
    }
    
    /**
     * Checks for resource filter.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.hasResourceFilter(id, this);
    }

    /**
     * Gets the template ref.
     * 
     * @param id the id
     * @return the template ref
     */
    public org.semanticwb.model.TemplateRef getTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.getTemplateRef(id, this);
    }

    /**
     * List template refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.listTemplateRefs(this);
    }

    /**
     * Creates the template ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. template ref
     */
    public org.semanticwb.model.TemplateRef createTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(id,this);
    }

    /**
     * Creates the template ref.
     * 
     * @return the org.semanticwb.model. template ref
     */
    public org.semanticwb.model.TemplateRef createTemplateRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateRef);
        return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(String.valueOf(id),this);
    } 

    /**
     * Removes the template ref.
     * 
     * @param id the id
     */
    public void removeTemplateRef(String id)
    {
        org.semanticwb.model.TemplateRef.ClassMgr.removeTemplateRef(id, this);
    }
    
    /**
     * Checks for template ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.hasTemplateRef(id, this);
    }

    /**
     * Gets the calendar ref.
     * 
     * @param id the id
     * @return the calendar ref
     */
    public org.semanticwb.model.CalendarRef getCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.getCalendarRef(id, this);
    }

    /**
     * List calendar refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.listCalendarRefs(this);
    }

    /**
     * Creates the calendar ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. calendar ref
     */
    public org.semanticwb.model.CalendarRef createCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(id,this);
    }

    /**
     * Creates the calendar ref.
     * 
     * @return the org.semanticwb.model. calendar ref
     */
    public org.semanticwb.model.CalendarRef createCalendarRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_CalendarRef);
        return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(String.valueOf(id),this);
    } 

    /**
     * Removes the calendar ref.
     * 
     * @param id the id
     */
    public void removeCalendarRef(String id)
    {
        org.semanticwb.model.CalendarRef.ClassMgr.removeCalendarRef(id, this);
    }
    
    /**
     * Checks for calendar ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.hasCalendarRef(id, this);
    }

    /**
     * Gets the resource.
     * 
     * @param id the id
     * @return the resource
     */
    public org.semanticwb.model.Resource getResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.getResource(id, this);
    }

    /**
     * List resources.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Resource> listResources()
    {
        return org.semanticwb.model.Resource.ClassMgr.listResources(this);
    }

    /**
     * Creates the resource.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource
     */
    public org.semanticwb.model.Resource createResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.createResource(id,this);
    }

    /**
     * Creates the resource.
     * 
     * @return the org.semanticwb.model. resource
     */
    public org.semanticwb.model.Resource createResource()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Resource);
        return org.semanticwb.model.Resource.ClassMgr.createResource(String.valueOf(id),this);
    } 

    /**
     * Removes the resource.
     * 
     * @param id the id
     */
    public void removeResource(String id)
    {
        org.semanticwb.model.Resource.ClassMgr.removeResource(id, this);
    }
    
    /**
     * Checks for resource.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.hasResource(id, this);
    }

    /**
     * Gets the resource type.
     * 
     * @param id the id
     * @return the resource type
     */
    public org.semanticwb.model.ResourceType getResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.getResourceType(id, this);
    }

    /**
     * List resource types.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
    {
        return org.semanticwb.model.ResourceType.ClassMgr.listResourceTypes(this);
    }

    /**
     * Creates the resource type.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource type
     */
    public org.semanticwb.model.ResourceType createResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.createResourceType(id,this);
    }

    /**
     * Removes the resource type.
     * 
     * @param id the id
     */
    public void removeResourceType(String id)
    {
        org.semanticwb.model.ResourceType.ClassMgr.removeResourceType(id, this);
    }
    
    /**
     * Checks for resource type.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.hasResourceType(id, this);
    }

    /**
     * Gets the p flow instance.
     * 
     * @param id the id
     * @return the p flow instance
     */
    public org.semanticwb.model.PFlowInstance getPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.getPFlowInstance(id, this);
    }

    /**
     * List p flow instances.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.listPFlowInstances(this);
    }

    /**
     * Creates the p flow instance.
     * 
     * @param id the id
     * @return the org.semanticwb.model. p flow instance
     */
    public org.semanticwb.model.PFlowInstance createPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(id,this);
    }

    /**
     * Creates the p flow instance.
     * 
     * @return the org.semanticwb.model. p flow instance
     */
    public org.semanticwb.model.PFlowInstance createPFlowInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowInstance);
        return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(String.valueOf(id),this);
    } 

    /**
     * Removes the p flow instance.
     * 
     * @param id the id
     */
    public void removePFlowInstance(String id)
    {
        org.semanticwb.model.PFlowInstance.ClassMgr.removePFlowInstance(id, this);
    }
    
    /**
     * Checks for p flow instance.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.hasPFlowInstance(id, this);
    }

    /**
     * Gets the template group.
     * 
     * @param id the id
     * @return the template group
     */
    public org.semanticwb.model.TemplateGroup getTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.getTemplateGroup(id, this);
    }

    /**
     * List template groups.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups()
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.listTemplateGroups(this);
    }

    /**
     * Creates the template group.
     * 
     * @param id the id
     * @return the org.semanticwb.model. template group
     */
    public org.semanticwb.model.TemplateGroup createTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(id,this);
    }

    /**
     * Creates the template group.
     * 
     * @return the org.semanticwb.model. template group
     */
    public org.semanticwb.model.TemplateGroup createTemplateGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateGroup);
        return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(String.valueOf(id),this);
    } 

    /**
     * Removes the template group.
     * 
     * @param id the id
     */
    public void removeTemplateGroup(String id)
    {
        org.semanticwb.model.TemplateGroup.ClassMgr.removeTemplateGroup(id, this);
    }
    
    /**
     * Checks for template group.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.hasTemplateGroup(id, this);
    }

    /**
     * Gets the role ref.
     * 
     * @param id the id
     * @return the role ref
     */
    public org.semanticwb.model.RoleRef getRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.getRoleRef(id, this);
    }

    /**
     * List role refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return org.semanticwb.model.RoleRef.ClassMgr.listRoleRefs(this);
    }

    /**
     * Creates the role ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. role ref
     */
    public org.semanticwb.model.RoleRef createRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(id,this);
    }

    /**
     * Creates the role ref.
     * 
     * @return the org.semanticwb.model. role ref
     */
    public org.semanticwb.model.RoleRef createRoleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RoleRef);
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id),this);
    } 

    /**
     * Removes the role ref.
     * 
     * @param id the id
     */
    public void removeRoleRef(String id)
    {
        org.semanticwb.model.RoleRef.ClassMgr.removeRoleRef(id, this);
    }
    
    /**
     * Checks for role ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.hasRoleRef(id, this);
    }
}
