package org.semanticwb.model.base;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.SWBInstance;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
public class SWBContextBase
{
   private static SWBVocabulary vocabulary=new SWBVocabulary();
   private static SemanticMgr mgr=SWBInstance.getSemanticMgr();
    public static SWBVocabulary getVocabulary()
    {
        return vocabulary;
    }
    public static void removeObject(String uri)
    {
         removeObject(mgr.getOntology().getSemanticObject(uri));
    }
   public static void removeObject(SemanticObject obj)
   {
       if(obj!=null)
       {
           mgr.getOntology().getRDFOntModel().remove(obj.getRDFResource(), null, null);
       }
   }
    public static User getUser(String uri)
    {
        return (User)mgr.getOntology().getSemanticObject(uri,vocabulary.User);
    }
    public static User createUser(SemanticModel model, String uri)
    {
        return (User)model.createSemanticObject(uri, vocabulary.User);
    }
    public static Iterator<org.semanticwb.model.User> listUsers()
    {
        return (Iterator<org.semanticwb.model.User>)vocabulary.User.listInstances();
    }
    public static Calendar getCalendar(String uri)
    {
        return (Calendar)mgr.getOntology().getSemanticObject(uri,vocabulary.Calendar);
    }
    public static Calendar createCalendar(SemanticModel model, String uri)
    {
        return (Calendar)model.createSemanticObject(uri, vocabulary.Calendar);
    }
    public static Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return (Iterator<org.semanticwb.model.Calendar>)vocabulary.Calendar.listInstances();
    }
    public static Community getCommunity(String uri)
    {
        return (Community)mgr.getOntology().getSemanticObject(uri,vocabulary.Community);
    }
    public static Community createCommunity(SemanticModel model, String uri)
    {
        return (Community)model.createSemanticObject(uri, vocabulary.Community);
    }
    public static Iterator<org.semanticwb.model.Community> listCommunitys()
    {
        return (Iterator<org.semanticwb.model.Community>)vocabulary.Community.listInstances();
    }
    public static ContentPortlet getContentPortlet(String uri)
    {
        return (ContentPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.ContentPortlet);
    }
    public static ContentPortlet createContentPortlet(SemanticModel model, String uri)
    {
        return (ContentPortlet)model.createSemanticObject(uri, vocabulary.ContentPortlet);
    }
    public static Iterator<org.semanticwb.model.ContentPortlet> listContentPortlets()
    {
        return (Iterator<org.semanticwb.model.ContentPortlet>)vocabulary.ContentPortlet.listInstances();
    }
    public static TemplateRef getTemplateRef(String uri)
    {
        return (TemplateRef)mgr.getOntology().getSemanticObject(uri,vocabulary.TemplateRef);
    }
    public static TemplateRef createTemplateRef(SemanticModel model, String uri)
    {
        return (TemplateRef)model.createSemanticObject(uri, vocabulary.TemplateRef);
    }
    public static Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return (Iterator<org.semanticwb.model.TemplateRef>)vocabulary.TemplateRef.listInstances();
    }
    public static Templateable getTemplateable(String uri)
    {
        return (Templateable)mgr.getOntology().getSemanticObject(uri,vocabulary.Templateable);
    }
    public static Templateable createTemplateable(SemanticModel model, String uri)
    {
        return (Templateable)model.createSemanticObject(uri, vocabulary.Templateable);
    }
    public static Iterator<org.semanticwb.model.Templateable> listTemplateables()
    {
        return (Iterator<org.semanticwb.model.Templateable>)vocabulary.Templateable.listInstances();
    }
    public static Deleteable getDeleteable(String uri)
    {
        return (Deleteable)mgr.getOntology().getSemanticObject(uri,vocabulary.Deleteable);
    }
    public static Deleteable createDeleteable(SemanticModel model, String uri)
    {
        return (Deleteable)model.createSemanticObject(uri, vocabulary.Deleteable);
    }
    public static Iterator<org.semanticwb.model.Deleteable> listDeleteables()
    {
        return (Iterator<org.semanticwb.model.Deleteable>)vocabulary.Deleteable.listInstances();
    }
    public static Reference getReference(String uri)
    {
        return (Reference)mgr.getOntology().getSemanticObject(uri,vocabulary.Reference);
    }
    public static Reference createReference(SemanticModel model, String uri)
    {
        return (Reference)model.createSemanticObject(uri, vocabulary.Reference);
    }
    public static Iterator<org.semanticwb.model.Reference> listReferences()
    {
        return (Iterator<org.semanticwb.model.Reference>)vocabulary.Reference.listInstances();
    }
    public static Roleable getRoleable(String uri)
    {
        return (Roleable)mgr.getOntology().getSemanticObject(uri,vocabulary.Roleable);
    }
    public static Roleable createRoleable(SemanticModel model, String uri)
    {
        return (Roleable)model.createSemanticObject(uri, vocabulary.Roleable);
    }
    public static Iterator<org.semanticwb.model.Roleable> listRoleables()
    {
        return (Iterator<org.semanticwb.model.Roleable>)vocabulary.Roleable.listInstances();
    }
    public static HomePage getHomePage(String uri)
    {
        return (HomePage)mgr.getOntology().getSemanticObject(uri,vocabulary.HomePage);
    }
    public static HomePage createHomePage(SemanticModel model, String uri)
    {
        return (HomePage)model.createSemanticObject(uri, vocabulary.HomePage);
    }
    public static Iterator<org.semanticwb.model.HomePage> listHomePages()
    {
        return (Iterator<org.semanticwb.model.HomePage>)vocabulary.HomePage.listInstances();
    }
    public static RoleRefable getRoleRefable(String uri)
    {
        return (RoleRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.RoleRefable);
    }
    public static RoleRefable createRoleRefable(SemanticModel model, String uri)
    {
        return (RoleRefable)model.createSemanticObject(uri, vocabulary.RoleRefable);
    }
    public static Iterator<org.semanticwb.model.RoleRefable> listRoleRefables()
    {
        return (Iterator<org.semanticwb.model.RoleRefable>)vocabulary.RoleRefable.listInstances();
    }
    public static Ruleable getRuleable(String uri)
    {
        return (Ruleable)mgr.getOntology().getSemanticObject(uri,vocabulary.Ruleable);
    }
    public static Ruleable createRuleable(SemanticModel model, String uri)
    {
        return (Ruleable)model.createSemanticObject(uri, vocabulary.Ruleable);
    }
    public static Iterator<org.semanticwb.model.Ruleable> listRuleables()
    {
        return (Iterator<org.semanticwb.model.Ruleable>)vocabulary.Ruleable.listInstances();
    }
    public static IPFilter getIPFilter(String uri)
    {
        return (IPFilter)mgr.getOntology().getSemanticObject(uri,vocabulary.IPFilter);
    }
    public static IPFilter createIPFilter(SemanticModel model, String uri)
    {
        return (IPFilter)model.createSemanticObject(uri, vocabulary.IPFilter);
    }
    public static Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        return (Iterator<org.semanticwb.model.IPFilter>)vocabulary.IPFilter.listInstances();
    }
    public static PFlow getPFlow(String uri)
    {
        return (PFlow)mgr.getOntology().getSemanticObject(uri,vocabulary.PFlow);
    }
    public static PFlow createPFlow(SemanticModel model, String uri)
    {
        return (PFlow)model.createSemanticObject(uri, vocabulary.PFlow);
    }
    public static Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        return (Iterator<org.semanticwb.model.PFlow>)vocabulary.PFlow.listInstances();
    }
    public static ApplicationPortlet getApplicationPortlet(String uri)
    {
        return (ApplicationPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.ApplicationPortlet);
    }
    public static ApplicationPortlet createApplicationPortlet(SemanticModel model, String uri)
    {
        return (ApplicationPortlet)model.createSemanticObject(uri, vocabulary.ApplicationPortlet);
    }
    public static Iterator<org.semanticwb.model.ApplicationPortlet> listApplicationPortlets()
    {
        return (Iterator<org.semanticwb.model.ApplicationPortlet>)vocabulary.ApplicationPortlet.listInstances();
    }
    public static SWBModel getSWBModel(String uri)
    {
        return (SWBModel)mgr.getOntology().getSemanticObject(uri,vocabulary.SWBModel);
    }
    public static SWBModel createSWBModel(SemanticModel model, String uri)
    {
        return (SWBModel)model.createSemanticObject(uri, vocabulary.SWBModel);
    }
    public static Iterator<org.semanticwb.model.SWBModel> listSWBModels()
    {
        return (Iterator<org.semanticwb.model.SWBModel>)vocabulary.SWBModel.listInstances();
    }
    public static RuleRefable getRuleRefable(String uri)
    {
        return (RuleRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.RuleRefable);
    }
    public static RuleRefable createRuleRefable(SemanticModel model, String uri)
    {
        return (RuleRefable)model.createSemanticObject(uri, vocabulary.RuleRefable);
    }
    public static Iterator<org.semanticwb.model.RuleRefable> listRuleRefables()
    {
        return (Iterator<org.semanticwb.model.RuleRefable>)vocabulary.RuleRefable.listInstances();
    }
    public static Valueable getValueable(String uri)
    {
        return (Valueable)mgr.getOntology().getSemanticObject(uri,vocabulary.Valueable);
    }
    public static Valueable createValueable(SemanticModel model, String uri)
    {
        return (Valueable)model.createSemanticObject(uri, vocabulary.Valueable);
    }
    public static Iterator<org.semanticwb.model.Valueable> listValueables()
    {
        return (Iterator<org.semanticwb.model.Valueable>)vocabulary.Valueable.listInstances();
    }
    public static Calendarable getCalendarable(String uri)
    {
        return (Calendarable)mgr.getOntology().getSemanticObject(uri,vocabulary.Calendarable);
    }
    public static Calendarable createCalendarable(SemanticModel model, String uri)
    {
        return (Calendarable)model.createSemanticObject(uri, vocabulary.Calendarable);
    }
    public static Iterator<org.semanticwb.model.Calendarable> listCalendarables()
    {
        return (Iterator<org.semanticwb.model.Calendarable>)vocabulary.Calendarable.listInstances();
    }
    public static PortletRefable getPortletRefable(String uri)
    {
        return (PortletRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.PortletRefable);
    }
    public static PortletRefable createPortletRefable(SemanticModel model, String uri)
    {
        return (PortletRefable)model.createSemanticObject(uri, vocabulary.PortletRefable);
    }
    public static Iterator<org.semanticwb.model.PortletRefable> listPortletRefables()
    {
        return (Iterator<org.semanticwb.model.PortletRefable>)vocabulary.PortletRefable.listInstances();
    }
    public static Permission getPermission(String uri)
    {
        return (Permission)mgr.getOntology().getSemanticObject(uri,vocabulary.Permission);
    }
    public static Permission createPermission(SemanticModel model, String uri)
    {
        return (Permission)model.createSemanticObject(uri, vocabulary.Permission);
    }
    public static Iterator<org.semanticwb.model.Permission> listPermissions()
    {
        return (Iterator<org.semanticwb.model.Permission>)vocabulary.Permission.listInstances();
    }
    public static TemplateRefable getTemplateRefable(String uri)
    {
        return (TemplateRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.TemplateRefable);
    }
    public static TemplateRefable createTemplateRefable(SemanticModel model, String uri)
    {
        return (TemplateRefable)model.createSemanticObject(uri, vocabulary.TemplateRefable);
    }
    public static Iterator<org.semanticwb.model.TemplateRefable> listTemplateRefables()
    {
        return (Iterator<org.semanticwb.model.TemplateRefable>)vocabulary.TemplateRefable.listInstances();
    }
    public static WebSiteable getWebSiteable(String uri)
    {
        return (WebSiteable)mgr.getOntology().getSemanticObject(uri,vocabulary.WebSiteable);
    }
    public static WebSiteable createWebSiteable(SemanticModel model, String uri)
    {
        return (WebSiteable)model.createSemanticObject(uri, vocabulary.WebSiteable);
    }
    public static Iterator<org.semanticwb.model.WebSiteable> listWebSiteables()
    {
        return (Iterator<org.semanticwb.model.WebSiteable>)vocabulary.WebSiteable.listInstances();
    }
    public static RuleRef getRuleRef(String uri)
    {
        return (RuleRef)mgr.getOntology().getSemanticObject(uri,vocabulary.RuleRef);
    }
    public static RuleRef createRuleRef(SemanticModel model, String uri)
    {
        return (RuleRef)model.createSemanticObject(uri, vocabulary.RuleRef);
    }
    public static Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return (Iterator<org.semanticwb.model.RuleRef>)vocabulary.RuleRef.listInstances();
    }
    public static StrategyPortlet getStrategyPortlet(String uri)
    {
        return (StrategyPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.StrategyPortlet);
    }
    public static StrategyPortlet createStrategyPortlet(SemanticModel model, String uri)
    {
        return (StrategyPortlet)model.createSemanticObject(uri, vocabulary.StrategyPortlet);
    }
    public static Iterator<org.semanticwb.model.StrategyPortlet> listStrategyPortlets()
    {
        return (Iterator<org.semanticwb.model.StrategyPortlet>)vocabulary.StrategyPortlet.listInstances();
    }
    public static Referensable getReferensable(String uri)
    {
        return (Referensable)mgr.getOntology().getSemanticObject(uri,vocabulary.Referensable);
    }
    public static Referensable createReferensable(SemanticModel model, String uri)
    {
        return (Referensable)model.createSemanticObject(uri, vocabulary.Referensable);
    }
    public static Iterator<org.semanticwb.model.Referensable> listReferensables()
    {
        return (Iterator<org.semanticwb.model.Referensable>)vocabulary.Referensable.listInstances();
    }
    public static Groupable getGroupable(String uri)
    {
        return (Groupable)mgr.getOntology().getSemanticObject(uri,vocabulary.Groupable);
    }
    public static Groupable createGroupable(SemanticModel model, String uri)
    {
        return (Groupable)model.createSemanticObject(uri, vocabulary.Groupable);
    }
    public static Iterator<org.semanticwb.model.Groupable> listGroupables()
    {
        return (Iterator<org.semanticwb.model.Groupable>)vocabulary.Groupable.listInstances();
    }
    public static Device getDevice(String uri)
    {
        return (Device)mgr.getOntology().getSemanticObject(uri,vocabulary.Device);
    }
    public static Device createDevice(SemanticModel model, String uri)
    {
        return (Device)model.createSemanticObject(uri, vocabulary.Device);
    }
    public static Iterator<org.semanticwb.model.Device> listDevices()
    {
        return (Iterator<org.semanticwb.model.Device>)vocabulary.Device.listInstances();
    }
    public static SystemPortlet getSystemPortlet(String uri)
    {
        return (SystemPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.SystemPortlet);
    }
    public static SystemPortlet createSystemPortlet(SemanticModel model, String uri)
    {
        return (SystemPortlet)model.createSemanticObject(uri, vocabulary.SystemPortlet);
    }
    public static Iterator<org.semanticwb.model.SystemPortlet> listSystemPortlets()
    {
        return (Iterator<org.semanticwb.model.SystemPortlet>)vocabulary.SystemPortlet.listInstances();
    }
    public static Localeable getLocaleable(String uri)
    {
        return (Localeable)mgr.getOntology().getSemanticObject(uri,vocabulary.Localeable);
    }
    public static Localeable createLocaleable(SemanticModel model, String uri)
    {
        return (Localeable)model.createSemanticObject(uri, vocabulary.Localeable);
    }
    public static Iterator<org.semanticwb.model.Localeable> listLocaleables()
    {
        return (Iterator<org.semanticwb.model.Localeable>)vocabulary.Localeable.listInstances();
    }
    public static Camp getCamp(String uri)
    {
        return (Camp)mgr.getOntology().getSemanticObject(uri,vocabulary.Camp);
    }
    public static Camp createCamp(SemanticModel model, String uri)
    {
        return (Camp)model.createSemanticObject(uri, vocabulary.Camp);
    }
    public static Iterator<org.semanticwb.model.Camp> listCamps()
    {
        return (Iterator<org.semanticwb.model.Camp>)vocabulary.Camp.listInstances();
    }
    public static Dns getDns(String uri)
    {
        return (Dns)mgr.getOntology().getSemanticObject(uri,vocabulary.Dns);
    }
    public static Dns createDns(SemanticModel model, String uri)
    {
        return (Dns)model.createSemanticObject(uri, vocabulary.Dns);
    }
    public static Iterator<org.semanticwb.model.Dns> listDnss()
    {
        return (Iterator<org.semanticwb.model.Dns>)vocabulary.Dns.listInstances();
    }
    public static Portletable getPortletable(String uri)
    {
        return (Portletable)mgr.getOntology().getSemanticObject(uri,vocabulary.Portletable);
    }
    public static Portletable createPortletable(SemanticModel model, String uri)
    {
        return (Portletable)model.createSemanticObject(uri, vocabulary.Portletable);
    }
    public static Iterator<org.semanticwb.model.Portletable> listPortletables()
    {
        return (Iterator<org.semanticwb.model.Portletable>)vocabulary.Portletable.listInstances();
    }
    public static UserRepository getUserRepository(String uri)
    {
        return (UserRepository)mgr.getOntology().getSemanticObject(uri,vocabulary.UserRepository);
    }
    public static UserRepository createUserRepository(SemanticModel model, String uri)
    {
        return (UserRepository)model.createSemanticObject(uri, vocabulary.UserRepository);
    }
    public static Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        return (Iterator<org.semanticwb.model.UserRepository>)vocabulary.UserRepository.listInstances();
    }
    public static Template getTemplate(String uri)
    {
        return (Template)mgr.getOntology().getSemanticObject(uri,vocabulary.Template);
    }
    public static Template createTemplate(SemanticModel model, String uri)
    {
        return (Template)model.createSemanticObject(uri, vocabulary.Template);
    }
    public static Iterator<org.semanticwb.model.Template> listTemplates()
    {
        return (Iterator<org.semanticwb.model.Template>)vocabulary.Template.listInstances();
    }
    public static Priorityable getPriorityable(String uri)
    {
        return (Priorityable)mgr.getOntology().getSemanticObject(uri,vocabulary.Priorityable);
    }
    public static Priorityable createPriorityable(SemanticModel model, String uri)
    {
        return (Priorityable)model.createSemanticObject(uri, vocabulary.Priorityable);
    }
    public static Iterator<org.semanticwb.model.Priorityable> listPriorityables()
    {
        return (Iterator<org.semanticwb.model.Priorityable>)vocabulary.Priorityable.listInstances();
    }
    public static Role getRole(String uri)
    {
        return (Role)mgr.getOntology().getSemanticObject(uri,vocabulary.Role);
    }
    public static Role createRole(SemanticModel model, String uri)
    {
        return (Role)model.createSemanticObject(uri, vocabulary.Role);
    }
    public static Iterator<org.semanticwb.model.Role> listRoles()
    {
        return (Iterator<org.semanticwb.model.Role>)vocabulary.Role.listInstances();
    }
    public static VersionInfo getVersionInfo(String uri)
    {
        return (VersionInfo)mgr.getOntology().getSemanticObject(uri,vocabulary.VersionInfo);
    }
    public static VersionInfo createVersionInfo(SemanticModel model, String uri)
    {
        return (VersionInfo)model.createSemanticObject(uri, vocabulary.VersionInfo);
    }
    public static Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        return (Iterator<org.semanticwb.model.VersionInfo>)vocabulary.VersionInfo.listInstances();
    }
    public static Portlet getPortlet(String uri)
    {
        return (Portlet)mgr.getOntology().getSemanticObject(uri,vocabulary.Portlet);
    }
    public static Portlet createPortlet(SemanticModel model, String uri)
    {
        return (Portlet)model.createSemanticObject(uri, vocabulary.Portlet);
    }
    public static Iterator<org.semanticwb.model.Portlet> listPortlets()
    {
        return (Iterator<org.semanticwb.model.Portlet>)vocabulary.Portlet.listInstances();
    }
    public static Descriptiveable getDescriptiveable(String uri)
    {
        return (Descriptiveable)mgr.getOntology().getSemanticObject(uri,vocabulary.Descriptiveable);
    }
    public static Descriptiveable createDescriptiveable(SemanticModel model, String uri)
    {
        return (Descriptiveable)model.createSemanticObject(uri, vocabulary.Descriptiveable);
    }
    public static Iterator<org.semanticwb.model.Descriptiveable> listDescriptiveables()
    {
        return (Iterator<org.semanticwb.model.Descriptiveable>)vocabulary.Descriptiveable.listInstances();
    }
    public static Versionable getVersionable(String uri)
    {
        return (Versionable)mgr.getOntology().getSemanticObject(uri,vocabulary.Versionable);
    }
    public static Versionable createVersionable(SemanticModel model, String uri)
    {
        return (Versionable)model.createSemanticObject(uri, vocabulary.Versionable);
    }
    public static Iterator<org.semanticwb.model.Versionable> listVersionables()
    {
        return (Iterator<org.semanticwb.model.Versionable>)vocabulary.Versionable.listInstances();
    }
    public static RoleRef getRoleRef(String uri)
    {
        return (RoleRef)mgr.getOntology().getSemanticObject(uri,vocabulary.RoleRef);
    }
    public static RoleRef createRoleRef(SemanticModel model, String uri)
    {
        return (RoleRef)model.createSemanticObject(uri, vocabulary.RoleRef);
    }
    public static Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return (Iterator<org.semanticwb.model.RoleRef>)vocabulary.RoleRef.listInstances();
    }
    public static Rule getRule(String uri)
    {
        return (Rule)mgr.getOntology().getSemanticObject(uri,vocabulary.Rule);
    }
    public static Rule createRule(SemanticModel model, String uri)
    {
        return (Rule)model.createSemanticObject(uri, vocabulary.Rule);
    }
    public static Iterator<org.semanticwb.model.Rule> listRules()
    {
        return (Iterator<org.semanticwb.model.Rule>)vocabulary.Rule.listInstances();
    }
    public static Statusable getStatusable(String uri)
    {
        return (Statusable)mgr.getOntology().getSemanticObject(uri,vocabulary.Statusable);
    }
    public static Statusable createStatusable(SemanticModel model, String uri)
    {
        return (Statusable)model.createSemanticObject(uri, vocabulary.Statusable);
    }
    public static Iterator<org.semanticwb.model.Statusable> listStatusables()
    {
        return (Iterator<org.semanticwb.model.Statusable>)vocabulary.Statusable.listInstances();
    }
    public static WebPage getWebPage(String uri)
    {
        return (WebPage)mgr.getOntology().getSemanticObject(uri,vocabulary.WebPage);
    }
    public static WebPage createWebPage(SemanticModel model, String uri)
    {
        return (WebPage)model.createSemanticObject(uri, vocabulary.WebPage);
    }
    public static Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return (Iterator<org.semanticwb.model.WebPage>)vocabulary.WebPage.listInstances();
    }
    public static WebPageable getWebPageable(String uri)
    {
        return (WebPageable)mgr.getOntology().getSemanticObject(uri,vocabulary.WebPageable);
    }
    public static WebPageable createWebPageable(SemanticModel model, String uri)
    {
        return (WebPageable)model.createSemanticObject(uri, vocabulary.WebPageable);
    }
    public static Iterator<org.semanticwb.model.WebPageable> listWebPageables()
    {
        return (Iterator<org.semanticwb.model.WebPageable>)vocabulary.WebPageable.listInstances();
    }
    public static WebSite getWebSite(String uri)
    {
        return (WebSite)mgr.getOntology().getSemanticObject(uri,vocabulary.WebSite);
    }
    public static WebSite createWebSite(SemanticModel model, String uri)
    {
        return (WebSite)model.createSemanticObject(uri, vocabulary.WebSite);
    }
    public static Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (Iterator<org.semanticwb.model.WebSite>)vocabulary.WebSite.listInstances();
    }
    public static ObjectGroup getObjectGroup(String uri)
    {
        return (ObjectGroup)mgr.getOntology().getSemanticObject(uri,vocabulary.ObjectGroup);
    }
    public static ObjectGroup createObjectGroup(SemanticModel model, String uri)
    {
        return (ObjectGroup)model.createSemanticObject(uri, vocabulary.ObjectGroup);
    }
    public static Iterator<org.semanticwb.model.ObjectGroup> listObjectGroups()
    {
        return (Iterator<org.semanticwb.model.ObjectGroup>)vocabulary.ObjectGroup.listInstances();
    }
    public static Language getLanguage(String uri)
    {
        return (Language)mgr.getOntology().getSemanticObject(uri,vocabulary.Language);
    }
    public static Language createLanguage(SemanticModel model, String uri)
    {
        return (Language)model.createSemanticObject(uri, vocabulary.Language);
    }
    public static Iterator<org.semanticwb.model.Language> listLanguages()
    {
        return (Iterator<org.semanticwb.model.Language>)vocabulary.Language.listInstances();
    }
    public static PortletRef getPortletRef(String uri)
    {
        return (PortletRef)mgr.getOntology().getSemanticObject(uri,vocabulary.PortletRef);
    }
    public static PortletRef createPortletRef(SemanticModel model, String uri)
    {
        return (PortletRef)model.createSemanticObject(uri, vocabulary.PortletRef);
    }
    public static Iterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        return (Iterator<org.semanticwb.model.PortletRef>)vocabulary.PortletRef.listInstances();
    }
    public static PortletType getPortletType(String uri)
    {
        return (PortletType)mgr.getOntology().getSemanticObject(uri,vocabulary.PortletType);
    }
    public static PortletType createPortletType(SemanticModel model, String uri)
    {
        return (PortletType)model.createSemanticObject(uri, vocabulary.PortletType);
    }
    public static Iterator<org.semanticwb.model.PortletType> listPortletTypes()
    {
        return (Iterator<org.semanticwb.model.PortletType>)vocabulary.PortletType.listInstances();
    }
}
