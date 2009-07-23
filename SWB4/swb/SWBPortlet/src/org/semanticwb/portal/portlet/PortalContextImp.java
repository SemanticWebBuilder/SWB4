/**
  * Copyright 2003 IBM Corporation and Sun Microsystems, Inc.
  * All rights reserved.
  * Use is subject to license terms.
  */

package org.semanticwb.portal.portlet;

import java.util.Vector;
import javax.portlet.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;


/**
 * The <CODE>PortalContext</CODE> interface gives the portlet
 * the ability to retrieve information about the portal calling this portlet.
 * <p>
 * The portlet can only read the <CODE>PortalContext</CODE> data.
 */
public class PortalContextImp implements PortalContext
{

  
  /**
   * Returns the portal property with the given name, 
   * or a <code>null</code> if there is 
   * no property by that name.
   *
   * @param  name    property name
   *
   * @return  portal property with key <code>name</code>
   *
   * @exception	java.lang.IllegalArgumentException	
   *                      if name is <code>null</code>.
   */

  public java.lang.String getProperty(java.lang.String name)
  {
      if(name==null)
      {
          throw new IllegalArgumentException("PortalContext.getProperty(name==null)");
      }
      return SWBPlatform.getEnv(name);
  }


  /**
   * Returns all portal property names, or an empty 
   * <code>Enumeration</code> if there are no property names.
   *
   * @return  All portal property names as an 
   *          <code>Enumeration</code> of <code>String</code> objects
   */
  public java.util.Enumeration getPropertyNames()
  {
      return SWBPlatform.getWebProperties().keys();
  }


  /**
   * Returns all supported portlet modes by the portal
   * as an enumertation of <code>PorltetMode</code> objects.
   * <p>
   * The portlet modes must at least include the
   * standard portlet modes <code>EDIT, HELP, VIEW</code>.
   *
   * @return  All supported portal modes by the portal
   *          as an enumertation of <code>PorltetMode</code> objects.
   */

  public java.util.Enumeration getSupportedPortletModes()
  {
      Vector ret=new Vector();
      ret.add(PortletMode.EDIT);
      ret.add(PortletMode.HELP);
      ret.add(PortletMode.VIEW);
      ret.add(new PortletMode("admin"));
      return ret.elements();
  }


  /**
   * Returns all supported window states by the portal
   * as an enumertation of <code>WindowState</code> objects.
   * <p>
   * The window states must at least include the
   * standard window states <code> MINIMIZED, NORMAL, MAXIMIZED</code>.
   *
   * @return  All supported window states by the portal
   *          as an enumertation of <code>WindowState</code> objects.
   */

  public java.util.Enumeration getSupportedWindowStates()
  {
      Vector ret=new Vector();
      ret.add(WindowState.MAXIMIZED);
      ret.add(WindowState.MINIMIZED);
      ret.add(WindowState.NORMAL);
      return ret.elements();
  }


  /**
   * Returns information about the portal like vendor, version, etc.
   * <p>
   * The form of the returned string is <I>servername/versionnumber</I>. For 
   * example, the reference implementation Pluto may return the string 
   * <CODE>Pluto/1.0</CODE>.
   * <p>
   * The portlet container may return other optional information  after the 
   * primary string in parentheses, for example, <CODE>Pluto/1.0 
   * (JDK 1.3.1; Windows NT 4.0 x86)</CODE>.
   * 
   * @return a <CODE>String</CODE> containing at least the portal name and version number
   */

  public java.lang.String getPortalInfo()
  {
      return "WebBuilder/3.1";
  }
}
