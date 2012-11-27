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
package org.semanticwb.portal.portlet;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;

/**
 * The <CODE>PortletRequest</CODE> defines the base interface to provide client
 * request information to a portlet. The portlet container uses two specialized
 * versions of this interface when invoking a portlet, <CODE>ActionRequest</CODE>
 * and <CODE>RenderRequest</CODE>. The portlet container creates these objects and 
 * passes them as  arguments to the portlet's <CODE>processAction</CODE> and
 * <CODE>render</CODE> methods.
 * 
 * @see ActionRequest
 * @see RenderRequest
 */
public class PortletRequestImp extends HttpServletRequestWrapper implements RenderRequest
{
    SWBParameters paramsRequest=null;
    private boolean removeParameters=false;
    private Hashtable parms = new Hashtable();    
    private WBPortletDefinition portletDefinition;
    
    private PortletSession portletSession;
    
    private java.security.Principal principal=null;
    
    public PortletRequestImp(HttpServletRequest request, SWBParameters paramsRequest, WBPortletDefinition portletDefinition, boolean removeParameters)
    {
        super(request);
        this.paramsRequest=paramsRequest;
        this.removeParameters=removeParameters;
        this.portletDefinition=portletDefinition;
        
        if(paramsRequest!=null && paramsRequest.getUser().isRegistered())
        {
            principal=paramsRequest.getUser();
        }
    }


  /**
   * Returns true, if the given window state is valid
   * to be set for this portlet in the context
   * of the current request.
   *
   * @param  state    window state to checked
   *
   * @return    true, if it is valid for this portlet
   *             in this request to change to the
   *            given window state
   *
   */
  public boolean isWindowStateAllowed(WindowState state)
  {
      //TODO
      if(state.equals(WindowState.MAXIMIZED) || state.equals(WindowState.MINIMIZED) || state.equals(WindowState.NORMAL))return true;
      return false;
  }


  /**
   * Returns true, if the given portlet mode is a valid
   * one to set for this portlet  in the context
   * of the current request.
   *
   * @param  mode    portlet mode to check
   *
   * @return    true, if it is valid for this portlet
   *             in this request to change to the
   *            given portlet mode
   *
   */

  public boolean isPortletModeAllowed(PortletMode mode)
  {
      //TODO
      return true;
  }


  /**
   * Returns the current portlet mode of the portlet.
   *
   * @return   the portlet mode
   */

  public PortletMode getPortletMode ()
  {
      return new PortletMode(paramsRequest.getMode());
  }


  /**
   * Returns the current window state of the portlet.
   *
   * @return   the window state
   */

  public WindowState getWindowState ()
  {
      return new WindowState(paramsRequest.getWindowState());
  }


  /**
   * Returns the preferences object associated with the portlet.
   *
   * @return the portlet preferences
   */
  public PortletPreferences getPreferences ()
  {
      return null;
  }


  /**
   * Returns the current portlet session or, if there is no current session,
   * creates one and returns the new session.
   *  <p>
   * Creating a new portlet session will result in creating
   * a new <code>HttpSession</code> on which the portlet session is based on.
   *
   * @return the portlet session
   */

  public PortletSession getPortletSession ()
  {
      return getPortletSession(true);
  }


  /**
   * Returns the current portlet session or, if there is no current session
   * and the given flag is <CODE>true</CODE>, creates one and returns
   * the new session.
   * <P>
   * If the given flag is <CODE>false</CODE> and there is no current
   * portlet session, this method returns <CODE>null</CODE>.
   *  <p>
   * Creating a new portlet session will result in creating
   * a new <code>HttpSession</code> on which the portlet session is based on.
   * 
   * @param create
   *               <CODE>true</CODE> to create a new session, <BR>
   *               <CODE>false</CODE> to return <CODE>null</CODE> if there
   *               is no current session
   * @return the portlet session
   */

  public PortletSession getPortletSession (boolean create)
  {
        // check if the session was invalidated
        HttpSession httpSession = this._getHttpServletRequest().getSession(false);

        if ((portletSession != null) && (httpSession == null)) 
        {
                portletSession = null;
        } else if (httpSession != null) 
        {
                create = true;
        }

        if (create && portletSession == null) 
        {
                httpSession = this._getHttpServletRequest().getSession(create);
                if (httpSession != null) 
                {
                    portletSession = new PortletSessionImp(httpSession,portletDefinition.getPortletConfig().getPortletContext(),paramsRequest.getResourceBase().getId());
                }
        }

        return portletSession;
  }


