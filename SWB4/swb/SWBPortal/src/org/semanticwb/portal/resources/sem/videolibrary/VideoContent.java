package org.semanticwb.portal.resources.sem.videolibrary;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
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
    public String getPreview()
    {
        String code=SWBUtils.TEXT.decodeExtendedCharacters(this.getCode());
        String ret = null;
        //******************  is YouTube  ***********************************
        String pre = "http://www.youtube.com/v/";
        String post = "\">";
        int s = code.indexOf(pre);
        if (s >= 0)
        {
            int f = code.indexOf(post, s);
            if (f > s)
            {
                ret = code.substring(s + pre.length(), f);
                int a = ret.indexOf('&');
                if (a > 0)
                {
                    ret = ret.substring(0, a);
                }
            }
        }
        if (ret != null)
        {
            ret = "http://i.ytimg.com/vi/" + ret + "/default.jpg";
        }
        return ret;
    }
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBVideoLibrary/showvideo.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("content", this);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return;
    }

}
