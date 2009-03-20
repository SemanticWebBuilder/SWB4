using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XmlRpcLibrary;
namespace WBOffice4.Interfaces
{
    interface IOfficeDocument : IXmlRpcProxy
    {
        [XmlRpcMethod("OfficeDocument.save")]
        String save(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values);

        [XmlRpcMethod("OfficeDocument.setTitle")]
        void setTitle(String repositoryName, String contentID, String title);

        [XmlRpcMethod("OfficeDocument.setDescription")]
        void setDescription(String repositoryName, String contentID, String description);

        [XmlRpcMethod("OfficeDocument.getTitle")]
        String getTitle(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.getCategory")]
        String getCategory(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.getLastUpdate")]
        DateTime getLastUpdate(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.getDescription")]
        String getDescription(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.delete")]
        void delete(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.exists")]
        bool exists(String repositoryName, String contentId);

        [XmlRpcMethod("OfficeDocument.updateContent")]
        String updateContent(String repositoryName, String contentId, String file);

        [XmlRpcMethod("OfficeDocument.getVersions")]
        VersionInfo[] getVersions(String repositoryName, String contentId);

        [XmlRpcMethod("OfficeDocument.publishToResourceContent")]
        ResourceInfo publishToResourceContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage, PropertyInfo[] properties, String[] values);

        [XmlRpcMethod("OfficeDocument.getResourceProperties")]
        PropertyInfo[] getResourceProperties(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.getContentProperties")]
        PropertyInfo[] getContentProperties(String repositoryName, String type);

        [XmlRpcMethod("OfficeDocument.getViewPropertyValue")]
        String getViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo);

        [XmlRpcMethod("OfficeDocument.setViewPropertyValue")]
        void setViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value);

        [XmlRpcMethod("OfficeDocument.setResourceProperties")]
        void setResourceProperties(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value);

        [XmlRpcMethod("OfficeDocument.listResources")]
        ResourceInfo[] listResources(String repositoryName, String contentid);

        [XmlRpcMethod("OfficeDocument.deleteContentOfPage")]
        void deleteContentOfPage(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.activateResource")]
        void activateResource(ResourceInfo info, bool active);

        [XmlRpcMethod("OfficeDocument.getCategoryInfo")]
        CategoryInfo getCategoryInfo(String repositoryName, String contentid);

        [XmlRpcMethod("OfficeDocument.changeCategory")]
        void changeCategory(String repositoryName, String contentId, String newCategoryId);

        [XmlRpcMethod("OfficeDocument.changeVersionPorlet")]
        void changeVersionPorlet(ResourceInfo info, String newVersion);

        [XmlRpcMethod("OfficeDocument.deleteResource")]
        void deleteResource(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.getVersionToShow")]
        String getVersionToShow(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.createPreview")]
        String createPreview(String repositoryName, String contentId, String version);

        [XmlRpcMethod("OfficeDocument.deletePreview")]
        void deletePreview(String dir);

        [XmlRpcMethod("OfficeDocument.getNumberOfVersions")]
        int getNumberOfVersions(String repositoryName, String contentId);

        [XmlRpcMethod("OfficeDocument.getCalendars")]
        CalendarInfo[] getCalendars(ResourceInfo resourceInfo);

        [XmlRpcMethod("OfficeDocument.updateCalendar")]
        void updateCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo);

        [XmlRpcMethod("OfficeDocument.insertCalendar")]
        CalendarInfo insertCalendar(ResourceInfo resourceInfo, String title, String xml);

        [XmlRpcMethod("OfficeDocument.deleteCalendar")]
        void deleteCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo);

        [XmlRpcMethod("OfficeDocument.activeCalendar")]
        void activeCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo, bool active);

        [XmlRpcMethod("OfficeDocument.updatePorlet")]
        void updatePorlet(ResourceInfo resourceInfo);

        [XmlRpcMethod("OfficeDocument.deleteVersionOfContent")]
        void deleteVersionOfContent(String repositoryName, String contentId, String versionName);

        [XmlRpcMethod("OfficeDocument.allVersionsArePublished")]
        bool allVersionsArePublished(String repositoryName, String contentId);

        [XmlRpcMethod("OfficeDocument.validateViewValues")]
        void validateViewValues(String repositoryName, String contentID, PropertyInfo[] properties, Object[] values);

        [XmlRpcMethod("OfficeDocument.validateContentValues")]
        void validateContentValues(String repositoryName, PropertyInfo[] properties, Object[] values, String type);

        [XmlRpcMethod("OfficeDocument.setContentPropertyValue")]
        void setContentPropertyValue(String repositoryName, String contentID, PropertyInfo propertyInfo, String value);

        [XmlRpcMethod("OfficeDocument.getNameOfContent")]
        String getNameOfContent(String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.getContentProperty")]
        String getContentProperty(PropertyInfo prop, String repositoryName, String contentID);

        [XmlRpcMethod("OfficeDocument.setContentProperties")]
        void setContentProperties(String repositoryName, String contentID, PropertyInfo[] properties, String[] values);
    }
}
