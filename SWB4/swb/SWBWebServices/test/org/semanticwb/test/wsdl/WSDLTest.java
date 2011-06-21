/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.test.wsdl;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Service;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.WebService;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLTest
{
    
    public WSDLTest()
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
    public void testLoadWSDL()
    {
        try
        {
            //"
            ServiceInfo info=WebService.getServiceinfo(new URL("http://webservices.amazon.com/AWSECommerceService/AWSECommerceService.wsdl"));
            //ServiceInfo info=WebService.getServiceinfo(new URL("http://www.biess.fin.ec:8080/BiessShopService/OrdenWSService?wsdl"));
            //ServiceInfo info=WebService.getServiceinfo(new URL("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL"));
            
            for(Service service : info.getServices())
            {
                System.out.println("service: "+service.getId());                
                for(Operation operation : service.getOperations())
                {
                    System.out.println("operation: "+operation.getName());              
                    for(ParameterDefinition def : operation.getInput().getDefinitions())
                    {
                         System.out.println("code: \r\n"+ def.getDefinitionClass().getCode());
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
