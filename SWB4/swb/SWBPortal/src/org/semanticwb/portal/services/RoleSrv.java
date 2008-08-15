/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.SWBDBAdmLog;
/**
 *
 * @author jorge.jimenez
 */
public class RoleSrv {
    
    public Role createRole(UserRepository userRep, String title, String description, User user) throws SWBException
    {
        Role role=userRep.createRole();
        role.setTitle(title);
        role.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", role.getName(), role.getURI(), "create Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating role", e);
        }
        return role;

    }
    
    public Role createRole(UserRepository userRep, String id,  String title, String description, User user) throws SWBException
    {
        Role role=userRep.createRole(id);
        role.setTitle(title);
        role.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", role.getName(), role.getURI(), "create Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating role", e);
        }
        return role;

    }
    
    public boolean removeRole(UserRepository userRep, String id, User user) throws SWBException
    {
        boolean deleted=false;
        userRep.removeRole(id);
        deleted=true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", id, id, "remove Role", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing role", e);
        }
        return deleted;

    }
    
}
