/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.rest.consume.Method;
import org.semanticwb.rest.consume.ParameterDefinition;
import org.semanticwb.rest.consume.ParameterValue;
import org.semanticwb.rest.consume.RepresentationResponse;
import org.semanticwb.rest.consume.Resource;
import org.semanticwb.rest.consume.RestSource;
import org.semanticwb.rest.consume.ServiceInfo;
import org.semanticwb.rest.consume.XmlResponse;

/**
 *
 * @author victor.lorenzana
 */
public class TestRestSource
{

    public TestRestSource()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.createInstance();
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
    public void hello()
    {

        try
        {
            RestSource source = new RestSource(new URL("http://localhost:8080/swb/restTest.jsp"));
            ServiceInfo info = source.getServiceInfo();
            Resource res = info.getResource("EventElement");
            Method minfo = res.getMethod("_listEventElements");
            ArrayList<ParameterValue> values = new ArrayList<ParameterValue>();
            RepresentationResponse resp = minfo.request(values);
            ParameterDefinition[] parameters = resp.getParameterDefinitions();
            for (ParameterDefinition parameter : parameters)
            {
                if(resp instanceof XmlResponse)
                {
                    XmlResponse xmlResponse=(XmlResponse)resp;

                    URL[] objs = xmlResponse.getLinks(parameter);
                    for (URL obj : objs)
                    {
                        System.out.println("obj: " + obj);
                    }
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (e.getCause() != null)
            {
                e.getCause().printStackTrace();
            }
        }
    }
}
