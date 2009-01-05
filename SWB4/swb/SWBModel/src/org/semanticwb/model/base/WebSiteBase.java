package org.semanticwb.model.base;


public class WebSiteBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Versionable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.Deleteable
{
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
    public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swb_Community=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Community");
    public static final org.semanticwb.platform.SemanticClass swb_PortletType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletType");
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    public static final org.semanticwb.platform.SemanticClass swb_Dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    public static final org.semanticwb.platform.SemanticClass swb_Portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portlet");
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    public static final org.semanticwb.platform.SemanticClass swb_IPFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#IPFilter");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticClass swb_PortletRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");
    public static final org.semanticwb.platform.SemanticClass swb_Permission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Permission");
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticClass swb_PortletSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletSubType");
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    public WebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(swb_actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(swb_lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
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

    public org.semanticwb.model.Community getCommunity(String id)
    {
        return (org.semanticwb.model.Community)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Community),swb_Community);
    }

    public java.util.Iterator<org.semanticwb.model.Community> listCommunitys()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Community.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Community>(org.semanticwb.model.Community.class, stit, true);
    }

    public org.semanticwb.model.Community createCommunity(String id)
    {
        return (org.semanticwb.model.Community)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Community), swb_Community);
    }

    public void removeCommunity(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Community));
    }
    public boolean hasCommunity(String id)
    {
        return (getCommunity(id)!=null);
    }

    public org.semanticwb.model.PortletType getPortletType(String id)
    {
        return (org.semanticwb.model.PortletType)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_PortletType),swb_PortletType);
    }

    public java.util.Iterator<org.semanticwb.model.PortletType> listPortletTypes()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_PortletType.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PortletType>(org.semanticwb.model.PortletType.class, stit, true);
    }

    public org.semanticwb.model.PortletType createPortletType(String id)
    {
        return (org.semanticwb.model.PortletType)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_PortletType), swb_PortletType);
    }

    public void removePortletType(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_PortletType));
    }
    public boolean hasPortletType(String id)
    {
        return (getPortletType(id)!=null);
    }

    public org.semanticwb.model.RuleRef getRuleRef(String id)
    {
        return (org.semanticwb.model.RuleRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_RuleRef),swb_RuleRef);
    }

    public java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_RuleRef.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, stit, true);
    }

    public org.semanticwb.model.RuleRef createRuleRef(String id)
    {
        return (org.semanticwb.model.RuleRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_RuleRef), swb_RuleRef);
    }

    public org.semanticwb.model.RuleRef createRuleRef()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_RuleRef.getName());
        return createRuleRef(""+id);
    } 

    public void removeRuleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_RuleRef));
    }
    public boolean hasRuleRef(String id)
    {
        return (getRuleRef(id)!=null);
    }

    public org.semanticwb.model.Language getLanguage(String id)
    {
        return (org.semanticwb.model.Language)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Language),swb_Language);
    }

    public java.util.Iterator<org.semanticwb.model.Language> listLanguages()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Language.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Language>(org.semanticwb.model.Language.class, stit, true);
    }

    public org.semanticwb.model.Language createLanguage(String id)
    {
        return (org.semanticwb.model.Language)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Language), swb_Language);
    }

    public void removeLanguage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Language));
    }
    public boolean hasLanguage(String id)
    {
        return (getLanguage(id)!=null);
    }

    public org.semanticwb.model.Dns getDns(String id)
    {
        return (org.semanticwb.model.Dns)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Dns),swb_Dns);
    }

    public java.util.Iterator<org.semanticwb.model.Dns> listDnss()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Dns.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns>(org.semanticwb.model.Dns.class, stit, true);
    }

    public org.semanticwb.model.Dns createDns(String id)
    {
        return (org.semanticwb.model.Dns)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Dns), swb_Dns);
    }

    public void removeDns(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Dns));
    }
    public boolean hasDns(String id)
    {
        return (getDns(id)!=null);
    }

    public org.semanticwb.model.Rule getRule(String id)
    {
        return (org.semanticwb.model.Rule)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Rule),swb_Rule);
    }

    public java.util.Iterator<org.semanticwb.model.Rule> listRules()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Rule.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule>(org.semanticwb.model.Rule.class, stit, true);
    }

    public org.semanticwb.model.Rule createRule(String id)
    {
        return (org.semanticwb.model.Rule)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Rule), swb_Rule);
    }

    public org.semanticwb.model.Rule createRule()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Rule.getName());
        return createRule(""+id);
    } 

    public void removeRule(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Rule));
    }
    public boolean hasRule(String id)
    {
        return (getRule(id)!=null);
    }

    public org.semanticwb.model.Camp getCamp(String id)
    {
        return (org.semanticwb.model.Camp)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Camp),swb_Camp);
    }

    public java.util.Iterator<org.semanticwb.model.Camp> listCamps()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Camp.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp>(org.semanticwb.model.Camp.class, stit, true);
    }

    public org.semanticwb.model.Camp createCamp(String id)
    {
        return (org.semanticwb.model.Camp)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Camp), swb_Camp);
    }

    public org.semanticwb.model.Camp createCamp()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Camp.getName());
        return createCamp(""+id);
    } 

    public void removeCamp(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Camp));
    }
    public boolean hasCamp(String id)
    {
        return (getCamp(id)!=null);
    }

    public org.semanticwb.model.VersionInfo getVersionInfo(String id)
    {
        return (org.semanticwb.model.VersionInfo)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_VersionInfo),swb_VersionInfo);
    }

    public java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_VersionInfo.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo>(org.semanticwb.model.VersionInfo.class, stit, true);
    }

    public org.semanticwb.model.VersionInfo createVersionInfo(String id)
    {
        return (org.semanticwb.model.VersionInfo)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_VersionInfo), swb_VersionInfo);
    }

    public org.semanticwb.model.VersionInfo createVersionInfo()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_VersionInfo.getName());
        return createVersionInfo(""+id);
    } 

    public void removeVersionInfo(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_VersionInfo));
    }
    public boolean hasVersionInfo(String id)
    {
        return (getVersionInfo(id)!=null);
    }

    public org.semanticwb.model.Portlet getPortlet(String id)
    {
        return (org.semanticwb.model.Portlet)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Portlet),swb_Portlet);
    }

    public java.util.Iterator<org.semanticwb.model.Portlet> listPortlets()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Portlet.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Portlet>(org.semanticwb.model.Portlet.class, stit, true);
    }

    public org.semanticwb.model.Portlet createPortlet(String id)
    {
        return (org.semanticwb.model.Portlet)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Portlet), swb_Portlet);
    }

    public org.semanticwb.model.Portlet createPortlet()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Portlet.getName());
        return createPortlet(""+id);
    } 

    public void removePortlet(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Portlet));
    }
    public boolean hasPortlet(String id)
    {
        return (getPortlet(id)!=null);
    }

    public org.semanticwb.model.WebPage getWebPage(String id)
    {
        return (org.semanticwb.model.WebPage)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_WebPage),swb_WebPage);
    }

    public java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_WebPage.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit, true);
    }

    public org.semanticwb.model.WebPage createWebPage(String id)
    {
        return (org.semanticwb.model.WebPage)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_WebPage), swb_WebPage);
    }

    public void removeWebPage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_WebPage));
    }
    public boolean hasWebPage(String id)
    {
        return (getWebPage(id)!=null);
    }

    public org.semanticwb.model.Calendar getCalendar(String id)
    {
        return (org.semanticwb.model.Calendar)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Calendar),swb_Calendar);
    }

    public java.util.Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Calendar.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit, true);
    }

    public org.semanticwb.model.Calendar createCalendar(String id)
    {
        return (org.semanticwb.model.Calendar)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Calendar), swb_Calendar);
    }

    public org.semanticwb.model.Calendar createCalendar()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Calendar.getName());
        return createCalendar(""+id);
    } 

    public void removeCalendar(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Calendar));
    }
    public boolean hasCalendar(String id)
    {
        return (getCalendar(id)!=null);
    }

    public org.semanticwb.model.Device getDevice(String id)
    {
        return (org.semanticwb.model.Device)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Device),swb_Device);
    }

    public java.util.Iterator<org.semanticwb.model.Device> listDevices()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Device.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(org.semanticwb.model.Device.class, stit, true);
    }

    public org.semanticwb.model.Device createDevice(String id)
    {
        return (org.semanticwb.model.Device)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Device), swb_Device);
    }

    public org.semanticwb.model.Device createDevice()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Device.getName());
        return createDevice(""+id);
    } 

    public void removeDevice(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Device));
    }
    public boolean hasDevice(String id)
    {
        return (getDevice(id)!=null);
    }

    public org.semanticwb.model.IPFilter getIPFilter(String id)
    {
        return (org.semanticwb.model.IPFilter)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_IPFilter),swb_IPFilter);
    }

    public java.util.Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_IPFilter.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.IPFilter>(org.semanticwb.model.IPFilter.class, stit, true);
    }

    public org.semanticwb.model.IPFilter createIPFilter(String id)
    {
        return (org.semanticwb.model.IPFilter)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_IPFilter), swb_IPFilter);
    }

    public org.semanticwb.model.IPFilter createIPFilter()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_IPFilter.getName());
        return createIPFilter(""+id);
    } 

    public void removeIPFilter(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_IPFilter));
    }
    public boolean hasIPFilter(String id)
    {
        return (getIPFilter(id)!=null);
    }

    public org.semanticwb.model.PFlowRef getPFlowRef(String id)
    {
        return (org.semanticwb.model.PFlowRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_PFlowRef),swb_PFlowRef);
    }

    public java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_PFlowRef.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, stit, true);
    }

    public org.semanticwb.model.PFlowRef createPFlowRef(String id)
    {
        return (org.semanticwb.model.PFlowRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_PFlowRef), swb_PFlowRef);
    }

    public void removePFlowRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_PFlowRef));
    }
    public boolean hasPFlowRef(String id)
    {
        return (getPFlowRef(id)!=null);
    }

    public org.semanticwb.model.PortletRef getPortletRef(String id)
    {
        return (org.semanticwb.model.PortletRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_PortletRef),swb_PortletRef);
    }

    public java.util.Iterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_PortletRef.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, stit, true);
    }

    public org.semanticwb.model.PortletRef createPortletRef(String id)
    {
        return (org.semanticwb.model.PortletRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_PortletRef), swb_PortletRef);
    }

    public org.semanticwb.model.PortletRef createPortletRef()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_PortletRef.getName());
        return createPortletRef(""+id);
    } 

    public void removePortletRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_PortletRef));
    }
    public boolean hasPortletRef(String id)
    {
        return (getPortletRef(id)!=null);
    }

    public org.semanticwb.model.Permission getPermission(String id)
    {
        return (org.semanticwb.model.Permission)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Permission),swb_Permission);
    }

    public java.util.Iterator<org.semanticwb.model.Permission> listPermissions()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Permission.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Permission>(org.semanticwb.model.Permission.class, stit, true);
    }

    public org.semanticwb.model.Permission createPermission(String id)
    {
        return (org.semanticwb.model.Permission)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Permission), swb_Permission);
    }

    public org.semanticwb.model.Permission createPermission()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Permission.getName());
        return createPermission(""+id);
    } 

    public void removePermission(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Permission));
    }
    public boolean hasPermission(String id)
    {
        return (getPermission(id)!=null);
    }

    public org.semanticwb.model.Template getTemplate(String id)
    {
        return (org.semanticwb.model.Template)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Template),swb_Template);
    }

    public java.util.Iterator<org.semanticwb.model.Template> listTemplates()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Template.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(org.semanticwb.model.Template.class, stit, true);
    }

    public org.semanticwb.model.Template createTemplate(String id)
    {
        return (org.semanticwb.model.Template)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Template), swb_Template);
    }

    public org.semanticwb.model.Template createTemplate()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Template.getName());
        return createTemplate(""+id);
    } 

    public void removeTemplate(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Template));
    }
    public boolean hasTemplate(String id)
    {
        return (getTemplate(id)!=null);
    }

    public org.semanticwb.model.PFlow getPFlow(String id)
    {
        return (org.semanticwb.model.PFlow)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_PFlow),swb_PFlow);
    }

    public java.util.Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_PFlow.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow>(org.semanticwb.model.PFlow.class, stit, true);
    }

    public org.semanticwb.model.PFlow createPFlow(String id)
    {
        return (org.semanticwb.model.PFlow)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_PFlow), swb_PFlow);
    }

    public org.semanticwb.model.PFlow createPFlow()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_PFlow.getName());
        return createPFlow(""+id);
    } 

    public void removePFlow(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_PFlow));
    }
    public boolean hasPFlow(String id)
    {
        return (getPFlow(id)!=null);
    }

    public org.semanticwb.model.TemplateRef getTemplateRef(String id)
    {
        return (org.semanticwb.model.TemplateRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_TemplateRef),swb_TemplateRef);
    }

    public java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_TemplateRef.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, stit, true);
    }

    public org.semanticwb.model.TemplateRef createTemplateRef(String id)
    {
        return (org.semanticwb.model.TemplateRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_TemplateRef), swb_TemplateRef);
    }

    public org.semanticwb.model.TemplateRef createTemplateRef()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_TemplateRef.getName());
        return createTemplateRef(""+id);
    } 

    public void removeTemplateRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_TemplateRef));
    }
    public boolean hasTemplateRef(String id)
    {
        return (getTemplateRef(id)!=null);
    }

    public org.semanticwb.model.TemplateGroup getTemplateGroup(String id)
    {
        return (org.semanticwb.model.TemplateGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_TemplateGroup),swb_TemplateGroup);
    }

    public java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_TemplateGroup.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateGroup>(org.semanticwb.model.TemplateGroup.class, stit, true);
    }

    public org.semanticwb.model.TemplateGroup createTemplateGroup(String id)
    {
        return (org.semanticwb.model.TemplateGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_TemplateGroup), swb_TemplateGroup);
    }

    public org.semanticwb.model.TemplateGroup createTemplateGroup()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_TemplateGroup.getName());
        return createTemplateGroup(""+id);
    } 

    public void removeTemplateGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_TemplateGroup));
    }
    public boolean hasTemplateGroup(String id)
    {
        return (getTemplateGroup(id)!=null);
    }

    public org.semanticwb.model.RoleRef getRoleRef(String id)
    {
        return (org.semanticwb.model.RoleRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_RoleRef),swb_RoleRef);
    }

    public java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_RoleRef.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, stit, true);
    }

    public org.semanticwb.model.RoleRef createRoleRef(String id)
    {
        return (org.semanticwb.model.RoleRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_RoleRef), swb_RoleRef);
    }

    public org.semanticwb.model.RoleRef createRoleRef()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_RoleRef.getName());
        return createRoleRef(""+id);
    } 

    public void removeRoleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_RoleRef));
    }
    public boolean hasRoleRef(String id)
    {
        return (getRoleRef(id)!=null);
    }

    public org.semanticwb.model.PortletSubType getPortletSubType(String id)
    {
        return (org.semanticwb.model.PortletSubType)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_PortletSubType),swb_PortletSubType);
    }

    public java.util.Iterator<org.semanticwb.model.PortletSubType> listPortletSubTypes()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_PortletSubType.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PortletSubType>(org.semanticwb.model.PortletSubType.class, stit, true);
    }

    public org.semanticwb.model.PortletSubType createPortletSubType(String id)
    {
        return (org.semanticwb.model.PortletSubType)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_PortletSubType), swb_PortletSubType);
    }

    public void removePortletSubType(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_PortletSubType));
    }
    public boolean hasPortletSubType(String id)
    {
        return (getPortletSubType(id)!=null);
    }
}
