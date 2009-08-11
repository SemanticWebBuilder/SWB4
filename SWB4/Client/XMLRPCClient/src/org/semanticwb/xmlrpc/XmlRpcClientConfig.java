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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.net.URI;

/**
 *
 * @author victor.lorenzana
 */
class XmlRpcClientConfig {
    private URI webAddress;
    private URI proxyServer;
    private int proxyPort;
    private String userName;
    private String password;
    public XmlRpcClientConfig(URI webAddress)
    {
        this.webAddress=webAddress;
    }
    public XmlRpcClientConfig(URI webAddress,URI proxyServer,int proxyPort)
    {
        this(webAddress);
        this.proxyServer=proxyServer;
        this.proxyPort=proxyPort;
    }
    public XmlRpcClientConfig(URI webAddress,URI proxyServer,int proxyPort,String user,String password)
    {
        this(webAddress,proxyServer,proxyPort);
        this.userName=user;
        this.password=password;
    }
    public XmlRpcClientConfig(URI webAddress,String user,String password)
    {        
        this(webAddress);
        this.userName=user;
        this.password=password;
    }
    public void setUserName(String userName)
    {
        this.userName=userName;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }   
    
    public boolean hasUserPassWord()
    {
        boolean hasUserPassWord=false;
        if(this.userName==null || password==null)
        {
            hasUserPassWord=false;
        }
        else
        {
            hasUserPassWord=true;
        }
        return hasUserPassWord;
    }
    public String getUserName()
    {
        return userName;
    }
    public String getPassword()
    {
        return password;
    }
    public boolean usesProxyServer()
    {
        boolean usesProxyServer=false;
        if(this.getProxyServer()==null || proxyPort==0)
        {
            usesProxyServer= false;
        }
        else
        {
            usesProxyServer=true;
        }
        return usesProxyServer;
    }
    public URI getProxyServer()
    {
        return proxyServer;    
    }
    public int getProxyPort()
    {
        return proxyPort;    
    }
    public void setProxyServer(URI proxyServer)
    {
        this.proxyServer=proxyServer;
    }
    public void setProxyPort(int proxyPort)
    {
        this.proxyPort=proxyPort;
    }
    public URI getWebAddress()
    {
        return webAddress;
    }
    public void setWebAddress(URI ServerURI)
    {
        this.webAddress=ServerURI;
    }
}
