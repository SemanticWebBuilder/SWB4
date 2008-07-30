/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

import org.semanticwb.xmlrpc.XmlRpcMethod;

/**
 *
 * @author victor.lorenzana
 */
public interface IOfficeDocument
{
    @XmlRpcMethod(methodName="OfficeDocument.publish") 
    public int publish(String title,String description,String path,String type) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.getPath")
    public String getPath(int contentID) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setTitle")
    public void setTitle(int contentID,String title) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setDescription")
    public void setDescription(int contentID,String description) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setPath")
    public void setPath(int contentID,String path) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setActive")
    public void setActive(int contentID,boolean active) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.getActive")
    public boolean getActive(int contentID) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.delete")
    public void delete(int contentID) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.exists")
    public boolean exists(int contentId) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.updateContent")
    void updateContent(int contentId) throws Exception; 
}
