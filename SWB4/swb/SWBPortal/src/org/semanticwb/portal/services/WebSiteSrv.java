
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.model.*;
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

    public WebSite createWebSite(String name, String nsp, String homeTitle, User user) throws SWBException {
        
        WebSite website=SWBContext.createWebSite(name, nsp);
        WebPage wp=website.createWebPage(homeTitle);
        website.setHomePage(wp);
        
        website.setUserCreated(user);
       

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", website.getURI(), website.getURI(), "Create WebSite", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating WebSite", e);
        }
        return website;
    }

    public boolean removeWebSite(String uri, User user) throws SWBException {
        SWBContext.removeWebSite(uri);
        
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", uri, uri, "Remove WebSite", null);
         try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error removing WebSite",e);
        }
        return true;
    }

    public boolean updateWebSite(WebSite webSite, String title, String description, User user) throws SWBException {
        
        if (title != null) {
            webSite.setTitle(title);
        }
        if (description != null) {
            webSite.setDescription(description);
        }
        webSite.setUserModified(user);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", webSite.getName(), webSite.getName(), "Update WebSite", null);
        try{
            swbAdmLog.create();
        }catch(Exception e){
             throw new SWBException("Error updating WebSite",e);
        }
        return true;
    }

}