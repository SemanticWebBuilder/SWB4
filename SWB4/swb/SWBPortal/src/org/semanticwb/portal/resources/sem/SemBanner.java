package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SemBanner extends org.semanticwb.portal.resources.sem.base.SemBannerBase 
{

    public SemBanner()
    {
    }

   /**
   * Constructs a SemBanner with a SemanticObject
   * @param base The SemanticObject with the properties for the SemBanner
   */
    public SemBanner(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello SemBanner...");    }

}
