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

    @XmlRpcMethod(methodName = "OfficeDocument.save")
    public String save(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setTitle")
    public void setTitle(String repositoryName, String contentID, String title) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setDescription")
    public void setDescription(String repositoryName, String contentID, String description) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getTitle")
    public String getTitle(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getCategory")
    public String getCategory(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getLastUpdate")
    public Date getLastUpdate(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getDescription")
    public String getDescription(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.delete")
    public void delete(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.exists")
    public boolean exists(String repositoryName, String contentId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.updateContent")
    public String updateContent(String repositoryName, String contentId, String file) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getVersions")
    public VersionInfo[] getVersions(String repositoryName, String contentId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.publishToPortletContent")
    public PortletInfo publishToPortletContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage,PropertyInfo[] properties,String[] values) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getPortletProperties")
    public PropertyInfo[] getPortletProperties(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getContentProperties")
    public PropertyInfo[] getContentProperties(String repositoryName, String type) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getViewPropertyValue")
    public String getViewPropertyValue(PortletInfo portletInfo, PropertyInfo propertyInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setViewPropertyValue")
    public void setViewPropertyValue(PortletInfo portletInfo, PropertyInfo propertyInfo, String value) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setPortletProperties")
    public void setPortletProperties(PortletInfo portletInfo, PropertyInfo propertyInfo, String value) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.listPortlets")
    public PortletInfo[] listPortlets(String repositoryName, String contentid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteContentOfPage")
    public void deleteContentOfPage(PortletInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.activatePortlet")
    public void activatePortlet(PortletInfo info, boolean active) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getCategoryInfo")
    public CategoryInfo getCategoryInfo(String repositoryName, String contentid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.changeCategory")
    public void changeCategory(String repositoryName, String contentId, String newCategoryId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.changeVersionPorlet")
    public void changeVersionPorlet(PortletInfo info, String newVersion) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deletePortlet")
    public void deletePortlet(PortletInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getVersionToShow")
    public String getVersionToShow(PortletInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.createPreview")
    public String createPreview(String repositoryName, String contentId, String version) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deletePreview")
    public void deletePreview(String dir) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getNumberOfVersions")
    public int getNumberOfVersions(String repositoryName, String contentId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getCalendars")
    public CalendarInfo[] getCalendars(PortletInfo portletInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.updateCalendar")
    public void updateCalendar(PortletInfo portletInfo, CalendarInfo calendarInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.insertCalendar")
    public CalendarInfo insertCalendar(PortletInfo portletInfo, String title, String xml) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteCalendar")
    public void deleteCalendar(PortletInfo portletInfo, CalendarInfo calendarInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.activeCalendar")
    public void activeCalendar(PortletInfo portletInfo, CalendarInfo calendarInfo, boolean active) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.updatePorlet")
    public void updatePorlet(PortletInfo portletInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteVersionOfContent")
    public void deleteVersionOfContent(String repositoryName, String contentId, String versionName) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.allVersionsArePublished")
    public boolean allVersionsArePublished(String repositoryName, String contentId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.validateViewValues")
    public void validateViewValues(String repositoryName, String contentID, PropertyInfo[] properties, Object[] values) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.validateContentValues")
    public void validateContentValues(String repositoryName, PropertyInfo[] properties, Object[] values, String type) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setContentPropertyValue")
    public void setContentPropertyValue(String repositoryName, String contentID, PropertyInfo propertyInfo, String value) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getNameOfContent")
    public String getNameOfContent(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getContentProperty")
    public String getContentProperty(PropertyInfo prop, String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setContentProperties")
    public void setContentProperties(String repositoryName, String contentID, PropertyInfo[] properties, String[] values) throws Exception;
}


