/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

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
            InputStream in = con.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1028];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read, charset));
                read = in.read(buffer);
            }
            in.close();
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
   

    private String constructParameters(List<ParameterValue> values) throws RestException
    {
        StringBuilder sb = new StringBuilder("{");
        try
        {
            for (Parameter parameter : this.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        String name = parameter.getName();
                        String value = pvalue.getValue().toString();
                        sb.append("\"");
                        sb.append(name);
                        sb.append("\":");
                        sb.append(value);
                        sb.append(",");
                    }
                }
            }
            for (Parameter parameter : this.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        String name = parameter.getName();
                        String value = pvalue.getValue().toString();
                        sb.append("\"");
                        sb.append(name);
                        sb.append("\":");
                        sb.append(value);
                        sb.append(",");
                    }
                }
            }
            for (Parameter parameter : this.parameters)
            {
                if (parameter.isFixed())
                {
                    String name = parameter.getName();
                    String value = parameter.getFixedValue();
                    sb.append("\"");
                    sb.append(name);
                    sb.append("\":");
                    sb.append(value);
                    sb.append(",");

                }
            }


            for (Parameter parameter : this.method.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        String name = parameter.getName();
                        String value = pvalue.getValue().toString();
                        sb.append("\"");
                        sb.append(name);
                        sb.append("\":");
                        sb.append(value);
                        sb.append(",");
                    }
                }
            }
            for (Parameter parameter : this.method.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        String name = parameter.getName();
                        String value = pvalue.getValue().toString();
                        sb.append("\"");
                        sb.append(name);
                        sb.append("\":");
                        sb.append(value);
                        sb.append(",");
                    }
                }
            }
            for (Parameter parameter : this.method.getAllParameters())
            {
                if (parameter.isFixed())
                {
                    String name = parameter.getName();
                    String value = parameter.getFixedValue();
                    sb.append("\"");
                    sb.append(name);
                    sb.append("\":");
                    sb.append(",");
                    sb.append(value);
                }
            }

        }
        catch (Exception e)
        {
            log.debug(e);
            throw new RestException(e);
        }
        String valueToReturn=sb.toString().trim();
        if(valueToReturn.endsWith(","))
        {
            valueToReturn=valueToReturn.substring(0,valueToReturn.length()-2);
        }
        valueToReturn+="}";
        return valueToReturn;
    }

    public RepresentationResponse request(List<ParameterValue> values) throws ExecutionRestException, RestException
    {
        checkParameters(values);
        URL _url = this.getMethod().getResource().getPath();
        try
        {
            HttpURLConnection con = (HttpURLConnection) _url.openConnection();
            con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
            String charset = Charset.defaultCharset().name();
            con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON+ "; charset=" + charset);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out = con.getOutputStream();
            String requestAtomDocument = constructParameters(values);            
            out.write(requestAtomDocument.getBytes());
            out.close();
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
}
