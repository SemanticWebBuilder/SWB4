/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.util.JarFile;

/**
 *
 * @author Jei
 */
public class Admin implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Login.class);
    
    protected static SimpleDateFormat dateFormats[];
    boolean asegurar=false;
    
    public void init(ServletContext scontext) 
    {
        try
        {
            asegurar = SWBPlatform.getEnv("swb/secureAdmin","false").equalsIgnoreCase("true");
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
        }else if(path !=null && path.equals("/swbadmin"))
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
        
        OutputStream out=response.getOutputStream();
        response.setContentType(contentType);
        response.setContentLength((int)file.length());        
        
        try
        {
            response.setBufferSize(SWBUtils.IO.getBufferSize());
        }catch(Exception noe){}
        
        
        SWBUtils.IO.copyStream(file.getInputStream(), out);
        //out.close();
        //System.out.println("Sent:"+path);
    }
    
    
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
                catch(java.text.ParseException e) { }

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

    protected String getETag(JarFile file)
    {
        return "\"" + file.length() + "-" + file.lastModified() + "\"";
    }        


}
