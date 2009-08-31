<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%!

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

%>


<%

    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage wp=paramRequest.getWebPage();

    MicroSite site=MicroSite.getMicroSite(paramRequest.getWebPage());

    Member member=getMember(user, site);

    //out.println("User:"+user.getFullName());
%>

<div id="leftProfile">
    <p><img src="/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg" alt="Usuario" width="174" height="174" ></p>
    <p class="addOn"><a href="#">Cambiar imagen</a></p>
<%
    SWBResourceURL urla = paramRequest.getActionUrl();


    if(user.isRegistered())
    {
        if(member==null)
        {
            urla.setParameter("act","subscribe");
            out.println("<p class=\"addOn\"><a href=\""+urla+"\">Suscribirse a esta comunidad</a></p>");
        }else
        {
            urla.setParameter("act","unsubscribe");
            out.println("<p class=\"addOn\"><a href=\""+urla+"\">Cancelar suscripción</a></p>");
        }
    }

%>
</div>

<div id="centerProfile">
        <!--p class="editarInfo"><a href="#">Editar información</a></p -->
        <div class="clear">&nbsp;</div>
<%
        out.println("<h2 class=\"tituloGrande\">"+site.getDisplayName()+"</h2>");
        out.println("<p class=\"tituloGrande\"><img src=\"/swbadmin/jsp/microsite/MembershipResource/solidLine.jpg\" alt=\"\" width=\"495\" height=\"1\" ></p>");
        if(site.getDescription()!=null)
        {
            out.println("<p>"+site.getDescription()+"</p>");
        }
        out.println("<p>Palabras clave: "+site.getTags()+"</p>");
        out.println("<p>Creador: "+site.getCreator().getFullName()+"</p>");
        out.println("<p>Creada: "+SWBUtils.TEXT.getTimeAgo(site.getCreated(),user.getLanguage())+"</p>");
        out.println("<p>Modificada: "+SWBUtils.TEXT.getTimeAgo(site.getUpdated(),user.getLanguage())+"</p>");
%>

</div>







