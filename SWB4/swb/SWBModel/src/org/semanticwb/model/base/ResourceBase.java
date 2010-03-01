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
 * The Class ResourceBase.
 */
public abstract class ResourceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Hitable,org.semanticwb.model.Deviceable,org.semanticwb.model.Filterable,org.semanticwb.model.Expirable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Searchable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Viewable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Tagable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Campable,org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Referensable,org.semanticwb.model.Priorityable,org.semanticwb.model.XMLable,org.semanticwb.model.RoleRefable
{
    
    /** The Constant swb_ResourceFilter. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
    
    /** The Constant swb_resourceFilter. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceFilter");
    
    /** The Constant swb_ResourceRef. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    
    /** The Constant swb_hasResourceRefInv. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRefInv");
    
    /** The Constant swb_ResourceType. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    
    /** The Constant swb_resourceType. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceType");
    
    /** The Constant swb_SWBSemanticResource. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBSemanticResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBSemanticResource");
    
    /** The Constant swb_resourceData. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceData");
    
    /** The Constant swb_PFlowInstance. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    
    /** The Constant swb_pflowInstance. */
    public static final org.semanticwb.platform.SemanticProperty swb_pflowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflowInstance");
    
    /** The Constant swb_ResourceSubType. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    
    /** The Constant swb_resourceSubType. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceSubType");
    
    /** The Constant swb_Resourceable. */
    public static final org.semanticwb.platform.SemanticClass swb_Resourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resourceable");
    
