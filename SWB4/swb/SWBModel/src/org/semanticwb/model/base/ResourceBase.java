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
    * Un recurso es un componente en una Página Web con el cual el usuario tiene interacción.
    */
public abstract class ResourceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Searchable,org.semanticwb.model.Deviceable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Campable,org.semanticwb.model.Viewable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Localeable,org.semanticwb.model.Tagable,org.semanticwb.model.Hitable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Sortable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Indexable,org.semanticwb.model.XMLable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Priorityable
{
   
   /** Objeto por el cual se define un filtro para que un recurso o componente se pueda presentar o no dentro de la estructura del sitio. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
    
    /** The Constant swb_resourceFilter. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceFilter");
   
   /** Referencia a un objeto de tipo Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    
    /** The Constant swb_hasResourceRefInv. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRefInv");
   
   /** Objeto por medio del cual se define un tipo de componente o recurso. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    
    /** The Constant swb_resourceType. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceType");
   /**
   * Un recurso semántico es un componente con el cual el usuario interactúa. El recurso puede manipular objetos de persistencia y sus propiedades
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBSemanticResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBSemanticResource");
    
    /** The Constant swb_resourceData. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceData");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    
    /** The Constant swb_pflowInstance. */
    public static final org.semanticwb.platform.SemanticProperty swb_pflowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflowInstance");
   
   /** Objeto que define una agripacion de componentes o recursos de un mismo tipo. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    
    /** The Constant swb_resourceSubType. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceSubType");
   
   /** Interfaz que define propiedades para los elementos que pueden contener recursos. */
    public static final org.semanticwb.platform.SemanticClass swb_Resourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resourceable");
    
    /** The Constant swb_hasResourceable. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceable");
    
    /** The Constant swb_resourceWindow. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceWindow");
   
   /** Un recurso es un componente en una Página Web con el cual el usuario tiene interacción. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of Resource for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.Resource
        */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResources(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Resource for all models
       * @return Iterator of org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResources()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(it, true);
        }

        /**
         * Creates the resource.
         * 
         * @param model the model
         * @return the org.semanticwb.model. resource
         */
        public static org.semanticwb.model.Resource createResource(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Resource.ClassMgr.createResource(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Resource
       * @param id Identifier for org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.Resource
       * @return A org.semanticwb.model.Resource
       */
        public static org.semanticwb.model.Resource getResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Resource
       * @param id Identifier for org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.Resource
       * @return A org.semanticwb.model.Resource
       */
        public static org.semanticwb.model.Resource createResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Resource
       * @param id Identifier for org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.Resource
       */
        public static void removeResource(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Resource
       * @param id Identifier for org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.Resource
       * @return true if the org.semanticwb.model.Resource exists, false otherwise
       */

        public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResource(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Camp
       * @param value Camp of the type org.semanticwb.model.Camp
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_camp, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Camp
       * @param value Camp of the type org.semanticwb.model.Camp
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_camp,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceFilter
       * @param value ResourceFilter of the type org.semanticwb.model.ResourceFilter
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceFilter, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceFilter
       * @param value ResourceFilter of the type org.semanticwb.model.ResourceFilter
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceFilter,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceRefInv
       * @param value ResourceRefInv of the type org.semanticwb.model.ResourceRef
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceRefInv
       * @param value ResourceRefInv of the type org.semanticwb.model.ResourceRef
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceType
       * @param value ResourceType of the type org.semanticwb.model.ResourceType
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceType
       * @param value ResourceType of the type org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.model.PFlowInstance
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pflowInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.model.PFlowInstance
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pflowInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceSubType
       * @param value ResourceSubType of the type org.semanticwb.model.ResourceSubType
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceSubType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined ResourceSubType
       * @param value ResourceSubType of the type org.semanticwb.model.ResourceSubType
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceSubType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Device
       * @param value Device of the type org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_device, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Device
       * @param value Device of the type org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_device,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Resourceable
       * @param value Resourceable of the type org.semanticwb.model.Resourceable
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceable, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Resourceable
       * @param value Resourceable of the type org.semanticwb.model.Resourceable
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceable,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Resource with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.model.Resource
       */

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a ResourceBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the Resource
    */
    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.model.UserGroupRef
   * @return A GenericIterator with all the org.semanticwb.model.UserGroupRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

   /**
    * Gets true if has a UserGroupRef.
    * 
    * @param value org.semanticwb.model.UserGroupRef to verify
    * @return true if the org.semanticwb.model.UserGroupRef exists, false otherwise
    */
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
    * Gets all the UserGroupRefs inherits.
    * 
    * @return A GenericIterator with all the org.semanticwb.model.UserGroupRef
    */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }
   
   /**
    * Adds a UserGroupRef.
    * 
    * @param value org.semanticwb.model.UserGroupRef to add
    */

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }
   
   /**
    * Removes all the UserGroupRef.
    */

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }
   
   /**
    * Removes a UserGroupRef.
    * 
    * @param value org.semanticwb.model.UserGroupRef to remove
    */

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
    }