  /**
   * Returns the value of the specified request property
   * as a <code>String</code>. If the request did not include a property
   * of the specified name, this method returns <code>null</code>.
   * <p>
   * A portlet can access portal/portlet-container specific properties 
   * through this method and, if available, the
   * headers of the HTTP client request.
   * <p>
   * This method should only be used if the 
   * property has only one value. If the property might have
   * more than one value, use {@link #getProperties}.
   * <p>
   * If this method is used with a multivalued
   * parameter, the value returned is equal to the first value
   * in the Enumeration returned by <code>getProperties</code>.
   *
   * @param name		a <code>String</code> specifying the
   *				property name
   *
   * @return			a <code>String</code> containing the
   *				value of the requested
   *				property, or <code>null</code>
   *				if the request does not
   *				have a property of that name.
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   */			

  public String getProperty(String name)
  {
        if(name == null)
            throw new IllegalArgumentException("Attribute name == null");
        String prop = getHeader(name);
//        if(prop == null)
//        {
//            Map map = PropertyManager.getRequestProperties(portletWindow, _getHttpServletRequest());
//            if(map != null)
//            {
//                String properties[] = (String[])map.get(name);
//                if(properties != null && properties.length > 0)
//                    prop = properties[0];
//            }
//        }
        return prop;
  }


  /**
   * Returns all the values of the specified request property
   * as a <code>Enumeration</code> of <code>String</code> objects.
   * <p>
   * If the request did not include any propertys
   * of the specified name, this method returns an empty
   * <code>Enumeration</code>.
   * The property name is case insensitive. You can use
   * this method with any request property.
   *
   * @param name		a <code>String</code> specifying the
   *				property name
   *
   * @return		a <code>Enumeration</code> containing
   *                  	the values of the requested property. If
   *                  	the request does not have any properties of
   *                  	that name return an empty <code>Enumeration</code>.
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   */			
  
  public java.util.Enumeration getProperties(String name)
  {
        if(name == null)
            throw new IllegalArgumentException("Property name == null");
        Vector v = new Vector();
        Enumeration props = getHeaders(name);
        if(props != null)
            for(; props.hasMoreElements(); v.add(props.nextElement()));
//        Map map = PropertyManager.getRequestProperties(portletWindow, _getHttpServletRequest());
//        if(map != null)
//        {
//            String properties[] = (String[])map.get(name);
//            if(properties != null)
//            {
//                for(int i = 0; i < properties.length; i++)
//                    v.add(properties[i]);
//
//            }
//        }
        return v.elements();
  }
    
    
  /**
   *
   * Returns a <code>Enumeration</code> of all the property names
   * this request contains. If the request has no
   * properties, this method returns an empty <code>Enumeration</code>.
   *
   *
   * @return			an <code>Enumeration</code> of all the
   *				property names sent with this
   *				request; if the request has
   *				no properties, an empty <code>Enumeration</code>.
   */

  public java.util.Enumeration getPropertyNames()
  {
        Vector v = new Vector();
//        Map map = PropertyManager.getRequestProperties(portletWindow, _getHttpServletRequest());
//        if(map != null)
//        {
//            for(Iterator propsIter = map.keySet().iterator(); propsIter.hasNext(); v.add(propsIter.next()));
//        }
        Enumeration props = getHeaderNames();
        if(props != null)
            for(; props.hasMoreElements(); v.add(props.nextElement()));
        return v.elements();
  }
    
    
  /**
   * Returns the context of the calling portal.
   *
   * @return the context of the calling portal
   */

  public PortalContext getPortalContext()
  {
      return WBFactoryMgr.getPortalContext();
  }
  
    public String getContextPath() 
    {
        return portletDefinition.getContext();
    }  


  /**
   * Returns the login of the user making this request, if the user 
   * has been authenticated, or null if the user has not been authenticated.
   *
   * @return		a <code>String</code> specifying the login
   *			of the user making this request, or <code>null</code>
   *			if the user login is not known.
   *
   */

  public java.lang.String getRemoteUser()
  {
      if(paramsRequest.getUser().isRegistered())
          return paramsRequest.getUser().getLogin();
      else
          return null;
  }


  /**
   * Returns a java.security.Principal object containing the name of the 
   * current authenticated user.
   *
   * @return		a <code>java.security.Principal</code> containing
   *			the name of the user making this request, or
   *			<code>null</code> if the user has not been 
   *			authenticated.
   */

  public java.security.Principal getUserPrincipal()
  {
     return principal;
  }


