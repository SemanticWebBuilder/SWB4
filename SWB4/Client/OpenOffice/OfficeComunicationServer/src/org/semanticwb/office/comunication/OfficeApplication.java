/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.util.ArrayList;
import java.util.HashMap;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.ContentType;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.repository.RepositoryManagerLoader;
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

    public void createPage(String title, String id, String description) throws Exception
    {
    }

    public void changePassword(String newPassword) throws Exception
    {
    }

    public boolean existsPage(String id) throws Exception
    {
        return false;
    }

    public String[] getContents(String repositoryName, String categoryID) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, "", "");
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

    public String[] getRepositories() throws Exception
    {
        return loader.getWorkspacesForOffice();
    }

    public CategoryInfo[] getCategories(String repositoryName) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, "", "");
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Node root = session.getRootNode();
            NodeIterator it = root.getNodes();
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            while (it.hasNext())
            {
                Node node = it.nextNode();
                if (node.getName().equals(cm_category))
                {
                    CategoryInfo category = new CategoryInfo();
                    category.UDDI = node.getUUID();
                    String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                    String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                    category.description = node.getProperty(cm_description).getValue().getString();
                    category.title = node.getProperty(cm_title).getValue().getString();
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

    public String createCategory(String repositoryName, String title, String description) throws Exception
    {
        String UUID = "";
        Session session = null;
        Node root = null;
        try
        {
            session = loader.openSession(repositoryName, "", "");
            root = session.getRootNode();
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            Query query;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                String statement = "SELECT ?title WHERE {?x cm:title ?title FILTER regex(?title, \"" + title + "\")  }";
                query = session.getWorkspace().getQueryManager().createQuery(statement, "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//" + cm_category + "[@cm:title='" + title + "']", Query.XPATH);
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
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                newNode.setProperty(cm_title, title);
                newNode.setProperty(cm_description, description);
                root.save();
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

    public boolean canDeleteCategory(String repositoryName, String id) throws Exception
    {
        boolean canDeleteCategory = false;
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, "", "");
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
            session = loader.openSession(repositoryName, "", "");
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
            session = loader.openSession(repositoryName, "", "");
            parent = session.getNodeByUUID(categoryId);
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            Query query;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                String statement = "SELECT ?title WHERE {?x cm:title ?title FILTER regex(?title, \"" + title + "\")  }";
                query = session.getWorkspace().getQueryManager().createQuery(statement, "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//" + cm_category + "[@cm:title='" + title + "']", Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            if (nodeIterator.hasNext())
            {
                UUID = nodeIterator.nextNode().getUUID();
            }
            else
            {
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                Node newNode = parent.addNode(cm_category, cm_category);
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

    public CategoryInfo[] getCategories(String repositoryName, String categoryId) throws Exception
    {
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, "", "");
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Node categoryNode = session.getNodeByUUID(categoryId);
            NodeIterator it = categoryNode.getNodes();
            String cm_category = loader.getOfficeManager(repositoryName).getCategoryType();
            while (it.hasNext())
            {
                Node node = it.nextNode();
                if (node.getName().equals(cm_category))
                {
                    CategoryInfo category = new CategoryInfo();
                    category.UDDI = node.getUUID();
                    String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                    String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                    category.description = node.getProperty(cm_description).getValue().getString();
                    category.title = node.getProperty(cm_title).getValue().getString();
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
}
