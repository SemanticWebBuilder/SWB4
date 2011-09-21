package org.semanticwb.portal.resources.sem.catalog;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SWBCatalogResource extends org.semanticwb.portal.resources.sem.catalog.base.SWBCatalogResourceBase 
{

    public SWBCatalogResource()
    {
    }

   /**
   * Constructs a SWBCatalogResource with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBCatalogResource
   */
    public SWBCatalogResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello SWBCatalogResource...");    }

}
