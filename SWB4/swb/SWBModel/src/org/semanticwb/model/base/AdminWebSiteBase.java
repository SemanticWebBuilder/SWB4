package org.semanticwb.model.base;


public abstract class AdminWebSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Indexable,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.Localeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
    public static final org.semanticwb.platform.SemanticClass swb_AdminWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite>(it, true);
        }

        public static org.semanticwb.model.AdminWebSite getAdminWebSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.AdminWebSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.AdminWebSite)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.model.AdminWebSite createAdminWebSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.AdminWebSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        public static void removeAdminWebSite(String id)
        {
            org.semanticwb.model.AdminWebSite obj=getAdminWebSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasAdminWebSite(String id)
        {
            return (getAdminWebSite(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, modifiedby.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,modifiedby.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, parentwebsite.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,parentwebsite.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByUserRepository(org.semanticwb.model.UserRepository userrepository,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, userrepository.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByUserRepository(org.semanticwb.model.UserRepository userrepository)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(userrepository.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,userrepository.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, language.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByLanguage(org.semanticwb.model.Language language)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjectsByClass(swb_language,language.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjectsByClass(swb_creator,creator.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByHomePage(org.semanticwb.model.WebPage homepage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, homepage.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByHomePage(org.semanticwb.model.WebPage homepage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(homepage.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,homepage.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByDefaultTemplate(org.semanticwb.model.Template defaulttemplate,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, defaulttemplate.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByDefaultTemplate(org.semanticwb.model.Template defaulttemplate)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(defaulttemplate.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,defaulttemplate.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteBySubModel(org.semanticwb.model.SWBModel hassubmodel,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, hassubmodel.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteBySubModel(org.semanticwb.model.SWBModel hassubmodel)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(hassubmodel.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,hassubmodel.getSemanticObject(),sclass));
            return it;
        }
    }

    public AdminWebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.MenuItem getMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.getMenuItem(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
    {
        return org.semanticwb.model.MenuItem.ClassMgr.listMenuItems(this);
    }

    public org.semanticwb.model.MenuItem createMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.createMenuItem(id,this);
    }

    public void removeMenuItem(String id)
    {
        org.semanticwb.model.MenuItem.ClassMgr.removeMenuItem(id, this);
    }
    public boolean hasMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.hasMenuItem(id, this);
    }

    public org.semanticwb.model.ObjectBehavior getObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.getObjectBehavior(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.listObjectBehaviors(this);
    }

    public org.semanticwb.model.ObjectBehavior createObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.createObjectBehavior(id,this);
    }

    public void removeObjectBehavior(String id)
    {
        org.semanticwb.model.ObjectBehavior.ClassMgr.removeObjectBehavior(id, this);
    }
    public boolean hasObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.hasObjectBehavior(id, this);
    }
}
