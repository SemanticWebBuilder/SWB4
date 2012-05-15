package org.semanticwb.ws.util;

/**
 *
 * @author carlos.ramos
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Document;

public class toJson {
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
    public void testToJson()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<catalog>\n");
         sb.append("<journal date=\"April 2004\">\n");
        sb.append("   <article>\n");
        sb.append("    <title>Declarative Programming in Java</title>\n");
        sb.append("    <author>Narayanan Jayaratchagan</author>\n");
        sb.append("   </article>\n");
        sb.append(" </journal>");
        sb.append(" <journal date=\"January 2004\">\n");
        sb.append("   <article>\n");
        sb.append("    <title>Data Binding with XMLBeans</title>\n");
        sb.append("    <author>Daniel Steinberg</author>\n");
        sb.append("   </article>\n");
        sb.append(" </journal>\n");
        sb.append("</catalog>");
        Document dom = XMLDocumentUtil.xmlToDom(sb.toString()); 
        System.out.println("el dom esta bien.....\n"+XMLDocumentUtil.domToXml(dom, "UTF-8", true));
        try {
            JSONObject json = XMLDocumentUtil.toJSON(dom); 
            System.out.println("json="+json.toString());
        }catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
