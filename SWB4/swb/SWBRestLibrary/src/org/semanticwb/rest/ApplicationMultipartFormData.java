/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public final class ApplicationMultipartFormData extends RepresentationBase implements RepresentationRequest {

    private static String boundary = "gc0p4Jq0M2Yt08jU534c0p";
    public ApplicationMultipartFormData(Method method)
    {
        super("multipart/form-data",method);
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
            con.setRequestProperty(CONTENT_TYPE, this.mediaType);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out = sendHeaders(con);
            for(ParameterValue value : values)
            {
                sendPart(value, out);
            }
            writeEnd(out);
            out.close();
            if(con.getResponseCode()==200)
            {
                if(con.getHeaderField(CONTENT_TYPE)!=null && (con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML) || con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(TEXT_XML)))
                {
                    InputStream in=con.getInputStream();
                    Document response=SWBUtils.XML.xmlToDom(in);
                    if(response==null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    ApplicationXML resp=new ApplicationXML(response,this.getMethod(),con.getResponseCode());
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
                    AtomXML resp = new AtomXML(this.method,con.getResponseCode());
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
