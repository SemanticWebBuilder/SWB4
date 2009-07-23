
/*
 * ActionRequestImp.java
 *
 * Created on 5 de septiembre de 2005, 11:43 AM
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
