/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.net.URI;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author victor.lorenzana
 */
public class Resource {

    private final ServiceInfo serviceInfo;
    private final Set<Method> methods=new HashSet<Method>();
    private String id;
    private URL path;
    private Resource(String id,URL path,ServiceInfo serviceInfo)
    {
        this.id=id;
        this.path=path;
        this.serviceInfo=serviceInfo;
    }
    public String getId()
    {
        return id;
    }
    public URL getPath()
    {
        return path;
    }
    public ServiceInfo getServiceInfo()
    {
        return serviceInfo;
    }
    static Resource createResourceInfo(final Element resource,final URL basePath,ServiceInfo serviceInfo) throws RestException
    {
        String id=resource.getAttribute("id");
        if(id==null || resource.getAttribute("id").trim().equals(""))
        {
            id=resource.getAttribute("path");
        }
        URL path;
        if(resource.getAttribute("path")!=null && !resource.getAttribute("path").trim().equals(""))
        {
            try
            {
                String spath=resource.getAttribute("path");
                URI uriPath=new URI(spath);
                if(!uriPath.isAbsolute())
                {
                    URI base=basePath.toURI();
                    if(!basePath.toString().endsWith("/"))
                    {
                        String newpath=basePath.toString()+"/";
                        base=new URI(newpath);
                    }
                    URI temp=base.resolve(uriPath);
                    path=temp.normalize().toURL();
                }
                else
                {
                    path=uriPath.toURL();
                }
            }
            catch(URISyntaxException me)
            {
                throw new RestException(me);
            }
            catch(MalformedURLException me)
            {
                throw new RestException(me);
            }
        }
        else
        {
            path=basePath;
        }
        Resource info=new Resource(id,path,serviceInfo);
        NodeList emethods=resource.getElementsByTagNameNS(resource.getNamespaceURI(), "method");
        for(int i=0;i<emethods.getLength();i++)
        {
            Node node=emethods.item(i);
            if(node instanceof Element)
            {
                Method m=Method.createMethodInfo((Element)node,info);
                info.methods.add(m);
            }
        }

        return info;
    }
    public Method[] getMethods()
    {
        return methods.toArray(new Method[methods.size()]);
    }
}
