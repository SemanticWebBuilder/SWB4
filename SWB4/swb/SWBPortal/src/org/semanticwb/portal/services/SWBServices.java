/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.io.File;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Calendar;
import org.semanticwb.model.Calendarable;
import org.semanticwb.model.Deleteable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.Device;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Groupable;
import org.semanticwb.model.HomePage;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.Language;
import org.semanticwb.model.Localeable;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.Priorityable;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.Rule;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRefable;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.RuleRefable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Statusable;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.TemplateRefable;
import org.semanticwb.model.User;
import org.semanticwb.model.Valueable;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebPageable;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticIterator;

/**
 *
 * @author jorge.jimenez
 */
public class SWBServices {

    private static Logger log = SWBUtils.getLogger(SWBServices.class);

    public boolean delete(Deleteable deleteable, boolean deleted, User user) throws SWBException {
        boolean doAction = false;
        deleteable.setDeleted(deleted);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "delete", deleteable.getURI(), deleteable.getURI(), "delete", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Delete", e);
        }
        return doAction;
    }

    public boolean addCalendar(Calendarable calendarable, Calendar calendar, User user) throws SWBException {
        boolean doAction = false;
        calendarable.addCalendar(calendar);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", calendarable.getURI(), calendarable.getURI(), "assign calendar", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Assign calendar", e);
        }
        return doAction;
    }

    public boolean removeCalendar(Calendarable calendarable, Calendar calendar, User user) throws SWBException {
        boolean doAction = false;
        calendarable.removeCalendar(calendar);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "Unassign", calendarable.getURI(), calendarable.getURI(), "unassign calendar", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("UnAssign calendar", e);
        }
        return doAction;
    }

    public static boolean setStatus(Statusable statusable, int status, User user) throws SWBException {

        statusable.setStatus(status);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "status", statusable.getURI(), statusable.getURI(), "update Status", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating status", e);
        }
        return true;
    }

    public boolean addTemplateRef(TemplateRefable templateRefable, TemplateRef templateRef, User user) throws SWBException {

        templateRefable.addTemplateRef(templateRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", templateRefable.getURI(), templateRefable.getURI(), "Assign template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding template", e);
        }

        return true;
    }

    public boolean removeTemplateRef(TemplateRefable templateRefable, TemplateRef templateRef, User user) throws SWBException {

        templateRefable.removeTemplateRef(templateRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "UnAssign", templateRefable.getURI(), templateRefable.getURI(), "UnAssign template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing templateRef", e);
        }

        return true;
    }

    public boolean addRuleRef(RuleRefable ruleRefable, RuleRef ruleRef, User user) throws SWBException {
        boolean doAction = false;
        ruleRefable.addRuleRef(ruleRef);
        doAction = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", ruleRefable.getURI(), ruleRefable.getURI(), "assign rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assigning rule", e);
        }
        return doAction;
    }

    public boolean removeRuleRef(RuleRefable ruleRefable, RuleRef ruleRef, User user) throws SWBException {
        boolean doAction = false;
        ruleRefable.removeRuleRef(ruleRef);
        doAction = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "Unassign", ruleRefable.getURI(), ruleRefable.getURI(), "unassign rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error unassigning rule", e);
        }
        return doAction;
    }

    public boolean addRoleRef(RoleRefable roleRefable, RoleRef roleRef, User user) throws SWBException {
        boolean doAction = false;
        roleRefable.addRoleRef(roleRef);
        doAction = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", roleRefable.getURI(), roleRefable.getURI(), "assign role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assigning role", e);
        }
        return doAction;
    }

    public boolean removeRoleRef(RoleRefable roleRefable, RoleRef roleRef, User user) throws SWBException {
        boolean doAction = false;
        roleRefable.removeRoleRef(roleRef);
        doAction = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "Unassign", roleRefable.getURI(), roleRefable.getURI(), "assign role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error unassigning role", e);
        }
        return doAction;
    }

    public boolean addDescription(Descriptiveable descriptiveable, String description, User user) throws SWBException
    {
        boolean doAction = false;
        descriptiveable.setDescription(description);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", descriptiveable.getURI(), descriptiveable.getURI(), "assing description", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining description", e);
        }
        return doAction;
    }
    
    public boolean groupable(Groupable groupable, ObjectGroup objectgroup, User user) throws SWBException
    {
        boolean doAction = false;
        groupable.addGroup(objectgroup);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", groupable.getURI(), groupable.getURI(), "assing description", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining description", e);
        }
        return doAction;
    }
    
    public boolean localeable(Localeable localeable, Language language, User user) throws SWBException
    {
        boolean doAction = false;
        localeable.setLanguage(language);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", localeable.getURI(), localeable.getURI(), "assing language", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining language", e);
        }
        return doAction;
    }
    
     public boolean priorityable(Priorityable priorityable, int priority, User user) throws SWBException
     {
        boolean doAction = false;
        priorityable.setPriority(priority);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", priorityable.getURI(), priorityable.getURI(), "assing priority", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining priority", e);
        }
        return doAction;
    }
     
     
     public boolean setValue(Valueable valueable, String value, User user) throws SWBException
     {
        boolean doAction = false;
        valueable.setValue(value);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", valueable.getURI(), valueable.getURI(), "assing value", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining value", e);
        }
        return doAction;
    }
     
     public boolean setVersion(Versionable versionable, VersionInfo versionInfo, User user) throws SWBException
     {
        boolean doAction = false;
        versionable.setActualVersion(versionInfo);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", versionable.getURI(), versionable.getURI(), "assing versionInfo", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining versionInfo", e);
        }
        return doAction;
    }
     
    public boolean removeVersion(Versionable versionable, User user) throws SWBException
    {
        boolean doAction = false;
        versionable.removeActualVersion();
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "UnAssign", versionable.getURI(), versionable.getURI(), "UnAssing versionInfo", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error unAssining versionInfo", e);
        }
        return doAction;
    } 
    
    public boolean addWebPage(WebPageable webPageable, WebPage webPage, User user) throws SWBException
    {
        boolean doAction = false;
        webPageable.addWebPage(webPage);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", webPageable.getURI(), webPageable.getURI(), "assing webPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error assining webPage", e);
        }
        return doAction;
    } 
    
    public boolean removeWebPage(WebPageable webPageable, WebPage webPage, User user) throws SWBException
    {
        boolean doAction = false;
        webPageable.removeWebPage(webPage);
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "unAssign", webPageable.getURI(), webPageable.getURI(), "UnAssing webPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error UnAssining webPage", e);
        }
        return doAction;
    } 
  
    public static class WebSiteSrv {

        public static WebSite createWebSite(SemanticModel model, String siteUri, String homeUri, String title, String homeTitle, User user) throws SWBException {
            //creación de modelo
            //SemanticModel model=SWBInstance.getSemanticMgr().loadDBModel("system");

            WebSite website = SWBContext.createWebSite(model, siteUri);
            HomePage hpage = SWBContext.createHomePage(model, homeUri);
            hpage.setTitle(homeTitle);
            website.addHome(hpage);

            website.setUserCreated(user);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", website.getURI(), website.getURI(), SWBUtils.IO.getLocaleString(SWBUtils.LOCALE_SERVICES, "create_website"), null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating WebSite", e);
            }
            return website;
        }

        public static boolean updateWebSite(WebSite webSite, String description, String home, String language, String title, boolean deleted, int status, User user) throws SWBException {

            if (title != null) {
                webSite.setTitle(title);
            }
            webSite.setDeleted(deleted);
            if (description != null) {
                webSite.setDescription(description);
            }
            webSite.setStatus(status);
            webSite.setUserModified(user);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", webSite.getURI(), webSite.getURI(), "Update WebSite", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error updating WebSite", e);
            }
            return true;
        }
    }

    public static class WebPageSrv {

        public WebPage createWebPage(SemanticModel model, String uri, String title, WebPage childOf, User user) throws SWBException {
            WebPage page = SWBContext.createWebPage(model, uri);
            page.setTitle(title);
            page.setIsChildOf(childOf);
            page.setUserCreated(user);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", page.getURI(), page.getURI(), "create WebPage", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating WebPage", e);
            }

            return page;
        }

        public boolean removeWebPage(WebPage page, User user) throws SWBException {
            SWBContext.removeObject(page.getURI());

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", page.getURI(), page.getURI(), "remove WebPage", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing WebPage", e);
            }

            return true;
        }

        public boolean removeTemplateFromWebPage(SemanticModel model, WebPage page, Template template, User user) throws SWBException {
            SemanticIterator<TemplateRef> itTemplateRef = page.listTemplateRef();
            while (itTemplateRef.hasNext()) {
                TemplateRef ref = itTemplateRef.next();
                if (ref.getTemplate().getURI().equals(ref)) {
                    page.removeTemplateRef(ref);
                }
            }

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "unassign", page.getURI(), page.getURI(), "remove Ref Template from page", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing rule from WebPage", e);
            }

            return true;
        }

        public boolean removeRuleFromWebPage(SemanticModel model, WebPage page, Rule rule, User user) throws SWBException {
            SemanticIterator<RuleRef> itRuleRef = page.listRuleRef();
            while (itRuleRef.hasNext()) {
                RuleRef ref = itRuleRef.next();
                if (ref.getRule().getURI().equals(ref)) {
                    page.removeRuleRef(ref);
                }
            }

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "unassign", page.getURI(), page.getURI(), "remove Ref Rule from page", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing rule from WebPage", e);
            }

            return true;
        }

        public boolean removeRoleFromWebPage(SemanticModel model, WebPage page, Role role, User user) throws SWBException {
            SemanticIterator<RoleRef> itRoleRef = page.listRoleRef();
            while (itRoleRef.hasNext()) {
                RoleRef ref = itRoleRef.next();
                if (ref.getRole().getURI().equals(ref)) {
                    page.removeRoleRef(ref);
                }
            }

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "unassign", page.getURI(), page.getURI(), "remove Ref Role from page", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing role from WebPage", e);
            }

            return true;
        }
    }

    public static class TemplateSrv {

        public Template createTemplate(SemanticModel model, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
            Template template = SWBContext.createTemplate(model);
            template.setTitle(titulo);
            template.setDescription(description);
            template.addGroup(objectgroup);
            template.setStatus(1);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", template.getURI(), template.getURI(), "create Template", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating Template", e);
            }

            return template;
        }

        public Template createTemplate(SemanticModel model, String templateUri, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
            Template template = SWBContext.createTemplate(model, templateUri);
            template.setTitle(titulo);
            template.setDescription(description);
            template.addGroup(objectgroup);
            template.setStatus(1);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", template.getURI(), template.getURI(), "create Template", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating Template", e);
            }

            return template;
        }

        public boolean removeTemplate(Template template, User user) throws SWBException {
            boolean deleted = false;
            SWBContext.removeObject(template.getURI());
            deleted = true;

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", template.getURI(), template.getURI(), "remove Template", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing Template", e);
            }

            return deleted;
        }

        public static boolean resetTemplates(SemanticModel model, Template template, User user) {
            try {
                String rutawork = (String) SWBInstance.getWorkPath();
                File dir = new File(rutawork + "/sites/" + "modelo" + "/templates/" + template.getURI());
                if (dir != null && dir.exists() && dir.isDirectory()) {
                    File listado[] = dir.listFiles();
                    for (int i = 0; i < listado.length; i++) {
                        if (listado[i].isDirectory() && !listado[i].getName().equals(String.valueOf(template.getActualVersion().getValue()))) {
                            boolean flag = SWBUtils.IO.removeDirectory(listado[i].getPath());
                            if (flag) {
                                listado[i].delete();
                            }
                        }
                    }
                    boolean b = true;
                    String lastV = template.getActualVersion().getValue();
                    if (!lastV.equals("1")) {
                        File ActualVDir = new File(rutawork + "/sites/" + "topicmap" + "/templates/" + "templateid" + "/" + lastV);
                        File f = new File(rutawork + "/sites/" + "topicmap" + "/templates/" + "templateid" + "/1");
                        f.mkdir();
                        String sSource = "sites/" + "topicmap" + "/templates/" + "templateid" + "/" + lastV;
                        String sTarget = "sites/" + "topicmap" + "/templates/" + "templateid" + "/1";
                        b = SWBUtils.IO.copyStructure(ActualVDir.getPath() + "/", f.getPath() + "/", true, sSource, sTarget);
                    }
                    if (b) {
                        VersionInfo verInfo = SWBContext.createVersionInfo(model);
                        verInfo.setValue("1");
                        template.setActualVersion(verInfo);
                        template.setLastVersion(verInfo);

                        //logeo
                        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", template.getURI(), template.getURI(), "remove Template versions", null);
                        try {
                            swbAdmLog.create();
                        } catch (Exception e) {
                            throw new SWBException("Error removing Template versions", e);
                        }

                        return true;
                    }
                }
            } catch (Exception e) {
                log.error("Error while removing template version width id:" + "templateid" + "-TemplateSrv:RemoveTemplateVersion", e);
            }
            return false;
        }
    }

    public static class RuleSrv {

        public Rule createRule(SemanticModel model, String title, String description, User user) throws SWBException {
            Rule rule = SWBContext.createRule(model);
            rule.setTitle(title);
            rule.setDescription(description);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", rule.getURI(), rule.getURI(), "create Rule", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating rule", e);
            }
            return rule;

        }

        public Rule createRule(SemanticModel model, String ruleUri, String title, String description, User user) throws SWBException {
            Rule rule = SWBContext.createRule(model, ruleUri);
            rule.setTitle(title);
            rule.setDescription(description);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", rule.getURI(), rule.getURI(), "create Rule", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating rule", e);
            }
            return rule;

        }

        public boolean removeRule(Rule rule, User user) throws SWBException {
            boolean deleted = false;
            SWBContext.removeObject(rule.getURI());
            deleted = true;

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", rule.getURI(), rule.getURI(), "remove Rule", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing rule", e);
            }
            return deleted;

        }
    }

    public static class RoleSrv {

        public Role createRule(SemanticModel model, String title, String description, User user) throws SWBException {
            Role role = SWBContext.createRole(model);
            role.setTitle(title);
            role.setDescription(description);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", role.getURI(), role.getURI(), "create Role", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating role", e);
            }
            return role;

        }

        public Role createRule(SemanticModel model, String roleUri, String title, String description, User user) throws SWBException {
            Role role = SWBContext.createRole(model, roleUri);
            role.setTitle(title);
            role.setDescription(description);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", role.getURI(), role.getURI(), "create Role", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating role", e);
            }
            return role;

        }

        public boolean removeRole(Role role, User user) throws SWBException {
            boolean deleted = false;
            SWBContext.removeObject(role.getURI());
            deleted = true;

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", role.getURI(), role.getURI(), "remove Role", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing role", e);
            }
            return deleted;

        }
    }

    public static class IPFilterSrv {

        public IPFilter createIPFilter(SemanticModel model, String value, User user) throws SWBException {
            IPFilter ipFilter = SWBContext.createIPFilter(model);
            ipFilter.setStatus(1);
            ipFilter.setValue(value);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", ipFilter.getURI(), ipFilter.getURI(), "create IPFilter", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating IPFilter", e);
            }
            return ipFilter;
        }

        public IPFilter createIPFilter(SemanticModel model, String filterUri, String value, User user) throws SWBException {
            IPFilter ipFilter = SWBContext.createIPFilter(model, filterUri);
            ipFilter.setStatus(1);
            ipFilter.setValue(value);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", ipFilter.getURI(), ipFilter.getURI(), "create IPFilter", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating IPFilter", e);
            }
            return ipFilter;
        }

        public boolean removeIPFilter(IPFilter ipFilter, User user) throws SWBException {
            boolean removed = false;
            SWBContext.removeObject(ipFilter.getURI());
            removed = true;
            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", ipFilter.getURI(), ipFilter.getURI(), "create IPFilter", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating IPFilter", e);
            }
            return removed;
        }

        public boolean updateIPFilter(IPFilter ipFilter, String value, User user) throws SWBException {
            boolean updated = false;

            if (value != null) {
                ipFilter.setValue(value);
            }
            updated = true;
            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", ipFilter.getURI(), ipFilter.getURI(), "update IPFilter", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error updating IPFilter", e);
            }
            return updated;
        }
    }

    public static class DnsSrv {

        public Dns createDNS(SemanticModel model, String title, String description, String value, User user) throws SWBException {
            Dns dns = null;
            dns = SWBContext.createDns(model);
            dns.setTitle(title);
            dns.setDescription(description);
            dns.setValue(value);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", dns.getURI(), dns.getURI(), "create DNS", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating dns", e);
            }
            return dns;
        }

        public Dns createDNS(SemanticModel model, String dnsUri, String title, String description, String value, User user) throws SWBException {
            Dns dns = null;
            dns = SWBContext.createDns(model, dnsUri);
            dns.setTitle(title);
            dns.setDescription(description);
            dns.setValue(value);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", dns.getURI(), dns.getURI(), "create DNS", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating dns", e);
            }
            return dns;
        }

        public boolean removeDNS(Dns dns, User user) throws SWBException {
            boolean deleted = false;
            SWBContext.removeObject(dns.getURI());
            deleted = true;
            //logeo.creat
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", dns.getURI(), dns.getURI(), "remove DNS", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing dns:" + dns.getURI(), e);
            }
            return deleted;
        }

        public boolean updateDNS(SemanticModel model, Dns dns, String title, String description, String value, User user) throws SWBException {
            boolean updated = false;

            if (title != null) {
                dns.setTitle(title);
            }
            if (description != null) {
                dns.setDescription(description);
            }
            if (value != null) {
                dns.setValue(value);
            }
            updated = true;
            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", dns.getURI(), dns.getURI(), "update DNS", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error updating Dns", e);
            }
            return updated;
        }
    }

    public static class DeviceSrv {

        public Device createDevice(SemanticModel model, String title, String description, String value, User user) throws SWBException {

            Device device = null;
            device = SWBContext.createDevice(model);
            device.setTitle(title);
            device.setDescription(description);
            device.setValue(value);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", device.getURI(), device.getURI(), "create device", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating device", e);
            }
            return device;
        }

        public Device createDevice(SemanticModel model, String deviceUri, String title, String description, String value, User user) throws SWBException {

            Device device = null;
            device = SWBContext.createDevice(model, deviceUri);
            device.setTitle(title);
            device.setDescription(description);
            device.setValue(value);

            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", device.getURI(), device.getURI(), "create device", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error creating device", e);
            }
            return device;
        }

        public boolean removeDNS(Device device, User user) throws SWBException {
            boolean deleted = false;
            SWBContext.removeObject(device.getURI());
            deleted = true;
            //logeo.creat
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", device.getURI(), device.getURI(), "remove Device", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error removing device:" + device.getURI(), e);
            }
            return deleted;
        }

        public boolean updateDevice(Device device, String uri, String title, String description, String value, User user) throws SWBException {
            boolean updated = false;

            if (title != null) {
                device.setTitle(title);
            }
            if (description != null) {
                device.setDescription(description);
            }
            if (value != null) {
                device.setValue(value);
            }
            updated = true;
            //logeo
            SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", device.getURI(), device.getURI(), "update Device", null);
            try {
                swbAdmLog.create();
            } catch (Exception e) {
                throw new SWBException("Error updating device", e);
            }
            return updated;
        }
    }
}
