/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.util;

import javax.servlet.http.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.lib.SWBBridgeResponse;


/** Esta clase sirve de puente entre un servidor WebBuilder y otro servidor del cual
 * obtien el c�digo html para presentarlo en WebBuilder.
 *
 * This class is used like a bridge between a WebBuilder server and other server.
 * extracts html source from other server and displays it in WebBuilder.
 * @author : Infotec
 * @since : September 9th 2003, 03:31
 */
public class SWBBridge
{
    private static Logger log = SWBUtils.getLogger(SWBBridge.class);
    
    private HashMap getParams = new HashMap();
    private HashMap postParams = new HashMap();
    private HashMap headers = new HashMap();
    private ArrayList cookies = new ArrayList();
    private byte[] postData=null;
    private boolean requestQueryString=true;
    
    private boolean followRedirects=false;
    private boolean replaseHost=true;    
    private boolean acceptEncoding=true;    

    /** Creates a new instance of WBBridge */
    public SWBBridge()
    {
    }

    /**
     * @param surl
     * @return
     */
    public String getHtml(String surl)
    {
        return getHtml(surl, null);
    }

    /**
     * @param surl
     * @param request
     * @return
     */
    public String getHtml(String surl, HttpServletRequest request)
    {
        String ret = null;
        try
        {
            URL pagina = new URL(surl);
            URLConnection conex = pagina.openConnection();
            ret = SWBUtils.IO.readInputStream(conex.getInputStream());
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_WBBridge_getHtml_BridgeError"), e);
        }
        return ret;
    }
    
