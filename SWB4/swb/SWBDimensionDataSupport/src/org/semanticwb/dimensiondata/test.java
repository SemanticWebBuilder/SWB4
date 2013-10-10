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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.dimensiondata;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import sun.misc.BASE64Encoder;

/**
 *
 * @author serch
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//        URL url = new URL("https://api.opsourcecloud.net/oec/0.9/myaccount");
//        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
//        connection.setRequestMethod("GET");
        BASE64Encoder enc = new sun.misc.BASE64Encoder();
      String userpassword = "USER" + ":" + "#######";
      String encodedAuthorization = enc.encode( userpassword.getBytes() );
       // System.out.println("resutl:"+getRESTget("https://api.opsourcecloud.net/oec/0.9/myaccount", encodedAuthorization));
        //f367c118-3f58-49b0-8bf6-127d26d9d823 OrgID
        
        System.out.println("Result: "+ getRESTget("https://api.opsourcecloud.net/oec/0.9/base/image", encodedAuthorization));
        
        
//      connection.setRequestProperty("Authorization", "Basic "+
//            encodedAuthorization);
//      connection.connect();
//        Reader stream = new InputStreamReader(connection.getInputStream());
//        BufferedReader reader =  new BufferedReader(stream);
//        String line;
//        while ((line=reader.readLine())!=null){
//            System.out.println("line:"+line);
//        }
//        reader.close();
    }
    
    public static String getRESTget(String url, String encodedAuth) throws IOException{
        String ret = "";
        URL lurl = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection)lurl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic "+encodedAuth);
        connection.connect();
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        byte [] buff = new byte[8192];
        int length;
        while ((length=bis.read(buff))>0){
            ret = ret + new String(buff,0,length);
        }
        bis.close();
        return ret;
    }
}
