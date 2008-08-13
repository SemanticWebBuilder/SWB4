/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import java.io.IOException;

/**
 *
 * @author juan.fernandez
 */
public class SWBGenericPortlet extends javax.portlet.GenericPortlet{

    private static String ADMIN = "admin";
    @Override
    protected void doDispatch(RenderRequest renderReq, RenderResponse renderRes) throws PortletException, IOException {
        
        if(renderReq.getPortletMode().equals(ADMIN))
        {
            doAdmin(renderReq,renderRes);
        }
        else
        {
           super.doDispatch(renderReq, renderRes);
        }
    }


    protected void doAdmin(RenderRequest renderReq, RenderResponse renderRes) throws PortletException, IOException {
        
    }

    
}
