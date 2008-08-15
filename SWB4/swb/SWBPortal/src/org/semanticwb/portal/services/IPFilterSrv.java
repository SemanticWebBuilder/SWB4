/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

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

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", ipFilter.getName(), ipFilter.getURI(), "create IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return ipFilter;
    }
    
    public IPFilter createIPFilter(WebSite website, String id, String filterUri, String value, User user) throws SWBException 
    {
        IPFilter ipFilter = website.createIPFilter(id);
        ipFilter.setStatus(1);
        ipFilter.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", ipFilter.getName(), ipFilter.getURI(), "create IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return ipFilter;
    }

    public boolean removeIPFilter(WebSite website, String id, User user) throws SWBException 
    {
        boolean removed=false;
        website.removeIPFilter(id);
        removed=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return removed;
    }
    
    public boolean updateIPFilter(IPFilter ipFilter, String value, User user) throws SWBException {
        boolean updated = false;
        
        if (value != null) {
            ipFilter.setValue(value);
        }
        updated = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", ipFilter.getURI(), ipFilter.getURI(), "update IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating IPFilter", e);
        }
        return updated;
    }
    
    
}
