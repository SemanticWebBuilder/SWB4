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
public class XmlRpcClientConfig {
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