   /**
    * Gets the UserGroupRef.
    * 
    * @return a org.semanticwb.model.UserGroupRef
    */
    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property Camp.
    * 
    * @param value Camp to set
    */

    public void setCamp(org.semanticwb.model.Camp value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_camp, value.getSemanticObject());
        }else
        {
            removeCamp();
        }
    }
   
   /**
    * Remove the value for Camp property.
    */

    public void removeCamp()
    {
        getSemanticObject().removeProperty(swb_camp);
    }

   /**
    * Gets the Camp.
    * 
    * @return a org.semanticwb.model.Camp
    */
    public org.semanticwb.model.Camp getCamp()
    {
         org.semanticwb.model.Camp ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_camp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Camp)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Xml property.
 * 
 * @return String with the Xml
 */
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

/**
 * Sets the Xml property.
 * 
 * @param value long with the Xml
 */
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }

/**
 * Gets the Hits property.
 * 
 * @return long with the Hits
 */
    public long getHits()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(swb_hits,false);
    }

/**
 * Sets the Hits property.
 * 
 * @param value long with the Hits
 */
    public void setHits(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(swb_hits, value,false);
    }

/**
 * Gets the XmlConf property.
 * 
 * @return String with the XmlConf
 */
    public String getXmlConf()
    {
        return getSemanticObject().getProperty(swb_xmlConf);
    }

/**
 * Sets the XmlConf property.
 * 
 * @param value long with the XmlConf
 */
    public void setXmlConf(String value)
    {
        getSemanticObject().setProperty(swb_xmlConf, value);
    }

/**
 * Gets the Tags property.
 * 
 * @return String with the Tags
 */
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
 * Sets the Tags property.
 * 
 * @param value long with the Tags
 */
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
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
    * Sets the value for the property ResourceFilter.
    * 
    * @param value ResourceFilter to set
    */

    public void setResourceFilter(org.semanticwb.model.ResourceFilter value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceFilter, value.getSemanticObject());
        }else
        {
            removeResourceFilter();
        }
    }
   
   /**
    * Remove the value for ResourceFilter property.
    */

    public void removeResourceFilter()
    {
        getSemanticObject().removeProperty(swb_resourceFilter);
    }

   /**
    * Gets the ResourceFilter.
    * 
    * @return a org.semanticwb.model.ResourceFilter
    */
    public org.semanticwb.model.ResourceFilter getResourceFilter()
    {
         org.semanticwb.model.ResourceFilter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceFilter);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceFilter)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.model.CalendarRef
   * @return A GenericIterator with all the org.semanticwb.model.CalendarRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

   /**
    * Gets true if has a CalendarRef.
    * 
    * @param value org.semanticwb.model.CalendarRef to verify
    * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
    */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a CalendarRef.
    * 
    * @param value org.semanticwb.model.CalendarRef to add
    */

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }
   
   /**
    * Removes all the CalendarRef.
    */

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }
   
   /**
    * Removes a CalendarRef.
    * 
    * @param value org.semanticwb.model.CalendarRef to remove
    */

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

   /**
    * Gets the CalendarRef.
    * 
    * @return a org.semanticwb.model.CalendarRef
    */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.ResourceRef
   * @return A GenericIterator with all the org.semanticwb.model.ResourceRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(getSemanticObject().listObjectProperties(swb_hasResourceRefInv));
    }

   /**
    * Gets true if has a ResourceRefInv.
    * 
    * @param value org.semanticwb.model.ResourceRef to verify
    * @return true if the org.semanticwb.model.ResourceRef exists, false otherwise
    */
    public boolean hasResourceRefInv(org.semanticwb.model.ResourceRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
    * Gets the ResourceRefInv.
    * 
    * @return a org.semanticwb.model.ResourceRef
    */
    public org.semanticwb.model.ResourceRef getResourceRefInv()
    {
         org.semanticwb.model.ResourceRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceRef)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property ResourceType.
    * 
    * @param value ResourceType to set
    */

    public void setResourceType(org.semanticwb.model.ResourceType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceType, value.getSemanticObject());
        }else
        {
            removeResourceType();
        }
    }
   
   /**
    * Remove the value for ResourceType property.
    */

    public void removeResourceType()
    {
        getSemanticObject().removeProperty(swb_resourceType);
    }

   /**
    * Gets the ResourceType.
    * 
    * @return a org.semanticwb.model.ResourceType
    */
    public org.semanticwb.model.ResourceType getResourceType()
    {
         org.semanticwb.model.ResourceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceType)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the MaxViews property.
 * 
 * @return long with the MaxViews
 */
    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

