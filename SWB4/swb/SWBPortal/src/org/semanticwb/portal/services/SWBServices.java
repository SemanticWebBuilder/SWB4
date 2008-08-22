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

        SWBPortal.createInstance().log(user.getURI(), "delete", deleteable.toString(), deleteable.toString(), "delete", null); 
        
        return doAction;
    }

    public boolean addCalendar(Calendarable calendarable, Calendar calendar, User user) throws SWBException {
        boolean doAction = false;
        calendarable.addCalendar(calendar);
        doAction = true;

       SWBPortal.createInstance().log(user.getURI(), "assign", calendarable.toString(), calendar.getURI(), "assign", null); 
       
        return doAction;
    }

    public boolean removeCalendar(Calendarable calendarable, Calendar calendar, User user) throws SWBException {
        boolean doAction = false;
        calendarable.removeCalendar(calendar);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "unAssign", calendarable.toString(), calendar.getURI(), "unAssign", null); 
        
        return doAction;
    }

    public static boolean setStatus(Statusable statusable, int status, User user) throws SWBException {

        statusable.setStatus(status);

        SWBPortal.createInstance().log(user.getURI(), "status", statusable.toString(), statusable.toString(), "update status", null); 
       
        return true;
    }

    public boolean addTemplate(WebSite webSite, TemplateRefable templateRefable, Template template, User user) throws SWBException {
        boolean doAction = false;
        TemplateRef templateRef = webSite.createTemplateRef();
        templateRef.setTemplate(template);
        templateRef.setStatus(1);

        templateRefable.addTemplateRef(templateRef);
        doAction = true;
       
        SWBPortal.createInstance().log(user.getURI(), "assing", templateRefable.toString(), template.getURI(), "assing template", null); 

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

        SWBPortal.createInstance().log(user.getURI(), "UnAssing", templateRefable.toString(), template.getURI(), "UnAssing template", null); 

        return doAction;
    }

    public boolean addRuleRef(WebSite webSite, RuleRefable ruleRefable, Rule rule, User user) throws SWBException {
        boolean doAction = false;
        RuleRef ruleRef = webSite.createRuleRef();
        ruleRef.setRule(rule);

        ruleRefable.addRuleRef(ruleRef);
        doAction = true;
       
        SWBPortal.createInstance().log(user.getURI(), "assing", ruleRefable.toString(), rule.getURI(), "assing rule", null); 
        
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

        SWBPortal.createInstance().log(user.getURI(), "unAssing", ruleRefable.toString(), rule.getURI(), "UnAssing rule", null); 

        return doAction;
    }

    public boolean addRoleRef(WebSite webSite, RoleRefable roleRefable, Role role, User user) throws SWBException {
        boolean doAction = false;
        RoleRef roleRef = webSite.createRoleRef();
        roleRef.setRole(role);

        roleRefable.addRoleRef(roleRef);
        doAction = true;
        
        SWBPortal.createInstance().log(user.getURI(), "assing", roleRefable.toString(), role.getURI(), "assing role", null); 
        
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
       
        SWBPortal.createInstance().log(user.getURI(), "unAssing", roleRefable.toString(), role.getURI(), "unAssing role", null); 
        
        return doAction;
    }

    public boolean addDescription(Descriptiveable descriptiveable, String description, User user) throws SWBException {
        boolean doAction = false;
        descriptiveable.setDescription(description);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assing", descriptiveable.toString(), description.toString(), "assing description", null); 
        
        return doAction;
    }

    public boolean groupable(Groupable groupable, ObjectGroup objectgroup, User user) throws SWBException {
        boolean doAction = false;
        groupable.addGroup(objectgroup);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assing", groupable.toString(), objectgroup.getURI(), "assing group", null); 
       
        return doAction;
    }

    public boolean localeable(Localeable localeable, Language language, User user) throws SWBException 
    {
        boolean doAction = false;
        localeable.setLanguage(language);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assing", localeable.toString(), language.getURI(), "assing language", null); 
        
        return doAction;
    }

    public boolean createLanguage(WebSite webSite, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        Language language = webSite.createLanguage();
        language.setTitle(title);
        language.setDescription(description);
        language.setCreator(user);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "create", language.toString(), language.getURI(), "create language", null); 
        
        return doAction;
    }

    public boolean priorityable(Priorityable priorityable, int priority, User user) throws SWBException {
        boolean doAction = false;
        priorityable.setPriority(priority);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assign", priorityable.toString(), priorityable.toString(), "assign priority", null); 
        
        return doAction;
    }

    public boolean setValue(Valueable valueable, String value, User user) throws SWBException {
        boolean doAction = false;
        valueable.setValue(value);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assign", valueable.toString(), valueable.toString(), "assign value", null); 
       
        return doAction;
    }

    public boolean setVersion(Versionable versionable, VersionInfo versionInfo, User user) throws SWBException {
        boolean doAction = false;
        versionable.setActualVersion(versionInfo);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assign", versionable.toString(), versionable.toString(), "assign version", null); 
        
        return doAction;
    }

    public boolean removeVersion(Versionable versionable, User user) throws SWBException {
        boolean doAction = false;
        versionable.removeActualVersion();
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "unAssign", versionable.toString(), versionable.toString(), "unAssign version", null); 
        
        return doAction;
    }

    public boolean addWebPage(WebPageable webPageable, WebPage webPage, User user) throws SWBException {
        boolean doAction = false;
        webPageable.addWebPage(webPage);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "assign", webPageable.toString(), webPage.getURI(), "assign webpage", null); 
       
        return doAction;
    }

    public boolean removeWebPage(WebPageable webPageable, WebPage webPage, User user) throws SWBException {
        boolean doAction = false;
        webPageable.removeWebPage(webPage);
        doAction = true;

        SWBPortal.createInstance().log(user.getURI(), "unAssign", webPageable.toString(), webPage.getURI(), "unAssign webpage", null); 
        
        return doAction;
    }
}
