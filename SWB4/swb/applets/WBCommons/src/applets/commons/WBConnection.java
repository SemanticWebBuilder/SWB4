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
 


/*
 * WBConnection.java
 *
 * Created on 15 de febrero de 2006, 06:55 PM
 */

package applets.commons;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBConnection
{
    private Applet _applet=null;
    
    public static final String PRM_JSESS="jsess";
    public static final String PRM_CGIPATH="cgipath";
    
    private String cgiPath="/gtw.jsp";
    private String jsess=null;                    //session del usuario
    private URL url=null;    
    
    /** Creates a new instance of WBConnection */
    public WBConnection(Applet applet)
    {
        setApplet(applet);
    }

    /** Creates a new instance of WBConnection */
    public WBConnection(String jsess, String cgipath, String codeBase)
    {
        this.jsess=jsess;
        this.cgiPath=cgipath;
        try
        {
            URL cb=new URL(codeBase);
            //System.out.println("codeBase:"+codeBase+" cgipath:"+cgipath+" cb:"+cb);
            if(cgiPath!=null)
            {
                url=new URL(cb,cgiPath);
                //System.out.println("url:"+url);
            }
        }catch(Exception e){e.printStackTrace();}
    }

    
    public void setApplet(Applet applet)
    {
        _applet=applet;
        configParams();
    }
    
    private void configParams()
    {
        jsess=_applet.getParameter(PRM_JSESS);
        cgiPath=_applet.getParameter(PRM_CGIPATH);
        try
        {
            url=new URL(_applet.getCodeBase().getProtocol(),_applet.getCodeBase().getHost(),_applet.getCodeBase().getPort(),cgiPath);
        }catch(Exception e){}
    }
    
    public Applet getApplet()
    {
        return _applet;
    }
    
    public WBTreeNode executeCommand(String cmd)
    {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+cmd+"</cmd></req>";
        String data=getData(xml);
        if(data==null)return null;
        return new WBXMLParser().parse(data);
    }
    
    public String getData(String xml)
    {
        StringBuffer ret=new StringBuffer();
        try
        {
            //URL gurl=new URL(this.url,cgi);
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            //System.out.println("JSESSIONID="+jsess);
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
                //System.out.println(inputLine);
            }
            in.close();
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        return ret.toString();
    }

    public String getEncData(String xml)
    {
        StringBuilder ret=new StringBuilder();
        try
        {
            //URL gurl=new URL(this.url,cgi);
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            //System.out.println("JSESSIONID="+jsess);
            urlconn.setDoOutput(true);
            OutputStream out=urlconn.getOutputStream();
            out.write(xml.getBytes("UTF-8"));
            out.flush();
            out.close();

            InputStreamReader in = new InputStreamReader(urlconn.getInputStream(), "UTF-8");
            char[] bfile = new char[8192];
            int x;
            while ((x = in.read(bfile, 0, 8192)) > -1)
            {
                ret.append(new String(bfile, 0, x));
            }
            in.close();
            return ret.toString();
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        return ret.toString();
    }

    public String getUrl()
    {
        return url.toString();
    }

    public String getUri()
    {
        String ret="";
        String url=getUrl();
        int i=url.indexOf("suri=");
        if(i>0)
        {
            ret=url.substring(i+5);
            ret=URLDecoder.decode(ret);
        }
        return ret;
    }

        /**
         * Reads an input stream and creates a string with that content.
         * <p>Lee un objeto inputStream y crea un objeto string con el contenido le&iacute;do.</p>
         *
         * @param in an input stream to read its content
         * @return a string whose content is the same as for the input stream read.
         * un objeto string cuyo contenido es el mismo que el del objeto
         * inputStream le&iacute;do.
         * @throws IOException if the input stream received is {@code null}.
         * <p>Si el objeto inputStream recibido tiene un valor {@code null}.</p>
         */
        public static String readInputStream(InputStream in) throws IOException
        {
            if (in == null)
            {
                throw new IOException("Input Stream null");
            }
            StringBuilder buf = new StringBuilder();
            byte[] bfile = new byte[8192];
            int x;
            while ((x = in.read(bfile, 0, 8192)) > -1)
            {
                String aux = new String(bfile, 0, x);
                buf.append(aux);
            }
            in.close();
            return buf.toString();
        }
        
        public static String readEncInputStream(InputStream input,String enc) throws IOException
        {
            if (input == null)
            {
                throw new IOException("Input Stream null");
            }
            StringBuilder ret = new StringBuilder();
            InputStreamReader in = new InputStreamReader(input,enc);
            char[] bfile = new char[8192];
            int x;
            while ((x = in.read(bfile, 0, 8192)) > -1)
            {
                ret.append(new String(bfile, 0, x));
            }
            in.close();
            return ret.toString();
        }



}
