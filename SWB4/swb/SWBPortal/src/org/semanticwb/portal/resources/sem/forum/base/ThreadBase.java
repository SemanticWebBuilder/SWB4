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
package org.semanticwb.portal.resources.sem.forum.base;


// TODO: Auto-generated Javadoc
/**
 * The Class ThreadBase.
 */
public abstract class ThreadBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Expirable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Viewable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Indexable,org.semanticwb.model.Undeleteable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Hiddenable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable,org.semanticwb.model.Searchable,org.semanticwb.model.Rankable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant frm_thLastPostMember. */
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostMember");
    
    /** The Constant frm_SWBForum. */
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    
    /** The Constant frm_thForum. */
    public static final org.semanticwb.platform.SemanticProperty frm_thForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thForum");
    
    /** The Constant frm_thViewCount. */
    public static final org.semanticwb.platform.SemanticProperty frm_thViewCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thViewCount");
    
    /** The Constant frm_thReplyCount. */
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thReplyCount");
    
    /** The Constant frm_UserFavThread. */
    public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
    
    /** The Constant frm_hasUserFavThread. */
    public static final org.semanticwb.platform.SemanticProperty frm_hasUserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasUserFavThread");
    
    /** The Constant frm_thLastPostDate. */
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostDate");
    
    /** The Constant frm_thBody. */
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thBody");
    
    /** The Constant frm_Attachment. */
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
    
    /** The Constant frm_hasThAttachments. */
    public static final org.semanticwb.platform.SemanticProperty frm_hasThAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThAttachments");
    
    /** The Constant frm_Post. */
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    
    /** The Constant frm_hasPost. */
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasPost");
    
    /** The Constant frm_Thread. */
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List threads.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
        }

        /**
         * List threads.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
        }

        /**
         * Creates the thread.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. thread
         */
        public static org.semanticwb.portal.resources.sem.forum.Thread createThread(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Thread.ClassMgr.createThread(String.valueOf(id), model);
        }

        /**
         * Gets the thread.
         * 
         * @param id the id
         * @param model the model
         * @return the thread
         */
        public static org.semanticwb.portal.resources.sem.forum.Thread getThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the thread.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. thread
         */
        public static org.semanticwb.portal.resources.sem.forum.Thread createThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the thread.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeThread(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for thread.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (getThread(id, model)!=null);
        }

        /**
         * List thread by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, hasusergroupref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,hasusergroupref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by ass member.
         * 
         * @param hasassmemberinv the hasassmemberinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAssMember(org.semanticwb.model.AssMember hasassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, hasassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by ass member.
         * 
         * @param hasassmemberinv the hasassmemberinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAssMember(org.semanticwb.model.AssMember hasassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasassmemberinv.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,hasassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by last post member.
         * 
         * @param thlastpostmember the thlastpostmember
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLastPostMember(org.semanticwb.model.User thlastpostmember,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_thLastPostMember, thlastpostmember.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by last post member.
         * 
         * @param thlastpostmember the thlastpostmember
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLastPostMember(org.semanticwb.model.User thlastpostmember)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(thlastpostmember.getSemanticObject().getModel().listSubjectsByClass(frm_thLastPostMember,thlastpostmember.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by virtual parent.
         * 
         * @param haswebpagevirtualparent the haswebpagevirtualparent
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, haswebpagevirtualparent.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by virtual parent.
         * 
         * @param haswebpagevirtualparent the haswebpagevirtualparent
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualparent.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,haswebpagevirtualparent.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by web page virtual child.
         * 
         * @param haswebpagevirtualchild the haswebpagevirtualchild
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, haswebpagevirtualchild.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by web page virtual child.
         * 
         * @param haswebpagevirtualchild the haswebpagevirtualchild
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualchild.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,haswebpagevirtualchild.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by p flow ref.
         * 
         * @param haspflowref the haspflowref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPFlowRef(org.semanticwb.model.PFlowRef haspflowref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, haspflowref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by p flow ref.
         * 
         * @param haspflowref the haspflowref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPFlowRef(org.semanticwb.model.PFlowRef haspflowref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haspflowref.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,haspflowref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by template ref.
         * 
         * @param hastemplateref the hastemplateref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, hastemplateref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by template ref.
         * 
         * @param hastemplateref the hastemplateref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hastemplateref.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,hastemplateref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by child.
         * 
         * @param haswebpagechild the haswebpagechild
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByChild(org.semanticwb.model.WebPage haswebpagechild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, haswebpagechild.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by child.
         * 
         * @param haswebpagechild the haswebpagechild
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByChild(org.semanticwb.model.WebPage haswebpagechild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haswebpagechild.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,haswebpagechild.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, hascalendarref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,hascalendarref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by parent.
         * 
         * @param webpageparent the webpageparent
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByParent(org.semanticwb.model.WebPage webpageparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, webpageparent.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by parent.
         * 
         * @param webpageparent the webpageparent
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByParent(org.semanticwb.model.WebPage webpageparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(webpageparent.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,webpageparent.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by resource.
         * 
         * @param hasresource the hasresource
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByResource(org.semanticwb.model.Resource hasresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, hasresource.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by resource.
         * 
         * @param hasresource the hasresource
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByResource(org.semanticwb.model.Resource hasresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasresource.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,hasresource.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by forum.
         * 
         * @param thforum the thforum
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByForum(org.semanticwb.portal.resources.sem.forum.SWBForum thforum,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_thForum, thforum.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by forum.
         * 
         * @param thforum the thforum
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByForum(org.semanticwb.portal.resources.sem.forum.SWBForum thforum)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(thforum.getSemanticObject().getModel().listSubjectsByClass(frm_thForum,thforum.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by user fav thread.
         * 
         * @param hasuserfavthread the hasuserfavthread
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread hasuserfavthread,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasUserFavThread, hasuserfavthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by user fav thread.
         * 
         * @param hasuserfavthread the hasuserfavthread
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread hasuserfavthread)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasuserfavthread.getSemanticObject().getModel().listSubjectsByClass(frm_hasUserFavThread,hasuserfavthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by role ref.
         * 
         * @param hasroleref the hasroleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, hasroleref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by role ref.
         * 
         * @param hasroleref the hasroleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,hasroleref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by this role ass member.
         * 
         * @param hasthisroleassmemberinv the hasthisroleassmemberinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, hasthisroleassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by this role ass member.
         * 
         * @param hasthisroleassmemberinv the hasthisroleassmemberinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasthisroleassmemberinv.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,hasthisroleassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by attachment.
         * 
         * @param hasthattachments the hasthattachments
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAttachment(org.semanticwb.portal.resources.sem.forum.Attachment hasthattachments,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasThAttachments, hasthattachments.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by attachment.
         * 
         * @param hasthattachments the hasthattachments
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAttachment(org.semanticwb.portal.resources.sem.forum.Attachment hasthattachments)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasthattachments.getSemanticObject().getModel().listSubjectsByClass(frm_hasThAttachments,hasthattachments.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjectsByClass(swb_creator,creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, hasruleref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,hasruleref.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by post.
         * 
         * @param haspost the haspost
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPost(org.semanticwb.portal.resources.sem.forum.Post haspost,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasPost, haspost.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by post.
         * 
         * @param haspost the haspost
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPost(org.semanticwb.portal.resources.sem.forum.Post haspost)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haspost.getSemanticObject().getModel().listSubjectsByClass(frm_hasPost,haspost.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by this type association.
         * 
         * @param hasthistypeassociationinv the hasthistypeassociationinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, hasthistypeassociationinv.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List thread by this type association.
         * 
         * @param hasthistypeassociationinv the hasthistypeassociationinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasthistypeassociationinv.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,hasthistypeassociationinv.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new thread base.
     * 
     * @param base the base
     */
    public ThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the last post member.
     * 
     * @param value the new last post member
     */
    public void setLastPostMember(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(frm_thLastPostMember, value.getSemanticObject());
    }

    /**
     * Removes the last post member.
     */
    public void removeLastPostMember()
    {
        getSemanticObject().removeProperty(frm_thLastPostMember);
    }

    /**
     * Gets the last post member.
     * 
     * @return the last post member
     */
    public org.semanticwb.model.User getLastPostMember()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thLastPostMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the forum.
     * 
     * @param value the new forum
     */
    public void setForum(org.semanticwb.portal.resources.sem.forum.SWBForum value)
    {
        getSemanticObject().setObjectProperty(frm_thForum, value.getSemanticObject());
    }

    /**
     * Removes the forum.
     */
    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thForum);
    }

    /**
     * Gets the forum.
     * 
     * @return the forum
     */
    public org.semanticwb.portal.resources.sem.forum.SWBForum getForum()
    {
         org.semanticwb.portal.resources.sem.forum.SWBForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thForum);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.SWBForum)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the view count.
     * 
     * @return the view count
     */
    public int getViewCount()
    {
        return getSemanticObject().getIntProperty(frm_thViewCount);
    }

    /**
     * Sets the view count.
     * 
     * @param value the new view count
     */
    public void setViewCount(int value)
    {
        getSemanticObject().setIntProperty(frm_thViewCount, value);
    }

    /**
     * Gets the reply count.
     * 
     * @return the reply count
     */
    public int getReplyCount()
    {
        return getSemanticObject().getIntProperty(frm_thReplyCount);
    }

    /**
     * Sets the reply count.
     * 
     * @param value the new reply count
     */
    public void setReplyCount(int value)
    {
        getSemanticObject().setIntProperty(frm_thReplyCount, value);
    }

    /**
     * List user fav threads.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(getSemanticObject().listObjectProperties(frm_hasUserFavThread));
    }

    /**
     * Checks for user fav thread.
     * 
     * @param userfavthread the userfavthread
     * @return true, if successful
     */
    public boolean hasUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread userfavthread)
    {
        boolean ret=false;
        if(userfavthread!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasUserFavThread,userfavthread.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the user fav thread.
     * 
     * @return the user fav thread
     */
    public org.semanticwb.portal.resources.sem.forum.UserFavThread getUserFavThread()
    {
         org.semanticwb.portal.resources.sem.forum.UserFavThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasUserFavThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.UserFavThread)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the last post date.
     * 
     * @return the last post date
     */
    public java.util.Date getLastPostDate()
    {
        return getSemanticObject().getDateProperty(frm_thLastPostDate);
    }

    /**
     * Sets the last post date.
     * 
     * @param value the new last post date
     */
    public void setLastPostDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(frm_thLastPostDate, value);
    }

    /**
     * Gets the body.
     * 
     * @return the body
     */
    public String getBody()
    {
        return getSemanticObject().getProperty(frm_thBody);
    }

    /**
     * Sets the body.
     * 
     * @param value the new body
     */
    public void setBody(String value)
    {
        getSemanticObject().setProperty(frm_thBody, value);
    }

    /**
     * List attachments.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(getSemanticObject().listObjectProperties(frm_hasThAttachments));
    }

    /**
     * Checks for attachment.
     * 
     * @param attachment the attachment
     * @return true, if successful
     */
    public boolean hasAttachment(org.semanticwb.portal.resources.sem.forum.Attachment attachment)
    {
        boolean ret=false;
        if(attachment!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasThAttachments,attachment.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the attachment.
     * 
     * @return the attachment
     */
    public org.semanticwb.portal.resources.sem.forum.Attachment getAttachment()
    {
         org.semanticwb.portal.resources.sem.forum.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasThAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Attachment)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List posts.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(getSemanticObject().listObjectProperties(frm_hasPost));
    }

    /**
     * Checks for post.
     * 
     * @param post the post
     * @return true, if successful
     */
    public boolean hasPost(org.semanticwb.portal.resources.sem.forum.Post post)
    {
        boolean ret=false;
        if(post!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasPost,post.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the post.
     * 
     * @return the post
     */
    public org.semanticwb.portal.resources.sem.forum.Post getPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
