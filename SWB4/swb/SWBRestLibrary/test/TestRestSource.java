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
import org.semanticwb.rest.consume.*;
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
        /*String data="{\"context\":{\"country\":\"default\",\"language\":\"default\",\"view\":\"default\",\"container\":\"default\"},\"gadgets\":[{\"url\":\"http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml\",\"moduleId\":1}]}";
        try
        {
            URL url=new URL("http://localhost:8080/gadgets/metadata?st=john.doe:john.doe:appid:cont:url:0:default");
            java.net.HttpURLConnection con= (java.net.HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "JSON");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out=con.getOutputStream();
            out.write(data.getBytes());
            out.close();
            InputStream in=con.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1028];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
            in.close();
            System.out.println(sb.toString());
        }
        catch(Exception e)
        {
            
        }*/
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
            e.printStackTrace();
            if (e.getCause() != null)
            {
                e.getCause().printStackTrace();
            }
        }
    }
}
