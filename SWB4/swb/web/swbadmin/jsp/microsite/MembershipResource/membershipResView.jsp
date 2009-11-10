<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%!
    private Member getMember(User user, MicroSite site)
    {
        if (site != null)
        {
            Iterator<Member> it = Member.listMemberByUser(user, site.getWebSite());
            while (it.hasNext())
            {
                Member mem = it.next();
                if (mem.getMicroSite().equals(site))
                {
                    return mem;
                }
            }
        }
        return null;
    }
%>
<%

            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wp = paramRequest.getWebPage();
            SWBResourceURL urle = paramRequest.getRenderUrl();
            urle.setParameter("act", "edit");
            MicroSite site = MicroSite.getMicroSite(paramRequest.getWebPage());
            Member member = getMember(user, site);
            if (!(wp.getSemanticObject().getGenericInstance() instanceof MicroSite))
            {
                return;
            }            
            String pathPhoto = "/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg";

            if (site.getPhoto() != null)
            {
                pathPhoto = SWBPortal.getContextPath() + SWBPortal.getWebWorkPath() + site.getPhoto();
            }
%>

<div class="columnaIzquierda">    
    <%
            if (site.getDescription() != null)
            {
                %>
                <p><%=site.getDescription()%></p>
                <%

                
            }
            if (site.getTags() != null && site.getTags().trim().length() > 0 && !site.getTags().equals("null"))
            {
                %>
                <p>Palabras clave: <%=site.getTags()%></p>
                <%
            }
            %>
            <p>Creador: <%=site.getCreator().getFullName()%></p>
            <p>Creada: <%=SWBUtils.TEXT.getTimeAgo(site.getCreated(), user.getLanguage())%></p>
            <p>Modificada: <%=SWBUtils.TEXT.getTimeAgo(site.getUpdated(), user.getLanguage())%></p>
            
</div>

<div class="columnaCentro">
    <h2 class="blogTitle"><%=site.getTitle()%></h2>
    <p><img src="<%=pathPhoto%>" alt="Imagen comunidad"  ></p>
        <%
            if (null != member && member.getAccessLevel() == Member.LEVEL_OWNER && user.isRegistered())
            {

        %>
    <p><a href="<%=urle%>">[Cambiar imagen]</a></p>

    <%
            }
    %>
    <ul class="miContenido">
       <%
        SWBResourceURL urla = paramRequest.getActionUrl();
        if (user.isRegistered())
        {
            if (member == null)
            {
                urla.setParameter("act", "subscribe");
        %>
        <li><a href="<%=urla%>">Suscribirse a esta comunidad</a></li>
        <%
    }
    else
    {
        urla.setParameter("act", "unsubscribe");
        %>
        <li><a href="<%=urla%>">Cancelar suscripción</a></li>
        <%
    }
            
}
String pageUri="/swbadmin/jsp/microsite/rss/rss.jsp?comm="+java.net.URLEncoder.encode(wp.getURI());
            %>
            <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS </a></li>
            <%

%>
   
    </ul>
</div>