  /**
   * Returns a boolean indicating whether the authenticated user is 
   * included in the specified logical "role".  Roles and role membership can be
   * defined using deployment descriptors.  If the user has not been
   * authenticated, the method returns <code>false</code>.
   *
   * @param role		a <code>String</code> specifying the name
   *				of the role
   *
   * @return		a <code>boolean</code> indicating whether
   *			the user making this request belongs to a given role;
   *			<code>false</code> if the user has not been 
   *			authenticated.
   */

  public boolean isUserInRole(java.lang.String role)
  {
      //System.out.println("role:"+role);
      String link=(String)portletDefinition.getPortletRoles().get(role);
      if(link==null)link=role;
      User user=paramsRequest.getUser();
      Role r=user.getUserRepository().getRole(link);
      if(r!=null)return user.hasRole(r);
      else return false;
  }


  /**
   *
   * Returns the value of the named attribute as an <code>Object</code>,
   * or <code>null</code> if no attribute of the given name exists. 
   * <p>
   * Attribute names should follow the same conventions as package
   * names. This specification reserves names matching <code>java.*</code>,
   * and <code>javax.*</code>. 
   * <p>
   * In a distributed portlet web application the <code>Object</code>
   * needs to be serializable.
   *
   * @param name	a <code>String</code> specifying the name of 
   *			the attribute
   *
   * @return		an <code>Object</code> containing the value 
   *			of the attribute, or <code>null</code> if
   *			the attribute does not exist.
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   *
   */

  public Object getAttribute(String name)
  {
      //TODO
//        if(name == null)
//            throw new IllegalArgumentException("Attribute name == null");
//        Object attribute = _getHttpServletRequest().getAttribute(NamespaceMapperAccess.getNamespaceMapper().encode(portletWindow.getId(), name));
//        if(attribute == null)
//            attribute = _getHttpServletRequest().getAttribute(name);
//        return attribute;      
      return super.getAttribute(name);
  }


  /**
   * Returns an <code>Enumeration</code> containing the
   * names of the attributes available to this request. 
   * This method returns an empty <code>Enumeration</code>
   * if the request has no attributes available to it.
   * 
   *
   * @return		an <code>Enumeration</code> of strings 
   *			containing the names 
   * 			of the request attributes, or an empty 
   *                    <code>Enumeration</code> if the request 
   *                    has no attributes available to it.
   */
  
  public java.util.Enumeration getAttributeNames()
  {
    //TODO
//        Enumeration attributes = _getHttpServletRequest().getAttributeNames();
//        Vector portletAttributes = new Vector();
//        do
//        {
//            if(!attributes.hasMoreElements())
//                break;
//            String attribute = (String)attributes.nextElement();
//            String portletAttribute = NamespaceMapperAccess.getNamespaceMapper().decode(portletWindow.getId(), attribute);
//            if(portletAttribute != null)
//                portletAttributes.add(portletAttribute);
//        } while(true);
//        return portletAttributes.elements();      
      return super.getAttributeNames();
  }


  /**
   * Returns the value of a request parameter as a <code>String</code>,
   * or <code>null</code> if the parameter does not exist. Request parameters
   * are extra information sent with the request. The returned parameter 
   * are "x-www-form-urlencoded" decoded.
   * <p>
   * Only parameters targeted to the current portlet are accessible.
   * <p>
   * This method should only be used if the 
   * parameter has only one value. If the parameter might have
   * more than one value, use {@link #getParameterValues}.
   * <p>
   * If this method is used with a multivalued
   * parameter, the value returned is equal to the first value
   * in the array returned by <code>getParameterValues</code>.
   *
   *
   *
   * @param name 	a <code>String</code> specifying the 
   *			name of the parameter
   *
   * @return		a <code>String</code> representing the 
   *			single value of the parameter
   *
   * @see 		#getParameterValues
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   *
   */
  
  public String getParameter(String name)
  {
        if(removeParameters)
            return (String) parms.get(name);
        else 
            return super.getParameter(name);
  }


  /**
   *
   * Returns an <code>Enumeration</code> of <code>String</code>
   * objects containing the names of the parameters contained
   * in this request. If the request has 
   * no parameters, the method returns an 
   * empty <code>Enumeration</code>. 
   * <p>
   * Only parameters targeted to the current portlet are returned.
   *
   *
   * @return		an <code>Enumeration</code> of <code>String</code>
   *			objects, each <code>String</code> containing
   * 			the name of a request parameter; or an 
   *			empty <code>Enumeration</code> if the
   *			request has no parameters.
   */

  public java.util.Enumeration getParameterNames()
  {
        if(removeParameters)
            return parms.keys();
        else
            return super.getParameterNames();      
  }


