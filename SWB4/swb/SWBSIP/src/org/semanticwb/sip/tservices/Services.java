package org.semanticwb.sip.tservices;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class Services extends org.semanticwb.sip.tservices.base.ServicesBase 
{
    private static Logger log = SWBUtils.getLogger(Services.class);
    
    public Services() { }

    public Services(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("ADD")) {
            doAdd(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/sip/services/listServices.jsp";

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("servList", listServices());
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/sip/services/addService.jsp";

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        WebSite model = response.getWebPage().getWebSite();
        User user = response.getUser();

        /*if (!user.isRegistered() && !user.isSigned()) {
            return;
        }*/
                
        if (action.equals("ADD")) {
            String title = request.getParameter("title");
            String link = request.getParameter("link");
            String icon = request.getParameter("cssIcon");

            if (!title.trim().equals("") && !link.trim().equals("") && !icon.trim().equals("")) {
                Service srv = Service.ClassMgr.createService(model);
                srv.setTitle(title);
                srv.setUrl(link);
                srv.setCssIcon(icon);

                addService(srv);
            }
        } else if (action.equals("DEL")) {
            String uri = request.getParameter("uri");
            SemanticObject so = SemanticObject.createSemanticObject(uri);
            if (so != null) {
                Service srv = (Service)so.createGenericInstance();
                removeService(srv);
            }
        }
        response.setMode(response.Mode_VIEW);
    }

}
