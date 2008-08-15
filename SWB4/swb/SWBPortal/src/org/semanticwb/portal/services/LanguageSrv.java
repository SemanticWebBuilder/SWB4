/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Language;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class LanguageSrv {

    public boolean createLanguage(WebSite website, String title, String description, String value, User user) throws SWBException        
    {
        boolean doAction=false;
        Language language=website.createLanguage();
        language.setUserCreated(user);
        language.setTitle(title);
        language.setValue(value);
        language.setDescription(description);
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", language.getName(), language.getURI(), "create language", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating language", e);
        }
        return doAction;
    }
    
    public boolean createLanguage(WebSite website, String id, String title, String description,String value, User user) throws SWBException        
    {
        boolean doAction=false;
        Language language=website.createLanguage(id);
        language.setUserCreated(user);
        language.setTitle(title);
        language.setValue(value);
        language.setDescription(description);
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", language.getName(), language.getURI(), "create language", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating language", e);
        }
        return doAction;
    }
    
    public boolean removeLanguage(WebSite website, String id, User user) throws SWBException        
    {
        boolean doAction=false;
        website.removeLanguage(id);
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove language", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing language", e);
        }
        return doAction;
    }
    
    public boolean updateLanguage(WebSite website, Language language, String title, String description, String value, User user) throws SWBException        
    {
        boolean doAction=false;
        if(title!=null){
            language.setTitle(title);
        }
        
        if(description!=null){
            language.setDescription(description);
        }
        
        if(value!=null){
            language.setValue(value);
        }
        language.setUserModified(user);
        doAction=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", language.getName(), language.getURI(), "update language", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating language", e);
        }
        return doAction;
    }
    
}
