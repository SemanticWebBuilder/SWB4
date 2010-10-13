/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author victor.lorenzana
 */
public class ServerSideExecutionRestException extends ExecutionRestException {

    private static final String DETAIL = "Detail:";    
    private static final String NL = "\r\n";
    private String errordetail=null;
    private String errormessage=null;
    private int errorCode=-1;
    private static final String CONTENT_TYPE = "Content-Type";
    public ServerSideExecutionRestException(HTTPMethod method,URL url,HttpURLConnection con)
    {
        super(method,url,"ServerSide error executing a REST Service");
        try
        {
            errorCode=con.getResponseCode();
            errormessage=con.getResponseMessage();
        }
        catch(IOException ioe)
        {

        }
        try
        {
            errordetail=loadError(con);
        }
        catch(IOException ioe)
        {
            errordetail=null;
        }
    }
    public String getResponseMessage()
    {
        return errormessage;
    }
    public int getCode()
    {
        return errorCode;
    }
    public String getServerSideErrorDetail()
    {
        return errordetail;
    }
    private String loadError(HttpURLConnection con) throws IOException
    {

        StringBuilder error=new StringBuilder();                
        InputStream in=con.getErrorStream();
        String ct=con.getHeaderField(CONTENT_TYPE);
        if(in!=null && ct!=null && ct.toLowerCase().startsWith("text"))
        {
            String charset=Charset.defaultCharset().name();
            int pos=ct.indexOf(";");
            if(pos!=-1)
            {
                String tempcharset=ct.substring(pos+1).trim();
                try
                {
                    charset=Charset.forName(tempcharset).name();
                }
                catch(IllegalArgumentException e)
                {

                }
            }
            byte[] buffer=new byte[1024*8];
            int read=in.read(buffer);
            while(read!=-1)
            {
                error.append(new String(buffer,0,read,charset));
                read=in.read(buffer);
            }
            in.close();

        }
        return error.toString();
    }
    @Override
    public void printStackTrace()
    {
        this.printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream s)
    {        
        super.printStackTrace(s);
        if(errorCode!=-1)
        {
            s.println("\tServerSide error message: "+errormessage);
            s.println("\tServerSide error code: "+errorCode);
        }
    }

    @Override
    public void printStackTrace(PrintWriter s)
    {        
        super.printStackTrace(s);
        if(errorCode!=-1)
        {
            s.println("\tServerSide error message: "+errormessage);
            s.println("\tServerSide error code: "+errorCode);
        }        
    }
}
