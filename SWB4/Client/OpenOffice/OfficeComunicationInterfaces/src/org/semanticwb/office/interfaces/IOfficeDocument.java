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
    public int publish(String title,String description,String path);
    @XmlRpcMethod(methodName="OfficeDocument.getPath")
    public String getPath(int contentID);
    @XmlRpcMethod(methodName="OfficeDocument.setTitle")
    public void setTitle(int contentID,String title);
    @XmlRpcMethod(methodName="OfficeDocument.setDescription")
    public void setDescription(int contentID,String description);
    @XmlRpcMethod(methodName="OfficeDocument.setPath")
    public void setPath(int contentID,String path);
    @XmlRpcMethod(methodName="OfficeDocument.setActive")
    public void setActive(int contentID,boolean active);
    @XmlRpcMethod(methodName="OfficeDocument.getActive")
    public boolean getActive(int contentID);
    @XmlRpcMethod(methodName="OfficeDocument.delete")
    public void delete(int contentID);
    @XmlRpcMethod(methodName="OfficeDocument.exists")
    public boolean exists(int contentId);
    @XmlRpcMethod(methodName="OfficeDocument.updateContent")
    void updateContent(int contentId);
}
