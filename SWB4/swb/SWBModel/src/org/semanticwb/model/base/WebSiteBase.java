package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebSiteBase extends SemanticObject implements Descriptiveable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebSiteBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public Date getCreated()
    {
        return getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        setDateProperty(vocabulary.created, created);
    }

    public void setUserModified(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userModified, user);
    }

    public void removeUserModified()
    {
        removeProperty(vocabulary.userModified);
    }

    public User getUserModified()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public void setHomePage(org.semanticwb.model.WebPage webpage)
    {
        addObjectProperty(vocabulary.homePage, webpage);
    }

    public void removeHomePage()
    {
        removeProperty(vocabulary.homePage);
    }

    public WebPage getHomePage()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.homePage.getRDFProperty());
         SemanticIterator<org.semanticwb.model.WebPage> it=new SemanticIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         return it.next();
    }

    public String getTitle()
    {
        return getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        setProperty(vocabulary.title, title);
    }

    public String getDescription()
    {
        return getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        setProperty(vocabulary.description, description);
    }

    public void setUserCreated(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userCreated, user);
    }

    public void removeUserCreated()
    {
        removeProperty(vocabulary.userCreated);
    }

    public User getUserCreated()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userCreated.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public Date getUpdated()
    {
        return getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        setDateProperty(vocabulary.updated, updated);
    }
    public WebPage getWebPage(String id)
    {
        return (WebPage)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.WebPage),vocabulary.WebPage);
    }

    public Iterator<WebPage> listWebPages()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.WebPage.getOntClass());
        return new SemanticIterator<WebPage>(WebPage.class, stit);
    }

    public WebPage createWebPage(String id)
    {
        return (WebPage)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.WebPage), vocabulary.WebPage);
    }

    public WebPage createWebPage()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.WebPage.getName());
        return createWebPage(""+id);
    } 

    public void removeWebPage(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.WebPage));
    }
    public Calendar getCalendar(String id)
    {
        return (Calendar)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Calendar),vocabulary.Calendar);
    }

    public Iterator<Calendar> listCalendars()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Calendar.getOntClass());
        return new SemanticIterator<Calendar>(Calendar.class, stit);
    }

    public Calendar createCalendar(String id)
    {
        return (Calendar)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Calendar), vocabulary.Calendar);
    }

    public Calendar createCalendar()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Calendar.getName());
        return createCalendar(""+id);
    } 

    public void removeCalendar(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Calendar));
    }
    public RuleRef getRuleRef(String id)
    {
        return (RuleRef)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.RuleRef),vocabulary.RuleRef);
    }

    public Iterator<RuleRef> listRuleRefs()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.RuleRef.getOntClass());
        return new SemanticIterator<RuleRef>(RuleRef.class, stit);
    }

    public RuleRef createRuleRef(String id)
    {
        return (RuleRef)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.RuleRef), vocabulary.RuleRef);
    }

    public RuleRef createRuleRef()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.RuleRef.getName());
        return createRuleRef(""+id);
    } 

    public void removeRuleRef(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.RuleRef));
    }
    public StrategyPortlet getStrategyPortlet(String id)
    {
        return (StrategyPortlet)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.StrategyPortlet),vocabulary.StrategyPortlet);
    }

    public Iterator<StrategyPortlet> listStrategyPortlets()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.StrategyPortlet.getOntClass());
        return new SemanticIterator<StrategyPortlet>(StrategyPortlet.class, stit);
    }

    public StrategyPortlet createStrategyPortlet(String id)
    {
        return (StrategyPortlet)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.StrategyPortlet), vocabulary.StrategyPortlet);
    }

    public StrategyPortlet createStrategyPortlet()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.StrategyPortlet.getName());
        return createStrategyPortlet(""+id);
    } 

    public void removeStrategyPortlet(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.StrategyPortlet));
    }
    public ObjectGroup getObjectGroup(String id)
    {
        return (ObjectGroup)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.ObjectGroup),vocabulary.ObjectGroup);
    }

    public Iterator<ObjectGroup> listObjectGroups()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.ObjectGroup.getOntClass());
        return new SemanticIterator<ObjectGroup>(ObjectGroup.class, stit);
    }

    public ObjectGroup createObjectGroup(String id)
    {
        return (ObjectGroup)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.ObjectGroup), vocabulary.ObjectGroup);
    }

    public ObjectGroup createObjectGroup()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.ObjectGroup.getName());
        return createObjectGroup(""+id);
    } 

    public void removeObjectGroup(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.ObjectGroup));
    }
    public RoleRef getRoleRef(String id)
    {
        return (RoleRef)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.RoleRef),vocabulary.RoleRef);
    }

    public Iterator<RoleRef> listRoleRefs()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.RoleRef.getOntClass());
        return new SemanticIterator<RoleRef>(RoleRef.class, stit);
    }

    public RoleRef createRoleRef(String id)
    {
        return (RoleRef)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.RoleRef), vocabulary.RoleRef);
    }

    public RoleRef createRoleRef()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.RoleRef.getName());
        return createRoleRef(""+id);
    } 

    public void removeRoleRef(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.RoleRef));
    }
    public Device getDevice(String id)
    {
        return (Device)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Device),vocabulary.Device);
    }

    public Iterator<Device> listDevices()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Device.getOntClass());
        return new SemanticIterator<Device>(Device.class, stit);
    }

    public Device createDevice(String id)
    {
        return (Device)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Device), vocabulary.Device);
    }

    public Device createDevice()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Device.getName());
        return createDevice(""+id);
    } 

    public void removeDevice(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Device));
    }
    public Permission getPermission(String id)
    {
        return (Permission)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Permission),vocabulary.Permission);
    }

    public Iterator<Permission> listPermissions()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Permission.getOntClass());
        return new SemanticIterator<Permission>(Permission.class, stit);
    }

    public Permission createPermission(String id)
    {
        return (Permission)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Permission), vocabulary.Permission);
    }

    public Permission createPermission()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Permission.getName());
        return createPermission(""+id);
    } 

    public void removePermission(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Permission));
    }
    public Template getTemplate(String id)
    {
        return (Template)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Template),vocabulary.Template);
    }

    public Iterator<Template> listTemplates()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Template.getOntClass());
        return new SemanticIterator<Template>(Template.class, stit);
    }

    public Template createTemplate(String id)
    {
        return (Template)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Template), vocabulary.Template);
    }

    public Template createTemplate()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Template.getName());
        return createTemplate(""+id);
    } 

    public void removeTemplate(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Template));
    }
    public SystemPortlet getSystemPortlet(String id)
    {
        return (SystemPortlet)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.SystemPortlet),vocabulary.SystemPortlet);
    }

    public Iterator<SystemPortlet> listSystemPortlets()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.SystemPortlet.getOntClass());
        return new SemanticIterator<SystemPortlet>(SystemPortlet.class, stit);
    }

    public SystemPortlet createSystemPortlet(String id)
    {
        return (SystemPortlet)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.SystemPortlet), vocabulary.SystemPortlet);
    }

    public SystemPortlet createSystemPortlet()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.SystemPortlet.getName());
        return createSystemPortlet(""+id);
    } 

    public void removeSystemPortlet(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.SystemPortlet));
    }
    public VersionInfo getVersionInfo(String id)
    {
        return (VersionInfo)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.VersionInfo),vocabulary.VersionInfo);
    }

    public Iterator<VersionInfo> listVersionInfos()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.VersionInfo.getOntClass());
        return new SemanticIterator<VersionInfo>(VersionInfo.class, stit);
    }

    public VersionInfo createVersionInfo(String id)
    {
        return (VersionInfo)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.VersionInfo), vocabulary.VersionInfo);
    }

    public VersionInfo createVersionInfo()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.VersionInfo.getName());
        return createVersionInfo(""+id);
    } 

    public void removeVersionInfo(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.VersionInfo));
    }
    public Community getCommunity(String id)
    {
        return (Community)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Community),vocabulary.Community);
    }

    public Iterator<Community> listCommunitys()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Community.getOntClass());
        return new SemanticIterator<Community>(Community.class, stit);
    }

    public Community createCommunity(String id)
    {
        return (Community)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Community), vocabulary.Community);
    }

    public Community createCommunity()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Community.getName());
        return createCommunity(""+id);
    } 

    public void removeCommunity(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Community));
    }
    public Language getLanguage(String id)
    {
        return (Language)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Language),vocabulary.Language);
    }

    public Iterator<Language> listLanguages()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Language.getOntClass());
        return new SemanticIterator<Language>(Language.class, stit);
    }

    public Language createLanguage(String id)
    {
        return (Language)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Language), vocabulary.Language);
    }

    public Language createLanguage()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Language.getName());
        return createLanguage(""+id);
    } 

    public void removeLanguage(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Language));
    }
    public TemplateRef getTemplateRef(String id)
    {
        return (TemplateRef)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.TemplateRef),vocabulary.TemplateRef);
    }

    public Iterator<TemplateRef> listTemplateRefs()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.TemplateRef.getOntClass());
        return new SemanticIterator<TemplateRef>(TemplateRef.class, stit);
    }

    public TemplateRef createTemplateRef(String id)
    {
        return (TemplateRef)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.TemplateRef), vocabulary.TemplateRef);
    }

    public TemplateRef createTemplateRef()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.TemplateRef.getName());
        return createTemplateRef(""+id);
    } 

    public void removeTemplateRef(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.TemplateRef));
    }
    public PortletRef getPortletRef(String id)
    {
        return (PortletRef)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.PortletRef),vocabulary.PortletRef);
    }

    public Iterator<PortletRef> listPortletRefs()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.PortletRef.getOntClass());
        return new SemanticIterator<PortletRef>(PortletRef.class, stit);
    }

    public PortletRef createPortletRef(String id)
    {
        return (PortletRef)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.PortletRef), vocabulary.PortletRef);
    }

    public PortletRef createPortletRef()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.PortletRef.getName());
        return createPortletRef(""+id);
    } 

    public void removePortletRef(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.PortletRef));
    }
    public IPFilter getIPFilter(String id)
    {
        return (IPFilter)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.IPFilter),vocabulary.IPFilter);
    }

    public Iterator<IPFilter> listIPFilters()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.IPFilter.getOntClass());
        return new SemanticIterator<IPFilter>(IPFilter.class, stit);
    }

    public IPFilter createIPFilter(String id)
    {
        return (IPFilter)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.IPFilter), vocabulary.IPFilter);
    }

    public IPFilter createIPFilter()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.IPFilter.getName());
        return createIPFilter(""+id);
    } 

    public void removeIPFilter(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.IPFilter));
    }
    public Rule getRule(String id)
    {
        return (Rule)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Rule),vocabulary.Rule);
    }

    public Iterator<Rule> listRules()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Rule.getOntClass());
        return new SemanticIterator<Rule>(Rule.class, stit);
    }

    public Rule createRule(String id)
    {
        return (Rule)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Rule), vocabulary.Rule);
    }

    public Rule createRule()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Rule.getName());
        return createRule(""+id);
    } 

    public void removeRule(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Rule));
    }
    public PFlow getPFlow(String id)
    {
        return (PFlow)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.PFlow),vocabulary.PFlow);
    }

    public Iterator<PFlow> listPFlows()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.PFlow.getOntClass());
        return new SemanticIterator<PFlow>(PFlow.class, stit);
    }

    public PFlow createPFlow(String id)
    {
        return (PFlow)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.PFlow), vocabulary.PFlow);
    }

    public PFlow createPFlow()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.PFlow.getName());
        return createPFlow(""+id);
    } 

    public void removePFlow(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.PFlow));
    }
    public ContentPortlet getContentPortlet(String id)
    {
        return (ContentPortlet)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.ContentPortlet),vocabulary.ContentPortlet);
    }

    public Iterator<ContentPortlet> listContentPortlets()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.ContentPortlet.getOntClass());
        return new SemanticIterator<ContentPortlet>(ContentPortlet.class, stit);
    }

    public ContentPortlet createContentPortlet(String id)
    {
        return (ContentPortlet)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.ContentPortlet), vocabulary.ContentPortlet);
    }

    public ContentPortlet createContentPortlet()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.ContentPortlet.getName());
        return createContentPortlet(""+id);
    } 

    public void removeContentPortlet(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.ContentPortlet));
    }
    public Camp getCamp(String id)
    {
        return (Camp)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Camp),vocabulary.Camp);
    }

    public Iterator<Camp> listCamps()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Camp.getOntClass());
        return new SemanticIterator<Camp>(Camp.class, stit);
    }

    public Camp createCamp(String id)
    {
        return (Camp)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Camp), vocabulary.Camp);
    }

    public Camp createCamp()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Camp.getName());
        return createCamp(""+id);
    } 

    public void removeCamp(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Camp));
    }
    public ApplicationPortlet getApplicationPortlet(String id)
    {
        return (ApplicationPortlet)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.ApplicationPortlet),vocabulary.ApplicationPortlet);
    }

    public Iterator<ApplicationPortlet> listApplicationPortlets()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.ApplicationPortlet.getOntClass());
        return new SemanticIterator<ApplicationPortlet>(ApplicationPortlet.class, stit);
    }

    public ApplicationPortlet createApplicationPortlet(String id)
    {
        return (ApplicationPortlet)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.ApplicationPortlet), vocabulary.ApplicationPortlet);
    }

    public ApplicationPortlet createApplicationPortlet()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.ApplicationPortlet.getName());
        return createApplicationPortlet(""+id);
    } 

    public void removeApplicationPortlet(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.ApplicationPortlet));
    }
    public PortletType getPortletType(String id)
    {
        return (PortletType)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.PortletType),vocabulary.PortletType);
    }

    public Iterator<PortletType> listPortletTypes()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.PortletType.getOntClass());
        return new SemanticIterator<PortletType>(PortletType.class, stit);
    }

    public PortletType createPortletType(String id)
    {
        return (PortletType)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.PortletType), vocabulary.PortletType);
    }

    public PortletType createPortletType()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.PortletType.getName());
        return createPortletType(""+id);
    } 

    public void removePortletType(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.PortletType));
    }
    public Dns getDns(String id)
    {
        return (Dns)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Dns),vocabulary.Dns);
    }

    public Iterator<Dns> listDnss()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Dns.getOntClass());
        return new SemanticIterator<Dns>(Dns.class, stit);
    }

    public Dns createDns(String id)
    {
        return (Dns)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Dns), vocabulary.Dns);
    }

    public Dns createDns()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Dns.getName());
        return createDns(""+id);
    } 

    public void removeDns(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Dns));
    }
}
