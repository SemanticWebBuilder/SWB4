/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 * @author victor.lorenzana
 */
public final class MultipartFormData extends RepresentationBase implements RepresentationRequest {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    private static String boundary = "gc0p4Jq0M2Yt08jU534c0p";
    public MultipartFormData()
    {
        
    }
    private static OutputStream sendHeaders(HttpURLConnection connection) throws IOException
    {
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        OutputStream out = connection.getOutputStream();
        return out;
    }
    private static void writeEnd(OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        out.write(newBoundary.getBytes());
    }
    private static void sendPart(ParameterValue value,OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"" + value.getName() + "\"; \r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        byte[] buffer=value.getValue().toString().getBytes();
        out.write(buffer, 0, buffer.length);
    }
    public RepresentationResponse request(List<ParameterValue> values) throws RestException
    {
        checkParameters(values);
        URL url=this.getMethod().getResource().getPath();        
        try
        {            
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
            con.setRequestProperty(CONTENT_TYPE, MULTIPART_FORM_DATA);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out = sendHeaders(con);
            for(ParameterValue value : values)
            {
                sendPart(value, out);
            }
            writeEnd(out);
            out.close();            
            return processResponse(con);            
        }
        catch(Exception ioe)
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), url, ioe);
        }
    }

    public void setMethod(Method method)
    {
        this.method=method;
    }

    public String getMediaType()
    {
        return MULTIPART_FORM_DATA;
    }

    public void addParameter(Parameter parameter)
    {
        this.parameters.add(parameter);
    }
}
