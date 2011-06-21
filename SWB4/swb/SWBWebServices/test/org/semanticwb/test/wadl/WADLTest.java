/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.test.wadl;

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
public class WADLTest
{
    
    public WADLTest()
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
    public void testLoadWADL()
    {
        try
        {
            ServiceInfo info=WebService.getServiceinfo(new URL("http://localhost:8080/swb/rest/so"));
            for(Service service : info.getServices())
            {
                System.out.println("servicio: "+service.getId());
                for(Operation operation : service.getOperations())
                {
                    System.out.println("operation: "+operation.getName());              
                    for(ParameterDefinition def : operation.getInput().getDefinitions())
                    {
                         System.out.println("code: "+ def.getDefinitionClass().getCode());
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
