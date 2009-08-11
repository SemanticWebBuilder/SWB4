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
 * WBHttpServletResponseWrapper.java
 *
 * Created on 22 de marzo de 2006, 06:48 PM
 */
package org.semanticwb.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    ServletOutputStream sout = new SWBServletOutputStreamImp(bout);
    PrintWriter pout = null;
    private String sendRedirect = null;
    private int err = -1;
    private String errMessage = null;
    private boolean trapSendError = false;
    private boolean trapContentType = true;

    private String contentType=null;

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
        if(pout!=null)
        {
            pout.flush();
            String dec=new String(bout.toByteArray(),SWBUtils.TEXT.CHARSET_UTF8);
            bout.reset();
            sout.write(dec.getBytes());
            pout=null;
        }
        return sout;
    }

    @Override
    public java.io.PrintWriter getWriter() throws java.io.IOException
    {
        //return response.getWriter();
        log.debug("WBResponse:getWriter");
        if(pout==null)
        {
            try
            {
                pout = new PrintWriter(new OutputStreamWriter(sout,SWBUtils.TEXT.CHARSET_UTF8));
                //pout = new PrintWriter(new OutputStreamWriter(sout));
            }catch(Exception e){log.error(e);}
        }
        return pout;
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
            if(pout!=null)
            {
                pout.flush();
                pout.close();
            }
            bout.flush();
        } catch (Exception e)
        {
            log.error(e);
        }
        String ret="";
        try
        {
            if(pout!=null)
            {
                ret=new String(bout.toByteArray(),SWBUtils.TEXT.CHARSET_UTF8);
                //ret=SWBUtils.TEXT.decode(ret,SWBUtils.TEXT.CHARSET_UTF8);
            }else
            {
                ret=bout.toString();
            }
        }catch(Exception e){log.error(e);}
        //log.trace("WBResponse:out:" + ret);
        return ret;
    }

    public byte[] toByteArray()
    {
        try
        {
            if(pout!=null)
            {
                pout.flush();
                pout.close();
            }
            bout.flush();
        } catch (Exception e)
        {
            log.error(e);
        }
        byte arr[]=bout.toByteArray();
        try
        {
            if(pout!=null)
            {
                arr=new String(arr, SWBUtils.TEXT.CHARSET_UTF8).getBytes();
                //ret=SWBUtils.TEXT.decode(ret,SWBUtils.TEXT.CHARSET_UTF8);
            }
        }catch(Exception e){log.error(e);}

        return arr;
    }

    @Override
    public void sendRedirect(String location) throws java.io.IOException
    {
        location = location.replaceAll("&amp;", "&");
        log.debug("WBHttpServletResponseWrapper:sendRedirect");
        super.sendRedirect(location);
        sendRedirect = location;
    }

    public String getSendRedirect()
    {
        return sendRedirect;
    }

    public boolean isSendRedirect()
    {
        return sendRedirect != null;
    }

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

    public boolean isSendError()
    {
        return err != -1;
    }

    public int getError()
    {
        return err;
    }

    public String getErrorMsg()
    {
        return errMessage;
    }

    public void setTrapSendError(boolean trapSendError)
    {
        this.trapSendError = trapSendError;
    }

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

    public String getContentType()
    {
        return contentType;
    }

    public void setTrapContentType(boolean trapContentType)
    {
        this.trapContentType = trapContentType;
    }

    public boolean getTrapContentType()
    {
        return this.trapContentType;
    }

}
