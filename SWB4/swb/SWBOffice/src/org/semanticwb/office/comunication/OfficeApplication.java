/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.CalendarInfo;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.ContentInfo;
import org.semanticwb.office.interfaces.ContentType;
import org.semanticwb.office.interfaces.FlowContentInformation;
import org.semanticwb.office.interfaces.IOfficeApplication;

import org.semanticwb.office.interfaces.PageInfo;
import org.semanticwb.office.interfaces.RepositoryInfo;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.resource.office.sem.OfficeResource;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements IOfficeApplication
{

    static Logger log = SWBUtils.getLogger(OfficeApplication.class);
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    //private Session session;
    public boolean isValidVersion(double version)
    {
        return IOfficeApplication.version >= version;
    }

    public void createPage(WebPageInfo page, String pageid, String title, String description) throws Exception
    {
        WebSite website = SWBContext.getWebSite(page.siteID);
        if (website.getWebPage(pageid) != null)
        {
            throw new Exception("The webpage already exists");
        }
        WebPage newpage = website.createWebPage(pageid);
        newpage.setTitle(title);
        newpage.setDescription(description);
        newpage.setCreated(new Date(System.currentTimeMillis()));
        newpage.setUpdated(new Date(System.currentTimeMillis()));
        WebPage parent = website.getWebPage(page.id);
        newpage.setParent(parent);
    }

    public void changePassword(String newPassword) throws Exception
    {
        org.semanticwb.model.User userModel = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
        userModel.setPassword(newPassword);
        userModel.setUpdated(new Date(System.currentTimeMillis()));
    }

    public boolean existsPage(WebSiteInfo site, String pageid) throws Exception
    {
        WebSite website = SWBContext.getWebSite(site.id);
        WebPage page = website.getWebPage(pageid);
        if (page == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public String[] getContents(String repositoryName, String categoryID) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, this.user, this.password);
            ArrayList<String> contents = new ArrayList<String>();
            Node categoryNode = session.getNodeByUUID(categoryID);
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            NodeIterator nodes = categoryNode.getNodes(cm_category);
            while (nodes.hasNext())
            {
                Node nodeContent = nodes.nextNode();
                VersionIterator versions = nodeContent.getVersionHistory().getAllVersions();
                while (versions.hasNext())
                {
                    Version versionContent = versions.nextVersion();
                    StringBuilder content = new StringBuilder(nodeContent.getUUID());
                    content.append(",");
                    content.append(versionContent.getName());
                    content.append(",");
                    String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                    content.append(nodeContent.getProperty(cm_title).getString());
                    contents.add(content.toString());
                }
            }
            return contents.toArray(new String[contents.size()]);
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

    public RepositoryInfo[] getRepositories() throws Exception
    {
        return loader.getWorkspacesForOffice();
    }

    public CategoryInfo[] getCategories(String repositoryName) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, this.user, this.password);
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Node root = session.getRootNode();
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            NodeIterator it = root.getNodes(cm_category);
            while (it.hasNext())
            {
                Node node = it.nextNode();
                if (node.getDefinition().getDeclaringNodeType().getName().equals(cm_category))
                {
                    CategoryInfo category = new CategoryInfo();
                    category.UDDI = node.getUUID();
                    String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                    String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                    category.description = node.getProperty(cm_description).getValue().getString();
                    category.title = node.getProperty(cm_title).getValue().getString();
                    category.childs = (int) node.getNodes(cm_category).getSize();
                    categories.add(category);
                }
            }
            return categories.toArray(new CategoryInfo[categories.size()]);
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

    public String createCategory(String repositoryName, String title, String description) throws Exception
    {

        String UUID = "";
        Session session = null;
        Node root = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            root = session.getRootNode();
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            Query query;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                String statement = "SELECT DISTINCT ?x WHERE {?x " + cm_title + " ?title FILTER (?title=\"" + title + "\")  }";
                query = session.getWorkspace().getQueryManager().createQuery(statement, "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//" + cm_category + "[@" + cm_title + "='" + title + "']", Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            if (nodeIterator.hasNext())
            {
                UUID = nodeIterator.nextNode().getUUID();
            }
            else
            {
                Node newNode = root.addNode(cm_category, cm_category);
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                newNode.setProperty(cm_user, this.user);
                newNode.setProperty(cm_title, title);
                newNode.setProperty(cm_description, description);
                root.save();
                UUID = newNode.getUUID();
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

        return UUID;
    }

    public boolean canDeleteCategory(String repositoryName, String id) throws Exception
    {
        boolean canDeleteCategory = false;
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node node = session.getNodeByUUID(id);
            canDeleteCategory = !node.getNodes().hasNext();
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return canDeleteCategory;
    }

    public boolean deleteCategory(String repositoryName, String id) throws Exception
    {
        boolean deleteCategory = false;
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node node = session.getNodeByUUID(id);
            if (!node.getNodes().hasNext())
            {
                Node parent = node.getParent();
                node.remove();
                parent.save();
                deleteCategory = true;
            }

        }
        catch (Throwable e)
        {
            log.error(e);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return deleteCategory;
    }

    public String createCategory(String repositoryName, String categoryId, String title, String description) throws Exception
    {
        String UUID = "";
        Session session = null;
        Node parent = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            parent = session.getNodeByUUID(categoryId);
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            Query query;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                String statement = "SELECT DISTINCT ?x WHERE {?x " + cm_title + " ?title FILTER (?title=\"" + title + "\") }";
                query = session.getWorkspace().getQueryManager().createQuery(statement, "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//" + cm_category + "[@" + cm_title + "='" + title + "']", Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            if (nodeIterator.hasNext())
            {
                UUID = nodeIterator.nextNode().getUUID();
            }
            else
            {
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                Node newNode = parent.addNode(cm_category, cm_category);
                String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                newNode.setProperty(cm_user, this.user);
                newNode.setProperty(cm_title, title);
                newNode.setProperty(cm_description, description);
                parent.save();
                UUID = newNode.getUUID();
            }
        }
        catch (Exception e)
        {
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

        return UUID;
    }

    public CategoryInfo[] getAllCategories(String repositoryName) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, this.user, this.password);
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            QueryManager manager = session.getWorkspace().getQueryManager();
            Query query = manager.createQuery("SELECT ?x WHERE { ?x swbrep:name ?name FILTER (?name=\"" + cm_category + "\") }", "SPARQL");
            QueryResult result = query.execute();
            NodeIterator it = result.getNodes();
            while (it.hasNext())
            {
                Node node = it.nextNode();
                if (node.getDefinition().getDeclaringNodeType().getName().equals(cm_category))
                {
                    CategoryInfo category = new CategoryInfo();
                    category.UDDI = node.getUUID();
                    String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                    String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                    category.description = node.getProperty(cm_description).getValue().getString();
                    category.title = node.getProperty(cm_title).getValue().getString();
                    //category.childs = (int) node.getNodes(cm_category).getSize();
                    categories.add(category);
                }

            }
            return categories.toArray(new CategoryInfo[categories.size()]);
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

    public CategoryInfo[] getCategories(String repositoryName, String categoryId) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, this.user, this.password);
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Node categoryNode = session.getNodeByUUID(categoryId);
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            NodeIterator it = categoryNode.getNodes(cm_category);
            while (it.hasNext())
            {
                Node node = it.nextNode();
                if (node.getDefinition().getDeclaringNodeType().getName().equals(cm_category))
                {
                    CategoryInfo category = new CategoryInfo();
                    category.UDDI = node.getUUID();
                    String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                    String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                    category.description = node.getProperty(cm_description).getValue().getString();
                    category.title = node.getProperty(cm_title).getValue().getString();
                    category.childs = (int) node.getNodes(cm_category).getSize();
                    categories.add(category);
                }
            }
            return categories.toArray(new CategoryInfo[categories.size()]);
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

    public ContentType[] getContentTypes(String repositoryName) throws Exception
    {
        ArrayList<ContentType> types = new ArrayList<ContentType>();
        HashMap<String, String> mtypes = loader.getOfficeManager(repositoryName).getContentTypes();
        for (String type : mtypes.keySet())
        {
            ContentType contentType = new ContentType();
            contentType.id = type;
            contentType.title = mtypes.get(type);
            types.add(contentType);
        }
        return types.toArray(new ContentType[types.size()]);
    }

    public ContentInfo[] search(String repositoryName, String title, String description, String category, String type, String officeType) throws Exception
    {
        Session session = null;
        ArrayList<ContentInfo> contents = new ArrayList<ContentInfo>();
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
            String cm_officeType = loader.getOfficeManager(repositoryName).getPropertyType();
            Query query = null;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                StringBuilder statement = new StringBuilder("SELECT DISTINCT ?x ");

                statement.append(" WHERE {");

                if (!(title.equals("") || title.equals("*")))
                {
                    statement.append(" ?x " + cm_title + " ?title . ");
                    statement.append("FILTER regex(?title,\"" + title + "\") ");
                }


                if (!(officeType.equals("") || officeType.equals("*")))
                {
                    statement.append(" ?x " + cm_officeType + " ?officetype . ");
                    statement.append(" FILTER (?officetype=\"" + officeType + "\") ");
                }

                if (!(description.equals("") || description.equals("*")))
                {
                    statement.append(" ?x " + cm_description + " ?description . ");
                    statement.append(" FILTER regex(?description, \"" + description + "\") ");
                }



                if (!(type.equals("") || type.equals("*")))
                {
                    statement.append(" ?x jcr:primaryType ?type . ");
                    statement.append(" FILTER (?type=\"" + type + "\") ");
                }
                statement.append(" } ");
                query = session.getWorkspace().getQueryManager().createQuery(statement.toString(), "SPARQL");

            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//" + type, Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node node = nodeIterator.nextNode();
                if (category == null || category.equals("") || category.equals("*"))
                {
                    Node parent = node.getParent();
                    ContentInfo info = new ContentInfo();
                    info.id = node.getUUID();
                    info.title = node.getProperty(cm_title).getValue().getString();
                    info.descripcion = node.getProperty(cm_description).getValue().getString();
                    info.categoryId = parent.getUUID();
                    info.categoryTitle = parent.getProperty(cm_title).getValue().getString();
                    info.created = node.getProperty("jcr:created").getDate().getTime();
                    contents.add(info);
                }
                else
                {
                    Node parent = node.getParent();
                    if (category.equals(parent.getUUID()))
                    {
                        ContentInfo info = new ContentInfo();
                        info.id = node.getUUID();
                        info.title = node.getProperty(cm_title).getValue().getString();
                        info.descripcion = node.getProperty(cm_description).getValue().getString();
                        info.categoryId = parent.getUUID();
                        info.categoryTitle = parent.getProperty(cm_title).getValue().getString();
                        info.created = node.getProperty("jcr:created").getDate().getTime();
                        contents.add(info);
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return contents.toArray(new ContentInfo[contents.size()]);
    }

    public String openContent(String repositoryName, VersionInfo versioninfo) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node contentNode = session.getNodeByUUID(versioninfo.contentId);
            Node resContent = contentNode.getNode(OfficeDocument.JCR_CONTENT);
            VersionHistory history = resContent.getVersionHistory();
            Version versiontoReturn = history.getVersion(versioninfo.nameOfVersion);
            if (versiontoReturn != null)
            {
                Node frozenNode = versiontoReturn.getNode("jcr:frozenNode");
                String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
                String file = frozenNode.getProperty(cm_file).getString();
                InputStream in = frozenNode.getProperty("jcr:data").getStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[2048];
                int read = in.read(buffer);
                while (read != -1)
                {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
                Part part = new Part(out.toByteArray(), file, file);
                this.responseParts.add(part);
                return file;
            }
            else
            {
                throw new Exception("The version " + versioninfo.nameOfVersion + " does not exist");
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

    public WebSiteInfo[] getSites() throws Exception
    {
        ArrayList<WebSiteInfo> websites = new ArrayList<WebSiteInfo>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            WebSiteInfo info = new WebSiteInfo();
            info.title = site.getTitle();
            info.id = site.getId();
            if (!(info.id.equals(SWBContext.WEBSITE_ADMIN) || info.id.equals(SWBContext.WEBSITE_GLOBAL) || info.id.equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                websites.add(info);
            }
        }
        return websites.toArray(new WebSiteInfo[websites.size()]);
    }

    public WebPageInfo getHomePage(WebSiteInfo website) throws Exception
    {

        WebSite site = SWBContext.getWebSite(website.id);
        WebPageInfo info = new WebPageInfo();
        info.id = site.getHomePage().getId();
        info.title = site.getHomePage().getTitle();
        site.getHomePage().getUrl();
        info.siteID = website.id;
        info.description = site.getDescription();
        info.url = site.getHomePage().getUrl();
        int childs = 0;
        GenericIterator<WebPage> childWebPages = site.getHomePage().listChilds();
        while (childWebPages.hasNext())
        {
            childWebPages.next();
            childs++;
        }
        info.childs = childs;
        return info;
    }

    public WebPageInfo[] getPages(WebPageInfo webpage) throws Exception
    {
        ArrayList<WebPageInfo> pagesToReturn = new ArrayList<WebPageInfo>();
        WebSite site = SWBContext.getWebSite(webpage.siteID);
        WebPage parent = site.getWebPage(webpage.id);
        GenericIterator<WebPage> pages = parent.listChilds();
        while (pages.hasNext())
        {
            WebPage page = pages.next();
            WebPageInfo info = new WebPageInfo();
            info.id = page.getId();
            info.title = page.getTitle();
            info.siteID = webpage.siteID;
            info.description = page.getDescription();
            info.url = page.getUrl();
            int childs = 0;
            GenericIterator<WebPage> childWebPages = page.listChilds();
            while (childWebPages.hasNext())
            {
                childWebPages.next();
                childs++;
            }
            info.childs = childs;
            pagesToReturn.add(info);
        }
        return pagesToReturn.toArray(new WebPageInfo[pagesToReturn.size()]);
    }

    public int getLimitOfVersions() throws Exception
    {
        String snumberOfVersions = SWBPlatform.getEnv("swbrep/maxNumberOfVersions");
        int getLimitOfVersions = 0;
        if (snumberOfVersions == null)
        {
            log.debug("The variable swbrep/maxNumberOfVersions is null");
        }
        else
        {
            try
            {
                getLimitOfVersions = Integer.parseInt(snumberOfVersions);
                if (getLimitOfVersions <= 0)
                {
                    log.debug("The variable swbrep/maxNumberOfVersions is " + getLimitOfVersions);
                }
            }
            catch (NumberFormatException e)
            {
                log.error(e);
            }
        }
        return getLimitOfVersions;
    }

    public FlowContentInformation[] getMyContents(WebSiteInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminRepository().getUserByLogin(user);
        HashSet<FlowContentInformation> contents = new HashSet<FlowContentInformation>();
        for (Resource res : SWBPortal.getPFlowManager().getContentsAtFlowOfUser(wbuser, site))
        {
            for (String type : OfficeDocument.getOfficeTypes())
            {
                ResourceType resourceType = site.createResourceType(type);
                if (res.getResourceType().equals(resourceType))
                {
                    OfficeResource officeResource = new OfficeResource();
                    officeResource.setResourceBase(res);
                    FlowContentInformation flowContentInformation = new FlowContentInformation();
                    flowContentInformation.id = res.getPflowInstance().getId();
                    flowContentInformation.status = res.getPflowInstance().getStatus();
                    flowContentInformation.step = res.getPflowInstance().getStep();
                    flowContentInformation.title = res.getPflowInstance().getPflow().getTitle();
                    flowContentInformation.resourceInfo = new ResourceInfo();
                    flowContentInformation.resourceInfo.active = res.isActive();
                    flowContentInformation.resourceInfo.description = res.getDescription();
                    flowContentInformation.resourceInfo.page = new PageInfo();
                    Iterator<Resourceable> resourceables = res.listResourceables();
                    while (resourceables.hasNext())
                    {
                        Resourceable resourceable = resourceables.next();
                        if (resourceable instanceof WebPage)
                        {
                            WebPage webpage = (WebPage) resourceable;
                            flowContentInformation.resourceInfo.page.id = webpage.getId();
                            flowContentInformation.resourceInfo.page.description = webpage.getDescription();
                            flowContentInformation.resourceInfo.page.title = webpage.getTitle();
                            flowContentInformation.resourceInfo.page.site = new SiteInfo();
                            flowContentInformation.resourceInfo.page.site.id = site.getId();
                            flowContentInformation.resourceInfo.page.site.title = site.getTitle();
                            flowContentInformation.resourceInfo.page.site.description = site.getDescription();
                        }
                    }
                    flowContentInformation.resourceInfo.id = res.getId();
                    flowContentInformation.resourceInfo.title = res.getTitle();
                    flowContentInformation.resourceInfo.version = officeResource.getVersionToShow();
                    contents.add(flowContentInformation);
                    break;
                }
            }
        }
        return contents.toArray(new FlowContentInformation[contents.size()]);
    }

    public void authorize(ResourceInfo resourceInfo, String message) throws Exception
    {
        Resource resource = SWBContext.getWebSite(resourceInfo.page.site.id).getResource(resourceInfo.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminRepository().getUserByLogin(user);
        SWBPortal.getPFlowManager().approveResource(resource, wbuser, message);
    }

    public void reject(ResourceInfo resourceInfo, String message) throws Exception
    {
        Resource resource = SWBContext.getWebSite(resourceInfo.page.site.id).getResource(resourceInfo.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminRepository().getUserByLogin(user);
        SWBPortal.getPFlowManager().rejectResource(resource, wbuser, message);
    }

    public FlowContentInformation[] getAllContents(WebSiteInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.id);
        HashSet<FlowContentInformation> contents = new HashSet<FlowContentInformation>();
        for (Resource res : SWBPortal.getPFlowManager().getContentsAtFlowAll(site))
        {
            for (String type : OfficeDocument.getOfficeTypes())
            {
                ResourceType resourceType = site.createResourceType(type);
                if (res.getResourceType().equals(resourceType))
                {
                    OfficeResource officeResource = new OfficeResource();
                    officeResource.setResourceBase(res);
                    FlowContentInformation flowContentInformation = new FlowContentInformation();
                    flowContentInformation.id = res.getPflowInstance().getId();
                    flowContentInformation.status = res.getPflowInstance().getStatus();
                    flowContentInformation.step = res.getPflowInstance().getStep();
                    flowContentInformation.title = res.getPflowInstance().getPflow().getTitle();
                    flowContentInformation.resourceInfo = new ResourceInfo();
                    flowContentInformation.resourceInfo.active = res.isActive();
                    flowContentInformation.resourceInfo.description = res.getDescription();
                    flowContentInformation.resourceInfo.page = new PageInfo();
                    Iterator<Resourceable> resourceables = res.listResourceables();
                    while (resourceables.hasNext())
                    {
                        Resourceable resourceable = resourceables.next();
                        if (resourceable instanceof WebPage)
                        {
                            WebPage webpage = (WebPage) resourceable;
                            flowContentInformation.resourceInfo.page.id = webpage.getId();
                            flowContentInformation.resourceInfo.page.description = webpage.getDescription();
                            flowContentInformation.resourceInfo.page.title = webpage.getTitle();
                            flowContentInformation.resourceInfo.page.site = new SiteInfo();
                            flowContentInformation.resourceInfo.page.site.id = site.getId();
                            flowContentInformation.resourceInfo.page.site.title = site.getTitle();
                            flowContentInformation.resourceInfo.page.site.description = site.getDescription();
                        }
                    }
                    flowContentInformation.resourceInfo.id = res.getId();
                    flowContentInformation.resourceInfo.title = res.getTitle();
                    flowContentInformation.resourceInfo.version = officeResource.getVersionToShow();
                    contents.add(flowContentInformation);
                    break;
                }
            }
        }
        return contents.toArray(new FlowContentInformation[contents.size()]);
    }

    public FlowContentInformation[] getContentsForAuthorize(WebSiteInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminRepository().getUserByLogin(user);
        HashSet<FlowContentInformation> contents = new HashSet<FlowContentInformation>();
        for (Resource res : SWBPortal.getPFlowManager().getContentsAtFlow(wbuser, site))
        {
            for (String type : OfficeDocument.getOfficeTypes())
            {
                ResourceType resourceType = site.createResourceType(type);
                if (res.getResourceType().equals(resourceType))
                {
                    OfficeResource officeResource = new OfficeResource();
                    officeResource.setResourceBase(res);
                    FlowContentInformation flowContentInformation = new FlowContentInformation();
                    flowContentInformation.id = res.getPflowInstance().getId();
                    flowContentInformation.status = res.getPflowInstance().getStatus();
                    flowContentInformation.step = res.getPflowInstance().getStep();
                    flowContentInformation.title = res.getPflowInstance().getPflow().getTitle();
                    flowContentInformation.resourceInfo = new ResourceInfo();
                    flowContentInformation.resourceInfo.active = res.isActive();
                    flowContentInformation.resourceInfo.description = res.getDescription();
                    flowContentInformation.resourceInfo.page = new PageInfo();
                    Iterator<Resourceable> resourceables = res.listResourceables();
                    while (resourceables.hasNext())
                    {
                        Resourceable resourceable = resourceables.next();
                        if (resourceable instanceof WebPage)
                        {
                            WebPage webpage = (WebPage) resourceable;
                            flowContentInformation.resourceInfo.page.id = webpage.getId();
                            flowContentInformation.resourceInfo.page.description = webpage.getDescription();
                            flowContentInformation.resourceInfo.page.title = webpage.getTitle();
                            flowContentInformation.resourceInfo.page.site = new SiteInfo();
                            flowContentInformation.resourceInfo.page.site.id = site.getId();
                            flowContentInformation.resourceInfo.page.site.title = site.getTitle();
                            flowContentInformation.resourceInfo.page.site.description = site.getDescription();
                        }
                    }
                    flowContentInformation.resourceInfo.id = res.getId();
                    flowContentInformation.resourceInfo.title = res.getTitle();
                    flowContentInformation.resourceInfo.version = officeResource.getVersionToShow();
                    contents.add(flowContentInformation);
                    break;
                }
            }
        }
        return contents.toArray(new FlowContentInformation[contents.size()]);
    }

    public boolean isReviewer(ResourceInfo resourceInfo) throws Exception
    {
        Resource resource = SWBContext.getWebSite(resourceInfo.page.site.id).getResource(resourceInfo.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminRepository().getUserByLogin(user);
        return SWBPortal.getPFlowManager().isReviewer(resource, wbuser);
    }

    public CalendarInfo createCalendar(SiteInfo siteInfo, String title, String xml) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.createCalendar();
        cal.setActive(true);
        cal.setXml(xml);
        cal.setCreated(new Date(System.currentTimeMillis()));
        cal.setUpdated(new Date(System.currentTimeMillis()));
        cal.setTitle(title);
        CalendarInfo info = new CalendarInfo();
        info.id = cal.getId();
        info.title = cal.getTitle();
        info.active = cal.isActive();
        info.xml = cal.getXml();
        return info;
    }
}
