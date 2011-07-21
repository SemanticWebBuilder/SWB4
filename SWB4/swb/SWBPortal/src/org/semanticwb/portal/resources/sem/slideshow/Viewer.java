package org.semanticwb.portal.resources.sem.slideshow;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class Viewer extends org.semanticwb.portal.resources.sem.slideshow.base.ViewerBase 
{

    public Viewer()
    {
    }

   /**
   * Constructs a Viewer with a SemanticObject
   * @param base The SemanticObject with the properties for the Viewer
   */
    public Viewer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello Viewer...");    }

}
