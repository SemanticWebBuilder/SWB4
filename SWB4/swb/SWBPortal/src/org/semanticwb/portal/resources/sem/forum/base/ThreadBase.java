package org.semanticwb.portal.resources.sem.forum.base;


public abstract class ThreadBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Expirable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Viewable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Indexable,org.semanticwb.model.Undeleteable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Hiddenable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable,org.semanticwb.model.Searchable,org.semanticwb.model.Rankable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostMember");
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thViewCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thViewCount");
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thReplyCount");
    public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasUserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasUserFavThread");
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostDate");
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thBody");
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
    public static final org.semanticwb.platform.SemanticProperty frm_hasThAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThAttachments");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasPost");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.forum.Thread createThread(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Thread.ClassMgr.createThread(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.resources.sem.forum.Thread getThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.forum.Thread createThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeThread(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (getThread(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, hasusergroupref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,hasusergroupref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAssMember(org.semanticwb.model.AssMember hasassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, hasassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAssMember(org.semanticwb.model.AssMember hasassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasassmemberinv.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,hasassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLastPostMember(org.semanticwb.model.User thlastpostmember,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_thLastPostMember, thlastpostmember.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByLastPostMember(org.semanticwb.model.User thlastpostmember)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(thlastpostmember.getSemanticObject().getModel().listSubjectsByClass(frm_thLastPostMember,thlastpostmember.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, haswebpagevirtualparent.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualparent.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,haswebpagevirtualparent.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, haswebpagevirtualchild.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualchild.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,haswebpagevirtualchild.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPFlowRef(org.semanticwb.model.PFlowRef haspflowref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, haspflowref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPFlowRef(org.semanticwb.model.PFlowRef haspflowref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haspflowref.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,haspflowref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, hastemplateref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hastemplateref.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,hastemplateref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByChild(org.semanticwb.model.WebPage haswebpagechild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, haswebpagechild.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByChild(org.semanticwb.model.WebPage haswebpagechild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haswebpagechild.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,haswebpagechild.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, hascalendarref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,hascalendarref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByParent(org.semanticwb.model.WebPage webpageparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, webpageparent.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByParent(org.semanticwb.model.WebPage webpageparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(webpageparent.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,webpageparent.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, modifiedby.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,modifiedby.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByResource(org.semanticwb.model.Resource hasresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, hasresource.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByResource(org.semanticwb.model.Resource hasresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasresource.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,hasresource.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByForum(org.semanticwb.portal.resources.sem.forum.SWBForum thforum,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_thForum, thforum.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByForum(org.semanticwb.portal.resources.sem.forum.SWBForum thforum)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(thforum.getSemanticObject().getModel().listSubjectsByClass(frm_thForum,thforum.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread hasuserfavthread,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasUserFavThread, hasuserfavthread.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread hasuserfavthread)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasuserfavthread.getSemanticObject().getModel().listSubjectsByClass(frm_hasUserFavThread,hasuserfavthread.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, hasroleref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,hasroleref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, hasthisroleassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasthisroleassmemberinv.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,hasthisroleassmemberinv.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAttachment(org.semanticwb.portal.resources.sem.forum.Attachment hasthattachments,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasThAttachments, hasthattachments.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByAttachment(org.semanticwb.portal.resources.sem.forum.Attachment hasthattachments)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasthattachments.getSemanticObject().getModel().listSubjectsByClass(frm_hasThAttachments,hasthattachments.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjectsByClass(swb_creator,creator.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, hasruleref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,hasruleref.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPost(org.semanticwb.portal.resources.sem.forum.Post haspost,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasPost, haspost.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByPost(org.semanticwb.portal.resources.sem.forum.Post haspost)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(haspost.getSemanticObject().getModel().listSubjectsByClass(frm_hasPost,haspost.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, hasthistypeassociationinv.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> it=new org.semanticwb.model.GenericIterator(hasthistypeassociationinv.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,hasthistypeassociationinv.getSemanticObject(),sclass));
            return it;
        }
    }

    public ThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setLastPostMember(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(frm_thLastPostMember, value.getSemanticObject());
    }

    public void removeLastPostMember()
    {
        getSemanticObject().removeProperty(frm_thLastPostMember);
    }

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

    public void setForum(org.semanticwb.portal.resources.sem.forum.SWBForum value)
    {
        getSemanticObject().setObjectProperty(frm_thForum, value.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thForum);
    }

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

    public int getViewCount()
    {
        return getSemanticObject().getIntProperty(frm_thViewCount);
    }

    public void setViewCount(int value)
    {
        getSemanticObject().setIntProperty(frm_thViewCount, value);
    }

    public int getReplyCount()
    {
        return getSemanticObject().getIntProperty(frm_thReplyCount);
    }

    public void setReplyCount(int value)
    {
        getSemanticObject().setIntProperty(frm_thReplyCount, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(getSemanticObject().listObjectProperties(frm_hasUserFavThread));
    }

    public boolean hasUserFavThread(org.semanticwb.portal.resources.sem.forum.UserFavThread userfavthread)
    {
        boolean ret=false;
        if(userfavthread!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasUserFavThread,userfavthread.getSemanticObject());
        }
        return ret;
    }

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

    public java.util.Date getLastPostDate()
    {
        return getSemanticObject().getDateProperty(frm_thLastPostDate);
    }

    public void setLastPostDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(frm_thLastPostDate, value);
    }

    public String getBody()
    {
        return getSemanticObject().getProperty(frm_thBody);
    }

    public void setBody(String value)
    {
        getSemanticObject().setProperty(frm_thBody, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(getSemanticObject().listObjectProperties(frm_hasThAttachments));
    }

    public boolean hasAttachment(org.semanticwb.portal.resources.sem.forum.Attachment attachment)
    {
        boolean ret=false;
        if(attachment!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasThAttachments,attachment.getSemanticObject());
        }
        return ret;
    }

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

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(getSemanticObject().listObjectProperties(frm_hasPost));
    }

    public boolean hasPost(org.semanticwb.portal.resources.sem.forum.Post post)
    {
        boolean ret=false;
        if(post!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasPost,post.getSemanticObject());
        }
        return ret;
    }

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