    /**
     * @param remoteURL
     * @param request
     * @param response
     * @param instance
     * @throws MalformedURLException
     * @throws IOException
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL,  HttpServletRequest request, HttpServletResponse response, long instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, null, request, response, response.getOutputStream(), true, instance);
    }
    
    /**
     * @param remoteURL
     * @param request
     * @param out
     * @param instance
     * @throws MalformedURLException
     * @throws IOException
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL, HttpServletRequest request, OutputStream out, long instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, null, request, null, out, false, instance);
    }    
    

    /**
     * @param remoteURL
     * @param request
     * @param response
     * @param instance
     * @throws MalformedURLException
     * @throws IOException
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL,String ssoURL,  HttpServletRequest request, HttpServletResponse response, long instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, ssoURL, request, response, response.getOutputStream(), true, instance);
    }

    /**
     * @param remoteURL
     * @param request
     * @param out
     * @param instance
     * @throws MalformedURLException
     * @throws IOException
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL, String ssoURL, HttpServletRequest request, OutputStream out, long instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, ssoURL, request, null, out, false, instance);
    }

    private SWBBridgeResponse bridge(String remoteURL, String ssoURL, HttpServletRequest request, HttpServletResponse response, OutputStream out, boolean usedResponse, long instance) throws java.net.MalformedURLException, java.io.IOException
    {
        //System.out.println(""+System.currentTimeMillis()+": "+"EnterBridge:"+remoteURL);
        SWBBridgeResponse ret = new SWBBridgeResponse();
        //********************** Query String *********************************
        String query = "";
        String rquery = null;
        try
        {
            rquery = new URL(remoteURL).getQuery();
        } catch (Exception e)
        {
            log.error("", e);
        }

        log.debug("request.getQueryString():"+request.getQueryString());
        log.debug("getGetQueryString():"+getGetQueryString());
        
        if (requestQueryString && request.getQueryString() != null) query += "&" + request.getQueryString();
        if (getGetQueryString() != null) query += "&" + getGetQueryString();

        if (rquery == null && query.length() > 0) query = "?" + query.substring(1);

        java.net.URL url = new java.net.URL(remoteURL + query);
        log.debug("url:"+url);
        java.net.HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setFollowRedirects(followRedirects);
        log.debug("URL:" + url + ")");
        log.debug(request.getSession().getId());

        //*********************************** HostCookieSessionVariable*******************
        String host = url.getHost();
        if(url.getPort()>0)host+=":"+url.getPort();
        String path = url.getPath();
        Date date = new Date();
        log.debug(host+" "+path+" "+date);

        boolean createCookieMgr=false;
        SWBCookieMgr cookiemgr = (SWBCookieMgr) request.getSession().getAttribute("WBCookieMgr");
        if (cookiemgr == null)
        {
            cookiemgr = new SWBCookieMgr();
            request.getSession().setAttribute("WBCookieMgr", cookiemgr);
            createCookieMgr=true;
        }

        if(ssoURL!=null)
        {
            if(!cookiemgr.haveInstanceCookies(instance))
            {
                bridge(ssoURL,null, request, new ByteArrayOutputStream(), instance);
            }
        }

        //System.out.println("*************** header *********************************");
        String cookieTag = "Cookie";
        Enumeration en = request.getHeaderNames();
        while (en.hasMoreElements())
        {
            String name = (String) en.nextElement();
            log.debug(name+":"+request.getHeader(name));
            if (!name.equalsIgnoreCase("cookie"))
            {
                if (replaseHost && name.equalsIgnoreCase("host"))
                {
                    con.setRequestProperty(name, host);
                } else if (name.equalsIgnoreCase("referer"))
                {
                    String s = url.toString();
                    //System.out.println(s);
                    String f = "";
//                    if (url.getQuery() != null)
//                    {
//                        //System.out.println(url.getQuery());
//                        f = s.substring(1, s.length() - url.getQuery().length());
//                    } else
//                    {
                        f = s;
//                    }
                    //System.out.println(f);
                    con.setRequestProperty(name, f);
                } else if(!acceptEncoding && name.equalsIgnoreCase("Accept-Encoding"))
                {
                    // Eliminar encoding
                } else
                {
                    con.setRequestProperty(name, request.getHeader(name));
                }
            } else
            {
                cookieTag = name;

                //buscar Cookie _wb_sso
                if(createCookieMgr==true)
                {
                    String wbcookie=request.getHeader(cookieTag);
                    //System.out.println("wbcookie:"+wbcookie);
                    SWBCookieMgr aux=new SWBCookieMgr();
                    aux.addCookies(wbcookie,"[*]",0);
                    Iterator it=aux.getWBSSOCookies();
                    while(it.hasNext())
                    {
                        SWBCookie wbc=(SWBCookie)it.next();
                        //System.out.println("wbc:"+wbc.getName()+"="+wbc.getValue());
                        cookiemgr.addCookie(wbc);
                    }
                }
            }
        }
        
        Iterator it=headers.keySet().iterator();
        while (it.hasNext())
        {
            String name=(String)it.next();
            //System.out.println("Head:"+name+"="+headers.get(name));
            con.setRequestProperty(name, (String)headers.get(name));
        }
        

        String cookie = cookiemgr.getCookie(host, path, date, instance);
        //System.out.println("getCookie:"+cookie);
        log.debug("getCookie:" + cookie);
        String lcookie = getLocaleCookies();
        if (cookie != null && cookie.length() > 0)
        {
            if (lcookie != null)
            {
                cookie += "; " + lcookie;
            }
        } else if (lcookie != null)
        {
            cookie = lcookie;
        }
        if (cookie != null && cookie.length() > 0)
        {
            con.setRequestProperty(cookieTag, cookie);
            log.debug("URLcookie:"+cookie);
        }

        int length = request.getContentLength();
        if (length > 0)
        {
            //System.out.println("*************** post *********************************");
            con.setDoOutput(true);
            InputStream in = request.getInputStream();
            OutputStream outb = con.getOutputStream();
            byte buff[] = new byte[8192];
            int r = 0;
            while ((r = in.read(buff)) != -1)
            {
                //System.out.println(new String(buff,0,r));
                outb.write(buff, 0, r);
            }
            outb.flush();
            outb.close();
        }else if(postData!=null)
        {
            con.setDoOutput(true);
            OutputStream outb = con.getOutputStream();
            outb.write(postData);
            outb.flush();
            outb.close();
        }else if(!postParams.isEmpty())
        {
            con.setDoOutput(true);
            OutputStream outb = con.getOutputStream();
            outb.write(getPostQueryString().getBytes());
            outb.flush();
            outb.close();
        }

        //*********************************** Con Header ********************************************
        log.debug("Headers size:"+con.getHeaderFields().size());
        for (int x = 1; con.getHeaderField(x) != null; x++)
        {
            log.debug(con.getHeaderFieldKey(x)+":"+con.getHeaderField(x));
            if (!con.getHeaderFieldKey(x).equalsIgnoreCase("Set-Cookie") && !con.getHeaderFieldKey(x).equalsIgnoreCase("Transfer-Encoding"))
            {
                ret.addHeader(con.getHeaderFieldKey(x), con.getHeaderField(x));
            }
        }
        int resc = 0;
        String resm = "No Connected...";
        try
        {
            resc = con.getResponseCode();
            resm = con.getResponseMessage();
        } catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        ret.setResponseCode(resc);
        ret.setResponseMessage(resm);

        if (resc == 302)
        {
            InputStream st=con.getErrorStream();
            if(st!=null)ret.setErrorMessage(SWBUtils.IO.readInputStream(st));            
            //System.out.println("*************** redirect *********************************");
            if (usedResponse) response.sendRedirect(con.getHeaderField("Location"));
            //ret="302:"+con.getHeaderField("Location");
        } else if (resc == 200)
        {
            //System.out.println("*************** contenido *********************************");
            ret.setContentType(con.getContentType());
            if (usedResponse)
            {
                if (con.getContentType() != null) response.setContentType(con.getContentType());
                for (int x = 1; con.getHeaderField(x) != null; x++)
                {
                    if(!con.getHeaderFieldKey(x).equalsIgnoreCase("Set-Cookie") && !con.getHeaderFieldKey(x).equalsIgnoreCase("Transfer-Encoding"))
                    {
                        response.setHeader(con.getHeaderFieldKey(x), con.getHeaderField(x));
                    }
                }
            }
            InputStream in = con.getInputStream();
            byte buff[] = new byte[8192];
            int r = 0;
            while ((r = in.read(buff)) != -1)
            {
                out.write(buff, 0, r);
                //System.out.print(new String(buff,0,r));
            }
            //ret="200:"+con.getContentType();
        } else
        {
            InputStream st=con.getErrorStream();
            if(st!=null)ret.setErrorMessage(SWBUtils.IO.readInputStream(st));
            if (usedResponse) response.sendError(resc, resm);
            //ret=con.getResponseCode()+":"+con.getResponseMessage();
        }

        //********************** Find Cookies ************************************
        for (int x = 1; con.getHeaderFieldKey(x) != null; x++)
        {
            //System.out.println(con.getHeaderFieldKey(x)+":"+con.getHeaderField(x));
            if (con.getHeaderFieldKey(x).equalsIgnoreCase("Set-Cookie"))
            {
                cookiemgr.setCookie(con.getHeaderField(x), host, instance);
                log.debug("setCookie:" + con.getHeaderField(x) + " host:" + host + " instance:" + instance);
            }
        }

        //System.out.println("Ret:"+ret);
        //System.out.println(""+System.currentTimeMillis()+": "+"ExitBridge:"+remoteURL);
        return ret;
    }

    /** Getter for property getParams.
     * @return Value of property getParams.
     * @param name
     */
    public String getGetParameter(String name)
    {
        return (String) this.getParams.get(name);
    }

