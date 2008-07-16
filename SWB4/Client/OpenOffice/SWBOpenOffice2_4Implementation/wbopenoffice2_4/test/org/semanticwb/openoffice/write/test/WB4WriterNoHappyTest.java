package org.semanticwb.openoffice.write.test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.NoHasLocationException;
import org.semanticwb.openoffice.NoHasLocationException;
import org.semanticwb.openoffice.OfficeDocumentHelper;
import org.semanticwb.openoffice.SaveDocumentFormat;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.writer.WB4Writer;
import org.semanticwb.openoffice.writer.WB4WriterApplication;
import static org.junit.Assert.*;

/**
 *
 * @author  Edgar Chavarria
 */
public class WB4WriterNoHappyTest {

    XComponentContext xContext;
    XComponent xCompDest = null;
    XDesktop oDesktop = null;
    File sUrlDestiny = new File("c:/temp/demopub.odt");
  
  
    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        try {
            xContext = Bootstrap.bootstrap();
            // Obtener la factoria de servicios de OpenOffice   
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
            oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);

            // Obtener interfaz XComponentLoader del XDesktop   
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);

            // Cargar el documento en una nueva ventana oculta del XDesktop   
            PropertyValue[] loadProps = new PropertyValue[0];            
            String url = "file:///" + sUrlDestiny.getPath().replace('\\', '/');
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);

        } catch (com.sun.star.uno.Exception e) {
            e.printStackTrace(System.out);
        } catch (BootstrapException be) {
            be.printStackTrace(System.out);
        } catch (java.lang.Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @After
    public void tearDown() {
//      xCompDest.dispose();

        //  Opcionalmente, cerrar el ejecutable de OpenOffice (solo si no vamos a extraer nada mas)   
        oDesktop.terminate();
        xCompDest = null;
        oDesktop = null;
    }


    /**
     * Testing: getLocalPath() 
     * Case: try to get the path of a ducument no has been saved
     * The test is successful if return a NoHasLocationException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected = NoHasLocationException.class)
    @Ignore
    public void getLocalPathTest() throws WBException {
    
            xCompDest = getNewDocument();
            
            WB4Writer writer = new WB4Writer(xCompDest);
            File actual = writer.getLocalPath();
       
    }

    
    
    /**
     * Testing: save()
     * Case 1: try to save a document when the document never has been saved
     * The test is successful if return WBException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected = WBException.class)
    @Ignore
    public void saveTest() throws WBException{

         xCompDest = getNewDocument();
            
         WB4Writer writer = new WB4Writer(xCompDest);
          if(writer.isNewDocument()){
               writer.save();
           }
        
    }
    
    /**
     * Testing: save()
     * Case 2: try to save a document when it has save before & has not been modified
     * The test is successful if return WBException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=WBException.class) 
    @Ignore 
    public void saveTest2()throws WBException{
        
        
        xCompDest=getDocument("file:///c:/NegativeTest/PruebaSave.odt");
        
        WB4Writer writer = new WB4Writer(xCompDest);
        if(!writer.isNewDocument())
            writer.save();
        
    }

    /**
     * Testing: saveAs();
     * Case 1: try to save a document with a type format determined(HTML)
     * the function receives the path of a file ,but the path should be directory 
     * The test is successful if return java.lang.IllegalArgumentException 
     * @throws com.sun.star.lang.IllegalArgumentException
     * @throws WBException
    */
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    @Ignore
    public void saveAsSaveDocumentFormatHTMLTest1() throws IllegalArgumentException, WBException {
        
          
          xCompDest = getDocument("file:///c:/NegativeTest/TestSave.odt");
                
          WB4Writer writer = new WB4Writer(xCompDest);
          File actual = writer.saveAs(new File("c:/temp/demopub.odt"), SaveDocumentFormat.HTML);
        
    }
  
    
    /**
     * Testing: saveAs();  
     * case 2: try to save a document already exist
     * Document can not be saved, the file is read only
     * The test is successful if return a WBException
     * @throws org.semanticwb.openoffice.WBException
     * @throws com.sun.star.lang.IllegalArgumentException
     */
    @Test(expected = WBException.class)
    @Ignore 
    public void saveAsSaveDocumentFormatHTMLTest2() throws IllegalArgumentException, WBException {
        
            
        xCompDest = getDocument("file:///c:/NegativeTest/TestSave.odt");
        
        WB4Writer writer = new WB4Writer(xCompDest);
        File actual = writer.saveAs(new File("C:/NegativeTest/ReadOnly"), SaveDocumentFormat.HTML);
      
    }
    
    
    /**
     * testing: saveCustomProperties()
     * Case: try insert more than 4 properties, the maximun is 4  
     * The test is successful if return a WBException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected = WBException.class)
    @Ignore
    public void saveCustomPropertiesTest() throws WBException {
       
        xCompDest=getNewDocument();
        
        WB4Writer writer = new WB4Writer(xCompDest);
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("id1", "11");
        properties.put("tp2", "2Hola");
        properties.put("id3", "31");
        properties.put("tp4", "4Hola");
        properties.put("id5", "51");
        properties.put("tp6", "6Hola");

        writer.saveCustomProperties(properties);
        
    }


    /**
     * Testing: open()
     * Case: the document can not be opened,the function receives a domuent absent
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected = WBException.class)
    @Ignore
    public void openDocumentTest() throws WBException {
        WB4WriterApplication writer = new WB4WriterApplication(this.xContext);
        writer.open(sUrlDestiny);

    }

   
    @Test(expected = NoHasLocationException.class)
    @Ignore
    public void getAllAttachmentsTest() throws NoHasLocationException {
        try {
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url = "private:factory/swriter";
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);
            WB4Writer writer = new WB4Writer(xCompDest);
            List<File> attachments = writer.getAllAttachments();
        } catch (NoHasLocationException e) {
            throw e;

        } catch (Exception e) {
            Assert.fail(e.getMessage());
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
            fail(ioe.getMessage());
        
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
            fail(ioe.getMessage());
        
        }
        
        return XComp;
        
    }
    
    
    
}
  
    