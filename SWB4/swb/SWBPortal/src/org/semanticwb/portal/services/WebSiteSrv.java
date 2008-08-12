
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;

/**
 *
 * @author jorge.jimenez
 */
public class WebSiteSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebSite createWebSite(SemanticModel model, String siteUri, String homeUri, String title, String homeTitle, User user) throws SWBException {
        //creación de modelo
        //SemanticModel model=SWBInstance.getSemanticMgr().loadDBModel("system");
        
        WebSite website = SWBContext.createWebSite(model, siteUri);
        HomePage hpage = SWBContext.createHomePage(model, homeUri);
        hpage.setTitle(homeTitle);
        website.addHome(hpage);
        
        website.setUserCreated(user);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "create", website.getURI(), website.getURI(), "Create WebSite", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating WebSite", e);
        }
        return website;
    }

    public boolean removeWebSite(WebSite website) throws SWBException {
        SWBContext.removeObject(website.getURI());

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "remove", website.getURI(), website.getURI(), "Remove WebSite", null);
         try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error removing WebSite",e);
        }
        return true;
    }

    public boolean updateWebSite(WebSite webSite, String description, String home, String language, String title, boolean deleted, int status, String repository) throws SWBException {
        
        if (title != null) {
            webSite.setTitle(title);
        }
        webSite.setDeleted(deleted);
        if (description != null) {
            webSite.setDescription(description);
        }
        webSite.setStatus(status);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "update", webSite.getURI(), webSite.getURI(), "Update WebSite", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error updating WebSite",e);
        }
        return true;
    }

    public boolean setStatusWebSite(WebSite webSite, int status) throws SWBException {
        
        webSite.setStatus(status);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "status", webSite.getURI(), webSite.getURI(), "update WebSite Status", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error updating status to WebSite",e);
        }
        return true;
    }
}