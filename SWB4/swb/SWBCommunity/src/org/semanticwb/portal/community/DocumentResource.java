package org.semanticwb.portal.community;

import java.io.*;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class DocumentResource extends org.semanticwb.portal.community.base.DocumentResourceBase {
    private static Logger log = SWBUtils.getLogger(PhotoResource.class);
    
    public DocumentResource()
    {
    }

    public DocumentResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String act=request.getParameter("act");
        if(act==null) {
            act = (String)request.getSession(true).getAttribute("act");
        }
        if(act==null) {
            act = "view";
        }        

        String path="/swbadmin/jsp/microsite/PhotoResource/photoView.jsp";
        if(act.equals("add"))path="/swbadmin/jsp/microsite/PhotoResource/photoAdd.jsp";
        if(act.equals("edit"))path="/swbadmin/jsp/microsite/PhotoResource/photoEdit.jsp";
        if(act.equals("detail"))path="/swbadmin/jsp/microsite/PhotoResource/photoDetail.jsp";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){
            log.error(e);
            System.out.println(e);
        }
    }

}
