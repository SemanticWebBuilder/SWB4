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

import javax.portlet.PortletPreferences;
import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBParameters;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class ActionRequestImp extends PortletRequestImp implements ActionRequest
{
    private PortletPreferences portletPreferences;
    private WBPortletDefinition portletDefinition;
    
    /** Creates a new instance of RenderRequestImp */
    public ActionRequestImp(HttpServletRequest request, SWBParameters paramsRequest, WBPortletDefinition portletDefinition, boolean removeParameters)
    {
        super(request,paramsRequest,portletDefinition, removeParameters);
        this.portletDefinition=portletDefinition;
    }    
    
    /**
     * Returns the preferences object associated with the portlet.
     *
     * @return the portlet preferences
     */
    @Override
    public PortletPreferences getPreferences()
    {
        if(portletPreferences==null)
        {
            portletPreferences=new PortletPreferencesImp(portletDefinition,paramsRequest,1);
        }
        return portletPreferences;
    }
    
    public java.io.InputStream getPortletInputStream() throws java.io.IOException
    {
        javax.servlet.http.HttpServletRequest servletRequest = (javax.servlet.http.HttpServletRequest) super.getRequest();

        if (servletRequest.getMethod().equals("POST"))
        {
            String contentType=servletRequest.getContentType();
            if (contentType==null||contentType.equals("application/x-www-form-urlencoded"))
            {
                throw new java.lang.IllegalStateException(
                                                         "User request HTTP POST data is of type application/x-www-form-urlencoded. This data has been already processed by the portal/portlet-container and is available as request parameters."
                                                         );
            }
        }
        return servletRequest.getInputStream();        
    }
    
}
