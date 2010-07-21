/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ProcessWebPage;
import org.semanticwb.process.model.SWBProcessMgr;

/**
 *
 * @author juan.fernandez
 */
public class CreateProcessInstance extends GenericResource{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url =  paramRequest.getActionUrl();
        url.setParameter("act", "cpi");
        out.println("<a href=\""+url.toString()+"\">"+paramRequest.getLocaleString("lblCreateInstance")+"</a>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        WebPage wp = response.getWebPage();
        User user=response.getUser();
        if(wp!=null&&wp instanceof ProcessWebPage)
        {
            org.semanticwb.process.model.Process process=SWBProcessMgr.getProcess(wp);
            String act=request.getParameter("act");
            if(act!=null)
            {
                if(act.equals("cpi"))
                {
                    SWBProcessMgr.createProcessInstance(process, user);
                }
            }
        }
    }


}
