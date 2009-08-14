package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class MembershipResource extends org.semanticwb.portal.community.base.MembershipResourceBase 
{

    public MembershipResource()
    {
    }

    public MembershipResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        User user=paramRequest.getUser();
        WebPage page=paramRequest.getWebPage();
        MicroSite site=MicroSite.getMicroSite(paramRequest.getWebPage());
        
        Member member=getMember(user, site);

        out.println("User:"+user.getFullName());
        if(user.isRegistered())
        {
            if(member==null)
            {
                out.println("<a href=\""+paramRequest.getActionUrl().setParameter("act", "subscribe")+"\">Suscribirse a la comunidad...</a>");
            }else
            {
                out.println("<a href=\""+paramRequest.getActionUrl().setParameter("act", "unsubscribe")+"\">Eliminar Suscripción de la comunidad...</a>");
            }
        }
    }

    private Member getMember(User user, MicroSite site)
    {
        System.out.println("getMember:"+user+" "+site);
        if(site!=null)
        {
            Iterator<Member> it=Member.listMemberByUser(user,site.getWebSite());
            while(it.hasNext())
            {
                Member mem=it.next();
                System.out.println("mem:"+mem+" "+mem.getMicroSite());
                if(mem.getMicroSite().equals(site))
                {
                   return mem;
                }
            }
        }
        return null;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage page=response.getWebPage();
        User user=response.getUser();
        System.out.println("user:"+user);
        String action=request.getParameter("act");
        System.out.println("act:"+action);
        if("subscribe".equals(action))
        {
            Member member=Member.createMember(page.getWebSite());
            member.setAccessLevel(Member.LEVEL_ADMIN);
            member.setUser(user);
            member.setMicroSite(MicroSite.getMicroSite(page));
            System.out.println("member:"+member);
        }else if("unsubscribe".equals(action))
        {
            Member member=Member.getMember(user, page);
            member.remove();
        }
    }

}