/**
 * Sets the MaxViews property.
 * 
 * @param value long with the MaxViews
 */
    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }

/**
 * Gets the Index property.
 * 
 * @return int with the Index
 */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
 * Sets the Index property.
 * 
 * @param value long with the Index
 */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
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
 * Gets the Expiration property.
 * 
 * @return java.util.Date with the Expiration
 */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
 * Sets the Expiration property.
 * 
 * @param value long with the Expiration
 */
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

    /**
     * Sets the resource data.
     * 
     * @param value the new resource data
     */
    public void setResourceData(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swb_resourceData, value);
    }

    /**
     * Removes the resource data.
     */
    public void removeResourceData()
    {
        getSemanticObject().removeProperty(swb_resourceData);
    }

/**
 * Gets the ResourceData property.
 * 
 * @return the value for the property as org.semanticwb.platform.SemanticObject
 */
    public org.semanticwb.platform.SemanticObject getResourceData()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_resourceData);
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
 * Gets the MaxHits property.
 * 
 * @return long with the MaxHits
 */
    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(swb_maxHits);
    }

/**
 * Sets the MaxHits property.
 * 
 * @param value long with the MaxHits
 */
    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(swb_maxHits, value);
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
    * Sets the value for the property PflowInstance.
    * 
    * @param value PflowInstance to set
    */

    public void setPflowInstance(org.semanticwb.model.PFlowInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pflowInstance, value.getSemanticObject());
        }else
        {
            removePflowInstance();
        }
    }
   
   /**
    * Remove the value for PflowInstance property.
    */

    public void removePflowInstance()
    {
        getSemanticObject().removeProperty(swb_pflowInstance);
    }

   /**
    * Gets the PflowInstance.
    * 
    * @return a org.semanticwb.model.PFlowInstance
    */
    public org.semanticwb.model.PFlowInstance getPflowInstance()
    {
         org.semanticwb.model.PFlowInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pflowInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowInstance)obj.createGenericInstance();
         }
         return ret;
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
    * Sets the value for the property ResourceSubType.
    * 
    * @param value ResourceSubType to set
    */

    public void setResourceSubType(org.semanticwb.model.ResourceSubType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceSubType, value.getSemanticObject());
        }else
        {
            removeResourceSubType();
        }
    }
   
   /**
    * Remove the value for ResourceSubType property.
    */

    public void removeResourceSubType()
    {
        getSemanticObject().removeProperty(swb_resourceSubType);
    }

   /**
    * Gets the ResourceSubType.
    * 
    * @return a org.semanticwb.model.ResourceSubType
    */
    public org.semanticwb.model.ResourceSubType getResourceSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Priority property.
 * 
 * @return int with the Priority
 */
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

