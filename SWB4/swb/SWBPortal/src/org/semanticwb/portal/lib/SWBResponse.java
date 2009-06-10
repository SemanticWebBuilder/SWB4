
/*
 * WBResponse.java
 *
 * Created on 2 de septiembre de 2003, 11:35
 */

package org.semanticwb.portal.lib;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.ServletOutputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


/**
 * Clase que implementa HttpServletResponse, la cual se utiliza para remplazar el
 * Response por defecto para se enviado al recurso.
 * @author  Administrador
 */
public class SWBResponse implements HttpServletResponse
{
    private static Logger log = SWBUtils.getLogger(SWBResponse.class);
    
    private HttpServletResponse response;
    ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
    ServletOutputStream sout=new SWBServletOutputStreamImp(bout);
    PrintWriter out = new PrintWriter(bout);
    private String sendRedirect=null;

    /** Creates a new instance of WBResponse */
    public SWBResponse()
    {
        log.debug("WBResponse:WBResponse()");
        this.response = null;
    }

    /** Creates a new instance of WBResponse */
    public SWBResponse(HttpServletResponse response)
    {
        log.debug("WBResponse:WBResponse()");
        this.response = response;
    }

    public void addCookie(javax.servlet.http.Cookie cookie)
    {
        log.debug("WBResponse:addCookie");
        if(response!=null)
            response.addCookie(cookie);
    }

    public void addDateHeader(String str, long param)
    {
        log.debug("WBResponse:addDateHeader");
        if(response!=null)
            response.addDateHeader(str, param);
    }

    public void addHeader(String str, String str1)
    {
        log.debug("WBResponse:addHeader");
        if(response!=null)
            response.addHeader(str, str1);
    }

    public void addIntHeader(String str, int param)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            response.addIntHeader(str, param);
    }

    public boolean containsHeader(String str)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            return response.containsHeader(str);
        else
            return false;
    }

    public String encodeRedirectURL(String str)
    {
        log.debug("WBResponse:encodeRedirectURL");
        if(response!=null)
            return response.encodeRedirectURL(str);
        return null;
    }

    public String encodeRedirectUrl(String str)
    {
        log.debug("WBResponse:encodeRedirectUrl");
        if(response!=null)
            return response.encodeRedirectUrl(str);
        return null;
    }

    public String encodeURL(String str)
    {
        log.debug("WBResponse:encodeURL");
        if(response!=null)
            return response.encodeURL(str);
        return null;
    }

    public String encodeUrl(String str)
    {
        log.debug("WBResponse:encodeUrl");
        if(response!=null)
            return response.encodeUrl(str);
        return null;
    }

    public void flushBuffer() throws java.io.IOException
    {
        log.debug("WBResponse:flushBuffer");
        bout.flush();
    }

    public int getBufferSize()
    {
        log.debug("WBResponse:getBufferSize");
        return bout.size();
    }

    public String getCharacterEncoding()
    {
        log.debug("WBResponse:getCharacterEncoding");
        if(response!=null)
            return response.getCharacterEncoding();
        return null;
    }

    public java.util.Locale getLocale()
    {
        log.debug("WBResponse:getLocale");
        if(response!=null)
            return response.getLocale();
        return null;
    }

    public javax.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
    {
        log.debug("WBResponse:getOutputStream");
        out.flush();
        return sout;
    }

    public java.io.PrintWriter getWriter() throws java.io.IOException
    {
        log.debug("WBResponse:getWriter");
        return out;
    }

    public boolean isCommitted()
    {
        log.debug("WBResponse:isCommitted");
        return false;
    }

    public void reset()
    {
        log.debug("WBResponse:reset");
        resetBuffer();
    }

    public void resetBuffer()
    {
        log.debug("WBResponse:resetBuffer");
        bout.reset();
    }

    public void sendError(int param) throws java.io.IOException
    {
        log.debug("WBResponse:sendError");
        if(response!=null)
            response.sendError(param);
    }

    public void sendError(int param, String str) throws java.io.IOException
    {
        log.debug("WBResponse:sendError");
        if(response!=null)
            response.sendError(param, str);
    }

    public void sendRedirect(String str) throws java.io.IOException
    {
        sendRedirect=str;
        log.debug("WBResponse:sendRedirect");
        if(response!=null)
            response.sendRedirect(str);
    }
    
    public String getSendRedirect()
    {
        return sendRedirect;
    }
    
    public boolean isSendRedirect()
    {
        return sendRedirect!=null;
    }

    public void setBufferSize(int param)
    {
        log.debug("WBResponse:setBufferSize:" + param);
    }

    public void setContentLength(int param)
    {
        log.debug("WBResponse:setContentLength");
        if(response!=null)
            response.setContentLength(param);
    }

    public void setContentType(String str)
    {
        log.debug("WBResponse:setContentType");
        if(response!=null)
            response.setContentType(str);
    }

    public void setDateHeader(String str, long param)
    {
        log.debug("WBResponse:setDateHeader");
        if(response!=null)
            response.setDateHeader(str, param);
    }

    public void setHeader(String str, String str1)
    {
        log.debug("WBResponse:setHeader");
        if(response!=null)
            response.setHeader(str, str1);
    }

    public void setIntHeader(String str, int param)
    {
        log.debug("WBResponse:setIntHeader");
        if(response!=null)
            response.setIntHeader(str, param);
    }

    public void setLocale(java.util.Locale locale)
    {
        log.debug("WBResponse:setLocale");
        if(response!=null)
            response.setLocale(locale);
    }

    public void setStatus(int param)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            response.setStatus(param);
    }

    public void setStatus(int param, String str)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            response.setStatus(param, str);
    }

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
        log.debug("WBResponse:out:" + bout.toString());
        return bout.toString();//new String(bout.toByteArray());
    }

}
