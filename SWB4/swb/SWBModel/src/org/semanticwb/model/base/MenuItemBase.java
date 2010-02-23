package org.semanticwb.model.base;


public abstract class MenuItemBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Resourceable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Expirable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Searchable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Tagable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Iconable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.Indexable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RoleRefable
{
    public static final org.semanticwb.platform.SemanticProperty swb_mnuItemShowIFrame=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#mnuItemShowIFrame");
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem>(it, true);
        }

        public static org.semanticwb.model.MenuItem getMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MenuItem)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.MenuItem createMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MenuItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMenuItem(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByAssMember(org.semanticwb.model.AssMember hasassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasAssMemberInv, hasassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByAssMember(org.semanticwb.model.AssMember hasassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasassmemberinv.getSemanticObject().getModel().listSubjects(swb_hasAssMemberInv,hasassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualParent, haswebpagevirtualparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualparent.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualParent,haswebpagevirtualparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualChild, haswebpagevirtualchild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualchild.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualChild,haswebpagevirtualchild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasTemplateRef, hastemplateref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hastemplateref.getSemanticObject().getModel().listSubjects(swb_hasTemplateRef,hastemplateref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByPFlowRef(org.semanticwb.model.PFlowRef haspflowref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasPFlowRef, haspflowref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByPFlowRef(org.semanticwb.model.PFlowRef haspflowref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(haspflowref.getSemanticObject().getModel().listSubjects(swb_hasPFlowRef,haspflowref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByChild(org.semanticwb.model.WebPage haswebpagechild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageChild, haswebpagechild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByChild(org.semanticwb.model.WebPage haswebpagechild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(haswebpagechild.getSemanticObject().getModel().listSubjects(swb_hasWebPageChild,haswebpagechild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByParent(org.semanticwb.model.WebPage webpageparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_webPageParent, webpageparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByParent(org.semanticwb.model.WebPage webpageparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(webpageparent.getSemanticObject().getModel().listSubjects(swb_webPageParent,webpageparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByResource(org.semanticwb.model.Resource hasresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasResource, hasresource.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByResource(org.semanticwb.model.Resource hasresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasresource.getSemanticObject().getModel().listSubjects(swb_hasResource,hasresource.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRoleRef, hasroleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(swb_hasRoleRef,hasroleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasThisRoleAssMemberInv, hasthisroleassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasthisroleassmemberinv.getSemanticObject().getModel().listSubjects(swb_hasThisRoleAssMemberInv,hasthisroleassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRuleRef, hasruleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(swb_hasRuleRef,hasruleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasThisTypeAssociationInv, hasthistypeassociationinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(hasthistypeassociationinv.getSemanticObject().getModel().listSubjects(swb_hasThisTypeAssociationInv,hasthistypeassociationinv.getSemanticObject()));
            return it;
        }
    }

    public MenuItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getShowAs()
    {
        return getSemanticObject().getProperty(swb_mnuItemShowIFrame);
    }

    public void setShowAs(String value)
    {
        getSemanticObject().setProperty(swb_mnuItemShowIFrame, value);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

    public org.semanticwb.model.AdminWebSite getAdminWebSite()
    {
        return (org.semanticwb.model.AdminWebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
