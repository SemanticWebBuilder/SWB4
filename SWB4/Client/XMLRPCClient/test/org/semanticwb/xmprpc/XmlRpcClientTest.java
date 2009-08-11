/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
