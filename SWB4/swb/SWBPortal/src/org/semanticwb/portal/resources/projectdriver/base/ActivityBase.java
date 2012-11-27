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
 * The Class ActivityBase.
 */
public abstract class ActivityBase extends org.semanticwb.portal.resources.projectdriver.ActivityContainer implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Searchable,org.semanticwb.model.Rankable,org.semanticwb.portal.resources.projectdriver.Period,org.semanticwb.model.Expirable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Viewable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Tagable,org.semanticwb.model.Filterable,org.semanticwb.model.PFlowRefable
{
    
    /** The Constant swbproy_Activity. */
    public static final org.semanticwb.platform.SemanticClass swbproy_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Activity");
    
    /** The Constant swbproy_hasSuccessorInv. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_hasSuccessorInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#hasSuccessorInv");
    
    /** The Constant swbproy_critical. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_critical=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#critical");
    
    /** The Constant swbproy_actType. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_actType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#actType");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swbproy_responsible. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_responsible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#responsible");
    
    /** The Constant swbproy_hasPredecessor. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_hasPredecessor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#hasPredecessor");
    
    /** The Constant swbproy_status. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#status");
    
    /** The Constant swbproy_hasParticipants. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_hasParticipants=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#hasParticipants");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Activity");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List activities.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(it, true);
        }

        /**
         * List activities.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(it, true);
        }

        /**
         * Gets the activity.
         * 
         * @param id the id
         * @param model the model
         * @return the activity
         */
        public static org.semanticwb.portal.resources.projectdriver.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the activity.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.projectdriver. activity
         */
        public static org.semanticwb.portal.resources.projectdriver.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the activity.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for activity.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivity(id, model)!=null);
        }

        /**
         * List activity by user group ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by user group ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by ass member.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by ass member.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by successor.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityBySuccessor(org.semanticwb.portal.resources.projectdriver.Activity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasSuccessorInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by successor.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityBySuccessor(org.semanticwb.portal.resources.projectdriver.Activity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasSuccessorInv,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by responsible.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResponsible(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_responsible, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by responsible.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResponsible(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_responsible,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by virtual parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by virtual parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by web page virtual child.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by web page virtual child.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by p flow ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by p flow ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by template ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by template ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by child.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by child.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by calendar ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by calendar ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by predecessor.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasPredecessor, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by predecessor.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasPredecessor,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by modified by.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by modified by.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by resource.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by resource.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by role ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by role ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by this role ass member.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by this role ass member.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by rule ref.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by rule ref.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by creator.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by creator.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by this type association.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by this type association.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by participants.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParticipants(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasParticipants, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List activity by participants.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParticipants(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasParticipants,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new activity base.
     * 
     * @param base the base
     */
    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List successors.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> listSuccessors()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(getSemanticObject().listObjectProperties(swbproy_hasSuccessorInv));
    }

    /**
     * Checks for successor.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasSuccessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbproy_hasSuccessorInv,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the successor.
     * 
     * @return the successor
     */
    public org.semanticwb.portal.resources.projectdriver.Activity getSuccessor()
    {
         org.semanticwb.portal.resources.projectdriver.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_hasSuccessorInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.projectdriver.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#getCurrentHour()
     */
    public int getCurrentHour()
    {
        //Override this method in Activity object
        return getSemanticObject().getIntProperty(swbproy_currentHour,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#setCurrentHour(int)
     */
    public void setCurrentHour(int value)
    {
        //Override this method in Activity object
        getSemanticObject().setIntProperty(swbproy_currentHour, value,false);
    }

    /**
     * Checks if is critical.
     * 
     * @return true, if is critical
     */
    public boolean isCritical()
    {
        return getSemanticObject().getBooleanProperty(swbproy_critical);
    }

    /**
     * Sets the critical.
     * 
     * @param value the new critical
     */
    public void setCritical(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbproy_critical, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#getCurrentPercentage()
     */
    public float getCurrentPercentage()
    {
        //Override this method in Activity object
        return getSemanticObject().getFloatProperty(swbproy_currentPercentage,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#setCurrentPercentage(float)
     */
    public void setCurrentPercentage(float value)
    {
        //Override this method in Activity object
        getSemanticObject().setFloatProperty(swbproy_currentPercentage, value,false);
    }

    /**
     * Gets the act type.
     * 
     * @return the act type
     */
    public String getActType()
    {
        return getSemanticObject().getProperty(swbproy_actType);
    }

    /**
     * Sets the act type.
     * 
     * @param value the new act type
     */
    public void setActType(String value)
    {
        getSemanticObject().setProperty(swbproy_actType, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#getPlannedHour()
     */
    public int getPlannedHour()
    {
        //Override this method in Activity object
        return getSemanticObject().getIntProperty(swbproy_plannedHour,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#setPlannedHour(int)
     */
    public void setPlannedHour(int value)
    {
        //Override this method in Activity object
        getSemanticObject().setIntProperty(swbproy_plannedHour, value,false);
    }

    /**
     * Sets the responsible.
     * 
     * @param value the new responsible
     */
    public void setResponsible(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swbproy_responsible, value.getSemanticObject());
    }

    /**
     * Removes the responsible.
     */
    public void removeResponsible()
    {
        getSemanticObject().removeProperty(swbproy_responsible);
    }

    /**
     * Gets the responsible.
     * 
     * @return the responsible
     */
    public org.semanticwb.model.User getResponsible()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_responsible);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#getEndDate()
     */
    public java.util.Date getEndDate()
    {
        //Override this method in Activity object
        return getSemanticObject().getDateProperty(swbproy_endDate,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#setEndDate(java.util.Date)
     */
    public void setEndDate(java.util.Date value)
    {
        //Override this method in Activity object
        getSemanticObject().setDateProperty(swbproy_endDate, value,false);
    }

    /**
     * List predecessors.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> listPredecessors()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(getSemanticObject().listObjectProperties(swbproy_hasPredecessor));
    }

    /**
     * Checks for predecessor.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbproy_hasPredecessor,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the predecessor.
     * 
     * @param value the value
     */
    public void addPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        getSemanticObject().addObjectProperty(swbproy_hasPredecessor, value.getSemanticObject());
    }

    /**
     * Removes the all predecessor.
     */
    public void removeAllPredecessor()
    {
        getSemanticObject().removeProperty(swbproy_hasPredecessor);
    }

    /**
     * Removes the predecessor.
     * 
     * @param value the value
     */
    public void removePredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        getSemanticObject().removeObjectProperty(swbproy_hasPredecessor,value.getSemanticObject());
    }

    /**
     * Gets the predecessor.
     * 
     * @return the predecessor
     */
    public org.semanticwb.portal.resources.projectdriver.Activity getPredecessor()
    {
         org.semanticwb.portal.resources.projectdriver.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_hasPredecessor);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.projectdriver.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#getStartDate()
     */
    public java.util.Date getStartDate()
    {
        //Override this method in Activity object
        return getSemanticObject().getDateProperty(swbproy_startDate,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.PeriodBase#setStartDate(java.util.Date)
     */
    public void setStartDate(java.util.Date value)
    {
        //Override this method in Activity object
        getSemanticObject().setDateProperty(swbproy_startDate, value,false);
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus()
    {
        return getSemanticObject().getProperty(swbproy_status);
    }

    /**
     * Sets the status.
     * 
     * @param value the new status
     */
    public void setStatus(String value)
    {
        getSemanticObject().setProperty(swbproy_status, value);
    }

    /**
     * List participantses.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listParticipantses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(swbproy_hasParticipants));
    }

    /**
     * Checks for participants.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasParticipants(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbproy_hasParticipants,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the participants.
     * 
     * @param value the value
     */
    public void addParticipants(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(swbproy_hasParticipants, value.getSemanticObject());
    }

    /**
     * Removes the all participants.
     */
    public void removeAllParticipants()
    {
        getSemanticObject().removeProperty(swbproy_hasParticipants);
    }

    /**
     * Removes the participants.
     * 
     * @param value the value
     */
    public void removeParticipants(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(swbproy_hasParticipants,value.getSemanticObject());
    }

    /**
     * Gets the participants.
     * 
     * @return the participants
     */
    public org.semanticwb.model.User getParticipants()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_hasParticipants);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.ActivityContainerBase#getProjectSite()
     */
    public org.semanticwb.portal.resources.projectdriver.ProjectSite getProjectSite()
    {
        return (org.semanticwb.portal.resources.projectdriver.ProjectSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