  /**
   * Returns an array of <code>String</code> objects containing 
   * all of the values the given request parameter has, or 
   * <code>null</code> if the parameter does not exist.
   * The returned parameters are "x-www-form-urlencoded" decoded.
   * <p>
   * If the parameter has a single value, the array has a length
   * of 1.
   *
   *
   * @param name	a <code>String</code> containing the name of 
   *			the parameter the value of which is requested
   *
   * @return		an array of <code>String</code> objects 
   *			containing the parameter values.
   *
   * @see		#getParameter
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   *
   */

  public String[] getParameterValues(String name)
  {
        if(removeParameters)
        {
            int size = parms.values().size();
            String ret[] = new String[size];
            Iterator it = parms.values().iterator();
            int x = 0;
            while (it.hasNext())
            {
                ret[x] = (String) it.next();
                x++;
            }
            return ret;
        }else
        {
            return super.getParameterValues(name);
        }      
  }


  /** 
   * Returns a <code>Map</code> of the parameters of this request.
   * Request parameters are extra information sent with the request.  
   * The returned parameters are "x-www-form-urlencoded" decoded.
   * <p>
   * The values in the returned <code>Map</code> are from type
   * String array (<code>String[]</code>).
   * <p>
   * If no parameters exist this method returns an empty <code>Map</code>.
   *
   * @return     an immutable <code>Map</code> containing parameter names as 
   *             keys and parameter values as map values, or an empty <code>Map</code>
   *             if no parameters exist. The keys in the parameter
   *             map are of type String. The values in the parameter map are of type
   *             String array (<code>String[]</code>).
   */

  public java.util.Map getParameterMap()
  {
        if(removeParameters)
            return parms;
        else
            return super.getParameterMap();      
  }


  /**
   * Stores an attribute in this request.
   *
   * <p>Attribute names should follow the same conventions as
   * package names. Names beginning with <code>java.*</code>,
   * <code>javax.*</code>, and <code>com.sun.*</code> are
   * reserved for use by Sun Microsystems.
   *<br> If the value passed into this method is <code>null</code>, 
   * the effect is the same as calling {@link #removeAttribute}.
   *
   *
   * @param name			a <code>String</code> specifying 
   *					the name of the attribute
   *
   * @param o				the <code>Object</code> to be stored
   *
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   */
  
  public void setAttribute(String name, Object o)
  {
      //TODO
//        if(name == null)
//            throw new IllegalArgumentException("Attribute name == null");
//        if(o == null)
//            removeAttribute(name);
//        else
//        if(isNameReserved(name))
//            _getHttpServletRequest().setAttribute(name, o);
//        else
//            _getHttpServletRequest().setAttribute(NamespaceMapperAccess.getNamespaceMapper().encode(portletWindow.getId(), name), o);      
      super.setAttribute(name, o);
  }


  /**
   *
   * Removes an attribute from this request.  This method is not
   * generally needed, as attributes only persist as long as the request
   * is being handled.
   *
   * <p>Attribute names should follow the same conventions as
   * package names. Names beginning with <code>java.*</code>,
   * <code>javax.*</code>, and <code>com.sun.*</code> are
   * reserved for use by Sun Microsystems.
   *
   * @param name			a <code>String</code> specifying 
   *					the name of the attribute to be removed
   *
   *
   * @exception  java.lang.IllegalArgumentException 
   *                            if name is <code>null</code>.
   */

  public void removeAttribute(String name)
  {
      //TODO
//        if(name == null)
//        {
//            throw new IllegalArgumentException("Attribute name == null");
//        } else
//        {
//            _getHttpServletRequest().removeAttribute(NamespaceMapperAccess.getNamespaceMapper().encode(portletWindow.getId(), name));
//            return;
//        }   
      super.removeAttribute(name);
  }

   
  /**
   *
   * Returns the session ID indicated in the client request.
   * This session ID may not be a valid one, it may be an old 
   * one that has expired or has been invalidated.
   * If the client request
   * did not specify a session ID, this method returns
   * <code>null</code>.
   *
   * @return		a <code>String</code> specifying the session
   *			ID, or <code>null</code> if the request did
   *			not specify a session ID
   *
   * @see		#isRequestedSessionIdValid
   *
   */

  public String getRequestedSessionId()
  {
      return super.getRequestedSessionId();
  }


  /**
   *
   * Checks whether the requested session ID is still valid.
   *
   * @return			<code>true</code> if this
   *				request has an id for a valid session
   *				in the current session context;
   *				<code>false</code> otherwise
   *
   * @see			#getRequestedSessionId
   * @see			#getPortletSession
   */

