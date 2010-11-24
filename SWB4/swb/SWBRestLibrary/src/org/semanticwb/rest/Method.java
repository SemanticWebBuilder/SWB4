/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public final class Method {
    private final String name;
    private final Set<Parameter> parameters=new HashSet<Parameter>();
    private final HTTPMethod httpMethod;
    private final Resource resource;
    protected final Set<RepresentationRequest> requests=new HashSet<RepresentationRequest>();
    protected RepresentationRequest defaultRequestRepresentation;
    private final String id;
    protected Method(String name,HTTPMethod httpMethod,Resource resource)
    {
        this.name=name;
        this.httpMethod=httpMethod;
        this.resource=resource;
        id=UUID.randomUUID().toString().replace("-", "_");
    }
    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    static Method createMethodInfo(Element method,Resource resource) throws RestException
    {
        String id=method.getAttribute("id");
        String name=method.getAttribute("name");
        HTTPMethod httpMethod=HTTPMethod.GET;
        if(name.equals(HTTPMethod.DELETE.toString()))
        {
            httpMethod=HTTPMethod.DELETE;
        }
        if(name.equals(HTTPMethod.POST.toString()))
        {
            httpMethod=HTTPMethod.POST;
        }
        if(name.equals(HTTPMethod.PUT.toString()))
        {
            httpMethod=HTTPMethod.PUT;
        }
        Method m= new Method(id,httpMethod,resource);
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
            XWWWFormUrlEncoded _default=new XWWWFormUrlEncoded(m);
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
        return httpMethod;
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
    
    
    
    public RepresentationResponse request(List<ParameterValue> values) throws ExecutionRestException,RestException
    {
        return getDefaultRequestRepresentation().request(values);
    }

}
