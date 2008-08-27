/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;


import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.repository.RepositoryManager;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements IOfficeApplication
{   
    private static final String CONTENT_MODEL_URI = "http://www.semanticwb.org.mx/model/content/1.0";
    private static final String CONTENT_MODEL_PREFIX = "swb";
    private static final String CONTENT_URI = "http://www.semanticwb.org.mx/model/content/1.0/cm";
    private static final String CONTENT_PREFIX = "cm";
    private static final String CONTENT_TITE = CONTENT_PREFIX+":title";
    private static final String CONTENT_DESCRIPTION = CONTENT_PREFIX+":description";
    static
    {
        try
        {
            RepositoryManager.addNamespace(CONTENT_MODEL_PREFIX, new URI(CONTENT_MODEL_URI));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        try
        {
            RepositoryManager.addNamespace(CONTENT_PREFIX, new URI(CONTENT_URI));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }        
    }

    
    
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
            session = RepositoryManager.openSession(repositoryName,"","");
            ArrayList<String> contents = new ArrayList<String>();
            Node categoryNode = session.getNodeByUUID(categoryID);
            NodeIterator nodes = categoryNode.getNodes("Content");
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
                    content.append(nodeContent.getProperty(CONTENT_TITE).getString());
                    contents.add(content.toString());
                }
            }
            return contents.toArray(new String[contents.size()]);
        }
        catch ( Exception e )
        {
            throw e;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    public String[] getRepositories() throws Exception
    {
        return RepositoryManager.getRepositoryNames().toArray(new String[RepositoryManager.getRepositoryNames().size()]);
    }

    public CategoryInfo[] getCategories(String repositoryName) throws Exception
    {
        Session session = null;
        try
        {
            session = RepositoryManager.openSession(repositoryName,"","");
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Query query = session.getWorkspace().getQueryManager().createQuery("//Category", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode = nodeIterator.nextNode();
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.UDDI = categoryNode.getUUID();
                categoryInfo.title = categoryNode.getProperty(CONTENT_TITE).getString();
                categoryInfo.description = categoryNode.getProperty(CONTENT_DESCRIPTION).getString();
                categories.add(categoryInfo);
            }
            return categories.toArray(new CategoryInfo[categories.size()]);
        }
        catch ( Exception e )
        {
            throw e;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    public String createCategory(String repositoryName, String title, String description) throws Exception
    {
        String UUID = "";
        Session session = null;
        try
        {
            session = RepositoryManager.openSession(repositoryName,"","");
            Query query = session.getWorkspace().getQueryManager().createQuery("//Category[@cm:title='" + title + "']", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            if ( nodeIterator.hasNext() )
            {
                UUID = nodeIterator.nextNode().getUUID();
            }
            else
            {
                Node root = session.getRootNode();
                Node newNode = root.addNode("Category", "swb:categoryType");
                newNode.setProperty( CONTENT_TITE,title);
                newNode.setProperty( CONTENT_DESCRIPTION,description);
                root.save();
                UUID = newNode.getUUID();
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
            throw e;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }

        return UUID;
    }
}
