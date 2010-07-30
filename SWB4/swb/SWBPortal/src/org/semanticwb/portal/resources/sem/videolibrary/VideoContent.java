package org.semanticwb.portal.resources.sem.videolibrary;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class VideoContent extends org.semanticwb.portal.resources.sem.videolibrary.base.VideoContentBase 
{

    public VideoContent()
    {
    }

    public VideoContent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        
    }

}
