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
    public String publish(String title,String description,String repositoryName,String categoryID,String type,String nodeType) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.getPath")    
    public void setTitle(String contentID,String title) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setDescription")
    public void setDescription(String contentID,String description) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.setPath")            
    public void delete(String contentID) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.exists")
    public boolean exists(String contentId) throws Exception;
    @XmlRpcMethod(methodName="OfficeDocument.updateContent")    
    public String updateContent(String contentId) throws Exception; 
    @XmlRpcMethod(methodName="OfficeDocument.setPagination")    
    public void setPagination(String contentId) throws Exception;     
}
