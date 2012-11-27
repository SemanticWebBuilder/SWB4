/*
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
 */
package org.semanticwb.test.wsdl;

import java.net.URL;
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
    @Ignore
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
