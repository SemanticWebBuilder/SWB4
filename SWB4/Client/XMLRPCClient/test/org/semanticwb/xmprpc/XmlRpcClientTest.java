/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmprpc;

import org.semanticwb.xmlrpc.XmlRpcProxyFactory;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.xmlrpc.Attachment;
import org.semanticwb.xmlrpc.HttpException;
import org.semanticwb.xmlrpc.XmlRpcClient;
import org.semanticwb.xmlrpc.XmlRpcClientConfig;
import org.semanticwb.xmlrpc.XmlRpcException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcClientTest
{

    public XmlRpcClientTest()
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
    public void executeTestWithProxy()
    {
        try
        {                        
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class);
            obj.setUri(new URI("http://localhost:8084/TestRPC/GatewayOffice"));            
            obj.setUser("v");
            obj.setPassword("h");
            String res=obj.add(5, 5.5, "a", new Date(), false);            
            Assert.assertNotNull(res);
            System.out.println(res);
        }
        catch ( Exception e )
        {
            fail(e.getMessage());
        }

    }

    @Test
    @Ignore
    public void executeTest()
    {

        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig(new URI("http://localhost:8084/TestRPC/GatewayOffice"), "v", "h");
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            Object[] params = {4, -220.4, "Demo", new Date(), true};
            List<Attachment> attachments = new ArrayList<Attachment>();
            StringBuilder builder = new StringBuilder();
            for ( Object obj : params )
            {
                builder.append(obj);
            }
            String res = client.execute("Demo.add", params, attachments);
            Assert.assertEquals(res, builder.toString());

        }
        catch ( Exception e )
        {
            fail(e.getMessage());
        }
    }

    @Test(expected = HttpException.class)
    @Ignore
    public void executeTestWithOutPassword() throws HttpException
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            Object[] params = {4, -220.4, "Demo", new Date(), true};
            List<Attachment> attachments = new ArrayList<Attachment>();
            StringBuilder builder = new StringBuilder();
            for ( Object obj : params )
            {
                builder.append(obj);
            }
            String res = client.execute("Demo.add", params, attachments);
            Assert.assertEquals(res, builder.toString());

        }
        catch ( URISyntaxException e )
        {
            fail(e.getMessage());
        }
        catch ( XmlRpcException e )
        {
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void executeTestWithAttachments()
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig(new URI("http://localhost:8084/TestRPC/GatewayOffice"), "victor", "Lorenzana");
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            int[] array = {4, 5, 6};
            Object[] params = {4, -220.4, "Demo", new Date(), true};
            List<Attachment> attachments = new ArrayList<Attachment>();
            attachments.add(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            StringBuilder builder = new StringBuilder();
            for ( Object obj : params )
            {
                builder.append(obj);
            }
            String res = client.execute("Demo.add", params, attachments);
            Assert.assertEquals(res, builder.toString());
        }
        catch ( Exception e )
        {
            fail(e.getMessage());
        }
    }

    @Test(expected = HttpException.class)
    @Ignore
    public void executeTestWithAttachmentsWithNoCorrectPath() throws HttpException
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig(new URI("http://localhost:8084/TestRPC/GatewayOffice2"), "victor", "Lorenzana");
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            int[] array = {4, 5, 6};
            Object[] params = {4, -220.4, "Demo", new Date(), true, array};
            List<Attachment> attachments = new ArrayList<Attachment>();
            attachments.add(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            StringBuilder builder = new StringBuilder();
            for ( Object obj : params )
            {
                builder.append(obj);
            }
            String res = client.execute("Demo.add", params, attachments);
            Assert.assertEquals(res, builder.toString());
        }
        catch ( XmlRpcException e )
        {
            fail(e.getMessage());
        }
        catch ( URISyntaxException e )
        {
            fail(e.getMessage());
        }
    }

    @Test(expected = XmlRpcException.class)
    @Ignore
    public void executeTestWithAttachmentsAndNotMethod() throws XmlRpcException
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig(new URI("http://localhost:8084/TestRPC/GatewayOffice"), "victor", "lorenzana");
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            Object[] params = {4, -220.4, "Demo αινσϊ", new Date(), true, 5};
            List<Attachment> attachments = new ArrayList<Attachment>();
            attachments.add(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            StringBuilder builder = new StringBuilder();
            for ( Object obj : params )
            {
                builder.append(obj);
            }
            String res = client.execute("Demo.hi", params, attachments);
            Assert.assertEquals(res, builder.toString());
        }
        catch ( HttpException e )
        {
            fail(e.getMessage());
        }
        catch ( URISyntaxException e )
        {
            fail(e.getMessage());
        }
    }
}
