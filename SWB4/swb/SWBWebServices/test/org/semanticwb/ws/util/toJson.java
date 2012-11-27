/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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
