/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Language;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class LanguageSrv {

    public boolean createLanguage(WebSite website, String title, String description, String value, User user) throws SWBException        
    {
        boolean doAction=false;
        Language language=website.createLanguage();
        language.setCreator(user);
        language.setTitle(title);
        language.setValue(value);
        language.setDescription(description);
        doAction=true;
        
        SWBPortal.createInstance().log(user.getURI(), "create", language.getURI(), language.getURI(), "create language", null);
        
        return doAction;
    }
    
    public boolean createLanguage(WebSite website, String id, String title, String description,String value, User user) throws SWBException        
    {
        boolean doAction=false;
        Language language=website.createLanguage(id);
        language.setCreator(user);
        language.setTitle(title);
        language.setValue(value);
        language.setDescription(description);
        doAction=true;
        
        SWBPortal.createInstance().log(user.getURI(), "create", language.getURI(), language.getURI(), "create language", null);
        
        return doAction;
    }
    
    public boolean removeLanguage(WebSite website, String id, User user) throws SWBException        
    {
        boolean doAction=false;
        website.removeLanguage(id);
        doAction=true;
        
        SWBPortal.createInstance().log(user.getURI(), "remove", id, id, "remove language", null);
        
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
        language.setModifiedBy(user);
        doAction=true;
        
        SWBPortal.createInstance().log(user.getURI(), "update", language.getURI(), language.getURI(), "update language", null);
        
        return doAction;
    }
    
}
