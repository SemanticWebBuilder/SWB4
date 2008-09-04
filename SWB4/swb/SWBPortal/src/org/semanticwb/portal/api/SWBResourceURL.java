
/*
 * WBResourceURL.java
 *
 * Created on 8 de junio de 2004, 12:57 PM
 */

package org.semanticwb.portal.api;

/**
 * Interfase que sirve para generar un URL valido por el portal para el recursos.
 * @author Javier Solis Gonzalez
 */
public interface SWBResourceURL extends SWBResourceModes
{
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
    
    public SWBResourceURL setWindowState(String state);
    
    public String getWindowState();
    
    public int getCallMethod();
    
    public SWBResourceURL setCallMethod(int callMethod);
    
    
    
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
    
    public SWBResourceURL setMode(String mode);
    
    public String getMode();
    
    public boolean isSecure();
    
    public SWBResourceURL setSecure(boolean secure);
    
    /** Getter for property action.
     * @return Value of property action.
     *
     */
    public java.lang.String getAction();
    
    /** Setter for property action.
     * @param action New value of property action.
     *
     */
    public SWBResourceURL setAction(java.lang.String action);

    
    //public void setURLType(int type);
    //public int getURLType();
    //public com.infotec.topicmaps.Topic getTopic();
    //public com.infotec.wb.core.Resource getResourceBase();
    
    
    /**
     * Returns the portlet URL string representation to be embedded in the
     * markup.<br>
     * Note that the returned String may not be a valid URL, as it may
     * be rewritten by the portal/portlet-container before returning the
     * markup to the client.
     *
     * @return   the encoded URL as a string
     */
    public String toString();
    
    /**
     * Regresa url del recurso
     * @param encodeAmp boolean codifica "&" por "&amp;"
     * @return String url
     */
    public String toString(boolean encodeAmp);
    
    
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
    
    public void setParameters(java.util.Map parameters);
    
    
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
    
    public void setParameter(String key, String value);
    
    
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
    
    public void setParameter(String key, String[] values);
    
    
    /**
     *    Regresa tipo de URL (render, action)
     *    Render = 0
     *    Action = 1
     */
    public int getURLType();
    
    /**
     * Getter for property onlyContent.
     * @return Value of property onlyContent.
     */
    public boolean isOnlyContent();
    
    /**
     * Setter for property onlyContent.
     * @param onlyContent New value of property onlyContent.
     */
    public void setOnlyContent(boolean onlyContent);
    
}
