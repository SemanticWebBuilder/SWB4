/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.model.Camp;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticIterator;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class WebPageSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebPage createWebPage(WebSite website, String title, WebPage childOf, User user) throws SWBException {

        WebPage wp = website.createWebPage(title);
        wp.setIsChildOf(childOf);
        //TODO: Revisar como pongo el scope y con que tags (En el metodo setProperty de WebPage)

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
        //TODO:Revisar si este metodo elimina por completo la página o solo logicamente, debería ser x completo
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

    public boolean changeStatusTemplate2WebPage(WebPage webPage, String id, int status, User user) throws SWBException {
        boolean doAction = false;
        SemanticIterator<TemplateRef> itTempRef = webPage.listTemplateRef();
        while (itTempRef.hasNext()) {
            TemplateRef tplRef = itTempRef.next();
            //TODO:Revisar si el id que me pasan es una Uri completa
            if (tplRef.getURI().equals(id)) {
                tplRef.setStatus(status);
                break;
            }   
        }
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "status", id, id, "change status TemplateRef2WebPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error changing Status TemplateRef to WebPage", e);
        }

        return doAction;
    }

    public boolean changeStatusRule2WebPage(WebPage webPage, String id, int status, User user) throws SWBException {
        boolean doAction = false;
        SemanticIterator<RuleRef> itRuleRef = webPage.listRuleRef();
        while (itRuleRef.hasNext()) {
            RuleRef ruleRef = itRuleRef.next();
            //TODO:Revisar si el id que me pasan es una Uri completa
            if (ruleRef.getURI().equals(id)) {
                ruleRef.setStatus(status);
                break;
            }
        }
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "status", id, id, "change status RuleRef2WebPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error changing Status RuleRef to WebPage", e);
        }

        return doAction;
    }
    
    
    public boolean changeStatusRole2WebPage(WebPage webPage, String id, int status, User user) throws SWBException {
        boolean doAction = false;
        SemanticIterator<RoleRef> itRoleRef = webPage.listRoleRef();
        while (itRoleRef.hasNext()) {
            RoleRef roleRef = itRoleRef.next();
            //TODO:Revisar si el id que me pasan es una Uri completa
            if (roleRef.getURI().equals(id)) {
                roleRef.setStatus(status);
                break;
            }
        }
        doAction = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "status", id, id, "change status RoleRef2WebPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error changing Status RoleRef to WebPage", e);
        }

        return doAction;
    }
    
    //TODO
    public boolean addFlow2WebPage(WebSite website, WebPage webpage, PFlow pflow, User user)
    {
        return true;
    }
    
    //TODO
    public boolean removeFlow2WebPage(WebSite website, WebPage webpage, PFlow pflow, User user)
    {
        return true;
    }
    
    //TODO
    public boolean addCamp2WebPage(WebSite website, WebPage webpage, Camp camp, User user)
    {
        return true;
    }
    
    //TODO
    public boolean removeCamp2WebPage(WebSite website, WebPage webpage, Camp camp, User user)
    {
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
