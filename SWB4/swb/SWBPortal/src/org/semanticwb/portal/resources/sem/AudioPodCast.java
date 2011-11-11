package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class AudioPodCast extends org.semanticwb.portal.resources.sem.base.AudioPodCastBase 
{

    public AudioPodCast()
    {
    }

   /**
   * Constructs a AudioPodCast with a SemanticObject
   * @param base The SemanticObject with the properties for the AudioPodCast
   */
    public AudioPodCast(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello AudioPodCast...");    }

}
