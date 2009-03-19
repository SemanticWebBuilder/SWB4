
/*
 * WBParameters.java
 *
 * Created on 2 de junio de 2004, 01:50 PM
 */

package org.semanticwb.portal.api;

import org.semanticwb.model.Resource;
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

  public Resource getResourceBase();
  

    /**
   * Sets the window state of a resource to the given window state.
   * <p>
   * Possible values are the standard window states and any custom 
   * window states supported by the portal and the resource.
   * Standard window states are:
   * <ul>
   * <li>MINIMIZED
   * <li>NORMAL
   * <li>MAXIMIZED
   * </ul>
   *
   * @param windowState
   *               the new resource window state
   *
   * @exception WindowStateException
   *                   if the resource cannot switch to the specified window state.
   *                   To avoid this exception the resource can check the allowed
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
   * Sets the resource mode of a resource to the given resource mode.
   * <p>
   * Possible values are the standard resource modes and any custom
   * resource modes supported by the portal and the resource. Resources
   * must declare in the deployment descriptor the resource modes they
   * support for each markup type.  
   * Standard resource modes are:
   * <ul>
   * <li>EDIT
   * <li>HELP
   * <li>VIEW
   * </ul>
   * <p>
   * Note: The resource may still be called in a different window
   *       state in the next render call, depending on the resource container / portal.
   * 
   * @param resourceMode
   *               the new resource mode
   *
   * @exception ResourceModeException
   *                   if the resource cannot switch to this resource mode,
   *                   because the resource or portal does not support it for this markup,
   *                   or the current user is not allowed to switch to this resource mode.
   *                   To avoid this exception the resource can check the allowed
   *                   resource modes with <code>Request.isResourceModeAllowed()</code>.
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
