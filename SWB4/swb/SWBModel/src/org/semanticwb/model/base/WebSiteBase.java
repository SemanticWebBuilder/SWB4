package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebSiteBase extends GenericObjectBase implements Versionable,Descriptiveable,Traceable,Localeable,Activeable,Deleteable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebSiteBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_created, created);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_deleted, deleted);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_active, active);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.swb_modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_title, title, lang);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(vocabulary.swb_actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_actualVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.swb_VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.swb_language);
    }

    public Language getLanguage()
    {
         Language ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_language);
         if(obj!=null)
         {
             ret=(Language)vocabulary.swb_Language.newGenericInstance(obj);
         }
         return ret;
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_updated, updated);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(vocabulary.swb_lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_lastVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.swb_VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.swb_creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public void setHomePage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_homePage, webpage.getSemanticObject());
    }

    public void removeHomePage()
    {
        getSemanticObject().removeProperty(vocabulary.swb_homePage);
    }

    public WebPage getHomePage()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_homePage);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public void setUserRepository(org.semanticwb.model.UserRepository userrepository)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_userRepository, userrepository.getSemanticObject());
    }

    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(vocabulary.swb_userRepository);
    }

    public UserRepository getUserRepository()
    {
         UserRepository ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_userRepository);
         if(obj!=null)
         {
             ret=(UserRepository)vocabulary.swb_UserRepository.newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_description, description, lang);
    }

    public Community getCommunity(String id)
    {
        return (Community)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Community),vocabulary.swb_Community);
    }

    public Iterator<Community> listCommunitys()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Community.getOntClass());
        return new GenericIterator<Community>(Community.class, stit, true);
    }

    public Community createCommunity(String id)
    {
        return (Community)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Community), vocabulary.swb_Community);
    }

    public void removeCommunity(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Community));
    }
    public boolean hasCommunity(String id)
    {
        return (getCommunity(id)!=null);
    }

    public PortletType getPortletType(String id)
    {
        return (PortletType)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PortletType),vocabulary.swb_PortletType);
    }

    public Iterator<PortletType> listPortletTypes()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_PortletType.getOntClass());
        return new GenericIterator<PortletType>(PortletType.class, stit, true);
    }

    public PortletType createPortletType(String id)
    {
        return (PortletType)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_PortletType), vocabulary.swb_PortletType);
    }

    public void removePortletType(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PortletType));
    }
    public boolean hasPortletType(String id)
    {
        return (getPortletType(id)!=null);
    }

    public RuleRef getRuleRef(String id)
    {
        return (RuleRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_RuleRef),vocabulary.swb_RuleRef);
    }

    public Iterator<RuleRef> listRuleRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_RuleRef.getOntClass());
        return new GenericIterator<RuleRef>(RuleRef.class, stit, true);
    }

    public RuleRef createRuleRef(String id)
    {
        return (RuleRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_RuleRef), vocabulary.swb_RuleRef);
    }

    public RuleRef createRuleRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_RuleRef.getName());
        return createRuleRef(""+id);
    } 

    public void removeRuleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_RuleRef));
    }
    public boolean hasRuleRef(String id)
    {
        return (getRuleRef(id)!=null);
    }

    public Language getLanguage(String id)
    {
        return (Language)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Language),vocabulary.swb_Language);
    }

    public Iterator<Language> listLanguages()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Language.getOntClass());
        return new GenericIterator<Language>(Language.class, stit, true);
    }

    public Language createLanguage(String id)
    {
        return (Language)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Language), vocabulary.swb_Language);
    }

    public void removeLanguage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Language));
    }
    public boolean hasLanguage(String id)
    {
        return (getLanguage(id)!=null);
    }

    public Dns getDns(String id)
    {
        return (Dns)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Dns),vocabulary.swb_Dns);
    }

    public Iterator<Dns> listDnss()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Dns.getOntClass());
        return new GenericIterator<Dns>(Dns.class, stit, true);
    }

    public Dns createDns(String id)
    {
        return (Dns)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Dns), vocabulary.swb_Dns);
    }

    public void removeDns(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Dns));
    }
    public boolean hasDns(String id)
    {
        return (getDns(id)!=null);
    }

    public Rule getRule(String id)
    {
        return (Rule)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Rule),vocabulary.swb_Rule);
    }

    public Iterator<Rule> listRules()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Rule.getOntClass());
        return new GenericIterator<Rule>(Rule.class, stit, true);
    }

    public Rule createRule(String id)
    {
        return (Rule)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Rule), vocabulary.swb_Rule);
    }

    public Rule createRule()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Rule.getName());
        return createRule(""+id);
    } 

    public void removeRule(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Rule));
    }
    public boolean hasRule(String id)
    {
        return (getRule(id)!=null);
    }

    public Camp getCamp(String id)
    {
        return (Camp)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Camp),vocabulary.swb_Camp);
    }

    public Iterator<Camp> listCamps()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Camp.getOntClass());
        return new GenericIterator<Camp>(Camp.class, stit, true);
    }

    public Camp createCamp(String id)
    {
        return (Camp)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Camp), vocabulary.swb_Camp);
    }

    public Camp createCamp()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Camp.getName());
        return createCamp(""+id);
    } 

    public void removeCamp(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Camp));
    }
    public boolean hasCamp(String id)
    {
        return (getCamp(id)!=null);
    }

    public VersionInfo getVersionInfo(String id)
    {
        return (VersionInfo)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_VersionInfo),vocabulary.swb_VersionInfo);
    }

    public Iterator<VersionInfo> listVersionInfos()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_VersionInfo.getOntClass());
        return new GenericIterator<VersionInfo>(VersionInfo.class, stit, true);
    }

    public VersionInfo createVersionInfo(String id)
    {
        return (VersionInfo)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_VersionInfo), vocabulary.swb_VersionInfo);
    }

    public VersionInfo createVersionInfo()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_VersionInfo.getName());
        return createVersionInfo(""+id);
    } 

    public void removeVersionInfo(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_VersionInfo));
    }
    public boolean hasVersionInfo(String id)
    {
        return (getVersionInfo(id)!=null);
    }

    public Portlet getPortlet(String id)
    {
        return (Portlet)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Portlet),vocabulary.swb_Portlet);
    }

    public Iterator<Portlet> listPortlets()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Portlet.getOntClass());
        return new GenericIterator<Portlet>(Portlet.class, stit, true);
    }

    public Portlet createPortlet(String id)
    {
        return (Portlet)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Portlet), vocabulary.swb_Portlet);
    }

    public Portlet createPortlet()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Portlet.getName());
        return createPortlet(""+id);
    } 

    public void removePortlet(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Portlet));
    }
    public boolean hasPortlet(String id)
    {
        return (getPortlet(id)!=null);
    }

    public WebPage getWebPage(String id)
    {
        return (WebPage)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_WebPage),vocabulary.swb_WebPage);
    }

    public Iterator<WebPage> listWebPages()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_WebPage.getOntClass());
        return new GenericIterator<WebPage>(WebPage.class, stit, true);
    }

    public WebPage createWebPage(String id)
    {
        return (WebPage)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_WebPage), vocabulary.swb_WebPage);
    }

    public void removeWebPage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_WebPage));
    }
    public boolean hasWebPage(String id)
    {
        return (getWebPage(id)!=null);
    }

    public Calendar getCalendar(String id)
    {
        return (Calendar)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Calendar),vocabulary.swb_Calendar);
    }

    public Iterator<Calendar> listCalendars()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Calendar.getOntClass());
        return new GenericIterator<Calendar>(Calendar.class, stit, true);
    }

    public Calendar createCalendar(String id)
    {
        return (Calendar)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Calendar), vocabulary.swb_Calendar);
    }

    public Calendar createCalendar()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Calendar.getName());
        return createCalendar(""+id);
    } 

    public void removeCalendar(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Calendar));
    }
    public boolean hasCalendar(String id)
    {
        return (getCalendar(id)!=null);
    }

    public Device getDevice(String id)
    {
        return (Device)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Device),vocabulary.swb_Device);
    }

    public Iterator<Device> listDevices()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Device.getOntClass());
        return new GenericIterator<Device>(Device.class, stit, true);
    }

    public Device createDevice(String id)
    {
        return (Device)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Device), vocabulary.swb_Device);
    }

    public Device createDevice()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Device.getName());
        return createDevice(""+id);
    } 

    public void removeDevice(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Device));
    }
    public boolean hasDevice(String id)
    {
        return (getDevice(id)!=null);
    }

    public IPFilter getIPFilter(String id)
    {
        return (IPFilter)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_IPFilter),vocabulary.swb_IPFilter);
    }

    public Iterator<IPFilter> listIPFilters()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_IPFilter.getOntClass());
        return new GenericIterator<IPFilter>(IPFilter.class, stit, true);
    }

    public IPFilter createIPFilter(String id)
    {
        return (IPFilter)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_IPFilter), vocabulary.swb_IPFilter);
    }

    public IPFilter createIPFilter()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_IPFilter.getName());
        return createIPFilter(""+id);
    } 

    public void removeIPFilter(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_IPFilter));
    }
    public boolean hasIPFilter(String id)
    {
        return (getIPFilter(id)!=null);
    }

    public PFlowRef getPFlowRef(String id)
    {
        return (PFlowRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PFlowRef),vocabulary.swb_PFlowRef);
    }

    public Iterator<PFlowRef> listPFlowRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_PFlowRef.getOntClass());
        return new GenericIterator<PFlowRef>(PFlowRef.class, stit, true);
    }

    public PFlowRef createPFlowRef(String id)
    {
        return (PFlowRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_PFlowRef), vocabulary.swb_PFlowRef);
    }

    public void removePFlowRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PFlowRef));
    }
    public boolean hasPFlowRef(String id)
    {
        return (getPFlowRef(id)!=null);
    }

    public PortletRef getPortletRef(String id)
    {
        return (PortletRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PortletRef),vocabulary.swb_PortletRef);
    }

    public Iterator<PortletRef> listPortletRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_PortletRef.getOntClass());
        return new GenericIterator<PortletRef>(PortletRef.class, stit, true);
    }

    public PortletRef createPortletRef(String id)
    {
        return (PortletRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_PortletRef), vocabulary.swb_PortletRef);
    }

    public PortletRef createPortletRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_PortletRef.getName());
        return createPortletRef(""+id);
    } 

    public void removePortletRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PortletRef));
    }
    public boolean hasPortletRef(String id)
    {
        return (getPortletRef(id)!=null);
    }

    public Template getTemplate(String id)
    {
        return (Template)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Template),vocabulary.swb_Template);
    }

    public Iterator<Template> listTemplates()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Template.getOntClass());
        return new GenericIterator<Template>(Template.class, stit, true);
    }

    public Template createTemplate(String id)
    {
        return (Template)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Template), vocabulary.swb_Template);
    }

    public Template createTemplate()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Template.getName());
        return createTemplate(""+id);
    } 

    public void removeTemplate(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Template));
    }
    public boolean hasTemplate(String id)
    {
        return (getTemplate(id)!=null);
    }

    public PFlow getPFlow(String id)
    {
        return (PFlow)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PFlow),vocabulary.swb_PFlow);
    }

    public Iterator<PFlow> listPFlows()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_PFlow.getOntClass());
        return new GenericIterator<PFlow>(PFlow.class, stit, true);
    }

    public PFlow createPFlow(String id)
    {
        return (PFlow)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_PFlow), vocabulary.swb_PFlow);
    }

    public PFlow createPFlow()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_PFlow.getName());
        return createPFlow(""+id);
    } 

    public void removePFlow(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PFlow));
    }
    public boolean hasPFlow(String id)
    {
        return (getPFlow(id)!=null);
    }

    public TemplateRef getTemplateRef(String id)
    {
        return (TemplateRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_TemplateRef),vocabulary.swb_TemplateRef);
    }

    public Iterator<TemplateRef> listTemplateRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_TemplateRef.getOntClass());
        return new GenericIterator<TemplateRef>(TemplateRef.class, stit, true);
    }

    public TemplateRef createTemplateRef(String id)
    {
        return (TemplateRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_TemplateRef), vocabulary.swb_TemplateRef);
    }

    public TemplateRef createTemplateRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_TemplateRef.getName());
        return createTemplateRef(""+id);
    } 

    public void removeTemplateRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_TemplateRef));
    }
    public boolean hasTemplateRef(String id)
    {
        return (getTemplateRef(id)!=null);
    }

    public TemplateGroup getTemplateGroup(String id)
    {
        return (TemplateGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_TemplateGroup),vocabulary.swb_TemplateGroup);
    }

    public Iterator<TemplateGroup> listTemplateGroups()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_TemplateGroup.getOntClass());
        return new GenericIterator<TemplateGroup>(TemplateGroup.class, stit, true);
    }

    public TemplateGroup createTemplateGroup(String id)
    {
        return (TemplateGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_TemplateGroup), vocabulary.swb_TemplateGroup);
    }

    public TemplateGroup createTemplateGroup()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_TemplateGroup.getName());
        return createTemplateGroup(""+id);
    } 

    public void removeTemplateGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_TemplateGroup));
    }
    public boolean hasTemplateGroup(String id)
    {
        return (getTemplateGroup(id)!=null);
    }

    public RoleRef getRoleRef(String id)
    {
        return (RoleRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_RoleRef),vocabulary.swb_RoleRef);
    }

    public Iterator<RoleRef> listRoleRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_RoleRef.getOntClass());
        return new GenericIterator<RoleRef>(RoleRef.class, stit, true);
    }

    public RoleRef createRoleRef(String id)
    {
        return (RoleRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_RoleRef), vocabulary.swb_RoleRef);
    }

    public RoleRef createRoleRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_RoleRef.getName());
        return createRoleRef(""+id);
    } 

    public void removeRoleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_RoleRef));
    }
    public boolean hasRoleRef(String id)
    {
        return (getRoleRef(id)!=null);
    }

    public PortletSubType getPortletSubType(String id)
    {
        return (PortletSubType)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PortletSubType),vocabulary.swb_PortletSubType);
    }

    public Iterator<PortletSubType> listPortletSubTypes()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_PortletSubType.getOntClass());
        return new GenericIterator<PortletSubType>(PortletSubType.class, stit, true);
    }

    public PortletSubType createPortletSubType(String id)
    {
        return (PortletSubType)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_PortletSubType), vocabulary.swb_PortletSubType);
    }

    public void removePortletSubType(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_PortletSubType));
    }
    public boolean hasPortletSubType(String id)
    {
        return (getPortletSubType(id)!=null);
    }
}
