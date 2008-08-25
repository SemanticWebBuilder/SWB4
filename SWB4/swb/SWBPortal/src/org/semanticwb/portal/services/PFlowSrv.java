/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

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
        pflow.setCreator(user);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "create", pflow.getURI(), pflow.getURI(), "create pflow", null); 
        
        return doAction;
    }
    
    public boolean createPFlow(WebSite webSite, String id, String title, String description, User user) throws SWBException
    {
        boolean doAction=false;
        PFlow pflow=webSite.createPFlow(id);
        pflow.setTitle(title);
        pflow.setDescription(description);
        pflow.setCreator(user);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "create", pflow.getURI(), pflow.getURI(), "create pflow", null); 
        
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
        
        pflow.setModifiedBy(user);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "update", pflow.getURI(), pflow.getURI(), "update pflow", null); 
        
        return doAction;
    }
    
    public boolean removePFlow(WebSite webSite, String id, User user) throws SWBException
    {
        boolean doAction=false;
        webSite.removePFlow(id);
        doAction=true;
         
        SWBPortal.log(user.getURI(), "remove", id, id, "remove pflow", null); 
        
        return doAction;
    }
}
