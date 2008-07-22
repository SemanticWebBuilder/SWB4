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
    private URI ServerURI;
    private URI proxyServer;
    private int proxyPort;
    private String userName;
    private String password;
    public XmlRpcClientConfig(URI ServerURI)
    {
        this.ServerURI=ServerURI;
    }
    public XmlRpcClientConfig(URI ServerURI,URI proxyServer,int proxyPort)
    {
        this(ServerURI);
        this.proxyServer=proxyServer;
        this.proxyPort=proxyPort;
    }
    public XmlRpcClientConfig(URI ServerURI,URI proxyServer,int proxyPort,String user,String password)
    {
        this(ServerURI,proxyServer,proxyPort);
        this.userName=user;
        this.password=password;
    }
    public XmlRpcClientConfig(URI ServerURI,String user,String password)
    {        
        this(ServerURI);
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
        if(this.proxyServer()==null || proxyPort==0)
        {
            usesProxyServer= false;
        }
        else
        {
            usesProxyServer=true;
        }
        return usesProxyServer;
    }
    public URI proxyServer()
    {
        return proxyServer;    
    }
    public int getProxyPort()
    {
        return proxyPort;    
    }
    public URI getServerURI()
    {
        return ServerURI;
    }
    public void setServerURI(URI ServerURI)
    {
        this.ServerURI=ServerURI;
    }
}
