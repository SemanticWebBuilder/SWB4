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

    
    public boolean createLanguage(WebSite website, String lang, String title, String description,String value, User user) throws SWBException        
    {
        boolean doAction=false;
        Language language=website.createLanguage(lang);
        language.setCreator(user);
        language.setTitle(title);
        language.setDescription(description);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "create", language.getURI(), language.getURI(), "create language", null);
        
        return doAction;
    }
    
    public boolean removeLanguage(WebSite website, String lang, User user) throws SWBException        
    {
        boolean doAction=false;
        website.removeLanguage(lang);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "remove", lang, lang, "remove language", null);
        
        return doAction;
    }
    
    public boolean updateLanguage(WebSite website, Language language, String title, String description, User user) throws SWBException        
    {
        boolean doAction=false;
        if(title!=null){
            language.setTitle(title);
        }
        
        if(description!=null){
            language.setDescription(description);
        }
        
        language.setModifiedBy(user);
        doAction=true;
        
        SWBPortal.log(user.getURI(), "update", language.getURI(), language.getURI(), "update language", null);
        
        return doAction;
    }
    
}
