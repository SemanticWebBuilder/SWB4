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
package org.semanticwb.portal.util;

import javax.servlet.http.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.lib.SWBBridgeResponse;


// TODO: Auto-generated Javadoc
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
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBBridge.class);
    
    /** The get params. */
    private HashMap getParams = new HashMap();
    
    /** The post params. */
    private HashMap postParams = new HashMap();
    
    /** The headers. */
    private HashMap headers = new HashMap();
    
    /** The cookies. */
    private ArrayList cookies = new ArrayList();
    
    /** The post data. */
    private byte[] postData=null;
    
    /** The request query string. */
    private boolean requestQueryString=true;
    
    /** The follow redirects. */
    private boolean followRedirects=false;
    
    /** The replase host. */
    private boolean replaseHost=true;    
    
    /** The accept encoding. */
    private boolean acceptEncoding=true;    

    /**
     * Creates a new instance of WBBridge.
     */
    public SWBBridge()
    {
    }

    /**
     * Gets the html.
     * 
     * @param surl the surl
     * @return the html
     * @return
     */
    public String getHtml(String surl)
    {
        return getHtml(surl, null);
    }

    /**
     * Gets the html.
     * 
     * @param surl the surl
     * @param request the request
     * @return the html
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
     * Bridge.
     * 
     * @param remoteURL the remote url
     * @param request the request
     * @param response the response
     * @param instance the instance
     * @return the sWB bridge response
     * @throws MalformedURLException the malformed url exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL,  HttpServletRequest request, HttpServletResponse response, String instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, null, request, response, response.getOutputStream(), true, instance);
    }
    
    /**
     * Bridge.
     * 
     * @param remoteURL the remote url
     * @param request the request
     * @param out the out
     * @param instance the instance
     * @return the sWB bridge response
     * @throws MalformedURLException the malformed url exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL, HttpServletRequest request, OutputStream out, String instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, null, request, null, out, false, instance);
    }    
    

    /**
     * Bridge.
     * 
     * @param remoteURL the remote url
     * @param ssoURL the sso url
     * @param request the request
     * @param response the response
     * @param instance the instance
     * @return the sWB bridge response
     * @throws MalformedURLException the malformed url exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL,String ssoURL,  HttpServletRequest request, HttpServletResponse response, String instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, ssoURL, request, response, response.getOutputStream(), true, instance);
    }

    /**
     * Bridge.
     * 
     * @param remoteURL the remote url
     * @param ssoURL the sso url
     * @param request the request
     * @param out the out
     * @param instance the instance
     * @return the sWB bridge response
     * @throws MalformedURLException the malformed url exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @return
     */
    public SWBBridgeResponse bridge(String remoteURL, String ssoURL, HttpServletRequest request, OutputStream out, String instance) throws java.net.MalformedURLException, java.io.IOException
    {
        return bridge(remoteURL, ssoURL, request, null, out, false, instance);
    }

    /**
     * Bridge.
     * 
     * @param remoteURL the remote url
     * @param ssoURL the sso url
     * @param request the request
     * @param response the response
     * @param out the out
     * @param usedResponse the used response
     * @param instance the instance
     * @return the sWB bridge response
     * @throws MalformedURLException the malformed url exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SWBBridgeResponse bridge(String remoteURL, String ssoURL, HttpServletRequest request, HttpServletResponse response, OutputStream out, boolean usedResponse, String instance) throws java.net.MalformedURLException, java.io.IOException
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
                    aux.addCookies(wbcookie,"[*]","");
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

        if (con.getHeaderField("Location")!=null)//resc == 302)
        {
            InputStream st=con.getErrorStream();
            if(st!=null)ret.setErrorMessage(SWBUtils.IO.readInputStream(st));            
            //System.out.println("*************** redirect *********************************");
            //System.out.println(con.getHeaderField("Location"));
            if (usedResponse && out!=null) response.sendRedirect(con.getHeaderField("Location"));
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
                        System.out.println(con.getHeaderFieldKey(x)+"->"+con.getHeaderField(x));
                    }
                }
            }
            InputStream in = con.getInputStream();
            if(out!=null)
            {
                byte buff[] = new byte[8192];
                int r = 0;
                while ((r = in.read(buff)) != -1)
                {
                    out.write(buff, 0, r);
                    //System.out.print(new String(buff,0,r));
                }
            }else
            {
                ret.setInputStream(in);
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

    /**
     * Getter for property getParams.
     * 
     * @param name the name
     * @return Value of property getParams.
     */
    public String getGetParameter(String name)
    {
        return (String) this.getParams.get(name);
    }

    /**
     * Setter for property getParams.
     * 
     * @param name the name
     * @param value the value
     */
    public void addGetParameter(String name, String value)
    {
        this.getParams.put(name, value);
    }

    /**
     * Setter for property getParams.
     * 
     * @param name the name
     */
    public void removeGetParameter(String name)
    {
        this.getParams.remove(name);
    }

    /**
     * Getter for property getParams.
     * 
     * @param name the name
     * @return Value of property getParams.
     */
    public String getPostParameter(String name)
    {
        return (String) this.postParams.get(name);
    }

    /**
     * Setter for property getParams.
     * 
     * @param name the name
     * @param value the value
     */
    public void addPostParameter(String name, String value)
    {
        this.postParams.put(name, value);
    }

    /**
     * Setter for property getParams.
     * 
     * @param name the name
     */
    public void removePostParameter(String name)
    {
        this.postParams.remove(name);
    }
    
    /**
     * Getter for property getParams.
     * 
     * @param name the name
     * @return Value of property getParams.
     */
    public String getHeader(String name)
    {
        return (String) this.headers.get(name);
    }

    /**
     * Setter for property getParams.
     * 
     * @param name the name
     * @param value the value
     */
    public void addHeader(String name, String value)
    {
        this.headers.put(name, value);
    }

    /**
     * Setter for property getParams.
     * 
     * @param name the name
     */
    public void removeHeader(String name)
    {
        this.headers.remove(name);
    }    

    /**
     * Setter for property getParams.
     * 
     * @param cookie the cookie
     */
    public void addCookie(String cookie)
    {
        cookies.add(cookie);
    }

    /**
     * Gets the gets the query string.
     * 
     * @return the gets the query string
     */
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

    /**
     * Gets the post query string.
     * 
     * @return the post query string
     */
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

    /**
     * Gets the locale cookies.
     * 
     * @return the locale cookies
     */
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
