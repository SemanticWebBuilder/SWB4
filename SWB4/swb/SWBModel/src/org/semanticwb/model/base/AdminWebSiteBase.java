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
 * The Class AdminWebSiteBase.
 */
public abstract class AdminWebSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Trashable,org.semanticwb.model.Localeable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swbxf_MenuItem. */
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
    
    /** The Constant swbxf_ObjectBehavior. */
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
    
    /** The Constant swb_AdminWebSite. */
    public static final org.semanticwb.platform.SemanticClass swb_AdminWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List admin web sites.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite>(it, true);
        }

        /**
         * List admin web sites.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite>(it, true);
        }

        /**
         * Gets the admin web site.
         * 
         * @param id the id
         * @return the admin web site
         */
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

        /**
         * Creates the admin web site.
         * 
         * @param id the id
         * @param namespace the namespace
         * @return the org.semanticwb.model. admin web site
         */
        public static org.semanticwb.model.AdminWebSite createAdminWebSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.AdminWebSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the admin web site.
         * 
         * @param id the id
         */
        public static void removeAdminWebSite(String id)
        {
            org.semanticwb.model.AdminWebSite obj=getAdminWebSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        /**
         * Checks for admin web site.
         * 
         * @param id the id
         * @return true, if successful
         */
        public static boolean hasAdminWebSite(String id)
        {
            return (getAdminWebSite(id)!=null);
        }

        /**
         * List admin web site by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by user repository.
         * 
         * @param userrepository the userrepository
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByUserRepository(org.semanticwb.model.UserRepository userrepository,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_userRepository, userrepository.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by user repository.
         * 
         * @param userrepository the userrepository
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByUserRepository(org.semanticwb.model.UserRepository userrepository)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(userrepository.getSemanticObject().getModel().listSubjects(swb_userRepository,userrepository.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by language.
         * 
         * @param language the language
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_language, language.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by language.
         * 
         * @param language the language
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByLanguage(org.semanticwb.model.Language language)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjects(swb_language,language.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by home page.
         * 
         * @param homepage the homepage
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByHomePage(org.semanticwb.model.WebPage homepage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_homePage, homepage.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by home page.
         * 
         * @param homepage the homepage
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByHomePage(org.semanticwb.model.WebPage homepage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(homepage.getSemanticObject().getModel().listSubjects(swb_homePage,homepage.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by default template.
         * 
         * @param defaulttemplate the defaulttemplate
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByDefaultTemplate(org.semanticwb.model.Template defaulttemplate,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_defaultTemplate, defaulttemplate.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by default template.
         * 
         * @param defaulttemplate the defaulttemplate
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByDefaultTemplate(org.semanticwb.model.Template defaulttemplate)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(defaulttemplate.getSemanticObject().getModel().listSubjects(swb_defaultTemplate,defaulttemplate.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by sub model.
         * 
         * @param hassubmodel the hassubmodel
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteBySubModel(org.semanticwb.model.SWBModel hassubmodel,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasSubModel, hassubmodel.getSemanticObject()));
            return it;
        }

        /**
         * List admin web site by sub model.
         * 
         * @param hassubmodel the hassubmodel
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteBySubModel(org.semanticwb.model.SWBModel hassubmodel)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(hassubmodel.getSemanticObject().getModel().listSubjects(swb_hasSubModel,hassubmodel.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new admin web site base.
     * 
     * @param base the base
     */
    public AdminWebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the menu item.
     * 
     * @param id the id
     * @return the menu item
     */
    public org.semanticwb.model.MenuItem getMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.getMenuItem(id, this);
    }

    /**
     * List menu items.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
    {
        return org.semanticwb.model.MenuItem.ClassMgr.listMenuItems(this);
    }

    /**
     * Creates the menu item.
     * 
     * @param id the id
     * @return the org.semanticwb.model. menu item
     */
    public org.semanticwb.model.MenuItem createMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.createMenuItem(id,this);
    }

    /**
     * Removes the menu item.
     * 
     * @param id the id
     */
    public void removeMenuItem(String id)
    {
        org.semanticwb.model.MenuItem.ClassMgr.removeMenuItem(id, this);
    }
    
    /**
     * Checks for menu item.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.hasMenuItem(id, this);
    }

    /**
     * Gets the object behavior.
     * 
     * @param id the id
     * @return the object behavior
     */
    public org.semanticwb.model.ObjectBehavior getObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.getObjectBehavior(id, this);
    }

    /**
     * List object behaviors.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.listObjectBehaviors(this);
    }

    /**
     * Creates the object behavior.
     * 
     * @param id the id
     * @return the org.semanticwb.model. object behavior
     */
    public org.semanticwb.model.ObjectBehavior createObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.createObjectBehavior(id,this);
    }

    /**
     * Removes the object behavior.
     * 
     * @param id the id
     */
    public void removeObjectBehavior(String id)
    {
        org.semanticwb.model.ObjectBehavior.ClassMgr.removeObjectBehavior(id, this);
    }
    
    /**
     * Checks for object behavior.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.hasObjectBehavior(id, this);
    }
}
