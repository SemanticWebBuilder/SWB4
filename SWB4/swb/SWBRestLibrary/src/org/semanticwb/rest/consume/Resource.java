/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.net.URI;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class Resource
{

    private static final Logger log = SWBUtils.getLogger(Resource.class);
    private final ServiceInfo serviceInfo;
    private final Map<String, Method> methods = new HashMap<String, Method>();
    private final Map<String, Resource> subresources = new HashMap<String, Resource>();
    private String id;
    private URL path;

    private Resource(String id, URL path, ServiceInfo serviceInfo)
    {
        this.id = id;
        this.path = path;
        this.serviceInfo = serviceInfo;
    }

    public URL getPath()
    {
        try
        {
            return new URL(path.toString());
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return null;
    }

    public String getId()
    {
        return id;
    }

    public Method[] getAllMethods()
    {
        ArrayList<Method> allMethods = new ArrayList<Method>();
        for (Resource resource : subresources.values())
        {
            allMethods.addAll(Arrays.asList(resource.getAllMethods()));
        }
        allMethods.addAll(this.methods.values());
        return allMethods.toArray(new Method[allMethods.size()]);
    }

    public ServiceInfo getServiceInfo()
    {
        return serviceInfo;
    }

    static Resource createResourceInfo(final Element eresource, final URL basePath, ServiceInfo serviceInfo) throws RestException
    {
        String id = eresource.getAttribute("id");
        if (id == null || eresource.getAttribute("id").trim().equals(""))
        {
            id = eresource.getAttribute("path");
        }
        URL path;
        if (eresource.getAttribute("path") != null && !eresource.getAttribute("path").trim().equals(""))
        {
            try
            {
                String spath = eresource.getAttribute("path");
                URI uriPath = new URI(spath);
                if (!uriPath.isAbsolute())
                {
                    URI base = basePath.toURI();
                    if (!basePath.toString().endsWith("/"))
                    {
                        String newpath = basePath.toString() + "/";
                        base = new URI(newpath);
                    }
                    URI temp = base.resolve(uriPath);
                    path = temp.normalize().toURL();
                }
                else
                {
                    path = uriPath.toURL();
                }
            }
            catch (URISyntaxException me)
            {
                throw new RestException(me);
            }
            catch (MalformedURLException me)
            {
                throw new RestException(me);
            }
        }
        else
        {
            path = basePath;
        }
        Resource resource = new Resource(id, path, serviceInfo);


        NodeList childs = eresource.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getNamespaceURI().equals(eresource.getNamespaceURI()))
            {
                if (((Element) childs.item(i)).getLocalName().equals("method"))
                {
                    Method m = Method.createMethodInfo((Element) childs.item(i), resource);
                    resource.methods.put(m.getId(), m);
                }
                if (((Element) childs.item(i)).getLocalName().equals("resource"))
                {
                    Resource subresource = Resource.createResourceInfo((Element) childs.item(i), resource.path, serviceInfo);
                    resource.subresources.put(subresource.id, subresource);
                }
            }
        }

        return resource;
    }

    public Method[] getMethods()
    {
        return methods.values().toArray(new Method[methods.size()]);
    }

    public Method getMethod(String id)
    {
        if (methods.containsKey(id))
        {
            return methods.get(id);
        }
        else
        {
            for (Resource subresource : subresources.values())
            {
                Method tmp=subresource.getMethod(id);
                if(tmp!=null)
                {
                    return tmp;
                }
            }
        }
        return null;
    }

    public Resource getResource(String id)
    {
        if(this.id.equals(id))
        {
            return this;
        }
        else
        {
            for(Resource resource : subresources.values())
            {
                Resource tmp=resource.getResource(id);
                if(tmp!=null)
                {
                    return tmp;
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return id.toString();
    }

}
