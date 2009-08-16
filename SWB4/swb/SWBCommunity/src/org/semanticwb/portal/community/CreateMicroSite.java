/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author javier.solis
 */
public class CreateMicroSite extends GenericResource
{
    private static Logger log=SWBUtils.getLogger(CreateMicroSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String act=request.getParameter("act");
        if(act==null)act="view";
        String path="/swbadmin/jsp/microsite/CreateMicroSite/view.jsp";
        if(act.equals("add"))path="/swbadmin/jsp/microsite/CreateMicroSite/add.jsp";
        if(act.equals("edit"))path="/swbadmin/jsp/microsite/CreateMicroSite/edit.jsp";
        if(act.equals("detail"))path="/swbadmin/jsp/microsite/CreateMicroSite/detail.jsp";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException 
    {
        WebPage page=response.getWebPage();
        String action=request.getParameter("act");
        System.out.println("act:"+action);
        if("add".equals(action))
        {
            String title=request.getParameter("title");
            String description=request.getParameter("description");
            String utils[]=request.getParameterValues("utils");
            System.out.println("title:"+title);
            System.out.println("description:"+description);
            System.out.println("utils:"+utils);
        }
        else super.processAction(request, response);
    }

}
