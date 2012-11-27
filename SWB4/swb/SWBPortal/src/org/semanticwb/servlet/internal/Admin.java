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
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.util.JarFile;

// TODO: Auto-generated Javadoc
/**
 * The Class Admin.
 * 
 * @author Jei
 */
public class Admin implements InternalServlet
{
    
    /** The log. */
    static Logger log=SWBUtils.getLogger(Admin.class);
    
    /** The date formats. */
    protected static SimpleDateFormat dateFormats[];
    
    /** The asegurar. */
    boolean asegurar=false;

    /** The agzip. */
    boolean agzip = true;
    
    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext scontext) 
    {
        try
        {
            asegurar = SWBPlatform.getEnv("swb/secureAdmin","false").equalsIgnoreCase("true");
            agzip = SWBPlatform.getEnv("swb/responseGZIPEncoding", "true").equalsIgnoreCase("true");
        } catch (Exception e)
        {
            log.error("Can't find init variables asuming defaults...",e);
        }
    }
    
    static 
    {
        dateFormats = (new SimpleDateFormat[] {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
            , new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US)
            , new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
        });
        TimeZone tz = TimeZone.getTimeZone("GMT");
        dateFormats[0].setTimeZone(tz);
        dateFormats[1].setTimeZone(tz);
        dateFormats[2].setTimeZone(tz);
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams)throws IOException 
    {
        String acont=request.getContextPath();
        String uri=request.getRequestURI();
        int lcont=0;
        if(acont!=null)
        {
            lcont=acont.length();
            if(lcont==1)lcont=0;
        }
        String path=uri.substring(lcont);
        path=SWBUtils.IO.normalizePath(path);
        
        if(path == null || path.toUpperCase().startsWith("/WEB-INF") || path.toUpperCase().startsWith("/META-INF"))
        {
            response.sendError(404, path);
            return;
        }else if(path !=null && (path.equals("/swbadmin")||path.equals("/swbadmin/")))
        {
            WebPage aux=SWBContext.getAdminWebSite().getHomePage();
            if (asegurar)
                response.sendRedirect("https://"+request.getServerName()+SWBPlatform.getContextPath()+"/login/"+aux.getWebSiteId()+"/"+aux.getId());
            else
                response.sendRedirect(SWBPlatform.getContextPath()+"/login/"+aux.getWebSite().getId()+"/"+aux.getId());
            return;
        }
        
        JarFile file = SWBPortal.getAdminFile(path);
        if(!file.exists())
        {
            response.sendError(404, path);
            return;
        }
        
        if(!file.isDirectory() && path.endsWith("/"))
        {
            response.sendError(404, path);
            return;
        }
        
        if(file.isDirectory())
        {
            //is directory
        } else
        {
            if(!checkIfHeaders(request, response, file))return;
        }
        
        String contentType = request.getSession().getServletContext().getMimeType(path);
        if(contentType==null)contentType="bin/application";
        if(file.isDirectory())
        {
            //if(!listings)
            {
                response.sendError(404, path);
                return;
            }
            //contentType = "text/html;charset=UTF-8";
        } else
        {
            response.setHeader("ETag", getETag(file));
            response.setDateHeader("Last-Modified", file.lastModified());
        }
        
        response.setContentType(contentType);

        boolean gzip = false;
        if (agzip)
        {
            if (request.getHeader("Via") != null
                    || request.getHeader("X-Forwarded-For") != null
                    //|| request.getHeader("Cache-Control") != null
                )
            {
                //using proxy -> no zip
            } else {
                String accept = request.getHeader("Accept-Encoding");
                if (accept != null && accept.toLowerCase().indexOf("gzip") != -1) {
                    gzip = true;
                }
            }
        }

        java.util.zip.GZIPOutputStream garr = null;
        OutputStream out=null;

        if (gzip && contentType.indexOf("text")>-1) {
            response.setHeader("Content-Encoding", "gzip");
            out = new java.util.zip.GZIPOutputStream(response.getOutputStream());
        } else {
            response.setContentLength((int)file.length());
            out = response.getOutputStream();
        }
        
        try
        {
            response.setBufferSize(SWBUtils.IO.getBufferSize());
        }catch(Exception noe){}

        if(file.hasCache())
        {
            out.write(file.getCache());
            out.flush();
            out.close();
        }else
        {
            SWBUtils.IO.copyStream(file.getInputStream(), out);
        }
        //out.close();
        //System.out.println("Sent:"+path);
    }
    
    
    /**
     * Check if headers.
     * 
     * @param request the request
     * @param response the response
     * @param file the file
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected boolean checkIfHeaders(HttpServletRequest request, HttpServletResponse response, JarFile file)
        throws IOException
    {
        String eTag = getETag(file);
        long fileLength = file.length();
        long lastModified = file.lastModified();
        String headerValue = request.getHeader("If-Match");
        if(headerValue != null && headerValue.indexOf('*') == -1)
        {
            StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
            boolean conditionSatisfied;
            for(conditionSatisfied = false; !conditionSatisfied && commaTokenizer.hasMoreTokens();)
            {
                String currentToken = commaTokenizer.nextToken();
                if(currentToken.trim().equals(eTag))
                    conditionSatisfied = true;
            }

            if(!conditionSatisfied)
            {
                response.sendError(412);
                return false;
            }
        }
        headerValue = request.getHeader("If-Modified-Since");
        if(headerValue != null && request.getHeader("If-None-Match") == null)
        {
            Date date = null;
            for(int i = 0; date == null && i < dateFormats.length; i++)
                try
                {
                    date = dateFormats[i].parse(headerValue);
                }
                catch(Exception e) { }
            if(date != null && lastModified <= date.getTime() + 1000L)
            {
                response.setStatus(304);
                return false;
            }
        }
        headerValue = request.getHeader("If-None-Match");
        if(headerValue != null)
        {
            boolean conditionSatisfied = false;
            if(!headerValue.equals("*"))
            {
                for(StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ","); !conditionSatisfied && commaTokenizer.hasMoreTokens();)
                {
                    String currentToken = commaTokenizer.nextToken();
                    if(currentToken.trim().equals(eTag))
                        conditionSatisfied = true;
                }

            } else
            {
                conditionSatisfied = true;
            }
            if(conditionSatisfied)
                if("GET".equals(request.getMethod()) || "HEAD".equals(request.getMethod()))
                {
                    response.setStatus(304);
                    return false;
                } else
                {
                    response.sendError(412);
                    return false;
                }
        }
        headerValue = request.getHeader("If-Unmodified-Since");
        if(headerValue != null)
        {
            Date date = null;
            for(int i = 0; date == null && i < dateFormats.length; i++)
                try
                {
                    date = dateFormats[i].parse(headerValue);
                }
                catch(java.text.ParseException e) { }

            if(date != null && lastModified > date.getTime())
            {
                response.sendError(412);
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the e tag.
     * 
     * @param file the file
     * @return the e tag
     */
    protected String getETag(JarFile file)
    {
        return "\"" + file.length() + "-" + file.lastModified() + "\"";
    }        


}
