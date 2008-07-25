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
import java.util.ArrayList;
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

    @Test
    public void testRemoveDirectory() {
        System.out.println("RemoveDirectory(");
        String path = "C:/prueba";
        java.io.File dir = new java.io.File(path);
        boolean result = SWBUtils.IO.removeDirectory(path);
        System.out.println("result");
    }

    @Test
    public void testgetFileFromPath() {
        String path = "C:/prueba/prueba.txt";
        java.io.File dir = new java.io.File(path);
        String result = SWBUtils.IO.getFileFromPath(path);
        System.out.println("result:" + result);
    }

    @Test
    public void testCreateDirectory() {
        System.out.println("createDirectory");
        String path = "C:/prueba1";
        java.io.File dir = new java.io.File(path);
        boolean result = SWBUtils.IO.createDirectory(path);
        System.out.println("result:" + result);
    }
    //TODO:Probar, tambien metodos xmlVerifierImpl
    @Test
    public void testCopyStructure() {
        System.out.println("copyStructure");
        String path1 = "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/wb3/prueba/";
        String path2 = "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/wb3/prueba1/";
        boolean result = SWBUtils.IO.copyStructure(path1, path2);
        System.out.println("result:" + result);
    }
    //TODO:generar y probar
    @Test
    public void testxmlVerifierImpl() {
    }

    @Test
    public void testNode2Document() {
        System.out.println("node2Document");
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
        System.out.println("xml2dom");
        String xml = "<data><name>Jorge Jimenez</name><old>33</old></data>";
        Document dom = SWBUtils.XML.xmlToDom(xml);
        SWBUtils.XML.domtoFile(dom, "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/wb3/prueba/pruebaJ.xml", "UTF-8");
    }

    @Test
    public void tesEncode() {
        System.out.println("encode");
        String text = "Jorge Jiménez niño";
        try {
            String result = SWBUtils.TEXT.encode(text, "UTF-8");
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDecode() {
        System.out.println("decode");
        String text = "Jorge JimÃ©nez niÃ±o";
        try {
            String result = SWBUtils.TEXT.decode(text, "UTF-8");
            System.out.println("result2:" + result);
        } catch (Exception e) {
        }
    }

    /**
     * Este metodo prueba el envío x correo del metodo sendMail (NO BACKGROUND) de SWBUtils
     */
    @Test
    public void testSendMail() {
        try {
            javax.mail.internet.InternetAddress address1 = new javax.mail.internet.InternetAddress();
            address1.setAddress("george24@infotec.com.mx");
            javax.mail.internet.InternetAddress address2 = new javax.mail.internet.InternetAddress();
            address2.setAddress("george190475@hotmail.com");
            ArrayList aAddress = new ArrayList();
            aAddress.add(address1);
            aAddress.add(address2);

            org.apache.commons.mail.EmailAttachment attach = new org.apache.commons.mail.EmailAttachment();
            attach.setPath("C:/temp.html");
            attach.setDisposition(attach.ATTACHMENT);
            attach.setDescription("Prueba Jorge Description");
            attach.setName("Prueba Jorge Name");
            ArrayList<org.apache.commons.mail.EmailAttachment> aListAttachments = new ArrayList();
            aListAttachments.add(attach);

            String result = SWBUtils.EMAIL.sendMail("webbuilder@infotec.com.mx", "Jorge Jiménez", aAddress, null, null, "Prueba de Envío2", "text", "Esta es mi prueba2", null, null, aListAttachments);
            System.out.println("result2:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo prueba el envío x correo del metodo sendBGEmail (BACKGROUND) de SWBUtils, pasandole la una instancia de
     * clase SWBMail
     */
    @Test
    public void testSendMail2() {
        try {
            javax.mail.internet.InternetAddress address1 = new javax.mail.internet.InternetAddress();
            address1.setAddress("george24@infotec.com.mx");
            javax.mail.internet.InternetAddress address2 = new javax.mail.internet.InternetAddress();
            address2.setAddress("george190475@hotmail.com");
            ArrayList aAddress = new ArrayList();
            aAddress.add(address1);
            aAddress.add(address2);

            org.apache.commons.mail.EmailAttachment attach = new org.apache.commons.mail.EmailAttachment();
            attach.setPath("C:/temp.html");
            attach.setDisposition(attach.ATTACHMENT);
            attach.setDescription("Prueba Jorge Description");
            attach.setName("Prueba Jorge Name");
            ArrayList<org.apache.commons.mail.EmailAttachment> aListAttachments = new ArrayList();
            aListAttachments.add(attach);

            org.semanticwb.base.util.SWBMail swbMail = new org.semanticwb.base.util.SWBMail();
            swbMail.setAddress(aAddress);
            swbMail.setAttachments(aListAttachments);
            swbMail.setContentType("text");
            swbMail.setData("Prueba Jorge 3");
            swbMail.setSubject("Prueba 3");
            swbMail.setFromName("Jorge");
            swbMail.setFromEmail("george@infotec.com.mx");

            SWBUtils.EMAIL.sendBGEmail(swbMail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo prueba el envío x correo del metodo sendMail (NO BACKGROUND) de la clase SWBMail
     * clase SWBMail
     */
    @Test
    public void testSendMail3() {
        try {
            javax.mail.internet.InternetAddress address1 = new javax.mail.internet.InternetAddress();
            address1.setAddress("george24@infotec.com.mx");
            javax.mail.internet.InternetAddress address2 = new javax.mail.internet.InternetAddress();
            address2.setAddress("george190475@hotmail.com");
            ArrayList aAddress = new ArrayList();
            aAddress.add(address1);
            aAddress.add(address2);

            org.apache.commons.mail.EmailAttachment attach = new org.apache.commons.mail.EmailAttachment();
            attach.setPath("C:/temp.html");
            attach.setDisposition(attach.ATTACHMENT);
            attach.setDescription("Prueba Jorge Description");
            attach.setName("Prueba Jorge Name");
            ArrayList<org.apache.commons.mail.EmailAttachment> aListAttachments = new ArrayList();
            aListAttachments.add(attach);

            org.semanticwb.base.util.SWBMail swbMail = new org.semanticwb.base.util.SWBMail();
            swbMail.setAddress(aAddress);
            swbMail.setAttachments(aListAttachments);
            swbMail.setContentType("text");
            swbMail.setData("Prueba Jorge 4");
            swbMail.setSubject("Prueba 4");
            swbMail.setFromName("Jorge");
            swbMail.setFromEmail("george@infotec.com.mx");

            swbMail.setHostName("webmail.infotec.com.mx");

            swbMail.sendMail();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}