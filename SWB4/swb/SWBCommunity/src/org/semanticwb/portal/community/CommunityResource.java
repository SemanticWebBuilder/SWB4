package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class CommunityResource extends org.semanticwb.portal.community.base.CommunityResourceBase 
{

    public CommunityResource()
    {
    }

    public CommunityResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello CommunityResource...");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("returnRank")) {
            returnRank(request, response);
        } else if (paramRequest.getMode().equals("returnAbusedState")) {
            returnAbusedState(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage page=response.getWebPage();
        Member mem=Member.getMember(response.getUser(),page);
        if(!mem.canView())return;                                       //si el usuario no pertenece a la red sale;

        String action=request.getParameter("act");
        System.out.println("act:"+action);
        if("subscribe".equals(action))
        {
            if(page instanceof MicroSiteWebPageUtil)((MicroSiteWebPageUtil)page).subscribeToElement(mem);
        }else if("unsubscribe".equals(action))
        {
            if(page instanceof MicroSiteWebPageUtil)((MicroSiteWebPageUtil)page).unSubscribeFromElement(mem);
        } else if ("vote".equals(action)) {
            rank(request, response);
        } else if ("abuseReport".equals(action)) {
            abusedStateChange(request, response);
        } else if ("addComment".equals(action)) {
            addComment(request, response, mem);
        }
    }

    private void rank(HttpServletRequest request, SWBActionResponse response) {

        String suri = request.getParameter("uri");
        SemanticObject so = null;
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof MicroSiteElement) {
            MicroSiteElement mse = (MicroSiteElement) so.getGenericInstance();
            int vote = 0;
            try {
                vote = Integer.parseInt(request.getParameter("value"));
            } catch (Exception ne) {}
            double rank = mse.getRank();
            long rev = mse.getReviews();
            response.setRenderParameter("uri", suri);

            rank = rank * rev;
            rev++;
            rank = rank + vote;
            rank = rank / rev;

            mse.setRank(rank);
            mse.setReviews(rev);
        }
        response.setMode("returnRank");
    }

    private void abusedStateChange(HttpServletRequest request,
            SWBActionResponse response) {

        String message = null;
        String suri = request.getParameter("uri");
        SemanticObject so = null;
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof MicroSiteElement) {
            MicroSiteElement mse = (MicroSiteElement) so.getGenericInstance();
            if (mse.isAbused()) {
                mse.setAbused(false);
            } else {
                mse.setAbused(true);
            }
            message = Boolean.toString(mse.isAbused());
        }
        response.setMode("returnAbusedState");
        response.setRenderParameter("message",
                                    message != null ? message : "Not OK");
    }

    private void addComment(HttpServletRequest request,
            SWBActionResponse response, Member mem) {

        String suri = request.getParameter("uri");
        String desc = request.getParameter("comentario");
        SemanticObject so = null;
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (desc != null) {
            desc = "";
        }
        if (so.getGenericInstance() instanceof MicroSiteElement) {
            MicroSiteElement mse = (MicroSiteElement) so.getGenericInstance();
            if (mse.canComment(mem) && desc.length() > 0) {
                SemanticObject semObj = MicroSiteElement.swbcomm_Comment.newInstance(MicroSiteElement.swbcomm_Comment.getURI());
                if (semObj.getGenericInstance() instanceof Comment) {
                    Comment comment = (Comment) semObj.getGenericInstance();
                    comment.setDescription(desc);
                    mse.addComment(comment);
                }
            }
        }
        response.setRenderParameter("uri", suri);
        response.setRenderParameter("act", "detail");
        response.setMode(SWBParamRequest.Mode_VIEW);
    }

    private void returnRank(HttpServletRequest request,
            HttpServletResponse response) {

        String message = null;
        String suri = request.getParameter("uri");
        SemanticObject so = null;
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof MicroSiteElement) {
            MicroSiteElement mse = (MicroSiteElement) so.getGenericInstance();
            message = mse.getRank() + "," + mse.getReviews();
        }
        try {
            response.getWriter().print(message != null ? message : "Not OK");
        } catch (IOException ioe) {}
    }

    private void returnAbusedState(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest) {

        String message = request.getParameter("message");
        try {
            response.getWriter().print(message != null ? message : "Not OK");
        } catch (IOException ioe) {}
    }

}
