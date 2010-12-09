/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.publish;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.rest.consume.MethodModule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public abstract class ResourceModule
{

    protected final Map<String, MethodModule> methods = Collections.synchronizedMap(new HashMap<String, MethodModule>());
    protected final Map<String, ResourceModule> subResources = Collections.synchronizedMap(new HashMap<String, ResourceModule>());

    public abstract String getId();

    public void addMethods(Element resource)
    {
        Document doc = resource.getOwnerDocument();
        String WADL_NS = resource.getNamespaceURI();
        for (String id : methods.keySet())
        {
            MethodModule methodModule = methods.get(id);
            Element method = doc.createElementNS(WADL_NS, "method");
            resource.appendChild(method);
            method.setAttribute("id", id);
            method.setAttribute("name", methodModule.getHTTPMethod().toString());
            methodModule.addParameters(method);
        }        
        for (String id : subResources.keySet())
        {
            ResourceModule subresource=subResources.get(id);
            Element esubresource = doc.createElementNS(WADL_NS, "resource");
            esubresource.setAttribute("id", subresource.getId());
            esubresource.setAttribute("path", id);
            subresource.addMethods(esubresource);
            resource.appendChild(esubresource);
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response, String servet, List<String> path, String basepath) throws IOException
    {
        System.out.println("path: " + path.size());
        if (path.isEmpty())
        {
            for (MethodModule methodModule : methods.values())
            {
                if (methodModule.getHTTPMethod().toString().equals(request.getMethod()))
                {
                    methodModule.execute(request, response,basepath);
                }
            }
        }
        else // issubresource
        {            
            String subresource=path.get(0);            
            if(subResources.containsKey(subresource))
            {
                ResourceModule resourceModule=subResources.get(subresource);
                basepath+="/"+path.remove(0);
                resourceModule.service(request, response, servet, path, basepath);
            }
            else
            {
                response.setStatus(404);
            }
        }
    }
}
