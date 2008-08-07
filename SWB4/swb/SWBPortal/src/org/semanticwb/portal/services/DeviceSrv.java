/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class DeviceSrv 
{
    public Device createDevice(String modelUri, String uri, String title, String description, String value, User user) throws SWBException 
    {
   
        Device device = null;
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        device = SWBContext.createDevice(model, uri);
        device.setTitle(title);
        device.setDescription(description);
        device.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating device", e);
        }
        return device;
    }
    
    public boolean removeDNS(String dnsDevice, User user) throws SWBException {
        boolean deleted = false;
        SWBContext.removeObject(dnsDevice);
        deleted = true;
        //logeo.creat
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "remove", dnsDevice, dnsDevice, "remove Device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing device:" + dnsDevice, e);
        }
        return deleted;
    }
    
    public boolean updateDevice(String modelUri, String uri, String title, String description, String value, User user) throws SWBException {
        boolean updated = false;
        Device device = null;
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        device = SWBContext.getDevice(uri);

        if (title != null) {
            device.setTitle(title);
        }
        if (description != null) {
            device.setDescription(description);
        }
        if (value != null) {
            device.setValue(value);
        }
        updated = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "update", uri, uri, "update Device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating device", e);
        }
        return updated;
    }
    
    
}
