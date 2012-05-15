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

public class toDom {
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
    public void testToDom()
    {
        try {
            String j="{'k':null,'book':'hola lola','a':[{'x':[1,2],'y':['alfa','beta']},'text',1003.14159],'pilot':{'firstName':'Buzz','lastName':'Aldrin'}}";
            JSONObject json = new JSONObject(j);
        
            Document doc = XMLDocumentUtil.getDocument(json, null);
            System.out.println(XMLDocumentUtil.domToXml(doc, "UTF-8", true));
        }catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
