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

import java.util.Date;
import org.semanticwb.xmlrpc.XmlRpcDescription;
import org.semanticwb.xmlrpc.XmlRpcMethod;

/**
 *
 * @author victor.lorenzana
 */
public interface IOfficeDocument
{

    @XmlRpcMethod(methodName = "OfficeDocument.save")
    @XmlRpcDescription(description="Save the document")
    public String save(@XmlRpcDescription(description="title") String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setTitle")
    public void setTitle(String repositoryName, String contentID, String title) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteCalendarFromCatalog")
    public void deleteCalendarFromCatalog(SiteInfo siteInfo, CalendarInfo cal) throws Exception;

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
    public String updateContent(String repositoryName, String contentId, String file,ResourceInfo[] resources,PFlow[] flows,String[] msg) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getVersions")
    public VersionInfo[] getVersions(String repositoryName, String contentId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.publishToResourceContent")
    public ResourceInfo publishToResourceContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage,PropertyInfo[] properties,String[] values) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.canPublishToResourceContent")
    public boolean canPublishToResourceContent(String type,WebPageInfo page) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getResourceProperties")
    public PropertyInfo[] getResourceProperties(String repositoryName, String contentID) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getContentProperties")
    public PropertyInfo[] getContentProperties(String repositoryName, String type) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getViewPropertyValue")
    public String getViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setViewPropertyValue")
    public void setViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setResourceProperties")
    public void setResourceProperties(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.listResources")
    public ResourceInfo[] listResources(String repositoryName, String contentid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteContentOfPage")
    public void deleteContentOfPage(ResourceInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.activateResource")
    public void activateResource(ResourceInfo info, boolean active) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getCategoryInfo")
    public CategoryInfo getCategoryInfo(String repositoryName, String contentid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.changeCategory")
    public void changeCategory(String repositoryName, String contentId, String newCategoryId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.changeVersionPorlet")
    public void changeVersionPorlet(ResourceInfo info, String newVersion) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteResource")
    public void deleteResource(ResourceInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getVersionToShow")
    public String getVersionToShow(ResourceInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.createPreview")
    public String createPreview(String repositoryName, String contentId, String version,String type) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deletePreview")
    public void deletePreview(String dir) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getNumberOfVersions")
    public int getNumberOfVersions(String repositoryName, String contentId) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getCatalogCalendars")
    public CalendarInfo[] getCatalogCalendars(SiteInfo siteInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getCalendarsOfResource")
    public CalendarInfo[] getCalendarsOfResource(ResourceInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.updateCalendar")
    public void updateCalendar(SiteInfo siteInfo,CalendarInfo calendarInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.insertCalendartoResource")
    public void insertCalendartoResource(ResourceInfo resourceInfo,CalendarInfo calendar) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteCalendar")
    public void deleteCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.activeCalendar")
    public void activeCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo, boolean active) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setEndDate")
    public void setEndDate(ResourceInfo resourceInfo, Date date) throws Exception;


    @XmlRpcMethod(methodName = "OfficeDocument.getEndDate")
    public Date getEndDate(ResourceInfo resourceInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteEndDate")
    public void deleteEndDate(ResourceInfo resourceInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.updatePorlet")
    public void updatePorlet(ResourceInfo resourceInfo) throws Exception;

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

    @XmlRpcMethod(methodName = "OfficeDocument.needsSendToPublish")
    public boolean needsSendToPublish(ResourceInfo info);

    @XmlRpcMethod(methodName = "OfficeDocument.getFlows")
    public PFlow[] getFlows(ResourceInfo info);

    @XmlRpcMethod(methodName = "OfficeDocument.sendToAuthorize")
    public void sendToAuthorize(ResourceInfo info,PFlow flow,String message) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.isInFlow")
    public boolean isInFlow(ResourceInfo info);

    @XmlRpcMethod(methodName = "OfficeDocument.isAuthorized")
    public boolean isAuthorized(ResourceInfo info);

    @XmlRpcMethod(methodName = "OfficeDocument.getElementsOfResource")
    public ElementInfo[] getElementsOfResource(ResourceInfo info) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.addElementToResource")
    public void addElementToResource(ResourceInfo info, ElementInfo ruleInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.deleteElementToResource")
    public void deleteElementToResource(ResourceInfo info, ElementInfo ruleInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.changeResourceOfWebPage")
    public void changeResourceOfWebPage(ResourceInfo info, WebPageInfo webPageInfo) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getLanguages")
    public LanguageInfo[] getLanguages(SiteInfo site) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.getTitleOfWebPage")
    public String getTitleOfWebPage(PageInfo webPageInfo,LanguageInfo laguage) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.setTitlesOfWebPage")
    public void setTitlesOfWebPage(PageInfo webPageInfo,LanguageInfo[] languages,String[] values) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.existContentOldVersion")
    public ContentInfo existContentOldVersion(String contentid,String topicmap,String topicid) throws Exception;

    @XmlRpcMethod(methodName = "OfficeDocument.canModify")
    public boolean canModify(String repositoryName, String contentID) throws Exception;
}


