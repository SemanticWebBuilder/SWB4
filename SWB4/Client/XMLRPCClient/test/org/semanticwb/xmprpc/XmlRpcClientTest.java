/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmprpc;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.xmlrpc.Attachment;
import org.semanticwb.xmlrpc.XmlRpcClient;
import org.semanticwb.xmlrpc.XmlRpcClientConfig;
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
    @Ignore
    public void executeTest()
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig();
            config.setServerURI(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            Object[] params = {4, -220.4, "Demo", new Date(), true};            
            List<Attachment> attachments=new ArrayList<Attachment>();
            StringBuilder builder=new StringBuilder();
            for(Object obj : params)
            {
                builder.append(obj);
            }
            String res=client.execute("Demo.add",params,attachments);
            Assert.assertEquals(res, builder.toString()); 
                    
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test        
    public void executeTestWithAttachments()
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig();
            config.setServerURI(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            Object[] params = {4, -220.4, "Demo αινσϊ", new Date(), true};            
            List<Attachment> attachments=new ArrayList<Attachment>();
            attachments.add(new Attachment(new File("C:\\temp\\demo.ppt"),"content"));
            StringBuilder builder=new StringBuilder();
            for(Object obj : params)
            {
                builder.append(obj);
            }
            String res=client.execute("Demo.add",params,attachments);
            Assert.assertEquals(res, builder.toString());        
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test    
    @Ignore
    public void executeTestWithAttachmentsAndNotMethod()
    {
        try
        {
            XmlRpcClientConfig config = new XmlRpcClientConfig();
            config.setServerURI(new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            XmlRpcClient<String> client = new XmlRpcClient<String>(config);
            Object[] params = {4, -220.4, "Demo αινσϊ", new Date(), true};            
            List<Attachment> attachments=new ArrayList<Attachment>();
            attachments.add(new Attachment(new File("C:\\temp\\demo.ppt"),"content"));
            StringBuilder builder=new StringBuilder();
            for(Object obj : params)
            {
                builder.append(obj);
            }
            String res=client.execute("Demo.hi",params,attachments);
            Assert.assertEquals(res, builder.toString());        
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
}
