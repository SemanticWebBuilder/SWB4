<%@page contentType="text/html"%>
<%@page import="java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<script language="Javascript" type="text/javascript">
    function validateremove(url, title,uri)
    {
        if(confirm('¿Esta seguro de borrar la noticia: '+title+'?'))
        {
            var url=url+'&uri='+escape(uri);
            window.location.href=url;
        }
    }
</script>
<%!    private static final int ELEMENETS_BY_PAGE = 5;
%>
<%
            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            String cssPath = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            String urladd = paramRequest.getRenderUrl().setParameter("act", "add").toString();
            boolean canadd = member.canAdd();
            String suscribeURL = paramRequest.getActionUrl().setParameter("act", "subcribe").toString();
            String unSuscribeURL = paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString();

            ArrayList<NewsElement> elements = new ArrayList();
            int elementos = 0;
            Iterator<NewsElement> it = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
            it = SWBComparator.sortByCreated(it, false);
            while (it.hasNext())
            {
                NewsElement element = it.next();
                if (element.canView(member))
                {
                    elements.add(element);
                    elementos++;
                }
            }
            int paginas = elementos / ELEMENETS_BY_PAGE;
            if (elementos % ELEMENETS_BY_PAGE != 0)
            {
                paginas++;
            }
            int inicio = 0;
            int fin = ELEMENETS_BY_PAGE;
            int ipage = 1;
            if (request.getParameter("ipage") != null)
            {
                try
                {
                    ipage = Integer.parseInt(request.getParameter("ipage"));
                    inicio = (ipage * ELEMENETS_BY_PAGE) - ELEMENETS_BY_PAGE;
                    fin = (ipage * ELEMENETS_BY_PAGE);
                }
                catch (NumberFormatException nfe)
                {
                    ipage = 1;
                }
            }
            if (ipage < 1 || ipage > paginas)
            {
                ipage = 1;
            }
            if (inicio < 0)
            {
                inicio = 0;
            }
            if (fin < 0)
            {
                fin = ELEMENETS_BY_PAGE;
            }
            if (fin > elementos)
            {
                fin = elementos;
            }
            if (inicio > fin)
            {
                inicio = 0;
                fin = ELEMENETS_BY_PAGE;
            }
            if (fin - inicio > ELEMENETS_BY_PAGE)
            {
                inicio = 0;
                fin = ELEMENETS_BY_PAGE;
            }
            inicio++;
%>

<div class="columnaIzquierda">
    <!-- paginacion -->
    <%
            if (paginas > 1)
            {
    %>
    <div id="paginacion">


        <%
                String nextURL = "#";
                String previusURL = "#";
                if (ipage < paginas)
                {
                    nextURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage + 1);
                }
                if (ipage > 1)
                {
                    previusURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage - 1);
                }
                if (ipage > 1)
                {
        %>
        <a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"></a>
            <%
                }
                for (int i = 1; i <= paginas; i++)
                {
            %>
        <a href="<%=wpage.getUrl()%>?ipage=<%=i%>"><%=i%></a>
        <%
                }
        %>


        <%
                if (ipage != paginas)
                {
        %>
        <a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"></a>
            <%
                }
            %>
    </div>
    <%
            }
    %>
    <!-- fin paginacion -->
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
        <a class="adminTool" href="<%=suscribeURL%>">Suscribirse a canal de noticias</a>
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
            if (elements.size() == 0)
            {
    %>
    <p>No hay noticias registradas en la comunidad</p>
    <%            }
    int iElement = 0;
    for (NewsElement anew : elements)
    {
        if (anew.canView(member))
        {
            iElement++;

            if (iElement >= inicio && iElement <= fin)
            {
                SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
                String rank = df.format(anew.getRank());
                String editEventURL = paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI()).toString();
                SWBResourceURL removeUrl = paramRequest.getActionUrl();
                removeUrl.setParameter("act", "remove");
                String removeurl = "javascript:validateremove('" + removeUrl + "','" + anew.getTitle() + "','" + anew.getURI() + "')";
    %>
    <div class="noticia">
        <img src="<%= SWBPortal.getWebWorkPath() + anew.getNewsThumbnail()%>" alt="<%= anew.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=anew.getTitle()%></h2>
            <p>&nbsp;<br>Por: <%=anew.getAuthor()%><br><%=dateFormat.format(anew.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%></p>
            <p>
                <%=anew.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
                <%
                        if (anew.canModify(member))
                        {
                %>
                | <a href="<%=editEventURL%>">Editar</a> | <a href="<%=removeurl%>">Eliminar</a>
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
            }
    %>


    <!-- paginacion -->
    <%
                if (paginas > 1)
                {
    %>
    <div id="paginacion">


        <%
                    String nextURL = "#";
                    String previusURL = "#";
                    if (ipage < paginas)
                    {
                        nextURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage + 1);
                    }
                    if (ipage > 1)
                    {
                        previusURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage - 1);
                    }
                    if(ipage>1)
                    {
        %>
        <a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"></a>
            <%
            }
                    for (int i = 1; i <= paginas; i++)
                    {
            %>
        <a href="<%=wpage.getUrl()%>?ipage=<%=i%>"><%=i%></a>
        <%
                    }
        %>


        <%
                if (ipage != paginas)
                {
        %>
        <a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"></a>
            <%
                }
            %>
    </div>
    <%
            }
    %>
    <!-- fin paginacion -->
</div>
<div class="columnaCentro">
    <%
            if (paginas > 1)
            {
    %>
    <br><br>
    <%            }
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
        <li><a href="<%=urla%>">Cancelar suscripción a comunidad</a></li>
        <%
                }
            }
            String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?news=" + java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de noticias de la comunidad</a></li>
    </ul>
</div>

