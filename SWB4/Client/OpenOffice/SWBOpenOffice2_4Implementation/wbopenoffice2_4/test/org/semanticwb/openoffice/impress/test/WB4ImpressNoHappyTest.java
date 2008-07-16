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
 * @author  Edgar Chavarria
 */public class WB4ImpressNoHappyTest{

    private XComponentContext xContext;
    private XComponent xCompDest = null;
    private XComponent xCompSrc = null;
    private XDesktop oDesktop = null;
    private File sUrlDestiny = new File("C:/NegativeTest/PruebaSave.odp");
    

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
        
    }

     /**
     * Testing: getLocalPath() 
     * class: WB4Impress
     * Case: try to get the path of document when the document no has been saved
     * The test is successful if return  NoHasLocationException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=NoHasLocationException.class)
    @Ignore
    public void getLocalPathTest()throws NoHasLocationException{
      
        xCompDest = getNewDocument();
        
        WB4Impress writer = new WB4Impress(xCompDest);
        File actual = writer.getLocalPath();
        
    }
    
    /**
     * Testing:saveAsHtml(File dir)
     * class: WB4Impress
     * Case 1:Try to save a impress document as Html document
     * The parameter received is a file,the function should received a directory
     * The test is successful if return java.lang.IllegalArgumentException
     * @throws org.semanticwb.openoffice.WBException
     * @throws java.lang.IllegalArgumentException
     */
 
    @Test(expected=java.lang.IllegalArgumentException.class)
    @Ignore
    public void saveAsHTMLTest1() throws WBException,IllegalArgumentException  
    {    
            String url = "file:///c:/NegativeTest/PruebaSave.odp";
            xCompDest = getDocument(url);
           
            
            WB4Impress writer = new WB4Impress(xCompDest);
            File actual = writer.saveAsHtml(new File("c:/NegativeTest/PruebaSave.odp"));
            
            
    }
    
    
     /**
     * Testing:saveAsHtml(File dir)
     * Class: WB4Impress
     * Case 2:Try to save a impress document as Html document
     * the document already exist and is read only
     * The test is successful if return org.semanticwb.openoffice.WBException
     * @throws org.semanticwb.openoffice.WBException
     * @throws java.lang.IllegalArgumentException
     */
    
    @Test(expected=WBException.class)
    @Ignore
      public void saveAsHTMLTest2()throws WBException,IllegalArgumentException{
        
            String url = "file:///c:/NegativeTest/PruebaSave.odp";
            xCompDest = getDocument(url);
            
            WB4Impress writer = new WB4Impress(xCompDest);
            File actual = writer.saveAsHtml(new File("c:/NegativeTest/"));
            
       
        
    }
    
    /**
     * Testing: save();
     * Class: WB4Impress
     * case 1: try to save a never has been document
     * The test is successful if return org.semanticwb.openoffice.WBException 
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=WBException.class)
    @Ignore
    
    public void saveTest1() throws WBException {
       
        xCompDest = getNewDocument();
        
        WB4Impress writer = new WB4Impress(xContext);
        writer.save();
        
    }
    
    
    /**
     * Testing: save();
     * Class: WB4Impress
     * case 2: try to save a document, the document does save before and
     * the document no has  been modified
     * The test is successful if return org.semanticwb.openoffice.WBException 
     * @throws org.semanticwb.openoffice.WBException
     */
    
    @Test(expected=WBException.class)
    @Ignore
    public void saveTest2() throws IOException, IllegalArgumentException, WBOfficeException, WBException {
       
        String url = "file:///c:/NegativeTest/PruebaSave.odp";
        xCompDest = getDocument(url);
        
        WB4Impress writer = new WB4Impress(xContext);
        writer.save();
        
    }
    
    /**
     * Testing: File saveAs(File dir, SaveDocumentFormat format)
     * class: WB4Impress
     * Case 1:try to save a document as HTML document
     * the function received a file, but need a directory as parameter
     * The test is successful if return java.lang.IllegalArgumentException
     * @throws org.semanticwb.openoffice.WBOfficeException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=java.lang.IllegalArgumentException.class)
    @Ignore
    public void saveAsSaveDocumentFormatHTMLTest1() throws WBOfficeException, WBException {
       
        String url = "file:///c:/NegativeTest/PruebaSave.odp";
        xCompDest =getDocument(url);
        
        WB4Impress writer = new WB4Impress(xContext);
        File actual=writer.saveAs(new File("c:/NegativeTest/PruebaSave.odp"), SaveDocumentFormat.HTML);            
        
    }
    
    /**
     * Testing: File saveAs(File dir, SaveDocumentFormat format)
     * class: WB4Impress
     * Case 2:try to save a impress document as HTML document
     * the document already exist and is read only 
     * The test is successful if return WBException
     * @throws org.semanticwb.openoffice.WBOfficeException
     * @throws org.semanticwb.openoffice.WBException
     */

    
    @Test(expected=WBException.class)
    @Ignore
    public void saveAsSaveDocumentFormatHTMLTest2() throws WBOfficeException, WBException  {
       
        String url = "file:///c:/NegativeTest/PruebaSave.odp";
        xCompDest = getDocument(url);

        WB4Impress writer = new WB4Impress(xContext);
        File actual=writer.saveAs(new File("c:/NegativeTest/"), SaveDocumentFormat.HTML);            
        
    }
    
    
    
    /**
     * Testing:saveCustomProperties(Map<String, String> properties)
     * Case: try to insert more than 4 properties
     * The test is successful if return WBException
     * @throws org.semanticwb.openoffice.WBException
     */
    
    @Test(expected=WBException.class)
    @Ignore
    public void saveCustomPropertiesTest()throws WBException{
        try{
            WB4Impress writer = new WB4Impress(xContext);
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
    
    /**
     * Get the XComponent of a new document 
     * @return XComponent
     * @throws com.sun.star.uno.Exception
     */
    public XComponent getNewDocument(){
        
        XComponent XComp=null;
        
        
         try {
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            
            XComp = xCompLoader.loadComponentFromURL("private:factory/swriter", "_blank", 0, loadProps);
            
        } catch (com.sun.star.uno.Exception ioe) {
            Assert.fail(ioe.getMessage());
        
        }
        
        return (XComp);
        
    }
    
    
    /**
     * Get the XComponent of a existent domcument 
     * @param String - the path of a Open Office document to Open
     * @return XComponent
     * @throws com.sun.star.uno.Exception
     */
    
    public XComponent getDocument(String path){
        
        XComponent XComp=null;
        
         try {
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            
            XComp = xCompLoader.loadComponentFromURL(path, "_blank", 0, loadProps);
            
        } catch (com.sun.star.uno.Exception ioe) {
            Assert.fail(ioe.getMessage());
        
        }
        
        return XComp;
        
    }
    
    
    
    
 }
 
 
 
 
 

