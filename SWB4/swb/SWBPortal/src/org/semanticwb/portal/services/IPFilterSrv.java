/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class IPFilterSrv {

    public IPFilter createIPFilter(SemanticModel model, String value, User user) throws SWBException 
    {
        IPFilter ipFilter = SWBContext.createIPFilter(model);
        ipFilter.setStatus(1);
        ipFilter.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", ipFilter.getURI(), ipFilter.getURI(), "create IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return ipFilter;
    }
    
    public IPFilter createIPFilter(SemanticModel model, String filterUri, String value, User user) throws SWBException 
    {
        IPFilter ipFilter = SWBContext.createIPFilter(model, filterUri);
        ipFilter.setStatus(1);
        ipFilter.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", ipFilter.getURI(), ipFilter.getURI(), "create IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return ipFilter;
    }

    public boolean removeIPFilter(IPFilter ipFilter, User user) throws SWBException 
    {
        boolean removed=false;
        SWBContext.removeObject(ipFilter.getURI());
        removed=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", ipFilter.getURI(), ipFilter.getURI(), "create IPFilter", null);
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "update", ipFilter.getURI(), ipFilter.getURI(), "update IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating IPFilter", e);
        }
        return updated;
    }
    
    
}
