/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

import java.util.Date;
import org.semanticwb.xmlrpc.XmlRpcMethod;

/**
 *
 * @author victor.lorenzana
 */
public interface IOfficeDocument
{
    @XmlRpcMethod(methodName="OfficeDocument.publish") 
    public String publish(String title,String description,String repositoryName,String categoryID,String type,String nodeType,String file) throws Exception;

    @XmlRpcMethod(methodName="OfficeDocument.setTitle")
    public void setTitle(String repositoryName,String contentID,String title) throws Exception;
    
    @XmlRpcMethod(methodName="OfficeDocument.setDescription")
    public void setDescription(String repositoryName,String contentID,String description) throws Exception;


    @XmlRpcMethod(methodName="OfficeDocument.getTitle")
    public String getTitle(String repositoryName,String contentID) throws Exception;

    @XmlRpcMethod(methodName="OfficeDocument.getLasUpdate")
    public Date getLasUpdate(String repositoryName,String contentID) throws Exception;
    
    @XmlRpcMethod(methodName="OfficeDocument.getDescription")
    public String getDescription(String repositoryName,String contentID) throws Exception;
    
    @XmlRpcMethod(methodName="OfficeDocument.setDescription")
    public void setDescription(String contentID,String description) throws Exception;
    
    public void delete(String repositoryName,String contentID) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.exists")
    public boolean exists(String repositoryName,String contentId) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.updateContent")    
    public String updateContent(String repositoryName,String contentId,String file) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setPagination")    
    public void setPagination(String contentId) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.getVersions")
    public VersionInfo[] getVersions(String repositoryName,String contentId) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.publishToPortletContent")
    public String publishToPortletContent(String repositoryName, String contentId, WebPageInfo webpage) throws Exception;
}
