package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SemPromo extends org.semanticwb.portal.resources.sem.base.SemPromoBase 
{

    public SemPromo()
    {
    }

   /**
   * Constructs a SemPromo with a SemanticObject
   * @param base The SemanticObject with the properties for the SemPromo
   */
    public SemPromo(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello SemPromo...");    }

}
