/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class JSON implements RepresentationResponse {

    public static final String APPLICATION_JSON="application/json";
    private static final Logger log = SWBUtils.getLogger(JSON.class);
    private Method method;
    private int status;
    private URL url;
    private JSONObject response;
    public Object getResponse() throws RestException
    {
        return response;
    }
    public Object[] getValues(ParameterDefinition definition) throws RestException
    {
        return null;
    }
    public Object getValue(ParameterDefinition definition) throws RestException
    {
        Object[] values = getValues(definition);
        if (values.length > 0)
        {
            return values[0];
        }
        return null;
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
            Charset charset=Charset.defaultCharset();
            String contentType=con.getHeaderField("Content-Type");
            if(contentType!=null)
            {
                int pos=contentType.indexOf("charset");
                if(pos!=-1)
                {
                    String scharset=contentType.substring(pos+1);
                    pos=scharset.indexOf("=");
                    if(pos!=-1)
                    {
                        scharset=scharset.substring(pos+1);
                        try
                        {
                            charset=Charset.forName(scharset);
                        }
                        catch(Exception e)
                        {
                            log.debug(e);
                        }
                    }
                }
            }
            InputStream in=con.getInputStream();
            StringBuilder sb=new StringBuilder();
            byte[] buffer=new byte[1028];
            int read=in.read(buffer);
            while(read!=-1)
            {
                sb.append(new String(buffer,0,read,charset));
                read=in.read(buffer);
            }
            in.close();
            response=new JSONObject(sb.toString());
        }
        catch(Exception e)
        {
            throw new ExecutionRestException(method.getHTTPMethod(), url, e);
        }
    }

    public void setMethod(Method method)
    {
        if(this.method==null)
        {
            this.method=method;
        }
    }

    public void setURL(URL url)
    {
        if(this.url==null)
        {
            this.url=url;
        }
    }
    public void setStatus(int status)
    {
        if(this.status==0)
        {
            this.status=status;
        }
    }

    public String getMediaType()
    {
        return APPLICATION_JSON;
    }

    public Method getMethod()
    {
        return getMethod();
    }
}
