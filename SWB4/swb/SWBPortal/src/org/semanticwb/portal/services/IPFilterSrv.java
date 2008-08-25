/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class IPFilterSrv {

    public IPFilter createIPFilter(WebSite website, String value, User user) throws SWBException 
    {
        IPFilter ipFilter = website.createIPFilter();
        ipFilter.setStatus(1);
        ipFilter.setValue(value);

        SWBPortal.log(user.getURI(), "create", ipFilter.getURI(), ipFilter.getURI(), "create ipFilter", null);
        
        return ipFilter;
    }
    
    public IPFilter createIPFilter(WebSite website, String id, String filterUri, String value, User user) throws SWBException 
    {
        IPFilter ipFilter = website.createIPFilter(id);
        ipFilter.setStatus(1);
        ipFilter.setValue(value);

        SWBPortal.log(user.getURI(), "create", ipFilter.getURI(), ipFilter.getURI(), "create ipFilter", null);
        
        return ipFilter;
    }

    public boolean removeIPFilter(WebSite website, String id, User user) throws SWBException 
    {
        boolean removed=false;
        website.removeIPFilter(id);
        removed=true;
       
        SWBPortal.log(user.getURI(), "remove", id, id, "remove ipFilter", null);
        
        return removed;
    }
    
    public boolean updateIPFilter(IPFilter ipFilter, String value, User user) throws SWBException {
        boolean updated = false;
        
        if (value != null) {
            ipFilter.setValue(value);
        }
        updated = true;
        
        SWBPortal.log(user.getURI(), "update", ipFilter.getURI(), ipFilter.getURI(), "remove ipFilter", null);
        
        return updated;
    }
    
    
}
