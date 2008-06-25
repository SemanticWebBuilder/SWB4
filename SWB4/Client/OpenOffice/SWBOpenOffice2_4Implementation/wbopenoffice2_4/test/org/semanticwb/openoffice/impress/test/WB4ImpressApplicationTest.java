/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.impress.test;

import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.openoffice.impress.WB4ImpressApplication;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class WB4ImpressApplicationTest
{

    XComponentContext xContext;
    XDesktop oDesktop = null;
    File sUrlDestiny = new File("c:/temp/demo.odp");
    File tempDir = new File("c:/temp/demo/");

    public WB4ImpressApplicationTest()
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
        try
        {
            xContext = Bootstrap.bootstrap();


            // Obtener la factoria de servicios de OpenOffice   
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
            oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);


        }
        catch (com.sun.star.uno.Exception e)
        {
            e.printStackTrace(System.out);
        }
        catch (BootstrapException be)
        {
            be.printStackTrace(System.out);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    @After
    public void tearDown()
    {
        oDesktop.terminate();
    }

    @Test
    public void openTest()
    {
        try
        {
            WB4ImpressApplication application=new WB4ImpressApplication(xContext);            
            application.open(sUrlDestiny);
            Assert.assertEquals(application.getDocuments().size(),1);
        }
        catch(Exception e)
        {
            Assert.fail();
        }
        
    }
}
