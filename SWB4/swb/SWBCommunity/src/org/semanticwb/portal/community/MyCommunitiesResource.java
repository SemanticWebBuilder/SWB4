package org.semanticwb.portal.community;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

public class MyCommunitiesResource extends org.semanticwb.portal.community.base.MyCommunitiesResourceBase 
{
    private static Logger log=SWBUtils.getLogger(MyCommunitiesResource.class);

    public MyCommunitiesResource()
    {
    }

    public MyCommunitiesResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        String path="/scripts/microsite/listCommunities.groovy";

        request.setAttribute("paramRequest", paramRequest);
        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            dis.forward(request, response);
        }catch(Exception e){log.error(e);}
    }

}
