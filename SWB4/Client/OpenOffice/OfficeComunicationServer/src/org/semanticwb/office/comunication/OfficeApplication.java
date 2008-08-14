/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.util.ArrayList;
import java.util.Map;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements RepositorySupport, IOfficeApplication
{

    Map<String, Repository> repositories;
    //private Session session;
    public boolean isValidVersion(double version)
    {
        return IOfficeApplication.version >= version;
    }

    private Session openSession() throws LoginException, RepositoryException
    {
        Session session = repositories.get("wbrepository").login(new SimpleCredentials("", "".toCharArray()));
        return session;
    }
    
    private Session openSession(String repositoryName) throws LoginException, RepositoryException
    {
        Session session = repositories.get(repositoryName).login(new SimpleCredentials("", "".toCharArray()));
        return session;
    }

    public void setRepositories(Map<String, Repository> repositories)
    {
        if ( repositories == null )
        {
            throw new IllegalArgumentException("The session can be null");
        }
        this.repositories = repositories;
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

    public String[] getRepositories() throws Exception            
    {
        return repositories.keySet().toArray(new String[repositories.keySet().size()]);
    }
    public String[] getCategories(String repositoryName) throws Exception
    {        
        Session session = null;
        try
        {
            session = openSession(repositoryName);
            ArrayList<String> categories=new ArrayList<String>();
            Query query = session.getWorkspace().getQueryManager().createQuery("//Category", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode=nodeIterator.nextNode();
                categories.add(categoryNode.getUUID()+","+categoryNode.getProperty("cm:title").getString());
            }
            return categories.toArray(new String[categories.size()]);
        }      
        catch(Exception e)
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

    public String createCategory(String repositoryName,String title, String description) throws Exception
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
