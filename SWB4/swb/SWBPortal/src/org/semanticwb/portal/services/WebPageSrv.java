/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticIterator;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class WebPageSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebPage createWebPage(SemanticModel model, String uri, String title, WebPage childOf, User user) throws SWBException {
        WebPage page = SWBContext.createWebPage(model, uri);
        page.setTitle(title);
        page.setIsChildOf(childOf);
        page.setUserCreated(user);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", page.getURI(), page.getURI(), "create WebPage", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "remove", page.getURI(), page.getURI(), "remove WebPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing WebPage", e);
        }

        return true;
    }

    public boolean setStatusWebPage(WebPage page, int status, User user) throws SWBException {
        page.setStatus(status);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "status", page.getURI(), page.getURI(), "updating status", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding template to WebPage", e);
        }

        return true;
    }

    public boolean addTemplate2WebPage(SemanticModel model, WebPage page, Template template, User user) throws SWBException {

        TemplateRef templateRef = SWBContext.createTemplateRef(model);
        templateRef.setTemplate(template);

        page.addTemplateRef(templateRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", template.getURI(), template.getURI(), "Assign template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding template to WebPage", e);
        }

        return true;
    }
    
    public boolean addTemplate2WebPage(SemanticModel model, String templateRefUri, WebPage page, Template template, User user) throws SWBException {

        TemplateRef templateRef = SWBContext.createTemplateRef(model, templateRefUri);
        templateRef.setTemplate(template);

        page.addTemplateRef(templateRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", template.getURI(), template.getURI(), "Assign template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding template to WebPage", e);
        }

        return true;
    }

    public boolean addRule2WebPage(SemanticModel model, WebPage page, Rule rule, User user) throws SWBException {

        RuleRef ruleRef = SWBContext.createRuleRef(model);
        ruleRef.setRule(rule);

        page.addRuleRef(ruleRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", rule.getURI(), rule.getURI(), "Assign Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding rule to WebPage", e);
        }

        return true;
    }

    public boolean addRule2WebPage(SemanticModel model, String ruleRefUri, WebPage page, Rule rule, User user) throws SWBException {
        RuleRef ruleRef = SWBContext.createRuleRef(model, ruleRefUri);
        ruleRef.setRule(rule);

        page.addRuleRef(ruleRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", rule.getURI(), rule.getURI(), "Assign Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding rule to WebPage", e);
        }

        return true;
    }

    public boolean addRole2WebPage(SemanticModel model, WebPage page, Role role, User user) throws SWBException {

        RoleRef roleRef = SWBContext.createRoleRef(model);
        roleRef.setRole(role);

        page.addRoleRef(roleRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", role.getURI(), role.getURI(), "Assign Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding role to WebPage", e);
        }

        return true;
    }

    public boolean addRole2WebPage(SemanticModel model, String roleRefUri, WebPage page, Role role, User user) throws SWBException {
        RoleRef roleRef = SWBContext.createRoleRef(model, roleRefUri);
        roleRef.setRole(role);

        page.addRoleRef(roleRef);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", role.getURI(), role.getURI(), "Assign Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding rule to WebPage", e);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "unassign", page.getURI(), page.getURI(), "remove Ref Template from page", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "unassign", page.getURI(), page.getURI(), "remove Ref Rule from page", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "unassign", page.getURI(), page.getURI(), "remove Ref Role from page", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing role from WebPage", e);
        }

        return true;
    }
}
