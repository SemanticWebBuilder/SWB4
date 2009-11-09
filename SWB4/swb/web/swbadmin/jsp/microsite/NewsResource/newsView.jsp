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
                    String editEventURL = paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI()).toString();
    %>
    <div class="noticia">
        <img src="<%= SWBPortal.getWebWorkPath() + anew.getNewsThumbnail()%>" alt="<%= anew.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=anew.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=anew.getAuthor()%><br><%=dateFormat.format(anew.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%></p>
            <p>
                <%=anew.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
                <%
                    if(anew.canModify(member))
                        {
                        %>
                        | <a href="<%=editEventURL%>">Editar</a> | <a href="<%=viewUrl%>">Eliminar</a>
                        <%
                        }
                %>
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
            String pageUri="/swbadmin/jsp/microsite/rss/rss.jsp?news="+java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS a noticias de la comunidad</a></li>
        </ul>
</div>

