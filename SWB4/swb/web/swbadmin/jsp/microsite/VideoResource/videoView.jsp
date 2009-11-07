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

    %>
    <div class="noticia">
        <img src="<%=video.getPreview()%>" alt="<%= video.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=video.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=video.getCreator().getFullName()%><br><%=dateFormat.format(video.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(video.getCreated(), user.getLanguage())%></p>
            <p>
                <%=video.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
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

</div>
<%--
<%
    if(member.canAdd())
    {
%>
    <br />
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act","add").toString()%>">Agregar Video</a></p>
    </div>
<%
        if(wputil!=null && member.canView())
        {
            if(!wputil.isSubscribed(member))
            {
%>
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getActionUrl().setParameter("act","subscribe").toString()%>">Suscribirse a este elemento</a></p>
    </div>
<%
            }else
            {
%>
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getActionUrl().setParameter("act","unsubscribe").toString()%>">Cancelar suscripción</a></p>
    </div>
<%
            }
        }
    }%>

<br /><br />
<div id="entriesWrapper">
<%
    Iterator<VideoElement> it=VideoElement.listVideoElementByWebPage(wpage,wpage.getWebSite());
    int i=0;
    while(it.hasNext())
    {
        VideoElement video=it.next();
        SWBResourceURL viewurl=paramRequest.getRenderUrl().setParameter("act","detail").setParameter("uri",video.getURI());
        if(video.canView(member))
        {
            if(i%2==0)
                out.println("<tr>");
%>
    <div class="entry">
        <a href="<%=viewurl%>">
            <img src="<%=video.getPreview()%>" alt="<%= video.getTitle()%>" border="0" />
        </a>
        <div class="entryInfo">
            <p><%=SWBUtils.TEXT.getTimeAgo(video.getCreated(),user.getLanguage())%></p>
            <p class="tituloNaranja"><%=video.getTitle()%></p>
            <p><%=video.getDescription()%></p>
            <p><strong><%=video.getCreator().getFullName()%></strong></p>            
            <p><%
                DecimalFormat df=new DecimalFormat("#0.0#");
                String rank=df.format(video.getRank());
            %>
            Calificación: <%=rank%> de 5</p>
            <p><%=video.getViews()%> vistas</p>
            <div class="clear">&nbsp;</div>
        </div>
    </div>

<%

        }
    }
%>
</div>

--%>