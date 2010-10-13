/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public class RestPublish {

    private class EventServiceListener implements Runnable
    {
        private final ServiceListener listener;
        private final ServiceInfo serviceInfo;
        private final HTTPMethod method;
        private final Document response;
        private final Set<RestParameter> parameters;

        EventServiceListener(ServiceListener listener,ServiceInfo serviceInfo,HTTPMethod method,Document response,Set<RestParameter> parameters)
        {
            this.listener=listener;
            this.serviceInfo=serviceInfo;
            this.method=method;
            this.response=response;
            this.parameters=parameters;
        }
        public void run()
        {
            switch(method)
            {
                case GET:
                    listener.onGET(serviceInfo, response, parameters);
                    break;
                case DELETE:
                    listener.onDELETE(serviceInfo, parameters);
                    break;
                case POST:
                    listener.onPOST(serviceInfo, parameters);
                    break;
                case PUT:
                    listener.onPUT(serviceInfo, parameters);
                    break;
            }
            
        }

    }
    private static final Set<ServiceListener> listeners=Collections.synchronizedSet(new HashSet<ServiceListener>());
    private final Map<ServiceProvider, UUID> providers=Collections.synchronizedMap(new HashMap<ServiceProvider, UUID>());
    public RestPublish()
    {
        
    }
    public void registerServiceProvider(ServiceProvider provider)
    {        
        providers.put(provider,UUID.randomUUID());
    }
    public void removeServiceProvider(ServiceProvider provider)
    {
        providers.remove(provider);
    }
    private Document getList(HttpServletRequest request)
    {
        Document doc=SWBUtils.XML.getNewDocument();
        Element services=doc.createElement("services");
        doc.appendChild(services);
        for(ServiceProvider provider : providers.keySet())
        {
            UUID uuid=providers.get(provider);
            Element service=doc.createElement("service");
            services.appendChild(service);
            String link=uuid.toString().replace('-', '_');
            String port="";
            if(request.getServerPort()!=80)
            {
                port=":"+request.getServerPort();
            }
            link=request.getScheme()+port+"//"+request.getServerName()+request.getContextPath()+uuid;
            service.setAttributeNS("http://www.w3.org/1999/xlink", "href", link);
            service.setAttribute("name",provider.getServiceInfo().getName());
            service.appendChild(doc.createTextNode(provider.getServiceInfo().getName()));
            for(ParameterInfo parameterInfo : provider.getServiceInfo().getParameters())
            {
                Element parameter=doc.createElement("parameter");
                parameter.setAttribute("name", parameterInfo.getParameterName());
                parameter.appendChild(doc.createTextNode(parameterInfo.getParameterDescription()));
                service.appendChild(parameter);

            }
        }
        return doc;
    }
    private void showList(HttpServletRequest request,HttpServletResponse response) throws IOException
    {        
        Document doc=getList(request);
        PrintWriter out=response.getWriter();
        response.setContentType("application/xml; charset=utf-8");
        String xml=SWBUtils.XML.domToXml(doc, "utf-8", true);
        out.print(xml);
        out.close();
    }
    public void service(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        String uri=request.getRequestURI();
        String cntx = request.getContextPath();
        uri = uri.substring(cntx.length());
        String[] path=uri.split("/");
        
        if(path!=null && path.length<=2)
        {
            showList(request,response);
            return;
        }
        if(path==null || (path!=null && path.length>3))
        {
            response.sendError(404, "The service was not found");
        }
        String uuid=path[2];
        UUID uuidprovider=null;
        try
        {
            uuidprovider=UUID.fromString(uuid.replace('_', '-'));
        }
        catch(IllegalArgumentException iae)
        {
            response.sendError(404, "The service was not found, incorrect id");
            return;
        }
        if(uuidprovider==null)
        {
            response.sendError(404, "The service was not found");
            return;
        }
        ServiceProvider provider=null;
        for(ServiceProvider temp : providers.keySet())
        {
            UUID value=providers.get(temp);
            if(value.equals(uuidprovider))
            {
                provider=temp;
                break;
            }
        }
        
        HTTPMethod method=HTTPMethod.GET;
        if(request.getMethod().toUpperCase().equals(HTTPMethod.DELETE.toString()))
        {
            method=HTTPMethod.DELETE;
        }
        if(request.getMethod().toUpperCase().equals(HTTPMethod.GET.toString()))
        {
            method=HTTPMethod.GET;
        }
        if(request.getMethod().toUpperCase().equals(HTTPMethod.PUT.toString()))
        {
            method=HTTPMethod.PUT;
        }
        if(request.getMethod().toUpperCase().equals(HTTPMethod.POST.toString()))
        {
            method=HTTPMethod.POST;
        }

        Document doc=null;
        final Set<RestParameter> parameters=new HashSet<RestParameter>();
        Enumeration names=request.getParameterNames();
        while(names.hasMoreElements())
        {
            String name=(String)names.nextElement();
            if(name!=null)
            {
                String[] values=request.getParameterValues(name);
                if(values!=null)
                {
                    parameters.add(new RestParameter(name, values));
                }
            }
        }
        try
        {
            switch(method)
            {
                case GET:
                    Document resp=provider.executeGET(parameters);
                    response.setContentType("application/xml; charset=utf-8");
                    String xml=SWBUtils.XML.domToXml(doc, "utf-8", true);
                    PrintWriter writer=response.getWriter();
                    writer.println(xml);
                    writer.close();
                    try
                    {
                        fireOnService(provider.getServiceInfo(), resp, method, parameters);
                    }
                    catch(Throwable t)
                    {

                    }
                    break;
                case DELETE:
                    provider.executeDelete(parameters);
                    try
                    {
                        fireOnService(provider.getServiceInfo(), null, method, parameters);
                    }
                    catch(Throwable t)
                    {

                    }
                    break;
                case POST:                    
                    InputStream inpost=request.getInputStream();
                    SWBUtils.XML.xmlToDom(inpost);
                    inpost.close();
                    provider.executePOST(parameters,doc);
                    try
                    {
                        fireOnService(provider.getServiceInfo(), null, method, parameters);
                    }
                    catch(Throwable t)
                    {

                    }
                    break;
                 case PUT:                    
                    InputStream input=request.getInputStream();
                    SWBUtils.XML.xmlToDom(input);
                    input.close();
                    provider.executePUT(parameters,doc);
                    try
                    {
                        fireOnService(provider.getServiceInfo(), null, method, parameters);
                    }
                    catch(Throwable t)
                    {
                        
                    }
                    break;

            }
        }
        catch(RestException re)
        {            
            response.sendError(500,re.getMessage());
            return;
        }
        
    }
    
    public void addServiceListener(ServiceListener listener)
    {
        listeners.add(listener);        
    }
    public void removeServiceListener(ServiceListener listener)
    {
        listeners.remove(listener);
    }

    private void fireOnService(ServiceInfo info,Document response,HTTPMethod method,Set<RestParameter> parameters)
    {
        if(listeners.size()>0)
        {
            ExecutorService executor=Executors.newFixedThreadPool(listeners.size());
            for(ServiceListener listener : listeners)
            {
                executor.execute(new EventServiceListener(listener,info,method,response,parameters));
            }
            executor.shutdown();
        }
    }




}

