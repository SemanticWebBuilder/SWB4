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
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class WebPageSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebPage createWebPage(String uri, String title, WebPage childOf, User user) throws SWBException
    {
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(uri);
        WebPage page = SWBContext.createWebPage(model, uri);
        page.setTitle(title);
        page.addIsChildOf(childOf);
        page.addUserCreated(user);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "cretae", uri, uri, "create WebPage", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding template to WebPage",e);
        }

        return page;
    }

    public boolean removeWebPage(String uri, User user) throws SWBException
    {
        SWBContext.removeObject(uri);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "cretae", uri, uri, "create WebPage", null);
         try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding template to WebPage",e);
        }
        
        return true;        
    }
    
    public boolean setStatusWebPage(String uri, int status, User user) throws SWBException
    {
        WebPage page=SWBContext.getWebPage(uri);
        page.setStatus(status);
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create WebPage", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding template to WebPage",e);
        }
        
        return true;        
    }
    
    public boolean addTemplate2WebPage(String uri,String templateUri,User user) throws SWBException
    {
        Template template=SWBContext.getTemplate(templateUri);
        TemplateRef templateRef=SWBContext.getTemplateRef(templateUri);
        WebPage WebPage=SWBContext.getWebPage(uri);
        WebPage.addTemplateRef(templateRef);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", uri, uri, "Assign Template", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding template to WebPage",e);
        }
        
        return true;
        
    }
    
    public boolean addRule2WebPage(String uri,String ruleUri,User user) throws SWBException
    {
        Rule rule=SWBContext.getRule(ruleUri);
        RuleRef ruleRef=SWBContext.getRuleRef(ruleUri);
        WebPage WebPage=SWBContext.getWebPage(uri);
        WebPage.addRuleRef(ruleRef);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", uri, uri, "Assign Template", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding rule to WebPage",e);
        }
        
        return true;
    }
    
    public boolean addRole2WebPage(String uri,String roleUri,User user) throws SWBException
    {
        Role role=SWBContext.getRole(roleUri);
        RoleRef roleRef=SWBContext.getRoleRef(roleUri);
        WebPage WebPage=SWBContext.getWebPage(uri);
        WebPage.addRoleRef(roleRef);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "assign", uri, uri, "Assign Role", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding role to WebPage",e);
        }
        
        return true;
    }
   
    
    public boolean removeRuleFromWebPage(String uri,String ruleUri,User user) throws SWBException
    {
        Rule rule=SWBContext.getRule(ruleUri);
        RuleRef ruleRef=SWBContext.getRuleRef(ruleUri);
        WebPage WebPage=SWBContext.getWebPage(uri);
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "unassign", uri, uri, "Assign Template", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error adding rule to WebPage",e);
        }
        
        return true;
    }
    
}
