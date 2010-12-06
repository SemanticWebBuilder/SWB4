/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
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
    public static final String ORG_SEMANTICWB_REST_REPRESENTATIONBASE = "org.semanticwb.rest.RepresentationBase/";

    protected static final String JSON_CONTENT_TYPE = "json";    
    protected static final String TEXT_XML = "text/xml";

    protected static final String CONTENT_TYPE = "Content-Type";
    protected final Set<Parameter> parameters=new HashSet<Parameter>();
    //protected String mediaType;
    protected Method method;
    protected ResponseDefinition responseDefinition;
    /*protected RepresentationBase(String mediaType,Method method)
    {
        this.mediaType=mediaType;
        this.method=method;        
    }*/
    protected RepresentationBase()
    {
        
    }
    static RepresentationRequest createRepresenatationRequest(Element representation,Method method) throws RestException
    {
        String mediaType=representation.getAttribute("mediaType");
        if(mediaType==null)
        {
            throw new RestException("The mediaType atributo was not found");
        }
        
        try
        {

            Class<RepresentationRequest> repclass=RestSource.getRepresentationRequest(mediaType);
            if(repclass!=null)
            {
                Object objrep=repclass.newInstance();
                if(objrep instanceof RepresentationRequest)
                {
                    RepresentationRequest representationInfo=(RepresentationRequest)objrep;
                    representationInfo.setMethod(method);
                    NodeList params=representation.getChildNodes();
                    for(int i=0;i<params.getLength();i++)
                    {
                        if(params.item(i) instanceof Element && ((Element)params.item(i)).getTagName().equals("param"))
                        {
                            Parameter param=Parameter.createParamterInfo((Element)params.item(i));
                            representationInfo.addParameter(param);
                        }
                    }
                    return representationInfo;
                }
                else
                {
                    throw new RestException("The representatin "+ mediaType +" is not supported");
                }
            }
            else
            {
                throw new RestException("The representatin "+ mediaType +" is not supported");
            }
        }
        catch(IllegalAccessException cnfe)
        {
            throw new RestException("The representatin "+ mediaType +" is not supported", cnfe);
        }
        catch(InstantiationException cnfe)
        {
            throw new RestException("The representatin "+ mediaType +" is not supported", cnfe);
        }         
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

        for(Parameter parameter : this.method.getRequiredParameters())
        {
            if(!exists(parameter.getName(), values))
            {
                throw new RestException("The parameter "+parameter.getName()+" was not found");
            }
            valid(parameter, values);
        }
        for(Parameter parameter : this.method.getOptionalParameters())
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
    protected RepresentationResponse processResponse(HttpURLConnection con) throws IOException,InstantiationException,IllegalAccessException,RestException,ExecutionRestException
    {
        int responseCode = con.getResponseCode();
            if (con.getHeaderField(CONTENT_TYPE) != null)
            {
                String mediaType = con.getHeaderField(CONTENT_TYPE);
                for (ResponseDefinition definition : method.getResponseDefinitions())
                {
                    if (definition.getMediaType().equals(mediaType) && definition.getStatus() == responseCode)
                    {
                        Class clazz = RestSource.getRepresentationResponse(mediaType);
                        Object obj = clazz.newInstance();
                        if (obj instanceof RepresentationResponse)
                        {
                            RepresentationResponse repResponse = (RepresentationResponse) obj;
                            repResponse.setMethod(method);
                            repResponse.setStatus(responseCode);
                            repResponse.setURL(con.getURL());
                            repResponse.process(con);
                            for (ResponseDefinition def : this.method.definitionResponses)
                            {
                                if (def.getMediaType().equals(con.getHeaderField(CONTENT_TYPE)))
                                {
                                    def.validateResponse(repResponse.getResponse());
                                }
                            }
                            return repResponse;
                        }
                    }
                }
                throw new ExecutionRestException(this.getMethod().getHTTPMethod(), con.getURL(), "The response "+mediaType+" is not supported");
            }
            else
            {
                throw new ExecutionRestException(this.getMethod().getHTTPMethod(), con.getURL(), "The content-type was not found");
            }
    }
    

}
