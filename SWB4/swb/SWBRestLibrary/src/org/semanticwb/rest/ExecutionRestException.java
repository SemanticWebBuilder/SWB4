/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;

/**
 *
 * @author victor.lorenzana
 */
public class ExecutionRestException extends RestException{

    private final HTTPMethod method;
    private final URL url;

    public ExecutionRestException(HTTPMethod method,URL url,String msg)
    {
        super(msg);
        this.method=method;
        this.url=url;
    }

    public ExecutionRestException(HTTPMethod method,URL url,Throwable e)
    {
        super(e);
        this.method=method;
        this.url=url;
    }
    public ExecutionRestException(HTTPMethod method,URL url,String msg,Throwable e)
    {
        super(msg,e);
        this.method=method;
        this.url=url;
    }

    public URL getURL()
    {
        return url;
    }
    public HTTPMethod getMethod()
    {
        return method;
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
        s.println("\tError executing the method: "+method);
        s.println("\tURL: "+url);
    }

    @Override
    public void printStackTrace(PrintWriter s)
    {
        
        super.printStackTrace(s);
        s.println("\tError executing the method: "+method);
        s.println("\tURL: "+url);
    }



}
