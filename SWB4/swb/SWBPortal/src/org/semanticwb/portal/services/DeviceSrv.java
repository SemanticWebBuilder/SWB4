/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Device;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

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

        SWBPortal.createInstance().log(user.getURI(), "create", device.getURI(), device.getURI(), "create Device", null);
       
        return device;
    }
    
    
    public Device createDevice(WebSite website, String id, String title, String description, String value, User user) throws SWBException 
    {
   
        Device device = null;
        device = website.createDevice(id);
        device.setTitle(title);
        device.setDescription(description);
        device.setValue(value);

        SWBPortal.createInstance().log(user.getURI(), "create", device.getURI(), device.getURI(), "create Device", null);
        
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
       
        SWBPortal.createInstance().log(user.getURI(), "update", device.getURI(), device.getURI(), "update Device", null);
        
        return updated;
    }
    
    
}
