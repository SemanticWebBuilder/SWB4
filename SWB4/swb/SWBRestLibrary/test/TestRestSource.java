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
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rest.consume.*;
/**
 *
 * @author victor.lorenzana
 */
public class TestRestSource
{
    private static final Logger log = SWBUtils.getLogger(TestRestSource.class);
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
            RestSource source = new RestSource(new URL("http://localhost:8080/swb/rest/so"));
            ServiceInfo info = source.getServiceInfo();
            Method[] methods=info.getAllMethods();
            for(Method m : methods)
            {
                System.out.println("method "+m.getId());
            }
            Method minfo = info.getMethod("swbcomm_EventElementlistEventElements");
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
            log.debug(e);
        }
    }
}
