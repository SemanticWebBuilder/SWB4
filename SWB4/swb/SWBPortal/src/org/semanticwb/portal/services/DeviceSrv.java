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
    
    public Device createDevice(SemanticModel model , String title, String description, String value, User user) throws SWBException 
    {
   
        Device device = null;
        device = SWBContext.createDevice(model);
        device.setTitle(title);
        device.setDescription(description);
        device.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", device.getURI(), device.getURI(), "create device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating device", e);
        }
        return device;
    }
    
    
    public Device createDevice(SemanticModel model, String deviceUri, String title, String description, String value, User user) throws SWBException 
    {
   
        Device device = null;
        device = SWBContext.createDevice(model, deviceUri);
        device.setTitle(title);
        device.setDescription(description);
        device.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", device.getURI(), device.getURI(), "create device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating device", e);
        }
        return device;
    }
    
    public boolean removeDNS(Device device , User user) throws SWBException {
        boolean deleted = false;
        SWBContext.removeObject(device.getURI());
        deleted = true;
        //logeo.creat
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "remove", device.getURI(), device.getURI(), "remove Device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing device:" + device.getURI(), e);
        }
        return deleted;
    }
    
    public boolean updateDevice(Device device, String uri, String title, String description, String value, User user) throws SWBException {
        boolean updated = false;
        
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "update", device.getURI(), device.getURI(), "update Device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating device", e);
        }
        return updated;
    }
    
    
}
