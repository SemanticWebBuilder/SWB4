/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcProxyFactory implements java.lang.reflect.InvocationHandler, XmlProxy
{

    private List<Attachment> attachments = new ArrayList<Attachment>();
    private URI Uri;
    private String user,  password;
    private URI proxyAddress;
    private int proxyPort;
    public URI getWebAddress()
    {
        return Uri;
    }

    public void setWebAddress(URI uri)
    {
        this.Uri = uri;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    public URI getProxyAddress()
    {
        return proxyAddress;
    }
    public void setProxyAddress(URI proxyAddress)
    {
        this.proxyAddress=proxyAddress;
    }
    
    public int getProxyPort()
    {
        return proxyPort;
    }
    public void setProxyPort(int proxyPort)
    {
        this.proxyPort=proxyPort;
    }

    public void addAttachment(Attachment attachment)
    {
        this.attachments.add(attachment);
    }

    public void clearAttachments()
    {
        this.attachments.clear();
    }

    public static <T> T newInstance(java.lang.Class<? extends XmlProxy> clazz)
    {
        Class[] interfaces = {clazz};
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, new XmlRpcProxyFactory());
        return ( T ) obj;
    }
    private XmlRpcProxyFactory()
    {

    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable
    {
        Object ObjectToreturn = null;
        if ( m.getName().equals("getWebAddress") )
        {
            ObjectToreturn = this.Uri;
        }
        else if ( m.getName().equals("setWebAddress") )
        {
            URI uri = ( URI ) args[0];
            this.setWebAddress(uri);
        }
        else if ( m.getName().equals("setUser") )
        {
            String pUser = ( String ) args[0];
            this.setUser(pUser);
        }
        else if ( m.getName().equals("setPassword") )
        {
            String pPassword = ( String ) args[0];
            this.setPassword(pPassword);
        }
        else if ( m.getName().equals("getUser") )
        {
            ObjectToreturn = this.getUser();
        }
        else if ( m.getName().equals("getPassword") )
        {
            ObjectToreturn = this.getPassword();
        }
        else if ( m.getName().equals("addAttachment") )
        {
            Attachment attachment = ( Attachment ) args[0];
            this.addAttachment(attachment);

        }
        else if ( m.getName().equals("clearAttachments") )
        {
            this.clearAttachments();
        }
        else if ( m.getName().equals("getProxyAddress") )
        {            
            ObjectToreturn = this.getProxyAddress();
        }
        else if ( m.getName().equals("setProxyAddress") )
        {
            URI pProxyAddress = ( URI ) args[0];
            this.setProxyAddress(pProxyAddress);
        }
        else if ( m.getName().equals("getProxyPort") )
        {
            ObjectToreturn = this.getProxyPort();
        }
        else if ( m.getName().equals("setProxyPort") )
        {
            Integer pProxyPort = ( Integer ) args[0];
            this.setProxyPort(pProxyPort);
        }
        else
        {
            String methodName = m.getDeclaringClass().getSimpleName() + "." + m.getName();
            AnnotatedClass annotatedClass = AnnotationManager.getAnnotatedClass(m.getDeclaringClass());
            if ( annotatedClass != null )
            {
                AnnotatedMethod annotatedMethod = annotatedClass.getAnnotatedMethod(m);
                if ( annotatedMethod != null && annotatedMethod.getAnnotation(XmlRpcMethod.class) != null )
                {
                    XmlRpcMethod xmlRpcMethod = ( XmlRpcMethod ) annotatedMethod.getAnnotation(XmlRpcMethod.class);
                    methodName = xmlRpcMethod.methodName();
                }
            }
            XmlRpcClientConfig config = new XmlRpcClientConfig(this.Uri);
            config.setPassword(password);
            config.setUserName(user);
            config.setProxyServer(this.getProxyAddress());
            config.setProxyPort(this.getProxyPort());
            XmlRpcClient<Object> xmlclient = new XmlRpcClient<Object>(config);
            ObjectToreturn = xmlclient.execute(methodName, args, this.attachments);
        }
        return ObjectToreturn;
    }
}


