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

    public IPFilter createIPFilter(String modelUri, String uri, String value, User user) throws SWBException {
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        IPFilter ipFilter = SWBContext.createIPFilter(model, uri);
        ipFilter.setStatus(1);
        ipFilter.setValue(uri);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return ipFilter;
    }

    public boolean removeIPFilter(String uri, User user) throws SWBException 
    {
        boolean removed=false;
        SWBContext.removeObject(uri);
        removed=true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating IPFilter", e);
        }
        return removed;
    }
    
    public boolean updateIPFilter(String modelUri, String uri, String value, User user) throws SWBException {
        boolean updated = false;
        IPFilter ipFilter = null;
        ipFilter = SWBContext.getIPFilter(uri);

        if (value != null) {
            ipFilter.setValue(value);
        }
        updated = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "update", uri, uri, "update IPFilter", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating IPFilter", e);
        }
        return updated;
    }
    
    
}
