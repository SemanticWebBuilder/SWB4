/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Rule;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class RuleSrv {
    
    public Rule createRule(SemanticModel model, String title, String description, User user) throws SWBException
    {
        Rule rule = SWBContext.createRule(model);
        rule.setTitle(title);
        rule.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", rule.getURI(), rule.getURI(), "create Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating rule", e);
        }
        return rule;

    }

    public Rule createRule(SemanticModel model, String ruleUri, String title, String description, User user) throws SWBException
    {
        Rule rule = SWBContext.createRule(model, ruleUri);
        rule.setTitle(title);
        rule.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", rule.getURI(), rule.getURI(), "create Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating rule", e);
        }
        return rule;

    }
    
    public boolean removeRule(Rule rule, User user) throws SWBException
    {
        boolean deleted=false;
        SWBContext.removeObject(rule.getURI());
        deleted=true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", rule.getURI(), rule.getURI(), "remove Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing rule", e);
        }
        return deleted;

    }
}
