/**
  * Copyright 2003 IBM Corporation and Sun Microsystems, Inc.
  * All rights reserved.
  * Use is subject to license terms.
  */

package org.semanticwb.portal.portlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * The <CODE>RenderResponse</CODE> defines an object to assist a portlet in
 * sending a response to the portal.
 * It extends the <CODE>PortletResponse</CODE> interface to provide specific 
 * render response functionality to portlets.<br>
 * The portlet container creates a <CODE>RenderResponse</CODE> object and 
 * passes it as argument to the portlet's <CODE>render</CODE> method.
 * 
 * @see RenderRequest
 * @see PortletResponse
 */
public class PortletResponseImp extends HttpServletResponseWrapper implements PortletResponse
{
    public PortletResponseImp(HttpServletResponse servletResponse)
    {
        super(servletResponse);
    }
    

  /**
   * Adds a String property to an existing key to be returned to the portal.
   * <p>
   * This method allows response properties to have multiple values.
   * <p>
   * Properties can be used by portlets to provide vendor specific 
   * information to the portal.
   *
   * @param  key    the key of the property to be returned to the portal
   * @param  value  the value of the property to be returned to the portal
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if key is <code>null</code>.
   */

  public void addProperty(String key, String value)
  {
      //TODO:
//        if(key == null)
//            throw new IllegalArgumentException("Property key == null");
//        Map map = PropertyManager.getRequestProperties(portletWindow, getHttpServletRequest());
//        if(map == null)
//            map = new HashMap();
//        String properties[] = (String[])map.get(key);
//        if(properties == null)
//            properties = (new String[] {
//                value
//            });
//        else
//            properties[properties.length] = value;
//        map.put(key, properties);
//        PropertyManager.setResponseProperties(portletWindow, getHttpServletRequest(), _getHttpServletResponse(), map);      
  }


  /**
   * Sets a String property to be returned to the portal.
   * <p>
   * Properties can be used by portlets to provide vendor specific 
   * information to the portal.
   * <p>
   * This method resets all properties previously added with the same key.
   *
   * @param  key    the key of the property to be returned to the portal
   * @param  value  the value of the property to be returned to the portal
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if key is <code>null</code>.
   */

  public void setProperty(String key, String value)
  {
      //TODO:
  }


  /**
   * Returns the encoded URL of the resource, like servlets,
   * JSPs, images and other static files, at the given path.
   * <p>
   * Some portal/portlet-container implementation may require 
   * those URLs to contain implementation specific data encoded
   * in it. Because of that, portlets should use this method to 
   * create such URLs.
   * <p>
   * The <code>encodeURL</code> method may include the session ID 
   * and other portal/portlet-container specific information into the URL. 
   * If encoding is not needed, it returns the URL unchanged. 
   *
   * @param   path
   *          the URI path to the resource. This must be either
   *          an absolute URL (e.g. 
   *          <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
   *          or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if path doesn't have a leading slash or is not an absolute URL
   * 
   * @return   the encoded resource URL as string
   */

  public String encodeURL (String path)
  {
      //TODO:
//        if(path.indexOf("://") == -1 && !path.startsWith("/"))
//            throw new IllegalArgumentException("only absolute URLs or full path URIs are allowed");
//        ResourceURLProvider provider = InformationProviderAccess.getDynamicProvider(getHttpServletRequest()).getResourceURLProvider(portletWindow);
//        if(path.indexOf("://") != -1)
//            provider.setAbsoluteURL(path);
//        else
//            provider.setFullPath(path);
//        return _getHttpServletResponse().encodeURL(provider.toString());      
      return path;
  }

    public String getNamespace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addProperty(Cookie arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addProperty(String arg0, Element arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Element createElement(String arg0) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}


