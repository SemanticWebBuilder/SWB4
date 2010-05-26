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
    public interface IOfficeDocument : IXmlRpcProxy
    {
        [XmlRpcMethod("OfficeDocument.save")]
        String save(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values);

        [XmlRpcMethod("OfficeDocument.setTitle")]
        void setTitle(String repositoryName, String contentID, String title);

        [XmlRpcMethod("OfficeDocument.deleteCalendarFromCatalog")]
        void deleteCalendarFromCatalog(SiteInfo siteInfo, CalendarInfo cal);

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
        String updateContent(String repositoryName, String contentId, String file, ResourceInfo[] resourcestoSend, PFlow[] flowsToSend, String[] msg);

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
        String createPreview(String repositoryName, String contentId, String version, String type);

        [XmlRpcMethod("OfficeDocument.deletePreview")]
        void deletePreview(String dir);

        [XmlRpcMethod("OfficeDocument.getNumberOfVersions")]
        int getNumberOfVersions(String repositoryName, String contentId);

        [XmlRpcMethod("OfficeDocument.getCatalogCalendars")]
        CalendarInfo[] getCatalogCalendars(SiteInfo siteInfo);

        [XmlRpcMethod("OfficeDocument.getCalendarsOfResource")]
        CalendarInfo[] getCalendarsOfResource(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.updateCalendar")]
        void updateCalendar(SiteInfo siteInfo, CalendarInfo calendarInfo);

        [XmlRpcMethod("OfficeDocument.insertCalendartoResource")]
        void insertCalendartoResource(ResourceInfo resourceInfo, CalendarInfo calendar);

        [XmlRpcMethod("OfficeDocument.deleteCalendar")]
        void deleteCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo);

        [XmlRpcMethod("OfficeDocument.activeCalendar")]
        void activeCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo, bool active);

        [XmlRpcMethod("OfficeDocument.setEndDate")]
        void setEndDate(ResourceInfo resourceInfo, DateTime date);


        [XmlRpcMethod("OfficeDocument.getEndDate")]
        DateTime getEndDate(ResourceInfo resourceInfo);

        [XmlRpcMethod("OfficeDocument.deleteEndDate")]
        void deleteEndDate(ResourceInfo resourceInfo);

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

        [XmlRpcMethod("OfficeDocument.needsSendToPublish")]
        bool needsSendToPublish(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.getFlows")]
        PFlow[] getFlows(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.sendToAuthorize")]
        void sendToAuthorize(ResourceInfo info, PFlow flow, String message);

        [XmlRpcMethod("OfficeDocument.isInFlow")]
        bool isInFlow(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.isAuthorized")]
        bool isAuthorized(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.getElementsOfResource")]
        ElementInfo[] getElementsOfResource(ResourceInfo info);

        [XmlRpcMethod("OfficeDocument.addElementToResource")]
        void addElementToResource(ResourceInfo info, ElementInfo ruleInfo);

        [XmlRpcMethod("OfficeDocument.deleteElementToResource")]
        void deleteElementToResource(ResourceInfo info, ElementInfo ruleInfo);

        [XmlRpcMethod("OfficeDocument.changeResourceOfWebPage")]
        void changeResourceOfWebPage(ResourceInfo info, WebPageInfo webPageInfo);

        [XmlRpcMethod("OfficeDocument.getLanguages")]
        LanguageInfo[] getLanguages(SiteInfo site);

        [XmlRpcMethod("OfficeDocument.getTitleOfWebPage")]
        String getTitleOfWebPage(PageInfo webPageInfo, LanguageInfo laguage);

        [XmlRpcMethod("OfficeDocument.setTitlesOfWebPage")]
        void setTitlesOfWebPage(PageInfo webPageInfo, LanguageInfo[] languages, String[] values);

        [XmlRpcMethod("OfficeDocument.existContentOldVersion")]
        ContentInfo existContentOldVersion(String contentid, String topicmap, String topicid);

        [XmlRpcMethod("OfficeDocument.canPublishToResourceContent")]
        bool canPublishToResourceContent(String type, WebPageInfo page);

        [XmlRpcMethod("OfficeDocument.canModify")]
        bool canModify(String repositoryName, String contentID);

    }
}
