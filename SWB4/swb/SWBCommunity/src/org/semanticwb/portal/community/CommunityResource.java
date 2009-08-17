package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
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
        } else if (paramRequest.getMode().equals("returnStateMessage")) {
            returnStateMessage(request, response, paramRequest);
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
        } else if ("spamReport".equals(action)) {
            spamStateChange(request, response);
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
        response.setMode("returnStateMessage");
        response.setRenderParameter("message",
                                    message != null ? message : "Not OK");
    }

    private void addComment(HttpServletRequest request,
            SWBActionResponse response, Member mem) {

        String suri = request.getParameter("uri");
        String desc = request.getParameter("comentario");

        GenericObject gen=SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        if (desc == null) {
            desc = "";
        }
        if (gen!=null && gen instanceof MicroSiteElement)
        {
            MicroSiteElement mse = (MicroSiteElement) gen;
            if (mse.canComment(mem) && desc.length() > 0) 
            {
                Comment comment=Comment.createComment(response.getWebPage().getWebSite());
                comment.setDescription(desc);
                mse.addComment(comment);
            }
        }
        response.setRenderParameter("uri", suri);
        response.setRenderParameter("act", "detail");
        response.setMode(SWBParamRequest.Mode_VIEW);
    }

    private void spamStateChange(HttpServletRequest request,
            SWBActionResponse response) {

        String message = null;
        String suri = request.getParameter("uri");
        String commentId = request.getParameter("commentId");
        SemanticObject so = null;
        System.out.println("suri:" + suri + ", id:" + commentId );
        if (commentId == null) {
            return;
        }
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof MicroSiteElement) {
            MicroSiteElement mse = (MicroSiteElement) so.getGenericInstance();
            GenericIterator<Comment> iterator =  mse.listComments();
            while (iterator.hasNext()) {
                Comment comment = iterator.next();
                System.out.println("comment.Id:" + comment.getId() + ", comparacion:" + comment.getId().equals(commentId));
                if (comment.getId().equals(commentId)) {
                    if (comment.isSpam()) {
                        comment.setSpam(false);
                    } else {
                        comment.setSpam(true);
                    }
                    message = Boolean.toString(comment.isSpam());
                    System.out.println("message:" + message);
                    break;
                }
            }
        }
        response.setMode("returnStateMessage");
        response.setRenderParameter("message",
                                    message != null || "".equals(message)
                                    ? message : "Not OK");
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

    private void returnStateMessage(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest) {

        String message = request.getParameter("message");
        System.out.println("message en returnStateMessage:" + message);
        try {
            response.getWriter().print(message != null ? message : "Not OK");
        } catch (IOException ioe) {}
    }

}
