
/*
 * WBParameters.java
 *
 * Created on 2 de junio de 2004, 01:50 PM
 */

package org.semanticwb.portal.api;

import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 * Intefase con los parametros enviados al recurso.
 * @author Javier Solis Gonzalez
 */
public interface SWBParameters extends SWBResourceModes
{
  /** Getter for property topic.
   * @return Value of property topic.
   *
   */
  public WebPage getTopic();
  
  public WebPage getAdminTopic();
  
  /** Getter for property user.
   * @return Value of property user.
   *
   */
  public User getUser();
  
  
  /** Getter for property userLevel.
   * @return Value of property userLevel.
   *
   */
  public int getUserLevel();

  public Portlet getResourceBase();
  

    /**
   * Sets the window state of a portlet to the given window state.
   * <p>
   * Possible values are the standard window states and any custom 
   * window states supported by the portal and the portlet. 
   * Standard window states are:
   * <ul>
   * <li>MINIMIZED
   * <li>NORMAL
   * <li>MAXIMIZED
   * </ul>
   *
   * @param windowState
   *               the new portlet window state
   *
   * @exception WindowStateException
   *                   if the portlet cannot switch to the specified window state.
   *                   To avoid this exception the portlet can check the allowed
   *                   window states with <code>Request.isWindowStateAllowed()</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   *
   * @see WindowState
   */
    
    //public void setWindowState(String state);
    
    public String getWindowState();
    
    public int getCallMethod();

    //public void setCallMethod(int callMethod);

    
  
  /**
   * Sets the portlet mode of a portlet to the given portlet mode.
   * <p>
   * Possible values are the standard portlet modes and any custom 
   * portlet modes supported by the portal and the portlet. Portlets 
   * must declare in the deployment descriptor the portlet modes they 
   * support for each markup type.  
   * Standard portlet modes are:
   * <ul>
   * <li>EDIT
   * <li>HELP
   * <li>VIEW
   * </ul>
   * <p>
   * Note: The portlet may still be called in a different window
   *       state in the next render call, depending on the portlet container / portal.
   * 
   * @param portletMode
   *               the new portlet mode
   *
   * @exception PortletModeException
   *                   if the portlet cannot switch to this portlet mode,
   *                   because the portlet or portal does not support it for this markup,
   *                   or the current user is not allowed to switch to this portlet mode.
   *                   To avoid this exception the portlet can check the allowed
   *                   portlet modes with <code>Request.isPortletModeAllowed()</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   */
    
    //public void setMode(String mode);

    public String getMode();
    
    public boolean isSecure();
    
    //public void setSecure(boolean secure);    
    
    /** Getter for property action.
    * @return Value of property action.
    *
    */
    public java.lang.String getAction();

    /** Setter for property action.
    * @param action New value of property action.
    *
    */
    //public void setAction(java.lang.String action);
  
    public boolean haveVirtualTopic();
}
