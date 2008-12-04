/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.test.repository;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.jcr170.implementation.RepositoryImp;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class TestRepository
{

    public TestRepository()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.createInstance(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Ignore
    public void getWorkspaceTest()
    {
        try
        {
            Repository repository = new RepositoryImp();
            SimpleCredentials credentials = new SimpleCredentials("victor", "victor".toCharArray());
            Session session = repository.login(credentials);
            Node root = session.getRootNode();
            System.out.println(session.getWorkspace().getName());
            System.out.println(root.getName());
            System.out.println(root.getPath());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void addNode()
    {
        String UUID = "";
        Session session = null;
        try
        {
            RepositoryImp repository = new RepositoryImp();            
            //repository.recreateDefaultWorkspace();
            SimpleCredentials credentials = new SimpleCredentials("victor", "victor".toCharArray());
            session = repository.login(credentials);
            String title = "Deportes";
            Workspace ws = session.getWorkspace();
            Node root = session.getRootNode();            
            NodeIterator it=root.getNodes();
            while(it.hasNext())
            {
                Node node=it.nextNode();
                node.remove();
            }
            QueryManager qmanager = ws.getQueryManager();
            Query query;
            if(repository.getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder")!=-1)
            {
        
                String statement="SELECT ?title WHERE {?x cm:title ?title FILTER regex(?title, \""+ title +"\")  }" ; 
                query = qmanager.createQuery(statement, "SPARQL");
            }
            else
            {
                query = qmanager.createQuery("//" + title + "[@cm:title='" + title + "']", Query.XPATH);
            }            
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node node = nodeIterator.nextNode();
                node.remove();
            }
            Node newNode = root.addNode(title, "cm:Category");            
            newNode.setProperty("cm:title", title);
            newNode.setProperty("cm:description", title);
            root.save();
            String id=newNode.getUUID();
            session.getNodeByUUID(id);            
            Node content = newNode.addNode("Contenido1", "cm:Content");
            content.setProperty("cm:title", "Contenido 1");
            content.setProperty("cm:description", "Contenido de prueba");
            UUID = newNode.getUUID();
            root.save();
            content.checkin();
            content.checkout();
            content.setProperty("cm:title", "Contenido 2");
            content.setProperty("cm:description", "Contenido de prueba 2");
            content.save();
            content.checkin();            
            if ( UUID == null || UUID.equals("") )
            {
                Assert.fail("UUDI incorrecto");
            }

        }
        catch ( Throwable e )
        {
            e.printStackTrace(System.out);
            fail(e.getMessage());
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
            
        }


    }
}
