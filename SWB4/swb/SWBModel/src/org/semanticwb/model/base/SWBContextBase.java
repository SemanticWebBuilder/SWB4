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
    public static Iterator<org.semanticwb.model.User> listUsers()
    {
        return (Iterator<org.semanticwb.model.User>)vocabulary.User.listInstances();
    }
    public static User createUser(SemanticModel model, String id)
    {
        return (User)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.User.getName()+"#"
                +id, vocabulary.User);
    }
    public static User createUser(SemanticModel model)
    {
        return (User)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.User.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.User.getName()), vocabulary.User);
    }
    public static Calendar getCalendar(String uri)
    {
        return (Calendar)mgr.getOntology().getSemanticObject(uri,vocabulary.Calendar);
    }
    public static Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return (Iterator<org.semanticwb.model.Calendar>)vocabulary.Calendar.listInstances();
    }
    public static Calendar createCalendar(SemanticModel model, String id)
    {
        return (Calendar)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Calendar.getName()+"#"
                +id, vocabulary.Calendar);
    }
    public static Calendar createCalendar(SemanticModel model)
    {
        return (Calendar)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Calendar.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Calendar.getName()), vocabulary.Calendar);
    }
    public static Community getCommunity(String uri)
    {
        return (Community)mgr.getOntology().getSemanticObject(uri,vocabulary.Community);
    }
    public static Iterator<org.semanticwb.model.Community> listCommunitys()
    {
        return (Iterator<org.semanticwb.model.Community>)vocabulary.Community.listInstances();
    }
    public static Community createCommunity(SemanticModel model, String id)
    {
        return (Community)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Community.getName()+"#"
                +id, vocabulary.Community);
    }
    public static ContentPortlet getContentPortlet(String uri)
    {
        return (ContentPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.ContentPortlet);
    }
    public static Iterator<org.semanticwb.model.ContentPortlet> listContentPortlets()
    {
        return (Iterator<org.semanticwb.model.ContentPortlet>)vocabulary.ContentPortlet.listInstances();
    }
    public static ContentPortlet createContentPortlet(SemanticModel model, String id)
    {
        return (ContentPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.ContentPortlet.getName()+"#"
                +id, vocabulary.ContentPortlet);
    }
    public static ContentPortlet createContentPortlet(SemanticModel model)
    {
        return (ContentPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.ContentPortlet.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.ContentPortlet.getName()), vocabulary.ContentPortlet);
    }
    public static TemplateRef getTemplateRef(String uri)
    {
        return (TemplateRef)mgr.getOntology().getSemanticObject(uri,vocabulary.TemplateRef);
    }
    public static Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return (Iterator<org.semanticwb.model.TemplateRef>)vocabulary.TemplateRef.listInstances();
    }
    public static TemplateRef createTemplateRef(SemanticModel model, String id)
    {
        return (TemplateRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.TemplateRef.getName()+"#"
                +id, vocabulary.TemplateRef);
    }
    public static TemplateRef createTemplateRef(SemanticModel model)
    {
        return (TemplateRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.TemplateRef.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.TemplateRef.getName()), vocabulary.TemplateRef);
    }
    public static Templateable getTemplateable(String uri)
    {
        return (Templateable)mgr.getOntology().getSemanticObject(uri,vocabulary.Templateable);
    }
    public static Iterator<org.semanticwb.model.Templateable> listTemplateables()
    {
        return (Iterator<org.semanticwb.model.Templateable>)vocabulary.Templateable.listInstances();
    }
    public static Deleteable getDeleteable(String uri)
    {
        return (Deleteable)mgr.getOntology().getSemanticObject(uri,vocabulary.Deleteable);
    }
    public static Iterator<org.semanticwb.model.Deleteable> listDeleteables()
    {
        return (Iterator<org.semanticwb.model.Deleteable>)vocabulary.Deleteable.listInstances();
    }
    public static Reference getReference(String uri)
    {
        return (Reference)mgr.getOntology().getSemanticObject(uri,vocabulary.Reference);
    }
    public static Iterator<org.semanticwb.model.Reference> listReferences()
    {
        return (Iterator<org.semanticwb.model.Reference>)vocabulary.Reference.listInstances();
    }
    public static Reference createReference(SemanticModel model, String id)
    {
        return (Reference)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Reference.getName()+"#"
                +id, vocabulary.Reference);
    }
    public static Reference createReference(SemanticModel model)
    {
        return (Reference)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Reference.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Reference.getName()), vocabulary.Reference);
    }
    public static Roleable getRoleable(String uri)
    {
        return (Roleable)mgr.getOntology().getSemanticObject(uri,vocabulary.Roleable);
    }
    public static Iterator<org.semanticwb.model.Roleable> listRoleables()
    {
        return (Iterator<org.semanticwb.model.Roleable>)vocabulary.Roleable.listInstances();
    }
    public static HomePage getHomePage(String uri)
    {
        return (HomePage)mgr.getOntology().getSemanticObject(uri,vocabulary.HomePage);
    }
    public static Iterator<org.semanticwb.model.HomePage> listHomePages()
    {
        return (Iterator<org.semanticwb.model.HomePage>)vocabulary.HomePage.listInstances();
    }
    public static HomePage createHomePage(SemanticModel model, String id)
    {
        return (HomePage)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.HomePage.getName()+"#"
                +id, vocabulary.HomePage);
    }
    public static RoleRefable getRoleRefable(String uri)
    {
        return (RoleRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.RoleRefable);
    }
    public static Iterator<org.semanticwb.model.RoleRefable> listRoleRefables()
    {
        return (Iterator<org.semanticwb.model.RoleRefable>)vocabulary.RoleRefable.listInstances();
    }
    public static Ruleable getRuleable(String uri)
    {
        return (Ruleable)mgr.getOntology().getSemanticObject(uri,vocabulary.Ruleable);
    }
    public static Iterator<org.semanticwb.model.Ruleable> listRuleables()
    {
        return (Iterator<org.semanticwb.model.Ruleable>)vocabulary.Ruleable.listInstances();
    }
    public static IPFilter getIPFilter(String uri)
    {
        return (IPFilter)mgr.getOntology().getSemanticObject(uri,vocabulary.IPFilter);
    }
    public static Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        return (Iterator<org.semanticwb.model.IPFilter>)vocabulary.IPFilter.listInstances();
    }
    public static IPFilter createIPFilter(SemanticModel model, String id)
    {
        return (IPFilter)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.IPFilter.getName()+"#"
                +id, vocabulary.IPFilter);
    }
    public static IPFilter createIPFilter(SemanticModel model)
    {
        return (IPFilter)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.IPFilter.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.IPFilter.getName()), vocabulary.IPFilter);
    }
    public static PFlow getPFlow(String uri)
    {
        return (PFlow)mgr.getOntology().getSemanticObject(uri,vocabulary.PFlow);
    }
    public static Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        return (Iterator<org.semanticwb.model.PFlow>)vocabulary.PFlow.listInstances();
    }
    public static PFlow createPFlow(SemanticModel model, String id)
    {
        return (PFlow)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.PFlow.getName()+"#"
                +id, vocabulary.PFlow);
    }
    public static PFlow createPFlow(SemanticModel model)
    {
        return (PFlow)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.PFlow.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.PFlow.getName()), vocabulary.PFlow);
    }
    public static ApplicationPortlet getApplicationPortlet(String uri)
    {
        return (ApplicationPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.ApplicationPortlet);
    }
    public static Iterator<org.semanticwb.model.ApplicationPortlet> listApplicationPortlets()
    {
        return (Iterator<org.semanticwb.model.ApplicationPortlet>)vocabulary.ApplicationPortlet.listInstances();
    }
    public static ApplicationPortlet createApplicationPortlet(SemanticModel model, String id)
    {
        return (ApplicationPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.ApplicationPortlet.getName()+"#"
                +id, vocabulary.ApplicationPortlet);
    }
    public static ApplicationPortlet createApplicationPortlet(SemanticModel model)
    {
        return (ApplicationPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.ApplicationPortlet.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.ApplicationPortlet.getName()), vocabulary.ApplicationPortlet);
    }
    public static RuleRefable getRuleRefable(String uri)
    {
        return (RuleRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.RuleRefable);
    }
    public static Iterator<org.semanticwb.model.RuleRefable> listRuleRefables()
    {
        return (Iterator<org.semanticwb.model.RuleRefable>)vocabulary.RuleRefable.listInstances();
    }
    public static Valueable getValueable(String uri)
    {
        return (Valueable)mgr.getOntology().getSemanticObject(uri,vocabulary.Valueable);
    }
    public static Iterator<org.semanticwb.model.Valueable> listValueables()
    {
        return (Iterator<org.semanticwb.model.Valueable>)vocabulary.Valueable.listInstances();
    }
    public static Calendarable getCalendarable(String uri)
    {
        return (Calendarable)mgr.getOntology().getSemanticObject(uri,vocabulary.Calendarable);
    }
    public static Iterator<org.semanticwb.model.Calendarable> listCalendarables()
    {
        return (Iterator<org.semanticwb.model.Calendarable>)vocabulary.Calendarable.listInstances();
    }
    public static PortletRefable getPortletRefable(String uri)
    {
        return (PortletRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.PortletRefable);
    }
    public static Iterator<org.semanticwb.model.PortletRefable> listPortletRefables()
    {
        return (Iterator<org.semanticwb.model.PortletRefable>)vocabulary.PortletRefable.listInstances();
    }
    public static Permission getPermission(String uri)
    {
        return (Permission)mgr.getOntology().getSemanticObject(uri,vocabulary.Permission);
    }
    public static Iterator<org.semanticwb.model.Permission> listPermissions()
    {
        return (Iterator<org.semanticwb.model.Permission>)vocabulary.Permission.listInstances();
    }
    public static Permission createPermission(SemanticModel model, String id)
    {
        return (Permission)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Permission.getName()+"#"
                +id, vocabulary.Permission);
    }
    public static TemplateRefable getTemplateRefable(String uri)
    {
        return (TemplateRefable)mgr.getOntology().getSemanticObject(uri,vocabulary.TemplateRefable);
    }
    public static Iterator<org.semanticwb.model.TemplateRefable> listTemplateRefables()
    {
        return (Iterator<org.semanticwb.model.TemplateRefable>)vocabulary.TemplateRefable.listInstances();
    }
    public static WebSiteable getWebSiteable(String uri)
    {
        return (WebSiteable)mgr.getOntology().getSemanticObject(uri,vocabulary.WebSiteable);
    }
    public static Iterator<org.semanticwb.model.WebSiteable> listWebSiteables()
    {
        return (Iterator<org.semanticwb.model.WebSiteable>)vocabulary.WebSiteable.listInstances();
    }
    public static RuleRef getRuleRef(String uri)
    {
        return (RuleRef)mgr.getOntology().getSemanticObject(uri,vocabulary.RuleRef);
    }
    public static Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return (Iterator<org.semanticwb.model.RuleRef>)vocabulary.RuleRef.listInstances();
    }
    public static RuleRef createRuleRef(SemanticModel model, String id)
    {
        return (RuleRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.RuleRef.getName()+"#"
                +id, vocabulary.RuleRef);
    }
    public static RuleRef createRuleRef(SemanticModel model)
    {
        return (RuleRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.RuleRef.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.RuleRef.getName()), vocabulary.RuleRef);
    }
    public static StrategyPortlet getStrategyPortlet(String uri)
    {
        return (StrategyPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.StrategyPortlet);
    }
    public static Iterator<org.semanticwb.model.StrategyPortlet> listStrategyPortlets()
    {
        return (Iterator<org.semanticwb.model.StrategyPortlet>)vocabulary.StrategyPortlet.listInstances();
    }
    public static StrategyPortlet createStrategyPortlet(SemanticModel model, String id)
    {
        return (StrategyPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.StrategyPortlet.getName()+"#"
                +id, vocabulary.StrategyPortlet);
    }
    public static StrategyPortlet createStrategyPortlet(SemanticModel model)
    {
        return (StrategyPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.StrategyPortlet.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.StrategyPortlet.getName()), vocabulary.StrategyPortlet);
    }
    public static Referensable getReferensable(String uri)
    {
        return (Referensable)mgr.getOntology().getSemanticObject(uri,vocabulary.Referensable);
    }
    public static Iterator<org.semanticwb.model.Referensable> listReferensables()
    {
        return (Iterator<org.semanticwb.model.Referensable>)vocabulary.Referensable.listInstances();
    }
    public static Groupable getGroupable(String uri)
    {
        return (Groupable)mgr.getOntology().getSemanticObject(uri,vocabulary.Groupable);
    }
    public static Iterator<org.semanticwb.model.Groupable> listGroupables()
    {
        return (Iterator<org.semanticwb.model.Groupable>)vocabulary.Groupable.listInstances();
    }
    public static Device getDevice(String uri)
    {
        return (Device)mgr.getOntology().getSemanticObject(uri,vocabulary.Device);
    }
    public static Iterator<org.semanticwb.model.Device> listDevices()
    {
        return (Iterator<org.semanticwb.model.Device>)vocabulary.Device.listInstances();
    }
    public static Device createDevice(SemanticModel model, String id)
    {
        return (Device)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Device.getName()+"#"
                +id, vocabulary.Device);
    }
    public static Device createDevice(SemanticModel model)
    {
        return (Device)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Device.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Device.getName()), vocabulary.Device);
    }
    public static SystemPortlet getSystemPortlet(String uri)
    {
        return (SystemPortlet)mgr.getOntology().getSemanticObject(uri,vocabulary.SystemPortlet);
    }
    public static Iterator<org.semanticwb.model.SystemPortlet> listSystemPortlets()
    {
        return (Iterator<org.semanticwb.model.SystemPortlet>)vocabulary.SystemPortlet.listInstances();
    }
    public static SystemPortlet createSystemPortlet(SemanticModel model, String id)
    {
        return (SystemPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.SystemPortlet.getName()+"#"
                +id, vocabulary.SystemPortlet);
    }
    public static SystemPortlet createSystemPortlet(SemanticModel model)
    {
        return (SystemPortlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.SystemPortlet.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.SystemPortlet.getName()), vocabulary.SystemPortlet);
    }
    public static Localeable getLocaleable(String uri)
    {
        return (Localeable)mgr.getOntology().getSemanticObject(uri,vocabulary.Localeable);
    }
    public static Iterator<org.semanticwb.model.Localeable> listLocaleables()
    {
        return (Iterator<org.semanticwb.model.Localeable>)vocabulary.Localeable.listInstances();
    }
    public static Camp getCamp(String uri)
    {
        return (Camp)mgr.getOntology().getSemanticObject(uri,vocabulary.Camp);
    }
    public static Iterator<org.semanticwb.model.Camp> listCamps()
    {
        return (Iterator<org.semanticwb.model.Camp>)vocabulary.Camp.listInstances();
    }
    public static Camp createCamp(SemanticModel model, String id)
    {
        return (Camp)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Camp.getName()+"#"
                +id, vocabulary.Camp);
    }
    public static Camp createCamp(SemanticModel model)
    {
        return (Camp)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Camp.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Camp.getName()), vocabulary.Camp);
    }
    public static Dns getDns(String uri)
    {
        return (Dns)mgr.getOntology().getSemanticObject(uri,vocabulary.Dns);
    }
    public static Iterator<org.semanticwb.model.Dns> listDnss()
    {
        return (Iterator<org.semanticwb.model.Dns>)vocabulary.Dns.listInstances();
    }
    public static Dns createDns(SemanticModel model, String id)
    {
        return (Dns)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Dns.getName()+"#"
                +id, vocabulary.Dns);
    }
    public static Dns createDns(SemanticModel model)
    {
        return (Dns)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Dns.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Dns.getName()), vocabulary.Dns);
    }
    public static Portletable getPortletable(String uri)
    {
        return (Portletable)mgr.getOntology().getSemanticObject(uri,vocabulary.Portletable);
    }
    public static Iterator<org.semanticwb.model.Portletable> listPortletables()
    {
        return (Iterator<org.semanticwb.model.Portletable>)vocabulary.Portletable.listInstances();
    }
    public static UserRepository getUserRepository(String uri)
    {
        return (UserRepository)mgr.getOntology().getSemanticObject(uri,vocabulary.UserRepository);
    }
    public static Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        return (Iterator<org.semanticwb.model.UserRepository>)vocabulary.UserRepository.listInstances();
    }
    public static UserRepository createUserRepository(SemanticModel model, String id)
    {
        return (UserRepository)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.UserRepository.getName()+"#"
                +id, vocabulary.UserRepository);
    }
    public static Template getTemplate(String uri)
    {
        return (Template)mgr.getOntology().getSemanticObject(uri,vocabulary.Template);
    }
    public static Iterator<org.semanticwb.model.Template> listTemplates()
    {
        return (Iterator<org.semanticwb.model.Template>)vocabulary.Template.listInstances();
    }
    public static Template createTemplate(SemanticModel model, String id)
    {
        return (Template)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Template.getName()+"#"
                +id, vocabulary.Template);
    }
    public static Template createTemplate(SemanticModel model)
    {
        return (Template)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Template.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Template.getName()), vocabulary.Template);
    }
    public static Priorityable getPriorityable(String uri)
    {
        return (Priorityable)mgr.getOntology().getSemanticObject(uri,vocabulary.Priorityable);
    }
    public static Iterator<org.semanticwb.model.Priorityable> listPriorityables()
    {
        return (Iterator<org.semanticwb.model.Priorityable>)vocabulary.Priorityable.listInstances();
    }
    public static Role getRole(String uri)
    {
        return (Role)mgr.getOntology().getSemanticObject(uri,vocabulary.Role);
    }
    public static Iterator<org.semanticwb.model.Role> listRoles()
    {
        return (Iterator<org.semanticwb.model.Role>)vocabulary.Role.listInstances();
    }
    public static Role createRole(SemanticModel model, String id)
    {
        return (Role)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Role.getName()+"#"
                +id, vocabulary.Role);
    }
    public static Role createRole(SemanticModel model)
    {
        return (Role)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Role.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Role.getName()), vocabulary.Role);
    }
    public static VersionInfo getVersionInfo(String uri)
    {
        return (VersionInfo)mgr.getOntology().getSemanticObject(uri,vocabulary.VersionInfo);
    }
    public static Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        return (Iterator<org.semanticwb.model.VersionInfo>)vocabulary.VersionInfo.listInstances();
    }
    public static VersionInfo createVersionInfo(SemanticModel model, String id)
    {
        return (VersionInfo)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.VersionInfo.getName()+"#"
                +id, vocabulary.VersionInfo);
    }
    public static VersionInfo createVersionInfo(SemanticModel model)
    {
        return (VersionInfo)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.VersionInfo.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.VersionInfo.getName()), vocabulary.VersionInfo);
    }
    public static Portlet getPortlet(String uri)
    {
        return (Portlet)mgr.getOntology().getSemanticObject(uri,vocabulary.Portlet);
    }
    public static Iterator<org.semanticwb.model.Portlet> listPortlets()
    {
        return (Iterator<org.semanticwb.model.Portlet>)vocabulary.Portlet.listInstances();
    }
    public static Portlet createPortlet(SemanticModel model, String id)
    {
        return (Portlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Portlet.getName()+"#"
                +id, vocabulary.Portlet);
    }
    public static Portlet createPortlet(SemanticModel model)
    {
        return (Portlet)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Portlet.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Portlet.getName()), vocabulary.Portlet);
    }
    public static Descriptiveable getDescriptiveable(String uri)
    {
        return (Descriptiveable)mgr.getOntology().getSemanticObject(uri,vocabulary.Descriptiveable);
    }
    public static Iterator<org.semanticwb.model.Descriptiveable> listDescriptiveables()
    {
        return (Iterator<org.semanticwb.model.Descriptiveable>)vocabulary.Descriptiveable.listInstances();
    }
    public static Versionable getVersionable(String uri)
    {
        return (Versionable)mgr.getOntology().getSemanticObject(uri,vocabulary.Versionable);
    }
    public static Iterator<org.semanticwb.model.Versionable> listVersionables()
    {
        return (Iterator<org.semanticwb.model.Versionable>)vocabulary.Versionable.listInstances();
    }
    public static RoleRef getRoleRef(String uri)
    {
        return (RoleRef)mgr.getOntology().getSemanticObject(uri,vocabulary.RoleRef);
    }
    public static Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return (Iterator<org.semanticwb.model.RoleRef>)vocabulary.RoleRef.listInstances();
    }
    public static RoleRef createRoleRef(SemanticModel model, String id)
    {
        return (RoleRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.RoleRef.getName()+"#"
                +id, vocabulary.RoleRef);
    }
    public static RoleRef createRoleRef(SemanticModel model)
    {
        return (RoleRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.RoleRef.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.RoleRef.getName()), vocabulary.RoleRef);
    }
    public static Rule getRule(String uri)
    {
        return (Rule)mgr.getOntology().getSemanticObject(uri,vocabulary.Rule);
    }
    public static Iterator<org.semanticwb.model.Rule> listRules()
    {
        return (Iterator<org.semanticwb.model.Rule>)vocabulary.Rule.listInstances();
    }
    public static Rule createRule(SemanticModel model, String id)
    {
        return (Rule)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Rule.getName()+"#"
                +id, vocabulary.Rule);
    }
    public static Rule createRule(SemanticModel model)
    {
        return (Rule)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Rule.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.Rule.getName()), vocabulary.Rule);
    }
    public static Statusable getStatusable(String uri)
    {
        return (Statusable)mgr.getOntology().getSemanticObject(uri,vocabulary.Statusable);
    }
    public static Iterator<org.semanticwb.model.Statusable> listStatusables()
    {
        return (Iterator<org.semanticwb.model.Statusable>)vocabulary.Statusable.listInstances();
    }
    public static WebPage getWebPage(String uri)
    {
        return (WebPage)mgr.getOntology().getSemanticObject(uri,vocabulary.WebPage);
    }
    public static Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return (Iterator<org.semanticwb.model.WebPage>)vocabulary.WebPage.listInstances();
    }
    public static WebPage createWebPage(SemanticModel model, String id)
    {
        return (WebPage)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.WebPage.getName()+"#"
                +id, vocabulary.WebPage);
    }
    public static WebPageable getWebPageable(String uri)
    {
        return (WebPageable)mgr.getOntology().getSemanticObject(uri,vocabulary.WebPageable);
    }
    public static Iterator<org.semanticwb.model.WebPageable> listWebPageables()
    {
        return (Iterator<org.semanticwb.model.WebPageable>)vocabulary.WebPageable.listInstances();
    }
    public static WebSite getWebSite(String uri)
    {
        return (WebSite)mgr.getOntology().getSemanticObject(uri,vocabulary.WebSite);
    }
    public static Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (Iterator<org.semanticwb.model.WebSite>)vocabulary.WebSite.listInstances();
    }
    public static WebSite createWebSite(SemanticModel model, String id)
    {
        return (WebSite)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.WebSite.getName()+"#"
                +id, vocabulary.WebSite);
    }
    public static ObjectGroup getObjectGroup(String uri)
    {
        return (ObjectGroup)mgr.getOntology().getSemanticObject(uri,vocabulary.ObjectGroup);
    }
    public static Iterator<org.semanticwb.model.ObjectGroup> listObjectGroups()
    {
        return (Iterator<org.semanticwb.model.ObjectGroup>)vocabulary.ObjectGroup.listInstances();
    }
    public static ObjectGroup createObjectGroup(SemanticModel model, String id)
    {
        return (ObjectGroup)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.ObjectGroup.getName()+"#"
                +id, vocabulary.ObjectGroup);
    }
    public static ObjectGroup createObjectGroup(SemanticModel model)
    {
        return (ObjectGroup)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.ObjectGroup.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.ObjectGroup.getName()), vocabulary.ObjectGroup);
    }
    public static Language getLanguage(String uri)
    {
        return (Language)mgr.getOntology().getSemanticObject(uri,vocabulary.Language);
    }
    public static Iterator<org.semanticwb.model.Language> listLanguages()
    {
        return (Iterator<org.semanticwb.model.Language>)vocabulary.Language.listInstances();
    }
    public static Language createLanguage(SemanticModel model, String id)
    {
        return (Language)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.Language.getName()+"#"
                +id, vocabulary.Language);
    }
    public static PortletRef getPortletRef(String uri)
    {
        return (PortletRef)mgr.getOntology().getSemanticObject(uri,vocabulary.PortletRef);
    }
    public static Iterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        return (Iterator<org.semanticwb.model.PortletRef>)vocabulary.PortletRef.listInstances();
    }
    public static PortletRef createPortletRef(SemanticModel model, String id)
    {
        return (PortletRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.PortletRef.getName()+"#"
                +id, vocabulary.PortletRef);
    }
    public static PortletRef createPortletRef(SemanticModel model)
    {
        return (PortletRef)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.PortletRef.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.PortletRef.getName()), vocabulary.PortletRef);
    }
    public static PortletType getPortletType(String uri)
    {
        return (PortletType)mgr.getOntology().getSemanticObject(uri,vocabulary.PortletType);
    }
    public static Iterator<org.semanticwb.model.PortletType> listPortletTypes()
    {
        return (Iterator<org.semanticwb.model.PortletType>)vocabulary.PortletType.listInstances();
    }
    public static PortletType createPortletType(SemanticModel model, String id)
    {
        return (PortletType)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.PortletType.getName()+"#"
                +id, vocabulary.PortletType);
    }
    public static PortletType createPortletType(SemanticModel model)
    {
        return (PortletType)model.createSemanticObject(model.getNameSpace()+"/"+vocabulary.PortletType.getName()+"#"
                +SWBInstance.getCounterValue(model.getName()+"/"+vocabulary.PortletType.getName()), vocabulary.PortletType);
    }
}
