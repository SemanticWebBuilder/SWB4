/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
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

        SWBPortal.log(user.getURI(), "create", role.getURI(), role.getURI(), "create role", null); 
       
        return role;

    }
    
    public Role createRole(UserRepository userRep, String id,  String title, String description, User user) throws SWBException
    {
        Role role=userRep.createRole(id);
        role.setTitle(title);
        role.setDescription(description);

       SWBPortal.log(user.getURI(), "create", role.getURI(), role.getURI(), "create role", null); 
       
        return role;

    }
    
    public boolean removeRole(UserRepository userRep, String id, User user) throws SWBException
    {
        boolean deleted=false;
        userRep.removeRole(id);
        deleted=true;

       SWBPortal.log(user.getURI(), "remove", id, id, "remove role", null); 
        
        return deleted;

    }
    
}
