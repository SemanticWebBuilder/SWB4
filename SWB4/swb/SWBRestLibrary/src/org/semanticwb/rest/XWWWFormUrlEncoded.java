/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public final class XWWWFormUrlEncoded extends RepresentationBase implements RepresentationRequest {

    
    public XWWWFormUrlEncoded(Method method)
    {
        super("application/x-www-form-urlencoded",method);
    }
    private String constructParameters(List<ParameterValue> values) throws RestException
    {        
        StringBuilder sb = new StringBuilder();
        try
        {
            for (Parameter parameter : this.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        sb.append(parameter.getName());
                        sb.append("=");
                        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
                        sb.append("&");
                    }
                }
            }
            for (Parameter parameter : this.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        sb.append(parameter.getName());
                        sb.append("=");
                        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
                        sb.append("&");
                    }
                }
            }            
            for (Parameter parameter : this.parameters)
            {
                if (parameter.isFixed())
                {
                    sb.append(parameter.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
                    sb.append("&");
                }
            }


            for (Parameter parameter : this.method.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        sb.append(parameter.getName());
                        sb.append("=");
                        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
                        sb.append("&");
                    }
                }
            }
            for (Parameter parameter : this.method.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        sb.append(parameter.getName());
                        sb.append("=");
                        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
                        sb.append("&");
                    }
                }
            }
            for (Parameter parameter : this.method.getAllParameters())
            {
                if (parameter.isFixed())
                {
                    sb.append(parameter.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
                    sb.append("&");
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RestException(e);
        }
        return sb.toString();
    }
    public RepresentationResponse request(List<ParameterValue> values) throws RestException
    {
        checkParameters(values);
        URL url=this.getMethod().getResource().getPath();
        String _parameters=constructParameters(values);        
        try
        {            
            if(this.getMethod().getHTTPMethod()==HTTPMethod.GET || this.getMethod().getHTTPMethod()==HTTPMethod.DELETE)
            {
                url=new URL(url.toString()+"?"+_parameters);
            }
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod(this.method.getHTTPMethod().toString());
            if(this.getMethod().getHTTPMethod()==HTTPMethod.PUT || this.getMethod().getHTTPMethod()==HTTPMethod.POST)
            {
                con.setRequestProperty(CONTENT_TYPE, this.mediaType);
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream out = con.getOutputStream();
                byte[] bparameters=_parameters.getBytes();
                out.write(bparameters);
                out.close();
            }
            
            
            if(con.getResponseCode()==200)
            {
                if(con.getHeaderField(CONTENT_TYPE)!=null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML))
                {
                    InputStream in=con.getInputStream();
                    Document response=SWBUtils.XML.xmlToDom(in);
                    if(response==null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    ApplicationXML resp=new ApplicationXML(response);
                    return resp;
                }
                if (con.getHeaderField(CONTENT_TYPE) != null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(AtomXML.ATOM_NS))
                {
                    InputStream in = con.getInputStream();
                    Document response = SWBUtils.XML.xmlToDom(in);
                    if (response == null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    AtomXML resp = new AtomXML(this.method);
                    resp.setDocument(response);
                    return resp;
                }
                else
                {
                    throw new RestException("The response has a not valid Content-Type header: "+con.getHeaderField(CONTENT_TYPE)+"(only "+JSON_CONTENT_TYPE+","+APPLICATION_XML+" are valid)");
                }
            }
            else
            {
                throw new RestException("The document was not found error code "+con.getResponseCode());
            }
        }
        catch(Exception ioe)
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), url, ioe);
        }
    }
}
