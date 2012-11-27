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
package org.semanticwb.security.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Serch
 */
public class RemoteURLLoginUtil
{

    static Logger log = SWBUtils.getLogger(RemoteURLLoginUtil.class);
    public static HashMap<String, String> getUserAttributes(String login, String stamp, String host, String URL, String soapAcction)
    {
        HashMap<String, String> ret = null;
        try
        {
            // Construct data
            String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login, "UTF-8");
            data += "&" + URLEncoder.encode("stamp", "UTF-8") + "=" + URLEncoder.encode(stamp, "UTF-8");

            String wsdata = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <Validate xmlns=\"http://wwww.PortalCiudadano.Signia.com/\">\n" +
//                    "      <Login>" + URLEncoder.encode(login, "UTF-8") + "</Login>\n" +
//                    "      <TimeStamp>" + URLEncoder.encode(stamp, "UTF-8") + "</TimeStamp>\n" +
                    "      <Login>" + login + "</Login>\n" +
                    "      <TimeStamp>" + stamp + "</TimeStamp>\n" +

                    "    </Validate>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>";
//System.out.println("WS-->"+wsdata);
            log.trace("ws-->"+wsdata);
            WSInvoker wsInvoker = new WSInvoker();
            wsInvoker.setHost(host);
            wsInvoker.setUrl(URL);
            wsInvoker.setSoapaction(soapAcction);
            String result = wsInvoker.invoke(wsdata);
            log.debug("wsResponse:"+result);
//            System.out.println(result);
            Document doc = SWBUtils.XML.xmlToDom(result);
            result = ((Element) doc.getFirstChild()).getElementsByTagName("ValidateResult").item(0).getTextContent();
           // result = SWBUtils.TEXT.decode(result, "UTF-16");
            result = result.replaceFirst("utf-16", "iso-8859-1");
            log.trace("->"+result);
//            System.out.println("->"+result);

//            // Send data
//            URL url = new URL(baseURL);
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            wr.write(data);
//            wr.flush();
//
//            // Get the response
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            StringBuilder xml = new StringBuilder(4096);
//            while ((line = rd.readLine()) != null)
//            {
//                xml.append(line);
//            }
//            wr.close();
//            rd.close();
//            System.out.println(xml);
//            //Document
            doc = SWBUtils.XML.xmlToDom(result);
            Element root = (Element) doc.getFirstChild();
//            System.out.println("Buscando user");
            log.trace("Buscando en el resultado");
            if (root.getNodeName().equals("user"))
            {
//                System.out.println("Hay user");
                log.trace("Tengo Tag");
                NodeList nl = root.getChildNodes();
                ret = new HashMap<String, String>();
//                System.out.println("tengo hijos " + nl.getLength());
                for (int i = 0; i < nl.getLength(); i++)
                {
                    ret.put(nl.item(i).getNodeName(), nl.item(i).getTextContent());
                    log.trace("Agregando "+ nl.item(i).getNodeName() + ":" + nl.item(i).getTextContent());
//                    System.out.println("->" + nl.item(i).getNodeName() + ":" + nl.item(i).getTextContent());
                }
            }
        } catch (Exception e)
        {
        }




        return ret;
    }
}

class WSInvoker
{

    private URL url;
    private String content_type = "text/xml; charset=utf-8";
    private String soapaction = "\"\"";
    private String host = null;

    public WSInvoker()
    {
    }

    public String invoke(String xml) throws Exception
    {
        //AFUtils.debug("WSInvoker:url:" + getUrl());
        //AFUtils.debug("WSInvoker:invoke:" + xml);
        String ret = null;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", content_type);
        con.setRequestProperty("SOAPAction", soapaction);
        if (host != null)
        {
            con.setRequestProperty("Host", host);
        }
        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        out.write(xml.getBytes());
        out.flush();
        out.close();
        int resc = con.getResponseCode();
        String resm = con.getResponseMessage();
//        System.out.println("ResponseMessage:" + resm);
        InputStream in = null;

        try
        {
            in = con.getInputStream();
        } catch (IOException e)
        {
            in = con.getErrorStream();
        }

        ret = SWBUtils.IO.readInputStream(in);
        //AFUtils.getInstance().readInputStream(in);

        if (resc != 200)
        {
            throw new Exception(resm + "\n" + ret);
        }
        return ret;
    }

    /** Getter for property url.
     * @return Value of property url.
     *
     */
    public URL getUrl()
    {
        return url;
    }

    /** Setter for property url.
     * @param url New value of property url.
     *
     */
    public void setUrl(java.lang.String url) throws MalformedURLException
    {
        this.url = new URL(url);
    }

    /** Setter for property url.
     * @param url New value of property url.
     *
     */
    public void setUrl(URL url)
    {
        this.url = url;
    }

    /** Getter for property soapaction.
     * @return Value of property soapaction.
     *
     */
    public java.lang.String getSoapaction()
    {
        return soapaction;
    }

    /** Setter for property soapaction.
     * @param soapaction New value of property soapaction.
     *
     */
    public void setSoapaction(java.lang.String soapaction)
    {
        if (soapaction != null)
        {
            this.soapaction = soapaction.trim();
            if (soapaction.startsWith("\""))
            {
                this.soapaction = soapaction;
            } else
            {
                this.soapaction = "\"" + soapaction + "\"";
            }
        }
    }

    /**
     * Getter for property host.
     * @return Value of property host.
     */
    public java.lang.String getHost()
    {
        return host;
    }

    /**
     * Setter for property host.
     * @param host New value of property host.
     */
    public void setHost(java.lang.String host)
    {
        this.host = host;
    }
}
