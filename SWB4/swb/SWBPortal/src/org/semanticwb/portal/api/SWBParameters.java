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

import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

// TODO: Auto-generated Javadoc
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
  public WebPage getWebPage();
  
  /**
   * Gets the admin topic.
   * 
   * @return the admin topic
   */
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

  /**
   * Gets the resource base.
   * 
   * @return the resource base
   */
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
     * @return the window state
     * @exception WindowStateException
     * if the resource cannot switch to the specified window state.
     * To avoid this exception the resource can check the allowed
     * window states with <code>Request.isWindowStateAllowed()</code>.
     * @exception java.lang.IllegalStateException
     * if the method is invoked after <code>sendRedirect</code> has been called.
     * @see WindowState
     */
    
    //public void setWindowState(String state);
    
    public String getWindowState();
    
    /**
     * Gets the call method.
     * 
     * @return the call method
     */
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
     * state in the next render call, depending on the resource container / portal.
     * 
     * @return the mode
     * @exception ResourceModeException
     * if the resource cannot switch to this resource mode,
     * because the resource or portal does not support it for this markup,
     * or the current user is not allowed to switch to this resource mode.
     * To avoid this exception the resource can check the allowed
     * resource modes with <code>Request.isResourceModeAllowed()</code>.
     * @exception java.lang.IllegalStateException
     * if the method is invoked after <code>sendRedirect</code> has been called.
     */
    
    //public void setMode(String mode);

    public String getMode();
    
    /**
     * Checks if is secure.
     * 
     * @return true, if is secure
     */
    public boolean isSecure();
    
    //public void setSecure(boolean secure);    
    
    /** Getter for property action.
    * @return Value of property action.
    *
    */
    public java.lang.String getAction();

    /**
     * Setter for property action.
     * 
     * @return true, if successful
     */
    //public void setAction(java.lang.String action);
  
    public boolean haveVirtualWebPage();
}
