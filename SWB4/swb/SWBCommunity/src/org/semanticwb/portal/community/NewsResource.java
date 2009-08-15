package org.semanticwb.portal.community;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

public class NewsResource extends org.semanticwb.portal.community.base.NewsResourceBase 
{
    private static Logger log = SWBUtils.getLogger(NewsResource.class);
    public NewsResource()
    {
    }

    public NewsResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action = request.getParameter("act");
        action = (action == null ? "view" : action);

        String path = "/swbadmin/jsp/microsite/NewsResource/newsView.jsp";
        if (action.equals("add")) {
            path = "/swbadmin/jsp/microsite/NewsResource/newsAdd.jsp";
        } else if (action.equals("edit")) {
            path = "/swbadmin/jsp/microsite/NewsResource/newsEdit.jsp";
        } else if (action.equals("detail")) {
            path = "/swbadmin/jsp/microsite/NewsResource/newsDetail.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
