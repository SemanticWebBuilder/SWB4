package org.semanticwb.model.comm;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class Forum extends org.semanticwb.model.comm.base.ForumBase 
{

    public Forum()
    {
    }

    public Forum(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello Forum...");    }

}
