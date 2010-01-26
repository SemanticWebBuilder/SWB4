/**
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
**/


/*
 * WBParameters.java
 *
 * Created on 2 de junio de 2004, 01:50 PM
 */

package com.infotec.wb.lib;

import com.infotec.wb.core.*;
import com.infotec.topicmaps.*;

/**
 * Intefase con los parametros enviados al recurso.
 * @author Javier Solis Gonzalez
 */
public interface WBParameters extends WBResourceModes
{
  /** Getter for property topic.
   * @return Value of property topic.
   *
   */
  public Topic getTopic();
  
  public Topic getAdminTopic();
  
  /** Getter for property user.
   * @return Value of property user.
   *
   */
  public WBUser getUser();
  
  
  /** Getter for property userLevel.
   * @return Value of property userLevel.
   *
   */
  public int getUserLevel();

  public Resource getResourceBase();
  

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
