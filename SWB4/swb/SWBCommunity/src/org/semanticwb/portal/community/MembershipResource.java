/**
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
*
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
* del SemanticWebBuilder 4.0.
*
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
* de la misma.
*
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
* dirección electrónica:
*  http://www.semanticwebbuilder.org
**/

package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class MembershipResource extends org.semanticwb.portal.community.base.MembershipResourceBase 
{
    private static Logger log=SWBUtils.getLogger(MembershipResource.class);

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
        

        String act=request.getParameter("act");
        if(act==null)act="view";
        String path="/swbadmin/jsp/microsite/MembershipResource/membershipResView.jsp";
//        if(act.equals("add"))path="/swbadmin/jsp/microsite/MembershipResource/membershipResAdd.jsp";
//        if(act.equals("edit"))path="/swbadmin/jsp/microsite/MembershipResource/membershipResEdit.jsp";
//        if(act.equals("detail"))path="/swbadmin/jsp/microsite/MembershipResource/membershipResDetail.jsp";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}

        
    }

    private Member getMember(User user, MicroSite site)
    {
        //System.out.println("getMember:"+user+" "+site);
        if(site!=null)
        {
            Iterator<Member> it=Member.listMemberByUser(user,site.getWebSite());
            while(it.hasNext())
            {
                Member mem=it.next();
                //System.out.println("mem:"+mem+" "+mem.getMicroSite());
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
        //System.out.println("user:"+user);
        String action=request.getParameter("act");
        //System.out.println("act:"+action);
        if("subscribe".equals(action))
        {
            Member member=Member.createMember(page.getWebSite());
            member.setAccessLevel(Member.LEVEL_EDIT);
            member.setUser(user);
            member.setMicroSite(MicroSite.getMicroSite(page));
            //System.out.println("member:"+member);
        }else if("unsubscribe".equals(action))
        {
            Member member=Member.getMember(user, page);
            member.remove();
        }
    }

}
