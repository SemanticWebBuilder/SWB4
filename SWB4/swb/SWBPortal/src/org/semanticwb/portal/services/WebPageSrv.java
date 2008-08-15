/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class WebPageSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebPage createWebPage(WebSite website, String title, WebPage childOf, User user) throws SWBException {
        
        WebPage wp=website.createWebPage(title);
        wp.setIsChildOf(childOf);
        wp.setUserCreated(user);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", wp.getName(), wp.getURI(), "create WebPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating WebPage", e);
        }

        return wp;
    }

    public boolean removeWebPage(WebSite website, String id, User user) throws SWBException {
        website.removeWebPage(id);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove WebPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing WebPage", e);
        }

        return true;
    }
/*
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
 * */
    
    
}
