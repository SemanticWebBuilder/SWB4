/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Calendar;
import org.semanticwb.model.Calendarable;
import org.semanticwb.model.Deleteable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.Groupable;
import org.semanticwb.model.Language;
import org.semanticwb.model.Localeable;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.Priorityable;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.RoleRefable;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.RuleRefable;
import org.semanticwb.model.Statusable;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.TemplateRefable;
import org.semanticwb.model.User;
import org.semanticwb.model.Valueable;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebPageable;
import org.semanticwb.portal.SWBDBAdmLog;

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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "delete", deleteable.toString(), deleteable.toString(), "delete", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", calendarable.toString(), calendarable.toString(), "assign calendar", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "Unassign", calendarable.toString(), calendarable.toString(), "unassign calendar", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "status", statusable.toString(), statusable.toString(), "update Status", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", templateRefable.toString(), templateRefable.toString(), "Assign template", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "UnAssign", templateRefable.toString(), templateRefable.toString(), "UnAssign template", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", ruleRefable.toString(), ruleRefable.toString(), "assign rule", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "Unassign", ruleRefable.toString(), ruleRefable.toString(), "unassign rule", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", roleRefable.toString(), roleRefable.toString(), "assign role", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "Unassign", roleRefable.toString(), roleRefable.toString(), "assign role", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", descriptiveable.toString(), descriptiveable.toString(), "assing description", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", groupable.toString(), groupable.toString(), "assing description", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", localeable.toString(), localeable.toString(), "assing language", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", priorityable.toString(), priorityable.toString(), "assing priority", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", valueable.toString(), valueable.toString(), "assing value", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", versionable.toString(), versionable.toString(), "assing versionInfo", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "UnAssign", versionable.toString(), versionable.toString(), "UnAssing versionInfo", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "assign", webPageable.toString(), webPageable.toString(), "assing webPage", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "unAssign", webPageable.toString(), webPageable.toString(), "UnAssing webPage", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error UnAssining webPage", e);
        }
        return doAction;
    } 
  
}
