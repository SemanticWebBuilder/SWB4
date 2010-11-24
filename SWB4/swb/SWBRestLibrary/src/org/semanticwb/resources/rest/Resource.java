/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.resources.rest;

import java.net.MalformedURLException;
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

    private final Set<Method> methods=new HashSet<Method>();
    private String id;
    private URL path;
    private Resource(String id,URL path)
    {
        this.id=id;
        this.path=path;
    }
    public String getId()
    {
        return id;
    }
    public URL getPath()
    {
        return path;
    }
    static Resource createResourceInfo(final Element resource,final URL basePath) throws RestException
    {
        String id=resource.getAttribute("id");
        if(id==null)
        {
            id=resource.getAttribute("path");
        }
        URL path;
        if(resource.getAttribute("path")!=null)
        {
            try
            {
                String spath=resource.getAttribute("path");
                String _basepath=basePath.toString();
                if(_basepath.indexOf(".")==-1)
                {
                    _basepath=basePath+"/";
                    URL tmp=new URL(_basepath);
                    path=new URL(tmp,spath);
                }
                else
                {
                    path=new URL(basePath, spath);
                }                
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
        Resource info=new Resource(id,path);
        NodeList emethods=resource.getElementsByTagNameNS(ServiceInfo.WADL_NS, "method");
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
