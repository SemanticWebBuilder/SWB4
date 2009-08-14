package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class CommunityResource extends org.semanticwb.portal.community.base.CommunityResourceBase 
{

    public CommunityResource()
    {
    }

    public CommunityResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello CommunityResource...");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage page=response.getWebPage();
        Member mem=Member.getMember(response.getUser(),page);
        if(!mem.canView())return;                                       //si el usuario no pertenece a la red sale;

        String action=request.getParameter("act");
        System.out.println("act:"+action);
        if("subscribe".equals(action))
        {
            if(page instanceof MicroSiteWebPageUtil)((MicroSiteWebPageUtil)page).subscribeToElement(mem);
        }else if("unsubscribe".equals(action))
        {
            if(page instanceof MicroSiteWebPageUtil)((MicroSiteWebPageUtil)page).unSubscribeFromElement(mem);
        }
    }

}
