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
 * @author krystel.montero
 */
public class WB4WriterNoHappyTest {

    XComponentContext xContext;
    XComponent xCompDest = null;
    XDesktop oDesktop = null;
    File sUrlDestiny = new File("c:/temp/demopub.odt");
    File tempDir = new File("c:/temp/demo/");
    

  
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


    @Test(expected = NoHasLocationException.class)
    
    public void getLocalPathTest() throws WBException {
    
            xCompDest = getNewDocument();
            
            WB4Writer writer = new WB4Writer(xCompDest);
            File actual = writer.getLocalPath();
       
    }

    
    
    /**
     * Testing: save()
     * Case: save a document when document has not save before
     * 
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
    
    @Test(expected=WBException.class)//File not is new & has not been modified 
    @Ignore 
    public void saveTest2()throws WBException{
        
        
        xCompDest=getDocument("file:///c:/NegativeTest/PruebaSave.odt");
        
        WB4Writer writer = new WB4Writer(xCompDest);
        if(!writer.isNewDocument())
            writer.save();
        
    }

    @Test(expected = java.lang.IllegalArgumentException.class)//the path is a path file
    @Ignore
    public void saveAsSaveDocumentFormatHTMLTest() throws IllegalArgumentException, WBException {
        
          
          xCompDest = getDocument("file:///c:/NegativeTest/TestSave.odt");
                
          WB4Writer writer = new WB4Writer(xCompDest);
          File actual = writer.saveAs(new File("c:/temp/demopub.odt"), SaveDocumentFormat.HTML);
        
    }
  
    
    /**
     * Testing: saveAs  
     * case 1: Document can not be saved, the file is read only
     * The test is successful if return a WBException
     * @throws com.sun.star.lang.IllegalArgumentException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected = WBException.class)
    @Ignore 
    public void saveAsSaveDocumentFormatHTMLTest2() throws IllegalArgumentException, WBException {
        
            
        xCompDest = getDocument("file:///c:/NegativeTest/TestSave.odt");
        
        WB4Writer writer = new WB4Writer(xCompDest);
        File actual = writer.saveAs(new File("C:/NegativeTest/ReadOnly"), SaveDocumentFormat.HTML);
      
    }
    
    
    /**
     * testing: saveCustomProperties
     * Case: try insert more than 4 properties  
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

    @Test(expected = WBException.class)
    @Ignore
    public void SaveHtmlPrepareAndGetFilesTest() throws WBException {
        
        
        WB4Writer writer = new WB4Writer(this.xContext);
        String guid = writer.getGuid();
        File file = writer.saveHtmlPrepareAndGetFiles(guid);

    }

    @Test()
    @Ignore
    public void publishTest() throws WBException {
        WB4Writer writer = new WB4Writer(this.xContext);
        OfficeDocumentHelper.publish(writer);

    }

    @Test(expected = NoClassDefFoundError.class)
    @Ignore
    public void openTest() {
        WB4WriterApplication writer = new WB4WriterApplication(this.xContext);
        OfficeDocumentHelper.open(writer);

    }

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

    @Test(expected = WBException.class)
    @Ignore
    public void openDocumentExceptionTest() throws WBException {
        WB4WriterApplication writer = new WB4WriterApplication(this.xContext);
        writer.open(new File("c:\\demo.doc"));
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
  
    