/*
 * WBHttpServletResponseWrapper.java
 *
 * Created on 22 de marzo de 2006, 06:48 PM
 */

package org.semanticwb.servlet;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.ServletOutputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBHttpServletResponseWrapper extends HttpServletResponseWrapper
{
    static Logger log = SWBUtils.getLogger(SWBHttpServletResponseWrapper.class);
    
    ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
    ServletOutputStream sout=new SWBServletOutputStreamImp(bout);
    PrintWriter out = new PrintWriter(bout);    
    private String sendRedirect=null;    
    
    /** Creates a new instance of WBHttpServletResponseWrapper */
    public SWBHttpServletResponseWrapper(HttpServletResponse response)
    {
        super(response);
    }
    
    @Override
    public javax.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
    {
        //return response.getOutputStream();
        log.debug("WBResponse:getOutputStream");
        out.flush();
        return sout;
    }
    
    @Override
    public java.io.PrintWriter getWriter() throws java.io.IOException
    {
        //return response.getWriter();
        log.debug("WBResponse:getWriter");
        return out;
    }    
    
    @Override
    public void flushBuffer() throws java.io.IOException
    {
        //response.flushBuffer();
        log.debug("WBResponse:flushBuffer");
        bout.flush();
    }

    @Override
    public int getBufferSize()
    {
        //return response.getBufferSize();
        log.debug("WBResponse:getBufferSize");
        return bout.size();
    }    
    
    @Override
    public void resetBuffer()
    {
        log.debug("WBResponse:resetBuffer");
        bout.reset();
    }    
    
    @Override
    public void setBufferSize(int param)
    {
        log.debug("WBResponse:setBufferSize:" + param);
        //bout = new ByteArrayOutputStream(param);
    }    
    
    @Override
    public String toString()
    {
        try
        {
            out.flush();
            out.close();
            bout.flush();
        } catch (Exception e)
        {
            log.error(e);
        }
        log.trace("WBResponse:out:" + bout.toString());
        return bout.toString();
    }   
    
    @Override
    public void sendRedirect(String location) throws java.io.IOException
    {
        location=location.replaceAll("&amp;","&");
        log.debug("WBHttpServletResponseWrapper:sendRedirect");
        super.sendRedirect(location);
        sendRedirect=location;
    }
    
    public String getSendRedirect()
    {
        return sendRedirect;
    }
    
    public boolean isSendRedirect()
    {
        return sendRedirect!=null;
    }    
    
}
