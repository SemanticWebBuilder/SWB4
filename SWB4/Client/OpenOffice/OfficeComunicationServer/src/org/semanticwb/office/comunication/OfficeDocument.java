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
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Portletable;
import org.semanticwb.model.SWBContext;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.PageInfo;
import org.semanticwb.office.interfaces.PortletInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portlet.office.ExcelPortlet;
import org.semanticwb.portlet.office.OfficePortlet;
import org.semanticwb.portlet.office.PPTPortlet;
import org.semanticwb.portlet.office.WordPortlet;
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
    private static final String JCR_CONTENT = "jcr:content";
    private static final String JCR_DATA = "jcr:data";
    private static final String JCR_LASTMODIFIED = "jcr:lastModified";
    private static Logger log = SWBUtils.getLogger(OfficeDocument.class);
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();
    private static final String NL = System.getProperty("line.separator");

    public String publish(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file) throws Exception
    {
        Session session = null;
        Node categoryNode = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            categoryNode = session.getNodeByUUID(categoryID);
            if (!categoryNode.isLocked())
            {
                //categoryNode.lock(false, false); // blocks the nodeContent
            }
            else
            {
                throw new Exception("The node is locked");
            }
            try
            {
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                Node contentNode = categoryNode.addNode(nodeType, nodeType);
                contentNode.setProperty(cm_title, title);
                String cm_type = loader.getOfficeManager(repositoryName).getPropertyType();
                String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
                String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                contentNode.setProperty(cm_type, type);
                contentNode.setProperty(cm_description, description);
                contentNode.setProperty(cm_file, file);
                contentNode.setProperty(cm_user, this.user);
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
                    Node resNode = contentNode.addNode(JCR_CONTENT, "nt:resource");
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.setProperty(JCR_DATA, in);
                    in.close();
                    Calendar lastModified = Calendar.getInstance();
                    lastModified.setTimeInMillis(System.currentTimeMillis());
                    resNode.setProperty(JCR_LASTMODIFIED, lastModified);
                    categoryNode.save();
                }
                Version version = contentNode.checkin();
                log.debug("Version created with number " + version.getName());
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

    /*private static Node getNodeFromVersion(Version version) throws Exception
    {
    NodeIterator frozenNodes = version.getNodes();
    while (frozenNodes.hasNext())
    {
    Node frozenNode = frozenNodes.nextNode();
    Node contentNode = frozenNode.getNodes().nextNode();
    return contentNode;
    }
    throw new Exception("Node not found from a version");
    }*/
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
            if (!nodeContent.isLocked())
            {
                //nodeContent.lock(false, false); // blocks the nodeContent for all
            }
            else
            {
                throw new Exception("The content is locked");
            }
            if (!nodeContent.isCheckedOut())
            {
                nodeContent.checkout();
                String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
                String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                nodeContent.setProperty(cm_file, file);
                nodeContent.setProperty(cm_user, this.user);
                nodeContent.save();

                try
                {
                    for (Part part : requestParts)
                    {
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
                        resNode.setProperty("jcr:mimeType", mimeType);
                        resNode.setProperty("jcr:encoding", "");
                        InputStream in = new ByteArrayInputStream(part.getContent());
                        resNode.getProperty(JCR_DATA).setValue(in);
                        Calendar lastModified = Calendar.getInstance();
                        lastModified.setTimeInMillis(System.currentTimeMillis());
                        resNode.setProperty(JCR_LASTMODIFIED, lastModified);
                    }
                    session.save();
                }
                catch (RepositoryException e)
                {
                    e.printStackTrace(System.out);
                    throw e;
                }
                finally
                {
                    Version version = nodeContent.checkin();
                    String snumberOfVersions = SWBPlatform.getEnv("swbrep/maxNumberOfVersions");
                    if (snumberOfVersions != null && !snumberOfVersions.trim().equals(""))
                    {
                        try
                        {
                            // debe haber una más por la versión raiz
                            long numberOfVersions = Long.parseLong(snumberOfVersions);
                            if (numberOfVersions <= 0)
                            {
                                log.error("The configuration of swb/numberOfVersions is invalid, the value must be greater than 0, the value will be omited");
                            }
                            else
                            {
                                numberOfVersions++;
                                VersionHistory history = nodeContent.getVersionHistory();
                                VersionIterator versions = history.getAllVersions();
                                if (versions.getSize() > numberOfVersions)
                                {
                                    long dif = versions.getSize() - numberOfVersions;
                                    Version root = history.getRootVersion();
                                    while (dif != 0)
                                    {
                                        for (Version upperVersion : root.getSuccessors())
                                        {
                                            boolean isRootBefore = false;
                                            for (Version lowerVersion : upperVersion.getPredecessors())
                                            {
                                                if (lowerVersion.getName().equals(root.getName()))
                                                {
                                                    isRootBefore = true;
                                                    break;
                                                }
                                            }
                                            if (isRootBefore)
                                            {
                                                upperVersion.remove();
                                                history.save();
                                                break;
                                            }

                                        }

                                        versions = history.getAllVersions();
                                        dif = versions.getSize() - numberOfVersions;
                                    }

                                }
                            }
                        }
                        catch (NumberFormatException nfe)
                        {
                            log.error(nfe);
                        }
                    }



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

                    session.save();
                    return version.getName();
                }
            }
            else
            {
                throw new Exception("El contenido esta siendo editado, intente más tarde");
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

    public void setPagination(String contentId) throws Exception
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

    public VersionInfo[] getVersions(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        ArrayList<VersionInfo> versions = new ArrayList<VersionInfo>();
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);            
            VersionIterator it = nodeContent.getVersionHistory().getAllVersions();
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
            if (!nodeContent.isLocked())
            {
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                nodeContent.checkout();
                nodeContent.setProperty(cm_title, title);
                Node resource = nodeContent.getNode(JCR_CONTENT);
                resource.getProperty(JCR_LASTMODIFIED).setValue(Calendar.getInstance());
                nodeContent.save();
                nodeContent.checkin();
            }
            else
            {
                throw new Exception("The content is locked");
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
            if (!nodeContent.isLocked())
            {
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                nodeContent.checkout();
                nodeContent.setProperty(cm_description, description);
                Node resource = nodeContent.getNode(JCR_CONTENT);
                resource.getProperty(JCR_LASTMODIFIED).setValue(Calendar.getInstance());
                nodeContent.save();
                nodeContent.checkin();
            }
            else
            {
                throw new Exception("The content is locked");
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
    }

    public Date getLasUpdate(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            Node resource = nodeContent.getNode(JCR_CONTENT);
            return resource.getProperty(JCR_LASTMODIFIED).getDate().getTime();

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

    private PortletInfo getPortletInfo(OfficePortlet officePortlet)
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
            if (type.equals("EXCEL"))
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
            else if (type.equals("PPT"))
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
            page.addPortlet(portlet);
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

    public PropertyInfo[] getPortletProperties(PortletInfo portletInfo) throws Exception
    {
        ArrayList<PropertyInfo> properties = new ArrayList<PropertyInfo>();
        WebSite site = SWBContext.getWebSite(portletInfo.page.site.id);
        Portlet portlet = site.getPortlet(portletInfo.id);
        Iterator<SemanticProperty> propertiesClazz = portlet.getSemanticObject().getSemanticClass().listProperties();
        while (propertiesClazz.hasNext())
        {
            SemanticProperty prop = propertiesClazz.next();

            if (prop.isDataTypeProperty() && !prop.isBinary() && prop.getPrefix() != null)
            {
                String label = prop.getLabel();
                if (label != null)
                {
                    PropertyInfo info = new PropertyInfo();
                    info.id = prop.getURI();
                    info.isRequired = prop.isRequired();
                    info.title = label;
                    if (prop.isString())
                    {
                        info.type = "String";
                    }
                    if (prop.isBoolean())
                    {
                        info.type = "Boolean";
                    }
                    if (prop.isInt())
                    {
                        info.type = "Integer";
                    }
                    properties.add(info);
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
            if (version.equals("*"))
            {

                Node resNode = nodeContent.getNode(JCR_CONTENT);
                return resNode.getProperty(JCR_DATA).getStream();
            }
            else
            {
                Version versionNode = nodeContent.getVersionHistory().getVersion(version);
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

    public String getContentFile(String repositoryName, String contentId, String version) throws Exception
    {
        Session session = null;
        try
        {
            this.user = "demo";
            this.password = "demo";
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
            if (version.equals("*"))
            {
                return nodeContent.getProperty(cm_file).getString();
            }
            else
            {
                Version versionNode = nodeContent.getVersionHistory().getVersion(version);
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

    public void activatePortlet(PortletInfo info, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        OfficePortlet portlet = OfficePortlet.getOfficePortlet(info.id,site);
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
            resource.getProperty(JCR_LASTMODIFIED).setValue(Calendar.getInstance());
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
            CategoryInfo info=new CategoryInfo();
            info.UDDI=parent.getUUID();
            info.childs=0;
            info.description=parent.getProperty(cm_description).getString();
            info.title=parent.getProperty(cm_title).getString();
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
        portlet.remove();
    }
}