    /** The Constant swb_hasResourceable. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceable");
    
    /** The Constant swb_resourceWindow. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceWindow");
    
    /** The Constant swb_Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List resources.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResources(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(it, true);
        }

        /**
         * List resources.
         * 
         * @return the java.util. iterator
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
         * Gets the resource.
         * 
         * @param id the id
         * @param model the model
         * @return the resource
         */
        public static org.semanticwb.model.Resource getResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the resource.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. resource
         */
        public static org.semanticwb.model.Resource createResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the resource.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeResource(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for resource.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResource(id, model)!=null);
        }

        /**
         * List resource by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by camp.
         * 
         * @param camp the camp
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp camp,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_camp, camp.getSemanticObject()));
            return it;
        }

        /**
         * List resource by camp.
         * 
         * @param camp the camp
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp camp)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(camp.getSemanticObject().getModel().listSubjects(swb_camp,camp.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource filter.
         * 
         * @param resourcefilter the resourcefilter
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter resourcefilter,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_resourceFilter, resourcefilter.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource filter.
         * 
         * @param resourcefilter the resourcefilter
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter resourcefilter)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(resourcefilter.getSemanticObject().getModel().listSubjects(swb_resourceFilter,resourcefilter.getSemanticObject()));
            return it;
        }

        /**
         * List resource by language.
         * 
         * @param language the language
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_language, language.getSemanticObject()));
            return it;
        }

        /**
         * List resource by language.
         * 
         * @param language the language
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language language)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjects(swb_language,language.getSemanticObject()));
            return it;
        }

        /**
         * List resource by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource ref inv.
         * 
         * @param hasresourcerefinv the hasresourcerefinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef hasresourcerefinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasResourceRefInv, hasresourcerefinv.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource ref inv.
         * 
         * @param hasresourcerefinv the hasresourcerefinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef hasresourcerefinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasresourcerefinv.getSemanticObject().getModel().listSubjects(swb_hasResourceRefInv,hasresourcerefinv.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource type.
         * 
         * @param resourcetype the resourcetype
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType resourcetype,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_resourceType, resourcetype.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource type.
         * 
         * @param resourcetype the resourcetype
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType resourcetype)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(resourcetype.getSemanticObject().getModel().listSubjects(swb_resourceType,resourcetype.getSemanticObject()));
            return it;
        }

        /**
         * List resource by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List resource by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List resource by pflow instance.
         * 
         * @param pflowinstance the pflowinstance
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance pflowinstance,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_pflowInstance, pflowinstance.getSemanticObject()));
            return it;
        }

        /**
         * List resource by pflow instance.
         * 
         * @param pflowinstance the pflowinstance
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance pflowinstance)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(pflowinstance.getSemanticObject().getModel().listSubjects(swb_pflowInstance,pflowinstance.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource sub type.
         * 
         * @param resourcesubtype the resourcesubtype
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType resourcesubtype,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_resourceSubType, resourcesubtype.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resource sub type.
         * 
         * @param resourcesubtype the resourcesubtype
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType resourcesubtype)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(resourcesubtype.getSemanticObject().getModel().listSubjects(swb_resourceSubType,resourcesubtype.getSemanticObject()));
            return it;
        }

        /**
         * List resource by role ref.
         * 
         * @param hasroleref the hasroleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRoleRef, hasroleref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by role ref.
         * 
         * @param hasroleref the hasroleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(swb_hasRoleRef,hasroleref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by device.
         * 
         * @param device the device
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device device,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_device, device.getSemanticObject()));
            return it;
        }

        /**
         * List resource by device.
         * 
         * @param device the device
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device device)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(device.getSemanticObject().getModel().listSubjects(swb_device,device.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resourceable.
         * 
         * @param hasresourceable the hasresourceable
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable hasresourceable,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasResourceable, hasresourceable.getSemanticObject()));
            return it;
        }

        /**
         * List resource by resourceable.
         * 
         * @param hasresourceable the hasresourceable
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable hasresourceable)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasresourceable.getSemanticObject().getModel().listSubjects(swb_hasResourceable,hasresourceable.getSemanticObject()));
            return it;
        }

        /**
         * List resource by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List resource by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List resource by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRuleRef, hasruleref.getSemanticObject()));
            return it;
        }

        /**
         * List resource by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(swb_hasRuleRef,hasruleref.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new resource base.
     * 
     * @param base the base
     */
    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#listUserGroupRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#hasUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        boolean ret=false;
        if(usergroupref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#listInheritUserGroupRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#addUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#removeAllUserGroupRef()
     */
    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#removeUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#getUserGroupRef()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CampableBase#setCamp(org.semanticwb.model.Camp)
     */
    public void setCamp(org.semanticwb.model.Camp value)
    {
        getSemanticObject().setObjectProperty(swb_camp, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CampableBase#removeCamp()
     */
    public void removeCamp()
    {
        getSemanticObject().removeProperty(swb_camp);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CampableBase#getCamp()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.HitableBase#getHits()
     */
    public long getHits()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(swb_hits,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.HitableBase#setHits(long)
     */
    public void setHits(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(swb_hits, value,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.XMLableBase#getXml()
     */
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.XMLableBase#setXml(java.lang.String)
     */
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.XMLConfableBase#getXmlConf()
     */
    public String getXmlConf()
    {
        return getSemanticObject().getProperty(swb_xmlConf);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.XMLConfableBase#setXmlConf(java.lang.String)
     */
    public void setXmlConf(String value)
    {
        getSemanticObject().setProperty(swb_xmlConf, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TagableBase#getTags()
     */
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TagableBase#setTags(java.lang.String)
     */
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#isActive()
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#setActive(boolean)
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#isDeleted()
     */
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#setDeleted(boolean)
     */
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    /**
     * Sets the resource filter.
     * 
     * @param value the new resource filter
     */
    public void setResourceFilter(org.semanticwb.model.ResourceFilter value)
    {
        getSemanticObject().setObjectProperty(swb_resourceFilter, value.getSemanticObject());
    }

    /**
     * Removes the resource filter.
     */
    public void removeResourceFilter()
    {
        getSemanticObject().removeProperty(swb_resourceFilter);
    }

    /**
     * Gets the resource filter.
     * 
     * @return the resource filter
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#setLanguage(org.semanticwb.model.Language)
     */
    public void setLanguage(org.semanticwb.model.Language value)
    {
        getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#removeLanguage()
     */
    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#getLanguage()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#listCalendarRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#hasCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        boolean ret=false;
        if(calendarref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#addCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeAllCalendarRef()
     */
    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#getCalendarRef()
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
     * List resource ref invs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(getSemanticObject().listObjectProperties(swb_hasResourceRefInv));
    }

    /**
     * Checks for resource ref inv.
     * 
     * @param resourceref the resourceref
     * @return true, if successful
     */
    public boolean hasResourceRefInv(org.semanticwb.model.ResourceRef resourceref)
    {
        boolean ret=false;
        if(resourceref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceRefInv,resourceref.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the resource ref inv.
     * 
     * @return the resource ref inv
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
     * Sets the resource type.
     * 
     * @param value the new resource type
     */
    public void setResourceType(org.semanticwb.model.ResourceType value)
    {
        getSemanticObject().setObjectProperty(swb_resourceType, value.getSemanticObject());
    }

    /**
     * Removes the resource type.
     */
    public void removeResourceType()
    {
        getSemanticObject().removeProperty(swb_resourceType);
    }

    /**
     * Gets the resource type.
     * 
     * @return the resource type
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#getMaxViews()
     */
    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#setMaxViews(long)
     */
    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#getIndex()
     */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#setIndex(int)
     */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#getExpiration()
     */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#setExpiration(java.util.Date)
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
     * Gets the resource data.
     * 
     * @return the resource data
     */
    public org.semanticwb.platform.SemanticObject getResourceData()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_resourceData);
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.HitableBase#getMaxHits()
     */
    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(swb_maxHits);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.HitableBase#setMaxHits(long)
     */
    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(swb_maxHits, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Sets the pflow instance.
     * 
     * @param value the new pflow instance
     */
    public void setPflowInstance(org.semanticwb.model.PFlowInstance value)
    {
        getSemanticObject().setObjectProperty(swb_pflowInstance, value.getSemanticObject());
    }

    /**
     * Removes the pflow instance.
     */
    public void removePflowInstance()
    {
        getSemanticObject().removeProperty(swb_pflowInstance);
    }

    /**
     * Gets the pflow instance.
     * 
     * @return the pflow instance
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IndexableBase#isIndexable()
     */
    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IndexableBase#setIndexable(boolean)
     */
    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }

    /**
     * Sets the resource sub type.
     * 
     * @param value the new resource sub type
     */
    public void setResourceSubType(org.semanticwb.model.ResourceSubType value)
    {
        getSemanticObject().setObjectProperty(swb_resourceSubType, value.getSemanticObject());
    }

    /**
     * Removes the resource sub type.
     */
    public void removeResourceSubType()
    {
        getSemanticObject().removeProperty(swb_resourceSubType);
    }

    /**
     * Gets the resource sub type.
     * 
     * @return the resource sub type
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PriorityableBase#getPriority()
     */
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PriorityableBase#setPriority(int)
     */
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#listRoleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#hasRoleRef(org.semanticwb.model.RoleRef)
     */
    public boolean hasRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        boolean ret=false;
        if(roleref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#listInheritRoleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#addRoleRef(org.semanticwb.model.RoleRef)
     */
    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#removeAllRoleRef()
     */
    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#removeRoleRef(org.semanticwb.model.RoleRef)
     */
    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#getRoleRef()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DeviceableBase#setDevice(org.semanticwb.model.Device)
     */
    public void setDevice(org.semanticwb.model.Device value)
    {
        getSemanticObject().setObjectProperty(swb_device, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DeviceableBase#removeDevice()
     */
    public void removeDevice()
    {
        getSemanticObject().removeProperty(swb_device);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DeviceableBase#getDevice()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#getViews()
     */
    public long getViews()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#setViews(long)
     */
    public void setViews(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }

    /**
     * List resourceables.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable> listResourceables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable>(getSemanticObject().listObjectProperties(swb_hasResourceable));
    }

    /**
     * Checks for resourceable.
     * 
     * @param resourceable the resourceable
     * @return true, if successful
     */
    public boolean hasResourceable(org.semanticwb.model.Resourceable resourceable)
    {
        boolean ret=false;
        if(resourceable!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceable,resourceable.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the resourceable.
     * 
     * @return the resourceable
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#listRuleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#hasRuleRef(org.semanticwb.model.RuleRef)
     */
    public boolean hasRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        boolean ret=false;
        if(ruleref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#listInheritRuleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#addRuleRef(org.semanticwb.model.RuleRef)
     */
    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#removeAllRuleRef()
     */
    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#removeRuleRef(org.semanticwb.model.RuleRef)
     */
    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#getRuleRef()
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
     * Checks if is resource window.
     * 
     * @return true, if is resource window
     */
    public boolean isResourceWindow()
    {
        return getSemanticObject().getBooleanProperty(swb_resourceWindow);
    }

    /**
     * Sets the resource window.
     * 
     * @param value the new resource window
     */
    public void setResourceWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_resourceWindow, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
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
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
