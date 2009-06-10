/*
 * WBRequest.java
 *
 * Created on 5 de agosto de 2003, 13:49
 */

package org.semanticwb.portal.lib;

import java.util.*;

/**
 * Clase que implementa HttpServletRequest, la cual se utiliza para remplazar el
 * Request por defecto para se enviado al recurso.
 * @author Javier Solis Gonzalez
 */
public class SWBRequest implements javax.servlet.http.HttpServletRequest
{
    SWBSession sess;
    Hashtable parms = new Hashtable();
    Hashtable attr=new Hashtable();
    
    javax.servlet.http.HttpServletRequest request=null;

    /** Creates a new instance of WBRequest */
    public SWBRequest()
    {
        sess = new SWBSession();
    }
    
    public SWBRequest(javax.servlet.http.HttpServletRequest request)
    {
        this.request=request;
    }

    public Object getAttribute(String str)
    {
        if(request==null)
        {
            return attr.get(str);
        }
        return request.getAttribute(str);
    }

    public java.util.Enumeration getAttributeNames()
    {
        if(request==null)
        {
            return attr.keys();
        }
        return request.getAttributeNames();//(new Vector()).elements();
    }

    public String getAuthType()
    {
        if(request==null)return null;
        return request.getAuthType();
    }

    public String getCharacterEncoding()
    {
        if(request==null)return null;
        return request.getCharacterEncoding();
    }

    public int getContentLength()
    {
        return 0;
    }

    public String getContentType()
    {
        if(request==null)return "text/html";
        return request.getContentType();
    }

    public String getContextPath()
    {
        if(request==null)return null;
        return request.getContextPath();
    }

    public javax.servlet.http.Cookie[] getCookies()
    {
        if(request==null)return null;
        return request.getCookies();
    }

    public long getDateHeader(String str)
    {
        return new Date().getTime();//request.getDateHeader(str);
    }

    public String getHeader(String str)
    {
        return null;
    }

    public java.util.Enumeration getHeaderNames()
    {
        return new Vector().elements();
    }

    public java.util.Enumeration getHeaders(String str)
    {
        return new Vector().elements();
    }

    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
    {
        return null;
    }

    public int getIntHeader(String str)
    {
        if(request==null)
            return -1;
        else 
            return request.getIntHeader(str);
    }

    public java.util.Locale getLocale()
    {
        if(request==null)return Locale.getDefault();
        return request.getLocale();
    }

    public java.util.Enumeration getLocales()
    {
        if(request==null)return new Vector().elements();
        return request.getLocales();
    }

    public String getMethod()
    {
        if(request==null)return "GET";
        return request.getMethod();
    }

    public String getParameter(String str)
    {
        String ret=null;
        String [] arr=(String[])parms.get(str);
        if(arr!=null)ret=arr[0];
        return ret;
    }

    public java.util.Map getParameterMap()
    {
        return parms;
    }

    public java.util.Enumeration getParameterNames()
    {
        return parms.keys();
    }

    public String[] getParameterValues(String str)
    {
        return (String[])parms.get(str);
    }

    public String getPathInfo()
    {
        if(request==null)return null;
        return request.getPathInfo();
    }

    public String getPathTranslated()
    {
        if(request==null)return null;
        return request.getPathTranslated();
    }

    public String getProtocol()
    {
        if(request==null)return null;
        return request.getProtocol();
    }

    public String getQueryString()
    {
        return null;
    }

    public java.io.BufferedReader getReader() throws java.io.IOException
    {
        return null;
    }

    public String getRealPath(String str)
    {
        if(request==null)return null;
        return request.getRealPath(str);
    }

    public String getRemoteAddr()
    {
        if(request==null)return null;
        return request.getRemoteAddr();
    }

    public String getRemoteHost()
    {
        if(request==null)return null;
        return request.getRemoteHost();
    }

    public String getRemoteUser()
    {
        if(request==null)return null;
        return request.getRemoteUser();
    }

    public javax.servlet.RequestDispatcher getRequestDispatcher(String str)
    {
        if(request==null)return null;
        return request.getRequestDispatcher(str);
    }

    public String getRequestURI()
    {
        if(request==null)return null;
        return request.getRequestURI();
    }

    public StringBuffer getRequestURL()
    {
        if(request==null)return null;
        return request.getRequestURL();
    }

    public String getRequestedSessionId()
    {
        if(request==null)sess.getId();
        return request.getRequestedSessionId();
    }

    public String getScheme()
    {
        if(request==null)return null;
        return request.getScheme();
    }

    public String getServerName()
    {
        if(request==null)return null;
        return request.getServerName();//com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/distributor");
    }

    public int getServerPort()
    {
        if(request==null)return 0;
        return request.getServerPort();
    }

    public String getServletPath()
    {
        if(request==null)return null;
        return request.getServletPath();//WBUtils.getInstance().getWebPath();
    }

    public javax.servlet.http.HttpSession getSession()
    {
        if(request==null)return sess;
        return request.getSession();
    }

    public javax.servlet.http.HttpSession getSession(boolean param)
    {
        if(request==null)return sess;
        return request.getSession(param);
    }

    public java.security.Principal getUserPrincipal()
    {
        if(request==null)return null;
        return request.getUserPrincipal();
    }

    public boolean isRequestedSessionIdFromCookie()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdFromCookie();
    }

    public boolean isRequestedSessionIdFromURL()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdFromUrl()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdFromUrl();
    }

    public boolean isRequestedSessionIdValid()
    {
        if(request==null)return false;
        return request.isRequestedSessionIdValid();
    }

    public boolean isSecure()
    {
        if(request==null)return false;
        return request.isSecure();
    }

    public boolean isUserInRole(String str)
    {
        if(request==null)return false;
        return request.isUserInRole(str);
    }

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

    public void setCharacterEncoding(String str) throws java.io.UnsupportedEncodingException
    {
        if(request==null)return;
        request.setCharacterEncoding(str);
    }

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
