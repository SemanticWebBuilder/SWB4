/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.*;

/**
 *
 * @author jorge.jimenez
 */
public class SWBUtilsTest {

    public SWBUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    /*
    @Test
    public void testRemoveDirectory() {
    System.out.println("RemoveDirectory(");
    String path = "C:/prueba";
    File dir = new File(path);
    boolean expResult = true;
    boolean result = SWBUtils.IO.removeDirectory(path);
    assertEquals(expResult, result);
    }
    
    @Test
    public void testgetFileFromPath() {
    System.out.println("getFileFromPath");
    String path = "C:/prueba/prueba.txt";
    File dir = new File(path);
    String result = SWBUtils.IO.getFileFromPath(path);
    System.out.println("result:"+result);
    }
    
    @Test
    public void testCreateDirectory() {
    System.out.println("createDirectory");
    String path = "C:/prueba1";
    File dir = new File(path);
    boolean result = SWBUtils.IO.createDirectory(path);
    System.out.println("result:"+result);
    }
    
    //TODO:Probar, tambien metodos xmlVerifierImpl
    @Test
    public void testCopyStructure() {
    System.out.println("copyStructure");
    String path1 = "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/wb3/prueba/";
    String path2 = "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/wb3/prueba1/";
    boolean result = SWBUtils.IO.copyStructure(path1,path2);
    System.out.println("result:"+result);
    }
    
    //TODO:generar y probar
    @Test
    public void testxmlVerifierImpl() {
    }

    @Test
    public void testNode2Document() {
        String xml = "<data><name>Jorge Jimenez</name><old>33</old></data>";
        Document dom = SWBUtils.XML.xmlToDom(xml);
        NodeList nviewNode = dom.getElementsByTagName("name");
        try {
            Document domResult = SWBUtils.XML.node2Document(nviewNode.item(0));
            System.out.println("result:" + SWBUtils.XML.domToXml(domResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testxmlToDom() {
        String xml = "<data><name>Jorge Jimenez</name><old>33</old></data>";
        Document dom = SWBUtils.XML.xmlToDom(xml);
        SWBUtils.XML.domtoFile(dom, "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/wb3/prueba/pruebaJ.xml", "UTF-8");
    }

    @Test
    public void tesEncode() {
        String text = "Jorge Jiménez niño";
        try {
            String result = SWBUtils.TEXT.encode(text, "UTF-8");
            System.out.println("result:" + result);
        } catch (Exception e) {
        }
    }

    @Test
    public void testDecode() {
        String text = "Jorge JimÃ©nez niÃ±o";
        try {
            String result = SWBUtils.TEXT.decode(text, "UTF-8");
            System.out.println("result2:" + result);
        } catch (Exception e) {
        }
    }
    */
     @Test
    public void testSendSimpleMail() {
        try {
            String result = SWBUtils.IO.sendSimpleMail("webbuilder@infotec.com", "george24@infotec.com", null, null, "Prueba de Envío", null, 1, "Esta es mi prueba");
            System.out.println("result2:" + result);
        } catch (Exception e) {
        }
    }
    
    
}