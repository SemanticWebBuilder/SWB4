/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rest.util.HTTPMethod;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class JSON extends RepresentationBase implements RepresentationRequest, JSONResponse
{

    public static final String APPLICATION_JSON = "application/json";
    private static final Logger log = SWBUtils.getLogger(JSON.class);
    private int status;
    private URL url;
    private JSONObject response;

    public Object getResponse() throws RestException
    {
        return response;
    }
    public int getStatus()
    {
        return status;
    }

    public ParameterDefinition[] getParameterDefinitions()
    {
        for (ResponseDefinition def : this.getMethod().definitionResponses)
        {
            if (def.getStatus() == status)
            {
                return def.getParameters();
            }
        }
        return null;
    }

    public void process(HttpURLConnection con) throws ExecutionRestException
    {
        try
        {
            Charset charset = Charset.defaultCharset();
            String contentType = con.getHeaderField("Content-Type");
            if (contentType != null)
            {
                int pos = contentType.indexOf("charset");
                if (pos != -1)
                {
                    String scharset = contentType.substring(pos + 1);
                    pos = scharset.indexOf("=");
                    if (pos != -1)
                    {
                        scharset = scharset.substring(pos + 1);
                        try
                        {
                            charset = Charset.forName(scharset);
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                    }
                }
            }
            InputStreamReader reader=new InputStreamReader(con.getInputStream(), charset);
            char[] buffer=new char[1024*8];
            StringBuilder sb = new StringBuilder();            
            int read = reader.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = reader.read(buffer);
            }
            reader.close();
            response = new JSONObject(sb.toString());
        }
        catch (Exception e)
        {
            throw new ExecutionRestException(method.getHTTPMethod(), url, e);
        }
    }

    public void setMethod(Method method)
    {
        if (this.method == null)
        {
            this.method = method;
        }
    }

    public void setURL(URL url)
    {
        if (this.url == null)
        {
            this.url = url;
        }
    }

    public void setStatus(int status)
    {
        if (this.status == 0)
        {
            this.status = status;
        }
    }

    public String getMediaType()
    {
        return APPLICATION_JSON;
    }
   private Parameter getDefinition(String name)
    {
        for (Parameter parameter : this.getAllParameters())
        {
            if (parameter.getName().equals(name))
            {
                return parameter;
            }
        }
        for (Parameter parameter : this.method.getAllParameters())
        {
            if (parameter.getName().equals(name))
            {
                return parameter;
            }
        }
        return null;
    }
    private String constructParametersToURL(List<ParameterValue> values) throws RestException
    {
        StringBuilder sb = new StringBuilder();
        for (ParameterValue pvalue : values)
        {
            Parameter definition = getDefinition(pvalue.getName());
            if (definition != null && "query".equals(definition.getStyle()))
            {
                try
                {
                    sb.append(pvalue.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
                    sb.append("&");
                }
                catch (Exception e)
                {
                    log.debug(e);
                    throw new RestException(e);
                }
            }

        }

        for (Parameter parameter : this.parameters)
        {
            if (parameter.isFixed())
            {
                try
                {
                    sb.append(parameter.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
                    sb.append("&");
                }
                catch (Exception e)
                {
                    log.debug(e);
                    throw new RestException(e);
                }
            }
        }

        for (Parameter parameter : this.method.getAllParameters())
        {
            if (parameter.isFixed())
            {
                try
                {
                    sb.append(parameter.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
                    sb.append("&");
                }
                catch (Exception e)
                {
                    log.debug(e);
                    throw new RestException(e);
                }
            }
        }


        /*try
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
        log.debug(e);
        throw new RestException(e);
        }*/
        return sb.toString();
    }

    

    public RepresentationResponse request(List<ParameterValue> values) throws ExecutionRestException, RestException
    {
        checkParameters(values);
        URL _url = this.getMethod().getResource().getPath();
        HttpURLConnection con=null;        
        String _parameters = constructParametersToURL(values);
        try
        {
            _url = new URL(_url.toString() + "?" + _parameters);
            if(this.getMethod().getHTTPMethod()==HTTPMethod.POST || this.getMethod().getHTTPMethod()==HTTPMethod.PUT)
            {
                con = (HttpURLConnection) _url.openConnection();
                con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
                String charset = Charset.defaultCharset().name();
                con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON+ "; charset=" + charset);
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream out = con.getOutputStream();
                out.write("".getBytes());
                out.close();
            }
            else
            {
                URL newurl=new URL(_url.toString()+"?st="+URLEncoder.encode(""));
                con = (HttpURLConnection)newurl.openConnection();
                con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
                String charset = Charset.defaultCharset().name();
                con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON+ "; charset=" + charset);
            }
            return super.processResponse(con);
        }
        catch (Exception ioe)
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), _url, ioe);
        }
    }

    public void addParameter(Parameter parameter)
    {
        this.parameters.add(parameter);
    }

    public JSONObject getJSONResponse()
    {
        return response;
    }

    public RepresentationResponse request(List<ParameterValue> values, Document request) throws ExecutionRestException, RestException
    {
        checkParameters(values);
        URL _url = this.getMethod().getResource().getPath();
        HttpURLConnection con=null;        
        String _parameters = constructParametersToURL(values);
        String charset = Charset.defaultCharset().name();
        String xml=SWBUtils.XML.domToXml(request, charset, true);
        try
        {
            _url = new URL(_url.toString() + "?" + _parameters);
            if(this.getMethod().getHTTPMethod()==HTTPMethod.POST || this.getMethod().getHTTPMethod()==HTTPMethod.PUT)
            {
                con = (HttpURLConnection) _url.openConnection();
                con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
                
                con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON+ "; charset=" + charset);
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream out = con.getOutputStream();
                
                out.write(xml.getBytes());
                out.close();
            }
            else
            {
                URL newurl=new URL(_url.toString()+"?st="+URLEncoder.encode(xml));
                con = (HttpURLConnection)newurl.openConnection();
                con.setRequestMethod(this.getMethod().getHTTPMethod().toString());                
                con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON+ "; charset=" + charset);
            }
            return super.processResponse(con);
        }
        catch (Exception ioe)
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), _url, ioe);
        }
    }
}
