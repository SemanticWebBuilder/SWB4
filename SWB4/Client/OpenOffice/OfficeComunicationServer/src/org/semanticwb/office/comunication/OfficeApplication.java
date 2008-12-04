/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;


import java.util.ArrayList;
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
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.repository.SWBRepositoryManager;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements IOfficeApplication
{
    private static final String CATEGORY_NAME = "Category";
    private static final String CM_CATEGORY = "cm:Category";
    private static final String CM_TITLE = "cm:tite";
    private static final String CM_DESCRIPTION = "cm:description";
    private static final RepositoryManagerLoader loader=RepositoryManagerLoader.getInstance();
        
    
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
            
            session = loader.openSession(repositoryName,"","");
            ArrayList<String> contents = new ArrayList<String>();
            Node categoryNode = session.getNodeByUUID(categoryID);
            NodeIterator nodes = categoryNode.getNodes(CATEGORY_NAME);
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
                    content.append(nodeContent.getProperty(CM_TITLE).getString());
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
        return loader.getWorkspacesForOffice();
    }

    public CategoryInfo[] getCategories(String repositoryName) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName,"","");
            ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
            Query query;
            if(session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder")!=-1)
            {
                String statement="SELECT ?name WHERE {?x swbrep:name ?name FILTER regex(?name, \""+ CATEGORY_NAME +"\")  }" ; 
                query = session.getWorkspace().getQueryManager().createQuery(statement, "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//"+CATEGORY_NAME, Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode = nodeIterator.nextNode();
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.UDDI = categoryNode.getUUID();
                categoryInfo.title = categoryNode.getProperty(CM_TITLE).getString();
                categoryInfo.description = categoryNode.getProperty(CM_DESCRIPTION).getString();
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
        Node root = session.getRootNode();
        root.lock(false,false);
        try
        {
            session = loader.openSession(repositoryName,"","");
            Query query;
            if(session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder")!=-1)
            {
                String statement="SELECT ?title WHERE {?x cm:title ?title FILTER regex(?title, \""+ title +"\")  }" ; 
                query = session.getWorkspace().getQueryManager().createQuery(statement, "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//"+ CATEGORY_NAME +"[@cm:title='" + title + "']", Query.XPATH);
            }            
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            if ( nodeIterator.hasNext() )
            {
                UUID = nodeIterator.nextNode().getUUID();
            }
            else
            {
                Node newNode = root.addNode(CATEGORY_NAME,CM_CATEGORY);
                newNode.setProperty( CM_TITLE,title);
                newNode.setProperty( CM_DESCRIPTION,description);
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
                root.unlock();
                session.logout();
            }
        }

        return UUID;
    }
}
