/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements RepositorySupport, IOfficeApplication
{

    private static final String CONTENT_MODE_PATH = "C:\\repositorio\\contentmodel.cnd";
    public static final String URI_NODEDEF = "http://www.semanticwb.org.mx/model/content/1.0";
    public static final String NODEDEF_PREFIX = "swb";
    public static final String URI_CONTENT = "http://www.semanticwb.org.mx/model/content/1.0/cm";
    public static final String CONTENT_PREFIX = "cm";
    private static Map<String, Repository> repositories;
    //private Session session;
    public boolean isValidVersion(double version)
    {
        return IOfficeApplication.version >= version;
    }

    public boolean hasListOfRepositories()
    {
        boolean hasListOfRepositories = false;
        if ( repositories != null )
        {
            hasListOfRepositories = true;
        }
        return hasListOfRepositories;
    }

    public static void RegisterCustomNodeTypes(Workspace ws, String cndFileName) throws Exception
    {

        boolean exists = false;
        for ( String uri : ws.getNamespaceRegistry().getURIs() )
        {
            if ( uri.equals(URI_NODEDEF) )
            {
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            try
            {
                ws.getNamespaceRegistry().registerNamespace(NODEDEF_PREFIX, URI_NODEDEF);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }
        exists = false;
        for ( String uri : ws.getNamespaceRegistry().getURIs() )
        {
            if ( uri.equals(URI_CONTENT) )
            {
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            try
            {
                ws.getNamespaceRegistry().registerNamespace(CONTENT_PREFIX, URI_CONTENT);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }


        // Read in the CND file
        FileReader fileReader = new FileReader(cndFileName);

        // Create a CompactNodeTypeDefReader
        CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(fileReader, cndFileName);

        // Get the List of NodeTypeDef objects
        List ntdList = cndReader.getNodeTypeDefs();

        // Get the NodeTypeManager from the Workspace.
        // Note that it must be cast from the generic JCR NodeTypeManager to the
        // Jackrabbit-specific implementation.        
        NodeTypeManagerImpl ntmgr = ( NodeTypeManagerImpl ) ws.getNodeTypeManager();

        // Acquire the NodeTypeRegistry
        NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();

        // Loop through the prepared NodeTypeDefs
        for ( Iterator i = ntdList.iterator(); i.hasNext();)
        {
            // Get the NodeTypeDef...
            NodeTypeDef ntd = ( NodeTypeDef ) i.next();
            try
            {
                if ( !ntreg.isRegistered(ntd.getName()) )
                {
                    ntreg.registerNodeType(ntd);
                }
            }
            catch ( InvalidNodeTypeDefException e )
            {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    public static Session openSession() throws Exception
    {
        if ( repositories.size() > 0 )
        {
            Set<String> keys = repositories.keySet();
            Session session = repositories.get(keys.iterator().next()).login(new SimpleCredentials("", "".toCharArray()));
            RegisterCustomNodeTypes(session.getWorkspace(), CONTENT_MODE_PATH);
            return session;
        }
        throw new Exception("There are not repositories");
    }

    public static Session openSession(String repositoryName) throws Exception
    {
        if ( repositories.get(repositoryName) == null )
        {
            throw new Exception("The repository " + repositoryName + " does not exist");
        }
        Session session = repositories.get(repositoryName).login(new SimpleCredentials("", "".toCharArray()));
        RegisterCustomNodeTypes(session.getWorkspace(), CONTENT_MODE_PATH);
        return session;
    }

    public void setRepositories(Map<String, Repository> repositories)
    {
        if ( repositories != null )
        {
            throw new IllegalArgumentException("The repository list already exists");
        }
        OfficeApplication.repositories = repositories;
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
            session = openSession(repositoryName);
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
                    content.append(nodeContent.getProperty("cm:title").getString());
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
        return repositories.keySet().toArray(new String[repositories.keySet().size()]);
    }

    public CategoryInfo[] getCategories(String repositoryName) throws Exception
    {
        Session session = null;
        try
        {
            session = openSession(repositoryName);
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Query query = session.getWorkspace().getQueryManager().createQuery("//Category", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode = nodeIterator.nextNode();
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.UDDI = categoryNode.getUUID();
                categoryInfo.title = categoryNode.getProperty("cm:title").getString();
                categoryInfo.description = categoryNode.getProperty("cm:description").getString();
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
            session = openSession(repositoryName);
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
                newNode.setProperty("cm:title", title);
                newNode.setProperty("cm:description", description);
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
