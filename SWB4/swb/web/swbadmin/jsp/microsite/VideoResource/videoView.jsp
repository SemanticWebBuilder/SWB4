<%@page contentType="text/html"%>
<%@page import="java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String suscribeURL = paramRequest.getActionUrl().setParameter("act", "subscribe").toString();
            String unsuscribeURL = paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString();
            String urlAddVideo = paramRequest.getRenderUrl().setParameter("act", "add").toString();
%>

<div class="columnaIzquierda">
    <div class="adminTools">

        <%
            if (member.canAdd())
            {
        %>
        <a class="adminTool" href="<%=urlAddVideo%>">Agregar video</a>
        <%
       }
       if (wputil != null && member.canView())
       {
           if (!wputil.isSubscribed(member))
           {
        %>
        <a class="adminTool" href="<%=suscribeURL%>">Suscribirse a este elemento</a>
        <%
                }
                else
                {
        %>
        <a class="adminTool" href="<%=unsuscribeURL%>">Calcelar suscripción</a>
        <%
                }
            }
        %>


    </div>
    <%
            Iterator<VideoElement> it = VideoElement.listVideoElementByWebPage(wpage, wpage.getWebSite());
            int i = 0;
            while (it.hasNext())
            {
                VideoElement video = it.next();
                SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", video.getURI());
                if (video.canView(member))
                {
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                    String rank = df.format(video.getRank());
                    String editEventURL = paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", video.getURI()).toString();

    %>
    <div class="noticia">
        <img src="<%=video.getPreview()%>" alt="<%= video.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=video.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=video.getCreator().getFullName()%><br><%=dateFormat.format(video.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(video.getCreated(), user.getLanguage())%></p>
            <p>
                <%=video.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
                 <%
                    if(video.canModify(member))
                        {
                        %>
                        | <a href="<%=editEventURL%>">Editar</a> | <a href="<%=viewUrl%>">Eliminar</a>
                        <%
                        }
                %>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=video.getViews()%> vistas
            </p>
        </div>
    </div>
    <%
                }
            }
    %>

    <!-- div id="paginacion">
        <a href="#"><img src="images/pageArrowLeft.gif" alt="anterior"></a> <a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a> <a href="#"><img src="images/pageArrowRight.gif" alt="siguiente"></a>
    </div-->
</div>
<div class="columnaCentro">
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
        <li><a href="<%=urla%>">Cancelar suscripción a comunidad</a></li>
        <%
                }
            }
            String pageUri="/swbadmin/jsp/microsite/rss/rss.jsp?video="+java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS a videos de la comunidad</a></li>
        </ul>
</div>