    /** Setter for property getParams.
     * @param name
     * @param value
     */
    public void addGetParameter(String name, String value)
    {
        this.getParams.put(name, value);
    }

    /** Setter for property getParams.
     * @param name
     */
    public void removeGetParameter(String name)
    {
        this.getParams.remove(name);
    }

    /** Getter for property getParams.
     * @return Value of property getParams.
     * @param name
     */
    public String getPostParameter(String name)
    {
        return (String) this.postParams.get(name);
    }

    /** Setter for property getParams.
     * @param name
     * @param value
     */
    public void addPostParameter(String name, String value)
    {
        this.postParams.put(name, value);
    }

    /** Setter for property getParams.
     * @param name
     */
    public void removePostParameter(String name)
    {
        this.postParams.remove(name);
    }
    
    /** Getter for property getParams.
     * @return Value of property getParams.
     * @param name
     */
    public String getHeader(String name)
    {
        return (String) this.headers.get(name);
    }

    /** Setter for property getParams.
     * @param name
     * @param value
     */
    public void addHeader(String name, String value)
    {
        this.headers.put(name, value);
    }

    /** Setter for property getParams.
     * @param name
     */
    public void removeHeader(String name)
    {
        this.headers.remove(name);
    }    

    /** Setter for property getParams.
     * @param cookie
     */
    public void addCookie(String cookie)
    {
        cookies.add(cookie);
    }

