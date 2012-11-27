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
 * The Class ProjectSiteBase.
 */
public abstract class ProjectSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Localeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable
{
    
    /** The Constant swbproy_ActivityContainer. */
    public static final org.semanticwb.platform.SemanticClass swbproy_ActivityContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ActivityContainer");
    
    /** The Constant swbproy_Activity. */
    public static final org.semanticwb.platform.SemanticClass swbproy_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Activity");
    
    /** The Constant swbproy_UserWebPageContainer. */
    public static final org.semanticwb.platform.SemanticClass swbproy_UserWebPageContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#UserWebPageContainer");
    
    /** The Constant swbproy_Project. */
    public static final org.semanticwb.platform.SemanticClass swbproy_Project=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Project");
    
    /** The Constant swbproy_UserWebPage. */
    public static final org.semanticwb.platform.SemanticClass swbproy_UserWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#UserWebPage");
    
    /** The Constant swbproy_ProjectSite. */
    public static final org.semanticwb.platform.SemanticClass swbproy_ProjectSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ProjectSite");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ProjectSite");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List project sites.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite>(it, true);
        }

        /**
         * List project sites.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite>(it, true);
        }

        /**
         * Gets the project site.
         * 
         * @param id the id
         * @return the project site
         */
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

        /**
         * Creates the project site.
         * 
         * @param id the id
         * @param namespace the namespace
         * @return the org.semanticwb.portal.resources.projectdriver. project site
         */
        public static org.semanticwb.portal.resources.projectdriver.ProjectSite createProjectSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.portal.resources.projectdriver.ProjectSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the project site.
         * 
         * @param id the id
         */
        public static void removeProjectSite(String id)
        {
            org.semanticwb.portal.resources.projectdriver.ProjectSite obj=getProjectSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        /**
         * Checks for project site.
         * 
         * @param id the id
         * @return true, if successful
         */
        public static boolean hasProjectSite(String id)
        {
            return (getProjectSite(id)!=null);
        }

        /**
         * List project site by modified by.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by modified by.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by parent web site.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by parent web site.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by ontology.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by ontology.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by user repository.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by user repository.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by language.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by language.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by creator.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by creator.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by home page.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by home page.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by default template.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by default template.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by sub model.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List project site by sub model.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> listProjectSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.ProjectSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new project site base.
     * 
     * @param base the base
     */
    public ProjectSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the activity container.
     * 
     * @param id the id
     * @return the activity container
     */
    public org.semanticwb.portal.resources.projectdriver.ActivityContainer getActivityContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.getActivityContainer(id, this);
    }

    /**
     * List activity containers.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.ActivityContainer> listActivityContainers()
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.listActivityContainers(this);
    }

    /**
     * Creates the activity container.
     * 
     * @param id the id
     * @return the org.semanticwb.portal.resources.projectdriver. activity container
     */
    public org.semanticwb.portal.resources.projectdriver.ActivityContainer createActivityContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.createActivityContainer(id,this);
    }

    /**
     * Removes the activity container.
     * 
     * @param id the id
     */
    public void removeActivityContainer(String id)
    {
        org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.removeActivityContainer(id, this);
    }
    
    /**
     * Checks for activity container.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasActivityContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.ActivityContainer.ClassMgr.hasActivityContainer(id, this);
    }

    /**
     * Gets the activity.
     * 
     * @param id the id
     * @return the activity
     */
    public org.semanticwb.portal.resources.projectdriver.Activity getActivity(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.getActivity(id, this);
    }

    /**
     * List activities.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivities()
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.listActivities(this);
    }

    /**
     * Creates the activity.
     * 
     * @param id the id
     * @return the org.semanticwb.portal.resources.projectdriver. activity
     */
    public org.semanticwb.portal.resources.projectdriver.Activity createActivity(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.createActivity(id,this);
    }

    /**
     * Removes the activity.
     * 
     * @param id the id
     */
    public void removeActivity(String id)
    {
        org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.removeActivity(id, this);
    }
    
    /**
     * Checks for activity.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasActivity(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Activity.ClassMgr.hasActivity(id, this);
    }

    /**
     * Gets the user web page container.
     * 
     * @param id the id
     * @return the user web page container
     */
    public org.semanticwb.portal.resources.projectdriver.UserWebPageContainer getUserWebPageContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.getUserWebPageContainer(id, this);
    }

    /**
     * List user web page containers.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPageContainer> listUserWebPageContainers()
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.listUserWebPageContainers(this);
    }

    /**
     * Creates the user web page container.
     * 
     * @param id the id
     * @return the org.semanticwb.portal.resources.projectdriver. user web page container
     */
    public org.semanticwb.portal.resources.projectdriver.UserWebPageContainer createUserWebPageContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.createUserWebPageContainer(id,this);
    }

    /**
     * Removes the user web page container.
     * 
     * @param id the id
     */
    public void removeUserWebPageContainer(String id)
    {
        org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.removeUserWebPageContainer(id, this);
    }
    
    /**
     * Checks for user web page container.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasUserWebPageContainer(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPageContainer.ClassMgr.hasUserWebPageContainer(id, this);
    }

    /**
     * Gets the project.
     * 
     * @param id the id
     * @return the project
     */
    public org.semanticwb.portal.resources.projectdriver.Project getProject(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.getProject(id, this);
    }

    /**
     * List projects.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Project> listProjects()
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.listProjects(this);
    }

    /**
     * Creates the project.
     * 
     * @param id the id
     * @return the org.semanticwb.portal.resources.projectdriver. project
     */
    public org.semanticwb.portal.resources.projectdriver.Project createProject(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.createProject(id,this);
    }

    /**
     * Removes the project.
     * 
     * @param id the id
     */
    public void removeProject(String id)
    {
        org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.removeProject(id, this);
    }
    
    /**
     * Checks for project.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasProject(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.Project.ClassMgr.hasProject(id, this);
    }

    /**
     * Gets the user web page.
     * 
     * @param id the id
     * @return the user web page
     */
    public org.semanticwb.portal.resources.projectdriver.UserWebPage getUserWebPage(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.getUserWebPage(id, this);
    }

    /**
     * List user web pages.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.portal.resources.projectdriver.UserWebPage> listUserWebPages()
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.listUserWebPages(this);
    }

    /**
     * Creates the user web page.
     * 
     * @param id the id
     * @return the org.semanticwb.portal.resources.projectdriver. user web page
     */
    public org.semanticwb.portal.resources.projectdriver.UserWebPage createUserWebPage(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.createUserWebPage(id,this);
    }

    /**
     * Removes the user web page.
     * 
     * @param id the id
     */
    public void removeUserWebPage(String id)
    {
        org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.removeUserWebPage(id, this);
    }
    
    /**
     * Checks for user web page.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasUserWebPage(String id)
    {
        return org.semanticwb.portal.resources.projectdriver.UserWebPage.ClassMgr.hasUserWebPage(id, this);
    }
}
