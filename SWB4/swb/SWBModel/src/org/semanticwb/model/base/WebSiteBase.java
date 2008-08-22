package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebSiteBase extends GenericObjectBase implements Deleteable,Localeable,Versionable,Statusable,Descriptiveable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebSiteBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.modifiedBy.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().addObjectProperty(vocabulary.actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(vocabulary.actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.actualVersion.getRDFProperty());
         GenericIterator<org.semanticwb.model.VersionInfo> it=new GenericIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        getSemanticObject().setLongProperty(vocabulary.status, status);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().addObjectProperty(vocabulary.language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.language);
    }

    public Language getLanguage()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.language.getRDFProperty());
         GenericIterator<org.semanticwb.model.Language> it=new GenericIterator<org.semanticwb.model.Language>(Language.class, stit);
         return it.next();
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
        getSemanticObject().addObjectProperty(vocabulary.lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(vocabulary.lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.lastVersion.getRDFProperty());
         GenericIterator<org.semanticwb.model.VersionInfo> it=new GenericIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.creator.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public void setHomePage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().addObjectProperty(vocabulary.homePage, webpage.getSemanticObject());
    }

    public void removeHomePage()
    {
        getSemanticObject().removeProperty(vocabulary.homePage);
    }

    public WebPage getHomePage()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.homePage.getRDFProperty());
         GenericIterator<org.semanticwb.model.WebPage> it=new GenericIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         return it.next();
    }

    public void setUserRepository(org.semanticwb.model.UserRepository userrepository)
    {
        getSemanticObject().addObjectProperty(vocabulary.userRepository, userrepository.getSemanticObject());
    }

    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(vocabulary.userRepository);
    }

    public UserRepository getUserRepository()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.userRepository.getRDFProperty());
         GenericIterator<org.semanticwb.model.UserRepository> it=new GenericIterator<org.semanticwb.model.UserRepository>(UserRepository.class, stit);
         return it.next();
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
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

    public WebPage createWebPage()
    {
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.WebPage.getName());
        return createWebPage(""+id);
    } 

    public void removeWebPage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.WebPage));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Calendar.getName());
        return createCalendar(""+id);
    } 

    public void removeCalendar(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Calendar));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.RuleRef.getName());
        return createRuleRef(""+id);
    } 

    public void removeRuleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.RuleRef));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.ObjectGroup.getName());
        return createObjectGroup(""+id);
    } 

    public void removeObjectGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.ObjectGroup));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.RoleRef.getName());
        return createRoleRef(""+id);
    } 

    public void removeRoleRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.RoleRef));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Device.getName());
        return createDevice(""+id);
    } 

    public void removeDevice(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Device));
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

    public Permission createPermission()
    {
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Permission.getName());
        return createPermission(""+id);
    } 

    public void removePermission(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Permission));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Template.getName());
        return createTemplate(""+id);
    } 

    public void removeTemplate(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Template));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.VersionInfo.getName());
        return createVersionInfo(""+id);
    } 

    public void removeVersionInfo(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.VersionInfo));
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

    public Community createCommunity()
    {
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Community.getName());
        return createCommunity(""+id);
    } 

    public void removeCommunity(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Community));
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

    public Language createLanguage()
    {
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Language.getName());
        return createLanguage(""+id);
    } 

    public void removeLanguage(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Language));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.TemplateRef.getName());
        return createTemplateRef(""+id);
    } 

    public void removeTemplateRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.TemplateRef));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.PortletRef.getName());
        return createPortletRef(""+id);
    } 

    public void removePortletRef(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletRef));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.IPFilter.getName());
        return createIPFilter(""+id);
    } 

    public void removeIPFilter(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.IPFilter));
    }

    public PortletClass getPortletClass(String id)
    {
        return (PortletClass)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletClass),vocabulary.PortletClass);
    }

    public Iterator<PortletClass> listPortletClasss()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.PortletClass.getOntClass());
        return new GenericIterator<PortletClass>(PortletClass.class, stit);
    }

    public PortletClass createPortletClass(String id)
    {
        return (PortletClass)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.PortletClass), vocabulary.PortletClass);
    }

    public PortletClass createPortletClass()
    {
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.PortletClass.getName());
        return createPortletClass(""+id);
    } 

    public void removePortletClass(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PortletClass));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Rule.getName());
        return createRule(""+id);
    } 

    public void removeRule(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Rule));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.PFlow.getName());
        return createPFlow(""+id);
    } 

    public void removePFlow(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.PFlow));
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
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Camp.getName());
        return createCamp(""+id);
    } 

    public void removeCamp(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Camp));
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

    public Dns createDns()
    {
        long id=SWBInstance.getCounterValue(getSemanticObject().getModel().getName()+"/"+vocabulary.Dns.getName());
        return createDns(""+id);
    } 

    public void removeDns(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Dns));
    }
}
