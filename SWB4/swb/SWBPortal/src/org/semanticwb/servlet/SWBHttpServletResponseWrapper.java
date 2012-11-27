/*
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
 */
package org.semanticwb.servlet;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.ServletOutputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBHttpServletResponseWrapper.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBHttpServletResponseWrapper extends HttpServletResponseWrapper
{

    /** The log. */
    static Logger log = SWBUtils.getLogger(SWBHttpServletResponseWrapper.class);

    /** The bout. */
    ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
    
    /** The sout. */
    ServletOutputStream sout = new SWBServletOutputStreamImp(bout);
    
    CharArrayWriter cout=null;
    
    /** The pout. */
    PrintWriter pout = null;
    
    /** The send redirect. */
    private String sendRedirect = null;
    
    /** The err. */
    private int err = -1;
    
    /** The err message. */
    private String errMessage = null;
    
    /** The trap send error. */
    private boolean trapSendError = false;
    
    /** The trap content type. */
    private boolean trapContentType = true;
        
    private boolean trapResponse = true;

    /** The content type. */
    private String contentType=null;

    /** The charset. */
    private String charset=null;
    

    /**
     * Creates a new instance of WBHttpServletResponseWrapper.
     * 
     * @param response the response
     */
    public SWBHttpServletResponseWrapper(HttpServletResponse response)
    {
        super(response);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getOutputStream()
     */
    /**
     * Gets the output stream.
     * 
     * @return the output stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public javax.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
    {
        if(trapResponse)
        {
            log.debug("WBResponse:getOutputStream");
            if(pout!=null)
            {
                cout.flush();
                bout.write(cout.toString().getBytes());
                pout=null;
            }
            return sout;
        }else
        {
            return super.getOutputStream();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getWriter()
     */
    /**
     * Gets the writer.
     * 
     * @return the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public java.io.PrintWriter getWriter() throws java.io.IOException
    {
        if(trapResponse)
        {        
            log.debug("WBResponse:getWriter");
            if(pout==null)
            {
                try
                {
                    cout=new CharArrayWriter();
                    pout = new PrintWriter(cout);
                }catch(Exception e){log.error(e);}
            }
            return pout;
        }else
        {
            return super.getWriter();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#flushBuffer()
     */
    /**
     * Flush buffer.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void flushBuffer() throws java.io.IOException
    {
        if(trapResponse)
        {        
            log.debug("WBResponse:flushBuffer");
            bout.flush();
        }else
        {
            super.flushBuffer();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getBufferSize()
     */
    /**
     * Gets the buffer size.
     * 
     * @return the buffer size
     */
    @Override
    public int getBufferSize()
    {
        if(trapResponse)
        {        
            log.debug("WBResponse:getBufferSize");
            return bout.size();
        }else
        {
            return super.getBufferSize();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#resetBuffer()
     */
    /**
     * Reset buffer.
     */
    @Override
    public void resetBuffer()
    {
        if(trapResponse)
        {        
            log.debug("WBResponse:resetBuffer");
            bout.reset();
        }else
        {
            super.resetBuffer();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#setBufferSize(int)
     */
    /**
     * Sets the buffer size.
     * 
     * @param param the new buffer size
     */
    @Override
    public void setBufferSize(int param)
    {
        if(trapResponse)
        {        
            log.debug("WBResponse:setBufferSize:" + param);
            //bout = new ByteArrayOutputStream(param);
        }else
        {
            super.setBufferSize(param);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    /**
     * To string.
     * 
     * @return the string
     */
    @Override
    public String toString()
    {
        if(trapResponse)
        {        
            if(pout!=null)
            {
                return cout.toString();
            }else
            {
                return bout.toString();
            }
        }else
        {
            return super.toString();
        }        
    }

    /**
     * To byte array.
     * 
     * @return the byte[]
     */
    public byte[] toByteArray()
    {
        if(pout!=null)
        {
            return cout.toString().getBytes();
        }else
        {
            return bout.toByteArray();
        }        
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#sendRedirect(java.lang.String)
     */
    /**
     * Send redirect.
     * 
     * @param location the location
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void sendRedirect(String location) throws java.io.IOException
    {
        location = location.replaceAll("&amp;", "&");
        log.debug("WBHttpServletResponseWrapper:sendRedirect");
        super.sendRedirect(location);
        sendRedirect = location;
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
        return sendRedirect != null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int)
     */
    /**
     * Send error.
     * 
     * @param err the err
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void sendError(int err) throws IOException
    {
        if (trapSendError)
        {
            sendError(err, null);
        } else
        {
            super.sendError(err);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int, java.lang.String)
     */
    /**
     * Send error.
     * 
     * @param err the err
     * @param msg the msg
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void sendError(int err, String msg) throws IOException
    {
        if (trapSendError)
        {
            log.debug("WBHttpServletResponseWrapper:Capturing sendError");
            this.err = err;
            this.errMessage = msg;
        } else
        {
            super.sendError(err, msg);
        }

    }

    /**
     * Checks if is send error.
     * 
     * @return true, if is send error
     */
    public boolean isSendError()
    {
        return err != -1;
    }

    /**
     * Gets the error.
     * 
     * @return the error
     */
    public int getError()
    {
        return err;
    }

    /**
     * Gets the error msg.
     * 
     * @return the error msg
     */
    public String getErrorMsg()
    {
        return errMessage;
    }

    /**
     * Sets the trap send error.
     * 
     * @param trapSendError the new trap send error
     */
    public void setTrapSendError(boolean trapSendError)
    {
        this.trapSendError = trapSendError;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#setContentType(java.lang.String)
     */
    /**
     * Sets the content type.
     * 
     * @param type the new content type
     */
    @Override
    public void setContentType(String type) 
    {
        log.debug("contentType:"+type+" trapContentType:"+trapContentType);
        contentType=type;
        if(!trapContentType)
        {
            super.setContentType(type);
        }
    }

    @Override
    public void setCharacterEncoding(String charset) 
    {
        log.debug("charset:"+charset);
        charset=charset;
        if(!trapContentType)
        {
            super.setCharacterEncoding(charset);
        }        
    }

    @Override
    public String getCharacterEncoding() 
    {
        if(!trapContentType)
        {
            return super.getCharacterEncoding();
        }else
        {
            return charset;
        }
    }

    /**
     * Gets the content type.
     * 
     * @return the content type
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * Sets the trap content type.
     * 
     * @param trapContentType the new trap content type
     */
    public void setTrapContentType(boolean trapContentType)
    {
        this.trapContentType = trapContentType;
    }

    /**
     * Gets the trap content type.
     * 
     * @return the trap content type
     */
    public boolean getTrapContentType()
    {
        return this.trapContentType;
    }

    public void setTrapResponse(boolean trapResponse) {
        this.trapResponse = trapResponse;
    }

    public boolean isTrapResponse() {
        return trapResponse;
    }

}
