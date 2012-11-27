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
package org.semanticwb.servlet;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;



// TODO: Auto-generated Javadoc
/**
 * The Class SWBHttpServletRequestWrapper.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBBaseHttpServletRequestWrapper extends HttpServletRequestWrapper
{
    private String host=null;
    private String x_forwarded_for=null;
    private StringBuffer requestURL=null;
    private String serverName=null;
    private Integer serverPort=null;

    /**
     * Creates a new instance of WBHttpServletRequestWrapper.
     * 
     * @param request the request
     */
    public SWBBaseHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
        host=this.getHeader("X-Forwarded-Host");
        x_forwarded_for=this.getHeader("X-Forwarded-For");
    }
    
    @Override
    public StringBuffer getRequestURL()
    {
        if (host != null && host.length()>0)
        {
            if(requestURL==null)
            {
                String port = "";
                if (this.getServerPort() != 80)
                {
                    port = ":" + this.getServerPort();
                }
                StringBuffer buffer=new StringBuffer(this.getScheme());
                buffer.append("://");
                buffer.append(this.getServerName());
                buffer.append(port);
                buffer.append(this.getRequestURI());
                requestURL=buffer;
            }
            return requestURL;
        }
        return super.getRequestURL();
    }

    @Override
    public String getServerName()
    {
        if (host != null && host.length()>0)
        {
            if(serverName==null)
            {
                int pos = host.indexOf(":");
                if (pos == -1)
                {
                    serverName=host;
                }
                else
                {
                    serverName=host.substring(0, pos);
                }
            }
            return serverName;
        }
        return super.getServerName();//com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/distributor");
    }

    @Override
    public int getServerPort()
    {
        if (host != null && host.length()>0)
        {
            if(serverPort==null)
            {
                int pos = host.indexOf(":");
                if (pos == -1)
                {
                    serverPort=80;
                }
                else
                {
                    String port = host.substring(pos + 1);
                    try
                    {
                        serverPort=Integer.parseInt(port);
                    }
                    catch (NumberFormatException nfe)
                    {
                        serverPort=80;
                    }
                }
            }
            return serverPort;
        }
        return super.getServerPort();
    }

    @Override
    public String getRemoteAddr()
    {
        if (x_forwarded_for != null && x_forwarded_for.length()>0)
        {
            return x_forwarded_for;
        }
        return super.getRemoteAddr();
    }
    
}
