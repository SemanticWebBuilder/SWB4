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

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * Intefase que extiende de WBParameter la cual contiene los objetos que son pasados
 * al recurso como parametros.
 * Ejemplo: Topic, WBUser..
 * @author Javier Solis Gonzalez
 */
public interface SWBParamRequest extends SWBParameters
{
    
    /**
     * Gets the arguments.
     * 
     * @return the arguments
     */
    public Map getArguments();

    /**
     * Gets the argument.
     * 
     * @param key the key
     * @return the argument
     */
    public String getArgument(String key);

    /**
     * Gets the argument.
     * 
     * @param key the key
     * @param defvalue the defvalue
     * @return the argument
     */
    public String getArgument(String key, String defvalue);
    
    /**
     * Gets the action url.
     * 
     * @return the action url
     */
    public SWBResourceURL getActionUrl();
    
    /**
     * Gets the render url.
     * 
     * @return the render url
     */
    public SWBResourceURL getRenderUrl();
    
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
    
    /**
     * Gets the window title.
     * 
     * @return the window title
     */
    public java.lang.String getWindowTitle();
    
    /**
     * Sets the window title.
     * 
     * @param windowTitle the new window title
     */
    public void setWindowTitle(java.lang.String windowTitle);
    
    /**
     * Sets the template head.
     * 
     * @param templateHead the new template head
     */
    public void setTemplateHead(String templateHead);
}
