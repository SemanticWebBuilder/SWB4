
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.portal.SWBDBAdmLog;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;

/**
 *
 * @author jorge.jimenez
 */
public class WebSiteSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebSite createWebSite(String name, String nsp, String title, String description, String homeTitle, String homeDescription, User user) throws SWBException {
        
        WebSite website=SWBContext.createWebSite(name, nsp);
        website.setTitle(title);
        website.setDescription(description);        
        WebPage wp=website.createWebPage(homeTitle);
        wp.setDescription(homeDescription);
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

    public boolean removeWebSite(String uri, User user) throws SWBException 
    {
        WebSite webSite=SWBContext.getWebSite(uri);
        
        //Elimina templates, TODO: Asumiendo q el sitio solo tendra grupos de templates, ESTO NO ES CORRECTO
        Iterator <ObjectGroup> itObjGroups=webSite.listObjectGroups();
        while(itObjGroups.hasNext())
        {
            ObjectGroup objGroup=itObjGroups.next();
            SWBPortal.createInstance().getTemplateSrv().removeTemplateGroup(webSite, objGroup.getURI(), user);
        }
        //Elimina reglas
        Iterator<Rule> itRules=webSite.listRules();
        while(itRules.hasNext()){
            Rule rule=itRules.next();
            SWBPortal.createInstance().getRuleSrv().removeRule(webSite, rule.getURI(), user);
        }
        //Elimina lenguages
        Iterator<Language> itLanguages=webSite.listLanguages();
        while(itLanguages.hasNext()){
            Language lang=itLanguages.next();
            SWBPortal.createInstance().getLanguageSrv().removeLanguage(webSite, lang.getURI(), user);
        }
        //Elimina Campañas
        Iterator<Camp> itCamps=webSite.listCamps();
        while(itCamps.hasNext()){
            Camp camp=itCamps.next();
            SWBPortal.createInstance().getCampSrv().removeCamp(webSite, camp.getURI(), user);
        }
        
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