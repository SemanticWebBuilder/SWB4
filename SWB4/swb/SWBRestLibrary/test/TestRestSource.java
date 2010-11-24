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
import org.semanticwb.SWBUtils;
import org.semanticwb.resources.rest.ApplicationXML;
import org.semanticwb.resources.rest.Method;
import org.semanticwb.resources.rest.ParameterValue;
import org.semanticwb.resources.rest.RepresentationResponse;
import org.semanticwb.resources.rest.Resource;
import org.semanticwb.resources.rest.RestSource;
import org.semanticwb.resources.rest.ServiceInfo;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class TestRestSource {

    public TestRestSource() {
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
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        
        try
        {
            RestSource source = new RestSource(new URL("http://localhost:8080/swb/restTest.jsp"));
            ServiceInfo info=source.getServiceInfo();
            for(Resource res : info.getResources())
            {                
                for(Method minfo : res.getMethods())
                {
                    if(minfo.getId().equals("deleteEventElement"))
                    {
                        ArrayList<ParameterValue> values=new ArrayList<ParameterValue>();
                        values.add(new ParameterValue("uri", "reg_digital_demo:swbcomm_EventElement:42"));
                        RepresentationResponse resp=minfo.getDefaultRequestRepresentation().request(values);
                        System.out.print("obj :"+resp.getObject().getClass().getName());
                        if(resp instanceof ApplicationXML)
                        {
                            Document doc=((ApplicationXML)resp).getDocument();
                            String xml=SWBUtils.XML.domToXml(doc);
                            System.out.println("Respuesta XML: ");
                            System.out.println(xml);
                        }
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(e.getCause()!=null)
            {
                e.getCause().printStackTrace();
            }
        }
    }

}