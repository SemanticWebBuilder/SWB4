package org.semanticwb.resources.sem.forumcat;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SWBForumCatResource extends org.semanticwb.resources.sem.forumcat.base.SWBForumCatResourceBase 
{

    public SWBForumCatResource()
    {
    }

    public SWBForumCatResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello SWBForumCatResource...");    }

}
