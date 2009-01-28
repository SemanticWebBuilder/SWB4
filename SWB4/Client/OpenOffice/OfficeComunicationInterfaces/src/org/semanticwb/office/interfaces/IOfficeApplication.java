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
public interface IOfficeApplication
{

    public static double version = 0.1;

    @XmlRpcMethod(methodName = "OfficeApplication.isValidVersion")
    public boolean isValidVersion(double version) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.changePassword")
    public void changePassword(String newPassword) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createPage")
    public void createPage(WebPageInfo page,String pageid,String title, String description) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.existPage")
    public boolean existsPage(WebSiteInfo site,String pageid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createCategory")
    public String createCategory(String repositoryName, String title, String description) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createCategory")
    public String createCategory(String repositoryName, String categoryId, String title, String description) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canDeleteCategory")
    public boolean canDeleteCategory(String repositoryName, String id) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.deleteCategory")
    public boolean deleteCategory(String repositoryName, String id) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getRepositories")
    public String[] getRepositories() throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getCategories")
    public CategoryInfo[] getCategories(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getAllCategories")
    public CategoryInfo[] getAllCategories(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getCategories")
    public CategoryInfo[] getCategories(String repositoryName, String categoryId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getContentTypes")
    public ContentType[] getContentTypes(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.search")
    public ContentInfo[] search(String repositoryName, String title, String description, String category,String type,String officeType) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.openContent")
    public String openContent(String repositoryName,VersionInfo versioninfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSites")
    public WebSiteInfo[] getSites() throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getHomePage")
    public WebPageInfo getHomePage(WebSiteInfo website) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getPages")
    public WebPageInfo[] getPages(WebPageInfo webpage) throws Exception;
}
