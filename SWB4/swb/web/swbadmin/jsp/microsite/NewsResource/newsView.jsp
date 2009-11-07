<%@page contentType="text/html"%>
<%@page import="java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");            
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String urladd = paramRequest.getRenderUrl().setParameter("act", "add").toString();
            boolean canadd = member.canAdd();
            String suscribeURL = paramRequest.getActionUrl().setParameter("act", "subcribe").toString();
            String unSuscribeURL = paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString();
%>

<div class="columnaIzquierda">
    <div class="adminTools">
        <%
            if (canadd)
            {
        %>
        <a class="adminTool" href="<%=urladd%>">Agregar noticia</a>
        <%
            }
            if (wputil != null && member.canView())
            {
                if (!wputil.isSubscribed(member))
                {
        %>
        <a class="adminTool" href="<%=suscribeURL%>">Suscribirse</a>
        <%
                    }
                    else
                    {
        %>
        <a class="adminTool" href="<%=unSuscribeURL%>">Cancelar suscripción</a>
        <%
                        }
                    }


        %>



    </div>
    <%
            Iterator<NewsElement> eit = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
            while (eit.hasNext())
            {
                NewsElement anew = eit.next();
                if (anew.canView(member))
                {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                    String rank = df.format(anew.getRank());
    %>
    <div class="noticia">
        <img src="<%= SWBPortal.getWebWorkPath() + anew.getNewsThumbnail()%>" alt="<%= anew.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=anew.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=anew.getAuthor()%><br><%=dateFormat.format(anew.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%></p>
            <p>
                <%=anew.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=anew.getViews()%> vistas
            </p>
        </div>
    </div>
    <%
                }
            }
    %>


    <!-- <div id="paginacion">
        <a href="#"><img src="images/pageArrowLeft.gif" alt="anterior"></a> <a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a> <a href="#"><img src="images/pageArrowRight.gif" alt="siguiente"></a>
    </div >-->
</div>
<div class="columnaCentro">
    
</div>

<%--
    if (member.canAdd()) {
%>
    <br />
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar noticia</a></p>
    </div>
<%
        if(wputil != null && member.canView()) {
            if(!wputil.isSubscribed(member)) {
%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "subcribe").toString()%>">Suscribirse</a></p></div>
<%
            }else {
%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString()%>">Cancelar suscripción</a></p></div>
<%
            }
        }
    }
%>

<br /><br />
<div id="entriesWrapper">
<%
    Iterator<NewsElement> eit = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
    while (eit.hasNext()) {
        NewsElement anew = eit.next();
        if(anew.canView(member)) {
            SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
%>

<div class="entry">
    <a href="<%= viewUrl%>">
        <img src="<%= SWBPortal.getWebWorkPath()+anew.getNewsThumbnail() %>" alt="<%= anew.getTitle()%>" border="0" />
    </a>
    <div class="entryInfo">
        <p class="tituloNaranja"><%= anew.getTitle()%>&nbsp;(<%= anew.getCitation()%>)</p>
        <p>Por:&nbsp;<strong><%= anew.getAuthor()%></strong></p>
        <p class="eventoInicio">
            <strong><%= dateFormat.format(anew.getCreated()) %></strong> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%>
        </p>
        <p><%= anew.getDescription()%>&nbsp;|&nbsp;<a href="<%=viewUrl%>">Ver m&aacute;s</a></p>
        <p>Puntuación:&nbsp;<%= anew.getRank()%></p>
        <p><%= anew.getViews()%> vistas.</p>
        <div class="clear">&nbsp;</div>
    </div>
</div>
<%
        }
    }
%>

</div>

--%>