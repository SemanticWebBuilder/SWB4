package org.semanticwb.openoffice.impress.test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.NoHasLocationException;
import org.semanticwb.openoffice.SaveDocumentFormat;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.WBOfficeException;
import org.semanticwb.openoffice.impress.WB4Impress;
/**
 *
 * @author krystel.montero
 */public class WB4ImpressNoHappyTest{

    private XComponentContext xContext;
    private XComponent xCompDest = null;
    private XComponent xCompSrc = null;
    private XDesktop oDesktop = null;
    private File sUrlDestiny = new File("C:/NegativeTest/PruebaSave.odp");
    private File tempDir = new File("C:/NegativeTest/");


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

            // Obtener interfaz XComponentLoader del XDesktop   
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);

            // Definir URL del fichero a cargar (de destino, o sea, el que recogera las nuevas diapositivas   


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
        //xCompDest.dispose();

        // Opcionalmente, cerrar el ejecutable de OpenOffice (solo si no vamos a extraer nada mas)   
        oDesktop.terminate();
        xCompDest = null;
        oDesktop = null;
        DeleteTemporalDirectory(this.tempDir);
    }

    public void DeleteTemporalDirectory(File dir)
    {
        File[] files = dir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    file.delete();
                }
                else
                {
                    DeleteTemporalDirectory(file);
                    file.delete();
                }
            }
        }
        dir.delete();
    }
    
    @Test(expected=WBException.class)
    @Ignore
    public void getLocalPathTest()throws NoHasLocationException, WBOfficeException{
      
        try {
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url = "private:factory/swriter";
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);
           
        } catch (com.sun.star.uno.Exception ioe) {
            Assert.fail(ioe.getMessage());
        
        }
        
        WB4Impress writer = new WB4Impress(xCompDest);
        File actual = writer.getLocalPath();
        
    }
    /* borrado 
    @Test(expected=WBException.class)
    public void getCustomPropertiesTest()throws WBException{
       
        WB4Impress writer = new WB4Impress(this.xContext);
        Map<String, String> properties = writer.getCustomProperties();
       
        
    }

    */

    @Test
    @Ignore
    public void getDocumentTypeTest() throws WBOfficeException
    {
        
            XComponent document=null; 
            WB4Impress writer = new WB4Impress(document);
            writer=null;
            DocumentType actual = writer.getDocumentType();
            
            
            Assert.fail(actual.name());
            
    }
    
    @Test(expected=java.lang.IllegalArgumentException.class)//is not a directory is a file
    @Ignore
    public void saveAsHTMLTest() throws IOException, IllegalArgumentException
    {
        
        try
        {
            
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url = "file:///c:/NegativeTest/PruebaSave.odp";
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);
            
           
            WB4Impress writer = new WB4Impress(xContext);
            File actual = writer.saveAsHtml(new File("c:/NegativeTest/PruebaSave.odp"));
            
            Assert.assertTrue(actual.exists());
        }
        catch (WBException wbe)
        {
            //Assert.fail(wbe.getMessage());
        }
    }
    
    
    @Test(expected=WBException.class)//the file cant be saved
    @Ignore
      public void saveAsHTMLTest2()throws WBException{
        
        try {
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url = "file:///c:/NegativeTest/PruebaSave.odp";
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);
            
            WB4Impress writer = new WB4Impress(xContext);
            File actual = writer.saveAsHtml(new File("c:/NegativeTest/"));
            
            Assert.fail("Error: Deberia Fallar");
          
            
        } catch (com.sun.star.uno.Exception ioe) {
            Assert.fail(ioe.getMessage());
        
        }
        
    }
    
    
    @Test(expected=WBException.class)
    @Ignore
    //test1 case: the document never has been saved 
    public void saveTest1() throws IOException, IllegalArgumentException, WBOfficeException, WBException {
       
        XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
        PropertyValue[] loadProps = new PropertyValue[0];
        String url = "private:factory/swriter";
        xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);

        WB4Impress writer = new WB4Impress(xContext);
        writer.save();
        
    }
    
    
    @Test(expected=WBException.class)
    @Ignore
    //test1 case: the document has not been modified 
    public void saveTest2() throws IOException, IllegalArgumentException, WBOfficeException, WBException {
       
        XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
        PropertyValue[] loadProps = new PropertyValue[0];
        String url = "file:///c:/NegativeTest/PruebaSave.odp";
        xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);
       
        WB4Impress writer = new WB4Impress(xContext);
        writer.save();
        
    }
    
    
    @Test(expected=java.lang.IllegalArgumentException.class)
    @Ignore
    //case 1: the path is a File
    public void saveAsSaveDocumentFormatHTMLTest1() throws IOException, 
            IllegalArgumentException, WBOfficeException, WBException{
       
        XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
        PropertyValue[] loadProps = new PropertyValue[0];
        String url = "file:///c:/NegativeTest/PruebaSave.odp";
        xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);



        WB4Impress writer = new WB4Impress(xContext);
        File actual=writer.saveAs(new File("c:/NegativeTest/PruebaSave.odp"), SaveDocumentFormat.HTML);            
        
    }
    
    

    @Test(expected=WBException.class)
    @Ignore
    //case 2: the docuemet can not be saved, is read only
    public void saveAsSaveDocumentFormatHTMLTest2() throws IOException, 
            IllegalArgumentException, WBOfficeException, WBException{
       
        XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
        PropertyValue[] loadProps = new PropertyValue[0];
        String url = "file:///c:/NegativeTest/PruebaSave.odp";
        xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);



        WB4Impress writer = new WB4Impress(xContext);
        File actual=writer.saveAs(new File("c:/NegativeTest/"), SaveDocumentFormat.HTML);            
        
    }
    
    
    
    
    
    @Test(expected=WBException.class)
    @Ignore
    public void saveCustomPropertiesTest()throws WBException{
        try{
            WB4Impress writer = new WB4Impress(this.xContext);
            HashMap<String, String> properties = new HashMap<String, String>();
            properties.put("id", "1");
            properties.put("tp", "Hola");
            properties.put("Name", "any");
            properties.put("Text", "1");
            properties.put("path", " c: ");
            writer.saveCustomProperties(properties);
        }
        catch (WBException wbe){
            throw wbe;
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
 }
 
 
 
 
 

