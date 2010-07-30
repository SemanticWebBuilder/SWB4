package org.semanticwb.portal.resources.sem.videolibrary;


import java.io.IOException;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

public class VideoContent extends org.semanticwb.portal.resources.sem.videolibrary.base.VideoContentBase 
{
    private static final Logger log = SWBUtils.getLogger(VideoContent.class);
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
        String path = "/swbadmin/jsp/SWBVideoLibrary/showvideo.jsp";
    }

}
