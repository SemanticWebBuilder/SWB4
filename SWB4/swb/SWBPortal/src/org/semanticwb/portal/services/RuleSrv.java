/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
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

    public Rule createRule(String modelUri, String uri, String title, String description, User user) throws SWBException
    {
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        Rule rule = SWBContext.createRule(model, uri);
        rule.setTitle(title);
        rule.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating rule", e);
        }
        return rule;

    }
    
    public boolean removeRule(String uri, User user) throws SWBException
    {
        boolean deleted=false;
        SWBContext.removeObject(uri);
        deleted=true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "remove Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing rule", e);
        }
        return deleted;

    }
}
