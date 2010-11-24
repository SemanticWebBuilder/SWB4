/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public abstract class Method {
    private final String id;
    private final Set<Parameter> parameters=new HashSet<Parameter>();
    private final HTTPMethod method;
    private final Resource resource;
    protected final Set<RepresentationRequest> requests=new HashSet<RepresentationRequest>();
    protected RepresentationRequest defaultRequestRepresentation;
    protected Method(String id,HTTPMethod method,Resource resource)
    {
        this.id=id;
        this.method=method;
        this.resource=resource;
    }
    public String getId()
    {
        return id;
    }
    static Method createMethodInfo(Element method,Resource resource) throws RestException
    {
        String id=method.getAttribute("id");
        String name=method.getAttribute("name");
        Method m=null;
        if(name.toUpperCase().equals("POST"))
        {
            m=new POSTMethod(id,resource);
        }
        else if(name.toUpperCase().equals("PUT"))
        {
            m=new PUTMethod(id,resource);
        }
        else if(name.toUpperCase().equals("DELETE"))
        {
            m=new DELETEMethod(id,resource);
        }
        else
        {
            m=new GETMethod(id,resource);
        }
        NodeList nodesRequest=method.getElementsByTagNameNS(ServiceInfo.WADL_NS, "request");
        if(nodesRequest.getLength()>0)
        {
            Element request=(Element)nodesRequest.item(0);
            NodeList childs=(request).getChildNodes();
            for(int i=0;i<childs.getLength();i++)
            {
                if(childs.item(i) instanceof Element)
                {
                    if(((Element)childs.item(i)).getTagName().equals("param"))
                    {
                        Parameter param=Parameter.createParamterInfo((Element)childs.item(i));
                        m.parameters.add(param);
                    }
                    if(((Element)childs.item(i)).getTagName().equals("representation"))
                    {
                        Element representation=(Element)childs.item(i);
                        RepresentationRequest rep=RepresentationBase.createRepresenatationRequest(representation, m);
                        if(m.defaultRequestRepresentation==null)
                        {
                            m.defaultRequestRepresentation=rep;
                        }
                    }
                }
            }
        }
        if(m.defaultRequestRepresentation==null)
        {
            ApplicationXwwwFormUrlEncoded _default=new ApplicationXwwwFormUrlEncoded(m);
            m.defaultRequestRepresentation=_default;
        }        
        return m;
    }
    public Resource getResource()
    {
        return resource;
    }
    public HTTPMethod getHTTPMethod()
    {
        return method;
    }
    public Parameter[] getAllParameters()
    {
        return parameters.toArray(new Parameter[parameters.size()]);
    }
    public RepresentationRequest[] getRequestRepresentations()
    {
        return requests.toArray(new RepresentationRequest[requests.size()]);
    }
    public Parameter[] getRequiredParameters()
    {
        ArrayList<Parameter> getRequiredParameters=new ArrayList<Parameter>();
        for(Parameter p : parameters)
        {
            if(p.isRequired() && !p.isFixed())
            {
                getRequiredParameters.add(p);
            }
        }
        return getRequiredParameters.toArray(new Parameter[getRequiredParameters.size()]);
    }
    public Parameter[] getOptionalParameters()
    {
        ArrayList<Parameter> getOptionalParameters=new ArrayList<Parameter>();
        for(Parameter p : parameters)
        {
            if(!p.isRequired() && !p.isFixed())
            {
                getOptionalParameters.add(p);
            }
        }
        return getOptionalParameters.toArray(new Parameter[getOptionalParameters.size()]);
    }
    
    public RepresentationRequest getDefaultRequestRepresentation()
    {
        return defaultRequestRepresentation;
    }
    
    public abstract RepresentationResponse execute(Map<String, Object> values) throws ExecutionRestException;
    


}
