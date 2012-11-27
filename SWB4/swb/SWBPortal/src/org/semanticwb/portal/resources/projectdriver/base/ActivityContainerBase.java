/*
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
 */
package org.semanticwb.portal.resources.projectdriver.base;


// TODO: Auto-generated Javadoc
/**
 * The Class ActivityContainerBase.
 */
public abstract class ActivityContainerBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Searchable,org.semanticwb.model.Rankable,org.semanticwb.model.Expirable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Viewable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Tagable,org.semanticwb.model.Filterable,org.semanticwb.model.PFlowRefable
{
    
    /** The Constant swbproy_ActivityContainer. */
    public static final org.semanticwb.platform.SemanticClass swbproy_ActivityContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ActivityContainer");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ActivityContainer");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List activity containers.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer>(it, true);
        }

        /**
         * List activity containers.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer>(it, true);
        }

        /**
         * Gets the activity container.
         * 
         * @param id the id
         * @param model the model
         * @return the activity container
         */
        public static org.semanticwb.portal.resources.projectdriver.ActivityContainer getActivityContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.ActivityContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the activity container.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.projectdriver. activity container
         */
        public static org.semanticwb.portal.resources.projectdriver.ActivityContainer createActivityContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.ActivityContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the activity container.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeActivityContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for activity container.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasActivityContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivityContainer(id, model)!=null);
        }

        /**
         * List activity container by user group ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by user group ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by ass member.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by ass member.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by virtual parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by virtual parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by web page virtual child.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by web page virtual child.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by template ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by template ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by p flow ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by p flow ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by child.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by child.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by calendar ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by calendar ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by modified by.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by modified by.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by resource.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by resource.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by role ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by role ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by this role ass member.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by this role ass member.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by creator.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by creator.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by rule ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by rule ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by this type association.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity container by this type association.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainerByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new activity container base.
     * 
     * @param base the base
     */
    public ActivityContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the project site.
     * 
     * @return the project site
     */
    public org.semanticwb.portal.resources.projectdriver.ProjectSite getProjectSite()
    {
        return (org.semanticwb.portal.resources.projectdriver.ProjectSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
