/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.test;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.xmlrpc.XmlRpcProxyFactory;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class DocumentRepoteTest {

    public DocumentRepoteTest() {
    }

    @BeforeClass
    public static
    void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static
    void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test    
    public void publishTest() 
    {        
        try
        {
            IOpenOfficeDocument document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            document.setUser("victor");
            document.setPassword("victor");
            String id=document.publish("demo", "description", "/", "WORD");
            System.out.println(id);
        }
        catch(URISyntaxException ure)
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch(Exception e)
        {
            Assert.fail(e.getLocalizedMessage());
        }
    }
    
    @Test
    public void existTest() 
    {        
        try
        {
            IOpenOfficeDocument document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, new URI("http://localhost:8084/TestRPC/GatewayOffice"));
            document.setUser("victor");
            document.setPassword("victor");
            boolean actual=document.exists("/demo[4]");                    
            Assert.assertTrue(actual);
        }
        catch(URISyntaxException ure)
        {
            Assert.fail(ure.getLocalizedMessage());
        }
        catch(Exception e)
        {
            Assert.fail(e.getLocalizedMessage());
        }
    }

}