/**
 * Sets the Priority property.
 * 
 * @param value long with the Priority
 */
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }
   /**
   * Gets all the org.semanticwb.model.RoleRef
   * @return A GenericIterator with all the org.semanticwb.model.RoleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

   /**
    * Gets true if has a RoleRef.
    * 
    * @param value org.semanticwb.model.RoleRef to verify
    * @return true if the org.semanticwb.model.RoleRef exists, false otherwise
    */
    public boolean hasRoleRef(org.semanticwb.model.RoleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
    * Gets all the RoleRefs inherits.
    * 
    * @return A GenericIterator with all the org.semanticwb.model.RoleRef
    */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }
   
   /**
    * Adds a RoleRef.
    * 
    * @param value org.semanticwb.model.RoleRef to add
    */

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }
   
   /**
    * Removes all the RoleRef.
    */

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }
   
   /**
    * Removes a RoleRef.
    * 
    * @param value org.semanticwb.model.RoleRef to remove
    */

    public void removeRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,value.getSemanticObject());
    }

   /**
    * Gets the RoleRef.
    * 
    * @return a org.semanticwb.model.RoleRef
    */
    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property Device.
    * 
    * @param value Device to set
    */

    public void setDevice(org.semanticwb.model.Device value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_device, value.getSemanticObject());
        }else
        {
            removeDevice();
        }
    }
   
   /**
    * Remove the value for Device property.
    */

    public void removeDevice()
    {
        getSemanticObject().removeProperty(swb_device);
    }

   /**
    * Gets the Device.
    * 
    * @return a org.semanticwb.model.Device
    */
    public org.semanticwb.model.Device getDevice()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_device);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Views property.
 * 
 * @return long with the Views
 */
    public long getViews()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

/**
 * Sets the Views property.
 * 
 * @param value long with the Views
 */
    public void setViews(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }
   /**
   * Gets all the org.semanticwb.model.Resourceable
   * @return A GenericIterator with all the org.semanticwb.model.Resourceable
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable> listResourceables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable>(getSemanticObject().listObjectProperties(swb_hasResourceable));
    }

   /**
    * Gets true if has a Resourceable.
    * 
    * @param value org.semanticwb.model.Resourceable to verify
    * @return true if the org.semanticwb.model.Resourceable exists, false otherwise
    */
    public boolean hasResourceable(org.semanticwb.model.Resourceable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceable,value.getSemanticObject());
        }
        return ret;
    }

   /**
    * Gets the Resourceable.
    * 
    * @return a org.semanticwb.model.Resourceable
    */
    public org.semanticwb.model.Resourceable getResourceable()
    {
         org.semanticwb.model.Resourceable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceable);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resourceable)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.model.RuleRef
   * @return A GenericIterator with all the org.semanticwb.model.RuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

   /**
    * Gets true if has a RuleRef.
    * 
    * @param value org.semanticwb.model.RuleRef to verify
    * @return true if the org.semanticwb.model.RuleRef exists, false otherwise
    */
    public boolean hasRuleRef(org.semanticwb.model.RuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
    * Gets all the RuleRefs inherits.
    * 
    * @return A GenericIterator with all the org.semanticwb.model.RuleRef
    */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }
   
   /**
    * Adds a RuleRef.
    * 
    * @param value org.semanticwb.model.RuleRef to add
    */

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }
   
   /**
    * Removes all the RuleRef.
    */

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }
   
   /**
    * Removes a RuleRef.
    * 
    * @param value org.semanticwb.model.RuleRef to remove
    */

    public void removeRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,value.getSemanticObject());
    }

   /**
    * Gets the RuleRef.
    * 
    * @return a org.semanticwb.model.RuleRef
    */
    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the ResourceWindow property.
 * 
 * @return boolean with the ResourceWindow
 */
    public boolean isResourceWindow()
    {
        return getSemanticObject().getBooleanProperty(swb_resourceWindow);
    }

/**
 * Sets the ResourceWindow property.
 * 
 * @param value long with the ResourceWindow
 */
    public void setResourceWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_resourceWindow, value);
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
    * Gets the WebSite.
    * 
    * @return a instance of org.semanticwb.model.WebSite
    */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
