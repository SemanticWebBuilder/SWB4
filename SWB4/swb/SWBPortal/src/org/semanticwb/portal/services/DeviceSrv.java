/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Device;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class DeviceSrv 
{
    
    public Device createDevice(WebSite website, String title, String description, String value, User user) throws SWBException 
    {
   
        Device device = null;
        device = website.createDevice();
        device.setTitle(title);
        device.setDescription(description);
        device.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", device.getURI(), device.getURI(), "create device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating device", e);
        }
        return device;
    }
    
    
    public Device createDevice(WebSite website, String id, String title, String description, String value, User user) throws SWBException 
    {
   
        Device device = null;
        device = website.createDevice(id);
        device.setTitle(title);
        device.setDescription(description);
        device.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", device.getURI(), device.getURI(), "create device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating device", e);
        }
        return device;
    }
    
    public boolean updateDevice(Device device, String title, String description, String value, User user) throws SWBException {
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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", device.getURI(), device.getURI(), "update Device", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating device", e);
        }
        return updated;
    }
    
    
}
