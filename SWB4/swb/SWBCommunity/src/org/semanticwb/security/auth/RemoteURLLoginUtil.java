/**
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
 **/

package org.semanticwb.security.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Serch
 */
public class RemoteURLLoginUtil {
    

    public static HashMap<String, String> getUserAttributes(String login, String stamp, String baseURL){
    HashMap<String, String> ret = null;
    try {
        // Construct data
        String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login, "UTF-8");
        data += "&" + URLEncoder.encode("stamp", "UTF-8") + "=" + URLEncoder.encode(stamp, "UTF-8");
    
        // Send data
        URL url = new URL(baseURL);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
    
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder xml = new StringBuilder(4096);
        while ((line = rd.readLine()) != null) {
            xml.append(line);
        }
        wr.close();
        rd.close();
        System.out.println(xml);
        Document doc = SWBUtils.XML.xmlToDom(xml.toString());
        Element root = (Element)doc.getFirstChild();
        System.out.println("Buscando user");
        if (root.getNodeName().equals("user")){
            System.out.println("Hay user");
            NodeList nl = root.getChildNodes();
            ret = new HashMap<String, String>();
            System.out.println("tengo hijos "+nl.getLength());
            for(int i=0;i<nl.getLength();i++){
                ret.put(nl.item(i).getNodeName(), nl.item(i).getTextContent());
                System.out.println("->"+nl.item(i).getNodeName()+":"+ nl.item(i).getTextContent());
            }
        }
    } catch (Exception e) {
    }

  


    return ret;
    }
    
    
}
