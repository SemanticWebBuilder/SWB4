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
    public int publish(String title,String description,String path,String type)
    {
        return -1;
    }    
    public void updateContent(int contentId)
    {
    
    }
    public String getPath(int contentID)
    {        
        return "/";
    }
    public boolean exists(int contentId)
    {
        return false;
    }
    public void setTitle(int contentID,String title)
    {
            
    }
    public void setDescription(int contentID,String description)
    {
            
    }
    public void setPath(int contentID,String path)
    {        
    
    }
    public void sendToAuthorize()
    {        
        
    }    
    public void setActive(int contentID,boolean active)
    {        
    
    }
    public boolean getActive(int contentID)
    {
        return false;
    }
    public void delete(int contentID)
    {
        
    }
}
