/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class PFlowSrv 
{
    
    public boolean createPFlow(WebSite webSite, String title, String description, User user) throws SWBException
    {
        boolean doAction=false;
        PFlow pflow=webSite.createPFlow();
        pflow.setTitle(title);
        pflow.setDescription(description);
        pflow.setUserCreated(user);
        doAction=true;
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", pflow.getTitle(), pflow.getURI(), "create PFlow", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating PFlow", e);
        }
        return doAction;
    }
    
    public boolean createPFlow(WebSite webSite, String id, String title, String description, User user) throws SWBException
    {
        boolean doAction=false;
        PFlow pflow=webSite.createPFlow(id);
        pflow.setTitle(title);
        pflow.setDescription(description);
        pflow.setUserCreated(user);
        doAction=true;
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", pflow.getTitle(), pflow.getURI(), "create PFlow", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating PFlow", e);
        }
        return doAction;
    }
    
    public boolean updatePFlow(WebSite webSite, String id, String title, String description, User user) throws SWBException
    {
        boolean doAction=false;
        PFlow pflow=webSite.getPFlow(id);
        if(title!=null){
            pflow.setTitle(title);
        }
        if(description!=null){
            pflow.setDescription(description);
        }
        
        pflow.setUserModified(user);
        doAction=true;
        
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", pflow.getTitle(), pflow.getURI(), "update PFlow", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updatating PFlow", e);
        }
        return doAction;
    }
    
    public boolean removePFlow(WebSite webSite, String id, User user) throws SWBException
    {
        boolean doAction=false;
        webSite.removePFlow(id);
        doAction=true;
         //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove PFlow", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing PFlow", e);
        }
        return doAction;
    }
}
