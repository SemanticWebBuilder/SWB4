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
 * WBRequest.java
 *
 * Created on 5 de agosto de 2003, 13:49
 */

package org.semanticwb.portal.lib;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * Clase que implementa HttpServletRequest, la cual se utiliza para remplazar el
 * Request por defecto para se enviado al recurso.
 * @author Javier Solis Gonzalez
 */
public class SWBRequest implements javax.servlet.http.HttpServletRequest
{
    
    /** The sess. */
    SWBSession sess;
    
    /** The parms. */
    Hashtable parms = new Hashtable();
    
    /** The attr. */
    Hashtable attr=new Hashtable();
    
    /** The request. */
    javax.servlet.http.HttpServletRequest request=null;

    /**
     * Creates a new instance of WBRequest.
     */
    public SWBRequest()
    {
        sess = new SWBSession();
    }
    
    /**
     * Instantiates a new sWB request.
     * 
     * @param request the request
     */
    public SWBRequest(javax.servlet.http.HttpServletRequest request)
    {
        this.request=request;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
     */
    public Object getAttribute(String str)
    {
        if(request==null)
        {
            return attr.get(str);
        }
        return request.getAttribute(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getAttributeNames()
     */
    public java.util.Enumeration getAttributeNames()
    {
        if(request==null)
        {
            return attr.keys();
        }
        return request.getAttributeNames();//(new Vector()).elements();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getAuthType()
     */
    public String getAuthType()
    {
        if(request==null)return null;
        return request.getAuthType();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getCharacterEncoding()
     */
    public String getCharacterEncoding()
    {
        if(request==null)return null;
        return request.getCharacterEncoding();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getContentLength()
     */
    public int getContentLength()
    {
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getContentType()
     */
    public String getContentType()
    {
        if(request==null)return "text/html";
        return request.getContentType();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getContextPath()
     */
    public String getContextPath()
    {
        if(request==null)return null;
        return request.getContextPath();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getCookies()
     */
    public javax.servlet.http.Cookie[] getCookies()
    {
        if(request==null)return null;
        return request.getCookies();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
     */
    public long getDateHeader(String str)
    {
        return new Date().getTime();//request.getDateHeader(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
     */
    public String getHeader(String str)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
     */
    public java.util.Enumeration getHeaderNames()
    {
        return new Vector().elements();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
     */
    public java.util.Enumeration getHeaders(String str)
    {
        return new Vector().elements();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getInputStream()
     */
    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
     */
    public int getIntHeader(String str)
    {
        if(request==null)
            return -1;
        else 
            return request.getIntHeader(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getLocale()
     */
    public java.util.Locale getLocale()
    {
        if(request==null)return Locale.getDefault();
        return request.getLocale();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getLocales()
     */
    public java.util.Enumeration getLocales()
    {
        if(request==null)return new Vector().elements();
        return request.getLocales();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getMethod()
     */
    public String getMethod()
    {
        if(request==null)return "GET";
        return request.getMethod();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
     */
    public String getParameter(String str)
    {
        String ret=null;
        String [] arr=(String[])parms.get(str);
        if(arr!=null)ret=arr[0];
        return ret;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterMap()
     */
    public java.util.Map getParameterMap()
    {
        return parms;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterNames()
     */
    public java.util.Enumeration getParameterNames()
    {
        return parms.keys();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String str)
    {
        return (String[])parms.get(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getPathInfo()
     */
    public String getPathInfo()
    {
        if(request==null)return null;
        return request.getPathInfo();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
     */
    public String getPathTranslated()
    {
        if(request==null)return null;
        return request.getPathTranslated();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getProtocol()
     */
    public String getProtocol()
    {
        if(request==null)return null;
        return request.getProtocol();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getQueryString()
     */
    public String getQueryString()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getReader()
     */
    public java.io.BufferedReader getReader() throws java.io.IOException
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
     */
    public String getRealPath(String str)
    {
        if(request==null)return null;
        return request.getRealPath(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getRemoteAddr()
     */
    public String getRemoteAddr()
    {
        if(request==null)return null;
        return request.getRemoteAddr();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getRemoteHost()
     */
    public String getRemoteHost()
    {
        if(request==null)return null;
        return request.getRemoteHost();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
     */
    public String getRemoteUser()
    {
        if(request==null)return null;
        return request.getRemoteUser();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
     */
    public javax.servlet.RequestDispatcher getRequestDispatcher(String str)
    {
        if(request==null)return null;
        return request.getRequestDispatcher(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getRequestURI()
     */
    public String getRequestURI()
    {
        if(request==null)return null;
        return request.getRequestURI();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getRequestURL()
     */
    public StringBuffer getRequestURL()
    {
        if(request==null)return null;
        return request.getRequestURL();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
     */
    public String getRequestedSessionId()
    {
        if(request==null)sess.getId();
        return request.getRequestedSessionId();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getScheme()
     */
    public String getScheme()
    {
        if(request==null)return null;
        return request.getScheme();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getServerName()
     */
    public String getServerName()
    {
        if(request==null)return null;
        return request.getServerName();//com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/distributor");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getServerPort()
     */
    public int getServerPort()
    {
        if(request==null)return 0;
        return request.getServerPort();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getServletPath()
     */
    public String getServletPath()
    {
        if(request==null)return null;
        return request.getServletPath();//WBUtils.getInstance().getWebPath();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getSession()
     */
    public javax.servlet.http.HttpSession getSession()
    {
        if(request==null)return sess;
        return request.getSession();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
     */
    public javax.servlet.http.HttpSession getSession(boolean param)
    {
        if(request==null)return sess;
        return request.getSession(param);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
     */
    public java.security.Principal getUserPrincipal()
    {
        if(request==null)return null;
        return request.getUserPrincipal();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
     */
    public boolean isRequestedSessionIdFromCookie()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdFromCookie();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
     */
    public boolean isRequestedSessionIdFromURL()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdFromURL();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
     */
    public boolean isRequestedSessionIdFromUrl()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdFromUrl();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
     */
    public boolean isRequestedSessionIdValid()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdValid();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#isSecure()
     */
    public boolean isSecure()
    {
        if(request==null)return false;
        return request.isSecure();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
     */
    public boolean isUserInRole(String str)
    {
        if(request==null)return false;
        return request.isUserInRole(str);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String str)
    {
        if(request==null)
        {
            attr.remove(str);
        }else
        {
            request.removeAttribute(str);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String str, Object obj)
    {
        if(request==null)
        {
            attr.put(str, obj);
        }else
        {
            request.setAttribute(str,obj);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
     */
    public void setCharacterEncoding(String str) throws java.io.UnsupportedEncodingException
    {
        if(request==null)return;
        request.setCharacterEncoding(str);
    }

    /**
     * Adds the parameter.
     * 
     * @param name the name
     * @param value the value
     */
    public void addParameter(String name, String value)
    {
        Object obj=parms.get(name);
        if(obj == null)
        {
            parms.put(name, new String[]{value});
        }else if(obj instanceof String[])
        {
            String arr[]=(String [])obj;
            String fin[]=new String[arr.length+1];
            int x=0;
            for(;x<arr.length;x++)
            {
                fin[x]=arr[x];
            }
            fin[x]=value;
            parms.put(name, fin);
        }
    }

}