    private String getGetQueryString()
    {
        String ret = "";
        Iterator it = getParams.keySet().iterator();
        while (it.hasNext())
        {
            String key = (String) it.next();
            ret += key + "=" + URLEncoder.encode((String) getParams.get(key));
            if (it.hasNext()) ret += "&";
        }
        if (ret.length() == 0) return null;
        return ret;
    }

    private String getPostQueryString()
    {
        String ret = "";
        Iterator it = postParams.keySet().iterator();
        while (it.hasNext())
        {
            String key = (String) it.next();
            ret += key + "=" + postParams.get(key);
            if (it.hasNext()) ret += "&";
        }
        if (ret.length() == 0) return null;
        return ret;
    }

    private String getLocaleCookies()
    {
        String c = "";
        Iterator it = cookies.iterator();
        while (it.hasNext())
        {
            c += (String) it.next();
            if (it.hasNext()) c += "; ";
        }
        if (c.length() == 0) return null;
        return c;
    }

    /**
     * Getter for property postData.
     * @return Value of property postData.
     */
    public byte[] getPostData()
    {
        return this.postData;
    }
    
    /**
     * Setter for property postData.
     * @param postData New value of property postData.
     */
    public void setPostData(byte[] postData)
    {
        this.postData = postData;
    }
    
    /**
     * Getter for property requestQueryString.
     * @return Value of property requestQueryString.
     */
    public boolean isUsedRequestQueryString()
    {
        return requestQueryString;
    }
    
    /**
     * Setter for property requestQueryString.
     * @param requestQueryString New value of property requestQueryString.
     */
    public void setUseRequestQueryString(boolean requestQueryString)
    {
        this.requestQueryString = requestQueryString;
    }
    
    /**
     * Getter for property followRedirects.
     * @return Value of property followRedirects.
     */
    public boolean isFollowRedirects()
    {
        return followRedirects;
    }
    
    /**
     * Setter for property followRedirects.
     * @param followRedirects New value of property followRedirects.
     */
    public void setFollowRedirects(boolean followRedirects)
    {
        this.followRedirects = followRedirects;
    }
    
    /**
     * Getter for property replaseHost.
     * @return Value of property replaseHost.
     */
    public boolean isReplaseHost()
    {
        return replaseHost;
    }
    
    /**
     * Setter for property replaseHost.
     * @param replaseHost New value of property replaseHost.
     */
    public void setReplaseHost(boolean replaseHost)
    {
        this.replaseHost = replaseHost;
    }
    
    /**
     * Getter for property acceptEncoding.
     * @return Value of property acceptEncoding.
     */
    public boolean isAcceptEncoding()
    {
        return acceptEncoding;
    }
    
    /**
     * Setter for property acceptEncoding.
     * @param acceptEncoding New value of property acceptEncoding.
     */
    public void setAcceptEncoding(boolean acceptEncoding)
    {
        this.acceptEncoding = acceptEncoding;
    }
    
}
