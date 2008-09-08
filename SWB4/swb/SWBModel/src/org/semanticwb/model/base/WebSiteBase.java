package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebSiteBase extends GenericObjectBase implements Deleteable,Localeable,Activeable,Versionable,Descriptiveable,Traceable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebSiteBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(vocabulary.actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.actualVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().setObjectProperty(vocabulary.language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.language);
    }

    public Language getLanguage()
    {
         Language ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.language);
         if(obj!=null)
         {
             ret=(Language)vocabulary.Language.newGenericInstance(obj);
         }
         return ret;
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(vocabulary.lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.lastVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public void setHomePage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(vocabulary.homePage, webpage.getSemanticObject());
    }

    public void removeHomePage()
    {
        getSemanticObject().removeProperty(vocabulary.homePage);
    }

    public WebPage getHomePage()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.homePage);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public void setUserRepository(org.semanticwb.model.UserRepository userrepository)
    {
        getSemanticObject().setObjectProperty(vocabulary.userRepository, userrepository.getSemanticObject());
    }

    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(vocabulary.userRepository);
    }

    public UserRepository getUserRepository()
    {
         UserRepository ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.userRepository);
         if(obj!=null)
         {
             ret=(UserRepository)vocabulary.UserRepository.newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public WebPage getWebPage(String id)
    {
        return (WebPage)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.WebPage),vocabulary.WebPage);
    }

    public Iterator<WebPage> listWebPages()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.WebPage.getOntClass());
        return new GenericIterator<WebPage>(WebPage.class, stit);
    }

    public WebPage createWebPage(String id)
    {
        return (WebPage)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.WebPage), vocabulary.WebPage);
    }

    public void removeWebPage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.WebPage));
    }
    public boolean hasWebPage(String id)
    {
        return (getWebPage(id)!=null);
    }

    public Calendar getCalendar(String id)
    {
        return (Calendar)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Calendar),vocabulary.Calendar);
    }

    public Iterator<Calendar> listCalendars()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Calendar.getOntClass());
        return new GenericIterator<Calendar>(Calendar.class, stit);
    }

    public Calendar createCalendar(String id)
    {
        return (Calendar)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Calendar), vocabulary.Calendar);
    }

    public Calendar createCalendar()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Calendar.getName());
        return createCalendar(""+id);
    } 

    public void removeCalendar(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Calendar));
    }
    public boolean hasCalendar(String id)
    {
        return (getCalendar(id)!=null);
    }

    public RuleRef getRuleRef(String id)
    {
        return (RuleRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.RuleRef),vocabulary.RuleRef);
    }

    public Iterator<RuleRef> listRuleRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.RuleRef.getOntClass());
        return new GenericIterator<RuleRef>(RuleRef.class, stit);
    }

    public RuleRef createRuleRef(String id)
    {
        return (RuleRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.RuleRef), vocabulary.RuleRef);
    }

    public RuleRef createRuleRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.RuleRef.getName());
        return createRuleRef(""+id);
    } 

    public void removeRuleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.RuleRef));
    }
    public boolean hasRuleRef(String id)
    {
        return (getRuleRef(id)!=null);
    }

    public ObjectGroup getObjectGroup(String id)
    {
        return (ObjectGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.ObjectGroup),vocabulary.ObjectGroup);
    }

    public Iterator<ObjectGroup> listObjectGroups()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.ObjectGroup.getOntClass());
        return new GenericIterator<ObjectGroup>(ObjectGroup.class, stit);
    }

    public ObjectGroup createObjectGroup(String id)
    {
        return (ObjectGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.ObjectGroup), vocabulary.ObjectGroup);
    }

    public ObjectGroup createObjectGroup()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.ObjectGroup.getName());
        return createObjectGroup(""+id);
    } 

    public void removeObjectGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.ObjectGroup));
    }
    public boolean hasObjectGroup(String id)
    {
        return (getObjectGroup(id)!=null);
    }

    public RoleRef getRoleRef(String id)
    {
        return (RoleRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.RoleRef),vocabulary.RoleRef);
    }

    public Iterator<RoleRef> listRoleRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.RoleRef.getOntClass());
        return new GenericIterator<RoleRef>(RoleRef.class, stit);
    }

    public RoleRef createRoleRef(String id)
    {
        return (RoleRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.RoleRef), vocabulary.RoleRef);
    }

    public RoleRef createRoleRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.RoleRef.getName());
        return createRoleRef(""+id);
    } 

    public void removeRoleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.RoleRef));
    }
    public boolean hasRoleRef(String id)
    {
        return (getRoleRef(id)!=null);
    }

    public PortletSubType getPortletSubType(String id)
    {
        return (PortletSubType)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletSubType),vocabulary.PortletSubType);
    }

    public Iterator<PortletSubType> listPortletSubTypes()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.PortletSubType.getOntClass());
        return new GenericIterator<PortletSubType>(PortletSubType.class, stit);
    }

    public PortletSubType createPortletSubType(String id)
    {
        return (PortletSubType)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.PortletSubType), vocabulary.PortletSubType);
    }

    public void removePortletSubType(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletSubType));
    }
    public boolean hasPortletSubType(String id)
    {
        return (getPortletSubType(id)!=null);
    }

    public Device getDevice(String id)
    {
        return (Device)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Device),vocabulary.Device);
    }

    public Iterator<Device> listDevices()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Device.getOntClass());
        return new GenericIterator<Device>(Device.class, stit);
    }

    public Device createDevice(String id)
    {
        return (Device)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Device), vocabulary.Device);
    }

    public Device createDevice()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Device.getName());
        return createDevice(""+id);
    } 

    public void removeDevice(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Device));
    }
    public boolean hasDevice(String id)
    {
        return (getDevice(id)!=null);
    }

    public Permission getPermission(String id)
    {
        return (Permission)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Permission),vocabulary.Permission);
    }

    public Iterator<Permission> listPermissions()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Permission.getOntClass());
        return new GenericIterator<Permission>(Permission.class, stit);
    }

    public Permission createPermission(String id)
    {
        return (Permission)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Permission), vocabulary.Permission);
    }

    public void removePermission(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Permission));
    }
    public boolean hasPermission(String id)
    {
        return (getPermission(id)!=null);
    }

    public Template getTemplate(String id)
    {
        return (Template)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Template),vocabulary.Template);
    }

    public Iterator<Template> listTemplates()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Template.getOntClass());
        return new GenericIterator<Template>(Template.class, stit);
    }

    public Template createTemplate(String id)
    {
        return (Template)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Template), vocabulary.Template);
    }

    public Template createTemplate()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Template.getName());
        return createTemplate(""+id);
    } 

    public void removeTemplate(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Template));
    }
    public boolean hasTemplate(String id)
    {
        return (getTemplate(id)!=null);
    }

    public VersionInfo getVersionInfo(String id)
    {
        return (VersionInfo)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.VersionInfo),vocabulary.VersionInfo);
    }

    public Iterator<VersionInfo> listVersionInfos()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.VersionInfo.getOntClass());
        return new GenericIterator<VersionInfo>(VersionInfo.class, stit);
    }

    public VersionInfo createVersionInfo(String id)
    {
        return (VersionInfo)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.VersionInfo), vocabulary.VersionInfo);
    }

    public VersionInfo createVersionInfo()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.VersionInfo.getName());
        return createVersionInfo(""+id);
    } 

    public void removeVersionInfo(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.VersionInfo));
    }
    public boolean hasVersionInfo(String id)
    {
        return (getVersionInfo(id)!=null);
    }

    public Community getCommunity(String id)
    {
        return (Community)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Community),vocabulary.Community);
    }

    public Iterator<Community> listCommunitys()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Community.getOntClass());
        return new GenericIterator<Community>(Community.class, stit);
    }

    public Community createCommunity(String id)
    {
        return (Community)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Community), vocabulary.Community);
    }

    public void removeCommunity(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Community));
    }
    public boolean hasCommunity(String id)
    {
        return (getCommunity(id)!=null);
    }

    public Language getLanguage(String id)
    {
        return (Language)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Language),vocabulary.Language);
    }

    public Iterator<Language> listLanguages()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Language.getOntClass());
        return new GenericIterator<Language>(Language.class, stit);
    }

    public Language createLanguage(String id)
    {
        return (Language)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Language), vocabulary.Language);
    }

    public void removeLanguage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Language));
    }
    public boolean hasLanguage(String id)
    {
        return (getLanguage(id)!=null);
    }

    public TemplateRef getTemplateRef(String id)
    {
        return (TemplateRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.TemplateRef),vocabulary.TemplateRef);
    }

    public Iterator<TemplateRef> listTemplateRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.TemplateRef.getOntClass());
        return new GenericIterator<TemplateRef>(TemplateRef.class, stit);
    }

    public TemplateRef createTemplateRef(String id)
    {
        return (TemplateRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.TemplateRef), vocabulary.TemplateRef);
    }

    public TemplateRef createTemplateRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.TemplateRef.getName());
        return createTemplateRef(""+id);
    } 

    public void removeTemplateRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.TemplateRef));
    }
    public boolean hasTemplateRef(String id)
    {
        return (getTemplateRef(id)!=null);
    }

    public PortletRef getPortletRef(String id)
    {
        return (PortletRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletRef),vocabulary.PortletRef);
    }

    public Iterator<PortletRef> listPortletRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.PortletRef.getOntClass());
        return new GenericIterator<PortletRef>(PortletRef.class, stit);
    }

    public PortletRef createPortletRef(String id)
    {
        return (PortletRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.PortletRef), vocabulary.PortletRef);
    }

    public PortletRef createPortletRef()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.PortletRef.getName());
        return createPortletRef(""+id);
    } 

    public void removePortletRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletRef));
    }
    public boolean hasPortletRef(String id)
    {
        return (getPortletRef(id)!=null);
    }

    public IPFilter getIPFilter(String id)
    {
        return (IPFilter)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.IPFilter),vocabulary.IPFilter);
    }

    public Iterator<IPFilter> listIPFilters()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.IPFilter.getOntClass());
        return new GenericIterator<IPFilter>(IPFilter.class, stit);
    }

    public IPFilter createIPFilter(String id)
    {
        return (IPFilter)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.IPFilter), vocabulary.IPFilter);
    }

    public IPFilter createIPFilter()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.IPFilter.getName());
        return createIPFilter(""+id);
    } 

    public void removeIPFilter(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.IPFilter));
    }
    public boolean hasIPFilter(String id)
    {
        return (getIPFilter(id)!=null);
    }

    public TemplateGroup getTemplateGroup(String id)
    {
        return (TemplateGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.TemplateGroup),vocabulary.TemplateGroup);
    }

    public Iterator<TemplateGroup> listTemplateGroups()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.TemplateGroup.getOntClass());
        return new GenericIterator<TemplateGroup>(TemplateGroup.class, stit);
    }

    public TemplateGroup createTemplateGroup(String id)
    {
        return (TemplateGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.TemplateGroup), vocabulary.TemplateGroup);
    }

    public void removeTemplateGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.TemplateGroup));
    }
    public boolean hasTemplateGroup(String id)
    {
        return (getTemplateGroup(id)!=null);
    }

    public Rule getRule(String id)
    {
        return (Rule)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Rule),vocabulary.Rule);
    }

    public Iterator<Rule> listRules()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Rule.getOntClass());
        return new GenericIterator<Rule>(Rule.class, stit);
    }

    public Rule createRule(String id)
    {
        return (Rule)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Rule), vocabulary.Rule);
    }

    public Rule createRule()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Rule.getName());
        return createRule(""+id);
    } 

    public void removeRule(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Rule));
    }
    public boolean hasRule(String id)
    {
        return (getRule(id)!=null);
    }

    public PFlow getPFlow(String id)
    {
        return (PFlow)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PFlow),vocabulary.PFlow);
    }

    public Iterator<PFlow> listPFlows()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.PFlow.getOntClass());
        return new GenericIterator<PFlow>(PFlow.class, stit);
    }

    public PFlow createPFlow(String id)
    {
        return (PFlow)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.PFlow), vocabulary.PFlow);
    }

    public PFlow createPFlow()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.PFlow.getName());
        return createPFlow(""+id);
    } 

    public void removePFlow(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PFlow));
    }
    public boolean hasPFlow(String id)
    {
        return (getPFlow(id)!=null);
    }

    public Camp getCamp(String id)
    {
        return (Camp)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Camp),vocabulary.Camp);
    }

    public Iterator<Camp> listCamps()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Camp.getOntClass());
        return new GenericIterator<Camp>(Camp.class, stit);
    }

    public Camp createCamp(String id)
    {
        return (Camp)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Camp), vocabulary.Camp);
    }

    public Camp createCamp()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Camp.getName());
        return createCamp(""+id);
    } 

    public void removeCamp(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Camp));
    }
    public boolean hasCamp(String id)
    {
        return (getCamp(id)!=null);
    }

    public PortletType getPortletType(String id)
    {
        return (PortletType)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletType),vocabulary.PortletType);
    }

    public Iterator<PortletType> listPortletTypes()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.PortletType.getOntClass());
        return new GenericIterator<PortletType>(PortletType.class, stit);
    }

    public PortletType createPortletType(String id)
    {
        return (PortletType)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.PortletType), vocabulary.PortletType);
    }

    public void removePortletType(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletType));
    }
    public boolean hasPortletType(String id)
    {
        return (getPortletType(id)!=null);
    }

    public Dns getDns(String id)
    {
        return (Dns)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Dns),vocabulary.Dns);
    }

    public Iterator<Dns> listDnss()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Dns.getOntClass());
        return new GenericIterator<Dns>(Dns.class, stit);
    }

    public Dns createDns(String id)
    {
        return (Dns)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Dns), vocabulary.Dns);
    }

    public void removeDns(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Dns));
    }
    public boolean hasDns(String id)
    {
        return (getDns(id)!=null);
    }

    public Portlet getPortlet(String id)
    {
        return (Portlet)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Portlet),vocabulary.Portlet);
    }

    public Iterator<Portlet> listPortlets()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Portlet.getOntClass());
        return new GenericIterator<Portlet>(Portlet.class, stit);
    }

    public Portlet createPortlet(String id)
    {
        return (Portlet)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Portlet), vocabulary.Portlet);
    }

    public Portlet createPortlet()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Portlet.getName());
        return createPortlet(""+id);
    } 

    public void removePortlet(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Portlet));
    }
    public boolean hasPortlet(String id)
    {
        return (getPortlet(id)!=null);
    }

    public PFlowRef getPFlowRef(String id)
    {
        return (PFlowRef)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PFlowRef),vocabulary.PFlowRef);
    }

    public Iterator<PFlowRef> listPFlowRefs()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.PFlowRef.getOntClass());
        return new GenericIterator<PFlowRef>(PFlowRef.class, stit);
    }

    public PFlowRef createPFlowRef(String id)
    {
        return (PFlowRef)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.PFlowRef), vocabulary.PFlowRef);
    }

    public void removePFlowRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PFlowRef));
    }
    public boolean hasPFlowRef(String id)
    {
        return (getPFlowRef(id)!=null);
    }
}
