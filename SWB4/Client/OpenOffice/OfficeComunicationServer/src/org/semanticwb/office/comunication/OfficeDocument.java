/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeDocument extends XmlRpcObject implements IOfficeDocument
{
    public int publish(String title,String description,String path)
    {
        return 0;
    }    
    public String getPath()
    {        
        return "/";
    }
    public void setTitle(String title)
    {
            
    }
    public void setDescription(String description)
    {
            
    }
    public void setPath(String path)
    {        
    
    }
    public void sendToAuthorize()
    {        
        
    }
    public void setActive(boolean active)
    {        
    
    }
    public boolean getActive()
    {
        return false;
    }
    public void delete()
    {
        
    }
}
