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
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class,new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setWebAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice"));            
            obj.setUser("v");
            obj.setPassword("h");
            obj.setProxyAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setProxyPort(8080);
            obj.addAttachment(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            String res=obj.add(5, 5.5, "a", new Date(), false);            
            Assert.assertNotNull(res);
            System.out.println(res);
        }
        catch ( Exception e )
        {
            if(e!=null && e.getMessage()!=null)
            {    
                fail(e.getMessage());
            }
        }

    }
    @Test    
    @Ignore
    public void executeTest()
    {
        try
        {                        
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class,new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setWebAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice"));                        
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
    

    @Test(expected = HttpException.class)    
    @Ignore
    public void executeTestWithOutPassword() throws HttpException
    {
        try
        {
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class,new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setWebAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            String res=obj.add(4, -220.4, "Demo", new Date(), true);            
            Assert.assertNotNull(res);
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
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class,new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setWebAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setUser("v");
            obj.setPassword("h");
            obj.addAttachment(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            String res=obj.add(4, -220.4, "Demo", new Date(), true);
            Assert.assertNotNull(res);                        
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
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class,new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setWebAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice2"));
            obj.setUser("v");
            obj.setPassword("h");
            obj.addAttachment(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            String res=obj.add(4, -220.4, "Demo", new Date(), true);
            Assert.assertNotNull(res);                        
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

    @Test(expected = HttpException.class)    
    @Ignore
    public void executeTestWithAttachmentsAndNotMethod() throws HttpException
    {
        try
        {
            IDemo obj = XmlRpcProxyFactory.newInstance(IDemo.class,new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            obj.setWebAddress(new URI("http://localhost:8084/TestRPC/GatewayOffice2"));
            obj.setUser("v");
            obj.setPassword("h");
            obj.addAttachment(new Attachment(new File("C:\\temp\\demo.ppt"), "content"));
            String res=obj.add(4, -220.4, "Demo", new Date(), 1);
            Assert.assertNotNull(res); 
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
}
