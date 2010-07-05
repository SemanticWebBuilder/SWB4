package org.semanticwb.portal.resources.projectdriver.base;


public abstract class ProjectSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Localeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swbproy_ActivityContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ActivityContainer");
    public static final org.semanticwb.platform.SemanticClass swbproy_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Activity");
    public static final org.semanticwb.platform.SemanticClass swbproy_UserWebPageContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#UserWebPageContainer");
    public static final org.semanticwb.platform.SemanticClass swbproy_Project=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Project");
    public static final org.semanticwb.platform.SemanticClass swbproy_UserWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#UserWebPage");
    public static final org.semanticwb.platform.SemanticClass swbproy_ProjectSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ProjectSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ProjectSite");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite>(it, true);
        }

        public static org.semanticwb.portal.resources.projectdriver.ProjectSite getProjectSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.portal.resources.projectdriver.ProjectSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.portal.resources.projectdriver.ProjectSite)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.portal.resources.projectdriver.ProjectSite createProjectSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.portal.resources.projectdriver.ProjectSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        public static void removeProjectSite(String id)
        {
            org.semanticwb.portal.resources.projectdriver.ProjectSite obj=getProjectSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasProjectSite(String id)
        {
            return (getProjectSite(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProjectSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.portal.resources.projectdriver.ActivityContainer getActivityContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.getActivityContainer(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainers()
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.listActivityContainers(this);
    }

    public org.semanticwb.portal.resources.projectdriver.ActivityContainer createActivityContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.createActivityContainer(id,this);
    }

    public void removeActivityContainer(String id)
    {
        org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.removeActivityContainer(id, this);
    }
    public boolean hasActivityContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.hasActivityContainer(id, this);
    }

    public org.semanticwb.portal.resources.projectdriver.Activity getActivity(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.getActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivities()
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.listActivities(this);
    }

    public org.semanticwb.portal.resources.projectdriver.Activity createActivity(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.createActivity(id,this);
    }

    public void removeActivity(String id)
    {
        org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.removeActivity(id, this);
    }
    public boolean hasActivity(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.hasActivity(id, this);
    }

    public org.semanticwb.portal.resources.projectdriver.UserWebPageContainer getUserWebPageContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.getUserWebPageContainer(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainers()
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.listUserWebPageContainers(this);
    }

    public org.semanticwb.portal.resources.projectdriver.UserWebPageContainer createUserWebPageContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.createUserWebPageContainer(id,this);
    }

    public void removeUserWebPageContainer(String id)
    {
        org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.removeUserWebPageContainer(id, this);
    }
    public boolean hasUserWebPageContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.hasUserWebPageContainer(id, this);
    }

    public org.semanticwb.portal.resources.projectdriver.Project getProject(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.getProject(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Project> listProjects()
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.listProjects(this);
    }

    public org.semanticwb.portal.resources.projectdriver.Project createProject(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.createProject(id,this);
    }

    public void removeProject(String id)
    {
        org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.removeProject(id, this);
    }
    public boolean hasProject(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.hasProject(id, this);
    }

    public org.semanticwb.portal.resources.projectdriver.UserWebPage getUserWebPage(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.getUserWebPage(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPage> listUserWebPages()
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.listUserWebPages(this);
    }

    public org.semanticwb.portal.resources.projectdriver.UserWebPage createUserWebPage(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.createUserWebPage(id,this);
    }

    public void removeUserWebPage(String id)
    {
        org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.removeUserWebPage(id, this);
    }
    public boolean hasUserWebPage(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.hasUserWebPage(id, this);
    }
}
