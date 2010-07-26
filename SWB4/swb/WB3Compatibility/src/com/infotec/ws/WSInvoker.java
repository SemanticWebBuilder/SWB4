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
 * WSInvoker.java
 *
 * Created on 24 de octubre de 2004, 11:30 PM
 */

package com.infotec.ws;

import com.infotec.appfw.util.AFUtils;
import java.io.*;
import java.net.*;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class WSInvoker
{
    private URL url;
    private String content_type="text/xml; charset=utf-8";
    private String soapaction="\"\"";
    private String host=null;
    
    //Content-Length
    
    /** Creates a new instance of WSInvoker */
    public WSInvoker()
    {
        
    }
    
    public String invoke(String xml) throws Exception
    {
        AFUtils.debug("WSInvoker:url:"+getUrl());
        AFUtils.debug("WSInvoker:invoke:"+xml);
        String ret=null;
        HttpURLConnection con=(HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", content_type);
        con.setRequestProperty("SOAPAction", soapaction);
        if(host!=null)con.setRequestProperty("Host", host);
        con.setDoOutput(true);
        OutputStream out=con.getOutputStream();
        out.write(xml.getBytes());
        out.flush();
        out.close();
        int resc=con.getResponseCode();
        String resm=con.getResponseMessage();    
        System.out.println("ResponseMessage:"+resm);
        InputStream in=null;
        
        try
        {
            in=con.getInputStream();
        }catch(IOException e)
        {
            in=con.getErrorStream();
        }
        
        ret=AFUtils.getInstance().readInputStream(in);
        
        if(resc!=200)
        {
            throw new Exception(resm+"\n"+ret);
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
        if(soapaction!=null)
        {
            this.soapaction=soapaction.trim();
            if(soapaction.startsWith("\""))
            {
                this.soapaction = soapaction;
            }else
            {
                this.soapaction = "\""+soapaction+"\"";
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
