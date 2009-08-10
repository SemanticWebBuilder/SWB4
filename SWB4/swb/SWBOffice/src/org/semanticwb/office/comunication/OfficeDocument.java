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
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.SWBContext;

import org.semanticwb.model.UserGroup;
import org.semanticwb.model.UserGroupRef;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.CalendarInfo;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.PFlow;

import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.ElementInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.Value;
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

    private static final SemanticProperty prop_content = OfficeResource.swboffice_content;
    private static final SemanticClass swb_office = org.semanticwb.repository.office.OfficeDocument.swboffice_OfficeDocument;
    private static final SemanticProperty PROP_JCR_DATA = org.semanticwb.repository.office.OfficeDocument.jcr_data;
    private static final String JCR_DATA = PROP_JCR_DATA.getPrefix() + ":" + PROP_JCR_DATA.getName();
    private static final SemanticProperty PROP_JCR_LASTMODIFIED = org.semanticwb.repository.office.OfficeDocument.jcr_lastModified;
    private static final String JCR_LASTMODIFIED = PROP_JCR_LASTMODIFIED.getPrefix() + ":" + PROP_JCR_LASTMODIFIED.getName();
    private static final SemanticProperty PROP_LASTMODIFIED = org.semanticwb.repository.office.OfficeContent.swboffice_lastModified;
    private static final String LASTMODIFIED = PROP_LASTMODIFIED.getPrefix() + ":" + PROP_LASTMODIFIED.getName();
    public static final String JCR_CONTENT = "jcr:content";
    private static final String JCR_FROZEN_NODE = "jcr:frozenNode";
    private static final String WORD_RESOURCE_TYPE = "WordContent";
    private static final String WORD_RESOURCE_DESCRIPTION = "WordContent";
    private static final String WORD_RESOURCE_TITLE = WORD_RESOURCE_DESCRIPTION;
    private static final String PPT_RESOURCE_TYPE = "PPTContent";
    private static final String PPT_RESOURCE_DESCRIPTION = "PPTContent";
    private static final String PPT_RESOURCE_TITLE = PPT_RESOURCE_DESCRIPTION;
    private static final String EXCEL_RESOURCE_TYPE = "ExcelContent";
    private static final String EXCEL_RESOURCE_DESCRIPTION = "ExcelContent";
    private static final String EXCEL_RESOURCE_TITLE = EXCEL_RESOURCE_DESCRIPTION;
    private static final String CONTENT_NOT_FOUND = "El contenido no se encontró en el repositorio.";
    private static Logger log = SWBUtils.getLogger(OfficeDocument.class);
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    public static String[] getOfficeTypes()
    {
        String[] getOfficeTypes = new String[3];
        getOfficeTypes[0] = WORD_RESOURCE_TYPE;
        getOfficeTypes[1] = EXCEL_RESOURCE_TYPE;
        getOfficeTypes[2] = PPT_RESOURCE_TYPE;
        return getOfficeTypes;
    }

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
                contentNode.setProperty(LASTMODIFIED, lastModified);
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
                    Node resNode = contentNode.addNode(JCR_CONTENT, swb_office.getPrefix() + ":" + swb_office.getName());
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
                    log.trace("Version created with number " + version.getName());
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
                    WebSite site = sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                            if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName) && officeResource.getVersionToShow().equals("*"))
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
            nodeContent.setProperty(LASTMODIFIED, lastModified);
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
                    WebSite site = sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                            if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName))
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
            boolean existsRep = false;
            for (String rep : loader.getWorkspaces())
            {
                if (rep.equals(repositoryName))
                {
                    existsRep = true;
                    break;
                }
            }
            if (existsRep)
            {
                session = loader.openSession(repositoryName, this.user, this.password);
                session.getNodeByUUID(contentId);
                exists = true;
            }
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
                WebSite site = sites.next();
                Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentID);
                while (it.hasNext())
                {
                    SemanticObject obj = it.next();
                    if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                    {
                        OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
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
                        WebSite site = sites.next();
                        if (info.published)
                        {
                            break;
                        }
                        Iterator<SemanticObject> itSubjects = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                        while (itSubjects.hasNext())
                        {
                            SemanticObject obj = itSubjects.next();
                            if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                            {
                                OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                                if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName) && officeResource.getVersionToShow() != null)
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
            nodeContent.getProperty(LASTMODIFIED).setValue(Calendar.getInstance());
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
            nodeContent.getProperty(LASTMODIFIED).setValue(Calendar.getInstance());
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
            return nodeContent.getProperty(LASTMODIFIED).getDate().getTime();

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
                    WebPage page = (WebPage) resourceable;
                    info = new ResourceInfo(officeResource.getId(), page.getId());
                    info.active = officeResource.getResourceBase().isActive();
                    info.description = officeResource.getResourceBase().getDescription();
                    info.title = officeResource.getResourceBase().getTitle();
                    info.version = officeResource.getVersionToShow();
                    info.page.title = page.getTitle();
                    info.page.active = page.isActive();
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
            Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentid);
            while (it.hasNext())
            {
                SemanticObject obj = it.next();
                if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                {
                    OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                    if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName))
                    {
                        Resource base = site.getResource(obj.getId());
                        if (base != null)
                        {
                            officeResource.setResourceBase(base);
                            ResourceInfo info = getResourceInfo(officeResource);
                            if (info != null)
                            {
                                listResources.add(info);
                            }
                        }
                    }
                }
            }
        }
        return listResources.toArray(new ResourceInfo[listResources.size()]);
    }

    public static void registerContents(WebSite site)
    {
        ResourceType resourceType = site.getResourceType(EXCEL_RESOURCE_TYPE);
        if (resourceType == null)
        {
            resourceType = site.createResourceType(EXCEL_RESOURCE_TYPE);
            resourceType.setCreated(new Date(System.currentTimeMillis()));
            resourceType.setDescription(EXCEL_RESOURCE_DESCRIPTION);
            resourceType.setResourceBundle(ExcelResource.class.getCanonicalName());
            resourceType.setTitle(EXCEL_RESOURCE_TITLE);
            resourceType.setResourceMode(1);
            resourceType.setResourceClassName(ExcelResource.class.getCanonicalName());
            resourceType.setUpdated(new Date(System.currentTimeMillis()));
            log.event("Resource  " + EXCEL_RESOURCE_TYPE + " registered in " + site.getTitle() + "...");
        }
        resourceType = site.getResourceType(PPT_RESOURCE_TYPE);
        if (resourceType == null)
        {
            resourceType = site.createResourceType(PPT_RESOURCE_TYPE);
            resourceType.setCreated(new Date(System.currentTimeMillis()));
            resourceType.setDescription(PPT_RESOURCE_DESCRIPTION);
            resourceType.setResourceBundle(PPTResource.class.getCanonicalName());
            resourceType.setTitle(PPT_RESOURCE_TITLE);
            resourceType.setResourceMode(1);
            resourceType.setResourceClassName(PPTResource.class.getCanonicalName());
            resourceType.setUpdated(new Date(System.currentTimeMillis()));
            log.event("Resource  " + PPT_RESOURCE_TYPE + " registered in " + site.getTitle() + "...");
        }
        resourceType = site.getResourceType(WORD_RESOURCE_TYPE);
        if (resourceType == null)
        {
            resourceType = site.createResourceType(WORD_RESOURCE_TYPE);
            resourceType.setCreated(new Date(System.currentTimeMillis()));
            resourceType.setDescription(WORD_RESOURCE_DESCRIPTION);
            resourceType.setTitle(WORD_RESOURCE_TITLE);
            resourceType.setResourceBundle(WordResource.class.getCanonicalName());
            resourceType.setResourceMode(1);
            resourceType.setResourceClassName(WordResource.class.getCanonicalName());
            resourceType.setUpdated(new Date(System.currentTimeMillis()));
            log.event("Resource  " + WORD_RESOURCE_TYPE + " registered in " + site.getTitle() + "...");
        }
    }

    public static void registerContents()
    {
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                registerContents(site);
            }
        }
    }

    public ResourceInfo publishToResourceContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage, PropertyInfo[] properties, String[] values) throws Exception
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
            Resource resource = site.createResource(id);
            OfficeResource officeResource = null;
            if (type.equalsIgnoreCase("EXCEL"))
            {
                officeResource = ExcelResource.createExcelResource(id, site);
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
                officeResource = PPTResource.createPPTResource(id, site);
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
                officeResource = WordResource.createWordResource(id, site);

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
            org.semanticwb.model.User creator = SWBContext.getAdminRepository().getUserByLogin(user);
            resource.setCreator(creator);
            officeResource.setRepositoryName(repositoryName);
            resource.setTitle(title);
            resource.setPriority(1);
            resource.setDescription(description);
            officeResource.setVersionToShow(version);
            resource.setCreated(new Date(System.currentTimeMillis()));
            resource.setUpdated(new Date(System.currentTimeMillis()));
            int i = 0;
            for (PropertyInfo prop : properties)
            {
                String value = values[i];
                Iterator<SemanticProperty> semanticProperties = officeResource.getSemanticObject().getSemanticClass().listProperties();
                while (semanticProperties.hasNext())
                {
                    SemanticProperty semanticProperty = semanticProperties.next();
                    if (semanticProperty.getURI().equals(prop.id))
                    {
                        if (semanticProperty.isBoolean())
                        {
                            boolean bvalue = Boolean.parseBoolean(value);
                            officeResource.getSemanticObject().setBooleanProperty(semanticProperty, bvalue);
                        }
                        else
                        {
                            officeResource.getSemanticObject().setProperty(semanticProperty, value);
                        }
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
            try
            {
                InputStream in = getContent(repositoryName, contentId, version);
                officeResource.loadContent(in);
                ResourceInfo info = getResourceInfo(officeResource);
                return info;
            }
            catch (Exception e)
            {
                officeResource.getResourceBase().remove();
                officeResource.getSemanticObject().remove();
                throw e;
            }


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
        OfficeResource resource = OfficeResource.getOfficeResource(resourceInfo.id, site);
        SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propertyInfo.id);
        if (prop.isBoolean())
        {
            boolean bvalue = Boolean.parseBoolean(value);
            resource.getSemanticObject().setBooleanProperty(prop, bvalue);
        }
        else
        {
            resource.getSemanticObject().setProperty(prop, value);
        }
    }

    public void setViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        OfficeResource resource = OfficeResource.getOfficeResource(resourceInfo.id, site);
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        if (prop.isBoolean())
        {
            boolean bvalue = Boolean.parseBoolean(value);
            resource.getSemanticObject().setBooleanProperty(prop, bvalue);
        }
        else
        {
            resource.getSemanticObject().setProperty(prop, value);
        }
    }

    public CalendarInfo[] getCalendarsOfResource(ResourceInfo resourceInfo) throws Exception
    {
        HashSet<CalendarInfo> getCalendarInfo = new HashSet<CalendarInfo>();
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        GenericIterator<CalendarRef> calendars = resource.listCalendarRefs();
        while (calendars.hasNext())
        {
            org.semanticwb.model.CalendarRef ref = calendars.next();
            org.semanticwb.model.Calendar cal = ref.getCalendar();
            CalendarInfo info = new CalendarInfo();
            info.id = cal.getId();
            info.xml = cal.getXml();
            info.active = ref.isActive();
            info.title = cal.getTitle();
            getCalendarInfo.add(info);
        }
        return getCalendarInfo.toArray(new CalendarInfo[getCalendarInfo.size()]);
    }

    public CalendarInfo[] getCatalogCalendars(SiteInfo siteInfo) throws Exception
    {
        HashSet<CalendarInfo> getCalendarInfo = new HashSet<CalendarInfo>();
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        Iterator<org.semanticwb.model.Calendar> calendars = site.listCalendars();
        while (calendars.hasNext())
        {
            org.semanticwb.model.Calendar cal = calendars.next();
            if (cal.getXml() != null)
            {
                CalendarInfo info = new CalendarInfo();
                info.id = cal.getId();
                info.xml = cal.getXml();
                info.active = cal.isActive();
                info.title = cal.getTitle();
                getCalendarInfo.add(info);
            }
        }
        return getCalendarInfo.toArray(new CalendarInfo[getCalendarInfo.size()]);
    }

    public String getViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        OfficeResource resource = OfficeResource.getOfficeResource(resourceInfo.id, site);
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
            officeResource = new ExcelResource();
            clazz = ExcelResource.sclass;
        }
        else if (contentType.equalsIgnoreCase("ppt"))
        {
            officeResource = new PPTResource();
            clazz = PPTResource.sclass;
        }
        else
        {
            officeResource = new WordResource();
            clazz = WordResource.sclass;
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
                        String values = propDisplay.getDisplaySelectValues("es");
                        if (values != null)
                        {
                            StringTokenizer st = new StringTokenizer(values, "|");
                            if (st.countTokens() > 0)
                            {
                                info.values = new Value[st.countTokens()];
                                int i = 0;
                                while (st.hasMoreTokens())
                                {
                                    String value = st.nextToken();
                                    int pos = value.indexOf(":");
                                    if (pos != -1)
                                    {
                                        info.values[i] = new Value();
                                        info.values[i].key = value.substring(0, pos);
                                        info.values[i].title = value.substring(pos + 1);
                                    }
                                    i++;
                                }
                            }
                        }
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

    public String createPreview(String repositoryName, String contentId, String version, String type) throws Exception
    {
        String name = UUID.randomUUID().toString();
        String dir = "/" + name;
        InputStream in = getContent(repositoryName, contentId, version);
        OfficeResource.loadContent(in, dir, type);
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
        Resource resource = site.getResource(info.id);
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
            resource.getProperty(LASTMODIFIED).setValue(Calendar.getInstance());
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
        Resource resource = site.getResource(info.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(info.id, site);
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
        OfficeResource officeResource = OfficeResource.getOfficeResource(info.id, site);
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
        Resource resource = site.getResource(info.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(info.id, site);
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

    public void updateCalendar(SiteInfo siteInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.setXml(calendarInfo.xml);
        cal.setUpdated(new Date(System.currentTimeMillis()));
    }

    public void insertCalendartoResource(ResourceInfo resourceInfo, CalendarInfo calendar) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendar.id);
        Resource resource = site.getResource(resourceInfo.id);
        boolean exists = false;
        GenericIterator<CalendarRef> refs = resource.listCalendarRefs();
        while (refs.hasNext())
        {
            CalendarRef ref = refs.next();
            if (ref.getCalendar().getId().equals(calendar.id))
            {
                exists = true;
                break;
            }
        }
        if (!exists)
        {
            CalendarRef ref = CalendarRef.createCalendarRef(site);
            ref.setCalendar(cal);
            ref.setActive(true);
            resource.addCalendarRef(ref);
        }
    }

    public void deleteCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        GenericIterator<CalendarRef> refs = resource.listCalendarRefs();
        while (refs.hasNext())
        {
            CalendarRef ref = refs.next();
            if (ref.getCalendar().getId().equals(calendarInfo.id))
            {
                ref.remove();
            }
        }
    }

    public void activeCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        GenericIterator<CalendarRef> refs = resource.listCalendarRefs();
        while (refs.hasNext())
        {
            CalendarRef ref = refs.next();
            if (ref.getCalendar().getId().equals(calendarInfo.id))
            {
                ref.setActive(active);
            }
        }
    }

    public void updatePorlet(ResourceInfo resourceInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(resourceInfo.id, site);
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
            throw new Exception("El contenido no se encontró en el repositorio.", infe);
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

    public boolean needsSendToPublish(ResourceInfo info)
    {
        boolean needsSendToPublish = false;
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        try
        {
            needsSendToPublish = SWBPortal.getPFlowManager().needAnAuthorization(resource);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e);
        }
        return needsSendToPublish;
    }

    public PFlow[] getFlows(ResourceInfo info)
    {
        HashSet<PFlow> flows = new HashSet<PFlow>();
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        for (org.semanticwb.model.PFlow flow : SWBPortal.getPFlowManager().getFlowsToSendContent(resource))
        {
            PFlow pflow = new PFlow();
            pflow.id = flow.getId();
            pflow.title = flow.getTitle();
            flows.add(pflow);
        }
        return flows.toArray(new PFlow[flows.size()]);
    }

    public void sendToAuthorize(ResourceInfo info, org.semanticwb.office.interfaces.PFlow flow, String message) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        if (resource.getPflowInstance() != null)
        {
            if (resource.getPflowInstance().getStatus() == 3 || resource.getPflowInstance().getStatus() == -1)
            {
                resource.getPflowInstance().remove();
            }
            else
            {
                throw new Exception("The content is in flow, and is nor rejected or aproveed");
            }
        }
        org.semanticwb.model.User wbuser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
        org.semanticwb.model.PFlow pflow = site.getPFlow(flow.id);
        SWBPortal.getPFlowManager().sendResourceToAuthorize(resource, pflow, message, wbuser);
    }

    public boolean isInFlow(ResourceInfo info)
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        return SWBPortal.getPFlowManager().isInFlow(resource);

    }

    public boolean isAuthorized(ResourceInfo info)
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        return SWBPortal.getPFlowManager().isAuthorized(resource);
    }

    public void setEndDate(ResourceInfo info, Date date) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        resource.setExpiration(date);
    }

    public void deleteEndDate(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        resource.setExpiration(null);
    }

    public Date getEndDate(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        return resource.getExpiration();
    }

    public void deleteCalendarFromCatalog(SiteInfo siteInfo, CalendarInfo calendarIndo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarIndo.id);
        site.removeCalendar(calendarIndo.id);
    }

    public ElementInfo[] getElementsOfResource(ResourceInfo info) throws Exception
    {
        HashSet<ElementInfo> rules = new HashSet<ElementInfo>();
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        {
            GenericIterator<RuleRef> refs = resource.listRuleRefs();
            while (refs.hasNext())
            {
                RuleRef ref = refs.next();
                Rule rule = ref.getRule();
                ElementInfo rinfo = new ElementInfo();
                rinfo.id = rule.getId();
                rinfo.title = rule.getTitle();
                rinfo.active = ref.isActive();
                rinfo.type = Rule.sclass.getName();
                rules.add(rinfo);
            }
        }
        {
            GenericIterator<RoleRef> refs = resource.listRoleRefs();
            while (refs.hasNext())
            {
                RoleRef ref = refs.next();
                Role role = ref.getRole();
                ElementInfo rinfo = new ElementInfo();
                rinfo.id = role.getId();
                rinfo.title = role.getTitle();
                rinfo.active = ref.isActive();
                rinfo.type = Role.sclass.getName();
                rules.add(rinfo);
            }
        }
        {
            GenericIterator<UserGroupRef> refs = resource.listUserGroupRefs();
            while (refs.hasNext())
            {
                UserGroupRef ref = refs.next();
                UserGroup userGroupRef = ref.getUserGroup();
                ElementInfo rinfo = new ElementInfo();
                rinfo.id = userGroupRef.getId();
                rinfo.title = userGroupRef.getTitle();
                rinfo.active = ref.isActive();
                rinfo.type = Role.sclass.getName();
                rules.add(rinfo);
            }
        }
        return rules.toArray(new ElementInfo[rules.size()]);
    }

    public void addElementToResource(ResourceInfo info, ElementInfo ruleInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = Resource.getResource(info.id, site);
        if (ruleInfo.type.equals(Rule.sclass.getName()))
        {
            GenericIterator<RuleRef> refs = resource.listRuleRefs();
            boolean exists = false;
            while (refs.hasNext())
            {
                RuleRef ref = refs.next();
                Rule rule = ref.getRule();
                if (rule.getId().equals(ruleInfo.id))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                RuleRef ref = RuleRef.createRuleRef(site);
                ref.setActive(ruleInfo.active);
                ref.setRule(Rule.getRule(ruleInfo.id, site));
                resource.addRuleRef(ref);
            }
        }
        if (ruleInfo.type.equals(Role.sclass.getName()))
        {
            GenericIterator<RoleRef> refs = resource.listRoleRefs();
            boolean exists = false;
            while (refs.hasNext())
            {
                RoleRef ref = refs.next();
                Role rule = ref.getRole();
                if (rule.getId().equals(ruleInfo.id))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                RoleRef ref = RoleRef.createRoleRef(site);
                ref.setActive(ruleInfo.active);
                ref.setRole(site.getUserRepository().getRole(ruleInfo.id));
                resource.addRoleRef(ref);
            }
        }
        if (ruleInfo.type.equals(UserGroup.sclass.getName()))
        {
            GenericIterator<UserGroupRef> refs = resource.listUserGroupRefs();
            boolean exists = false;
            while (refs.hasNext())
            {
                UserGroupRef ref = refs.next();
                UserGroup rule = ref.getUserGroup();
                if (rule.getId().equals(ruleInfo.id))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                UserGroupRef ref = UserGroupRef.createUserGroupRef(site);
                ref.setActive(ruleInfo.active);
                ref.setUserGroup(site.getUserRepository().getUserGroup(ruleInfo.id));
                resource.addUserGroupRef(ref);
            }
        }

    }

    public void deleteElementToResource(ResourceInfo info, ElementInfo ruleInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        if (ruleInfo.type.equals(Rule.sclass.getName()))
        {
            GenericIterator<RuleRef> refs = resource.listRuleRefs();
            while (refs.hasNext())
            {
                RuleRef ref = refs.next();
                Rule rule = ref.getRule();
                if (rule.getId().equals(ruleInfo.id))
                {
                    resource.removeRuleRef(ref);
                    break;
                }
            }
        }
        if (ruleInfo.type.equals(Role.sclass.getName()))
        {
            GenericIterator<RoleRef> refs = resource.listRoleRefs();
            while (refs.hasNext())
            {
                RoleRef ref = refs.next();
                Role rule = ref.getRole();
                if (rule.getId().equals(ruleInfo.id))
                {
                    resource.removeRoleRef(ref);
                    break;
                }
            }
        }
        if (ruleInfo.type.equals(UserGroup.sclass.getName()))
        {
            GenericIterator<UserGroupRef> refs = resource.listUserGroupRefs();
            while (refs.hasNext())
            {
                UserGroupRef ref = refs.next();
                UserGroup rule = ref.getUserGroup();
                if (rule.getId().equals(ruleInfo.id))
                {
                    resource.removeUserGroupRef(ref);
                    break;
                }
            }
        }
    }

    public void changeResourceOfWebPage(ResourceInfo info, WebPageInfo webPageInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        WebPage oldWebPage = site.getWebPage(info.page.id);
        WebPage newpageofResource = site.getWebPage(webPageInfo.id);
        newpageofResource.addResource(resource);
        oldWebPage.removeResource(resource);
    }
}

