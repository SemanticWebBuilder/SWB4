package org.semanticwb.portal.resources.projectdriver.base;


public abstract class UserWebPageContainerBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Searchable,org.semanticwb.model.Rankable,org.semanticwb.model.Expirable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Viewable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Tagable,org.semanticwb.model.Filterable,org.semanticwb.model.PFlowRefable
{
    public static final org.semanticwb.platform.SemanticClass swbproy_UserWebPageContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#UserWebPageContainer");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#UserWebPageContainer");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer>(it, true);
        }

        public static org.semanticwb.portal.resources.projectdriver.UserWebPageContainer getUserWebPageContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.UserWebPageContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.projectdriver.UserWebPageContainer createUserWebPageContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.UserWebPageContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeUserWebPageContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasUserWebPageContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserWebPageContainer(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainerByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public UserWebPageContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.portal.resources.projectdriver.ProjectSite getProjectSite()
    {
        return (org.semanticwb.portal.resources.projectdriver.ProjectSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
