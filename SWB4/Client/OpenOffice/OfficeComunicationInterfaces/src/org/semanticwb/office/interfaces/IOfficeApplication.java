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
public interface IOfficeApplication {
    public static double version=0.1;
    @XmlRpcMethod(methodName="OfficeApplication.isValidVersion") 
    public boolean isValidVersion(double version) throws Exception;
    @XmlRpcMethod(methodName="OfficeApplication.changePassword") 
    public void changePassword(String newPassword) throws Exception; 
    @XmlRpcMethod(methodName="OfficeApplication.createPage") 
    public void createPage(String title,String id,String description) throws Exception; 
    @XmlRpcMethod(methodName="OfficeApplication.existPage")
    public boolean existsPage(String id) throws Exception; 
    @XmlRpcMethod(methodName="OfficeApplication.createCategory")
    public String createCategory(String repositoryName,String id,String description) throws Exception; 
    @XmlRpcMethod(methodName="OfficeApplication.getRepositories")
    public String[] getRepositories() throws Exception;
    @XmlRpcMethod(methodName="OfficeApplication.getCategories")
    public CategoryInfo[] getCategories(String repositoryName) throws Exception;
}
