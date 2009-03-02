/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.write.test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.Configuration;
import org.semanticwb.openoffice.ConfigurationListURI;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.ErrorLog;
import org.semanticwb.openoffice.writer.WB4WriterApplication;

/**
 *
 * @author victor.lorenzana
 */
public class WB4WriterApplicationTest
{
    XComponentContext xContext;
    XComponent xCompDest = null;
    XDesktop oDesktop = null;
    File sUrlDestiny = new File("C:\\temp\\articulo.odt");
    public WB4WriterApplicationTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        File home=new File(System.getProperty("user.home"));
        System.setProperty(ConfigurationListURI.CONFIGURATION, home.getPath()+"/list.xml");
        System.setProperty(Configuration.CONFIGURATION_PROPERTY_NAME, home.getPath()+"/config.xml");
        System.setProperty(ErrorLog.CONFIGURATION, home.getPath());
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
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
            oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);

            // Obtener interfaz XComponentLoader del XDesktop
            XComponentLoader xCompLoader = ( XComponentLoader ) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);

            // Cargar el documento en una nueva ventana oculta del XDesktop
            PropertyValue[] loadProps = new PropertyValue[0];
            /*loadProps[0] = new PropertyValue();
            loadProps[0].Name = "Hidden";
            loadProps[0].Value = new Boolean(false);*/
            String url = "file:///" + sUrlDestiny.getPath().replace('\\', '/');
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);


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
        this.oDesktop.terminate();
    }

    
    @Test
    @Ignore
    public void openTest()
    {
        try
        {
            WB4WriterApplication application=new WB4WriterApplication(xContext);            
            //application.open(sUrlDestiny);
            application.open(DocumentType.WORD);
            Assert.assertEquals(application.getDocuments().size(),1);
        }
        catch(Exception e)
        {
            Assert.fail();
        }
        
    }

    @Test
    @Ignore
    public void openSessionTest()
    {
        try
        {
            WB4WriterApplication.openSession();
        }
        catch(Exception e)
        {
            Assert.fail();
        }

    }

     @Test
    public void showContentsToAuthorizeTest()
    {
        try
        {
            WB4WriterApplication.showContentsToAuthorize();
        }
        catch(Exception e)
        {
            Assert.fail();
        }

    }
}
