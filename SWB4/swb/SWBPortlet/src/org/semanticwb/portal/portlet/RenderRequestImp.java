/*
 * RenderRequestImp.java
 *
 * Created on 5 de septiembre de 2005, 11:33 AM
 */

package org.semanticwb.portal.portlet;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBParameters;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class RenderRequestImp extends PortletRequestImp implements RenderRequest
{
    private PortletPreferences portletPreferences;
    private WBPortletDefinition portletDefinition;
    
    /** Creates a new instance of RenderRequestImp */
    public RenderRequestImp(HttpServletRequest request, SWBParameters paramsRequest, WBPortletDefinition portletDefinition, boolean removeParameters)
    {
        super(request,paramsRequest,portletDefinition,removeParameters);
        this.portletDefinition=portletDefinition;
    }    
    
    /**
     * Returns the preferences object associated with the portlet.
     *
     * @return the portlet preferences
     */
    public PortletPreferences getPreferences()
    {
        if(portletPreferences==null)
        {
            portletPreferences=new PortletPreferencesImp(portletDefinition, paramsRequest,0);
        }
        return portletPreferences;
    }
    
}
