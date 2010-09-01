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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XmlRpcLibrary;
namespace WBOffice4.Interfaces
{
    public interface IOfficeApplication : IXmlRpcProxy
    {
        [XmlRpcMethod("OfficeApplication.isValidVersion")]
        bool isValidVersion(double version);

        [XmlRpcMethod("OfficeApplication.changePassword")]
        void changePassword(String newPassword);

        [XmlRpcMethod("OfficeApplication.createPage")]
        void createPage(WebPageInfo page, String pageid, String title, String description);

        [XmlRpcMethod("OfficeApplication.existPage")]
        bool existsPage(WebSiteInfo site, String pageid);

        [XmlRpcMethod("OfficeApplication.createCategory")]
        String createCategory(String repositoryName, String title, String description);

        [XmlRpcMethod("OfficeApplication.createCategory")]
        String createCategory(String repositoryName, String categoryId, String title, String description);

        [XmlRpcMethod("OfficeApplication.canDeleteCategory")]
        bool canDeleteCategory(String repositoryName, String id);

        [XmlRpcMethod("OfficeApplication.deleteCategory")]
        bool deleteCategory(String repositoryName, String id);

        [XmlRpcMethod("OfficeApplication.getRepositories")]
        RepositoryInfo[] getRepositories();

        [XmlRpcMethod("OfficeApplication.getCategories")]
        CategoryInfo[] getCategories(String repositoryName);

        [XmlRpcMethod("OfficeApplication.getAllCategories")]
        CategoryInfo[] getAllCategories(String repositoryName);

        [XmlRpcMethod("OfficeApplication.getCategories")]
        CategoryInfo[] getCategories(String repositoryName, String categoryId);

        [XmlRpcMethod("OfficeApplication.getContentTypes")]
        ContentType[] getContentTypes(String repositoryName);

        [XmlRpcMethod("OfficeApplication.search")]
        ContentInfo[] search(String repositoryName, String title, String description, String category, String type, String officeType, WebPageInfo uriWebPage);

        [XmlRpcMethod("OfficeApplication.search")]
        ContentInfo[] search(String repositoryName, String title, String description, String category, String type, String officeType);

        [XmlRpcMethod("OfficeApplication.openContent")]
        String openContent(String repositoryName, VersionInfo versioninfo);

        [XmlRpcMethod("OfficeApplication.getSites")]
        WebSiteInfo[] getSites();

        [XmlRpcMethod("OfficeApplication.getHomePage")]
        WebPageInfo getHomePage(WebSiteInfo website);

        [XmlRpcMethod("OfficeApplication.getPages")]
        WebPageInfo[] getPages(WebPageInfo webpage);

        [XmlRpcMethod("OfficeApplication.getLimitOfVersions")]
        int getLimitOfVersions();

        [XmlRpcMethod("OfficeApplication.getMyContents")]
        FlowContentInformation[] getMyContents(WebSiteInfo site);

        [XmlRpcMethod("OfficeApplication.getAllContents")]
        FlowContentInformation[] getAllContents(WebSiteInfo site);

        [XmlRpcMethod("OfficeApplication.createCalendar")]
        CalendarInfo createCalendar(SiteInfo info, String title, String xml);

        [XmlRpcMethod("OfficeApplication.getContentsForAuthorize")]
        FlowContentInformation[] getContentsForAuthorize(WebSiteInfo site);

        [XmlRpcMethod("OfficeApplication.isReviewer")]
        bool isReviewer(ResourceInfo info);

        [XmlRpcMethod("OfficeApplication.authorize")]
        void authorize(ResourceInfo resourceInfo, String message);

        [XmlRpcMethod("OfficeApplication.reject")]
        void reject(ResourceInfo resourceInfo, String message);

        [XmlRpcMethod("OfficeApplication.canDeleteCalendar")]
        bool canDeleteCalendar(SiteInfo siteInfo, CalendarInfo CalendarInfo);

        [XmlRpcMethod("OfficeApplication.existCalendar")]
        bool existCalendar(SiteInfo siteInfo, CalendarInfo CalendarInfo);

        [XmlRpcMethod("OfficeApplication.getElementsToAdd")]
        ElementInfo[] getElementsToAdd(SiteInfo siteInfo);

        [XmlRpcMethod("OfficeApplication.activePage")]
        void activePage(PageInfo webPageInfo, bool active);

        [XmlRpcMethod("OfficeApplication.canCreatePage")]
        bool canCreatePage(WebPageInfo page);

        [XmlRpcMethod("OfficeApplication.canCreateCategory")]
        bool canCreateCategory(String repositoryName);

        [XmlRpcMethod("OfficeApplication.canCreateCategory")]
        bool canCreateCategory(String repositoryName, String categoryId);

        [XmlRpcMethod("OfficeApplication.canRemoveCategory")]
        bool canRemoveCategory(String repositoryName);

        [XmlRpcMethod("OfficeApplication.canRemoveCategory")]
        bool canRemoveCategory(String repositoryName, String categoryId);
        
        [XmlRpcMethod("OfficeApplication.getSemanticRepositories")]
        SemanticRepository[] getSemanticRepositories(SiteInfo siteInfo);

        [XmlRpcMethod("OfficeApplication.getSemanticFolderRepositories")]
        SemanticFolderRepository[] getSemanticFolderRepositories(SiteInfo siteInfo, SemanticRepository semanticRepository);

        [XmlRpcMethod("OfficeApplication.getSemanticFolderRepositories")]
        SemanticFolderRepository[] getSemanticFolderRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository,SemanticFolderRepository semanticFolderRepository);


        [XmlRpcMethod("OfficeApplication.getSemanticFileRepositories")]
        SemanticFileRepository[] getSemanticFileRepositories(SiteInfo siteInfo, SemanticRepository semanticRepository, SemanticFolderRepository semanticFolder);

        [XmlRpcMethod("OfficeApplication.getSemanticFileRepositories")]
        SemanticFileRepository[] getSemanticFileRepositories(SiteInfo siteInfo, SemanticRepository semanticRepository);
        
    }
}