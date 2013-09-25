package org.semanticwb.bsc.admin.resources;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class DetailViewManager extends org.semanticwb.bsc.admin.resources.base.DetailViewManagerBase 
{

    public DetailViewManager()
    {
    }

   /**
   * Constructs a DetailViewManager with a SemanticObject
   * @param base The SemanticObject with the properties for the DetailViewManager
   */
    public DetailViewManager(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello DetailViewManager...");    }

}
