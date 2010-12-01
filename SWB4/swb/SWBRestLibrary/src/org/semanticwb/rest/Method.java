/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public final class Method {
    private final String id;
    private final Set<Parameter> parameters=new HashSet<Parameter>();
    private final HTTPMethod httpMethod;
    private final Resource resource;
    protected final Set<RepresentationRequest> requests=new HashSet<RepresentationRequest>();
    protected RepresentationRequest defaultRequestRepresentation;
    protected final Set<ResponseDefinition> definitionResponses=new HashSet<ResponseDefinition>();
    protected Method(String name,HTTPMethod httpMethod,Resource resource)
    {
        this.id=name;
        this.httpMethod=httpMethod;
        this.resource=resource;
        
    }
    public ResponseDefinition[] getResponseDefinitions()
    {
        return definitionResponses.toArray(new ResponseDefinition[definitionResponses.size()]);
    }
    public String getId()
    {
        return id;
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
        NodeList nodesRequest=method.getElementsByTagNameNS(method.getNamespaceURI(), "request");
        if(nodesRequest.getLength()>0)
        {
            Element request=(Element)nodesRequest.item(0);
            NodeList childs=request.getChildNodes();
            for(int i=0;i<childs.getLength();i++)
            {
                if(childs.item(i) instanceof Element && ((Element)childs.item(i)).getNamespaceURI()!=null && ((Element)childs.item(i)).getNamespaceURI().equals(request.getNamespaceURI()))
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
            XWWWFormUrlEncoded _default=new XWWWFormUrlEncoded();
            _default.setMethod(m);
            m.defaultRequestRepresentation=_default;
        }
        NodeList listResponse=method.getElementsByTagNameNS(method.getNamespaceURI(), "response");
        for(int i=0;i<listResponse.getLength();i++)
        {
            if(listResponse.item(i) instanceof Element)
            {
                Element response=(Element)listResponse.item(i);
                ResponseDefinition[] definitions=ResponseDefinition.createResponseDefinition(response, m);
                m.definitionResponses.addAll(Arrays.asList(definitions));
            }
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
