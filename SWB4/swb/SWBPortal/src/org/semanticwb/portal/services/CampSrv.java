/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Camp;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class CampSrv {
    
    public Camp createCamp(WebSite website, String title, String description, User user) throws SWBException        
    {
        Camp camp=website.createCamp();
        camp.setTitle(title);
        camp.setDescription(description);
        camp.setUserCreated(user);
        
        SWBPortal.createInstance().log(user.getURI(), "create", camp.getURI(), camp.getURI(), "create Camp", null);
        
        return camp;
    }
    
    public Camp createCamp(WebSite website, String id, String title, String description, User user) throws SWBException        
    {
        Camp camp=website.createCamp(id);
        camp.setTitle(title);
        camp.setDescription(description);
        camp.setUserCreated(user);
        
        SWBPortal.createInstance().log(user.getURI(), "create", camp.getURI(), camp.getURI(), "create Camp", null);
         
        return camp;
    }
    
    public boolean updateCamp(Camp camp, String title, String description, int status, User user) throws SWBException
    {
        boolean doAction=false;
        if(title!=null){
            camp.setTitle(title);
        }
        if(description!=null){
            camp.setDescription(description);
        }
        
        camp.setStatus(status);
        
        doAction=true;
        
        SWBPortal.createInstance().log(user.getURI(), "update", camp.getURI(), camp.getURI(), "update Camp", null);
       
        return doAction;
    }
    
    public boolean removeCamp(WebSite website, String id, User user) throws SWBException        
    {
        boolean doAction=false;
        website.removeCamp(id);
        doAction=true;
        
        SWBPortal.createInstance().log(user.getURI(), "update", id, id, "update Camp", null);
        
        return doAction;
    } 

}
