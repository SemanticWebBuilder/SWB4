
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBInstance;
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

    public WebSite createWebSite(String model, String uri, String title, String homeTitle, User user) throws SWBException {
        //creación de modelo
        //SemanticModel semModel=SWBInstance.getSemanticMgr().loadDBModel("system");
        SemanticModel semModel = SWBInstance.getSemanticMgr().getSystemModel();

        WebSite website = SWBContext.createWebSite(semModel, uri);
        HomePage hpage = SWBContext.createHomePage(semModel, uri);
        hpage.setTitle(homeTitle);
        website.addHome(hpage);
        website.addUserCreated(user);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "create", website.getURI(), uri, "Create WebSite", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating WebSite", e);
        }
        return website;
    }

    public boolean removeWebSite(String uri) throws SWBException {
        SWBContext.removeObject(uri);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "remove", uri, uri, "Remove WebSite", null);
         try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error removing WebSite",e);
        }
        return true;
    }

    public boolean updateWebSite(String uri, String description, String home, String language, String title, boolean deleted, int status, String repository) throws SWBException {
        WebSite webSite = SWBContext.getWebSite(uri);

        if (title != null) {
            webSite.setTitle(title);
        }
        webSite.setDeleted(deleted);
        if (description != null) {
            webSite.setDescription(description);
        }
        webSite.setStatus(status);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "update", uri, uri, "Update WebSite", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error updating WebSite",e);
        }
        return true;
    }

    public boolean setStatusWebSite(String uri, int status) throws SWBException {
        WebSite webSite = SWBContext.getWebSite(uri);

        webSite.setStatus(status);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog("user", "status", uri, uri, "change WebSite Status", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error changing status to WebSite",e);
        }
        return true;
    }
}