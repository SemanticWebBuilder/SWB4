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
package org.semanticwb.portal.api;


// TODO: Auto-generated Javadoc
/**
 * Interfase que da acceso al Response del recurso cuando es llamado por el
 * ActionResponse.
 * @author Javier Solis Gonzalez
 */
public interface SWBActionResponse extends SWBParameters
{
    


  /**
   * Instructs the resource container to send a redirect response
   * to the client using the specified redirect location URL.
   * <p>
   * This method only accepts an absolute URL (e.g.
   * <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
   * or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
   * If required,
   * the resource container may encode the given URL before the
   * redirection is issued to the client.
   * <p>
   * The sendRedirect method can not be invoked after any of the
   * following methods of the ActionResponse interface has been called:
   * <ul>
   * <li>setResourceMode
   * <li>setWindowState
   * <li>setRenderParameter
   * <li>setRenderParameters
   * </ul>
   * 
   * @param location the location
   * @param		location	the redirect location URL
   * @exception	java.io.IOException
   * if an input or output exception occurs.
   * @exception	java.lang.IllegalArgumentException
   * if a relative path URL is given
   * @exception java.lang.IllegalStateException
   * if the method is invoked after any of above mentioned methods of
   * the ActionResponse interface has been called.
   */

  public void sendRedirect(String location);
  
  /**
   * Gets the location.
   * 
   * @return the location
   */
  public String getLocation();

  
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
   * @param state the new window state
   * @exception WindowStateException
   * if the resource cannot switch to the specified window state.
   * To avoid this exception the resource can check the allowed
   * window states with <code>Request.isWindowStateAllowed()</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
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
   * <code>ResourceRequest.getParameter</code> call until
   * a new request is targeted to the resource.
   * <p>
   * The given parameters do not need to be encoded
   * prior to calling this method.
   * 
   * @param parameters the new render parameters
   * @exception	java.lang.IllegalArgumentException
   * if parameters is <code>null</code>, if
   * any of the key/values in the Map are <code>null</code>,
   * if any of the keys is not a String, or if any of
   * the values is not a String array.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameters(java.util.Map parameters);


  /**
   * Sets a String parameter for the render request.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>ResourceRequest.getParameter</code> call until
   * a request is targeted to the resource.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   * 
   * @param key the key
   * @param value the value
   * @exception	java.lang.IllegalArgumentException
   * if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameter(String key, String value);


  /**
   * Sets a String array parameter for the render request.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>ResourceRequest.getParameter</code> call until
   * a request is targeted to the resource.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   * 
   * @param key the key
   * @param values the values
   * @exception	java.lang.IllegalArgumentException
   * if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameter(String key, String[] values);
  
  /**
   * Sets the call method.
   * 
   * @param callMethod the new call method
   */
  public void setCallMethod(int callMethod);
  
  //public void setSecure(boolean secure);
  
  /**
   * Sets the action.
   * 
   * @param action the new action
   */
  public void setAction(java.lang.String action);
  
  /**
   * Sets the mode.
   * 
   * @param mode the new mode
   */
  public void setMode(String mode);
  
  /**
   * Gets the locale string.
   * 
   * @param key the key
   * @return the locale string
   * @throws SWBResourceException the sWB resource exception
   */
  public String getLocaleString(String key) throws SWBResourceException;

  /**
   * Gets the locale log string.
   * 
   * @param key the key
   * @return the locale log string
   * @throws SWBResourceException the sWB resource exception
   */
  public String getLocaleLogString(String key) throws SWBResourceException;
}
