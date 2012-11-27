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
package org.semanticwb.portal.portlet.factory;

import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import javax.portlet.PortalContext;
import javax.servlet.ServletContext;
import org.semanticwb.portal.portlet.WBPortletContainer;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBFactoryMgr {
    private static WBPortletContainer portletContainer = null;
    private static PortalContext portalContext = null;
    
    private static HashMap contexts=new HashMap();

    public static WBPortletContainer getPortletContainer() {
        return portletContainer;
    }

    public static void setPortletContainer(WBPortletContainer container) {
        portletContainer = container;
    }

    public static PortalContext getPortalContext() {
        return portalContext;
    }

    public static void setPortalContext(PortalContext context) {
        portalContext = context;
    }
    
    public static void addServletContext(ServletContext context)
    {
        try
        {
            URL url=context.getResource("/");
            String path=url.toString();
            if(path.endsWith("/"))path=path.substring(0,path.length()-1);
            path=path.substring(path.lastIndexOf("/")+1);
            //System.out.println("host:"+url.getHost());
            System.out.println("**************************************************");
            System.out.println("Context Path:"+path);
            //path=context.getServletContextName();
            //System.out.println("Context Name:"+path);
            System.out.println("**************************************************");
            contexts.put(path, context);
        }catch(Exception e){e.printStackTrace();}
    }
        
    
    public static void removeServletContext(ServletContext context)
    {
        removeServletContext(context.getServletContextName());
    }    
    
    
    public static void removeServletContext(String contextName)
    {
        contexts.remove(contextName);
    }    
    
    public static ServletContext getServletContext(String contextName)
    {
        return (ServletContext)contexts.get(contextName);
    }    
    
    public static Set getServletContextNames()
    {
        return contexts.keySet();
    }
}
