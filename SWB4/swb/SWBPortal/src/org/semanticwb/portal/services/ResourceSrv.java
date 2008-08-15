/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.ContentPortlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class ResourceSrv {

    public boolean createContentPortlet(WebSite website, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        ContentPortlet cPortlet = website.createContentPortlet();
        cPortlet.setTitle(title);
        cPortlet.setDescription(description);
        cPortlet.setUserCreated(user);
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", cPortlet.getName(), cPortlet.getURI(), "create language", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating language", e);
        }
        return doAction;
    }

    public boolean createContentPortlet(WebSite website, String id, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        ContentPortlet cPortlet = website.createContentPortlet(id);
        cPortlet.setTitle(title);
        cPortlet.setDescription(description);
        cPortlet.setUserCreated(user);
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", cPortlet.getName(), cPortlet.getURI(), "create contentPortlet", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating language", e);
        }
        return doAction;
    }
    
    public boolean removeContentPortlet(WebSite website, String id, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        website.removeContentPortlet(id);
        
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", id, id, "remove contentPortlet", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing contentPortlet", e);
        }
        return doAction;
    }
    
    
}
