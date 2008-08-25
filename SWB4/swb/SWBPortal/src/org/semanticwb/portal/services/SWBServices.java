/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Calendar;
import org.semanticwb.model.Calendarable;
import org.semanticwb.model.Deleteable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Groupable;
import org.semanticwb.model.Language;
import org.semanticwb.model.Localeable;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.Priorityable;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.RoleRefable;
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.RuleRefable;
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
import org.semanticwb.model.WebSite;

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

        SWBPortal.log(user.getURI(), "delete", deleteable.getURI(), deleteable.getId(), "delete", null); 
        
        return doAction;
    }

    public boolean addCalendar(Calendarable calendarable, Calendar calendar, User user) throws SWBException {
        boolean doAction = false;
        calendarable.addCalendar(calendar);
        doAction = true;

       SWBPortal.log(user.getURI(), "assign", calendar.getWebSite().getURI(), calendar.getId(), "assign", null); 
       
        return doAction;
    }

    public boolean removeCalendar(Calendarable calendarable, Calendar calendar, User user) throws SWBException {
        boolean doAction = false;
        calendarable.removeCalendar(calendar);
        doAction = true;

        SWBPortal.log(user.getURI(), "unAssign", calendar.getWebSite().getId(), calendar.getId(), "unAssign", null); 
        
        return doAction;
    }

    public static boolean setStatus(Statusable statusable, int status, User user) throws SWBException {

        statusable.setStatus(status);

        SWBPortal.log(user.getURI(), "status", statusable.getURI(), statusable.getId(), "update status", null); 
       
        return true;
    }

    public boolean addTemplate(TemplateRefable templateRefable, Template template, User user) throws SWBException {
        WebSite website=template.getWebSite();
        boolean doAction = false;
        TemplateRef templateRef = website.createTemplateRef();
        templateRef.setTemplate(template);
        templateRef.setStatus(1);

        templateRefable.addTemplateRef(templateRef);
        doAction = true;
       
        SWBPortal.log(user.getURI(), "assing", website.getURI(), template.getId(), "assing template", null); 

        return doAction;
    }

    public boolean removeTemplateRef(TemplateRefable templateRefable, Template template, User user) throws SWBException {
        boolean doAction = false;
        GenericIterator itTemplateRef = templateRefable.listTemplateRef();
        while (itTemplateRef.hasNext()) {
            TemplateRef tempRef = (TemplateRef) itTemplateRef.next();
            if (tempRef.getTemplate().getURI().equals(template.getURI())) {
                templateRefable.removeTemplateRef(tempRef);
                doAction = true;
                break;
            }
        }

        SWBPortal.log(user.getURI(), "UnAssing", templateRefable.toString(), template.getURI(), "UnAssing template", null); 

        return doAction;
    }

    public boolean addRuleRef(RuleRefable ruleRefable, Rule rule, User user) throws SWBException {
        WebSite website=rule.getWebSite();
        boolean doAction = false;
        RuleRef ruleRef = website.createRuleRef();
        ruleRef.setRule(rule);

        ruleRefable.addRuleRef(ruleRef);
        doAction = true;
       
        SWBPortal.log(user.getURI(), "assing", website.getURI(), rule.getId(), "assing rule", null); 
        
        return doAction;
    }

    public boolean removeRuleRef(RuleRefable ruleRefable, Rule rule, User user) throws SWBException {
        boolean doAction = false;
        GenericIterator itRuleRef = ruleRefable.listRuleRef();
        while (itRuleRef.hasNext()) {
            RuleRef ruleRef = (RuleRef) itRuleRef.next();
            if (ruleRef.getRule().getURI().equals(rule.getURI())) {
                ruleRefable.removeRuleRef(ruleRef);
                doAction = true;
                break;
            }
        }

        SWBPortal.log(user.getURI(), "unAssing", rule.getWebSite().getURI(), rule.getId(), "UnAssing rule", null); 

        return doAction;
    }

    public boolean addRoleRef(WebSite website, RoleRefable roleRefable, Role role, User user) throws SWBException {
        boolean doAction = false;
        RoleRef roleRef = website.createRoleRef();
        roleRef.setRole(role);

        roleRefable.addRoleRef(roleRef);
        doAction = true;
        
        SWBPortal.log(user.getURI(), "assing", website.getURI(), role.getId(), "assing role", null); 
        
        return doAction;
    }

    public boolean removeRoleRef(RoleRefable roleRefable, Role role, User user) throws SWBException {
        boolean doAction = false;
        GenericIterator itRoleRef = roleRefable.listRoleRef();
        while (itRoleRef.hasNext()) {
            RoleRef roleRef = (RoleRef) itRoleRef.next();
            if (roleRef.getRole().getURI().equals(role.getURI())) {
                roleRefable.removeRoleRef(roleRef);
                doAction = true;
                break;
            }
        }
       
        SWBPortal.log(user.getURI(), "unAssing", role.getURI(), role.getId(), "unAssing role", null); 
        
        return doAction;
    }

    public boolean addDescription(Descriptiveable descriptiveable, String description, User user) throws SWBException {
        boolean doAction = false;
        descriptiveable.setDescription(description);
        doAction = true;

        SWBPortal.log(user.getURI(), "assing", descriptiveable.getURI(), descriptiveable.getId(), "assing description", null); 
        
        return doAction;
    }

    public boolean groupable(Groupable groupable, ObjectGroup objectgroup, User user) throws SWBException {
        boolean doAction = false;
        groupable.addGroup(objectgroup);
        doAction = true;

        SWBPortal.log(user.getURI(), "assing", objectgroup.getWebSite().getURI(), objectgroup.getId(), "assing group", null); 
       
        return doAction;
    }

    public boolean localeable(Localeable localeable, Language language, User user) throws SWBException 
    {
        boolean doAction = false;
        localeable.setLanguage(language);
        doAction = true;

        SWBPortal.log(user.getURI(), "assing", localeable.getURI(), language.getId(), "assing language", null); 
        
        return doAction;
    }

    public boolean createLanguage(WebSite webSite, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        Language language = webSite.createLanguage();
        language.setTitle(title);
        language.setDescription(description);
        language.setCreator(user);
        doAction = true;

        SWBPortal.log(user.getURI(), "create", webSite.getURI(), language.getId(), "create language", null); 
        
        return doAction;
    }

    public boolean priorityable(Priorityable priorityable, int priority, User user) throws SWBException {
        boolean doAction = false;
        priorityable.setPriority(priority);
        doAction = true;

        SWBPortal.log(user.getURI(), "assign", priorityable.getURI(), priorityable.getId(), "assign priority", null); 
        
        return doAction;
    }

    public boolean setValue(Valueable valueable, String value, User user) throws SWBException {
        boolean doAction = false;
        valueable.setValue(value);
        doAction = true;

        SWBPortal.log(user.getURI(), "assign", valueable.getURI(), valueable.getId(), "assign value", null); 
       
        return doAction;
    }

    public boolean setVersion(Versionable versionable, VersionInfo versionInfo, User user) throws SWBException {
        boolean doAction = false;
        versionable.setActualVersion(versionInfo);
        doAction = true;

        SWBPortal.log(user.getURI(), "assign", versionable.getURI(), versionable.getId(), "assign version", null); 
        
        return doAction;
    }

    public boolean removeVersion(Versionable versionable, User user) throws SWBException 
    {
        versionable.removeActualVersion();
        SWBPortal.log(user.getURI(), "unAssign", versionable.getURI(), versionable.getId(), "unAssign version", null); 
        
        return true;
    }

    public boolean addWebPage(WebPageable webPageable, WebPage webPage, User user) throws SWBException {
        boolean doAction = false;
        webPageable.addWebPage(webPage);
        doAction = true;

        SWBPortal.log(user.getURI(), "assign", webPageable.getURI(), webPage.getId(), "assign webpage", null); 
       
        return doAction;
    }

    public boolean removeWebPage(WebPageable webPageable, WebPage webPage, User user) throws SWBException {
        boolean doAction = false;
        webPageable.removeWebPage(webPage);
        doAction = true;

        SWBPortal.log(user.getURI(), "unAssign", webPageable.getURI(), webPage.getId(), "unAssign webpage", null); 
        
        return doAction;
    }
}
