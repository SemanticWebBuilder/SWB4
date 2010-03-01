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
 * The Class WebSiteBase.
 */
public abstract class WebSiteBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Localeable
{
    
    /** The Constant swb_UserRepository. */
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    
    /** The Constant swb_userRepository. */
    public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
    
    /** The Constant swb_WebPage. */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    
    /** The Constant swb_homePage. */
    public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
    
    /** The Constant swb_Template. */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    
    /** The Constant swb_defaultTemplate. */
    public static final org.semanticwb.platform.SemanticProperty swb_defaultTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#defaultTemplate");
    
    /** The Constant swb_SWBModel. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    
    /** The Constant swb_hasSubModel. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasSubModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasSubModel");
    
    /** The Constant swb_ResourceSubType. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    
    /** The Constant swb_Association. */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    
    /** The Constant swb_ResourceRef. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    
    /** The Constant swb_RuleRef. */
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    
    /** The Constant swb_Language. */
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
    
    /** The Constant swb_Dns. */
    public static final org.semanticwb.platform.SemanticClass swb_Dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
    
    /** The Constant swb_AssMember. */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    
    /** The Constant swb_Rule. */
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    
    /** The Constant swb_Camp. */
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    
    /** The Constant swb_VersionInfo. */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    
    /** The Constant swb_Calendar. */
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    
    /** The Constant swb_Device. */
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    
    /** The Constant swb_AdminFilter. */
    public static final org.semanticwb.platform.SemanticClass swb_AdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminFilter");
    
    /** The Constant swb_IPFilter. */
    public static final org.semanticwb.platform.SemanticClass swb_IPFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#IPFilter");
    
    /** The Constant swb_PFlowRef. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    
    /** The Constant swb_Country. */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    
    /** The Constant swb_PFlow. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    
    /** The Constant swb_ResourceFilter. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
    
    /** The Constant swb_TemplateRef. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    
    /** The Constant swb_CalendarRef. */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
    