  public boolean isRequestedSessionIdValid()
  {
      return super.isRequestedSessionIdValid();
  }


  /**
   * Returns the portal preferred content type for the response.
   * <p>
   * The content type only includes the MIME type, not the
   * character set.
   * <p>
   * Only content types that the portlet has defined in its
   * deployment descriptor are valid return values for
   * this method call. If the portlet has defined
   * <code>'*'</code> or <code>'* / *'</code> as supported content
   * types, these may also be valid return values.
   *
   * @return preferred MIME type of the response
   */

  public String getResponseContentType()
  {
      //TODO
//       String responseContentType = provider.getResponseContentType();
//        return responseContentType;          
      String responseContentType = super.getContentType();
      if(responseContentType==null)responseContentType="text/html";
      return responseContentType;
  }


  /**
   * Gets a list of content types which the portal accepts for the response.
   * This list is ordered with the most preferable types listed first.
   * <p>
   * The content type only includes the MIME type, not the
   * character set.
   * <p>
   * Only content types that the portlet has defined in its
   * deployment descriptor are valid return values for
   * this method call. If the portlet has defined
   * <code>'*'</code> or <code>'* / *'</code> as supported content
   * types, these may also be valid return values.
   *
   * @return ordered list of MIME types for the response
   */

  public java.util.Enumeration getResponseContentTypes()
  {
      //TODO
//        Iterator responseContentTypes = provider.getResponseContentTypes();
//        return new Enumerator(responseContentTypes);
      Vector vec=new Vector();
      vec.add("text/html");
      return vec.elements();
  }


  /**
   * Returns the preferred Locale in which the portal will accept content.
   * The Locale may be based on the Accept-Language header of the client.
   *
   * @return  the prefered Locale in which the portal will accept content.
   */

  public java.util.Locale getLocale()
  {
      Locale loc=super.getLocale();
      List list=portletDefinition.getSupportedLocales();
      if(list.contains(loc))return loc;
      if(list.size()>0)
      {
            return (Locale)list.get(0);          
      }        
      return loc;
  }


  /**
   * Returns an Enumeration of Locale objects indicating, in decreasing
   * order starting with the preferred locale in which the portal will
   * accept content for this request.
   * The Locales may be based on the Accept-Language header of the client.
   * 
   * @return  an Enumeration of Locales, in decreasing order, in which 
   *           the portal will accept content for this request
   */

  public java.util.Enumeration getLocales()
  {
      Vector vec=new Vector();
      Enumeration en=super.getLocales();
      while(en.hasMoreElements())
      {
          vec.add(en.nextElement());
      }
      Iterator it=portletDefinition.getSupportedLocales().iterator();
      while(it.hasNext())
      {
          Object obj=it.next();
          if(!vec.contains(obj))vec.add(obj);
      }
      return vec.elements();
  }


  /**
   * Returns the name of the scheme used to make this request.
   * For example, <code>http</code>, <code>https</code>, or <code>ftp</code>.
   * Different schemes have different rules for constructing URLs,
   * as noted in RFC 1738.
   *
   * @return		a <code>String</code> containing the name 
   *			of the scheme used to make this request
   */

  public String getScheme()
  {
      return super.getScheme();
  }
    

  /**
   * Returns the host name of the server that received the request.
   *
   * @return		a <code>String</code> containing the name 
   *			of the server to which the request was sent
   */

  public String getServerName()
  {
      return super.getServerName();
  }
    
    
  /**
   * Returns the port number on which this request was received.
   *
   * @return		an integer specifying the port number
   */

  public int getServerPort()
  {
      return super.getServerPort();
  }

    
    public void addParameter(String name, String value)
    {
        if(removeParameters)
        {
            parms.put(name, value);    
        }
    }    
    
    public String getQueryString()
    {
        if(removeParameters)
        {
            return null;    
        }else
            return super.getQueryString();
    }

    public java.io.BufferedReader getReader() throws java.io.IOException
    {
        if(removeParameters)
        {
            return null;
        }else
            return super.getReader();
    }    
    
    public int getContentLength()
    {
        if(removeParameters)
            return 0;
        else
            return super.getContentLength();
    }    
    
    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
    {
        if(removeParameters)
            return null;
        else
            return super.getInputStream();
    }    
    
    private javax.servlet.http.HttpServletRequest _getHttpServletRequest() {
            return (javax.servlet.http.HttpServletRequest) super.getRequest();
    }

    public String getETag() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getWindowID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<String, String[]> getPrivateParameterMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<String, String[]> getPublicParameterMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

}
