/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.openoffice.interfaces.IOpenOfficeApplication;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.xmlrpc.Attachment;
import org.semanticwb.xmlrpc.XmlRpcProxyFactory;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class DocumentRepoteTest
{

    public DocumentRepoteTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
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

    @Test
    @Ignore
    public void getRepositoriesTest()
    {
        try
        {
            IOpenOfficeApplication application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            application.setUser("victor");
            application.setPassword("victor");
            String[] repNames=application.getRepositories();
            for ( String repName :  repNames)
            {
                System.out.println(repName);
            }
            Assert.assertEquals(repNames.length, 1);
        }
        catch ( URISyntaxException ure )
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getLocalizedMessage());
        }

    }
    @Test    
    public void getCategoriesTest()
    {
        try
        {
            IOpenOfficeApplication application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            application.setUser("victor");
            application.setPassword("victor");
            String[] repNames=application.getRepositories();
            CategoryInfo[] catinfo=application.getCategories(repNames[0]);            
            Assert.assertTrue(catinfo.length>0);
        }
        catch ( URISyntaxException ure )
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getLocalizedMessage());
        }

    }
    @Test
    @Ignore
    public void publishTest()
    {
        try
        {
            IOpenOfficeApplication application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            application.setUser("victor");
            application.setPassword("victor");
            String categoryID = application.createCategory(application.getRepositories()[0],"Mis documentos", "Contenidos publicados por mi");
            IOpenOfficeDocument document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            document.setUser("victor");
            document.setPassword("victor");
            document.addAttachment(new Attachment(new File("c:\\temp\\demo.doc")));
            String id1 = document.publish("demo publication 'a'a'a'a++++", "description",application.getRepositories()[0], categoryID, "WORD");
            String id2 = document.publish("demo publication 'a'a'a'a++++", "description",application.getRepositories()[0], categoryID, "WORD");
            System.out.println(id1);
        }
        catch ( URISyntaxException ure )
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    
    
    @Test    
    @Ignore
    public void createCategoryTest()
    {
        try
        {
            IOpenOfficeApplication application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            application.setUser("victor");
            application.setPassword("victor");
            String[] repNames=application.getRepositories();
            for ( String repName :  repNames)
            {
                System.out.println(repName);
                String UUDI=application.createCategory(repName, "Deportes","Categoria de deportes");
                System.out.println(UUDI);
            }
            Assert.assertEquals(repNames.length, 1);
        }
        catch ( URISyntaxException ure )
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getLocalizedMessage());
        }

    }

    @Test
    @Ignore
    public void updateTest()
    {
        try
        {
            IOpenOfficeApplication application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            application.setUser("victor");
            application.setPassword("victor");
            String categoryID = application.createCategory(application.getRepositories()[0],"Mis documentos", "Contenidos publicados por mi");

            IOpenOfficeDocument document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            document.setUser("victor");
            document.setPassword("victor");
            document.addAttachment(new Attachment(new File("c:\\temp\\demo.doc")));
            String id1 = document.publish("demo publication 'a'a'a'a++++", "description", application.getRepositories()[0],categoryID, "WORD");
            document.addAttachment(new Attachment(new File("c:\\temp\\demo.doc")));
            document.addAttachment(new Attachment(new File("c:\\temp\\demo.odp")));
            String version = document.updateContent(id1);

            document.addAttachment(new Attachment(new File("c:\\temp\\demo.odp")));
            version = document.updateContent(id1);

            document.addAttachment(new Attachment(new File("c:\\temp\\demo.doc")));
            version = document.updateContent(id1);

            System.out.println(id1);
        }
        catch ( URISyntaxException ure )
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    

    @Test
    @Ignore
    public void existTest()
    {
        try
        {
            IOpenOfficeDocument document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            document.setUser("victor");
            document.setPassword("victor");
            boolean actual = document.exists("/demo[4]");
            Assert.assertTrue(actual);
        }
        catch ( URISyntaxException ure )
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch ( Exception e )
        {
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