    /** The Constant swb_Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    
    /** The Constant swb_ResourceType. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    
    /** The Constant swb_PFlowInstance. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    
    /** The Constant swb_TemplateGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
    
    /** The Constant swb_RoleRef. */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    
    /** The Constant swb_WebSite. */
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List web sites.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(it, true);
        }

        /**
         * List web sites.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(it, true);
        }

        /**
         * Gets the web site.
         * 
         * @param id the id
         * @return the web site
         */
        public static org.semanticwb.model.WebSite getWebSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.WebSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.WebSite)obj.createGenericInstance();
                }
            }
            return ret;
        }

        /**
         * Creates the web site.
         * 
         * @param id the id
         * @param namespace the namespace
         * @return the org.semanticwb.model. web site
         */
        public static org.semanticwb.model.WebSite createWebSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.WebSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the web site.
         * 
         * @param id the id
         */
        public static void removeWebSite(String id)
        {
            org.semanticwb.model.WebSite obj=getWebSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        /**
         * Checks for web site.
         * 
         * @param id the id
         * @return true, if successful
         */
        public static boolean hasWebSite(String id)
        {
            return (getWebSite(id)!=null);
        }

        /**
         * List web site by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List web site by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List web site by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List web site by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List web site by user repository.
         * 
         * @param userrepository the userrepository
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByUserRepository(org.semanticwb.model.UserRepository userrepository,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_userRepository, userrepository.getSemanticObject()));
            return it;
        }

        /**
         * List web site by user repository.
         * 
         * @param userrepository the userrepository
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByUserRepository(org.semanticwb.model.UserRepository userrepository)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(userrepository.getSemanticObject().getModel().listSubjects(swb_userRepository,userrepository.getSemanticObject()));
            return it;
        }

        /**
         * List web site by language.
         * 
         * @param language the language
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_language, language.getSemanticObject()));
            return it;
        }

        /**
         * List web site by language.
         * 
         * @param language the language
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByLanguage(org.semanticwb.model.Language language)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjects(swb_language,language.getSemanticObject()));
            return it;
        }

        /**
         * List web site by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List web site by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List web site by home page.
         * 
         * @param homepage the homepage
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByHomePage(org.semanticwb.model.WebPage homepage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_homePage, homepage.getSemanticObject()));
            return it;
        }

        /**
         * List web site by home page.
         * 
         * @param homepage the homepage
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByHomePage(org.semanticwb.model.WebPage homepage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(homepage.getSemanticObject().getModel().listSubjects(swb_homePage,homepage.getSemanticObject()));
            return it;
        }

        /**
         * List web site by default template.
         * 
         * @param defaulttemplate the defaulttemplate
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByDefaultTemplate(org.semanticwb.model.Template defaulttemplate,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_defaultTemplate, defaulttemplate.getSemanticObject()));
            return it;
        }

        /**
         * List web site by default template.
         * 
         * @param defaulttemplate the defaulttemplate
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByDefaultTemplate(org.semanticwb.model.Template defaulttemplate)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(defaulttemplate.getSemanticObject().getModel().listSubjects(swb_defaultTemplate,defaulttemplate.getSemanticObject()));
            return it;
        }

        /**
         * List web site by sub model.
         * 
         * @param hassubmodel the hassubmodel
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteBySubModel(org.semanticwb.model.SWBModel hassubmodel,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasSubModel, hassubmodel.getSemanticObject()));
            return it;
        }

        /**
         * List web site by sub model.
         * 
         * @param hassubmodel the hassubmodel
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteBySubModel(org.semanticwb.model.SWBModel hassubmodel)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(hassubmodel.getSemanticObject().getModel().listSubjects(swb_hasSubModel,hassubmodel.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new web site base.
     * 
     * @param base the base
     */
    public WebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IndexableBase#isIndexable()
     */
    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IndexableBase#setIndexable(boolean)
     */
    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }

    /**
     * Sets the user repository.
     * 
     * @param value the new user repository
     */
    public void setUserRepository(org.semanticwb.model.UserRepository value)
    {
        getSemanticObject().setObjectProperty(swb_userRepository, value.getSemanticObject());
    }

    /**
     * Removes the user repository.
     */
    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(swb_userRepository);
    }

    /**
     * Gets the user repository.
     * 
     * @return the user repository
     */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
         org.semanticwb.model.UserRepository ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userRepository);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserRepository)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#isActive()
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#setActive(boolean)
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#isDeleted()
     */
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#setDeleted(boolean)
     */
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#setLanguage(org.semanticwb.model.Language)
     */
    public void setLanguage(org.semanticwb.model.Language value)
    {
        getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#removeLanguage()
     */
    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#getLanguage()
     */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the home page.
     * 
     * @param value the new home page
     */
    public void setHomePage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swb_homePage, value.getSemanticObject());
    }

    /**
     * Removes the home page.
     */
    public void removeHomePage()
    {
        getSemanticObject().removeProperty(swb_homePage);
    }

    /**
     * Gets the home page.
     * 
     * @return the home page
     */
    public org.semanticwb.model.WebPage getHomePage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_homePage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the default template.
     * 
     * @param value the new default template
     */
    public void setDefaultTemplate(org.semanticwb.model.Template value)
    {
        getSemanticObject().setObjectProperty(swb_defaultTemplate, value.getSemanticObject());
    }

    /**
     * Removes the default template.
     */
    public void removeDefaultTemplate()
    {
        getSemanticObject().removeProperty(swb_defaultTemplate);
    }

    /**
     * Gets the default template.
     * 
     * @return the default template
     */
    public org.semanticwb.model.Template getDefaultTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_defaultTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /**
     * List sub models.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> listSubModels()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(getSemanticObject().listObjectProperties(swb_hasSubModel));
    }

    /**
     * Checks for sub model.
     * 
     * @param swbmodel the swbmodel
     * @return true, if successful
     */
    public boolean hasSubModel(org.semanticwb.model.SWBModel swbmodel)
    {
        boolean ret=false;
        if(swbmodel!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasSubModel,swbmodel.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the sub model.
     * 
     * @param value the value
     */
    public void addSubModel(org.semanticwb.model.SWBModel value)
    {
        getSemanticObject().addObjectProperty(swb_hasSubModel, value.getSemanticObject());
    }

    /**
     * Removes the all sub model.
     */
    public void removeAllSubModel()
    {
        getSemanticObject().removeProperty(swb_hasSubModel);
    }

    /**
     * Removes the sub model.
     * 
     * @param swbmodel the swbmodel
     */
    public void removeSubModel(org.semanticwb.model.SWBModel swbmodel)
    {
        getSemanticObject().removeObjectProperty(swb_hasSubModel,swbmodel.getSemanticObject());
    }

    /**
     * Gets the sub model.
     * 
     * @return the sub model
     */
    public org.semanticwb.model.SWBModel getSubModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasSubModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#isUndeleteable()
     */
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#setUndeleteable(boolean)
     */
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    /**
     * Gets the resource sub type.
     * 
     * @param id the id
     * @return the resource sub type
     */
    public org.semanticwb.model.ResourceSubType getResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.getResourceSubType(id, this);
    }

    /**
     * List resource sub types.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceSubType> listResourceSubTypes()
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.listResourceSubTypes(this);
    }

    /**
     * Creates the resource sub type.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource sub type
     */
    public org.semanticwb.model.ResourceSubType createResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.createResourceSubType(id,this);
    }

    /**
     * Removes the resource sub type.
     * 
     * @param id the id
     */
    public void removeResourceSubType(String id)
    {
        org.semanticwb.model.ResourceSubType.ClassMgr.removeResourceSubType(id, this);
    }
    
    /**
     * Checks for resource sub type.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.hasResourceSubType(id, this);
    }

    /**
     * Gets the association.
     * 
     * @param id the id
     * @return the association
     */
    public org.semanticwb.model.Association getAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.getAssociation(id, this);
    }

    /**
     * List associations.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Association> listAssociations()
    {
        return org.semanticwb.model.Association.ClassMgr.listAssociations(this);
    }

    /**
     * Creates the association.
     * 
     * @param id the id
     * @return the org.semanticwb.model. association
     */
    public org.semanticwb.model.Association createAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.createAssociation(id,this);
    }

    /**
     * Creates the association.
     * 
     * @return the org.semanticwb.model. association
     */
    public org.semanticwb.model.Association createAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Association);
        return org.semanticwb.model.Association.ClassMgr.createAssociation(String.valueOf(id),this);
    } 

    /**
     * Removes the association.
     * 
     * @param id the id
     */
    public void removeAssociation(String id)
    {
        org.semanticwb.model.Association.ClassMgr.removeAssociation(id, this);
    }
    
    /**
     * Checks for association.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.hasAssociation(id, this);
    }

    /**
     * Gets the resource ref.
     * 
     * @param id the id
     * @return the resource ref
     */
    public org.semanticwb.model.ResourceRef getResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.getResourceRef(id, this);
    }

    /**
     * List resource refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.listResourceRefs(this);
    }

    /**
     * Creates the resource ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource ref
     */
    public org.semanticwb.model.ResourceRef createResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(id,this);
    }

    /**
     * Creates the resource ref.
     * 
     * @return the org.semanticwb.model. resource ref
     */
    public org.semanticwb.model.ResourceRef createResourceRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceRef);
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id),this);
    } 

    /**
     * Removes the resource ref.
     * 
     * @param id the id
     */
    public void removeResourceRef(String id)
    {
        org.semanticwb.model.ResourceRef.ClassMgr.removeResourceRef(id, this);
    }
    
    /**
     * Checks for resource ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.hasResourceRef(id, this);
    }

    /**
     * Gets the rule ref.
     * 
     * @param id the id
     * @return the rule ref
     */
    public org.semanticwb.model.RuleRef getRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.getRuleRef(id, this);
    }

    /**
     * List rule refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return org.semanticwb.model.RuleRef.ClassMgr.listRuleRefs(this);
    }

    /**
     * Creates the rule ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. rule ref
     */
    public org.semanticwb.model.RuleRef createRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(id,this);
    }

    /**
     * Creates the rule ref.
     * 
     * @return the org.semanticwb.model. rule ref
     */
    public org.semanticwb.model.RuleRef createRuleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RuleRef);
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(String.valueOf(id),this);
    } 

    /**
     * Removes the rule ref.
     * 
     * @param id the id
     */
    public void removeRuleRef(String id)
    {
        org.semanticwb.model.RuleRef.ClassMgr.removeRuleRef(id, this);
    }
    
    /**
     * Checks for rule ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.hasRuleRef(id, this);
    }

    /**
     * Gets the language.
     * 
     * @param id the id
     * @return the language
     */
    public org.semanticwb.model.Language getLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.getLanguage(id, this);
    }

    /**
     * List languages.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Language> listLanguages()
    {
        return org.semanticwb.model.Language.ClassMgr.listLanguages(this);
    }

    /**
     * Creates the language.
     * 
     * @param id the id
     * @return the org.semanticwb.model. language
     */
    public org.semanticwb.model.Language createLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.createLanguage(id,this);
    }

    /**
     * Removes the language.
     * 
     * @param id the id
     */
    public void removeLanguage(String id)
    {
        org.semanticwb.model.Language.ClassMgr.removeLanguage(id, this);
    }
    
    /**
     * Checks for language.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.hasLanguage(id, this);
    }

    /**
     * Gets the dns.
     * 
     * @param id the id
     * @return the dns
     */
    public org.semanticwb.model.Dns getDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.getDns(id, this);
    }

    /**
     * List dnses.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Dns> listDnses()
    {
        return org.semanticwb.model.Dns.ClassMgr.listDnses(this);
    }

    /**
     * Creates the dns.
     * 
     * @param id the id
     * @return the org.semanticwb.model. dns
     */
    public org.semanticwb.model.Dns createDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.createDns(id,this);
    }

    /**
     * Creates the dns.
     * 
     * @return the org.semanticwb.model. dns
     */
    public org.semanticwb.model.Dns createDns()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Dns);
        return org.semanticwb.model.Dns.ClassMgr.createDns(String.valueOf(id),this);
    } 

    /**
     * Removes the dns.
     * 
     * @param id the id
     */
    public void removeDns(String id)
    {
        org.semanticwb.model.Dns.ClassMgr.removeDns(id, this);
    }
    
    /**
     * Checks for dns.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.hasDns(id, this);
    }

    /**
     * Gets the ass member.
     * 
     * @param id the id
     * @return the ass member
     */
    public org.semanticwb.model.AssMember getAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.getAssMember(id, this);
    }

    /**
     * List ass members.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers()
    {
        return org.semanticwb.model.AssMember.ClassMgr.listAssMembers(this);
    }

    /**
     * Creates the ass member.
     * 
     * @param id the id
     * @return the org.semanticwb.model. ass member
     */
    public org.semanticwb.model.AssMember createAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.createAssMember(id,this);
    }

    /**
     * Creates the ass member.
     * 
     * @return the org.semanticwb.model. ass member
     */
    public org.semanticwb.model.AssMember createAssMember()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AssMember);
        return org.semanticwb.model.AssMember.ClassMgr.createAssMember(String.valueOf(id),this);
    } 

    /**
     * Removes the ass member.
     * 
     * @param id the id
     */
    public void removeAssMember(String id)
    {
        org.semanticwb.model.AssMember.ClassMgr.removeAssMember(id, this);
    }
    
    /**
     * Checks for ass member.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.hasAssMember(id, this);
    }

    /**
     * Gets the rule.
     * 
     * @param id the id
     * @return the rule
     */
    public org.semanticwb.model.Rule getRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.getRule(id, this);
    }

    /**
     * List rules.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Rule> listRules()
    {
        return org.semanticwb.model.Rule.ClassMgr.listRules(this);
    }

    /**
     * Creates the rule.
     * 
     * @param id the id
     * @return the org.semanticwb.model. rule
     */
    public org.semanticwb.model.Rule createRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.createRule(id,this);
    }

    /**
     * Creates the rule.
     * 
     * @return the org.semanticwb.model. rule
     */
    public org.semanticwb.model.Rule createRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Rule);
        return org.semanticwb.model.Rule.ClassMgr.createRule(String.valueOf(id),this);
    } 

    /**
     * Removes the rule.
     * 
     * @param id the id
     */
    public void removeRule(String id)
    {
        org.semanticwb.model.Rule.ClassMgr.removeRule(id, this);
    }
    
    /**
     * Checks for rule.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.hasRule(id, this);
    }

    /**
     * Gets the camp.
     * 
     * @param id the id
     * @return the camp
     */
    public org.semanticwb.model.Camp getCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.getCamp(id, this);
    }

    /**
     * List camps.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Camp> listCamps()
    {
        return org.semanticwb.model.Camp.ClassMgr.listCamps(this);
    }

    /**
     * Creates the camp.
     * 
     * @param id the id
     * @return the org.semanticwb.model. camp
     */
    public org.semanticwb.model.Camp createCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.createCamp(id,this);
    }

    /**
     * Creates the camp.
     * 
     * @return the org.semanticwb.model. camp
     */
    public org.semanticwb.model.Camp createCamp()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Camp);
        return org.semanticwb.model.Camp.ClassMgr.createCamp(String.valueOf(id),this);
    } 

    /**
     * Removes the camp.
     * 
     * @param id the id
     */
    public void removeCamp(String id)
    {
        org.semanticwb.model.Camp.ClassMgr.removeCamp(id, this);
    }
    
    /**
     * Checks for camp.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.hasCamp(id, this);
    }

    /**
     * Gets the version info.
     * 
     * @param id the id
     * @return the version info
     */
    public org.semanticwb.model.VersionInfo getVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.getVersionInfo(id, this);
    }

    /**
     * List version infos.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.listVersionInfos(this);
    }

    /**
     * Creates the version info.
     * 
     * @param id the id
     * @return the org.semanticwb.model. version info
     */
    public org.semanticwb.model.VersionInfo createVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(id,this);
    }

    /**
     * Creates the version info.
     * 
     * @return the org.semanticwb.model. version info
     */
    public org.semanticwb.model.VersionInfo createVersionInfo()
    {
        long id=getSemanticObject().getModel().getCounter(swb_VersionInfo);
        return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(String.valueOf(id),this);
    } 

    /**
     * Removes the version info.
     * 
     * @param id the id
     */
    public void removeVersionInfo(String id)
    {
        org.semanticwb.model.VersionInfo.ClassMgr.removeVersionInfo(id, this);
    }
    
    /**
     * Checks for version info.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.hasVersionInfo(id, this);
    }

    /**
     * Gets the web page.
     * 
     * @param id the id
     * @return the web page
     */
    public org.semanticwb.model.WebPage getWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.getWebPage(id, this);
    }

    /**
     * List web pages.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return org.semanticwb.model.WebPage.ClassMgr.listWebPages(this);
    }

    /**
     * Creates the web page.
     * 
     * @param id the id
     * @return the org.semanticwb.model. web page
     */
    public org.semanticwb.model.WebPage createWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.createWebPage(id,this);
    }

    /**
     * Removes the web page.
     * 
     * @param id the id
     */
    public void removeWebPage(String id)
    {
        org.semanticwb.model.WebPage.ClassMgr.removeWebPage(id, this);
    }
    
    /**
     * Checks for web page.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.hasWebPage(id, this);
    }

    /**
     * Gets the calendar.
     * 
     * @param id the id
     * @return the calendar
     */
    public org.semanticwb.model.Calendar getCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.getCalendar(id, this);
    }

    /**
     * List calendars.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return org.semanticwb.model.Calendar.ClassMgr.listCalendars(this);
    }

    /**
     * Creates the calendar.
     * 
     * @param id the id
     * @return the org.semanticwb.model. calendar
     */
    public org.semanticwb.model.Calendar createCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.createCalendar(id,this);
    }

    /**
     * Creates the calendar.
     * 
     * @return the org.semanticwb.model. calendar
     */
    public org.semanticwb.model.Calendar createCalendar()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Calendar);
        return org.semanticwb.model.Calendar.ClassMgr.createCalendar(String.valueOf(id),this);
    } 

    /**
     * Removes the calendar.
     * 
     * @param id the id
     */
    public void removeCalendar(String id)
    {
        org.semanticwb.model.Calendar.ClassMgr.removeCalendar(id, this);
    }
    
    /**
     * Checks for calendar.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.hasCalendar(id, this);
    }

    /**
     * Gets the device.
     * 
     * @param id the id
     * @return the device
     */
    public org.semanticwb.model.Device getDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.getDevice(id, this);
    }

    /**
     * List devices.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Device> listDevices()
    {
        return org.semanticwb.model.Device.ClassMgr.listDevices(this);
    }

    /**
     * Creates the device.
     * 
     * @param id the id
     * @return the org.semanticwb.model. device
     */
    public org.semanticwb.model.Device createDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.createDevice(id,this);
    }

    /**
     * Removes the device.
     * 
     * @param id the id
     */
    public void removeDevice(String id)
    {
        org.semanticwb.model.Device.ClassMgr.removeDevice(id, this);
    }
    
    /**
     * Checks for device.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.hasDevice(id, this);
    }

    /**
     * Gets the admin filter.
     * 
     * @param id the id
     * @return the admin filter
     */
    public org.semanticwb.model.AdminFilter getAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.getAdminFilter(id, this);
    }

    /**
     * List admin filters.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.listAdminFilters(this);
    }

    /**
     * Creates the admin filter.
     * 
     * @param id the id
     * @return the org.semanticwb.model. admin filter
     */
    public org.semanticwb.model.AdminFilter createAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.createAdminFilter(id,this);
    }

    /**
     * Creates the admin filter.
     * 
     * @return the org.semanticwb.model. admin filter
     */
    public org.semanticwb.model.AdminFilter createAdminFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AdminFilter);
        return org.semanticwb.model.AdminFilter.ClassMgr.createAdminFilter(String.valueOf(id),this);
    } 

    /**
     * Removes the admin filter.
     * 
     * @param id the id
     */
    public void removeAdminFilter(String id)
    {
        org.semanticwb.model.AdminFilter.ClassMgr.removeAdminFilter(id, this);
    }
    
    /**
     * Checks for admin filter.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.hasAdminFilter(id, this);
    }

    /**
     * Gets the iP filter.
     * 
     * @param id the id
     * @return the iP filter
     */
    public org.semanticwb.model.IPFilter getIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.getIPFilter(id, this);
    }

    /**
     * List ip filters.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        return org.semanticwb.model.IPFilter.ClassMgr.listIPFilters(this);
    }

    /**
     * Creates the ip filter.
     * 
     * @param id the id
     * @return the org.semanticwb.model. ip filter
     */
    public org.semanticwb.model.IPFilter createIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.createIPFilter(id,this);
    }

    /**
     * Creates the ip filter.
     * 
     * @return the org.semanticwb.model. ip filter
     */
    public org.semanticwb.model.IPFilter createIPFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_IPFilter);
        return org.semanticwb.model.IPFilter.ClassMgr.createIPFilter(String.valueOf(id),this);
    } 

    /**
     * Removes the ip filter.
     * 
     * @param id the id
     */
    public void removeIPFilter(String id)
    {
        org.semanticwb.model.IPFilter.ClassMgr.removeIPFilter(id, this);
    }
    
    /**
     * Checks for ip filter.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.hasIPFilter(id, this);
    }

    /**
     * Gets the p flow ref.
     * 
     * @param id the id
     * @return the p flow ref
     */
    public org.semanticwb.model.PFlowRef getPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.getPFlowRef(id, this);
    }

    /**
     * List p flow refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.listPFlowRefs(this);
    }

    /**
     * Creates the p flow ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. p flow ref
     */
    public org.semanticwb.model.PFlowRef createPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(id,this);
    }

    /**
     * Creates the p flow ref.
     * 
     * @return the org.semanticwb.model. p flow ref
     */
    public org.semanticwb.model.PFlowRef createPFlowRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowRef);
        return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(String.valueOf(id),this);
    } 

    /**
     * Removes the p flow ref.
     * 
     * @param id the id
     */
    public void removePFlowRef(String id)
    {
        org.semanticwb.model.PFlowRef.ClassMgr.removePFlowRef(id, this);
    }
    
    /**
     * Checks for p flow ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.hasPFlowRef(id, this);
    }

    /**
     * Gets the template.
     * 
     * @param id the id
     * @return the template
     */
    public org.semanticwb.model.Template getTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.getTemplate(id, this);
    }

    /**
     * List templates.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Template> listTemplates()
    {
        return org.semanticwb.model.Template.ClassMgr.listTemplates(this);
    }

    /**
     * Creates the template.
     * 
     * @param id the id
     * @return the org.semanticwb.model. template
     */
    public org.semanticwb.model.Template createTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.createTemplate(id,this);
    }

    /**
     * Creates the template.
     * 
     * @return the org.semanticwb.model. template
     */
    public org.semanticwb.model.Template createTemplate()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Template);
        return org.semanticwb.model.Template.ClassMgr.createTemplate(String.valueOf(id),this);
    } 

    /**
     * Removes the template.
     * 
     * @param id the id
     */
    public void removeTemplate(String id)
    {
        org.semanticwb.model.Template.ClassMgr.removeTemplate(id, this);
    }
    
    /**
     * Checks for template.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.hasTemplate(id, this);
    }

    /**
     * Gets the country.
     * 
     * @param id the id
     * @return the country
     */
    public org.semanticwb.model.Country getCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.getCountry(id, this);
    }

    /**
     * List countries.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Country> listCountries()
    {
        return org.semanticwb.model.Country.ClassMgr.listCountries(this);
    }

    /**
     * Creates the country.
     * 
     * @param id the id
     * @return the org.semanticwb.model. country
     */
    public org.semanticwb.model.Country createCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.createCountry(id,this);
    }

    /**
     * Removes the country.
     * 
     * @param id the id
     */
    public void removeCountry(String id)
    {
        org.semanticwb.model.Country.ClassMgr.removeCountry(id, this);
    }
    
    /**
     * Checks for country.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.hasCountry(id, this);
    }

    /**
     * Gets the p flow.
     * 
     * @param id the id
     * @return the p flow
     */
    public org.semanticwb.model.PFlow getPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.getPFlow(id, this);
    }

    /**
     * List p flows.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        return org.semanticwb.model.PFlow.ClassMgr.listPFlows(this);
    }

    /**
     * Creates the p flow.
     * 
     * @param id the id
     * @return the org.semanticwb.model. p flow
     */
    public org.semanticwb.model.PFlow createPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.createPFlow(id,this);
    }

    /**
     * Creates the p flow.
     * 
     * @return the org.semanticwb.model. p flow
     */
    public org.semanticwb.model.PFlow createPFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlow);
        return org.semanticwb.model.PFlow.ClassMgr.createPFlow(String.valueOf(id),this);
    } 

    /**
     * Removes the p flow.
     * 
     * @param id the id
     */
    public void removePFlow(String id)
    {
        org.semanticwb.model.PFlow.ClassMgr.removePFlow(id, this);
    }
    
    /**
     * Checks for p flow.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.hasPFlow(id, this);
    }

    /**
     * Gets the resource filter.
     * 
     * @param id the id
     * @return the resource filter
     */
    public org.semanticwb.model.ResourceFilter getResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.getResourceFilter(id, this);
    }

    /**
     * List resource filters.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceFilter> listResourceFilters()
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.listResourceFilters(this);
    }

    /**
     * Creates the resource filter.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource filter
     */
    public org.semanticwb.model.ResourceFilter createResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.createResourceFilter(id,this);
    }

    /**
     * Creates the resource filter.
     * 
     * @return the org.semanticwb.model. resource filter
     */
    public org.semanticwb.model.ResourceFilter createResourceFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceFilter);
        return org.semanticwb.model.ResourceFilter.ClassMgr.createResourceFilter(String.valueOf(id),this);
    } 

    /**
     * Removes the resource filter.
     * 
     * @param id the id
     */
    public void removeResourceFilter(String id)
    {
        org.semanticwb.model.ResourceFilter.ClassMgr.removeResourceFilter(id, this);
    }
    
    /**
     * Checks for resource filter.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.hasResourceFilter(id, this);
    }

    /**
     * Gets the template ref.
     * 
     * @param id the id
     * @return the template ref
     */
    public org.semanticwb.model.TemplateRef getTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.getTemplateRef(id, this);
    }

    /**
     * List template refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.listTemplateRefs(this);
    }

    /**
     * Creates the template ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. template ref
     */
    public org.semanticwb.model.TemplateRef createTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(id,this);
    }

    /**
     * Creates the template ref.
     * 
     * @return the org.semanticwb.model. template ref
     */
    public org.semanticwb.model.TemplateRef createTemplateRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateRef);
        return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(String.valueOf(id),this);
    } 

    /**
     * Removes the template ref.
     * 
     * @param id the id
     */
    public void removeTemplateRef(String id)
    {
        org.semanticwb.model.TemplateRef.ClassMgr.removeTemplateRef(id, this);
    }
    
    /**
     * Checks for template ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.hasTemplateRef(id, this);
    }

    /**
     * Gets the calendar ref.
     * 
     * @param id the id
     * @return the calendar ref
     */
    public org.semanticwb.model.CalendarRef getCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.getCalendarRef(id, this);
    }

    /**
     * List calendar refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.listCalendarRefs(this);
    }

    /**
     * Creates the calendar ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. calendar ref
     */
    public org.semanticwb.model.CalendarRef createCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(id,this);
    }

    /**
     * Creates the calendar ref.
     * 
     * @return the org.semanticwb.model. calendar ref
     */
    public org.semanticwb.model.CalendarRef createCalendarRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_CalendarRef);
        return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(String.valueOf(id),this);
    } 

    /**
     * Removes the calendar ref.
     * 
     * @param id the id
     */
    public void removeCalendarRef(String id)
    {
        org.semanticwb.model.CalendarRef.ClassMgr.removeCalendarRef(id, this);
    }
    
    /**
     * Checks for calendar ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.hasCalendarRef(id, this);
    }

    /**
     * Gets the resource.
     * 
     * @param id the id
     * @return the resource
     */
    public org.semanticwb.model.Resource getResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.getResource(id, this);
    }

    /**
     * List resources.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.Resource> listResources()
    {
        return org.semanticwb.model.Resource.ClassMgr.listResources(this);
    }

    /**
     * Creates the resource.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource
     */
    public org.semanticwb.model.Resource createResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.createResource(id,this);
    }

    /**
     * Creates the resource.
     * 
     * @return the org.semanticwb.model. resource
     */
    public org.semanticwb.model.Resource createResource()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Resource);
        return org.semanticwb.model.Resource.ClassMgr.createResource(String.valueOf(id),this);
    } 

    /**
     * Removes the resource.
     * 
     * @param id the id
     */
    public void removeResource(String id)
    {
        org.semanticwb.model.Resource.ClassMgr.removeResource(id, this);
    }
    
    /**
     * Checks for resource.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.hasResource(id, this);
    }

    /**
     * Gets the resource type.
     * 
     * @param id the id
     * @return the resource type
     */
    public org.semanticwb.model.ResourceType getResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.getResourceType(id, this);
    }

    /**
     * List resource types.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
    {
        return org.semanticwb.model.ResourceType.ClassMgr.listResourceTypes(this);
    }

    /**
     * Creates the resource type.
     * 
     * @param id the id
     * @return the org.semanticwb.model. resource type
     */
    public org.semanticwb.model.ResourceType createResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.createResourceType(id,this);
    }

    /**
     * Removes the resource type.
     * 
     * @param id the id
     */
    public void removeResourceType(String id)
    {
        org.semanticwb.model.ResourceType.ClassMgr.removeResourceType(id, this);
    }
    
    /**
     * Checks for resource type.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.hasResourceType(id, this);
    }

    /**
     * Gets the p flow instance.
     * 
     * @param id the id
     * @return the p flow instance
     */
    public org.semanticwb.model.PFlowInstance getPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.getPFlowInstance(id, this);
    }

    /**
     * List p flow instances.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.listPFlowInstances(this);
    }

    /**
     * Creates the p flow instance.
     * 
     * @param id the id
     * @return the org.semanticwb.model. p flow instance
     */
    public org.semanticwb.model.PFlowInstance createPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(id,this);
    }

    /**
     * Creates the p flow instance.
     * 
     * @return the org.semanticwb.model. p flow instance
     */
    public org.semanticwb.model.PFlowInstance createPFlowInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowInstance);
        return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(String.valueOf(id),this);
    } 

    /**
     * Removes the p flow instance.
     * 
     * @param id the id
     */
    public void removePFlowInstance(String id)
    {
        org.semanticwb.model.PFlowInstance.ClassMgr.removePFlowInstance(id, this);
    }
    
    /**
     * Checks for p flow instance.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.hasPFlowInstance(id, this);
    }

    /**
     * Gets the template group.
     * 
     * @param id the id
     * @return the template group
     */
    public org.semanticwb.model.TemplateGroup getTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.getTemplateGroup(id, this);
    }

    /**
     * List template groups.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups()
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.listTemplateGroups(this);
    }

    /**
     * Creates the template group.
     * 
     * @param id the id
     * @return the org.semanticwb.model. template group
     */
    public org.semanticwb.model.TemplateGroup createTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(id,this);
    }

    /**
     * Creates the template group.
     * 
     * @return the org.semanticwb.model. template group
     */
    public org.semanticwb.model.TemplateGroup createTemplateGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateGroup);
        return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(String.valueOf(id),this);
    } 

    /**
     * Removes the template group.
     * 
     * @param id the id
     */
    public void removeTemplateGroup(String id)
    {
        org.semanticwb.model.TemplateGroup.ClassMgr.removeTemplateGroup(id, this);
    }
    
    /**
     * Checks for template group.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.hasTemplateGroup(id, this);
    }

    /**
     * Gets the role ref.
     * 
     * @param id the id
     * @return the role ref
     */
    public org.semanticwb.model.RoleRef getRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.getRoleRef(id, this);
    }

    /**
     * List role refs.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return org.semanticwb.model.RoleRef.ClassMgr.listRoleRefs(this);
    }

    /**
     * Creates the role ref.
     * 
     * @param id the id
     * @return the org.semanticwb.model. role ref
     */
    public org.semanticwb.model.RoleRef createRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(id,this);
    }

    /**
     * Creates the role ref.
     * 
     * @return the org.semanticwb.model. role ref
     */
    public org.semanticwb.model.RoleRef createRoleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RoleRef);
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id),this);
    } 

    /**
     * Removes the role ref.
     * 
     * @param id the id
     */
    public void removeRoleRef(String id)
    {
        org.semanticwb.model.RoleRef.ClassMgr.removeRoleRef(id, this);
    }
    
    /**
     * Checks for role ref.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.hasRoleRef(id, this);
    }
}
