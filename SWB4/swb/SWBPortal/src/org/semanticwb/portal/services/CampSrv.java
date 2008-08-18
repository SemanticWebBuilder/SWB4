/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Camp;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class CampSrv {
    
    public boolean createCamp(WebSite website, String title, String description, User user) throws SWBException        
    {
        boolean doAction=false;
        Camp camp=website.createCamp();
        camp.setTitle(title);
        camp.setDescription(description);
        camp.setUserCreated(user);
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", camp.getName(), camp.getURI(), "create Camp", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating camp", e);
        }
        return doAction;
    }
    
    public boolean createCamp(WebSite website, String id, String title, String description, User user) throws SWBException        
    {
        boolean doAction=false;
        Camp camp=website.createCamp(id);
        camp.setTitle(title);
        camp.setDescription(description);
        camp.setUserCreated(user);
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", camp.getName(), camp.getURI(), "create Camp", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating camp", e);
        }
        return doAction;
    }
    
    public boolean updateCamp(Camp camp, String title, String description, User user) throws SWBException
    {
        boolean doAction=false;
        if(title!=null){
            camp.setTitle(title);
        }
        if(description!=null){
            camp.setDescription(description);
        }
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", camp.getTitle(), camp.getURI(), "update Camp", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating camp", e);
        }
        return doAction;
    }
    
    public boolean removeCamp(WebSite website, String id, User user) throws SWBException        
    {
        boolean doAction=false;
        website.removeCamp(id);
        doAction=true;
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove Camp", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing camp", e);
        }
        return doAction;
    } 

}
