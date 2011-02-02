package org.semanticwb.resources.sem.forumcat;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

public class PublishQuestion extends org.semanticwb.resources.sem.forumcat.base.PublishQuestionBase 
{
    private Logger log = SWBUtils.getLogger(PublishQuestion.class);
    public PublishQuestion() {
    }

   /**
   * Constructs a PublishQuestion with a SemanticObject
   * @param base The SemanticObject with the properties for the PublishQuestion
   */
    public PublishQuestion(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/forumCat/publishQuestion.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        try {
            request.setAttribute("paramRequest", paramRequest);
            if (getForumCatID() != null) {
                SWBForumCatResource res = (SWBForumCatResource) SWBPortal.getResourceMgr().getResource(paramRequest.getWebPage().getWebSiteId(), getForumCatID());
                if (res != null) {
                    request.setAttribute("forumResource", res);
                }
            }
            rd.include(request, response);
        }catch (Exception e) {
            log.error(e);
        }
    }
}