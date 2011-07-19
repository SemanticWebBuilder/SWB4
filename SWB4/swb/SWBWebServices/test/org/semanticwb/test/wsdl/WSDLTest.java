/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.test.wsdl;

import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
import com.sun.xml.internal.ws.api.model.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
    @Ignore
    public void ConversionRate()
    {
        try
        {

            ServiceInfo info = WebService.getServiceinfo(new URL("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL"));
            Operation op = info.getServices()[0].getOperationByName("CurrencyConvertorSoap_CurrencyConvertorSoap_ConversionRate");

            JSONObject request = new JSONObject();
            JSONObject parameters = new JSONObject();
            request.accumulate("parameters", parameters);
            parameters.accumulate("FromCurrency", "XXX");
            parameters.accumulate("ToCurrency", "DZD");
            JSONObject response = op.execute(request);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void OrdenWSService()
    {
        try
        {
            ServiceInfo info = WebService.getServiceinfo(new URL("http://www.biess.fin.ec:8080/BiessShopService/OrdenWSService?wsdl"));
            Assert.assertEquals(info.getServices().length,1);
            Service service=info.getServices()[0];
            Assert.assertEquals(service.getOperations().length,2);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    //@Ignore
    public void testLoadWSDL()
    {
        try
        {
            
            ServiceInfo info = WebService.getServiceinfo(new URL("http://webservices.amazon.com/AWSECommerceService/AWSECommerceService.wsdl"));            
            //ServiceInfo info=WebService.getServiceinfo(new URL("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL"));

            for (Service service : info.getServices())
            {
                System.out.println("service: " + service.getId());
                for (Operation operation : service.getOperations())
                {
                    System.out.println("operation: " + operation.getName());
                    for (ParameterDefinition def : operation.getInput().getDefinitions())
                    {
                        System.out.println("def:" + def.getName());
                    }
                    for (ParameterDefinition def : operation.getOutput().getDefinitions())
                    {
                        System.out.println("def out:" + def.getName());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
