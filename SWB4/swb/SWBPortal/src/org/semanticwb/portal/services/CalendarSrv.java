/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Calendar;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class CalendarSrv {
    
    public boolean createCalendar(WebSite website, String title, String description, User user) throws SWBException
    {
        boolean doAction = false;
        Calendar cal=website.createCalendar();
        cal.setTitle(title);
        cal.setDescription(description);
        cal.setUserCreated(user);
        doAction=true;
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", cal.getName(), cal.getURI(), "create Calendar", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Calendar", e);
        }
        return doAction;
    }
    
    
    public boolean createCalendar(WebSite website, String id, String title, String description, User user) throws SWBException
    {
        boolean doAction = false;
        Calendar cal=website.createCalendar(id);
        cal.setTitle(title);
        cal.setDescription(description);
        cal.setUserCreated(user);
        doAction=true;
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", cal.getName(), cal.getURI(), "create Calendar", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Calendar", e);
        }
        return doAction;
    }
    
    public boolean removeCalendar(WebSite website, String id, User user) throws SWBException
    {
        boolean doAction = false;
        website.removeCalendar(id);
        doAction=true;
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove Calendar", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing Calendar", e);
        }
        return doAction;
    }
}
