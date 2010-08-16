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
package org.semanticwb.office.comunication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.AccessControlException;
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
import org.semanticwb.jcr170.implementation.SWBCredentials;
import org.semanticwb.jcr170.implementation.SWBRepository;
import org.semanticwb.model.AdminFilter;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Role;
import org.semanticwb.model.Rule;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
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
import org.semanticwb.office.interfaces.ElementInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.FlowNotification;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.resource.office.sem.OfficeResource;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;
import org.semanticwb.office.interfaces.SemanticRepository;
import org.semanticwb.office.interfaces.SemanticFolderRepository;
import org.semanticwb.office.interfaces.SemanticFileRepository;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements IOfficeApplication, FlowNotification
{
    private static final SemanticClass swb_office = org.semanticwb.repository.office.OfficeDocument.swboffice_OfficeDocument;
    private static final String SWB_FILEREP_DELETED = "swbfilerep:deleted";
    private static final String REP_FILE = "swbfilerep:RepositoryFile";
    private static final String REP_FOLDER = "swbfilerep:RepositoryFolder";
    private static SWBRepository rep = null;
    public static final String JCR_CONTENT = "jcr:content";
    private static final Logger log = SWBUtils.getLogger(OfficeApplication.class);
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    static {
        try {
            rep = new SWBRepository();
        } catch (Exception e) {
            log.error(e);
        }
    }
    //private Session session;
    public boolean isValidVersion(double version)
    {
        return IOfficeApplication.version == version;
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
        User ouser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(user);
        return loader.getWorkspacesForOffice(ouser);
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
            e.printStackTrace();
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
    private boolean isSu()
    {
        UserGroup su=UserGroup.ClassMgr.getUserGroup("su", SWBContext.getAdminRepository());
        User ouser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(user);
        return ((su!=null && ouser.hasUserGroup(su)));
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
                    statement.append(" ?x ");
                    statement.append(cm_title);
                    statement.append(" ?title . ");
                    statement.append("FILTER regex(?title,\"");
                    statement.append(title);
                    statement.append("\") ");
                }


                if (!(officeType.equals("") || officeType.equals("*")))
                {
                    statement.append(" ?x ");
                    statement.append(cm_officeType);
                    statement.append(" ?officetype . ");
                    statement.append(" FILTER (?officetype=\"");
                    statement.append(officeType);
                    statement.append("\") ");
                }

                if (!(description.equals("") || description.equals("*")))
                {
                    statement.append(" ?x ");
                    statement.append(cm_description);
                    statement.append(" ?description . ");
                    statement.append(" FILTER regex(?description, \"");
                    statement.append(description);
                    statement.append("\") ");
                }



                if (!(type.equals("") || type.equals("*")))
                {
                    statement.append(" ?x jcr:primaryType ?type . ");
                    statement.append(" FILTER (?type=\"");
                    statement.append(type);
                    statement.append("\") ");
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
                    String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                    Node resNode = node.getNode(JCR_CONTENT);//, swb_office.getPrefix() + ":" + swb_office.getName());
                    String userlogin=resNode.getProperty(cm_user).getString();
                    if(isSu() || (userlogin!=null && userlogin.equals(this.user)))
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
                String encode = System.getenv("Dfile.encoding");
                if (encode == null || encode.equals(""))
                {
                    encode = "utf-8";
                }
                file = java.net.URLDecoder.decode(file, encode);
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
            User ouser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(user);
            String siteid=site.getId();
            if (!(siteid.equals(SWBContext.WEBSITE_ADMIN) || siteid.equals(SWBContext.WEBSITE_GLOBAL) || siteid.equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                WebPage home = site.getHomePage();                
                if (SWBPortal.getAdminFilterMgr().haveTreeAccessToSemanticObject(ouser, site.getSemanticObject()) && SWBPortal.getAdminFilterMgr().haveTreeAccessToSemanticObject(ouser, home.getSemanticObject()))
                {
                    WebSiteInfo info = new WebSiteInfo();
                    info.title = site.getTitle();
                    info.id = site.getId();
                    websites.add(info);
                }
            }
        }
        return websites.toArray(new WebSiteInfo[websites.size()]);
    }

    public WebPageInfo getHomePage(WebSiteInfo website) throws Exception
    {

        WebSite site = SWBContext.getWebSite(website.id);
        WebPageInfo info = new WebPageInfo();
        info.id = site.getHomePage().getId();
        info.active = site.getHomePage().isActive();
        info.title = site.getHomePage().getTitle();
        site.getHomePage().getUrl();
        info.siteID = website.id;
        info.description = site.getDescription();
        info.url = site.getHomePage().getUrl();
        int childs = 0;
        GenericIterator<WebPage> childWebPages = site.getHomePage().listChilds();
        if (childWebPages.hasNext())
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
            User ouser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(user);
            if (SWBPortal.getAdminFilterMgr().haveTreeAccessToSemanticObject(ouser, page.getSemanticObject()))
            {
                WebPageInfo info = new WebPageInfo();
                info.id = page.getId();
                info.active = page.isActive();
                info.title = page.getTitle();
                info.siteID = webpage.siteID;
                info.description = page.getDescription();
                info.url = page.getUrl();
                int childs = 0;
                GenericIterator<WebPage> childWebPages = page.listChilds();
                if (childWebPages.hasNext())
                {
                    childWebPages.next();
                    childs++;
                }
                info.childs = childs;
                pagesToReturn.add(info);
            }
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
                ResourceType resourceType = site.getResourceType(type);
                if (resourceType!=null && res.getResourceType().equals(resourceType))
                {
                    OfficeResource officeResource = OfficeResource.getOfficeResource(res.getId(), site);
                    officeResource.setResourceBase(res);
                    if (officeResource.getContent() != null && officeResource.getRepositoryName() != null && officeResource.getVersionToShow() != null)
                    {
                        FlowContentInformation flowContentInformation = new FlowContentInformation();
                        flowContentInformation.id = res.getPflowInstance().getId();
                        flowContentInformation.status = res.getPflowInstance().getStatus();
                        flowContentInformation.step = res.getPflowInstance().getStep();
                        flowContentInformation.title = res.getPflowInstance().getPflow().getTitle();

                        Iterator<Resourceable> resourceables = res.listResourceables();
                        while (resourceables.hasNext())
                        {
                            Resourceable resourceable = resourceables.next();
                            if (resourceable instanceof WebPage)
                            {
                                WebPage webpage = (WebPage) resourceable;
                                flowContentInformation.resourceInfo = new ResourceInfo(res.getId(), webpage.getId());
                                flowContentInformation.resourceInfo.active = res.isActive();
                                flowContentInformation.resourceInfo.description = res.getDescription();
                                flowContentInformation.resourceInfo.page.active = webpage.isActive();
                                flowContentInformation.resourceInfo.page.description = webpage.getDescription();
                                flowContentInformation.resourceInfo.page.title = webpage.getTitle();
                                flowContentInformation.resourceInfo.page.site = new SiteInfo();
                                flowContentInformation.resourceInfo.page.site.id = site.getId();
                                flowContentInformation.resourceInfo.page.site.title = site.getTitle();
                                flowContentInformation.resourceInfo.page.site.description = site.getDescription();

                                flowContentInformation.resourceInfo.contentid = officeResource.getContent();
                                flowContentInformation.resourceInfo.repository = officeResource.getRepositoryName();
                                Session session = null;
                                try
                                {
                                    session = loader.openSession(officeResource.getRepositoryName(), this.user, this.password);
                                    Node nodeContent = session.getNodeByUUID(officeResource.getContent());
                                    String cm_type = loader.getOfficeManager(officeResource.getRepositoryName()).getPropertyType();
                                    flowContentInformation.resourceInfo.type = nodeContent.getProperty(cm_type).getString();
                                }
                                catch (Exception e)
                                {
                                    log.error(e);
                                }
                                //flowContentInformation.resourceInfo.type
                                OfficeDocument doc = new OfficeDocument();
                                doc.setUser(user);
                                doc.setPassword(this.password);
                                flowContentInformation.resourceInfo.lastversion = doc.getLastVersionOfcontent(officeResource.getRepositoryName(), officeResource.getContent());

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
        }
        return contents.toArray(new FlowContentInformation[contents.size()]);
    }

    public void authorize(ResourceInfo resourceInfo, String message) throws Exception
    {
        Resource resource = SWBContext.getWebSite(resourceInfo.page.site.id).getResource(resourceInfo.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminRepository().getUserByLogin(user);
        SWBPortal.getPFlowManager().approveResource(resource, wbuser, message, this);
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
                ResourceType resourceType = site.getResourceType(type);
                if (resourceType!=null && res.getResourceType().equals(resourceType))
                {
                    OfficeResource officeResource = OfficeResource.getOfficeResource(res.getId(), site);
                    officeResource.setResourceBase(res);
                    if (officeResource.getContent() != null && officeResource.getRepositoryName() != null && officeResource.getVersionToShow() != null)
                    {
                        FlowContentInformation flowContentInformation = new FlowContentInformation();
                        flowContentInformation.id = res.getPflowInstance().getId();
                        flowContentInformation.status = res.getPflowInstance().getStatus();
                        flowContentInformation.step = res.getPflowInstance().getStep();
                        flowContentInformation.title = res.getPflowInstance().getPflow().getTitle();
                        Iterator<Resourceable> resourceables = res.listResourceables();
                        while (resourceables.hasNext())
                        {
                            Resourceable resourceable = resourceables.next();
                            if (resourceable instanceof WebPage)
                            {
                                WebPage webpage = (WebPage) resourceable;
                                flowContentInformation.resourceInfo = new ResourceInfo(res.getId(), webpage.getId());
                                flowContentInformation.resourceInfo.active = res.isActive();
                                flowContentInformation.resourceInfo.description = res.getDescription();
                                flowContentInformation.resourceInfo.page.active = webpage.isActive();
                                flowContentInformation.resourceInfo.page.description = webpage.getDescription();
                                flowContentInformation.resourceInfo.page.title = webpage.getTitle();
                                flowContentInformation.resourceInfo.page.site = new SiteInfo();
                                flowContentInformation.resourceInfo.page.site.id = site.getId();
                                flowContentInformation.resourceInfo.page.site.title = site.getTitle();
                                flowContentInformation.resourceInfo.page.site.description = site.getDescription();

                                flowContentInformation.resourceInfo.contentid = officeResource.getContent();
                                flowContentInformation.resourceInfo.repository = officeResource.getRepositoryName();
                                Session session = null;
                                try
                                {
                                    session = loader.openSession(officeResource.getRepositoryName(), this.user, this.password);
                                    Node nodeContent = session.getNodeByUUID(officeResource.getContent());
                                    String cm_type = loader.getOfficeManager(officeResource.getRepositoryName()).getPropertyType();
                                    flowContentInformation.resourceInfo.type = nodeContent.getProperty(cm_type).getString();
                                }
                                catch (Exception e)
                                {
                                    log.error(e);
                                }
                                OfficeDocument doc = new OfficeDocument();
                                doc.setUser(user);
                                doc.setPassword(this.password);
                                flowContentInformation.resourceInfo.lastversion = doc.getLastVersionOfcontent(officeResource.getRepositoryName(), officeResource.getContent());
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
                ResourceType resourceType = site.getResourceType(type);
                if (resourceType!=null && res.getResourceType().equals(resourceType))
                {
                    OfficeResource officeResource = OfficeResource.getOfficeResource(res.getId(), site);
                    officeResource.setResourceBase(res);
                    if (officeResource.getContent() != null && officeResource.getRepositoryName() != null && officeResource.getVersionToShow() != null)
                    {
                        FlowContentInformation flowContentInformation = new FlowContentInformation();
                        flowContentInformation.id = res.getPflowInstance().getId();
                        flowContentInformation.status = res.getPflowInstance().getStatus();
                        flowContentInformation.step = res.getPflowInstance().getStep();
                        flowContentInformation.title = res.getPflowInstance().getPflow().getTitle();

                        Iterator<Resourceable> resourceables = res.listResourceables();
                        while (resourceables.hasNext())
                        {
                            Resourceable resourceable = resourceables.next();
                            if (resourceable instanceof WebPage)
                            {
                                WebPage webpage = (WebPage) resourceable;
                                flowContentInformation.resourceInfo = new ResourceInfo(res.getId(), webpage.getId());
                                flowContentInformation.resourceInfo.active = res.isActive();
                                flowContentInformation.resourceInfo.description = res.getDescription();

                                flowContentInformation.resourceInfo.page.active = webpage.isActive();
                                flowContentInformation.resourceInfo.page.description = webpage.getDescription();
                                flowContentInformation.resourceInfo.page.title = webpage.getTitle();
                                flowContentInformation.resourceInfo.page.site = new SiteInfo();
                                flowContentInformation.resourceInfo.page.site.id = site.getId();
                                flowContentInformation.resourceInfo.page.site.title = site.getTitle();
                                flowContentInformation.resourceInfo.page.site.description = site.getDescription();

                                flowContentInformation.resourceInfo.contentid = officeResource.getContent();
                                flowContentInformation.resourceInfo.repository = officeResource.getRepositoryName();
                                Session session = null;
                                try
                                {
                                    session = loader.openSession(officeResource.getRepositoryName(), this.user, this.password);
                                    Node nodeContent = session.getNodeByUUID(officeResource.getContent());
                                    String cm_type = loader.getOfficeManager(officeResource.getRepositoryName()).getPropertyType();
                                    flowContentInformation.resourceInfo.type = nodeContent.getProperty(cm_type).getString();
                                }
                                catch (Exception e)
                                {
                                    log.error(e);
                                }
                                OfficeDocument doc = new OfficeDocument();
                                doc.setUser(user);
                                doc.setPassword(this.password);
                                flowContentInformation.resourceInfo.lastversion = doc.getLastVersionOfcontent(officeResource.getRepositoryName(), officeResource.getContent());
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

    public boolean canDeleteCalendar(SiteInfo siteInfo, CalendarInfo CalendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(CalendarInfo.id);
        GenericIterator<CalendarRef> refs = cal.listCalendarRefInvs();
        if (refs.hasNext())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean existCalendar(SiteInfo siteInfo, CalendarInfo CalendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(CalendarInfo.id);
        if (cal == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ElementInfo[] getElementsToAdd(SiteInfo siteInfo) throws Exception
    {
        HashSet<ElementInfo> rules = new HashSet<ElementInfo>();
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        {
            Iterator<Rule> orules = site.listRules();
            while (orules.hasNext())
            {
                Rule rule = orules.next();
                ElementInfo info = new ElementInfo();
                info.id = rule.getId();
                info.title = rule.getTitle();
                info.type = Rule.sclass.getName();
                rules.add(info);
            }
        }
        {
            Iterator<Role> roles = site.getUserRepository().listRoles();
            while (roles.hasNext())
            {
                Role role = roles.next();
                ElementInfo info = new ElementInfo();
                info.id = role.getId();
                info.title = role.getTitle();
                info.type = Role.sclass.getName();
                rules.add(info);
            }
        }
        {

            Iterator<UserGroup> userGroups = site.getUserRepository().listUserGroups();
            while (userGroups.hasNext())
            {
                UserGroup role = userGroups.next();
                ElementInfo info = new ElementInfo();
                info.id = role.getId();
                info.title = role.getTitle();
                info.type = UserGroup.sclass.getName();
                rules.add(info);
            }
        }

        return rules.toArray(new ElementInfo[rules.size()]);
    }

    public void activePage(PageInfo webPageInfo, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(webPageInfo.site.id);
        WebPage page = site.getWebPage(webPageInfo.id);
        page.setActive(active);

    }

    public void autorize(Resource resource)
    {
        WebSite site = SWBContext.getWebSite(resource.getWebSiteId());
        OfficeResource officeResource = OfficeResource.getOfficeResource(resource.getId(), site);
        if (officeResource.getRepositoryName() != null && officeResource.getContent() != null)
        {
            OfficeDocument doc = new OfficeDocument(this.user, this.password);
            try
            {
                InputStream in = doc.getContent(officeResource.getRepositoryName(), officeResource.getContent(), officeResource.getVersionToShow());
                User wbuser=SWBContext.getAdminRepository().getUserByLogin(user);
                officeResource.loadContent(in,wbuser);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    public void publish(Resource resource)
    {
    }

    public void noAutorize(Resource resource)
    {
    }

    public boolean canCreatePage(WebPageInfo webpage) throws Exception
    {
        WebSite site = SWBContext.getWebSite(webpage.siteID);
        WebPage parent = site.getWebPage(webpage.id);
        User ouser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(user);
        return SWBPortal.getAdminFilterMgr().haveClassAction(ouser, parent.getSemanticObject().getSemanticClass(), AdminFilter.ACTION_ADD) && SWBPortal.getAdminFilterMgr().haveAccessToSemanticObject(ouser, parent.getSemanticObject());
    }

    public boolean canCreateCategory(String repositoryName) throws Exception
    {
        Session session = null;
        Node root = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            root = session.getRootNode();
            try
            {
                session.checkPermission(root.getPath(), "add_node");
                return true;
            }
            catch (AccessControlException ace)
            {
                log.debug(ace);
                return false;
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

    public boolean canCreateCategory(String repositoryName, String categoryId) throws Exception
    {
        Session session = null;
        Node parent = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            parent = session.getNodeByUUID(categoryId);
            try
            {
                session.checkPermission(parent.getPath(), "add_node");
                return true;
            }
            catch (AccessControlException ace)
            {
                log.debug(ace);
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    public boolean canRemoveCategory(String repositoryName) throws Exception
    {
        Session session = null;
        Node root = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            root = session.getRootNode();
            try
            {
                session.checkPermission(root.getPath(), "remove");
                return true;
            }
            catch (AccessControlException ace)
            {
                log.debug(ace);
                return false;
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

    public boolean canRemoveCategory(String repositoryName, String categoryId) throws Exception
    {
        Session session = null;
        Node parent = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            parent = session.getNodeByUUID(categoryId);
            try
            {
                session.checkPermission(parent.getPath(), "remove");
                return true;
            }
            catch (AccessControlException ace)
            {
                log.debug(ace);
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    public SemanticRepository[] getSemanticRepositories(SiteInfo siteInfo) throws Exception
    {

        HashSet<SemanticRepository> getSemanticRepositories=new HashSet<SemanticRepository>();
        WebSite website = SWBContext.getWebSite(siteInfo.id);
        Iterator<ResourceType> resourceTypes=ResourceType.ClassMgr.listResourceTypes(website);
        while(resourceTypes.hasNext())
        {
            ResourceType resourceType=resourceTypes.next();
            if(resourceType.getResourceClassName().equals("org.semanticwb.resources.filerepository.SemanticRepositoryFile"))
            {
                Iterator<Resource> resources=Resource.ClassMgr.listResourceByResourceType(resourceType, website);
                while(resources.hasNext())
                {
                    Resource resource=resources.next();
                    GenericIterator<Resourceable> resourceables=resource.listResourceables();
                    while(resourceables.hasNext())
                    {
                        Resourceable resourceable=resourceables.next();
                        if(resourceable instanceof WebPage)
                        {
                            WebPage wp=(WebPage)resourceable;                            
                            SemanticRepository sr=new SemanticRepository();
                            sr.name=resource.getTitle();
                            sr.resid=resource.getId();                            
                            sr.pageid=wp.getId();
                            sr.uri=resource.getURI();
                            getSemanticRepositories.add(sr);
                        }
                    }
                }
            }
        }
        return getSemanticRepositories.toArray(new SemanticRepository[getSemanticRepositories.size()]);
    }


    public SemanticFolderRepository[] getSemanticFolderRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository) throws Exception
    {
        HashSet<SemanticFolderRepository> getSemanticFolderRepositories=new HashSet<SemanticFolderRepository>();
        WebSite website = SWBContext.getWebSite(siteInfo.id);
        Resource resource=new Resource(SemanticObject.getSemanticObject(semanticRepository.uri));
        GenericIterator<Resourceable> resourceables=resource.listResourceables();
        while(resourceables.hasNext())
        {
            Resourceable resourceable=resourceables.next();
            if(resourceable instanceof WebPage)
            {
                WebPage wp=(WebPage)resourceable;
                String resUUID = resource.getAttribute(wp.getId() + "_uuid");
                Session session=null;
                String idrep=docRepNS(website);
                if(idrep!=null)
                {
                    org.semanticwb.model.User userModel = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
                    session = rep.login(new SWBCredentials(userModel), idrep);
                    Node nodePage = session.getNodeByUUID(resUUID);
                    NodeIterator nit = nodePage.getNodes(REP_FOLDER);
                    while (nit.hasNext())
                    {
                        Node nodofolder = nit.nextNode();
                        boolean hasChilds=false;
                        NodeIterator nc=nodofolder.getNodes(REP_FOLDER);
                        hasChilds=nc.hasNext();
                        String title=nodofolder.getProperty("swb:title").getString();
                        String uuid=nodofolder.getUUID();
                        SemanticFolderRepository sf=new SemanticFolderRepository();
                        sf.name=title;
                        sf.uuid=uuid;
                        sf.haschilds=hasChilds;
                        getSemanticFolderRepositories.add(sf);
                    }
                }
            }
        }
        return getSemanticFolderRepositories.toArray(new SemanticFolderRepository[getSemanticFolderRepositories.size()]);
    }

    private String docRepNS(WebSite site) {
        String NS = null;
        WebSite ws = site;
        try {

            String[] lws = rep.listWorkspaces();
            for (int i = 0; i < lws.length; i++) {
                if (lws[i].endsWith(ws.getId() + "_rep")) {
                    NS = lws[i];
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Error al obtener el NameSpace del Repositorio de documentos.", e);
        }
        return NS;
    }

    public SemanticFileRepository[] getSemanticFileRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository,SemanticFolderRepository semanticFolder) throws Exception
    {
        HashSet<SemanticFileRepository> getSemanticFileRepositories=new HashSet<SemanticFileRepository>();
        WebSite website = SWBContext.getWebSite(siteInfo.id);
        Resource resource=new Resource(SemanticObject.getSemanticObject(semanticRepository.uri));
        GenericIterator<Resourceable> resourceables=resource.listResourceables();
        while(resourceables.hasNext())
        {
            Resourceable resourceable=resourceables.next();
            if(resourceable instanceof WebPage)
            {
                WebPage wp=(WebPage)resourceable;                
                Session session=null;
                String idrep=docRepNS(website);
                if(idrep!=null)
                {
                    org.semanticwb.model.User userModel = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
                    session = rep.login(new SWBCredentials(userModel), idrep);
                    Node nodeFolder = session.getNodeByUUID(semanticFolder.uuid);
                    NodeIterator nit = nodeFolder.getNodes();
                    while (nit.hasNext())
                    {
                        Node nodoFile = nit.nextNode();
                        if (nodoFile.getPrimaryNodeType().getName().equals(REP_FILE))
                        {
                            boolean isdeleted = false;
                            try {
                                isdeleted = nodoFile.getProperty(SWB_FILEREP_DELETED).getBoolean();
                            } catch (Exception e) {
                                log.event("Error al revisar la propiedad Deleted del repositorio de documentos.", e);
                            }
                            if(!isdeleted)
                            {
                                String title=nodoFile.getProperty("swb:title").getString();
                                Date created=nodoFile.getProperty("jcr:created").getDate().getTime();
                                SemanticFileRepository sf=new SemanticFileRepository();
                                sf.uuid=nodoFile.getUUID();
                                sf.title=title;
                                sf.name=nodoFile.getName();
                                sf.date=created;
                                getSemanticFileRepositories.add(sf);
                            }
                        }
                    }

                }
            }
        }
        return getSemanticFileRepositories.toArray(new SemanticFileRepository[getSemanticFileRepositories.size()]);
    }

    public SemanticFolderRepository[] getSemanticFolderRepositories(SiteInfo siteInfo, SemanticRepository semanticRepository, SemanticFolderRepository semanticFolderRepository) throws Exception
    {
        HashSet<SemanticFolderRepository> getSemanticFolderRepositories=new HashSet<SemanticFolderRepository>();
        WebSite website = SWBContext.getWebSite(siteInfo.id);
        Resource resource=new Resource(SemanticObject.getSemanticObject(semanticRepository.uri));
        GenericIterator<Resourceable> resourceables=resource.listResourceables();
        while(resourceables.hasNext())
        {
            Resourceable resourceable=resourceables.next();
            if(resourceable instanceof WebPage)
            {
                WebPage wp=(WebPage)resourceable;                
                Session session=null;
                String idrep=docRepNS(website);
                if(idrep!=null)
                {
                    org.semanticwb.model.User userModel = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
                    session = rep.login(new SWBCredentials(userModel), idrep);
                    Node nodeFolder = session.getNodeByUUID(semanticFolderRepository.uuid);
                    NodeIterator nit = nodeFolder.getNodes(REP_FOLDER);
                    while (nit.hasNext())
                    {
                        Node subnodofolder = nit.nextNode();
                        boolean hasChilds=false;
                        NodeIterator nc=subnodofolder.getNodes(REP_FOLDER);
                        hasChilds=nc.hasNext();
                        String title=subnodofolder.getProperty("swb:title").getString();
                        String uuid=subnodofolder.getUUID();
                        SemanticFolderRepository sf=new SemanticFolderRepository();
                        sf.name=title;
                        sf.uuid=uuid;
                        sf.haschilds=hasChilds;
                        getSemanticFolderRepositories.add(sf);
                    }
                }
            }
        }
        return getSemanticFolderRepositories.toArray(new SemanticFolderRepository[getSemanticFolderRepositories.size()]);
    }
    public SemanticFileRepository[] getSemanticFileRepositories(SiteInfo siteInfo,SemanticRepository semanticRepository) throws Exception
    {
        HashSet<SemanticFileRepository> getSemanticFileRepositories=new HashSet<SemanticFileRepository>();
        WebSite website = SWBContext.getWebSite(siteInfo.id);
        Resource resource=new Resource(SemanticObject.getSemanticObject(semanticRepository.uri));
        GenericIterator<Resourceable> resourceables=resource.listResourceables();
        while(resourceables.hasNext())
        {
            Resourceable resourceable=resourceables.next();
            if(resourceable instanceof WebPage)
            {
                WebPage wp=(WebPage)resourceable;
                String resUUID = resource.getAttribute(wp.getId() + "_uuid");
                Session session=null;
                String idrep=docRepNS(website);
                if(idrep!=null)
                {
                    org.semanticwb.model.User userModel = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
                    session = rep.login(new SWBCredentials(userModel), idrep);
                    Node nodeFolder = session.getNodeByUUID(resUUID);
                    NodeIterator nit = nodeFolder.getNodes();
                    while (nit.hasNext())
                    {
                        Node nodoFile = nit.nextNode();
                        if (nodoFile.getPrimaryNodeType().getName().equals(REP_FILE))
                        {
                            boolean isdeleted = false;
                            try {
                                isdeleted = nodoFile.getProperty(SWB_FILEREP_DELETED).getBoolean();
                            } catch (Exception e) {
                                log.event("Error al revisar la propiedad Deleted del repositorio de documentos.", e);
                            }
                            if(!isdeleted)
                            {
                                String title=nodoFile.getProperty("swb:title").getString();
                                Date created=nodoFile.getProperty("jcr:created").getDate().getTime();
                                SemanticFileRepository sf=new SemanticFileRepository();
                                sf.uuid=nodoFile.getUUID();
                                sf.title=title;
                                sf.name=nodoFile.getName();
                                sf.date=created;
                                getSemanticFileRepositories.add(sf);
                            }
                        }
                    }

                }
            }
        }
        return getSemanticFileRepositories.toArray(new SemanticFileRepository[getSemanticFileRepositories.size()]);
    }
}
