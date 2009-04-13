/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBContext;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.CalendarInfo;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.PageInfo;

import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.OfficeManager;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.repository.WorkspaceNotFoudException;
import org.semanticwb.resource.office.sem.ExcelResource;
import org.semanticwb.resource.office.sem.OfficeResource;
import org.semanticwb.resource.office.sem.PPTResource;
import org.semanticwb.resource.office.sem.WordResource;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeDocument extends XmlRpcObject implements IOfficeDocument
{
    
    private static final String JCR_FROZEN_NODE = "jcr:frozenNode";
    private static final String WORD_RESOURCE_TYPE = "word_resource";
    private static final String WORD_RESOURCE_DESCRIPTION = "Recurso Word";    
    private static final String WORD_RESOURCE_TITLE = WORD_RESOURCE_DESCRIPTION;
    private static final String PPT_RESOURCE_TYPE = "ppt_resource";
    private static final String PPT_RESOURCE_DESCRIPTION = "Recurso Power Point";
    
    private static final String PPT_RESOURCE_TITLE = PPT_RESOURCE_DESCRIPTION;
    private static final String EXCEL_RESOURCE_TYPE = "excel_resource";
    private static final String EXCEL_RESOURCE_DESCRIPTION = "Recurso Excel";
    
    private static final String EXCEL_RESOURCE_TITLE = EXCEL_RESOURCE_DESCRIPTION;
    private static final String CONTENT_NOT_FOUND = "El contenido no se encontr� en el repositorio.";
    public static final String JCR_CONTENT = "jcr:content";
    private static final String JCR_DATA = "jcr:data";
    private static final String JCR_LASTMODIFIED = "jcr:lastModified";
    private static final String CM_LASTMODIFIED = "cm:lastModified";
    private static Logger log = SWBUtils.getLogger(OfficeDocument.class);
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();
    private static final String NL = System.getProperty("line.separator");

    public String save(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values) throws Exception
    {
        Session session = null;
        Node categoryNode = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            categoryNode = session.getNodeByUUID(categoryID);
            try
            {
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                Node contentNode = categoryNode.addNode(nodeType, nodeType);
                contentNode.setProperty(cm_title, title);
                String cm_type = loader.getOfficeManager(repositoryName).getPropertyType();
                String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
                contentNode.setProperty(cm_type, type);
                contentNode.setProperty(cm_description, description);
                Calendar lastModified = Calendar.getInstance();
                lastModified.setTimeInMillis(System.currentTimeMillis());
                contentNode.setProperty(CM_LASTMODIFIED, lastModified);
                if (properties != null)
                {
                    int i = 0;
                    for (PropertyInfo prop : properties)
                    {
                        String value = values[i];
                        contentNode.setProperty(prop.id, value);
                        i++;
                    }
                }
                for (Part part : requestParts)
                {
                    String mimeType = DEFAULT_MIME_TYPE;
                    if (config != null && config.getServletContext() != null)
                    {
                        mimeType = config.getServletContext().getMimeType(part.getName());
                        if (mimeType == null)
                        {
                            mimeType = DEFAULT_MIME_TYPE;
                        }
                    }
                    Node resNode = contentNode.addNode(JCR_CONTENT, "cm:OfficeDocument");
                    resNode.addMixin("mix:versionable");
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    resNode.setProperty(cm_file, file);
                    String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                    resNode.setProperty(cm_user, this.user);
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.setProperty(JCR_DATA, in);
                    in.close();
                    resNode.setProperty(JCR_LASTMODIFIED, lastModified);
                    categoryNode.save();
                    Version version = resNode.checkin();
                    log.debug("Version created with number " + version.getName());
                }

                return contentNode.getUUID();
            }
            catch (ItemExistsException e)
            {
                throw new Exception("Ya existe un contenido con ese nombre", e);
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
                throw e;
            }
            finally
            {
                //categoryNode.unlock();
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("La categoria indica no existe", infe);
        }
        catch (LockException e)
        {
            e.printStackTrace(System.out);
            throw e;
        }
        catch (Exception e)
        {
            if (categoryNode != null)
            {
                //categoryNode.unlock();
            }
            e.printStackTrace(System.out);
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public int getNumberOfVersions(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node nodeRes = nodeContent.getNode(JCR_CONTENT);
            VersionHistory history = nodeRes.getVersionHistory();
            // the version minus the root version
            return (int) history.getAllVersions().getSize() - 1;
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public boolean allVersionsArePublished(String repositoryName, String contentId) throws Exception
    {
        for (VersionInfo version : getVersions(repositoryName, contentId))
        {
            if (!version.published)
            {
                return false;
            }
        }
        return true;
    }

    public void deleteVersionOfContent(String repositoryName, String contentId, String versionName) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            String lastVersion = getLastVersionOfcontent(repositoryName, contentId);
            VersionHistory history = resContent.getVersionHistory();
            Version version = history.getVersion(versionName);
            version.remove();
            history.save();
            nodeContent.save();
            if (lastVersion != null && lastVersion.equals(versionName))
            {
                Iterator<WebSite> sites = SWBContext.listWebSites();
                while (sites.hasNext())
                {
                    WebSite site=sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(OfficeResource.office_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {                            
                            OfficeResource officeResource = new OfficeResource(obj);
                            if(officeResource.getRepositoryName().equals(repositoryName) && officeResource.getVersionToShow().equals("*"))
                            {
                                InputStream in = getContent(repositoryName, contentId, officeResource.getVersionToShow());
                                officeResource.loadContent(in);
                            }
                        }
                    }
                }
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    /**
     * Update a Content
     * @param contentId ID of the content, the id is a UUID
     * @return The version name created
     * @throws java.lang.Exception
     */
    public String updateContent(String repositoryName, String contentId, String file) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);

            String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
            String cm_user = loader.getOfficeManager(repositoryName).getUserType();
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(System.currentTimeMillis());
            nodeContent.setProperty(CM_LASTMODIFIED, lastModified);
            nodeContent.save();
            try
            {
                if (requestParts.size() == 0)
                {
                    throw new Exception("The content can not be updated, The content document was not found");
                }
                else
                {
                    Part part = requestParts.iterator().next();
                    String mimeType = DEFAULT_MIME_TYPE;
                    if (this.config != null && this.config.getServletContext() != null)
                    {
                        mimeType = this.config.getServletContext().getMimeType(part.getName());
                    }
                    if (mimeType == null)
                    {
                        mimeType = DEFAULT_MIME_TYPE;
                    }
                    Node resNode = nodeContent.getNode(JCR_CONTENT);
                    resNode.checkout();
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    resNode.setProperty(cm_file, file);
                    resNode.setProperty(cm_user, this.user);
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.getProperty(JCR_DATA).setValue(in);
                    resNode.setProperty(JCR_LASTMODIFIED, lastModified);
                    session.save();
                    Version version = resNode.checkin();
                    return version.getName();
                }


            }
            catch (RepositoryException e)
            {
                e.printStackTrace(System.out);
                throw e;
            }
            finally
            {

                // actualiza version
                Iterator<WebSite> sites = SWBContext.listWebSites();
                while (sites.hasNext())
                {
                    Iterator<SemanticObject> it = sites.next().getSemanticObject().getModel().listSubjects(OfficeResource.office_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = new OfficeResource(obj);
                            if (officeResource.getRepositoryName().equals(repositoryName))
                            {
                                InputStream in = getContent(repositoryName, contentId, officeResource.getVersionToShow());
                                officeResource.loadContent(in);
                            }
                        }
                    }
                }
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }

    }

    public String getPath(String contentID)
    {
        return "/";
    }

    public boolean exists(String repositoryName, String contentId) throws Exception
    {
        boolean exists = false;
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            session.getNodeByUUID(contentId);
            exists = true;
        }
        catch (WorkspaceNotFoudException wsnf)
        {
            exists = false;
        }
        catch (ItemNotFoundException pnfe)
        {
            exists = false;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return exists;

    }

    public void sendToAuthorize()
    {
    }

    public void setActive(String contentID, boolean active)
    {
    }

    public boolean getActive(String contentID)
    {
        return false;
    }

    public void delete(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            Node parent = nodeContent.getParent();
            Iterator<WebSite> sites = SWBContext.listWebSites();
            while (sites.hasNext())
            {
                WebSite site=sites.next();
                Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(OfficeResource.office_content, contentID);
                while (it.hasNext())
                {
                    SemanticObject obj = it.next();
                    if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                    {
                        OfficeResource officeResource = new OfficeResource(obj);
                        site.removeResource(officeResource.getId());
                    }
                }
            }
            nodeContent.remove();
            parent.save();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    private String getLastVersionOfcontent(Session session, String repositoryName, String contentId) throws Exception
    {
        String getLastVersionOfcontent = null;
        ArrayList<Version> versions = new ArrayList<Version>();
        try
        {
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            VersionIterator it = resContent.getVersionHistory().getAllVersions();
            while (it.hasNext())
            {
                Version version = it.nextVersion();
                if (!version.getName().equals("jcr:rootVersion"))
                {
                    versions.add(version);
                }
            }
            for (Version version : versions)
            {
                if (getLastVersionOfcontent == null)
                {
                    getLastVersionOfcontent = version.getName();
                }
                else
                {
                    try
                    {
                        float currentVersion = Float.parseFloat(version.getName());
                        if (Float.parseFloat(getLastVersionOfcontent) < currentVersion)
                        {
                            getLastVersionOfcontent = version.getName();
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return getLastVersionOfcontent;
    }

    private String getLastVersionOfcontent(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            return getLastVersionOfcontent(session, repositoryName, contentId);
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public VersionInfo[] getVersions(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        ArrayList<VersionInfo> versions = new ArrayList<VersionInfo>();
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            VersionIterator it = resContent.getVersionHistory().getAllVersions();
            while (it.hasNext())
            {
                Version version = it.nextVersion();
                if (!version.getName().equals("jcr:rootVersion"))
                {
                    VersionInfo info = new VersionInfo();
                    info.contentId = contentId;
                    info.nameOfVersion = version.getName();
                    info.created = version.getProperty("jcr:created").getDate().getTime();
                    String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                    info.user = version.getNode(JCR_FROZEN_NODE).getProperty(cm_user).getString();
                    info.published = false;
                    Iterator<WebSite> sites = SWBContext.listWebSites();
                    while (sites.hasNext())
                    {
                        if (info.published)
                        {
                            break;
                        }
                        Iterator<SemanticObject> itSubjects = sites.next().getSemanticObject().getModel().listSubjects(OfficeResource.office_content, contentId);
                        while (itSubjects.hasNext())
                        {
                            SemanticObject obj = itSubjects.next();
                            if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                            {
                                OfficeResource officeResource = new OfficeResource(obj);
                                if (officeResource.getRepositoryName().equals(repositoryName) && officeResource.getVersionToShow() != null)
                                {
                                    if (officeResource.getVersionToShow().equals("*"))
                                    {
                                        String maxversion = getLastVersionOfcontent(repositoryName, contentId);
                                        if (maxversion != null)
                                        {
                                            if (maxversion.equals(info.nameOfVersion))
                                            {
                                                info.published = true;
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (officeResource.getVersionToShow().equals(info.nameOfVersion))
                                        {
                                            info.published = true;
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                    versions.add(info);
                }
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return versions.toArray(new VersionInfo[versions.size()]);
    }

    public void setTitle(String repositoryName, String contentID, String title) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();

            nodeContent.setProperty(cm_title, title);
            nodeContent.getProperty(CM_LASTMODIFIED).setValue(Calendar.getInstance());
            nodeContent.save();


        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getTitle(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            return nodeContent.getProperty(cm_title).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getDescription(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
            return nodeContent.getProperty(cm_description).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void setDescription(String repositoryName, String contentID, String description) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();

            nodeContent.setProperty(cm_description, description);
            nodeContent.getProperty(CM_LASTMODIFIED).setValue(Calendar.getInstance());
            nodeContent.save();



        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public Date getLastUpdate(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            return nodeContent.getProperty(CM_LASTMODIFIED).getDate().getTime();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public static ResourceInfo getResourceInfo(OfficeResource officeResource)
    {
        ResourceInfo info = null;
        GenericIterator<Resourceable> portlables = officeResource.getResourceBase().listResourceables();
        if (portlables != null)
        {
            while (portlables.hasNext())
            {
                Resourceable resourceable = portlables.next();
                if (resourceable != null && resourceable instanceof WebPage)
                {
                    info = new ResourceInfo();
                    info.active = officeResource.getResourceBase().isActive();
                    info.description = officeResource.getResourceBase().getDescription();
                    info.id = officeResource.getId();
                    info.title = officeResource.getResourceBase().getTitle();
                    info.version = officeResource.getVersionToShow();
                    info.page = new PageInfo();
                    WebPage page = (WebPage) resourceable;
                    info.page.title = page.getTitle();
                    info.page.id = page.getId();
                    info.page.description = page.getDescription();
                    info.page.site = new SiteInfo();
                    info.page.site.title = page.getWebSite().getTitle();
                    info.page.site.description = page.getWebSite().getDescription();
                    info.page.url = page.getUrl();
                    info.page.site.id = page.getWebSite().getId();
                }
            }
        }
        return info;
    }

    public ResourceInfo[] listResources(String repositoryName, String contentid) throws Exception
    {
        ArrayList<ResourceInfo> listResources = new ArrayList<ResourceInfo>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(OfficeResource.office_content, contentid);
            while (it.hasNext())
            {
                SemanticObject obj = it.next();
                if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                {
                    OfficeResource officeResource = new OfficeResource(obj);
                    if (officeResource.getRepositoryName().equals(repositoryName))
                    {
                        ResourceInfo info = getResourceInfo(officeResource);
                        if (info != null)
                        {
                            listResources.add(info);
                        }
                    }
                }
            }
        }
        return listResources.toArray(new ResourceInfo[listResources.size()]);
    }

    public ResourceInfo publishToResourceContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage,PropertyInfo[] properties,String[] values) throws Exception
    {
        WebSite site = SWBContext.getWebSite(webpage.siteID);
        WebPage page = site.getWebPage(webpage.id);

        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node contentNode = session.getNodeByUUID(contentId);
            String cm_officeType = loader.getOfficeManager(repositoryName).getPropertyType();
            String type = contentNode.getProperty(cm_officeType).getString();            
            String id = UUID.randomUUID().toString();
            ResourceType resourceType = null;
            Resource resource=site.createResource(id);
            OfficeResource officeResource=null;
            if (type.equalsIgnoreCase("EXCEL"))
            {
                officeResource=new ExcelResource();
                resourceType = site.getResourceType(EXCEL_RESOURCE_TYPE);
                if (resourceType == null)
                {
                    resourceType = site.createResourceType(EXCEL_RESOURCE_TYPE);
                    resourceType.setCreated(new Date(System.currentTimeMillis()));
                    resourceType.setDescription(EXCEL_RESOURCE_DESCRIPTION);
                    resourceType.setResourceBundle(ExcelResource.class.getSimpleName());
                    resourceType.setTitle(EXCEL_RESOURCE_TITLE);
                    resourceType.setResourceMode(1);
                    resourceType.setResourceClassName(ExcelResource.class.getCanonicalName());
                    resourceType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            else if (type.equalsIgnoreCase("PPT"))
            {
                officeResource=new PPTResource();
                resourceType = site.getResourceType(PPT_RESOURCE_TYPE);
                if (resourceType == null)
                {
                    resourceType = site.createResourceType(PPT_RESOURCE_TYPE);
                    resourceType.setCreated(new Date(System.currentTimeMillis()));
                    resourceType.setDescription(PPT_RESOURCE_DESCRIPTION);
                    resourceType.setResourceBundle(PPTResource.class.getSimpleName());
                    resourceType.setTitle(PPT_RESOURCE_TITLE);
                    resourceType.setResourceMode(1);
                    resourceType.setResourceClassName(PPTResource.class.getCanonicalName());
                    resourceType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            else
            {
                officeResource=new WordResource();

                resourceType = site.getResourceType(WORD_RESOURCE_TYPE);
                if (resourceType == null)
                {
                    resourceType = site.createResourceType(WORD_RESOURCE_TYPE);
                    resourceType.setCreated(new Date(System.currentTimeMillis()));
                    resourceType.setDescription(WORD_RESOURCE_DESCRIPTION);
                    resourceType.setTitle(WORD_RESOURCE_TITLE);
                    resourceType.setResourceBundle(WordResource.class.getSimpleName());
                    resourceType.setResourceMode(1);
                    resourceType.setResourceClassName(WordResource.class.getCanonicalName());
                    resourceType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            officeResource.setResourceBase(resource);
            resource.setResourceType(resourceType);
            officeResource.setContent(contentId);
            resource.setResourceType(resourceType);
            officeResource.setRepositoryName(repositoryName);
            resource.setTitle(title);
            resource.setPriority(1);
            resource.setDescription(description);
            officeResource.setVersionToShow(version);
            resource.setCreated(new Date(System.currentTimeMillis()));
            resource.setUpdated(new Date(System.currentTimeMillis()));
            int i=0;
            for(PropertyInfo prop : properties)
            {
                String value=values[i];
                Iterator<SemanticProperty> semanticProperties=resource.getSemanticObject().getSemanticClass().listProperties();
                while(semanticProperties.hasNext())
                {
                    SemanticProperty semanticProperty=semanticProperties.next();
                    if(semanticProperty.getURI().equals(prop.id))
                    {
                        resource.getSemanticObject().setProperty(semanticProperty, value);
                    }
                }
                i++;
            }
            try
            {
                page.addResource(resource);
            }
            catch (Exception e)
            {
                log.error(e);
            }
            InputStream in = getContent(repositoryName, contentId, version);
            officeResource.loadContent(in);
            ResourceInfo info = getResourceInfo(officeResource);
            return info;

        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void setResourceProperties(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propertyInfo.id);
        resource.getSemanticObject().setProperty(prop, value);
    }

    public void setViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource=site.getResource(resourceInfo.id);       
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        resource.getSemanticObject().setProperty(prop, value);
    }

    public CalendarInfo[] getCalendars(ResourceInfo resourceInfo) throws Exception
    {
        ArrayList<CalendarInfo> getCalendarInfo = new ArrayList<CalendarInfo>();
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource=site.getResource(resourceInfo.id);
        OfficeResource officeResource = new OfficeResource();
        officeResource.setResourceBase(resource);
        Iterator<org.semanticwb.model.Calendar> calendars = resource.listCalendars();
        while (calendars.hasNext())
        {
            org.semanticwb.model.Calendar cal = calendars.next();
            CalendarInfo info = new CalendarInfo();
            info.id = cal.getId();
            info.xml = cal.getXml();
            info.active = cal.isActive();
            info.title = cal.getTitle();
            getCalendarInfo.add(info);
        }
        return getCalendarInfo.toArray(new CalendarInfo[getCalendarInfo.size()]);
    }

    public String getViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource=site.getResource(resourceInfo.id);
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        return resource.getSemanticObject().getProperty(prop);
    }

    public void validateViewValues(String repositoryName, String contentID, PropertyInfo[] properties, Object[] values) throws Exception
    {
        String contentType = getContentType(repositoryName, contentID);
        OfficeResource officeResource;
        SemanticClass clazz;
        if (contentType.equalsIgnoreCase("excel"))
        {
            officeResource=new ExcelResource();
            clazz=ExcelResource.sclass;
        }
        else if (contentType.equalsIgnoreCase("ppt"))
        {
            officeResource=new PPTResource();
            clazz=PPTResource.sclass;
        }
        else
        {
            officeResource=new WordResource();
            clazz=WordResource.sclass;
        }
        if (properties.length == values.length)
        {
            HashMap<SemanticProperty, Object> valuesToValidate = new HashMap<SemanticProperty, Object>();
            int i = 0;
            for (PropertyInfo propertyInfo : properties)
            {
                Object value = values[i];
                Iterator<SemanticProperty> propertiesClazz = clazz.listProperties();
                while (propertiesClazz.hasNext())
                {
                    SemanticProperty prop = propertiesClazz.next();
                    if (prop.getURI().equals(propertyInfo.id))
                    {
                        valuesToValidate.put(prop, value);
                    }
                }
                i++;
            }
            officeResource.validateViewPropertyValues(valuesToValidate);           
        }
        else
        {
            throw new Exception("The number of properties is not equals to the number of values");
        }
    }

    private boolean isSuperProperty(SemanticProperty prop, SemanticClass clazz)
    {
        Iterator<SemanticClass> classes = clazz.listSuperClasses();
        while (classes.hasNext())
        {
            SemanticClass superClazz = classes.next();
            if (superClazz.isSWBClass())
            {
                Iterator<SemanticProperty> propertiesClazz = superClazz.listProperties();
                while (propertiesClazz.hasNext())
                {
                    SemanticProperty propSuperClazz = propertiesClazz.next();
                    if (propSuperClazz.getURI().equals(prop.getURI()))
                    {
                        return true;
                    }
                    else
                    {
                        boolean res = isSuperProperty(prop, superClazz);
                        if (res == true)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public PropertyInfo[] getContentProperties(String repositoryName, String type) throws Exception
    {
        return loader.getOfficeManager(repositoryName).getContentProperties(type);
    }

    public PropertyInfo[] getResourceProperties(String repositoryName, String contentID) throws Exception
    {
        ArrayList<PropertyInfo> properties = new ArrayList<PropertyInfo>();
        String type = getContentType(repositoryName, contentID);
        SemanticClass clazz = null;
        if (type.equalsIgnoreCase("excel"))
        {
            clazz = ExcelResource.sclass;
        }
        else if (type.equalsIgnoreCase("ppt"))
        {
            clazz = PPTResource.sclass;
        }
        else
        {
            clazz = WordResource.sclass;
        }
        Iterator<SemanticProperty> propertiesClazz = clazz.listProperties();
        while (propertiesClazz.hasNext())
        {
            SemanticProperty prop = propertiesClazz.next();

            if (!isSuperProperty(prop, clazz) && prop.isDataTypeProperty() && !prop.isBinary() && prop.getPrefix() != null)
            {

                SemanticObject displayObj = prop.getDisplayProperty();
                if (displayObj != null)
                {
                    DisplayProperty propDisplay = new DisplayProperty(displayObj);
                    if (!propDisplay.isHidden() && !propDisplay.isDisabled())
                    {
                        PropertyInfo info = new PropertyInfo();
                        info.id = prop.getURI();
                        info.isRequired = prop.isRequired();
                        info.title = prop.getDisplayName();
                        if (prop.isString())
                        {
                            info.type = "String";
                            properties.add(info);
                        }
                        else if (prop.isBoolean())
                        {
                            info.type = "Boolean";
                            properties.add(info);
                        }
                        else if (prop.isInt())
                        {
                            info.type = "Integer";
                            properties.add(info);
                        }
                    }
                }
            }
        }
        return properties.toArray(new PropertyInfo[properties.size()]);
    }

    public void deleteContentOfPage(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.id);
        site.removeResource(info.id);
    }

    public InputStream getContent(String repositoryName, String contentId, String version) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            if (version.equals("*"))
            {
                String lastVersion = getLastVersionOfcontent(repositoryName, contentId);
                Version versionNode = resContent.getVersionHistory().getVersion(lastVersion);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);                    
                    return frozenNode.getProperty(JCR_DATA).getStream();
                }
                else
                {
                    return null;
                }
            }
            else
            {
                Version versionNode = resContent.getVersionHistory().getVersion(version);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);                    
                    return frozenNode.getProperty(JCR_DATA).getStream();
                }
                else
                {
                    return null;
                }
            }

        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentFile(Session session, String repositoryName, String contentId, String version) throws Exception
    {
        try
        {
            Node nodeContent = session.getNodeByUUID(contentId);
            String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            if (version.equals("*"))
            {
                String lastVersion = getLastVersionOfcontent(session, repositoryName, contentId);
                Version versionNode = resContent.getVersionHistory().getVersion(lastVersion);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty(cm_file).getString();
                }
                else
                {
                    return null;
                }

            }
            else
            {
                Version versionNode = resContent.getVersionHistory().getVersion(version);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty(cm_file).getString();
                }
                else
                {
                    return null;
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentFile(String repositoryName, String contentId, String version, org.semanticwb.model.User user) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, user);
            return getContentFile(session, repositoryName, contentId, version);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentFile(String repositoryName, String contentId, String version) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            return getContentFile(session, repositoryName, contentId, version);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String createPreview(String repositoryName, String contentId, String version) throws Exception
    {
        String name = UUID.randomUUID().toString();
        String dir = "/" + name;
        InputStream in = getContent(repositoryName, contentId, version);
        OfficeResource.loadContent(in, dir);
        in.close();
        return name;
    }

    public String getContentType(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            String cm_Type = loader.getOfficeManager(repositoryName).getPropertyType();
            return nodeContent.getProperty(cm_Type).getString();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void activateResource(ResourceInfo info, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource=site.getResource(info.id);
        resource.setActive(active);
    }

    public String getCategory(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            Node parent = nodeContent.getParent();
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            return parent.getProperty(cm_title).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void changeCategory(String repositoryName, String contentId, String newCategoryId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node newCategory = session.getNodeByUUID(newCategoryId);
            session.move(nodeContent.getPath(), newCategory.getPath());
            Node resource = nodeContent.getNode(JCR_CONTENT);
            resource.getProperty(CM_LASTMODIFIED).setValue(Calendar.getInstance());
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void changeVersionPorlet(ResourceInfo info, String newVersion) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource=site.getResource(info.id);

        OfficeResource officeResource = new OfficeResource();
        officeResource.setResourceBase(resource);
        officeResource.setVersionToShow(newVersion);
        InputStream in = getContent(officeResource.getRepositoryName(), officeResource.getContent(), newVersion);
        officeResource.loadContent(in);

    }

    public CategoryInfo getCategoryInfo(String repositoryName, String contentid) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentid);
            Node parent = nodeContent.getParent();
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
            CategoryInfo info = new CategoryInfo();
            info.UDDI = parent.getUUID();
            info.childs = 0;
            info.description = parent.getProperty(cm_description).getString();
            info.title = parent.getProperty(cm_title).getString();
            return info;
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void deleteResource(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        OfficeResource officeResource=new OfficeResource();
        officeResource.setResourceBase(resource);
        try
        {
            officeResource.clean();
        }
        catch (Exception e)
        {
            log.event(e);
        }
        finally
        {
            site.removeResource(officeResource.getId());
        }
    }

    public String getVersionToShow(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource=site.getResource(info.id);
        OfficeResource officeResource = new OfficeResource();
        officeResource.setResourceBase(resource);
        return officeResource.getVersionToShow();
    }

    public void deletePreview(String dir) throws Exception
    {
        if (!dir.startsWith("/"))
        {
            dir = "/" + dir;
        }
        OfficeResource.clean(dir);
    }

    public void updateCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.setXml(calendarInfo.xml);
        cal.setUpdated(new Date(System.currentTimeMillis()));
    }

    public CalendarInfo insertCalendar(ResourceInfo resourceInfo, String title, String xml) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.createCalendar();
        cal.setXml(xml);
        cal.setTitle(title);
        cal.setCreated(new Date(System.currentTimeMillis()));
        cal.setUpdated(new Date(System.currentTimeMillis()));
        CalendarInfo info = new CalendarInfo();
        info.title = title;
        info.id = cal.getId();
        info.active = cal.isActive();
        info.xml = cal.getXml();
        return info;
    }

    public void deleteCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.remove();
    }

    public void activeCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.setActive(active);
    }

    public void updatePorlet(ResourceInfo resourceInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource=site.getResource(resourceInfo.id);
        OfficeResource officeResource = new OfficeResource();
        officeResource.setResourceBase(resource);
        resource.setTitle(resourceInfo.title);
        resource.setDescription(resourceInfo.description);
        resource.setActive(resourceInfo.active);
        officeResource.setVersionToShow(resourceInfo.version);
    }

    public void validateContentValues(String repositoryName, PropertyInfo[] properties, Object[] values, String type) throws Exception
    {
        OfficeManager manager = loader.getOfficeManager(repositoryName);
        manager.validateContentValues(properties, values, type);
    }

    public void setContentPropertyValue(String repositoryName, String contentID, PropertyInfo propertyInfo, String value) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            nodeContent.setProperty(propertyInfo.id, value);
            nodeContent.save();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void setContentProperties(String repositoryName, String contentID, PropertyInfo[] properties, String[] values) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            int i = 0;
            for (PropertyInfo propertyInfo : properties)
            {
                String value = values[i];
                nodeContent.setProperty(propertyInfo.id, value);
                i++;
            }
            nodeContent.save();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getNameOfContent(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            return nodeContent.getName();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentProperty(PropertyInfo prop, String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            return nodeContent.getProperty(prop.id).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentr� en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }
}

