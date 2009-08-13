package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class MembershipResource extends org.semanticwb.portal.community.base.MembershipResourceBase 
{

    public MembershipResource()
    {
    }

    public MembershipResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello MembershipResource...");    }

}
