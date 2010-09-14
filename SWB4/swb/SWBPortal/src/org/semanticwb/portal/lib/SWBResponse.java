/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 

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


// TODO: Auto-generated Javadoc
/**
 * Clase que implementa HttpServletResponse, la cual se utiliza para remplazar el
 * Response por defecto para se enviado al recurso.
 * @author  Administrador
 */
public class SWBResponse implements HttpServletResponse
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBResponse.class);
    
    /** The response. */
    private HttpServletResponse response;
    
    /** The bout. */
    ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
    
    /** The sout. */
    ServletOutputStream sout=new SWBServletOutputStreamImp(bout);
    
    /** The out. */
    PrintWriter out = new PrintWriter(bout);
    
    /** The send redirect. */
    private String sendRedirect=null;

    /**
     * Creates a new instance of WBResponse.
     */
    public SWBResponse()
    {
        log.debug("WBResponse:WBResponse()");
        this.response = null;
    }

    /**
     * Creates a new instance of WBResponse.
     * 
     * @param response the response
     */
    public SWBResponse(HttpServletResponse response)
    {
        log.debug("WBResponse:WBResponse()");
        this.response = response;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
     */
    /**
     * Adds the cookie.
     * 
     * @param cookie the cookie
     */
    public void addCookie(javax.servlet.http.Cookie cookie)
    {
        log.debug("WBResponse:addCookie");
        if(response!=null)
            response.addCookie(cookie);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
     */
    /**
     * Adds the date header.
     * 
     * @param str the str
     * @param param the param
     */
    public void addDateHeader(String str, long param)
    {
        log.debug("WBResponse:addDateHeader");
        if(response!=null)
            response.addDateHeader(str, param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
     */
    /**
     * Adds the header.
     * 
     * @param str the str
     * @param str1 the str1
     */
    public void addHeader(String str, String str1)
    {
        log.debug("WBResponse:addHeader");
        if(response!=null)
            response.addHeader(str, str1);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
     */
    /**
     * Adds the int header.
     * 
     * @param str the str
     * @param param the param
     */
    public void addIntHeader(String str, int param)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            response.addIntHeader(str, param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
     */
    /**
     * Contains header.
     * 
     * @param str the str
     * @return true, if successful
     */
    public boolean containsHeader(String str)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            return response.containsHeader(str);
        else
            return false;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String)
     */
    /**
     * Encode redirect url.
     * 
     * @param str the str
     * @return the string
     */
    public String encodeRedirectURL(String str)
    {
        log.debug("WBResponse:encodeRedirectURL");
        if(response!=null)
            return response.encodeRedirectURL(str);
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String)
     */
    /**
     * Encode redirect url.
     * 
     * @param str the str
     * @return the string
     */
    public String encodeRedirectUrl(String str)
    {
        log.debug("WBResponse:encodeRedirectUrl");
        if(response!=null)
            return response.encodeRedirectUrl(str);
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
     */
    /**
     * Encode url.
     * 
     * @param str the str
     * @return the string
     */
    public String encodeURL(String str)
    {
        log.debug("WBResponse:encodeURL");
        if(response!=null)
            return response.encodeURL(str);
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
     */
    /**
     * Encode url.
     * 
     * @param str the str
     * @return the string
     */
    public String encodeUrl(String str)
    {
        log.debug("WBResponse:encodeUrl");
        if(response!=null)
            return response.encodeUrl(str);
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#flushBuffer()
     */
    /**
     * Flush buffer.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void flushBuffer() throws java.io.IOException
    {
        log.debug("WBResponse:flushBuffer");
        bout.flush();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getBufferSize()
     */
    /**
     * Gets the buffer size.
     * 
     * @return the buffer size
     */
    public int getBufferSize()
    {
        log.debug("WBResponse:getBufferSize");
        return bout.size();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getCharacterEncoding()
     */
    /**
     * Gets the character encoding.
     * 
     * @return the character encoding
     */
    public String getCharacterEncoding()
    {
        log.debug("WBResponse:getCharacterEncoding");
        if(response!=null)
            return response.getCharacterEncoding();
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getLocale()
     */
    /**
     * Gets the locale.
     * 
     * @return the locale
     */
    public java.util.Locale getLocale()
    {
        log.debug("WBResponse:getLocale");
        if(response!=null)
            return response.getLocale();
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getOutputStream()
     */
    /**
     * Gets the output stream.
     * 
     * @return the output stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public javax.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
    {
        log.debug("WBResponse:getOutputStream");
        out.flush();
        return sout;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getWriter()
     */
    /**
     * Gets the writer.
     * 
     * @return the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public java.io.PrintWriter getWriter() throws java.io.IOException
    {
        log.debug("WBResponse:getWriter");
        return out;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#isCommitted()
     */
    /**
     * Checks if is committed.
     * 
     * @return true, if is committed
     */
    public boolean isCommitted()
    {
        log.debug("WBResponse:isCommitted");
        return false;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#reset()
     */
    /**
     * Reset.
     */
    public void reset()
    {
        log.debug("WBResponse:reset");
        resetBuffer();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#resetBuffer()
     */
    /**
     * Reset buffer.
     */
    public void resetBuffer()
    {
        log.debug("WBResponse:resetBuffer");
        bout.reset();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#sendError(int)
     */
    /**
     * Send error.
     * 
     * @param param the param
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void sendError(int param) throws java.io.IOException
    {
        log.debug("WBResponse:sendError");
        if(response!=null)
            response.sendError(param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
     */
    /**
     * Send error.
     * 
     * @param param the param
     * @param str the str
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void sendError(int param, String str) throws java.io.IOException
    {
        log.debug("WBResponse:sendError");
        if(response!=null)
            response.sendError(param, str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
     */
    /**
     * Send redirect.
     * 
     * @param str the str
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void sendRedirect(String str) throws java.io.IOException
    {
        sendRedirect=str;
        log.debug("WBResponse:sendRedirect");
        if(response!=null)
            response.sendRedirect(str);
    }
    
    /**
     * Gets the send redirect.
     * 
     * @return the send redirect
     */
    public String getSendRedirect()
    {
        return sendRedirect;
    }
    
    /**
     * Checks if is send redirect.
     * 
     * @return true, if is send redirect
     */
    public boolean isSendRedirect()
    {
        return sendRedirect!=null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setBufferSize(int)
     */
    /**
     * Sets the buffer size.
     * 
     * @param param the new buffer size
     */
    public void setBufferSize(int param)
    {
        log.debug("WBResponse:setBufferSize:" + param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setContentLength(int)
     */
    /**
     * Sets the content length.
     * 
     * @param param the new content length
     */
    public void setContentLength(int param)
    {
        log.debug("WBResponse:setContentLength");
        if(response!=null)
            response.setContentLength(param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setContentType(java.lang.String)
     */
    /**
     * Sets the content type.
     * 
     * @param str the new content type
     */
    public void setContentType(String str)
    {
        log.debug("WBResponse:setContentType");
        if(response!=null)
            response.setContentType(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
     */
    /**
     * Sets the date header.
     * 
     * @param str the str
     * @param param the param
     */
    public void setDateHeader(String str, long param)
    {
        log.debug("WBResponse:setDateHeader");
        if(response!=null)
            response.setDateHeader(str, param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
     */
    /**
     * Sets the header.
     * 
     * @param str the str
     * @param str1 the str1
     */
    public void setHeader(String str, String str1)
    {
        log.debug("WBResponse:setHeader");
        if(response!=null)
            response.setHeader(str, str1);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
     */
    /**
     * Sets the int header.
     * 
     * @param str the str
     * @param param the param
     */
    public void setIntHeader(String str, int param)
    {
        log.debug("WBResponse:setIntHeader");
        if(response!=null)
            response.setIntHeader(str, param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
     */
    /**
     * Sets the locale.
     * 
     * @param locale the new locale
     */
    public void setLocale(java.util.Locale locale)
    {
        log.debug("WBResponse:setLocale");
        if(response!=null)
            response.setLocale(locale);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setStatus(int)
     */
    /**
     * Sets the status.
     * 
     * @param param the new status
     */
    public void setStatus(int param)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            response.setStatus(param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
     */
    /**
     * Sets the status.
     * 
     * @param param the param
     * @param str the str
     */
    public void setStatus(int param, String str)
    {
        log.debug("WBResponse:setStatus");
        if(response!=null)
            response.setStatus(param, str);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
