/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.SWBFormMgr;

/**
 *
 * @author javier.solis
 */
public class GenericSemResource extends GenericResource
{
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        SWBFormMgr mgr=new SWBFormMgr(getResourceBase().getResourceData(), null, SWBFormMgr.MODE_EDIT);
        mgr.setAction(paramRequest.getActionUrl().toString());
        out.print(mgr.renderForm(request));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        SWBFormMgr mgr=new SWBFormMgr(getResourceBase().getResourceData(), null, SWBFormMgr.MODE_EDIT);
        mgr.processForm(request);
    }

}
