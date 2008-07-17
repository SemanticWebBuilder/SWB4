
package org.semanticwb.openoffice.calc.test;



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
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.NoHasLocationException;
import org.semanticwb.openoffice.SaveDocumentFormat;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.WBOfficeException;
import org.semanticwb.openoffice.calc.WB4Calc;
import org.semanticwb.openoffice.calc.WB4Calc;



/**
 *
 * @author Edgar Chavarria
 */
public class WB4CalcNoHappyTest {
    

    private XComponentContext xContext;
    private XComponent xCompDest = null;   
    private XDesktop oDesktop = null;
    private File sUrlDestiny = new File("C:/NegativeTest/PruebaSave.odp");
    
    
    @BeforeClass
    public static void setUpClass() throws Exception{
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception{
    }
    
    @Before
    public void setUp(){
        try
        {
            xContext = Bootstrap.bootstrap();

            // Obtener la factoria de servicios de OpenOffice   
            XMultiComponentFactory xMCF = xContext.getServiceManager();
            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
            oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);
            
        }catch (com.sun.star.uno.Exception e){
            e.printStackTrace(System.out);
        }
        catch (BootstrapException be){
            be.printStackTrace(System.out);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
    @After
    public void tearDown(){
//        xCompDest.dispose();
        // Opcionalmente, cerrar el ejecutable de OpenOffice (solo si no vamos a extraer nada mas)   
        oDesktop.terminate();
        xCompDest = null;
        oDesktop = null;
    //    DeleteTemporalDirectory(this.tempDir);
    }
    
    
    
     /**
     * Testing: getLocalPath() 
     * class: WB4Calc
     * Case: try to get the path of document when the document no has been saved
     * The test is successful if return a NoHasLocationException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=NoHasLocationException.class)
    @Ignore
    public void getLocalPathTest() throws WBOfficeException, NoHasLocationException {
        
            
        xCompDest = getNewDocument();
        
        WB4Calc write =new WB4Calc(xContext);
        write.getLocalPath();
        
    }
    
    /**
     * Testing: saveAsHtml()
     * class: WB4Calc
     * Case 1: try to save the document in html format, the path is a file,when should be a directory
     * The test is successful if return a com.sun.star.lang.IllegalArgumentException
     * @throws com.sun.star.lang.IllegalArgumentException
     */
    @Test(expected=java.lang.IllegalArgumentException.class)
    @Ignore
    public void saveAsHTML_PathIsAFileTest()throws IllegalArgumentException, WBException, IOException{
        
        String path="C:/NegativeTest/Document.ods";
        String url = "file:///c:/NegativeTest/TestSave.ods";
        xCompDest =getDocument(url);
            WB4Calc writer = new WB4Calc(xContext);
            File actual=writer.saveAsHtml(new File(path));
         
    }
    
    
    /**
     * Testing: saveAsHtml()
     * class: WB4Calc
     * Case 2: try to save the a document, the document can not be seved, it is read only
     * The test is successful if return a org.semanticwb.openoffice.WBException 
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=WBException.class)
    @Ignore
    public void saveAsHTML_DocReadOnlyTest()throws IllegalArgumentException, WBException{

        String url = "file:///c:/NegativeTest/TestSave.ods";
        xCompDest =getDocument(url);

        WB4Calc writer = new WB4Calc(xContext);
        File actual=writer.saveAsHtml(new File("C:/NegativeTest/ReadOnly"));

        
    }
    
     /**
     * Testing: save()
     * class: WB4Calc
     * Case 1: the document can not be seved, it a new document (never has been save before)
     * The test is successful if return a org.semanticwb.openoffice.WBException 
     * @throws org.semanticwb.openoffice.WBException
     */
    
     @Test(expected=WBException.class)
     @Ignore
     public void save_IsANewDocTest() throws WBException, IOException, IllegalArgumentException{
           
        xCompDest = getNewDocument();
        
        WB4Calc writer = new WB4Calc(this.xContext);
        writer.save();
      
    }
     
     /**
     * Testing: save()
     * class: WB4Calc
     * Case 2: the document can not be seved, the document has not been modified
     * The test is successful if return a org.semanticwb.openoffice.WBException 
     * @throws org.semanticwb.openoffice.WBException
     */
    
     @Test(expected=WBException.class)
     @Ignore
     public void save_DocumentNoModifyTest() throws WBException, IOException, IllegalArgumentException{
           
        String url = "file:///C:/NegativeTest/TestSave.ods";
        xCompDest = getDocument(url);
        
        WB4Calc writer = new WB4Calc(xContext);
        writer.save();
      
    }
     
    /**
     * Testing: save()
     * class: WB4Calc
     * Case 3: the document can not be seved, the document is read only
     * The test is successful if return a org.semanticwb.openoffice.WBException 
     * @throws org.semanticwb.openoffice.WBException
     */ 
     
    @Test(expected=WBException.class)
    @Ignore
    public void save_DocReadOnlyTest() throws WBException, IOException, IllegalArgumentException{
           
        String url = "file:///C:/NegativeTest/ReadOnly/TestSave.ods";
        xCompDest = getDocument(url);
        
        WB4Calc writer = new WB4Calc(xContext);
        writer.save();
      
    }
    
    
    /**
     * Testing:saveAs()
     * Case 1: the function receive a directory ,but should receive a file
     * The test is successful if return a java.lang.IllegalArgumentException 
     * @throws org.semanticwb.openoffice.WBException
     * @throws com.sun.star.lang.IllegalArgumentException
     */
    @Test(expected=java.lang.IllegalArgumentException.class)//the parameter is a file
    @Ignore
    public void saveAsSaveDocumentFormatHTML_PathIsAFileTest() throws  WBException,  IllegalArgumentException{

        String url = "file:///C:/NegativeTest/TestSave.ods";
        xCompDest = getDocument(url);
        
        WB4Calc writer = new WB4Calc(this.xContext);
        File actual=writer.saveAs(new File("C:/NegativeTest/TestSave.ods"), SaveDocumentFormat.HTML);
        
    }
    /**
     * Testing:saveAs()
     * Case 2: the document can not saved, it is read only
     * The test is successful if return a WBException
     * @throws org.semanticwb.openoffice.WBException
     * @throws com.sun.star.lang.IllegalArgumentException
     */
    
    @Test(expected=WBException.class)
    @Ignore
    public void saveAsSaveDocumentFormatHTML_ReadOnlyTest() throws WBOfficeException, WBException, IOException, IllegalArgumentException{

        String url = "file:///C:/NegativeTest/TestSave.ods";
        xCompDest = getDocument(url);
      
        
        WB4Calc writer = new WB4Calc(this.xContext);
        File actual=writer.saveAs(new File("C:/NegativeTest/ReadOnly"), SaveDocumentFormat.HTML);
        
    }
    
    
    /**
     * Testing:saveCustomProperties(Map<String, String> properties)
     * Case: the Map have 6 properties, shouldn't be more than 4
     * The test is successful if return a WBException
     * @throws org.semanticwb.openoffice.WBException
     */
    @Test(expected=WBException.class)
    @Ignore
    public void saveCustomPropertiesTest() throws WBException{
        
        
        
        WB4Calc writer = new WB4Calc(xContext);
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("id", "1");
        properties.put("tp", "Hola");
        properties.put("X", "a");
        properties.put("Y", "Local");
        properties.put("Z", "2");

        writer.saveCustomProperties(properties);

    }
    
    /**
     * Testing: getAllAttachments();
     * Case: A relative link used to inert at the new document,the function trying get the Attachments
     * The test is successful if return a NoHasLocationException
     * @throws java.lang.RuntimeException
     * @throws java.lang.Exception
     */
    @Test(expected=NoHasLocationException.class)
    @Ignore
    public void getAllAttachmentsTest() throws RuntimeException, Exception,NoHasLocationException{
      
      
        
         org.semanticwb.openoffice.write.test.SpreadsheetDocHelper UtilX=
                new org.semanticwb.openoffice.write.test.SpreadsheetDocHelper(new String[0],xContext); 
        
        com.sun.star.sheet.XSpreadsheet xSheet = UtilX.getSpreadsheet( 0 );
        com.sun.star.table.XCell xCell = null;
        com.sun.star.beans.XPropertySet xPropSet = null;
        String aText;

        // --- Get cell B3 by position - (column, row) ---
        xCell = xSheet.getCellByPosition( 1, 2 );  
        
          // --- Insert two text paragraphs into the cell. ---
        com.sun.star.text.XText xText = (com.sun.star.text.XText)
            UnoRuntime.queryInterface( com.sun.star.text.XText.class, xCell );
        com.sun.star.text.XTextCursor xTextCursor = xText.createTextCursor();

        xText.insertString( xTextCursor, "Text in first line.", false );
        xText.insertControlCharacter( xTextCursor,
            com.sun.star.text.ControlCharacter.PARAGRAPH_BREAK, false );
        xText.insertString( xTextCursor, "And a ", false );

        // create a hyperlink
        com.sun.star.lang.XMultiServiceFactory xServiceMan = (com.sun.star.lang.XMultiServiceFactory)
            UnoRuntime.queryInterface( com.sun.star.lang.XMultiServiceFactory.class, UtilX.getDocument() );
        Object aHyperlinkObj = xServiceMan.createInstance( "com.sun.star.text.TextField.URL" );
        xPropSet = (com.sun.star.beans.XPropertySet)
            UnoRuntime.queryInterface( com.sun.star.beans.XPropertySet.class, aHyperlinkObj );
        xPropSet.setPropertyValue( "URL", "./Directorio1/Doc1.odt" );
        xPropSet.setPropertyValue( "Representation", "hyperlink" );
        // ... and insert
        com.sun.star.text.XTextContent xContent = (com.sun.star.text.XTextContent)
            UnoRuntime.queryInterface( com.sun.star.text.XTextContent.class, aHyperlinkObj );
        xText.insertTextContent( xTextCursor, xContent, false );
   
        
       WB4Calc writer = new WB4Calc(xContext);
       List<File> links=writer.getAllAttachments();
       
       UtilX.closeDocument();
       
    
        
    }

    
     /**
     * Get the XComponent of a existent domcument 
     * @param String - the path of a Open Office document to Open
     * @return XComponent
     * @throws com.sun.star.uno.Exception
     */
        public XComponent getNewDocument(){
        
        XComponent XComp=null;
        
        
         try {
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            
            XComp = xCompLoader.loadComponentFromURL("private:factory/scalc", "_blank", 0, loadProps);
            
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
