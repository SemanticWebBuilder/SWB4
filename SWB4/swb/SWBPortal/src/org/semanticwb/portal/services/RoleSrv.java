/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;
/**
 *
 * @author jorge.jimenez
 */
public class RoleSrv {
    public Role createRule(String modelUri, String uri, String title, String description, User user) throws SWBException
    {
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        Role role = SWBContext.createRole(model, uri);
        role.setTitle(title);
        role.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating role", e);
        }
        return role;

    }
    
    public boolean removeRole(String uri, User user) throws SWBException
    {
        boolean deleted=false;
        SWBContext.removeObject(uri);
        deleted=true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "remove Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing role", e);
        }
        return deleted;

    }
}
