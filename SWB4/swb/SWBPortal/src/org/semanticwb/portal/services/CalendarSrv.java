/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Calendar;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class CalendarSrv 
{
    private static Logger log = SWBUtils.getLogger(SWBPortal.class);
    
    public boolean createCalendar(WebSite website, String title, String description, String xmlInterval, User user) throws SWBException
    {
        boolean doAction = false;
        Calendar cal=website.createCalendar();
        cal.setTitle(title);
        cal.setDescription(description);
        cal.setCreator(user);
        
        SWBPortal.log(user.getURI(), "Create", website.getURI(), cal.getURI(), "Create Calendar", null);
        
        doAction=true;
        
        return doAction;
    }
    
    
    public boolean createCalendar(WebSite website, String id, String title, String description, String xmlInterval, User user) throws SWBException
    {
        boolean doAction = false;
        Calendar cal=website.createCalendar(id);
        cal.setTitle(title);
        cal.setDescription(description);
        cal.setCreator(user);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "Create", cal.getURI(), cal.getURI(), "Create Calendar", null);
        
        return doAction;
    }
    
    public boolean removeCalendar(WebSite website, String id, User user) throws SWBException
    {
        boolean doAction = false;
        website.removeCalendar(id);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "remove", id, id, "remove Calendar", null); 
        
        return doAction;
    }
    
    public boolean updateCalendar(WebSite website, String id, String title, String description, String xmlInterval, User user) throws SWBException
    {
        boolean doAction = false;
        Calendar cal=website.createCalendar(id);
        if(title!=null){
            cal.setTitle(title);
        }
        if(description!=null){
            cal.setDescription(description);
        }
        cal.setModifiedBy(user);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "update", cal.getURI(), cal.getURI(), "update Calendar", null);
        
        return doAction;
    }
    
}
