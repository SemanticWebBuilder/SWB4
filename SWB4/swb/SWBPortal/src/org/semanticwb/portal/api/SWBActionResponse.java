
/*
 * WBResponse.java
 *
 * Created on 2 de septiembre de 2003, 11:35
 */

package org.semanticwb.portal.api;


/**
 * Interfase que da acceso al Response del recurso cuando es llamado por el
 * ActionResponse.
 * @author Javier Solis Gonzalez
 */
public interface SWBActionResponse extends SWBParameters
{
    


  /**
   * Instructs the portlet container to send a redirect response 
   * to the client using the specified redirect location URL.  
   * <p>
   * This method only accepts an absolute URL (e.g. 
   * <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
   * or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
   * If required, 
   * the portlet container may encode the given URL before the 
   * redirection is issued to the client.
   * <p>
   * The sendRedirect method can not be invoked after any of the 
   * following methods of the ActionResponse interface has been called:
   * <ul>
   * <li>setPortletMode
   * <li>setWindowState
   * <li>setRenderParameter
   * <li>setRenderParameters
   * </ul>
   *
   * @param		location	the redirect location URL
   *
   * @exception	java.io.IOException	
   *                    if an input or output exception occurs.
   * @exception	java.lang.IllegalArgumentException	
   *                    if a relative path URL is given
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after any of above mentioned methods of 
   *                    the ActionResponse interface has been called.
   */

  public void sendRedirect(String location);
  
  public String getLocation();

  
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

  public void setWindowState(String state);

  /**
   * Sets a parameter map for the render request.
   * <p>
   * All previously set render parameters are cleared.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>PortletRequest.getParameter</code> call until
   * a new request is targeted to the portlet.
   * <p>
   * The given parameters do not need to be encoded
   * prior to calling this method.
   *
   * @param  parameters   Map containing parameter names for 
   *                      the render phase as 
   *                      keys and parameter values as map 
   *                      values. The keys in the parameter
   *                      map must be of type String. The values 
   *                      in the parameter map must be of type
   *                      String array (<code>String[]</code>).
   *
   * @exception	java.lang.IllegalArgumentException 
   *                      if parameters is <code>null</code>, if
   *                      any of the key/values in the Map are <code>null</code>, 
   *                      if any of the keys is not a String, or if any of 
   *                      the values is not a String array.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameters(java.util.Map parameters);


  /**
   * Sets a String parameter for the render request.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>PortletRequest.getParameter</code> call until
   * a request is targeted to the portlet.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   *
   * @param  key    key of the render parameter
   * @param  value  value of the render parameter
   *
   * @exception	java.lang.IllegalArgumentException	
   *                      if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameter(String key, String value);


  /**
   * Sets a String array parameter for the render request.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>PortletRequest.getParameter</code> call until
   * a request is targeted to the portlet.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   * 
   * @param  key     key of the render parameter
   * @param  values  values of the render parameter
   *
   * @exception	java.lang.IllegalArgumentException	
   *                      if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameter(String key, String[] values);
  
  public void setCallMethod(int callMethod);
  
  //public void setSecure(boolean secure);
  
  public void setAction(java.lang.String action);
  
  public void setMode(String mode);
  
  public String getLocaleString(String key) throws SWBResourceException;

  public String getLocaleLogString(String key) throws SWBResourceException;
}
