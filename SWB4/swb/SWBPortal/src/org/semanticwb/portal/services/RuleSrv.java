/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Rule;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class RuleSrv {

    public Rule createRule(WebSite website, String title, String description, User user) throws SWBException {
        Rule rule = website.createRule();
        rule.setTitle(title);
        rule.setDescription(description);

        SWBPortal.createInstance().log(user.getURI(), "create", rule.getURI(), rule.getURI(), "create rule", null); 
        
        return rule;

    }

    public Rule createRule(WebSite website, String id, String title, String description, User user) throws SWBException {
        Rule rule = website.createRule(id);
        rule.setTitle(title);
        rule.setDescription(description);

        SWBPortal.createInstance().log(user.getURI(), "create", rule.getURI(), rule.getURI(), "create rule", null); 
        
        return rule;

    }

    public boolean removeRule(WebSite website, String id, User user) throws SWBException {
        boolean deleted = false;
        website.removeRule(id);
        deleted = true;

       SWBPortal.createInstance().log(user.getURI(), "remove", id, id, "remove rule", null); 
       
        return deleted;

    }
}
