/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Portletable;
import org.semanticwb.model.SWBContext;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.CalendarInfo;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.PageInfo;
import org.semanticwb.office.interfaces.PortletInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portlet.office.ExcelPortlet;
import org.semanticwb.portlet.office.OfficePortlet;
import org.semanticwb.portlet.office.PPTPortlet;
import org.semanticwb.portlet.office.WordPortlet;
import org.semanticwb.repository.OfficeManager;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.repository.WorkspaceNotFoudException;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeDocument extends XmlRpcObject implements IOfficeDocument
{

    private static final String JCR_FROZEN_NODE = "jcr:frozenNode";
    private static final String WORD_PORTLET_TYPE = "word_resource";
    private static final String WORD_PORTLET_DESCRIPTION = "Recurso Word";
    private static final String WORD_PORTLET_CLASS = "org.semanticwb.portal.resources.office.WordResource";
    private static final String WORD_PORTLET_TITLE = WORD_PORTLET_DESCRIPTION;
    private static final String PPT_PORTLET_TYPE = "ppt_resource";
    private static final String PPT_PORTLET_DESCRIPTION = "Recurso Power Point";
    private static final String PPT_PORTLET_CLASS = "org.semanticwb.portal.resources.office.PowerPointResource";
    private static final String PPT_PORTLET_TITLE = PPT_PORTLET_DESCRIPTION;
    private static final String EXCEL_PORTLET_TYPE = "excel_resource";
    private static final String EXCEL_PORTLET_DESCRIPTION = "Recurso Excel";
    private static final String EXCEL_PORTLET_CLASS = "org.semanticwb.portal.resources.office.ExcelResource";
    private static final String EXCEL_PORTLET_TITLE = EXCEL_PORTLET_DESCRIPTION;
    private static final String CONTENT_NOT_FOUND = "El contenido no se encontró en el repositorio.";
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
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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
                    Iterator<SemanticObject> it = sites.next().getSemanticObject().getModel().listSubjects(OfficePortlet.swbrep_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficePortlet.sclass) || obj.getSemanticClass().equals(OfficePortlet.sclass))
                        {
                            OfficePortlet officePortlet = new OfficePortlet(obj);
                            if (officePortlet.getRepositoryName().equals(repositoryName) && officePortlet.getVersionToShow().equals("*"))
                            {
                                InputStream in = getContent(repositoryName, contentId, officePortlet.getVersionToShow());
                                officePortlet.loadContent(in);
                            }
                        }
                    }
                }
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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
                    Iterator<SemanticObject> it = sites.next().getSemanticObject().getModel().listSubjects(OfficePortlet.swbrep_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficePortlet.sclass) || obj.getSemanticClass().equals(OfficePortlet.sclass))
                        {
                            OfficePortlet officePortlet = new OfficePortlet(obj);
                            if (officePortlet.getRepositoryName().equals(repositoryName))
                            {
                                InputStream in = getContent(repositoryName, contentId, officePortlet.getVersionToShow());
                                officePortlet.loadContent(in);
                            }
                        }
                    }
                }
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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
                Iterator<SemanticObject> it = sites.next().getSemanticObject().getModel().listSubjects(OfficePortlet.swbrep_content, contentID);
                while (it.hasNext())
                {
                    SemanticObject obj = it.next();
                    if (obj.getSemanticClass().isSubClass(OfficePortlet.sclass) || obj.getSemanticClass().equals(OfficePortlet.sclass))
                    {
                        OfficePortlet officePortlet = new OfficePortlet(obj);
                        officePortlet.remove();
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
                        Iterator<SemanticObject> itSubjects = sites.next().getSemanticObject().getModel().listSubjects(OfficePortlet.swbrep_content, contentId);
                        while (itSubjects.hasNext())
                        {
                            SemanticObject obj = itSubjects.next();
                            if (obj.getSemanticClass().isSubClass(OfficePortlet.sclass) || obj.getSemanticClass().equals(OfficePortlet.sclass))
                            {
                                OfficePortlet officePortlet = new OfficePortlet(obj);
                                if (officePortlet.getRepositoryName().equals(repositoryName) && officePortlet.getVersionToShow() != null)
                                {
                                    if (officePortlet.getVersionToShow().equals("*"))
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
                                        if (officePortlet.getVersionToShow().equals(info.nameOfVersion))
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

    public static PortletInfo getPortletInfo(OfficePortlet officePortlet)
    {
        PortletInfo info = null;
        GenericIterator<Portletable> portlables = officePortlet.listPortletables();
        if (portlables != null)
        {
            while (portlables.hasNext())
            {
                Portletable portletable = portlables.next();
                if (portletable != null && portletable instanceof WebPage)
                {
                    info = new PortletInfo();
                    info.active = officePortlet.isActive();
                    info.description = officePortlet.getDescription();
                    info.id = officePortlet.getId();
                    info.title = officePortlet.getTitle();
                    info.version = officePortlet.getVersionToShow();
                    info.page = new PageInfo();
                    WebPage page = (WebPage) portletable;
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

    public PortletInfo[] listPortlets(String repositoryName, String contentid) throws Exception
    {
        ArrayList<PortletInfo> listPortlets = new ArrayList<PortletInfo>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(OfficePortlet.swbrep_content, contentid);
            while (it.hasNext())
            {
                SemanticObject obj = it.next();
                if (obj.getSemanticClass().isSubClass(OfficePortlet.sclass) || obj.getSemanticClass().equals(OfficePortlet.sclass))
                {
                    OfficePortlet officePortlet = new OfficePortlet(obj);
                    if (officePortlet.getRepositoryName().equals(repositoryName))
                    {
                        PortletInfo info = getPortletInfo(officePortlet);
                        if (info != null)
                        {
                            listPortlets.add(info);
                        }
                    }
                }
            }
        }
        return listPortlets.toArray(new PortletInfo[listPortlets.size()]);
    }

    public PortletInfo publishToPortletContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage) throws Exception
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
            OfficePortlet portlet = null;
            String id = UUID.randomUUID().toString();
            PortletType portletType = null;
            if (type.equalsIgnoreCase("EXCEL"))
            {
                portlet = ExcelPortlet.createExcelPortlet(id, site);
                portletType = site.getPortletType(EXCEL_PORTLET_TYPE);
                if (portletType == null)
                {
                    portletType = site.createPortletType(EXCEL_PORTLET_TYPE);
                    portletType.setCreated(new Date(System.currentTimeMillis()));
                    portletType.setDescription(EXCEL_PORTLET_DESCRIPTION);
                    portletType.setTitle(EXCEL_PORTLET_TITLE);
                    portletType.setPortletMode(1);
                    portletType.setPortletClassName(EXCEL_PORTLET_CLASS);
                    portletType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            else if (type.equalsIgnoreCase("PPT"))
            {
                portlet = PPTPortlet.createPPTPortlet(id, site);
                portletType = site.getPortletType(PPT_PORTLET_TYPE);
                if (portletType == null)
                {
                    portletType = site.createPortletType(PPT_PORTLET_TYPE);
                    portletType.setCreated(new Date(System.currentTimeMillis()));
                    portletType.setDescription(PPT_PORTLET_DESCRIPTION);
                    portletType.setTitle(PPT_PORTLET_TITLE);
                    portletType.setPortletMode(1);
                    portletType.setPortletClassName(PPT_PORTLET_CLASS);
                    portletType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            else
            {
                portlet = WordPortlet.createWordPortlet(id, site);
                portletType = site.getPortletType(WORD_PORTLET_TYPE);
                if (portletType == null)
                {
                    portletType = site.createPortletType(WORD_PORTLET_TYPE);
                    portletType.setCreated(new Date(System.currentTimeMillis()));
                    portletType.setDescription(WORD_PORTLET_DESCRIPTION);
                    portletType.setTitle(WORD_PORTLET_TITLE);
                    portletType.setPortletMode(1);
                    portletType.setPortletClassName(WORD_PORTLET_CLASS);
                    portletType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            portlet.setContent(contentId);
            portlet.setPortletType(portletType);
            portlet.setRepositoryName(repositoryName);
            portlet.setTitle(title);
            portlet.setPriority(1);
            portlet.setDescription(description);
            portlet.setVersionToShow(version);
            portlet.setCreated(new Date(System.currentTimeMillis()));
            portlet.setUpdated(new Date(System.currentTimeMillis()));
            try
            {
                page.addPortlet(portlet);
            }
            catch (Exception e)
            {
                log.error(e);
            }
            InputStream in = getContent(repositoryName, contentId, version);
            portlet.loadContent(in);
            PortletInfo info = getPortletInfo(portlet);
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

    public void setPortletProperties(PortletInfo portletInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        Portlet portlet = site.getPortlet(portletInfo.id);
        SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propertyInfo.id);
        portlet.getSemanticObject().setProperty(prop, value);
    }

    public void setViewPropertyValue(PortletInfo portletInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(portletInfo.id, site);
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        portlet.getSemanticObject().setProperty(prop, value);
    }

    public CalendarInfo[] getCalendars(PortletInfo portletInfo) throws Exception
    {
        ArrayList<CalendarInfo> getCalendarInfo = new ArrayList<CalendarInfo>();
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(portletInfo.id, site);
        Iterator<org.semanticwb.model.Calendar> calendars = portlet.listCalendars();
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

    public String getViewPropertyValue(PortletInfo portletInfo, PropertyInfo propertyInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(portletInfo.id, site);
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        return portlet.getSemanticObject().getProperty(prop);
    }

    public void validateViewValues(String repositoryName, String contentID, PropertyInfo[] properties, Object[] values) throws Exception
    {
        String contentType = getContentType(repositoryName, contentID);
        SemanticClass clazz;
        Class type = null;
        if (contentType.equalsIgnoreCase("excel"))
        {
            type = ExcelPortlet.class;
            clazz = ExcelPortlet.sclass;
        }
        else if (contentType.equalsIgnoreCase("ppt"))
        {
            type = PPTPortlet.class;
            clazz = PPTPortlet.sclass;
        }
        else
        {
            type = WordPortlet.class;
            clazz = WordPortlet.sclass;
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

            Constructor c = type.getConstructor(clazz.getSemanticObject().getClass());
            Object obj = c.newInstance(clazz.getSemanticObject());
            Method m = type.getMethod("validatePropertyValue", valuesToValidate.getClass());
            try
            {
                m.invoke(obj, valuesToValidate);
            }
            catch (InvocationTargetException ite)
            {
                if (ite.getTargetException() != null)
                {
                    throw new Exception(ite.getTargetException());
                }
            }
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

    public PropertyInfo[] getPortletProperties(String repositoryName, String contentID) throws Exception
    {
        ArrayList<PropertyInfo> properties = new ArrayList<PropertyInfo>();
        String type = getContentType(repositoryName, contentID);
        SemanticClass clazz = null;
        if (type.equalsIgnoreCase("excel"))
        {
            clazz = ExcelPortlet.sclass;
        }
        else if (type.equalsIgnoreCase("ppt"))
        {
            clazz = PPTPortlet.sclass;
        }
        else
        {
            clazz = WordPortlet.sclass;
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
                    if (!propDisplay.isHidden() && propDisplay.isEditable())
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

    public void deleteContentOfPage(PortletInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.id);
        site.removePortlet(info.id);
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
                    Node resNode = frozenNode.getNode(JCR_CONTENT);
                    return resNode.getProperty(JCR_DATA).getStream();
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
                    Node resNode = frozenNode.getNode(JCR_CONTENT);
                    return resNode.getProperty(JCR_DATA).getStream();
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
        OfficePortlet.loadContent(in, dir);
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

    public void activatePortlet(PortletInfo info, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(info.id, site);
        portlet.setActive(active);
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

    public void changeVersionPorlet(PortletInfo info, String newVersion) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(info.id, site);
        portlet.setVersionToShow(newVersion);
        InputStream in = getContent(portlet.getRepositoryName(), portlet.getContent(), newVersion);
        portlet.loadContent(in);

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

    public void deletePortlet(PortletInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(info.id, site);
        try
        {
            portlet.clean();
        }
        catch (Exception e)
        {
            log.event(e);
        }
        finally
        {
            portlet.remove();
        }
    }

    public String getVersionToShow(PortletInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(info.id, site);
        return portlet.getVersionToShow();
    }

    public void deletePreview(String dir) throws Exception
    {
        if (!dir.startsWith("/"))
        {
            dir = "/" + dir;
        }
        OfficePortlet.clean(dir);
    }

    public void updateCalendar(PortletInfo portletInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.setXml(calendarInfo.xml);
        cal.setUpdated(new Date(System.currentTimeMillis()));
    }

    public CalendarInfo insertCalendar(PortletInfo portletInfo, String title, String xml) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
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

    public void deleteCalendar(PortletInfo portletInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.remove();
    }

    public void activeCalendar(PortletInfo portletInfo, CalendarInfo calendarInfo, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.setActive(active);
    }

    public void updatePorlet(PortletInfo portletInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(portletInfo.id, site);
        portlet.setTitle(portletInfo.title);
        portlet.setDescription(portletInfo.description);
        portlet.setActive(portletInfo.active);
        portlet.setVersionToShow(portletInfo.version);
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
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
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

