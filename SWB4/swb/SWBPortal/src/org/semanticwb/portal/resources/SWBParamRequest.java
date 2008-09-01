
/*
 * WBResRequest.java
 *
 * Created on 1 de junio de 2004, 01:41 PM
 */

package org.semanticwb.portal.resources;

import java.util.*;
import org.semanticwb.SWBException;

/**
 * Intefase que extiende de WBParameter la cual contiene los objetos que son pasados
 * al recurso como parametros.
 * Ejemplo: Topic, WBUser..
 * @author Javier Solis Gonzalez
 */
public interface SWBParamRequest extends SWBParameters
{
    public Map getArguments();

    public String getArgument(String key);

    public String getArgument(String key, String defvalue);
    
    public SWBResourceURL getActionUrl();
    
    public SWBResourceURL getRenderUrl();
    
    public String getLocaleString(String key) throws SWBException;

    public String getLocaleLogString(String key) throws SWBException;
    
    public java.lang.String getWindowTitle();
    
    public void setWindowTitle(java.lang.String windowTitle);
    
    public void setTemplateHead(String templateHead);
}
