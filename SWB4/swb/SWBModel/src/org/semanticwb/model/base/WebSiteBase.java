package org.semanticwb.model.base;


public class WebSiteBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Localeable,org.semanticwb.model.Deleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
    public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPost");
    public static final org.semanticwb.platform.SemanticClass frm_FrmThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThread");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPriorityCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPriorityCat");
    public static final org.semanticwb.platform.SemanticClass frm_FrmCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmCategory");
    public static final org.semanticwb.platform.SemanticClass frm_FrmAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmAttachment");
    public static final org.semanticwb.platform.SemanticClass frm_FrmForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmForum");
    public static final org.semanticwb.platform.SemanticClass frm_FrmThreadTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThreadTypeCat");
    public static final org.semanticwb.platform.SemanticClass frm_FrmModererationCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmModererationCat");
    public static final org.semanticwb.platform.SemanticClass frm_FrmTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmTypeCat");
    public static final org.semanticwb.platform.SemanticClass swb_Community=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Community");
    public static final org.semanticwb.platform.SemanticClass swb_PortletType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletType");
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    public static final org.semanticwb.platform.SemanticClass swb_Dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticClass swb_Portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portlet");
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    public static final org.semanticwb.platform.SemanticClass swb_AdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminFilter");
    public static final org.semanticwb.platform.SemanticClass swb_IPFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#IPFilter");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticClass swb_PortletRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");
    public static final org.semanticwb.platform.SemanticClass swb_Permission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Permission");
    public static final org.semanticwb.platform.SemanticClass swb_PortletFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletFilter");
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticClass swb_PortletSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletSubType");
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    public WebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.WebSite getWebSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.WebSite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(org.semanticwb.model.WebSite.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(org.semanticwb.model.WebSite.class, it, true);
    }

    public static org.semanticwb.model.WebSite createWebSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.WebSite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeWebSite(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasWebSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (getWebSite(id, model)!=null);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, deleted);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().setObjectProperty(swb_language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public void setUserRepository(org.semanticwb.model.UserRepository userrepository)
    {
        getSemanticObject().setObjectProperty(swb_userRepository, userrepository.getSemanticObject());
    }

    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(swb_userRepository);
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
         org.semanticwb.model.UserRepository ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userRepository);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserRepository)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setHomePage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(swb_homePage, webpage.getSemanticObject());
    }

    public void removeHomePage()
    {
        getSemanticObject().removeProperty(swb_homePage);
    }

    public org.semanticwb.model.WebPage getHomePage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_homePage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.forum.FrmPost getFrmPost(String id)
    {
        return org.semanticwb.forum.FrmPost.getFrmPost(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmPost> listFrmPosts()
    {
        return org.semanticwb.forum.FrmPost.listFrmPosts(this);
    }

    public org.semanticwb.forum.FrmPost createFrmPost(String id)
    {
        return org.semanticwb.forum.FrmPost.createFrmPost(id,this);
    }

    public org.semanticwb.forum.FrmPost createFrmPost()
    {
        long id=getSemanticObject().getModel().getCounter(frm_FrmPost);
        return org.semanticwb.forum.FrmPost.createFrmPost(String.valueOf(id),this);
    } 

    public void removeFrmPost(String id)
    {
        org.semanticwb.forum.FrmPost.removeFrmPost(id, this);
    }
    public boolean hasFrmPost(String id)
    {
        return org.semanticwb.forum.FrmPost.hasFrmPost(id, this);
    }

    public org.semanticwb.forum.FrmThread getFrmThread(String id)
    {
        return org.semanticwb.forum.FrmThread.getFrmThread(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmThread> listFrmThreads()
    {
        return org.semanticwb.forum.FrmThread.listFrmThreads(this);
    }

    public org.semanticwb.forum.FrmThread createFrmThread(String id)
    {
        return org.semanticwb.forum.FrmThread.createFrmThread(id,this);
    }

    public org.semanticwb.forum.FrmThread createFrmThread()
    {
        long id=getSemanticObject().getModel().getCounter(frm_FrmThread);
        return org.semanticwb.forum.FrmThread.createFrmThread(String.valueOf(id),this);
    } 

    public void removeFrmThread(String id)
    {
        org.semanticwb.forum.FrmThread.removeFrmThread(id, this);
    }
    public boolean hasFrmThread(String id)
    {
        return org.semanticwb.forum.FrmThread.hasFrmThread(id, this);
    }

    public org.semanticwb.forum.FrmPriorityCat getFrmPriorityCat(String id)
    {
        return org.semanticwb.forum.FrmPriorityCat.getFrmPriorityCat(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmPriorityCat> listFrmPriorityCats()
    {
        return org.semanticwb.forum.FrmPriorityCat.listFrmPriorityCats(this);
    }

    public org.semanticwb.forum.FrmPriorityCat createFrmPriorityCat(String id)
    {
        return org.semanticwb.forum.FrmPriorityCat.createFrmPriorityCat(id,this);
    }

    public void removeFrmPriorityCat(String id)
    {
        org.semanticwb.forum.FrmPriorityCat.removeFrmPriorityCat(id, this);
    }
    public boolean hasFrmPriorityCat(String id)
    {
        return org.semanticwb.forum.FrmPriorityCat.hasFrmPriorityCat(id, this);
    }

    public org.semanticwb.forum.FrmCategory getFrmCategory(String id)
    {
        return org.semanticwb.forum.FrmCategory.getFrmCategory(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmCategory> listFrmCategorys()
    {
        return org.semanticwb.forum.FrmCategory.listFrmCategorys(this);
    }

    public org.semanticwb.forum.FrmCategory createFrmCategory(String id)
    {
        return org.semanticwb.forum.FrmCategory.createFrmCategory(id,this);
    }

    public org.semanticwb.forum.FrmCategory createFrmCategory()
    {
        long id=getSemanticObject().getModel().getCounter(frm_FrmCategory);
        return org.semanticwb.forum.FrmCategory.createFrmCategory(String.valueOf(id),this);
    } 

    public void removeFrmCategory(String id)
    {
        org.semanticwb.forum.FrmCategory.removeFrmCategory(id, this);
    }
    public boolean hasFrmCategory(String id)
    {
        return org.semanticwb.forum.FrmCategory.hasFrmCategory(id, this);
    }

    public org.semanticwb.forum.FrmAttachments getFrmAttachments(String id)
    {
        return org.semanticwb.forum.FrmAttachments.getFrmAttachments(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmAttachments> listFrmAttachmentss()
    {
        return org.semanticwb.forum.FrmAttachments.listFrmAttachmentss(this);
    }

    public org.semanticwb.forum.FrmAttachments createFrmAttachments(String id)
    {
        return org.semanticwb.forum.FrmAttachments.createFrmAttachments(id,this);
    }

    public org.semanticwb.forum.FrmAttachments createFrmAttachments()
    {
        long id=getSemanticObject().getModel().getCounter(frm_FrmAttachments);
        return org.semanticwb.forum.FrmAttachments.createFrmAttachments(String.valueOf(id),this);
    } 

    public void removeFrmAttachments(String id)
    {
        org.semanticwb.forum.FrmAttachments.removeFrmAttachments(id, this);
    }
    public boolean hasFrmAttachments(String id)
    {
        return org.semanticwb.forum.FrmAttachments.hasFrmAttachments(id, this);
    }

    public org.semanticwb.forum.FrmForum getFrmForum(String id)
    {
        return org.semanticwb.forum.FrmForum.getFrmForum(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmForum> listFrmForums()
    {
        return org.semanticwb.forum.FrmForum.listFrmForums(this);
    }

    public org.semanticwb.forum.FrmForum createFrmForum(String id)
    {
        return org.semanticwb.forum.FrmForum.createFrmForum(id,this);
    }

    public org.semanticwb.forum.FrmForum createFrmForum()
    {
        long id=getSemanticObject().getModel().getCounter(frm_FrmForum);
        return org.semanticwb.forum.FrmForum.createFrmForum(String.valueOf(id),this);
    } 

    public void removeFrmForum(String id)
    {
        org.semanticwb.forum.FrmForum.removeFrmForum(id, this);
    }
    public boolean hasFrmForum(String id)
    {
        return org.semanticwb.forum.FrmForum.hasFrmForum(id, this);
    }

    public org.semanticwb.forum.FrmThreadTypeCat getFrmThreadTypeCat(String id)
    {
        return org.semanticwb.forum.FrmThreadTypeCat.getFrmThreadTypeCat(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmThreadTypeCat> listFrmThreadTypeCats()
    {
        return org.semanticwb.forum.FrmThreadTypeCat.listFrmThreadTypeCats(this);
    }

    public org.semanticwb.forum.FrmThreadTypeCat createFrmThreadTypeCat(String id)
    {
        return org.semanticwb.forum.FrmThreadTypeCat.createFrmThreadTypeCat(id,this);
    }

    public void removeFrmThreadTypeCat(String id)
    {
        org.semanticwb.forum.FrmThreadTypeCat.removeFrmThreadTypeCat(id, this);
    }
    public boolean hasFrmThreadTypeCat(String id)
    {
        return org.semanticwb.forum.FrmThreadTypeCat.hasFrmThreadTypeCat(id, this);
    }

    public org.semanticwb.forum.FrmModererationCat getFrmModererationCat(String id)
    {
        return org.semanticwb.forum.FrmModererationCat.getFrmModererationCat(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmModererationCat> listFrmModererationCats()
    {
        return org.semanticwb.forum.FrmModererationCat.listFrmModererationCats(this);
    }

    public org.semanticwb.forum.FrmModererationCat createFrmModererationCat(String id)
    {
        return org.semanticwb.forum.FrmModererationCat.createFrmModererationCat(id,this);
    }

    public void removeFrmModererationCat(String id)
    {
        org.semanticwb.forum.FrmModererationCat.removeFrmModererationCat(id, this);
    }
    public boolean hasFrmModererationCat(String id)
    {
        return org.semanticwb.forum.FrmModererationCat.hasFrmModererationCat(id, this);
    }

    public org.semanticwb.forum.FrmTypeCat getFrmTypeCat(String id)
    {
        return org.semanticwb.forum.FrmTypeCat.getFrmTypeCat(id, this);
    }

    public java.util.Iterator<org.semanticwb.forum.FrmTypeCat> listFrmTypeCats()
    {
        return org.semanticwb.forum.FrmTypeCat.listFrmTypeCats(this);
    }

    public org.semanticwb.forum.FrmTypeCat createFrmTypeCat(String id)
    {
        return org.semanticwb.forum.FrmTypeCat.createFrmTypeCat(id,this);
    }

    public void removeFrmTypeCat(String id)
    {
        org.semanticwb.forum.FrmTypeCat.removeFrmTypeCat(id, this);
    }
    public boolean hasFrmTypeCat(String id)
    {
        return org.semanticwb.forum.FrmTypeCat.hasFrmTypeCat(id, this);
    }

    public org.semanticwb.model.Community getCommunity(String id)
    {
        return org.semanticwb.model.Community.getCommunity(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Community> listCommunitys()
    {
        return org.semanticwb.model.Community.listCommunitys(this);
    }

    public org.semanticwb.model.Community createCommunity(String id)
    {
        return org.semanticwb.model.Community.createCommunity(id,this);
    }

    public void removeCommunity(String id)
    {
        org.semanticwb.model.Community.removeCommunity(id, this);
    }
    public boolean hasCommunity(String id)
    {
        return org.semanticwb.model.Community.hasCommunity(id, this);
    }

    public org.semanticwb.model.PortletType getPortletType(String id)
    {
        return org.semanticwb.model.PortletType.getPortletType(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PortletType> listPortletTypes()
    {
        return org.semanticwb.model.PortletType.listPortletTypes(this);
    }

    public org.semanticwb.model.PortletType createPortletType(String id)
    {
        return org.semanticwb.model.PortletType.createPortletType(id,this);
    }

    public void removePortletType(String id)
    {
        org.semanticwb.model.PortletType.removePortletType(id, this);
    }
    public boolean hasPortletType(String id)
    {
        return org.semanticwb.model.PortletType.hasPortletType(id, this);
    }

    public org.semanticwb.model.RuleRef getRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.getRuleRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return org.semanticwb.model.RuleRef.listRuleRefs(this);
    }

    public org.semanticwb.model.RuleRef createRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.createRuleRef(id,this);
    }

    public org.semanticwb.model.RuleRef createRuleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RuleRef);
        return org.semanticwb.model.RuleRef.createRuleRef(String.valueOf(id),this);
    } 

    public void removeRuleRef(String id)
    {
        org.semanticwb.model.RuleRef.removeRuleRef(id, this);
    }
    public boolean hasRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.hasRuleRef(id, this);
    }

    public org.semanticwb.model.Language getLanguage(String id)
    {
        return org.semanticwb.model.Language.getLanguage(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Language> listLanguages()
    {
        return org.semanticwb.model.Language.listLanguages(this);
    }

    public org.semanticwb.model.Language createLanguage(String id)
    {
        return org.semanticwb.model.Language.createLanguage(id,this);
    }

    public void removeLanguage(String id)
    {
        org.semanticwb.model.Language.removeLanguage(id, this);
    }
    public boolean hasLanguage(String id)
    {
        return org.semanticwb.model.Language.hasLanguage(id, this);
    }

    public org.semanticwb.model.Dns getDns(String id)
    {
        return org.semanticwb.model.Dns.getDns(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Dns> listDnss()
    {
        return org.semanticwb.model.Dns.listDnss(this);
    }

    public org.semanticwb.model.Dns createDns(String id)
    {
        return org.semanticwb.model.Dns.createDns(id,this);
    }

    public void removeDns(String id)
    {
        org.semanticwb.model.Dns.removeDns(id, this);
    }
    public boolean hasDns(String id)
    {
        return org.semanticwb.model.Dns.hasDns(id, this);
    }

    public org.semanticwb.model.Rule getRule(String id)
    {
        return org.semanticwb.model.Rule.getRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Rule> listRules()
    {
        return org.semanticwb.model.Rule.listRules(this);
    }

    public org.semanticwb.model.Rule createRule(String id)
    {
        return org.semanticwb.model.Rule.createRule(id,this);
    }

    public org.semanticwb.model.Rule createRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Rule);
        return org.semanticwb.model.Rule.createRule(String.valueOf(id),this);
    } 

    public void removeRule(String id)
    {
        org.semanticwb.model.Rule.removeRule(id, this);
    }
    public boolean hasRule(String id)
    {
        return org.semanticwb.model.Rule.hasRule(id, this);
    }

    public org.semanticwb.model.Camp getCamp(String id)
    {
        return org.semanticwb.model.Camp.getCamp(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Camp> listCamps()
    {
        return org.semanticwb.model.Camp.listCamps(this);
    }

    public org.semanticwb.model.Camp createCamp(String id)
    {
        return org.semanticwb.model.Camp.createCamp(id,this);
    }

    public org.semanticwb.model.Camp createCamp()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Camp);
        return org.semanticwb.model.Camp.createCamp(String.valueOf(id),this);
    } 

    public void removeCamp(String id)
    {
        org.semanticwb.model.Camp.removeCamp(id, this);
    }
    public boolean hasCamp(String id)
    {
        return org.semanticwb.model.Camp.hasCamp(id, this);
    }

    public org.semanticwb.model.VersionInfo getVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.getVersionInfo(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        return org.semanticwb.model.VersionInfo.listVersionInfos(this);
    }

    public org.semanticwb.model.VersionInfo createVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.createVersionInfo(id,this);
    }

    public org.semanticwb.model.VersionInfo createVersionInfo()
    {
        long id=getSemanticObject().getModel().getCounter(swb_VersionInfo);
        return org.semanticwb.model.VersionInfo.createVersionInfo(String.valueOf(id),this);
    } 

    public void removeVersionInfo(String id)
    {
        org.semanticwb.model.VersionInfo.removeVersionInfo(id, this);
    }
    public boolean hasVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.hasVersionInfo(id, this);
    }

    public org.semanticwb.model.Portlet getPortlet(String id)
    {
        return org.semanticwb.model.Portlet.getPortlet(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Portlet> listPortlets()
    {
        return org.semanticwb.model.Portlet.listPortlets(this);
    }

    public org.semanticwb.model.Portlet createPortlet(String id)
    {
        return org.semanticwb.model.Portlet.createPortlet(id,this);
    }

    public org.semanticwb.model.Portlet createPortlet()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Portlet);
        return org.semanticwb.model.Portlet.createPortlet(String.valueOf(id),this);
    } 

    public void removePortlet(String id)
    {
        org.semanticwb.model.Portlet.removePortlet(id, this);
    }
    public boolean hasPortlet(String id)
    {
        return org.semanticwb.model.Portlet.hasPortlet(id, this);
    }

    public org.semanticwb.model.WebPage getWebPage(String id)
    {
        return org.semanticwb.model.WebPage.getWebPage(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return org.semanticwb.model.WebPage.listWebPages(this);
    }

    public org.semanticwb.model.WebPage createWebPage(String id)
    {
        return org.semanticwb.model.WebPage.createWebPage(id,this);
    }

    public void removeWebPage(String id)
    {
        org.semanticwb.model.WebPage.removeWebPage(id, this);
    }
    public boolean hasWebPage(String id)
    {
        return org.semanticwb.model.WebPage.hasWebPage(id, this);
    }

    public org.semanticwb.model.Calendar getCalendar(String id)
    {
        return org.semanticwb.model.Calendar.getCalendar(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return org.semanticwb.model.Calendar.listCalendars(this);
    }

    public org.semanticwb.model.Calendar createCalendar(String id)
    {
        return org.semanticwb.model.Calendar.createCalendar(id,this);
    }

    public org.semanticwb.model.Calendar createCalendar()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Calendar);
        return org.semanticwb.model.Calendar.createCalendar(String.valueOf(id),this);
    } 

    public void removeCalendar(String id)
    {
        org.semanticwb.model.Calendar.removeCalendar(id, this);
    }
    public boolean hasCalendar(String id)
    {
        return org.semanticwb.model.Calendar.hasCalendar(id, this);
    }

    public org.semanticwb.model.Device getDevice(String id)
    {
        return org.semanticwb.model.Device.getDevice(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Device> listDevices()
    {
        return org.semanticwb.model.Device.listDevices(this);
    }

    public org.semanticwb.model.Device createDevice(String id)
    {
        return org.semanticwb.model.Device.createDevice(id,this);
    }

    public org.semanticwb.model.Device createDevice()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Device);
        return org.semanticwb.model.Device.createDevice(String.valueOf(id),this);
    } 

    public void removeDevice(String id)
    {
        org.semanticwb.model.Device.removeDevice(id, this);
    }
    public boolean hasDevice(String id)
    {
        return org.semanticwb.model.Device.hasDevice(id, this);
    }

    public org.semanticwb.model.AdminFilter getAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.getAdminFilter(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return org.semanticwb.model.AdminFilter.listAdminFilters(this);
    }

    public org.semanticwb.model.AdminFilter createAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.createAdminFilter(id,this);
    }

    public org.semanticwb.model.AdminFilter createAdminFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AdminFilter);
        return org.semanticwb.model.AdminFilter.createAdminFilter(String.valueOf(id),this);
    } 

    public void removeAdminFilter(String id)
    {
        org.semanticwb.model.AdminFilter.removeAdminFilter(id, this);
    }
    public boolean hasAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.hasAdminFilter(id, this);
    }

    public org.semanticwb.model.IPFilter getIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.getIPFilter(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        return org.semanticwb.model.IPFilter.listIPFilters(this);
    }

    public org.semanticwb.model.IPFilter createIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.createIPFilter(id,this);
    }

    public org.semanticwb.model.IPFilter createIPFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_IPFilter);
        return org.semanticwb.model.IPFilter.createIPFilter(String.valueOf(id),this);
    } 

    public void removeIPFilter(String id)
    {
        org.semanticwb.model.IPFilter.removeIPFilter(id, this);
    }
    public boolean hasIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.hasIPFilter(id, this);
    }

    public org.semanticwb.model.PFlowRef getPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.getPFlowRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return org.semanticwb.model.PFlowRef.listPFlowRefs(this);
    }

    public org.semanticwb.model.PFlowRef createPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.createPFlowRef(id,this);
    }

    public org.semanticwb.model.PFlowRef createPFlowRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowRef);
        return org.semanticwb.model.PFlowRef.createPFlowRef(String.valueOf(id),this);
    } 

    public void removePFlowRef(String id)
    {
        org.semanticwb.model.PFlowRef.removePFlowRef(id, this);
    }
    public boolean hasPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.hasPFlowRef(id, this);
    }

    public org.semanticwb.model.PortletRef getPortletRef(String id)
    {
        return org.semanticwb.model.PortletRef.getPortletRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        return org.semanticwb.model.PortletRef.listPortletRefs(this);
    }

    public org.semanticwb.model.PortletRef createPortletRef(String id)
    {
        return org.semanticwb.model.PortletRef.createPortletRef(id,this);
    }

    public org.semanticwb.model.PortletRef createPortletRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PortletRef);
        return org.semanticwb.model.PortletRef.createPortletRef(String.valueOf(id),this);
    } 

    public void removePortletRef(String id)
    {
        org.semanticwb.model.PortletRef.removePortletRef(id, this);
    }
    public boolean hasPortletRef(String id)
    {
        return org.semanticwb.model.PortletRef.hasPortletRef(id, this);
    }

    public org.semanticwb.model.Permission getPermission(String id)
    {
        return org.semanticwb.model.Permission.getPermission(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Permission> listPermissions()
    {
        return org.semanticwb.model.Permission.listPermissions(this);
    }

    public org.semanticwb.model.Permission createPermission(String id)
    {
        return org.semanticwb.model.Permission.createPermission(id,this);
    }

    public org.semanticwb.model.Permission createPermission()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Permission);
        return org.semanticwb.model.Permission.createPermission(String.valueOf(id),this);
    } 

    public void removePermission(String id)
    {
        org.semanticwb.model.Permission.removePermission(id, this);
    }
    public boolean hasPermission(String id)
    {
        return org.semanticwb.model.Permission.hasPermission(id, this);
    }

    public org.semanticwb.model.PortletFilter getPortletFilter(String id)
    {
        return org.semanticwb.model.PortletFilter.getPortletFilter(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PortletFilter> listPortletFilters()
    {
        return org.semanticwb.model.PortletFilter.listPortletFilters(this);
    }

    public org.semanticwb.model.PortletFilter createPortletFilter(String id)
    {
        return org.semanticwb.model.PortletFilter.createPortletFilter(id,this);
    }

    public org.semanticwb.model.PortletFilter createPortletFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PortletFilter);
        return org.semanticwb.model.PortletFilter.createPortletFilter(String.valueOf(id),this);
    } 

    public void removePortletFilter(String id)
    {
        org.semanticwb.model.PortletFilter.removePortletFilter(id, this);
    }
    public boolean hasPortletFilter(String id)
    {
        return org.semanticwb.model.PortletFilter.hasPortletFilter(id, this);
    }

    public org.semanticwb.model.Template getTemplate(String id)
    {
        return org.semanticwb.model.Template.getTemplate(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Template> listTemplates()
    {
        return org.semanticwb.model.Template.listTemplates(this);
    }

    public org.semanticwb.model.Template createTemplate(String id)
    {
        return org.semanticwb.model.Template.createTemplate(id,this);
    }

    public org.semanticwb.model.Template createTemplate()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Template);
        return org.semanticwb.model.Template.createTemplate(String.valueOf(id),this);
    } 

    public void removeTemplate(String id)
    {
        org.semanticwb.model.Template.removeTemplate(id, this);
    }
    public boolean hasTemplate(String id)
    {
        return org.semanticwb.model.Template.hasTemplate(id, this);
    }

    public org.semanticwb.model.PFlow getPFlow(String id)
    {
        return org.semanticwb.model.PFlow.getPFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        return org.semanticwb.model.PFlow.listPFlows(this);
    }

    public org.semanticwb.model.PFlow createPFlow(String id)
    {
        return org.semanticwb.model.PFlow.createPFlow(id,this);
    }

    public org.semanticwb.model.PFlow createPFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlow);
        return org.semanticwb.model.PFlow.createPFlow(String.valueOf(id),this);
    } 

    public void removePFlow(String id)
    {
        org.semanticwb.model.PFlow.removePFlow(id, this);
    }
    public boolean hasPFlow(String id)
    {
        return org.semanticwb.model.PFlow.hasPFlow(id, this);
    }

    public org.semanticwb.model.TemplateRef getTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.getTemplateRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return org.semanticwb.model.TemplateRef.listTemplateRefs(this);
    }

    public org.semanticwb.model.TemplateRef createTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.createTemplateRef(id,this);
    }

    public org.semanticwb.model.TemplateRef createTemplateRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateRef);
        return org.semanticwb.model.TemplateRef.createTemplateRef(String.valueOf(id),this);
    } 

    public void removeTemplateRef(String id)
    {
        org.semanticwb.model.TemplateRef.removeTemplateRef(id, this);
    }
    public boolean hasTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.hasTemplateRef(id, this);
    }

    public org.semanticwb.model.TemplateGroup getTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.getTemplateGroup(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups()
    {
        return org.semanticwb.model.TemplateGroup.listTemplateGroups(this);
    }

    public org.semanticwb.model.TemplateGroup createTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.createTemplateGroup(id,this);
    }

    public org.semanticwb.model.TemplateGroup createTemplateGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateGroup);
        return org.semanticwb.model.TemplateGroup.createTemplateGroup(String.valueOf(id),this);
    } 

    public void removeTemplateGroup(String id)
    {
        org.semanticwb.model.TemplateGroup.removeTemplateGroup(id, this);
    }
    public boolean hasTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.hasTemplateGroup(id, this);
    }

    public org.semanticwb.model.RoleRef getRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.getRoleRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return org.semanticwb.model.RoleRef.listRoleRefs(this);
    }

    public org.semanticwb.model.RoleRef createRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.createRoleRef(id,this);
    }

    public org.semanticwb.model.RoleRef createRoleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RoleRef);
        return org.semanticwb.model.RoleRef.createRoleRef(String.valueOf(id),this);
    } 

    public void removeRoleRef(String id)
    {
        org.semanticwb.model.RoleRef.removeRoleRef(id, this);
    }
    public boolean hasRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.hasRoleRef(id, this);
    }

    public org.semanticwb.model.PortletSubType getPortletSubType(String id)
    {
        return org.semanticwb.model.PortletSubType.getPortletSubType(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PortletSubType> listPortletSubTypes()
    {
        return org.semanticwb.model.PortletSubType.listPortletSubTypes(this);
    }

    public org.semanticwb.model.PortletSubType createPortletSubType(String id)
    {
        return org.semanticwb.model.PortletSubType.createPortletSubType(id,this);
    }

    public void removePortletSubType(String id)
    {
        org.semanticwb.model.PortletSubType.removePortletSubType(id, this);
    }
    public boolean hasPortletSubType(String id)
    {
        return org.semanticwb.model.PortletSubType.hasPortletSubType(id, this);
    }
}
