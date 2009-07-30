using System;
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
        public ElementInfo[] getElementsToAdd(SiteInfo siteInfo);


    }
}