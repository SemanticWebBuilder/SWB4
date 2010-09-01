/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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

    public static double version = 4.009;

    @XmlRpcMethod(methodName = "OfficeApplication.isValidVersion")
    public boolean isValidVersion(double version) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.changePassword")
    public void changePassword(String newPassword) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createPage")
    public void createPage(WebPageInfo page, String pageid, String title, String description) throws Exception;


    @XmlRpcMethod(methodName = "OfficeApplication.canCreatePage")
    public boolean canCreatePage(WebPageInfo page) throws Exception;


    @XmlRpcMethod(methodName = "OfficeApplication.existsPage")
    public boolean existsPage(WebSiteInfo site, String pageid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createCategory")
    public String createCategory(String repositoryName, String title, String description) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canCreateCategory")
    public boolean canCreateCategory(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canCreateCategory")
    public boolean canCreateCategory(String repositoryName, String categoryId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canRemoveCategory")
    public boolean canRemoveCategory(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canRemoveCategory")
    public boolean canRemoveCategory(String repositoryName, String categoryId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createCategory")
    public String createCategory(String repositoryName, String categoryId, String title, String description) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canDeleteCategory")
    public boolean canDeleteCategory(String repositoryName, String id) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.deleteCategory")
    public boolean deleteCategory(String repositoryName, String id) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getRepositories")
    public RepositoryInfo[] getRepositories() throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getCategories")
    public CategoryInfo[] getCategories(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getAllCategories")
    public CategoryInfo[] getAllCategories(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getCategories")
    public CategoryInfo[] getCategories(String repositoryName, String categoryId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getContentTypes")
    public ContentType[] getContentTypes(String repositoryName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.search")
    public ContentInfo[] search(String repositoryName, String title, String description, String category, String type, String officeType) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.search")
    public ContentInfo[] search(String repositoryName, String title, String description, String category, String type, String officeType,WebPageInfo webPageInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.openContent")
    public String openContent(String repositoryName, VersionInfo versioninfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSites")
    public WebSiteInfo[] getSites() throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getHomePage")
    public WebPageInfo getHomePage(WebSiteInfo website) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getPages")
    public WebPageInfo[] getPages(WebPageInfo webpage) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getLimitOfVersions")
    public int getLimitOfVersions() throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getMyContents")
    public FlowContentInformation[] getMyContents(WebSiteInfo site) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getAllContents")
    public FlowContentInformation[] getAllContents(WebSiteInfo site) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.createCalendar")
    public CalendarInfo createCalendar(SiteInfo info,String title,String xml) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getContentsForAuthorize")
    public FlowContentInformation[] getContentsForAuthorize(WebSiteInfo site) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.isReviewer")
    public boolean isReviewer(ResourceInfo info) throws Exception;   

    @XmlRpcMethod(methodName = "OfficeApplication.authorize")
    public void authorize(ResourceInfo resourceInfo, String message) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.reject")
    public void reject(ResourceInfo resourceInfo, String message) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.canDeleteCalendar")
    public boolean canDeleteCalendar(SiteInfo siteInfo,CalendarInfo CalendarInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.existCalendar")
    public boolean existCalendar(SiteInfo siteInfo,CalendarInfo CalendarInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getElementsToAdd")
    public ElementInfo[] getElementsToAdd(SiteInfo siteInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.activePage")
    public void activePage(PageInfo webPageInfo,boolean active) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSemanticRepositories")
    public SemanticRepository[] getSemanticRepositories(SiteInfo siteInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSemanticFolderRepositories")
    public SemanticFolderRepository[] getSemanticFolderRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSemanticFolderRepositories")
    public SemanticFolderRepository[] getSemanticFolderRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository,SemanticFolderRepository semanticFolderRepository) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSemanticFileRepositories")
    public SemanticFileRepository[] getSemanticFileRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository,SemanticFolderRepository semanticFolder) throws Exception;

    @XmlRpcMethod(methodName = "OfficeApplication.getSemanticFileRepositories")
    public SemanticFileRepository[] getSemanticFileRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository) throws Exception;
}
