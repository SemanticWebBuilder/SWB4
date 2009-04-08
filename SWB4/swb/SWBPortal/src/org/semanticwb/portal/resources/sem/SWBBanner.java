package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SWBBanner extends org.semanticwb.portal.resources.sem.base.SWBBannerBase 
{

    public SWBBanner()
    {
    }

    public SWBBanner(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello SWBBanner...");    }

}
