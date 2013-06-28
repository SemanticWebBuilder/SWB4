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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SWBProxyMessageCenter;

/**
 *
 * @author serch
 */
public class SWBProxyAdminFilter implements Filter{

    static private Logger log = SWBUtils.getLogger(SWBProxyAdminFilter.class);
    static private SWBProxyAdminFilter instance;
    private FilterConfig filterConfig = null;
    private static Properties props = null;
    private SWBProxyMessageCenter center;
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        instance=this;
        props = SWBUtils.TEXT.getPropertyFile("/web.properties");
        log.event("************************************");
        log.event("Initializing SWBProxyAdmin...");
        this.filterConfig = filterConfig;
        String prefix = filterConfig.getServletContext().getRealPath("/");
        SWBUtils.createInstance(prefix);
        center = new SWBProxyMessageCenter();
        log.event("SWBProxyAdmin Started...");
        log.event("************************************");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.println("<html><title>Status</title><body>");
        out.println("<h2>Local:</h2><br><ul>");
        for (String addr: center.localAddresses()){
            out.println("<li>"+addr+"</li>");
        }
        out.println("</ul><br>");
        out.println("<h2>Remote:</h2><br><ul>");
        for (String addr: center.remoteAddresses()){
            out.println("<li>"+addr+"</li>");
        }
        out.println("</ul><br></body></html>");
    }

    @Override
    public void destroy() {
        center.destroy();
    }
    
    public static String getEnv(String name)
    {
        return getEnv(name, null);
    }
    
    public static String getEnv(String name, String defect)
    {
        String obj = null;

        obj=System.getProperty("swb.web."+name);
        if(obj!=null)return obj;
        if(props!=null)
        {
            obj = props.getProperty(name);
        }
        if (obj == null) return defect;
        return obj;
    }
    
}
