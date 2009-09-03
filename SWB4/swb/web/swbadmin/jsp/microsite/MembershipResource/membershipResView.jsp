<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%!
    private Member getMember(User user, MicroSite site)
    {
        if(site!=null)
        {
            Iterator<Member> it=Member.listMemberByUser(user,site.getWebSite());
            while(it.hasNext())
            {
                Member mem=it.next();
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

    String pathPhoto = "/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg";

    if(site.getPhoto()!=null)
    {
        pathPhoto=SWBPlatform.getContextPath()+SWBPlatform.getWebWorkPath()+site.getPhoto();
    }


    Member member=getMember(user, site);
    if(wp.getSemanticObject().getGenericInstance() instanceof MicroSite)
    {
%>
<div id="leftProfile">
    <p><img src="<%=pathPhoto%>" alt="Imagen comunidad"  ></p>

<%

     if(null!=member&&member.getAccessLevel()==Member.LEVEL_OWNER&&user.isRegistered())
     {
         SWBResourceURL urle = paramRequest.getRenderUrl();
         urle.setParameter("act", "edit");
         //urle.setCallMethod(SWBResourceURL.Call_DIRECT);
%>
    <p class="addOn"><a href="<%=urle%>">Cambiar imagen</a></p>
<%
    }
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
        <div class="clear">&nbsp;</div>
<%
        out.println("<h2 class=\"tituloGrande\">"+site.getDisplayName()+"</h2>");
        out.println("<p class=\"tituloGrande\"><img src=\"/swbadmin/jsp/microsite/MembershipResource/solidLine.jpg\" alt=\"\" width=\"495\" height=\"1\" ></p>");
        if(site.getDescription()!=null)
        {
            out.println("<p>"+site.getDescription()+"</p>");
        }
        if(site.getTags()!=null&&site.getTags().trim().length()>0&&!site.getTags().equals("null")) out.println("<p>Palabras clave: "+site.getTags()+"</p>");
        out.println("<p>Creador: "+site.getCreator().getFullName()+"</p>");
        out.println("<p>Creada: "+SWBUtils.TEXT.getTimeAgo(site.getCreated(),user.getLanguage())+"</p>");
        out.println("<p>Modificada: "+SWBUtils.TEXT.getTimeAgo(site.getUpdated(),user.getLanguage())+"</p>");
%>
</div>
<%
    }
%>