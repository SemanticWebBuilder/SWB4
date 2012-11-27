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
package org.semanticwb.portal.portlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;


/**
 *
 * @author Javier Solis Gonzalez
 */
public interface WBPortletContainer
{
    final static String ATT_PORTLET_DEFINITION = "com.infotec.wb.portlet.PortletDefinition";
    final static String ATT_PORTLET = "com.infotec.wb.portlet.Portlet";
    final static String ATT_METHOD = "com.infotec.wb.portlet.Method";
    final static String ATT_PORTLET_REQUEST = "javax.portlet.request";
    final static String ATT_PORTLET_RESPONSE = "javax.portlet.response";
    final static String ATT_PORTLET_CONFIG = "javax.portlet.config";
    final static Integer VAL_METHOD_RENDER = new Integer(0);
    final static Integer VAL_METHOD_ACTION = new Integer(1);
    final static Integer VAL_METHOD_LOAD = new Integer(2);
    final static Integer VAL_METHOD_INIT = new Integer(3);
    
    public Portlet loadPortlet(WBPortletDefinition def, HttpServletRequest request);
    
    public WBPortletDefinition getPortletDefinition(String site, String id);
    
    public void removePortletDefinition(String site, String id);
    
    public void addPortletDefinition(WBPortletDefinition def);
    
    public void log(Throwable e);
    
    public void log(String msg);
    
    public void log(String msg, Throwable e);
    
    public void invoke_render(HttpServletRequest request,
    HttpServletResponse response, SWBParamRequest params,
    WBPortletDefinition def) throws ServletException, IOException;
    
    public void invoke_action(HttpServletRequest request,
    SWBActionResponse params, WBPortletDefinition def)
    throws ServletException, IOException;
}
