/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.resources.rest;

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
public abstract class RepresentationBase implements RepresentationRequest {

    protected static final String JSON_CONTENT_TYPE = "json";
    protected static final String APPLICATION_XML = "application/xml";
    protected static final String CONTENT_TYPE = "Content-Type";
    protected final Set<Parameter> parameters=new HashSet<Parameter>();
    protected final String mediaType;
    protected final Method method;
    protected RepresentationBase(String mediaType,Method method)
    {
        this.mediaType=mediaType;
        this.method=method;
    }
    static RepresentationBase createRepresenatationRequest(Element representation,Method method) throws RestException
    {
        String mediaType=representation.getAttribute("mediaType");
        if(mediaType==null)
        {
            throw new RestException("The mediaType atributo was not found");
        }
       
        RepresentationBase representationInfo=null;        
        if(mediaType.equals("application/x-www-form-urlencoded"))
        {
            representationInfo=new ApplicationXwwwFormUrlEncoded(method);
        }
        if(mediaType.equals("multipart/form-data"))
        {
            representationInfo=new ApplicationMultipartFormData(method);
        }
        if(representation==null)
        {
            throw new RestException("The representation "+ mediaType +" is not supported");
        }
        else
        {
            NodeList params=representation.getChildNodes();
            for(int i=0;i<params.getLength();i++)
            {
                if(params.item(i) instanceof Element && ((Element)params.item(i)).getTagName().equals("param"))
                {
                    Parameter param=Parameter.createParamterInfo((Element)params.item(i));
                    representationInfo.parameters.add(param);
                }
            }
        }
        return representationInfo;
    }
    public String getMediaType()
    {
        return mediaType;
    }
    public Method getMethod()
    {
        return method;
    }
    private boolean exists(String parameterName,List<ParameterValue> values) throws RestException
    {
        for(ParameterValue value : values)
        {
            if(parameterName.equals(value.getName()) && value.getValue()!=null)
            {
                return true;
            }
        }
        return false;
    }
    private void valid(Parameter parameter,ParameterValue value) throws RestException
    {
        if(value.getValue()==null)
        {
            throw new RestException("The value is null");
        }
        if(!value.getValue().getClass().equals(parameter.getType()))
        {
            throw new RestException("Value invalid required :"+ parameter.getType().getName() +" value type: "+value.getValue().getClass().getName());
        }        
        
    }
    private void valid(Parameter parameter,List<ParameterValue> values) throws RestException
    {
        for(ParameterValue value : values)
        {
            if(value==null)
            {
                throw new RestException("The value is null");
            }
            if(parameter.getName().equals(value.getName()))
            {
                valid(parameter, value);
            }
        }        
    }
    public void checkParameters(List<ParameterValue> values) throws RestException
    {
        for(Parameter parameter : this.getRequiredParameters())
        {
            if(!exists(parameter.getName(), values))
            {
                throw new RestException("The parameter "+parameter.getName()+" was not found");
            }
            valid(parameter, values);
        }
        for(Parameter parameter : this.getOptionalParameters())
        {
            valid(parameter, values);
        }
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
        getRequiredParameters.addAll(Arrays.asList(method.getRequiredParameters()));
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
        getOptionalParameters.addAll(Arrays.asList(method.getOptionalParameters()));
        return getOptionalParameters.toArray(new Parameter[getOptionalParameters.size()]);
    }

    public Parameter[] getAllParameters()
    {
        ArrayList<Parameter> getAllParameters=new ArrayList<Parameter>();
        getAllParameters.addAll(parameters);
        getAllParameters.addAll(Arrays.asList(method.getOptionalParameters()));
        return getAllParameters.toArray(new Parameter[getAllParameters.size()]);
    }

    

}
